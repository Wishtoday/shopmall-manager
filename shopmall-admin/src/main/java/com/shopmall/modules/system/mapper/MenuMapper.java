package com.shopmall.modules.system.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.system.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
* @author zhuxiying
* @date 2023-12-07
*/
@Repository
@Mapper
public interface MenuMapper extends CoreMapper <Menu> {


    /**
     * 根据菜单的 PID 查询
     * @param pid /
     * @return /
     */
    List<Menu> findByPid(@Param("pid") long pid);

    /**
     * 根据角色ID 查询
     * @param roleId /
     * @return /
     */
    Set<Menu> findMenuByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据角色ID列表 查询
     * @param roleIds /
     * @return /
     */
    List<Menu> selectListByRoles(@Param("roleIds") List <Long> roleIds);
}
