package com.xuecheng.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname CmsTemplateAndSite
 * @Description 封装了模板和站点的字段,便于前台页面列表显示
 * @Date 2019/7/2 15:56
 * @Created by Jiavg
 */
@Data
@ToString
public class CmsTemplateAndSite extends CmsTemplate{
    
    private String siteName;
    
}
