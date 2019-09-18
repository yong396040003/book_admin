package cn.yong.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description:
 * Date: 20:18 2018/12/31
 *
 * @author yong
 * @see
 */
@Controller
public class LoginExit {

    /**
     * 清除session,跳转到登录首页
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/loginExit.do")
    public void loginExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("./");
    }
}
