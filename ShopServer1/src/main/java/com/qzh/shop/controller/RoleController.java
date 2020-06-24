package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/10 12:36
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/role/list/{page}/{size}")
    public void getRoleList(HttpServletResponse response, @PathVariable String page, @PathVariable String size) throws IOException {
        Integer page1 = Integer.parseInt(page);
        Integer size1 = Integer.parseInt(size);
        IPage<Users> ipage =  roleService.getAllRole(page1,size1);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",ipage);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @GetMapping("/role/{page}/{size}/{query}")
    public void getRoleLikeList(HttpServletResponse response, @PathVariable String page, @PathVariable String size, @PathVariable String query) throws IOException {
        Integer page1 = Integer.parseInt(page);
        Integer size1 = Integer.parseInt(size);
        IPage<Users> ipage =  roleService.getAll(page1,size1, query);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",ipage);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

}
