package com.shopmall.modules.system.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@Repository
@Mapper
public interface SysUserMapper extends CoreMapper <User> {

    /**
     * 修改密码
     * @param username 用户名
     * @param password 密码
     * @param lastPasswordResetTime /
     */
    void updatePassword(@Param("password") String password, @Param("lastPasswordResetTime") String lastPasswordResetTime, @Param("username") String username);

    /**
     * 修改邮箱
     * @param username 用户名
     * @param email 邮箱
     */
    void updateEmail(@Param("email") String email, @Param("username") String username);

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     */
    User findByName(String userName);

}
