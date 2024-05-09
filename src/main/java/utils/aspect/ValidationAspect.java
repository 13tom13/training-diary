package utils.aspect;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import utils.ValidatorFactoryProvider;

@Aspect
public class ValidationAspect {

    private final Validator validator = ValidatorFactoryProvider.getValidator();

    @Pointcut("execution(* entity.dto.*.*(..))")
    public void dtoMethods() {}

    @Around("dtoMethods() && args(dto,..)")
    public Object validateDTO(ProceedingJoinPoint joinPoint, Object dto) throws Throwable {
        var validate = validator.validate(dto);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        } else {
            return joinPoint.proceed();
        }
    }
}

