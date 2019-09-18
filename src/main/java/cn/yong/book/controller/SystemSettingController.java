package cn.yong.book.controller;

import cn.yong.book.pojo.Nav;
import cn.yong.book.service.SystemSettingService;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Date: 10:08 2019/7/15
 *
 * @author yong
 * @see
 */
@Controller
public class SystemSettingController {
    @Autowired
    SystemSettingService systemSettingService;

    /**
     * 查询指定的nav
     *
     * @param page
     * @param limit
     * @param name
     * @return
     */
    @RequestMapping(value = "/getNav.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getNav(int page, int limit, String name) {
        return systemSettingService.getNav(page, limit, name);
    }

    /**
     * 添加nav 根据category不同
     *
     * @param nav
     * @return
     */
    @RequestMapping(value = "/insertNav.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject insertNav(@RequestBody Nav nav) {
        return systemSettingService.insertNav(nav, nav.getCategory());
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteNavById.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteNav(int id, String category) {
        return systemSettingService.deleteById(id, category);
    }

    /**
     * 根据id删除数据
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/bathDeleteNavById.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject batchDeleteNav(@RequestParam(value = "ids[]") int[] ids, @Param(value = "category") String category) {
        return systemSettingService.batchDeleteById(ids, category);
    }
}
