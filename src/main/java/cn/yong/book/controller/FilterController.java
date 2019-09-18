package cn.yong.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 * Date: 18:10 2019/7/4
 *
 * @author yong
 * @see
 */
@Controller
public class FilterController {

    /**
     * 拦截页面
     * @return
     */
    @RequestMapping("/filter.do")
    public String filter() {
        return "index/page/404";
    }
}
