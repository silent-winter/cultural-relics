package com.buct.computer.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.buct.computer.request.CulturalRelicInfoVO;
import com.buct.computer.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/16/11:53
 */
@RestController
@RequestMapping("/oss")
@Slf4j
public class OSSController {

    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

    @Autowired
    private OSS ossClient;

    @PostMapping("/uploadAndSave")
    @Transactional
    public ApiResult<PutObjectResult> uploadAndSave(@RequestBody List<CulturalRelicInfoVO> culturalRelicInfoVOList) {
        String url = "https://images.collections.yale.edu/iiif/2/yuag:94377523-2f73-493b-836c-d776030bd8ac/full/!480,480/0/default.jpg";
        try {
            InputStream inputStream = new URL(url).openStream();
            // 设置设置 HTTP 头 里边的 Content-Type
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");
            objectMetadata.setContentDisposition("inline");
            // 创建PutObject请求。
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, "test/test.jpg", inputStream, objectMetadata);
            return ApiResult.success(putObjectResult);
        } catch (OSSException oe) {
            log.warn("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.");
            log.warn("Error Message={}, Error Code={}, Request ID={}, Host ID={}",
                    oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            return ApiResult.fail();
        } catch (ClientException | MalformedURLException ce) {
            log.warn("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.warn("Error Message{}", ce.getMessage());
            return ApiResult.fail();
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.fail();
        }
    }

}
