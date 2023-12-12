package com.shopmall.modules.template.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class YxShippingTemplatesDto implements Serializable {

    /** 模板ID */
    private Integer id;

    /** 模板名称 */
    private String name;

    /** 计费方式 */
    private Integer type;

    /** 地域以及费用 */
    private String regionInfo;

    /** 指定包邮开关 */
    private Integer appoint;

    /** 指定包邮内容 */
    private String appointInfo;

    /** 添加时间 */
    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer isDel;

    /** 排序 */
    private Integer sort;
}
