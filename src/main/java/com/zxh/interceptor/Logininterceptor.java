package com.zxh.interceptor;


import com.zxh.entity.User;
import com.zxh.utils.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logininterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI.toString());
        //登录检查逻辑
        HttpSession session = request.getSession();
        User loginuser = (User) session.getAttribute("loginuser2");
        if(loginuser!=null){
            Logger.usernamr= loginuser.getUserPhone();
            //放行
            return true;
        }
        request.setAttribute("msg","请先登录");
        request.getRequestDispatcher("/").forward(request,response);
        return false;
    }
}
