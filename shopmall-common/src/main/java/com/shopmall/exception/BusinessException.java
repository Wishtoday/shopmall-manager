package com.shopmall.exception;


import com.shopmall.api.ApiCode;

/**
 * 业务异常
 * @author zhuxiying
 * @date 2023-12-12
 */
public class BusinessException extends ShopMallException {
	private static final long serialVersionUID = -2303357122330162359L;

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public BusinessException(ApiCode apiCode) {
        super(apiCode);
    }

}
