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
import com.pig4cloud.pigx.admin.mapper.EtprobRegisterMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.service.EtprobAllService;
import com.pig4cloud.pigx.admin.service.EtprobRegisterService;
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
 * 试用期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:18:05
 */
@Service
@AllArgsConstructor
public class EtprobRegisterServiceImpl extends ServiceImpl<EtprobRegisterMapper, EtprobRegister> implements EtprobRegisterService {

	private final  EtprobRegisterMapper etprobRegisterMapper;
	private final EtprobAllService etprobAllService;
	private  final EtemployeeService etemployeeService;
	/**
	 * 试用转正：1.员工不为试用状态,不能做试用相关的操作；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotProbForProbation(EtprobRegister etemployee){
		return etprobRegisterMapper.listEtEmployeeNotProbForProbation(etemployee);
	}
	/**
	 * 试用转正：2.员工已经离职,不能进行试用相关操作；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeEmpLeaveForProbation(EtprobRegister etemployee){
		return etprobRegisterMapper.listEtEmployeeEmpLeaveForProbation(etemployee);
	}
	/**
	 * 确认试用转正
	 *
	 * @return R
	 */
	@Transactional
	public R updateEmployeeForZhuanZheng(EtprobRegister etemployee){
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
			return R.ok("试用转正日期不能晚于今天!");
		}
		//实习转正日期不能早于入职日期!
		String joindate = etemployee.getJoindate();
		if(joindate.compareTo(currentTime)<0){
			return R.ok("试用转正日期不能早于入职日期!");
		}
		EtprobRegister one = getOne(Wrappers.<EtprobRegister>query().lambda().eq(EtprobRegister::getId, etemployee.getId()).eq(EtprobRegister::getInitialized,0));
		if (one == null) {
			return R.ok("实习转正信息未确认");
		}

		EtprobRegister one2 = getById(id);
		Integer eid = one2.getEid();
		EtprobRegister dD = new EtprobRegister();
		//更新etemployee
		Etemployee etemployee1 = new Etemployee();
		etemployee1.setEid(eid);
		etemployee1.setPracconfdate(one2.getEffectdate());
		etemployeeService.updateById(etemployee1);
		//插入历史
		EtprobAll etprobAll = new EtprobAll();
		BeanUtils.copyProperties(one2, etprobAll);
		etprobAll.setClosed(1);
		etprobAll.setClosedby(currentEid);
		etprobAll.setClosedtime(currentTime);
		etprobAllService.save(etprobAll);
		//删除登记表
		removeById(id);
		return R.ok("成功！");
	}
}
