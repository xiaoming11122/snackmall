package com.zxh.service.business;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.zxh.constants.LoginStateEnum;
import com.zxh.constants.RecordStateEnum;
import com.zxh.entity.Admin;
import com.zxh.service.AdminService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class AdminBusiness {
    @Resource
    AdminService adminService;

    public int checkadmin(String phone, String password, String code, String realCode, HttpSession session){
        if(!code.toLowerCase().equals(realCode.toLowerCase())){
            return LoginStateEnum.FailCaseCode.getCode();
        }
        Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName, phone).eq(Admin::getAdminPassword,password));
        if(Objects.isNull(admin)){
            return LoginStateEnum.FailCaseAccount.getCode();
        }else {
            session.setAttribute("loginuser",admin);
            return LoginStateEnum.Success.getCode();
        }
    }

    public int register(String phone,String password){
        if(!Objects.isNull(adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName, phone)))){
            return 2;
        }
        Admin admin=new Admin();
        admin.setAdminName(phone);
        admin.setAdminPassword(password);
        return adminService.save(admin)? RecordStateEnum.Success.getCode() : RecordStateEnum.Fail.getCode();
    }

}
