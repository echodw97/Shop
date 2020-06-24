package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.qzh.shop.POJO.DO.FirstMenu;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/18 9:19
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class MenuController {
  private static final String TOKEN="6jQ4zVypmZG0s7wIYqzDfkvwofqgYlW3BrxFs9LRTlTGZkrmDSg0VyBo9aC22Dg4";
    @Resource
    private MenuService menuService;

    @GetMapping("/menus/{token}")
    public void getAllMenu(HttpServletResponse response, @PathVariable String token) throws IOException {
         if (token.equals(TOKEN) ){
             List<FirstMenu> allMenu = menuService.getAllMenu();
             Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
             JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
             jsonObject.put("data",allMenu);
             Map<String,Object> map= new HashMap<>();
             map.put("status",200);
             map.put("mgs","查询成功");
             jsonObject.put("meta",map);
             response.setContentType("text/html;charset=utf-8");
             response.getWriter().write(jsonObject.toJSONString());
         }else {
             List<FirstMenu> sectionMenu = menuService.getSectionMenu();
             Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
             JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
             jsonObject.put("data",sectionMenu);
             Map<String,Object> map= new HashMap<>();
             map.put("status",200);
             map.put("mgs","查询成功");
             jsonObject.put("meta",map);
             response.setContentType("text/html;charset=utf-8");
             response.getWriter().write(jsonObject.toJSONString());
         }


    }
}
