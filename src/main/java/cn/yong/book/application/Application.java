package cn.yong.book.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Description:
 * Date: 12:43 2018/12/29
 *
 * @author yong
 * @see
 */
@ComponentScan(basePackages = {"cn.yong.book"})
@MapperScan(value = {"cn.yong.book.mapper"})
//@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration()
@SpringBootApplication(scanBasePackages = {"cn.yong.book.controller", "cn.yong.book.service", "cn.yong.book.service.serviceimpl"})
public class Application extends SpringBootServletInitializer {
    /**
     * 重写方法方能部署成功
     * @param application
     * @return.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args){
        //启动springBoot框架
        SpringApplication.run(Application.class,args);
    }
}
