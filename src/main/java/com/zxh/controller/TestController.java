package com.zxh.controller;

import com.zxh.config.AsyncTask;
import com.zxh.entity.Orders;
import com.zxh.entity.User;
import com.zxh.mapper.OrderMapper;
import com.zxh.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    AsyncTask asyncTask;

    @Resource
    UserService userService;

    @Resource
    OrderMapper orderMapper;


    @ResponseBody
    @GetMapping("/Transactional")
    @Transactional(rollbackFor = Exception.class)
    public Integer test(){
        try{
            userService.save(new User());
            orderMapper.addoneeturnid(new Orders());
        }catch (Exception e){
            System.out.println("事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return 1;
    }
    @ResponseBody
    @GetMapping("/Async")
    public String test2() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        asyncTask.task01();
        asyncTask.task02();
        asyncTask.task03();
        Thread.sleep(100);
        long currentTimeMillis1 = System.currentTimeMillis();
        return "task任务耗时" + (currentTimeMillis1 - currentTimeMillis);
    }
}
