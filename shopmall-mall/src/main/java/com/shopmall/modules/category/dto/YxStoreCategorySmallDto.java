package com.shopmall.modules.category.dto;

import lombok.Data;

import java.io.Serializable;


/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxStoreCategorySmallDto implements Serializable {

    // 商品分类表ID
    private Integer id;


    // 分类名称
    private String cateName;



}
