package com.zxh.utils;



import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {
    private static final org.apache.log4j.Logger LOGGER= org.apache.log4j.Logger.getLogger(Logger.class);
    public static String usernamr="";

    @Pointcut("execution(public * com.zxh.controller.*.*(..))")
    private void pt1(){};
    @Around("pt1()")
    public Object aroundprintLg(ProceedingJoinPoint joinPoint) throws Throwable {
        Object rtValue=null;
        try{
            LOGGER.info("用户："+usernamr+"正在调用方法："+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"参数长度："+joinPoint.getArgs().length);
            Object[] args=joinPoint.getArgs();
            rtValue=joinPoint.proceed(args);
            String argss=null;
            int i=1;
            for (Object arg : args) {
                try {
                    argss+= "第"+i+"个参数:"+JSONObject.toJSONString(arg)+"     ";
                }catch (Exception e){
                    LOGGER.error("参数转换出现异常");
                }
                 i++;
            }
            LOGGER.info("用户："+usernamr+"调用方法："+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"参数信息："+argss);
            LOGGER.info("返回信息"+ JSONObject.toJSONString(rtValue));
        }catch (Exception ex){
            LOGGER.info(ex.getMessage(),ex);
            LOGGER.info("方法"+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"出现异常");
        }finally {

        }
        return rtValue;
    }
}
