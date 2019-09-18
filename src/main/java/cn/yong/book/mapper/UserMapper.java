package cn.yong.book.mapper;

import cn.yong.book.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description:用户管理中心
 * 持久层
 * Date: 0:50 2019/1/4
 *
 * @author yong
 * @see
 */
public interface UserMapper {
    /**
     * 获取所有管理员的信息
     * @return
     */
    List<User> getAll();

    /**
     * 插入数据
     * @param user
     * @return
     */
    int insertUser(@Param("user") User user);

    /**
     * 跟改数据
     * @param user
     * @return
     */
    int updateUser(@Param("user") User user);

    /**
     * 根据指定id删除数据
     * @param id
     * @return
     */
    int deleteUserById(@Param("id") int id);

    /**
     * 根据id批量删除数据
     * @param data
     * @return
     */
    int bathDeleteUserById(@Param("data")int[] data);
}
