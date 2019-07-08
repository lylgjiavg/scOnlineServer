package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname ManageCmsClientApplication
 * @Description TODO
 * @Date 2019/7/8 11:27
 * @Created by Jiavg
 */
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")    // 扫描实体类
@ComponentScan(basePackages={"com.xuecheng.framework"})    // 扫描common下的所有类(异常处理类)
@ComponentScan(basePackages={"com.xuecheng.manage_cms_client"})    // 扫描common下的所有类(异常处理类)
public class ManageCmsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class, args);
    }
    
}
