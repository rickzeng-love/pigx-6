package com.pig4cloud.pigx.admin.entity;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

	String value() default "";

	int col() default 0;
}