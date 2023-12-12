package com.shopmall.modules.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxStoreCategoryDto implements Serializable {

    /** 商品分类表ID */
    private Integer id;

    /** 父id */
    private Integer pid;

    /** 分类名称 */
    private String cateName;

    /** 排序 */
    private Integer sort;

    /** 图标 */
    private String pic;

    /** 是否推荐 */
    private Integer isShow;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<YxStoreCategoryDto> children;

    public String getLabel() {
        return cateName;
    }
}
