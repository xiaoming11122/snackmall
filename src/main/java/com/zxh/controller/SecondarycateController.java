package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxh.entity.Secondarycate;
import com.zxh.service.SecondarycateService;
import com.zxh.service.business.SecondaryCateBusiness;
import com.zxh.service.impl.SecondarycateServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/secondarycate")
public class SecondarycateController {
    @Resource
    private SecondarycateService secondarycateService;
    @Resource
    private SecondaryCateBusiness secondaryCateBusiness;
    @ResponseBody
    @GetMapping("getlistbypcid")
    public List<Secondarycate> getlistbypcid(String pcId){
        return secondaryCateBusiness.getlistbypcid(pcId);
    }

}

