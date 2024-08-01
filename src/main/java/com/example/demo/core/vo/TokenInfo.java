package com.example.demo.core.vo;

import lombok.Data;

/**
 * 登陆后返回给前端token信息
 */
@Data
public class TokenInfo {
    //登陆凭证，账户鉴权
    private String accessToken;
    // 刷新token的凭证，到期后可以凭借此凭证获取新的at
    private String refreshToken;
    //过期时间 秒
    private Integer expiresIn;
}
