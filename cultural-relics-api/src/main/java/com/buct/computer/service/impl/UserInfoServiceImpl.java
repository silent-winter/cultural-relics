package com.buct.computer.service.impl;

import com.buct.computer.model.UserInfo;
import com.buct.computer.mapper.UserInfoMapper;
import com.buct.computer.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
