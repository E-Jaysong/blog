package com.pubutech.blog.business.aspect;

import com.alibaba.fastjson.JSONObject;
import com.pubutech.blog.business.annotation.SysLog;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Pointcut(value = "@annotation(com.pubutech.blog.business.annotation.SysLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName(); //获取方法名称
        Map<String,Object > nameAndArgs = null;
        //获取参数名称和值
        try {
            nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object proceed = null;
        try {
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("request", nameAndArgs);
        json.put("response", proceed);
        log.info("借口请求日志,{}", JSONObject.toJSONString(json));

        return proceed;
    }

    /**
     * 获取方法中的中文备注
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getMthodRemark(ProceedingJoinPoint joinPoint) throws Exception {

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    SysLog methodCache = m.getAnnotation(SysLog.class);
                    if (methodCache != null) {
                        methode = methodCache.remark();
                    }
                    break;
                }
            }
        }
        return methode;
    }

    /**
     * @Description 获取字段名和字段值
     * @Author
     * @date
     * @return Map<String,Object>
     */
    private Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws Exception {
        Map<String,Object > map=new HashMap<String,Object>();


        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc;
        cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++){
            String key = attr.variableName(i + pos);
            /*if(i == 0){
                key = "baseRequest";
            }*/
            map.put( key,args[i]);//paramNames即参数名
        }
        return map;
    }

}
