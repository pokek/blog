package com.blog.vueblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    通过 scanBasePackages 进行扫外部包 和启动类 自己的包 才能被浏览器正常访问.....
 */
@SpringBootApplication(scanBasePackages = {"com.blog.framwork", "com.blog.vueblog"})
@MapperScan("com.blog.framwork.mapper")
public class VueBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueBlogApplication.class, args);
    }
}
