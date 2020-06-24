package com.qzh.shop.mapper;

import com.qzh.shop.POJO.DO.FirstMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/18 8:50
 * @Version 1.0
 */
@Mapper
public interface MenuMapper {

    /**
     * 查询所有的左侧菜单
     * @return OneMenu
     * */
    public List<FirstMenu> getAllMenu();

    /**
     * 查询部分的左侧菜单
     * @return OneMenu
     * */
    public List<FirstMenu> getSectionMenu();

}
