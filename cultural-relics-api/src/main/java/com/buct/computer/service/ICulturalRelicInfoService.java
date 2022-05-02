package com.buct.computer.service;

import com.buct.computer.model.CulturalRelicInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buct.computer.request.QueryRequestDTO;

import java.util.List;

/**
 * <p>
 * 文物具体信息表 服务类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
public interface ICulturalRelicInfoService extends IService<CulturalRelicInfo> {

    CulturalRelicInfo likeOrUnlike(CulturalRelicInfo culturalRelicInfo, boolean isLike);

    void collectOrCancelCollect(Long id, boolean isCollect);

    List<CulturalRelicInfo> queryByCondition(QueryRequestDTO queryRequestDTO);

}
