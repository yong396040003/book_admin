package cn.yong.book.service.serviceimpl;

import cn.yong.book.juntil.JavaPy;
import cn.yong.book.juntil.ReadWriteFile;
import cn.yong.book.juntil.ResourcePath;
import cn.yong.book.mapper.BookMapper;
import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import cn.yong.book.pojo.NewBookDate;
import cn.yong.book.service.BookService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Date: 16:41 2019/2/5
 *
 * @author yong
 * @see
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper BookMapper;

    @Override
    public JSONObject selectBook(int page, int limit, String category, String status, String sort) {
        //设置一个page的限制
        if (category == null && sort == null) {
            PageHelper.startPage(page, limit);
            List<Book> books = BookMapper.selectNewBook();
            PageInfo pageInfo = new PageInfo<>(books);
            for (Book book : books) {
                ReadWriteFile.readSynopsis(book);
            }
            JSONObject jsonObject = new JSONObject();
            //因为是layUi框架写的后台页面，所以基本json数据格式是这样 count代表总数 total单页总数
            jsonObject.put("data", books);
            jsonObject.put("count", pageInfo.getTotal());
            jsonObject.put("total", limit);
            jsonObject.put("code", 0);
            return jsonObject;
        }
        List<Book> books;
        int total;
        String finish = "完本";
        String all = "all";
        //若类型等于完本
        if (finish.equals(status)) {
//            如果category为all 那么查询book里所有的完本数据
            if (all.equals(category)) {
                total = BookMapper.selectBookStatusAllCount(status);
                //拿到所有完本小说
                books = BookMapper.selectFinishAllBook(sort, total, status, (page - 1) * limit, limit);
            } else {
                total = BookMapper.selectBookStatusCount(status, category);
                System.err.println(category + "     " + total);
                //拿到指定类型的完本小说
                books = BookMapper.selectFinishBook(category, sort, total, status, (page - 1) * limit, limit);
            }
        } else {
            total = BookMapper.selectBookCount(category);
            //根据不同的小说类型拿到小说list列表
            books = BookMapper.selectBook(category, sort, total, (page - 1) * limit, limit);
        }
        for (Book book : books) {
            ReadWriteFile.readSynopsis(book);
        }
        //把拿到的数据列表放到pageInfo实列中
//        PageInfo<Book> pageInfo = new PageInfo<Book>(books);
        //设置一个jsonObject用来返回json数据
        JSONObject jsonObject = new JSONObject();
        //因为是layUi框架写的后台页面，所以基本json数据格式是这样 count代表总数 total单页总数
        jsonObject.put("data", books);
        jsonObject.put("count", total);
        jsonObject.put("total", limit);
        jsonObject.put("code", 0);
        return jsonObject;
    }

    @Override
    public JSONObject selectBookByNumber(int bookNumber) {
        Book book = BookMapper.selectBookByNumber(bookNumber);
        JSONObject jsonObject = new JSONObject();
        ReadWriteFile.readSynopsis(book);
        jsonObject.put("book", book);
        List<Catalogue> catalogueList = BookMapper.selectCatalogueByNumber(bookNumber);
        jsonObject.put("catalogueCount", catalogueList.size());
        jsonObject.put("catalogueList", catalogueList);
        return jsonObject;
    }

    @Override
    public JSONObject selectBookBodyByCount(int count, int bookNumber) {
        String CONTENTS = "<dd id=\"contents\">+.+?</dd>";
        Catalogue catalogue = BookMapper.selectBookBodyByCount(count, bookNumber);
        String body = JavaPy.senGet(catalogue.getUrl());
        List<String> list = JavaPy.RegexString(body, CONTENTS);
        list.set(0, list.get(0).replaceAll("<dd", "<span"));
        list.set(0, list.get(0).replaceAll("<br /><br />", "<br />"));
        list.set(0, list.get(0).replaceAll("</dd>", "</span>"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", catalogue.getName());
        jsonObject.put("contents", list.get(0));
        return jsonObject;
    }

    @Override
    public JSONObject selectBookByHits(String hits) {
        JSONObject jsonObject = new JSONObject();
        List<Book> books = BookMapper.selectBookByHits(hits);
        for (Book book : books) {
            ReadWriteFile.readSynopsis(book);
        }
        jsonObject.put("paiHang", books);
        return jsonObject;
    }

    @Override
    public JSONObject selectBookByTotalHits(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Book> books = BookMapper.selectBookByTotalHits();
        PageInfo pageInfo = new PageInfo<>(books);

        for (Book book : books) {
            ReadWriteFile.readSynopsis(book);
        }
        JSONObject jsonObject = new JSONObject();
        //因为是layUi框架写的后台页面，所以基本json数据格式是这样 count代表总数 total单页总数
        jsonObject.put("data", books);
        jsonObject.put("count", pageInfo.getTotal());
        jsonObject.put("total", limit);
        jsonObject.put("code", 0);
        return jsonObject;
    }

    @Override
    public JSONObject selectBookByAuthor(String author) {
        JSONObject jsonObject = new JSONObject();
        List<Book> books = BookMapper.selectBookByAuthor(author);
        for (Book book : books) {
            ReadWriteFile.readSynopsis(book);
        }
        jsonObject.put("author", books);
        return jsonObject;
    }

    @Override
    public JSONObject selectBookLikeAuthorOrName(int page, int limit, String author, String name) {
        JSONObject jsonObject = new JSONObject();
        page = (page - 1) * limit;
        List<Book> books = BookMapper.selectBookLikeAuthorOrName(page, limit, author, name);
        for (Book book : books) {
            ReadWriteFile.readSynopsis(book);
        }
        int count = BookMapper.selectBookLikeAuthorOrNameCount(author, name);
        //因为是layUi框架写的后台页面，所以基本json数据格式是这样 count代表总数 total单页总数
        jsonObject.put("data", books);
        jsonObject.put("count", count);
        jsonObject.put("total", limit);
        jsonObject.put("code", 0);
        return jsonObject;
    }

    @Override
    public JSONObject selectCatalogueByNumber(int number) {
        List<Catalogue> catalogueList = BookMapper.selectCatalogueByNumber(number);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("catalogueCount", catalogueList.size());
        jsonObject.put("catalogueList", catalogueList);
        return jsonObject;
    }

    @Override
    public JSONObject bookUpdate(Book book) {
        JSONObject jsonObject = new JSONObject();
        int code = 0;
        String msg = "";
        String synopsis = book.getSynopsis();
        //添加书籍 否则修改书籍
        if (book.getId() == 0) {
            int id = BookMapper.getLastBookId();
            book.setId(id + 1);
            String path;
            path = ResourcePath.resourcePath + "/book/" + book.getNumber() + "/0.txt";
            book.setSynopsis(path);
            if (BookMapper.insertBook(book) > 0) {
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                NewBookDate newBookDate = new NewBookDate();
                newBookDate.setId(book.getNumber());
                newBookDate.setDate(df.format(new Date()));
                code = BookMapper.insertNewBookDate(newBookDate);
            } else {
                code = -1;
                msg = "时间添加错误";
            }
        } else {
            code = BookMapper.updateBook(book);
        }
        jsonObject.put("code", code);
        if (code > 0) {
            msg = "数据已添加/修改";
        }
        ReadWriteFile.writeSynopsis(synopsis, book.getNumber());
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    @Override
    public JSONObject deleteBookById(int number) {
        int code = BookMapper.deleteBookById(number);
        String msg = "删除失败";
        if (code > 0) {
            msg = "删除成功";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    @Override
    public JSONObject bathDeleteBookById(int[] data) {
        int code = BookMapper.bathDeleteBookById(data);
        String msg = "删除失败";
        if (code > 0) {
            msg = "删除成功";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

}
