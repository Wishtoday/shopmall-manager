package com.shopmall.modules.product.service;

import com.shopmall.modules.product.domain.YxStoreProduct;
import com.shopmall.modules.product.dto.StoreProductDto;
import com.shopmall.modules.product.dto.YxStoreProductDto;
import com.shopmall.modules.product.dto.YxStoreProductQueryCriteria;
import com.shopmall.modules.product.param.YxStoreProductQueryParam;
import com.shopmall.modules.product.vo.ProductVo;
import com.shopmall.modules.product.vo.YxStoreProductQueryVo;
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
public interface YxStoreProductService extends BaseService <YxStoreProduct> {

    /**
     * 增加库存 减少销量
     * @param num 数量
     * @param productId 商品id
     * @param unique sku唯一值
     */
    void incProductStock(Integer num, Long productId, String unique, Long activityId, String type);

    /**
     * 减少库存与增加销量
     * @param num 数量
     * @param productId 商品id
     * @param unique sku
     */
    void decProductStock(int num, Long productId, String unique, Long activityId, String type);

    YxStoreProduct getProductInfo(Long id);

    /**
     * 获取单个商品
     * @param id 商品id
     * @return YxStoreProductQueryVo
     */
    YxStoreProductQueryVo getStoreProductById(Long id);

    /**
     * 返回普通商品库存
     * @param productId 商品id
     * @param unique sku唯一值
     * @return int
     */
    int getProductStock(Long productId, String unique, String type);

    /**
     * 商品列表
     * @param productQueryParam YxStoreProductQueryParam
     * @return list
     */
    List<YxStoreProductQueryVo> getGoodsList(YxStoreProductQueryParam productQueryParam);

    /**
     * 商品详情
     * @param id 商品id
     * @param uid 用户id
     * @param latitude 纬度
     * @param longitude 经度
     * @return ProductVo
     */
    ProductVo goodsDetail(Long id, Long uid, String latitude, String longitude);

    /**
     *  商品浏览量
     * @param productId
     */
    void incBrowseNum(Long productId);

    /**
     * 商品列表
     * @param page 页码
     * @param limit 条数
     * @param order ProductEnum
     * @return List
     */
    List<YxStoreProductQueryVo> getList(int page, int limit, int order);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreProductQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreProductDto>
    */
    List<YxStoreProduct> queryAll(YxStoreProductQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <YxStoreProductDto> all, HttpServletResponse response) throws IOException;



    /**
     * 商品上架下架
     * @param id 商品id
     * @param status  ShopCommonEnum
     */
    void onSale(Long id, Integer status);

    /**
     * 获取生成的属性
     * @param id 商品id
     * @param jsonStr jsonStr
     * @return map
     */
    Map<String,Object> getFormatAttr(Long id, String jsonStr, boolean isActivity);




    /**
     * 新增/保存商品
     * @param storeProductDto 商品
     */
    void insertAndEditYxStoreProduct(StoreProductDto storeProductDto);

    /**+
     * 删除商品转发海报
     * @param id
     */
    void deleteForwardImg(Long id);
}
