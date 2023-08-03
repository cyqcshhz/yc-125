package com.yc.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTool implements InvocationHandler {
    private Object target;


    public JdkProxyTool(Object target) {
        this.target = target;
    }
    
    public  Object createProxy(){
        Object proxy= Proxy.newProxyInstance(JdkProxyTool.class.getClassLoader(),target.getClass().getInterfaces(),this);
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("add")){
            showHello();
        }
        Object returnValue=method.invoke(target,args);
        return returnValue;
    }

    private void showHello() {
        System.out.println("hello");
    }
}
