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
import com.pig4cloud.pigx.admin.entity.CtsalaryRegister;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 薪资开启
 *
 * @author gaoxiao
 * @date 2020-06-11 11:29:18
 */
@Mapper
public interface CtsalaryRegisterMapper extends BaseMapper<CtsalaryRegister> {
	/**
	 * 薪资开启确认
	 *
	 * @param  registerMap
	 * @return code
	 */
	Map cSP_SalaryCheck(Map registerMap);
	/**
	 * 薪资开启生效
	 *
	 * @param  registerMap
	 * @return code
	 */
	Map cSP_SalaryStart(Map registerMap);

	/**
	 * 薪资变动确认
	 *
	 * @param  registerMap
	 */
	Map cSP_ChangeSalaryCheck(Map registerMap);
	/**
	 * 薪资变动生效
	 *
	 * @param  registerMap
	 */
	Map cSP_ChangeSalaryStart(Map registerMap);
}
