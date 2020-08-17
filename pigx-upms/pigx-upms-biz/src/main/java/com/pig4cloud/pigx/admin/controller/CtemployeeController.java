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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.*;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


/**
 *
 *
 * @author gaoxiao
 * @date 2020-04-14 14:20:29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctemployee" )
@Api(value = "ctemployee", tags = "管理")
@Slf4j
public class CtemployeeController {

	private final  CtemployeeService ctemployeeService;
	private final SystpaystditemService systpaystditemService;
	private final SystpaystditemMapper systpaystditemMapper;
	private final CtemployeeMapper ctemployeeMapper;
	private final SystmessageMapper systmessageMapper;
	private final CommonLogService commonLogService;
	private final SystpayrollgroupService systpayrollgroupService;
	private final  SystpayrollgroupprocessService systpayrollgroupprocessService;
	private final  SystpayitemfactvalueService systpayitemfactvalueService;
	private final OtdepartmentMapper otdepartmentMapper;
	private final SystpayrollitemService systpayrollitemService;
	private final  CtattenditemService ctattenditemService;
	private final  CtattenditemfactvalueService ctattenditemfactvalueService;
	private final  CtperformanceitemService ctperformanceitemService;
	private final  SystdataobjsService systdataobjsService;
	private final  SystdatacolsService systdatacolsService;
	private final  CtcdConstService ctcdConstService;
	private final  CtcdPayrollperiodService ctcdPayrollperiodService;
	//ctCD_DecSumPeriods
	private final CtcdDecsumperiodsService ctcdDecsumperiodsService;
	private final SystpayrollempinfoService systpayrollempinfoService;
	private final SystpayrollempinfoAllService systpayrollempinfoAllService;
	private final SystpayiteminputvalueService systpayiteminputvalueService;
	private final SystpayitemfactvalueAllService systpayitemfactvalueAllService;
	private final SystpayitemfactvalueMapper systpayitemfactvalueMapper;
	private final SystpayiteminputvalueMapper systpayiteminputvalueMapper;
	private final SystpayitemfactvalueAllMapper systpayitemfactvalueAllMapper;
	private final SystpayrollitemAllService systpayrollitemAllService;
	private final SystpayrollitemAllMapper systpayrollitemAllMapper;
	private final CtattendAllService ctattendAllService;
	private final CtattendService ctattendService;
	private final CtattendAllMapper ctattendAllMapper;
	private final CtattenditemAllService ctattenditemAllService;
	private final CtattenditemAllMapper ctattenditemAllMapper;
	private final CtattenditemfactvalueAllService ctattenditemfactvalueAllService;
	private final CtattenditemfactvalueMapper ctattenditemfactvalueMapper;
	private final CtperformancebonusService ctperformancebonusService;
	private final CtperformancebonusAllService ctperformancebonusAllService;
	private final CtperformanceitemAllService ctperformanceitemAllService;
	private final CtperformanceitemfactvalueService ctperformanceitemfactvalueService;
	private final CtperformanceitemfactvalueAllService ctperformanceitemfactvalueAllService;
	private final CtbenpaybackRegisterService ctbenpaybackRegisterService;
	private final CtbenpaybackAllService ctbenpaybackAllService;
	private final CtbencalcService ctbencalcService;
	private final CtbencalcAllService ctbencalcAllService;
	private final CtbenefitstatusService ctbenefitstatusService;
	private final SystpaystditemAllService systpaystditemAllService;
	private final CtstandardService ctstandardService;
	private final CtstandardAllService ctstandardAllService;
	private final SystpayrollgroupprocessAllService systpayrollgroupprocessAllService;
	private final CtregularpayRegisterService ctregularpayRegisterService;
	private final CtregularpayAllService ctregularpayAllService;
	private final CtbenefitstatusAllService ctbenefitstatusAllService;
	private final CtemployeeAllService ctemployeeAllService;
	/**
	 /**
	 * 分页查询
	 * @param page 分页对象
	 * @param ctemployee
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R page(Page page, Ctemployee ctemployee) {
		return R.ok(ctemployeeService.page(page, Wrappers.query(ctemployee)));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "获取薪资信息(动态)", notes = "获取薪资开启登记(动态)")
	@PostMapping("/getCtemployeePageSql" )
	public R getCtemployeePageSql(Page page,@RequestBody CvwEmployee cvwEmployee ){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		Integer type =null;
		//获取当前薪资薪资
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpcode(corpcode);

		List systpaystditemList = systpaystditemService.list(Wrappers.query(systpaystditem).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select A1.eid as eid,A1.badge as badge,A1.name as name,A1.compid as compid,A1.depid as depid,A1.jobid as jobid,A1.empstatus as empstatus,A1.empgrade as empgrade,date_format( A1.joindate, '%Y-%m-%d' ) as joindate,A1.workcity as workcity,A1.paystatus as paystatus,A1.salarystatus as salarystatus,A1.paymode as paymode,A1.costid as costid,A1.bankid as bankid,A1.bankno as bankno,A1.openbankemp as openbankemp,A1.bankid2 as bankid2,A1.bankno2 as bankno2,A1.openbankemp2 as openbankemp2,A1.salarytype as salarytype,A1.salarygrade as salarygrade,A1.salarykind as salarykind,A1.salarycity as salarycity,A1.calcway as calcway,date_format( A1.effectdate, '%Y-%m-%d' ) as effectdate,A1.remark ";
		String sql2= "(select EID ";
		String sql5 = "(select a.eid ";
		for(int i=0;i<systpaystditemList.size();i++){

			item = (Systpaystditem)systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			sql1 = sql1 + "," +"" + "A2." +title;

			sql2 = sql2 + " ,case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;


			sql5 = sql5 + ","+" max(a."+title+")"+" "+title;


		}
		long current = page.getCurrent();
		long size = page.getSize();
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from ctstandard  Where  corpcode = '" + corpcode +"') a group by eid ) A2";
		sql5 = sql5 + " from " +sql2;

		sql = sql1+" left join "+sql5 +" on A1.EID = A2.EID ";

		sql = sql + " where A1.corpcode ='" + corpcode +"'";
		Integer status = cvwEmployee.getSalarystatus();
		Integer depid = cvwEmployee.getDepid();
		Integer jobid = cvwEmployee.getJobid();
		String name = cvwEmployee.getName();
		if(!StringUtils.isEmpty(depid)){
			sql = sql + " and  A1.depid ="+depid;
		}
		if(!StringUtils.isEmpty(jobid)){
			sql = sql + " and A1.jobid ="+jobid;
		}
		if(!StringUtils.isEmpty(name)){
			sql = sql + " and A1.name like '%"+name+"%'";
		}
		if(!StringUtils.isEmpty(status)){
			sql = sql + " and ifnull(A1.salarystatus,0)="+status;
		}
		sql = sql + " limit "+(current-1)*size+","+size;
		List list = systpaystditemMapper.listSalaryTemplate(sql);

		QueryWrapper<Systpaystditem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		List stditem = systpaystditemService.list(queryWrapper);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());
		Map resultMap = new HashMap();
		resultMap.put("resultpage",resultpage);
		resultMap.put("stditem",stditem);
		return  R.ok(resultMap);



	}

	/**
	 * 关联ctemploye etmeployee
	 * @param page 分页对象
	 * @param ctemployee
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getCtemployeePage" )
	public R getCtemployeePage(Page page, Ctemployee ctemployee) {
		return R.ok(ctemployeeService.page(page, Wrappers.query(ctemployee)));
	}

	/**
	 * 通过id查询
	 * @param eid id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{eid}" )
	public R getById(@PathVariable("eid" ) Integer eid) {
		return R.ok(ctemployeeService.getById(eid));
	}

	/**
	 * 新增
	 * @param ctemployee
	 * @return R
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@SysLog("新增" )
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_ctemployee_add')" )
	public R save(@RequestBody Ctemployee ctemployee) {
		return R.ok(ctemployeeService.save(ctemployee));
	}



	/**
	 * 初始化
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "初始化", notes = "初始化")
	@SysLog("初始化")
	@PostMapping("/sysSPcInit1")
	@Transactional
	public R sysSPcInit1(Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		/*
		QueryWrapper<CommonLog> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("type","1");

		List list = commonLogService.list(queryWrapper);
		if(list.size()>0){
			//return R.ok("已开启");
		}else{
			String currentTime = DateUtils.getTimeString();
			CommonLog commonLog = new CommonLog();
			commonLog.setCorpname(pigxUser.getCorpname());
			commonLog.setCorpcode(corpcode);
			commonLog.setCorpid(pigxUser.getCorpid());
			commonLog.setUsername(pigxUser.getName());
			commonLog.setType(2);
			commonLog.setCreatedate(currentTime);
			commonLogService.save(commonLog);
		}

		QueryWrapper<Systpayrollgroup> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper1);
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请先维护薪资账套!");
		}
		Integer gid = systpayrollgroup.getId();
		Systpayrollgroupprocess systpayrollgroupprocess = systpayrollgroupprocessService.getById(gid);
		if(StringUtils.isEmpty(systpayrollgroupprocess)){
			return R.ok("","请弹出薪资期间选择框(前端操作)",-1);
		}else{
			if(systpayrollgroupprocess.getClosed()==1){
				return R.ok("","请弹出薪资期间选择框(前端操作)",-1);
			}
		}*/
		QueryWrapper<Systpayrollgroup> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper1);
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请先维护薪资账套!");
		}
		//{call sysSPcInit1(#{term,mode=IN,jdbcType=VARCHAR},#{userid,mode=IN,jdbcType=INTEGER},#{result,mode=OUT,jdbcType=INTEGER})}
		Integer gid = systpayrollgroup.getId();
		String term = paramMap.get("term").toString();
		String sql = "{call sysSPcInit"+gid+"(#{"+term+",mode=IN,jdbcType=VARCHAR},#{"+pigxUser.getId()+",mode=IN,jdbcType=INTEGER},#{null,,mode=OUT,jdbcType=INTEGER})}";

		Map msgMap = systpaystditemMapper.listSalaryTemplate2(sql);
		//调用确认的存储过程
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		if(msgid==0){
			return R.ok(message);
		}else{
			return R.failed(message);
		}


/*
		int userid = pigxUser.getId();
		paramMap.put("userid", userid);
		//调用确认的存储过程
		Map msgMap = ctemployeeMapper.sysSPcInit1(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		return R.ok(message);*/
	}

	/**
	 * 计算
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "计算", notes = "计算")
	@SysLog("计算")
	@PostMapping("/sysSPcCalc1")
	@Transactional
	public R sysSPcCalc1(Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		paramMap.put("userid", userid);
		//调用确认的存储过程
		Map msgMap = ctemployeeMapper.sysSPcCalc1(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		return R.ok(message);
	}

	/**
	 * 签发
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "签发", notes = "签发")
	@SysLog("签发")
	@PostMapping("/cSPPayrollConfirm")
	@Transactional
	public R cSPPayrollConfirm(Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		paramMap.put("userid", userid);
		//调用确认的存储过程
		Map msgMap = ctemployeeMapper.cSPPayrollConfirm(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		return R.ok(message);
	}
	/**
	 * 反签发
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "反签发", notes = "反签发")
	@SysLog("反签发")
	@PostMapping("/cSPPayrollUnConfirm")
	@Transactional
	public R cSPPayrollUnConfirm(Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		paramMap.put("userid", userid);
		//调用确认的存储过程
		Map msgMap = ctemployeeMapper.cSPPayrollUnConfirm(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		return R.ok(message);
	}

	/**
	 * 封账
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "封账", notes = "封账")
	@SysLog("反签发")
	@PostMapping("/sysSPcClose1")
	@Transactional
	public R sysSPcClose1(Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		paramMap.put("userid", userid);
		//调用确认的存储过程
		Map msgMap = ctemployeeMapper.sysSPcClose1(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		return R.ok(message);
	}

	/*
	 */
