package com.shopmall.modules.category.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxStoreCategoryQueryCriteria{
    @Query
    private String cateName;
}
