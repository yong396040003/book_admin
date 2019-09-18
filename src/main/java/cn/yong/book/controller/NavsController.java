package cn.yong.book.controller;

import cn.yong.book.service.NavService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 * 左边导航栏遍历
 * Date: 16:58 2018/12/10
 *
 * @author yong
 * @see
 */
@Controller
public class NavsController {
    @Autowired
    private NavService navService;

    @RequestMapping("/nav.do")
    @ResponseBody
    public JSONObject getAllNavList(){
        return navService.getAllNavList();
    }
}
