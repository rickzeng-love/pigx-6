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

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtleaveAll;
import com.pig4cloud.pigx.admin.entity.EtstaffAll;
import com.pig4cloud.pigx.admin.entity.ReminderEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 员工信息表
 *
 * @author gaoxiao
 * @date 2020-04-08 13:22:25
 */
@Mapper
public interface EtemployeeMapper extends BaseMapper<Etemployee> {

	/**
	 * 手机端获取公司通讯录
	 *
	 * @return R
	 */
	public List<Map> listAllOtcdEmpgradeWithPinyin(Etemployee etemployee);
	/**
	 * 入：1.黑名单校验；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeBlackList(Etemployee etemployee);
	/**
	 * 入：2.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeCDRelationship(Etemployee etemployee);
	/**
	 * 入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeCJRelationship(Etemployee etemployee);
	/**
	 * 入：4.员工工号已经存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeExistsEmployee(Etemployee etemployee);
	/**
	 * 入：4-1.员员工号号已存在，正在办理入职手续
	 *
	 * @return List<Map>
	 */
	public List<Map> listExistsEtstaffRegisterByBadge(Etemployee etemployee);

	/**
	 * 入：5.直接主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsReportTo(Etemployee etemployee);
	/**
	 * 入：6.职能主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsWFReportTo(Etemployee etemployee);
	/**
	 * 入：7.所选公司不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsComp(Etemployee etemployee);
	/**
	 * 入：8.所选公司已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeCompIsDisable(Etemployee etemployee);
	/**
	 * 入：9.所选部门不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsDept(Etemployee etemployee);
	/**
	 * 入：10.所选部门已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeDeptIsDisable(Etemployee etemployee);
	/**
	 * 入：11.所选岗位不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsJob(Etemployee etemployee);
	/**
	 * 入：12.所选岗位已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeJobIsDisable(Etemployee etemployee);
	/**
	 * 入：14.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeMobileRepeat(Etemployee etemployee);
	/**
	 * 入：14-1.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtstaffRegisterByMobile(Etemployee etemployee);

	/**
	 * 员工花名册
	 *
	 * @return IPage
	 */
	public IPage listEtEmployeeHMC(Page page, @Param("query") Etemployee etemployee);
	/**
	 * 人事概况报表
	 *
	 * @return List<Map>
	 */
	public Map listPersonnelProfileEmpStatus1(Etemployee etemployee);
	public Map listPersonnelProfileEmpStatus2(Etemployee etemployee);
	public Map listPersonnelProfileEmpStatus4(Etemployee etemployee);
	public Map listPersonnelProfileSum(Etemployee etemployee);
	public Map listPersonnelProfileEmpStatus5(Etemployee etemployee);
	public Map listPersonnelProfileEmpStatus6(Etemployee etemployee);
	public Map listPersonnelProfileEmpStatus9(Etemployee etemployee);
	/**
	 * 人事提醒报表
	 */
	//未签订合同
	public IPage<Map>listHasNoContract(Page page, @Param("query") Etemployee etemployee);
	//合同到期
	public IPage<Map> listContractExpire(Page page,  @Param("query")Etemployee etemployee);
	//合同到期列表
	public IPage<Map> listContractExpire2(Page page,  @Param("query")Etemployee etemployee);
	//待入职
	public IPage<Map> listEtstaffRegisterForRuZhi(Page page, @Param("query") Etemployee etemployee);
	//待转正
	public IPage<Map> listEtemployeeWaitingForCorrection(Page page,  @Param("query") Etemployee etemployee);
	//待离职
	public IPage<Map> listEtleaveRegisterWaitingForLeave(Page page, @Param("query") Etemployee etemployee);
	/*
	 * 性别报表
	 */
	public List<Map> listEtemployeeForGender(Etemployee etemployee);
	/*性别报表all*/
	public Map listEtemployeeForGenderALL(Etemployee etemployee);
	/*年龄段统计*/
	public Map listEtemployeeForAge(Etemployee etemployee);
	/*员工关怀*/
	public List<Map> listEmployeeCare(Etemployee etemployee);
	/*入职周年*/
	public List<Map> listEmployeeEntrAnniversary(Etemployee etemployee);
	//待入职
	public Map listEtstaffRegisterCount(Etemployee etemployee);
	//待离职
	public Map listEtleaveRegisterCount(Etemployee etemployee);
	//已离职
	public Map listEtleaveAllCount(Etemployee etemployee);

