package com.buct.computer.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.buct.computer.common.assembler.CulturalRelicAssembler;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.request.CulturalRelicInfoDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.ICulturalRelicInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
@Api(tags = "oss文件上传接口")
public class OSSController {

    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

    @Autowired
    private OSS ossClient;
    @Autowired
    private ICulturalRelicInfoService culturalRelicInfoService;


    @PostMapping("/uploadAndSave")
    @Transactional
    public ApiResult<List<CulturalRelicInfo>> uploadAndSave(@RequestBody List<CulturalRelicInfoDTO> culturalRelicInfoVOList) {
        Set<String> imgNameSet = new HashSet<>(128);
        // 对象mapping
        List<CulturalRelicInfo> culturalRelicInfos = culturalRelicInfoVOList.stream()
                .map(e -> CulturalRelicAssembler.MAPPER.culturalRelicInfoDTOToCulturalRelicInfo(e, 0))
                .collect(Collectors.toList());
        try {
            for (CulturalRelicInfoDTO culturalRelicInfoDTO : culturalRelicInfoVOList) {
                String imgName = culturalRelicInfoDTO.getImgName();
                if (imgNameSet.contains(imgName)) {
                    continue;
                }
                imgNameSet.add(imgName);
                InputStream inputStream = new FileInputStream("E:\\15\\15\\img_15\\" + imgName);
                // 设置header
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType("image/jpg");
                objectMetadata.setContentDisposition("inline");
                // 创建PutObject请求。
                ossClient.putObject(bucketName, "pic15/" + imgName, inputStream, objectMetadata);
            }
            culturalRelicInfoService.saveBatch(culturalRelicInfos);
            return ApiResult.success(culturalRelicInfos);

        } catch (OSSException oe) {
            log.warn("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.");
            log.warn("Error Message={}, Error Code={}, Request ID={}, Host ID={}",
                    oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
        } catch (ClientException ce) {
            log.warn("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.warn("Error Message{}", ce.getMessage());
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException detected");
        }
        return ApiResult.fail();
    }

}
