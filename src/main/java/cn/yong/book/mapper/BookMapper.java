package cn.yong.book.mapper;

import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import cn.yong.book.pojo.NewBookDate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description:
 * @Date: 2019/4/11 13:50
 *
 * @author yong
 * @version 1.0
 * @see
 */
public interface BookMapper {
    /**
     * 查询书籍总数
     * @param arg1
     * @return
     */
    @Select("SELECT COUNT(*) FROM book where category = #{arg1}")
    int selectBookCount(String arg1);

    /**
     * 查询所有完本书籍总数
     * @param arg1
     * @return
     */
    @Select("SELECT COUNT(*) FROM book where `status` = #{arg1}")
    int selectBookStatusAllCount(String arg1);

    /**
     * 查询指定书籍完本书籍总数
     * @param status
     * @param category
     * @return
     */
    @Select("SELECT COUNT(*) FROM book where `status` = '${status}' AND `category` = '${category}'")
    int selectBookStatusCount(@Param(value = "status") String status,@Param(value = "category") String category);

    /**
     *
     * 根据传过来的参数查询对应的book数据
     * @param category 小说类型
     * @param sort 最热 最新 推荐
     * @param total book总数
     * @param page
     * @param limit
     * @return
     */
    List<Book> selectBook(@Param(value = "category") String category,@Param(value = "sort") String sort,@Param(value = "total") int total,@Param(value = "page") int page,@Param(value = "limit") int limit);

    /**
     * 根据传过来的参数查询对应的book数据
     * @param category 小说类型
     * @param status 状态 (特殊：完结)
     * @param sort
     * @param total
     * @param status
     * @param page
     * @param limit
     * @return
     */
    List<Book> selectFinishBook(@Param(value = "category") String category,@Param(value = "sort") String sort,@Param(value = "total") int total,@Param(value = "status") String status,@Param(value = "page") int page,@Param(value = "limit") int limit);

    /**
     * 根据传过来的参数查询对应的book数据
     * @param status 状态(特殊：完结)
     * @param sort
     * @param total
     * @param status
     * @param page
     * @param limit
     * @return
     */
    List<Book> selectFinishAllBook(@Param(value = "sort") String sort,@Param(value = "total") int total,@Param(value = "status") String status,@Param(value = "page") int page,@Param(value = "limit") int limit);

    /**
     * 根据book编号来查询相应的book数据
     * @param bookNumber
     * @return
     */
    Book selectBookByNumber(@Param(value = "bookNumber") int bookNumber);

    /**
     * 根据book编号来查询相应的catalogue数据
     * @param bookNumber
     * @return
     */
    List<Catalogue> selectCatalogueByNumber(@Param(value = "bookNumber") int bookNumber);

    /**
     * 根据count查询对应的章节信息
     * @param count
     * @param bookNumber
     * @return
     */
    Catalogue selectBookBodyByCount(@Param(value = "count") int count,@Param(value = "bookNumber") int bookNumber);

    /**
     * 根据点击数来显示排行榜
     * 手机端
     * @param hits
     * @return
     */
    List<Book> selectBookByHits(@Param(value = "hits") String hits);

    /**
     * 根据点击数来显示排行榜
     * pc端
     * @return
     */
    List<Book> selectBookByTotalHits();

    /**
     * 根据作者查询对应的书籍
     * @param author
     * @return
     */
    @Select("SELECT `id`,`number`,`category`,`name`,`author`,`status`,`wordNumber`,`data`,`img`,`synopsis` FROM book WHERE 1=1 AND author=\"${author}\"")
    List<Book> selectBookByAuthor(@Param(value = "author") String author);

    /**
     * 根据作者或书名模糊搜索
     * @param page
     * @param limit
     * @param author
     * @param name
     * @return
     */
    List<Book> selectBookLikeAuthorOrName(@Param(value = "page") int page,@Param("limit") int limit,@Param(value = "author") String author,@Param(value = "name") String name);

    /**
     * 查询作者或书名模糊搜索的总数
     * @param author
     * @param name
     * @return
     */
    @Select("SELECT count(*) from book WHERE author LIKE \"%${author}%\" OR `name` LIKE \"%${name}%\"")
    int selectBookLikeAuthorOrNameCount(@Param(value = "author") String author,@Param(value = "name") String name);

    /**
     * 插入数据
     * @param book
     * @return
     */
    int insertBook(@Param("book") Book book);

    /**
     * 修改书籍
     * @param book
     * @return
     */
    int updateBook(@Param("book") Book book);

    /**
     * 查询所有新添书籍
     * @return
     */
    List<Book> selectNewBook();

    /**
     * 插入该书添加的时间
     * @param newBookDate
     * @return
     */
    @Insert("INSERT INTO newbookdate (`id`,`date`) VALUES (\"${newbookdate.id}\",\"${newbookdate.date}\")")
    int insertNewBookDate(@Param("newbookdate") NewBookDate newBookDate);

    /**
     * 根据id删除指定数据
     * @param number
     * @return
     */
    int deleteBookById(@Param("number") int number);

    /**
     * 根据id批量删除数据
     * @param data
     * @return
     */
    int bathDeleteBookById(@Param("data")int[] data);

    /**
     * 查询书籍最后一本书的id
     * @return
     */
    @Select("SELECT id FROM book ORDER BY id DESC LIMIT 0,1")
    int getLastBookId();
}
