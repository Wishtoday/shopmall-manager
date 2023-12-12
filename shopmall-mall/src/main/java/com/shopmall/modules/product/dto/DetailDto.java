package com.shopmall.modules.product.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DetailDTO
 * @Author zhuxiying
 * @Date 2013/12/12
 **/
@Data
public class DetailDto {
    private List<String> data;

    //private List<Map<String,List<Map<String,String>>>> res;

    private List<Map<String,Map<String,String>>> res;
}
