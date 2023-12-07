package com.shopmall.modules.system.service.impl;

import com.shopmall.modules.system.domain.UsersRoles;
import com.shopmall.modules.system.mapper.UsersRolesMapper;
import com.shopmall.modules.system.service.UsersRolesService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* @author zhuxiying
* @date 2023-11-30
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "usersRoles")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UsersRolesServiceImpl extends BaseServiceImpl <UsersRolesMapper, UsersRoles> implements UsersRolesService {

}
