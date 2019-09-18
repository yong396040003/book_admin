package cn.yong.book.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Date: 0:05 2019/1/4
 *
 * @author yong
 * @see
 */
public interface ManagerService {
    /**
     * 查询所有的数据
     * @return
     */
    JSONObject getAll(int page,int limit);
}
