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
package com.pig4cloud.pigx.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtpracRegisterMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.service.EtpracAllService;
import com.pig4cloud.pigx.admin.service.EtpracRegisterService;
import com.pig4cloud.pigx.admin.service.EtprobAllService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 实习期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:12:57
 */
@Service
@AllArgsConstructor
public class EtpracRegisterServiceImpl extends ServiceImpl<EtpracRegisterMapper, EtpracRegister> implements EtpracRegisterService {

	private final  EtpracRegisterMapper etpracRegisterMapper;
	private final EtemployeeService etemployeeService;
	private  final EtpracAllService etpracAllService;
	/**
	 * 实习转正：1.员工不为实习状态,不能进行实习相关操作；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNoPracticeForPractice(EtpracRegister etemployee){
		return etpracRegisterMapper.listEtEmployeeNoPracticeForPractice(etemployee);
	}
	/**
	 * 实习转正：2.员工已经离职,不能进行实习相关操作；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeEmpLeaveForPractice(EtpracRegister etemployee){
		return etpracRegisterMapper.listEtEmployeeEmpLeaveForPractice(etemployee);
	}
	/**
	 * 实习转正：3.员工不能同时存在两种及以上的状态；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeTwoStatusForPractice(EtpracRegister etemployee){
		return etpracRegisterMapper.listEtEmployeeTwoStatusForPractice(etemployee);
	}
	/**
	 * 确认实习转正
	 *
	 * @return R
	 */
	@Transactional
	public R updateEmployeeForZhuanZheng(EtpracRegister etemployee){
		//操作人信息
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer currentEid = pigxUser.getEid();
		String currentTime = DateUtils.getTimeString();
		etemployee.setRegdate(currentTime);
		etemployee.setInitialized(0);
		etemployee.setSubmitby(currentEid);
		etemployee.setSubmittime(currentTime);
		Integer id = etemployee.getId();
		//实习转正日期不能晚于今天!
		String effectdate = etemployee.getEffectdate();
		if(effectdate.compareTo(currentTime)<0){
			return R.ok("实习转正日期不能晚于今天!");
		}
		//实习转正日期不能早于入职日期!
		String joindate = etemployee.getJoindate();
		if(joindate.compareTo(currentTime)<0){
			return R.ok("实习转正日期不能早于入职日期!");
		}
		EtpracRegister one = getOne(Wrappers.<EtpracRegister>query().lambda().eq(EtpracRegister::getId, etemployee.getId()).eq(EtpracRegister::getInitialized,0));
		if (one == null) {
			return R.ok("实习转正信息未确认");
		}

		EtpracRegister one2 = getById(id);
		Integer eid = one2.getEid();
		EtpracRegister dD = new EtpracRegister();
		//更新etemployee
		Etemployee etemployee1 = new Etemployee();
		etemployee1.setEid(eid);
		etemployee1.setPracconfdate(one2.getEffectdate());
		etemployeeService.updateById(etemployee1);
		//插入历史
		EtpracAll etpracAll = new EtpracAll();
		BeanUtils.copyProperties(one2, etpracAll);
		etpracAll.setClosed(1);
		etpracAll.setClosedby(currentEid);
		etpracAll.setClosedtime(currentTime);
		etpracAllService.save(etpracAll);
		//删除登记表
		removeById(id);
		return R.ok("成功！");
	}
}
