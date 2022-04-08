package com.zxh.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zxh.constants.LoginStateEnum;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Admin;
import com.zxh.entity.User;
import com.zxh.service.UserService;
import com.zxh.utils.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/login.html")
    public String adminlogin(HttpSession session){
        session.removeAttribute("loginuser2");
        return "index";
    }

    @GetMapping("usermain.html")
    public String main(){
        return "/user/usermain.html";
    }
    @RequestMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("code2",code);
        ServletOutputStream os=response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(150,35,os,code);
    }
    @ResponseBody
    @GetMapping("register")
    public int register(String phone,String password,String name){
        if(!Objects.isNull(userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, phone)))){
            return 2;
        }
        User user=new User();
        user.setUserPhone(phone);
        user.setUserName(name);
        user.setUserPassword(password);
        return userService.save(user)? RecordStateEnum.Success.getCode() : RecordStateEnum.Fail.getCode();
    }

    @ResponseBody
    @GetMapping("checkuser")
    public int checkadmin(String phone,String password,String code,HttpSession session){
        String realCode=(String)session.getAttribute("code2");
        if(!code.toLowerCase().equals(realCode.toLowerCase())){
            return LoginStateEnum.FailCaseCode.getCode();
        }
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserPhone, phone).eq(User::getUserPassword,password));
        if(Objects.isNull(user)){
            return LoginStateEnum.FailCaseAccount.getCode();
        }else {
            if(user.getUserStatus()==0){
                return LoginStateEnum.UserException.getCode();
            }
            session.setAttribute("loginuser2",user);
            return LoginStateEnum.Success.getCode();
        }
    }

}

