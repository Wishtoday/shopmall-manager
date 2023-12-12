package com.shopmall.modules.product.service.impl;

import com.shopmall.modules.product.domain.YxStoreProductAttrValue;
import com.shopmall.modules.product.mapper.StoreProductAttrValueMapper;
import com.shopmall.modules.product.service.YxStoreProductAttrValueService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
* @author zhuxiying
* @date 2023-12-12
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreProductAttrValueServiceImpl extends BaseServiceImpl <StoreProductAttrValueMapper, YxStoreProductAttrValue> implements YxStoreProductAttrValueService {


}
