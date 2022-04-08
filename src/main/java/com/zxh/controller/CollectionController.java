package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Collection;
import com.zxh.entity.Goods;
import com.zxh.entity.User;
import com.zxh.service.CollectionService;
import com.zxh.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@Controller
@RequestMapping("/collection")
public class CollectionController {
    @Resource
    CollectionService collectionService;
    @Resource
    GoodsService goodsService;

    @ResponseBody
    @GetMapping("addone")
    public int addone(String goodsid, HttpServletRequest request){
        Collection list = collectionService.getOne(new LambdaQueryWrapper<Collection>().eq(Collection::getGoodsId, goodsid));
        if(list!=null){
            return 2;
        }
        HttpSession session = request.getSession();
        User loginuser = (User)session.getAttribute("loginuser2");
        Collection collection=new Collection();
        collection.setGoodsId(Long.parseLong(goodsid));
        collection.setUserId(loginuser.getUserId());
        return collectionService.save(collection)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @GetMapping("deleteone")
    public int deleteone(String collectionid){
        return collectionService.removeById(collectionid)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @GetMapping("/page")
    public Map<String,Object> page(Integer start,Integer pagesize,String userid){
        HashMap<String, Object> map = new HashMap<>();
        Page<Collection> collectionlist=new Page<>(start,pagesize);
        collectionService.page(collectionlist,new LambdaQueryWrapper<Collection>().eq(Collection::getUserId,userid));
        ArrayList<Goods> goodslist = new ArrayList<>();
        for (Integer i = 0; i < collectionlist.getRecords().size(); i++) {
            Goods goods = goodsService.getById(collectionlist.getRecords().get(i));
            goodslist.add(goods);
        }
        map.put("collectionlist",collectionlist);
        map.put("goodslist",goodslist);
        return map;
    }

}

