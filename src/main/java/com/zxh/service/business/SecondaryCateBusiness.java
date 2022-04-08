package com.zxh.service.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxh.entity.Secondarycate;
import com.zxh.service.SecondarycateService;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SecondaryCateBusiness {

    @Resource
    private SecondarycateService secondarycateService;

    public List<Secondarycate> getlistbypcid(String pcId){
        return secondarycateService.list(new LambdaQueryWrapper<Secondarycate>().eq(Secondarycate::getPcId,pcId));
    }
    public Secondarycate getlistbyid(Long id){
        return secondarycateService.getById(id);
    }
}
