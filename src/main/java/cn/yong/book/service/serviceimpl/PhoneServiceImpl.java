package cn.yong.book.service.serviceimpl;

import cn.yong.book.juntil.ReadWriteFile;
import cn.yong.book.mapper.PhoneMapper;
import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import cn.yong.book.service.PhoneService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Date: 18:38 2019/8/10
 *
 * @author yong
 * @see
 */
@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    public JSONObject getClassify() {
        String[] category = {"都市言情", "其他小说", "玄幻魔法", "女生小说", "历史军事", "武侠仙侠", "网游竞技", "科幻小说", "恐怖灵异"};
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < category.length; i++) {
            int count = phoneMapper.getClassifyCount(category[i]);
            switch (i) {
                case 0:
                    jsonObject.put("city", count);
                    break;
                case 1:
                    jsonObject.put("colleagues", count);
                    break;
                case 2:
                    jsonObject.put("fantasy", count);
                    break;
                case 3:
                    jsonObject.put("girl", count);
                    break;
                case 4:
                    jsonObject.put("history", count);
                    break;
                case 5:
                    jsonObject.put("martial", count);
                    break;
                case 6:
                    jsonObject.put("online", count);
                    break;
                case 7:
                    jsonObject.put("science", count);
                    break;
                case 8:
                    jsonObject.put("supperNatural", count);
                    break;
                default:
                    break;
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getBookCityBook() {
        JSONObject jsonObject = new JSONObject();
        List<Book> hotBook = phoneMapper.getHotBook();
        List<Book> newBook = phoneMapper.getNewBook();
        List<Book> myBook = phoneMapper.getMyBook();
        List<Book> endBook = phoneMapper.getEndBook();
        for (Book book : hotBook) {
            ReadWriteFile.readSynopsis(book);
        }
        for (Book book : newBook) {
            ReadWriteFile.readSynopsis(book);
        }
        for (Book book : myBook) {
            ReadWriteFile.readSynopsis(book);
        }
        for (Book book : endBook) {
            ReadWriteFile.readSynopsis(book);
        }
        jsonObject.put("myBook", myBook);
        jsonObject.put("hotBook", hotBook);
        jsonObject.put("newBook", newBook);
        jsonObject.put("endBook", endBook);
        return jsonObject;
    }

    @Override
    public JSONObject getLastCatalog(int number) {
        JSONObject jsonObject = new JSONObject();
        int code = -1;
        String msg = "error";
        Catalogue catalogue = phoneMapper.getLastCatalog(number);
        int count = phoneMapper.getCount(number);
        if (catalogue != null && count >= 0) {
            code = 0;
            msg = "success";
            jsonObject.put("data", catalogue);
            jsonObject.put("count", count);
        }
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    @Override
    public JSONObject getCatalog(int number, int count) {
        JSONObject jsonObject = new JSONObject();
        int code = -1;
        String msg = "error";

        Catalogue catalogue = phoneMapper.getCatalog(number, count);
        if (catalogue != null) {
            code = 0;
            jsonObject.put("msg", "success");
            jsonObject.put("data", catalogue);
        }

        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }
}
