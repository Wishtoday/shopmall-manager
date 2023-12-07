package com.shopmall.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shopmall.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dept")
public class Dept extends BaseDomain {

    /** ID */
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;


    /** 名称 */
    @NotBlank(message = "部门名称不能为空")
    private String name;


    /** 上级部门 */
    @NotNull(message = "上级部门不能为空")
    private Long pid;


    /** 状态 */
    private Boolean enabled;




    public void copy(Dept source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
