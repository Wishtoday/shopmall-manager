package com.shopmall.modules.system.service;

import com.shopmall.modules.system.domain.Role;
import com.shopmall.modules.system.dto.RoleDto;
import com.shopmall.modules.system.dto.RoleQueryCriteria;
import com.shopmall.modules.system.dto.RoleSmallDto;
import com.shopmall.modules.system.dto.UserDto;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author zhuxiying
* @date 2023-11-30
*/
public interface RoleService extends BaseService <Role> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(RoleQueryCriteria criteria, Pageable pageable);


    /**
     * 查询数据分页
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Object queryAlls(RoleQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<RoleDto>
    */
    List<Role> queryAll(RoleQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <RoleDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return /
     */
    List<RoleSmallDto> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     * @param roles /
     * @return /
     */
    Integer findByRoles(Set <Role> roles);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    RoleDto findById(long id);

    /**
     * 修改绑定的菜单
     * @param resources /
     * @param roleDto /
     */
    void updateMenu(Role resources, RoleDto roleDto);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    RoleDto create(Role resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Role resources);

    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    Collection<SimpleGrantedAuthority> mapToGrantedAuthorities(UserDto user);

    void delete(Set <Long> ids);
}
