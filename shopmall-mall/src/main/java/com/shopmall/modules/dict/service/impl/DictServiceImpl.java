package com.shopmall.modules.dict.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageInfo;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.dict.domain.Dict;
import com.shopmall.modules.dict.dto.DictDetailDto;
import com.shopmall.modules.dict.dto.DictDto;
import com.shopmall.modules.dict.dto.DictQueryCriteria;
import com.shopmall.modules.dict.mapper.DictMapper;
import com.shopmall.modules.dict.service.DictService;
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
* @author zhuxiying
* @date 2023-12-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "dict")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends BaseServiceImpl <DictMapper, Dict> implements DictService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(DictQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <Dict> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), DictDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<Dict> queryAll(DictQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Dict.class, criteria));
    }


    @Override
    public void download(List<DictDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDto dict : all) {
            if(CollectionUtil.isNotEmpty(dict.getDictDetails())){
                for (DictDetailDto dictDetail : dict.getDictDetails()) {
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("字典名称", dict.getName());
                    map.put("字典描述", dict.getRemark());
                    map.put("字典标签", dictDetail.getLabel());
                    map.put("字典值", dictDetail.getValue());
                    map.put("创建日期", dictDetail.getCreateTime());
                    list.add(map);
                }
            } else {
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("字典名称", dict.getName());
                map.put("字典描述", dict.getRemark());
                map.put("字典标签", null);
                map.put("字典值", null);
                map.put("创建日期", dict.getCreateTime());
                list.add(map);
            }
        }
        FileUtil.downloadExcel(list, response);
    }
}
