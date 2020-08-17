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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.AtshiftDetailsMapper;
import com.pig4cloud.pigx.admin.mapper.AvwShiftDayMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-06-22 11:43:40
 */
@Service
@AllArgsConstructor
public class AvwShiftDayServiceImpl extends ServiceImpl<AvwShiftDayMapper, AvwShiftDay> implements AvwShiftDayService {
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



	//考勤初始化
	@Transactional(rollbackFor = Exception.class)
	public R getAttendMonthInit(Map paramMap){
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		Integer agentmode = null;
		paramMap.put("userid", userid);
		String term = paramMap.get("term") != null ? paramMap.get("term").toString() : null;
		//2020-07-08
		String nyterm = "";
		String yterm = "";
		String fnyterm = "";
		if (!StringUtils.isEmpty(term)) {
			nyterm = term.substring(0, 7);
			yterm = term.substring(0, 4);
			fnyterm = term.substring(0, 7);
		} else {
			R.failed("考勤月份不能为空");
		}
	/*	if (StringUtils.isEmpty(paramMap.get("agentmode"))) {
			R.failed("考勤账套不能为空");
		} else {
			agentmode = Integer.parseInt(paramMap.get("agentmode").toString());
		}*/
		//atcd_agentmode
		QueryWrapper<AtcdAgentmode> atcdAgentmodeQueryWrapper = new QueryWrapper<>();
		atcdAgentmodeQueryWrapper.eq("corpcode", corpcode);
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(atcdAgentmodeQueryWrapper);
		if (StringUtils.isEmpty(atcdAgentmode)) {
			R.failed("请维护考勤账套！");
		}
		agentmode = atcdAgentmode.getId();

	/*	QueryWrapper<AtattendPeriods> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpid", corpid);
		queryWrapper.eq("ifnull(closed,0)", 0);
		queryWrapper.eq("ifnull(initialized,0)", 1);
		queryWrapper.inSql("agentmode", "Select max(AID) as aid from atCD_AgentMode_User Where UserID=" + userid);
		AtattendPeriods atattendPeriods = atattendPeriodsService.getOne(queryWrapper);
		if (StringUtils.isEmpty(term) && !StringUtils.isEmpty(atattendPeriods)) {
			term = atattendPeriods.getTerm();
		}*/

		//请先封帐当月考勤，再初始化参数月份的考勤
		QueryWrapper<AtattendPeriods> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpid", corpid);
		queryWrapper2.ne("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper2.eq("ifnull(closed,0)", 0);
		queryWrapper2.eq("ifnull(initialized,0)", 1);
		queryWrapper2.eq("ifnull(iscurrentmonth,0)", 1);
		AtattendPeriods atattendPeriods2 = atattendPeriodsService.getOne(queryWrapper2);
		if (!StringUtils.isEmpty(atattendPeriods2)) {
			R.failed("请先封帐当月考勤，再初始化参数月份的考勤");
		}
		//已经封帐的考勤月份不能大于等于当前封帐的考勤月份

		QueryWrapper<AtattendPeriodsAll> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("corpid", corpid);
		queryWrapper3.ge("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper3.eq("agentmode", agentmode);
		List<AtattendPeriodsAll> list1 = atattendPeriodsAllService.list(queryWrapper3);
		if (list1.size() > 0) {
			R.failed("已经封帐的考勤月份不能大于等于当前封帐的考勤月份");
		}

		QueryWrapper<AtattendPeriods> queryWrapper4 = new QueryWrapper<>();
		queryWrapper4.eq("corpid", corpid);
		queryWrapper4.eq("date_format( term, '%Y' )", yterm);
		queryWrapper4.eq("agentmode", agentmode);
		List list2 = atattendPeriodsService.list(queryWrapper4);
		//当月第一天
		String firstDay = DateUtils.getFirstDayOfGivenMonth(term);
		//当月最后一天
		String lastDay = DateUtils.getLastDayOfMonth(term);
		String begindate = "";
		String enddate = "";
		//当年的第一天
		String sql = "";
/*
		QueryWrapper<AtattendPeriods> queryWrapper111=new QueryWrapper<>();
		queryWrapper111.eq("agentmode",agentmode).orderByDesc("enddate").last("limit 1");
		String xenddate = atattendPeriodsService.getOne(queryWrapper111).getEnddate();*/

		//对应参数月份没有考勤月份数据的，生成当前的数据
		if (list2.size() < 1) {
			getAttendPeriodsEasy(term, agentmode);

		}
		//初始化一整年的数据
		String xterm2 = DateUtils.getNextYear(term, 1);
		//12个月以后不存在数据，就初始化当年日历
		QueryWrapper<AtattendPeriods> queryWrapper6 = new QueryWrapper<>();
		queryWrapper6.eq("agentmode", agentmode);
		queryWrapper6.eq("date_format( term, '%Y' )", xterm2.substring(0, 4));
		List<AtattendPeriods> list = atattendPeriodsService.list(queryWrapper6);
		if (list.size() < 1) {
			getAttendPeriodsEasy(xterm2, agentmode);
		}

		//循环生成参数月份之后11个月的轮班组、员工排班，具体生成哪个轮班组有aSP_GroupTurnGenEasy的条件决定
		QueryWrapper<AtattendPeriods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("agentmode", agentmode);
		queryWrapper1.eq("Initialized", 0);
		queryWrapper1.ge("PERIOD_DIFF(date_format(Term,'%Y%m'),date_format('" + term + "','%Y%m'))", 0);
		queryWrapper1.le("PERIOD_DIFF(date_format(Term,'%Y%m'),date_format('" + term + "','%Y%m'))", 11);
		List<AtattendPeriods> list3 = atattendPeriodsService.list(queryWrapper1);
		AtattendPeriods atattendPeriods1 = null;
		for (int jj = 0; jj < list3.size(); jj++) {
			atattendPeriods1 = list3.get(jj);
			String mterm = atattendPeriods1.getTerm();
			String nyTerm = mterm.substring(0, 7);
			String begindate2 = atattendPeriods1.getBegindate();
			String enddate2 = atattendPeriods1.getEnddate();
			generateGroupTurnGenEasy(null, begindate2, enddate2, agentmode);
			Map map = new HashMap();
			map.put("term", mterm);
			getShiftTurnRunEasy(map);
			//对下月期间的结束日期之前的年度考勤期间锁定
			sql = "Update atAttend_Periods  " +
					" Set Initialized=1 ,InitializedTime=now()" +
					" Where date_format( Term, '%Y-%m' )='" + nyTerm +
					"' and ifnull(Initialized,0)=0 And AgentMode=" + agentmode;
			systpaystditemMapper.listSalaryTemplate3(sql);
		}
		//更新当前月份数据fnyterm
		sql = "Update atAttend_Periods Set IsCurrentMonth=1 " +
				" Where date_format( Term, '%Y-%m' )='" + fnyterm + "'" +
				//"	and ifnull(Initialized,0)=0 And AgentMode="+agentmode;
				"	and  AgentMode=" + agentmode;
		systpaystditemMapper.listSalaryTemplate3(sql);
		return R.ok("初始化成功！");
	}

	//考勤汇总
	@Transactional(rollbackFor = Exception.class)
	public R getAttendMonthCalcEasy( Map paramMap){
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		Integer agentmode = null;
		paramMap.put("userid", userid);
		String term = paramMap.get("term") != null ? paramMap.get("term").toString() : null;
		//2020-07-08
		String nyterm = "";
		String yterm = "";
		if (!StringUtils.isEmpty(term)) {
			nyterm = term.substring(0, 7);
			yterm = term.substring(0, 4);
		} else {
			R.failed("考勤月份不能为空");
		}
	/*	if (StringUtils.isEmpty(paramMap.get("agentmode"))) {
			R.failed("考勤账套不能为空");
		} else {
			agentmode = Integer.parseInt(paramMap.get("agentmode").toString());
		}*/
		QueryWrapper<AtcdAgentmode> atcdAgentmodeQueryWrapper = new QueryWrapper<>();
		atcdAgentmodeQueryWrapper.eq("corpcode", corpcode);
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(atcdAgentmodeQueryWrapper);
		agentmode = atcdAgentmode.getId();
		String sql = "";
		sql = "SELECT 1 FROM atAttend_Periods c " +
				"   WHERE date_format( c.Term, '%Y-%m' )<>'" + nyterm + "' AND c.AgentMode=" + agentmode +
				"    AND IFNULL(Closed,0)=0 AND IFNULL(Initialized,0)=1 and iscurrentmonth=1";
		List<Map> maps1 = systpaystditemMapper.listSalaryTemplate4(sql);
		if ((!maps1.isEmpty()) && maps1.size() > 0) {
			R.failed("参数月份与现有月份不一致，请选择正确的月份");
		}

		//临时表存在的话删除临时表
		sql = "DROP TABLE IF EXISTS `t_aStatus`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//创建临时表
		sql = "CREATE TEMPORARY TABLE t_aStatus (EID INT,corpid int);";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aStatus ON t_aStatus ( EID );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除临时表
		sql = "DROP TABLE IF EXISTS `t_aAttend_Total`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//创建临时表
		sql = "CREATE TEMPORARY TABLE t_aAttend_Total " +
				"  (" +
				"   Term     datetime," +
				"   TermFlag    tinyint," +
				"   EID      int NULL ," +
				"   TWID      smallint NULL ," +
				"   Amount     decimal(18, 2) NULL ," +
				"   Unit      varchar (2) NULL ," +
				"   xAmount     decimal(18, 2) Not NULL default 0," +
				"   DecAmount  decimal(18, 2) Not NULL default 0 ," +
				"   corpid      int  " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//加索引
		sql = "CREATE INDEX IX_aAttend_Total ON t_aAttend_Total (Term,TermFlag,EID,TWID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aAttend_DayTotal`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aAttend_DayTotal " +
				"  (" +
				"   Term    datetime," +
				"   xTerm    datetime," +
				"   EID     int NULL ," +
				"   TWID     smallint NULL ," +
				"   Amount    decimal(18, 5) Not NULL default 0 ," +
				"   xAmount    decimal(18, 5) Not NULL default 0 ," +
				"   DecAmount  decimal(18, 5) Not NULL default 0 ," +
				"   corpid      int  " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE INDEX IX_aAttend_DayTotal ON t_aAttend_DayTotal (Term,xTerm,EID,TWID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 对考勤结算的处理
		// 考勤汇总
		QueryWrapper<AtattendPeriods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("AgentMode", agentmode);
		queryWrapper1.eq("date_format( term, '%Y-%m' )", nyterm);
		List<AtattendPeriods> atattendPeriods = atattendPeriodsService.list(queryWrapper1);
		String beginDate = atattendPeriods.get(0).getBegindate();
		String endDate = atattendPeriods.get(0).getEnddate();

		sql = "INSERT INTO t_aStatus(EID,corpid)" +
				"  SELECT a.EID " + "," + corpid +
				"  FROM  aVW_StatusEasy a " +
				"  WHERE date_format( a.JoinDate, '%Y-%m' )<='" + endDate + "' And a.AID=" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//日统计
		sql = "insert t_aAttend_DayTotal (Term,xTerm,EID,TWID,Amount,corpid)" +
				"  Select '" + term + "',a.Term,a.EID,a.TWID,Sum(Amount)," + corpid +
				"  from atShift_Statist a , atAttend_Periods b, t_aStatus c  " +
				"  Where " + " a.corpid = " + corpid + " and b.corpid=" + corpid +
				//"	and a.Term between b.BeginDate and b.EndDate " +
				" and date_format(a.term, '%Y-%m-%d') >=date_format(b.BeginDate, '%Y-%m-%d')  " +
				" and date_format(a.term, '%Y-%m-%d') <=date_format(b.EndDate, '%Y-%m-%d')  " +
				"   and  a.EID=c.EID And b.AgentMode=" + agentmode + " And date_format( b.Term, '%Y-%m' )='" + nyterm + "'" +
				"  Group by a.Term,a.EID,a.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 生成月汇总数据（由纵向数据转为横向数据）
		// #aAttend_Total （月汇总的纵向表）
		// aShift_Total   （月汇总的横向表）
		sql = "Insert t_aAttend_Total(Term,TermFlag,EID,TWID,Amount,corpid)" +
				"  Select '" + term + "',0,a.EID,b.TWID,0" + "," + corpid +
				"  from t_aStatus a, aVW_TimeWageSum b " +
				"  Where " + " a.corpid = " + corpid +
				"	and b.TWID/100*100<>b.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "delete from atShift_Total  where " + " corpid=" + corpid +
				"	and date_format( Term, '%Y-%m' )='" + nyterm + "' And EID In(Select EID from t_aStatus);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成每人每项目的月汇总数值
		sql = "DROP TABLE IF EXISTS `t_aAttend_EmpTotal`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aAttend_EmpTotal " +
				"  (" +
				"   EID     int NULL ," +
				"   TWID     smallint NULL ," +
				"   Amount    decimal(18, 5) default 0," +
				"   corpid      int  " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "insert into t_aAttend_EmpTotal(EID,TWID,Amount,corpid) SELECT EID,TWID,Sum(Amount) AS Amount,corpid from t_aAttend_DayTotal  Group By EID,TWID,corpid;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//月汇总项目数值
		sql = "Update t_aAttend_Total a ,t_aAttend_EmpTotal b" +
				"  Set a.Amount = b.Amount " +
				"  Where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.TWID=b.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成考勤的月显示结果
		sql = "insert  atShift_Total(Term,EID,corpid) select '" + term + "',EID,corpid from t_aStatus where " + " corpid=" + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//对于纵向表更新横向表(集体)
		//call aSP_AttendMonthSumTranSub (_RetVal);
		getAttendMonthSumTranSub();

		sql = "update atShift_Total a,etEmployee b,t_aStatus c" +
				"  set a.Badge= b.Badge,a.Name = b.Name,a.CompID=b.CompID,a.DepID=b.DepID,a.JobID=b.JobID,a.JoinDate=b.JoinDate," +
				"     a.EmpGrade=b.EmpGrade," +
				"     a.JobType=(Select JobType from otJob Where b.JobID=b.JobID LIMIT 1)" +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID And a.EID=c.EID And date_format( a.Term, '%Y-%m' )='" + nyterm + "';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//考勤汇总、月汇总(纵向表)
		sql = "delete from atAttend_Total " +
				"  where " + " corpid=" + corpid +
				"	and date_format( term, '%Y-%m' )='" + nyterm + "' And EID In(Select EID from t_aStatus);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert atAttend_Total(Term,EID,TWID,Amount,Unit,corpid)" +
				"  Select '" + term + "',EID,TWID,sum(Amount),Unit ," + corpid +
				"  from t_aAttend_Total " +
				"  Group by EID,TWID,Unit;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "update atAttend_Total a, atCD_TimeWage b " +
				"  set a.AttendID=b.AttendID,a.Unit=b.Unit       " +
				"  where " + "  a.corpid=" + corpid +
				"	and a.TWID=b.TWID and date_format( a.Term, '%Y-%m' )='" + nyterm + "';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "update atAttend_Total a,etEmployee b,t_aStatus c" +
				"  set a.Badge= b.Badge,a.Name = b.Name,a.CompID=b.CompID,a.DepID=b.DepID,a.JobID=b.JobID" +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID And a.EID=c.EID and date_format( a.Term, '%Y-%m' )='" + nyterm + "' ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//标记为已经考勤汇总过
		sql = "Update atAttend_Periods Set Submit = 1,SubmitBy=" + userid + ",SubmitTime = NOW()" +
				"  Where date_format( Term, '%Y-%m' )='" + nyterm + "' And AgentMode=" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 删除临时表
		sql = "DROP Table t_aStatus;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP Table t_aAttend_EmpTotal;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE t_aAttend_DayTotal;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		return R.ok("汇总成功！");
	}
	//考勤封账
	@Transactional(rollbackFor = Exception.class)
	public R getAttendMonthCloseEasy( Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		//查询考勤账套
//		Systpayrollgroup systpayrollgroup =null;
//		QueryWrapper<Systpayrollgroup> payrollgroupqueryWrapper = new QueryWrapper<>();
//		payrollgroupqueryWrapper.eq("corpid",pigxUser.getCorpid());
//		systpayrollgroup = systpayrollgroupService.getOne(payrollgroupqueryWrapper);
//		Integer gid = null;
//		if(StringUtils.isEmpty(systpayrollgroup)){
//			return R.failed("请维护薪资账套号！");
//		}else{
//			gid = systpayrollgroup.getId();
//		}
		String endDate = "";
		String beginDate = "";
		String sql = "";
		String term = paramMap.get("term") != null ? paramMap.get("term").toString() : null;
		String nyterm = term.substring(0, 7);
		Integer agentmode = null;
	/*
		if (StringUtils.isEmpty(paramMap.get("agentmode"))) {
			R.failed("考勤账套不能为空");
		} else {
			agentmode = Integer.parseInt(paramMap.get("agentmode").toString());
		}
*/
		QueryWrapper<AtcdAgentmode> atcdAgentmodeQueryWrapper = new QueryWrapper<>();
		atcdAgentmodeQueryWrapper.eq("corpcode", corpcode);
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(atcdAgentmodeQueryWrapper);
		agentmode = atcdAgentmode.getId();
		QueryWrapper<AtattendPeriods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("agentmode", agentmode);
		queryWrapper1.eq("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper1.eq("submit", 1);
		List<AtattendPeriods> atattendPeriods = atattendPeriodsService.list(queryWrapper1);
		if (atattendPeriods.isEmpty() || atattendPeriods.size() <= 0) {
			return R.failed("尚未汇总过");
		}
		endDate = atattendPeriods.get(0).getEnddate().substring(0, 10);
		beginDate = atattendPeriods.get(0).getBegindate().substring(0, 10);
		term = atattendPeriods.get(0).getTerm().substring(0, 10);


		//临时表存在的话删除临时表
		sql = "DROP TABLE IF EXISTS `t_aStatus`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//创建临时表
		sql = "CREATE TEMPORARY TABLE t_aStatus (EID INT,corpid int);";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE INDEX IX_aStatus ON t_aStatus ( EID );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO t_aStatus(EID,corpid) SELECT EID,corpid FROM aVW_Status WHERE AID =" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		if (!StringUtils.isEmpty(term)) {
			//封账
			QueryWrapper<Atshift> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper2.eq("corpid", corpid);
			queryWrapper2.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<Atshift> atshifts = atshiftService.list(queryWrapper2);
			AtshiftAll atshiftAll = new AtshiftAll();
			for (Atshift atshift : atshifts) {
				BeanUtils.copyProperties(atshift, atshiftAll);
				atshiftAllService.save(atshiftAll);
			}

			sql = "Delete a" +
					" from atShift_Details_Original a" +
					" Where a.corpid=" + corpid +
					"	and Term<= '" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtshiftDay> queryWrapper3 = new QueryWrapper<>();
			queryWrapper3.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper3.eq("corpid", corpid);
			queryWrapper3.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtshiftDay> atshiftDays = atshiftDayService.list(queryWrapper3);
			AtshiftDayAll atshiftDayAll = new AtshiftDayAll();
			for (AtshiftDay atshiftDay : atshiftDays) {
				BeanUtils.copyProperties(atshiftDay, atshiftDayAll);
				atshiftDayAllService.save(atshiftDayAll);
			}

			sql = "Delete a" +
					"  from atShift_Day a" +
					"  Where a.corpid=" + corpid +
					"	and Term<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtshiftDetails> queryWrapper4 = new QueryWrapper<>();
			queryWrapper4.le("Term", endDate);
			queryWrapper4.eq("corpid", corpid);
			queryWrapper4.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtshiftDetails> atshiftDetails = atshiftDetailsService.list(queryWrapper4);
			AtshiftDetailsAll atshiftDetailsAll = new AtshiftDetailsAll();
			for (AtshiftDetails atshiftDetail : atshiftDetails) {
				BeanUtils.copyProperties(atshiftDetail, atshiftDetailsAll);
				atshiftDetailsAllService.save(atshiftDetailsAll);
			}

			sql = "Delete a" +
					"  from atShift_Details a" +
					"  Where a.corpid=" + corpid +
					" and Term<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid + ")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete a" +
					" from atShift_Details_Original a" +
					" Where a.corpid = " + corpid +
					" and Term<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid + ")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtshiftStatist> queryWrapper5 = new QueryWrapper<>();
			queryWrapper5.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper5.eq("corpid", corpid);
			queryWrapper5.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtshiftStatist> atshiftStatists = atshiftStatistService.list(queryWrapper5);
			AtshiftStatistAll atshiftStatistAll = new AtshiftStatistAll();
			for (AtshiftStatist atshiftStatist : atshiftStatists) {
				BeanUtils.copyProperties(atshiftStatist, atshiftStatistAll);
				atshiftStatistAllService.save(atshiftStatistAll);
			}

			sql = "Delete a" +
					"  from atShift_Statist a" +
					"  Where a.corpid=" + corpid +
					" and Term<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid + ")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Update atCardLost_Register a Set Closed=1,ClosedTime=NOW()" +
					" Where a.corpid=" + corpid +
					"	and ShiftTerm<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					" )";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtcardlostRegister> queryWrapper6 = new QueryWrapper<>();
			queryWrapper6.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper6.eq("corpid", corpid);
			queryWrapper6.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtcardlostRegister> atcardlostRegisters = atcardlostRegisterService.list(queryWrapper6);
			AtcardlostAll atcardlostAll = new AtcardlostAll();
			for (AtcardlostRegister atcardlostRegister : atcardlostRegisters) {
				BeanUtils.copyProperties(atcardlostRegister, atcardlostAll);
				atcardlostAllService.save(atcardlostAll);
			}

			sql = "Delete a" +
					"  from atCardLost_Register a" +
					"  Where a.corpid=" + corpid +
					"	and ShiftTerm<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtcardRecord> queryWrapper7 = new QueryWrapper<>();
			queryWrapper7.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper7.eq("corpid", corpid);
			queryWrapper7.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtcardRecord> atcardRecords = atcardRecordService.list(queryWrapper7);
			AtcardRecordAll atcardRecordAll = new AtcardRecordAll();
			for (AtcardRecord atcardRecord : atcardRecords) {
				BeanUtils.copyProperties(atcardRecord, atcardRecordAll);
				atcardRecordAllService.save(atcardRecordAll);
			}

			sql = "Delete a" +
					"  from atCard_Record a" +
					"  Where a.corpid=" + corpid +
					"	and  ifnull(Term,xDateTime)<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete a" +
					" from atPlan_Break a" +
					" Where  a.corpid=" + corpid +
					"	and Term+1<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete a" +
					"  from atPlan_DayBreak a" +
					"  Where  a.corpid=" + corpid +
					"	and Term+1<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete a" +
					"  from atPlan_FlexBreak a" +
					"  Where a.corpid=" + corpid +
					"	and Term+1<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtplanReg> queryWrapper8 = new QueryWrapper<>();
			queryWrapper8.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper8.le("corpid", corpid);
			queryWrapper8.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtplanReg> atplanRegs = atplanRegService.list(queryWrapper8);
			AtplanRegAll atplanRegAll = new AtplanRegAll();
			for (AtplanReg atplanReg : atplanRegs) {
				BeanUtils.copyProperties(atplanReg, atplanRegAll);
				atplanRegAllService.save(atplanRegAll);
			}

			sql = "Delete a" +
					"  from atPlan_Reg a" +
					"  Where a.corpid= " + corpid +
					" and Term<='" + endDate +
					"'    And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtshiftTotal> queryWrapper9 = new QueryWrapper<>();
			queryWrapper9.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper9.le("corpid", corpid);
			queryWrapper9.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtshiftTotal> atshiftTotals = atshiftTotalService.list(queryWrapper9);
			AtshiftTotalAll atshiftTotalAll = new AtshiftTotalAll();
			for (AtshiftTotal atshiftTotal : atshiftTotals) {
				BeanUtils.copyProperties(atshiftTotal, atshiftTotalAll);
				atshiftTotalAllService.save(atshiftTotalAll);
			}

			sql = "Delete a" +
					"  from atShift_Total a" +
					"  Where a.corpid=" + corpid +
					"	and date_format( Term, '%Y-%m' )='" + nyterm + "'" +
					"    And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtplanTimewage> queryWrapper10 = new QueryWrapper<>();
			queryWrapper10.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper10.eq("corpid", corpid);
			queryWrapper10.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtplanTimewage> atplanTimewages = atplanTimewageService.list(queryWrapper10);
			AtplanTimewageAll atplanTimewageAll = new AtplanTimewageAll();
			for (AtplanTimewage atplanTimewage : atplanTimewages) {
				BeanUtils.copyProperties(atplanTimewage, atplanTimewageAll);
				atplanTimewageAllService.save(atplanTimewageAll);
			}

			sql = "Delete a" +
					"  from atPlan_TimeWage a" +
					"  Where a.corpid=" + corpid +
					"  and Term<='" + endDate +
					"'    And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Update atRegSect_Register a Set Closed=1, ClosedTime= 'NOW()'" +
					" Where a.corpid=" + corpid +
					"	and ifnull(EndDate,BeginDate)<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtregsectRegister> queryWrapper11 = new QueryWrapper<>();
			queryWrapper11.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper11.eq("corpid", corpid);
			queryWrapper11.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtregsectRegister> atregsectRegisters = atregsectRegisterService.list(queryWrapper11);
			AtregsectAll atregsectAll = new AtregsectAll();
			for (AtregsectRegister atregsectRegister : atregsectRegisters) {
				BeanUtils.copyProperties(atregsectRegister, atregsectAll);
				atregsectAllService.save(atregsectAll);
			}

			sql = "Delete a" +
					"  from atRegSect_Register a" +
					"  Where a.corpid=" + corpid +
					"	and ifnull(EndDate,BeginDate)<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Update atRegSectLeaveLong_Register a Set Closed=1,ClosedTime=now()" +
					" Where a.corpid=" + corpid +
					"	and EndDate<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtregsectleavelongRegister> queryWrapper12 = new QueryWrapper<>();
			queryWrapper12.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper12.eq("corpid", corpid);
			queryWrapper12.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtregsectleavelongRegister> atregsectleavelongRegisters = atregsectleavelongRegisterService.list(queryWrapper12);
			AtregsectleavelongAll atregsectleavelongAll = new AtregsectleavelongAll();
			for (AtregsectleavelongRegister atregsectleavelongRegister : atregsectleavelongRegisters) {
				BeanUtils.copyProperties(atregsectleavelongRegister, atregsectleavelongAll);
				atregsectleavelongAllService.save(atregsectleavelongAll);
			}

			sql = "Delete a" +
					"  from atRegSectLeaveLong_Register a" +
					"  Where a.corpid=" + corpid +
					"	and EndDate<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtcardIn> queryWrapper13 = new QueryWrapper<>();
			queryWrapper13.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper13.eq("corpid", corpid);
			queryWrapper13.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtcardIn> atcardIns = atcardInService.list(queryWrapper13);
			AtcardInAll atcardInAll = new AtcardInAll();
			for (AtcardIn atcardIn : atcardIns) {
				BeanUtils.copyProperties(atcardIn, atcardInAll);
				atcardInAllService.save(atcardInAll);
			}

			sql = "Delete a" +
					" from atCard_In a" +
					"  Where a.corpid=" + corpid +
					" and Term<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete a" +
					" from atCard_Out a " +
					"  Where a.corpid=" + corpid +
					"	and Term<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Update atRegTime_Register a Set Closed=1, ClosedTime=now()" +
					"  Where a.corpid=" + corpid +
					"	and EndDate<='" + endDate +
					"'  And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtregtimeRegister> queryWrapper14 = new QueryWrapper<>();
			queryWrapper14.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper14.eq("corpid", corpid);
			queryWrapper14.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtregtimeRegister> atregtimeRegisters = atregtimeRegisterService.list(queryWrapper14);
			AtregtimeAll atregtimeAll = new AtregtimeAll();
			for (AtregtimeRegister atregtimeRegister : atregtimeRegisters) {
				BeanUtils.copyProperties(atregtimeRegister, atregtimeAll);
				atregtimeAllService.save(atregtimeAll);
			}

			sql = "Delete a" +
					"  from atRegTime_Register a" +
					"  Where a.corpid=" + corpid +
					"	and EndDate<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					"	)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Update atRegTimeLeaveLong_Register a Set Closed=1,ClosedTime=now()" +
					"  Where a.corpid=" + corpid +
					"	and EndDate<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtregtimeleavelongRegister> queryWrapper15 = new QueryWrapper<>();
			queryWrapper15.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper15.eq("corpid", corpid);
			queryWrapper15.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtregtimeleavelongRegister> atregtimeleavelongRegisters = atregtimeleavelongRegisterService.list(queryWrapper15);
			AtregtimeleavelongAll atregtimeleavelongAll = new AtregtimeleavelongAll();
			for (AtregtimeleavelongRegister atregtimeleavelongRegister : atregtimeleavelongRegisters) {
				BeanUtils.copyProperties(atregtimeleavelongRegister, atregtimeleavelongAll);
				atregtimeleavelongAllService.save(atregtimeleavelongAll);
			}

			sql = "Delete a" +
					"  from atRegTimeLeaveLong_Register a" +
					"  Where a.corpid=" + corpid +
					" and EndDate<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtshiftWork> queryWrapper16 = new QueryWrapper<>();
			queryWrapper16.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper16.eq("corpid", corpid);
			queryWrapper16.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtshiftWork> atshiftWorks = atshiftWorkService.list(queryWrapper16);
			AtshiftWorkAll atshiftWorkAll = new AtshiftWorkAll();
			for (AtshiftWork atshiftWork : atshiftWorks) {
				BeanUtils.copyProperties(atshiftWork, atshiftWorkAll);
				atshiftWorkAllService.save(atshiftWorkAll);
			}

			sql = "Delete a" +
					"  from atShift_Work a" +
					"  Where a.corpid=" + corpid +
					" and date_format( a.Term, '%Y-%m' )>'" + nyterm + "'" +
					"   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			QueryWrapper<AtplanRange> queryWrapper17 = new QueryWrapper<>();
			queryWrapper17.le("date_format(term, '%Y-%m-%d')", endDate);
			queryWrapper17.eq("corpid", corpid);
			queryWrapper17.inSql("eid", "Select EID from t_aStatus where corpid=" + corpid);
			List<AtplanRange> atplanRanges = atplanRangeService.list(queryWrapper17);
			AtplanRangeAll atplanRangeAll = new AtplanRangeAll();
			for (AtplanRange atplanRange : atplanRanges) {
				BeanUtils.copyProperties(atplanRange, atplanRangeAll);
				atplanRangeAllService.save(atplanRangeAll);
			}

			sql = "Delete a" +
					"  from atPlan_Range a" +
					"  Where a.corpid=" + corpid +
					" and DATE_ADD(  a.Term,INTERVAL 1 DAY )<='" + endDate +
					"'   And a.EID In(Select EID from t_aStatus where corpid=" + corpid +
					")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//移动端打卡数据
			QueryWrapper<Oatattendancedetail> queryWrapper18 = new QueryWrapper<>();
			queryWrapper18.eq("attendancedate", endDate);
			queryWrapper18.inSql("userid", "Select c.user_id from t_aStatus b,sys_user c Where b.corpid=" + corpid + " and c.corpid=" + corpid +
					" and b.EID=c.EID");
			List<Oatattendancedetail> oatattendancedetails = oatattendancedetailService.list(queryWrapper18);
			OatattendancedetailAll oatattendancedetailAll = new OatattendancedetailAll();
			for (Oatattendancedetail oatattendancedetail : oatattendancedetails) {
				BeanUtils.copyProperties(oatattendancedetail, oatattendancedetailAll);
				oatattendancedetailAllService.save(oatattendancedetailAll);
			}

			sql = "Delete a" +
					"  from oatAttendanceDetail a" +
					"  Where a.corpid=" + corpid +
					" and date_format( AttendanceDate, '%Y-%m' )='" + endDate.substring(0, 7) + "'" +
					"   And a.UserID In(Select c.user_id from t_aStatus b,sys_user c Where b.corpid=" + corpid + " and c.corpid=" + corpid +
					" and b.EID=c.EID)";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//轮班组
//			QueryWrapper<AtgroupShift> queryWrapper19=new QueryWrapper<>();
//			queryWrapper19.le("term",endDate);
//			queryWrapper19.eq("agentmode",agentmode);
//			List<AtgroupShift> atgroupShifts = atgroupShiftService.list(queryWrapper19);
//			AtgroupshiftAll atgroupshiftAll=new AtgroupshiftAll();
//			for (AtgroupShift atgroupShift:atgroupShifts) {
//				BeanUtils.copyProperties(atgroupShift,atgroupshiftAll);
//				atgroupshiftAllService.save(atgroupshiftAll);
//			}

