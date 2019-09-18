package cn.yong.book.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 过滤器
 * Description:
 * Date: 16:03 2018/11/10
 *
 * @author yong
 * @see
 */
@Component
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String url = request.getServletPath();
        if (username != null || "/".equals(url)
                || url.endsWith(".css") || url.endsWith(".png")
                || url.endsWith(".jpg") || url.endsWith(".gif")
                || url.endsWith(".js") || url.endsWith(".do")
                || url.endsWith(".ttf") || url.endsWith(".woff")
                || url.endsWith(".ac") || url.endsWith(".nav")) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("./filter.do");
        }
    }

    @Override
    public void destroy() {
        System.err.println("拦截关闭");
    }
}
