package com.isxcode.acorn.common.response;

import com.isxcode.acorn.api.exception.AbstractException;
import com.isxcode.acorn.api.pojo.base.BaseResponse;
import com.isxcode.acorn.common.constant.ResponseConstant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AbstractException.class)
	public ResponseEntity<BaseResponse<?>> customException(AbstractException abstractException) {

		BaseResponse<?> errorResponse = new BaseResponse<>();
		errorResponse.setCode(
				abstractException.getCode() == null
						? ResponseConstant.ERROR_CODE
						: abstractException.getCode());
		errorResponse.setMsg(abstractException.getMsg());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<BaseResponse<String>> accessDeniedException(
			AccessDeniedException accessDeniedException) {

		BaseResponse<String> errorResponse = new BaseResponse<>();
		errorResponse.setCode(ResponseConstant.FORBIDDEN_CODE);
		errorResponse.setData(accessDeniedException.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<BaseResponse<Object>> successException(SuccessException successException) {

		return new ResponseEntity<>(successException.getBaseResponse(), HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<?>> allException(Exception exception) {

		BaseResponse<?> baseResponse = new BaseResponse<>();
		baseResponse.setCode(ResponseConstant.ERROR_CODE);
		baseResponse.setMsg(
				exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage());
		exception.printStackTrace();
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			@NonNull HttpHeaders headers,
			@NonNull HttpStatus status,
			@NonNull WebRequest request) {
		ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
		return new ResponseEntity<>(
				new BaseResponse<>("400", objectError.getDefaultMessage(), ""), HttpStatus.OK);
	}
}
