package com.buct.computer.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.computer.common.assembler.UserInfoAssembler;
import com.buct.computer.common.enums.UserTypeEnum;
import com.buct.computer.model.UserInfo;
import com.buct.computer.response.ApiResult;
import com.buct.computer.response.vo.UserInfoDetailVO;
import com.buct.computer.service.IUserBrowseLogService;
import com.buct.computer.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Objects;


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
    @ApiImplicitParam(name = "userId", value = "用户id")
    public ApiResult<UserInfoDetailVO> getUserInfo(@RequestParam(value = "userId", required = false) Integer userId) {
        if (Objects.isNull(userId)) {
            // userId为空，默认查询当前登录用户的信息
            userId = StpUtil.getLoginIdAsInt();
        }
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        UserInfoDetailVO userInfoDetailVO = UserInfoAssembler.MAPPER.userInfoToUserInfoDetailVO(userInfo);
        userInfoDetailVO.setBrowseIds(userBrowseLogService.getLast3DaysBrowseLog(userId));
        return ApiResult.success(userInfoDetailVO);
    }


    @PatchMapping("/info")
    @ApiOperation("更新登录态用户信息")
    public ApiResult<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        userInfo.setId(StpUtil.getLoginId(0));
        if (userInfoService.updateById(userInfo)) {
            return ApiResult.success("update user info successfully");
        }
        return ApiResult.fail("fail to update user info");
    }

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名" ,required = true),
    })
    public ApiResult<String> register(@RequestBody UserInfo userInfo) {
        if(userInfoService.getOne(new QueryWrapper<UserInfo>().eq("user_name", userInfo.getUserName())) != null) {
            return ApiResult.fail("fail to register: the username has existed");
        }
        if (StringUtils.isBlank(userInfo.getType())) {
            userInfo.setType(UserTypeEnum.ordinary.getTypeName());
        }
        if (userInfo.getStatus() == null) {
            userInfo.setStatus(1);
        }
        userInfoService.save(userInfo);
        return ApiResult.success("register successfully");
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名" ,required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "密码" ,required = true, paramType = "body"),
    })
    public ApiResult<String> login(@ApiIgnore @RequestBody UserInfo userInfo) {
        UserInfo savedAccount = userInfoService.getOne(new QueryWrapper<UserInfo>()
                .eq("user_name", userInfo.getUserName())
                .eq("password", userInfo.getPassword()));
        if (savedAccount == null) {
            return ApiResult.fail("the user hasn't registered");
        }
        StpUtil.login(savedAccount.getId());
        return ApiResult.success("login successfully");
    }

    @GetMapping("/logout")
    @ApiOperation("注销当前登录用户")
    public ApiResult<String> logout() {
        StpUtil.logout();
        return ApiResult.success("logout successfully");
    }

}
