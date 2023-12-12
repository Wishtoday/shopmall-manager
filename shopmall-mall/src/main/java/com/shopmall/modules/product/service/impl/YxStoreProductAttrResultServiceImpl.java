package com.shopmall.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shopmall.modules.product.domain.YxStoreProductAttrResult;
import com.shopmall.modules.product.mapper.StoreProductAttrResultMapper;
import com.shopmall.modules.product.service.YxStoreProductAttrResultService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;


/**
* @author zhuxiying
* @date 2023-12-12
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreProductAttrResultServiceImpl extends BaseServiceImpl <StoreProductAttrResultMapper, YxStoreProductAttrResult> implements YxStoreProductAttrResultService {

    /**
     * 新增商品属性详情
     * @param map map
     * @param productId 商品id
     */
    @Override
    public void insertYxStoreProductAttrResult(Map<String, Object> map, Long productId)
    {
        YxStoreProductAttrResult yxStoreProductAttrResult = new YxStoreProductAttrResult();
        yxStoreProductAttrResult.setProductId(productId);
        yxStoreProductAttrResult.setResult(JSON.toJSONString(map));
        yxStoreProductAttrResult.setChangeTime(new Date());

        long count = this.count(Wrappers.<YxStoreProductAttrResult>lambdaQuery()
                .eq(YxStoreProductAttrResult::getProductId,productId));
        if(count > 0) {
            this.remove(Wrappers.<YxStoreProductAttrResult>lambdaQuery()
                    .eq(YxStoreProductAttrResult::getProductId,productId));
        }

        this.save(yxStoreProductAttrResult);
    }


}
