package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.LoginService;
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
 * @Date 2020/5/13 16:15
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class LoginController {

    @Resource
    private LoginService loginService;

    @GetMapping("/login/{username}/{password}")
    public void login(HttpServletResponse response, @PathVariable String username, @PathVariable String password) throws IOException {

        Users login = loginService.login(username, password);

        if (login == null) {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            Map<String,Object> map3 = new HashMap<>();
            map3.put("status",404);
            map3.put("msg","账号或密码错误");
            jsonObject.put("data",null);
            jsonObject.put("meta",map3);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());

        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            Map<String,Object> map1= new HashMap<>();
            map1.put("id",login.getId());
            map1.put("username",login.getUsername());
            map1.put("password",login.getPassword());
            map1.put("email",login.getEmail());
            map1.put("mobile",login.getMobile());
            map1.put("token",login.getToken());
            jsonObject.put("data",map1);
            Map<String,Object> map2= new HashMap<>();
            map2.put("status",200);
            map2.put("mgs","登录成功");
            jsonObject.put("meta",map2);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }

    }
}
