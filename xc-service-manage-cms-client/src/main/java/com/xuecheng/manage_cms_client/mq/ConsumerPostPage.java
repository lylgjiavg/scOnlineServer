package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @Classname ConsumerPostPage
 * @Description TODO
 * @Date 2019/7/8 16:53
 * @Created by Jiavg
 */
@Component
public class ConsumerPostPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);
    @Autowired
    private PageService pageService;
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){

        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String) map.get("pageId");

        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(!optional.isPresent()){
            LOGGER.error("postPage():cmsPage is not found, pageId:{}", pageId);
            return;
        }
        
        //将页面保存到服务器物理路径
        pageService.savePageToServerPath(pageId);

    }
    
}
