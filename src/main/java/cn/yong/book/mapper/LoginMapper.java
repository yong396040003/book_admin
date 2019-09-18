package cn.yong.book.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * Date: 16:12 2018/12/30
 *
 * @author yong
 * @see
 */
@Repository
public interface LoginMapper {

    /**
     * 根据传来的name及密码判断是否有该用户
     * @param username
     * @param password
     * @return
     */
    int checkout(@Param(value="username") String username, @Param(value = "password") String password);

    /**
     * 获取登录账户图标
     * @param username
     * @return
     */
    @Select("SELECT `icon` FROM manager WHERE `username` = #{username}")
    String getIcon(@Param(value = "username") String username);
}
