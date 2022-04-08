package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Goods;
import com.zxh.entity.Recommend;
import com.zxh.service.GoodsService;
import com.zxh.service.RecommendService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-04-02
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController {
    @Resource
    RecommendService recommendService;

    @Resource
    GoodsService goodsService;
    private String path="C:\\Users\\hui\\Desktop\\20211223备份\\毕设\\项目\\recommendimg\\";

    @ResponseBody
    @PostMapping("addone")
    public int addone(Recommend recommend, @RequestParam("rimgpath") MultipartFile rimgpath) throws IOException {
        Goods goods = goodsService.getById(recommend.getGoodsId());
        if (Objects.isNull(goods)){
            return 3;
        }
        File file=new File(path);
        if (!file.exists()){
            file.setWritable(true);
            file.mkdirs();
        }

        if(rimgpath!=null){
            String uuid= UUID.randomUUID().toString().substring(1,10);
            String filename=uuid+"_recommend.jpg";
            rimgpath.transferTo(new File(path,filename));
            recommend.setRecommendPic("/recommendimg/"+filename);
        }
        Recommend recommend1 = recommendService.getOne(new LambdaQueryWrapper<Recommend>().eq(Recommend::getGoodsId, recommend.getGoodsId()));
        if(!Objects.isNull(recommend1)){
            return 2;
        }
        return recommendService.save(recommend)? RecordStateEnum.Success.getCode() :RecordStateEnum.Success.getCode();
    }

    @ResponseBody
    @GetMapping("/page")
    public Page<Recommend> page(Integer start, Integer pagesize,
                                @RequestParam(value = "goodsid" ,required = false)String goodsid,
                                @RequestParam(value = "recommendleve" ,required = false)String recommendleve,
                                @RequestParam(value = "recommendstatus" ,required = false) String recommendstatus){
        Page<Recommend> page=new Page<>(start,pagesize);
        LambdaQueryWrapper<Recommend> wrapper=new LambdaQueryWrapper<>();
        if (!Strings.isEmpty(goodsid)){
            wrapper.eq(Recommend::getGoodsId,goodsid);
        }
        if(!Strings.isEmpty(recommendstatus)){
            wrapper.eq(Recommend::getRecommendStatus,recommendstatus);
        }
        if (!Strings.isEmpty(recommendleve)){
            wrapper.eq(Recommend::getRecommendLeve,recommendleve);
        }
        recommendService.page(page,wrapper);
        return page;
    }

    @ResponseBody
    @GetMapping("deleteone")
    public int deleteone(String recommendid){
        return recommendService.removeById(recommendid)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @PostMapping("/updateone")
    public int updateone(Recommend recommend, MultipartFile image) throws IOException {
        Goods goods = goodsService.getById(recommend.getGoodsId());
        if (Objects.isNull(goods)){
            return 3;
        }
        if(image!=null){
            String uuid= UUID.randomUUID().toString().substring(1,10);
            String filename=uuid+"___recommend.jpg";
            recommend.setRecommendPic("/recommendimg/"+filename);
            image.transferTo(new File(path,filename));
        }
        return recommendService.updateById(recommend)? RecordStateEnum.Success.getCode():RecordStateEnum.Fail.getCode();
    }
    @ResponseBody
    @GetMapping("getbystatus")
    public Map<String,Object> getbystatus(){
        Map map=new HashMap();
        List<Recommend> listbystatus3 = recommendService.list(new LambdaQueryWrapper<Recommend>()
                .eq(Recommend::getRecommendStatus, RecordStateEnum.Success.getCode())
                .eq(Recommend::getRecommendLeve,3));
        List<Recommend> listbystatus12 = recommendService.list(new LambdaQueryWrapper<Recommend>()
                .eq(Recommend::getRecommendStatus, RecordStateEnum.Success.getCode())
                .in(Recommend::getRecommendLeve,Arrays.asList(1,2)));
        map.put("recommend3",listbystatus3);
        map.put("recommend12",listbystatus12);
        return map;
    }

}

