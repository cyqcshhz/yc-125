package org.ycframework.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ycframework.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class YcAnnotationConfigApplicationContext implements YcApplicationContext{
    private Logger logger = LoggerFactory.getLogger(YcAnnotationConfigApplicationContext.class);

    private Map<String, YcBeanDefinition> beanDefinionMap = new HashMap<>();

    private Map<String,Object> beanMap=new HashMap<>();

    private Properties pros;
    public  YcAnnotationConfigApplicationContext(Class...configClasses){
        try {
            pros = System.getProperties();
            List<String> toScanPackagePath = new ArrayList<>();
            for (Class cls : configClasses) {
                if (cls.isAnnotationPresent(YcConfiguration.class) == false) {
                    continue;
                }
                String[] basePackages = null;
                if (cls.isAnnotationPresent(YcComponentScan.class)) {
                    YcComponentScan ycComponentScan = (YcComponentScan) cls.getAnnotation(YcComponentScan.class);
                    basePackages = ycComponentScan.basePackages();
                    if (basePackages == null || basePackages.length <= 0) {
                        basePackages = new String[1];
                        basePackages[0] = cls.getPackage().getName();
                    }
                    logger.info(cls.getName() + "类上有@YcComponentScan注解，它要扫描的路径：" + basePackages[0]);
                }
                recursiveLoadBeanDefinition(basePackages);
            }
            createBean();
            doDi();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }



    }

    private void doDi() throws IllegalAccessException, InstantiationException, ClassNotFoundException{
        for (Map.Entry<String,Object> entry:beanMap.entrySet()){
            String beanId=entry.getKey();
            Object beanObj=entry.getValue();

            Field[] fields= beanObj.getClass().getDeclaredFields();
            for (Field field:fields){
                if (field.isAnnotationPresent(YcResource.class)){
                    YcResource ycResource=field.getAnnotation(YcResource.class);
                    String toDiBeanId=ycResource.name();
                    Object obj=getToDiBean(toDiBeanId);
                    field.setAccessible(true);
                    field.set(beanObj,obj);
                }
            }
        }
        
    }

    private Object getToDiBean(String toDiBeanId)throws IllegalAccessException, InstantiationException, ClassNotFoundException{
        if (beanMap.containsKey(toDiBeanId)){
            return beanMap.get(toDiBeanId);
        }
        if (!beanDefinionMap.containsKey(toDiBeanId)){
            throw new RuntimeException("spring容器中没有加载此class:"+toDiBeanId);
        }
        YcBeanDefinition bd=beanDefinionMap.get(toDiBeanId);
        if (bd.isIslazy()){
            String  classpath=bd.getClassInfo();
            Object beanObj= Class.forName(classpath).newInstance();
            beanMap.put(toDiBeanId,beanObj);
            return beanObj;
        }
        if (bd.getScope().equalsIgnoreCase("prototype")){
            String classpath=bd.getClassInfo();
            Object beanObj= Class.forName(classpath).newInstance();
            beanMap.put(toDiBeanId,beanObj);
            return beanObj;
        }
        if (bd.getScope().equalsIgnoreCase("prototype")){
            String classpath=bd.getClassInfo();
            Object beanObj= Class.forName(classpath).newInstance();
            return beanObj;
        }
        return null;
    }

    private void createBean() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for (Map.Entry<String ,YcBeanDefinition> entry: beanDefinionMap.entrySet()){
            String beanId=entry.getKey();
            YcBeanDefinition ybd=entry.getValue();
            if (!ybd.isIslazy() && !ybd.getScope().equalsIgnoreCase("prototype")){
                String classInfo=ybd.getClassInfo();
                Object obj=Class.forName(classInfo).newInstance();
                beanMap.put(beanId,obj);
                logger.trace("spring容器托管了:"+beanId+"=>"+classInfo);
            }
        }
    }

    private void recursiveLoadBeanDefinition(String[] basePackages) {
        for (String basePackage: basePackages){
            String packagePath = basePackage.replaceAll("\\.","/");
            Enumeration<URL> files=null;
            try {
                files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
                while (files.hasMoreElements()){
                    URL url=files.nextElement();
                    logger.trace("当前正在递归加载:"+url.getFile());
                    findPackageClasses(url.getFile(),basePackage);
                }

            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private void findPackageClasses(String packagePath, String basePackage) {
        if (packagePath.startsWith("/")){
            packagePath=packagePath.substring(1);
        }
        File file = new File(packagePath);

        File[] classFiles= file.listFiles((pathname )->{
            if (pathname.getName().endsWith(".class")||pathname.isDirectory()){
                return true;
            }
            return false;

        });
        if (classFiles==null || classFiles.length<=0){
            return;
        }
        for (File cf:classFiles){
            if (cf.isDirectory()){
                logger.trace("递归:"+cf.getAbsolutePath()+",它对应的名字为："+(basePackage +"."+cf.getName()));
                findPackageClasses(cf.getAbsolutePath(),basePackage+"."+cf.getName());
            }else {
                URLClassLoader uc = new URLClassLoader(new URL[]{});
                Class cls = null;
                try {
                    cls = uc.loadClass(basePackage + "." + cf.getName().replaceAll(".class", ""));
                    if (cls.isAnnotationPresent(YcComponent.class)
                            || cls.isAnnotationPresent(YcConfiguration.class)
                            || cls.isAnnotationPresent(YcController.class)
                            || cls.isAnnotationPresent(YcService.class))

                        logger.info("加载到一个待托管的类：" + cls.getName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            }
        }


    @Override
    public Object getBean(String beanid) {
        return null;
    }
}
