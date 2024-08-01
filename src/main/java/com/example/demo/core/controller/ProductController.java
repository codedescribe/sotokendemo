package com.example.demo.core.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.core.service.ProductService;
import com.example.demo.core.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangf
 * @since 2024-07-17
 */
@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Product>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Product> aPage = productService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Product params) {
        productService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        productService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Product params) {
        productService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
    @GetMapping(value = "/list")
    public ResponseEntity<Object> listOwn(Integer limitNum) {
        return new ResponseEntity<>(productService.listTest(limitNum), HttpStatus.OK);
    }
}
