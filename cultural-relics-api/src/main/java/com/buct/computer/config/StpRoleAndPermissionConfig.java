package com.buct.computer.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.buct.computer.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuechenlong
 * @date 2022/4/25
 * @apiNote
 */
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
        //if(memberMapper.selectById(StpUtil.getLoginId(0)).getLevel() >= 10) {
        if ("admin".equals(userInfoService.getById(StpUtil.getLoginId(0)).getType())) {
            list.add("admin");
        } else {
            list.add("user");
        }
        return list;
    }
}
