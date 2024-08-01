package com.example.demo.core.event;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginEventListener {

    @EventListener(LoginEvent.class)
    public void handleLoginEvent(LoginEvent event){
        log.info("登陆监听到的event:{}", JSONObject.toJSONString(event));
    }
}
