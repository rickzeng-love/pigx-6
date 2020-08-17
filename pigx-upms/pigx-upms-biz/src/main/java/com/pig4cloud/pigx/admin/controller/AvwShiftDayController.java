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

package com.pig4cloud.pigx.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.AtshiftDetailsMapper;
import com.pig4cloud.pigx.admin.mapper.AvwShiftDayMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-06-22 11:43:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/avwshiftday" )
@Api(value = "avwshiftday", tags = "VIEW管理")
public class AvwShiftDayController {

	private final AvwShiftDayService avwShiftDayService;
	private final AvwShiftDayMapper avwShiftDayMapper;
	private final SystmessageMapper systmessageMapper;
	private final AtcdAgentmodeService atcdAgentmodeService;
	private final AtshiftDetailsMapper atshiftDetailsMapper;
	private final AtimportDshandsettingService atimportDshandsettingService;
	private final AtattendPeriodsService atattendPeriodsService;
	private final AtattendPeriodsAllService atattendPeriodsAllService;
	private final AtcdAttendperiodService atcdAttendperiodService;
	private final SystpaystditemMapper systpaystditemMapper;
	private final AtshiftService atshiftService;
	private final AtshiftAllService atshiftAllService;
	private final AtshiftDayService atshiftDayService;
	private final AtshiftDayAllService atshiftDayAllService;
	private final AtshiftDetailsService atshiftDetailsService;
	private final AtshiftDetailsAllService atshiftDetailsAllService;
	private final AtshiftStatistService atshiftStatistService;
	private final AtshiftStatistAllService atshiftStatistAllService;
	private final AtcardlostRegisterService atcardlostRegisterService;
	private final AtcardlostAllService atcardlostAllService;
	private final AtcardRecordService atcardRecordService;
	private final AtcardRecordAllService atcardRecordAllService;
	private final AtplanRegService atplanRegService;
	private final AtshiftTotalService atshiftTotalService;
	private final AtshiftTotalAllService atshiftTotalAllService;
	private final AtplanTimewageService atplanTimewageService;
	private final AtregsectRegisterService atregsectRegisterService;
	private final AtregsectAllService atregsectAllService;
	private final AtregsectleavelongRegisterService atregsectleavelongRegisterService;
	private final AtregsectleavelongAllService atregsectleavelongAllService;
	private final AtcardInService atcardInService;
	private final AtcardInAllService atcardInAllService;
	private final AtregtimeRegisterService atregtimeRegisterService;
	private final AtregtimeAllService atregtimeAllService;
	private final AtregtimeleavelongRegisterService atregtimeleavelongRegisterService;
	private final AtregtimeleavelongAllService atregtimeleavelongAllService;
	private final AtshiftWorkService atshiftWorkService;
	private final AtshiftWorkAllService atshiftWorkAllService;
	private final AtplanRangeService atplanRangeService;
	private final AtplanRangeAllService atplanRangeAllService;
	private final OatattendancedetailService oatattendancedetailService;
	private final OatattendancedetailAllService oatattendancedetailAllService;
	private final AtgroupShiftService atgroupShiftService;
	private final AtgroupshiftAllService atgroupshiftAllService;
	private final AtplanTimewageAllService atplanTimewageAllService;
	private final AtplanRegAllService atplanRegAllService;
	private final AtcdCalendartypeService atcdCalendartypeService;
	private final AtstatusService atstatusService;
	private final AtshiftsupplyWorkService atshiftsupplyWorkService;
	private final AtshiftsupplyWorkAllService atshiftsupplyWorkAllService;
	private final AtattendLockstatusService atattendLockstatusService;
	private final AtstaffRegisterService atstaffRegisterService;
	private final AtstaffAllService atstaffAllService;
	private final AtgroupService atgroupService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param avwShiftDay VIEW
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getAvwShiftDayPage(Page page, AvwShiftDay avwShiftDay) {
		return R.ok(avwShiftDayService.page(page, Wrappers.query(avwShiftDay)));
	}

	/**
	 * 刷卡导入
	 *
	 * @return R
	 */
	@ApiOperation(value = "刷卡导入", notes = "刷卡导入")
	@SysLog("刷卡导入")
	@PostMapping("/aSPImportSQLHandLog")
	@Transactional
	public R aSPImportSQLHandLog(@RequestBody AtimportDshandsetting atimportDshandsetting) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		//AtimportDshandsetting atimportDshandsetting = new AtimportDshandsetting();
		atimportDshandsetting.setCorpcode(corpcode);
		atimportDshandsetting.setCorpid(corpid);
		atimportDshandsetting.setDsid(1);
		atimportDshandsetting.setEid(pigxUser.getEid());
		atimportDshandsetting.setName(pigxUser.getName());
		atimportDshandsettingService.save(atimportDshandsetting);
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		Map map = new HashMap();
		map.put("id", atimportDshandsetting.getId());
		map.put("corpid", pigxUser.getCorpid());
		String message = null;
		//调用确认的存储过程
		avwShiftDayMapper.aSPImportSQLHandLog(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}

