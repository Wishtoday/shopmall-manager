package com.shopmall.modules.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AttrValueDTO
 * @Author zhuxiying
 * @Date 2023/12/12
 **/
@Data
public class AttrValueDto {

    @ApiModelProperty(value = "属性")
    private String attr;

    @ApiModelProperty(value = "是否选择")
    private Boolean check = false;
}
