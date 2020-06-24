package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.FirstCategories;
import com.qzh.shop.POJO.DO.SecondCategories;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.CategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 19:17
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class CategoriesController {

    @Resource
    private CategoriesService categoriesService;

    @GetMapping("/categories/{page}/{size}")
    public void getAllCategoriesList(HttpServletResponse response,  @PathVariable Integer page, @PathVariable Integer size) throws IOException {

        FirstCategories firstCategories = new FirstCategories();
        Page<FirstCategories> ipage = categoriesService.getAllCategories(page,size, firstCategories);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", ipage);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","所有商品获取成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @GetMapping("/categories/{page}/{size}/{query}")
    public void getAllCategoriesLikeList(HttpServletResponse response, @PathVariable Integer page, @PathVariable Integer size, @PathVariable String query) throws IOException {
        FirstCategories firstCategories = new FirstCategories();
        Page<FirstCategories> ipage = categoriesService.getAllCategoriesLike(page,size, firstCategories,query);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", ipage);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","获取匹配商品获取成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @GetMapping("/categories/parent")
    public void getAllCategoriesParents(HttpServletResponse response) throws IOException {
      List<FirstCategories> goods =  categoriesService.getAllCategoriesParent();
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", goods);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","获取父级分类商品成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @PostMapping("/addcategories/{catName}/{catPid}/{catLevel}")
    public void addCategories(HttpServletResponse response, @PathVariable String catName, @PathVariable Integer catPid, @PathVariable Integer catLevel) throws IOException {
       int flag = categoriesService.addCategories(catName,catPid,catLevel);
       if (flag>0) {
           Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
           JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
           jsonObject.put("data", null);
           Map<String, Object> map = new HashMap<>();
           map.put("status", 201);
           map.put("mgs", "添加商品分类名称成功");
           jsonObject.put("meta", map);
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().write(jsonObject.toJSONString());
       }else {
           Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
           JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
           jsonObject.put("data", null);
           Map<String, Object> map = new HashMap<>();
           map.put("status", 404);
           map.put("mgs", "添加商品分类名称失败");
           jsonObject.put("meta", map);
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().write(jsonObject.toJSONString());
       }
    }

    @DeleteMapping("/categories/{catId}/{catLevel}")
    public void deleteCategoriesById(HttpServletResponse response, @PathVariable Integer catId, @PathVariable Integer catLevel) throws IOException {
       int flag = categoriesService.deleteCategoriesById(catId,catLevel);
        if(flag>0) {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "删除商品分类名称成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 404);
            map.put("mgs", "删除商品分类名称失败");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @PostMapping("/categories/{catId}/{catLevel}")
    public void getCategoriesById(HttpServletResponse response, @PathVariable Integer catId, @PathVariable Integer catLevel) throws IOException {
        if (catLevel.equals(0)){
          FirstCategories firstCategories =  categoriesService.getFirstCategories(catId);
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", firstCategories);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "获取第一级商品分类名称成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            SecondCategories secondCategories =  categoriesService.getSecondCategories(catId);
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", secondCategories);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "获取第二级商品分类名称成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @PostMapping("/categories/{catId}/{catName}/{catLevel}")
    public void updateCategoriesById(HttpServletResponse response, @PathVariable Integer catId, @PathVariable String catName, @PathVariable Integer catLevel) throws IOException {
         if (catLevel.equals(0)){
             int i = categoriesService.updateFirstCategories(catId, catName);
             if (i>0){
                 Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
                 JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                 jsonObject.put("data",null);
                 Map<String, Object> map = new HashMap<>();
                 map.put("status", 201);
                 map.put("mgs", "更新第一级商品分类名称成功");
                 jsonObject.put("meta", map);
                 response.setContentType("text/html;charset=utf-8");
                 response.getWriter().write(jsonObject.toJSONString());
             }else {
                 Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
                 JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                 jsonObject.put("data",null);
                 Map<String, Object> map = new HashMap<>();
                 map.put("status", 404);
                 map.put("mgs", "更新第一级商品分类名称失败");
                 jsonObject.put("meta", map);
                 response.setContentType("text/html;charset=utf-8");
                 response.getWriter().write(jsonObject.toJSONString());
             }
         }else {
             int i = categoriesService.updateSecondCategories(catId, catName);
             if (i>0){
                 Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
                 JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                 jsonObject.put("data",null);
                 Map<String, Object> map = new HashMap<>();
                 map.put("status", 201);
                 map.put("mgs", "更新第二级商品分类名称成功");
                 jsonObject.put("meta", map);
                 response.setContentType("text/html;charset=utf-8");
                 response.getWriter().write(jsonObject.toJSONString());
             }else {
                 Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
                 JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                 jsonObject.put("data",null);
                 Map<String, Object> map = new HashMap<>();
                 map.put("status", 404);
                 map.put("mgs", "更新第二级商品分类名称失败");
                 jsonObject.put("meta", map);
                 response.setContentType("text/html;charset=utf-8");
                 response.getWriter().write(jsonObject.toJSONString());
             }
         }
    }
}
