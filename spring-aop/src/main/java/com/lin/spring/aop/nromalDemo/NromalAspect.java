package com.lin.spring.aop.nromalDemo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NromalAspect {

    // 拦截NormalController的所有方法
    @Pointcut("execution(* com.lin.spring.aop.nromalDemo.NormalController.*(..))")
    public void pointCut1() {}

    @Before("pointCut1()")
    public Object run(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        NormalController.Request request = (NormalController.Request) args[0];
        System.out.println("Before params = " + request);
        return request;
    }

    @After("pointCut1()")
    public Object runAfter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        NormalController.Request request = (NormalController.Request) args[0];
        System.out.println("After params = " + request);
        return request;
    }

    @AfterReturning(pointcut = "pointCut1()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        System.out.println("返回参数为：" + result);
        // 实际项目中可以根据业务做具体的返回值增强，但是已经返回了，要想修改返回结果，只能@Around
        String result1 = (String) result;
        System.out.println("doAfterReturning 对返回参数进行业务上的增强：{}" + result + "增强版");
    }
}
