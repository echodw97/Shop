package com.qzh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/16 18:30
 * @Version 1.0
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 查询对应id分类下所有的商品信息
     * @param page
     * @param id
     * @return Commodity
     * */
    public List<Commodity> getAllCommodityById(Page<Commodity> page, @Param("id") Integer id);
}
