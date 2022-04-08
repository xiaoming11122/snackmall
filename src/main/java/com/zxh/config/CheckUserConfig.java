package com.zxh.config;

import com.zxh.interceptor.Logininterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CheckUserConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Logininterceptor())
            .addPathPatterns("/collection/addone");
//            .excludePathPatterns("/","/login",
//                    "/zui/dist/lib/jquery/**","/zui/dist/js/**",
//                    "/zui/dist/css/**","/zui/dist/fonts/**","/img/**","/user/checkuser",
//                    "/images/**","/user/checkadmin","/user/adminlogin","/user/addadmin","/user/register","/goodsimg/**","/images/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/goodsimg/**")
                .addResourceLocations("file:C:\\Users\\hui\\Desktop\\20211223备份\\毕设\\项目\\goodsimg\\");//,"classpath:/static/","classpath:/templates/"
        registry.addResourceHandler("/recommendimg/**")
                .addResourceLocations("file:C:\\Users\\hui\\Desktop\\20211223备份\\毕设\\项目\\recommendimg\\");
    }
}
