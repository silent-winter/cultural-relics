package com.buct.computer.controller;


import com.buct.computer.common.assembler.UserInfoAssembler;
import com.buct.computer.model.UserInfo;
import com.buct.computer.response.ApiResult;
import com.buct.computer.response.vo.UserInfoDetailVO;
import com.buct.computer.service.IUserBrowseLogService;
import com.buct.computer.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户个人信息、登录相关接口")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserBrowseLogService userBrowseLogService;


    @GetMapping("/info")
    @ApiImplicitParam(name = "userId", value = "用户id", defaultValue = "1" , required = true)
    public ApiResult<UserInfoDetailVO> getUserInfo(@RequestParam("userId") Integer userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        UserInfoDetailVO userInfoDetailVO = UserInfoAssembler.MAPPER.userInfoToUserInfoDetailVO(userInfo);
        userInfoDetailVO.setBrowseIds(userBrowseLogService.getLast3DaysBrowseLog(userId));
        return ApiResult.success(userInfoDetailVO);
    }

}
