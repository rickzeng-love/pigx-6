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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtleaveAll;
import com.pig4cloud.pigx.admin.entity.EtstaffAll;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 员工信息表
 *
 * @author gaoxiao
 * @date 2020-04-08 13:22:25
 */
@Service
@AllArgsConstructor
public class EtemployeeServiceImpl extends ServiceImpl<EtemployeeMapper, Etemployee> implements EtemployeeService {
	private  final  EtemployeeMapper etemployeeMapper;
	/**
	 * 手机端获取公司通讯录
	 *
	 * @return R
	 */
	public List<Map> getAllOtcdEmpgradeWithPinyin(Etemployee etemployee){
		return etemployeeMapper.listAllOtcdEmpgradeWithPinyin(etemployee);
	}
	/**
	 * 入：1.黑名单校验；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeBlackList(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeBlackList(etemployee);
	}
	/**
	 * 入：2.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeCDRelationship(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeCDRelationship(etemployee);
	}
	/**
	 * 入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeCJRelationship(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeCJRelationship(etemployee);
	}
	/**
	 * 入：4.员工工号已经存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeExistsEmployee(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeExistsEmployee(etemployee);
	}
	/**
	 * 入：4-1.员员工号号已存在，正在办理入职手续
	 *
	 * @return List<Map>
	 */
	public List<Map> getExistsEtstaffRegisterByBadge(Etemployee etemployee){
		return etemployeeMapper.listExistsEtstaffRegisterByBadge(etemployee);
	}
	/**
	 * 入：5.直接主管不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsReportTo(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeNotExistsReportTo(etemployee);
	}
	/**
	 * 入：6.职能主管不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsWFReportTo(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeNotExistsWFReportTo(etemployee);
	}
	/**
	 * 入：7.所选公司不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsComp(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeNotExistsComp(etemployee);
	}
	/**
	 * 入：8.所选公司已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeCompIsDisable(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeCompIsDisable(etemployee);
	}

	/**
	 * 入：9.所选部门不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsDept(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeNotExistsDept(etemployee);
	}
	/**
	 * 入：10.所选部门已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeDeptIsDisable(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeDeptIsDisable(etemployee);
	}
	/**
	 * 入：11.所选岗位不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsJob(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeNotExistsJob(etemployee);
	}
	/**
	 * 入：12.所选岗位已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeJobIsDisable(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeJobIsDisable(etemployee);
	}
	/**
	 * 入：14.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeMobileRepeat(Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeMobileRepeat(etemployee);
	}
	/**
	 * 入：14-1.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtstaffRegisterByMobile(Etemployee etemployee){
		return etemployeeMapper.listEtstaffRegisterByMobile(etemployee);
	}
	/**
	 * 员工花名册
	 *
	 * @return IPage
	 */
	@Override
	public IPage getEtEmployeeHMC(Page page, Etemployee etemployee){
		return etemployeeMapper.listEtEmployeeHMC(page,etemployee);
	}
	/**
	 * 人事概况报表
	 *
	 * @return List<Map>
	 */
	@Override
	public Map getPersonnelProfileEmpStatus1(Etemployee etemployee){
		return etemployeeMapper.listPersonnelProfileEmpStatus1(etemployee);
	}
	@Override
	public Map getPersonnelProfileEmpStatus2(Etemployee etemployee){
		return etemployeeMapper.listPersonnelProfileEmpStatus2(etemployee);
	}
	@Override
	public Map getPersonnelProfileEmpStatus4(Etemployee etemployee){
		return etemployeeMapper.listPersonnelProfileEmpStatus4(etemployee);
	}
	@Override
	public Map getPersonnelProfileSum(Etemployee etemployee){
		return etemployeeMapper.listPersonnelProfileSum(etemployee);
	}
	/**
	 * 人事提醒报表
	 */
	//未签订合同
	@Override
	public IPage<Map> getHasNoContract(Page page, Etemployee etemployee){
		return etemployeeMapper.listHasNoContract(page,etemployee);
	}
	//合同到期
	@Override
	public IPage<Map> getContractExpire(Page page, Etemployee etemployee){
		return etemployeeMapper.listContractExpire(page,etemployee);
	}
	//合同到期2
	@Override
	public IPage<Map> getContractExpire2(Page page, Etemployee etemployee){
		return etemployeeMapper.listContractExpire2(page,etemployee);
	}
	//待入职
	@Override
	public IPage<Map> getEtstaffRegisterForRuZhi(Page page, Etemployee etemployee){
		return etemployeeMapper.listEtstaffRegisterForRuZhi(page,etemployee);
	}
	//待转正
	@Override
	public IPage<Map> getEtemployeeWaitingForCorrection(Page page, Etemployee etemployee){
		return etemployeeMapper.listEtemployeeWaitingForCorrection(page,etemployee);
	}
	//待离职
	@Override
	public IPage<Map> getEtleaveRegisterWaitingForLeave(Page page, Etemployee etemployee){
		return etemployeeMapper.listEtleaveRegisterWaitingForLeave(page,etemployee);
	}
	/*
	 * 性别报表
	 */
	@Override
	public List<Map> getEtemployeeForGender(Etemployee etemployee){
		return etemployeeMapper.listEtemployeeForGender(etemployee);
	}
	/*性别报表all*/
	@Override
	public Map getEtemployeeForGenderALL(Etemployee etemployee){
		return etemployeeMapper.listEtemployeeForGenderALL(etemployee);
	}
	/*年龄段统计*/
	@Override
	public Map getEtemployeeForAge(Etemployee etemployee){
		return etemployeeMapper.listEtemployeeForAge(etemployee);
	}

