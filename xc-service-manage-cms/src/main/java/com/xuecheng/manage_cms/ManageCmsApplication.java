package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname ManageCmsApplication
 * @Description 微服务启动类
 * @Date 2019/6/22 18:58
 * @Created by Jiavg
 */
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")    // 扫描实体类
@ComponentScan(basePackages={"com.xuecheng.api"})   // 扫描接口
@ComponentScan(basePackages={"com.xuecheng.manage_cms"})    // 扫描本项目下的所有类
@ComponentScan(basePackages={"com.xuecheng.framework"})    // 扫描common下的所有类(异常处理类)
public class ManageCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }
   
    /**
     * RestTemplate的底层可以使用第三方的http客户端工具实现http的请求
     *      常用的http客户端工具有Apache HttpClient、OkHttpClient等，
     *      本项目使用OkHttpClient完成http请求，原因也是因为它的性能比较出众。
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
    
}
