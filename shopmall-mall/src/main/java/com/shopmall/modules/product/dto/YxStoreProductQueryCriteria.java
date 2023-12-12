package com.shopmall.modules.product.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxStoreProductQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String storeName;

    // 精确
    @Query
    private Integer isDel;

    @Query
    private Integer isShow;

    @Query
    private Integer cateId;

    @Query
    private Integer isIntegral;
}
