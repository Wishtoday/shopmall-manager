package com.shopmall.modules.system.service;

import com.shopmall.modules.system.domain.Dept;
import com.shopmall.modules.system.dto.DeptDto;
import com.shopmall.modules.system.dto.DeptQueryCriteria;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
public interface DeptService  extends BaseService <Dept> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(DeptQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<DeptDto>
    */
    List<Dept> queryAll(DeptQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <DeptDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据PID查询
     * @param pid /
     * @return /
     */
    List<Dept> findByPid(long pid);

    /**
     * 构建树形数据
     * @param deptDtos 原始数据
     * @return /
     */
    Object buildTree(List <DeptDto> deptDtos);

    /**
     * 删除部门
     * @param deptIds
     */
    void delDepts(List <Long> deptIds);

    /**
     * 获取待删除的部门
     * @param deptList /
     * @param deptDtos /
     * @return /
     */
    /*Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos);*/

    /**
     * 根据角色ID查询
     * @param id /
     * @return /
     */
    Set<Dept> findByRoleIds(Long id);
}
