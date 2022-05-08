package com.buct.computer.common.assembler;

import com.alibaba.fastjson.JSON;
import com.buct.computer.model.UserInfo;
import com.buct.computer.request.UserRegisterDTO;
import com.buct.computer.response.vo.UserInfoDetailVO;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/24/18:48
 */
@Mapper
public interface UserInfoAssembler {

    UserInfoAssembler MAPPER = Mappers.getMapper(UserInfoAssembler.class);


    @Mapping(target = "browseIds", ignore = true)
    @Mapping(target = "collectIds", expression = "java(convertCollection(userInfo.getCollection()))")
    @Mapping(target = "likeIds", expression = "java(convertCollection(userInfo.getLikeCulturalRelics()))")
    UserInfoDetailVO userInfoToUserInfoDetailVO(UserInfo userInfo);

    default Set<Long> convertCollection(String ids) {
        if (StringUtils.isBlank(ids)) {
            return Sets.newHashSet();
        }
        return Sets.newHashSet(JSON.parseArray(ids, Long.class));
    }


    @Mapping(target = "likeCulturalRelics", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "collection", ignore = true)
    UserInfo userRegisterDTOToUserInfo(UserRegisterDTO userRegisterDTO, Integer status);

}
