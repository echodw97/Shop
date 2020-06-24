package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.qzh.shop.POJO.DO.Attribute;
import com.qzh.shop.POJO.DO.FirstCategories;
import com.qzh.shop.POJO.DO.Parameter;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.ParameterService;
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
 * @Date 2020/6/13 23:51
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class ParameterController {

    @Resource
    ParameterService parameterService;

    @GetMapping("/categories/list")
    public void getAllCategories(HttpServletResponse response) throws IOException {
        FirstCategories firstCategories = new FirstCategories();
        List<FirstCategories> list = parameterService.getAllCategories(firstCategories);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", list);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","所有商品分类获取成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @GetMapping("/categories/attributes/{activeName}/{catId}")
    public void getAttribute(HttpServletResponse response, @PathVariable("activeName") String activeName, @PathVariable Integer catId) throws IOException {
         if (activeName.equals("many")){
            List<Parameter> list = parameterService.getAllParameterById(catId);
             Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
             JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
             jsonObject.put("data", list);
             Map<String,Object> map= new HashMap<>();
             map.put("status",200);
             map.put("mgs","根据id获取所有的动态参数成功");
             jsonObject.put("meta",map);
             response.setContentType("text/html;charset=utf-8");
             response.getWriter().write(jsonObject.toJSONString());
         }else {
             List<Attribute> list = parameterService.getAllAttributeById(catId);
             Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
             JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
             jsonObject.put("data", list);
             Map<String,Object> map= new HashMap<>();
             map.put("status",200);
             map.put("mgs","根据id获取所有的静态属性成功");
             jsonObject.put("meta",map);
             response.setContentType("text/html;charset=utf-8");
             response.getWriter().write(jsonObject.toJSONString());
         }
    }

    @PostMapping("/categories/parameter/{catId}/{parameter}")
    public void addParameterByPid(HttpServletResponse response, @PathVariable Integer catId, @PathVariable String parameter) throws IOException {
      int flag = parameterService.addParameterByPid(catId,parameter);
      if (flag > 0) {
          Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
          JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
          jsonObject.put("data", null);
          Map<String, Object> map = new HashMap<>();
          map.put("status", 201);
          map.put("mgs", "根据pId添加动态参数成功");
          jsonObject.put("meta", map);
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().write(jsonObject.toJSONString());
      }else {
          Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
          JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
          jsonObject.put("data", null);
          Map<String, Object> map = new HashMap<>();
          map.put("status", 400);
          map.put("mgs", "根据pId添加动态参数失败");
          jsonObject.put("meta", map);
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().write(jsonObject.toJSONString());
      }
    }


    @PostMapping("/categories/attribute/{catId}/{attribute}")
    public void addAttributeByPid(HttpServletResponse response, @PathVariable Integer catId, @PathVariable String attribute) throws IOException {
        int flag = parameterService.addAttrbuteByPid(catId,attribute);
        if (flag > 0) {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 201);
            map.put("mgs", "根据pId添加静态属性成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 400);
            map.put("mgs", "根据pId添加静态属性失败");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @GetMapping("/categories/parameter/{id}")
    public void getParameterById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
       Parameter parameter = parameterService.getParameterById(id);
        Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", parameter);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("mgs", "根据id查询对应的动态参数成功");
        jsonObject.put("meta", map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @GetMapping("/categories/attribute/{id}")
    public void getAttributeById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        Attribute attribute = parameterService.getAttributeById(id);
        Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data", attribute);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("mgs", "根据id查询对应的静态属性成功");
        jsonObject.put("meta", map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @GetMapping("/categories/{id}/parameter/{parameter}")
    public void updataParameterById(HttpServletResponse response, @PathVariable Integer id, @PathVariable String parameter) throws IOException {

        int flag = parameterService.updataParameterById(id,parameter);
        if (flag >0) {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "根据id修改对应的动态参数成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 400);
            map.put("mgs", "根据id修改对应的动态参数失败");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @GetMapping("/categories/{id}/attribute/{attribute}")
    public void updataAttributeById(HttpServletResponse response, @PathVariable Integer id, @PathVariable String attribute) throws IOException {

        int flag = parameterService.updataAttributeById(id,attribute);
        if (flag >0) {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "根据id修改对应的静态属性成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 400);
            map.put("mgs", "根据id修改对应的静态属性失败");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @DeleteMapping("/categories/parameter/{id}")
    public void deleteParameterById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        int i = parameterService.deleteParameterById(id);
        if (i>0){
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "根据id删除对应的动态参数成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 400);
            map.put("mgs", "根据id删除对应的动态参数失败");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @DeleteMapping("/categories/attribute/{id}")
    public void deleteAttributeById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        int i = parameterService.deleteAttributeById(id);
        if (i>0){
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("mgs", "根据id删除对应的静态属性成功");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data", null);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 400);
            map.put("mgs", "根据id删除对应的静态属性失败");
            jsonObject.put("meta", map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @GetMapping("/parameter/optional/{id}")
    public void getParameterOptionalById(HttpServletResponse response, @PathVariable Integer id){
        System.out.println(id);
    }
}
