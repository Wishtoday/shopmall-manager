package com.shopmall.modules.product.dto;

import lombok.*;

import java.util.List;

/**
 * @ClassName FromatDetailDTO
 * @Author zhuxiying
 * @Date 2023/12/12
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromatDetailDto {
    private  String attrHidden;

    private  String detailValue;

    private List<String> detail;

    private String value;

}
