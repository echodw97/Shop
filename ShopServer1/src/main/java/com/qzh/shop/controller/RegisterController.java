package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.LoginService;
import com.qzh.shop.service.RegisterService;
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
 * @Date 2020/6/8 18:47
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class RegisterController {

    @Resource
    RegisterService registerService;
    @Resource
    LoginService loginService;

    @GetMapping("/register/{username}/{password}/{mobile}/{email}")
    public void register(HttpServletResponse response, @PathVariable String username, @PathVariable String password, @PathVariable String mobile, @PathVariable String email) throws IOException {

       if ((loginService.login(username,password)).getId()<0) {
           int flag = registerService.register(username, password, mobile, email);
           if (flag > 0) {
               Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
               JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
               jsonObject.put("data", null);
               Map<String, Object> map1 = new HashMap<>();
               map1.put("status", 200);
               map1.put("mgs", "注册成功");
               jsonObject.put("meta", map1);
               response.setContentType("text/html;charset=utf-8");
               response.getWriter().write(jsonObject.toJSONString());
           }
       }else {
           Result<Map<String, Object>, Map<String, Object>> result = new Result<>();
           JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
           jsonObject.put("data", null);
           Map<String, Object> map1 = new HashMap<>();
           map1.put("status", 404);
           map1.put("mgs", "注册失败,用户名已存在");
           jsonObject.put("meta", map1);
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().write(jsonObject.toJSONString());
       }
    }
}
