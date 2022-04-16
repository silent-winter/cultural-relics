package com.buct.computer.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/16/11:53
 */
@SpringBootTest
public class OSSControllerTest {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.accessKeyId}")
    private String accessId;
    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKey;
    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

    @Autowired
    private OSS ossClient;


    @Test
    void test() {
        try {
            // 填写Byte数组。
            byte[] content = "Hello OSS".getBytes();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, "test/test.txt", new ByteArrayInputStream(content));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
