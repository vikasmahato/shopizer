package com.salesmanager.core.business.services.accounting.quickbooks.aspect;

import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import com.intuit.ipp.util.Logger;

import java.util.Arrays;
import java.util.List;

@Configuration
@Aspect
public class CommonJoinPointConfig {

    private static final org.slf4j.Logger LOG = Logger.getLogger();

    @AfterThrowing(pointcut = "execution(* com.salesmanager.core.business.services.accounting.quickbooks.*.*.*(..))", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String stuff = signature.toString();
        String arguments = Arrays.toString(joinPoint.getArgs());
        LOG.error("Write something in the log... We have caught exception in method: "
                + methodName + " with arguments "
                + arguments + "\nand the full toString: " + stuff + "\nthe exception is: "
                + ex.getMessage());
    }

    @Around("execution(* com.salesmanager.core.business.services.accounting.quickbooks.*.*.*(..))")
    public Object exceptionHandlerWithReturnType(ProceedingJoinPoint joinPoint) throws Throwable{
        Object output = null;
        long start = System.currentTimeMillis();
        LOG.info("Class:"+joinPoint.getTarget().getClass()+" entry -> method ->"+joinPoint.getSignature().getName());
        try {
            output = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            LOG.info("Method execution time: " + elapsedTime + " milliseconds.");
            LOG.info("Class:"+joinPoint.getTarget().getClass()+" exit -> method ->"+joinPoint.getSignature().getName());
        } catch(FMSException ex) {
            List<Error> list = ex.getErrorList();
            list.forEach(error -> LOG.error("Error while calling entity add:: " + error.getMessage()));
        }
        return output;
    }
}
