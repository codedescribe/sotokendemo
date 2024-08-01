package com.example.demo.core.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.core.entity.User;
import com.example.demo.core.event.LoginEvent;
import com.example.demo.core.exceptions.MyExcption;
import com.example.demo.core.manager.TokenStore;
import com.example.demo.core.service.UserService;
import com.example.demo.core.vo.MyResponseEntity;
import com.example.demo.core.vo.TokenInfo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author maxfan
 * @since 2024-07-30
 */
@RestController
@RequestMapping("/")
public class UserLoginController {


    @Autowired
    private UserService userService;

    @Resource
    private ApplicationContext applicationContext;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "/login")
    public MyResponseEntity<TokenInfo> login(@RequestBody User user) {
        if(StringUtils.isBlank(user.getUsername())){
            return MyResponseEntity.fail("用户名为空");
        }
        QueryWrapper qw=new QueryWrapper<>();
        qw.eq("username",user.getUsername());
        User reUser=userService.getOne(qw);
        if(!reUser.getUsername().equals(user.getUsername()) || !reUser.getPassword().equals(user.getPassword())) {
            return MyResponseEntity.fail("密码不对");
        }
        applicationContext.publishEvent(new LoginEvent(UserLoginController.class,"login"));
        TokenInfo tokenInfo=tokenStore.storeAndGetVo(reUser);
        return MyResponseEntity.ok(tokenInfo);
    }

    @GetMapping("/isLogin")
    public MyResponseEntity isLogin() {
        System.out.println("token值:"+StpUtil.getTokenValue());

        Object loginId=StpUtil.getLoginId();
        System.out.println("登陆id:"+loginId);
        if(String.valueOf(loginId).equals("1")){
            throw new MyExcption("登录id为1");
        }
        return MyResponseEntity.ok( StpUtil.isLogin());
    }

    @PostMapping(value = "/logout")
    public MyResponseEntity<?> logout(@RequestBody User user) {
        StpUtil.logout();
        return MyResponseEntity.ok();
    }
}
