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
 * @Date: 2022/05/02/9:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryRequestDTO {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页大小
     */
    private Integer size;

    /**
     * 是否使用模糊查询，若为true则进行全字段模糊匹配
     */
    private Boolean isFuzzy;

    /**
     * 模糊匹配的搜索关键字
     */
    private String keyword;

    /**
     * 排序信息
     */
    private String sort;

    /* 以下字段只在高级搜索时用到 */
    /**
     * 文物材质
     */
    private String medium;

    /**
     * 文物作者，可匹配多个，逗号分割
     */
    private String artist;

    /**
     * 文物在展情况
     */
    private Integer status;

    /**
     * 文物分类，可匹配多个，逗号分割
     */
    private String customClass;

    /**
     * 文物所属博物馆，可匹配多个，逗号分割
     */
    private String museum;

}
