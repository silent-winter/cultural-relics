package com.buct.computer.mapper;

import com.buct.computer.model.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
