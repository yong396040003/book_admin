package cn.yong.book.service;

import cn.yong.book.pojo.Book;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Date: 16:39 2019/2/5
 *
 * @author yong
 * @see
 */
public interface BookService {

    /**
     *根据传过来的小说参数来查询改类型的小说
     * @param page 页数
     * @param limit 限制
     * @param category 小说类型
     * @param status 小说状态
     * @param sort 最热 最新 推荐（排序）
     * @return
     */
    JSONObject selectBook(int page,int limit,String category,String status,String sort);

    /**
     * 通过书号来查询数据
     * @param bookNumber
     * @return
     */
    JSONObject selectBookByNumber(int bookNumber);

    /**
     * 返回章节目录信息
     * @param count
     * @param bookNumber
     * @return
     */
    JSONObject selectBookBodyByCount(int count,int bookNumber);

    /**
     * 根据点击数来显示排行榜
     * 手机端
     * @param hits
     * @return
     */
    JSONObject selectBookByHits(String hits);

    /**
     * 根据点击数来显示排行榜
     * pc
     * @param page
     * @param limit
     * @return
     */
    JSONObject selectBookByTotalHits(int page,int limit);

    /**
     * 根据作者查询对应的书籍
     * @param author
     * @return
     */
    JSONObject selectBookByAuthor(String author);

    /**
     * 根据书名或作者模糊查询
     * @param page
     * @param limit
     * @param author
     * @param name
     * @return
     */
    JSONObject selectBookLikeAuthorOrName(int page, int limit,String author,String name);

    /**
     * 根据book编号来查询相应的catalogue数据
     * @param number
     * @return
     */
    JSONObject selectCatalogueByNumber(int number);

    /**
     * 书籍的修改或添加
     * @param book
     * @return
     */
    JSONObject bookUpdate(Book book);

    /**
     * 根据指定id删除数据
     * @param number
     * @return
     */
    JSONObject deleteBookById(int number);

    /**
     * 根据多个id批量删除数据
     * @param data
     * @return
     */
    JSONObject bathDeleteBookById(int[] data);
}
