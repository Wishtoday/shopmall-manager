package com.shopmall.modules.product.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shopmall.constant.ShopConstants;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.enums.ShopCommonEnum;
import com.shopmall.enums.SpecTypeEnum;
import com.shopmall.logging.aop.Log;
import com.shopmall.modules.aop.ForbidSubmit;
import com.shopmall.modules.category.domain.YxStoreCategory;
import com.shopmall.modules.category.service.YxStoreCategoryService;
import com.shopmall.modules.product.domain.YxStoreProduct;
import com.shopmall.modules.product.domain.YxStoreProductAttrResult;
import com.shopmall.modules.product.domain.YxStoreProductAttrValue;
import com.shopmall.modules.product.dto.ProductDto;
import com.shopmall.modules.product.dto.ProductFormatDto;
import com.shopmall.modules.product.dto.StoreProductDto;
import com.shopmall.modules.product.dto.YxStoreProductQueryCriteria;
import com.shopmall.modules.product.service.YxStoreProductAttrResultService;
import com.shopmall.modules.product.service.YxStoreProductAttrValueService;
import com.shopmall.modules.product.service.YxStoreProductRuleService;
import com.shopmall.modules.product.service.YxStoreProductService;
import com.shopmall.modules.template.domain.YxShippingTemplates;
import com.shopmall.modules.template.service.YxShippingTemplatesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:管理商品
 * @author: zhuxiying
 * @date: 2023/12/12 15:07
 **/

@Api(tags = "商品管理:管理商品")
@RestController
@RequestMapping("api")
public class StoreProductController {

    @Autowired
    private YxStoreProductService yxStoreProductService;
    @Autowired
    private YxStoreCategoryService yxStoreCategoryService;
    @Autowired
    private YxShippingTemplatesService yxShippingTemplatesService;
    @Autowired
    private YxStoreProductRuleService yxStoreProductRuleService;
    @Autowired
    private YxStoreProductAttrResultService yxStoreProductAttrResultService;
    @Autowired
    private YxStoreProductAttrValueService storeProductAttrValueService;
    @Autowired
    private IGenerator generator;
//    public StoreProductController(YxStoreProductService yxStoreProductService,
//                                  YxStoreCategoryService yxStoreCategoryService,
//                                  YxShippingTemplatesService yxShippingTemplatesService,
//                                  YxStoreProductRuleService yxStoreProductRuleService,
//                                  YxStoreProductAttrResultService yxStoreProductAttrResultService, YxStoreProductAttrValueService storeProductAttrValueService, IGenerator generator) {
//        this.yxStoreProductService = yxStoreProductService;
//        this.yxStoreCategoryService = yxStoreCategoryService;
//        this.yxShippingTemplatesService = yxShippingTemplatesService;
//        this.yxStoreProductRuleService = yxStoreProductRuleService;
//        this.yxStoreProductAttrResultService = yxStoreProductAttrResultService;
//        this.storeProductAttrValueService = storeProductAttrValueService;
//        this.generator = generator;
//    }

