package com.xuecheng.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MongoConfig
 * @Description TODO
 * @Date 2019/6/29 16:28
 * @Created by Jiavg
 */
@Configuration
public class MongoConfig {
    
    @Value("${spring.data.mongodb.database}")
    String db;

    /**
     * GridFSBucket用于打开下载流对象
     * @param mongoClient
     * @return
     */
    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient){
        MongoDatabase mongoDatabase = mongoClient.getDatabase(db);
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDatabase);
        return gridFSBucket;
    }
    
}
