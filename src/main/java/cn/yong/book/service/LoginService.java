package cn.yong.book.service;

/**
 * Description:
 * Date: 16:25 2018/12/30
 *
 * @author yong
 * @see
 */
public interface LoginService {

    /**
     * 根据传值去数据库查询是否有该数据
     * @param username
     * @param password
     * @return
     */
    Boolean checkout(String username,String password);

    /**
     * 获取登录用户的图标
     * @param username
     * @return
     */
    String getIcon(String username);
}
