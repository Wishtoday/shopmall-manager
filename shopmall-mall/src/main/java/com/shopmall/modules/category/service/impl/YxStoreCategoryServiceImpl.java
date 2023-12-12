package com.shopmall.modules.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.shopmall.category.dto.CateDto;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.enums.ShopCommonEnum;
import com.shopmall.modules.category.domain.YxStoreCategory;
import com.shopmall.modules.category.dto.YxStoreCategoryDto;
import com.shopmall.modules.category.dto.YxStoreCategoryQueryCriteria;
import com.shopmall.modules.category.mapper.StoreCategoryMapper;
import com.shopmall.modules.category.service.YxStoreCategoryService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import com.shopmall.pagehandle.utils.QueryHelpPlus;
import com.shopmall.utils.FileUtil;
import com.shopmall.utils.TreeUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @description:商品分类
 * @author: zhuxiying
 * @date: 2023/12/12 13:55
 **/

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreCategoryServiceImpl extends BaseServiceImpl <StoreCategoryMapper, YxStoreCategory> implements YxStoreCategoryService {

    private final IGenerator generator;

    /**
     * 获取分类列表树形列表
     * @return List
     */
    @Override
    public List<CateDto> getList() {
       LambdaQueryWrapper <YxStoreCategory> wrapper = new LambdaQueryWrapper <>();
        wrapper.eq(YxStoreCategory::getIsShow, ShopCommonEnum.SHOW_1.getValue())
                .orderByAsc(YxStoreCategory::getSort);
        List<CateDto> list = generator.convert(baseMapper.selectList(wrapper),CateDto.class);
        return TreeUtil.list2TreeConverter(list,0);
    }

    //===============================//

    @Override
    public Map<String, Object> queryAll(YxStoreCategoryQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <YxStoreCategoryDto> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getList());
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<YxStoreCategoryDto> queryAll(YxStoreCategoryQueryCriteria criteria){
        return generator.convert(this.baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreCategory.class, criteria)),
                YxStoreCategoryDto.class);
    }


    @Override
    public void download(List<YxStoreCategoryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreCategoryDto yxStoreCategory : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("父id", yxStoreCategory.getPid());
            map.put("分类名称", yxStoreCategory.getCateName());
            map.put("排序", yxStoreCategory.getSort());
            map.put("图标", yxStoreCategory.getPic());
            map.put("是否推荐", yxStoreCategory.getIsShow());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 构建树形
     * @param categoryDTOS 分类列表
     * @return map
     */
    @Override
    public Map<String, Object> buildTree(List<YxStoreCategoryDto> categoryDTOS) {
        Set<YxStoreCategoryDto> trees = new LinkedHashSet<>();
        Set<YxStoreCategoryDto> cates = new LinkedHashSet<>();
        List<String> deptNames = categoryDTOS.stream().map(YxStoreCategoryDto::getCateName)
                .collect(Collectors.toList());

        //YxStoreCategoryDto categoryDTO = new YxStoreCategoryDto();
        Boolean isChild;
        List<YxStoreCategory> categories = this.list();
        for (YxStoreCategoryDto deptDTO : categoryDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (YxStoreCategoryDto it : categoryDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<YxStoreCategoryDto>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                cates.add(deptDTO);
            }
            for (YxStoreCategory category : categories) {
                if (category.getId().equals(deptDTO.getPid()) && !deptNames.contains(category.getCateName())) {
                    cates.add(deptDTO);
                }
            }
        }


        if (CollectionUtils.isEmpty(trees)) {
            trees = cates;
        }


        Integer totalElements = categoryDTOS != null ? categoryDTOS.size() : 0;

        Map<String, Object> map = Maps.newHashMap();
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? categoryDTOS : trees);
        return map;
    }


    /**
     * 检测分类是否操过二级
     * @param pid 父级id
     * @return boolean
     */
    @Override
    public boolean checkCategory(int pid){
        if(pid == 0) {
            return true;
        }
        YxStoreCategory yxStoreCategory =  this.getOne(Wrappers.<YxStoreCategory>lambdaQuery()
                        .eq(YxStoreCategory::getId,pid));
        return yxStoreCategory.getPid() <= 0;
    }

    /**
     * 检测商品分类必选选择二级
     * @param id 分类id
     * @return boolean
     */
    @Override
    public boolean checkProductCategory(int id){
        YxStoreCategory yxStoreCategory =  this.getOne(Wrappers.<YxStoreCategory>lambdaQuery()
                .eq(YxStoreCategory::getId,id));
        return yxStoreCategory.getPid() != 0;
    }

}
