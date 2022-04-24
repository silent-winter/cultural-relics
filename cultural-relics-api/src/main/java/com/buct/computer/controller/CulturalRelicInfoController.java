package com.buct.computer.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.ICulturalRelicInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文物具体信息表 前端控制器
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/basic")
@Slf4j
@Api(tags = "文物信息相关接口")
public class CulturalRelicInfoController {

    @Autowired
    private ICulturalRelicInfoService culturalRelicInfoService;


    @GetMapping("/test")
    public ApiResult<List<CulturalRelicInfo>> findAll() {
        Page<CulturalRelicInfo> culturalRelicInfoPage =
                culturalRelicInfoService.getBaseMapper().selectPage(new Page<>(1, 10), null);
        return ApiResult.success(culturalRelicInfoPage.getRecords());
    }

}
