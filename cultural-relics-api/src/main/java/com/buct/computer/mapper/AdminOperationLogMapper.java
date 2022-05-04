package com.buct.computer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buct.computer.model.AdminOperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuechenlong
 * @date 2022/5/2
 * @apiNote 管理员操作记录 Mapper 接口
 */
@Mapper
public interface AdminOperationLogMapper extends BaseMapper<AdminOperationLog> {
}
