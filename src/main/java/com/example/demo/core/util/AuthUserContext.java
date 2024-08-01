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


import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.demo.core.entity.User;

/**
 * @author yangf
 * @date 2024/7/31
 */
public class AuthUserContext {

    private static final ThreadLocal<User> USER_INFO_IN_TOKEN_HOLDER = new TransmittableThreadLocal<>();

    public static User get() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }

    public static void set(User user) {
        USER_INFO_IN_TOKEN_HOLDER.set(user);
    }

    public static void clean() {
        if (USER_INFO_IN_TOKEN_HOLDER.get() != null) {
            USER_INFO_IN_TOKEN_HOLDER.remove();
        }
    }

}
