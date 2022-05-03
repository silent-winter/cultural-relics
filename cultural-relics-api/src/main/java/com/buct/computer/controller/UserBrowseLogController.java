package com.buct.computer.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.buct.computer.model.UserBrowseLog;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.IUserBrowseLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 用户浏览记录表 前端控制器
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/browse")
@Api(tags = "用户浏览历史相关接口")
public class UserBrowseLogController {

    @Autowired
    private IUserBrowseLogService userBrowseLogService;


    @PostMapping("/save/{id}")
    @ApiOperation("用户开始浏览文物时调用，保存浏览记录")
    public ApiResult<UserBrowseLog> browse(@PathVariable("id") Long id) {
        Integer userId = StpUtil.getLoginId(0);
        UserBrowseLog userBrowseLog = userBrowseLogService.findByCulturalRelicId(id);
        if (userBrowseLog == null) {
            UserBrowseLog browseLog = UserBrowseLog.builder()
                    .userId(userId)
                    .culturalRelicId(id)
                    .browseTime(new Date())
                    .build();
            userBrowseLogService.save(browseLog);
            return ApiResult.success(browseLog);
        }
        userBrowseLog.setBrowseTime(new Date());
        userBrowseLogService.updateById(userBrowseLog);
        return ApiResult.success(userBrowseLog);
    }

}
