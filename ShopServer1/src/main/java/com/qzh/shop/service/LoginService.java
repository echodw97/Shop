package com.qzh.shop.service;

import com.qzh.shop.POJO.DO.Users;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/13 16:02
 * @Version 1.0
 */
public interface LoginService {

    /**
     * 判断登陆
     * @param username 用户名
     * @param password 密码
     * @return Login
     * */
    public Users login(@Param("username") String username, @Param("password") String password);
}
