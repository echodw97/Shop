package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Commodity;
import com.qzh.shop.POJO.DO.Order;
import com.qzh.shop.mapper.CommodityMapper;
import com.qzh.shop.mapper.OrderMapper;
import com.qzh.shop.service.OrderService;
import com.qzh.shop.utils.Numbering;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/17 14:16
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    CommodityMapper commodityMapper;

    @Override
    public IPage<Order> getAllOrder(Integer page, Integer size) throws ParseException {

        IPage<Order> iPage = orderMapper.selectPage(new Page<Order>(page,size),new QueryWrapper<Order>());
        return iPage;
    }

    @Override
    public IPage<Order> getAllOrderLikeList(Integer page, Integer size, String query) {
        return  orderMapper.selectPage(new Page<Order>(page,size),
                new QueryWrapper<Order>()
                        .or().like("numbering",query)
                        .or().like("payment",query)
                        .or().like("ship",query));
    }

    @Override
    public int deleteOrderById(Integer id) {
        return orderMapper.deleteById(id);
    }

    @Override
    public List<Commodity> getAllCommodity() {
        return commodityMapper.selectList(new QueryWrapper<Commodity>());
    }

    @Override
    public int insertOrderByID(Integer id) {
        Order order = new Order();
        order.setNumbering(new Numbering().getNumbering());
        order.setPId(id);
        return orderMapper.insert(order);
    }

    @Override
    public int updateOrder() {
        return orderMapper.updateOrder();
    }

    @Override
    public Order getOrderByID(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    public int updateOrderById(Integer pId,Integer id,String payment,String ship) {
        Order order = new Order();
        order.setPId(pId);
        order.setPayment(payment);
        order.setShip(ship);
        return orderMapper.update(order,new QueryWrapper<Order>().eq("id",id));
    }
}
