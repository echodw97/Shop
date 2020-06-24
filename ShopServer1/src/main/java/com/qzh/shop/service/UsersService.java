package com.qzh.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Users;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/14 19:23
 * @Version 1.0
 */
public interface UsersService {
    /**
     * 查询出所有的user用户
     * @param page 第几页
     * @param size 每页大小
     * @return UserList
     * */
    public IPage<Users> getAllUsers(@Param("page")Integer page, @Param("size")Integer size) throws ParseException;


    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return  Users
     * */
    public Users getUsersById(@Param("id")Integer id);

    /**
     * 查询出所有的user用户
     * @param page 第几页
     * @param size 每页大小
     * @param query 查询关键字
     * @return UserList
     * */
    public IPage<Users> getAll(@Param("page")Integer page, @Param("size")Integer size, @Param("query")String query);


    /**
     * 修改Users用户信息
     * @param username 用户名
     * @param mobile 手机号码
     * @param email 邮箱
     * @return int
     * */
    public int updateUsers(@Param("username")String username, @Param("mobile")String mobile, @Param("email")String email);

    /**
     * 根据username查询用户信息
     * @param username 用户username
     * @return  Users
     * */
    public Users getUsersByUsername(@Param("username")String username);

    /**
     * 根据id删除用户信息
     * @param id 用户id
     * @return int
     * */
    public int deleteUsersById(@Param("id")Integer id);


}
