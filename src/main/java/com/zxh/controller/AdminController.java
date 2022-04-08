package com.zxh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.Admin;
import com.zxh.entity.Goods;
import com.zxh.service.AdminService;
import com.zxh.service.GoodsService;
import com.zxh.service.business.AdminBusiness;
import com.zxh.utils.VerifyCodeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @Resource
    GoodsService goodsService;

    @Resource
    AdminBusiness adminBusiness;


    @GetMapping("/getadmin")
    public Admin login(String id){
        return adminService.getById(id);
    }
    @GetMapping("/adminlogin")
    public String adminlogin(HttpSession session){
        session.removeAttribute("loginuser");
        return "adminlogin";
    }
    @ResponseBody
    @GetMapping("checkadmin")
    public int checkadmin(String username,String password,String code,HttpSession session){
        String realCode=(String)session.getAttribute("code");
        return adminBusiness.checkadmin(username,password,code,realCode,session);
    }

    @GetMapping("adminmain.html")
    public String main(){
        return "/admin/adminmain.html";
    }

    @ResponseBody
    @GetMapping("register")
    public int register(String username,String password){
        return adminBusiness.register(username,password);
    }


    @RequestMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("code",code);
        ServletOutputStream os=response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(150,35,os,code);
    }
}

