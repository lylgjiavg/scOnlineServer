package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;

/**
 * @Classname QueryPageRequest
 * @Description 封装了查询的查询条件信息
 * @Date 2019/6/22 18:14
 * @Created by Jiavg
 */

@Data
public class QueryPageRequest extends RequestData {
    //站点id
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模版id
    private String templateId;

}
