package com.buct.computer.request;

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
public class CulturalRelicInfoVO {

    private Long id;

    private String name;

    private String time;

    private String medium;

    private String classification;

    private String dimension;

    private String artist;

    private String location;

    private String details;

    private String detailUrl;

    private String photoUrl;

}
