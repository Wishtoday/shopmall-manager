package com.shopmall.modules.template.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName RegionDto
 * @Author zhuxiying
 * @Date 2023/12/12
 **/
@Getter
@Setter
public class RegionDto {

    private String name;

    private String city_id;

    List<RegionChildrenDto> children;

}
