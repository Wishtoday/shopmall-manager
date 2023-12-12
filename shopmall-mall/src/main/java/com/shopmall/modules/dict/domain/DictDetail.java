package com.shopmall.modules.dict.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shopmall.domain.BaseDomain;
import lombok.Data;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Data
@TableName("dict_detail")
public class DictDetail extends BaseDomain {

    /** 字典详细 */
    @TableId
    private Long id;


    /** 字典标签 */
    private String label;


    /** 字典值 */
    private String value;


    /** 排序 */
    private String sort;


    /** 字典id */
    private Long dictId;

    @TableField(exist = false)
    private Dict dict;




    public void copy(DictDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
