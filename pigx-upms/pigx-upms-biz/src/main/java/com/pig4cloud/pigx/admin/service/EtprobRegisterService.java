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
import com.pig4cloud.pigx.admin.entity.EtpracRegister;
import com.pig4cloud.pigx.admin.entity.EtprobRegister;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;
import java.util.Map;

/**
 * 试用期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:18:05
 */
public interface EtprobRegisterService extends IService<EtprobRegister> {
	/**
	 * 试用转正：1.员工不为试用状态,不能做试用相关的操作；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotProbForProbation(EtprobRegister Etemployee);
	/**
	 * 试用转正：2.员工已经离职,不能进行试用相关操作；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeEmpLeaveForProbation(EtprobRegister Etemployee);

	/**
	 * 确认试用转正
	 *
	 * @return R
	 */
	public R updateEmployeeForZhuanZheng(EtprobRegister etemployee);
}
