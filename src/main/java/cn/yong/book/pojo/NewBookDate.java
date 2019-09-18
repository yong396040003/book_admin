package cn.yong.book.pojo;

import java.io.Serializable;

/**
 * Description:
 * 最新添加的书籍id 和 时间
 * Date: 21:27 2019/7/18
 *
 * @author yong
 * @see
 */
public class NewBookDate implements Serializable {
    /**
     * 主键
     */
    private int id;
    /**
     * 时间
     */
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewBookDate{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}
