package com.shopmall.modules.product.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.product.domain.YxStoreProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Repository
public interface StoreProductMapper extends CoreMapper <YxStoreProduct> {

    /**
     * 正常商品库存 减库存 加销量
     * @param num
     * @param productId
     * @return
     */
//    @Update("update yx_store_product set stock=stock-#{num}, sales=sales+#{num}" +
//            " where id=#{productId} and stock >= #{num}")
    int decStockIncSales(@Param("num") Integer num, @Param("productId") Long productId);

    /**
     * 正常商品库存 加库存 减销量
     * @param num
     * @param productId
     * @return
     */
//    @Update("update yx_store_product set stock=stock+#{num}, sales=sales-#{num}" +
//            " where id=#{productId}")
    int incStockDecSales(@Param("num") Integer num, @Param("productId") Long productId);


//    @Update("update yx_store_product set is_show = #{status} where id = #{id}")
    void updateOnsale(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 拼团商品库存，减库存 加销量
     * @param num
     * @param productId
     * @param activityId
     * @return
     */
//    @Update("update yx_store_combination set stock=stock-#{num}, sales=sales+#{num}" +
//            " where id=#{activityId} and stock >= #{num}")
    int decCombinationStockIncSales(@Param("num") Integer num, @Param("productId") Long productId, @Param("activityId") Long activityId);

    /**
     * 秒杀产品库存 减库存，加销量
     * @param num
     * @param productId
     * @param activityId
     * @return
     */
//    @Update("update yx_store_seckill set stock=stock-#{num}, sales=sales+#{num}" +
//            " where id=#{activityId} and stock >= #{num}")
    int decSeckillStockIncSales(@Param("num") Integer num, @Param("productId") Long productId, @Param("activityId") Long activityId);

    /**
     * 拼团商品库存，加库存 减销量
     * @param num
     * @param productId
     * @param activityId
     */
//    @Update("update yx_store_combination set stock=stock+#{num}, sales=sales-#{num}" +
//            " where id=#{activityId} and stock >= #{num}")
    void incCombinationStockIncSales(@Param("num") Integer num, @Param("productId") Long productId, @Param("activityId") Long activityId);

//    /**
//     * 秒杀产品库存 加库存，减销量
//     * @param num
//     * @param productId
//     * @param activityId
//     * @return
//     */
//    @Update("update yx_store_seckill set stock=stock+#{num}, sales=sales-#{num}" +
//            " where id=#{activityId} and stock >= #{num}")
    void incSeckillStockIncSales(@Param("num") Integer num, @Param("productId") Long productId, @Param("activityId") Long activityId);

    /**
     * 商品浏览量
     * @param productId
     * @return
     */
//    @Update("update yx_store_product set browse=browse+1 " +
//            "where id=#{productId}")
    int incBrowseNum(@Param("productId") Long productId);

//    @Delete("DELETE from yx_system_attachment where name like CONCAT(#{id},'_%',#{name}, '%')")
    void deleteForwardImg(@Param("id") Long id, @Param("name") String name);

    @Override
//    @Select("SELECT id,spec_type,ot_price,mer_use,description,is_postage,is_sub,is_best,(sales+ficti) as sales,price,is_bargain,vip_price,store_name,stock,keyword,image,cost,is_good,unit_name,is_benefit,update_time,give_integral,is_new,sort,slider_image,is_show,bar_code,postage,code_path,create_time,cate_id,is_seckill,mer_id,temp_id,ficti,store_info,is_del,is_hot,is_integral,integral,browse FROM yx_store_product ${ew.customSqlSegment}")
    <E extends IPage <YxStoreProduct>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper <YxStoreProduct> queryWrapper);
}
