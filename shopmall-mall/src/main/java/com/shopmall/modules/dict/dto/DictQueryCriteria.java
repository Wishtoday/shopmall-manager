package com.shopmall.modules.dict.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class DictQueryCriteria{

    @Query(blurry = "name,remark")
    private String blurry;
}
