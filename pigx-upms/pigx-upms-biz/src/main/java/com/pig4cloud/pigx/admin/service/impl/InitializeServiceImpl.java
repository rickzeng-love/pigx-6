package com.pig4cloud.pigx.admin.service.impl;

import com.pig4cloud.pigx.admin.service.EtcdEmpstatusService;
import com.pig4cloud.pigx.admin.service.EtcdEmptypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/*
   注册时候的初始化，主要处理下拉数据源
 */

@Slf4j
@Service
@AllArgsConstructor
public class InitializeServiceImpl {

	//员工状态
	private final EtcdEmpstatusService etcdEmpstatusService;
	//员工类型
	private final EtcdEmptypeService etcdEmptypeService;
}
