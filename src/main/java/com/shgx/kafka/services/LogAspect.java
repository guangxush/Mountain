package com.shgx.kafka.services;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by gshan on 2018/9/1
 */
@Aspect
@Slf4j
public class LogAspect {


    @Pointcut("execution(* com.shgx.kafka..*.*(..))")
    private void log() {

    }

    /**
     * AOP joinpoint to handle exception and will send email to notify
     * */
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        String errMsg = "Errors " + e + " happened in AccioService: " + getMethodNameAndArgs(joinPoint);
        log.error(errMsg);
        //mailSender.sendEmail(errMsg);
    }

    /**
     * Java reflect to get class info
     * */
    private String getMethodNameAndArgs (JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuffer sb = new StringBuffer();
        sb.append(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
        for (int i = 0; i < args.length;  i++ ) {
            sb.append(" arg[" + i + "]: " + args[i]);
        }
        return sb.toString();
    }
}
