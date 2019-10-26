package cn.yong.book.juntil;

import cn.yong.book.pojo.EmailInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Description:
 * Date: 13:19 2019/10/26
 *
 * @author yong
 * @see
 */
public class SendEmailTest {
    public Long time;

    @Before
    public void before() {
        time = System.currentTimeMillis();
    }

    @Test
    public void send() {
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setReceiveUserName("1272514432@qq.com");
        emailInfo.setSubject("一本好书验证码");
        emailInfo.setContent("<h1>你好</h1>");
        SendEmail.send(emailInfo);
    }

    @After
    public void after() {
        System.out.println("测试用时:" + (System.currentTimeMillis() - time) / 1000 + "秒");
    }
}