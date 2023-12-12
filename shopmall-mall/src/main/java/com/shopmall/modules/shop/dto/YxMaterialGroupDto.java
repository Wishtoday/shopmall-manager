package com.shopmall.modules.shop.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxMaterialGroupDto implements Serializable {
    /** PK */
    private String id;


    /** 创建时间 */
    private Date createTime;

    /** 创建者ID */
    private String createId;

    /** 分组名 */
    private String name;
}
