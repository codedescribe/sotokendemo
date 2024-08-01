package com.example.demo.core.service.impl;

import com.example.demo.core.entity.Product;
import com.example.demo.core.mapper.ProductMapper;
import com.example.demo.core.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangf
 * @since 2024-07-17
 */
@AllArgsConstructor
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public List<Product> listTest(Integer limitNum) {
        limitNum=limitNum==null?10:limitNum;
        return productMapper.listTest(limitNum);
    }
}
