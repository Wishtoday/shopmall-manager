package com.shopmall.modules.product.service;

import com.shopmall.modules.product.domain.YxStoreProductAttrResult;
import com.shopmall.pagehandle.common.service.BaseService;

import java.util.Map;


/**
* @author zhuxiying
* @date 2023-12-12
*/
public interface YxStoreProductAttrResultService  extends BaseService <YxStoreProductAttrResult> {

    /**
     * 新增商品属性详情
     * @param map map
     * @param productId 商品id
     */
    void insertYxStoreProductAttrResult(Map <String, Object> map, Long productId);
}