			sql = "Delete a" +
					"  from atGroupShift a" +
					"  Where a.corpid=" + corpid +
					"	and date_format( a.Term, '%Y-%m-%d' )>='" + endDate.substring(0, 10) + "' And a.AgentMode=" + agentmode;
			systpaystditemMapper.listSalaryTemplate3(sql);

		}

		//标志为考勤封帐(放入历史表并删除当前考勤期间数据)
		sql = "Update atAttend_Periods Set Closed = 1,ClosedTime = NOW()" +
				" Where " +
				"	 date_format( Term, '%Y-%m' )='" + nyterm + "' And AgentMode=" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Delete From atAttend_Periods_All" +
				" Where date_format( Term, '%Y-%m' )='" + nyterm + "' And AgentMode=" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<AtattendPeriods> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.le("agentMode", agentmode);
		queryWrapper2.eq("date_format( term, '%Y-%m' )", nyterm);
		List<AtattendPeriods> atattendPeriods2 = atattendPeriodsService.list(queryWrapper2);
		AtattendPeriodsAll atattendPeriodsAll = new AtattendPeriodsAll();
		for (AtattendPeriods atattendPeriod : atattendPeriods2) {
			BeanUtils.copyProperties(atattendPeriod, atattendPeriodsAll);
			atattendPeriodsAllService.save(atattendPeriodsAll);
		}

		QueryWrapper<AtattendPeriods> queryWrapper111 = new QueryWrapper<>();
		queryWrapper111.eq("agentmode", agentmode).orderByDesc("enddate").last("limit 1");
		String xenddate = atattendPeriodsService.getOne(queryWrapper111).getEnddate();

		//删除当前考勤期间
		sql = "Delete from atAttend_Periods Where date_format( Term, '%Y-%m' )='" + nyterm + "' And AgentMode=" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//初始化下个月数据
