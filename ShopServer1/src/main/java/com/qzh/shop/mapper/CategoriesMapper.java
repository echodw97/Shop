package com.qzh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.FirstCategories;
import com.qzh.shop.POJO.DO.SecondCategories;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 18:08
 * @Version 1.0
 */
@Mapper
public interface CategoriesMapper extends BaseMapper<FirstCategories> {

    /**
     * 查询所有的商品信息
     * @param page
     * @param firstCategories
     * @return FirstCategories
     * */
    public List<FirstCategories> getAllCategories(Page<FirstCategories> page, @Param("firstCategories") FirstCategories firstCategories);

    /**
     * 查询模糊匹配的商品信息
     * @param page
     * @param firstCategories
     * @return FirstCategories
     * */
    public List<FirstCategories> getAllCategoriesLike(Page<FirstCategories> page, @Param("firstCategories") FirstCategories firstCategories,String query);


    /**
     * 查询前一级的商品信息
     * @return Goods
     * */
    public List<FirstCategories> getAllCategoriesParent();

    /**
     * 添加第一级商品分类信息
     * @param catName
     * @param  catPid
     * */
    public int addFirstCategoriesName(@Param("catName") String catName, @Param("catPid") Integer catPid);

    /**
     * 添加第二级商品分类信息
     * @param catName
     * @param  catPid
     * */
    public int addSecondCategoriesName(@Param("catName") String catName, @Param("catPid") Integer catPid);

    /**
     * 根据ID删除第一级商品分类信息
     * @param catId
     * */
    public int deleteFirstCategoriesById(@Param("catId") Integer catId);


    /**
     * 根据ID删除第二级商品分类信息
     * @param catId
     * */
    public int deleteSecondCategoriesById(@Param("catId") Integer catId);

    /**
     * 根据ID拿到第一级商品分类信息
     * @param catId
     * @return FirstCategories
     * */
    public FirstCategories getFirstCategoriesById(@Param("catId") Integer catId);

    /**
     * 根据ID拿到第二级商品分类信息
     * @param catId
     * @return SecondCategories
     * */
    public SecondCategories getSecondCategoriesById(@Param("catId") Integer catId);

    /**
     * 根据ID更新第一级商品分类信息
     * @param catId
     * @return int
     * */
    public int updateFirstCategoriesById(@Param("catId") Integer catId, @Param("catName") String catName);

    /**
     * 根据ID更新第二级商品分类信息
     * @param catId
     * @return int
     * */
    public int updateSecondCategoriesById(@Param("catId") Integer catId, @Param("catName") String catName);
}
