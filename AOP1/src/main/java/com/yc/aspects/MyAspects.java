package com.yc.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class MyAspects {
    @Pointcut("execution(* com .yc.biz.*.make.*(..))")
    private void a(){}

    @Before("a()")
    public void recordTime(){
        Date d=new Date();
        System.out.println("======下单的时间:"+d);
    }

    @AfterReturning("a()")
    public void sendEmail(){
        System.out.println("调用数据库......");
    }

    @AfterReturning("a()")
    public void recordParams(JoinPoint jp){
        System.out.println("增强的方法:"+jp.getSignature());
        System.out.println("增强的目标:"+jp.getTarget());
        System.out.println("参数:");
        Object[] params = jp.getArgs();
        for (Object o:params){
            System.out.println(o);
        }
    }

    @Pointcut("execution(* com.yc.biz.*.findOrderId(String))")
    private void b(){}

    private Map<String, Long> map=new ConcurrentHashMap<String ,Long>();

    @AfterReturning("b()")
    public void recordPnameCount(JoinPoint jp){
        Object[] objs=jp.getArgs();
        String pname=(String)objs[0];
        Long num=1L;
        if (map.containsKey(pname)){
            num=map.get(pname);
            num++;
        }
        map.put(pname,num);
        System.out.println("统计结果："+map);
    }

}
