package com.shopmall.modules.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author zhuxiying
* @date 2023-12-01
*/
@Data
public class UserAvatarDto implements Serializable {

    private Long id;

    /** 真实文件名 */
    private String realName;

    /** 路径 */
    private String path;

    /** 大小 */
    private String size;

    /** 创建时间 */
    private Timestamp createTime;
}
