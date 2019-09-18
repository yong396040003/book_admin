package cn.yong.book.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Date: 18:36 2019/8/10
 *
 * @author yong
 * @see
 */
public interface PhoneService {

    /**
     * 根据类别获取每一类书籍的数量
     * @return
     */
    JSONObject getClassify();

    /**
     * 根据不同的标题返回不同的书籍(算了一次性返回完算了)
     * @return
     */
    JSONObject getBookCityBook();

    /**
     * 返回最后一张章节
     * @return
     */
    JSONObject getLastCatalog(int number);

    /**
     * 根据number和count返回指定的章节
     * @param number
     * @param count
     * @return
     */
    JSONObject getCatalog(int number, int count);
}
