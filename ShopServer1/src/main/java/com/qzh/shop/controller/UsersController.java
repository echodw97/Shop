package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/14 21:16
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class UsersController {

    @Resource
    private UsersService usersService;

    @GetMapping("/users/{page}/{size}")
    public void getUserList(HttpServletResponse response, @PathVariable String page, @PathVariable String size) throws IOException, ParseException {
        Integer page1 = Integer.parseInt(page);
        Integer size1 = Integer.parseInt(size);
        IPage<Users> ipage =  usersService.getAllUsers(page1,size1);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",ipage);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","获取用户列表成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }



    @GetMapping("/users/{page}/{size}/{query}")
    public void getUserLikeList(HttpServletResponse response, @PathVariable String page, @PathVariable String size, @PathVariable String query) throws IOException {
        Integer page1 = Integer.parseInt(page);
        Integer size1 = Integer.parseInt(size);
        IPage<Users> ipage =  usersService.getAll(page1,size1, query);
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


    @PostMapping("/users/{id}")
    public void getUsersById(HttpServletResponse response,@PathVariable String id) throws IOException {
        Integer integer = Integer.parseInt(id);
        Users users = usersService.getUsersById(integer);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",users);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","根据id查询用户信息成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @PostMapping("/users/{username}/{email}/{mobile}")
    public void updateUsers(HttpServletResponse response, @PathVariable String username, @PathVariable String email, @PathVariable String mobile) throws IOException {
       int i = usersService.updateUsers(username,email,mobile);

        if (i>0){
          Users users = usersService.getUsersByUsername(username);
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data",users);
            Map<String,Object> map= new HashMap<>();
            map.put("status",201);
            map.put("mgs","修改用户信息成功");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data",null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",404);
            map.put("mgs","修改用户信息失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }

    }

    @DeleteMapping("/users/{id}")
    public void deleteUsersById(HttpServletResponse response, @PathVariable String id) throws IOException {
        int integer = Integer.parseInt(id);
       Integer integer1 = usersService.deleteUsersById(integer);

       if (integer1>0){
           Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
           JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
           jsonObject.put("data",null);
           Map<String,Object> map= new HashMap<>();
           map.put("status",200);
           map.put("mgs","删除成功");
           jsonObject.put("meta",map);
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().write(jsonObject.toJSONString());
       }else {
           Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
           JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
           jsonObject.put("data",null);
           Map<String,Object> map= new HashMap<>();
           map.put("status",404);
           map.put("mgs","删除失败");
           jsonObject.put("meta",map);
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().write(jsonObject.toJSONString());
       }

    }

}
