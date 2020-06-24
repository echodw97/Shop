package com.qzh.shop.service.impl;

import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.mapper.RegisterMapper;
import com.qzh.shop.service.RegisterService;
import com.qzh.shop.utils.Token;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/8 21:10
 * @Version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    RegisterMapper registerMapper;

    @Override
    public int register(String username, String password, String mobile, String email) {
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setMobile(mobile);
        users.setEmail(email);
        users.setToken(new Token().getToken());
        return registerMapper.insert(users);
    }
}
