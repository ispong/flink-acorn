package com.isxcode.acorn.client.response;

import com.isxcode.acorn.common.pojo.AcornResponse;
import com.isxcode.acorn.common.exception.AcornException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<AcornResponse> successException(SuccessException successException) {

        return new ResponseEntity<>(successException.getBaseResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(AcornException.class)
    public ResponseEntity<AcornResponse> customException(AcornException abstractException) {

        AcornResponse errorResponse = new AcornResponse();
        errorResponse.setCode(abstractException.getCode() == null ? "500" : abstractException.getCode());
        errorResponse.setMessage(abstractException.getMsg());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AcornResponse> allException(Exception exception) {

        AcornResponse baseResponse = new AcornResponse();
        baseResponse.setCode("500");
        baseResponse.setMessage(exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


}

