package com.shopmall.modules.template.service;

import com.shopmall.modules.template.domain.YxShippingTemplatesFree;
import com.shopmall.modules.template.dto.YxShippingTemplatesFreeDto;
import com.shopmall.modules.template.dto.YxShippingTemplatesFreeQueryCriteria;
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
public interface YxShippingTemplatesFreeService extends BaseService <YxShippingTemplatesFree> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxShippingTemplatesFreeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxShippingTemplatesFreeDto>
    */
    List<YxShippingTemplatesFree> queryAll(YxShippingTemplatesFreeQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <YxShippingTemplatesFreeDto> all, HttpServletResponse response) throws IOException;
}
