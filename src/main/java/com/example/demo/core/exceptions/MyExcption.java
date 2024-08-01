package com.example.demo.core.exceptions;

import com.example.demo.core.enums.HttpCodes;
import com.example.demo.core.vo.MyResponseEntity;
import lombok.Getter;

import java.io.Serial;

@Getter
public class MyExcption extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;

    private Object object;

    private MyResponseEntity<?> responseEntity;

    public MyExcption(String msg) {
        super(msg);
        this.code = HttpCodes.FAIL;
    }
    /**
     * 获取异常信息
     * @param code 异常信息
     * @param msg 异常对象
     */
    public MyExcption(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 获取异常信息
     * @param msg 异常信息
     * @param object 异常对象
     */
    public MyExcption(String msg, Object object) {
        super(msg);
        this.code = HttpCodes.FAIL;
        this.object = object;
    }

    public MyExcption(MyResponseEntity<?> responseEntity){
        this.responseEntity=responseEntity;
    }
}
