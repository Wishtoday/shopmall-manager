package com.shopmall.modules.system.controller;

import com.shopmall.config.thread.DataScope;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.exception.RequestException;
import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.system.domain.Job;
import com.shopmall.modules.system.dto.JobDto;
import com.shopmall.modules.system.dto.JobQueryCriteria;
import com.shopmall.modules.system.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Api(tags = "系统管理：岗位管理")
@RestController
@RequestMapping("/api/job")
public class JobController {

    private static final String ENTITY_NAME = "job";
    @Autowired
    private JobService jobService;
    @Autowired
    private DataScope dataScope;
    @Autowired
    private IGenerator generator;

    @Log("导出岗位数据")
    @ApiOperation("导出岗位数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','job:list')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        jobService.download(generator.convert(jobService.queryAll(criteria), JobDto.class), response);
    }

    @Log("查询岗位")
    @ApiOperation("查询岗位")
    @GetMapping
    @PreAuthorize("@el.check('admin','job:list','user:list')")
    public ResponseEntity <Object> getJobs(JobQueryCriteria criteria, Pageable pageable){
        // 数据权限
        criteria.setDeptIds(dataScope.getDeptIds());
        return new ResponseEntity <>(jobService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增岗位")
    @ApiOperation("新增岗位")
    @PostMapping
    @PreAuthorize("@el.check('admin','job:add')")
    public ResponseEntity <Object> create(@Validated @RequestBody Job resources){
        if (resources.getId() != null) {
            throw new RequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        resources.setDeptId(resources.getDept().getId());
        return new ResponseEntity <>(jobService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改岗位")
    @ApiOperation("修改岗位")
    @PutMapping
    @PreAuthorize("@el.check('admin','job:edit')")
    public ResponseEntity <Object> update(@Validated @RequestBody Job resources){
        resources.setDeptId(resources.getDept().getId());
        jobService.saveOrUpdate(resources);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除岗位")
    @ApiOperation("删除岗位")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','job:del')")
    public ResponseEntity <Object> delete(@RequestBody Set<Long> ids){
        try {
            jobService.removeByIds(ids);
        }catch (Throwable e){
            throw new RequestException( "所选岗位存在用户关联，请取消关联后再试");
        }
        return new ResponseEntity <>(HttpStatus.OK);
    }
}
