package cn.yong.book.mapper;

import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 手机端sql操作
 * Description:
 * Date: 18:26 2019/8/10
 *
 * @author yong
 * @see
 */
@Mapper
public interface PhoneMapper {
    /**
     * 根据类别获取每一类书籍的数量
     *
     * @param category
     * @return
     */
    @Select("SELECT COUNT(*) FROM book WHERE category = \"${category}\"")
    int getClassifyCount(@Param(value = "category") String category);

    /**
     * 编辑精选（其实是自己选的🤭）
     *
     * @return
     */
    @Select("SELECT * FROM book ORDER BY totalHits DESC  LIMIT 0 , 6")
    List<Book> getMyBook();

    /**
     * 热门连载
     *
     * @return
     */
    @Select("SELECT * FROM book WHERE `status` = '连载中' ORDER BY `data` DESC LIMIT 6,6")
    List<Book> getHotBook();

    /**
     * 火爆新书
     *
     * @return
     */
    @Select("SELECT * FROM book WHERE `status` = '连载中' ORDER BY `data` DESC LIMIT 0,6")
    List<Book> getNewBook();

    /**
     * 完本推荐
     *
     * @return
     */
    @Select("SELECT * FROM book WHERE `status` = '完本' ORDER BY `data` DESC LIMIT 0,6")
    List<Book> getEndBook();

    /**
     * 根据书号取得最后一章章节
     *
     * @param number
     * @return
     */
    @Select("SELECT `number`,`name`,`count`,`url` FROM catalogue WHERE number = ${number} ORDER BY `count` DESC LIMIT 0,1")
    Catalogue getLastCatalog(@Param("number") int number);

    /**
     * 根据number获取章节数量
     * @param number
     * @return
     */
    @Select("SELECT COUNT(id) FROM catalogue WHERE number = ${number}")
    int getCount(@Param("number") int number);

    /**
     * 根据书号和count返回指定的章节
     * @param number
     * @param count
     * @return
     */
    @Select("SELECT `number`,`name`,`count`,`url` FROM catalogue WHERE number = ${number} AND `count` = ${count} ")
    Catalogue getCatalog(@Param("number") int number,@Param("count") int count);
}
