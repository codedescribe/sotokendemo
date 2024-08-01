package com.example.demo.core.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginEvent {
//    操作表对象
    private Class clz;
    //操作数据
    private Object data;


}
