package cn.yong.book.service.serviceimpl;

import cn.yong.book.mapper.SystemSettingMapper;
import cn.yong.book.pojo.Nav;
import cn.yong.book.service.SystemSettingService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Date: 10:19 2019/7/15
 *
 * @author yong
 * @see
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    @Autowired
    SystemSettingMapper systemSettingMapper;

    @Override
    public JSONObject getNav(int page, int limit, String name) {
        PageHelper.startPage(page, limit);
        List<Nav> navList = systemSettingMapper.getNav(name);
        PageInfo pageInfo = new PageInfo<Nav>(navList, limit);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", pageInfo.getList());
        jsonObject.put("count", pageInfo.getTotal());
        jsonObject.put("total", limit);
        jsonObject.put("code", 0);
        return jsonObject;
    }

    @Override
    public JSONObject insertNav(Nav nav, String category) {
        JSONObject jsonObject = new JSONObject();
        int code = -1;
        String msg = "error";

        try {
            //添加数据
            if (nav.getId() == 0) {
                code = systemSettingMapper.insertNav(nav, nav.getCategory());
                if (code > 0) {
                    msg = "success";
                }
            } else { //修改数据
                code = systemSettingMapper.updateNav(nav, nav.getCategory());
                if (code > 0) {
                    msg = "success";
                }
            }
        } catch (Exception e) {
            msg = e.getMessage();
        }

        jsonObject.put("code", code);
        jsonObject.put("msg", msg);

        return jsonObject;
    }

    @Override
    public JSONObject deleteById(int id, String category) {
        JSONObject jsonObject = new JSONObject();
        int code = -1;
        String msg = "error";

        code = systemSettingMapper.deleteNav(id, category);
        if (code > 0) {
            msg = "success";
        }

        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    @Override
    public JSONObject batchDeleteById(int[] ids, String category) {
        JSONObject jsonObject = new JSONObject();
        int code = -1;
        String msg = "error";

        code = systemSettingMapper.batchDeleteNav(ids, category);
        if (code > 0) {
            msg = "success";
        }

        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }
}