	/*员工关怀*/
	@Override
	public List<Map> getEmployeeCare(Etemployee etemployee){
		return etemployeeMapper.listEmployeeCare(etemployee);
	}
	/*入职周年*/
	public List<Map> getEmployeeEntrAnniversary(Etemployee etemployee){
		return etemployeeMapper.listEmployeeEntrAnniversary(etemployee);
	}

	//待入职
	@Override
	public Map getEtstaffRegisterCount(Etemployee etemployee){
		return etemployeeMapper.listEtstaffRegisterCount(etemployee);
	}
	//待离职
	@Override
	public Map getEtleaveRegisterCount(Etemployee etemployee){
		return etemployeeMapper.listEtleaveRegisterCount(etemployee);
	}
	//政治面貌统计
	@Override
	public List<Map> getEmployeeForParty(Etemployee etemployee){
		return etemployeeMapper.listEmployeeForParty(etemployee);
	}
	/*各月份人员流动统计*/
	//入职
	@Override
	public List<Map> getEmployeeFlowRuZhiByMonth(EtstaffAll etstaffAll){
		return etemployeeMapper.listEmployeeFlowRuZhiByMonth(etstaffAll);
	}
	//离职
	@Override
	public List<Map> getEmployeeLiZhiFlowByMonth(EtleaveAll etleaveAll){
		return etemployeeMapper.listEmployeeLiZhiFlowByMonth(etleaveAll);
	}
	//离职原因报表
	@Override
	public List<Map> getEmployeeLiZhiByReason(EtleaveAll etleaveAll){
		return etemployeeMapper.listEmployeeLiZhiByReason(etleaveAll);
	}
	@Override
	public Map getEmployeeLiZhiByReasonCount(EtleaveAll etleaveAll){
		return etemployeeMapper.listEmployeeLiZhiByReasonCount(etleaveAll);
	}
	/*
	工龄报表
 	*/
	@Override
	public Map getEtemployeeForGAge(Etemployee etemployee){
		return etemployeeMapper.listEtemployeeForGAge(etemployee);
	}
	/*
  婚姻状况报表
 	*/
	@Override
	public List<Map> getEmployeeByMarriage(Etemployee etemployee){
		return etemployeeMapper.listEmployeeByMarriage(etemployee);
	}
	@Override
	public Map getEmployeeByMarriageCount(Etemployee etemployee){
		return etemployeeMapper.listEmployeeByMarriageCount(etemployee);
	}
	/*
	籍贯统计人数
 	*/
	@Override
	public List<Map> getEmployeeByResident(Etemployee etemployee){
		return etemployeeMapper.listEmployeeByResident(etemployee);
	}
	@Override
	public Map getEmployeeByResidentCount(Etemployee etemployee){
		return etemployeeMapper.listEmployeeByResidentCount(etemployee);
	}
	//通用的统计公司所有人数
	@Override
	public Map getEmployeeByCommonCount(Etemployee etemployee){
		return etemployeeMapper.listEmployeeByCommonCount(etemployee);
	}
	//员工转正
	@Override
	public IPage<Map> getEmployeeZhuangZheng(Page page ,Etemployee etemployee){
		return etemployeeMapper.listEmployeeZhuangZheng(page,etemployee);
	}
}
