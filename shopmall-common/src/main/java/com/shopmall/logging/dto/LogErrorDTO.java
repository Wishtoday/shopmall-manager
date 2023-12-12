/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co

 */
package com.shopmall.logging.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author zhuxiying
* @date 2023-12-10
*/
@Data
public class LogErrorDTO implements Serializable {

    private Long id;

    private String username;

    private String description;

    private String method;

    private String params;

    private String browser;

    private String requestIp;

    private String address;

    private Timestamp createTime;
}
