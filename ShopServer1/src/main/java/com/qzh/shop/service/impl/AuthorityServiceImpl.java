package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Authority;
import com.qzh.shop.mapper.AuthorityMapper;
import com.qzh.shop.service.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 13:25
 * @Version 1.0
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public IPage<Authority> getAllAuthority(Integer page, Integer size) {

        return  authorityMapper.selectPage(new Page<Authority>(page,size),new QueryWrapper<Authority>());
    }

    @Override
    public IPage<Authority> getLikeAllAuthority(Integer page, Integer size, String query) {
        return authorityMapper.selectPage(new Page<Authority>(page,size),
                new QueryWrapper<Authority>()
                        .or().like("path",query)
                        .or().like("name",query));
    }
}
