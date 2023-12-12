package com.shopmall.config;

import com.shopmall.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 权限用户校验
 * @author: zhuxiying
 * @date: 2023/12/12 10:17

 **/
@Service(value = "el")
public class ElPermissionConfig {

    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
