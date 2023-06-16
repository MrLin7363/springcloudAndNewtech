 

package com.lin.spring.aop.annoDemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 切面
 */
@Component
@Aspect
public class SentinelAspect {

    /**
     * 方法是通知/增强
     *
     * @param joinPoint 切点
     * @param SentinelAop 获取注解 SentinelAop必须和@annotation 里的名称一致
     */
    @Around(value = "@annotation(SentinelAop)")
    public Object doExeAdvice(ProceedingJoinPoint joinPoint, SentinelAop SentinelAop) throws Throwable {
        System.out.println("===================开始增强处理===================");

        // 获取签名 利用签名可以获取请求的包名、方法名，包括参数（通过 joinPoint.getArgs()获取）
        Signature signature = joinPoint.getSignature();

        // 获取注解
        String[] limitParams = SentinelAop.limitParams();

        //获取请求参数，详见接口类
        Object[] objects = joinPoint.getArgs();
        String name = objects[0].toString();
        System.out.println("name1->>>>>>>>>>>>>>>>>>>>>>" + name);

        // 修改入参
        objects[0] = "linAdvice";

        // 将修改后的参数传入, 下面会先执行controller切点里的方法
        final Object proceed = joinPoint.proceed(objects);

        // 修改返回结果
        String result = proceed.toString();
        result="SentinelController end change";

        // controller结束才会到这里
        System.out.println("around end =======================");
        // 修改后的返回结果返回
        return result;
    }
}
