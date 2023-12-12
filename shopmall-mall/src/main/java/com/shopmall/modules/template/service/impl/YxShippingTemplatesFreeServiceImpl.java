package com.shopmall.modules.template.service.impl;


import com.github.pagehelper.PageInfo;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.template.domain.YxShippingTemplatesFree;
import com.shopmall.modules.template.dto.YxShippingTemplatesFreeDto;
import com.shopmall.modules.template.dto.YxShippingTemplatesFreeQueryCriteria;
import com.shopmall.modules.template.mapper.YxShippingTemplatesFreeMapper;
import com.shopmall.modules.template.service.YxShippingTemplatesFreeService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import com.shopmall.pagehandle.utils.QueryHelpPlus;
import com.shopmall.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zhuxiying
 * @date: 2023/12/12 18:00
 **/

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxShippingTemplatesFreeServiceImpl extends BaseServiceImpl <YxShippingTemplatesFreeMapper, YxShippingTemplatesFree> implements YxShippingTemplatesFreeService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxShippingTemplatesFreeQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <YxShippingTemplatesFree> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxShippingTemplatesFreeDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxShippingTemplatesFree> queryAll(YxShippingTemplatesFreeQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxShippingTemplatesFree.class, criteria));
    }


    @Override
    public void download(List<YxShippingTemplatesFreeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxShippingTemplatesFreeDto yxShippingTemplatesFree : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("省ID", yxShippingTemplatesFree.getProvinceId());
            map.put("模板ID", yxShippingTemplatesFree.getTempId());
            map.put("城市ID", yxShippingTemplatesFree.getCityId());
            map.put("包邮件数", yxShippingTemplatesFree.getNumber());
            map.put("包邮金额", yxShippingTemplatesFree.getPrice());
            map.put("计费方式", yxShippingTemplatesFree.getType());
            map.put("分组唯一值", yxShippingTemplatesFree.getUniqid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
