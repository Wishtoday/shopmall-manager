package com.shopmall.modules.template.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName AppointInfoDto
 * @Author zhuxiying
 * @Date 2023/12/12
 **/
@Getter
@Setter
public class AppointInfoDto {

    /** 包邮件数 */
    private String a_num;

    /** 包邮费用 */
    private String a_price;

    /** 包邮地区 */
    private List<RegionDto> place;

    private String placeName;

}
