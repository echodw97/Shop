package com.qzh.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Commodity;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/16 18:31
 * @Version 1.0
 */
public interface CommodityService {

    /**
     * 查询对应id分类下所有的商品
     * @param page 第几页
     * @param size 每页大小
     * @param id 商品分类id
     * @return List<Commodity>
     * */
    public Page<Commodity> getAllCommodityById(@Param("page")Integer page, @Param("size")Integer size, @Param("id") Integer id);


    /**
     * 匹配查询出对应的商品
     * @param page 第几页
     * @param size 每页大小
     * @param query 查询关键字
     * @return Commodity
     * */
    public IPage<Commodity> getAllCommodity(@Param("page")Integer page, @Param("size")Integer size, @Param("query")String query);


    /**
     * 根据id添加商品
     * @return int
     * */
    public int addCommodityById(@Param("name")String name, @Param("price")Integer price, @Param("id")int  id);


    /**
     * 根据id删除商品
     * @return int
     * */
    public int deleteCommodityById(@Param("id")int  id);

    /**
     * 根据id查询商品信息
     * @return int
     * */
    public Commodity getCommodityById(@Param("id")int  id);

    /**
     * 根据id修改商品信息
     * @return int
     * */
    public int updateCommodityById(@Param("price") int price, @Param("id")int  id);


}
