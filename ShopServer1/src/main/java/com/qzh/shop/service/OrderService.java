package com.qzh.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Commodity;
import com.qzh.shop.POJO.DO.Order;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/17 14:12
 * @Version 1.0
 */
public interface OrderService {

    /**
     * 查询出所有的订单列表
     * @param page 第几页
     * @param size 每页大小
     * @return Order
     * */
    public IPage<Order> getAllOrder(@Param("page")Integer page, @Param("size")Integer size) throws ParseException;



    /**
     * 查询出匹配的订单
     * @param page 第几页
     * @param size 每页大小
     * @param query 查询关键字
     * @return Order
     * */
    public IPage<Order> getAllOrderLikeList(@Param("page")Integer page, @Param("size")Integer size, @Param("query")String query);


    /**
     * 根据id删除订单
     * @param id
     * @return int
     * */
    public int deleteOrderById(@Param("id") Integer id);


    /**
     * 查询所有的商品
     * @return Commodity
     * */
    public List<Commodity> getAllCommodity();

    /**
     * 根据p_id插入订单
     * @return Commodity
     * */
    public int insertOrderByID(@Param("id") Integer id);

    /**
     * 根据p_id更新order
     * @return int
     * */
    public int updateOrder();

    /**
     * 根据id查询对应的订单信息
     * @return Order
     * */
    public Order getOrderByID(@Param("id") Integer id);


    /**
     * 根据id更新order
     * @return int
     * */
    public int updateOrderById(@Param("pId") Integer pId,@Param("id") Integer id,@Param("payment") String payment,@Param("ship") String ship);
}
