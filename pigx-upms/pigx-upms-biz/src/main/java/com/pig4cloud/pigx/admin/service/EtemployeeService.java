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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtleaveAll;
import com.pig4cloud.pigx.admin.entity.EtstaffAll;
import com.pig4cloud.pigx.admin.entity.Otdepartment;

import java.util.List;
import java.util.Map;

/**
 * 员工信息表
 *
 * @author gaoxiao
 * @date 2020-04-08 13:22:25
 */
public interface EtemployeeService extends IService<Etemployee> {
	/**
	 * 手机端获取公司通讯录
	 *
	 * @return R
	 */
	public List<Map> getAllOtcdEmpgradeWithPinyin(Etemployee etemployee);
	/**
	 * 入：1.黑名单校验；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeBlackList(Etemployee etemployee);
	/**
	 * 入：2.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeCDRelationship(Etemployee etemployee);
	/**
	 * 入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeCJRelationship(Etemployee etemployee);
	/**
	 * 入：4.员工工号已经存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeExistsEmployee(Etemployee etemployee);
	/**
	 * 入：4-1.员员工号号已存在，正在办理入职手续
	 *
	 * @return List<Map>
	 */
	public List<Map> getExistsEtstaffRegisterByBadge(Etemployee etemployee);
	/**
	 * 入：5.直接主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsReportTo(Etemployee etemployee);
	/**
	 * 入：6.职能主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsWFReportTo(Etemployee etemployee);
	/**
	 * 入：7.所选公司不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsComp(Etemployee etemployee);
	/**
	 * 入：8.所选公司已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeCompIsDisable(Etemployee etemployee);
	/**
	 * 入：9.所选部门不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsDept(Etemployee etemployee);
	/**
	 * 入：10.所选部门已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeDeptIsDisable(Etemployee etemployee);
	/**
	 * 入：11.所选岗位不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsJob(Etemployee etemployee);
	/**
	 * 入：12.所选岗位已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeJobIsDisable(Etemployee etemployee);
	/**
	 * 入：14.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeMobileRepeat(Etemployee etemployee);
	/**
	 * 入：14-1.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtstaffRegisterByMobile(Etemployee etemployee);
	/**
	 * 员工花名册
	 *
	 * @return IPage
	 */
	public IPage getEtEmployeeHMC(Page page, Etemployee etemployee);
	/**
	 * 人事概况报表
	 *
	 * @return List<Map>
	 */
	public Map getPersonnelProfileEmpStatus1(Etemployee etemployee);
	public Map getPersonnelProfileEmpStatus2(Etemployee etemployee);
	public Map getPersonnelProfileEmpStatus4(Etemployee etemployee);
	public Map getPersonnelProfileSum(Etemployee etemployee);
	/**
	 * 人事提醒报表
	 */
	//未签订合同
	public IPage<Map> getHasNoContract(Page page, Etemployee etemployee);
	//合同到期
	public IPage<Map> getContractExpire(Page page, Etemployee etemployee);
	//合同到期列表
	public IPage<Map> getContractExpire2(Page page, Etemployee etemployee);
	//待入职
	public IPage<Map> getEtstaffRegisterForRuZhi(Page page, Etemployee etemployee);
	//待转正
	public IPage<Map> getEtemployeeWaitingForCorrection(Page page, Etemployee etemployee);
	//待离职
	public IPage<Map> getEtleaveRegisterWaitingForLeave(Page page, Etemployee etemployee);
	/*
	 * 性别报表
	 */
	public List<Map> getEtemployeeForGender(Etemployee etemployee);
	/*性别报表all*/
	public Map getEtemployeeForGenderALL(Etemployee etemployee);
	/*年龄段统计*/
	public Map getEtemployeeForAge(Etemployee etemployee);
	/*员工关怀*/
	public List<Map> getEmployeeCare(Etemployee etemployee);
	/*入职周年*/
	public List<Map> getEmployeeEntrAnniversary(Etemployee etemployee);

	/*
		手机端统计报表
	 */
	//待入职
	public Map getEtstaffRegisterCount(Etemployee etemployee);
	//待离职
	public Map getEtleaveRegisterCount(Etemployee etemployee);
	//政治面貌统计
	public List<Map> getEmployeeForParty(Etemployee etemployee);
	/*各月份人员流动统计*/
	//入职
	public List<Map> getEmployeeFlowRuZhiByMonth(EtstaffAll etstaffAll);
	//离职
	public List<Map> getEmployeeLiZhiFlowByMonth(EtleaveAll etleaveAll);
	//离职原因报表
	public List<Map> getEmployeeLiZhiByReason(EtleaveAll etleaveAll);
	public Map getEmployeeLiZhiByReasonCount(EtleaveAll etleaveAll);
	/*
	工龄报表
 	*/
	public Map getEtemployeeForGAge(Etemployee etemployee);
	/*
  	婚姻状况报表
	 */
	public List<Map> getEmployeeByMarriage(Etemployee etemployee);
	public Map getEmployeeByMarriageCount(Etemployee etemployee);
	/*
	籍贯统计人数
 	*/
	public List<Map> getEmployeeByResident(Etemployee etemployee);
	public Map getEmployeeByResidentCount(Etemployee etemployee);
	//通用的统计公司所有人数
	public Map getEmployeeByCommonCount(Etemployee etemployee);
	//员工转正
	public IPage<Map> getEmployeeZhuangZheng(Page page,Etemployee etemployee);
}
