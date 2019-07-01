package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Classname CmsPageRepositoryTest
 * @Description TODO
 * @Date 2019/6/22 21:15
 * @Created by Jiavg
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateTest {

    @Autowired
    RestTemplate restTemplate;

    /**
     * RestTemplate请求http接口
     */
    @Test
    public void testRestTemplate() {

        ResponseEntity<Map> entity = restTemplate.getForEntity
                ("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);

         Map body = entity.getBody();
        System.out.printf(body.toString());

    }

}
