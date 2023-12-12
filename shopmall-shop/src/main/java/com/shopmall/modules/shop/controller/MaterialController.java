package com.shopmall.modules.shop.controller;

import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.shop.domain.YxMaterial;
import com.shopmall.modules.shop.dto.YxMaterialQueryCriteria;
import com.shopmall.modules.shop.service.YxMaterialService;
import com.shopmall.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Api(tags = "商品管理:素材管理")
@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private YxMaterialService yxMaterialService;

    @GetMapping(value = "/page")
    @Log("查询素材管理")
    @ApiOperation("查询素材管理")
    public ResponseEntity <Object> getYxMaterials(YxMaterialQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity <>(yxMaterialService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增素材管理")
    @ApiOperation("新增素材管理")
    public ResponseEntity <Object> create(@Validated @RequestBody YxMaterial resources){
        resources.setCreateId(SecurityUtils.getUsername());
        return new ResponseEntity <>(yxMaterialService.save(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改素材管理")
    @ApiOperation("修改素材管理")
    public ResponseEntity <Object> update(@Validated @RequestBody YxMaterial resources){
        yxMaterialService.saveOrUpdate(resources);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除素材管理")
    @ApiOperation("删除素材管理")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity <Object> deleteAll(@PathVariable String id) {
        yxMaterialService.removeById(id);
        return new ResponseEntity <>(HttpStatus.OK);
    }


}
