package com.isxcode.acorn.common.response;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SuccessResponse {

	String value() default "";

	String msg() default "";
}
