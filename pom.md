一.springBoot启动所需的依赖包

1. `spring-boot-dependencies` 替换spring-boot-starter-parent，为以后多模块做铺垫。
1. `spring-boot-starter-web` S支持全栈式Web开发，包括Tomcat和spring-webmvc
1. `spring-boot-starter-jdbc` jdbc连接
1. `spring-boot-devtools` 热部署
1. `spring-boot-starter-log4j2 ` 对log4j2日志文件的支持需要和slf4j-api,log4j-slf4j-impl一起使用。
1. `spring-boot-starter-aop` aop
1. `spring-boot-starter-thymeleaf` thymeleaf模板引擎

二.外部依赖lib
-
1. `spring-boot-starter-tomcat` 导入Spring Boot的默认HTTP引擎（ Tomcat）
1. `mybatis-spring-boot-starter` mybatis与springBoot整合
1. `mysql-connector-java` mysql
1. `lombok` lombok简化带码,idea默认支持
1. `druid-spring-boot-starter` alibaba连接池
1. `fastjson` json。
1. `nekohtml` 配置这个才可以用html

