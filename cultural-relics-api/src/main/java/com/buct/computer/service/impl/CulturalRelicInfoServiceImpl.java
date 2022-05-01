package com.buct.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.mapper.CulturalRelicInfoMapper;
import com.buct.computer.model.UserInfo;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.ICulturalRelicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buct.computer.service.IUserInfoService;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文物具体信息表 服务实现类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Service
public class CulturalRelicInfoServiceImpl extends ServiceImpl<CulturalRelicInfoMapper, CulturalRelicInfo>
        implements ICulturalRelicInfoService {

    @Autowired
    private IUserInfoService userInfoService;


    @Override
    @Transactional
    public CulturalRelicInfo likeOrUnlike(CulturalRelicInfo culturalRelicInfo, boolean isLike) {
        Integer originLikeNum = culturalRelicInfo.getLikeNum();
        culturalRelicInfo.setLikeNum(isLike ? originLikeNum + 1 : originLikeNum - 1);
        this.updateById(culturalRelicInfo);
        // 更新用户信息
        UserInfo user = userInfoService.getById(StpUtil.getLoginId(0));
        Asserts.notNull(user, "当前用户未登录");
        userInfoService.updateList(user, "like", String.valueOf(culturalRelicInfo.getId()), isLike);
        return culturalRelicInfo;
    }

    @Override
    public void collectOrCancelCollect(Long id, boolean isCollect) {
        UserInfo user = userInfoService.getById(StpUtil.getLoginId(0));
        Asserts.notNull(user, "当前用户未登录");
        userInfoService.updateList(user, "collection", String.valueOf(id), isCollect);
    }
}
