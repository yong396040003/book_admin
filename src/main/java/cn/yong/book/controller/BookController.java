package cn.yong.book.controller;

import cn.yong.book.juntil.JavaPy;
import cn.yong.book.pojo.Book;
import cn.yong.book.service.BookService;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * book表操作
 * 除特别说明手机、pc端共用
 * 这是对数据库操作四大基本操作（增、删、改、查）
 * 网页可视是基于layUi框架
 * Description:
 * Date: 16:27 2019/2/5
 *
 * @author yong
 * @see
 */
@Controller
public class BookController {
    /**
     * 日志文件
     */
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final static String CONTENTS = "<dd id=\"contents\">+.+?</dd>";
    @Autowired
    private BookService BookService;

    /**
     * 根据传过来的小说参数来查询改类型的小说
     *
     * @param page     页数
     * @param limit    限制
     * @param category 小说类型
     * @param status   状态
     * @param sort     最热 最新 完结（排序）
     * @return
     * @return
     */
    @RequestMapping(value = "/selectBook.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject selectBook(int page, int limit, String category, String status, String sort) {
        return BookService.selectBook(page, limit, category, status, sort);
    }


    /**
     * 根据书号查询该书
     *
     * @param bookNumber
     * @return
     */
    @RequestMapping(value = "/selectBookByNumber.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject selectBookByNumber(int bookNumber) {
        return BookService.selectBookByNumber(bookNumber);
    }


    /**
     * 对章节url在线处理获取章节内容
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "/selectBookBody.do", method = RequestMethod.POST)
    @ResponseBody
    public String selectBookBody(String url) {

        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(10000)
                    .get();
        } catch (IOException e) {
            logger.error("网页连接失败" + e.getMessage());
            e.printStackTrace();
        }
        assert document != null;
        Element element = document.body();
        Element elementById = element.getElementById("contents");
        return elementById.html();
    }

    /**
     * 指定搜索章节
     *
     * @param count
     * @return
     */
    @RequestMapping(value = "selectBookBodyByCount.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject selectBookBodyByCount(int count, int bookNumber) {
        return BookService.selectBookBodyByCount(count, bookNumber);
    }

    /**
     * 根据点击数对图书进行排行
     * 手机端 排行榜
     *
     * @param hits
     * @return
     */
    @RequestMapping(value = "selectBookByHits.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject selectBookByHits(String hits) {
        return BookService.selectBookByHits(hits);
    }

    /**
     * 根据点击数对图书进行排行
     * pc端 排行榜
     *
     * @return
     */
    @RequestMapping(value = "selectBookByTotalHits.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject selectBookByTotalHits(int page,int limit) {
        return BookService.selectBookByTotalHits(page,limit);
    }

    /**
     * 根据作者查询对应的书籍
     *
     * @param author
     * @return
     */
    @RequestMapping(value = "selectBookByAuthor.do", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectBookByAuthor(String author) {
        return BookService.selectBookByAuthor(author);
    }

    /**
     * 根据作者或者书名模糊搜索该书
     *
     * @param author
     * @param name
     * @return
     */
    @RequestMapping(value = "selectBookLikeAuthorOrName.do", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectBookLikeAuthorOrName(int page, int limit, String author, String name) {
        return BookService.selectBookLikeAuthorOrName(page, limit, author, name);
    }

    /**
     * 根据书号查询章节
     * 显示到分页pc端
     * preview页面
     *
     * @param number 书号
     * @return
     */
    @RequestMapping("selectCatalog.do")
    @ResponseBody
    public JSONObject selectCatalog(int number) {
        return BookService.selectCatalogueByNumber(number);
    }

    /**
     * 书籍信息修改/添加
     *
     * @param book
     * @return
     */
    @RequestMapping(value = "/bookUpdate.do",
            method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bookUpdate(@RequestBody Book book) {
        JSONObject jsonObject;
        try {
            jsonObject = BookService.bookUpdate(book);
        } catch (Exception e) {
            jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("msg", "数据异常" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 根据书号删除书籍
     *
     * @param number
     * @return
     */
    @RequestMapping(value = "/deleteBookById.do",
            method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteUserById(int number) {
        return BookService.deleteBookById(number);
    }

    /**
     * 根据book id批量删除书籍
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/bathDeleteBookById.do",
            method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bathDeleteUserById(@RequestParam(value = "data[]") int[] data) {
        return BookService.bathDeleteBookById(data);
    }
}
