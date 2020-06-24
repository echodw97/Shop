package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Commodity;
import com.qzh.shop.mapper.CommodityMapper;
import com.qzh.shop.service.CommodityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/16 18:39
 * @Version 1.0
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    @Resource
    CommodityMapper commodityMapper;

    @Override
    public Page<Commodity> getAllCommodityById(Integer page, Integer size, Integer id) {
        Page<Commodity> page1 = new Page<Commodity>(page, size);
        return page1.setRecords(this.commodityMapper.getAllCommodityById(page1, id));
    }

    @Override
    public IPage<Commodity> getAllCommodity(Integer page, Integer size, String query) {
        return  commodityMapper.selectPage(new Page<Commodity>(page,size),
                new QueryWrapper<Commodity>()
                        .or().like("name",query));
    }

    @Override
    public int addCommodityById(String name, Integer price, int id) {
        Commodity commodity = new Commodity();
        commodity.setName(name);
        commodity.setPrice(price);
        commodity.setPId(id);
        return commodityMapper.insert(commodity);
    }

    @Override
    public int deleteCommodityById(int id) {
        return commodityMapper.deleteById(id);
    }

    @Override
    public Commodity getCommodityById(int id) {
        return commodityMapper.selectById(id);
    }

    @Override
    public int updateCommodityById(int price, int id) {
        Commodity commodity = new Commodity();
        commodity.setPrice(price);
        return commodityMapper.update(commodity,new QueryWrapper<Commodity>().eq("id",id));
    }
}