/**
 * 修改
 * @param ctemployee
 * @return R
 *//*

    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctemployee_edit')" )
    public R updateById(@RequestBody Ctemployee ctemployee) {
        return R.ok(ctemployeeService.updateById(ctemployee));
    }

    */
	/**
	 * 通过id删除
	 * @return R
	 *//*

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{eid}" )
    @PreAuthorize("@pms.hasPermission('admin_ctemployee_del')" )
    public R removeById(@PathVariable Integer eid) {
        return R.ok(ctemployeeService.removeById(eid));
    }
*/

	/*
	  以下为薪资报表
	 */
	//人员结构占比
	@ApiOperation(value = "人员结构占比", notes = "人员结构占比")
	@PostMapping("/getRYJGZB" )
	public R getRYJGZB(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//获取当前薪资薪资
		Double d1 = 0.0;
		Double d2 = 0.0;
		Double d3 = 0.0;
		//正式
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		map1.put("empstatus",1);
		map1.put("term",term);
		Map resultMap1  = ctemployeeMapper.listCtstandardAllGroupByEmpstatus(map1);
		if(StringUtils.isEmpty(resultMap1)){
			d1 = 0.0;
		}else{
			d1 = resultMap1.get("xvalue")!=null?Double.parseDouble(resultMap1.get("xvalue").toString()):0.0;
		}

		//試用
		Map map2 = new HashMap();
		map2.put("corpcode",corpcode);
		map2.put("empstatus",2);
		map2.put("term",term);
		Map resultMap2  = ctemployeeMapper.listCtstandardAllGroupByEmpstatus(map2);
		if(StringUtils.isEmpty(resultMap2)){
			d2 = 0.0;
		}else{
			d2 = resultMap2.get("xvalue")!=null?Double.parseDouble(resultMap2.get("xvalue").toString()):0.0;
		}

		//实习
		Map map3 = new HashMap();
		map3.put("corpcode",corpcode);
		map3.put("empstatus",4);
		map3.put("term",term);
		Map resultMap3  = ctemployeeMapper.listCtstandardAllGroupByEmpstatus(map3);
		if(StringUtils.isEmpty(resultMap3)){
			d3 = 0.0;
		}else {
			d3 = resultMap3.get("xvalue")!=null?Double.parseDouble(resultMap3.get("xvalue").toString()):0.0;
		}


		double dd1 =0.0;
		double dd2 =0.0;
		double dd3 =0.0;
		double dd = d1+d2+d3;
		if(dd==0.0)	{

		}else{
			dd1 = new BigDecimal(d1/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			dd2 = new BigDecimal(d2/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			dd3 = new BigDecimal(d3/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}


		Map resultmap1 = new HashMap();
		Map resultmap2 = new HashMap();
		Map resultmap3 = new HashMap();
		resultmap1.put("title","正式员工");
		resultmap1.put("total",d1);
		resultmap1.put("rate",dd1*100+"%");

		resultmap2.put("title","试用员工");
		resultmap2.put("total",d2);
		resultmap2.put("rate",dd2*100+"%");

		resultmap3.put("title","实习员工");
		resultmap3.put("total",d3);
		resultmap3.put("rate",dd3*100+"%");
		List resultList = new ArrayList(3);
		resultList.add(0,resultmap1);
		resultList.add(1,resultmap2);
		resultList.add(2,resultmap3);
		return  R.ok(resultList);

	}


	//薪资支出占比
	@ApiOperation(value = "薪资支出占比", notes = "薪资支出占比")
	@PostMapping("/getXZZCZBZB" )
	public R getXZZCZBZB(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//获取当前薪资薪资
		Double d1 = 0.0;
		Double d2 = 0.0;
		Double d3 = 0.0;
		Double d4 = 0.0;
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		map1.put("type",1);
		map1.put("term",term);
		Map resultMap1  = ctemployeeMapper.listCtstandardAllByType(map1);
		if(StringUtils.isEmpty(resultMap1)){
			d1 = 0.0;
		}else{
			d1 = resultMap1.get("xvalue")!=null?Double.parseDouble(resultMap1.get("xvalue").toString()):0.0;
		}


		//公积金社保
		Map map2 = new HashMap();
		map2.put("corpcode",corpcode);
		map2.put("term",term);
		Map resultMap2  = ctemployeeMapper.listGGJAndShebao(map2);
		if(StringUtils.isEmpty(resultMap2)){
			d2 = 0.0;
			d3 = 0.0;
		}else{
			//公积金
			d2 = resultMap2.get("accucorptotal")!=null?Double.parseDouble(resultMap2.get("accucorptotal").toString()):0.0;
			//社保
			d3 = resultMap2.get("bencorptotal")!=null?Double.parseDouble(resultMap2.get("bencorptotal").toString()):0.0;
		}

		//员工福利
		Map map3 = new HashMap();
		map3.put("corpcode",corpcode);
		map3.put("type",2);
		map3.put("term",term);
		Map resultMap3  = ctemployeeMapper.listCtstandardAllByType(map3);
		if(StringUtils.isEmpty(resultMap3)){
		}else{
			d4 = resultMap3.get("bencorptotal")!=null?Double.parseDouble(resultMap3.get("bencorptotal").toString()):0.0;
		}



		double dd1 =0.0;
		double dd2 =0.0;
		double dd3 =0.0;
		double dd4 =0.0;
		double dd = d1+d2+d3+d4;
		if(dd==0.0){


		}else{
			dd1 = new BigDecimal(d1/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			dd2 = new BigDecimal(d2/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			dd3 = new BigDecimal(d3/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			dd4 = new BigDecimal(d4/dd).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		Map resultmap1 = new HashMap();
		Map resultmap2 = new HashMap();
		Map resultmap3 = new HashMap();
		Map resultmap4 = new HashMap();
		resultmap1.put("title","工资");
		resultmap1.put("total",d1);
		resultmap1.put("rate",dd1*100+"%");

		resultmap2.put("title","公积金");
		resultmap2.put("total",d2);
		resultmap2.put("rate",dd2*100+"%");

		resultmap3.put("title","社保");
		resultmap3.put("total",d3);
		resultmap3.put("rate",dd3*100+"%");

		resultmap4.put("title","员工福利");
		resultmap4.put("total",d4);
		resultmap4.put("rate",dd4*100+"%");
		List resultList = new ArrayList(4);
		resultList.add(0,resultmap1);
		resultList.add(1,resultmap2);
		resultList.add(2,resultmap3);
		resultList.add(3,resultmap4);

		return  R.ok(resultList);

	}

	//部门人均工资排名
	@ApiOperation(value = "部门人均工资排名", notes = "部门人均工资排名")
	@PostMapping("/getBMRJGZ" )
	public R getBMRJGZ(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//获取当前薪资薪资
		Double d1 = 0.0;
		Double d2 = 0.0;
		Double d3 = 0.0;
		Double d4 = 0.0;
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		map1.put("term",term);
		List<LinkedHashMap> list1  = ctemployeeMapper.listBMRJGZ(map1);
		Map lmap = null;
		Integer depid = null;
		double factvalue = 0.0;
		Integer count = 0;
		Double rjgz = 0.0;
		Map mapcount = null;
		Otdepartment otdepartment = new Otdepartment();
		otdepartment.setCorpcode(corpcode);
		Map resultMap = new LinkedHashMap();
		String depname = "";
		List resultList = new ArrayList(list1.size());
		for(int i=0;i<list1.size();i++){
			depid = null;
			mapcount = null;
			factvalue = 0.0;
			count = 0;
			lmap = list1.get(i);
			factvalue = lmap.get("factvalue")!=null?Double.parseDouble(lmap.get("factvalue").toString()):0.00;
			depid = lmap.get("depid")!=null?Integer.parseInt(lmap.get("depid").toString()):null;
			otdepartment.setDepid(depid);
			depname = lmap.get("depname")!=null?lmap.get("depname").toString():"";
			mapcount = otdepartmentMapper.listOtdepartmentByCount(otdepartment);
			count = mapcount.get("depcount")!=null?Integer.parseInt(mapcount.get("depcount").toString()):0;
			rjgz = new BigDecimal(factvalue/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			resultMap.put("title",depname);
			resultMap.put("count",rjgz);
			resultList.add(i,resultMap);
		}

		return  R.ok(resultList);

	}

	//部门支出工资排名
	@ApiOperation(value = "部门支出工资排名", notes = "部门支出工资排名")
	@PostMapping("/getBMZCGZPM" )
	public R getBMZCGZPM(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		map1.put("term",term);
		List<LinkedHashMap> list1  = ctemployeeMapper.listBMZCGZPM(map1);
		return  R.ok(list1);

	}

	//岗位支出工资排名
	@ApiOperation(value = "岗位支出工资排名", notes = "岗位支出工资排名")
	@PostMapping("/getGWZCGZPM" )
	public R getGWZCGZPM(@RequestBody  Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		map1.put("term",term);
		List<LinkedHashMap> list1  = ctemployeeMapper.listGWZCGZPM(map1);
		return  R.ok(list1);

	}

	//近六个月工资趋势分析
	@ApiOperation(value = "近六个月工资趋势分析", notes = "近六个月工资趋势分析")
	@PostMapping("/getGZQSFX" )
	public R getGZQSFX(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		//map1.put("term",term);
		List<LinkedHashMap> list1  = ctemployeeMapper.listGZQSFX(map1);
		return  R.ok(list1);

	}

	//近六个月人力成本趋势分析
	@ApiOperation(value = "近六个月人力成本趋势分析", notes = "近六个月人力成本趋势分析")
	@PostMapping("/getRLCBQSFX" )
	public R getRLCBQSFX(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		//map1.put("term",term);
		List<LinkedHashMap> list1  = ctemployeeMapper.listRLCBQSFX(map1);
		return  R.ok(list1);

	}


	//近六个月计薪人数趋势分析
	@ApiOperation(value = "近六个月计薪人数趋势分析", notes = "近六个月计薪人数趋势分析")
	@PostMapping("/getJXRSQSFX" )
	public R getJXRSQSFX(@RequestBody Map pmap){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//工资支出
		String term = pmap.get("term").toString();
		Map map1 = new HashMap();
		map1.put("corpcode",corpcode);
		//map1.put("term",term);
		List<LinkedHashMap> list1  = ctemployeeMapper.listJXRSQSFX(map1);
		return  R.ok(list1);

	}


	/**
	 * 初始化
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "初始化", notes = "初始化")
	@SysLog("初始化")
	@PostMapping("/sysSPcInit")
	@Transactional
	public R sysSPcInit(@RequestBody  Map paramMap){

		//条件
		String sqlcondition = "";
		String sql = "";
		String curretTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		paramMap.put("userid", userid);
		if(StringUtils.isEmpty(paramMap.get("term"))){
			R.failed("薪资月份不能为空！");
		}
		String term = paramMap.get("term").toString();
		//2020-07-08
		String nyterm = term.substring(0,7);
		String yterm = term.substring(0,4);
		//查询薪资套
		Systpayrollgroup systpayrollgroup =null;
		QueryWrapper<Systpayrollgroup> payrollgroupqueryWrapper = new QueryWrapper<>();
		payrollgroupqueryWrapper.eq("corpid",pigxUser.getCorpid());
		systpayrollgroup = systpayrollgroupService.getOne(payrollgroupqueryWrapper);
		Integer gid = null;
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请维护薪资账套号！");
		}else{
			gid = systpayrollgroup.getId();
		}
		sqlcondition = systpayrollgroup.getSqlcondition();

		/*
		 *********进行初始化之前的一系列校验操********
		 */



		//账套对应期间已经封账
		QueryWrapper<Systpayrollgroupprocess> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpid",pigxUser.getCorpid());
		queryWrapper2.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper2.eq("ifnull(closed,0)",1);
		Systpayrollgroupprocess systpayrollgroupprocess2 = systpayrollgroupprocessService.getOne(queryWrapper2);
		if(!StringUtils.isEmpty(systpayrollgroupprocess2)){
			return R.failed("账套对应期间已经封账");
		}

		//新开的薪资区间必须在原始的薪资区间之后
		QueryWrapper<Systpayrollgroupprocess> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("corpid",pigxUser.getCorpid());
		queryWrapper3.lt("date_format( term, '%Y-%m' )",nyterm);
		Systpayrollgroupprocess systpayrollgroupprocess3 = systpayrollgroupprocessService.getOne(queryWrapper3);
		if(StringUtils.isEmpty(systpayrollgroupprocess3)){
			return R.failed("新开的薪资区间必须在原始的薪资区间之后");
		}

		//薪资数据已经签发
		QueryWrapper<Systpayrollgroupprocess> queryWrapper4 = new QueryWrapper<>();
		queryWrapper4.eq("corpid",pigxUser.getCorpid());
		queryWrapper4.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper4.eq("ifnull(confirm,0)",1);
		Systpayrollgroupprocess systpayrollgroupprocess4 = systpayrollgroupprocessService.getOne(queryWrapper4);
		if(!StringUtils.isEmpty(systpayrollgroupprocess4)){
			return R.failed("薪资数据已经签发");
		}

		//原始薪资区间还未封帐
		QueryWrapper<Systpayrollgroupprocess> queryWrapper5 = new QueryWrapper<>();
		queryWrapper5.eq("corpid",pigxUser.getCorpid());
		queryWrapper5.gt("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper5.eq("ifnull(closed,0)",0);
		Systpayrollgroupprocess systpayrollgroupprocess5 = systpayrollgroupprocessService.getOne(queryWrapper5);
		if(!StringUtils.isEmpty(systpayrollgroupprocess5)){
			return R.failed("原始薪资区间还未封帐");
		}

		//薪资累计期间未设置，无法初始化！
		QueryWrapper<CtcdPayrollperiod> queryWrapper6 = new QueryWrapper<>();
		queryWrapper6.eq("corpid",pigxUser.getCorpid());
		CtcdPayrollperiod ctcdPayrollperiod = ctcdPayrollperiodService.getOne(queryWrapper6);
		if(StringUtils.isEmpty(ctcdPayrollperiod)){
			return R.failed("薪资累计期间未设置，无法初始化！");
		}
		//薪资期间判断是否存在，不存在就创建,存在就更新
		String firstDay = DateUtils.getFirstDayOfGivenMonth(term);
		String lastDay = DateUtils.getLastDayOfMonth(term);
		QueryWrapper<Systpayrollgroupprocess> systpayrollgroupprocessQueryWrapper = new QueryWrapper<>();
		systpayrollgroupprocessQueryWrapper.eq("corpid",pigxUser.getCorpid());
		Systpayrollgroupprocess systpayrollgroupprocess = systpayrollgroupprocessService.getOne(systpayrollgroupprocessQueryWrapper);
		if(StringUtils.isEmpty(systpayrollgroupprocess)){
			systpayrollgroupprocess = new Systpayrollgroupprocess();
			systpayrollgroupprocess.setCorpid(corpid);
			systpayrollgroupprocess.setCorpcode(corpcode);
			systpayrollgroupprocess.setTerm(firstDay);
			systpayrollgroupprocess.setBegindate(firstDay);
			systpayrollgroupprocess.setEnddate(lastDay);
			systpayrollgroupprocess.setTitle(systpayrollgroup.getTitle());
			systpayrollgroupprocess.setGid(gid);
			systpayrollgroupprocessService.save(systpayrollgroupprocess);
		}else{
			UpdateWrapper<Systpayrollgroupprocess> systpayrollgroupprocessUpdateWrapper = new UpdateWrapper<>();
			Systpayrollgroupprocess systpayrollgroupprocess1 = new Systpayrollgroupprocess();
			systpayrollgroupprocess1.setTerm(firstDay);
			systpayrollgroupprocess1.setBegindate(firstDay);
			systpayrollgroupprocess1.setEnddate(lastDay);
			systpayrollgroupprocessUpdateWrapper.eq("corpid",corpid);
			systpayrollgroupprocessService.update(systpayrollgroupprocess1,systpayrollgroupprocessUpdateWrapper);
		}
		/*
		 *********校验结束：进行初始化之前的一系列校验操********
		 */


		//生成累积计税区间
		//SELECT 1 FROM ctCD_DecSumPeriods WHERE TIMESTAMPDIFF(YEAR,Term,_Term)=0 AND GID=2 AND IFNULL(CorpID,0)=12
		QueryWrapper<CtcdDecsumperiods> queryWrapper7 = new QueryWrapper<>();
		queryWrapper7.eq("corpid",pigxUser.getCorpid());
		queryWrapper7.eq("date_format( term, '%Y' )",yterm);
		CtcdDecsumperiods ctcdDecsumperiods  =ctcdDecsumperiodsService.getOne(queryWrapper7);
		Integer startMonth = null;
		Integer monFlag = null;
		String preYear = DateUtils.getNextYear(firstDay,-1);
		String smonFlag = "";
		String begindate = "";
		String nextYear = DateUtils.getNextYear(firstDay,1);
		String enddate = DateUtils.getMonthPlus(firstDay,11);
		String enddate2 = DateUtils.getMonthPlus(nextYear,11);
		String begindate2 = DateUtils.getFirstDayOfGivenMonth(nextYear);
		if(StringUtils.isEmpty(ctcdDecsumperiods)){
			startMonth = ctcdPayrollperiod.getStartmonth();
			monFlag = ctcdPayrollperiod.getMonflag();
			if(startMonth<10){
				smonFlag = "0"+startMonth;
			}else{
				smonFlag = ""+startMonth;
			}
			if(monFlag==1){
				begindate = preYear.substring(0,4) +"-"+smonFlag+"-01";
			}else{
				begindate = term.substring(0,4) +"-"+smonFlag+"-01";
			}
			CtcdDecsumperiods ctcdDecsumperiods1 = new CtcdDecsumperiods();
			ctcdDecsumperiods1.setCorpcode(corpcode);
			ctcdDecsumperiods1.setGid(gid);
			ctcdDecsumperiods1.setCorpid(corpid);
			ctcdDecsumperiods1.setTerm(firstDay);
			ctcdDecsumperiods1.setBegindate(begindate);
			ctcdDecsumperiods1.setInitialized(1);
			ctcdDecsumperiods1.setInitializedby(userid);
			ctcdDecsumperiods1.setInitializedtime(curretTime);
			ctcdDecsumperiods1.setEnddate(enddate);
			ctcdDecsumperiodsService.save(ctcdDecsumperiods1);
		}
		//如果薪资期间大于最大累计期间，就初始化下个期间的数据
		sql = "select max(EndDate) enddate from ctCD_DecSumPeriods Where corpid="+corpid;
		Map decsumperiod = systpaystditemMapper.listSalaryTemplate2(sql);
		String decsumperiodEnd = decsumperiod.get("enddate").toString();
		if(firstDay.compareTo(decsumperiodEnd)>0){
			if(StringUtils.isEmpty(ctcdDecsumperiods)){
				startMonth = ctcdPayrollperiod.getStartmonth();
				monFlag = ctcdPayrollperiod.getMonflag();
				if(startMonth<10){
					smonFlag = "0"+startMonth;
				}else{
					smonFlag = ""+startMonth;
				}
				if(monFlag==1){
					begindate = nextYear.substring(0,4) +"-"+smonFlag+"-01";
				}else{
					begindate = nextYear.substring(0,4) +"-"+smonFlag+"-01";
				}
				CtcdDecsumperiods ctcdDecsumperiods1 = new CtcdDecsumperiods();
				ctcdDecsumperiods1.setCorpcode(corpcode);
				ctcdDecsumperiods1.setCorpid(corpid);
				ctcdDecsumperiods1.setTerm(nextYear);
				ctcdDecsumperiods1.setBegindate(begindate2);
				ctcdDecsumperiods1.setInitialized(1);
				ctcdDecsumperiods1.setInitializedby(userid);
				ctcdDecsumperiods1.setInitializedtime(curretTime);
				ctcdDecsumperiods1.setEnddate(enddate2);
				ctcdDecsumperiodsService.save(ctcdDecsumperiods1);
			}
		}

		sql = "";

		//初始化前------------------------调用存储过程

		sql = "DROP  TABLE IF EXISTS t_systPayrollEmpInfo  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctAttend  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctPerformanceBonus  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_systPayrollEmpInfo2  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctAttend2  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctPerformanceBonus2  ";
		systpaystditemMapper.listSalaryTemplate3(sql);



		//生成人员表
		sql = "CREATE temporary TABLE t_systPayrollEmpInfo as select corpid,corpcode,gid,term,eid,badge,name,compid,depid,jobid,empgrade,joindate,costid,salarycity,remark FROM systPayrollEmpInfo ";
		sql = sql + " where corpid = "+corpid ;
		if(!StringUtils.isEmpty(sqlcondition)){
			sql = sql + " and  eid in("+sqlcondition+")";
		}
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "CREATE temporary TABLE t_systPayrollEmpInfo2 as select corpid,corpcode,gid,term,eid,badge,name,compid,depid,jobid,empgrade,joindate,costid,salarycity,remark FROM systPayrollEmpInfo ";
		sql = sql + " where corpid = "+corpid ;
		if(!StringUtils.isEmpty(sqlcondition)){
			sql = sql + " and  eid in("+sqlcondition+")";
		}
		systpaystditemMapper.listSalaryTemplate3(sql);



		sql = "INSERT INTO t_systPayrollEmpInfo(CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,JoinDate,EmpGrade,CostID,SalaryCity) ";
		sql = sql + " select "+corpid+",a.corpcode,"+gid+",'"+firstDay+"',a.EID,a.Badge,a.Name,a.CompID,a.DepID,a.JobID,a.JoinDate,a.EmpGrade,b.CostID,b.SalaryCity";
		sql = sql + " FROM etEmployee a Inner Join ctEmployee b On a.EID=b.EID ";
		sql = sql + " where a.corpid = "+corpid +" and b.corpid = "+corpid;
		if(!StringUtils.isEmpty(sqlcondition)){
			sql = sql + " and a.eid in("+sqlcondition+")";
		}
		sql = sql + " and a.EID Not In (Select EID From t_systPayrollEmpInfo2) ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		//
		sql = "DROP  TABLE IF EXISTS t_systPayrollEmpInfo2  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "UPDATE t_systPayrollEmpInfo a,ctEmployee b,etEmployee c " +
				" SET a.Term='" + term+"'"+
				",a.SalaryCity=b.SalaryCity,a.CostID=b.CostID,a.Name=c.Name,a.DepID=c.DepID,a.CompID=c.CompID,"+
				" a.JobID=c.JobID,a.EmpGrade=c.EmpGrade "+
				" where a.EID=b.EID and a.EID=c.EID ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "DELETE FROM systPayrollEmpInfo Where corpid = "+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成薪资计算人员
		sql = "INSERT INTO systPayrollEmpInfo(Badge,CompID,CorpCode,CorpID,CostID,DepID,EID,EmpGrade,GID,JobID,JoinDate,Name,Remark,SalaryCity,Term) "+
				" SELECT Badge,CompID,CorpCode,CorpID,CostID,DepID,EID,EmpGrade,GID,JobID,JoinDate,Name,Remark,SalaryCity,Term "+
				" FROM t_systPayrollEmpInfo";
		systpaystditemMapper.listSalaryTemplate3(sql);
		// 删除缺少的人员 输入
		sql = "Delete From systPayItemInputValue a "+
				"Where a.CorpID="+corpid+
				" And a.EID Not In(Select EID from t_systPayrollEmpInfo)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		// 删除缺少的人员 输出
		sql = "Delete From systPayItemFactValue a "+
				"Where a.CorpID="+corpid+
				" And a.EID Not In(Select EID from t_systPayrollEmpInfo)";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成考勤接口人员
		sql ="CREATE temporary TABLE t_ctAttend as Select CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,EmpGrade,JoinDate,Initialized,InitializedBy,InitializedTime,Remark " +
				" From ctAttend where corpid = " + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql ="CREATE temporary TABLE t_ctAttend2 as Select CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,EmpGrade,JoinDate,Initialized,InitializedBy,InitializedTime,Remark " +
				" From ctAttend where corpid = " + corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Insert Into t_ctAttend(CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,EmpGrade,JoinDate) " +
				" Select corpid,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,EmpGrade,JoinDate " +
				" From t_systPayrollEmpInfo a " +
				" Where a.EID Not In (Select EID From t_ctAttend2)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctAttend2  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Delete from ctAttend Where CorpID="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Insert Into ctAttend(CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,EmpGrade,JoinDate,Initialized,InitializedBy,InitializedTime,Remark) " +
				" SELECT CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,EmpGrade,JoinDate,Initialized,InitializedBy,InitializedTime,Remark " +
				" FROM t_ctAttend ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除缺少的人员
		sql = "Delete From ctAttendItemFactValue a " +
				" Where a.CorpID="+corpid+
				" And a.EID Not In(Select EID from t_ctAttend) ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成绩效接口人员
		sql = "CREATE temporary TABLE t_ctPerformanceBonus as Select CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,JobType,pTerm,Initialized,InitializedBy,InitializedTime,Remark " +
				" From ctPerformanceBonus " +
				" Where CorpID="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "CREATE temporary TABLE t_ctPerformanceBonus2 as Select CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,JobType,pTerm,Initialized,InitializedBy,InitializedTime,Remark " +
				" From ctPerformanceBonus " +
				" Where CorpID="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);


		sql = "INSERT INTO t_ctPerformanceBonus(CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,JobType,pTerm) " +
				" SELECT a.CorpID,a.corpcode,a.GID,a.Term,a.EID,a.Badge,a.Name,a.CompID,a.DepID,a.JobID,b.JobType,1 " +
				" FROM t_systPayrollEmpInfo a,eVW_Employee b " +
				" WHERE a.EID Not In (Select EID From t_ctPerformanceBonus2) AND a.EID=b.EID  and b.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctPerformanceBonus2  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Delete from ctPerformanceBonus Where CorpID="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "INSERT INTO ctPerformanceBonus(CorpID,corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,JobType,pTerm,Initialized,InitializedBy,InitializedTime,Remark) " +
				" SELECT IFNULL(CorpID,0),corpcode,GID,Term,EID,Badge,Name,CompID,DepID,JobID,JobType,pTerm,Initialized,InitializedBy,InitializedTime,Remark " +
				" FROM t_ctPerformanceBonus ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除缺少的人员
		sql = "Delete From ctPerformanceItemFactValue a " +
				" Where corpid = "+corpid+
				" And a.EID Not In(Select EID from t_ctPerformanceBonus) ";
		systpaystditemMapper.listSalaryTemplate3(sql);


		sql = "DROP  TABLE IF EXISTS t_systPayrollEmpInfo  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctAttend  ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "DROP  TABLE IF EXISTS t_ctPerformanceBonus  ";
		systpaystditemMapper.listSalaryTemplate3(sql);


		//调用初始化后存储过程
		//薪资薪资期间状态
/*		UpdateWrapper<Systpayrollgroupprocess> updateWrapper = new UpdateWrapper<>();
		Systpayrollgroupprocess systpayrollgroupprocess1 = new Systpayrollgroupprocess();
		systpayrollgroupprocess1.setInitialized(1);
		systpayrollgroupprocess1.setInitializedby(userid);
		systpayrollgroupprocess1.setInitializedtime(curretTime);
		updateWrapper.eq("corpid",corpid);
		updateWrapper.eq("date_format( term, '%Y-%m' )",nyterm);
		systpayrollgroupprocessService.update(systpayrollgroupprocess1,updateWrapper);*/
		sql = "update systpayrollgroupprocess set Initialized=1,InitializedBy="+userid
				+",InitializedTime='"+curretTime.substring(0,10)+"',Submit=null,SubmitBy=null,SubmitTime=null,Confirm=null,ConfirmBy=null,ConfirmTime=null,Closed=null,ClosedBy=null,ClosedTime=null where corpid="+corpid+" and date_format( term, '%Y-%m' )='"+nyterm+"'";
		systpaystditemMapper.listSalaryTemplate3(sql);



		return R.ok("初始化成功！");
	}

	/**
	 * 计算
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "计算", notes = "计算")
	@SysLog("计算")
	@PostMapping("/sysSPcCalc")
	@Transactional(rollbackFor = Exception.class)
	public R sysSPcCalc(@RequestBody  Map paramMap){
		String curretTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		Integer corpid = pigxUser.getCorpid();
		paramMap.put("userid", userid);
		String term = paramMap.get("term").toString();
		//2020-07-08
		String nyterm = term.substring(0,7);
		String firstDay = DateUtils.getFirstDayOfGivenMonth(term);
		String lastDay = DateUtils.getLastDayOfMonth(term);
		//薪资期间不能为空
		QueryWrapper<Systpayrollgroupprocess> systpayrollgroupprocessQueryWrapper = new QueryWrapper<>();
		systpayrollgroupprocessQueryWrapper.eq("corpid",pigxUser.getCorpid());
		Systpayrollgroupprocess systpayrollgroupprocess = systpayrollgroupprocessService.getOne(systpayrollgroupprocessQueryWrapper);
		if(StringUtils.isEmpty(systpayrollgroupprocess)){
			return R.failed("薪资期间不能为空");
		}
		String term2= systpayrollgroupprocess.getTerm();
		if(StringUtils.isEmpty(term2)){
			return R.failed("薪资期间不能为空");
		}
		//账套对应期间已经封账
		QueryWrapper<Systpayrollgroupprocess> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpid",pigxUser.getCorpid());
		queryWrapper2.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper2.eq("ifnull(closed,0)",1);
		Systpayrollgroupprocess systpayrollgroupprocess2 = systpayrollgroupprocessService.getOne(queryWrapper2);
		if(!StringUtils.isEmpty(systpayrollgroupprocess2)){
			return R.failed("账套对应期间已经封账");
		}
		//账套对应期间还没初始化
		QueryWrapper<Systpayrollgroupprocess> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("corpid",pigxUser.getCorpid());
		queryWrapper3.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper3.eq("ifnull(initialized,0)",0);
		Systpayrollgroupprocess systpayrollgroupprocess3 = systpayrollgroupprocessService.getOne(queryWrapper3);
		if(!StringUtils.isEmpty(systpayrollgroupprocess3)){
			return R.failed("账套对应期间还没初始化");
		}
		//当前薪资套还未初始化
		QueryWrapper<Systpayrollgroupprocess> queryWrapper4 = new QueryWrapper<>();
		queryWrapper4.eq("corpid",pigxUser.getCorpid());
		queryWrapper4.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper4.eq("ifnull(initialized,0)",1);
		Systpayrollgroupprocess systpayrollgroupprocess4 = systpayrollgroupprocessService.getOne(queryWrapper4);
		if(StringUtils.isEmpty(systpayrollgroupprocess4)){
			return R.failed("当前薪资套还未初始化");
		}
		//薪资数据已经签发
		QueryWrapper<Systpayrollgroupprocess> queryWrapper5 = new QueryWrapper<>();
		queryWrapper5.eq("corpid",pigxUser.getCorpid());
		queryWrapper5.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper5.eq("ifnull(confirm,0)",1);
		Systpayrollgroupprocess systpayrollgroupprocess5 = systpayrollgroupprocessService.getOne(queryWrapper5);
		if(!StringUtils.isEmpty(systpayrollgroupprocess5)){
			return R.failed("薪资数据已经签发");
		}
		//当前薪资套已经封帐
		QueryWrapper<Systpayrollgroupprocess> queryWrapper6 = new QueryWrapper<>();
		queryWrapper6.eq("corpid",pigxUser.getCorpid());
		queryWrapper6.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper6.eq("ifnull(closed,0)",1);
		Systpayrollgroupprocess systpayrollgroupprocess6 = systpayrollgroupprocessService.getOne(queryWrapper6);
		if(!StringUtils.isEmpty(systpayrollgroupprocess6)){
			return R.failed("当前薪资套已经封帐");
		}


		Systpayrollgroup systpayrollgroup =null;
		QueryWrapper<Systpayrollgroup> payrollgroupqueryWrapper = new QueryWrapper<>();
		payrollgroupqueryWrapper.eq("corpid",pigxUser.getCorpid());
		systpayrollgroup = systpayrollgroupService.getOne(payrollgroupqueryWrapper);
		Integer gid = null;
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请维护薪资账套号！");
		}else{
			gid = systpayrollgroup.getId();
		}

		//需要判断考勤是否签发

		/*
				sysSPcCalcBefore1****开始处理社保******
		 */


		String sql99 = "";
		sql99 = "delete from ctBenCalc where corpid = "+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);
		sql99 = " Insert into ctBenCalc(EID,Term,CorpID,GID,Badge,Name,CompID,DepID,JobID,BenStatus,BenCity,BenArea,BearType,BenBase, " +
				" IsAccu,AccuBase,IsPens,PensBase,PensBase_Self,IsMdcl,MdclBase,MdclBase_Self,IsUnem,UnemBase,UnemBase_Self, " +
				" IsBabs,BabsBase,IsInjs,InjsBase,IsSMdcl,SMdclBase,SMdclBase_Self,IsSuAccu,SuAccuBase,IsSuMdcl,SuMdclBase, " +
				" IsSuPen,SuPenBase) " +
				" Select a.EID,'" +
				firstDay +
				"'," +corpid+
				"," +gid+
				",a.Badge,a.Name,a.CompID,a.DepID,a.JobID,a.BenStatus,a.BenCity,a.BenArea,a.BearType,a.BenBase, " +
				" a.IsAccu,a.AccuBase,a.IsPens,a.PensBase,a.PensBase_Self,a.IsMdcl,a.MdclBase,a.MdclBase_Self,a.IsUnem,a.UnemBase,a.UnemBase_Self, " +
				" a.IsBabs,a.BabsBase,a.IsInjs,a.InjsBase,a.IsSMdcl,a.SMdclBase,a.SMdclBase_Self,a.IsSuAccu,a.SuAccuBase,a.IsSuMdcl,a.SuMdclBase, " +
				" a.IsSuPen,a.SuPenBase " +
				" From ctBenefitStatus a" +
				//" ,systPayItemInputValue b " +
				" ,systpayrollempinfo b " +

				" Where IFNULL(a.BenStatus,0)=1 And a.EID=b.EID And b.GID=" +gid +" and a.corpid="+corpid+
				" And IFNULL(b.CorpID,0)=" +corpid+
				" And IFNULL(a.CorpID,0)=" +corpid+
				" And date_format( b.term, '%Y-%m' )='" +firstDay.substring(0,7)+
				"' ";
		systpaystditemMapper.listSalaryTemplate3(sql99);


		//公积金
		sql99 ="Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(a.AccuBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.AccuBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.AccuBase,0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As Accu_Self, " +
				" sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.AccuBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.AccuBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.AccuBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As Accu_Corp " +
				" " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsAccu,0)=1 And a.BenArea=b.BenArea And b.BenType=1 " +
				"  and a.corpid="+corpid+" and b.corpid="+corpid+ " and a.gid="+gid+
				" ) y " +
				" Set x.Accu_Self=y.Accu_Self,x.Accu_Corp=y.Accu_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		//养老保
		sql99= "Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(IFNULL(a.PensBase_Self,a.PensBase),0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(IFNULL(a.PensBase_Self,a.PensBase),0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(IFNULL(a.PensBase_Self,a.PensBase),0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As Pen_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.PensBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.PensBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.PensBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As Pen_Corp " +
				" " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsPens,0)=1 And a.BenArea=b.BenArea And b.BenType=5 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" ) y " +
				" Set x.Pen_Self=y.Pen_Self,x.Pen_Corp=y.Pen_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		//医疗保险
		sql99 = "Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(IFNULL(a.MdclBase_Self,a.MdclBase),0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(IFNULL(a.MdclBase_Self,a.MdclBase),0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(IFNULL(a.MdclBase_Self,a.MdclBase),0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As Mdcl_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.MdclBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.MdclBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.MdclBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As Mdcl_Corp " +
				" " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsMdcl,0)=1 And a.BenArea=b.BenArea And b.BenType=4 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" )y  Set x.Mdcl_Self=y.Mdcl_Self,x.Mdcl_Corp=y.Mdcl_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);


		//失业保险
		sql99 = "Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(IFNULL(a.UnemBase_Self,a.UnemBase),0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(IFNULL(a.UnemBase_Self,a.UnemBase),0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(IFNULL(a.UnemBase_Self,a.UnemBase),0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As Uemp_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0)," +
				" (Case When IFNULL(a.UnemBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.UnemBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0)" +
				" else IFNULL(a.UnemBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As Uemp_Corp " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsUnem,0)=1 And a.BenArea=b.BenArea And b.BenType=6 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" )y Set x.Uemp_Self=y.Uemp_Self,x.Uemp_Corp=y.Uemp_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		//生育保险
		sql99 ="Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(a.BabsBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.BabsBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.BabsBase,0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As Baby_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.BabsBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.BabsBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.BabsBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As Baby_Corp " +
				" " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsBabs,0)=1 And a.BenArea=b.BenArea And b.BenType=2 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" )y" +
				" Set x.Baby_Self=y.Baby_Self,x.Baby_Corp=y.Baby_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		// 工伤保险
		sql99 = " Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(a.InjsBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.InjsBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.InjsBase,0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As Injury_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.InjsBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.InjsBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.InjsBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As Injury_Corp " +
				" " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsInjs,0)=1 And a.BenArea=b.BenArea And b.BenType=3 "+ " and a.corpid="+corpid + " and b.corpid="+corpid+
				" )y " +
				" Set x.Injury_Self=y.Injury_Self,x.Injury_Corp=y.Injury_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);
		//大病保险
		sql99 = "Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(IFNULL(a.SMdclBase_Self,a.SMdclBase),0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(IFNULL(a.SMdclBase_Self,a.SMdclBase),0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(IFNULL(a.SMdclBase_Self,a.SMdclBase),0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As SMdcl_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.SMdclBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.SMdclBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.SMdclBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As SMdcl_Corp " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsSMdcl,0)=1 And a.BenArea=b.BenArea And b.BenType=7 "  + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" )y " +
				" Set x.SMdcl_Self=y.SMdcl_Self,x.SMdcl_Corp=y.SMdcl_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		//补充公积金
		sql99 = "Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(a.suAccuBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.suAccuBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.suAccuBase,0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As SuAcc_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.suAccuBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.suAccuBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.suAccuBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As SuAcc_Corp " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsSuAccu,0)=1 And a.BenArea=b.BenArea And b.BenType=8 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" ) y Set x.SuAcc_Self=y.SuAcc_Self,x.SuAcc_Corp=y.SuAcc_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);


		//补充医疗保险
		sql99 = "Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(a.SuMdclBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.SuMdclBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.SuMdclBase,0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As SuMdcl_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.SuMdclBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.SuMdclBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.SuMdclBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As SuMdcl_Corp " +
				" " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsSuAccu,0)=1 And a.BenArea=b.BenArea And b.BenType=9 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" ) y " +
				" Set x.SuMdcl_Self=y.SuMdcl_Self,x.SuMdcl_Corp=y.SuMdcl_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);


		//补充养老保险
		sql99 = " Update ctBenCalc x, " +
				" (Select a.EID,sysFN_Round(IFNULL(b.Digital,0),IFNULL(b.Self_Mantissa,0), " +
				" (Case When IFNULL(a.SuPenBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.SuPenBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.SuPenBase,0) End)*b.Self_Rate+IFNULL(b.Self_AddNum,0) " +
				" ) As SuPen_Self,sysFN_Round(IFNULL(b.CompDigital,0),IFNULL(b.Comp_Mantissa,0), " +
				" (Case When IFNULL(a.SuPenBase,0)>IFNULL(b.MaxValue,0) Then IFNULL(b.MaxValue,0) " +
				" When IFNULL(a.SuPenBase,0)<IFNULL(b.MinValue,0) Then IFNULL(b.MinValue,0) " +
				" else IFNULL(a.SuPenBase,0) End)*b.Comp_Rate+IFNULL(b.Comp_AddNum,0) " +
				" ) As SuPen_Corp " +
				" From ctBenefitStatus a,ctCD_BenRate b " +
				" Where IFNULL(a.BenStatus,0)=1 And IFNULL(a.IsSuAccu,0)=1 And a.BenArea=b.BenArea And b.BenType=9 " + " and a.corpid="+corpid + " and b.corpid="+corpid+
				" ) y Set x.SuPen_Self=y.SuPen_Self,x.SuPen_Corp=y.SuPen_Corp " +
				" Where x.EID=y.EID And x.GID="+gid+" and x.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);


		//公积金补缴
		sql99 = "Update ctBenCalc a,ctBenPayBack_Register b " +
				" Set a.AccuBuCorp=Round(IFNULL(b.AccuCompPayBack,0.0),2), " +
				" a.AccuBuSelf=Round(IFNULL(b.AccuSelfPayBack,0.0),2) " +
				" Where a.EID=b.EID And a.GID="+gid+" and a.corpid="+corpid +" and b.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		sql99 = "Update ctBenCalc a,ctBenPayBack_Register b " +
				" Set a.BeneBuCorp=Round(IFNULL(b.BenCompPayBack,0.0),2), " +
				" a.BeneBuSelf=Round(IFNULL(b.BenSelfPayBack,0.0),2) " +
				" Where a.EID=b.EID And a.GID="+gid+" and a.corpid="+corpid+" and b.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		sql99 = "Update ctBenCalc a " +
				" Set a.BenCorp_Total=Round(IFNULL(a.Pen_Corp,0.0)+IFNULL(a.Mdcl_Corp,0.0)+IFNULL(a.Uemp_Corp,0.0)+IFNULL(a.Baby_Corp,0.0)+IFNULL(a.Injury_Corp,0.0) " +
				" +IFNULL(a.Inss_Corp,0.0)+IFNULL(a.SMdcl_Corp,0.0)+IFNULL(a.BeneBuCorp,0.0),2) " +
				" Where a.GID="+gid+" and a.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		sql99 = " Update ctBenCalc a " +
				" Set a.BenSelf_Total=Round(IFNULL(a.Pen_Self,0.0)+IFNULL(a.Mdcl_Self,0.0)+IFNULL(a.Uemp_Self,0.0)+IFNULL(a.Baby_Self,0.0)+IFNULL(a.Injury_Self,0.0) " +
				" +IFNULL(a.Inss_Self,0.0)+IFNULL(a.SMdcl_Self,0.0)+IFNULL(a.BeneBuSelf,0.0),2) " +
				" Where a.GID="+gid+" and a.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);

		sql99 = "Update ctBenCalc a " +
				" Set a.AccuSelf_Total=Round(IFNULL(a.Accu_Self,0.0)+IFNULL(a.SuAcc_Self,0.0)+IFNULL(a.AccuBuSelf,0.0),2) " +
				" Where a.GID="+gid+" and a.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);
		sql99="Update ctBenCalc a " +
				" Set a.AccuCorp_Total=Round(IFNULL(a.Accu_Corp,0.0)+IFNULL(a.SuAcc_Corp,0.0)+IFNULL(a.AccuBuCorp,0.0),2) " +
				" Where a.GID="+gid+" and a.corpid="+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql99);


		/*
		 ****结束处理社保******
		 */
		String sql00 = "";
		sql00 ="DROP  TABLE IF EXISTS t_TempTable";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 ="DROP  TABLE IF EXISTS t_TempTable2";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_TempColumn";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00="DROP  TABLE IF EXISTS t_TempPayrollFormula";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayrollEmpInfo";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayItemInputValue";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayItemFactValue";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_ctAttendItem";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayStdItem";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_ctPerformanceItem";
		systpaystditemMapper.listSalaryTemplate3(sql00);




		//t_systPayItemInputValue



		sql00 = "CREATE TEMPORARY TABLE t_TempTable" +
				"(" +
				"ID INT AUTO_INCREMENT PRIMARY KEY," +
				"TableName varchar(200)," +
				"IsSys INT" +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql00);

		sql00 = "CREATE TEMPORARY TABLE t_TempTable2" +
				"(" +
				"ID INT AUTO_INCREMENT PRIMARY KEY," +
				"TableName varchar(200)," +
				"IsSys INT" +
				")";
		systpaystditemMapper.listSalaryTemplate3(sql00);

		sql00="CREATE TEMPORARY TABLE t_TempColumn(ID INT AUTO_INCREMENT PRIMARY KEY,ColumnName varchar(200))";
		systpaystditemMapper.listSalaryTemplate3(sql00);

		sql00="CREATE TEMPORARY TABLE t_TempPayrollFormula(ID INT AUTO_INCREMENT PRIMARY KEY,GID Int,PayItem Int,Name varchar(200),Step Int,SQLFormula Text,SQLCondition varchar(3000),Digital varchar(10),DigitalType Int,xMinValue varchar(3000),xMaxValue varchar(3000),DefValue varchar(3000))";
		systpaystditemMapper.listSalaryTemplate3(sql00);


		//薪资人员表
		String sql01 = "CREATE temporary TABLE t_systPayrollEmpInfo as SELECT corpid,id,term,eid,badge,gid,name,compid,depid,jobid,empgrade,joindate,costid,salarycity,remark FROM systPayrollEmpInfo where CorpID= "+corpid +
				" AND date_format( term, '%Y-%m' )='" + term+ "'";
		systpaystditemMapper.listSalaryTemplate3(sql01);



		//输入项
		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpid(corpid);
		systpayrollitem.setIftype(1);
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));
		//输出项
		Systpayrollitem systpayrollitem2 = new Systpayrollitem();
		systpayrollitem2.setCorpid(corpid);
		systpayrollitem2.setIftype(0);
		List<Systpayrollitem> systpayrollitemList2 = systpayrollitemService.list(Wrappers.query(systpayrollitem2).orderByAsc("xorder"));
		//基本薪资
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpid(corpid);
		List systpaystditemList = systpaystditemService.list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		//考勤
		Ctattenditem ctattenditem = new Ctattenditem();
		ctattenditem.setCorpid(corpid);
		List ctattenditemList = ctattenditemService.list(Wrappers.query(ctattenditem).orderByAsc("xorder"));

		Ctperformanceitem ctperformanceitem = new Ctperformanceitem();
		ctperformanceitem.setCorpid(corpid);
		List ctperformanceitemList = ctperformanceitemService.list(Wrappers.query(ctperformanceitem).orderByAsc("xorder"));
		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.eid ";
		String sql2 = "(select EID ";
		String sql3 = "(select a.eid ";

		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +colName;

			sql2 = sql2 + " ,case when payitem = "+paystdItemID +" then factvalue else '' end "+colName;
			sql3 = sql3 + ","+"max(a."+colName+")"+"  "+colName;

		}

		sql1 = sql1+" from systpayrollempinfo A1 ";

		sql2 = sql2  +"  from systpayiteminputvalue  Where  corpid = '" + corpid +"'"+" and date_format( term, '%Y-%m' )='" + nyterm+ "'"+") a group by eid ) A3 on A1.EID = A3.EID ";
		sql3 = sql3 + " from " +sql2;


		sql = sql1 + " left join "+sql3;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpid =" + corpid;

		sql ="CREATE temporary TABLE t_systPayItemInputValue as "+ sql ;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//输出项
		item = null;
		item2 = null;
		colName = "";
		title = "";
		paystdItemID = null;
		sql = "";
		sql1 = "select  A1.eid ";
		sql2 = "(select EID ";
		sql3 = "(select a.eid ";

		for(int i=0;i<systpayrollitemList2.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList2.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +colName;

			sql2 = sql2 + " ,case when payitem = "+paystdItemID +" then factvalue else '' end "+colName;
			sql3 = sql3 + ","+" max(a."+colName+")"+"  "+colName;

		}

		sql1 = sql1+" from systpayrollempinfo A1 ";

		sql2 = sql2  +"  from systpayitemfactvalue  Where  corpid = '" + corpid +"'"+" and date_format( term, '%Y-%m' )='" + nyterm+ "'"+")  a group by eid ) A3 on A1.EID = A3.EID ";

		sql3 = sql3 + " from " +sql2;

		sql = sql1 + " left join "+sql3;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpid =" + corpid;

		sql ="CREATE temporary TABLE t_systPayItemFactValue as "+ sql ;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//基本薪资
		item = null;
		item2 = null;
		colName = "";
		title = "";
		paystdItemID = null;
		sql = "";
		sql1 = "select  A1.eid ";
		sql2 = "(select EID ";
		sql3 = "(select a.eid ";

		for(int i=0;i<systpaystditemList.size();i++){
			item = (Systpaystditem)systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			sql1 = sql1 + "," + "A3." +colName;

			sql2 = sql2 + " ,case when PayStdItemID = "+paystdItemID +" then xvalue else '' end "+colName;
			sql3 = sql3 + ","+" max(a." +colName+") "+"  "+colName;

		}

		sql1 = sql1+" from systpayrollempinfo A1 ";

		sql2 = sql2  +"  from ctstandard  Where  corpid = " + corpid  + ") a group by eid ) A3 on A1.EID = A3.EID ";
		sql3 = sql3 + " from " +sql2;

		sql = sql1 + " left join "+sql3;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpid =" + corpid;

		sql ="CREATE temporary TABLE t_systPayStdItem as "+ sql ;
		systpaystditemMapper.listSalaryTemplate3(sql);





		//考勤项
		Ctattenditem ctattenditem1 = null;
		colName = "";
		title = "";
		paystdItemID = null;
		sql = "";
		sql1 = "select  A1.eid ";
		sql2 = "(select EID ";
		sql3 = "(select a.eid ";

		for(int i=0;i<ctattenditemList.size();i++){
			ctattenditem1 = (Ctattenditem)ctattenditemList.get(i);
			colName = ctattenditem1.getColname();
			title = ctattenditem1.getTitle();
			paystdItemID = ctattenditem1.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +colName;

			sql2 = sql2 + " ,case when AttendItem = "+paystdItemID +" then FactValue else '' end "+colName;
			sql3 = sql3 + ","+" max(a."+colName+") "+colName;

		}

		sql1 = sql1+" from systpayrollempinfo A1 ";

		sql2 = sql2  +"  from ctattenditemfactvalue  Where  corpid = '" + corpid +"'"+" and date_format( term, '%Y-%m' )='" + nyterm+ "'"+") a group by eid ) A3 on A1.EID = A3.EID ";
		sql3 = sql3 +" from "+sql2;
		sql = sql1 + " left join "+sql3;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpid =" + corpid;

		sql ="CREATE temporary TABLE t_ctAttendItem as "+ sql ;
		systpaystditemMapper.listSalaryTemplate3(sql);


		//绩效
		Ctperformanceitem ctperformanceitem1 = null;
		colName = "";
		title = "";
		paystdItemID = null;
		sql = "";
		sql1 = "select  A1.eid ";
		sql2 = "(select EID ";
		sql3 = "(select a.eid ";

		for(int i=0;i<ctperformanceitemList.size();i++){
			ctperformanceitem1 = (Ctperformanceitem)ctperformanceitemList.get(i);
			colName = ctperformanceitem1.getColname();
			title = ctperformanceitem1.getTitle();
			paystdItemID = ctperformanceitem1.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +colName;

			sql2 = sql2 + " ,case when PerformanceItem = "+paystdItemID +" then FactValue else '' end "+colName;
			sql3 = sql3 + ","+"max(a." +colName+")"+"  "+colName;

		}

		sql1 = sql1+" from systpayrollempinfo A1 ";

		sql2 = sql2  +"  from ctperformanceitemfactvalue  Where  corpid = '" + corpid +"'"+" and date_format( term, '%Y-%m' )='" + nyterm+ "'"+") a group by eid ) A3 on A1.EID = A3.EID ";
		sql3 = sql3 + " from " +sql2;
		sql = sql1 + " left join "+sql3;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpid =" + corpid;

		sql ="CREATE temporary TABLE t_ctPerformanceItem as "+ sql ;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//生成薪资接口表对象
		sql = "Insert Into t_TempPayrollFormula(GID,PayItem,Name,Step,SQLFormula,SQLCondition,Digital,DigitalType,xMinValue,xMaxValue,DefValue) " +
				" Select a.GID,b.ID,a.ColName,a.Step,a.SQLFormula," +
				"a.SQLCondition,b.Digital,b.DigitalType,b.xMinValue,b.xMaxValue,b.DefValue" +
				" From systPayRollFormula a,systPayRollItem b where a.corpid = "+corpid +" and a.corpid = b.corpid and a.colname=b.colname and b.iftype=0";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql = "Insert into t_TempTable(TableName) " +
				" Select Distinct Objname From systPayrollObj a " +
				" Where Exists(Select 1 from systPayrollObjItem Where a.ObjName=ObjName)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Insert into t_TempTable2(TableName) " +
				" Select Distinct Objname From systPayrollObj a " +
				" Where Exists(Select 1 from systPayrollObjItem Where a.ObjName=ObjName)";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Insert Into t_TempTable(TableName,IsSys) "
				+"Select 'systPayStdItem',1 " +
				" Union Select 'systPayItemInputValue',1 " +
				" Union Select 'systPayItemFactValue',1 " +
				" Union Select 'ctAttendItem',1 "+
				" Union Select 'ctPerformanceItem',1 ";
		systpaystditemMapper.listSalaryTemplate3(sql);
		sql = "Insert Into t_TempTable2(TableName,IsSys) "
				+" Select 'systPayStdItem',1 " +
				" Union Select 'systPayItemInputValue',1 " +
				" Union Select 'systPayItemFactValue',1 " +
				" Union Select 'ctAttendItem',1 "+
				" Union Select 'ctPerformanceItem',1 ";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//替换公式里的薪资函数，增加EID参数
		Systdataobjs systdataobjs = new Systdataobjs();
		systdataobjs.setFtype("C");

		Integer id = null;
		String funname = "";
		Systdatacols systdatacols = null;
		QueryWrapper<Systdatacols> queryWrapper = null;
		List systdataobjsList = systdataobjsService.list(Wrappers.query(systdataobjs));
		List systdataclosList = null;
		log.info("替换公式里的薪资函数，增加EID参数 ......begin");

		for(int i=0;i<systdataobjsList.size();i++){
			systdatacols = new Systdatacols();
			queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("objname",systdatacols.getObjname());
			queryWrapper.ne("colname","");
			systdataclosList = systdatacolsService.list(queryWrapper);
			if(systdataclosList.size()>=2){
				sql ="Update t_TempPayrollFormula a Set a.SQLFormula=REPLACE(a.SQLFormula,CONCAT(" +
						systdataobjs.getObjname() +
						",'('),CONCAT(" +
						systdataobjs.getObjname()+
						",'('," +
						"systPayItemFactValue" +
						",'.EID,'))";
				log.info(sql);
				systpaystditemMapper.listSalaryTemplate3(sql);
			}
		}
		log.info("替换公式里的薪资函数，增加EID参数 ......end");
		//替换对象为临时表并加上Isnull(
		sql = "select id,tablename,issys from t_TempTable";
		List<LinkedHashMap> temptableList = systpaystditemMapper.listSalaryTemplate(sql);
		String objname = "";
		Map temptableMap = null;
		log.info("替换对象为临时表并加上Isnull( ......begin");
		for(int i=0;i<temptableList.size();i++){
			temptableMap = temptableList.get(i);
			objname = temptableMap.get("tablename").toString();
			//对象名在公式里，就把表名更新为对应的临时表
			sql = "Select 1 From t_TempPayrollFormula Where INSTR(SQLFormula" +

					",'" +
					objname +
					"') <> 0";
			List<LinkedHashMap> tempPayrollFormulaList = systpaystditemMapper.listSalaryTemplate(sql);
			log.info(sql);
			if(tempPayrollFormulaList.size()>0){
				sql = "Update t_TempPayrollFormula a Set a.SQLFormula=REPLACE(a.SQLFormula,CONCAT(" +
						objname +
						",'.'),CONCAT('IFNULL(t_'," +
						objname +
						",'.'))";
				systpaystditemMapper.listSalaryTemplate3(sql);
				log.info(sql);
			}else{
				//去掉那些没在公式里出现的对象
				sql = "Delete a From t_TempTable a Where TableName='" +
						objname +
						"' And '" +
						objname +
						"' Not In(Select TableName From t_TempTable2 Where IsSys=1)";
				systpaystditemMapper.listSalaryTemplate3(sql);


			}
		}
		log.info("替换对象为临时表并加上Isnull( ......end");
		//替换公式里的列名，并加上Isnull的后面部分,0.0)
		log.info("替换公式里的列名，并加上Isnull的后面部分,0.0) ......begin");
		sql = "INSERT INTO t_TempColumn(ColumnName) " +
				"SELECT DISTINCT xColumn FROM ( " +
				"Select xColumn From systPayrollObjItem Where IFNULL(CorpID,0)=" +
				corpid +
				" Union Select ColName As xColumn From systPayRollItem Where IFNULL(CorpID,0)=" +
				corpid +
				" Union Select ColName From systPayStdItem Where IFNULL(CorpID,0)=" +
				corpid +
				" Union Select ColName From ctAttendItem Where IFNULL(CorpID,0)=" +
				corpid +
				" Union Select ColName From ctPerformanceItem Where IFNULL(CorpID,0)=" +
				corpid +
				" Union Select 'EID'    ) a";
		systpaystditemMapper.listSalaryTemplate3(sql);
		log.info(sql);
		log.info("替换公式里的列名，并加上Isnull的后面部分,0.0) ......end");
		sql = "select id,columnname from t_TempColumn";
		List<LinkedHashMap> tempColumnList = systpaystditemMapper.listSalaryTemplate(sql);
		String colname = "";
		Map tempColumnMap = null;
		log.info("对象名在公式里，就把表面更新为对应的临时表 ......begin");
		for(int i=0;i<tempColumnList.size();i++) {
			tempColumnMap = tempColumnList.get(i);
			colname = tempColumnMap.get("columnname").toString();
			//对象名在公式里，就把表面更新为对应的临时表
			sql = "Update t_TempPayrollFormula a Set a.SQLFormula=REPLACE(a.SQLFormula,CONCAT('.','" +
					colname +
					"'),CONCAT('.','" +
					colname +
					"',',0.0)'))";
			systpaystditemMapper.listSalaryTemplate3(sql);
			log.info(sql);
		}
		log.info("对象名在公式里，就把表面更新为对应的临时表 ......end");
		//替换公式里的薪资常数
		CtcdConst ctcdConst  = new CtcdConst();
		ctcdConst.setCorpid(corpid);
		List<CtcdConst> ctcdConstList = ctcdConstService.list(Wrappers.query(ctcdConst));
		Integer constId = null;
		String constTitle =null;
		CtcdConst ctcdConst1 = null;
		log.info("替换公式里的薪资常数 ......begin");
		for(int i=0;i<ctcdConstList.size();i++){
			ctcdConst1 = ctcdConstList.get(i);
			constId =ctcdConst1.getId();
			constTitle = ctcdConst1.getTitle();
			sql ="UPDATE t_TempPayrollFormula a SET a.SQLFormula=REPLACE(a.SQLFormula," +
					constTitle +
					",CONCAT('(Select Value From ctCD_Const Where IFNULL(CorpID,0)=" +
					corpid+
					"And Title='''," +
					constTitle +
					",''')'))";
			systpaystditemMapper.listSalaryTemplate3(sql);
			log.info(sql);

		}
		log.info("替换公式里的薪资常数 ......end");
		//小数位进位类型
		//1四舍五入2有尾数进位3舍尾数4见分进元
		log.info("小数位进位类型 ......begin");
		sql = "UPDATE t_TempPayrollFormula a " +
				"Set a.SQLFormula=" +
				"Case When a.DigitalType =1 Then CONCAT('Round((',a.SQLFormula,'),',a.Digital,')') " +
				"When a.DigitalType =2 Then CONCAT('Round(((',a.SQLFormula,')+0.004),',a.Digital,')') " +
				"When a.DigitalType =3 Then CONCAT('Round(((',a.SQLFormula,')-0.005),',a.Digital,')') " +
				"When a.DigitalType =4 Then CONCAT('Round(((',a.SQLFormula,')+0.495),',a.Digital,')') " +
				"Else a.SQLFormula End";
		systpaystditemMapper.listSalaryTemplate3(sql);
		log.info(sql);
		log.info("小数位进位类型 ......end");
		//根据对象,生成临时表
		log.info("根据对象,生成临时表......begin");
		Systpayrollitem systpayrollitem1 = null;
		colname = "";
		String colnames = "";
		Integer payrollitem = null;
		Map systdatacloumMap = null;

		List<LinkedHashMap> temptableList2 = null;
		sql = "select id,tablename from t_TempTable where TableName<>'systPayItemFactValue'";
		temptableList2 = systpaystditemMapper.listSalaryTemplate(sql);
		String tablename2 = "";
		Map temptableMap2 = null;
		sql3 = "";
		String sqldrop = "";
		for(int gg=0;gg<temptableList2.size();gg++){
			temptableMap2 = temptableList2.get(gg);
			tablename2  =temptableMap2.get("tablename").toString();
			sql = "select GROUP_CONCAT(ColName) as colnames from systDataCols where objname='"+tablename2+"'";
			systdatacloumMap = systpaystditemMapper.listSalaryTemplate2(sql);
			colnames = systdatacloumMap.get("colnames").toString();
			if(!StringUtils.isEmpty(tablename2)){
				if(tablename2.equals("systPayStdItem") || tablename2.equals("systPayItemInputValue")|| tablename2.equals("systPayItemFactValue")|| tablename2.equals("ctAttendItem")|| tablename2.equals("ctPerformanceItem") ){

				}else{
					sql3 ="CREATE TABLE t_"+tablename2 +" as "+"select "+ colnames+" from "+tablename2+
							" where eid in(select eid from t_systPayrollEmpInfo)";
					systpaystditemMapper.listSalaryTemplate3(sql3);
					if(gg==0){
						sqldrop = sqldrop + " DROP  TABLE IF EXISTS t_"+tablename2;
					}else{
						sqldrop = sqldrop + " ;DROP  TABLE IF EXISTS t_"+tablename2;
					}

				}

			}

			log.info(sql3);
		}

		log.info("根据对象,生成临时表......end");

		log.info("初始化值......begin");
		//初始化值
		for(int i=0;i<systpayrollitemList2.size();i++){
			systpayrollitem1 = systpayrollitemList2.get(i);
			colname =systpayrollitem1.getColname();
			payrollitem=systpayrollitem1.getId();
			sql = "update t_systPayItemFactValue set "+colname+"=0.0";
			systpaystditemMapper.listSalaryTemplate3(sql);
			log.info(sql);
			//多表连接前一部分
			Integer step = null;
			Map systpaystditemMap = null;
			/*
			sql = "select id,gid,payitem,name, step, sqlformul, sqlcondit, digital, digitalty, xminvalue, xmaxvalue, defvalue  from t_TempPayrollFormula where corpid = "+corpid+" and name="+colname +" and IFNULL(SQLFormula,'') <> ''";
			systpaystditemList2 = systpaystditemMapper.listSalaryTemplate4(sql);
			//步骤 同一薪资项下的不同条件

			for(int g=0;g<systpaystditemList2.size();g++){
				systpaystditemMap = systpaystditemList2.get(i);
				step = Integer.parseInt(systpaystditemMap.get("step").toString());
				sql = "UPDATE t_systPayItemFactValue SET "+colname+"=CASE WHEN IFNULL(xMinValue,'') <> '' AND IFNULL(xMaxValue,'') = '' then case when ";
			}*/


		}
		log.info("初始化值......end");
		List<LinkedHashMap> tempPayrollFormulaList = null;
		Map tempPayrollFormulaMap = null;
		List<LinkedHashMap> tempTableList = null;
		Map tempTableMap = null;
		String formula = "";
		String tablename = "";
		String payitem = "";
		String sqlcondition = "";
		sql = "select ID,GID,PayItem ,Name,Step,SQLFormula,SQLCondition,Digital,DigitalType ,xMinValue,xMaxValue ,DefValue  from t_TempPayrollFormula where gid = "+gid+" and IFNULL(SQLFormula,'') <> ''";
		tempPayrollFormulaList = systpaystditemMapper.listSalaryTemplate(sql);
		sql = "select id,tablename from  t_TempTable";
		tempTableList = systpaystditemMapper.listSalaryTemplate(sql);
		log.info("计算操作......begin");
		for(int j=0;j<tempPayrollFormulaList.size();j++){
			tempPayrollFormulaMap = tempPayrollFormulaList.get(j);
			formula =tempPayrollFormulaMap.get("sqlformula").toString();
			payitem = tempPayrollFormulaMap.get("payitem").toString();
			sqlcondition = tempPayrollFormulaMap.get("sqlcondition").toString();
			for(int jj=0;jj<tempTableList.size();jj++){
				sql = "UPDATE t_systPayItemFactValue SET "+payitem+"="+formula;
				tempTableMap = tempTableList.get(jj);
				tablename = tempTableMap.get(jj).toString();
				if(formula.contains(tablename)){
					sql = sql + " left join t_"+tablename+" on t_systPayItemFactValue.eid = t_"+tablename+".eid ";
				}
			}
			sql = sql +"t_systPayItemFactValue.eid in("+sqlcondition+")";
			log.info(sql);
			systpaystditemMapper.listSalaryTemplate3(sql);

		}
		log.info("计算操作......end");

		sql = "delete from systPayItemFactValue Where corpid = "+corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		//插入到systPayItemFactValue表中
		log.info("插入到systPayItemFactValue表中......begin");
		String colname3 = "";
		Systpayrollitem systpayrollitem3 = null;
		for(int i=0;i<systpayrollitemList2.size();i++){
			systpayrollitem3 = systpayrollitemList2.get(i);
			colname3 = systpayrollitem3.getColname();
			sql = "Select " +corpid+
					" As CorpID,'" +term+
					"' As Term,EID," +systpayrollitem3.getId()+
					","+gid+" as gid,"
					+colname3+" As FactValue From t_systPayItemFactValue";
			if(i==systpayrollitemList2.size()-1){

			}else{
				sql = sql +" union all ";
			}
		}
		sql = "insert Into systPayItemFactValue(CorpID,Term,EID,PayItem,GID,FactValue) "+sql;
		log.info(sql);
		systpaystditemMapper.listSalaryTemplate3(sql);
		log.info("插入到systPayItemFactValue表中......end");






		//薪资薪资期间状态
		UpdateWrapper<Systpayrollgroupprocess> updateWrapper = new UpdateWrapper<>();
		Systpayrollgroupprocess systpayrollgroupprocess1 = new Systpayrollgroupprocess();
		systpayrollgroupprocess1.setSubmit(1);
		systpayrollgroupprocess1.setSubmitby(userid);
		systpayrollgroupprocess1.setSubmittime(curretTime);
		updateWrapper.eq("corpid",corpid);
		updateWrapper.eq("date_format( term, '%Y-%m' )",nyterm);
		systpayrollgroupprocessService.update(systpayrollgroupprocess1,updateWrapper);

		/*
		 *******计算后sysSPcCalcAfter1*******
		 */
		sql = "Update ctEmployee a,systpayrollempinfo b " +
				" Set a.Term ='" +firstDay +
				"' where a.eid=b.eid and ifnull(a.SalaryStatus,0)=1 And a.Term Is Null and a.corpid="+corpid+" and b.corpid=" +corpid;
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql00 ="DROP  TABLE IF EXISTS t_TempTable";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 ="DROP  TABLE IF EXISTS t_TempTable2";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_TempColumn";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00="DROP  TABLE IF EXISTS t_TempPayrollFormula";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayrollEmpInfo";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayItemInputValue";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayItemFactValue";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_ctAttendItem";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		sql00 = "DROP  TABLE IF EXISTS t_systPayStdItem";
		systpaystditemMapper.listSalaryTemplate3(sql00);
		log.info(sqldrop);
		if(!StringUtils.isEmpty(sqldrop)){
			systpaystditemMapper.listSalaryTemplate3(sqldrop);
		}

		return R.ok("操作成功！");
	}

	/**
	 * 签发
	 * Liang hy
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "签发", notes = "签发")
	@SysLog("签发")
	@PostMapping("/sysSPConfirm")
	@Transactional
	public R sysSPConfirm(@RequestBody  Map paramMap) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String currentTime = DateUtils.getTimeString();
		int userid = pigxUser.getId();
		Integer corpid = pigxUser.getCorpid();
		paramMap.put("userid", userid);
		String term = paramMap.get("term").toString();
		String nyterm = term.substring(0,7);
		//查询考勤账套
		Systpayrollgroup systpayrollgroup =null;
		QueryWrapper<Systpayrollgroup> payrollgroupqueryWrapper = new QueryWrapper<>();
		payrollgroupqueryWrapper.eq("corpid",pigxUser.getCorpid());
		systpayrollgroup = systpayrollgroupService.getOne(payrollgroupqueryWrapper);
		Integer gid = null;
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请维护薪资账套号！");
		}else{
			gid = systpayrollgroup.getId();
		}


		//判断当前薪资套是否已经封帐。
		QueryWrapper<Systpayrollgroupprocess> queryWrapper1=new QueryWrapper<>();
		queryWrapper1.eq("closed",1);
		queryWrapper1.eq("gid",gid);
		queryWrapper1.eq("date_format( term, '%Y-%m' )",nyterm);
		List<Systpayrollgroupprocess> systpayrollgroupprocesses = systpayrollgroupprocessService.list(queryWrapper1);
		if((!systpayrollgroupprocesses.isEmpty())&&systpayrollgroupprocesses.size()>0){
			return R.failed("当前薪资套已经封帐，不能签发！");
		}

		//判断当前薪资套薪资数据是否已经签发
		QueryWrapper<Systpayrollgroupprocess> queryWrapper2=new QueryWrapper<>();
		queryWrapper2.eq("confirm",1);
		queryWrapper2.eq("gid",gid);
		queryWrapper2.eq("date_format( term, '%Y-%m' )",nyterm);
		List<Systpayrollgroupprocess> systpayrollgroupprocesses2 = systpayrollgroupprocessService.list(queryWrapper2);
		if((!StringUtils.isEmpty(systpayrollgroupprocesses2))&&systpayrollgroupprocesses2.size()>0){
			return R.failed("薪资数据已经签发，请反签发后再签发！");
		}

		//判断当前薪资套薪资数据是否未计算
		QueryWrapper<Systpayrollgroupprocess> queryWrapper3=new QueryWrapper<>();
		queryWrapper3.eq("Submit",1);
		queryWrapper3.eq("gid",gid);
		queryWrapper3.eq("date_format( term, '%Y-%m' )",nyterm);
		List<Systpayrollgroupprocess> systpayrollgroupprocesses3 = systpayrollgroupprocessService.list(queryWrapper2);
		if(StringUtils.isEmpty(systpayrollgroupprocesses3)){
			return R.failed("当前薪资套还未计算，不能签发！");
		}

		//Update
		UpdateWrapper<Systpayrollgroupprocess> updateWrapper1 = new UpdateWrapper<>();
		updateWrapper1.eq("gid",gid);
		updateWrapper1.eq("date_format( term, '%Y-%m' )",nyterm);
		Systpayrollgroupprocess systpayrollgroupprocess  = new Systpayrollgroupprocess();
		systpayrollgroupprocess.setConfirm(1);
		systpayrollgroupprocess.setConfirmby(userid);
		systpayrollgroupprocess.setConfirmtime(currentTime);
		systpayrollgroupprocessService.update(systpayrollgroupprocess,updateWrapper1);


		return R.ok("");
	}

	/**
	 * 封账
	 * Liang hy
	 * @param paramMap
	 * @return R
	 * */
	@ApiOperation(value = "封账", notes = "封账")
	@SysLog("封账")
	@PostMapping("/sysSPcClose")
	@Transactional
	public R sysSPcClose(@RequestBody  Map paramMap)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		Integer corpid = pigxUser.getCorpid();
		paramMap.put("userid", userid);
		//2020-07-08
		String term = paramMap.get("term").toString();
		//2020-07
		String nyterm = term.substring(0,7);

		String firstDay = DateUtils.getFirstDayOfGivenMonth(term);
		String lastDay = DateUtils.getLastDayOfMonth(term);
		//查询考勤账套
		Systpayrollgroup systpayrollgroup =null;
		QueryWrapper<Systpayrollgroup> payrollgroupqueryWrapper = new QueryWrapper<>();
		payrollgroupqueryWrapper.eq("corpid",pigxUser.getCorpid());
		systpayrollgroup = systpayrollgroupService.getOne(payrollgroupqueryWrapper);
		Integer gid = null;
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请维护薪资账套号！");
		}else{
			gid = systpayrollgroup.getId();
		}



		//薪资期间不能为空
		QueryWrapper<Systpayrollgroupprocess> systpayrollgroupprocessQueryWrapper = new QueryWrapper<>();
		systpayrollgroupprocessQueryWrapper.eq("corpid",pigxUser.getCorpid());
		Systpayrollgroupprocess systpayrollgroupprocess = systpayrollgroupprocessService.getOne(systpayrollgroupprocessQueryWrapper);


		if(StringUtils.isEmpty(systpayrollgroupprocess)){
			return R.failed("薪资期间不能为空");
		}
		String term2= systpayrollgroupprocess.getTerm();
		if(StringUtils.isEmpty(term2)){
			return R.failed("薪资期间不能为空");
		}
		//账套对应期间已经封账
		QueryWrapper<Systpayrollgroupprocess> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpid",pigxUser.getCorpid());
		queryWrapper2.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper2.eq("closed",1);
		Systpayrollgroupprocess systpayrollgroupprocess2 = systpayrollgroupprocessService.getOne(queryWrapper2);
		if(!StringUtils.isEmpty(systpayrollgroupprocess2)){
			return R.failed("账套对应期间已经封账");
		}
		//薪资未计算
		QueryWrapper<Systpayrollgroupprocess> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("corpid",pigxUser.getCorpid());
		queryWrapper3.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper3.eq("ifnull(Submit,0)",0);
		Systpayrollgroupprocess systpayrollgroupprocess3 = systpayrollgroupprocessService.getOne(queryWrapper3);
		if(!StringUtils.isEmpty(systpayrollgroupprocess3)){
			return R.failed("薪资还未计算");
		}
		//当前薪资套还未签发！
		QueryWrapper<Systpayrollgroupprocess> queryWrapper4 = new QueryWrapper<>();
		queryWrapper4.eq("corpid",pigxUser.getCorpid());
		queryWrapper4.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper4.eq("ifnull(Confirm,0)",0);
		Systpayrollgroupprocess systpayrollgroupprocess4 = systpayrollgroupprocessService.getOne(queryWrapper4);
		if(!StringUtils.isEmpty(systpayrollgroupprocess4)){
			return R.failed("当前薪资套还未签发");
		}


		//Delete
		QueryWrapper<SystpayrollempinfoAll> queryWrapper6 = new QueryWrapper<>();
		queryWrapper6.eq("corpid",pigxUser.getCorpid());
		queryWrapper6.eq("date_format( term, '%Y-%m' )",nyterm);
		systpayrollempinfoAllService.remove(queryWrapper6);

		//Update
		UpdateWrapper<Systpayrollempinfo> queryWrapper7 = new UpdateWrapper<>();
		queryWrapper7.eq("corpid",corpid);
		Systpayrollempinfo systpayrollempinfo  = new Systpayrollempinfo();
		systpayrollempinfo.setTerm(firstDay);
		systpayrollempinfoService.update(systpayrollempinfo,queryWrapper7);



		//插入历史
		Systpayrollempinfo systpayrollempinfo1 =new Systpayrollempinfo();
		systpayrollempinfo1.setCorpid(corpid);
		List<Systpayrollempinfo> systpayrollempinfoList = systpayrollempinfoService.list(Wrappers.query(systpayrollempinfo1));
		Systpayrollempinfo systpayrollempinfo2 =null;
		SystpayrollempinfoAll systpayrollempinfoAll = null;
		for(int i=0;i<systpayrollempinfoList.size();i++){
			systpayrollempinfo2 = systpayrollempinfoList.get(i);
			systpayrollempinfoAll = new SystpayrollempinfoAll();
			BeanUtils.copyProperties(systpayrollempinfo2,systpayrollempinfoAll);
			systpayrollempinfoAllService.save(systpayrollempinfoAll);
		}

		QueryWrapper<Systpayiteminputvalue> queryWrapper8 = new QueryWrapper<>();
		queryWrapper8.eq("corpid",corpid);
		queryWrapper8.eq("gid",gid);
		queryWrapper8.eq("date_format( term, '%Y-%m' )",nyterm);
		systpayiteminputvalueService.remove(queryWrapper8);

		//更新关联id
		Systpayrollempinfo systpayrollempinfo3 =new Systpayrollempinfo();
		systpayrollempinfo1.setCorpid(corpid);
		systpayrollempinfo1.setGid(gid);
		List<Systpayrollempinfo> systpayrollempinfoList2 = systpayrollempinfoService.list(Wrappers.query(systpayrollempinfo3));
		for (Systpayrollempinfo systpayrollempinfo4:systpayrollempinfoList2) {
			UpdateWrapper<Systpayiteminputvalue> queryWrapper9 = new UpdateWrapper<>();
			queryWrapper7.eq("eid",systpayrollempinfo4.getEid());
			queryWrapper7.eq("gid",systpayrollempinfo4.getGid());
			Systpayiteminputvalue systpayiteminputvalue  = new Systpayiteminputvalue();
			systpayiteminputvalue.setTerm(firstDay);
			systpayiteminputvalue.setSeqid(systpayrollempinfo4.getId());
			systpayiteminputvalueService.update(systpayiteminputvalue,queryWrapper9);
		}

		//
		QueryWrapper<Systpayiteminputvalue> queryWrapper9 = new QueryWrapper<>();
		queryWrapper9.eq("corpid",corpid);
		queryWrapper9.eq("gid",gid);
		queryWrapper9.eq("date_format( term, '%Y-%m' )",nyterm);
		List<Systpayiteminputvalue> systpayiteminputvalues = systpayiteminputvalueService.list(queryWrapper9);
		for (Systpayiteminputvalue systpayiteminputvalue:systpayiteminputvalues) {
			SystpayitemfactvalueAll systpayitemfactvalueAll = new SystpayitemfactvalueAll();
			BeanUtils.copyProperties(systpayiteminputvalue,systpayitemfactvalueAll);
			systpayitemfactvalueAllService.save(systpayitemfactvalueAll);
		}

		//Delete 删除应加应扣
		QueryWrapper<Systpayiteminputvalue> queryWrapper10 = new QueryWrapper<>();
		queryWrapper10.eq("corpid",pigxUser.getCorpid());
		queryWrapper10.eq("gid",gid);
		systpayiteminputvalueService.remove(queryWrapper10);

		//Delete 薪资列表
		QueryWrapper<SystpayitemfactvalueAll> queryWrapper11 = new QueryWrapper<>();
		queryWrapper11.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper11.eq("corpid",pigxUser.getCorpid());
		queryWrapper11.eq("gid",gid);
		systpayitemfactvalueAllService.remove(queryWrapper11);

		//更新关联ID
		String sql="Update systPayItemFactValue a,systPayrollEmpInfo b" +
				" Set a.Term="+firstDay+",a.SeqID=b.ID" +
				" Where a.EID=b.EID And IFNULL(a.CorpID,0)="+corpid+" And a.GID="+gid+" And a.GID=b.GID;";


		QueryWrapper<Systpayitemfactvalue> queryWrapper19 = new QueryWrapper<>();
		queryWrapper19.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper19.eq("corpid",pigxUser.getCorpid());
		queryWrapper19.eq("gid",gid);
		List<Systpayitemfactvalue> systpayitemfactvalues = systpayitemfactvalueService.list(queryWrapper19);
		SystpayitemfactvalueAll systpayitemfactvalueAll=new SystpayitemfactvalueAll();
		for (Systpayitemfactvalue systpayitemfactvalue:systpayitemfactvalues) {
			BeanUtils.copyProperties(systpayitemfactvalue,systpayitemfactvalueAll);
			systpayitemfactvalueAllService.save(systpayitemfactvalueAll);
		}

		//Delete 删除薪资列表
		QueryWrapper<Systpayitemfactvalue> queryWrapper12 = new QueryWrapper<>();
		queryWrapper12.eq("corpid",pigxUser.getCorpid());
		queryWrapper12.eq("gid",gid);
		systpayitemfactvalueService.remove(queryWrapper12);

		//Delete 薪资项目
		QueryWrapper<SystpayrollitemAll> queryWrapper13 = new QueryWrapper<>();
		queryWrapper13.eq("corpid",pigxUser.getCorpid());
		queryWrapper13.eq("gid",gid);
		queryWrapper13.eq("date_format( term, '%Y-%m' )",nyterm);
		systpayrollitemAllService.remove(queryWrapper13);

		//Update
		UpdateWrapper<Systpayrollitem> queryWrapper14 = new UpdateWrapper<>();
		queryWrapper14.eq("corpid",corpid);
		queryWrapper14.eq("GID",corpid);
		Systpayrollitem systpayrollitem  = new Systpayrollitem();
		systpayrollitem.setTerm(firstDay);
		systpayrollitemService.update(systpayrollitem,queryWrapper14);

		QueryWrapper<Systpayrollitem> queryWrapper20 = new QueryWrapper<>();
		queryWrapper20.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper14.eq("corpid",corpid);
		queryWrapper14.eq("GID",corpid);
		List<Systpayrollitem> systpayrollitems = systpayrollitemService.list(queryWrapper14);
		SystpayrollitemAll systpayrollitemAll=new SystpayrollitemAll();
		for (Systpayrollitem systpayrollitem2:systpayrollitems) {
			BeanUtils.copyProperties(systpayrollitem2,systpayrollitemAll);
			systpayrollitemAll.setTerm(term);
			systpayrollitemAllService.save(systpayrollitemAll);
		}


		//Delete 考勤接口
		QueryWrapper<CtattendAll> queryWrapper15 = new QueryWrapper<>();
		queryWrapper15.eq("corpid",pigxUser.getCorpid());
		queryWrapper15.eq("gid",gid);
		queryWrapper15.eq("date_format( term, '%Y-%m' )",nyterm);
		ctattendAllService.remove(queryWrapper15);

		UpdateWrapper<Ctattend> queryWrapper16 = new UpdateWrapper<>();
		queryWrapper16.eq("corpid",pigxUser.getCorpid());
		queryWrapper16.eq("gid",gid);
		Ctattend ctattend = new Ctattend();
		ctattend.setTerm(firstDay);
		ctattendService.update(ctattend,queryWrapper16);

		QueryWrapper<Ctattend> queryWrapper21=new QueryWrapper<>();
		queryWrapper21.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper21.eq("corpid",pigxUser.getCorpid());
		List<Ctattend> ctattends = ctattendService.list(queryWrapper21);
		CtattendAll ctattendAll=new CtattendAll();
		for (Ctattend ctattend2:ctattends) {
			BeanUtils.copyProperties(ctattend2,ctattendAll);
			ctattendAllService.save(ctattendAll);
		}


		QueryWrapper<CtattenditemAll> queryWrapper17 = new QueryWrapper<>();
		queryWrapper17.eq("corpid",pigxUser.getCorpid());
		queryWrapper17.eq("gid",gid);
		queryWrapper17.eq("date_format( term, '%Y-%m' )",nyterm);
		ctattenditemAllService.remove(queryWrapper17);

		QueryWrapper<Ctattenditem> queryWrapper22 = new QueryWrapper<>();
		queryWrapper22.eq("corpid",corpid);
		queryWrapper17.eq("gid",gid);
		List<Ctattenditem> ctattenditems = ctattenditemService.list(queryWrapper22);
		CtattenditemAll ctattenditemAll=new CtattenditemAll();
		for (Ctattenditem ctattenditem :ctattenditems) {
			BeanUtils.copyProperties(ctattenditem,ctattenditemAll);
			ctattenditemAll.setTerm(term);
			ctattenditemAllService.save(ctattenditemAll);
		}


		QueryWrapper<CtattenditemfactvalueAll> queryWrapper18 = new QueryWrapper<>();
		queryWrapper18.eq("corpid",pigxUser.getCorpid());
		queryWrapper18.eq("gid",gid);
		queryWrapper18.eq("date_format( term, '%Y-%m' )",nyterm);
		ctattenditemfactvalueAllService.remove(queryWrapper18);

		sql="UPDATE ctAttendItemFactValue a,ctAttend b" +
				" SET a.Term='"+firstDay+"',a.SeqID=b.ID" +
				" WHERE a.EID=b.EID And IFNULL(a.CorpID,0)="+corpid+" AND a.GID=2 And a.GID=b.GID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctattenditemfactvalue> queryWrapper23 = new QueryWrapper<>();
		queryWrapper23.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper23.eq("corpid",pigxUser.getCorpid());
		queryWrapper23.eq("gid",gid);
		List<Ctattenditemfactvalue> ctattenditemfactvalues = ctattenditemfactvalueService.list(queryWrapper23);
		CtattenditemfactvalueAll ctattenditemfactvalueAll=new CtattenditemfactvalueAll();
		for (Ctattenditemfactvalue ctattenditemfactvalue:ctattenditemfactvalues) {
			BeanUtils.copyProperties(ctattenditemfactvalue,ctattenditemfactvalueAll);
			ctattenditemfactvalueAllService.save(ctattenditemfactvalueAll);
		}

		//删除考勤
		sql = "Delete From ctAttendItemFactValue Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Delete From ctAttend Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//绩效接口
		sql="Delete From ctPerformanceBonus_All Where " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				" And IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Update ctPerformanceBonus Set Term=" +term+
				" Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctperformancebonus> queryWrapper24=new QueryWrapper<>();
		queryWrapper24.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper24.eq("corpid",pigxUser.getCorpid());
		queryWrapper24.eq("gid",gid);
		List<Ctperformancebonus> ctperformancebonuses = ctperformancebonusService.list(queryWrapper24);
		CtperformancebonusAll ctperformancebonusAll=new CtperformancebonusAll();
		for (Ctperformancebonus ctperformancebonus:ctperformancebonuses) {
			BeanUtils.copyProperties(ctperformancebonus,ctperformancebonusAll);
			ctperformancebonusAllService.save(ctperformancebonusAll);
		}


		sql="Delete From ctPerformanceItem_All Where " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"And IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctperformanceitem> queryWrapper25=new QueryWrapper<>();
		queryWrapper25.eq("corpid",pigxUser.getCorpid());
		queryWrapper25.eq("gid",gid);
		List<Ctperformanceitem> ctperformanceitems = ctperformanceitemService.list(queryWrapper25);
		CtperformanceitemAll ctperformanceitemAll=new CtperformanceitemAll();
		for (Ctperformanceitem ctperformanceitem:ctperformanceitems) {
			BeanUtils.copyProperties(ctperformanceitem,ctperformanceitemAll);
			ctperformanceitemAll.setTerm(term);
			ctperformanceitemAllService.save(ctperformanceitemAll);
		}

		sql="Delete From ctPerformanceItemFactValue_All Where " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				" And IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Update ctPerformanceItemFactValue a,ctPerformanceBonus b " +
				"	Set a.Term='"+term+"',a.SeqID=b.ID " +
				" Where a.EID=b.EID And Ifnull(a.CorpID,0)="+corpid+" And a.GID="+gid+" And a.GID=b.GID;";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctperformanceitemfactvalue> queryWrapper26=new QueryWrapper<>();
		queryWrapper26.eq("date_format( term, '%Y-%m' )",nyterm);
		queryWrapper26.eq("corpid",pigxUser.getCorpid());
		queryWrapper26.eq("gid",gid);
		List<Ctperformanceitemfactvalue> ctperformanceitemfactvalues = ctperformanceitemfactvalueService.list(queryWrapper26);
		CtperformanceitemfactvalueAll ctperformanceitemfactvalueAll=new CtperformanceitemfactvalueAll();
		for (Ctperformanceitemfactvalue ctperformanceitemfactvalue:ctperformanceitemfactvalues) {
			BeanUtils.copyProperties(ctperformanceitemfactvalue,ctperformanceitemfactvalueAll);
			ctperformanceitemfactvalueAllService.save(ctperformanceitemfactvalueAll);
		}

		//删除绩效
		sql="DELETE FROM ctPerformanceItemFactValue Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="DELETE FROM ctPerformanceBonus Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//定期支付扣
		QueryWrapper<Systpayrollempinfo> queryWrapper27=new QueryWrapper<>();
		queryWrapper27.eq("corpid",pigxUser.getCorpid());
		queryWrapper27.eq("gid",gid);
		List<Systpayrollempinfo> systpayrollempinfos = systpayrollempinfoService.list(queryWrapper27);
		List<Integer> eids=new ArrayList<>();
		for (Systpayrollempinfo systpayrollempinfo4:systpayrollempinfos) {
			eids.add(systpayrollempinfo4.getEid());
		}
		QueryWrapper<CtregularpayRegister> queryWrapper28=new QueryWrapper<>();
		//这里不知道对不对
		queryWrapper28.ge("ToTerm",term);
		queryWrapper28.eq("corpid",pigxUser.getCorpid());
		if(eids.size()>0){
			queryWrapper28.in("eid",eids);
		}

		List<CtregularpayRegister> ctregularpayRegisters = ctregularpayRegisterService.list(queryWrapper28);
		CtregularpayAll ctregularpayAll=new CtregularpayAll();
		for (CtregularpayRegister ctregularpayRegister:ctregularpayRegisters) {
			BeanUtils.copyProperties(ctregularpayRegister,ctregularpayAll);
			ctregularpayAllService.save(ctregularpayAll);
		}


		//删除定期支付扣
		sql="Delete from ctRegularPay_register Where " +
				//"TIMESTAMPDIFF(month,ToTerm,'"+term+"')>=0 " +
				"   date_format( ToTerm, '%Y-%m' )>='" +term.substring(0,7)+"' "+
				" And IFNULL(CorpID,0)="+corpid +
				" And EID In(Select Distinct EID from systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除福利数据
		sql="Delete From ctBenPayBack_All " +
				" Where " +
				//" TIMESTAMPDIFF(month,term,'"+term+"')=0 " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"  And EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除福利补缴登记
		Set set = new HashSet();
		set.addAll(eids);
		eids=new ArrayList<>();
		eids.addAll(set);
		QueryWrapper<CtbenpaybackRegister> queryWrapper29=new QueryWrapper<>();
		queryWrapper29.eq("date_format( term, '%Y-%m' )",nyterm);
		if(eids.size()>0){
			queryWrapper29.in("eid",eids);
		}

		List<CtbenpaybackRegister> ctbenpaybackRegisters = ctbenpaybackRegisterService.list(queryWrapper29);
		CtbenpaybackAll ctbenpaybackAll=new CtbenpaybackAll();
		for (CtbenpaybackRegister ctbenpaybackRegister:ctbenpaybackRegisters) {
			BeanUtils.copyProperties(ctbenpaybackRegister,ctbenpaybackAll);
			ctbenpaybackAllService.save(ctbenpaybackAll);
		}


		sql="Delete from ctBenPayBack_Register " +
				" Where " +
				//"TIMESTAMPDIFF(MONTH,'"+term+"',Term)=0 " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"  And EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//删除福利数据
		sql="Delete From ctBenCalc_All " +
				" Where " +
				//" TIMESTAMPDIFF(MONTH,'"+term+"',Term)=0" +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"  And EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctbencalc> queryWrapper30=new QueryWrapper<>();
		queryWrapper30.eq("date_format( term, '%Y-%m' )",nyterm);
		if(eids.size()>0){
			queryWrapper30.in("eid",eids);
		}
		//queryWrapper30.in("eid",eids);
		List<Ctbencalc> ctbencalcs = ctbencalcService.list(queryWrapper30);
		CtbencalcAll ctbencalcAll=new CtbencalcAll();
		for (Ctbencalc ctbencalc:ctbencalcs) {
			BeanUtils.copyProperties(ctbencalc,ctbencalcAll);
			ctbencalcAllService.save(ctbencalcAll);
		}

		sql="Delete from ctBenCalc  " +
				" Where " +
				//" TIMESTAMPDIFF(MONTH,'"+term+"',Term)=0  " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"  And EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Delete From ctBenefitStatus_All " +
				" Where " +
				//" TIMESTAMPDIFF(MONTH,'"+term+"',Term)=0" +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"  And EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctbenefitstatus> queryWrapper31=new QueryWrapper<>();
		queryWrapper31.eq("date_format( term, '%Y-%m' )",nyterm);

		if(eids.size()>0){
			queryWrapper31.in("eid",eids);
		}
		List<Ctbenefitstatus> ctbenefitstatuses = ctbenefitstatusService.list(queryWrapper31);
		CtbenefitstatusAll ctbenefitstatusAll=new CtbenefitstatusAll();
		for (Ctbenefitstatus ctbenefitstatus:ctbenefitstatuses) {
			BeanUtils.copyProperties(ctbenefitstatus,ctbenefitstatusAll);
			ctbenefitstatusAllService.save(ctbenefitstatusAll);
		}

		//薪资人员
		sql="Delete From ctEmployee_All " +
				"  Where " +
				//"TIMESTAMPDIFF(month,term,'"+term+"')=0 " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"   And EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Update ctEmployee Set Term='"+term+"' " +
				"  Where EID In(Select Distinct EID From systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+");";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctemployee> queryWrapper32=new QueryWrapper<>();
		if(eids.size()>0){
			queryWrapper32.in("EID",eids);
		}

		List<Ctemployee> ctemployees = ctemployeeService.list(queryWrapper32);
		CtemployeeAll ctemployeeAll=new CtemployeeAll();
		for (Ctemployee ctemployee:ctemployees) {
			BeanUtils.copyProperties(ctemployee,ctemployeeAll);
			ctemployeeAll.setTerm(term);
			ctemployeeAllService.save(ctemployeeAll);
		}

		sql="Delete From systPayStdItem_All Where " +
				//"TIMESTAMPDIFF(month,term,'"+term+"')=0 " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"And IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Systpaystditem> queryWrapper33=new QueryWrapper<>();
		queryWrapper33.eq("corpid",pigxUser.getCorpid());
		queryWrapper33.eq("gid",gid);
		List<Systpaystditem> systpaystditems = systpaystditemService.list(queryWrapper33);
		SystpaystditemAll systpaystditemAll=new SystpaystditemAll();
		for (Systpaystditem systpaystditem:systpaystditems) {
			BeanUtils.copyProperties(systpaystditem,systpaystditemAll);
			systpaystditemAll.setTerm(term);
			systpaystditemAllService.save(systpaystditemAll);
		}

		sql="DELETE FROM ctStandard_All Where " +
				//"TIMESTAMPDIFF(month,term,'"+term+"')=0 " +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				" And IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Ctstandard> queryWrapper34=new QueryWrapper<>();
		queryWrapper34.eq("corpid",pigxUser.getCorpid());
		queryWrapper34.eq("gid",gid);
		List<Ctstandard> ctstandards = ctstandardService.list(queryWrapper34);
		CtstandardAll ctstandardAll=new CtstandardAll();
		for (Ctstandard ctstandard:ctstandards) {
			BeanUtils.copyProperties(ctstandard,ctstandardAll);
			ctstandardAll.setTerm(term);
			ctstandardAllService.save(ctstandardAll);
		}

		//删除本月薪资列表人员
		sql=" DELETE FROM systPayrollEmpInfo Where IFNULL(CorpID,0)="+corpid+" And GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		//call @RetVal=sysSPcCloseAfterCustom2( _UserID,_CorpID,@RetVal);

		sql="Update systPayrollGroupProcess Set Closed=1,ClosedBy=" +userid+
				",ClosedTime=NOW(), Term='"+firstDay+"' Where GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		sql="Delete From systPayrollGroupProcess_All Where GID="+gid+" And " +
				//"TIMESTAMPDIFF(month,term,"+term+")=0" +
				"  date_format( term, '%Y-%m' )='" +term.substring(0,7)+"' "+
				"";
		systpaystditemMapper.listSalaryTemplate3(sql);

		QueryWrapper<Systpayrollgroupprocess> queryWrapper35=new QueryWrapper<>();
		queryWrapper35.eq("gid",gid);
		List<Systpayrollgroupprocess> systpayrollgroupprocesses = systpayrollgroupprocessService.list(queryWrapper35);
		SystpayrollgroupprocessAll systpayrollgroupprocessAll=new SystpayrollgroupprocessAll();
		for (Systpayrollgroupprocess systpayrollgroupprocess6:systpayrollgroupprocesses) {
			BeanUtils.copyProperties(systpayrollgroupprocess6,systpayrollgroupprocessAll);
			systpayrollgroupprocessAllService.save(systpayrollgroupprocessAll);
		}

		sql="Update systPayrollGroupProcess  Set Initialized=0 Where GID="+gid+";";
		systpaystditemMapper.listSalaryTemplate3(sql);

		return R.ok("操作成功！");
	}




}
