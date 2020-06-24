package com.qzh.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Users;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/10 12:40
 * @Version 1.0
 */
public interface RoleService {

    /**
     * 查询出所有的角色
     * @param page 第几页
     * @param size 每页大小
     * @return Users
     * */
    public IPage<Users> getAllRole(@Param("page")Integer page, @Param("size")Integer size);

    /**
     * 查询出匹配的role
     * @param page 第几页
     * @param size 每页大小
     * @param query 查询关键字
     * @return UserList
     * */
    public IPage<Users> getAll(@Param("page")Integer page, @Param("size")Integer size, @Param("query")String query);
}
