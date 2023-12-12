package com.shopmall.modules.shop.controller;

import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.shop.domain.YxMaterialGroup;
import com.shopmall.modules.shop.dto.YxMaterialGroupQueryCriteria;
import com.shopmall.modules.shop.service.YxMaterialGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: zhuxiying
 * @date: 2023/12/12 18:32
 **/

@Api(tags = "商城管理:素材分组管理")
@RestController
@RequestMapping("/api/materialgroup")
public class MaterialGroupController {

    @Autowired
    private YxMaterialGroupService yxMaterialGroupService;


    @GetMapping(value = "/page")
    @Log("查询素材分组")
    @ApiOperation("查询素材分组")
    public ResponseEntity <Object> getYxMaterialGroups(YxMaterialGroupQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity <>(yxMaterialGroupService.queryAll(criteria,pageable), HttpStatus.OK);
    }


    @GetMapping(value = "/list")
    @Log("查询所有素材分组")
    @ApiOperation("查询所有素材分组")
    public ResponseEntity <Object> getYxMaterialGroupsList(YxMaterialGroupQueryCriteria criteria){
        return new ResponseEntity <>(yxMaterialGroupService.queryAll(criteria), HttpStatus.OK);
    }


    @PostMapping
    @Log("新增素材分组")
    @ApiOperation("新增素材分组")
    public ResponseEntity <Object> create(@Validated @RequestBody YxMaterialGroup resources){
        return new ResponseEntity <>(yxMaterialGroupService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @PutMapping
    @Log("修改素材分组")
    @ApiOperation("修改素材分组")
    public ResponseEntity <Object> update(@Validated @RequestBody YxMaterialGroup resources){
        yxMaterialGroupService.saveOrUpdate(resources);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除素材分组")
    @ApiOperation("删除素材分组")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity <Object> deleteAll(@PathVariable String id) {
        yxMaterialGroupService.removeById(id);
        return new ResponseEntity <>(HttpStatus.OK);
    }
}
