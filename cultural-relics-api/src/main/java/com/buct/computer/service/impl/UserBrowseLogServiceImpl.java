package com.buct.computer.service.impl;

import com.buct.computer.model.UserBrowseLog;
import com.buct.computer.mapper.UserBrowseLogMapper;
import com.buct.computer.service.IUserBrowseLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户浏览记录表 服务实现类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Service
public class UserBrowseLogServiceImpl extends ServiceImpl<UserBrowseLogMapper, UserBrowseLog> implements IUserBrowseLogService {

    @Autowired
    private UserBrowseLogMapper userBrowseLogMapper;


    @Override
    public Set<Long> getLast3DaysBrowseLog(Integer userId) {
        List<UserBrowseLog> userInfoList = userBrowseLogMapper.selectLast3DaysBrowseLogByUserId(userId);
        return userInfoList.stream().map(UserBrowseLog::getCulturalRelicId).collect(Collectors.toSet());
    }
}
