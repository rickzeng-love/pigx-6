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

package com.pig4cloud.pigx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtpracRegister;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 实习期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:12:57
 */
@Mapper
public interface EtpracRegisterMapper extends BaseMapper<EtpracRegister> {

	/**
	 * 实习转正：1.员工不为实习状态,不能进行实习相关操作；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNoPracticeForPractice(EtpracRegister Etemployee);
	/**
	 * 实习转正：2.员工已经离职,不能进行实习相关操作；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeEmpLeaveForPractice(EtpracRegister Etemployee);
	/**
	 * 实习转正：3.员工不能同时存在两种及以上的状态；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeTwoStatusForPractice(EtpracRegister Etemployee);
	//实习期转正校验
	Map eSPPracCheckSub(Map map);
	//实习期转正确认
	Map eSPPracStart(Map map);
}
