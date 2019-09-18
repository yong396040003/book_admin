package cn.yong.book.mapper;

import cn.yong.book.pojo.Nav;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description:
 * Date: 16:51 2018/12/10
 *
 * @author yong
 * @see
 */
public interface NavMapper {

    /**
     * 查询内容管理导航栏树
     * @return
     */
    @Select("SELECT `id`,`title`,`icon`,`href`,`spread` FROM content_management WHERE pid = 0")
    List<Nav> getAllContentManagement();

    /**
     * 查询内容管理导航栏树的节点
     * @return
     */
    @Select("SELECT `title`,`icon`,`href`,`spread`,`pid` FROM content_management WHERE pid > 0")
    List<Nav> getAllContentManagementTree();

    /**
     * 查询所有用户中心的导航栏
     * @return
     */
    @Select("SELECT `title`,`icon`,`href`,`spread` FROM user_center WHERE pid = 0")
    List<Nav> getAllUserCenter();

    /**
     * 查询所有系统设置的导航栏
     * @return
     */
    @Select("SELECT `id`,`title`,`icon`,`href`,`spread` FROM system_setting WHERE pid = 0")
    List<Nav> getAllSystemSetting();

    /**
     * 查询所有系统设置的导航栏tree
     * @return
     */
    @Select("SELECT `title`,`icon`,`href`,`spread`,`pid` FROM system_setting WHERE pid > 0")
    List<Nav> getAllSystemSettingTree();

    /**
     * 查询所有使用文档的导航栏
     * @return
     */
    @Select("SELECT `title`,`icon`,`href`,`spread` FROM seraph_api WHERE pid = 0")
    List<Nav> getAllSeraphApi();

}
