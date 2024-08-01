package com.example.demo.core.service.impl;

import com.example.demo.core.entity.User;
import com.example.demo.core.mapper.UserMapper;
import com.example.demo.core.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maxfan
 * @since 2024-07-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
