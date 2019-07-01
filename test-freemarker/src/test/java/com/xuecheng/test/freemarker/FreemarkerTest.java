package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-13 10:07
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    // 基于模板生成静态化文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        String path = this.getClass().getResource("/").getPath().toString();
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        //设置字符集
        //加载模板
        Template template = configuration.getTemplate("test1.ftl");
        //数据模型
        Map map = this.getMap();
        //静态化
        String templateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //静态化内容
        //输出文件
        InputStream inputStream = IOUtils.toInputStream(templateIntoString);
        FileOutputStream fileOutputStream = new FileOutputStream("E:/test.html");

        int copy = IOUtils.copy(inputStream, fileOutputStream);
        
    }

    // 基于模板字符串生成静态化文件
    @Test
    public void testGenerateHtmlByString() throws IOException, TemplateException {
        
        Configuration configuration = new Configuration(Configuration.getVersion());

        String templateString="" +
                "<html>\n" +
                "    <head></head>\n" +
                "    <body>\n" +
                "    名称：${name}\n" +
                "    </body>\n" +
                "</html>";
        // 定义模板加载器,加载模板字符串
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        
        // 配置模板加载器,生成模板对象
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("template", "utf-8");

        // 获得模板数据
        Map map = this.getMap();

        String templateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        // 生成静态化页面
        InputStream inputStream = IOUtils.toInputStream(templateIntoString);
        FileOutputStream outputStream = new FileOutputStream(new File("E:/string.html"));
        
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();

    }

    //数据模型
    private Map getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","Jiavg");
        //向数据模型放数据         
        map.put("name","黑马程序员");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMondy(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMondy(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        // 向数据模型放数据         
        map.put("stus",stus);
        // 准备map数据         
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        // 向数据模型放数据         
        map.put("stu1",stu1);
        // 向数据模型放数据         
        map.put("stuMap",stuMap);
        map.put("point", 123456789);
        return map;
    }
    
}