    @Log("查询商品")
    @ApiOperation(value = "查询商品")
    @GetMapping(value = "/yxStoreProduct")
    @PreAuthorize("hasAnyRole('admin','YXSTOREPRODUCT_ALL','YXSTOREPRODUCT_SELECT')")
    public ResponseEntity getYxStoreProducts(YxStoreProductQueryCriteria criteria, Pageable pageable){
        //商品分类
        List<YxStoreCategory> storeCategories = yxStoreCategoryService.lambdaQuery()
                .eq(YxStoreCategory::getIsShow, ShopCommonEnum.SHOW_1.getValue())
                .orderByAsc(YxStoreCategory::getPid)
                .list();
        List<Map<String,Object>> cateList = new ArrayList<>();
        Map<String, Object> queryAll = yxStoreProductService.queryAll(criteria, pageable);
        queryAll.put("cateList", this.makeCate(storeCategories,cateList,0,1));
        return new ResponseEntity <>(queryAll, HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("新增/修改商品")
    @ApiOperation(value = "新增/修改商品")
    @CacheEvict(cacheNames = ShopConstants.SHOP_REDIS_INDEX_KEY,allEntries = true)
    @PostMapping(value = "/yxStoreProduct/addOrSave")
    @PreAuthorize("hasAnyRole('admin','YXSTOREPRODUCT_ALL','YXSTOREPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody StoreProductDto storeProductDto){
        yxStoreProductService.insertAndEditYxStoreProduct(storeProductDto);
        return new ResponseEntity <>(HttpStatus.CREATED);
    }


    @ForbidSubmit
    @Log("删除商品")
    @ApiOperation(value = "删除商品")
    @CacheEvict(cacheNames = ShopConstants.SHOP_REDIS_INDEX_KEY,allEntries = true)
    @DeleteMapping(value = "/yxStoreProduct/{id}")
    @PreAuthorize("hasAnyRole('admin','YXSTOREPRODUCT_ALL','YXSTOREPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        yxStoreProductService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }



    @ApiOperation(value = "商品上架/下架")
    @CacheEvict(cacheNames = ShopConstants.SHOP_REDIS_INDEX_KEY,allEntries = true)
    @PostMapping(value = "/yxStoreProduct/onsale/{id}")
    public ResponseEntity onSale(@PathVariable Long id, @RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer status = jsonObject.getInteger("status");
        yxStoreProductService.onSale(id,status);
        return new ResponseEntity(HttpStatus.OK);
    }
    @ApiOperation(value = "生成属性（添加活动产品专用）")
    @PostMapping(value = "/yxStoreProduct/isFormatAttrForActivity/{id}")
    public ResponseEntity isFormatAttrForActivity(@PathVariable Long id, @RequestBody String jsonStr){
        return new ResponseEntity <>(yxStoreProductService.getFormatAttr(id,jsonStr,true), HttpStatus.OK);
    }

    @ApiOperation(value = "生成属性")
    @PostMapping(value = "/yxStoreProduct/isFormatAttr/{id}")
    public ResponseEntity isFormatAttr(@PathVariable Long id, @RequestBody String jsonStr){
        return new ResponseEntity <>(yxStoreProductService.getFormatAttr(id,jsonStr,false), HttpStatus.OK);
    }



    @ApiOperation(value = "获取商品信息")
    @GetMapping(value = "/yxStoreProduct/info/{id}")
    public ResponseEntity info(@PathVariable Long id){
        Map<String,Object> map = new LinkedHashMap<>(3);

        //运费模板
        List<YxShippingTemplates> shippingTemplatesList = yxShippingTemplatesService.list();
        map.put("tempList", shippingTemplatesList);

        //商品分类
        List<YxStoreCategory> storeCategories = yxStoreCategoryService.lambdaQuery()
                .eq(YxStoreCategory::getIsShow, ShopCommonEnum.SHOW_1.getValue())
                .orderByAsc(YxStoreCategory::getPid)
                .list();

        List<Map<String,Object>> cateList = new ArrayList<>();
        map.put("cateList", this.makeCate(storeCategories,cateList,0,1));

        //商品规格
        map.put("ruleList",yxStoreProductRuleService.list());


        if(id == 0){
            return new ResponseEntity <>(map, HttpStatus.OK);
        }

        //处理商品详情
        YxStoreProduct yxStoreProduct = yxStoreProductService.getById(id);
        ProductDto productDto = new ProductDto();
        BeanUtil.copyProperties(yxStoreProduct,productDto,"sliderImage");
        productDto.setSliderImage(Arrays.asList(yxStoreProduct.getSliderImage().split(",")));
        YxStoreProductAttrResult storeProductAttrResult = yxStoreProductAttrResultService
                .getOne(Wrappers.<YxStoreProductAttrResult>lambdaQuery()
                        .eq(YxStoreProductAttrResult::getProductId,id).last("limit 1"));
        JSONObject result = JSON.parseObject(storeProductAttrResult.getResult());
        List<YxStoreProductAttrValue> attrValues = storeProductAttrValueService.list(new LambdaQueryWrapper <YxStoreProductAttrValue>().eq(YxStoreProductAttrValue::getProductId, yxStoreProduct.getId()));
        List<ProductFormatDto> productFormatDtos =attrValues.stream().map(i ->{
            ProductFormatDto productFormatDto = new ProductFormatDto();
            BeanUtils.copyProperties(i,productFormatDto);
            productFormatDto.setPic(i.getImage());
            return productFormatDto;
        }).collect(Collectors.toList());
        if(SpecTypeEnum.TYPE_1.getValue().equals(yxStoreProduct.getSpecType())){
            productDto.setAttr(new ProductFormatDto());
            productDto.setAttrs(productFormatDtos);
            productDto.setItems(result.getObject("attr",ArrayList.class));
        }else{

            productFromat(productDto, result);
        }

        map.put("productInfo",productDto);

        return new ResponseEntity <>(map, HttpStatus.OK);
    }

    /**
     * 获取商品属性
     * @param productDto
     * @param result
     */
    private void productFromat(ProductDto productDto, JSONObject result) {
        Map<String,Object> mapAttr = (Map<String,Object>) result.getObject("value",ArrayList.class).get(0);
        ProductFormatDto productFormatDto = ProductFormatDto.builder()
                .pic(mapAttr.get("pic").toString())
                .price(Double.valueOf(mapAttr.get("price").toString()))
                .cost(Double.valueOf(mapAttr.get("cost").toString()))
                .otPrice(Double.valueOf(mapAttr.get("otPrice").toString()))
                .stock(Integer.valueOf(mapAttr.get("stock").toString()))
                .barCode(mapAttr.get("barCode").toString())
                .weight(Double.valueOf(mapAttr.get("weight").toString()))
                .volume(Double.valueOf(mapAttr.get("volume").toString()))
                .value1(mapAttr.get("value1").toString())
                .integral(mapAttr.get("integral") !=null ? Integer.valueOf(mapAttr.get("integral").toString()) : 0)
                .brokerage(Double.valueOf(mapAttr.get("brokerage").toString()))
                .brokerageTwo(Double.valueOf(mapAttr.get("brokerageTwo").toString()))
                .pinkPrice(Double.valueOf(mapAttr.get("pinkPrice").toString()))
                .pinkStock(Integer.valueOf(mapAttr.get("pinkStock").toString()))
                .seckillPrice(Double.valueOf(mapAttr.get("seckillPrice").toString()))
                .seckillStock(Integer.valueOf(mapAttr.get("seckillStock").toString()))
                .build();
        productDto.setAttr(productFormatDto);
    }


    /**
     *  分类递归
     * @param data 分类列表
     * @param pid 附件id
     * @param level d等级
     * @return list
     */
    private List<Map<String,Object>> makeCate(List<YxStoreCategory> data,List<Map<String,Object>> cateList,int pid, int level)
    {
        String html = "|-----";
        String newHtml = "";
        List<YxStoreCategory> storeCategories = yxStoreCategoryService.lambdaQuery().eq(YxStoreCategory::getPid, 0).list();

        for (int i = 0; i < data.size(); i++) {
            YxStoreCategory storeCategory = data.get(i);
            int catePid =  storeCategory.getPid();
            Map<String,Object> map = new HashMap<>();
            if(catePid == pid){
                newHtml = String.join("", Collections.nCopies(level,html));
                map.put("value",storeCategory.getId());
                map.put("label",newHtml + storeCategory.getCateName());
                if(storeCategory.getPid() == 0){
                    map.put("disabled",0);
                }else{
                    map.put("disabled",1);
                }
                cateList.add(map);
                data.remove(i);

                i--;
                if(storeCategory.getPid() > 0){
                    this.makeCate(data,cateList,storeCategory.getPid(),level);
                }else{
                    this.makeCate(data,cateList,storeCategory.getId(),level + 1);
                }

            }
        }


        return cateList;
    }

}
