package com.isxcode.acorn.client.response;

import com.isxcode.acorn.common.pojo.dto.AcornData;
import com.isxcode.acorn.common.pojo.AcornResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

@Aspect
public class SuccessResponseAdvice {

    private final MessageSource messageSource;

    public SuccessResponseAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Pointcut("@annotation(com.isxcode.acorn.client.response.SuccessResponse)")
    public void operateLog() {
    }

    @AfterReturning(returning = "data", value = "operateLog()&&@annotation(successResponse)")
    public void afterReturning(JoinPoint joinPoint, AcornData data, SuccessResponse successResponse) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AcornResponse baseResponse = new AcornResponse();
        if (!"void".equals(signature.getReturnType().getName())) {
            baseResponse.setCode("200");
            if (data.getClass().getDeclaredFields().length == 0) {
                baseResponse.setAcornData(null);
            } else {
                baseResponse.setAcornData(data);
            }
            baseResponse.setMessage(getMsg(successResponse));
            successResponse(baseResponse);
        } else {
            baseResponse.setCode("200");
            baseResponse.setMessage(getMsg(successResponse));
            successResponse(baseResponse);
        }

    }

    public String getMsg(SuccessResponse successResponse) {

        if (!successResponse.value().isEmpty()) {
            return successResponse.value();
        }

        try {
            return messageSource.getMessage(successResponse.msg(), null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return successResponse.msg();
        }
    }

    public void successResponse(AcornResponse baseResponse) {

        throw new SuccessException(baseResponse);
    }

}
