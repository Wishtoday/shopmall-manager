package com.shopmall.modules.dict.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
public class DictDto implements Serializable {

    /** 字典ID */
    private Long id;

    /** 字典名称 */
    private String name;

    private List<DictDetailDto> dictDetails;

    /** 描述 */
    private String remark;

    /** 创建日期 */
    private Timestamp createTime;
}
