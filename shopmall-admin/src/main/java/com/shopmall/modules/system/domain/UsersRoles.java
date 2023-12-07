package com.shopmall.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@Data
@TableName("users_roles")
public class UsersRoles implements Serializable {

    /** 用户ID */
    private Long userId;


    /** 角色ID */
    private Long roleId;

    public void copy(UsersRoles source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
