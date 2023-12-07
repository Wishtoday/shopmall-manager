package com.shopmall.modules.system.domain.vo;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 * @author zhuxiying
 * @date 2023年12月06日13:59:49
 */
@Data
public class UserPassVo {

    private String oldPass;

    private String newPass;
}
