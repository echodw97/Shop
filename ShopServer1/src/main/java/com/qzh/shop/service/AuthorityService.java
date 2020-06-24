package com.qzh.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Authority;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 13:21
 * @Version 1.0
 */
public interface AuthorityService {

    /**
     * 查询出所有的权限
     * @param page 第几页
     * @param size 每页大小
     * @return Authority
     * */
    public IPage<Authority> getAllAuthority(@Param("page")Integer page, @Param("size")Integer size);

    /**
     * 查询出匹配的authority
     * @param page 第几页
     * @param size 每页大小
     * @param query 查询关键字
     * @return Authority
     * */
    public IPage<Authority> getLikeAllAuthority(@Param("page")Integer page, @Param("size")Integer size, @Param("query")String query);
}
