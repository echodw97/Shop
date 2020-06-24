package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.mapper.LoginMapper;
import com.qzh.shop.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/13 16:06
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public Users login(String username, String password) {
        return  loginMapper.selectOne(new QueryWrapper<Users>()
        .eq("username",username)
        .eq("password",password));


    }
}
