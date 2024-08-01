package com.example.demo.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.core.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/login")
    public ResponseEntity<Object> userLogin(String name) {


        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}
