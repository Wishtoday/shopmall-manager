package com.shopmall.modules.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopmall.config.thread.DataScope;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.exception.RequestException;
import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.system.domain.Dept;
import com.shopmall.modules.system.dto.DeptDto;
import com.shopmall.modules.system.dto.DeptQueryCriteria;
import com.shopmall.modules.system.service.DeptService;
import com.shopmall.utils.ValidationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description: 部门管理
 * @author: zhuxiying
 * @date: 2023/12/12 11:07
 **/

@RestController
@Api(tags = "系统管理：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

    private static final String ENTITY_NAME = "dept";
    @Autowired
    private DeptService deptService;
    @Autowired
    private DataScope dataScope;
    @Autowired
    private IGenerator generator;

//    @Log("导出部门数据")
//    @ApiOperation("导出部门数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('admin','dept:list')")
//    public void download(HttpServletResponse response, DeptQueryCriteria criteria) throws IOException {
//        deptService.download(generator.convert(deptService.queryAll(criteria), DeptDto.class), response);
//    }

    @Log("查询部门")
    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','admin','dept:list')")
    public ResponseEntity <Object> getDepts(DeptQueryCriteria criteria){
        // 数据权限
        criteria.setIds(dataScope.getDeptIds());
        List<DeptDto> deptDtos = generator.convert(deptService.queryAll(criteria),DeptDto.class);
        return new ResponseEntity <>(deptService.buildTree(deptDtos), HttpStatus.OK);
    }

    @Log("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('admin','dept:add')")
    public ResponseEntity <Object> create(@Validated @RequestBody Dept resources){
        if (resources.getId() != null) {
            throw new RequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity <>(deptService.save(resources), HttpStatus.CREATED);
    }

    @Log("修改部门")
    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('admin','dept:edit')")
    public ResponseEntity <Object> update(@Validated @RequestBody Dept resources){
        if(resources.getId().equals(resources.getPid())) {
            throw new RequestException("上级不能为自己");
        }
        Dept dept = deptService.getOne(new LambdaQueryWrapper <Dept>()
                .eq(Dept::getId,resources.getId()));
        ValidationUtil.isNull( dept.getId(),"Dept","id",resources.getId());
        resources.setId(dept.getId());
        deptService.saveOrUpdate(resources);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','dept:del')")
    public ResponseEntity <Object> delete(@RequestBody Set<Long> ids){
        List<Long> deptIds = new ArrayList<>();
        for (Long id : ids) {
            List<Dept> deptList = deptService.findByPid(id);
            Dept dept =  deptService.getOne(new LambdaQueryWrapper <Dept>().eq(Dept::getId,id));
            if(null!=dept){
                deptIds.add(dept.getId());
            }
            if(CollectionUtil.isNotEmpty(deptList)){
                for(Dept d:deptList){
                    deptIds.add(d.getId());
                }
            }
        }

        deptService.delDepts(deptIds);
//        try {
//            deptService.delDepts(deptIds);
//        }catch (Throwable e){
//            throw new BadRequestException( "所选部门中存在岗位或者角色关联，请取消关联后再试");
//        }
        return new ResponseEntity <>(HttpStatus.OK);
    }
}
