package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Goods;
import com.zxh.entity.Secondarycate;
import com.zxh.service.GoodsService;
import com.zxh.service.business.SecondaryCateBusiness;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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
@RequestMapping("/goods")
public class GoodsController {
    private String path="C:\\Users\\hui\\Desktop\\20211223备份\\毕设\\项目\\goodsimg\\";

    @Resource
    GoodsService goodsService;

    @Resource
    SecondaryCateBusiness secondaryCateBusiness;
    @ResponseBody
    @PostMapping("addone")
    public int addone(Goods goods, @RequestParam("gimgpath") MultipartFile gimgpath) throws IOException {
        File file=new File(path);
        if (!file.exists()){
            file.setWritable(true);
            file.mkdirs();
        }

        goods.setGoodsNumber(0);
        if(gimgpath!=null){
            String uuid= UUID.randomUUID().toString().substring(1,10);
            String filename=uuid+"_snack.jpg";
            gimgpath.transferTo(new File(path,filename));
            goods.setGoodsPic("/goodsimg/"+filename);
        }
        return goodsService.save(goods)? RecordStateEnum.Success.getCode() :RecordStateEnum.Success.getCode();
    }
    @ResponseBody
    @GetMapping("/page")
    public Map<String ,Object> page(Integer start, Integer pagesize,
                                    @RequestParam(value = "name" ,required = false)String name,
                                    @RequestParam(value = "status" ,required = false)String status,
                                    @RequestParam(value = "id" ,required = false) String id){
        HashMap<String, Object> map = new HashMap<>();
        Page<Goods> page=new Page<>(start,pagesize);
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        if (!Strings.isEmpty(name)){
            wrapper.like(Goods::getGoodsName,name);
        }
        if(!Strings.isEmpty(status)){
            wrapper.eq(Goods::getGoodsStatus,status);
        }
        if (!Strings.isEmpty(id)){
            wrapper.eq(Goods::getGoodsId,id);
        }
        goodsService.page(page,wrapper);
        ArrayList<Secondarycate> secandarycatelist = new ArrayList<>();
        for (Goods record : page.getRecords()) {
            secandarycatelist.add(secondaryCateBusiness.getlistbyid(record.getScId()));
        }
        map.put("goodslist",page);
        map.put("secandarycatelist",secandarycatelist);
        return map;
    }
    @ResponseBody
    @GetMapping("deleteone")
    public int deleteone(String goodsId){
        return goodsService.removeById(goodsId)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @PostMapping("/updateone")
    public int updateone(Goods goods,MultipartFile image) throws IOException {
        if(image!=null){
            String uuid= UUID.randomUUID().toString().substring(1,10);
            String filename=uuid+"__snack.jpg";
            goods.setGoodsPic("/goodsimg/"+filename);
            image.transferTo(new File(path,filename));
        }

        return goodsService.updateById(goods)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @GetMapping("/details")
    public String details(String goodsid, HttpSession session){
        Goods goods1 = goodsService.getById(goodsid);
        if(Objects.isNull(goods1)){
            return "user/usermain.html";
        }
        Goods goods = goodsService.getById(goodsid);
        session.setAttribute("goods",goods);
        return "/user/goodsdetails";
    }
    @GetMapping("/batchdelete")
    public int delete(String[] ids){
        return goodsService.removeByIds(Arrays.asList(ids))?RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @PostMapping("/testupdateone")
    public int testupdateone(MultipartFile image) throws IOException {
        long size = image.getSize();
        return Integer.parseInt(size+"");
    }

}

