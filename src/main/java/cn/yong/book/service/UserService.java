package cn.yong.book.service;

import cn.yong.book.pojo.User;
import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Date: 0:52 2019/1/4
 *
 * @author yong
 * @see
 */
public interface UserService {

    /**
     * 查询所有数据
     * @param page
     * @param limit
     * @return
     */
    JSONObject getAll(int page, int limit);

    /**
     * 根据判断返回结果
     * @param user
     * @return
     */
    JSONObject getMsg(User user);

    /**
     * 根基id删除指定数据
     * @param id
     * @return
     */
    JSONObject isDelete(int id);

    /**
     * 根据多个id批量删除数据
     * @param data
     * @return
     */
    JSONObject bathDeleteUserById(int[] data);
}
