package com.shopmall.modules.shop.service.impl;

import com.github.pagehelper.PageInfo;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.shop.domain.YxMaterialGroup;
import com.shopmall.modules.shop.dto.YxMaterialGroupDto;
import com.shopmall.modules.shop.dto.YxMaterialGroupQueryCriteria;
import com.shopmall.modules.shop.mapper.MaterialGroupMapper;
import com.shopmall.modules.shop.service.YxMaterialGroupService;
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
* @author zhuxiying
* @date 2023-12-12
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxMaterialGroupServiceImpl extends BaseServiceImpl <MaterialGroupMapper, YxMaterialGroup> implements YxMaterialGroupService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(YxMaterialGroupQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <YxMaterialGroup> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxMaterialGroupDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<YxMaterialGroup> queryAll(YxMaterialGroupQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxMaterialGroup.class, criteria));
    }


    @Override
    public void download(List<YxMaterialGroupDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxMaterialGroupDto yxMaterialGroup : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("创建时间", yxMaterialGroup.getCreateTime());
            map.put("创建者ID", yxMaterialGroup.getCreateId());
            map.put("分组名", yxMaterialGroup.getName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
