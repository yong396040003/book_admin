package cn.yong.book.pojo;

import java.io.Serializable;

/**
 * Description:
 * Date: 17:23 2018/12/30
 *
 * @author yong
 * @see
 */
public class Manager implements Serializable {
    /**
     * id
     */
    private  int id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     *icon
     */
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
