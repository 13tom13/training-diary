package servlet.utils.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggableAspect {

    @Pointcut("within(@servlet.utils.annotations.Loggable *) && execution(* *(..))")
    public void annotatedByLoggable() {}

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Calling method: " + joinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis()-startTime;
        System.out.println("Execution of method: " + joinPoint.getSignature() + " finished. Execution time: " + endTime + " ms");
        return result;
    }
}