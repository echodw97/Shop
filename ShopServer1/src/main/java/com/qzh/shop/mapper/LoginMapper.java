package com.qzh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzh.shop.POJO.DO.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/13 16:00
 * @Version 1.0
 */
@Mapper
public interface LoginMapper extends BaseMapper<Users> {
}
