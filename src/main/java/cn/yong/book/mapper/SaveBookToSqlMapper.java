package cn.yong.book.mapper;

import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description:
 * Date: 14:23 2019/1/21
 *
 * @author yong
 * @see
 */
public interface SaveBookToSqlMapper {
    /**
     * 插入book信息
     * @param books
     * @return
     */
    int saveBook(@Param(value = "books") List<Book> books);

    /**
     * 插入目录信息
     * @param catalogues
     * @return
     */
    int saveCatalogue(@Param(value = "catalogues") List<Catalogue> catalogues);

    /**
     * 查询所有Book的数据
     * @param category
     * @return
     */
    @Select("SELECT * FROM book WHERE 1=1 AND `category` LIKE '%${category}%'")
    List<Book> getBookToSql(@Param(value = "category") String category);
}
