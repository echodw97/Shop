package com.qzh.shop.service.impl;

import com.qzh.shop.POJO.DO.FirstMenu;
import com.qzh.shop.mapper.MenuMapper;
import com.qzh.shop.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/18 9:16
 * @Version 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;


    @Override
    public List<FirstMenu> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    @Override
    public List<FirstMenu> getSectionMenu() {
        return menuMapper.getSectionMenu();
    }
}
