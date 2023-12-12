package com.shopmall.modules.template.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@TableName("yx_shipping_templates_free")
public class YxShippingTemplatesFree implements Serializable {

    /** 编号 */
    @TableId
    private Integer id;


    /** 省ID */
    @NotNull
    private Integer provinceId;


    /** 模板ID */
    @NotNull
    private Integer tempId;


    /** 城市ID */
    @NotNull
    private Integer cityId;


    /** 包邮件数 */
    @NotNull
    private BigDecimal number;


    /** 包邮金额 */
    @NotNull
    private BigDecimal price;


    /** 计费方式 */
    @NotNull
    private Integer type;


    /** 分组唯一值 */
    @NotBlank
    private String uniqid;


    public void copy(YxShippingTemplatesFree source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
