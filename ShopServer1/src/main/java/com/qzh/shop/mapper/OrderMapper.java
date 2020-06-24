package com.qzh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzh.shop.POJO.DO.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/17 14:06
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


    /**
     * 更新order表
     * @return int
     * */
    public int updateOrder();
}
