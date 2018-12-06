package me.yushuo.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* me.yushuo.wenda.controller.HomeController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object args : joinPoint.getArgs()) {
            sb.append("args: " + args + "|");
        }
        logger.info(sb.toString());
        logger.info("before indexcontroller" + new Date());
    }

    @After("execution(* me.yushuo.wenda.controller.HomeController.*(..))")
    public void afterMethod() {
        logger.info("after indexcontroller" + new Date());
    }
}
