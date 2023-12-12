package com.shopmall.modules.category.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shopmall.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @description:商品分类
 * @author: zhuxiying
 * @date: 2023/12/12 13:42

 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("yx_store_category")
public class YxStoreCategory extends BaseDomain {

    /** 商品分类表ID */
    @TableId
    private Integer id;


    /** 父id */
    private Integer pid;


    /** 分类名称 */
    @NotBlank(message = "分类名称必填")
    private String cateName;


    /** 排序 */
    private Integer sort;


    /** 图标 */
    private String pic;


    /** 是否推荐 */
    private Integer isShow;




    public void copy(YxStoreCategory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
