package cn.yong.book.pojo;

import java.util.List;

/**
 * Description:
 * Date: 10:46 2019/7/11
 *
 * @author yong
 * @see
 */
public class Nav {
    /**
     * 主键
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 链接
     */
    private String href;
    /**
     * 是否展开
     */
    private boolean spread;
    /**
     * 判断导航栏是几级
     */
    private int pid;
    /**
     * 子导航栏
     */
    private List<Nav> children;
    /**
     * 表名
     */
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<Nav> getChildren() {
        return children;
    }

    public void setChildren(List<Nav> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Nav{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", href='" + href + '\'' +
                ", spread=" + spread +
                ", pid=" + pid +
                ", children=" + children +
                ", category='" + category + '\'' +
                '}';
    }
}
