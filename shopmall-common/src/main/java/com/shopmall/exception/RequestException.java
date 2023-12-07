package com.shopmall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author zhuxiying
 * @date 2023-11-30
 * 统一异常处理
 */
@Getter
public class RequestException extends RuntimeException{

    private Integer status = BAD_REQUEST.value();

    public RequestException(String msg){
        super(msg);
    }

    public RequestException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
