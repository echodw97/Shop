package com.qzh.shop.service;

import org.apache.ibatis.annotations.Param;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/13 16:02
 * @Version 1.0
 */
public interface RegisterService {

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @param mobile 手机
     * @param email 邮箱
     * @return Login
     * */
    public int register(@Param("username") String username, @Param("password") String password, @Param("mobile") String mobile, @Param("email") String email);
}
