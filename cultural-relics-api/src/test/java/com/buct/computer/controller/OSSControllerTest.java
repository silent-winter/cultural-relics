package com.buct.computer.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
        String url = "https://images.collections.yale.edu/iiif/2/yuag:94377523-2f73-493b-836c-d776030bd8ac/full/!480,480/0/default.jpg";
        try {
            InputStream inputStream = new URL(url).openStream();
            // 设置设置 HTTP 头 里边的 Content-Type
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");
            objectMetadata.setContentDisposition("inline");
            // 创建PutObject请求。
            ossClient.putObject(bucketName, "test/test.jpg", inputStream, objectMetadata);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException | MalformedURLException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
