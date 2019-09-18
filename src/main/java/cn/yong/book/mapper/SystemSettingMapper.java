package cn.yong.book.mapper;

import cn.yong.book.pojo.Nav;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Description:
 * Date: 10:09 2019/7/15
 *
 * @author yong
 * @see
 */
public interface SystemSettingMapper {

    /**
     * 获取指定的导航栏
     *
     * @param name
     * @return
     */
    @Select("SELECT `id`,`title`,`icon`,`href`,`spread`,`pid` FROM ${name}")
    List<Nav> getNav(@Param("name") String name);

    /**
     * 插入nav
     *
     * @param nav
     * @param category
     * @return
     */
    @Insert("INSERT INTO `${category}` (`title`,`icon`,`href`,`spread`,`pid`)" +
            " VALUES (\"${nav.title}\",\"${nav.icon}\",\"${nav.href}\",\"${nav.spread}\",${nav.pid})")
    int insertNav(@Param("nav") Nav nav, @Param("category") String category);

    /**
     * 修改nav
     *
     * @param nav
     * @param category
     * @return
     */
    @Update("UPDATE `${category}` SET `title`=\"${nav.title}\"," +
            "`icon`=\"${nav.icon}\",`href`=\"${nav.href}\"," +
            "`spread`=\"${nav.spread}\",`pid`=${nav.pid}" +
            " WHERE id = ${nav.id}")
    int updateNav(@Param("nav") Nav nav, @Param("category") String category);

    /**
     * 根据id删除数据
     *
     * @param id
     * @param category
     * @return
     */
    @Delete("DELETE FROM `${category}` WHERE id = ${id}")
    int deleteNav(@Param("id") int id, @Param("category") String category);

    /**
     * 根据id数组删除数据
     *
     * @param ids
     * @param category
     * @return
     */
    int batchDeleteNav(@Param("ids") int[] ids, @Param("category") String category);
}
