package com.shopmall.modules.template.service.impl;

import com.github.pagehelper.PageInfo;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.template.domain.YxShippingTemplatesRegion;
import com.shopmall.modules.template.dto.YxShippingTemplatesRegionDto;
import com.shopmall.modules.template.dto.YxShippingTemplatesRegionQueryCriteria;
import com.shopmall.modules.template.mapper.YxShippingTemplatesRegionMapper;
import com.shopmall.modules.template.service.YxShippingTemplatesRegionService;
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

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @description:
 * @author: zhuxiying
 * @date: 2023/12/12 18:00
 **/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxShippingTemplatesRegion")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxShippingTemplatesRegionServiceImpl extends BaseServiceImpl <YxShippingTemplatesRegionMapper, YxShippingTemplatesRegion> implements YxShippingTemplatesRegionService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxShippingTemplatesRegionQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <YxShippingTemplatesRegion> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxShippingTemplatesRegionDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxShippingTemplatesRegion> queryAll(YxShippingTemplatesRegionQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxShippingTemplatesRegion.class, criteria));
    }


    @Override
    public void download(List<YxShippingTemplatesRegionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxShippingTemplatesRegionDto yxShippingTemplatesRegion : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("省ID", yxShippingTemplatesRegion.getProvinceId());
            map.put("模板ID", yxShippingTemplatesRegion.getTempId());
            map.put("城市ID", yxShippingTemplatesRegion.getCityId());
            map.put("首件", yxShippingTemplatesRegion.getFirst());
            map.put("首件运费", yxShippingTemplatesRegion.getFirstPrice());
            map.put("续件", yxShippingTemplatesRegion.getContinues());
            map.put("续件运费", yxShippingTemplatesRegion.getContinuePrice());
            map.put("计费方式", yxShippingTemplatesRegion.getType());
            map.put("分组唯一值", yxShippingTemplatesRegion.getUniqid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
