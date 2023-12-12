package com.shopmall.modules.template.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shopmall.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("yx_shipping_templates")
public class YxShippingTemplates extends BaseDomain {

    /** 模板ID */
    @TableId
    private Integer id;


    /** 模板名称 */
    private String name;


    /** 计费方式 */
    private Integer type;


    /** 地域以及费用 */
    private String regionInfo;


    /** 指定包邮开关 */
    private Integer appoint;


    /** 指定包邮内容 */
    private String appointInfo;




    /** 排序 */
    private Integer sort;


    public void copy(YxShippingTemplates source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
