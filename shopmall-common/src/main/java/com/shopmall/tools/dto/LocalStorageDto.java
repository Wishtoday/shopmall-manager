package com.shopmall.tools.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class LocalStorageDto implements Serializable {
    private Long id;

    private String realName;

    private String name;

    private String suffix;

    private String path;

    private String type;

    private String size;

    private String operate;

    private Timestamp createTime;
}