	/**
	 * 每日考勤查询
	 *
	 * @param page        分页对象
	 * @param avwShiftDay 每日考勤查询
	 * @return 测试通过
	 */
	@ApiOperation(value = "每日考勤查询", notes = "每日考勤查询")
	@PostMapping("/getAvwShiftDayPageBySql")
	public R getAvwShiftDayPageBySql(Page page, @RequestBody(required = false) AvwShiftDay avwShiftDay) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if (StringUtils.isEmpty(avwShiftDay)) {
			avwShiftDay = new AvwShiftDay();
		}
		avwShiftDay.setCorpcode(corpcode);
		return R.ok(avwShiftDayMapper.listAvwShiftDayPageBySql(page, avwShiftDay));
	}

	/**
	 * 考勤批量确认
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤批量确认", notes = "考勤批量确认")
	@SysLog("考勤批量确认")
	@PostMapping("/aSPAnalResuBatchConfirm")
	@Transactional
	public R aSPAnalResuBatchConfirm(@RequestBody AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		Map map = new HashMap();
		map.put("depid", avwShiftDay.getDepid());
		map.put("badge", avwShiftDay.getBadge());
		map.put("begindate", avwShiftDay.getBegintime());
		map.put("enddate", avwShiftDay.getEndtime());
		map.put("userid", pigxUser.getId());
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//批量考勤确认
		avwShiftDayMapper.aSPAnalResuBatchConfirm(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}


	/**
	 * 考勤确认
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤确认", notes = "考勤确认")
	@SysLog("考勤确认")
	@PostMapping("/aSPAnalResuConfirm")
	@Transactional
	public R aSPAnalResuConfirm(@RequestBody AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		Map map = new HashMap();
		map.put("term", avwShiftDay.getTerm());
		map.put("eid", avwShiftDay.getEid());
		map.put("agentmode", aid);
		map.put("userid", pigxUser.getId());
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//考勤确认
		avwShiftDayMapper.aSPAnalResuConfirm(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}

	/**
	 * 考勤取消
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤取消", notes = "考勤取消")
	@SysLog("考勤取消")
	@PostMapping("/aSPAnalResuCancel")
	@Transactional
	public R aSPAnalResuCancel(@RequestBody AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		Map map = new HashMap();
		map.put("term", avwShiftDay.getTerm());
		map.put("eid", avwShiftDay.getEid());
		map.put("agentmode", aid);
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//考勤取消
		avwShiftDayMapper.aSPAnalResuCancel(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}

	/**
	 * 考勤分析
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤分析", notes = "考勤分析")
	@SysLog("考勤分析")
	@PostMapping("/aSPAnalysisSimple")
	@Transactional
	public R aSPAnalysisSimple(@RequestBody AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		Map map = new HashMap();
		map.put("depid", avwShiftDay.getDepid());
		map.put("badge", avwShiftDay.getBadge());
		map.put("begindate", avwShiftDay.getBegintime());
		map.put("enddate", avwShiftDay.getEndtime());
		map.put("userid", pigxUser.getId());
		map.put("agentmode", aid);
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//批量考勤确认
		avwShiftDayMapper.aSPAnalysisSimple(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}


	/**
	 * 考勤初始化
	 * @return R
	 *//*
	@ApiOperation(value = "考勤初始化", notes = "考勤初始化")
	@SysLog("考勤初始化" )
	@PostMapping("/aSPAttendMonthInit")
	@Transactional
	public R aSPAttendMonthInit(@RequestBody(required=false)  AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if(!StringUtils.isEmpty(atcdAgentmode)){
			aid = atcdAgentmode.getId();
		}
		if(StringUtils.isEmpty(avwShiftDay)){
			avwShiftDay = new AvwShiftDay();
		}
		Map map = new HashMap();
		map.put("term",avwShiftDay.getTerm());
		map.put("userid",pigxUser.getId());
		map.put("agentmode",aid);
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//考勤取消
		avwShiftDayMapper.aSPAttendMonthInit(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}
*/

	/**
	 * 考勤汇总
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤汇总", notes = "考勤汇总")
	@SysLog("考勤汇总")
	@PostMapping("/aSPAttendMonthCalc")
	@Transactional
	public R aSPAttendMonthCalc(@RequestBody(required = false) AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		if (StringUtils.isEmpty(avwShiftDay)) {
			avwShiftDay = new AvwShiftDay();
		}
		Map map = new HashMap();
		map.put("userid", pigxUser.getId());
		map.put("agentmode", aid);
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//考勤取消
		avwShiftDayMapper.aSPAttendMonthCalc(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}

	/**
	 * 考勤结算
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤结算", notes = "考勤结算")
	@SysLog("考勤结算")
	@PostMapping("/aSPAttendMonthSubmit")
	@Transactional
	public R aSPAttendMonthSubmit(@RequestBody(required = false) AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		if (StringUtils.isEmpty(avwShiftDay)) {
			avwShiftDay = new AvwShiftDay();
		}
		Map map = new HashMap();
		map.put("userid", pigxUser.getId());
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//考勤取消
		avwShiftDayMapper.aSPAttendMonthCalc(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}

	/**
	 * 考勤封账
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤封账", notes = "考勤封账")
	@SysLog("考勤封账")
	@PostMapping("/aSPAttendMonthClose")
	@Transactional
	public R aSPAttendMonthClose(@RequestBody(required = false) AvwShiftDay avwShiftDay) {

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if (!StringUtils.isEmpty(atcdAgentmode)) {
			aid = atcdAgentmode.getId();
		}
		if (StringUtils.isEmpty(avwShiftDay)) {
			avwShiftDay = new AvwShiftDay();
		}
		Map map = new HashMap();
		map.put("agentmode", aid);
		//{DepID},{Badge},{BeginDate},{EndDate},{AgentMode},{UserID}
		String message = null;
		//考勤取消
		avwShiftDayMapper.aSPAttendMonthClose(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(message);
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}
	}


	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param atshiftDetails 日明细结果
	 * @return
	 */
	@ApiOperation(value = "日明细结果", notes = "日明细结果")
	@PostMapping("/getAtShiftDetaiList")
	public R getAtShiftDetaiList(Page page, @RequestBody AtshiftDetails atshiftDetails) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atshiftDetails.setCorpcode(corpcode);
		return R.ok(atshiftDetailsMapper.listAtshiftDetailsByEid(atshiftDetails));
	}

	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param atshiftStatist 日统计结果
	 * @return
	 */
	@ApiOperation(value = "日统计结果", notes = "日统计结果")
	@PostMapping("/getAtShiftStatist")
	public R getAtShiftStatist(Page page, @RequestBody AtshiftStatist atshiftStatist) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atshiftStatist.setCorpcode(corpcode);
		return R.ok(avwShiftDayMapper.listatShiftStatistByEid(atshiftStatist));
	}

	/**
	 * 分页查询
	 *
	 * @param avwCardRecord 当日刷卡记录
	 * @return
	 */
	@ApiOperation(value = "当日刷卡记录", notes = "当日刷卡记录")
	@PostMapping("/getaVWCardRecordByEid")
	public R getaVWCardRecordByEid(@RequestBody AvwCardRecord avwCardRecord) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		avwCardRecord.setCorpcode(corpcode);
		return R.ok(avwShiftDayMapper.listaVWCardRecordByEid(avwCardRecord));
	}


	/**
	 * 分页查询
	 *
	 * @param atcardlostRegister 当日补卡登记
	 * @return
	 */
	@ApiOperation(value = "当日补卡登记", notes = "当日补卡登记")
	@PostMapping("/getatCardLostRegisterByEid")
	public R getatCardLostRegisterByEid(@RequestBody AtcardlostRegister atcardlostRegister) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atcardlostRegister.setCorpcode(corpcode);
		return R.ok(avwShiftDayMapper.listatCardLostRegisterByEid(atcardlostRegister));
	}


	/*   *//**
	 * 通过id查询VIEW
	 * @param term id
	 * @return R
	 *//*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) LocalDateTime term) {
        return R.ok(avwShiftDayService.getById(term));
    }
*/

	/**
	 * 新增VIEW
	 *
	 * @param avwShiftDay VIEW
	 * @return R
	 */
	@ApiOperation(value = "新增VIEW", notes = "新增VIEW")
	@SysLog("新增VIEW")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_avwshiftday_add')")
	public R save(@RequestBody AvwShiftDay avwShiftDay) {
		return R.ok(avwShiftDayService.save(avwShiftDay));
	}

	/*    *//**
	 * 修改VIEW
	 * @param avwShiftDay VIEW
	 * @return R
	 *//*
    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_avwshiftday_edit')" )
    public R updateById(@RequestBody AvwShiftDay avwShiftDay) {
        return R.ok(avwShiftDayService.updateById(avwShiftDay));
    }*/

	/*  *//**
	 * 通过id删除VIEW
	 * @param term id
	 * @return R
	 *//*
    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_avwshiftday_del')" )
    public R removeById(@PathVariable LocalDateTime term) {
        return R.ok(avwShiftDayService.removeById(term));
    }*/

	/**
	 * 考勤初始化
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤初始化", notes = "考勤初始化")
	@SysLog("考勤初始化")
	@PostMapping("/getAttendMonthInit")
	public R getAttendMonthInit(@RequestBody(required = false) Map paramMap) {

		R r = avwShiftDayService.getAttendMonthInit(paramMap);
		return r;

	}

	/**
	 * 考勤汇总
	 *
	 * @return R
	 */
	@ApiOperation(value = "考勤汇总", notes = "考勤汇总")
	@SysLog("考勤汇总")
	@PostMapping("/getAttendMonthCalcEasy")
	public R getAttendMonthCalcEasy(@RequestBody(required = false) Map paramMap) {

		R r = avwShiftDayService.getAttendMonthCalcEasy(paramMap);
		return r;

	}

	/**
	 * 结束本月考勤
	 *
	 * @return R
	 */
	@ApiOperation(value = "月汇总-结束本月考勤", notes = "月汇总-结束本月考勤")
	@SysLog("月汇总-结束本月考勤")
	@PostMapping("/getAttendMonthCloseEasy")
	public R getAttendMonthCloseEasy(@RequestBody(required = false) Map paramMap) {
		R r = avwShiftDayService.getAttendMonthCloseEasy(paramMap);
		return r;
	}

	//员工考勤开启
	@PostMapping("getStaffstartEasy")
	public R getStaffstartEasy(@RequestBody AtstaffRegister atstaffRegister2) {

		R r = avwShiftDayService.getStaffstartEasy(atstaffRegister2);
		return r;

	}
	//考勤分析
	@PostMapping("getAnalysisEasy")
	@Transactional(rollbackFor = Exception.class)
	public R getAnalysisEasy(@RequestBody Map map) {

		R r = avwShiftDayService.getAnalysisEasy(map);
		return r;

	}

	//员工排版 手动排班
	@ApiOperation(value = "员工排版 手动排班", notes = "员工排版 手动排班")
	@SysLog("员工排版 手动排班")
	@PostMapping("/getShiftHandleTurnRunEasy")
	public R getShiftHandleTurnRunEasy(@RequestBody(required = false) Map map) {
		R r = avwShiftDayService.getShiftHandleTurnRunEasy(map);
		return r;
	}



	public void getRegTimeLeaveCheckEasy(Integer id, Integer userId, Integer type) {
		String sql = "";
		String eid = "";
		String twid = "";
		String beginTime = "";
		String endTime = "";
		String divType = "";
		String sheetID = "";
		String agentMode = "";
		String beginDate = "";
		String endDate = "";

		//登记项目不能为空
		sql = "SELECT 1 FROM wftRegTimeLeaveLong_Register WHERE ID='" + id + "' AND IFNULL(TWID,0)=0";
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map.isEmpty()) {
			R.failed("登记项目不能为空");
		}

		if (type.equals(1)) {
			sql = "Update wftRegTimeLeaveLong_Register a,atCD_TimeWage b" +
					"   Set a.AttendID=b.AttendID  " +
					"   Where a.ID='" + id + "' And a.TWID=b.TWID " +
					"    And IFNULL(b.AttendID,'') <> '' And IFNULL(a.AttendID,'') = '';";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "SELECT EID,TWID,BeginTime,EndTime,1," +//   -- 默认跨月请假拆分
					"      IFNULL(SheetID,1) SheetID" +//    -- 默认为请假
					"   FROM wftRegTimeLeaveLong_Register " +
					"   WHERE ID='" + id + "';";
			Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
			eid = map1.get("EID").toString();
			twid = map1.get("TWID").toString();
			beginTime = map1.get("BeginTime").toString();
			endTime = map1.get("EndTime").toString();
			divType = "1";
			sheetID = map1.get("SheetID").toString();
		}
		// 更新数据
		if (type.equals(2)) {
			sql = "UPDATE wftBusinessTravel a,atCD_TimeWage b" +
					"   SET a.AttendID=b.AttendID,a.FreqType=b.FreqType,a.Unit=b.RegUnit,a.TravelExpendID=Null" +
					"   WHERE a.ID='" + id + "'  And IFNULL(a.AttendID,'')='';";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Select EID,BeginTime,EndTime,IFNULL(SheetID,1) SheetID,TWID,1" +
					"   from wftBusinessTravel " +
					"   Where ID='" + id + "'; ";
			Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
			eid = map1.get("EID").toString();
			twid = map1.get("TWID").toString();
			beginTime = map1.get("BeginTime").toString();
			endTime = map1.get("EndTime").toString();
			divType = "1";
			sheetID = map1.get("SheetID").toString();
		}

		if (type.equals(3)) {
			sql = "Select EID,BeginTime,EndTime,IFNULL(SheetID,1) SheetID,TWID,1" +
					"   INTO @EID,@BeginTime,@EndTime,@SheetID,@TWID,@DivType" +
					"   from wftRegTime_Register " +
					"   Where ID='" + id + "';";
			Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
			eid = map1.get("EID").toString();
			twid = map1.get("TWID").toString();
			beginTime = map1.get("BeginTime").toString();
			endTime = map1.get("EndTime").toString();
			divType = "1";
			sheetID = map1.get("SheetID").toString();

		}

		sql = "Select AID from atStatus Where EID='" + eid + "';";
		Map map2 = systpaystditemMapper.listSalaryTemplate2(sql);
		agentMode = map2.get("AID").toString();

		if (StringUtils.isEmpty(agentMode)) {
			R.failed("员工没有开启考勤");
		}

		sql = "CREATE TEMPORARY TABLE temp_aReg_Register" +
				"  (" +
				"   ID    INT," +
				"   EID   INT," +
				"   TWID   INT NOT NULL ," +
				"   xType     INT," +//-- 1表示按时间登记2表示按时段登记
				"   BeginTime  datetime  NULL ," +
				"   EndTime   datetime  NULL ," +
				"   Amount   DECIMAL(18,3) ," +
				"   Unit    VARCHAR(2)  NULL ," +
				"   SheetID  TINYINT" +// -- 1表示请假，2-表示加班
				"  ); ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE INDEX IX_aReg_Register ON temp_aReg_Register(EID,TWID,xType,BeginTime,EndTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE temp_atRegTime_Register " +
				"  ( " +
				"   ID        INT," +
				"   Term     datetime," +
				"   EID     INT," +
				"   FreqType    varchar(6)   NULL , " +
				"   TWID     smallint," +
				"   BeginTime datetime," +
				"   EndTime  datetime," +
				"   Amount   Decimal(10,2)," +
				"   Unit     varchar(2)  NULL ," +
				"   DivType  tinyint," +
				"   xType     tinyint," +//  -- 1表示按时间登记2表示按时段登记
				"   SheetID  tinyint " +// -- 1表示请假，2-表示加班
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE INDEX IX_atRegTime_Register On temp_atRegTime_Register(Term,EID,FreqType,TWID,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//请假时段
		sql = "CREATE TEMPORARY TABLE temp_aPlan_Reg" +
				"  ( " +
				"   Term          datetime," +
				"   EID        int," +
				"   Shift          varchar(10) ," +
				"   TWID        smallint," +
				"   RegTWID     smallint," +
				"   CalcType       smallint," +//     -- 1-按自然日计算，2-按工作日计算
				"   BeginTime    datetime," +
				"   EndTime     datetime," +
				"   Amount         Decimal(18,2)," +
				"   ScanBeginTime datetime," +
				"   ScanEndTime    datetime," +
				"   RestBegintime  datetime," +
				"   RestEndtime    datetime," +
				"   SeqID        int," +
				"   xType        tinyint," +//      -- 1表示按时间登记2表示按时段登记
				"   SheetID      tinyint " +//     -- 1表示请假，2-表示加班
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE INDEX  IX_aPlan_Reg On temp_aPlan_Reg (Term,EID,TWID,RegTWID,BeginTime,SeqID,xType);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		if (type.equals(1)) {
			//temp_atRegTime_Register是临时表
			sql = "INSERT INTO temp_atRegTime_Register(ID,EID,FreqType,TWID,BeginTime,EndTime,Amount,Unit,DivType,SheetID,xType)" +
					"   SELECT ID,EID,FreqType,TWID,BeginTime,EndTime,Amount,Unit,1,SheetID,1" +
					"   FROM wftRegTimeLeaveLong_Register " +
					"   WHERE ID='" + id + "';";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

		if (type.equals(2)) {
			sql = "INSERT INTO temp_atRegTime_Register(ID,EID,FreqType,TWID,BeginTime,EndTime,Amount,Unit,DivType,SheetID,xType)" +
					"   SELECT ID,EID,FreqType,TWID,BeginTime,EndTime,Amount,Unit,1,SheetID,1" +
					"   FROM wftBusinessTravel " +
					"   WHERE ID='" + id + "';";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

		if (type.equals(3)) {
			sql = "INSERT INTO temp_atRegTime_Register(ID,Term,EID,FreqType,TWID,BeginTime,EndTime,Amount,Unit)" +
					"   Select ID,Term,EID,FreqType,TWID,BeginTime,EndTime,Amount,Unit" +
					"   from wftRegTime_Register " +
					"   Where ID='" + id + "';";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

//		拆分并求出请假对应班次时段的开始日期，结束日期
//		call aSP_RegDivEasy (_ID, 1, _SheetID, @RetVal);

		//求出请假对应的班次时段的开始日期、结束日期
		sql = "SELECT Max(Term) BeginDate term" +
				"  FROM temp_aPlan_Reg " +
				"  WHERE EID = '" + eid + "'" +
				"   AND '" + beginTime + "' BETWEEN BeginTime AND EndTime;";
		Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
		beginDate = map1.get("BeginDate").toString();

		sql = "SELECT Min(Term) EndDate" +
				"  FROM temp_aPlan_Reg   " +
				"  WHERE  EID= @EID" +
				"   AND '" + endTime + "' BETWEEN BeginTime AND EndTime;";
		Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
		endDate = map3.get("EndDate").toString();

		sql = "Select 1 from temp_atRegTime_Register a,atPlan_Range o" +
				"      Where a.EID = o.EID" +
				"        and o.EndTime >= a.EndTime" +
				"        and a.ID='" + id + "'";
		Map map4 = systpaystditemMapper.listSalaryTemplate2(sql);

		sql = "Select 1 from atShift_Process e" +
				" Where IFNULL(e.Submit,0) = 1 And e.AgentMode=@AgentMode" +
				"  and @BeginDate Between e.BeginDate and e.EndDate";
		Map map5 = systpaystditemMapper.listSalaryTemplate2(sql);

		if ((!StringUtils.isEmpty(beginDate)) && StringUtils.isEmpty(endDate) && map4.isEmpty() && (!map5.isEmpty())) {
//			Select @EndDate=dtrun('day',@EndTime);感觉需要自定义函数转代码
			sql = "DTRUN('day','" + endTime + "')";
			Map map6 = systpaystditemMapper.listSalaryTemplate2(sql);
			endTime = map6.get("RET").toString();
		}

		//确认的检查
		//call aSP_RegTimeCheckSubEasy (_ID, 2,@BeginDate,@EndDate,@AgentMode,@RetVal);

		sql = "DROP TABLE temp_aReg_Register;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE temp_aPlan_Reg;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE temp_atRegTime_Register;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}

	public void getRegTimeCheckSubEasy(Integer id, String divMode, String xBeginDate, String xEndDate, Integer agentMode) {
		String sql = "";

		sql = "Select 1 from t_atRegTime_Register a Where a.EID is null and a.ID=" + id;
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map.isEmpty()) {
			R.failed("1300138");// 不知道提示信息
		}

		sql = "Select 1 from t_atRegTime_Register a Where a.TWID Is Null and a.ID=" + id;
		Map map2 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map2.isEmpty()) {
			R.failed("假期类型必须选择!");
		}

		sql = "Select 1 from t_atRegTime_Register a " +
				"     Where a.ID=" + id + " And (a.BeginTime Is Null Or a.EndTime Is Null Or IFNULL(a.Amount,0)<=0)";
		Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map3.isEmpty()) {
			R.failed("开始时间、结束时间不能为空、长度必须大于0!");
		}

		sql = "Select 1 from t_atRegTime_Register a Where a.BeginTime >= a.EndTime and a.ID=" + id;
		Map map4 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map4.isEmpty()) {
			R.failed("登记的开始时间不能大于等于结束时间!");
		}

		sql = "Select 1 from t_atRegTime_Register a" +
				"   Where a.FreqType =  2" +
				"    And Convert(a.BeginTime,CHAR) = Convert(a.EndTime,CHAR)" +
				"    and a.ID=" + id;
		Map map5 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map5.isEmpty()) {
			R.failed("请假方式为分散，开始结束时间的时间点部分不能相同!");
		}

		sql = "Select 1 from t_atRegTime_Register a,etEmployee b" +
				"   Where a.EID = b.EID and a.ID=" + id +
				"    And (select datediff(b.JoinDate,'" + xBeginDate + "')>0 OR datediff('" + xBeginDate + "',IFNULL(b.LeaveDate,'9990-12-31'))>0)";
		Map map6 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map6.isEmpty()) {
			R.failed("请假方式为分散，开始结束时间的时间点部分不能相同!");
		}

		sql = "Select 1 from t_atRegTime_Register a,etEmployee b" +
				"   Where a.EID = b.EID And (datediff(b.JoinDate,'" + xEndDate + "')>0 OR datediff('" + xEndDate + "',IFNULL(b.LeaveDate,'9990-12-31'))>0) and a.ID=" + id;
		Map map7 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map7.isEmpty()) {
			R.failed("开始时间大于此员工的离职日期,请到考勤变动窗口中进行离职员工的处理!");
		}

		sql = "Select 1 from t_atRegTime_Register a,etEmployee b" +
				"   Where a.EID = b.EID And (datediff(b.JoinDate,IFNULL(b.LeaveDate,'" + xEndDate + "'))>0 OR datediff(b.JoinDate,IFNULL('" + xEndDate + "',IFNULL(b.LeaveDate,'9990-12-31')))>0) and a.ID=" + id;
		Map map8 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map8.isEmpty()) {
			R.failed("结束时间大于此员工的离职日期,请到考勤变动窗口中进行离职员工的处理!");
		}

		sql = "Select 1 from t_atRegTime_Register a,aVW_AttendStatusLis f" +
				"   Where" +
				"    a.EID = f.EID" +
				"    And f.ATStatus not in   ('0','1','2')" +
				"    And  " +
				"    Case  " +
				"    When datediff(f.BeginDate,'" + xBeginDate + "') >  0" +
				"    Then f.BeginDate " +
				"    Else '" + xBeginDate + "' " +
				"    End  <= " +
				"    Case  " +
				"    When datediff(f.EndDate,'" + xEndDate + "') >  0" +
				"    Then '" + xEndDate + "' " +
				"    Else f.EndDate " +
				"    End  " +
				"    and a.ID=" + id;
		Map map9 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map9.isEmpty()) {
			R.failed("登记的日期范围内的考勤状态只能是0,1,2");
		}

		sql = "Select 1 from t_atRegTime_Register a Where IFNULL(a.Amount,0) <= 0 and a.ID=" + id;
		Map map10 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map10.isEmpty()) {
			R.failed("登记的数值不能小于等于0");
		}

		sql = "Select 1 from t_atRegTime_Register a,atCD_TimeWage c,atCD_RoundID d " +
				"        Where a.TWID = c.TWID And c.RegRoundID = d.ID And IFNULL(a.Amount,0) < IFNULL(ABS(d.MinNum),0) " +
				"          and a.ID=" + id;
		Map map11 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map11.isEmpty()) {
			R.failed("登记的数值不能小于最小值");
		}

		sql = "Select 1 from t_atRegTime_Register a,atCD_TimeWage c,atCD_RoundID d " +
				"      Where a.TWID = c.TWID And c.RegRoundID = d.ID " +
				"        And d.Carry is not null  " +
				"        And Cast(a.Amount*100 As SIGNED) % Cast(ABS(d.Carry)*100 As SIGNED)>0 " +
				"        and a.ID=" + id;
		Map map12 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map12.isEmpty()) {
			R.failed("登记的数值不是按定义的近似值来累进的");
		}

		sql = "Select 1 from t_atRegTime_Register a,atCD_TimeWage c,atCD_RoundID d " +
				"      Where a.TWID = c.TWID And c.RegRoundID = d.ID " +
				"        And IFNULL(a.Amount,0) > d.MaxNum " +
				"      and a.ID=" + id;
		Map map13 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map13.isEmpty()) {
			R.failed("登记的数值不能大于最大值");
		}

		sql = "Select 1 from t_atRegTime_Register a " +
				"      Where a.TWID In(2119,2120) And a.ID='" + id + "' And a.Amount>aFN_GetBalCurrent(a.EID,a.TWID,a.BeginTime)";
		Map map14 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map14.isEmpty()) {
			R.failed("登记的有薪假不可透支，请检查后再确认！");
		}

		sql = "Select 1 from atPlan_Reg i,t_aPlan_Reg nt_,aVW_TimeWageReg g,atCD_SectTWIDRelaPara t " +
				"     Where " +
				"        i.EID = nt_.EID " +
				"        And g.AttendID is not null  " +
				"        And g.RegUnit is not null  " +
				"        And g.TWID %  100 " +
				"        <>  0 " +
				"        And t.SheetID in  ('2') " +
				"        And ( t.TWID = g.TWID " +
				"        / 100 " +
				"        * 100 " +
				"        OR t.TWID = g.TWID ) " +
				"        And nt_.TWID = g.TWID " +
				"        And   " +
				"        Case   " +
				"        When i.BeginTime > nt_.BeginTime " +
				"        Then i.BeginTime  " +
				"        Else nt_.BeginTime  " +
				"        End  <  " +
				"        Case   " +
				"        When i.EndTime > nt_.EndTime " +
				"        Then nt_.EndTime  " +
				"        Else i.EndTime  " +
				"        End";
		Map map15 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map15.isEmpty()) {
			R.failed("这段时间已经进行考勤登记");
		}

		sql = "Select 1 from atPlan_Reg i,t_aPlan_Reg nt_ " +
				"     Where i.EID = nt_.EID " +
				"        And i.TWID <>  2124 " +
				"        And   " +
				"        Case   " +
				"        When i.BeginTime > nt_.BeginTime " +
				"        Then i.BeginTime  " +
				"        Else nt_.BeginTime  " +
				"        End  <  " +
				"        Case   " +
				"        When i.EndTime > nt_.EndTime " +
				"        Then nt_.EndTime  " +
				"        Else i.EndTime  " +
				"        End";
		Map map16 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map16.isEmpty()) {
			R.failed("这段时间已经进行考勤登记");
		}

		sql = "Select 1 from atPlan_Reg i,t_aPlan_Reg nt_  " +
				"   Where i.EID = nt_.EID  " +
				"    And nt_.TWID = i.TWID  " +
				"    And    " +
				"    Case    " +
				"    When i.BeginTime > nt_.BeginTime  " +
				"    Then i.BeginTime   " +
				"    Else nt_.BeginTime   " +
				"    End  <   " +
				"    Case    " +
				"    When i.EndTime > nt_.EndTime  " +
				"    Then nt_.EndTime   " +
				"    Else i.EndTime   " +
				"    End ";
		Map map17 = systpaystditemMapper.listSalaryTemplate2(sql);
		if (!map17.isEmpty()) {
			R.failed("这段时间已经进行考勤登记");
		}

	}

	public void getRegDivEasy(Integer id, Integer xType, Integer sheetID) {

		String sql = "";

		sql = "DROP TABLE IF EXISTS `t_aPlan_TimeWage`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE `t_aPlan_TimeWage`" +
				"  (" +
				"   Term   datetime," +
				"   EID    INT," +
				"   TWID   SMALLINT," +
				"   Shift  VARCHAR(10)," +
				"   BeginTime datetime," +
				"   EndTime   datetime" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aPlan_TimeWage ON t_aPlan_TimeWage(Term,EID,TWID,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aPlan_Range`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE `t_aPlan_Range`" +
				"  (" +
				"   Term       datetime," +
				"   EID        INT," +
				"   GroupID    varchar(10)," +
				"   Shift      VARCHAR(10)," +
				"   DayType    TINYINT," +
				"   WorkNum    TINYINT," +
				"   BeginTime  datetime," +
				"   EndTime    datetime," +
				"   BeginTime2 datetime," +
				"   EndTime2   datetime," +
				"   BeginTime3 datetime," +
				"   EndTime3   datetime   " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aPlan_Range On t_aPlan_Range (Term,EID,GroupID,Shift,DayType,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aStatus_Ins`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aStatus_Ins" +
				"  (" +
				"   EID int," +
				"   BeginDate datetime," +
				"   EndDate datetime" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aStatus_Ins On t_aStatus_Ins (EID,BeginDate,EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "SELECT" +
				" WorkTWID,WorkATWID,WorkBTWID,WorkRTWID,WBHours,WAHours,RBHours,RAHours" +
				" FROM atCD_TimeWageInitPara;";
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);

		String workTWID = map.get("WorkTWID").toString();
		String workATWID = map.get("WorkATWID").toString();
		String workBTWID = map.get("WorkBTWID").toString();
		String workRTWID = map.get("WorkRTWID").toString();
		String wBHours = map.get("WBHours").toString();
		String wAHours = map.get("WAHours").toString();
		String rBHours = map.get("RBHours").toString();
		String rAHours = map.get("RAHours").toString();
		String from = "";
		String to = "";
		String eid = "";

		if (xType == 1) {
			sql = "SELECT CONVERT(DATE_ADD(BeginTime,INTERVAL -1 DAY),CHAR(10))From,CONVERT(EndTime+1,CHAR(10))To,EID" +
					"   FROM t_atRegTime_Register;";
			Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
			from = map1.get("From").toString();
			to = map1.get("To").toString();
			eid = map1.get("EID").toString();
		}

		sql = "insert into t_aStatus_Ins (EID,BeginDate,EndDate) values(" + eid + "," + from + "," + to + ")";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert t_aPlan_Range(Term,GroupID,Shift,EID,DayType,WorkNum,BeginTime,EndTime,BeginTime2,EndTime2,BeginTime3,EndTime3)" +
				"  Select Term,GroupID,Shift,EID,DayType,WorkNum,BeginTime,EndTime,BeginTime2,EndTime2,BeginTime3,EndTime3" +
				"  from atPlan_Range a" +
				"  Where  Exists(Select 1 from t_aStatus_Ins  b" +
				"     Where a.EID =b.EID" +
				"      and a.Term between b.BeginDate and b.EndDate );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//集中，按时间登记
		sql = "insert t_aReg_Register (ID,xType,EID,TWID,BeginTime,EndTime,Amount,Unit,SheetID) " +
				"  Select  a.ID,1,a.EID,a.TWID,a.BeginTime,a.EndTime,Amount,Unit,a.SheetID" +
				"  from t_atRegTime_Register a " +
				"  Where ID='" + id + "' and a.FreqType='1';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//分散,按时间登记
		sql = "INSERT INTO t_aReg_Register (ID,xType,EID,TWID,BeginTime,EndTime,Amount,Unit,SheetID) " +
				"  SELECT a.ID,1,a.EID,a.TWID,CONCAT(Convert(b.Term,CHAR(10)),' ',Convert(a.BeginTime,CHAR(5))) as BeginTime ," +
				"   Case  When  Convert(a.BeginTime,CHAR(5)) > Convert(a.EndTime,CHAR(5)) " +
				"    Then CONCAT(Convert(b.Term+1,CHAR(10)),' ',Convert(a.EndTime,CHAR(5)))" +
				"    Else  CONCAT(Convert(b.Term,CHAR(10)),' ',Convert(a.EndTime,CHAR(5)))" +
				"   End  EndTime," +
				"   a.Amount," +
				"   a.Unit,a.SheetID" +
				"  FROM t_atRegTime_Register a ,atCalendar b" +
				"  WHERE a.ID=" + id + " And b.AgentMode=(Select AID from atStatus Where EID=a.EID LIMIT 1)" +
				"   AND b.xCategory=0" +
				"   AND datediff(b.Term,a.BeginTime)>=0 " +
				"   AND datediff(a.EndTime,b.Term)>=0 " +
				"   AND " +
				"   Case  When  Convert(a.BeginTime,CHAR(5)) > Convert(a.EndTime,CHAR(5)) " +
				"    Then CONCAT(Convert(b.Term+1,CHAR(10)),' ',Convert(a.EndTime,CHAR(5)))" +
				"    Else  CONCAT(Convert(b.Term,CHAR(10)),' ',Convert(a.EndTime,CHAR(5)))" +
				"   End <=a.EndTime " +
				"   and a.FreqType='2';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//根据班次时段来生成每天每人的班次时段,一天一次上下班
		sql = "insert t_aPlan_TimeWage(Term,EID,Shift,Begintime,EndTime,TWID)  " +
				"  select a.Term,a.EID,a.Shift,a.begintime,a.endtime," + workTWID +
				"  from t_aPlan_Range a" +
				"  where a.Shift<>'R' And a.Term between '" + from + "' and '" + to +
				"'   and exists(select 1 from t_aStatus_ins c " +
				"     where a.EID=c.EID " +
				"      and a.Term between c.BeginDate and c.EndDate  " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//一天2次上下班
		sql = "insert t_aPlan_TimeWage(Term,EID,Shift,Begintime,EndTime,TWID)  " +
				"  select a.Term,a.EID,a.Shift,a.begintime2,a.endtime2," + workTWID +
				"  from t_aPlan_Range a" +
				"  where a.Shift<>'R' And a.WorkNum=2 And a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins c " +
				"     where a.EID=c.EID " +
				"      and a.Term between c.BeginDate and c.EndDate  " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//一天3次上下班
		sql = "insert t_aPlan_TimeWage(Term,EID,Shift,Begintime,EndTime,TWID)  " +
				"  select a.Term,a.EID,a.Shift,a.begintime2,a.endtime2," + workTWID +
				"  from t_aPlan_Range a" +
				"  where a.Shift<>'R' And a.WorkNum=3 And a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins c " +
				"     where a.EID=c.EID " +
				"      and a.Term between c.BeginDate and c.EndDate  " +
				"    )" +
				"  Union" +
				"  select a.Term,a.EID,a.Shift,a.begintime3,a.EndTime3," + workTWID +
				"  from t_aPlan_Range a" +
				"  where a.Shift<>'R' And a.WorkNum=3 And a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins c " +
				"     where a.EID=c.EID " +
				"      and a.Term between c.BeginDate and c.EndDate  " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成休息班次
		sql = "insert t_aPlan_TimeWage(Term,EID,Shift,Begintime,EndTime,TWID)  " +
				"  select a.Term,a.EID,a.Shift,a.Term,DateAdd(dd,1,a.Term)," + workRTWID +
				"  from t_aPlan_Range a" +
				"  where a.Shift='R' And a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins c " +
				"     where a.EID=c.EID " +
				"      and a.Term between c.BeginDate and c.EndDate  " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成登记记录
		sql = "Insert t_aPlan_Reg(Term,EID,Shift,RegTWID,TWID,BeginTime,EndTime,SeqID,xType,SheetID)" +
				"  Select a.Term,a.EID,a.Shift,c.TWID,c.TWID," +
				"   case  when a.begintime>=c.begintime  " +
				"    then a.begintime  " +
				"    else c.begintime " +
				"   end," +
				"   case  when a.endtime>=c.endtime   " +
				"    then c.endtime  " +
				"    else a.endtime " +
				"   end," +
				"   c.ID," +
				"   c.xType,c.SheetID" +
				"  from t_aPlan_TimeWage a,t_aReg_Register c" +
				"  Where a.EID=c.EID" +
				"   and a.TWID=" + workTWID +
				"   and case when a.begintime>=c.begintime   " +
				"     then a.begintime  " +
				"     else c.begintime " +
				"     end <    " +
				"     case  when a.endtime>=c.endtime   " +
				"     then c.endtime  " +
				"     else a.endtime " +
				"     end  ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新请假核算方式
		sql = "Update t_aPlan_Reg a,atCD_TimeWage b" +
				"  Set a.CalcType=IFNULL(b.CalcType,1)" +
				"  Where a.TWID=b.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新请假长度
		sql = "Update t_aPlan_Reg a" +
				"  Set a.Amount=TIMESTAMPDIFF(minute,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.EndTime, '%Y-%m-%d %H:%i'))/60.0" +
				"  Where a.Shift<>'R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新请假长度
		sql = "Update t_aPlan_Reg a" +
				"  Set a.Amount=Case When a.CalcType=1 Then 1 Else 0 End" +//  -- 按自然日算休息日就算一天，按工作日算0天
				"  Where a.Shift='R' And a.SheetID=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新加班长度
		sql = "Update t_aPlan_Reg a" +
				"  Set a.Amount=TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.EndTime, '%Y-%m-%d %H:%i'))/60.0" +//   -- 加班申请，算小时
				"  Where a.Shift='R' And a.SheetID=2;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//有中间休息的，要减掉
		sql = "Update t_aPlan_Reg a,atShift_Type b" +
				"  Set a.RestBegintime=aFN_GenDateTime(a.Term,b.RestBegintime),a.RestEndtime=aFN_GenDateTime(a.Term,b.RestEndtime)" +
				"  Where a.shift=b.shift And b.Type=1 And IFNULL(b.IsRest,0)=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aPlan_Reg a" +
				"  Set a.Amount=(TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.EndTime, '%Y-%m-%d %H:%i'))-" +
				"TIMESTAMPDIFF(MINUTE,case when a.Begintime>=a.RestBegintime" +
				"     then date_format(a.Begintime, '%Y-%m-%d %H:%i')" +
				"     else date_format(a.RestBegintime, '%Y-%m-%d %H:%i')" +
				"    end," +
				"    case when a.Endtime>= a.RestEndtime" +
				"     then date_format(a.RestEndtime, '%Y-%m-%d %H:%i')" +
				"     else date_format(a.Endtime, '%Y-%m-%d %H:%i')" +
				"    end" +
				"    )" +
				"         )/60.0" +
				"  Where IFNULL(a.RestBegintime,'')<>''" +
				"   And case when a.Begintime>=a.RestBegintime" +
				"    then a.Begintime   " +
				"    else a.RestBegintime" +
				"   end  <  " +
				"   case when a.Endtime>= a.RestEndtime" +
				"    then a.RestEndtime" +
				"    else a.Endtime   " +
				"   end ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE t_aPlan_TimeWage;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE t_aPlan_Range;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE t_aStatus_Ins;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}

	@ApiOperation(value = "getImportSQLHandLog", notes = "getImportSQLHandLog")
	@SysLog("getImportSQLHandLog")
	@PostMapping("/getImportSQLHandLog")
	@Transactional
	public R getImportSQLHandLog(@RequestParam Integer id) {
		this.getImportLog("SQL", id, 1);
		return R.ok("成功");
	}

	public void getImportLog(String dsType, Integer id, Integer importMode) {
		String sql;
		String allNum = "";
		String validNum = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		sql = "DROP TABLE IF EXISTS t_aRecord";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aRecord" +
				"  (" +
				"   ID int  ," +
				"   CardTxt varchar(4000)," +
				"   MacID varchar(4)," +
				"   CardID varchar(20)," +
				"   EID int," +
				"   xDateTime Datetime," +
				"   InOutFlag varchar(4)," +
				"   ErrMsg int  " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aRecord_EID ON t_aRecord (EID,xDateTime,InOutFlag);";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aRecord_CardID  ON t_aRecord (CardID,xDateTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);
		/*sql="SELECT DSID,Badge,EID,Name,UserID FROM atImport_DSHandSetting Where ID="+id+";";
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		String dsid=map.get("DSID").toString();
		String badge=map.get("Badge").toString();
		String eid=map.get("EID").toString();
		String name=map.get("Name").toString();
		String userId=map.get("UserID").toString();*/
		Integer dsid = 2;
		String badge = "";
		Integer eid = pigxUser.getEid();
		String name = pigxUser.getName();
		Integer userId = pigxUser.getId();
		sql = "SELECT MacType FROM atCD_CardImportDS Where ID=" + dsid + ";";
		Map map2 = systpaystditemMapper.listSalaryTemplate2(sql);
		String macType = map2.get("MacType").toString();

		//自动手动都不允许
		sql = "SELECT 1 FROM atCD_Constant WHERE ID=51 AND xValue='0'";
		List<Map> maps = systpaystditemMapper.listSalaryTemplate4(sql);
		if ((!maps.isEmpty()) || maps.size() > 0) {
			this.getImportRecord(dsType, id, importMode, macType);
			sql = "INSERT INTO atImport_Log(DSType,DataSource,Term,BeginTime,EndTime,EID,Badge,Name,ErrMsg,UserID,ImportMode)  " +
					"    SELECT '" + dsType + "',DSID,NOW(),BeginTime,EndTime,EID,Badge,Name, " + 1300113 + ", " + userId + ", " + importMode +
					"    FROM atImport_DSHandSetting" +
					"    WHERE ID = " + id + ";";
			systpaystditemMapper.listSalaryTemplate3(sql);
			sql = "SELECT 1 FROM atCD_MachinePara WHERE ID=" + macType + " AND Purpose='Card'";
			List<Map> maps1 = systpaystditemMapper.listSalaryTemplate4(sql);
			if ((!maps1.isEmpty()) || maps1.size() > 0) {
				sql = "INSERT atCard_Record (EID,MacID,CardID,xDateTime,InOutFlag,isDisable)  " +
						" SELECT EID,MacID,CardID,xDateTime,InOutFlag,0 " +
						" FROM t_aRecord a WHERE NOT EXISTS(SELECT 1 FROM atCard_Record b WHERE a.EID=b.EID AND" +
						" TIMESTAMPDIFF(MINUTE,date_format(a.xDateTime, '%Y-%m-%d %H:%i'),date_format(b.xDateTime, '%Y-%m-%d %H:%i')) = 0  " +
						"        AND (a.InOutFlag is null and b.InOutFlag is null or a.InOutFlag=b.InOutFlag)) AND ErrMsg IS NULL;";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}

			//处理文件的日志
			sql = "Select Sum(Case When ErrMsg is null Then 1  Else 0 End) ValidNum from t_aRecord;";
			Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
			if (map3 != null && (!map3.isEmpty())) {
				validNum = map3.get("ValidNum").toString();
			}

			/*IF @DSType = 'SQL' THEN
				Select Count(1) INTO @AllNum from t_aRecord;
			END IF; 有问题 不确定*/
			if (dsType.equals("SQL")) {
				sql = "Select Count(1) AllNum from t_aRecord;";
				Map map4 = systpaystditemMapper.listSalaryTemplate2(sql);
				allNum = map4.get("AllNum").toString();
			}

			sql = "select 1 from t_aRecord";
			List<Map> maps2 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps2.size() > 0) {
				sql = "Insert Into atImport_Log(Term,DataSource,begintime,endtime,EID,Badge,Name,RecordNum,ValidNum,ErrNum,UserID,ImportMode,DSType)   " +
						"    Select NOW()," + dsid + ",Min(xDateTime),Max(xDateTime)," + eid + "," + badge + "," + name + "," + allNum + "," + validNum + "," + allNum + "-" + validNum + "," + userId + "," + importMode + "," + dsType +
						"    from t_aRecord;";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}
			sql = "Drop table t_aRecord;";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}
		//手动不允许
		sql = "SELECT 1 FROM atCD_Constant WHERE ID=51 And xValue='2' And " + importMode + " =1";
		List<Map> maps1 = systpaystditemMapper.listSalaryTemplate4(sql);
		if (maps1.size() > 0) {
			sql = "INSERT INTO atImport_Log(DSType,DataSource,Term,BeginTime,EndTime,EID,Badge,Name,ErrMsg,UserID,ImportMode)   " +
					" SELECT '" + dsType + "',DSID,NOW(),BeginTime,EndTime,EID,Badge,Name, " + 1300114 + ", " + userId + ",  " + importMode +
					" FROM atImport_DSHandSetting " +
					" WHERE ID = " + id + ";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			this.getImportRecord(dsType, id, importMode, macType);
			;

			sql = "SELECT 1 FROM atCD_MachinePara WHERE ID=" + macType + " AND Purpose='Card'";
			List<Map> maps2 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps2.size() > 0) {
				sql = "INSERT atCard_Record (EID,MacID,CardID,xDateTime,InOutFlag,isDisable)   " +
						" SELECT EID,MacID,CardID,xDateTime,InOutFlag,0  " +
						" FROM t_aRecord a WHERE NOT EXISTS(SELECT 1 FROM atCard_Record b WHERE a.EID=b.EID AND" +
						" TIMESTAMPDIFF(MINUTE,date_format(a.xDateTime, '%Y-%m-%d %H:%i'),date_format(b.xDateTime, '%Y-%m-%d %H:%i')) = 0   " +
						"   AND (a.InOutFlag is null and b.InOutFlag is null or a.InOutFlag=b.InOutFlag)) AND ErrMsg IS NULL;";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}

			//处理文件的日志
			sql = "Select  Sum(Case When ErrMsg is null Then 1  Else 0 End) ValidNum " +
					" from t_aRecord;";
			Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
			if (map1 != null && (!map1.isEmpty())) {
				validNum = map1.get("ValidNum").toString();
			}

			if (dsType.equals("SQL")) {
				sql = "Select Count(1) AllNum from t_aRecord;";
				Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
				allNum = map3.get("AllNum").toString();
			}

			sql = "select 1 from t_aRecord";
			List<Map> maps3 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps3.size() > 0) {
				sql = "Insert Into atImport_Log(Term,DataSource,begintime,endtime,EID,Badge,Name,RecordNum,ValidNum,ErrNum,UserID,ImportMode,DSType)    " +
						"  Select NOW()," + dsid + ",Min(xDateTime),Max(xDateTime)," + eid + "," + badge + "," + name + "," + allNum + "," + validNum + "," + allNum + "-" + validNum + "," + userId + "," + importMode + "," + dsType +
						"  from t_aRecord";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}
			sql = "Drop table t_aRecord;";
			systpaystditemMapper.listSalaryTemplate3(sql);

		}

		//自动不允许
		sql = "Select 1 From atCD_Constant Where ID=51 And xValue='1' And " + importMode + " =2";
		List<Map> maps2 = systpaystditemMapper.listSalaryTemplate4(sql);
		if (maps2.size() > 0) {
			sql = "INSERT INTO atImport_Log(DSType,DataSource,Term,BeginTime,EndTime,EID,Badge,Name,ErrMsg,UserID,ImportMode)   " +
					"        SELECT '" + dsType + "',DSID,NOW(),BeginTime,EndTime,EID,Badge,Name,1300115," + userId + ",  " + importMode +
					"        FROM atImport_DSHandSetting " +
					"        WHERE ID = " + id + ";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			this.getImportRecord(dsType, id, importMode, macType);

			sql = "SELECT 1 FROM atCD_MachinePara WHERE ID=" + macType + " AND Purpose='Card'";
			List<Map> maps3 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps3.size() > 0) {
				sql = "INSERT atCard_Record (EID,MacID,CardID,xDateTime,InOutFlag,isDisable)   " +
						"  SELECT EID,MacID,CardID,xDateTime,InOutFlag,0  " +
						"  FROM t_aRecord a WHERE NOT EXISTS(SELECT 1 FROM atCard_Record b WHERE a.EID=b.EID AND" +
						" TIMESTAMPDIFF(MINUTE,date_format(a.xDateTime, '%Y-%m-%d %H:%i'),date_format(b.xDateTime, '%Y-%m-%d %H:%i')) = 0   " +
						"    AND (a.InOutFlag is null and b.InOutFlag is null or a.InOutFlag=b.InOutFlag)) AND ErrMsg IS NULL;";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}

			//处理文件的日志
			sql = "Select  Sum(Case When ErrMsg is null Then 1  Else 0 End) ValidNum" +
					"      from t_aRecord;";
			Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
			if (map3 != null && (!map3.isEmpty())) {
				validNum = map3.get("ValidNum").toString();
			}

			if (dsType.equals("SQL")) {
				sql = "Select Count(1) AllNum from t_aRecord;";
				Map map4 = systpaystditemMapper.listSalaryTemplate2(sql);
				allNum = map4.get("AllNum").toString();
			}

			sql = "select 1 from t_aRecord";
			List<Map> maps4 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps4.size() > 0) {
				sql = "Insert Into atImport_Log(Term,DataSource,begintime,endtime,EID,Badge,Name,RecordNum,ValidNum,ErrNum,UserID,ImportMode,DSType)    " +
						"   Select NOW()," + dsid + ",Min(xDateTime),Max(xDateTime)," + eid + "," + badge + "," + name + "," + allNum + "," + validNum + "," + allNum + "-" + validNum + "," + userId + "," + importMode + "," + dsType +
						"   from t_aRecord;";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}
			sql = "Drop table t_aRecord;";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

		if (StringUtils.isEmpty(macType)) {
			sql = "INSERT INTO atImport_Log(DSType,DataSource,Term,BeginTime,EndTime,EID,Badge,Name,ErrMsg,UserID,ImportMode)  " +
					" SELECT '" + dsType + "',DSID,NOW(),BeginTime,EndTime,EID,Badge,Name, 1300116, " + userId + "," + importMode +
					" FROM atImport_DSHandSetting" +
					" WHERE ID = " + id + ";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			this.getImportRecord(dsType, id, importMode, macType);

			sql = "SELECT 1 FROM atCD_MachinePara WHERE ID=" + macType + " AND Purpose='Card'";
			List<Map> maps3 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps3.size() > 0) {
				sql = "INSERT atCard_Record (EID,MacID,CardID,xDateTime,InOutFlag,isDisable)  " +
						" SELECT EID,MacID,CardID,xDateTime,InOutFlag,0 " +
						" FROM t_aRecord a WHERE NOT EXISTS(SELECT 1 FROM atCard_Record b WHERE a.EID=b.EID AND" +
						" TIMESTAMPDIFF(MINUTE,date_format(a.xDateTime, '%Y-%m-%d %H:%i'),date_format(b.xDateTime, '%Y-%m-%d %H:%i')) = 0  " +
						"  AND (a.InOutFlag is null and b.InOutFlag is null or a.InOutFlag=b.InOutFlag)) AND ErrMsg IS NULL;";
				systpaystditemMapper.listSalaryTemplate3(sql);

			}

			//处理文件的日志
			sql = "Select  Sum(Case When ErrMsg is null Then 1  Else 0 End) ValidNum" +
					"   from t_aRecord;";
			Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
			if (map3 != null && (!map3.isEmpty())) {
				validNum = map3.get("ValidNum").toString();
			}

			if (dsType.equals("SQL")) {
				sql = "Select Count(1) AllNum from t_aRecord;";
				Map map4 = systpaystditemMapper.listSalaryTemplate2(sql);
				allNum = map4.get("AllNum").toString();
			}

			sql = "select 1 from t_aRecord";
			List<Map> maps4 = systpaystditemMapper.listSalaryTemplate4(sql);
			if (maps4.size() > 0) {
				sql = "Insert Into atImport_Log(Term,DataSource,begintime,endtime,EID,Badge,Name,RecordNum,ValidNum,ErrNum,UserID,ImportMode,DSType)   " +
						"  Select NOW()," + dsid + ",Min(xDateTime),Max(xDateTime)," + eid + "," + badge + "," + name + "," + allNum + "," + validNum + "," + allNum + "-" + validNum + "," + userId + "," + importMode + "," + dsType +
						"  from t_aRecord;";
				systpaystditemMapper.listSalaryTemplate3(sql);
			}
			sql = "Drop table t_aRecord;";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

	}

	public void getImportRecord(String dsType, Integer id, Integer importMode, String macType) {
		if (dsType.equals("SQL")) {
			//对导入数据的处理
			this.getImportSQL2Record(id.toString(), importMode, macType);
		}
		//对数据的处理
		this.getImportRecordSub(macType);
	}

	public void getImportSQL2Record(String id, Integer importMode, String macType) {
		String sql;
		String beginTime = "";
		String endTime = "";

		sql = "Select DSID,Badge from atImport_DSHandSetting where ID=" + id + ";";
		Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
		String dsid = map1.get("DSID").toString();
		String badge = map1.get("Badge").toString();

		sql = "Select CardID CardNo from atStatus Where Badge='" + badge + "';";
		Map map2 = systpaystditemMapper.listSalaryTemplate2(sql);
		String cardNo = map2.get("CardNo").toString();

		if (importMode.equals(1)) {
			sql = "Select BeginTime,EndTime" +
					"   from atImport_DSHandSetting " +
					"   where ID=" + id + ";";
			Map map3 = systpaystditemMapper.listSalaryTemplate2(sql);
			beginTime = map3.get("BeginTime").toString();
			endTime = map3.get("EndTime").toString();
		}

		//不同的服务器/库上导入刷卡数据
		if (dsid.equals("1")) {
			//所要读取的刷卡数据的结束时间不能为空
			if (StringUtils.isEmpty(endTime)) {
				R.failed("所要读取的刷卡数据的结束时间不能为空");
			}
			//所要读取的刷卡数据的开始时间比其结束时间大，既没有需要读取的数据
			if (beginTime.compareTo(endTime) > 0) {
				R.failed("开始时间大于结束时间");
			}
			//定位打卡取数据
			sql = "Insert into t_aRecord(MacID,CardID,InOutFlag,xDateTime)" +
					"   Select 1,b.CardID,Null,CONCAT(Cast(a.AttendanceDate as char(20)),' ',Left(Cast(a.AttendanceTime As char(20)),8))" +
					"   From oatAttendanceDetail a,atStatus b,systSecUser c" +
					"   Where datediff(a.AttendanceDate,'" + beginTime + "')>=0" +
					"    And datediff('" + endTime + "',a.AttendanceDate)>=0" +
					"    And a.UserID=c.ID And b.EID=c.EID And (IFNULL('" + cardNo + "','')='' Or b.CardID='" + cardNo + "');";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}
	}

	public void getImportRecordSub(String macType) {
		String sql;
		String inOutFlagBegin;
		String inOutFlagLen;
		Integer validDays;

		sql = "Select InOutBegin InOutFlagBegin,InOutLen InOutFlagLen" +
				"  From atCD_MachinePara " +
				"  Where ID = " + macType + ";";
		Map map1 = systpaystditemMapper.listSalaryTemplate2(sql);
		inOutFlagBegin = map1.get("InOutFlagBegin").toString();
		inOutFlagLen = map1.get("InOutFlagLen").toString();

		//选择有效的天数
		sql = "Select xValue from atCD_Constant where ID=56;";
		Map map2 = systpaystditemMapper.listSalaryTemplate2(sql);
		validDays = Integer.parseInt(map2.get("xValue").toString());

		//依据机器号分进出时转换进出标记b.InOutFlag,只能有0,1两个值
		sql = "update t_aRecord a JOIN atCD_CardImportDS b ON a.MacID=b.ID set a.InOutFlag=b.InOutFlag " +
				"  Where b.MacType=" + macType +
				"   and b.InOutFlag is not null " +
				"   and a.ErrMsg is null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//根据设置对考勤卡号添加前缀
		sql = "update t_aRecord a,atCD_MachinePara b set a.CardID = Ifnull(b.Prefix,'')+a.CardID" +
				"  where b.ID = " + macType +
				"   and b.Prefix Is Not Null" +
				"   and a.ErrMsg Is Null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//转换为个人的EID
		sql = "update t_aRecord a JOIN aVW_CardChangeLis b ON a.CardID=b.CardID  set a.EID=b.EID" +
				"  where datediff(a.xDateTime,b.BeginDate)>=0 " +
				"   and datediff(b.EndDate,a.xDateTime)>=0 " +
				"   and ErrMsg is null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 出错判断
		// 无法转换成时间(AfterCheck)
		sql = "Update t_aRecord Set ErrMsg=1300119" +
				"  Where xDateTime is null " +
				"   and ErrMsg is null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//刷卡文本中机器号不在考勤机配置中
		sql = "Update t_aRecord  a set a.ErrMsg=1300120" +
				"  where not exists (select 1 from atCD_CardImportDS b where a.MacID =b.ID and b.MacType='" + macType + "')" +
				"   and " + macType + " is not null " +
				"   and ErrMsg is null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//刷卡文本中卡号存在空值
		sql = "Update t_aRecord  a set a.ErrMsg=1300121" +
				"  where ifnull(CardID,'')=''" +
				"   and CardTxt is not null " +
				"   and ErrMsg is null ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//有效的天数
		sql = "Update t_aRecord Set ErrMsg=1300122" +
				"  Where  datediff(NOW(),xDateTime)  not between 0 and " + validDays +
				"   and ErrMsg is null; ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		if (Integer.parseInt(inOutFlagLen) > 0 && Integer.parseInt(inOutFlagBegin) > 0) {
			//依据自身的进出标记分进出时没有判断出进出标记
			sql = "update t_aRecord a set a.ErrMsg=1300123" +
					"   Where   " +
					"    (" +
					"     a.InOutFlag is null " +
					"     or " +
					"     a.InOutFlag  not in (0,1) " +
					"    )" +
					"    and a.ErrMsg is NULL;";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}
		// 转化为个人的EID出错
		sql = "update t_aRecord a set a.ErrMsg=1300124" +
				"  where a.EID is null and ErrMsg is null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}
}
