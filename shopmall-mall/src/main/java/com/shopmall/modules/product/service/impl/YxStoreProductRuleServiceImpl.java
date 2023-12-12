package com.shopmall.modules.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.product.domain.YxStoreProductRule;
import com.shopmall.modules.product.dto.YxStoreProductRuleQueryCriteria;
import com.shopmall.modules.product.mapper.YxStoreProductRuleMapper;
import com.shopmall.modules.product.service.YxStoreProductRuleService;
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
public class YxStoreProductRuleServiceImpl extends BaseServiceImpl <YxStoreProductRuleMapper, YxStoreProductRule> implements YxStoreProductRuleService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreProductRuleQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <YxStoreProductRule> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getList());
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreProductRule> queryAll(YxStoreProductRuleQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreProductRule.class, criteria));
    }


    @Override
    public void download(List<YxStoreProductRule> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreProductRule yxStoreProductRule : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("规格名称", yxStoreProductRule.getRuleName());
            map.put("规格值", yxStoreProductRule.getRuleValue());
            map.put(" createTime",  yxStoreProductRule.getCreateTime());
            map.put(" updateTime",  yxStoreProductRule.getUpdateTime());
            map.put(" isDel",  yxStoreProductRule.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
