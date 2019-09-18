package cn.yong.book.controller;

import cn.yong.book.pojo.User;
import cn.yong.book.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:用户管理中心
 * Date: 0:49 2019/1/4
 *
 * @author yong
 * @see
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取所有用户数据
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/user.do")
    @ResponseBody
    public JSONObject getAll(int page, int limit) {
        return userService.getAll(page, limit);
    }

    /**
     * 用户信息修改/添加
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/userUpdate.do",
            method = RequestMethod.POST)
    @ResponseBody
    public JSONObject userUpdate(@RequestBody User user) {
        JSONObject jsonObject;
        try {
            jsonObject = userService.getMsg(user);
        } catch (Exception e) {
            jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("msg", "数据异常" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 根据用户id删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUserById.do",
            method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteUserById(int userId) {
        return userService.isDelete(userId);
    }

    /**
     * 根据用户id批量删除用户
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/bathDeleteUserById.do",
            method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bathDeleteUserById(@RequestParam(value = "data[]") int[] data) {
        return userService.bathDeleteUserById(data);
    }
}
