package com.buct.computer.mapper;

import com.buct.computer.model.UserBrowseLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户浏览记录表 Mapper 接口
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Mapper
public interface UserBrowseLogMapper extends BaseMapper<UserBrowseLog> {

    List<UserBrowseLog> selectLast3DaysBrowseLogByUserId(Integer userId);

}
