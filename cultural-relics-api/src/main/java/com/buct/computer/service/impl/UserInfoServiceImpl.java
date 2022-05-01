package com.buct.computer.service.impl;

import com.alibaba.fastjson.JSON;
import com.buct.computer.model.UserInfo;
import com.buct.computer.mapper.UserInfoMapper;
import com.buct.computer.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    @Override
    public void updateList(UserInfo userInfo, String type, String id, boolean isAdd) {
        if (StringUtils.equals("like", type)) {
            String like = userInfo.getLikeCulturalRelics();
            Set<String> likeIds = parseSet(like);
            addOrRemove(likeIds, id, isAdd);
            userInfo.setLikeCulturalRelics(JSON.toJSONString(likeIds));
        } else if (StringUtils.equals("collection", type)) {
            String collection = userInfo.getCollection();
            Set<String> collectIds = parseSet(collection);
            addOrRemove(collectIds, id, isAdd);
            userInfo.setCollection(JSON.toJSONString(collectIds));
        }
        this.updateById(userInfo);
    }

    private Set<String> parseSet(String str) {
        return StringUtils.isBlank(str) ? Sets.newHashSet() : Sets.newHashSet(JSON.parseArray(str, String.class));
    }

    private void addOrRemove(Set<String> set, String id, boolean isAdd) {
        if (isAdd) {
            set.add(id);
        } else {
            set.remove(id);
        }
    }

}
