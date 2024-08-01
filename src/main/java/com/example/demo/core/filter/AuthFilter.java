package com.example.demo.core.filter;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.demo.core.adapter.AuthConfigAdapter;
import com.example.demo.core.entity.User;
import com.example.demo.core.exceptions.MyExcption;
import com.example.demo.core.manager.TokenStore;
import com.example.demo.core.util.AuthUserContext;
import com.example.demo.core.util.HttpHandler;
import com.example.demo.core.vo.MyResponseEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;


/**
 * 过滤器、
 * 按规则过滤未登录请求，通过已登陆或静态资源路径，开放接口等
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {
    @Value("${sa-token.token-name}")
    private String tokenName;
    private final AuthConfigAdapter authConfigAdapter;
    private final TokenStore tokenStore;
    private final HttpHandler httpHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 获取当前请求路径
        String requestUri = req.getRequestURI();
        log.info("请求路径为:{}", requestUri);
        //判断是否需要登陆，需要则进行处理
        List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();
        //Spring 提供的路径匹配器
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 如果匹配不需要授权的路径，就不需要校验是否需要授权
        if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
            for (String excludePathPattern : excludePathPatterns) {
                if (pathMatcher.match(excludePathPattern, requestUri)) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }
//        boolean isLogin=StpUtil.isLogin();
//        Object id=StpUtil.getLoginId();
        // 检查用户是否已登录的逻辑
        // 获取请求的header中的token
        String accessToken = StpUtil.getTokenValue();
//        String accessToken = req.getHeader(tokenName);
        log.info("access_token:{}", accessToken);
        User user=null;
        try{
            if (StringUtils.isNotBlank(accessToken)) {
                //校验登陆信息，去除用户信息
                try {

                    user = tokenStore.getUserInfoByAccessToken(accessToken);
                }catch (Exception e){
                    //有异常就用http处理返回信息
                    httpHandler.printServerResponseToWeb(MyResponseEntity.fail("请重新登陆"));
                    return;
                }
            }else{
//                没有token 返回401
                httpHandler.printServerResponseToWeb(MyResponseEntity.fail("没有获取到登陆信息"));
                return;
            }
            // 保存上下文
            AuthUserContext.set(user);

            chain.doFilter(req, resp);
        }catch (Exception e){
            //过程报错  如果是自己主动抓的异常，返回
            if(e instanceof MyExcption){
                //TODO:返回前端自己的异常信息

                return;
            }
            throw e;
        }finally {
        AuthUserContext.clean();
        }
    }

    @Override
    public void destroy() {
        //过滤器销毁操作
        //如果过滤器试用期间打开了一些资源或者数据库连接，则在销毁阶段进行关闭
        Filter.super.destroy();
    }
}
