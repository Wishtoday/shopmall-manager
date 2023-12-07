package com.shopmall.modules.system.service.impl;

import com.shopmall.modules.system.domain.RolesMenus;
import com.shopmall.modules.system.mapper.RolesMenusMapper;
import com.shopmall.modules.system.service.RolesMenusService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
* @author zhuxiying
* @date 2020-05-16
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "rolesMenus")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolesMenusServiceImpl extends BaseServiceImpl <RolesMenusMapper, RolesMenus> implements RolesMenusService {

}
