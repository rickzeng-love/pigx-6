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
import com.pig4cloud.pigx.admin.entity.AtstaffRegister;
import com.pig4cloud.pigx.admin.entity.AvwShiftDay;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-06-22 11:43:40
 */
public interface AvwShiftDayService extends IService<AvwShiftDay> {
	public R getAttendMonthInit(Map paramMap);
	public R getAttendMonthCalcEasy( Map paramMap);
	public R getAttendMonthCloseEasy( Map paramMap);
	public R getShiftHandleTurnRunEasy( Map map);
	public R getStaffstartEasy(AtstaffRegister atstaffRegister2);
	public R getAnalysisEasy(Map map);


}
