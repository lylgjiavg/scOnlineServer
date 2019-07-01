package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Map;

/**
 * @Classname CmsPageRepositoryTest
 * @Description TODO
 * @Date 2019/6/22 21:15
 * @Created by Jiavg
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFSTest {

    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * GridFsTemplate存储文件
     */
    @Test
    public void testStore() throws FileNotFoundException {
        
        File file = new File("E:/index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);

        ObjectId store = gridFsTemplate.store(fileInputStream, "jianglicheng");
        System.out.printf(store.toString());

    }
    
    /**
     * GridFsTemplate下载文件
     */
    @Test
    public void testGridFsTemplate() throws IOException {
        String fileId = "5d17185fec93ec2c48206259";
        //根据id查询文件 
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象 
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象 
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        InputStream inputStream = gridFsResource.getInputStream();

        String file = IOUtils.toString(inputStream, "utf-8");

        System.out.printf(file);

    }

    /**
     * GridFsTemplate删除文件
     * @throws IOException
     */
    @Test 
    public void testDelFile() throws IOException {
        //根据文件id删除fs.files和fs.chunks中的记录    
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5b32480ed3a022164c4d2f92")));
    }   

    
}
