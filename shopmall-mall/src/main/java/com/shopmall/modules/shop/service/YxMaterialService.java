package com.shopmall.modules.shop.service;

import com.shopmall.modules.shop.domain.YxMaterial;
import com.shopmall.modules.shop.dto.YxMaterialQueryCriteria;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
* @author zhuxiying
* @date 2023-12-12
*/
public interface YxMaterialService extends BaseService <YxMaterial> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxMaterialQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxMaterialDto>
    */
    List<YxMaterial> queryAll(YxMaterialQueryCriteria criteria);


}
