package com.qzh.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qzh.shop.POJO.DO.Commodity;
import com.qzh.shop.POJO.DO.Order;
import com.qzh.shop.POJO.VO.Result;
import com.qzh.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/17 14:05
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/api/private/v1")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("/order/{page}/{size}")
    public void getAllOrder(HttpServletResponse response, @PathVariable Integer page, @PathVariable Integer size) throws ParseException, IOException {
        IPage<Order> ipage =  orderService.getAllOrder(page,size);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",ipage);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","获取订单列表成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }


    @GetMapping("/order/{page}/{size}/{query}")
    public void getOrderLikeList(HttpServletResponse response, @PathVariable Integer page, @PathVariable Integer size, @PathVariable String query) throws IOException {
        IPage<Order> ipage =  orderService.getAllOrderLikeList(page,size, query);
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

    @DeleteMapping("/order/{id}")
    public void deleteOrderById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        int i = orderService.deleteOrderById(id);
        if (i>0){
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data",null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",200);
            map.put("mgs","根据id删除订单成功");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data",null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",400);
            map.put("mgs","根据id删除订单失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @GetMapping("/order/commodity")
    public void getAllCommodity(HttpServletResponse response) throws IOException {
        List<Commodity> allCommodity = orderService.getAllCommodity();
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",allCommodity);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","查询所有商品");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @PostMapping("/addorder/{id}")
    public void addOrder(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        int i = orderService.insertOrderByID(id);
        if (i > 0){
            int i1 = orderService.updateOrder();
            if (i1 > 0){
                Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                jsonObject.put("data",null);
                Map<String,Object> map= new HashMap<>();
                map.put("status",201);
                map.put("mgs","插入订单成功");
                jsonObject.put("meta",map);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(jsonObject.toJSONString());
            }
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data",null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",400);
            map.put("mgs","插入订单失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @PostMapping("/getorder/{id}")
    public void getOrderById(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        Order order = orderService.getOrderByID(id);
        Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        jsonObject.put("data",order);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("mgs","根据id查询订单信息成功");
        jsonObject.put("meta",map);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }

    @PostMapping("/updateorder/{pId}/{id}/{payment}/{ship}")
    public void updateOrderByID(HttpServletResponse response, @PathVariable Integer pId, @PathVariable Integer id, @PathVariable String payment, @PathVariable String ship) throws IOException {
        int i = orderService.updateOrderById(pId, id,payment,ship);
        if (i>0){
            int i1 = orderService.updateOrder();
                if(i1>0){
                    Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
                    jsonObject.put("data",null);
                    Map<String,Object> map= new HashMap<>();
                    map.put("status",201);
                    map.put("mgs","修改订单成功");
                    jsonObject.put("meta",map);
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write(jsonObject.toJSONString());
                }
        }else {
            Result<Map<String,Object>,Map<String,Object>> result = new Result<>();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            jsonObject.put("data",null);
            Map<String,Object> map= new HashMap<>();
            map.put("status",400);
            map.put("mgs","修改订单失败");
            jsonObject.put("meta",map);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(jsonObject.toJSONString());
        }
    }
}
