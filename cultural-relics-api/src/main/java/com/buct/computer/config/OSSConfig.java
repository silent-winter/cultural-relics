package com.buct.computer.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/16/12:03
 */
@Configuration
public class OSSConfig {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKeySecret;


    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

}
