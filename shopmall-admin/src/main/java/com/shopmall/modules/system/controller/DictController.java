package com.shopmall.modules.system.controller;

import com.shopmall.dozer.service.IGenerator;
import com.shopmall.exception.RequestException;
import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.dict.domain.Dict;
import com.shopmall.modules.dict.service.DictService;
import com.shopmall.modules.dict.dto.DictQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description:字典管理
 * @author: zhuxiying
 * @date: 2023/12/12 11:09
 **/
@Api(tags = "系统管理：字典管理")
@RestController
@RequestMapping("/api/dict")
public class DictController {

    private final DictService dictService;
    private final IGenerator generator;

    private static final String ENTITY_NAME = "dict";

    public DictController(DictService dictService, IGenerator generator) {
        this.dictService = dictService;
        this.generator = generator;
    }

//    @Log("导出字典数据")
//    @ApiOperation("导出字典数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('admin','dict:list')")
//    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
//        dictService.download(generator.convert(dictService.queryAll(criteria), DictDto.class), response);
//    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('admin','dict:list')")
    public ResponseEntity <Object> all(){
        return new ResponseEntity <>(dictService.queryAll(new DictQueryCriteria()), HttpStatus.OK);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@el.check('admin','dict:list')")
    public ResponseEntity <Object> getDicts(DictQueryCriteria resources, Pageable pageable){
        return new ResponseEntity <>(dictService.queryAll(resources,pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@el.check('admin','dict:add')")
    public ResponseEntity <Object> create(@Validated @RequestBody Dict resources){
        if (resources.getId() != null) {
            throw new RequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity <>(dictService.save(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改字典")
    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('admin','dict:edit')")
    public ResponseEntity <Object> update(@Validated @RequestBody Dict resources){
        dictService.saveOrUpdate(resources);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('admin','dict:del')")
    public ResponseEntity <Object> delete(@PathVariable Long id){
        dictService.removeById(id);
        return new ResponseEntity <>(HttpStatus.OK);
    }
}
