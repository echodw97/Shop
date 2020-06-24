package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Commodity;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.CommodityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/16 18:17
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class CommodityController {

    @Resource
    CommodityService commodityService;


    @GetMapping("/categories/commodity/{catId}/{page}/{size}")
    public void getCommodityById(HttpServletResponse response, @PathVariable Integer catId, @PathVariable Integer page, @PathVariable Integer size) throws IOException {

            Page<Commodity> list = commodityService.getAllCommodityById(page,size,catId);
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", list);
            Map<String,Object> map= new HashMap<>();
            map.put("status",200);
            map.put("mgs","根据id获取对应分类的商品成功");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

    }

    @GetMapping("/commodity/{page}/{size}/{query}")
    public void getUserLikeList(HttpServletResponse response, @PathVariable String page, @PathVariable String size, @PathVariable String query) throws IOException {
        Integer page1 = Integer.parseInt(page);
        Integer size1 = Integer.parseInt(size);
        IPage<Commodity> ipage =  commodityService.getAllCommodity(page1,size1, query);
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

    @GetMapping("/addCommodity/{name}/{price}/{id}")
    public void getCommodityLike(HttpServletResponse response, @PathVariable String name, @PathVariable String price, @PathVariable Integer id) throws IOException {
        Integer page1 = Integer.parseInt(price);
        int i = commodityService.addCommodityById(name, page1, id);
        if (i > 0) {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",201);
            map.put("mgs","根据id添加商品成功");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",400);
            map.put("mgs","根据id添加商品失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @DeleteMapping("/commodity/{id}")
    public void deleteCommodityById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        int i = commodityService.deleteCommodityById(id);
        if (i > 0){
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",200);
            map.put("mgs","根据id删除商品失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",400);
            map.put("mgs","根据id删除商品成功");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @PostMapping("/show/commodity/{id}")
    public void getCommodityById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        Commodity commodity = commodityService.getCommodityById(id);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", commodity);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","根据id查询商品成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @PostMapping("/commodity/{price}/{id}")
    public void updateCommodityById(HttpServletResponse response, @PathVariable String price, @PathVariable Integer id) throws IOException {
        Integer integer = Integer.parseInt(price);
        int i = commodityService.updateCommodityById(integer, id);
        if (i > 0){
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",201);
            map.put("mgs","根据id修改商品成功");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",400);
            map.put("mgs","根据id修改商品失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }

    }

}
