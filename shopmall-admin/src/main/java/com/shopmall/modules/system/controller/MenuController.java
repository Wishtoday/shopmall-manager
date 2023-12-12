package com.shopmall.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.exception.RequestException;
import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.system.domain.Menu;
import com.shopmall.modules.system.dto.MenuDto;
import com.shopmall.modules.system.dto.MenuQueryCriteria;
import com.shopmall.modules.system.dto.UserDto;
import com.shopmall.modules.system.service.MenuService;
import com.shopmall.modules.system.service.RoleService;
import com.shopmall.modules.system.service.UserService;
import com.shopmall.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 菜单管理
 * @author: zhuxiying
 * @date: 2023/12/6 16:04
 **/

@Api(tags = "系统管理：菜单管理")
@RestController
@RequestMapping("/api/menus")
@SuppressWarnings("unchecked")
public class MenuController {

    private static final String ENTITY_NAME = "menu";
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IGenerator generator;


//    @ForbidSubmit
//    @Log("导出菜单数据")
//    @ApiOperation("导出菜单数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('menu:list')")
//    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws IOException {
//        menuService.download(generator.convert(menuService.queryAll(criteria),MenuDto.class), response);
//    }

    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public ResponseEntity <Object> buildMenus(){
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        List<MenuDto> menuDtoList = menuService.findByRoles(roleService.findByUsersId(user.getId()));
        List<MenuDto> menuDtos = (List<MenuDto>) menuService.buildTree(menuDtoList).get("content");
        return new ResponseEntity <>(menuService.buildMenus(menuDtos), HttpStatus.OK);
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/tree")
    @PreAuthorize("@el.check('menu:list','roles:list')")
    public ResponseEntity <Object> getMenuTree(){
        return new ResponseEntity <>(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping
    @PreAuthorize("@el.check('menu:list')")
    public ResponseEntity <Object> getMenus(MenuQueryCriteria criteria){
        List<MenuDto> menuDtoList = generator.convert(menuService.queryAll(criteria),MenuDto.class);
        return new ResponseEntity <>(menuService.buildTree(menuDtoList), HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@el.check('menu:add')")
    public ResponseEntity <Object> create(@Validated @RequestBody Menu resources){
        if (resources.getId() != null) {
            throw new RequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }

        return new ResponseEntity <>(menuService.create(resources), HttpStatus.CREATED);
    }

    @ForbidSubmit
    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping
    @PreAuthorize("@el.check('menu:edit')")
    public ResponseEntity <Object> update(@Validated @RequestBody Menu resources){
        menuService.update(resources);
        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping
    @PreAuthorize("@el.check('menu:del')")
    public ResponseEntity <Object> delete(@RequestBody Set<Long> ids){
        Set <Menu> menuSet = new HashSet <>();
        for (Long id : ids) {
            List<Menu> menuList = menuService.findByPid(id);
            menuSet.add(menuService.getOne(new LambdaQueryWrapper <Menu>().eq(Menu::getId,id)));
            menuSet = menuService.getDeleteMenus(menuList, menuSet);
        }
        menuService.delete(menuSet);
        return new ResponseEntity <>(HttpStatus.OK);
    }
}