	//政治面貌统计
	public List<Map> listEmployeeForParty(Etemployee etemployee);
	/*各月份人员流动统计*/
	//入职
	public List<Map> listEmployeeFlowRuZhiByMonth(EtstaffAll etstaffAll);
	//离职
	public List<Map> listEmployeeLiZhiFlowByMonth(EtleaveAll etleaveAll);
	//离职原因报表
	public List<Map> listEmployeeLiZhiByReason(EtleaveAll etleaveAll);
	public Map listEmployeeLiZhiByReasonCount(EtleaveAll etleaveAll);
	/*
	工龄报表
	 */
	public Map listEtemployeeForGAge(Etemployee etemployee);
	/*
	  婚姻状况报表
	 */
	public List<Map> listEmployeeByMarriage(Etemployee etemployee);
	public Map listEmployeeByMarriageCount(Etemployee etemployee);

	/*
  籍贯统计人数
 	*/
	public List<Map> listEmployeeByResident(Etemployee etemployee);
	public Map listEmployeeByResidentCount(Etemployee etemployee);
	//通用的统计公司所有人数
	public Map listEmployeeByCommonCount(Etemployee etemployee);
	//通用的统计公司所有人数
	public Map listEmployeeByCommonCount2(Etemployee etemployee);
	//员工转正
	public IPage<Map> listEmployeeZhuangZheng(Page page,@Param("query") Etemployee etemployee);
	//合并部门
	public void updateEmployeeForDepCombine(Map map);
	//合并岗位
	public void updateEmployeeForJobCombine(Map map);

	//员工转正
	public IPage<Map> listEmployeeForPageBySql(Page page,@Param("query") Etemployee etemployee);
	//员工花名册查询-PC端
	public IPage<Map> listEtEmployeeHMCBySqlForPc(Page page,@Param("query") Etemployee etemployee);
	public IPage<Map> listEtEmployeeHMCBySqlForPc2(Page page,@Param("query") Etemployee etemployee);
	public IPage<Map> listEtEmployeeHMCBySqlForPc3(Page page,@Param("query") Etemployee etemployee);
	//员工花名册查询员工详情
	public Map listEtEmployeeHMCDetailByEidSql(Etemployee etemployee);

	//待入职
	//public Map listEtstaffRegisterCount(Etemployee etemployee);
	//最近入职
	public Map listEtstaffAllRecentEntryCount(Etemployee etemployee);
	//放弃入职
	public Map listEtstaffAllGiveUpEntryCount(Etemployee etemployee);
	//待审批
	public Map listEtstaffRegisterApprovalPendingCount(Etemployee etemployee);
	//已通过
	public Map listEtstaffAllPassedCount(Etemployee etemployee);
	//已驳回
	public Map listEtstaffAllNotPassedCount(Etemployee etemployee);
	//代办提醒
	//01	入职
	@SqlParser(filter = true)
	public List<Map> listReminderEvent01(ReminderEvent reminderEvent);
	//02	生日
	@SqlParser(filter = true)
	public List<Map> listReminderEvent02(ReminderEvent reminderEvent);
	//03	入职周年
	@SqlParser(filter = true)
	public List<Map> listReminderEvent03(ReminderEvent reminderEvent);
	//04	转正
	@SqlParser(filter = true)
	public List<Map> listReminderEvent04(ReminderEvent reminderEvent);
	//05	离职
	@SqlParser(filter = true)
	public List<Map> listReminderEvent05(ReminderEvent reminderEvent);
	//06	试用到期
	@SqlParser(filter = true)
	public List<Map> listReminderEvent06(ReminderEvent reminderEvent);
	//07	实习到期
	@SqlParser(filter = true)
	public List<Map> listReminderEvent07(ReminderEvent reminderEvent);
	//08	合同到期
	@SqlParser(filter = true)
	public List<Map> listReminderEvent08(ReminderEvent reminderEvent);
	//09	即将退休-->
	@SqlParser(filter = true)
	public List<Map> listReminderEvent09(ReminderEvent reminderEvent);
	//10	合同到期-->
	@SqlParser(filter = true)
	public List<Map> listReminderEvent10(ReminderEvent reminderEvent);

}
