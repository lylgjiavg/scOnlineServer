package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname QueryPageRequest
 * @Description 封装了查询的查询条件信息
 * @Date 2019/6/22 18:14
 * @Created by Jiavg
 */

@Data
public class QueryPageRequest extends RequestData {
    
    @ApiModelProperty("站点id")
    private String siteId;
    
    @ApiModelProperty("页面ID")
    private String pageId;
    
    @ApiModelProperty("页面名称")
    private String pageName;
    
    @ApiModelProperty("别名")
    private String pageAliase;
    
    @ApiModelProperty("模版id")
    private String templateId;
    
    @ApiModelProperty("页面类型")
    private String pageType;
    
    @ApiModelProperty("模板名称")
    private String templateName;

}
