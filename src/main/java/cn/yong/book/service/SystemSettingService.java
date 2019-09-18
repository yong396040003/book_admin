package cn.yong.book.service;

import cn.yong.book.pojo.Nav;
import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Date: 10:17 2019/7/15
 *
 * @author yong
 * @see
 */
public interface SystemSettingService {

    /**
     * 获取指定的nav 查询nav并分页
     * @param page
     * @param limit
     * @param name
     * @return
     */
    JSONObject getNav(int page,int limit,String name);

    /**
     * 根据category不同插入nav
     * @param nav
     * @param category
     * @return
     */
    JSONObject insertNav(Nav nav, String category);

    /**
     * 根据id删除数据
     * @param id
     * @param category
     * @return
     */
    JSONObject deleteById(int id,String category);

    /**
     * 根据id数组删除数据
     * @param ids
     * @param category
     * @return
     */
    JSONObject batchDeleteById(int[] ids, String category);
}
