package com.shopmall.modules.shop.service;

import com.shopmall.modules.shop.domain.YxMaterialGroup;
import com.shopmall.modules.shop.dto.YxMaterialGroupDto;
import com.shopmall.modules.shop.dto.YxMaterialGroupQueryCriteria;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author zhuxiying
* @date 2023-12-12
*/
public interface YxMaterialGroupService extends BaseService <YxMaterialGroup> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxMaterialGroupQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxMaterialGroupDto>
    */
    List<YxMaterialGroup> queryAll(YxMaterialGroupQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <YxMaterialGroupDto> all, HttpServletResponse response) throws IOException;
}
