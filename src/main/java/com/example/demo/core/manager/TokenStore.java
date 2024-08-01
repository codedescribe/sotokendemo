package com.example.demo.core.manager;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.core.entity.User;
import com.example.demo.core.exceptions.MyExcption;
import com.example.demo.core.vo.TokenInfo;
import com.example.demo.core.enums.HttpCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenStore {
    //如果用redis缓存token
//    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * oauth 授权相关key
     */
    String OAUTH_PREFIX = "demo_oauth:";
    String OAUTH_TOKEN_PREFIX = OAUTH_PREFIX + "token:";
    String USER_INFO = OAUTH_TOKEN_PREFIX + "user_info:";

    /**
     * 生成token，并返回token展示信息
     * @param User
     * @return
     */
    public TokenInfo storeAndGetVo(User user) {

        //生成token 设置过期时间，保存token到缓存
        TokenInfo tokenInfo = storeAccessSaToken(user);

        return tokenInfo;
    }

    /**
     * 以Sa-Token技术生成token，并返回token信息
     * 官网 https://sa-token.cc/doc.html#/
     * Sa-Token 是一个轻量级 Java 权限认证框架，主要解决：登录认证、权限认证、单点登录、OAuth2.0、分布式Session会话、微服务网关鉴权 等一系列权限相关问题。
     * @param user
     * @return
     */
    private TokenInfo storeAccessSaToken(User user) {
        // token生成
        //过期时长  秒
        int timeoutSecond = getExpiresIn();
        //用户id 类型 拼接登陆uid
        String uid = user.getId()+"";
        StpUtil.login(uid, timeoutSecond);
        String token = StpUtil.getTokenValue();
        // 用户信息存入redis缓存
        String keyName = USER_INFO + uid;
//        redisTemplate.opsForValue().set(
//                keyName,
//                JSON.toJSONString(user)
//            );

        // 数据封装返回(token不用加密)
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpiresIn(timeoutSecond);
        tokenInfo.setAccessToken(token);
        return tokenInfo;
    }


    /**
     * 根据accessToken 获取用户信息
     * @param accessToken accessToken
     * @return 用户信息
     */
    public User getUserInfoByAccessToken(String accessToken) {
        String uid = this.getUidByToken(accessToken);
        //uid当前规则就是用户id
        User user = new User();
        user.setId(Integer.valueOf(uid));
        // 如果是用户端的请求，直接返回uid
        return user;
        // 后台管理的请求，需要从redis中获取权限列表、昵称等数据
//        return this.getUserInfoInTokenByCache(userInfoInToken);
    }

    /**
     * 获取登录用户的uid
     * @return
     */
    private String getUidByToken(String token) {
        Object uid;
        try {
            uid = StpUtil.getLoginIdByToken(token);
        } catch (NotLoginException e) {
            // 登录过期，请重新登录 -- 如果是sa-token未登录异常，需要替换成商城的未登录异常
            throw new MyExcption(HttpCodes.FAIL, "请重新登陆");
        }
        return uid.toString();
    }

    /**
     * 计算过期时间（单位:秒）
     * 如果因为不同用户类型影响时间，可以增加参数进行修改
     * @return
     */
    public int getExpiresIn() {
        // 3600秒
        int expiresIn = 3600;
        return expiresIn;
    }
}
