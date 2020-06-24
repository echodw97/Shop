package com.qzh.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.FirstCategories;
import com.qzh.shop.POJO.DO.SecondCategories;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 19:14
 * @Version 1.0
 */
public interface CategoriesService {

    /**
     * 查询所有的商品分类
     * @param page 第几页
     * @param size 每页大小
     * @param firstCategories 商品分类
     * @return List<FirstCategories>
     * */
    public Page<FirstCategories> getAllCategories(@Param("page")Integer page, @Param("size")Integer size, @Param("firstCategories") FirstCategories firstCategories);

    /**
     * 模糊匹配查询所有的商品分类
     * @param page 第几页
     * @param size 每页大小
     * @param firstCategories 商品分类
     * @return List<FirstCategories>
     * */
    public Page<FirstCategories> getAllCategoriesLike(@Param("page")Integer page, @Param("size")Integer size, @Param("firstCategories") FirstCategories firstCategories,String query);


    /**
     * 查询前两级的父商品分类
     * @return FirstCategories 商品
     * */
    public List<FirstCategories> getAllCategoriesParent();

    /**
     * 添加商品分类
     * @param catName 分类名称
     * @param catPid 分类父id
     * @param catLevel 分类级别
     * @return int
     * */
    public int addCategories(@Param("catName") String catName, @Param("catPid") Integer catPid, @Param("catLevel") Integer catLevel);

    /**
     * 删除商品分类
     * @param catId 分类名称
     * @param catLevel 分类级别
     * @return int
     * */
    public int deleteCategoriesById(@Param("catId") Integer catId,@Param("catLevel") Integer catLevel);

    /**
     * 根据id查询第一级分类
     * @param catId
     * @return FirstCategories
    * */
    public FirstCategories getFirstCategories(@Param("catId") Integer catId);

    /**
     * 根据id查询第二级分类
     * @param catId
     * @return SecondCategories
     * */
    public SecondCategories getSecondCategories(@Param("catId") Integer catId);

    /**
     * 根据id更新第一级分类
     * @param catId
     * @param catName
     * @return FirstCategories
     * */
    public int updateFirstCategories(@Param("catId") Integer catId, @Param("catName") String catName);

    /**
     * 根据id更新第二级分类
     * @param catId
     * @param catName
     * @return SecondCategories
     * */
    public int updateSecondCategories(@Param("catId") Integer catId, @Param("catName") String catName);
}
