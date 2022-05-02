package com.buct.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.mapper.CulturalRelicInfoMapper;
import com.buct.computer.model.UserInfo;
import com.buct.computer.request.QueryRequestDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.ICulturalRelicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buct.computer.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<CulturalRelicInfo> queryByCondition(QueryRequestDTO queryRequestDTO) {
        QueryWrapper<CulturalRelicInfo> queryWrapper = buildQueryWrapper(queryRequestDTO);
        Page<CulturalRelicInfo> infoPage = this.baseMapper.selectPage(
                new Page<>(queryRequestDTO.getPage(), queryRequestDTO.getSize()), queryWrapper
        );
        return infoPage.getRecords();
    }


    private QueryWrapper<CulturalRelicInfo> buildQueryWrapper(QueryRequestDTO queryRequestDTO) {
        QueryWrapper<CulturalRelicInfo> queryWrapper = new QueryWrapper<>();
        if (queryRequestDTO.getIsFuzzy()) {
            // 全字段模糊匹配
            String keyword = queryRequestDTO.getKeyword();
            if (StringUtils.isNotBlank(keyword)) {
                queryWrapper.like("name", keyword).or().like("discover_time", keyword).or()
                        .like("detail", keyword).or().like("medium", keyword).or()
                        .like("artist", keyword).or().like("dimension", keyword).or()
                        .like("custom_class", keyword).or().like("museum", keyword).or()
                        .like("location", keyword);
            }
        } else {
            // 高级搜索
            String medium = queryRequestDTO.getMedium();
            if (StringUtils.isNotBlank(medium)) {
                List<String> mediumList = Arrays.asList(StringUtils.split(medium, ","));
                queryWrapper.in("medium", mediumList);
            }
            String artist = queryRequestDTO.getArtist();
            if (StringUtils.isNotBlank(artist)) {
                List<String> artistList = Arrays.asList(StringUtils.split(artist, ","));
                queryWrapper.in("artist", artistList);
            }
            Integer status = queryRequestDTO.getStatus();
            if (Objects.nonNull(status)) {
                queryWrapper.eq("status", status);
            }
            String customClass = queryRequestDTO.getCustomClass();
            if (StringUtils.isNotBlank(customClass)) {
                List<String> customClassList = Arrays.asList(StringUtils.split(customClass, ","));
                queryWrapper.in("custom_class", customClassList);
            }
            String museum = queryRequestDTO.getMuseum();
            if (StringUtils.isNotBlank(museum)) {
                List<String> museumList = Arrays.asList(StringUtils.split(museum, ","));
                queryWrapper.in("museum", museumList);
            }
        }
        // 构建排序条件
        String sort = queryRequestDTO.getSort();
        if (StringUtils.isNotBlank(sort)) {
            String[] split = sort.split(":");
            queryWrapper.orderBy(true, StringUtils.equalsIgnoreCase("asc", split[1]), split[0]);
        }
        return queryWrapper;
    }
}
