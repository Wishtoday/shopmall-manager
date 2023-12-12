package com.shopmall.category.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 商城商品分类
 * </p>
 *
 * @author zhuxiying
 * @since 2023-12-12
 */
@Data
public class CateDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    /**
     * 上级分类编号
     */
    private Long pid;

    /**
     * 商品分类名称
     */
    private String cateName;

    /**
     * 缩略图url
     */
    private String pic;

    private List<CateDto> children = new ArrayList<>();

}
