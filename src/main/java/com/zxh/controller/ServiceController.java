package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Goods;
import com.zxh.entity.Orders;
import com.zxh.entity.Service;
import com.zxh.entity.User;
import com.zxh.service.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Resource
    ServiceService serviceService;
    @Resource
    UserService userService;

    @Resource
    CollectionService collectionService;
    @Resource
    GoodsService goodsService;

    @Resource
    OrderService orderService;
    @ResponseBody
    @GetMapping("addone")
    public Integer addone(@RequestParam(required = false,value = "orderid") String orderid, String goodsid, String price, String number, HttpServletRequest request){
        HttpSession session = request.getSession();
        User loginuser = (User)session.getAttribute("loginuser2");
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserPhone,loginuser.getUserPhone()));
        if (user.getUserBalance()<Float.parseFloat(price)){
            return 2;
        }
        Goods goods = goodsService.getById(Long.parseLong(goodsid));
        if(goods.getGoodsStock()<Long.parseLong(number)){
            return 3;
        }
        if(goods.getGoodsStatus()==0){
            return 4;
        }
        goods.setGoodsNumber(goods.getGoodsNumber()+Integer.parseInt(number));
        goods.setGoodsStock(goods.getGoodsStock()-Integer.parseInt(number));
        goodsService.updateById(goods);
        user.setUserBalance(user.getUserBalance()-Float.parseFloat(price));
        Orders order=null;
        if(!Strings.isEmpty(orderid)){
            order= orderService.getById(orderid);
            order.setOrderStatus(1);
            orderService.updateById(order);
        }else {
            order = new Orders();
            order.setPrice(Float.parseFloat(price));
            order.setGoodsId(Long.parseLong(goodsid));
            order.setUserId(user.getUserId());
            order.setNumber(Integer.parseInt(number));
            order.setOrderStatus(1);
            orderService.save(order);
        }
        userService.updateById(user);
        Service service=new Service();
        service.setGoodsId(Long.parseLong(goodsid));
        if (Strings.isNotEmpty(orderid)) {
            service.setOrderId(Long.parseLong(orderid));
        } else {
            service.setOrderId(order.getOrderId());
        }
        service.setServiceNumber(Integer.parseInt(number));
        service.setServicePrice(Float.parseFloat(price));
        service.setUserId(loginuser.getUserId());
        return serviceService.save(service)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @GetMapping("/page")
    public Map<String ,Object> page(Integer start, Integer pagesize,@RequestParam(required = false,value = "userid")String userid,
                                    @RequestParam(required = false,value = "servicecate")String servicecate,
                                    @RequestParam(required = false,value = "servicestatus")String servicestatus,
                                    @RequestParam(required = false,value = "orderstatus")String orderstatus){
        Map<String, Object> map = new HashMap<>();
        Page<Service> page=new Page<>(start,pagesize);
        LambdaQueryWrapper<Service> serviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(Strings.isNotEmpty(userid)){
            serviceLambdaQueryWrapper.eq(Service::getUserId,userid);
        }
        if(Strings.isNotEmpty(servicecate)){
            serviceLambdaQueryWrapper.eq(Service::getServiceCage,servicecate);
        }
        if(Strings.isNotEmpty(servicestatus)){
            serviceLambdaQueryWrapper.eq(Service::getServiceStatus,servicestatus);
        }
        if(Strings.isNotEmpty(orderstatus)){
            serviceLambdaQueryWrapper.eq(Service::getOrderStatus,orderstatus);
        }
        serviceService.page(page,serviceLambdaQueryWrapper);
        List<Goods> goodslist=new ArrayList<>();
        page.getRecords().stream().map(e -> e.getGoodsId()).forEach(x->{
           goodslist.add(goodsService.getById(x));
        });
        map.put("servicelist",page);
        map.put("goodslist",goodslist);
        return map;
    }
    @ResponseBody
    @GetMapping("refund")
    public Integer refund(String serviceid){
        Service service = serviceService.getById(Long.parseLong(serviceid));
        if (Objects.isNull(service)){
            return 2;
        }
        service.setServiceCage(1);
        service.setServiceStatus(1);
        return serviceService.updateById(service) ?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @GetMapping("cancelrefund")
    public Integer cancelrefund(String serviceid){
        Service service = serviceService.getById(Long.parseLong(serviceid));
        if (Objects.isNull(service)){
            return 2;
        }
        service.setServiceCage(0);
        service.setServiceStatus(0);
        return serviceService.updateById(service) ?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @GetMapping("deleteone")
    public Integer deleteone(String serviceid){
        Service service = serviceService.getById(Long.parseLong(serviceid));
        if (Objects.isNull(service)){
            return 2;
        }
        if(service.getOrderStatus()!=1){
            return RecordStateEnum.Fail.getCode();
        }
        return serviceService.removeById(serviceid)?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @GetMapping("Refuse")
    public Integer Refuse(String serviceid){
        Service service = serviceService.getById(Long.parseLong(serviceid));
        if (Objects.isNull(service)){
            return 2;
        }
        service.setOrderStatus(1);
        service.setServiceCage(1);
        service.setServiceStatus(4);
        return serviceService.updateById(service) ?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @GetMapping("completeservice")
    public Integer completeservice(String serviceid){
        Service service = serviceService.getById(Long.parseLong(serviceid));
        if (Objects.isNull(service)){
            return 2;
        }
        service.setOrderStatus(1);
        service.setServiceCage(0);
        service.setServiceStatus(0);
        return serviceService.updateById(service) ?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @GetMapping("consent")
    public Integer consent(String serviceid,String userid){
        Service service = serviceService.getById(Long.parseLong(serviceid));
        if (Objects.isNull(service)){
            return 2;
        }
        User user = userService.getById(userid);
        user.setUserBalance(user.getUserBalance()+service.getServicePrice());
        userService.updateById(user);
        service.setOrderStatus(1);
        service.setServiceCage(1);
        service.setServiceStatus(3);
        return serviceService.updateById(service) ?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
}

