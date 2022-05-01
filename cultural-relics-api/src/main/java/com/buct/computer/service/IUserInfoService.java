package com.buct.computer.service;

import com.buct.computer.model.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
public interface IUserInfoService extends IService<UserInfo> {

    void updateList(UserInfo userInfo, String type, String id, boolean isAdd);

}
