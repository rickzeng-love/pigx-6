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

package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtpracRegister;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;
import java.util.Map;

/**
 * 实习期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:12:57
 */
public interface EtpracRegisterService extends IService<EtpracRegister> {

	/**
	 * 实习转正：1.员工不为实习状态,不能进行实习相关操作；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNoPracticeForPractice(EtpracRegister Etemployee);
	/**
	 * 实习转正：2.员工已经离职,不能进行实习相关操作；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeEmpLeaveForPractice(EtpracRegister Etemployee);
	/**
	 * 实习转正：3.员工不能同时存在两种及以上的状态；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeTwoStatusForPractice(EtpracRegister Etemployee);
	/**
	 * 确认实习转正
	 *
	 * @return R
	 */
	public R updateEmployeeForZhuanZheng(EtpracRegister etemployee);


}
