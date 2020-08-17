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

package com.pig4cloud.pigx.common.gray.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.WebUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author lengleng
 * @date 2020/1/12
 */
@Slf4j
public class GrayRibbonLoadBalancerRule extends AbstractLoadBalancerRule {
	@Override
	public void initWithNiwsConfig(IClientConfig iClientConfig) {
	}

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}


	public Server choose(ILoadBalancer lb, Object key) {
		List<Server> reachableServers = lb.getReachableServers();

		//注册中心无可用实例 抛出异常
		if (CollUtil.isEmpty(reachableServers)) {
			log.warn("No instance available for {}", key);
			return null;
		}

		// 获取请求version，无则随机返回可用实例
		String reqVersion = WebUtils.getRequest() != null
				? WebUtils.getRequest().getHeader(CommonConstants.VERSION) : null;
		if (StrUtil.isBlank(reqVersion)) {
			return reachableServers.get(RandomUtil.randomInt(reachableServers.size()));
		}

		// 遍历可以实例元数据，若匹配则返回此实例
		for (Server server : reachableServers) {
			NacosServer nacosServer = (NacosServer) server;
			Map<String, String> metadata = nacosServer.getMetadata();
			String targetVersion = MapUtil.getStr(metadata, CommonConstants.VERSION);
			if (reqVersion.equalsIgnoreCase(targetVersion)) {
				log.debug("gray requst match success :{} {}", reqVersion, nacosServer);
				return nacosServer;
			}
		}
		return reachableServers.get(RandomUtil.randomInt(reachableServers.size()));
	}

}
