package com.example.demo.core.mapper;

import com.example.demo.core.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangf
 * @since 2024-07-17
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> listTest(int limitNum);
}
