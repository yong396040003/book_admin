package cn.yong.book.service;

import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Description:
 * Date: 14:30 2019/1/21
 *
 * @author yong
 * @see
 */
public interface SaveBookToSqlService {
    /**
     * 插入book信息
     * 返回插入成功的条数
     * @param books
     * @return
     */
    int saveBook(List<Book> books);


    /**
     * 插入目录信息
     * 返回插入成功的条数
     * @param catalogues
     * @return
     */
    int saveCatalogue(List<Catalogue> catalogues);

    /**
     *根据不同类别获取小说
     *
     * @param page 页数
     * @param limit 限制
     * @param paiHang 显示类别
     * @return
     */
    JSONObject getBookToSql(int page,int limit,String paiHang);
}
