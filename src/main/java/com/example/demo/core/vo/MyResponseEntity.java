package com.example.demo.core.vo;

import com.example.demo.core.enums.HttpCodes;
import lombok.Data;

/**
 * 返回对象
 */
@Data
public class MyResponseEntity<T> {

    private int code;
    private String msg;
    private T data;

    public MyResponseEntity() {
        this.code = HttpCodes.SUCCESS;
        this.msg = HttpCodes.SUCCESS_MSG;
    }

    public MyResponseEntity(T data) {
        this.code = HttpCodes.SUCCESS;
        this.msg = HttpCodes.SUCCESS_MSG;
        this.data = data;
    }

    public MyResponseEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MyResponseEntity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> MyResponseEntity<T> ok(){
        return ok(null);
    }
    public static <T> MyResponseEntity<T> ok(T data){
        return new MyResponseEntity<T>(HttpCodes.SUCCESS,HttpCodes.SUCCESS_MSG,data);
    }

    public static MyResponseEntity<?> fail(){
        return fail(HttpCodes.FAIL_MSG);
    }
    public static <T> MyResponseEntity<T> fail(String msg){
        return new MyResponseEntity<>(HttpCodes.FAIL,msg);
    }
    public static MyResponseEntity<?> fail(int code,String msg){
        return new MyResponseEntity<>(code,msg);
    }
    public static <T> MyResponseEntity<T> fail(int code,T data){
        return new MyResponseEntity<T>(code,"",data);
    }
}
