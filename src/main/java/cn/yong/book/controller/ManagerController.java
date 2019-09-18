package cn.yong.book.controller;

import cn.yong.book.service.ManagerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 * Date: 18:32 2019/7/23
 *
 * @author yong
 * @see
 */
@Controller
public class ManagerController {
    private String managerPhone = "15023501314";
    private String code = "886888";

    @Autowired
    private ManagerService managerService;

    /**
     * 查询管理员数据
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/manager.do")
    @ResponseBody
    public JSONObject manager(int page, int limit) {
        return managerService.getAll(page, limit);
    }

    /**
     * 根据phoneNum验证是否是管理员留的电话
     *
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/isManager.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isManager(String phoneNum) {
        JSONObject jsonObject = new JSONObject();
        if (managerPhone.equals(phoneNum)) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "验证成功");
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "验证失败");
        return jsonObject;
    }

    /**
     * 根据发送到手机的验证码判断后续操作
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/isManagerCode.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isManagerCode(String code) {
        JSONObject jsonObject = new JSONObject();
        if (code.equals(code)) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "成功");
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "验证码错误");
        return jsonObject;
    }
}
