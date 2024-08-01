package com.example.demo.core.service;

import com.example.demo.core.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangf
 * @since 2024-07-17
 */
public interface ProductService extends IService<Product> {

    List<Product> listTest(Integer limitNum);
}
