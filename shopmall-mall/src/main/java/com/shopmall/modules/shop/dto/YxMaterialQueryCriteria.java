package com.shopmall.modules.shop.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxMaterialQueryCriteria {

    @Query
    private String groupId;
}
