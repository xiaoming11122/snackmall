package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.*;
import com.zxh.mapper.CollectionMapper;
import com.zxh.service.CollectionService;
import com.zxh.service.GoodsService;
import com.zxh.service.SecondarycateService;
import com.zxh.service.UserService;
import com.zxh.service.business.SecondaryCateBusiness;
import com.zxh.service.impl.PrimarycateServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/primarycate")
public class PrimarycateController {
    @Resource
    PrimarycateServiceImpl primarycateService;
    @Resource
    SecondarycateService secondarycateService;

    @Resource
    CollectionService collectionService;

    @Resource
    SecondaryCateBusiness secondaryCateBusiness;
    @Resource
    CollectionMapper collectionMapper;
    @Resource
    GoodsService goodsService;

    @Resource
    UserService userService;

    @ResponseBody
    @GetMapping("getAll")
    public List<Primarycate> getAll(){
        return primarycateService.list();
    }

    @ResponseBody
    @GetMapping("getMap")
    public Map<String ,Object> getMap(String pcid){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Primarycate> primarycateList = primarycateService.list();
        List<Secondarycate> secondarycateList = secondarycateService.list(new LambdaQueryWrapper<Secondarycate>().eq(Secondarycate::getPcId, pcid));
        map.put("secondarycateList",secondarycateList);
        map.put("primarycateList",primarycateList);
        return map;
    }

    @ResponseBody
    @GetMapping("showgoodspanel")
    public Map<String,Object>  getAllpcandsc(Integer start, Integer pagesize,
                                             @RequestParam(value = "name",required = false) String name,
                                             @RequestParam(value = "scid",required = false) String scid,
                                             HttpServletRequest request){
        HttpSession session = request.getSession();
        User loginuser = (User)session.getAttribute("loginuser2");
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Primarycate> primarycateList = primarycateService.list();
        List<Secondarycate> secondarycateList = secondarycateService.list();
        Page<Goods> page=new Page<>(start,pagesize);
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        if (!Strings.isEmpty(name)){
            wrapper.like(Goods::getGoodsName,name);
        }
        if(!Strings.isEmpty(scid)){
            wrapper.eq(Goods::getScId,scid);
        }
        goodsService.page(page,wrapper.eq(Goods::getGoodsStatus,1));
        List<Long> goodsids=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            goodsids.add(page.getRecords().get(i).getGoodsId());
        }
        List<Numberation> collectionlist=new ArrayList<>();
        if(goodsids.size()!=0){
            LambdaQueryWrapper<Collection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            collectionLambdaQueryWrapper.in(Collection::getGoodsId,goodsids);
            List<Collection> collections=collectionService.list(collectionLambdaQueryWrapper);
            Map<Long, List<Collection>> collect = collections.stream().collect(Collectors.groupingBy(t -> t.getGoodsId()));
            for (int i = 0; i < page.getRecords().size(); i++) {
                Numberation numberation=new Numberation();
                List<Collection> list = collect.get(page.getRecords().get(i).getGoodsId());
                numberation.setId(page.getRecords().get(i).getGoodsId());
                if(list!=null){
                    numberation.setSum(list.size());
                }else {
                    numberation.setSum(0);
                }
                collectionlist.add(numberation);
            }
        }
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserPhone, loginuser.getUserPhone()));
        List<Collection> usercollectionlist = collectionService.list(new LambdaQueryWrapper<Collection>().eq(Collection::getUserId, user.getUserId()));
        map.put("usercollectionlist",usercollectionlist);
        map.put("collectionlist",collectionlist);
        map.put("primarycateList",primarycateList);
        map.put("secondarycateList",secondarycateList);
        map.put("goodslist",page);
        return map;
    }


}

