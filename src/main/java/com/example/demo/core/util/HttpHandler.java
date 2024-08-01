/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.example.demo.core.util;

import cn.hutool.core.util.CharsetUtil;
import com.example.demo.core.exceptions.MyExcption;
import com.example.demo.core.vo.MyResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/7/16
 */
@Component
@AllArgsConstructor
public class HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    //objectMapper 是一个 ObjectMapper 对象，它是 Jackson 库的一部分，用于将 Java 对象序列化为 JSON 字符串
    private final ObjectMapper objectMapper;

    public <T> void printServerResponseToWeb(MyResponseEntity<T> responseEntity) {
        if (responseEntity == null) {
            logger.info("print obj is null");
            return;
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            logger.error("requestAttributes is null, can not print to web");
            return;
        }
        HttpServletResponse response = requestAttributes.getResponse();
        if (response == null) {
            logger.error("httpServletResponse is null, can not print to web");
            return;
        }
        logger.error("response error: " + responseEntity.getMsg());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //
        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
//            response.getWriter() 返回一个 PrintWriter 对象，用于向客户端发送字符文本
            printWriter.write(objectMapper.writeValueAsString(responseEntity));
        }
        catch (IOException e) {
            throw new MyExcption("io 异常", e);
        }
    }

    public <T> void printServerResponseToWeb(MyExcption excption) {
        if (excption == null) {
            logger.info("print obj is null");
            return;
        }

        MyResponseEntity<T> responseEntity = new MyResponseEntity<>();
        responseEntity.setCode(excption.getCode());
        responseEntity.setMsg(excption.getMessage());
        printServerResponseToWeb(responseEntity);
    }

}
