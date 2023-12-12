package com.shopmall.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.shopmall.exception.EntityExistException;
import com.shopmall.modules.system.domain.Role;
import com.shopmall.modules.system.domain.UserAvatar;
import com.shopmall.modules.system.domain.UsersRoles;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import com.shopmall.pagehandle.utils.QueryHelpPlus;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.exception.ShopMallException;
import com.shopmall.modules.system.domain.User;
import com.shopmall.modules.system.dto.UserDto;
import com.shopmall.modules.system.dto.UserQueryCriteria;
import com.shopmall.modules.system.mapper.RoleMapper;
import com.shopmall.modules.system.mapper.SysUserMapper;
import com.shopmall.modules.system.service.*;
import com.shopmall.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@Service
//@AllArgsConstructor
//@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseServiceImpl <SysUserMapper, User> implements UserService {

    @Value("${file.avatar}")
    private String avatar;

    @Autowired
    private IGenerator generator;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private UserAvatarService userAvatarService;
    @Autowired
    private JobService jobService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UsersRolesService usersRolesService;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(UserQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <User> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), UserDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<User> queryAll(UserQueryCriteria criteria){
       List<User> userList =  baseMapper.selectList(QueryHelpPlus.getPredicate(User.class, criteria));
        for (User user : userList) {
            user.setJob(jobService.getById(user.getJobId()));
            user.setDept(deptService.getById(user.getDeptId()));
            user.setRoles(roleMapper.findByUsersId(user.getId()));
        }
       return userList;
    }


    @Override
    public void download(List<UserDto> all, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (UserDto user : all) {
//            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("邮箱", user.getEmail());
//            map.put("状态：1启用、0禁用", user.getEnabled());
//            map.put("密码", user.getPassword());
//            map.put("用户名", user.getUsername());
//            map.put("部门名称", user.getDeptId());
//            map.put("手机号码", user.getPhone());
//            map.put("创建日期", user.getCreateTime());
//            map.put("最后修改密码的日期", user.getLastPasswordResetTime());
//            map.put("昵称", user.getNickName());
//            map.put("性别", user.getSex());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param userName /
     * @return /
     */
    @Override
    public UserDto findByName(String userName) {
        User user = userMapper.findByName(userName);

        if(user == null) {
            throw new ShopMallException("当前用户不存在");
        }
        //用户所属岗位
        user.setJob(jobService.getById(user.getJobId()));
        //用户所属部门
        user.setDept(deptService.getById(user.getDeptId()));
        return generator.convert(user, UserDto.class);
    }

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    @Override
    public void updatePass(String username, String encryptPassword) {
        userMapper.updatePassword(encryptPassword, DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"),username);
    }

    /**
     * 修改头像
     *
     * @param multipartFile 文件
     */
    @Override
    public void updateAvatar(MultipartFile multipartFile) {
        User user = this.getOne(new LambdaQueryWrapper <User>()
                .eq(User::getUsername, SecurityUtils.getUsername()));
        UserAvatar userAvatar =  userAvatarService.getOne(new LambdaQueryWrapper <UserAvatar>()
                .eq(UserAvatar::getId,user.getAvatarId()));
        String oldPath = "";
        if(userAvatar != null){
            oldPath = userAvatar.getPath();
        } else {
            userAvatar = new UserAvatar();
        }
        File file = FileUtil.upload(multipartFile, avatar);
        assert file != null;
        userAvatar.setRealName(file.getName());
        userAvatar.setPath(file.getPath());
        userAvatar.setSize(FileUtil.getSize(multipartFile.getSize()));
        userAvatarService.saveOrUpdate(userAvatar);
        user.setAvatarId(userAvatar.getId());
        this.saveOrUpdate(user);
        if(StringUtils.isNotBlank(oldPath)){
            FileUtil.del(oldPath);
        }

    }

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    @Override
    public void updateEmail(String username, String email) {
        userMapper.updateEmail(email, username);
    }

    /**
     * 新增用户
     *
     * @param resources /
     * @return /
     */
    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean create(User resources) {
        User userName = this.getOne(new LambdaQueryWrapper <User>()
                .eq(User::getUsername,resources.getUsername()));
        if(userName != null){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }
        User userEmail = this.getOne(new LambdaQueryWrapper <User>()
                .eq(User::getEmail,resources.getEmail()));
        if(userEmail != null){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }
        resources.setDeptId(resources.getDept().getId());
        resources.setJobId(resources.getJob().getId());
        boolean result = this.save(resources);
        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUserId(resources.getId());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set ) {
            usersRoles.setRoleId(roleIds.getId());
        }
        if (result) {
            usersRolesService.save(usersRoles);
        }
        return result;
    }

    /**
     * 编辑用户
     *
     * @param resources /
     */
    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = this.getOne(new LambdaQueryWrapper <User>()
                .eq(User::getId,resources.getId()));
        ValidationUtil.isNull(user.getId(),"User","id",resources.getId());
        User user1 = this.getOne(new LambdaQueryWrapper <User>()
                .eq(User::getUsername,resources.getUsername()));
        User user2 = this.getOne(new LambdaQueryWrapper <User>()
                .eq(User::getEmail,resources.getEmail()));

        if(user1 !=null&&!user.getId().equals(user1.getId())){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

        if(user2!=null&&!user.getId().equals(user2.getId())){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setDeptId(resources.getDept().getId());
        user.setJobId(resources.getJob().getId());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setSex(resources.getSex());
        boolean result = this.saveOrUpdate(user);
        usersRolesService.lambdaUpdate().eq(UsersRoles ::getUserId,resources.getId()).remove();
        List<UsersRoles> usersRolesList = new ArrayList<>();
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            UsersRoles usersRoles = new UsersRoles();
            usersRoles.setUserId(resources.getId());
            usersRoles.setRoleId(roleIds.getId());
            usersRolesList.add(usersRoles);
        }
        if (result) {
            usersRolesService.saveBatch(usersRolesList);
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisUtils.del(key);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            usersRolesService.lambdaUpdate().eq(UsersRoles ::getUserId,id).remove();
        }
        this.removeByIds(ids);
    }

}
