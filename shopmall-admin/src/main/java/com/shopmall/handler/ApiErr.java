package com.shopmall.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuxiying
 * @since 2023-12-12
 */
@Data
class ApiErr {

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String msg;

    private ApiErr() {
        timestamp = LocalDateTime.now();
    }

    public ApiErr(Integer status, String message) {
        this();
        this.status = status;
        this.msg = message;
    }
}