//		call aSP_AttendMonthInitEasy (@xTerm,_UserID,_AgentMode,_RetVal);
		//getAttendMonthInitf(term,agentmode);
		getAttendMonthInitf(term, agentmode, xenddate);

		//删除临时表
		sql = "drop table t_aStatus;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		return R.ok("封账成功！");
	}
	//手动排班
	@Transactional(rollbackFor = Exception.class)
	public R getShiftHandleTurnRunEasy( Map map){
		String term = map.get("term").toString();
		Integer eid = Integer.parseInt(map.get("eid").toString());
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();
		String nyterm = "";
		if (!StringUtils.isEmpty(term)) {
			nyterm = term.substring(0, 7);
		} else {
			return R.failed("考勤月份不能为空");
		}

		sql = "DROP TABLE IF EXISTS t_aStatus_Ins;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aStatus_Ins" +
				"  (" +
				"   EID int ," +
				"   GroupID Int," +
				"   BeginDate date," +
				"   EndDate date" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aStatus_Ins On t_aStatus_Ins (EID,GroupID,BeginDate,EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS t_aShift;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aShift" +
				"  ( " +
				"   Term date," +
				"   EID int ," +
				"   GroupID Int," +
				"   Shift varchar(10)   ," +
				"   DayType tinyint" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aShift On t_aShift (Term,EID,GroupID,Shift,DayType);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Atstatus> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("eid", eid);
		Atstatus atstatus = atstatusService.getOne(queryWrapper1);
		Integer agentmode = atstatus.getAid();

//		正在考勤分析，不允许执行其它的操作
//		call aSP_AttendLockStatusCheck (@AgentMode,@RetVal);
		R r = getAttendLockStatusCheck(agentmode);
		if(r.getCode()!=0){
			return r;
		}
//		手动排班检查
//		call aSP_ShiftHandleRunCheckSub (_Term, _EID, @RetVal);
		R r2 = getShiftHandleRunCheckSub(term, eid);
		if(r2.getCode()!=0){
			return r2;
		}
		QueryWrapper<AtattendPeriods> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper2.eq("agentmode", agentmode);
		List<AtattendPeriods> atattendPeriods = atattendPeriodsService.list(queryWrapper2);
		String begindate = atattendPeriods.get(0).getBegindate();
		String enddate = atattendPeriods.get(0).getEnddate();

//		考勤状态、轮班组、登记的日期3者的交集(在这里登记的日期为考勤的期间)
		sql = "insert t_aStatus_Ins (EID,GroupID,BeginDate,EndDate) " +
				"  Select a.EID,GroupID," +
				"    Case When " +
				"     Case When a.BeginDate >'" + begindate + "'  then a.BeginDate else '" + begindate + "' end  >=" +
				"     Case When b.BeginDate >'" + begindate + "'  then b.BeginDate else '" + begindate + "' end " +
				"    Then    Case When a.BeginDate >'" + begindate + "'  then a.BeginDate else '" + begindate + "' end" +
				"    Else    Case When b.BeginDate >'" + begindate + "'  then b.BeginDate else '" + begindate + "' end " +
				"    End , " +
				"    Case When  " +
				"     Case when a.EndDate   <'" + enddate + "'    then a.EndDate   else '" + enddate + "' end   >=" +
				"     Case when b.EndDate   <'" + enddate + "'    then b.EndDate   else '" + enddate + "' end " +
				"    Then    Case when b.EndDate   <'" + enddate + "'    then b.EndDate   else '" + enddate + "' end " +
				"    Else    Case when a.EndDate   <'" + enddate + "'    then a.EndDate   else '" + enddate + "' end" +
				"    End  " +
				"  from aVW_AttendStatusLis a,aVW_GroupChangeLis b" +
				"  Where a.EID=b.EID " +
				"   and " +
				"    Case When " +
				"      Case When a.BeginDate >'" + begindate + "'  then a.BeginDate else '" + begindate + "' end  >=" +
				"      Case When b.BeginDate >'" + begindate + "'  then b.BeginDate else '" + begindate + "' end " +
				"    Then    Case When a.BeginDate >'" + begindate + "'  then a.BeginDate else '" + begindate + "' end" +
				"    Else    Case When b.BeginDate >'" + begindate + "'  then b.BeginDate else '" + begindate + "' end " +
				"    End <= " +
				"    Case When  " +
				"     Case when a.EndDate   <'" + enddate + "'    then a.EndDate else '" + enddate + "' end   >=" +
				"     Case when b.EndDate   <'" + enddate + "'    then b.EndDate else '" + enddate + "' end " +
				"    Then    Case when b.EndDate   <'" + enddate + "'    then b.EndDate else '" + enddate + "' end " +
				"    Else    Case when a.EndDate   <'" + enddate + "'    then a.EndDate else '" + enddate + "' end" +
				"    End  " +
				"   and a.ATStatus in (0)" +
				"   and a.EID=" + eid + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成排班
		for (int i = 1; i <= 31; i++) {
			sql = "Insert Into t_aShift(EID,Term,GroupID,Shift,DayType)" +
					"   Select a.EID,d.Term,a.GroupID," +
					"     Case     '" + i + "'" +
					"      When 1   Then  Case when IFNULL(b.S1,'')=IFNULL(e.S1,'') Then e.S1    Else b.S1   End" +
					"      When 2   Then  Case when IFNULL(b.S2,'')=IFNULL(e.S2,'') Then e.S2    Else b.S2   End " +
					"      When 3   Then  Case when IFNULL(b.S3,'')=IFNULL(e.S3,'') Then e.S3    Else b.S3   End" +
					"      When 4   Then  Case when IFNULL(b.S4,'')=IFNULL(e.S4,'') Then e.S4    Else b.S4   End" +
					"      When 5   Then  Case when IFNULL(b.S5,'')=IFNULL(e.S5,'') Then e.S5    Else b.S5   End" +
					"      When 6   Then  Case when IFNULL(b.S6,'')=IFNULL(e.S6,'') Then e.S6    Else b.S6   End" +
					"      When 7   Then  Case when IFNULL(b.S7,'')=IFNULL(e.S7,'') Then e.S7    Else b.S7   End" +
					"      When 8   Then  Case when IFNULL(b.S8,'')=IFNULL(e.S8,'') Then e.S8    Else b.S8   End" +
					"      When 9   Then  Case when IFNULL(b.S9,'')=IFNULL(e.S9,'') Then e.S9    Else b.S9   End" +
					"      When 10  Then  Case when IFNULL(b.S10,'')=IFNULL(e.S10,'') Then e.S10   Else b.S10  End" +
					"      When 11  Then  Case when IFNULL(b.S11,'')=IFNULL(e.S11,'') Then e.S11   Else b.S11  End" +
					"      When 12  Then  Case when IFNULL(b.S12,'')=IFNULL(e.S12,'') Then e.S12   Else b.S12  End" +
					"      When 13  Then  Case when IFNULL(b.S13,'')=IFNULL(e.S13,'') Then e.S13   Else b.S13  End" +
					"      When 14  Then  Case when IFNULL(b.S14,'')=IFNULL(e.S14,'') Then e.S14   Else b.S14  End" +
					"      When 15  Then  Case when IFNULL(b.S15,'')=IFNULL(e.S15,'') Then e.S15   Else b.S15  End" +
					"      When 16  Then  Case when IFNULL(b.S16,'')=IFNULL(e.S16,'') Then e.S16   Else b.S16  End" +
					"      When 17  Then  Case when IFNULL(b.S17,'')=IFNULL(e.S17,'') Then e.S17   Else b.S17  End" +
					"      When 18  Then  Case when IFNULL(b.S18,'')=IFNULL(e.S18,'') Then e.S18   Else b.S18  End" +
					"      When 19  Then  Case when IFNULL(b.S19,'')=IFNULL(e.S19,'') Then e.S19   Else b.S19  End" +
					"      When 20  Then  Case when IFNULL(b.S20,'')=IFNULL(e.S20,'') Then e.S20   Else b.S20  End" +
					"      When 21  Then  Case when IFNULL(b.S21,'')=IFNULL(e.S21,'') Then e.S21   Else b.S21  End" +
					"      When 22  Then  Case when IFNULL(b.S22,'')=IFNULL(e.S22,'') Then e.S22   Else b.S22  End" +
					"      When 23  Then  Case when IFNULL(b.S23,'')=IFNULL(e.S23,'') Then e.S23   Else b.S23  End" +
					"      When 24  Then  Case when IFNULL(b.S24,'')=IFNULL(e.S24,'') Then e.S24   Else b.S24  End" +
					"      When 25  Then  Case when IFNULL(b.S25,'')=IFNULL(e.S25,'') Then e.S25   Else b.S25  End" +
					"      When 26  Then  Case when IFNULL(b.S26,'')=IFNULL(e.S26,'') Then e.S26   Else b.S26  End" +
					"      When 27  Then  Case when IFNULL(b.S27,'')=IFNULL(e.S27,'') Then e.S27   Else b.S27  End" +
					"      When 28  Then  Case when IFNULL(b.S28,'')=IFNULL(e.S28,'') Then e.S28   Else b.S28  End" +
					"      When 29  Then  Case when IFNULL(b.S29,'')=IFNULL(e.S29,'') Then e.S29   Else b.S29  End" +
					"      When 30  Then  Case when IFNULL(b.S30,'')=IFNULL(e.S30,'') Then e.S30   Else b.S30  End" +
					"      When 31  Then  Case when IFNULL(b.S31,'')=IFNULL(e.S31,'') Then e.S31   Else b.S31  End" +
					"     End,d.xType" +
					"    From t_aStatus_Ins a,atShiftSupply_Work b ,atGroup c,atCalendar d,atShift_Work e    " +
					"    Where a.EID=b.EID and a.EID=e.EID And c.AgentMode=" + agentmode + " and a.GroupID=c.GroupID and c.xCalendar=d.xCategory " +
					"     and date_add('" + begindate + "', INTERVAL '" + i + "' day)=d.Term+1 and date_format( b.Term, '%Y-%m' )=date_format( e.Term, '%Y-%m' )" +
					"     and date_format( b.Term, '%Y-%m' )='" + nyterm + "' and d.Term between a.BeginDate and a.EndDate and d.Term between '" + begindate + "' and '" + enddate + "';";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

		sql = "Delete FROM t_aShift WHERE Shift IS NULL;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//班次不同的调整班次
		sql = "Update atShift a,t_aShift b Set a.Shift=b.Shift,a.ManualShift=b.Shift" +
				"  Where a.Term between '" + begindate + "' and '" + enddate + "'" +
				"   And a.EID=b.EID And a.Term=b.Term And a.Shift<>b.Shift;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//请假明细更新
		sql = "Update atPlan_Reg a,atPlan_Range b,t_aShift c,atShift_Type e " +
				"  Set a.BeginTime = aFN_GenDateTime(b.Term,e.BeginTime)," +
				"    a.EndTime = aFN_GenDateTime(b.Term,e.EndTime)" +
				"  Where a.Term = b.Term" +
				"   and a.EID = b.EID" +
				"   and a.EID = c.EID" +
				"   and a.EID = " + eid +
				"   and a.Term = c.Term" +
				"   and e.AgentMode = " + agentmode +
				"   and date_format( c.Term, '%Y-%m' )='" + nyterm + "'" +
				"   and c.Shift <> b.Shift" +
				"   and c.Shift = e.Shift" +
				"   and a.BeginTime = b.BeginTime" +
				"   and a.EndTime = b.EndTime;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新每人每天班次时段表
		sql = "Update atPlan_Range  a,atShift b,atShift_Type c  set a.Shift=b.Shift," +
				"    a.BeginTime=aFn_GenDateTime(a.Term,c.BeginTime)  ," +
				"    a.EndTime=aFn_GenDateTime(a.Term,c.EndTime)  ," +
				"    a.ScanBeginTime=aFn_GenDateTime(a.Term,c.ScanBeginTime) ," +
				"    a.ScanEndTime=aFn_GenDateTime(a.Term,c.ScanEndTime) ," +
				"    a.ShouldWorkTime=c.xHours," +
				"    CardBeginTime1=Null," +
				"    CardEndTime1=Null," +
				"    CardBeginTime2=Null," +
				"    CardEndTime2=Null," +
				"    CardBeginTime3=Null," +
				"    CardEndTime3=Null," +
				"    a.AnalyMode = Case When  a.AnalyMode > 1 Then 1  Else a.AnalyMode End" +
				"  Where a.Term between '" + begindate + "' and '" + enddate + "' " +
				"   and a.EID=b.EID" +
				"   and a.Term=b.Term  " +
				"   and c.AgentMode = " + agentmode +
				"   and ifnull(a.Shift,'')<>ifnull(b.Shift ,'')" +
				"   and b.Shift=c.Shift  " +
				"   and exists(select 1 from t_aStatus_Ins d " +
				"     where a.EID=d.EID " +
				"      and a.Term between d.BeginDate and d.EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//根据排班生成扫描范围
		sql = "insert atPlan_Range (Term,EID,Shift,DayType,AnalyMode,BeginTime,EndTime,ScanBeginTime,ScanEndTime,ShouldWorkTime)" +
				"  Select a.Term,a.EID,a.Shift,a.DayType ,0," +
				"   aFn_GenDateTime(a.Term,b.BeginTime)," +
				"   aFn_GenDateTime(a.Term,b.EndTime)," +
				"   aFn_GenDateTime(a.Term,b.ScanBeginTime)," +
				"   aFn_GenDateTime(a.Term,b.ScanEndTime)," +
				"   b.xHours" +
				"  from atShift a,atShift_Type b " +
				"  Where a.Term between '" + begindate + "' and '" + enddate + "' " +
				"   and  a.Shift=b.Shift and b.AgentMode = " + agentmode +
				"   and " +
				"   not exists(select 1 from atPlan_Range c " +
				"     where  a.EID=c.EID " +
				"      and a.Term=c.Term " +
				"    )" +
				"   and exists(select 1 from t_aStatus_Ins d " +
				"     where a.EID=d.EID " +
				"      and a.Term between d.BeginDate and d.EndDate " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);//

		//根据班次类型、班次时段生成每天每人的班次时段
		//call aSP_PlanTWIDInitSimple (@BeginDate ,@EndDate,@AgentMode,_retval);
		getPlanTWIDInitSimple(begindate, enddate, agentmode);

		//放到历史表中
		QueryWrapper<AtshiftsupplyWork> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("eid", eid);
		queryWrapper3.eq("date_format( term, '%Y-%m' )", nyterm);
		List<AtshiftsupplyWork> atshiftsupplyWorks = atshiftsupplyWorkService.list(queryWrapper3);
		for (AtshiftsupplyWork atshiftsupplyWork : atshiftsupplyWorks) {
			AtshiftsupplyWorkAll atshiftsupplyWorkAll = new AtshiftsupplyWorkAll();
			BeanUtils.copyProperties(atshiftsupplyWork, atshiftsupplyWorkAll);
			atshiftsupplyWorkAll.setClosed(1);
			atshiftsupplyWorkAll.setClosedby(userid);
			atshiftsupplyWorkAll.setClosedtime(new Date().toString());
			atshiftsupplyWorkAllService.save(atshiftsupplyWorkAll);
		}

		//更新自动排班
		sql = "Update atShift_Work a ,atShiftSupply_Work b Set a.S1=Case When b.S1 Is Null Then a.S1 Else b.S1 End," +
				"     a.S2=Case When b.S2 Is Null Then a.S2 Else b.S2 End," +
				"     a.S3=Case When b.S3 Is Null Then a.S3 Else b.S3 End," +
				"     a.S4=Case When b.S4 Is Null Then a.S4 Else b.S4 End," +
				"     a.S5=Case When b.S5 Is Null Then a.S5 Else b.S5 End," +
				"     a.S6=Case When b.S6 Is Null Then a.S6 Else b.S6 End," +
				"     a.S7=Case When b.S7 Is Null Then a.S7 Else b.S7 End," +
				"     a.S8=Case When b.S8 Is Null Then a.S8 Else b.S8 End," +
				"     a.S9=Case When b.S9 Is Null Then a.S9 Else b.S9 End," +
				"     a.S10=Case When b.S10 Is Null Then a.S10 Else b.S10 End," +
				"     a.S11=Case When b.S11 Is Null Then a.S11 Else b.S11 End," +
				"     a.S12=Case When b.S12 Is Null Then a.S12 Else b.S12 End," +
				"     a.S13=Case When b.S13 Is Null Then a.S13 Else b.S13 End," +
				"     a.S14=Case When b.S14 Is Null Then a.S14 Else b.S14 End," +
				"     a.S15=Case When b.S15 Is Null Then a.S15 Else b.S15 End," +
				"     a.S16=Case When b.S16 Is Null Then a.S16 Else b.S16 End," +
				"     a.S17=Case When b.S17 Is Null Then a.S17 Else b.S17 End," +
				"     a.S18=Case When b.S18 Is Null Then a.S18 Else b.S18 End," +
				"     a.S19=Case When b.S19 Is Null Then a.S19 Else b.S19 End," +
				"     a.S20=Case When b.S20 Is Null Then a.S20 Else b.S20 End," +
				"     a.S21=Case When b.S21 Is Null Then a.S21 Else b.S21 End," +
				"     a.S22=Case When b.S22 Is Null Then a.S22 Else b.S22 End," +
				"     a.S23=Case When b.S23 Is Null Then a.S23 Else b.S23 End," +
				"     a.S24=Case When b.S24 Is Null Then a.S24 Else b.S24 End," +
				"     a.S25=Case When b.S25 Is Null Then a.S25 Else b.S25 End," +
				"     a.S26=Case When b.S26 Is Null Then a.S26 Else b.S26 End," +
				"     a.S27=Case When b.S27 Is Null Then a.S27 Else b.S27 End," +
				"     a.S28=Case When b.S28 Is Null Then a.S28 Else b.S28 End," +
				"     a.S29=Case When b.S29 Is Null Then a.S29 Else b.S29 End," +
				"     a.S30=Case When b.S30 Is Null Then a.S30 Else b.S30 End," +
				"     a.S31=Case When b.S31 Is Null Then a.S31 Else b.S31 End " +
				"  Where  b.EID=" + eid + " and date_format( b.Term, '%Y-%m' )='" + nyterm + "' and date_format( b.Term, '%Y-%m' )=date_format( a.Term, '%Y-%m' ) and b.EID=a.EID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Delete from atShiftSupply_Work where EID=" + eid + " and '" + term + "'=Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP table t_aStatus_Ins;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP Table t_aShift;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		return R.ok("员工排版 手动排班成功");
	}
	//开启考勤
	@Transactional(rollbackFor = Exception.class)
	public R getStaffstartEasy(AtstaffRegister atstaffRegister2){
		Integer id = atstaffRegister2.getId();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userId = pigxUser.getId();
		String sql;
		Integer agentMode;
		Integer corpid;
		String tran = "C";

		if (id == null) {
			R.failed("记录不能为空，请选择记录！");
		}

		sql = "Select AID agentmode,corpid from atStaff_Register Where ID=" + id;
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		if (StringUtils.isEmpty(map)) {
			return R.failed("信息为空，请核实！");
		}
		//agentMode=
		Object agentModeOjb = map.get("agentmode");
		if (StringUtils.isEmpty(agentModeOjb)) {
			return R.failed("请维护此员工考勤账套！");
		}
		agentMode = Integer.parseInt(agentModeOjb.toString());
		corpid = Integer.parseInt(map.get("corpid").toString());

		//判断考勤基本信息是否完整
		R r = getStaffchecksubEasy(id, agentMode);
		if (r.getCode() != 0) {
			return r;
		}


		AtstaffRegister atstaffRegister = atstaffRegisterService.getById(id);
		Integer eid = atstaffRegister.getEid();
		Integer groupId = atstaffRegister.getGroupid();
		Integer atstatus = atstaffRegister.getAtstatus();
		Integer lineid = atstaffRegister.getLineid();
		Integer teamid = atstaffRegister.getTeamid();
		String cardid = atstaffRegister.getCardid();
		Integer atemptype = atstaffRegister.getAtemptype();
		String atbadge = atstaffRegister.getAtbadge();
		if(StringUtils.isEmpty(atbadge)){
			atbadge=null;
		}
		String hourlywages = atstaffRegister.getHourlywages();
		Integer aid = atstaffRegister.getAid();
		Integer agentuserid = atstaffRegister.getAgentuserid();
		String begindate = atstaffRegister.getJoindate();
		String remark = atstaffRegister.getRemark();
		if(StringUtils.isEmpty(remark)){
			remark=null;
		}
		String cardaddrid = atstaffRegister.getCardaddrid();

		String endDate = "9990-12-31";

		/*//更新考勤基本信息(atStatus）
		sql = "Update atStatus a Set a.LineID = " + lineid + ",a.TeamID = " + teamid + ",a.GroupID = " + groupId + ",a.ATBadge = " + atbadge + ", a.CardID =" + cardid + "," +
				"     a.ATStatus = " + atstatus + ",a.ATEmpType = " + atemptype + ",a.AID=" + aid + ",a.AgentUserID=" + agentuserid + ",a.remark=" + remark + "," +
				"     a.CardAddrID=" + cardaddrid +
				"  Where a.EID=" + eid + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);*/

		Atstatus atstatus1 = new Atstatus();
		atstatus1.setEid(eid);
		atstatus1.setCorpcode(atstaffRegister.getCorpcode());
		atstatus1.setAgentuserid(agentuserid);
		atstatus1.setAid(aid);
		atstatus1.setAtbadge(atbadge);
		atstatus1.setAtemptype(atemptype);
		atstatus1.setBadge(atstaffRegister.getBadge());
		atstatus1.setAtstatus(atstaffRegister.getAtstatus());
		atstatus1.setCardaddrid(cardaddrid);
		atstatus1.setCardid(cardid);
		atstatus1.setCorpid(atstaffRegister.getCorpid());
		atstatus1.setGroupid(groupId);
		atstatus1.setLineid(lineid);
		atstatus1.setTeamid(teamid);
		atstatus1.setRemark(remark);
		atstatus1.setTerm(atstaffRegister.getTerm());
		atstatusService.save(atstatus1);


		//将考勤状态变动的处理记录到统一的变动历史表中(aevent)
		getEvent(agentMode, corpid, 601, id, eid, atstatus, begindate, endDate, "新员工基本信息");

		//将个人轮班组变动的处理记录到统一的变动历史表中(aevent)
		getEvent(agentMode, corpid, 605, id, eid, atstatus, begindate, endDate, "新员工基本信息");

		//将卡号的处理记录到统一的变动历史表中(aevent)
		getEvent(agentMode, corpid, 607, id, eid, atstatus, begindate, endDate, "新员工基本信息");

		//将考勤账套的处理记录到统一的变动历史表中(aevent)
		getEvent(agentMode, corpid, 610, id, eid, atstatus, begindate, endDate, "新员工基本信息");

		//对于轮班、排班策略等引用的锁定
		getUserconfigsubmitcheck(2, "", agentMode);

		//将考勤基本信息处理的标志置为处理
		sql = "Update atStaff_Register Set Closed = 1, ClosedBy = " + userId + ",ClosedTime = NOW()" +
				" Where ID=" + id + " And IFNULL(Initialized,0)=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成员工对应的打卡地点
		sql = "Insert Into oatAttendanceUser(AttendanceID,UserID,EID)" +
				"  Select b.short_str,(Select ID from sys_user Where a.EID=EID),a.EID" +
				"  From atStaff_Register a,(SELECT substring_index(substring_index(t1.CardAddrID,',',t2.help_topic_id+1),',',-1) short_str" +
				"  FROM atstaff_register t1" +
				"  JOIN mysql.help_topic t2 on t2.help_topic_id < (LENGTH(t1.CardAddrID)-LENGTH(REPLACE(t1.CardAddrID,',',''))+1)" +
				"    WHERE t1.ID=" + id + " AND IFNULL(t1.Initialized,0)=1) b" +
				"  Where a.ID=" + id + " And Not Exists(Select 1 from oatAttendanceUser Where a.EID=EID And b.short_str=AttendanceID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//将考勤基本信息处理的登记放入历史表中
		sql = "Delete From atStaff_All Where ID=" + id + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert into atStaff_All(ID,Term,EID,Badge,Name,CompID,DepID,JobID,JoinDate,LineID,TeamID,GroupID,ATBadge,AgentUserID,AID,CardID," +
				"  CardAddrID,ATEmpType,ATStatus,HourlyWages,Initialized,InitializedBy,InitializedTime,Submit,SubmitBy,SubmitTime," +
				"      Closed,ClosedBy,ClosedTime,Remark)      " +
				"  Select ID,Term,EID,Badge,Name,CompID,DepID,JobID,JoinDate,LineID,TeamID,GroupID,ATBadge,AgentUserID,AID,CardID,CardAddrID," +
				"        ATEmpType,ATStatus,HourlyWages,Initialized,InitializedBy,InitializedTime,Submit,SubmitBy,SubmitTime," +
				"        Closed,ClosedBy,ClosedTime,Remark" +
				"  From atStaff_Register      " +
				"  Where ID=" + id + " And IFNULL(Closed,0)=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Delete From atStaff_Register Where ID=" + id + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		return R.ok("操作成功！");
	}
	//考勤分析
	@Transactional(rollbackFor = Exception.class)
	public R getAnalysisEasy(Map map){
		Integer depid=Integer.parseInt(map.get("depid").toString());
		String badge=map.get("badge").toString();
		String begindate=map.get("begindate").toString();
		String enddate=map.get("enddate").toString();
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();

		Integer agentmode = null;
		QueryWrapper<AtcdAgentmode> atcdAgentmodeQueryWrapper = new QueryWrapper<>();
		atcdAgentmodeQueryWrapper.eq("corpcode", corpcode);
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(atcdAgentmodeQueryWrapper);
		agentmode = atcdAgentmode.getId();

		//判断临时表存在删除临时表
		sql = "DROP TABLE IF EXISTS `t_aAnal_Status`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//创建临时表
		sql = "CREATE TEMPORARY TABLE t_aAnal_Status" +
				"  ( " +
				"   Term             datetime," +
				"   EID             int," +
				"   Shift            varchar(10)," +
				"   ATStatus         varchar(10) ," +
				"   AnalyMode        tinyint," +
				"   ProcessFlag        bit," +
				"   BeginTime1         Datetime," +
				"   EndTime1           Datetime," +
				"   BeginTime2         Datetime," +
				"   EndTime2           Datetime," +
				"   BeginTime3         Datetime," +
				"   EndTime3           Datetime," +
				"    corpid  int" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//加索引
		sql = "Create Index IX_aAnal_Status On t_aAnal_Status (Term,EID,ATStatus);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="DROP TABLE IF EXISTS t_aBal_Status";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//创建临时表
		sql = "CREATE TEMPORARY Table t_aBal_Status" +
				"  ( " +
				"   EID int  ," +
				"   TWID smallint    " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//索引
		sql = "Create Index IX_aBal_Status On t_aBal_Status (EID,TWID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="DROP TABLE IF EXISTS t_aAnal_CardIn";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//创建临时表
		sql = "Create TEMPORARY Table t_aAnal_CardIn" +
				"  (" +
				"   ID Int PRIMARY KEY AUTO_INCREMENT," +
				"   term  datetime," +
				"   EID  int," +
				"   Shift varchar(10)," +
				"   Type Int," +
				"   IsSection Int," +
				"   BeginTime Datetime,       " + // 班次开始时间
				"   EndTime Datetime,         " + // 班次结束时间
				"   BeginTime2 Datetime,      " + // 班次开始时间
				"   EndTime2 Datetime,        " + // 班次结束时间
				"   BeginTime3 Datetime,      " + // 班次开始时间
				"   EndTime3 Datetime,        " + // 班次结束时间
				"   BTScanBegintime Datetime, " + // BeginTime扫描开始时间
				"   BTScanEndtime Datetime,   " + // BeginTime扫描结束时间
				"   ETScanBegintime Datetime, " + // EndTime扫描开始时间
				"   ETScanEndtime Datetime,   " + // EndTime扫描结束时间
				"   BTScanBegintime2 Datetime," + // BeginTime2扫描开始时间
				"   BTScanEndtime2 Datetime,  " + // BeginTime2扫描结束时间
				"   ETScanBegintime2 Datetime," + // EndTime2扫描开始时间
				"   ETScanEndtime2 Datetime,  " + // EndTime2扫描结束时间
				"   BTScanBegintime3 Datetime," + // BeginTime3扫描开始时间
				"   BTScanEndtime3 Datetime,  " + // BeginTime3扫描结束时间
				"   ETScanBegintime3 Datetime," + // EndTime3扫描开始时间
				"   ETScanEndtime3 Datetime,  " + // EndTime3扫描结束时间
				"   CardBeginTime Datetime,   " + // 打卡开始时间
				"   CardEndTime Datetime,     " + // 打卡结束时间
				"   CardBeginTime2 Datetime,  " + // 打卡开始时间
				"   CardEndTime2 Datetime,    " + // 打卡结束时间
				"   CardBeginTime3 Datetime,  " + // 打卡开始时间
				"   CardEndTime3 Datetime,    " + // 打卡结束时间
				"   oddFlag Bit," +
				"   SourceType Int," +
				"   SeqID int," +
				"    corpid  int" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//加索引
		sql = "create index aAnal_CardIn_index on t_aAnal_CardIn (Term,EID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//Select Term INTO @xTerm from atAttend_Process Where AgentMode=_AgentMode;
		//sql="Select term from atAttend_Process Where AgentMode="+agentmode+";";
		//Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		//String xTerm=map.get("term").toString();

		String isDetailsDepID = "Y";

		//考勤分析的检查
//		call aSP_AnalysisCheckSubEasy (_DepID,@IsDetailsDepID,_Badge,_BeginDate,_EndDate,_AgentMode,_UserID,_RetVal);
		R r = getAnalysisCheckSubEasy(depid, "Y", badge, begindate, enddate, agentmode);
		if(r.getCode()!=0){
			return r;
		}
		String analyTime = new Date().toString();

		sql="DROP TABLE IF EXISTS t_Resu";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_Resu(DepID INT);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		Integer adminID = depid;

		sql = "INSERT INTO t_Resu" +
				"  SELECT DepID FROM otdepartment WHERE AdminID=" + adminID + " AND DepID=" + depid + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		List<Map> maps = systpaystditemMapper.listSalaryTemplate4(sql);
		StringBuffer depIds=new StringBuffer();
		for(Map map2:maps){
			depIds.append(map2.get("DepID")+",");
		}
		String depIds1=depIds.length()==0?null:depIds.toString().substring(0,depIds.length());
		for (int i = 20; i > 0; i--) {
			sql = "INSERT INTO t_Resu" +
					"     SELECT DepID from otDepartment" +
					"   WHERE AdminID IN ("+depIds1+") AND DepID NOT IN ("+depIds1+");";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

		//分析状态
		sql = "INSERT INTO t_aAnal_Status(Term,EID,Shift,ATStatus,AnalyMode,ProcessFlag,BeginTime1,EndTime1,BeginTime2,EndTime2,BeginTime3,EndTime3,corpid) " +
				"  SELECT a.Term,a.EID,a.Shift,c.ATStatus ,a.AnalyMode,a.ProcessFlag,a.BeginTime,a.EndTime,a.BeginTime2,a.EndTime2,a.BeginTime3,a.EndTime3" + "," + corpid +
				"  FROM atPlan_Range a,aVW_Status  b ,aVW_AttendStatusLis c" +
				"  WHERE  " +
				"  date_format( a.Term, '%Y-%m-%d' )>='" + begindate.substring(0, 10) + "'" +
				"  and date_format( a.Term, '%Y-%m-%d' )<='" + enddate.substring(0, 10) + "'" +
				"   And (" + badge + " ='' Or b.Badge = " + badge + ")" +
				"   And (" + depid + " ='' " +
				"    Or '" + isDetailsDepID + "' ='N' and b.DepID = " + depid +
				"    Or '" + isDetailsDepID + "' ='Y' " +
				"     and Exists(select 1 from t_Resu d " +
				"        where d.DepID=b.DepID         " +
				"       )" +
				"     )" +
				"   And a.EID=b.EID and a.EID=c.EID And c.ATStatus=0" +
				//"   And a.Term between c.BeginDate and c.EndDate " +
				"   and date_format( a.Term, '%Y-%m-%d' )>=date_format( c.BeginDate, '%Y-%m-%d' )" +
				"   and date_format( a.Term, '%Y-%m-%d' )<=date_format( c.EndDate, '%Y-%m-%d' ) " +
				"   And a.AnalyMode<=5 and IFNULL(a.Initialized,0)=0 " +
				"   And b.AID=" + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//刷卡数据分析，产生刷卡序列
		//call aSP_CardAnalMainEasy 	(_BeginDate,_EndDate,_RetVal);

		R r2 = getCardAnalMainEasy(begindate, enddate);
		if(r2.getCode()!=0){
			return r2;
		}
		sql = "Delete a" +
				"  FROM t_aAnal_Status a,atPlan_Range b" +
				"  WHERE a.EID=b.EID and a.Term=b.Term and a.AnalyMode>b.Analymode;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 考勤分析
		// call aSP_AnalMainEasy (_BeginDate,_EndDate,_AgentMode,_retVal);
		getAnalMainEasy(begindate, enddate, agentmode);
		sql = "Update atPlan_Range a,t_aAnal_CardIn b" +
				"  Set a.CardBeginTime1=b.CardBeginTime,a.CardEndTime1=b.CardEndTime,a.CardBeginTime2=b.CardBeginTime2,a.CardEndTime2=b.CardEndTime2," +
				"    a.CardBeginTime3=b.CardBeginTime3,a.CardEndTime3=b.CardEndTime3" +
				"  Where a.EID=b.EID And date_format( a.Term, '%Y-%m-%d' )=date_format( b.Term, '%Y-%m-%d' );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除临时表
		sql = "drop table t_aAnal_Status;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "drop table t_aBal_Status;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table t_aAnal_CardIn;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE t_Resu;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		return R.ok("操作成功！");
	}
	@Transactional(rollbackFor = Exception.class)
	public void getAttendPeriodsEasy(String term, Integer agentmode) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();
		//当年的1月1号
		String begindate = "";
		String enddate = "";
		String firstDayForYear = term.substring(0, 4) + "-01-01";
		String xterm = firstDayForYear;
		//生成年度的第1个月的开始日期/结束日期
		QueryWrapper<AtattendPeriods> queryWrapper55 = new QueryWrapper<>();
		queryWrapper55.eq("corpid", corpid);
		queryWrapper55.ge("PERIOD_DIFF(date_format(Term,'%Y%m'),date_format('" + firstDayForYear + "','%Y%m'))", 1);
		queryWrapper55.eq("agentmode", agentmode);
		AtattendPeriods atattendPeriods55 = atattendPeriodsService.getOne(queryWrapper55);
		if (!StringUtils.isEmpty(atattendPeriods55)) {
			begindate = DateUtils.getDayPlus(atattendPeriods55.getEnddate(), 1);
			enddate = DateUtils.getDayPlus(DateUtils.getMonthPlus(begindate, 1), -1);

		} else {
			begindate = getbeginDate("M", agentmode, firstDayForYear);
			enddate = DateUtils.getLastDayOfMonth(begindate);
		}

		for (int i = 1; i < 13; i++) {
			//删除没有确认的考勤期间
			sql = "Delete from atAttend_Periods Where date_format( Term, '%Y-%m' )='" + xterm.substring(0, 7) + "'" +
					" And ifnull(Initialized,0)=0 And AgentMode= " + agentmode;
			systpaystditemMapper.listSalaryTemplate3(sql);
			//生成每个月的考勤期间
			QueryWrapper<AtattendPeriods> queryWrapper6 = new QueryWrapper<>();
			queryWrapper6.eq("corpid", corpid);
			queryWrapper6.eq("agentmode", agentmode);
			queryWrapper6.eq("date_format( term, '%Y-%m' )", xterm.substring(0, 7));
			AtattendPeriods atattendPeriods6 = atattendPeriodsService.getOne(queryWrapper6);
			if (StringUtils.isEmpty(atattendPeriods6)) {
				//Insert atAttend_Periods(AgentMode,Term,BeginDate,EndDate,initialized)
				//Select @AgentMode,@xTerm ,@BeginDate,@EndDate ,0
				AtattendPeriods atattendPeriods1 = new AtattendPeriods();
				atattendPeriods1.setCorpid(corpid);
				atattendPeriods1.setCorpcode(corpcode);
				atattendPeriods1.setAgentmode(agentmode);
				atattendPeriods1.setTerm(xterm);
				atattendPeriods1.setBegindate(begindate);
				atattendPeriods1.setEnddate(enddate);
				atattendPeriods1.setInitialized(0);
				atattendPeriodsService.save(atattendPeriods1);
			}
			xterm = DateUtils.getMonthPlus(xterm, 1);
			begindate = DateUtils.getDayPlus(enddate, 1);
			enddate = DateUtils.getLastDayOfMonth(begindate);

		}
		//每个月是否存在atAttend_Periods,如果存在重新生成,@BeginDate,@EndDate,@xTerm已经增加了一年
		for (int i = 1; i < 13; i++) {
			QueryWrapper<AtattendPeriods> queryWrapper6 = new QueryWrapper<>();
			queryWrapper6.eq("corpid", corpid);
			queryWrapper6.eq("agentmode", agentmode);
			queryWrapper6.eq("Initialized", 0);
			queryWrapper6.ge("date_format( Term, '%Y-%m' )", xterm.substring(0, 7));
			AtattendPeriods atattendPeriods6 = atattendPeriodsService.getOne(queryWrapper6);
			if (!StringUtils.isEmpty(atattendPeriods6)) {
				//删除没有确认的考勤期间
				atattendPeriodsService.remove(queryWrapper6);
				QueryWrapper<AtattendPeriods> queryWrapper7 = new QueryWrapper<>();
				queryWrapper7.eq("corpid", corpid);
				queryWrapper7.eq("agentmode", agentmode);
				queryWrapper7.ge("date_format( Term, '%Y-%m' )", xterm.substring(0, 7));
				AtattendPeriods atattendPeriods7 = atattendPeriodsService.getOne(queryWrapper7);
				if (StringUtils.isEmpty(atattendPeriods7)) {
					//生成每个月的考勤期间
					AtattendPeriods atattendPeriods1 = new AtattendPeriods();
					atattendPeriods1.setCorpid(corpid);
					atattendPeriods1.setCorpcode(corpcode);
					atattendPeriods1.setAgentmode(agentmode);
					atattendPeriods1.setTerm(xterm);
					atattendPeriods1.setBegindate(begindate);
					atattendPeriods1.setEnddate(enddate);
					atattendPeriodsService.save(atattendPeriods1);
				}
			}
			xterm = DateUtils.getMonthPlus(xterm, 1);
			begindate = DateUtils.getDayPlus(enddate, 1);
			enddate = DateUtils.getDayPlus(DateUtils.getMonthPlus(begindate, 1), -1);
			//@BeginDate,@EndDate,@xTerm增加了2年
		}
		//超出考勤期间的删除
		//	select @xEndDate = Max(EndDate) from atAttend_Periods Where AgentMode=@AgentMode
		sql = "select Max(DATE_FORMAT(EndDate,'%Y-%m-%d')) as xenddate  from `atattend_periods` Where AgentMode='" + agentmode + "'";
		Map map = systpaystditemMapper.listSalaryTemplate5(sql).get(0);


		sql = "select Min(Term) as begindate,Max(Term) as enddate from atCalendar Where AgentMode=" + agentmode;
		List<Map> list = systpaystditemMapper.listSalaryTemplate5(sql);
		Map map2 = list.get(0);
		String xbegindate = "";
		//日历没有初始化
		if (StringUtils.isEmpty(map2)) {

			sql = "Select  Min(DATE_FORMAT(BeginDate,'%Y-%m-%d')) as begindate from atAttend_Periods Where AgentMode=" + agentmode;
			Map map3 = systpaystditemMapper.listSalaryTemplate5(sql).get(0);
			xbegindate = map3.get("begindate").toString();
		} else {
			xbegindate = enddate;
		}
		//--全部重新生成，或补足
		if (xbegindate.compareTo(map.get("xenddate").toString()) < 0) {
			generateCalendar(xbegindate, map.get("xenddate").toString(), agentmode);
		}
		//return "";
	}
	@Transactional(rollbackFor = Exception.class)
	public String getbeginDate(String datepart, Integer agentmode, String term) {
		String begindate = "";
		String enddate = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		QueryWrapper<AtattendPeriods> queryWrapper55 = new QueryWrapper<>();
		queryWrapper55.eq("corpid", corpid);
		queryWrapper55.ge("PERIOD_DIFF(date_format(Term,'%Y%m'),date_format('" + term + "','%Y%m'))", 1);
		queryWrapper55.eq("agentmode", agentmode);
		AtattendPeriods atattendPeriods55 = atattendPeriodsService.getOne(queryWrapper55);
		if (!StringUtils.isEmpty(atattendPeriods55)) {
			begindate = DateUtils.getDayPlus(atattendPeriods55.getEnddate(), 1);

		} else {
			Integer startDay = null;
			Integer monFlag = null;
			QueryWrapper<AtcdAttendperiod> queryWrapper5 = new QueryWrapper<>();
			queryWrapper5.eq("corpid", corpid);
			queryWrapper5.eq("agentmode", agentmode);
			AtcdAttendperiod atcdAttendperiod = atcdAttendperiodService.getOne(queryWrapper5);
			if (!StringUtils.isEmpty(atcdAttendperiod)) {
				startDay = atcdAttendperiod.getStartday();
				monFlag = atcdAttendperiod.getMonflag();
			} else {//没配置表给默认值
				startDay = 1;
				monFlag = 0;
			}
			//2020-02-02
			//当月startday  本月第几天
			String sstartday = "";
			if (startDay < 10) {
				sstartday = term.substring(0, 7) + "-" + "0" + startDay;
			} else {
				sstartday = term.substring(0, 7) + "-" + startDay;
			}

			Integer day = Integer.parseInt(term.substring(8, 10));
			if (day < startDay) {
				begindate = DateUtils.getMonthPlus(sstartday, -monFlag);
			} else {
				begindate = DateUtils.getMonthPlus(sstartday, -0);
			}
			//enddate = DateUtils.getLastDayOfMonth(begindate);
		}
		return begindate;
	}
	@Transactional(rollbackFor = Exception.class)
	public String generateCalendar(String xbegindate, String xenddate, Integer agentmode) {
		String begindate = "";
		String enddate = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();
		//生成日历
		String sql = "DROP  TABLE IF EXISTS t_aCalendar";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aCalendar2";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_iTable";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aCalendar" +
				"(" +
				" Term  datetime," +
				"xCategory tinyint," +
				"xType tinyint," +
				"AgentMode Int," +
				"corpid Int," +
				"corpcode varchar(200)" +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aCalendar2" +
				"(" +
				" Term  datetime," +
				"xCategory tinyint," +
				"xType tinyint," +
				"AgentMode Int," +
				"corpid Int," +
				"corpcode varchar(200)" +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_iTable(Num INT)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO t_iTable  SELECT -1 UNION SELECT 0 UNION  SELECT 1 UNION  SELECT 2 UNION SELECT 3  UNION SELECT 4 UNION SELECT 5";
		systpaystditemMapper.listSalaryTemplate3(sql);
		String seeddate = xbegindate;
		AtcdCalendartype atcdCalendartype = atcdCalendartypeService.getOne(Wrappers.query(new AtcdCalendartype()));
		Integer calendartypeid = atcdCalendartype.getId();
		while (seeddate.compareTo(xenddate) <= 0) {
			Integer week = DateUtils.getWeek(seeddate);
			Integer xtype = 0;
			if (week > 5) {
				xtype = 1;
			}
			sql = "INSERT INTO t_aCalendar(Term,xType,xCategory,AgentMode,corpid,corpcode) values('"
					+ seeddate + "'," + xtype + "," + calendartypeid + "," + agentmode + "," + corpid + ",'" + corpcode + "')";
			systpaystditemMapper.listSalaryTemplate3(sql);
			sql = "INSERT INTO t_aCalendar2(Term,xType,xCategory,AgentMode,corpid,corpcode) values('"
					+ seeddate + "'," + xtype + "," + calendartypeid + "," + agentmode + "," + corpid + ",'" + corpcode + "')";
			systpaystditemMapper.listSalaryTemplate3(sql);
			seeddate = DateUtils.getDayPlus(seeddate, 1);
		}

		//公历
		sql = "UPDATE t_aCalendar a,atCD_CalendarHolidayDef b ,t_iTable c " +
				" SET a.xType =2 " +
				" WHERE a.Term BETWEEN '" + xbegindate +
				"' AND '" + xenddate +
				"  '" +
				" AND b.xType=0 and b.xDate IS NOT NULL " +
				" AND CONVERT(DATE_ADD(a.Term,INTERVAL -c.Num DAY),CHAR)=CONVERT(b.xDate,CHAR) " +
				" AND c.Num BETWEEN b.xBegin AND b.xEnd " +
				" and a.corpid=" + corpid + " and a.AgentMode = " + agentmode;
		systpaystditemMapper.listSalaryTemplate3(sql);
		//农历节气
		sql = "UPDATE t_aCalendar a,atCD_CalendarHolidayDef b ,t_iTable c  " +
				" SET a.xType =2  " +
				" Where " +
				"   date_format(a.term, '%Y-%m-%d') >= '" + xbegindate +
				"' and date_format(a.term, '%Y-%m-%d') <= '" + xenddate +
				"' and b.xType=1 and b.xDate is not null  " +
				" and Substring(aFN_CalendarGetLunar(DATE_ADD(a.Term,INTERVAL -c.Num Day)),6,5) = Convert(b.xDate,CHAR) " +
				" and c.Num >= b.xBegin and c.Num<=b.xEnd  " +
				" and a.corpid=" + corpid + " and a.AgentMode = " + agentmode +
				//排除闰月的情况
				" and  Not Exists ( Select 1 from t_aCalendar2 d  " +
				" Where  a.xCategory=d.xCategory " +
				" and  date_format(d.term, '%Y-%m-%d') >=  '" + xbegindate +
				"' and date_format(d.term, '%Y-%m-%d') <= " +
				" '" + xenddate +
				"' and aFN_CalendarGetLunar(a.Term)=aFN_CalendarGetLunar(d.Term) " +
				" and d.Term <a.Term )";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//date_format(term, '%Y-%m-')

		sql = " Delete from atCalendar a " +
				" Where   a.Term Between '" + xbegindate + "' and '" + xenddate + "' " +
				" and a.corpid=" + corpid + " and a.AgentMode = " + agentmode +
				" and Exists(select 1 from t_aCalendar b " +
				" where  a.xCategory=b.xCategory " +
				" and a.Term=b.Term And a.AgentMode=b.AgentMode)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Delete from atCalendar_all a " +
				" Where a.Term Between '" + xbegindate +
				"' and '" + xenddate + "'" +
				" and a.corpid=" + corpid + " and a.AgentMode = " + agentmode;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert into atCalendar(Term,xType,xCategory,AgentMode,corpid,corpcode) " +
				" select Term,xType,xCategory, " + agentmode + "," + corpid + ",'" + corpcode + "'" +
				" from t_aCalendar ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//增加数据到历史表aCalendar_All
		sql = "Insert Into atCalendar_All(AgentMode,Term,xCategory,xType,BeginDate,EndDate,corpid,corpcode) " +
				" Select AgentMode,Term,xCategory,xType,'" + currentTime.substring(0, 10) +
				"','9990-12-31' " + "," + corpid + ",'" + corpcode + "'" +
				" From  t_aCalendar a " +
				" where Term between '" + xbegindate +
				"' and '" + xenddate + "'" +
				" and a.corpid=" + corpid + " and a.AgentMode = " + agentmode;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP  TABLE IF EXISTS t_aCalendar";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aCalendar2";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_iTable";
		systpaystditemMapper.listSalaryTemplate3(sql);
		return begindate;
	}
	@Transactional(rollbackFor = Exception.class)
	public String generateGroupTurnGenEasy(String groupid, String begindate, String enddate, Integer agentmode) {
		begindate = begindate.substring(0, 10);
		enddate = enddate.substring(0, 10);
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();
		String flgMonth = null;
		//Select  @FlgMonth = Term  from atAttend_Periods Where datediff(day,BeginDate,@BeginDate) =0 And AgentMode=@AgentMode
		QueryWrapper<AtattendPeriods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("corpid", corpid + "");
		queryWrapper1.eq("agentmode", agentmode + "");
		queryWrapper1.eq("date_format( term, '%Y-%m-%d' )", begindate.substring(0, 10));
		AtattendPeriods atattendPeriods1 = atattendPeriodsService.getOne(queryWrapper1);
		flgMonth = atattendPeriods1.getTerm();
		String sql = "";
		sql = "DROP  TABLE IF EXISTS t_aGroupShift";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aCalendar";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aGroup";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aGroup2";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aGroup_Ref";
		systpaystditemMapper.listSalaryTemplate3(sql);


		//创建轮班组排班临时表
		sql = " CREATE TEMPORARY TABLE t_aGroupShift " +
				" ( " +
				" Term date , " +
				" GroupID int , " +
				" Shift varchar(60) , " +
				" DayType int , " +
				" ReferPoint int, " +
				" BeginDate date, " +
				" corpid int, " +
				" agentmode int, " +
				" Primary Key (GroupID,Term) " +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//创建日历临时表，用于剪断排班
		sql = "CREATE TEMPORARY TABLE t_aCalendar " +
				" ( " +
				" GroupID Int, " +
				" Term date, " +
				" corpid int, " +
				" agentmode int, " +
				" RowNum int " +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create TEMPORARY Table  t_aGroup_Ref " +
				" ( " +
				" GroupID Int, " +
				" BeginDate Date,  " +
				" EndDate Date, " +
				" ReferPoint Int, " +
				" TurnID varchar(10)," +
				" corpid int, " +
				" agentmode int " +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);


		//创建轮班组临时表
		sql = " CREATE TEMPORARY TABLE t_aGroup " +
				" ( " +
				//" ID Int Primary Key, " +
				" ShiftMode int, " +
				" BeginDate date, " +
				" EndDate date, " +
				" TurnDayType varchar(2), " +
				" IsReset bit, " +
				" corpid int, " +
				" agentmode int, " +
				" Periods int," +
				" GroupID int" +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//创建轮班组临时表 复制表
		sql = " CREATE TEMPORARY TABLE t_aGroup2 " +
				" ( " +
				//" ID Int Primary Key, " +
				" ShiftMode int, " +
				" BeginDate date, " +
				" EndDate date, " +
				" TurnDayType varchar(2), " +
				" IsReset bit, " +
				" corpid int, " +
				" agentmode int, " +
				" Periods int," +
				" GroupID int" +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//从未排过班的轮班组的参照日期如果小于考勤期间的开始日期，调整为考勤期间的开始日期
		sql = " Update atGroup a set a.ReferDate= '" + begindate.substring(0,10) +
				"' where Not Exists(select 1 from aVW_Group_Ref_ALL b " +
				" where a.GroupID=b.GroupID And b.AgentMode= " + agentmode +
				" ) " +
				" and a.GroupID<>'-1' And a.AgentMode= " + agentmode +
				" and ifnull(a.ReferDate,'9990-12-31')<'" + begindate.substring(0,10) + "'";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//插入要排班轮班组，后面考虑只选有变化的轮班组
		sql = "Insert into t_aGroup(GroupID,ShiftMode,BeginDate,EndDate,TurnDayType,IsReset,corpid,agentmode) " +
				" Select GroupID,ShiftMode,a.ReferDate,'9990-12-31',TurnDayType,IsReset " + "," + corpid + "," + agentmode +
				" From atGroup a " +
				" Where a.AgentMode= " + agentmode + " and a.corpid =" + corpid +
				" And ifnull(a.DisableDate,'')='' " +
				"  And (ifnull(" + groupid +
				",0)=0 or a.GroupID = " + groupid +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//插入要排班轮班组，后面考虑只选有变化的轮班组 复制表
		sql = "Insert into t_aGroup2(GroupID,ShiftMode,BeginDate,EndDate,TurnDayType,IsReset,corpid,agentmode) " +
				" Select GroupID,ShiftMode,a.ReferDate,'9990-12-31',TurnDayType,IsReset " + "," + corpid + "," + agentmode +
				" From atGroup a " +
				" Where a.AgentMode= " + agentmode + " and a.corpid =" + corpid +
				" And ifnull(a.DisableDate,'')='' " +
				"  And (ifnull(" + groupid +
				",0)=0 or a.GroupID = " + groupid +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班的周期更新
		sql = "Update atShift_Group a,(Select TurnID,Count(1) As Num  From atShift_Group_Sub Group by TurnID) b    Set a.Periods = ifnull(b.Num,0)   " +
				" Where a.TurnID = b.TurnID And a.AgentMode=" + agentmode;

		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班组的周期更新
		sql = "Update atGroup a,(Select GroupID,Count(1) As Num    From atGroup_Turn Where GroupID In(Select GroupID From t_aGroup)  Group by GroupID) b Set a.Periods = ifnull(b.Num,0)*a.Interval  " +
				" Where a.GroupID = b.GroupID and a.GroupID<>-1 " +
				"  And a.AgentMode=" + agentmode +
				" And a.GroupID In(Select GroupID From t_aGroup2)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//对公共轮班组轮班、轮班班次、参考点、日历类型的判断
		//exec aSP_GroupTurnRunPublicCheckEasy NULL, @FlgMonth, @AgentMode,@RetVal output
		getGroupTurnRunPublicCheckEasy(null, flgMonth, agentmode);
		//删除参照日期之前的轮班组排班
		sql = "Delete a   " +
				" from atGroup_Ref a,atGroup b    " +
				" Where a.GroupID=b.GroupID And b.AgentMode= " + agentmode +
				" and date_format( a.Term, '%Y-%m-%d' )>date_format( b.ReferDate, '%Y-%m-%d' ) " +
				" and ( ifnull(" + groupid +
				",0)=0  or  a.GroupID =" + groupid +
				" )   " +
				" And b.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//删除下个期间的轮班组参照
		sql = "Delete a " +
				" from atGroup_Ref a " +
				" Where a.Term >'" + enddate +
				"' And a.AgentMode=" + agentmode +
				" and ( ifnull(" + groupid +
				",0)=0 or a.GroupID =" + groupid +
				" ) " +
				" And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//生成本考勤期间定义的轮班组的排班参照aGroup_Ref
		sql = " Delete a   " +
				" from atGroup_Ref a, atGroup b    " +
				" Where a.GroupID=b.GroupID And a.AgentMode=b.AgentMode And b.AgentMode=" + agentmode +
				" and b.ReferDate between '" + begindate.substring(0,10) +
				"' and     '" + enddate.substring(0,10) +
				"' and a.Term between '" + begindate.substring(0,10) +
				"'  and    '" + enddate.substring(0,10) +
				"' and (ifnull(" + groupid +
				",0)=0 or a.GroupID =" + groupid +
				")   " +
				" And b.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//第一次排班的轮班组
		sql = " insert atGroup_Ref(Term,GroupID,TurnID,ReferPoint,AgentMode,corpid)   " +
				" Select a.ReferDate Term,a.GroupID,b.TurnID,a.ReferPoint,a.AgentMode " + "," + corpid +
				" from atGroup a, atGroup_Turn b   " +
				" Where  a.GroupID=b.GroupID and b.xOrder=1 And a.AgentMode=" + agentmode +
				" and not exists(Select 1 from atGroup_Ref c    " +
				" Where a.GroupID=c.GroupID and c.Term between '" + begindate.substring(0,10) +
				"'  and '" + enddate.substring(0,10) +
				"' And c.AgentMode=" + agentmode +
				" )  " +
				" and a.ReferDate Between '" + begindate.substring(0,10) +
				"' and    '" + enddate.substring(0,10) +
				"' and (ifnull(" + groupid +
				",0)=0  or  a.GroupID =" + groupid +
				")   " +
				"  And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//Exec aSP_GroupTurnGenRef @AgentMode,@BeginDate,@EndDate , @RetVal Output
		getGroupTurnGenRef(agentmode, begindate.substring(0,10), enddate.substring(0,10));
		//轮班组排班主程序
		//	Exec  aSP_GroupTurnGenSub @AgentMode,@BeginDate,@EndDate ,@RetVal output
		getGroupTurnGenSub(agentmode, begindate.substring(0,10), enddate.substring(0,10));
		//生成轮班组排班
		sql = "Delete a " +
				" from atGroupShift  a  " +
				" Where " + " a.corpid = " + corpid +
				" and date_format( Term, '%Y-%m-%d' )<='" + begindate.substring(0, 10) + "'    " +
				" And date_format( Term, '%Y-%m-%d' )>='" + enddate.substring(0, 10) + "'   " +
				" and (ifnull(" + groupid +
				",'')=''  or  GroupID =" + groupid +
				")  " +
				" and Not Exists(Select 1 from aVW_Attend_Periods_All b  " +
				"  Where a.Term<=b.EndDate and b.Closed=1 And b.AgentMode= " + agentmode +
				" )  " +
				" And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = " insert into atGroupShift(Term,GroupID,Shift,DayType,AgentMode,corpid) " +
				" Select Term,GroupID,Shift,xType DayType, " + agentmode + "," + corpid +
				" from  t_GroupShift a " +
				" Where a.corpid = " + corpid +
				" and a.Term between '" + begindate.substring(0, 10) +
				"' and  '" + enddate.substring(0, 10) +
				"' and Not Exists(Select 1 from aVW_Attend_Periods_All b " +
				" Where a.Term<=b.EndDate and b.Closed=1 And b.AgentMode=" + agentmode + ")";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//设置轮班组参照排班为已经排班
		sql = " Update atGroup_Ref a set Initialized=1 " +
				" Where Term between '" + begindate.substring(0, 10) + "' and '" + enddate.substring(0, 10) + "' And a.AgentMode= " + agentmode +
				" and  (ifnull(" + groupid + ",'')=''  or  GroupID =" + groupid +
				") " +
				" and Not Exists(Select 1 from aVW_Attend_Periods_All b " +
				" Where a.Term<=b.EndDate and b.Closed=1 And b.AgentMode=" + agentmode +
				"      ) " +
				" And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//下个月的第一天
		sql = " Delete a " +
				" from atGroup_Ref a,atAttend_Periods b  " +
				" Where  '" + DateUtils.getDayPlus(enddate, 1) +
				"' = b.BeginDate And a.AgentMode=" + agentmode +
				" And a.AgentMode=b.AgentMode " +
				" and a.Term Between b.BeginDate and b.EndDate  " +
				" and (ifnull(" + groupid +
				",'')=''  or  GroupID =" + groupid +
				") " +
				" and Not Exists(Select 1 from aVW_Attend_Periods_All b  " +
				" Where a.Term<=b.EndDate and b.Closed=1 And b.AgentMode= " + agentmode +
				"  ) " +
				" And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//依据本考勤期间的排班参照来推导下考勤期间的排班参照
		sql = " insert atGroup_Ref(Term,GroupID,TurnID,ReferPoint,AgentMode,corpid) " +
				" Select   a.Term,a.GroupID,a.TurnID,a.ReferPoint, " + agentmode + "," + corpid +
				" from t_GroupShift a " +
				" Where a.Term= '" + DateUtils.getDayPlus(enddate, 1) +
				"' and (ifnull(" + groupid + ",'')=''  or  a.GroupID =" + groupid +
				") " +
				" and Not Exists(Select 1 from atGroup b " +
				" Where a.GroupID=b.GroupID and b.DisableDate <='" + enddate.substring(0, 10) +
				"' And b.AgentMode= " + agentmode +
				"     ) " +
				" and Not Exists(Select 1 from aVW_Attend_Periods_All b " +
				" Where a.Term<=b.EndDate and b.Closed=1 And b.AgentMode=" + agentmode + ")";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//删除表最新轮班组信息(aGroup_Bak)本月份需要排班的轮班组
		sql = "delete a " +
				" from atGroup_Bak a " +
				" where  (ifnull(" + groupid +
				",'')='' or a.GroupID =" + groupid +
				") and date_format( a.FlgMonth, '%Y-%m' )='" + flgMonth.substring(0, 7) +
				"' And a.AgentMode= " + agentmode +
				" And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 插入表最新轮班组信息(aGroup_Bak)本月份需要排班的轮班组的最新信息
		sql = " insert atGroup_Bak(GroupID,Title,ReferDate,xCalendar,ShiftMode,Remark,DisableDate,xOrder,TurnDayType,Periods,`Interval`,ReferPoint, " +
				" FlgMonth,Flag,AgentMode,Initialized,corpid) " +
				" select distinct a.GroupID,a.Title,a.ReferDate,a.xCalendar,a.ShiftMode,a.Remark,a.DisableDate,a.xOrder,a.TurnDayType,a.Periods,a.Interval, " +
				" a.ReferPoint, '" + flgMonth +
				"','Normal',a.AgentMode,a.Initialized " + "," + corpid +
				" from atGroup a,atGroup_Ref b " +
				" where  a.GroupID=b.GroupID And a.AgentMode=" + agentmode +
				" And a.AgentMode=b.AgentMode " +
				" and  (ifnull(" + groupid +
				",'')=''  or  a.GroupID =" + groupid +
				") And b.Term between '" + begindate.substring(0, 10) +
				"' and  '" + enddate.substring(0, 10) +
				"' And a.GroupID In(Select GroupID From t_aGroup)";
		systpaystditemMapper.listSalaryTemplate3(sql);


		sql = "DROP  TABLE IF EXISTS t_aGroupShift";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aCalendar";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aCalendar2";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aGroup";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aGroup2";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_aGroup_Ref";
		systpaystditemMapper.listSalaryTemplate3(sql);


		return begindate;
	}
	@Transactional(rollbackFor = Exception.class)
	public R getShiftTurnRunEasy(@RequestBody(required = false) Map term2) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();
		String nyterm = "";
		if (!StringUtils.isEmpty(term2)) {
			nyterm = term2.get("term").toString().substring(0, 7);
		} else {
			R.failed("考勤月份不能为空");
		}
		String term = term2.get("term").toString();
		Integer agentmode = null;
		QueryWrapper<AtcdAgentmode> atcdAgentmodeQueryWrapper = new QueryWrapper<>();
		atcdAgentmodeQueryWrapper.eq("corpcode", corpcode);
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(atcdAgentmodeQueryWrapper);
		agentmode = atcdAgentmode.getId();

		sql = "DROP PROCEDURE IF EXISTS `aSP_ShiftTurnRunEasy`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aStatus_Ins`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aStatus_Ins" +
				"  (" +
				"   EID int ," +
				"   GroupID Int," +
				"   BeginDate Date," +
				"   EndDate Date" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aStatus_Ins On t_aStatus_Ins (EID,GroupID,BeginDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aStatus_Del`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aStatus_Del" +
				"  (" +
				"   EID int ," +
				"   BeginDate Date," +
				"   EndDate Date" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aStatus_Del On t_aStatus_Del (EID,BeginDate,EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<AtattendPeriods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper1.eq("agentmode", agentmode);
		List<AtattendPeriods> atattendPeriods = atattendPeriodsService.list(queryWrapper1);
		String begindate = atattendPeriods.get(0).getBegindate();
		String enddate = atattendPeriods.get(0).getEnddate();

		//考勤状态、轮班组、登记的日期3者的交集(在这里登记的日期为考勤的期间)
		sql = "insert t_aStatus_Ins (EID,GroupID,BeginDate,EndDate) " +
				"  Select a.EID,b.GroupID," +
				"    Case When " +
				"      Case When date_format(a.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10) + "'  then date_format(a.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10)  + "' end  >=" +
				"      Case When date_format(b.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10)  + "'  then date_format(b.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10)  + "' end " +
				" Then Case When date_format(a.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10)  + "'  then date_format(a.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10)  + "' end" +
				" Else Case When date_format(b.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10)  + "'  then date_format(b.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10)  + "' end " +
				"    End , " +
				"    Case When  " +
				"      Case when date_format(a.EndDate, '%Y-%m-%d' )   <'" + enddate.substring(0,10)  + "'    then date_format(a.EndDate, '%Y-%m-%d' )    else '" + enddate.substring(0,10)  + "' end   >=" +
				"      Case when date_format(b.EndDate, '%Y-%m-%d' )   <'" + enddate.substring(0,10)  + "'    then date_format(b.EndDate, '%Y-%m-%d' )    else '" + enddate.substring(0,10)  + "' end " +
				" Then Case when date_format(b.EndDate, '%Y-%m-%d' )   <'" + enddate.substring(0,10)  + "'    then date_format(b.EndDate, '%Y-%m-%d' )    else '" + enddate.substring(0,10)  + "' end " +
				" Else Case when date_format(a.EndDate, '%Y-%m-%d' )   <'" + enddate.substring(0,10)  + "'    then date_format(a.EndDate, '%Y-%m-%d' )    else '" + enddate.substring(0,10)  + "' end" +
				"    End  " +
				"  from aVW_AttendStatusLis a,aVW_GroupChangeLis b" +
				"  Where a.EID=b.EID " +
				"     and " +
				"    Case When " +
				"      Case When date_format(a.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10) + "'  then date_format(a.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10) + "' end  >=" +
				"      Case When date_format(b.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10) + "'  then date_format(b.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10) + "' end " +
				" Then Case When date_format(a.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10) + "'  then date_format(a.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10) + "' end" +
				" Else Case When date_format(b.BeginDate, '%Y-%m-%d' ) >'" + begindate.substring(0,10) + "'  then date_format(b.BeginDate, '%Y-%m-%d' ) else '" + begindate.substring(0,10) + "' end " +
				"    End <= " +
				"    Case When  " +
				"     Case when date_format(a.EndDate, '%Y-%m-%d' ) <'" + enddate.substring(0,10) + "'  then date_format(a.EndDate, '%Y-%m-%d' ) else '" + enddate.substring(0,10) + "' end>=Case when date_format(b.EndDate, '%Y-%m-%d' ) <'" + enddate.substring(0,10) + "' then date_format(b.EndDate, '%Y-%m-%d' ) else '" + enddate.substring(0,10) + "' end " +
				"    Then    Case when date_format(b.EndDate, '%Y-%m-%d' )   <'" + enddate.substring(0,10) + "'    then date_format(b.EndDate, '%Y-%m-%d' ) else '" + enddate.substring(0,10) + "' end " +
				"    Else    Case when date_format(a.EndDate, '%Y-%m-%d' )   <'" + enddate.substring(0,10) + "'    then date_format(a.EndDate, '%Y-%m-%d' ) else '" + enddate.substring(0,10) + "' end" +
				" END" +
				"   and a.ATStatus in (0)" +
				"   And a.EID In(Select EID from atStatus Where AID =" + agentmode + ");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除原有排班，未手动排班过
		sql = "Delete a From atShift a" +
				"  Where " + " a.corpid = " + corpid +
				" and Exists(Select 1 From t_aStatus_Ins b" +
				"     Where a.EID = b.EID and date_format(a.Term, '%Y-%m-%d' ) between date_format(b.BeginDate, '%Y-%m-%d' )  and date_format(b.EndDate, '%Y-%m-%d' )" +
				"     )  " +
				"  and a.ManualShift is NULL";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//入离职删除排班
		sql = "UPDATE t_aStatus_Ins a,etEmployee b" +
				"  SET a.BeginDate = b.JoinDate" +
				"  WHERE " + " b.corpid = " + corpid +
				" and a.EID = b.EID and date_format(a.BeginDate, '%Y-%m-%d' )  < date_format(b.JoinDate, '%Y-%m-%d' ) ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aStatus_Ins a,etEmployee b " +
				"     SET a.EndDate = b.LeaveDate " +
				"    Where " + " b.corpid = " + corpid +
				"  and a.EID = b.EID and date_format(a.EndDate, '%Y-%m-%d' )  > IFNULL(b.LeaveDate,'9990-12-31');";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//存在手动排班更新
		sql = "UPDATE atShift a,atGroupShift b,t_aStatus_Ins c  " +
				"    SET a.Shift = b.Shift,a.DayType = b.DayType " +
				"    WHERE " + " a.corpid = " + corpid +
				"      and a.ManualShift is Not NULL " +
				"      and b.GroupID = c.GroupID " +
				"      and date_format( b.Term, '%Y-%m-%d' )  Between date_format(c.BeginDate, '%Y-%m-%d' )   and date_format(c.EndDate, '%Y-%m-%d' )   " +
				"      and date_format(a.Term, '%Y-%m-%d' )   = date_format(b.Term, '%Y-%m-%d' )  " +
				"      and a.EID = c.EID ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//重新插入排班
		sql = "Insert into atShift(Term,EID,Shift,DayType,corpid,corpcode) " +
				"    Select a.Term,b.EID,a.Shift,a.DayType," + corpid + ",'" + corpcode + "'" +
				"    From atGroupShift a,t_aStatus_Ins b " +
				"    Where " + " a.corpid = " + corpid + " and a.AgentMode=" + agentmode +
				"      and a.GroupID = b.GroupID " +
				" and date_format(a.Term, '%Y-%m-%d') >=  date_format(b.BeginDate, '%Y-%m-%d') " +
				" and  date_format(a.Term, '%Y-%m-%d') <=  date_format(b.EndDate, '%Y-%m-%d') " +
				"      and Not Exists(Select 1 From atShift c Where a.Term = c.Term and b.EID = c.EID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO atShift_Work(Term,EID,Badge,Name,CompID,DepID,JobID,corpid,corpcode) " +
				"    SELECT '" + term + "',a.EID,a.Badge,a.Name,a.CompID,a.DepID,a.JobID, " + corpid + ",'" + corpcode + "'" +
				"    FROM etEmployee a " +
				"    Where " + " a.corpid =" + corpid +
				"  and Exists(select 1 from t_aStatus_Ins b  " +
				"          where a.EID=b.EID  " +
				"            and case when date_format(b.BeginDate, '%Y-%m-%d')  >='" + begindate.substring(0,10) + "' then date_format(b.BeginDate, '%Y-%m-%d')  else '" + begindate.substring(0,10) + "' end <= " +
				"                case when date_format(b.EndDate, '%Y-%m-%d')  <='" + enddate.substring(0,10) + "' then date_format(b.EndDate, '%Y-%m-%d')  else '" + enddate.substring(0,10) + "' end " +
				"          ) " +
				"      and Not Exists(select 1 from atShift_Work b  " +
				"              where a.EID=b.EID  " +
				"                And date_format( b.Term, '%Y-%m' )='" + nyterm + "' " +
				"            );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "UPDATE atShift_Work a JOIN (Select c.EID," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 1 Then c.Shift Else Null End) As S1," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 2 Then c.Shift Else Null End) As S2," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 3 Then c.Shift Else Null End) As S3," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 4 Then c.Shift Else Null End) As S4," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 5 Then c.Shift Else Null End) As S5," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 6 Then c.Shift Else Null End) As S6," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 7 Then c.Shift Else Null End) As S7," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 8 Then c.Shift Else Null End) As S8," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 9 Then c.Shift Else Null End) As S9," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 10 Then c.Shift Else Null End) As S10," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 11 Then c.Shift Else Null End) As S11," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 12 Then c.Shift Else Null End) As S12," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 13 Then c.Shift Else Null End) As S13," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 14 Then c.Shift Else Null End) As S14," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 15 Then c.Shift Else Null End) As S15," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 16 Then c.Shift Else Null End) As S16," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 17 Then c.Shift Else Null End) As S17," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 18 Then c.Shift Else Null End) As S18," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 19 Then c.Shift Else Null End) As S19," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 20 Then c.Shift Else Null End) As S20," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 21 Then c.Shift Else Null End) As S21," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 22 Then c.Shift Else Null End) As S22," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 23 Then c.Shift Else Null End) As S23," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 24 Then c.Shift Else Null End) As S24," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 25 Then c.Shift Else Null End) As S25," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 26 Then c.Shift Else Null End) As S26," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 27 Then c.Shift Else Null End) As S27," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 28 Then c.Shift Else Null End) As S28," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 29 Then c.Shift Else Null End) As S29," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 30 Then c.Shift Else Null End) As S30," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 31 Then c.Shift Else Null End) As S31," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 32 Then c.Shift Else Null End) As S32," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 33 Then c.Shift Else Null End) As S33," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 34 Then c.Shift Else Null End) As S34," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 35 Then c.Shift Else Null End) As S35," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 36 Then c.Shift Else Null End) As S36," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 37 Then c.Shift Else Null End) As S37," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 38 Then c.Shift Else Null End) As S38," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 39 Then c.Shift Else Null End) As S39," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 40 Then c.Shift Else Null End) As S40," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 41 Then c.Shift Else Null End) As S41," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 42 Then c.Shift Else Null End) As S42," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 43 Then c.Shift Else Null End) As S43," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 44 Then c.Shift Else Null End) As S44," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 45 Then c.Shift Else Null End) As S45," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 46 Then c.Shift Else Null End) As S46," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 47 Then c.Shift Else Null End) As S47," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 48 Then c.Shift Else Null End) As S48," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 49 Then c.Shift Else Null End) As S49," +
				"     Max(Case datediff(c.Term,'" + begindate + "')+1 When 50 Then c.Shift Else Null End) As S50 " +
				"    From atShift c Where " + " c.corpid=" + corpid +
				"		and c.Term between '" + begindate + "' and '" + enddate + "' " +
				"      and Exists(select 1 from t_aStatus_Ins e where c.EID=e.EID and case when e.BeginDate >='" + begindate.substring(0,10) + "' then e.BeginDate " +
				"      else '" + begindate + "' end <=case when e.EndDate <='" + enddate + "' then e.EndDate else '" + enddate.substring(0,10) + "' end)" +
				"    Group by c.EID) b ON a.EID=b.EID " +
				"      Set a.S1=b.S1,a.S2=b.S2,a.S3=b.S3,a.S4=b.S4,a.S5=b.S5," +
				"      a.S6=b.S6,a.S7=b.S7,a.S8=b.S8,a.S9=b.S9,a.S10=b.S10," +
				"      a.S11=b.S11,a.S12=b.S12,a.S13=b.S13,a.S14=b.S14,a.S15=b.S15," +
				"      a.S16=b.S16,a.S17=b.S17,a.S18=b.S18,a.S19=b.S19,a.S20=b.S20," +
				"      a.S21=b.S21,a.S22=b.S22,a.S23=b.S23,a.S24=b.S24,a.S25=b.S25," +
				"      a.S26=b.S26,a.S27=b.S27,a.S28=b.S28 ,a.S29=b.S29,a.S30=b.S30," +
				"      a.S31=b.S31,a.S32=b.S32,a.S33=b.S33,a.S34=b.S34,a.S35=b.S35," +
				"      a.S36=b.S36,a.S37=b.S37,a.S38=b.S38 ,a.S39=b.S39,a.S40=b.S40, " +
				"      a.S41=b.S41,a.S42=b.S42,a.S43=b.S43,a.S44=b.S44,a.S45=b.S45," +
				"      a.S46=b.S46,a.S47=b.S47,a.S48=b.S48 ,a.S49=b.S49,a.S50=b.S50 " +
				"    Where  date_format( a.Term, '%Y-%m' )='" + nyterm + "';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//不处于考勤分析不排班
		sql = "DELETE a " +
				"  from atShift a,aVW_AttendStatusLis b " +
				"  where " + " a.corpid= " + corpid +
				" and a.eid=b.eid and a.term Between b.begindate and b.enddate" +
				"   And b.ATStatus=4 And a.EID In(Select EID from atStatus Where AID ='" + agentmode + "');";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DELETE a " +
				"  from atShift_Work a,aVW_AttendStatusLis b " +
				"  where " + " a.corpid= " + corpid +
				" and a.eid=b.eid and a.term Between b.begindate and b.enddate" +
				"   And b.ATStatus=4 And a.EID In(Select EID from atStatus Where AID =" + agentmode + ");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DELETE a " +
				"  from atPlan_Range a,aVW_AttendStatusLis b " +
				"  where " + " a.corpid= " + corpid +
				" and  a.eid=b.eid and  a.term Between b.begindate and b.enddate" +
				"   And b.ATStatus=4 And a.EID In(Select EID from atStatus Where AID =" + agentmode + ");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DELETE a " +
				"  from atShift_STATIST a, aVW_AttendStatusLis b " +
				"  where  " + " a.corpid= " + corpid +
				" and a.eid=b.eid and  a.term Between b.begindate and b.enddate" +
				"   And b.ATStatus=4 And a.EID In(Select EID from atStatus  Where AID =" + agentmode + ");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//锁定班次
		sql = "Update atShift_Type a,atShift b,t_aStatus_Ins c Set a.Initialized = 1" +
				"  Where " + " a.corpid= " + corpid +
				" and b.EID = c.EID" +
				"   and b.Term Between c.BeginDate and c.EndDate  " +
				"   and a.AgentMode = " + agentmode +
				"   and a.Shift = Ifnull(b.ManualShift,b.Shift) " +
				"   and Ifnull(a.Initialized,0)= 0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//请假明细更新
		sql = "Update atPlan_Reg a,atPlan_Range b,atShift c,t_aStatus_Ins d,atShift_Type e " +
				"  Set a.BeginTime = aFN_GenDateTime(b.Term,e.BeginTime)," +
				"    a.EndTime = aFN_GenDateTime(b.Term,e.EndTime)" +
				"  Where " + " a.corpid= " + corpid +
				"	and a.Term = b.Term" +
				"   and a.EID = b.EID" +
				"   and a.EID = c.EID" +
				"   and a.Term = c.Term" +
				"   and e.AgentMode = " + agentmode +
				"   and a.EID = d.EID" +
				"   and b.Term between d.BeginDate and d.EndDate      " +
				"   and Ifnull(c.ManualShift,c.Shift) <> b.Shift" +
				"   and Ifnull(c.ManualShift,c.Shift) = e.Shift " +
				"   and a.BeginTime = b.BeginTime" +
				"   and a.EndTime = b.EndTime;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新每人每天班次时段表
		sql = "Update atPlan_Range a,atShift b,atShift_Type c,t_aStatus_Ins d" +
				"  Set a.Shift = Ifnull(b.ManualShift,b.Shift)," +
				"    a.BeginTime = aFN_GenDateTime(a.Term,c.BeginTime)," +
				"    a.EndTime = aFN_GenDateTime(a.Term,c.EndTime)," +
				"    a.ScanBeginTime = aFN_GenDateTime(a.Term,c.ScanBeginTime)," +
				"    a.ScanEndTime = aFN_GenDateTime(a.Term,c.ScanEndTime)," +
				"    a.ShouldWorkTime = c.xHours," +
				"    a.DayType = b.DayType" +
				"  Where " + " a.corpid= " + corpid +
				" 	and a.Term = b.Term" +
				"   and a.EID = b.EID" +
				"   and a.EID = d.EID" +
				"   and a.Term between d.BeginDate and d.EndDate" +
				"   and Ifnull(b.ManualShift,b.Shift) = c.Shift" +
				"   and c.AgentMode = " + agentmode + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成每人每天班次时段表
		sql = "Insert into atPlan_Range(Term,EID,GroupID,Shift,WorkNum,DayType,BeginTime,EndTime,BeginTime2,EndTime2,BeginTime3,EndTime3," +
				"  ScanBeginTime,ScanEndTime,ShouldWorkTime,corpid,corpcode)" +
				"  Select a.Term,a.EID,d.GroupID,Ifnull(a.ManualShift,a.Shift),c.type,a.DayType,aFN_GenDateTime(a.Term,c.BeginTime)," +
				"  aFN_GenDateTime(a.Term,c.EndTime)," +
				"  aFN_GenDateTime(a.Term,c.BeginTime2),aFN_GenDateTime(a.Term,c.EndTime2)," +
				"  aFN_GenDateTime(a.Term,c.BeginTime3),aFN_GenDateTime(a.Term,c.EndTime3)," +
				"  aFN_GenDateTime(a.Term,c.ScanBeginTime),aFN_GenDateTime(a.Term,c.ScanEndTime),c.xHours" + "," + corpid + ",'" + corpcode + "' " +
				"  From atShift a,atShift_Type c,t_aStatus_Ins d" +
				"  Where" + " a.corpid= " + corpid +
				"   and  Ifnull(a.ManualShift,a.Shift) = c.Shift" +
				"   And c.AgentMode = " + agentmode +
				"   And a.EID=d.EID and a.Term Between d.BeginDate and d.EndDate" +
				"   And Not Exists(Select 1 From atPlan_Range e Where a.EID = e.EID and a.Term = e.Term);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//根据班次类型、班次时段生成每天每人的班次时段
		//call aSP_PlanTWIDInitSimple (@BeginDate ,@EndDate,@AgentMode,@retval);
		getPlanTWIDInitSimple(begindate, enddate, agentmode);
		return R.ok("自动排班成功");

	}
	@Transactional(rollbackFor = Exception.class)
	public void getGroupTurnGenRef(Integer agentmode, String begindate, String enddate) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();

		sql = "DROP TABLE IF EXISTS `t_aGroup_Ref`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aGroup_Ref1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);


		sql = " CREATE TEMPORARY TABLE t_aGroup_Ref " +
				" ( " +
				" GroupID int, " +
				" Term date, " +
				" BeginDate date, " +
				" EndDate date, " +
				" TurnID int, " +
				" ReferPoint int," +
				" corpid int, " +
				" agentmode int " +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = " CREATE TEMPORARY TABLE t_aGroup_Ref1 " +
				" ( " +
				" GroupID int, " +
				" Term date, " +
				" BeginDate date, " +
				" EndDate date, " +
				" TurnID int, " +
				" ReferPoint int," +
				" corpid int, " +
				" agentmode int " +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY)
		sql = "insert t_aGroup_Ref(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode) " +
				"  SELECT a.GroupID,b.Term BeginDate,DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY) EndDate,c.TurnID,c.ReferPoint" + "," + corpid + "," + agentmode +
				"  FROM atGroup a, atCalendar b,atGroup_Turn c" +
				"  WHERE a.xCalendar=b.xCategory" +
				"   AND a.GroupID=c.GroupID And a.AgentMode=" + agentmode + " AND b.AgentMode=" + agentmode +
				"   AND ( " +
				"     (PERIOD_DIFF(date_format(b.Term,'%Y%m'),date_format(a.ReferDate,'%Y%m'))%a.Periods+a.Interval)/a.Interval = c.xOrder " +
				"    AND  (PERIOD_DIFF(date_format(b.Term,'%Y%m'),date_format(a.ReferDate,'%Y%m'))%a.Periods+a.Interval)%a.Interval = 0" +
				"    AND  a.Periods>1" +
				"    OR  a.Periods=1 and c.xOrder =1" +
				"   )" +
				"   and     day(b.Term)=1" +
				"   and " +
				"   Case " +
				"    When b.Term >= '" + begindate + "' " +
				"    Then b.Term " +
				"    Else '" + begindate + "' " +
				"   End <=" +
				"   Case " +
				"    When DATE_ADD('" + enddate + "',INTERVAL 1 DAY)<= DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY)" +
				"    Then DATE_ADD('" + enddate + "',INTERVAL 1 DAY)" +
				"    Else DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY)" +
				"   End  " +
				"   and a.TurnDayType ='1'" +
				"   and (a.DisableDate is null or a.DisableDate >='" + begindate + "' )";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//复制表
		sql = "insert t_aGroup_Ref1(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode) " +
				"  SELECT a.GroupID,b.Term BeginDate,DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY) EndDate,c.TurnID,c.ReferPoint" + "," + corpid + "," + agentmode +
				"  FROM atGroup a, atCalendar b,atGroup_Turn c" +
				"  WHERE a.xCalendar=b.xCategory" +
				"   AND a.GroupID=c.GroupID And a.AgentMode=" + agentmode + " AND b.AgentMode=" + agentmode +
				"   AND ( " +
				"     (PERIOD_DIFF(date_format(b.Term,'%Y%m'),date_format(a.ReferDate,'%Y%m'))%a.Periods+a.Interval)/a.Interval = c.xOrder " +
				"    AND  (PERIOD_DIFF(date_format(b.Term,'%Y%m'),date_format(a.ReferDate,'%Y%m'))%a.Periods+a.Interval)%a.Interval = 0" +
				"    AND  a.Periods>1" +
				"    OR  a.Periods=1 and c.xOrder =1" +
				"   )" +
				"   and     day(b.Term)=1" +
				"   and " +
				"   Case " +
				"    When b.Term >= '" + begindate + "' " +
				"    Then b.Term " +
				"    Else '" + begindate + "' " +
				"   End <=" +
				"   Case " +
				"    When DATE_ADD('" + enddate + "',INTERVAL 1 DAY)<= DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY)" +
				"    Then DATE_ADD('" + enddate + "',INTERVAL 1 DAY)" +
				"    Else DATE_ADD(DATE_ADD(b.Term,INTERVAL a.Interval MONTH),INTERVAL -1 DAY)" +
				"   End  " +
				"   and a.TurnDayType ='1'" +
				"   and (a.DisableDate is null or a.DisableDate >='" + begindate + "' );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班周期为考勤月
		sql = "insert t_aGroup_Ref(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				" Select  a.GroupID,c.BeginDate,e.EndDate,b.TurnID,b.ReferPoint" + "," + corpid + "," + agentmode +
				" from  atGroup a , " +
				"  atGroup_Turn b ," +
				"  atAttend_Periods c ," +
				"  atAttend_Periods d ," +
				"  atAttend_Periods e " +
				" Where a.GroupID=b.GroupID And a.AgentMode=" + agentmode +
				"  And c.AgentMode=d.AgentMode And c.AgentMode=e.AgentMode And c.AgentMode=" + agentmode +
				"  and  a.ReferDate Between d.BeginDate and d.EndDate  " +
				"  and (" +
				"    (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder " +
				"   and  (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)%a.Interval=0" +
				"   and     (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder " +
				"   and  a.Periods>1" +
				"   or  a.Periods=1" +
				"   and  b.xOrder =1" +
				"  ) " +
				"  and   PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(c.Term,'%Y%m')) between 0 and a.Interval -1 " +
				"  and  a.TurnDayType ='3'" +
				"  and  e.BeginDate =  DATE_ADD('" + enddate + "',INTERVAL 1 DAY)" +
				"  and     (a.DisableDate is null or a.DisableDate >=DATE_ADD('" + enddate + "',INTERVAL 1 DAY));";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//复制表加数据
		sql = "insert t_aGroup_Ref1(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				" Select  a.GroupID,c.BeginDate,e.EndDate,b.TurnID,b.ReferPoint" + "," + corpid + "," + agentmode +
				" from  atGroup a , " +
				"  atGroup_Turn b ," +
				"  atAttend_Periods c ," +
				"  atAttend_Periods d ," +
				"  atAttend_Periods e " +
				" Where a.GroupID=b.GroupID And a.AgentMode=" + agentmode +
				"  And c.AgentMode=d.AgentMode And c.AgentMode=e.AgentMode And c.AgentMode=" + agentmode +
				"  and  a.ReferDate Between d.BeginDate and d.EndDate  " +
				"  and (" +
				"    (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder " +
				"   and  (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)%a.Interval=0" +
				"   and     ((PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder " +
				"   and  a.Periods>1" +
				"   or  a.Periods=1" +
				"   and  b.xOrder =1" +
				"  ) " +
				"  and   PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(c.Term,'%Y%m'))  between 0 and a.Interval -1 " +
				"  and  a.TurnDayType ='3'" +
				"  and  e.BeginDate =  DATE_ADD('" + enddate + "',INTERVAL 1 DAY)" +
				"  and     (a.DisableDate is null or a.DisableDate >=DATE_ADD('" + enddate + "',INTERVAL 1 DAY)));";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班周期为考勤月
		sql = "insert t_aGroup_Ref(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				" Select  a.GroupID,c.BeginDate,e.EndDate,b.TurnID,b.ReferPoint" + "," + corpid + "," + agentmode +
				" from  atGroup a , " +
				"  atGroup_Turn  b ," +
				"  atAttend_Periods c ," +
				"  atAttend_Periods d ," +
				"  atAttend_Periods e " +
				" Where a.GroupID=b.GroupID And a.AgentMode=" + agentmode +
				"  And c.AgentMode=d.AgentMode And c.AgentMode=e.AgentMode And c.AgentMode=" + agentmode +
				"  and  a.ReferDate Between d.BeginDate and d.EndDate  " +
				"  and (" +
				"    (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder " +
				"   and  (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)%a.Interval=0" +
				"   and     (PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder     " +
				"   and  a.Periods>1" +
				"   or  a.Periods=1" +
				"   and  b.xOrder =1  " +
				"  )   " +
				"  and   PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(c.Term,'%Y%m')) between 0 and a.Interval -1 " +
				"  and  a.TurnDayType ='3'" +
				"  and  e.BeginDate =  '" + begindate +
				"'  and     (a.DisableDate is null or a.DisableDate >='" + begindate + "' )" +
				"  and  Not Exists(Select 1 from t_aGroup_Ref1 f " +
				"     Where a.GroupID=f.GroupID" +
				"      and '" + begindate + "' between f.BeginDate and f.EndDate " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班周期为考勤月 复制表加数据
		sql = "insert t_aGroup_Ref1(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				" Select  a.GroupID,c.BeginDate,e.EndDate,b.TurnID,b.ReferPoint" + "," + corpid + "," + agentmode +
				" from  atGroup a , " +
				"  atGroup_Turn  b ," +
				"  atAttend_Periods c ," +
				"  atAttend_Periods d ," +
				"  atAttend_Periods e " +
				" Where a.GroupID=b.GroupID And a.AgentMode=" + agentmode +
				"  And c.AgentMode=d.AgentMode And c.AgentMode=e.AgentMode And c.AgentMode=" + agentmode +
				"  and  a.ReferDate Between d.BeginDate and d.EndDate  " +
				"  and (" +
				"    (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder " +
				"   and  (PERIOD_DIFF(date_format(c.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)%a.Interval=0" +
				"   and     (PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(d.Term,'%Y%m'))%a.Periods+a.Interval)/a.Interval=b.xOrder     " +
				"   and  a.Periods>1" +
				"   or  a.Periods=1" +
				"   and  b.xOrder =1  " +
				"  )   " +
				"  and   PERIOD_DIFF(date_format(e.Term,'%Y%m'),date_format(c.Term,'%Y%m')) between 0 and a.Interval -1 " +
				"  and  a.TurnDayType ='3'" +
				"  and  e.BeginDate =  '" + begindate +
				"'  and     (a.DisableDate is null or a.DisableDate >='" + begindate + "' )" +
				"  and  Not Exists(Select 1 from t_aGroup_Ref f " +
				"     Where a.GroupID=f.GroupID" +
				"      and '" + begindate + "' between f.BeginDate and f.EndDate " +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班周期为天
		sql = "insert t_aGroup_Ref(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				"  Select a.GroupID,b.Term, DATE_FORMAT(DATE_ADD(b.Term,INTERVAL a.Interval-1 DAY),'%Y-%m-%d')," +
				"   c.TurnID,c.ReferPoint" + "," + corpid + "," + agentmode +
				"  from atGroup a, atCalendar b,atGroup_Turn c, atGroup_Turn d " +
				"  Where a.xCalendar=b.xCategory and a.GroupID=c.GroupID And a.AgentMode=" + agentmode + " And b.AgentMode=" + agentmode +
				"   and (" +
				"     ((datediff(b.Term, a.ReferDate  ))%a.Periods+a.Interval)/a.Interval = c.xOrder " +
				"    and  ((datediff(b.Term, a.ReferDate  ))%a.Periods+a.Interval)%a.Interval = 0" +
				"    and  a.Periods>1" +
				"    or  a.Periods=1" +
				"    and  c.xOrder =1  " +
				"   )" +
				"   and " +
				"   Case " +
				"    When b.Term >= '" + begindate + "' " +
				"    Then b.Term " +
				"    Else '" + begindate + "' " +
				"   End <=" +
				"   Case " +
				"    When DATE_ADD('" + enddate + "',INTERVAL 1 DAY)<=DATE_ADD(b.Term,INTERVAL a.Interval-1 Day)" +
				"    Then DATE_ADD('" + enddate + "',INTERVAL 1 DAY)" +
				"    Else  DATE_ADD(b.Term,INTERVAL a.Interval-1 Day)" +
				"   End  " +
				"   and     b.Term >=a.ReferDate" +
				"   and  a.TurnDayType ='2'" +
				"   and     (a.DisableDate is null or a.DisableDate >='" + begindate + "' )" +
				"   and  a.GroupID=d.GroupID" +
				"   and  d.xOrder=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//轮班周期为天 复制表加数据
		sql = "insert t_aGroup_Ref1(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				"  Select a.GroupID,b.Term, DATE_FORMAT(DATE_ADD(b.Term,INTERVAL a.Interval-1 DAY),'%Y-%m-%d')," +
				"   c.TurnID,c.ReferPoint" + "," + corpid + "," + agentmode +
				"  from atGroup a, atCalendar b,atGroup_Turn c, atGroup_Turn d " +
				"  Where a.xCalendar=b.xCategory and a.GroupID=c.GroupID And a.AgentMode=" + agentmode + " And b.AgentMode=" + agentmode +
				"   and (" +
				"     ((datediff(b.Term, a.ReferDate  ))%a.Periods+a.Interval)/a.Interval = c.xOrder " +
				"    and  ((datediff(b.Term, a.ReferDate  ))%a.Periods+a.Interval)%a.Interval = 0" +
				"    and  a.Periods>1" +
				"    or  a.Periods=1" +
				"    and  c.xOrder =1  " +
				"   )" +
				"   and " +
				"   Case " +
				"    When b.Term >= '" + begindate + "' " +
				"    Then b.Term " +
				"    Else '" + begindate + "' " +
				"   End <=" +
				"   Case " +
				"    When DATE_ADD('" + enddate + "',INTERVAL 1 DAY)<=DATE_ADD(b.Term,INTERVAL a.Interval-1 Day)" +
				"    Then DATE_ADD('" + enddate + "',INTERVAL 1 DAY)" +
				"    Else  DATE_ADD(b.Term,INTERVAL a.Interval-1 Day)" +
				"   End  " +
				"   and     b.Term >=a.ReferDate" +
				"   and  a.TurnDayType ='2'" +
				"   and     (a.DisableDate is null or a.DisableDate >='" + begindate + "' )" +
				"   and  a.GroupID=d.GroupID" +
				"   and  d.xOrder=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aGroup_Ref a,atGroup b  " +
				" Set a.ReferPoint=b.ReferPoint," +
				"     a.BeginDate =b.ReferDate " +
				" Where  a.GroupID=b.GroupID And b.AgentMode=" + agentmode +
				"  and b.ReferDate between a.BeginDate and a.EndDate;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//复制表加数据
		sql = "Update t_aGroup_Ref1 a,atGroup b  " +
				" Set a.ReferPoint=b.ReferPoint," +
				"     a.BeginDate =b.ReferDate " +
				" Where  a.GroupID=b.GroupID And b.AgentMode=" + agentmode +
				"  and b.ReferDate between a.BeginDate and a.EndDate;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aGroup_Ref a,atGroup_Ref b  " +
				"  Set a.ReferPoint=b.ReferPoint," +
				"     a.BeginDate =b.Term " +
				"  Where  a.GroupID=b.GroupID And b.AgentMode=" + agentmode +
				"   and a.TurnID=b.TurnID" +
				"   and b.Term Between a.BeginDate and a.EndDate " +
				"   and b.Term = '" + begindate +
				"'   and (" +
				"      Exists(Select 1 from atGroup c" +
				"      Where   a.GroupID=c.GroupID" +
				"      and  c.TurnDayType ='2' And c.AgentMode=" + agentmode +
				"     )" +
				"    or a.BeginDate <>  '" + begindate +
				"'   );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//复制表加数据
		sql = "Update t_aGroup_Ref1 a,atGroup_Ref b  " +
				"  Set a.ReferPoint=b.ReferPoint," +
				"     a.BeginDate =b.Term " +
				"  Where  a.GroupID=b.GroupID And b.AgentMode=" + agentmode +
				"   and a.TurnID=b.TurnID" +
				"   and b.Term Between a.BeginDate and a.EndDate " +
				"   and b.Term = '" + begindate +
				"'   and (" +
				"      Exists(Select 1 from atGroup c" +
				"      Where   a.GroupID=c.GroupID" +
				"      and  c.TurnDayType ='2' And c.AgentMode=" + agentmode +
				"     )" +
				"    or a.BeginDate <>  '" + begindate +
				"'   );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert t_aGroup_Ref(GroupID,BeginDate,EndDate,TurnID,ReferPoint,corpid,agentmode)" +
				"  Select a.GroupID,a.Term,DATE_FORMAT(DATE_ADD('" + enddate + "',INTERVAL 1 DAY),'%Y-%m-%d') , a.TurnID,a.ReferPoint" + "," + corpid + "," + agentmode +
				"  from atGroup_Ref a " +
				"  Where  a.Term  between '" + begindate + "' and '" + enddate + "' And a.AgentMode=" + agentmode +
				"   and " +
				"   Not Exists(Select 1 from t_aGroup_Ref1 b " +
				"     Where a.GroupID=b.GroupID" +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}
	@Transactional(rollbackFor = Exception.class)
	public void getGroupTurnRunPublicCheckEasy(Integer groupid, String term, Integer agentmode) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();
		//当年的1月1号
		String begindate = "";
		String enddate = "";
		String firstDayForYear = term.substring(0, 4) + "-01-01";
		String xterm = firstDayForYear;
		String dTerm = term.substring(0, 9);

		//符合排班条件的轮班组所对应的轮班班次不能为空
		sql = "Select a.groupid from atGroup a,atGroup_Turn b,atAttend_Periods h " +
				" Where a.GroupID = b.GroupID And h.AgentMode=" + agentmode +
				" And a.AgentMode=h.AgentMode " +
				" And  Not Exists(Select 1 from atShift_Group_Sub d Where d.TurnID = b.TurnID) " +
				" And ( ifnull(" + groupid +
				",'') =  '' OR a.GroupID = " + groupid +
				" ) " +
				" And ( " +
				" Case " +
				" When date_format( h.BeginDate, '%Y-%m-%d' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )   " +
				" Then ifnull(a.ReferDate,'9990-12-31') " +
				" Else h.BeginDate " +
				" End " +
				" <= " +
				" Case " +
				" When date_format( h.EndDate, '%Y-%m-%d' )<=date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				" Then ifnull(a.DisableDate,'9990-12-31') " +
				" Else h.EndDate " +
				" End   ) " +
				" And ( '" + term +
				"' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "')";
		List list = systpaystditemMapper.listSalaryTemplate(sql);
		if (list.size() > 0) {
			R.failed("符合排班条件的轮班组所对应的轮班班次不能为空");
		}
		//符合排班条件的轮班组所对应的参考点要在对应轮班班次的序号范围内
		sql = "Select 1 from atGroup a,atGroup_Turn b,atAttend_Periods h " +
				" Where a.GroupID = b.GroupID And h.AgentMode=" + agentmode +
				" And a.AgentMode=h.AgentMode " +
				"       And Not Exists(Select 1 from atShift_Group_Sub d Where d.TurnID = b.TurnID And d.xOrder = b.ReferPoint) " +
				"       And ( ifnull(" + groupid +
				",'') =  '' OR a.GroupID = " + groupid +
				" ) " +
				"       And (   " +
				"       Case   " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' ) " +
				"       Then ifnull(a.ReferDate,'9990-12-31')  " +
				"       Else h.BeginDate  " +
				"      End    " +
				"       <=  " +
				"       Case    " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<=date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31')  " +
				"       Else h.EndDate  " +
				"       End   ) " +
				"       And ( '" + term +
				"' is null  OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) +
				"' )";

		List list2 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list2.size() > 0) {
			R.failed("符合排班条件的轮班组所对应的参考点要在对应轮班班次的序号范围内");
		}
		//符合排班条件的轮班组所对应的轮班班次的次序必须是从1开始且是连续的
		sql = "Select 1 from atGroup a,atGroup_Turn b,atAttend_Periods h " +
				"  Where a.GroupID = b.GroupID And h.AgentMode=" + agentmode +
				" And a.AgentMode=h.AgentMode " +
				"      And ( Exists(Select 1 from atShift_Group_Sub d " +
				" Where d.TurnID = b.TurnID " +
				" Group By  d.TurnID  " +
				" Having Sum(ifnull(d.xOrder,0))<>Max(ifnull(d.xOrder,0))*(Max(ifnull(d.xOrder,0))+1)/2.0 " +
				" )   " +
				" OR   " +
				" Exists(Select 1 from atShift_Group_Sub d " +
				" Where d.TurnID = b.TurnID And d.xOrder =  0 " +
				" ) " +
				" ) " +
				"      And ( ifnull(" + groupid +
				",'') =  '' OR a.GroupID = " + groupid +
				" ) " +
				"       And (   " +
				"      Case   " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' ) " +
				"       Then ifnull(a.ReferDate,'9990-12-31')  " +
				"      Else h.BeginDate  " +
				"       End   " +
				"       <=  " +
				"       Case   " +
				"      When date_format( h.EndDate, '%Y-%m-%d' )<=date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' ) " +
				"       Then ifnull(a.DisableDate,'9990-12-31')  " +
				"       Else h.EndDate  " +
				"       End   ) " +
				"       And ( '" + term +
				"' is null  OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) +
				"' )";
		List list3 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list3.size() > 0) {
			R.failed("符合排班条件的轮班组所对应的轮班班次的次序必须是从1开始且是连续的");
		}

		//符合排班条件的轮班组所对应的日历类型不能为空
		sql = "Select 1 from atGroup a,atAttend_Periods h" +
				"  Where a.xCalendar is null And h.AgentMode=" + agentmode + " And a.AgentMode=h.AgentMode" +
				"       And ( ifnull(" + groupid + ",'') =  '' OR a.GroupID = " + groupid + " )" +
				"       And (  " +
				"       Case  " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"       <= " +
				"       Case  " +
				"       When date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )<=date_format( h.EndDate, '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End   )" +
				"       And ( '" + term + "' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )";
		List list4 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list4.size() > 0) {
			R.failed("符合排班条件的轮班组所对应的日历类型不能为空");
		}

		//符合排班条件的轮班组所对应的日历类型对应的日历没有初始化
		sql = "Select 1 from atGroup a,atAttend_Periods h" +
				"  Where ( a.GroupID = " + groupid + " OR ifnull(" + groupid + ",'')='') And h.AgentMode=" + agentmode + " And a.AgentMode=h.AgentMode" +
				"  And  " +
				"  Case  " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"       <= " +
				"       Case  " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<=date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End  " +
				"       And ( '" + term + "' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )" +
				"       And  Not Exists(Select 1 from atCalendar i" +
				"  Where a.xCalendar = i.xCategory And i.Term Between h.BeginDate and h.EndDate +1" +
				"   And i.AgentMode=" + agentmode +
				"  Group By  i.xCategory" +
				"  Having  Count(i.xCategory) = datediff(h.BeginDate,h.EndDate) +2" +
				"  )";
		List list5 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list5.size() > 0) {
			R.failed("符合排班条件的轮班组所对应的日历类型对应的日历没有初始化");
		}

		//新增加的轮班组，参考日期不能小于本考勤期间|轮班组的参考日期已改变,请改回原值
		sql = "Select 1 from atGroup a,aVW_Attend_Process f " +
				"  Where( a.GroupID = " + groupid + " OR ifnull(" + groupid + ",'')='')And f.AgentMode=" + agentmode + " And a.AgentMode=f.AgentMode " +
				"       And date_format( f.BeginDate, '%Y-%m-%d' )<=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' ) " +
				"       And date_format( f.BeginDate, '%Y-%m-%d' )>date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' ) " +
				"       And   " +
				"  Not Exists(Select 1 from aVW_Group_Ref j " +
				"    Where j.GroupID = a.GroupID And date_format( j.Term, '%Y-%m-%d' )=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' ) " +
				"     And j.AgentMode=" + agentmode + " " +
				"   ) ";
		List list6 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list6.size() > 0) {
			R.failed("新增加的轮班组，参考日期不能小于本考勤期间|轮班组的参考日期已改变,请改回原值");
		}

		//轮班定义中存在班次时段重叠
		sql = "Select 1 from atShift_Group_Sub d,atShift_Group_Sub k,atGroup_Turn b,atShift_Type m,atShift_Type n" +
				"  Where" +
				"       d.TurnID = k.TurnID And b.GroupID In(Select GroupID from atGroup Where AgentMode=" + agentmode + ")" +
				"       And d.TurnID = b.TurnID" +
				"       And d.xOrder  +  1 = k.xOrder" +
				"       And d.Shift = m.Shift" +
				"       And k.Shift = n.Shift" +
				"       And  " +
				"       Case  " +
				"       When aFN_GenDateTime(NOW(),m.Begintime) > " +
				"       aFN_GenDateTime(NOW(),n.Begintime) +  1" +
				"       Then aFN_GenDateTime(NOW(),m.Begintime) " +
				"       Else aFN_GenDateTime(NOW(),n.Begintime) +  1" +
				"       End  < " +
				"       Case  " +
				"       When aFN_GenDateTime(NOW(),m.Endtime) > " +
				"       aFN_GenDateTime(NOW(),n.Endtime) +  1" +
				"       Then aFN_GenDateTime(NOW(),n.Endtime) +  1" +
				"       Else aFN_GenDateTime(NOW(),m.Endtime) " +
				"       End ";
		List list7 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list7.size() > 0) {
			R.failed("轮班定义中存在班次时段重叠");
		}

		//班次时段定义的开始结束时间应该是连续的
		sql = "Select 1 from atShift_Section p,atShift_Type m" +
				"  Where p.Shift = m.Shift And m.AgentMode=" + agentmode +
				"       And ( p.EndTime <> m.Endtime" +
				"   And Not Exists(Select 1 from atShift_Section q" +
				"    Where p.Shift = q.Shift And p.EndTime = q.BeginTime" +
				"    )" +
				"   OR p.BeginTime <> m.Begintime" +
				"   And Not Exists(Select 1 from atShift_Section q" +
				"    Where p.Shift = q.Shift And p.BeginTime = q.EndTime" +
				"    )" +
				"  )";

		List list8 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list8.size() > 0) {
			R.failed("班次时段定义的开始结束时间应该是连续的");
		}

		//弹性休息/弹性工作的设置存在问题，请参照班次类型窗口上[需设定时段提示]的提示进行修改
		sql = "Select 1 from aVW_Shift_FlexSect R Where R.Shift is not null And " +
				"R.Shift In(Select Shift from atShift_Type where AgentMode=" + agentmode + ")";
		List list9 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list9.size() > 0) {
			R.failed("弹性休息/弹性工作的设置存在问题，请参照班次类型窗口上[需设定时段提示]的提示进行修改");
		}

		//当轮班周期为日时，间隔必须大于轮班定义中轮班班次的个数
		sql = "Select 1 from atGroup a,atGroup_Turn b,atShift_Group T,atAttend_Periods h" +
				"  Where( ifnull(" + groupid + ",'') =  '' OR a.GroupID = " + groupid + " )" +
				"   And h.AgentMode=" + agentmode + " And a.GroupID <> -1 And a.GroupID = b.GroupID" +
				"       And a.Interval <> 0 And a.TurnDayType =  '2' And b.TurnID = T.TurnID" +
				"       And a.Interval < T.Periods And a.AgentMode=h.AgentMode" +
				"       And (  " +
				"       Case  " +
				"       When date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )>=date_format( h.BeginDate, '%Y-%m-%d' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"       <= " +
				"       Case  " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<=date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End   )" +
				"       And ( '" + term + "' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )";
		List list10 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list10.size() > 0) {
			R.failed("当轮班周期为日时，间隔必须大于轮班定义中轮班班次的个数");
		}

		//轮班组轮班定义中次序必须是从1开始且是连续的
		sql = "Select 1 from atGroup a,atAttend_Periods h" +
				"  Where( ifnull(" + groupid + ",'') =  '' OR a.GroupID = " + groupid + " ) And h.AgentMode=" + agentmode + " And a.AgentMode=h.AgentMode" +
				"       And (  " +
				"       Case  " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"       <= " +
				"       Case  " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<=date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End   )" +
				"       And ( '" + term + "' is null  OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )" +
				"       And (  " +
				"  Exists(Select 1 from atGroup_Turn b" +
				"   Where a.GroupID = b.GroupID" +
				"   Group By b.GroupID,a.Periods,a.Interval" +
				"   Having Sum(ifnull(b.xOrder,0))<>Max(ifnull(b.xOrder,0))*(Max(ifnull(b.xOrder,0))+1)/2.0" +
				"   )" +
				"  OR Exists(Select 1 from atGroup_Turn b Where a.GroupID = b.GroupID And b.xOrder =  0)" +
				"  )";
		List list11 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list11.size() > 0) {
			R.failed("轮班组轮班定义中次序必须是从1开始且是连续的");
		}

		//轮班组轮班定义不能为空
		sql = "Select 1 from atGroup a,atAttend_Periods h" +
				"  Where( ifnull(" + groupid + ",'') =  '' OR a.GroupID = " + groupid + " )" +
				"  And h.AgentMode=" + agentmode + " And a.GroupID <> -1 And a.AgentMode=h.AgentMode" +
				"  And Not Exists(Select 1 from atGroup_Turn b Where a.GroupID = b.GroupID)" +
				"  And (  " +
				"       Case  " +
				"       When date_format( h.BeginDate, '%Y-%m' )>=date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"       <= " +
				"       Case  " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End   )" +
				"       And ( '" + term + "' is null  OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )";
		List list12 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list12.size() > 0) {
			R.failed("轮班组轮班定义不能为空");
		}

		//轮班组的间隔应该大于0
		sql = "Select 1 from atGroup a,atAttend_Periods h" +
				"  Where( ifnull(" + groupid + ",'') =  '' OR a.GroupID = " + groupid + " ) And h.AgentMode=" + agentmode + " And a.AgentMode=h.AgentMode" +
				"   And a.GroupID <> -1 And ( a.Periods <=  0 OR a.Interval <=  0 )" +
				"       And (  " +
				"       Case  " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"       <= " +
				"       Case  " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End   )" +
				"       And ( '" + term + "' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )";
		List list13 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list13.size() > 0) {
			R.failed("轮班组的间隔应该大于0");
		}

		//轮班组的参考点不得超过对应轮班班次的序号范围
		sql = "Select 1 from atGroup a,atGroup_Turn b,atAttend_Periods h" +
				" Where(ifnull(" + groupid + ",'') = '' OR a.GroupID = " + groupid + ") And h.AgentMode=" + agentmode + " And a.AgentMode=h.AgentMode" +
				" And a.GroupID<>-1 And a.GroupID=b.GroupID" +
				"     And Not Exists(Select 1 from atShift_Group_Sub d" +
				"   Where b.TurnID = d.TurnID And a.ReferPoint = d.xOrder" +
				"   )" +
				"     And (  " +
				"     Case  " +
				"     When date_format( h.BeginDate, '%Y-%m-%d' )>date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )" +
				"     Then ifnull(a.ReferDate,'9990-12-31') " +
				"     Else h.BeginDate " +
				"     End  " +
				"     <= " +
				"     Case  " +
				"     When date_format( h.EndDate, '%Y-%m-%d' )<date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"     Then ifnull(a.DisableDate,'9990-12-31') " +
				"     Else h.EndDate " +
				"     End   )" +
				"     And ( '" + term + "' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "' )";
		List list14 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list14.size() > 0) {
			R.failed("轮班组的参考点不得超过对应轮班班次的序号范围");
		}

		//轮班组的轮班周期为空时，轮班组轮班定义中只能定义一个轮班
		sql = "Select 1 from atGroup a,atGroup_Turn b,atAttend_Periods h" +
				"  Where a.GroupID = b.GroupID And h.AgentMode=" + agentmode + " And a.AgentMode=h.AgentMode" +
				"   And b.xOrder<>1 And a.TurnDayType is null And ( ifnull(" + groupid + ",'') =  '' OR a.GroupID = " + groupid + " )" +
				"       And (  " +
				"       Case  " +
				"       When date_format( h.BeginDate, '%Y-%m-%d' )>date_format( ifnull(a.ReferDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.ReferDate,'9990-12-31') " +
				"       Else h.BeginDate " +
				"       End  " +
				"    <= " +
				"       Case  " +
				"       When date_format( h.EndDate, '%Y-%m-%d' )<date_format( ifnull(a.DisableDate,'9990-12-31'), '%Y-%m-%d' )" +
				"       Then ifnull(a.DisableDate,'9990-12-31') " +
				"       Else h.EndDate " +
				"       End   )" +
				"       And ( '" + term + "' is null OR date_format( h.Term, '%Y-%m' )='" + term.substring(0, 7) + "')";
		List list15 = systpaystditemMapper.listSalaryTemplate(sql);
		if (list15.size() > 0) {
			R.failed("轮班组的轮班周期为空时，轮班组轮班定义中只能定义一个轮班");
		}

	}

	//轮班组排班主程序
	@Transactional(rollbackFor = Exception.class)
	public void getGroupTurnGenSub(Integer agentmode, String begindate, String enddate) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();


		sql = "SELECT * from atCD_ShiftRest;";
		List<Map> maps = systpaystditemMapper.listSalaryTemplate4(sql);
		String shift = maps.get(0).get("shift").toString();

		sql = "DROP TABLE IF EXISTS `t_GroupShift`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = " CREATE TEMPORARY TABLE t_GroupShift " +
				" ( " +
				" Shift varchar(200), " +
				" GroupID int, " +
				" TurnID int, " +
				" Term date, " +
				" xType int," +
				" ID int," +
				" xPoint int, " +
				" ReferPoint int, " +
				" corpid int, " +
				" agentmode int " +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert into t_GroupShift(Shift,GroupID,TurnID,Term,xType,ID,xPoint,corpid,agentmode) " +
				"  Select '" + shift + "' Shift,a.GroupID GroupID,c.TurnID TurnID,b.Term Term,b.xType xType," +
				"   datediff(b.Term," +
				"     Case " +
				"      When c.BeginDate<'" + begindate + "' " +
				"      Then '" + begindate + "' " +
				"      Else c.BeginDate " +
				"     End) ID," +
				"   Case " +
				"    When a.ReferDate = b.Term" +
				"    Then a.ReferPoint " +
				"    Else c.ReferPoint" +
				"   End xPoint " + "," + corpid + "," + agentmode +
				"  From atGroup a, atCalendar b  ,t_aGroup_Ref c " +
				"  Where b.Term between '" + begindate + "' and DATE_ADD('" + enddate + "',INTERVAL 1 DAY) And a.AgentMode=" + agentmode + " And b.AgentMode=" + agentmode +
				"   and b.term between CAST(a.ReferDate as CHAR) and IFNULL(a.DisableDate,'9990-12-31')" +
				"   and b.Term between c.BeginDate and c.EndDate " +
				"   and a.GroupID=c.GroupID " +
				"   And a.xCalendar=b.xCategory  " +
				"   and Exists(select 1 from t_aGroup d where a.GroupID=d.GroupID)" +
				"  Order by a.GroupID,b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//剪断排班ID置为NULL ,排班为休息
		sql = "Update t_GroupShift a, atGroup b ,atCD_ShiftModeByDayType c " +
				"  set a.ID=NULL " +
				"  Where  " + "a.AgentMode=" + agentmode +
				" and a.GroupID=b.GroupID And b.AgentMode=" + agentmode +
				"   and a.xType=c.DayType " +
				"   and b.ShiftMode=c.ShiftMode" +
				"   and c.Mode=3;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_GroupShift2`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = " CREATE TEMPORARY TABLE t_GroupShift2 " +
				" ( " +
				" Shift varchar(200), " +
				" GroupID int, " +
				" TurnID int, " +
				" Term date, " +
				" xType int," +
				" ID int," +
				" xPoint int, " +
				" ReferPoint int, " +
				" corpid int, " +
				" agentmode int " +
				" )";
		systpaystditemMapper.listSalaryTemplate3(sql);


		sql = "insert into t_GroupShift2( Shift,GroupID,TurnID,Term,xType,ID,xPoint,corpid,agentmode)  SELECT Shift,GroupID,TurnID,Term,xType,ID,xPoint,corpid,agentmode FROM t_GroupShift";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//剪断的序号前移
		sql = "Update t_GroupShift a  ,t_aGroup_Ref b " +
				"   Set a.ID=a.ID-" +
				"    (Select Count(1)  From t_GroupShift2 c   " +
				"      Where " + " c.agentmode=" + agentmode +
				"      and  c.ID is Null   " +
				"      And a.GroupID=c.GroupID  " +
				"     and a.TurnID=c.TurnID" +
				"     and a.Term >c.Term " +
				"     and c.Term between b.BeginDate and b.EndDate      " +
				"     ) " +
				"   Where " + "a.agentmode=" + agentmode +
				" 	and a.ID is Not Null  " +
				"   and a.GroupID=b.GroupID" +
				"   and a.TurnID=b.TurnID" +
				"   and a.Term between b.BeginDate and b.EndDate;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//复制表更新 t_GroupShift2
		sql = "Update t_GroupShift2 a  ,t_aGroup_Ref b " +
				"   Set a.ID=a.ID-" +
				"    (Select Count(1)  From t_GroupShift c   " +
				"      Where " + " c.agentmode=" + agentmode +
				"	   and c.ID is Null   " +
				"      And a.GroupID=c.GroupID  " +
				"     and a.TurnID=c.TurnID" +
				"     and a.Term >c.Term " +
				"     and c.Term between b.BeginDate and b.EndDate      " +
				"     ) " +
				"   Where " + "a.agentmode=" + agentmode +
				"   and a.ID is Not Null  " +
				"   and a.GroupID=b.GroupID" +
				"   and a.TurnID=b.TurnID" +
				"   and a.Term between b.BeginDate and b.EndDate;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//以最大的+1为准,如果全月为剪断的话，那么ID=0 ，即原来的ReferPoint(这个地方需要仔细考虑)
		sql = "Update t_GroupShift a ,t_aGroup_Ref b " +
				"  Set a.ID =IFNULL((Select Max(ID) from t_GroupShift2 c " +
				"      where" + " c.agentmode=" + agentmode +
				"		and  a.GroupID=c.GroupID " +
				"       and a.TurnID=c.TurnID" +
				"       and c.Term between b.BeginDate and b.EndDate " +
				"     )+1,0) " +
				"  Where " + "a.agentmode=" + agentmode +
				"	and a.ID is null " +
				"   and a.GroupID=b.GroupID" +
				"   and a.TurnID=b.TurnID" +
				"   and a.Term   between b.BeginDate and b.enddate  " +
				"   and a.Term =DATE_ADD('" + enddate + "',INTERVAL 1 DAY);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//复制表更新t_GroupShift2
		sql = "Update t_GroupShift2 a ,t_aGroup_Ref b " +
				"  Set a.ID =IFNULL((Select Max(ID) from t_GroupShift c " +
				"      where " + " c.agentmode=" + agentmode +
				"		and a.GroupID=c.GroupID " +
				"       and a.TurnID=c.TurnID" +
				"       and c.Term between b.BeginDate and b.EndDate " +
				"     )+1,0) " +
				"  Where " + "a.agentmode=" + agentmode +
				"   and a.ID is null " +
				"   and a.GroupID=b.GroupID" +
				"   and a.TurnID=b.TurnID" +
				"   and a.Term   between b.BeginDate and b.enddate  " +
				"   and a.Term =DATE_ADD('" + enddate + "',INTERVAL 1 DAY);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 更新轮班对应的次序及班次
		// 正常排班 1111
		sql = "Update t_GroupShift a,atShift_Group c,atShift_Group_Sub d" +
				"  set a.Shift=d.Shift," +
				"    a.ReferPoint= " +
				"     Case When (a.ID+a.xPoint)%c.Periods=0 " +
				"        Then c.Periods" +
				"        else  (a.ID+a.xPoint)%c.Periods" +
				"     End " +
				"  Where   " + " a.agentmode=" + agentmode +
				"	and a.TurnID=c.TurnID" +
				"   and a.TurnID=d.TurnID" +
				"   and " +
				"    Case When (a.ID+a.xPoint)%c.Periods=0 " +
				"       Then c.Periods" +
				"       else  (a.ID+a.xPoint)%c.Periods" +
				"    End =d.xOrder ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//转休息的置为休息
		sql = "Update t_GroupShift a,atGroup b,atCD_ShiftModeByDayType c " +
				"  set a.Shift='" + shift +
				"'  where " + " a.agentmode=" + agentmode +
				"	and a.GroupID=b.GroupID " +
				"   and b.ShiftMode=c.ShiftMode and a.xType=c.DayType and c.Mode=2;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//剪断排班的为休息
		sql = "Update t_GroupShift  a set a.Shift='" + shift +
				"' where " + " a.agentmode=" + agentmode +
				" and a.ID is NULL;";
		systpaystditemMapper.listSalaryTemplate3(sql);


	}
	@Transactional(rollbackFor = Exception.class)
	public void getPlanTWIDInitSimple(String from, String to, Integer agentmode) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		sql = "DROP TABLE IF EXISTS `t_aPlan_TimeWage`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE t_aPlan_TimeWage" +
				"    (" +
				"   Term  datetime," +
				"   EID  int," +
				"   TWID  smallint," +
				"   xType  tinyint," +
				"   SeqID  int," +
				"   BeginTime datetime," +
				"   EndTime  datetime," +
				"   corpid  int," +
				"   agentmode  int" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aPlan_TimeWage On t_aPlan_TimeWage (Term,EID,TWID,xType,SeqID,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Select WorkTWID,WorkATWID,WorkBTWID,WorkRTWID,WBHours,WAHours,RBHours,RAHours" +
				" from atCD_TimeWageInitPara;";
		Map map = systpaystditemMapper.listSalaryTemplate4(sql).get(0);
		Integer worktwid = Integer.parseInt(map.get("WorkTWID").toString());
		Integer workatwid = Integer.parseInt(map.get("WorkATWID").toString());
		Integer workbtwid = Integer.parseInt(map.get("WorkBTWID").toString());
		Integer workrtwid = Integer.parseInt(map.get("WorkRTWID").toString());
		String wbhours = map.get("WBHours").toString();
		String wahours = map.get("WAHours").toString();
		String rbhours = map.get("RBHours").toString();
		String rahours = map.get("RAHours").toString();

		//生成休息的时段(上班前)
		sql = "insert t_aPlan_TimeWage(Term,EID,Begintime,EndTime,TWID,corpid,agentmode)" +
				"  select a.term,a.EID,Date_Add(a.term,INTERVAL -" + wbhours + " HOUR),a.BeginTime," + workbtwid + "," + corpid + "," + agentmode +
				"  from atPlan_Range a " +
				"  where " + " a.corpid = " + corpid +
				"   and a.Shift not in (select Shift from atCD_ShiftRest)" +
				"   and a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins b  where a.EID=b.EID and a.Term between b.BeginDate and b.EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成休息的时段(下班后)
		sql = "insert t_aPlan_TimeWage(Term,EID,Begintime,EndTime,TWID,corpid,agentmode)" +
				"  select a.term,a.EID,a.EndTime,Date_Add(a.term, INTERVAL " + wahours + " HOUR)," + workatwid + "," + corpid + "," + agentmode +
				"  from atPlan_Range a " +
				"  where  " + " a.corpid = " + corpid +
				"   and a.Shift not in (select Shift from atCD_ShiftRest)" +
				"   and a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins b where a.EID=b.EID and a.Term between b.BeginDate and b.EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成休息的时段(当天的班次为休息)
		sql = "insert t_aPlan_TimeWage(Term,EID,Begintime,EndTime,TWID,corpid,agentmode) " +
				"  select a.Term,a.EID,Date_Add(a.term, INTERVAL -" + rbhours + " HOUR),Date_Add(a.term, INTERVAL " + rahours + " HOUR)," + workrtwid + "," + corpid + "," + agentmode +
				"  from atPlan_Range a" +
				"  where  " + " a.corpid = " + corpid +
				"   and a.Shift in (select Shift from atCD_ShiftRest)" +
				"   and a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins b where a.EID=b.EID and a.Term between b.BeginDate and b.EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		// 生成工作的时段
		// 如果在班次时段中定义了数据，不允许从扫描范围表中选择工作时段插入班次时段临时表#aPlan_TimeWage（1100）
		// 否则的话，就向班次时段临时表#aPlan_TimeWage中插入工作的时段(1100)
		sql = "insert t_aPlan_TimeWage(Term,EID,Begintime,EndTime,TWID,corpid,agentmode)" +
				"  select a.term,a.EID,a.BeginTime,a.EndTime," + worktwid + "," + corpid + "," + agentmode +
				"  from atPlan_Range a" +
				"  where  " + " a.corpid = " + corpid +
				"  and a.Shift not in (select Shift from atCD_ShiftRest)" +
				"   and a.Term between '" + from + "' and '" + to + "' " +
				"   and exists(select 1 from t_aStatus_ins b " +
				"     where a.EID=b.EID and a.Term between b.BeginDate and b.EndDate)" +
				"   and not exists(select 1 from  atShift_Section d where d.Shift=a.Shift);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除在考勤登记处理中班次时段相同的记录
		sql = "delete a from t_aPlan_TimeWage a,atPlan_Reg b" +
				" where " + " a.corpid = " + corpid +
				" and a.EID  =  b.EID" +
				"  and a.Term  = b.Term" +
				"  and exists(select 1 from t_aStatus_ins c " +
				"   where a.EID=c.EID and a.Term between c.BeginDate and c.EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//选择考勤登记处理中班次时段相同的记录
		sql = "insert t_aPlan_TimeWage(Term,EID,Begintime,EndTime,TWID,corpid,agentmode)" +
				"  select a.Term,a.EID,a.Begintime,a.EndTime,a.TWID" + "," + corpid + "," + agentmode +
				"  from atPlan_TimeWage a  " +
				"  Where " + " a.corpid = " + corpid +
				"  and a.Term Between '" + from + "' and '" + to + "'" +
				"    and Exists(select 1 from t_aStatus_ins c Where a.EID=c.EID and a.Term between c.BeginDate and c.EndDate)" +
				"  and Exists(select 1 from atPlan_Reg b where a.EID=b.EID And a.Term =b.Term);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "delete a  from atPlan_TimeWage a" +
				"  Where " + " a.corpid = " + corpid +
				" and Term Between '" + from + "' and '" + to + "' " +
				"  and exists(select 1 from  t_aStatus_ins b  Where a.EID=b.EID and a.Term between b.BeginDate and b.EndDate);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//每天每人的班次时段
		sql = "insert atPlan_TimeWage(Term,xType,SeqID,EID,TWID,BeginTime,EndTime,corpid,agentmode)" +
				"  Select Term,xType,SeqID,EID,TWID,BeginTime,EndTime,corpid,agentmode" +
				"  from t_aPlan_TimeWage;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Drop Table t_aPlan_TimeWage;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}

	//对于纵向表更新横向表(集体)
	@Transactional(rollbackFor = Exception.class)
	public void getAttendMonthSumTranSub() {
		String sql = "";

		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();

		sql = "DROP TABLE IF EXISTS `t_aShift_Total`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aShift_Total" +
				"  (" +
				"   Term Datetime," +
				"   EID Int," +
				"   corpid Int," +
				"   DNAN Decimal(18,2)," +
				"   CDER Decimal(18,2)," +
				"   PNHR Decimal(18,2)," +
				"   PNDY Decimal(18,2)," +
				"   MHourlyST Decimal(18,2)," +
				"   MHourlyH Decimal(18,2)," +
				"   MHourlyM Decimal(18,2)," +
				"   MHourlyC Decimal(18,2)," +
				"   WKHR Decimal(18,2)," +
				"   WKDY Decimal(18,2)," +
				"   LIMN Decimal(18,2)," +
				"   EOMN Decimal(18,2)," +
				"   LITM Decimal(18,2)," +
				"   EOTM Decimal(18,2)," +
				"   ABHR Decimal(18,2)," +
				"   ABDY Decimal(18,2)," +
				"   UAHR Decimal(18,2)," +
				"   OTAB Decimal(18,2)," +
				"   OT1 Decimal(18,2)," +
				"   OT2 Decimal(18,2)," +
				"   OT3 Decimal(18,2)," +
				"   CLOT Decimal(18,2)," +
				"   SicL Decimal(18,2)," +
				"   PerL Decimal(18,2)," +
				"   AnnL Decimal(18,2)," +
				"   ComL Decimal(18,2)," +
				"   MarL Decimal(18,2)," +
				"   PrPL Decimal(18,2)," +
				"   MatL Decimal(18,2)," +
				"   PatL Decimal(18,2)," +
				"   OTCL Decimal(18,2)," +
				"   PuHL Decimal(18,2)," +
				"   NurL Decimal(18,2)," +
				"   ISJL Decimal(18,2)," +
				"   FANF Decimal(18,2)," +
				"   OfAL Decimal(18,2)," +
				"   LVWK Decimal(18,2)," +
				"   REWK Decimal(18,2)," +
				"   OTCD Decimal(18,2)," +
				"   NUDY Decimal(18,2)" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Insert Into t_aShift_Total(Term,EID,corpid,DNAN,CDER,PNHR,PNDY,WKHR,WKDY,LIMN,EOMN,LITM,EOTM,ABHR,ABDY,UAHR,OTAB,OT1,OT2,OT3,CLOT,SicL,PerL,AnnL,ComL,MarL " +
				"    ,PrPL,MatL,PatL,OTCL,PuHL,NurL,ISJL,FANF,OfAL,LVWK,REWK,OTCD,NUDY) " +
				"    Select Term,EID, " + corpid +
				"       ,Sum(Case When  TWID =3105 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3209 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3001 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3002 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3003 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3004 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3205 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3207 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3206 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3208 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3201 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3202 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3203 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3210 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =1301 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =1302 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =1303 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =1402 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2102 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2105 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2119 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2104 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2107 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2109 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2113 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2114 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2120 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2122 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2124 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2101 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2127 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2121 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3211 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =3213 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2131 Then Amount Else NULL End ) " +
				"      ,Sum(Case When  TWID =2132 Then Amount Else NULL End ) " +
				"      FROM t_aAttend_Total GROUP BY Term,EID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "UPDATE atShift_Total a,t_aShift_Total b SET " +
				"   a.DNAN=b.DNAN,a.CDER=b.CDER,a.PNHR=b.PNHR,a.PNDY=b.PNDY,a.MHourlyST=b.MHourlyST,a.MHourlyH=b.MHourlyH,a.MHourlyM=b.MHourlyM," +
				"   a.MHourlyC=b.MHourlyC,a.WKHR=b.WKHR,a.WKDY=b.WKDY,a.LIMN=b.LIMN,a.EOMN=b.EOMN,a.LITM=b.LITM,a.EOTM=b.EOTM,a.ABHR=b.ABHR," +
				"   a.ABDY=b.ABDY,a.UAHR=b.UAHR,a.OTAB=b.OTAB,a.OT1=b.OT1,a.OT2=b.OT2,a.OT3=b.OT3,a.CLOT=b.CLOT,a.SicL=b.SicL,a.PerL=b.PerL," +
				"   a.AnnL=b.AnnL,a.ComL=b.ComL,a.MarL=b.MarL,a.PrPL=b.PrPL,a.MatL=b.MatL,a.PatL=b.PatL,a.OTCL=b.OTCL,a.PuHL=b.PuHL,a.NurL=b.NurL," +
				"   a.ISJL=b.ISJL,a.FANF=b.FANF,a.OfAL=b.OfAL,a.LVWK=b.LVWK,a.REWK=b.REWK,a.OTCD=b.OTCD,a.NUDY=b.NUDY" +
				"   WHERE " + " a.corpid = " + corpid +
				"	and  a.Term=b.Term AND a.EID=b.EID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}
	@Transactional(rollbackFor = Exception.class)
	public R getAttendMonthInitf(String term, Integer agentmode, String xenddate) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		//2020-07-08
		String nyterm = "";
		String yterm = "";
		if (!StringUtils.isEmpty(term)) {
			nyterm = term.substring(0, 7);
			yterm = term.substring(0, 4);
		} else {
			R.failed("考勤月份不能为空");
		}
		if (StringUtils.isEmpty(agentmode)) {
			R.failed("考勤账套不能为空");
		} else {
		}

	/*	QueryWrapper<AtattendPeriods> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpid", corpid);
		queryWrapper.eq("ifnull(closed,0)", 0);
		queryWrapper.eq("ifnull(initialized,0)", 1);
		queryWrapper.inSql("agentmode", "Select max(AID) as aid from atCD_AgentMode_User Where UserID=" + userid);
		AtattendPeriods atattendPeriods = atattendPeriodsService.getOne(queryWrapper);
		if (StringUtils.isEmpty(term) && !StringUtils.isEmpty(atattendPeriods)) {
			term = atattendPeriods.getTerm();
		}*/

		//请先封帐当月考勤，再初始化参数月份的考勤
		QueryWrapper<AtattendPeriods> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpid", corpid);
		queryWrapper2.ne("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper2.eq("ifnull(closed,0)", 0);
		queryWrapper2.eq("ifnull(initialized,0)", 1);
		queryWrapper2.eq("ifnull(iscurrentmonth,0)", 1);
		AtattendPeriods atattendPeriods2 = atattendPeriodsService.getOne(queryWrapper2);
		if (!StringUtils.isEmpty(atattendPeriods2)) {
			R.failed("请先封帐当月考勤，再初始化参数月份的考勤");
		}
		//已经封帐的考勤月份不能大于等于当前封帐的考勤月份

		QueryWrapper<AtattendPeriodsAll> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("corpid", corpid);
		queryWrapper3.ge("date_format( term, '%Y-%m' )", nyterm);
		queryWrapper3.eq("agentmode", agentmode);
		List<AtattendPeriodsAll> list1 = atattendPeriodsAllService.list(queryWrapper3);
		if (list1.size() > 0) {
			R.failed("已经封帐的考勤月份不能大于等于当前封帐的考勤月份");
		}

		QueryWrapper<AtattendPeriods> queryWrapper4 = new QueryWrapper<>();
		queryWrapper4.eq("corpid", corpid);
		queryWrapper4.eq("date_format( term, '%Y' )", yterm);
		queryWrapper4.eq("agentmode", agentmode);
		List list2 = atattendPeriodsService.list(queryWrapper4);
		//当月第一天
		String firstDay = DateUtils.getFirstDayOfGivenMonth(term);
		//当月最后一天
		String lastDay = DateUtils.getLastDayOfMonth(term);
		String begindate = "";
		String enddate = "";
		//当年的第一天
		String sql = "";

		//对应参数月份没有考勤月份数据的，生成当前的数据
		if (list2.size() < 1) {
			getAttendPeriodsEasy(term, agentmode);

		}
		//初始化一整年的数据
		String xterm2 = DateUtils.getNextYear(term, 1);
		//12个月以后不存在数据，就初始化当年日历
		QueryWrapper<AtattendPeriods> queryWrapper6 = new QueryWrapper<>();
		queryWrapper6.eq("agentmode", agentmode);
		queryWrapper6.eq("date_format( term, '%Y' )", xterm2.substring(0, 4));
		List<AtattendPeriods> list = atattendPeriodsService.list(queryWrapper6);
		if (list.size() < 1) {
			getAttendPeriodsEasy(xterm2, agentmode);
		}

		//循环生成参数月份之后11个月的轮班组、员工排班，具体生成哪个轮班组有aSP_GroupTurnGenEasy的条件决定
		QueryWrapper<AtattendPeriods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("agentmode", agentmode);
		queryWrapper1.eq("Initialized", 0).or().eq("Initialized", null);
		queryWrapper1.ge("PERIOD_DIFF(date_format(Term,'%Y%m'),date_format('" + xterm2 + "','%Y%m'))", 0);
		queryWrapper1.le("PERIOD_DIFF(date_format(Term,'%Y%m'),date_format('" + xterm2 + "','%Y%m'))", 11);
		List<AtattendPeriods> list3 = atattendPeriodsService.list(queryWrapper1);
		AtattendPeriods atattendPeriods1 = null;
		for (int jj = 0; jj < list3.size(); jj++) {
			atattendPeriods1 = list3.get(jj);
			String mterm = atattendPeriods1.getTerm();
			String begindate2 = atattendPeriods1.getBegindate();
			String enddate2 = atattendPeriods1.getEnddate();
			generateGroupTurnGenEasy(null, begindate2, enddate2, agentmode);
			Map map = new HashMap();
			map.put("term", mterm);
			getShiftTurnRunEasy(map);
			//对下月期间的结束日期之前的年度考勤期间锁定
			sql = "Update atAttend_Periods  " +
					" Set Initialized=1 ,InitializedTime=now()" +
					" Where date_format( Term, '%Y-%m' )='" + mterm.substring(0, 7) +
					"' and ifnull(Initialized,0)=0 And AgentMode=" + agentmode;
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

		return R.ok("初始化成功！");

	}
	//正在考勤分析，不允许执行其它的操作
	@Transactional(rollbackFor = Exception.class)
	public R getAttendLockStatusCheck(Integer agentmode) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();

		QueryWrapper<AtattendLockstatus> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("isLock", 1);
		queryWrapper1.eq("angetmode", agentmode);
		List<AtattendLockstatus> atattendLockstatuses = atattendLockstatusService.list();

		if ((!atattendLockstatuses.isEmpty()) && atattendLockstatuses.size() > 0) {
			return R.failed("考勤分析中,为避免数据不一致，请稍后再做处理！");
		}

		return R.ok();

	}


	//手动排班检查
	@Transactional(rollbackFor = Exception.class)
	public R getShiftHandleRunCheckSub(String term, Integer eid) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();

		QueryWrapper<AtshiftWork> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("eid", eid);
		queryWrapper1.eq("term", term);
		List<AtshiftWork> atshiftWorks = atshiftWorkService.list(queryWrapper1);
		if (atshiftWorks.isEmpty() || atshiftWorks.size() < 1) {
			return R.failed("该员工在本排班期间没有相应的排班  ");
		}

		//班次时段定义的开始结束时间应该是连续的
		sql = "Select 1 from atShift_Section b,atShift_Type c  " +
				"  Where b.Shift = c.Shift  " +
				"   And ( b.EndTime <> c.Endtime  " +
				"    And Not Exists(Select 1 from atShift_Section f  " +
				"       Where b.Shift = f.Shift And b.EndTime = f.BeginTime  " +
				"      )" +
				"    OR b.BeginTime <> c.Begintime  " +
				"    And Not Exists(Select 1 from atShift_Section f  " +
				"       Where b.Shift = f.Shift And b.BeginTime = f.EndTime" +
				"      ))";
		List<Map> maps1 = systpaystditemMapper.listSalaryTemplate4(sql);
		if (maps1.isEmpty() || maps1.size() <= 0) {
			return R.failed("班次时段定义的开始结束时间应该是连续的 ");
		}

		//弹性休息/弹性工作的设置存在问题，请参照班次类型窗口上[需设定时段提示]的提示进行修改
		sql = "Select 1 from aVW_Shift_FlexSect d Where d.Shift is not null";
		List<Map> maps2 = systpaystditemMapper.listSalaryTemplate4(sql);
		if (maps2.isEmpty() || maps2.size() <= 0) {
			return R.failed("弹性休息/弹性工作的设置存在问题，请参照班次类型窗口上[需设定时段提示]的提示进行修改");
		}

		//此员工的班次没有进行任何变动
		sql = "Select 1 from atShiftSupply_Work a,atShift_Work b" +
				"   Where a.EID = " + eid + " And a.EID=b.EID And date_format( a.Term, '%Y-%m' )='" + term.substring(0, 7) + "'" +
				" And date_format( a.Term, '%Y-%m' )=date_format( b.Term, '%Y-%m' ) " +
				"   And IFNULL(a.S1,'')=IFNULL(b.S1,'') And IFNULL(a.S2,'')=IFNULL(b.S2,'') And IFNULL(a.S3,'')=IFNULL(b.S3,'') " +
				"   And IFNULL(a.S4,'')=IFNULL(b.S4,'') And IFNULL(a.S5,'')=IFNULL(b.S5,'') And IFNULL(a.S6,'')=IFNULL(b.S6,'') " +
				"   And IFNULL(a.S7,'')=IFNULL(b.S7,'') And IFNULL(a.S8,'')=IFNULL(b.S8,'') And IFNULL(a.S9,'')=IFNULL(b.S9,'') " +
				"   And IFNULL(a.S10,'')=IFNULL(b.S10,'') And IFNULL(a.S11,'')=IFNULL(b.S11,'') And IFNULL(a.S12,'')=IFNULL(b.S12,'') " +
				"   And IFNULL(a.S13,'')=IFNULL(b.S13,'') And IFNULL(a.S14,'')=IFNULL(b.S14,'') And IFNULL(a.S15,'')=IFNULL(b.S15,'') " +
				"   And IFNULL(a.S16,'')=IFNULL(b.S16,'') And IFNULL(a.S17,'')=IFNULL(b.S17,'') And IFNULL(a.S18,'')=IFNULL(b.S18,'') " +
				"   And IFNULL(a.S19,'')=IFNULL(b.S19,'') And IFNULL(a.S20,'')=IFNULL(b.S20,'') And IFNULL(a.S21,'')=IFNULL(b.S21,'') " +
				"   And IFNULL(a.S22,'')=IFNULL(b.S22,'') And IFNULL(a.S23,'')=IFNULL(b.S23,'') And IFNULL(a.S24,'')=IFNULL(b.S24,'') " +
				"   And IFNULL(a.S25,'')=IFNULL(b.S25,'') And IFNULL(a.S26,'')=IFNULL(b.S26,'') And IFNULL(a.S27,'')=IFNULL(b.S27,'') " +
				"   And IFNULL(a.S28,'')=IFNULL(b.S28,'') And IFNULL(a.S29,'')=IFNULL(b.S29,'') And IFNULL(a.S30,'')=IFNULL(b.S30,'') " +
				"   And IFNULL(a.S31,'')=IFNULL(b.S31,'') And IFNULL(a.S32,'')=IFNULL(b.S32,'') And IFNULL(a.S33,'')=IFNULL(b.S33,'') " +
				"   And IFNULL(a.S34,'')=IFNULL(b.S34,'') And IFNULL(a.S35,'')=IFNULL(b.S35,'') And IFNULL(a.S36,'')=IFNULL(b.S36,'') " +
				"   And IFNULL(a.S37,'')=IFNULL(b.S37,'') And IFNULL(a.S38,'')=IFNULL(b.S38,'') And IFNULL(a.S39,'')=IFNULL(b.S39,'') " +
				"   And IFNULL(a.S40,'')=IFNULL(b.S40,'') And IFNULL(a.S41,'')=IFNULL(b.S41,'') And IFNULL(a.S42,'')=IFNULL(b.S42,'') " +
				"   And IFNULL(a.S43,'')=IFNULL(b.S43,'') And IFNULL(a.S44,'')=IFNULL(b.S44,'') And IFNULL(a.S45,'')=IFNULL(b.S45,'') " +
				"   And IFNULL(a.S46,'')=IFNULL(b.S46,'') And IFNULL(a.S47,'')=IFNULL(b.S47,'') And IFNULL(a.S48,'')=IFNULL(b.S48,'') " +
				"   And IFNULL(a.S49,'')=IFNULL(b.S49,'') And IFNULL(a.S50,'')=IFNULL(b.S50,'')";
		List<Map> maps3 = systpaystditemMapper.listSalaryTemplate4(sql);
		if (maps3.isEmpty() || maps3.size() <= 0) {
			return R.failed("此员工的班次没有进行任何变动");
		}

		//排班在入职日期之前||排班在离职日期之后||考勤状态非0/1||考勤状态为0/1期间对应N/A轮班组
		sql = "Select 1 from atShiftSupply_Work e,atShift_Work  a  " +
				"    Where date_format( e.Term, '%Y-%m' )='" + term.substring(0, 7) + "' And date_format( a.Term, '%Y-%m' )=date_format( e.Term, '%Y-%m' )" +
				"     And e.EID =" + eid +
				"     And e.EID = a.EID  " +
				"     And ( e.S1 is not null   " +
				"     And a.S1 is null   " +
				"     OR e.S2 is not null   " +
				"     And a.S2 is null   " +
				"     OR e.S3 is not null   " +
				"     And a.S3 is null   " +
				"     OR e.S4 is not null   " +
				"     And a.S4 is null   " +
				"     OR e.S5 is not null   " +
				"     And a.S5 is null   " +
				"     OR e.S6 is not null   " +
				"     And a.S6 is null   " +
				"     OR e.S7 is not null   " +
				"     And a.S7 is null   " +
				"     OR e.S8 is not null   " +
				"     And a.S8 is null   " +
				"     OR e.S9 is not null   " +
				"     And a.S9 is null   " +
				"     OR e.S10 is not null   " +
				"     And a.S10 is null   " +
				"     OR e.S11 is not null   " +
				"     And a.S11 is null   " +
				"     OR e.S12 is not null   " +
				"     And a.S12 is null   " +
				"     OR e.S13 is not null   " +
				"     And a.S13 is null   " +
				"     OR e.S14 is not null   " +
				"     And a.S14 is null   " +
				"     OR e.S15 is not null   " +
				"     And a.S15 is null   " +
				"     OR e.S16 is not null   " +
				"     And a.S16 is null   " +
				"     OR e.S17 is not null   " +
				"     And a.S17 is null   " +
				"     OR e.S18 is not null   " +
				"     And a.S18 is null   " +
				"     OR e.S19 is not null   " +
				"     And a.S19 is null   " +
				"     OR e.S20 is not null   " +
				"     And a.S20 is null   " +
				"     OR e.S21 is not null   " +
				"     And a.S21 is null   " +
				"     OR e.S22 is not null   " +
				"     And a.S22 is null   " +
				"     OR e.S23 is not null   " +
				"     And a.S23 is null   " +
				"     OR e.S24 is not null   " +
				"     And a.S24 is null   " +
				"     OR e.S25 is not null   " +
				"     And a.S25 is null   " +
				"     OR e.S26 is not null   " +
				"     And a.S26 is null   " +
				"     OR e.S27 is not null   " +
				"     And a.S27 is null   " +
				"     OR e.S28 is not null   " +
				"     And a.S28 is null   " +
				"     OR e.S29 is not null   " +
				"     And a.S29 is null   " +
				"     OR e.S30 is not null   " +
				"     And a.S30 is null   " +
				"     OR e.S31 is not null   " +
				"     And a.S31 is null   " +
				"     OR e.S32 is not null   " +
				"     And a.S32 is null   " +
				"     OR e.S33 is not null   " +
				"     And a.S33 is null   " +
				"     OR e.S34 is not null   " +
				"     And a.S34 is null   " +
				"     OR e.S35 is not null   " +
				"     And a.S35 is null   " +
				"     OR e.S36 is not null   " +
				"     And a.S36 is null   " +
				"     OR e.S37 is not null   " +
				"     And a.S37 is null   " +
				"     OR e.S38 is not null   " +
				"     And a.S38 is null   " +
				"     OR e.S39 is not null   " +
				"     And a.S39 is null   " +
				"     OR e.S40 is not null   " +
				"     And a.S40 is null   " +
				"     OR e.S41 is not null   " +
				"     And a.S41 is null   " +
				"     OR e.S42 is not null   " +
				"     And a.S42 is null   " +
				"     OR e.S43 is not null   " +
				"     And a.S43 is null   " +
				"     OR e.S44 is not null   " +
				"     And a.S44 is null   " +
				"     OR e.S45 is not null   " +
				"     And a.S45 is null   " +
				"     OR e.S46 is not null   " +
				"     And a.S46 is null   " +
				"     OR e.S47 is not null   " +
				"     And a.S47 is null   " +
				"     OR e.S48 is not null   " +
				"     And a.S48 is null   " +
				"     OR e.S49 is not null   " +
				"     And a.S49 is null   " +
				"     OR e.S50 is not null   " +
				"     And a.S50 is null)";
		List<Map> maps4 = systpaystditemMapper.listSalaryTemplate4(sql);
		if (maps4.isEmpty() || maps4.size() <= 0) {
			return R.failed("此员工的班次没有进行任何变动");
		}
		return R.ok();

	}
	@Transactional(rollbackFor = Exception.class)
	public R getStaffchecksubEasy(Integer id,Integer agentMode){
		if(StringUtils.isEmpty(id)){
			return R.failed("登记信息不存在！请核实！");
		}
		if(StringUtils.isEmpty(agentMode)){
			return R.failed("账套信息不存在！请核实！");
		}
		QueryWrapper<AtstaffRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		AtstaffRegister atstaffRegister1 = atstaffRegisterService.getOne(queryWrapper);
		if(StringUtils.isEmpty(atstaffRegister1)){
			return R.failed("账套信息不存在！请核实！");
		}
		Integer lagentMode = atstaffRegister1.getAid();
		Integer lAgentUserID = atstaffRegister1.getAgentuserid();
		Integer aTStatus = atstaffRegister1.getAtstatus();
		Integer groupid = atstaffRegister1.getGroupid();
		Integer eid = atstaffRegister1.getEid();
		if(StringUtils.isEmpty(lagentMode)){
			return R.failed("考勤账套不能为空");
		}
		if(StringUtils.isEmpty(lAgentUserID)){
			return R.failed("考勤管理员不能为空");
		}
		if(StringUtils.isEmpty(aTStatus)){
			return R.failed("新员工的考勤状态不能为空  ");
		}
		if(StringUtils.isEmpty(groupid)){
			return R.failed("新员工的轮班组不能为空  ");
		}

		String sql = "Select 1 from atStaff_Register a,atCD_AgentMode_User b " +
				" Where IFNULL(a.AgentUserID,0)=b.UserID And IFNULL(a.AID,0)=b.AID and a.ID="+id;
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		if(StringUtils.isEmpty(map)){
			return R.failed("勤管理员与考勤账套不对应");
		}

		sql = "Select 1 from atStaff_Register a,aVw_CardValidateByStaff f Where a.EID = f.EID and a.ID="+id ;
		Map map2 = systpaystditemMapper.listSalaryTemplate2(sql);
		if(!StringUtils.isEmpty(map2)){
			return R.failed("新员工的卡号不能重复且不能为空");
		}



		QueryWrapper<AtattendPeriods> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("ifnull(Initialized,0)",1);
		queryWrapper2.eq("IFNULL(Closed,0)",0);
		queryWrapper2.eq("AgentMode",agentMode);
		List<AtattendPeriods> atattendPeriodsList = atattendPeriodsService.list(queryWrapper2);
		if( atattendPeriodsList.size()<1){
			return R.failed("请先做考勤初始化！");
		}

		QueryWrapper<AtstaffAll> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("eid",eid);
		List<AtstaffAll> atstaffAllList = atstaffAllService.list(queryWrapper3);
		if( atstaffAllList.size()>=1){
			return R.failed("新员工已经被处理过了！");
		}

		QueryWrapper<Atgroup> queryWrapper4 = new QueryWrapper<>();
		queryWrapper4.eq("GroupID",groupid);
		queryWrapper4.ne("GroupID",-1);
		queryWrapper4.isNotNull("DisableDate");
		List<Atgroup> atgroupList = atgroupService.list(queryWrapper4);
		if( atgroupList.size()>=1){
			return R.failed("新员工的轮班组不能是已经失效的轮班组！");
		}

	/*	QueryWrapper<AtgroupShift> queryWrapper5 = new QueryWrapper<>();
		queryWrapper5.eq("GroupID",groupid);
		queryWrapper5.ne("GroupID",-1);
		queryWrapper5.isNotNull("DisableDate");
		List<AtgroupShift> atgroupShiftList = atgroupShiftService.list(queryWrapper5);
		if( atgroupShiftList.size()<1){
			return R.failed("请配置轮班组的班次！");
		}*/


		return R.ok("成功！");
	}
	@Transactional(rollbackFor = Exception.class)
	public void getUserconfigsubmitcheck(Integer divType,String term,Integer agentMode){
		String sql;

		sql="DROP  TABLE IF EXISTS `t_aGroup_Ref`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="CREATE TEMPORARY TABLE `t_aGroup_Ref`" +
				"  (" +
				"   GroupID INT," +
				"   TurnID INT," +
				"   ShiftMode INT" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//对班贴和扣减关联的锁定
		if(divType==1){
			//自动排班月份扣减关联的锁定
			sql="Update atBreak a" +
					"   set a.Initialized =1" +
					"   where date_format(a.Term, '%Y-%m')='" +term.substring(0,7)+"'"+
					"	and IFNULL(a.Initialized,0)=0 " +
					"    And a.BreakID In (Select ID from atBreak_Type Where AgentMode="+agentMode+");";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//自动排班月份班贴关联的锁定
			sql="Update atAllow a" +
					"   set a.Initialized =1" +
					"   where date_format(a.Term, '%Y-%m')='" +term.substring(0,7)+"'"+
					"  and IFNULL(a.Initialized,0)=0" +
					"    And a.AllowID In(Select ID from atAllow_Type Where AgentMode="+agentMode+");";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//扣减定义的引用
			sql="Update atBreak_Type a set a.Initialized =1" +
					"   where exists( select 1 from atBreak b where a.ID =b.BreakID and b.Initialized=1)  " +
					"    and IFNULL(a.Initialized,0)=0 And a.AgentMode="+agentMode+";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//班贴定义的引用
			sql="Update atAllow_Type a  set a.Initialized =1" +
					"   where exists( select 1 from atAllow b where a.ID =b.AllowID and b.Initialized =1)  " +
					"    and IFNULL(a.Initialized,0)=0 And a.AgentMode="+agentMode+";";
			systpaystditemMapper.listSalaryTemplate3(sql);

		}else if (divType==2){  //表示对考勤状态为2的默认轮班组、班次的锁定
			//被引用的轮班和排班策略放在临时表中
			sql="insert into t_aGroup_Ref(GroupID,TurnID,ShiftMode)" +
					"   select  Distinct a.GroupID,b.TurnID,c.ShiftMode" +
					"   from atGroup_Ref a,atGroup_Turn b,atGroup c" +
					"   where  a.GroupID=b.GroupID and a.GroupID=c.GroupID" +
					"    and IFNULL(a.Submit,0) = 1 And c.AgentMode="+agentMode+";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//轮班的引用
			sql="Update atGroup_Turn a  set a.Initialized =1" +
					"   where exists(select 1 from t_aGroup_Ref b where a.GroupID =b.GroupID)" +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//轮班的引用
			sql="Update atShift_Group a  set a.Initialized =1" +
					"   where exists(select 1 from t_aGroup_Ref b where a.TurnID =b.TurnID)" +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//轮班班次的引用
			sql="Update atShift_Group_Sub a set a.Initialized =1" +
					"   where exists( select 1 from atShift_Group b where a.TurnID=b.TurnID and IFNULL(b.Initialized,0)=1)" +
					"    and IFNULL(a.Initialized,0)=0" +
					"    And exists(select 1 from t_aGroup_Ref c where a.TurnID =c.TurnID);";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//排班策略定义的引用
			sql="Update atCD_ShiftMode a set a.Initialized =1" +
					"   where exists(select 1 from t_aGroup_Ref b where a.ID = b.ShiftMode)" +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//排班策略(日期类型)的引用
			sql="Update atCD_ShiftModeByDayType  a  set a.Initialized =1" +
					"   where exists( select 1 from atCD_ShiftMode b where a.ShiftMode = b.ID and IFNULL(b.Initialized,0)=1)" +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//班次类型的引用
			sql="Update atShift_Type a  set a.Initialized =1" +
					"   where exists(Select 1 from atPlan_Range b ,aVW_Status c" +
					"      where a.Shift=b.Shift And b.EID=c.EID And c.AID=" +agentMode+
					"      )  " +
					"    and IFNULL(a.Initialized,0)=0 And a.AgentMode="+agentMode+";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//班次时段的引用
			sql="UPDATE atShift_Section a  set a.Initialized =1" +
					"   where exists(select 1 from atShift_Type b where a.Shift=b.Shift and IFNULL(b.Initialized,0)=1 And a.AgentMode="+agentMode+")  " +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//弹性休息的引用
			sql="UPDATE atShift_FlexSect a set a.Initialized =1 " +
					"   where exists( select 1 from atShift_Type b where a.Shift=b.Shift and IFNULL(b.Initialized,0)=1 And b.AgentMode="+agentMode+")  " +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//弹性工作的引用
			sql="Update atShift_FlexWorkSect a  set a.Initialized =1" +
					"   where exists( select 1 from atShift_Type b where a.Shift=b.Shift and IFNULL(b.Initialized,0)=1 And b.AgentMode="+agentMode+")  " +
					"    and IFNULL(a.Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);

		}else if(divType==3){ //对登记时段的封存
			sql="update atShift_RegSect a" +
					"   Set a.Initialized=1  " +
					"   where exists(Select 1 from atShift_Type b where a.Shift=b.Shift and IFNULL(b.Initialized,0)=1 And b.AgentMode="+agentMode+")  " +
					"    And IFNULL(Initialized,0)=0;";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}


	}
	@Transactional(rollbackFor = Exception.class)
	public void getEvent(Integer agentmode,Integer corpid,Integer sid,Integer id,Integer eid,Integer newValue,String beginDate,String endDate,String message){
		String sql;

		sql="SELECT Badge FROM etEmployee WHERE EID = "+eid+";";
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		String badge=map.get("Badge").toString();

		sql="SELECT 1 FROM atEvent WHERE EID= " + eid+
				"  AND SID="+sid+"";
		List<Map> maps = systpaystditemMapper.listSalaryTemplate4(sql);
		if(maps.size()>0){
			sql="CREATE TEMPORARY TABLE t_aSection_Reg" +
					"    (" +
					"    EID INT," +
					"    SID INT," +
					"    BeginDate DateTime," +
					"    EndDate  DateTime," +
					"    NewValue  varchar(50) ," +
					"    Remark  varchar(100) ," +
					"    AgentMode int," +
					"    corpid int" +
					"    );";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql="Create Index IX_aSection_Reg On t_aSection_Reg(EID,SID,BeginDate,NewValue);";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql="CREATE TEMPORARY TABLE t_aSection" +
					"    ( " +
					"    EID  int," +
					"    SID  int," +
					"    BeginDate DateTime," +
					"    EndDate  DateTime," +
					"    NewValue  varchar(50) ," +
					"    Remark  varchar(100) ," +
					"    AgentMode int," +
					"    corpid int" +
					"    );";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql="Create Index IX_aSection On t_aSection (EID,SID,BeginDate,NewValue);";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//生成变动的部分
			sql="INSERT INTO t_aSection_Reg(EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
					" values" +
					" ("+eid+","+sid+",Convert('"+beginDate+"',datetime),convert('"+endDate+"',datetime),'"+newValue+"','"+message+"',"+agentmode+","+corpid+")";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//生成现在存在
			sql="INSERT INTO t_aSection(EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
					"   SELECT EID,SID,BeginDate,EndDate,NewValue,Remark,"+agentmode+","+corpid +
					"   FROM atEvent WHERE EID=" +eid+
					" AND SID="+sid+";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			//生成最终的结果
			getSectionGenSub();

			sql="DELETE FROM atEvent Where EID="+eid+" and SID="+sid+";";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql="INSERT INTO atEvent(EID,Badge,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
					" Select EID,'"+badge+"',SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid" +
					"    FROM t_aSection;";
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql="DROP TABLE t_aSection_Reg;";
			systpaystditemMapper.listSalaryTemplate3(sql);
			sql="DROP TABLE t_aSection;";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}else{
			sql="insert atEvent(EID,Badge,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
					"     Select "+eid+",'"+badge+"',"+sid+",'"+beginDate+"','"+endDate+"',"+newValue+",'"+message+"',"+agentmode+","+corpid+";";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}

	}
	@Transactional(rollbackFor = Exception.class)
	public void getSectionGenSub(){
		String sql;

		sql="DROP TABLE IF EXISTS `t_aSection_Result`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="CREATE TEMPORARY TABLE t_aSection_Result" +
				"  (" +
				"   EID    INT," +
				"   SID    INT," +
				"   BeginDate   datetime," +
				"   EndDate     datetime," +
				"   NewValue    nvarchar(50)," +
				"   Remark      nvarchar(100) ," +
				"   AgentMode   int," +
				"   corpid      int" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Create Index t_IX_aSection_Result On t_aSection_Result (EID,SID,BeginDate,EndDate,NewValue);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//区间的前一部分
		sql="INSERT INTO t_aSection_Result(EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
				"  SELECT a.EID,a.SID,c.BeginDate, DATE_ADD(a.BeginDate,INTERVAL -1 DAY),c.NewValue,c.Remark,a.agentmode,a.corpid" +
				"  FROM t_aSection_Reg a,  t_aSection c " +
				"  WHERE  a.EID=c.EID AND CASE WHEN a.BeginDate>=c.BeginDate THEN a.BeginDate ELSE c.BeginDate END <=    " +
				"      CASE WHEN a.EndDate>=c.EndDate THEN c.EndDate ELSE a.EndDate END " +
				"  AND c.BeginDate <= DATE_ADD(a.BeginDate,INTERVAL -1 DAY) ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//区间的的后一部分
		sql="INSERT INTO t_aSection_Result(EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
				"  SELECT a.EID,a.SID,DATE_ADD(a.EndDate,INTERVAL 1 DAY),c.EndDate,c.NewValue,c.Remark,a.agentmode,a.corpid" +
				"  FROM t_aSection_Reg a, t_aSection c " +
				"  WHERE  a.EID=c.EID AND CASE WHEN a.BeginDate>=c.BeginDate  THEN a.BeginDate ELSE c.BeginDate END <=   " +
				"                   CASE WHEN a.EndDate>=c.EndDate THEN c.EndDate ELSE a.EndDate END " +
				"  AND DATE_ADD(a.EndDate,INTERVAL 1 DAY) <= c.EndDate ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//将相交的区间删除(包含单相交及完全包含)
		sql="DELETE  a FROM t_aSection a,t_aSection_Reg b" +
				"  WHERE a.EID=b.EID and a.SID=b.SID AND " +
				"      case when a.BeginDate > b.BeginDate then a.BeginDate else b.BeginDate end " +
				"    <=case when a.EndDate > b.EndDate then b.EndDate else a.EndDate end;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//将相交的区间的左右部分放入正式中
		sql="INSERT INTO t_aSection (EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
				"  SELECT EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid FROM t_aSection_Result ;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//Reg(变动部分)放入正式中
		sql="INSERT INTO t_aSection (EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid)" +
				"  SELECT EID,SID,BeginDate,EndDate,NewValue,Remark,agentmode,corpid FROM t_aSection_Reg;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="DROP  TABLE t_aSection_Result;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}

	@Transactional(rollbackFor = Exception.class)
	public R getAnalysisCheckSubEasy(Integer depId, String isDetailsDepID, String badge, String beginDate, String endDate, Integer agentmode) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();

		//参数对应的开始日期不能大于结束日期
		if (beginDate.compareTo(endDate) > 0) {
			return R.failed("参数对应的开始日期不能大于结束日期");
		}

		Integer adminId = depId;

		//以下脚本实现sysfn_GetSubDep功能，MYSQL自定义函数无法返回表
		sql = "DROP TABLE IF EXISTS `Resu`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE Resu SELECT DepID FROM otDepartment WHERE AdminID=" + adminId + " OR DepID=" + depId + ";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="SELECT DepID from Resu";
		List<Map> maps = systpaystditemMapper.listSalaryTemplate4(sql);
		StringBuffer depIds=new StringBuffer();
		for(Map map:maps){
			depIds.append(map.get("DepID")+",");
		}
		String depIds1=depIds.length()==0?null:depIds.toString().substring(0,depIds.length());
		for (int i = 20; i > 0; i--) {
			sql = "INSERT INTO Resu SELECT DepID FROM otDepartment WHERE AdminID in("+depIds1+") and DepID not in ("+depIds1+");";
			systpaystditemMapper.listSalaryTemplate3(sql);
		}
		//and date_format( c.Term, '%Y-%m-%d' )>='"+beginDate.substring(0,10)+"'"
//DATE_FORMAT(c.Term,'%Y-%m-%d')
		//参数对应的日期内不存在需要进行考勤分析的员工
		sql = "SELECT 1 FROM aVW_StatusEasy d,atPlan_Range c " +
				"   WHERE( " + depId + " =  '' OR d.DepID =" + depId +
				"        AND '" + isDetailsDepID + "' =  'N' OR '" + isDetailsDepID + "' =  'Y'" +
				"        AND EXISTS(SELECT 1 FROM Resu f WHERE DepID=" + depId + " AND d.DepID = f.DepID)" +
				"      )" +
				//        "    AND c.Term BETWEEN "+beginDate+" AND "+endDate+"" +
				"  and date_format( c.Term, '%Y-%m-%d' )>='" + beginDate.substring(0, 10) + "'" +
				"  and date_format( c.Term, '%Y-%m-%d' )<='" + endDate.substring(0, 10) + "'" +
				"    AND ( " + badge + " =  '' OR d.Badge = " + badge + " )" +
				"    AND c.EID = d.EID" +
				"    AND c.AnalyMode <=  5" +
				"    AND IFNULL(c.Initialized,0) =  0 ";
		List<Map> maps1 = systpaystditemMapper.listSalaryTemplate4(sql);

		sql = "SELECT 1 FROM aVW_Card_Record e,aVW_Status d" +
				"   Where" +
				"    date_format( DATE_ADD('" + beginDate + "', INTERVAL -1 DAY), '%Y-%m-%d' )<=date_format( e.xDateTime, '%Y-%m-%d' )" +
				"    And date_format( DATE_ADD(" + endDate + ",INTERVAL 1 DAY), '%Y-%m-%d' )>=date_format( e.xDateTime, '%Y-%m-%d' )" +
				"    And ( " + badge + " =  '' OR d.Badge = " + badge + " )" +
				"    And ( " + depId + " =  ''" +
				"      OR d.DepID =" + depId +
				"      And '" + isDetailsDepID + "' =  'N'" +
				"      OR '" + isDetailsDepID + "' =  'Y'" +
				"      AND EXISTS(SELECT 1 FROM Resu f WHERE DepID=" + depId + " AND d.DepID = f.DepID)" +
				"    )" +
				"    And e.EID = d.EID And IFNULL(e.initialized,0) =  0 And e.isDisable =  0";
		List<Map> maps2 = systpaystditemMapper.listSalaryTemplate4(sql);

		if ((maps1.isEmpty() || maps1.size() <= 0) && (maps2.isEmpty() || maps2.size() <= 0)) {
			return R.failed("参数对应的日期内不存在需要进行考勤分析的员工");
		}
		return R.ok();

	}

	//刷卡数据分析，产生刷卡序列
	@Transactional(rollbackFor = Exception.class)
	public R getCardAnalMainEasy(String begindate, String enddate) {
		String sql = "";
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		String currentTime = DateUtils.getTimeString();

		sql="drop table if EXISTS t_aRecord";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Create TEMPORARY TABLE t_aRecord" +
				"  (" +
				"   ID int PRIMARY KEY auto_increment," +
				"   Term Datetime," +
				"   MacID varchar(4)," +
				"   CardID varchar(20)," +
				"   EID int," +
				"   xDateTime Datetime," +
				"   InOutFlag varchar(4)," +
				"   SeqID int," +
				"   corpid int" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aRecord_EID on t_aRecord (EID,xDateTime,InOutFlag);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aRecord_CardID  on t_aRecord (CardID,xDateTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//所要读取的刷卡数据的结束时间不能为空
		if (StringUtils.isEmpty(enddate) || "".equalsIgnoreCase(enddate)) {
			return R.failed("所要读取的刷卡数据的结束时间不能为空");
		}

		//所要读取的刷卡数据的开始时间比其结束时间大，既没有需要读取的数据
		if (begindate.compareTo(enddate) > 0) {
			return R.failed("没有需要读取的数据");
		}

		//定位打卡取数据
		sql = "INSERT INTO t_aRecord(MacID,CardID,EID,InOutFlag,xDateTime,corpid)" +
				"  SELECT 1,b.EID,b.EID,Null,CONCAT(Cast(a.AttendanceDate as CHAR),' '," +
				"Left(Cast(AttendanceTime as CHAR),8))" + "," + corpid +
				"  FROM oatAttendanceDetail a,sys_user b," +
				"   (select EID,Min(Term) As BeginDate,Max(Term) As " +
				"EndDate From t_aAnal_Status Group By EID) c" +
				"  Where " + " a.corpid= " + corpid + " and b.corpid=" + corpid +
				" and a.UserID=b.user_id And b.EID=c.EID And " +
				" date_format( a.AttendanceDate, '%Y-%m-%d' )>=date_format( c.BeginDate, '%Y-%m-%d' ) And " +
				" date_format( a.AttendanceDate, '%Y-%m-%d' )<=date_format( c.EndDate, '%Y-%m-%d' );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//补卡数据
		sql = "INSERT INTO t_aRecord(MacID,CardID,EID,InOutFlag,xDateTime,SeqID,corpid)" +
				"  SELECT 1,a.EID,a.EID,NULL,a.xDateTime,a.ID" + "," + corpid +
				"  FROM atCardLost_Register a,(select EID,Min(Term) As BeginDate,Max(Term) " +
				" As EndDate FROM t_aAnal_Status where corpid=" + corpid +
				"  GROUP BY EID) b" +
				"  WHERE a.corpid = " + corpid +
				"	and a.EID=b.EID And date_format( a.xDateTime, '%Y-%m-%d' )>=date_format( b.BeginDate, '%Y-%m-%d' ) And " +
				"date_format( a.xDateTime, '%Y-%m-%d' )<=date_format( b.EndDate, '%Y-%m-%d' );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Delete FROM t_aRecord Where xDateTime Is Null Or EID Is Null;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//一天一次上下班班次，生成空记录
		sql = "insert into t_aAnal_CardIn(Term,EID,Type,IsSection,Shift,BeginTime,Endtime,BTScanBegintime,BTScanEndtime,ETScanBegintime,ETScanEndtime,oddFlag,SourceType,corpid)" +
				"  select a.term,a.EID,b.Type,b.IsSection,a.Shift,aFn_GenDateTime(a.Term,b.Begintime),aFn_GenDateTime(a.Term,b.Endtime)," +
				"   aFn_GenDateTime(a.Term,b.BTScanBegintime),aFn_GenDateTime(a.Term,b.BTScanEndtime)," +
				"   aFn_GenDateTime(a.Term,b.ETScanBegintime),aFn_GenDateTime(a.Term,b.ETScanEndtime),0,1" + "," + corpid +
				"  from t_aAnal_Status a,atShift_Type b" +
				"  Where " + " a.corpid=" + corpid +
				"	and a.Shift=b.Shift And b.Type=1" +
				"  Order By a.EID,a.term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//没有设置打卡时间段的，给默认值
		sql = "UPDATE t_aAnal_CardIn a" +
				"  SET   a.BTScanBegintime=CONVERT(a.BeginTime,CHAR)," +
				//第一次扫描开始时间取班次开始时间的0点
				"    a.BTScanEndtime=DATE_ADD(a.BeginTime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.Endtime, '%Y-%m-%d %H:%i' ))/2 MINUTE)," +
				//第一次扫描结束取平均值
				"    a.ETScanBegintime=" +
				"    DATE_ADD(DATE_ADD(a.BeginTime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.Endtime, '%Y-%m-%d %H:%i' ))/2 MINUTE),INTERVAL 1 MINUTE) ,   " +
				//第二次扫描开始取平均值+1分钟
				"    a.ETScanEndtime=CONCAT(CONVERT(a.Endtime,CHAR),' 23:59')  " +
				//第二次扫描结束取23：59
				"  WHERE " + "a.corpid = " + corpid +
				"	and a.Type=1 And IFNULL(a.IsSection,0)=0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//一天两次上下班班次，生成空记录
		sql = "INSERT INTO t_aAnal_CardIn(Term,EID,Type,IsSection,Shift,BeginTime,Endtime,BeginTime2,Endtime2,BTScanBegintime,BTScanEndtime,ETScanBegintime,ETScanEndtime," +
				"       BTScanBegintime2,BTScanEndtime2,ETScanBegintime2,ETScanEndtime2,oddFlag,SourceType,corpid)" +
				"  SELECT a.term,a.EID,b.Type,b.IsSection,a.Shift,aFn_GenDateTime(a.Term,b.BeginTime),aFn_GenDateTime(a.Term,b.Endtime)," +
				"   aFn_GenDateTime(a.Term,b.BeginTime2),aFn_GenDateTime(a.Term,b.Endtime2)," +
				"   aFn_GenDateTime(a.Term,b.BTScanBegintime),aFn_GenDateTime(a.Term,b.BTScanEndtime)," +
				"   aFn_GenDateTime(a.Term,b.ETScanBegintime),aFn_GenDateTime(a.Term,b.ETScanEndtime)," +
				"   aFn_GenDateTime(a.Term,b.BTScanBegintime2),aFn_GenDateTime(a.Term,b.BTScanEndtime2)," +
				"   aFn_GenDateTime(a.Term,b.ETScanBegintime2),aFn_GenDateTime(a.Term,b.ETScanEndtime2),0,1" + "," + corpid +
				"  FROM t_aAnal_Status a,atShift_Type b" +
				"  WHERE " + "a.corpid = " + corpid +
				"	and a.Shift=b.Shift AND b.Type=2" +
				"  Order By a.EID,a.term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//没有设置打卡时间段的，给默认值
		sql = "UPDATE t_aAnal_CardIn a " +
				"  SET   a.BTScanBegintime=CONVERT(a.BeginTime,CHAR)," +
				// 第一次扫描开始时间取班次开始时间的0点
				"    a.BTScanEndtime=DATE_ADD(a.BeginTime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.Endtime, '%Y-%m-%d %H:%i'))/2 MINUTE), " +
				// 第一次扫描结束取平均值
				"    a.ETScanBegintime=DATE_ADD(DATE_ADD(a.BeginTime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.Endtime, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE), " +
				// 第二次扫描开始取平均值+1分钟
				"    a.ETScanEndtime=DATE_ADD(a.Endtime, INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.Endtime, '%Y-%m-%d %H:%i'),date_format(a.BeginTime2, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				// 第二次扫描结束取平均值
				"    a.BTScanBegintime2=DATE_ADD(DATE_ADD(a.Endtime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.Endtime, '%Y-%m-%d %H:%i'),date_format(a.BeginTime2, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				"      a.BTScanEndtime2=DATE_ADD(a.BeginTime2,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime2, '%Y-%m-%d %H:%i'),date_format(a.Endtime2, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				"      a.ETScanBegintime2=DATE_ADD(DATE_ADD(a.BeginTime2,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime2, '%Y-%m-%d %H:%i'),date_format(a.Endtime2, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				"      a.ETScanEndtime2=CONCAT(CONVERT(a.Endtime2,CHAR),' 23:59')" +
				"  WHERE " + " a.corpid = " + corpid +
				" 	and a.Type=2 And IFNULL(a.IsSection,0)=0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//一天三次上下班班次，生成空记录
		sql = "insert into t_aAnal_CardIn(Term,EID,Type,IsSection,Shift,BeginTime,Endtime,BeginTime2,Endtime2,BeginTime3,Endtime3,BTScanBegintime,BTScanEndtime,ETScanBegintime,ETScanEndtime," +
				"       BTScanBegintime2,BTScanEndtime2,ETScanBegintime2,ETScanEndtime2," +
				"       BTScanBegintime3,BTScanEndtime3,ETScanBegintime3,ETScanEndtime3,oddFlag,SourceType,corpid)" +
				"  select a.term,a.EID,b.Type,b.IsSection,a.Shift,aFn_GenDateTime(a.Term,b.BeginTime),aFn_GenDateTime(a.Term,b.Endtime)," +
				"   aFn_GenDateTime(a.Term,b.BeginTime2),aFn_GenDateTime(a.Term,b.Endtime2)," +
				"   aFn_GenDateTime(a.Term,b.BeginTime3),aFn_GenDateTime(a.Term,b.Endtime3)," +
				"   aFn_GenDateTime(a.Term,b.BTScanBegintime),aFn_GenDateTime(a.Term,b.BTScanEndtime)," +
				"   aFn_GenDateTime(a.Term,b.ETScanBegintime),aFn_GenDateTime(a.Term,b.ETScanEndtime)," +
				"   aFn_GenDateTime(a.Term,b.BTScanBegintime2),aFn_GenDateTime(a.Term,b.BTScanEndtime2)," +
				"   aFn_GenDateTime(a.Term,b.ETScanBegintime2),aFn_GenDateTime(a.Term,b.ETScanEndtime2)," +
				"   aFn_GenDateTime(a.Term,b.BTScanBegintime3),aFn_GenDateTime(a.Term,b.BTScanEndtime3)," +
				"   aFn_GenDateTime(a.Term,b.ETScanBegintime3),aFn_GenDateTime(a.Term,b.ETScanEndtime3),0,1" + "," + corpid +
				"  from t_aAnal_Status a,atShift_Type b" +
				"  Where " + " a.corpid = " + corpid +
				"	and a.Shift=b.Shift And b.Type=3" +
				"  Order By a.EID,a.term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//没有设置打卡时间段的，给默认值
		sql = "Update t_aAnal_CardIn a " +
				"  set   a.BTScanBegintime=CONVERT(a.BeginTime,CHAR)," +
				// 第一次扫描开始时间取班次开始时间的0点
				"    a.BTScanEndtime=DATE_ADD(a.BeginTime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.Endtime, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				// 第一次扫描结束取平均值
				"    a.ETScanBegintime=DATE_ADD(DATE_ADD(a.BeginTime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime, '%Y-%m-%d %H:%i'),date_format(a.Endtime, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				// 第二次扫描开始取平均值+1分钟
				"    a.ETScanEndtime=DATE_ADD(a.Endtime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.Endtime, '%Y-%m-%d %H:%i'),date_format(a.BeginTime2, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				// 第二次扫描结束取平均值
				"    a.BTScanBegintime2=DATE_ADD(DATE_ADD(a.Endtime,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.Endtime, '%Y-%m-%d %H:%i'),date_format(a.BeginTime2, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				"    a.BTScanEndtime2=DATE_ADD(a.BeginTime2,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime2, '%Y-%m-%d %H:%i'),date_format(a.Endtime2, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				"    a.ETScanBegintime2=DATE_ADD(DATE_ADD(a.BeginTime2,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime2, '%Y-%m-%d %H:%i'),date_format(a.Endtime2, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				"    a.ETScanEndtime2=DATE_ADD(a.Endtime2,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.Endtime2, '%Y-%m-%d %H:%i'),date_format(a.BeginTime3, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				"    a.BTScanBegintime3=DATE_ADD(DATE_ADD(a.Endtime2,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.Endtime2, '%Y-%m-%d %H:%i'),date_format(a.BeginTime3, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				"    a.BTScanEndtime3=DATE_ADD(a.BeginTime3,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime3, '%Y-%m-%d %H:%i'),date_format(a.Endtime3, '%Y-%m-%d %H:%i'))/2 MINUTE)," +
				"    a.ETScanBegintime3=DATE_ADD(DATE_ADD(a.BeginTime3,INTERVAL TIMESTAMPDIFF(MINUTE,date_format(a.BeginTime3, '%Y-%m-%d %H:%i'),date_format(a.Endtime3, '%Y-%m-%d %H:%i'))/2 MINUTE),INTERVAL 1 MINUTE)," +
				"    a.ETScanEndtime3=CONCAT(Convert(a.Endtime3,CHAR),' 23:59')" +
				"  Where " + " a.corpid = " + corpid +
				"	and a.Type=3 And IFNULL(a.IsSection,0)=0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aRecord1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql="create TEMPORARY table t_aRecord1 as select * from t_aRecord";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE IF EXISTS `t_aRecord2`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql="create TEMPORARY table t_aRecord2 as select * from t_aRecord";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE IF EXISTS `t_aRecord3`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql="create TEMPORARY table t_aRecord3 as select * from t_aRecord";
		systpaystditemMapper.listSalaryTemplate3(sql);
		// 生成休息日的打卡记录
		// 满足开始扫描时段，认为是进卡
		sql = "Update t_aAnal_CardIn a " +
				"  set a.CardBeginTime=(Select Min(xDateTime) From t_aRecord " +
				"         Where " + " corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.BTScanBegintime, '%Y-%m-%d %H:%i' )" +
				"         )," +
				"     a.CardEndTime=(Select Max(xDateTime) From t_aRecord1 " +
				"         Where " + "  corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.ETScanEndtime, '%Y-%m-%d %H:%i' )" +
				"         ) where a.corpid = " + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aAnal_CardIn a set a.CardBeginTime2=(Select Min(xDateTime) From t_aRecord " +
				"         Where " + " corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.BTScanBegintime2, '%Y-%m-%d %H:%i')  And " +
				" date_format( xDateTime, '%Y-%m-%d %H:%i' )<=date_format( a.BTScanEndtime2, '%Y-%m-%d %H:%i' )" +
				"         )," +
				"     a.CardEndTime2=(Select Max(xDateTime) From t_aRecord1 " +
				"         Where " + " corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )<=date_format( a.ETScanEndtime2, '%Y-%m-%d %H:%i' )" +
				"         ) Where " + " a.corpid = " + corpid +
				"	and a.Type=2;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aAnal_CardIn a " +
				"  set a.CardBeginTime2=(Select Min(xDateTime) From t_aRecord " +
				"         Where " + " corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.BTScanEndtime2, '%Y-%m-%d %H:%i' )" +
				"         )," +
				"     a.CardEndTime2=(Select Max(xDateTime) From t_aRecord1 " +
				"         Where corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.ETScanBegintime2, '%Y-%m-%d %H:%i' )" +
				" And date_format( xDateTime, '%Y-%m-%d %H:%i' )<=date_format( a.ETScanEndtime2, '%Y-%m-%d %H:%i' )" +
				"         )," +
				"     a.CardBeginTime3=(Select Min(xDateTime) From t_aRecord2 " +
				"         Where corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.BTScanBegintime3, '%Y-%m-%d %H:%i' )" +
				" And date_format( xDateTime, '%Y-%m-%d %H:%i' )<=date_format( a.BTScanEndtime3, '%Y-%m-%d %H:%i' )" +
				"         )," +
				"     a.CardEndTime3=(Select Max(xDateTime) From t_aRecord3 " +
				"         Where corpid = " + corpid +
				"	and a.EID=EID And date_format( xDateTime, '%Y-%m-%d %H:%i' )>=date_format( a.ETScanBegintime3, '%Y-%m-%d %H:%i' )" +
				" And date_format( xDateTime, '%Y-%m-%d %H:%i' )<=date_format( a.ETScanEndtime3, '%Y-%m-%d %H:%i' )" +
				"         ) Where a.corpid=" + corpid +
				"	and a.Type=3;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DELETE a" +
				"  FROM atCard_In a, t_aAnal_CardIn b " +
				"  Where a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成atCard_In表数据
		sql = "Insert Into atCard_In(Term,EID,Shift,BeginTime,EndTime,BeginTime2,EndTime2,BeginTime3,EndTime3,oddFlag,corpid)" +
				"  Select Term,EID,Shift,CardBeginTime,CardEndTime,CardBeginTime2,CardEndTime2,CardBeginTime3,CardEndTime3,oddFlag" + "," + corpid +
				"  From t_aAnal_CardIn;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Drop table t_aRecord;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Drop table t_aRecord1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Drop table t_aRecord2;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		return R.ok();

	}

	@Transactional(rollbackFor = Exception.class)
	public R getAnalMainEasy(String beginDate, String endDate, Integer agentMode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String sql;
		// 显示界面中需要显示刷卡数据，异常，请假，考勤结果
		sql = "DROP TABLE IF EXISTS `t_aAnal_PlanTimeWage`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_PlanTimeWage" +
				"  (" +
				"   term         datetime ," +
				"   Shift        varchar (10)  ,     " +
				"   EID          int,    " +
				"   TWID         smallint , " +
				"   WorkNum      Int,   " +
				"   BeginTime    datetime ,    " +
				"   EndTime      datetime ,  " +
				"   Amount       decimal(10,2) ," +
				"   xOrder     smallint , " +
				"   xType        tinyint,    " +
				"   SeqID        int  ," +
				"   corpid     int    " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aAnal_PlanTimeWage On t_aAnal_PlanTimeWage (Term,EID,TWID,BeginTime,xType,SeqID); ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aAnal_PlanBreak`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_PlanBreak" +
				"  (    " +
				"   term         datetime ,  " +
				"   Shift        varchar (10)  ,    " +
				"   EID          int,    " +
				"   TWID         smallint ,    " +
				"   BeginTime    datetime ,    " +
				"   EndTime      datetime ," +
				"   Rate         Decimal(10,2)," +
				"   corpid     int    " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aAnal_PlanBreak On t_aAnal_PlanBreak (Term,EID,TWID,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aAnal_Range`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE IF EXISTS `t_aAnal_Range1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_Range" +
				"  (    " +
				"   Term           datetime ,    " +
				"   EID            int ,    " +
				"   GroupID        varchar(10),    " +
				"   DayType        tinyint,    " +
				"   Shift          varchar(10),  " +
				"   WorkNum        Int,  " +
				"   BeginTime      datetime ,    " +
				"   EndTime        datetime ,   " +
				"   BeginTime2     datetime ,    " +
				"   EndTime2       datetime ,  " +
				"   BeginTime3     datetime ,    " +
				"   EndTime3       datetime ," +
				"   Amount         decimal(10,2) ," +
				"   ScanBeginTime  datetime ,    " +
				"   ScanEndTime    datetime, " +
				"   corpid     int    " +
				"  ); ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_Range1" +
				"  (    " +
				"   Term           datetime ,    " +
				"   EID            int ,    " +
				"   GroupID        varchar(10),    " +
				"   DayType        tinyint,    " +
				"   Shift          varchar(10),  " +
				"   WorkNum        Int,  " +
				"   BeginTime      datetime ,    " +
				"   EndTime        datetime ,   " +
				"   BeginTime2     datetime ,    " +
				"   EndTime2       datetime ,  " +
				"   BeginTime3     datetime ,    " +
				"   EndTime3       datetime ," +
				"   Amount         decimal(10,2) ," +
				"   ScanBeginTime  datetime ,    " +
				"   ScanEndTime    datetime," +
				"   corpid     int    " +
				"  ); ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aAnal_Range on t_aAnal_Range(Term,EID,GroupID,Shift,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Create Index IX_aAnal_Range on t_aAnal_Range1(Term,EID,GroupID,Shift,BeginTime);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aAnal_Details`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_Details" +
				"  (    " +
				"   id             INT primary key AUTO_INCREMENT," +
				"   Term           datetime,    " +
				"   Term2          datetime,    " +
				"   EID            int,  " +
				"   Shift          varchar(10),    " +
				"   PlanTWID       SMALLINT,    " +
				"   PlanBeginTime  datetime ,    " +
				"   PlanEndTime    datetime ,    " +
				"   FactBeginTime  datetime ,    " +
				"   FactEndTime    datetime ,    " +
				"   BeginTime      datetime ,    " +
				"   EndTime        datetime ,    " +
				"   FactTWID       smallint ,    " +
				"   IsException    bit,    " +
				"   TotalMins      smallint,    " +
				"   NetMins        smallint,    " +
				"   Num            decimal(10, 2),    " +
				"   Amount         decimal(10, 2),    " +
				"   Unit           varchar (2),    " +
				"   xType          tinyint,    " +
				"   Seqid          int,    " +
				"   DecAmount      decimal(10, 2)," +
				"   NeedChange     Bit," +
				"   Remark         varchar(50), " +
				"   corpid     int    " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aAnal_Details On t_aAnal_Details (Term,EID,PlanBeginTime,FactBeginTime,FactTWID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aPlan_Reg`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aPlan_Reg" +
				"  ( " +
				"   Term        datetime," +
				"   EID        int," +
				"   Shift         varchar(10) ," +
				"   TWID        smallint," +
				"   RegTWID     smallint," +
				"   CalcType       smallint," +            // 1-按自然日计算，2-按工作日计算
				"   BeginTime    datetime," +
				"   EndTime     datetime," +
				"   Amount         Decimal(18,2)," +
				"   SeqID        int," +
				"   xType        tinyint," +             // 1表示按时间登记2表示按时段登记
				"   SheetID     tinyint," +             // 1表示请假，2-表示加班
				"   corpid     int    " +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aPlan_Reg On t_aPlan_Reg (Term,EID,TWID,RegTWID,BeginTime,SeqID,xType);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		/*sql="Select Term from atAttend_Periods Where AgentMode='"+agentMode+"';";
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		String term=map.get("Term").toString();*/

		// 将相关的刷卡数据，班次时段，考勤登记等数据转入分析
		// 取扫描范围
		sql = "insert into t_aAnal_Range(Term,EID,GroupID,DayType,Shift,WorkNum,BeginTime,EndTime,BeginTime2,EndTime2,BeginTime3,EndTime3,ScanBeginTime,ScanEndTime,corpid)" +
				"  select a.Term,a.EID,a.GroupID,DayType,a.Shift,a.WorkNum,a.BeginTime,a.EndTime,a.BeginTime2,a.EndTime2,a.BeginTime3,a.EndTime3,a.ScanBeginTime,a.ScanEndTime," + corpid +
				"  from atPlan_Range a ,t_aAnal_Status b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "insert into t_aAnal_Range1(Term,EID,GroupID,DayType,Shift,WorkNum,BeginTime,EndTime,BeginTime2,EndTime2,BeginTime3,EndTime3,ScanBeginTime,ScanEndTime,corpid)" +
				"  select a.Term,a.EID,a.GroupID,DayType,a.Shift,a.WorkNum,a.BeginTime,a.EndTime,a.BeginTime2,a.EndTime2,a.BeginTime3,a.EndTime3,a.ScanBeginTime,a.ScanEndTime," + corpid +
				"  from atPlan_Range a ,t_aAnal_Status b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//取班次时段,应工作时数
		sql = "insert into t_aAnal_PlanTimeWage(Term,Shift,EID,TWID,WorkNum,BeginTime,EndTime,xOrder,corpid)" +
				"  select a.Term,a.Shift,a.EID,3001,a.WorkNum,a.BeginTime,a.EndTime,1," + corpid +
				"  from t_aAnal_Range a" +
				"  Where " + " a.corpid=" + corpid +
				"	and shift<>'R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "insert INTO t_aAnal_PlanTimeWage(Term,Shift,EID,TWID,WorkNum,BeginTime,EndTime,xOrder,corpid)" +
				"  select a.Term,a.Shift,a.EID,3001,a.WorkNum,a.BeginTime2,a.EndTime2,2," + corpid +
				"  from t_aAnal_Range a" +
				"  Where " + " a.corpid=" + corpid +
				"	and WorkNum=2 And shift<>'R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO t_aAnal_PlanTimeWage(Term,Shift,EID,TWID,WorkNum,BeginTime,EndTime,xOrder,corpid)" +
				"  select a.Term,a.Shift,a.EID,3001,a.WorkNum,a.BeginTime2,a.EndTime2,2," + corpid +
				"  from t_aAnal_Range a" +
				"  Where " + " a. corpid=" + corpid +
				"	and WorkNum=3 And shift<>'R'" +
				"  Union" +
				"  select  a.Term,a.Shift,a.EID,3001,a.WorkNum,a.BeginTime3,a.EndTime3,3,corpid" +
				"  from t_aAnal_Range1 a " +
				"  Where " + " a. corpid=" + corpid +
				"	and WorkNum=3 And shift<>'R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//取休息时间
		sql = "insert into t_aAnal_PlanTimeWage(Term,Shift,EID,TWID,WorkNum,BeginTime,EndTime,corpid)" +
				"  select a.Term,a.Shift,a.EID,0,a.WorkNum,a.Term,DATE_ADD(a.Term,INTERVAL 24 DAY)" + "," + corpid +
				// 休息日从0-第二天0点
				"  from t_aAnal_Range a" +
				"  Where " + " a. corpid=" + corpid +
				"	and shift='R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//请假\加班数据
		sql = "insert into t_aPlan_Reg(Term,EID,Shift,TWID,RegTWID,BeginTime,EndTime,Amount,SeqID,xType,SheetID,corpid)" +
				"  select a.Term,a.EID,b.Shift,a.TWID,a.RegTWID,a.BeginTime,a.EndTime,a.Amount,a.SeqID,a.xType,a.SheetID" + "," + corpid +
				"  from atPlan_Reg a,t_aAnal_Status b " +
				"  where " + " a. corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
//		systpaystditemMapper.listSalaryTemplate3(sql); 有问题 字段Amount不在表里

		//取班次扣减
		sql = "insert into t_aAnal_PlanBreak(Term,Shift,EID,TWID,BeginTime,EndTime,corpid)" +
				"  select  a.Term,a.Shift,a.EID,-1000,aFN_GenDateTime(a.Term,b.RestBegintime),aFN_GenDateTime(a.Term,b.RestEndtime)" + "," + corpid +
				"  from t_aAnal_Range a,atShift_Type b" +
				"  where " + " a. corpid=" + corpid +
				"	and a.Shift=b.Shift And b.type=1 And b.IsRest=1;"; // 一天一次上下班，勾选了休息时间的才有休息时间
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aAnal_PlanTimeWage a set a.Amount=Case When a.Shift<>'R' Then TIMESTAMPDIFF(MINUTE,begintime,endtime) Else 0 End" +
				" where corpid = " + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//扣除中间休息
		sql = "Update t_aAnal_PlanTimeWage a,t_aAnal_PlanBreak b" +
				"  Set a.Amount = a.Amount-TIMESTAMPDIFF(MINUTE,case when a.Begintime>=b.Begintime" +
				"           then a.Begintime   " +
				"               else b.Begintime" +
				"               end,  " +
				"           case  when a.Endtime>=b.Endtime" +
				"               then b.Endtime" +
				"               else a.Endtime  " +
				"           end)" +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.term=b.term And a.Shift=b.Shift" +
				"   and  " +
				"   case when a.Begintime>=b.Begintime" +
				"    then a.Begintime   " +
				"    else b.Begintime" +
				"   end  <  " +
				"   case when a.Endtime>=b.Endtime" +
				"    then b.Endtime" +
				"    else a.Endtime   " +
				"   end;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aAnal_PlanTimeWage1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "create TEMPORARY table t_aAnal_PlanTimeWage1 as select * from t_aAnal_PlanTimeWage";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE IF EXISTS `t_aAnal_CardIn1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql="create TEMPORARY table t_aAnal_CardIn1 as select * from t_aAnal_CardIn";
		systpaystditemMapper.listSalaryTemplate3(sql);
		// 生成考勤明细结果
		// 生成工作-->迟到、早退，第一段
		sql = "insert INTO t_aAnal_Details(term,EID,Shift,PlanTWID,FactTWID,PlanBegintime,PlanEndTime,FactBeginTime,FactEndTime," +
				"      BeginTime,EndTime,xType,SeqID,NeedChange,corpid)" +
				"  select a.term,a.EID,a.Shift,a.TWID,3205,a.BeginTime,a.EndTime,b.CardBeginTime,b.CardEndTime , " +
				"   a.BeginTime,b.CardBeginTime,a.xType,a.SeqID,1" + "," + corpid +
				"  from t_aAnal_PlanTimeWage a,t_aAnal_CardIn b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID =b.EID and a.Term=b.Term And a.WorkNum=b.Type And a.xOrder=1" +
				"   And TIMESTAMPDIFF(MINUTE,a.BeginTime,b.CardBeginTime)>0 " +   // 打卡时间比班次开始时间大的，认为迟到
				"  Union  " + //  早退
				"  select a.term,a.EID,a.Shift,a.TWID,3207,a.BeginTime,a.EndTime,b.CardBeginTime,b.CardEndTime , " +
				"   b.CardEndTime,a.EndTime,a.xType,a.SeqID,1" + "," + corpid +
				"  from t_aAnal_PlanTimeWage1 a, t_aAnal_CardIn1 b " +
				"  where" + " a.corpid=" + corpid +
				"	and  a.EID =b.EID and a.Term=b.Term And a.WorkNum=b.Type And a.xOrder=1" +
				"   And TIMESTAMPDIFF(MINUTE,b.CardEndTime,a.EndTime)>0;   ";//  打卡时间比班次结束时间小的，认为早退
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成工作-->迟到、早退，第二段
		sql = "INSERT INTO t_aAnal_Details(term,EID,Shift,PlanTWID,FactTWID,PlanBegintime,PlanEndTime,FactBeginTime,FactEndTime,BeginTime,EndTime,xType,SeqID,NeedChange,corpid)" +
				"  SELECT a.term,a.EID,a.Shift,a.TWID,3205,a.BeginTime,a.EndTime,b.CardBeginTime2,b.CardEndTime2,a.BeginTime,b.CardBeginTime2,a.xType,a.SeqID,1" + "," + corpid +
				"  FROM t_aAnal_PlanTimeWage a, t_aAnal_CardIn b " +
				"  WHERE " + " a.corpid=" + corpid +
				"	and a.EID =b.EID AND a.Term=b.Term AND a.WorkNum=b.Type AND a.xOrder=2 " +
				"  AND  TIMESTAMPDIFF(MINUTE,a.BeginTime,b.CardBeginTime2)>0  " +// 打卡时间比班次开始时间大的，认为迟到
				"  UNION   " +// 早退
				"  SELECT a.term,a.EID,a.Shift,a.TWID,3207,a.BeginTime,a.EndTime,b.CardBeginTime2,b.CardEndTime2,b.CardEndTime2,a.EndTime,a.xType,a.SeqID,1" + "," + corpid +
				"  FROM  t_aAnal_PlanTimeWage1 a,t_aAnal_CardIn1 b " +
				"  WHERE " + " a.corpid=" + corpid +
				"	and a.EID =b.EID and a.Term=b.Term And a.WorkNum=b.Type And a.xOrder=2" +
				"   And TIMESTAMPDIFF(MINUTE,b.CardEndTime2,a.EndTime)>0; ";// 打卡时间比班次结束时间小的，认为早退
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成工作-->迟到、早退，第三段
		sql = "insert INTO t_aAnal_Details(term,EID,Shift,PlanTWID,FactTWID,PlanBegintime,PlanEndTime,FactBeginTime,FactEndTime,BeginTime,EndTime,xType,SeqID,NeedChange,corpid)" +
				"  select a.term,a.EID,a.Shift,a.TWID,3205,a.BeginTime,a.EndTime,b.CardBeginTime3,b.CardEndTime3,a.BeginTime,b.CardBeginTime3,a.xType,a.SeqID,1" + "," + corpid +
				"  from t_aAnal_PlanTimeWage a, t_aAnal_CardIn b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID =b.EID and a.Term=b.Term And a.WorkNum=b.Type And a.xOrder=3" +
				"   And TIMESTAMPDIFF(MINUTE,a.BeginTime,b.CardBeginTime3)>0 " +// -- 打卡时间比班次开始时间大的，认为迟到
				"  Union " +// -- 早退
				"  select a.term,a.EID,a.Shift,a.TWID,3207,a.BeginTime,a.EndTime,b.CardBeginTime3,b.CardEndTime3, " +
				"   b.CardEndTime3,a.EndTime,a.xType,a.SeqID,1" + "," + corpid +
				"  from t_aAnal_PlanTimeWage1 a, t_aAnal_CardIn1 b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID =b.EID and a.Term=b.Term And a.WorkNum=b.Type And a.xOrder=3" +
				"   And TIMESTAMPDIFF(MINUTE,b.CardEndTime3,a.EndTime)>0;   ";// -- 打卡时间比班次结束时间小的，认为早退
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除跟请假重叠的记录
		sql = "Delete a" +
				"  From t_aAnal_Details a,t_aPlan_Reg b" +
				"  Where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID And a.Term=b.Term And a.Shift=b.Shift And b.SheetID=1 " +// -- 只取请假
				"   And case when a.Begintime>=b.Begintime" +
				"    then a.Begintime   " +
				"    else b.Begintime" +
				"    end  <  " +
				"    case when a.Endtime>=b.Endtime" +
				"     then b.Endtime" +
				"     else a.Endtime   " +
				"    end;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update t_aAnal_Details a set TotalMins= TIMESTAMPDIFF(MINUTE,begintime,endtime),Amount= TIMESTAMPDIFF(MINUTE,begintime,endtime) " +
				" where " + " a.corpid=" + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//迟到，早退超30分钟转缺勤
		sql = "Update t_aAnal_Details a Set a.FactTWID=3201" +
				"  Where a.corpid = " + corpid +
				"	and a.Amount>30 And a.FactTWID In(3205,3207);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//缺勤超4小时转旷工
		sql = "Update t_aAnal_Details a Set a.FactTWID=3203" +
				"  Where a.corpid = " + corpid +
				"	and a.Amount/60.0>4.0  And a.FactTWID In(3205,3207);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//请假\加班数据
		sql = "insert INTO t_aAnal_Details(term,EID,Shift,PlanTWID,FactTWID,PlanBegintime,PlanEndTime,FactBeginTime,FactEndTime," +
				"      BeginTime,EndTime,xType,SeqID,Amount,corpid)" +
				"  select a.Term,a.EID,a.Shift,a.TWID,a.TWID,a.BeginTime,a.EndTime,a.BeginTime,a.EndTime," +
				"    a.BeginTime,a.EndTime,a.xType,a.SeqID,a.Amount,corpid" +
				"  from t_aPlan_Reg a " +
				" where a.corpid=" + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成应工作时数
		sql = "insert INTO t_aAnal_Details(term,EID,Shift,PlanTWID,FactTWID,PlanBegintime,PlanEndTime,FactBeginTime,FactEndTime," +
				"      BeginTime,EndTime,xType,SeqID,TotalMins,Amount,NeedChange,corpid)" +
				"  select a.Term,a.EID,a.Shift,a.TWID,a.TWID,a.BeginTime,a.EndTime,a.BeginTime,a.EndTime,a.BeginTime,a.EndTime,a.xType,a.SeqID,a.Amount,a.Amount,1,a.corpid " +
				"  from t_aAnal_PlanTimeWage a" +
				"  Where a.corpid = " + corpid +
				"	and a.Amount<>0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新统计单位
		sql = "Update t_aAnal_Details a ,atCD_TimeWage b Set a.Unit=b.Unit" +
				"  Where a.corpid = " + corpid +
				"	and a.FactTWID=b.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//单位为天及小时的全部转换成小时
		sql = "Update t_aAnal_Details K1 Set Amount =Case When Unit in('H','D') Then TotalMins/60.0 Else TotalMins End" +
				"  Where K1.corpid= " + corpid +
				"	and K1.NeedChange=1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//将分析结果记录到相应的表中
		sql = "Delete a " +
				"  from atShift_Details a,t_aAnal_Status b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成考勤的日明细结果
		sql = "insert INTO atShift_Details (Term,Term2,EID,PlanTWID," +
				"      PlanBeginTime,PlanEndTime," +
				"      FactBeginTime,FactEndTime," +
				"      BeginTime,EndTime," +
				"      FactTWID," +
				"      isException," +
				"      TotalMins,NetMins,Num," +
				"      Amount,Unit,Remark,corpid)" +
				"  select Term,Term2,EID,PlanTWID," +
				"    PlanBeginTime,PlanEndTime," +
				"    FactBeginTime,FactEndTime," +
				"    BeginTime,EndTime," +
				"    FactTWID," +
				"    isException," +
				"    TotalMins,NetMins,Num," +
				"    Amount,Unit,Remark,corpid" +
				"  from t_aAnal_Details;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//将分析结果记录到相应的表中
		getAnalDispInterfaceEasy(beginDate, endDate);

		//加班转调休的情况
		sql = "INSERT INTO atAnnual_Balances(Term,Badge,EID,Name,CompID,DepID,JobID,EmpStatus,JoinDate,ThisYear,Balances,Validity,TWID,corpid)" +
				"  SELECT CONCAT(CAST(DATE_FORMAT('" + beginDate + "','%Y') AS CHAR),'-01-01'),c.Badge,c.EID,c.Name,c.CompID,c.DepID,c.JobID,c.EmpStatus,c.JoinDate," +
				"   Sum(a.CLOT),Sum(a.CLOT),CONCAT(CAST(DATE_FORMAT('" + beginDate + "','%Y') AS CHAR),'-12-31'),2120,a.corpid " +
				"  FROM atShift_Day a,t_aAnal_Status b,etemployee c" +
				"  WHERE a.corpid=" + corpid +
				"	and a.EID=b.EID AND a.EID=c.EID And TIMESTAMPDIFF(DAY,a.Term,b.Term)=0 And IFNULL(a.CLOT,0)<>0" +
				"     AND NOT EXISTS(SELECT 1 from atAnnual_Balances WHERE corpid=" + corpid +
				"	and a.EID=EID AND TIMESTAMPDIFF(YEAR,Term,'" + beginDate + "')=0 And TWID=2120)";
		systpaystditemMapper.listSalaryTemplate3(sql); //有问题 字段非空但是查出来的是空 eid

		sql = "DROP TABLE IF EXISTS `t_aAnal_Status1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql="create TEMPORARY table t_aAnal_Status1 as select * from t_aAnal_Status";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE IF EXISTS `t_aAnal_Status2`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql="create TEMPORARY table t_aAnal_Status2 as select * from t_aAnal_Status";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Update atAnnual_Balances a,(Select Sum(OTCL) As UsedAmount,Sum(CLOT) As ThisYear" +
				"        From (Select IFNULL(OTCL,0) As OTCL,IFNULL(CLOT,0) As CLOT from atShift_Day " +
				"         Where corpid=" + corpid +
				"	and EID In(Select EID from t_aAnal_Status where corpid = " + corpid +
				" ) And TIMESTAMPDIFF(YEAR,Term,'" + beginDate + "')=0 And (IFNULL(OTCL,0)<>0 Or IFNULL(CLOT,0)<>0)" +
				"         Union " +
				"         Select IFNULL(OTCL,0) As OTCL,IFNULL(CLOT,0) As CLOT from atShift_Day_All " +
				"         Where corpid =" + corpid +
				"	and EID In(Select EID from t_aAnal_Status1 where corpid=" + corpid +
				"  ) And TIMESTAMPDIFF(YEAR,Term,'" + beginDate + "')=0 And (IFNULL(OTCL,0)<>0 Or IFNULL(CLOT,0)<>0)" +
				"         ) b" +
				"        ) c" +
				"  Set a.ThisYear=IFNULL(c.ThisYear,0),a.UsedAmount=IFNULL(c.UsedAmount,0)," +
				"     a.Balances=IFNULL(c.ThisYear,0)+IFNULL(a.ThisYearAdj,0)-IFNULL(c.UsedAmount,0)" +
				"  Where " + " a.corpid = " + corpid +
				"	and a.EID In(Select EID from t_aAnal_Status2) And TIMESTAMPDIFF(YEAR,a.Term," + beginDate + ")=0 And a.TWID=2120;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新年假
		sql = "Update atAnnual_Balances a,(Select Sum(AnnL) As UsedAmount" +
				"   From (Select IFNULL(AnnL,0) As AnnL from atShift_Day " +
				"    Where  corpid=" + corpid +
				"	and EID In(Select EID from t_aAnal_Status where corpid = " + corpid +
				" ) And TIMESTAMPDIFF(YEAR,Term,'" + beginDate + "')=0 And IFNULL(AnnL,0)<>0" +
				"    Union" +
				"    Select IFNULL(AnnL,0) As AnnL from atShift_Day_All " +
				"    Where corpid=" + corpid +
				"	and EID In(Select EID from t_aAnal_Status1 where corpid=" + corpid +
				"	) And TIMESTAMPDIFF(YEAR,Term,"+beginDate+")=0 And IFNULL(AnnL,0)<>0" +
				"    ) b" +
				"   ) c" +
				"  Set a.UsedAmount=IFNULL(c.UsedAmount,0),a.Balances=IFNULL(a.ThisYear,0)+IFNULL(a.ThisYearAdj,0)-IFNULL(c.UsedAmount,0)  " +
				"  Where " + " a.corpid = " + corpid +
				"	and a.EID In(Select EID from t_aAnal_Status2) And TIMESTAMPDIFF(YEAR,a.Term,'" + beginDate + "')=0 And a.TWID=2119;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "update atPlan_Range a,t_aAnal_Status b set a.AnalyMode =6,b.AnalyMode =6" +
				"  Where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DROP TABLE `t_aAnal_Status1`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP TABLE `t_aAnal_Status2`;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table  t_aAnal_Details;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table  t_aAnal_PlanTimeWage;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table  t_aAnal_PlanTimeWage1;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table  t_aAnal_PlanBreak;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table  t_aAnal_Range;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop table  t_aPlan_Reg;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		return R.ok("成功");
	}
	@Transactional(rollbackFor = Exception.class)
	public void getAnalDispInterfaceEasy(String from, String to) {
		String sql;
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();

		sql = "drop table if EXISTS t_aAnal_Disp";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_Disp" +
				"  (" +
				"   Term   datetime ," +
				"   Shift  varchar(10)," +
				"   EID   INT," +
				"   TWID   SMALLINT," +
				"   Amount  DECIMAL(18,2)," +
				"   Unit   varchar(2)," +
				"   xHours DECIMAL(18,2)," +
				"   corpid   INT" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aAnal_Disp On t_aAnal_Disp(Term,EID,TWID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "drop table if EXISTS t_aAnal_DaySum";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE TEMPORARY TABLE t_aAnal_DaySum" +
				"  (" +
				"   Term   datetime," +
				"   EID   INT," +
				"   TWID   SMALLINT," +
				"   Shift  varchar(10)," +
				"   Amount  decimal(18,2)," +
				"   Unit     varchar (2), " +
				"   corpid   INT" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Create Index IX_aAnal_DaySum On t_aAnal_DaySum (Term,EID,TWID);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "SELECT IFNULL(xValue,2) IsException from atCD_Constant where ID=4;";
		Map map = systpaystditemMapper.listSalaryTemplate2(sql);
		String isException = map.get("IsException").toString();

		sql = "delete a" +
				"  from atShift_day a, t_aAnal_Status b " +
				"  where a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成考勤的日显示结果
		sql = "insert INTO atShift_Day(Term,EID,corpid)" +
				"  select Term,EID,corpid " +
				"  from t_aAnal_Status;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO  t_aAnal_Disp(Term,EID,Shift,TWID,Unit,corpid)" +
				"  SELECT a.Term,a.EID,a.Shift,b.TWID ,b.Unit,a.corpid" +
				"  from t_aAnal_Status a, aVW_TimeWageDisp b " +
				"  Where " + " a.corpid=" + corpid +
				"	and b.TWID/100*100<>b.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO t_aAnal_DaySum(Term,EID,Shift,TWID,Unit,Amount,corpid)" +
				"  SELECT Term,EID,Shift,FactTWID,Unit,Sum(Amount),corpid" +
				"  FROM t_aAnal_Details where corpid=" + corpid +
				"  GROUP BY Term,EID,Shift,FactTWID,Unit,corpid";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新日显示数据
		sql = "UPDATE t_aAnal_Disp U1 Join t_aAnal_DaySum P2 On P2.EID=U1.EID and U1.Term=P2.Term" +
				"  SET U1.Amount=P2.Amount" +
				"  Where " + " U1.corpid=" + corpid +
				"	and U1.TWID=P2.TWID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "drop table if EXISTS t_aAnal_DaySum1";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "create TEMPORARY table t_aAnal_DaySum1 as select * from t_aAnal_DaySum";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//计算工作时数=应工作时数-请假-迟到、早退-缺勤、旷工(加班去掉)
		sql = "Update t_aAnal_Disp U1" +
				"   left Join  (Select EID,Term,Amount " +
				"      From t_aAnal_DaySum" +
				"      Where corpid=" + corpid +
				"	and TWID=3001" +
				"      ) P1 On P1.EID=U1.EID and U1.Term=P1.Term" +
				"   left Join  (Select EID,Term,Sum(Case When Unit='M' Then Amount/60.0 Else Amount End) As Amount " +
				"      From t_aAnal_DaySum1" +
				"      Where corpid=" + corpid +
				"	and (TWID/100*100=2100 Or TWID In(3201,3203,3205,3207))" +
				"      Group By EID,Term" +
				"      ) P2 On P2.EID=U1.EID and U1.Term=P2.Term" +
				"  Set U1.Amount = IFNULL(P1.Amount,0)-IFNULL(P2.Amount,0)" +
				"  Where U1.corpid=" + corpid +
				"	and U1.TWID=3003;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新一天的工作时数
		sql = "Update t_aAnal_Disp a,atShift_Type b Set a.xHours=b.xHours" +
				"  Where a.corpid=" + corpid +
				"	and a.Shift=b.Shift;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "drop table if EXISTS `t_aAnal_Disp1`";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "create TEMPORARY table t_aAnal_Disp1 as select * from t_aAnal_Disp ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//更新工作天数
		sql = "Update t_aAnal_Disp a,t_aAnal_Disp1 b Set a.Amount=b.Amount/b.xHours" +
				"  Where a.corpid=" + corpid +
				"	and a.EID=b.EID And a.Term=b.Term And a.Shift=b.Shift And a.TWID=3002 And b.TWID=3001 And a.Shift<>'R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新工作天数
		sql = "Update t_aAnal_Disp a,t_aAnal_Disp1 b Set a.Amount=b.Amount/b.xHours" +
				"  Where a.corpid=" + corpid +
				"	and a.EID=b.EID And a.Term=b.Term And a.Shift=b.Shift And a.TWID=3004 And b.TWID=3003 And a.Shift<>'R';";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//单位转换
		sql = "Update t_aAnal_Disp a Set a.Amount=Case When a.Unit='D' Then a.Amount/a.xHours Else a.Amount End" +
				"  Where a.corpid=" + corpid +
				"	and a.Shift<>'R' And a.TWID Not In(3002,3004);";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新日显示(公式)
		getAnalDispTranSub();

		sql = "update atShift_Day a, t_aAnal_Range b Set a.GroupID=b.GroupID,a.Shift=b.Shift " +
				"  where a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "update atShift_Day a, t_aAnal_Range1 b Set a.GroupID=b.GroupID,a.Shift=b.Shift " +
				"  where a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update atShift_Day a, etemployee b,t_aAnal_Status c" +
				"  Set a.Badge= b.badge,a.Name = b.name,a.CompID=b.CompID,a.DepID= b.DepID,a.JobID=b.JobID" +
				"  where a.corpid=" + corpid +
				"	and a.EID=b.EID And a.EID=c.EID And TIMESTAMPDIFF(DAY,a.Term,c.Term)=0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//更新实际出勤大于1的情况
		sql = "Update atShift_Day a,t_aAnal_Status b Set a.WKDY=1" +
				"  where a.corpid=" + corpid +
				"	and a.WKDY>1 And a.EID=b.EID And TIMESTAMPDIFF(DAY,a.Term,b.Term)=0;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Delete a " +
				"  from atShift_Statist a, t_aAnal_Status b " +
				"  where " + " a.corpid=" + corpid +
				"	and a.EID=b.EID and a.Term=b.Term;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成考勤的日统计结果
		sql = "insert into atShift_Statist (Term,EID,FactTWID,FactAmount,TWID,Amount,Unit,corpid)" +
				"  select Term,EID,TWID,Sum(Amount),TWID,Sum(Amount),Unit," + corpid +
				"  from t_aAnal_Disp" +
				"  Where " + " corpid=" + corpid +
				"	and Amount<>0" +
				"  Group by Term,EID,TWID,Unit,corpid";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update  atShift_Day a,t_aAnal_Status c Set a.IsException= 1 " +
				"  where  a.corpid=" + corpid +
				"	and  a.EID=c.EID   " +
				"   and a.Term=c.Term  " +
				"   and( '" + isException + "' in (0,2)" +
				"    and Exists(select 1 from atShift_Details b where b.corpid=" + corpid +
				"	and a.Term=b.Term and a.EID=b.EID and b.IsException=1)" +
				"    or '" + isException + "' in (1,2)" +
				"    and Exists(select 1 from atShift_Statist b where b.corpid=" + corpid +
				"	and a.Term=b.Term and a.EID=b.EID and b.IsException=1)" +
				"    );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Drop Table t_aAnal_Disp;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop Table t_aAnal_Disp1;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop Table t_aAnal_DaySum;";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Drop Table t_aAnal_DaySum1;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}
	@Transactional(rollbackFor = Exception.class)
	public void getAnalDispTranSub() {
		String sql;
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		sql = "DROP TABLE IF EXISTS `t_aShift_Day_Temp`;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE TEMPORARY TABLE `t_aShift_Day_Temp`" +
				"  (" +
				"   Term Datetime," +
				"   EID Int, " +
				"   CDER Decimal(18,2)," +
				"   PNHR Decimal(18,2)," +
				"   PNDY Decimal(18,2)," +
				"   WKHR Decimal(18,2)," +
				"   WKDY Decimal(18,2)," +
				"   LIMN Decimal(18,2)," +
				"   EOMN Decimal(18,2)," +
				"   DNAN Decimal(18,2)," +
				"   LITM Decimal(18,2)," +
				"   EOTM Decimal(18,2)," +
				"   ABHR Decimal(18,2)," +
				"   ABDY Decimal(18,2)," +
				"   UAHR Decimal(18,2)," +
				"   OTAB Decimal(18,2)," +
				"   OT1 Decimal(18,2)," +
				"   OT2 Decimal(18,2)," +
				"   OT3 Decimal(18,2)," +
				"   CLOT Decimal(18,2)," +
				"   SicL Decimal(18,2)," +
				"   PerL Decimal(18,2)," +
				"   AnnL Decimal(18,2)," +
				"   ComL Decimal(18,2)," +
				"   MarL Decimal(18,2)," +
				"   PrPL Decimal(18,2)," +
				"   MatL Decimal(18,2)," +
				"   PatL Decimal(18,2)," +
				"   OTCL Decimal(18,2)," +
				"   PuHL Decimal(18,2)," +
				"   NurL Decimal(18,2)," +
				"   ISJL Decimal(18,2)," +
				"   FANF Decimal(18,2)," +
				"   OfAL Decimal(18,2)," +
				"   LVWK Decimal(18,2)," +
				"   REWK Decimal(18,2)," +
				"   OTCD Decimal(18,2)," +
				"   NUDY Decimal(18,2)," +
				"   corpid int" +
				"  );";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Insert Into t_aShift_Day_Temp(Term,EID,CDER,PNHR,PNDY,WKHR,WKDY,LIMN,EOMN,DNAN,LITM,EOTM,ABHR,ABDY,UAHR,OTAB,OT1,OT2,OT3,CLOT,SicL,PerL,AnnL,ComL," +
				"  MarL,PrPL,MatL,PatL,OTCL,PuHL,NurL,ISJL,FANF,OfAL,LVWK,REWK,OTCD,NUDY,corpid)" +
				"  Select Term,EID,Sum(Case When  TWID =3209 Then Amount Else NULL End ),Sum(Case When  TWID =3001 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3002 Then Amount Else NULL End ),Sum(Case When  TWID =3003 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3004 Then Amount Else NULL End ),Sum(Case When  TWID =3205 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3207 Then Amount Else NULL End ),Sum(Case When  TWID =3105 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3206 Then Amount Else NULL End ),Sum(Case When  TWID =3208 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3201 Then Amount Else NULL End ),Sum(Case When  TWID =3202 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3203 Then Amount Else NULL End ),Sum(Case When  TWID =3210 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =1301 Then Amount Else NULL End ),Sum(Case When  TWID =1302 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =1303 Then Amount Else NULL End ),Sum(Case When  TWID =1402 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2102 Then Amount Else NULL End ),Sum(Case When  TWID =2105 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2119 Then Amount Else NULL End ),Sum(Case When  TWID =2104 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2107 Then Amount Else NULL End ),Sum(Case When  TWID =2109 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2113 Then Amount Else NULL End ),Sum(Case When  TWID =2114 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2120 Then Amount Else NULL End ),Sum(Case When  TWID =2122 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2124 Then Amount Else NULL End ),Sum(Case When  TWID =2101 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2127 Then Amount Else NULL End ),Sum(Case When  TWID =2121 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =3211 Then Amount Else NULL End ),Sum(Case When  TWID =3213 Then Amount Else NULL End )" +
				"   ,Sum(Case When  TWID =2131 Then Amount Else NULL End ),Sum(Case When  TWID =2132 Then Amount Else NULL End )" +
				"   ,corpid " +
				"  FROM t_aAnal_Disp  where corpid=" + corpid +
				" Group by Term,EID,corpid";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Update atShift_Day a,t_aShift_Day_Temp b " +
				"  Set a.CDER=b.CDER,a.PNHR=b.PNHR,a.PNDY=b.PNDY,a.WKHR=b.WKHR,a.WKDY=b.WKDY,a.LIMN=b.LIMN,a.EOMN=b.EOMN," +
				"  a.DNAN=b.DNAN,a.LITM=b.LITM,a.EOTM=b.EOTM,a.ABHR=b.ABHR,a.ABDY=b.ABDY,a.UAHR=b.UAHR,a.OTAB=b.OTAB," +
				"  a.OT1=b.OT1,a.OT2=b.OT2,a.OT3=b.OT3,a.CLOT=b.CLOT,a.SicL=b.SicL,a.PerL=b.PerL,a.AnnL=b.AnnL,a.ComL=b.ComL," +
				"  a.MarL=b.MarL,a.PrPL=b.PrPL,a.MatL=b.MatL,a.PatL=b.PatL,a.OTCL=b.OTCL,a.PuHL=b.PuHL,a.NurL=b.NurL," +
				"  a.ISJL=b.ISJL,a.FANF=b.FANF,a.OfAL=b.OfAL,a.LVWK=b.LVWK,a.REWK=b.REWK,a.OTCD=b.OTCD,a.NUDY=b.NUDY" +
				"  Where a.corpid=" + corpid +
				"	and a.Term=b.Term " +
				"  and a.EID=b.EID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

	}


}
