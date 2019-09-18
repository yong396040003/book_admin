package cn.yong.book.juntil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description:
 * 检查session是否存在
 * Date: 23:29 2019/1/3
 *
 * @author yong
 * @see
 */
public class CheckoutSession {

    public static String checkoutSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        String manager = (String) session.getAttribute("username");
        if(manager != null){
            return manager;
        }else {
            return null;
        }
    }
}
