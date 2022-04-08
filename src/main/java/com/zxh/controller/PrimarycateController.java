package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.Goods;
import com.zxh.entity.Primarycate;
import com.zxh.entity.Recommend;
import com.zxh.entity.Secondarycate;
import com.zxh.service.GoodsService;
import com.zxh.service.SecondarycateService;
import com.zxh.service.business.SecondaryCateBusiness;
import com.zxh.service.impl.PrimarycateServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/primarycate")
public class PrimarycateController {
    @Resource
    PrimarycateServiceImpl primarycateService;
    @Resource
    SecondarycateService secondarycateService;

    @Resource
    SecondaryCateBusiness secondaryCateBusiness;

    @Resource
    GoodsService goodsService;

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
                                             @RequestParam(value = "scid",required = false) String scid){
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
        map.put("primarycateList",primarycateList);
        map.put("secondarycateList",secondarycateList);
        map.put("goodslist",page);
        return map;
    }


}

