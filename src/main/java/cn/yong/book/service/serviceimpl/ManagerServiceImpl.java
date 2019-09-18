package cn.yong.book.service.serviceimpl;

import cn.yong.book.mapper.ManagerMapper;
import cn.yong.book.pojo.Manager;
import cn.yong.book.service.ManagerService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Date: 0:06 2019/1/4
 *
 * @author yong
 * @see
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 分页查询所有数据
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JSONObject getAll(int page, int limit) {
        //设定一页显示多少数据，默认limit为10
        PageHelper.startPage(page, limit);
        List<Manager> managers = managerMapper.getAll();
        PageInfo<Manager> managerPageInfo = new PageInfo<Manager>(managers);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",managerPageInfo.getList());
        jsonObject.put("count",managerPageInfo.getTotal());
        jsonObject.put("code",0);
        return jsonObject;
    }
}
