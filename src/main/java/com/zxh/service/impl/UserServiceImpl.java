package com.zxh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxh.entity.User;
import com.zxh.mapper.UserMapper;
import com.zxh.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
