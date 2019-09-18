package cn.yong.book.controller;

import cn.yong.book.juntil.MD5;
import cn.yong.book.pojo.Manager;
import cn.yong.book.service.LoginService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description:
 * 后盾登录首页
 * Date: 16:07 2018/12/30
 *
 * @author yong
 * @see
 */
@Controller()
@RequestMapping()
public class LoginController {
    /**
     * 日志文件
     */
    private static final Logger logger =LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    /**
     * 根据form表单的值检验是否登录成功
     * true:跳转到后台首页
     * false:返回到当前页面，并提示
     * @return
     */
    @RequestMapping("/login.do")
    public String login(Manager manager, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        //如果session name不为空那么直接跳转到首页
        if(username != null){
            logger.info("session生效哈哈哈");
            model.addAttribute("manager",username);
            manager.setIcon(loginService.getIcon(username));
            model.addAttribute("icon",manager.getIcon());
            return "index/index";
        }
        //true 跳转到index.html
        if(loginService.checkout(manager.getUsername(), MD5.MD5toString(manager.getPassword()))){
            logger.info("登陆成功");
            model.addAttribute("error","");
            session.setAttribute("username",manager.getUsername());
            model.addAttribute("manager",manager.getUsername());
            manager.setIcon(loginService.getIcon(manager.getUsername()));
            model.addAttribute("icon",manager.getIcon());
            return "index/index";
        }else {
            model.addAttribute("error","error:账号与密码不匹配");
            return "login/login";
        }
    }

    /**
     * 移动端手机登陆
     * true:密码正确
     * false:账号或密码错误
     * @return
     */
    @RequestMapping("/loginPhone.do")
    @ResponseBody
    public JSONObject login(Manager manager) {
        JSONObject jsonObject = new JSONObject();
        //true 跳转到index.html
        if(loginService.checkout(manager.getUsername(), MD5.MD5toString(manager.getPassword()))){
            jsonObject.put("code","true");
        }else {
            jsonObject.put("code","false");
        }
        return jsonObject;
    }
}
