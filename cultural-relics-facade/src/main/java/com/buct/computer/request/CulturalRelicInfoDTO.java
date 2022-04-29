package com.buct.computer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/17/11:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CulturalRelicInfoDTO {

    private Long id;

    private String name;

    private String time;

    private String medium;

    private String classification;

    private String dimension;

    private String artist;

    private String location;

    private String details;

    @JsonProperty("detail_url")
    private String detailUrl;

    @JsonProperty("photo_url")
    private String photoUrl;

    /**
     * 图片名字，用于映射本地
     */
    @JsonProperty("photo_name")
    private String imgName;

}
