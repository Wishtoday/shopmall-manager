package com.shopmall.modules.shop.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("yx_material_group")
public class YxMaterialGroup extends BaseDomain {

    /** PK */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;


    /** 创建者ID */
    private String createId;


    /** 分组名 */
    private String name;


    public void copy(YxMaterialGroup source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
