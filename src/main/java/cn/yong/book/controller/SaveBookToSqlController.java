package cn.yong.book.controller;

import cn.yong.book.juntil.JavaPy;
import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import cn.yong.book.service.SaveBookToSqlService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description:
 * 保存book信息到数据库MySQL去（目前数据内容已保存到数据库,所以该方法暂时弃用）
 * Date: 13:38 2019/1/21
 *
 * @author yong
 * @see
 */
@Controller
public class SaveBookToSqlController {
    private static final Logger logger = LoggerFactory.getLogger(SaveBookToSqlController.class);

    @Autowired
    private SaveBookToSqlService sqlService;

    private int start = 1;
    private int end = 50;

    /**
     * 把数据保存到文件中
     * @return
     */
    @RequestMapping("/saveBookToSql")
    @ResponseBody
    public String saveBookToSql(){
        while (true) {
            //拿到书籍信息
            List<Book> books = JavaPy.getBook(start, end);
            //拿到目录信息
            List<Catalogue> catalogues = JavaPy.getPathMulu(start, end);
            logger.info("成功插入"+sqlService.saveBook(books)+"条book数据");
            logger.info("成功插入"+sqlService.saveCatalogue(catalogues)+"条目录数据");
            start +=50;
            end +=50;
            if(start >= 34000){
                break;
            }
        }
        return "插入成功";
    }

    /**
     * 查询所有的book信息
     * @param page 页数
     * @param limit 一页显示数量
     * @param paiHang 根据排行的标签显示
     * @return
     */
    @RequestMapping(value = "/getBookToSql",method = RequestMethod.GET,produces = "application/json;charSet=UTF-8")
    @ResponseBody
    public String getBookToSql(int page, int limit,String paiHang){
        return "该方法已弃用";
//        return sqlService.getBookToSql(page,limit,paiHang);
    }



}
