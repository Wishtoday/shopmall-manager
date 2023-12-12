package com.shopmall.modules.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName 产品结果DTO
 * @Author zhuxiying
 * @Date 2023/12/12
 **/
@Getter
@Setter
@Builder
public class ProductResultDto {
    private Double minPrice;

    private Double minOtPrice;

    private Double minCost;

    private Integer stock;

    private Integer minIntegral;
}
