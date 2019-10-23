package cn.yong.book.controller;

import cn.yong.book.service.PhoneService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 手机端请求响应 主要用于书城
 * Description:
 * Date: 18:17 2019/8/10
 *
 * @author yong
 * @see
 */
@Controller
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    /**
     * 获取每一本分类书籍数量
     *
     * @return
     */
    @RequestMapping(value = "/getClassifyCount.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getClassifyCount() {
        return phoneService.getClassify();
    }

    /**
     * 根据不同标题返回不同的书籍
     * 书城
     * @return
     */
    @RequestMapping(value = "/getBookCityBook.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getBookCityBook(){
        return phoneService.getBookCityBook();
    }

    /**
     * 返回最后一章章节
     * @return
     */
    @RequestMapping(value = "/getLastCatalog.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getLastCatalog(int number){
        return phoneService.getLastCatalog(number);
    }

    /**
     * 返回指定章节
     * @return
     */
    @RequestMapping(value = "/getCatalog.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCatalog(int number,int count){
        return phoneService.getCatalog(number,count);
    }
}
