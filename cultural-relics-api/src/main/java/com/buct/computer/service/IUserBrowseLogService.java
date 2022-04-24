package com.buct.computer.service;

import com.buct.computer.model.UserBrowseLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 用户浏览记录表 服务类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
public interface IUserBrowseLogService extends IService<UserBrowseLog> {

    Set<Long> getLast3DaysBrowseLog(Integer userId);

}
