/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.example.demo.core.config;

import com.example.demo.core.exceptions.MyExcption;
import com.example.demo.core.vo.MyResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 自定义错误处理器
 * @author LGH
 */
@Slf4j
@Controller
@RestControllerAdvice
public class DefaultExceptionHandlerConfig {


    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandlerConfig.class);

    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
    public ResponseEntity<MyResponseEntity<List<String>>> methodArgumentNotValidExceptionHandler(Exception e) {
        logger.error("methodArgumentNotValidExceptionHandler", e);
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }
        if (fieldErrors == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(MyResponseEntity.fail("方法参数存在问题"));
        }

        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            defaultMessages.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(MyResponseEntity.fail(500, defaultMessages));
    }

    @ExceptionHandler(MyExcption.class)
    public ResponseEntity<MyResponseEntity<Object>> exceptionHandler(MyExcption e) {
        logger.error("MyExcption error", e);
        return ResponseEntity.status(HttpStatus.OK).body(MyResponseEntity.fail(e.getMessage()));
    }
    /**
     * 代理了所有的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyResponseEntity  <Object>> exceptionHandler(Exception e) {
        logger.error("exceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK).body(MyResponseEntity.fail(e.getMessage()));
    }
}
