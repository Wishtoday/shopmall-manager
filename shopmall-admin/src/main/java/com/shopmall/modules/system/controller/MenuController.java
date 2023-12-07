package com.shopmall.modules.system.controller;

import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.system.dto.MenuDto;
import com.shopmall.modules.system.dto.UserDto;
import com.shopmall.modules.system.service.MenuService;
import com.shopmall.modules.system.service.RoleService;
import com.shopmall.modules.system.service.UserService;
import com.shopmall.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 菜单管理
 * @author: zhuxiying
 * @date: 2023/12/6 16:04
 **/

@Api(tags = "系统：菜单管理")
@RestController
@RequestMapping("/api/menus")
@SuppressWarnings("unchecked")
public class MenuController {

    private final MenuService menuService;

    private final UserService userService;

    private final RoleService roleService;

    private final IGenerator generator;

    private static final String ENTITY_NAME = "menu";

    public MenuController(MenuService menuService, UserService userService, RoleService roleService, IGenerator generator) {
        this.menuService = menuService;
        this.userService = userService;
        this.roleService = roleService;
        this.generator = generator;
    }

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

//    @ApiOperation("返回全部的菜单")
//    @GetMapping(value = "/tree")
//    @PreAuthorize("@el.check('menu:list','roles:list')")
//    public ResponseEntity <Object> getMenuTree(){
//        return new ResponseEntity <>(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
//    }
//
//    @Log("查询菜单")
//    @ApiOperation("查询菜单")
//    @GetMapping
//    @PreAuthorize("@el.check('menu:list')")
//    public ResponseEntity <Object> getMenus(MenuQueryCriteria criteria){
//        List<MenuDto> menuDtoList = generator.convert(menuService.queryAll(criteria),MenuDto.class);
//        return new ResponseEntity <>(menuService.buildTree(menuDtoList), HttpStatus.OK);
//    }
//
//    @ForbidSubmit
//    @Log("新增菜单")
//    @ApiOperation("新增菜单")
//    @PostMapping
//    @PreAuthorize("@el.check('menu:add')")
//    public ResponseEntity <Object> create(@Validated @RequestBody Menu resources){
//        if (resources.getId() != null) {
//            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
//        }
//
//        return new ResponseEntity <>(menuService.create(resources), HttpStatus.CREATED);
//    }
//
//    @ForbidSubmit
//    @Log("修改菜单")
//    @ApiOperation("修改菜单")
//    @PutMapping
//    @PreAuthorize("@el.check('menu:edit')")
//    public ResponseEntity <Object> update(@Validated @RequestBody Menu resources){
//        menuService.update(resources);
//        return new ResponseEntity <>(HttpStatus.NO_CONTENT);
//    }
//
//    @ForbidSubmit
//    @Log("删除菜单")
//    @ApiOperation("删除菜单")
//    @DeleteMapping
//    @PreAuthorize("@el.check('menu:del')")
//    public ResponseEntity <Object> delete(@RequestBody Set<Long> ids){
//        Set<Menu> menuSet = new HashSet<>();
//        for (Long id : ids) {
//            List<Menu> menuList = menuService.findByPid(id);
//            menuSet.add(menuService.getOne(new LambdaQueryWrapper <Menu>().eq(Menu::getId,id)));
//            menuSet = menuService.getDeleteMenus(menuList, menuSet);
//        }
//        menuService.delete(menuSet);
//        return new ResponseEntity <>(HttpStatus.OK);
//    }
}
