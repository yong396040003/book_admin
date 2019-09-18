package cn.yong.book.mapper;

import cn.yong.book.pojo.Manager;

import java.util.List;

/**
 * Description:管理员管理中心
 * Date: 23:59 2019/1/3
 *
 * @author yong
 * @see
 */
public interface ManagerMapper {
    /**
     * 获取所有管理员的信息
     * @return
     */
    List<Manager> getAll();
}
