package com.buct.computer.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.buct.computer.common.enums.UserTypeEnum;
import com.buct.computer.model.UserInfo;
import com.buct.computer.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuechenlong
 * @date 2022/4/25
 * @apiNote
 */
@Component
public class StpRoleAndPermissionConfig implements StpInterface {

    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }


    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        UserInfo userInfo = userInfoService.getById(StpUtil.getLoginId(0));
        if (UserTypeEnum.admin.getTypeName().equals(userInfo.getType())) {
            list.add(UserTypeEnum.admin.getTypeName());
        } else {
            list.add(UserTypeEnum.ordinary.getTypeName());
        }
        return list;
    }
}
