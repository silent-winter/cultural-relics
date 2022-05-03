package com.buct.computer.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/05/03/11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "点赞，收藏统一请求参数")
public class LikeRequestDTO {

    @ApiModelProperty("操作的目标id")
    private Long targetId;

    @ApiModelProperty("若为true，表示点赞或者收藏，否则表示取消")
    private Boolean isAdd;

}
