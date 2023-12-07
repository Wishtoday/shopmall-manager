package com.shopmall.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@Data
public class RoleSmallDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
