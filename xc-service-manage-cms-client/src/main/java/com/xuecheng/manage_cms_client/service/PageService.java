package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @Classname PageService
 * @Description TODO
 * @Date 2019/7/8 15:49
 * @Created by Jiavg
 */
@Service
public class PageService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    //将页面html保存到页面物理路径
    public void savePageToServerPath(String pageId){
        
        // 根据页面Id获得页面信息
        CmsPage cmsPage = this.findCmsPageById(pageId);
        // 获得文件fileId
        String htmlFileId = cmsPage.getHtmlFileId();
        
        // 根据文件fileId获得文件输出流
        InputStream inputStream = this.findByFileId(htmlFileId);
        // 非法判断
        if(inputStream == null){
            LOGGER.error("findByFileId():inputStream is null,htmlFileId:{}", htmlFileId);
            return;
        }
        
        String siteId = cmsPage.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        // 获得站点物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();

        // 获得页面物理路径
        String pagePhysicalPath = cmsPage.getPagePhysicalPath();

        // 文件输出路径 = 站点物理路径 + 页面物理路径
        String path = sitePhysicalPath + pagePhysicalPath;

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(path));

            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    
    // 根据文件fileId获得文件输出流
    public InputStream findByFileId(String htmlFileId){

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(htmlFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // 根据页面Id获得页面信息
    public CmsSite findCmsSiteById(String cmsSite){
        Optional<CmsSite> optional = cmsSiteRepository.findById(cmsSite);
        if(optional.isPresent()){
            return optional.get();
        }

        return null;
    }
    
    // 根据页面Id获得页面信息
    public CmsPage findCmsPageById(String pageId){
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(optional.isPresent()){
            return optional.get();
        }
        
        return null;
    }
    
}
