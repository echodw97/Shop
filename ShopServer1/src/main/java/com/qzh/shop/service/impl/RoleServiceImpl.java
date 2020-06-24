package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.mapper.RoleMapper;
import com.qzh.shop.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/10 12:52
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public IPage<Users> getAllRole(Integer page, Integer size) {
        return roleMapper.selectPage(new Page<Users>(page,size),new QueryWrapper<Users>());
    }

    @Override
    public IPage<Users> getAll(Integer page, Integer size, String query) {
        return  roleMapper.selectPage(new Page<Users>(page,size),
                new QueryWrapper<Users>()
                        .or().like("username",query)
                        .or().like("role_name",query));
    }
}
