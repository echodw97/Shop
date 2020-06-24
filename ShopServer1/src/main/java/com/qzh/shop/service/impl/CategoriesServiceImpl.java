package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.FirstCategories;
import com.qzh.shop.POJO.DO.SecondCategories;
import com.qzh.shop.mapper.CategoriesMapper;
import com.qzh.shop.service.CategoriesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 19:15
 * @Version 1.0
 */
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Resource
    private CategoriesMapper categoriesMapper;


    @Override
    public Page<FirstCategories> getAllCategories(Integer page, Integer size, FirstCategories firstCategories) {
        Page<FirstCategories> page1 = new Page<FirstCategories>(page, size);
        return page1.setRecords(this.categoriesMapper.getAllCategories(page1, firstCategories));
    }

    @Override
    public Page<FirstCategories> getAllCategoriesLike(Integer page, Integer size, FirstCategories firstCategories,String query) {
        Page<FirstCategories> page1 = new Page<FirstCategories>(page, size);
        return page1.setRecords(this.categoriesMapper.getAllCategoriesLike(page1, firstCategories,query));
    }

    @Override
    public List<FirstCategories> getAllCategoriesParent() {
      return   categoriesMapper.getAllCategoriesParent();
    }

    @Override
    public int addCategories(String catName, Integer catPid, Integer catLevel) {
       if (catLevel == 0){
          return categoriesMapper.addFirstCategoriesName(catName,catPid);
       }else {
          return categoriesMapper.addSecondCategoriesName(catName,catPid);
       }
    }

    @Override
    public int deleteCategoriesById(Integer catId, Integer catLevel) {
        if (catLevel == 0){
           return categoriesMapper.deleteFirstCategoriesById(catId);
        }else {
          return   categoriesMapper.deleteSecondCategoriesById(catId);
        }
    }

    @Override
    public FirstCategories getFirstCategories(Integer catId) {
        return categoriesMapper.getFirstCategoriesById(catId);
    }

    @Override
    public SecondCategories getSecondCategories(Integer catId) {
        return categoriesMapper.getSecondCategoriesById(catId);
    }

    @Override
    public int updateFirstCategories(Integer catId,String catName) {
         return categoriesMapper.updateFirstCategoriesById(catId,catName);
    }

    @Override
    public int updateSecondCategories(Integer catId,String catName) {
        return categoriesMapper.updateSecondCategoriesById(catId,catName);
    }
}
