package com.shopmall.exception;


import com.shopmall.api.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 * @author zhuxiying
 * @date 2023-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShopMallException extends RuntimeException{

    private static final long serialVersionUID = -2470461654663264392L;

    private Integer errorCode;
    private String message;

    public ShopMallException() {
        super();
    }

    public ShopMallException(String message) {
        super(message);
        this.message = message;
    }

    public ShopMallException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ShopMallException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public ShopMallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopMallException(Throwable cause) {
        super(cause);
    }

}
