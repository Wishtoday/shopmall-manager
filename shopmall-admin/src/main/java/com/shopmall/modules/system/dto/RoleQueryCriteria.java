package com.shopmall.modules.system.dto;

import com.shopmall.pagehandle.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
* @author zhuxiying
* @date 2023-11-30
*/
@Data
public class RoleQueryCriteria {

    @Query(blurry = "name")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
