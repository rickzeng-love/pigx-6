/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.common.transaction.tx.springcloud.interceptor;

import com.codingapi.tx.aop.service.AspectBeforeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LCN on 2017/6/7.
 * @author LCN
 * @since 4.1.0
 */

@Slf4j
@Component
@AllArgsConstructor
public class TxManagerInterceptor {
	private final AspectBeforeService aspectBeforeService;

	@SneakyThrows
	public Object around(ProceedingJoinPoint point) {
		RequestAttributes requestAttributes;
		String groupId = null;
		try {
			requestAttributes = RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			groupId = request.getHeader("tx-group");
		} catch (IllegalStateException e) {
			log.info("非web 环境调用 不包含请求上下文");
		}
		return aspectBeforeService.around(groupId, point);
	}
}
