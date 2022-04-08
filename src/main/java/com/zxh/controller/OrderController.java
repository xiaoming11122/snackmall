package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Goods;
import com.zxh.entity.Orders;
import com.zxh.service.GoodsService;
import com.zxh.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderService orderservice;
    @Resource
    GoodsService goodsService;

    @ResponseBody
    @GetMapping("addone")
    public Integer addone(String goodsId,String userId,Float price,Integer number){
        Orders order = new Orders();
        order.setPrice(price*number);
        order.setGoodsId(Long.parseLong(goodsId));
        order.setUserId(Long.parseLong(userId));
        order.setNumber(
                number);
        return orderservice.save(order)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @GetMapping("deleteone")
    public Integer deleteone(String orderid){
        return orderservice.removeById(Long.parseLong(orderid))? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @GetMapping("/page")
    public Map<String ,Object> page(Integer start, Integer pagesize,String userid){
        HashMap<String, Object> map = new HashMap<>();
        Page<Orders> page=new Page<>(start,pagesize);
        ArrayList<Goods> goodsList = new ArrayList<>();
        orderservice.page(page,new LambdaQueryWrapper<Orders>().eq(Orders::getOrderStatus,0).eq(Orders::getUserId,Long.parseLong(userid)));
        for (int i = 0; i < page.getRecords().size(); i++) {
            Goods goods = goodsService.getById(page.getRecords().get(i).getGoodsId());
            goodsList.add(goods);
        }
        map.put("orderlist",page);
        map.put("goodslist",goodsList);
        return map;
    }

}

