package com.isxcode.acorn.common.response;

import java.io.InputStream;

import com.isxcode.acorn.api.pojo.BaseResponse;
import com.isxcode.acorn.common.constant.ResponseConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class SuccessResponseAdvice {

	private final MessageSource messageSource;

	@Pointcut("@annotation(com.isxcode.acorn.common.response.SuccessResponse)")
	public void operateLog() {}

	@AfterReturning(returning = "data", value = "operateLog()&&@annotation(successResponse)")
	public void afterReturning(JoinPoint joinPoint, Object data, SuccessResponse successResponse) {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		BaseResponse<Object> baseResponse = new BaseResponse<>();
		if (!"void".equals(signature.getReturnType().getName())) {
			if (data instanceof InputStream) {
				return;
			}
			baseResponse.setCode(ResponseConstant.SUCCESS_CODE);
			if (data.getClass().getDeclaredFields().length == 0) {
				baseResponse.setData(null);
			} else {
				baseResponse.setData(data);
			}
			baseResponse.setMsg(getMsg(successResponse));
			successResponse(baseResponse);
		} else {
			baseResponse.setCode(ResponseConstant.SUCCESS_CODE);
			baseResponse.setMsg(getMsg(successResponse));
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

	public void successResponse(BaseResponse<Object> baseResponse) {

		throw new SuccessException(baseResponse);
	}
}
