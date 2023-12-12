package com.shopmall.modules.template.service;

import com.shopmall.modules.template.domain.YxShippingTemplates;
import com.shopmall.modules.template.dto.ShippingTemplatesDto;
import com.shopmall.modules.template.dto.YxShippingTemplatesDto;
import com.shopmall.modules.template.dto.YxShippingTemplatesQueryCriteria;
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
public interface YxShippingTemplatesService extends BaseService <YxShippingTemplates> {

    /**
     * 新增与更新模板
     * @param id 模板id
     * @param shippingTemplatesDto ShippingTemplatesDto
     */
    void addAndUpdate(Integer id, ShippingTemplatesDto shippingTemplatesDto);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxShippingTemplatesQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxShippingTemplatesDto>
    */
    List<YxShippingTemplates> queryAll(YxShippingTemplatesQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <YxShippingTemplatesDto> all, HttpServletResponse response) throws IOException;
}
