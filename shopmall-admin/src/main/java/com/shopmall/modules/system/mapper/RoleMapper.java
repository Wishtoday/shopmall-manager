package com.shopmall.modules.system.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.system.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@Repository
@Mapper
public interface RoleMapper extends CoreMapper <Role> {

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return
     */
    Set<Role> findByUsersId(@Param("id") Long id);

    /**
     * 解绑角色菜单
     * @param id 菜单ID
     */
//    @Delete("delete from roles_menus where menu_id = #{id}")
    void untiedMenu(@Param("id") Long id);

    /**
     * 根据用户ID查询
     *
     * @param id 用户ID
     * @return
     */
    List<Role> selectListByUserId(@Param("id") Long id);

}
