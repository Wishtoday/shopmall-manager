package com.shopmall.modules.system.service.impl;

import com.shopmall.modules.system.domain.RolesDepts;
import com.shopmall.modules.system.mapper.RolesDeptsMapper;
import com.shopmall.modules.system.service.RolesDeptsService;
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
//@CacheConfig(cacheNames = "rolesDepts")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolesDeptsServiceImpl extends BaseServiceImpl <RolesDeptsMapper, RolesDepts> implements RolesDeptsService {

}
