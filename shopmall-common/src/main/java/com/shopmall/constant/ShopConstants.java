package com.shopmall.constant;

/**
 * 商城统一常量
 * @author zhuxiying
 * @since 2023-12-01
 */
public interface ShopConstants {

	/**
	 * 订单自动取消时间（分钟）
	 */
	long ORDER_OUTTIME_UNPAY = 30;
	/**
	 * 订单自动收货时间（天）
	 */
	long ORDER_OUTTIME_UNCONFIRM = 7;
	/**
	 * redis订单未付款key
	 */
	String REDIS_ORDER_OUTTIME_UNPAY = "order:unpay:";
	/**
	 * redis订单收货key
	 */
	String REDIS_ORDER_OUTTIME_UNCONFIRM = "order:unconfirm:";

	/**
	 * 商城默认密码
	 */
	String SHOPMALL_DEFAULT_PWD = "123456";

	/**
	 * 腾讯地图地址解析
	 */
	String QQ_MAP_URL = "https://apis.map.qq.com/ws/geocoder/v1/";

	String APP_LOGIN_USER = "app-online-token:";
	/**
	 * redis首页键
	 */
	String SHOP_REDIS_INDEX_KEY = "shop:index_data";
	/**
	 *后台api地址
	 *
	 */
	String ADMIN_API_URL="admin_api_url";

}
