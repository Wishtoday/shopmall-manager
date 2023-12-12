package com.shopmall.modules.dict.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class DictDetailQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    private String dictName;
}
