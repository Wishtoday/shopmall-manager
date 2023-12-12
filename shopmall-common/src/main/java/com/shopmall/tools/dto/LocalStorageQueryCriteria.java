package com.shopmall.tools.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class LocalStorageQueryCriteria {

    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
