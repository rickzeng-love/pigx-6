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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.EtchangeorgAll;
import com.pig4cloud.pigx.admin.entity.EtchangeorgRegister;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.mapper.EtchangeorgRegisterMapper;
import com.pig4cloud.pigx.admin.service.EtchangeorgAllService;
import com.pig4cloud.pigx.admin.service.EtchangeorgRegisterService;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
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
 * 调动登记
 *
 * @author gaoxiao
 * @date 2020-04-14 19:05:41
 */
@Service
@AllArgsConstructor
public class EtchangeorgRegisterServiceImpl extends ServiceImpl<EtchangeorgRegisterMapper, EtchangeorgRegister> implements EtchangeorgRegisterService {
	private  final  EtchangeorgRegisterMapper etchangeorgRegisterMapper;
	private  final EtemployeeService etemployeeService;
	private final EtchangeorgAllService etchangeorgAllService;
	/**
	 * 调动：1.员工已经离职，不能做变动；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeUnSignDel(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeUnSignDel(etemployee);
	}
	/**
	 * 调动：2.新公司已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewCompIsDisableForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewCompIsDisableForChange(etemployee);
	}
	/**
	 * 调动：3.新公司还未成立；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewCompUnSetupForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewCompUnSetupForChange(etemployee);
	}
	/**
	 * 调动：4.新部门已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewDepIsDisableForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewDepIsDisableForChange(etemployee);
	}
	/**
	 * 调动：5.新部门还未成立；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewDepUnSetupForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewDepUnSetupForChange(etemployee);
	}
	/**
	 * 调动：6.所选新部门不在新公司下；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewCompNewDepForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewCompNewDepForChange(etemployee);
	}
	/**
	 * 调动：7.所选新岗位已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewJobIsDisableForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewJobIsDisableForChange(etemployee);
	}
	/**
	 * 调动：8.所选新岗位还未成立；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewJobUnSetupForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewJobUnSetupForChange(etemployee);
	}

	/**
	 * 调动：9.所选新岗位不在新部门下；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNewJobNewDepForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeNewJobNewDepForChange(etemployee);
	}
	/**
	 * 调动：10.新行政上级已经离职；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeReportToLeaveForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeReportToLeaveForChange(etemployee);
	}
	/**
	 * 调动：11.生效日期不能在最近一次变动之前；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeEffectDateForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeeEffectDateForChange(etemployee);
	}
	/**
	 * 调动：12.该员工正兼职该岗位，先终止兼职；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeePartJobForChange(EtchangeorgRegister etemployee){
		return etchangeorgRegisterMapper.listEtEmployeePartJobForChange(etemployee);
	}

	/**
	 * 确认调动
	 *
	 * @return List<Map>
	 */
	@Transactional
	public R updateEmployeeForDD(EtchangeorgRegister etemployee){
		//操作人信息
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer currentEid = pigxUser.getEid();
		String currentTime = DateUtils.getTimeString();
		etemployee.setRegdate(currentTime);
		etemployee.setInitialized(0);
		etemployee.setSubmitby(currentEid);
		etemployee.setSubmittime(currentTime);
		Integer id = etemployee.getId();
		EtchangeorgRegister one = getOne(Wrappers.<EtchangeorgRegister>query().lambda().eq(EtchangeorgRegister::getId, etemployee.getId()).eq(EtchangeorgRegister::getInitialized,0));
		if (one == null) {
			return R.ok("调动信息未确认");
		}

		EtchangeorgRegister one2 = getById(id);
		Integer eid = one2.getEid();
		EtchangeorgRegister dD = new EtchangeorgRegister();
		//更新etemployee
		Etemployee etemployee1 = new Etemployee();
		etemployee1.setEid(eid);
		etemployee1.setCompid(one2.getNewCompid());
		etemployee1.setDepid(one2.getNewDepid());
		etemployee1.setJobid(one2.getNewJobid());
		etemployee1.setReportto(one2.getNewReportto());
		etemployee1.setPosition(one2.getNewPosition());
		etemployee1.setPosgrade(one2.getNewPosgrade());
		etemployee1.setEmpgrade(one2.getNewEmpgrade());
		etemployee1.setJobbegindate(one2.getEffectdate());
		etemployeeService.updateById(etemployee1);
		//插入历史
		EtchangeorgAll etchangeorgAll = new EtchangeorgAll();
		BeanUtils.copyProperties(one2, etchangeorgAll);
		etchangeorgAll.setClosed(1);
		etchangeorgAll.setClosedby(currentEid);
		etchangeorgAll.setClosedtime(currentTime);
		etchangeorgAllService.save(etchangeorgAll);
		//删除登记表
		removeById(id);
		return R.ok("成功！");
	}
}
