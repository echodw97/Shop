package com.qzh.shop.service;

import com.qzh.shop.POJO.DO.FirstMenu;

import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/18 9:15
 * @Version 1.0
 */
public interface MenuService {

    /**
     * 获取所有的左侧菜单数据
     * @return List<OneMenu>
     * */
    public List<FirstMenu> getAllMenu();

    /**
     * 获取部分的左侧菜单数据
     * @return List<OneMenu>
     * */
    public List<FirstMenu> getSectionMenu();
}
