package com.buct.computer.controller;

import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.request.QueryRequestDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.ICulturalRelicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    private final ConcurrentHashMap<Long, Object> lockMap = new ConcurrentHashMap<>();

    @Autowired
    private ICulturalRelicInfoService culturalRelicInfoService;


    @GetMapping("/page")
    @ApiOperation("条件查询，获取文物分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isFuzzy", value = "是否使用模糊查询", required = true),
            @ApiImplicitParam(name = "keyword", value = "模糊匹配的搜索关键字"),
            @ApiImplicitParam(name = "medium", value = "高级搜索时填，文物材质", defaultValue = "bronze,copper,silver,Porcelain,Iron"),
            @ApiImplicitParam(name = "artist", value = "高级搜索时填，文物作者，可匹配多个，逗号分割", defaultValue = "unknown,Chinese"),
            @ApiImplicitParam(name = "customClass", value = "高级搜索时填，文物自定义类别，可匹配多个，逗号分割",
                    defaultValue = "Numismatics,Container,Paintings,Sculpture"),
            @ApiImplicitParam(name = "status", value = "高级搜索时填，在展情况", defaultValue = "1"),
            @ApiImplicitParam(name = "museum", value = "高级搜索时填，文物所属博物馆，可匹配多个，逗号分割",
                    defaultValue = "Yale University Art Gallery,Saint Louis Art Museum"),
            @ApiImplicitParam(name = "sort", value = "排序条件{字段}:{asc|desc}", defaultValue = "like_num:desc"),
            @ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "size", value = "每页记录数", defaultValue = "10", required = true)
    })
    public ApiResult<List<CulturalRelicInfo>> getPage(QueryRequestDTO queryRequestDTO) {
        checkQueryParam(queryRequestDTO);
        List<CulturalRelicInfo> culturalRelicInfoList = culturalRelicInfoService.queryByCondition(queryRequestDTO);
        return ApiResult.success(culturalRelicInfoList);
    }

    @PostMapping("/like/{id}")
    @ApiOperation("文物点赞")
    public ApiResult<CulturalRelicInfo> like(@PathVariable("id") Long id) {
        lockMap.putIfAbsent(id, new Object());
        synchronized (lockMap.get(id)) {
            CulturalRelicInfo culturalRelicInfo = culturalRelicInfoService.getById(id);
            if (culturalRelicInfo == null) {
                return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
            }
            return ApiResult.success(culturalRelicInfoService.likeOrUnlike(culturalRelicInfo, true));
        }
    }

    @PostMapping("/unlike/{id}")
    @ApiOperation("取消文物点赞")
    public ApiResult<CulturalRelicInfo> unlike(@PathVariable("id") Long id) {
        lockMap.putIfAbsent(id, new Object());
        synchronized (lockMap.get(id)) {
            CulturalRelicInfo culturalRelicInfo = culturalRelicInfoService.getById(id);
            if (culturalRelicInfo == null) {
                return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
            }
            return ApiResult.success(culturalRelicInfoService.likeOrUnlike(culturalRelicInfo, false));
        }
    }

    @PostMapping("/collect/{id}")
    @ApiOperation("文物收藏")
    public ApiResult<CulturalRelicInfo> collect(@PathVariable("id") Long id) {
        CulturalRelicInfo culturalRelicInfo = culturalRelicInfoService.getById(id);
        if (culturalRelicInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        culturalRelicInfoService.collectOrCancelCollect(id, true);
        return ApiResult.success(culturalRelicInfo);
    }

    @PostMapping("/cancelCollect/{id}")
    @ApiOperation("取消文物收藏")
    public ApiResult<CulturalRelicInfo> cancelCollect(@PathVariable("id") Long id) {
        CulturalRelicInfo culturalRelicInfo = culturalRelicInfoService.getById(id);
        if (culturalRelicInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        culturalRelicInfoService.collectOrCancelCollect(id, false);
        return ApiResult.success(culturalRelicInfo);
    }


    private void checkQueryParam(QueryRequestDTO queryRequestDTO) {
        Asserts.notNull(queryRequestDTO.getPage(), "当前页不能为空");
        Asserts.notNull(queryRequestDTO.getSize(), "每页大小不能为空");
        Asserts.notNull(queryRequestDTO.getIsFuzzy(), "查询模式不能为空");
    }

}
