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
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.CtsalaryRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import javafx.beans.binding.DoubleExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 薪资开启
 *
 * @author gaoxiao
 * @date 2020-06-11 11:29:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctsalaryregister" )
@Api(value = "ctsalaryregister", tags = "薪资开启管理")
public class CtsalaryRegisterController {

	private final CtsalaryRegisterService ctsalaryRegisterService;
	private final CtsalaryAllService ctsalaryAllService;
	private final CtsalaryRegisterMapper ctsalaryRegisterMapper;
	private final SystmessageService systmessageService;
	private final CtemployeeService ctemployeeService;
	private final CtchangesalaryRegisterService ctchangesalaryRegisterService;
	private final CtchangesalaryAllService ctchangesalaryAllService;
	private final CtstandardService ctstandardService;
	private final SystpaystditemService systpaystditemService;
	private final SystpaystditemMapper systpaystditemMapper;
	private final SystpayrollgroupService systpayrollgroupService;
	private final CtsalaryStandardRegisterService ctsalaryStandardRegisterService;
	private final CtchangesalaryStandardRegisterService ctchangesalaryStandardRegisterService;
	private final SystpayrollitemService systpayrollitemService;
	private final SystpayiteminputvalueService systpayiteminputvalueService;
	private final SystpayiteminputvalueAllService systpayiteminputvalueAllService;
	private final SystpayrollgroupprocessService systpayrollgroupprocessService;

	/**
	 * 分页查询
	 *
	 * @param page             分页对象
	 * @param ctsalaryRegister 薪资开启
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/page")
	public R getCtsalaryRegisterPage(Page page, CtsalaryRegister ctsalaryRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtsalaryRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", corpcode);
		String name = ctsalaryRegister.getName();
		if (name != null && name != "") {
			queryWrapper.like("title", name);
		}
		Integer depid = ctsalaryRegister.getDepid();
		Integer jobid = ctsalaryRegister.getJobid();
		if (StringUtils.isEmpty(depid)) {
			queryWrapper.eq("jobid", depid);
		}
		if (StringUtils.isEmpty(jobid)) {
			queryWrapper.eq("jobid", jobid);
		}
		return R.ok(ctsalaryRegisterService.page(page, Wrappers.query(ctsalaryRegister)));
	}

	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "获取薪资开启登记(动态)", notes = "获取薪资开启登记(动态)")
	@PostMapping("/getCtsalaryRegisterPageSql")
	public R getCtsalaryRegisterPageSql(Page page,@RequestBody(required = false) CtsalaryRegister ctsalaryRegister) {
		if(StringUtils.isEmpty(ctsalaryRegister)){
			ctsalaryRegister = new CtsalaryRegister();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		Integer type = null;
		//获取当前薪资薪资
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpcode(corpcode);

		List systpaystditemList = systpaystditemService.list(Wrappers.query(systpaystditem).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		Integer ssrid = null;
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select A1.id,A1.eid,A1.badge,A1.name,A1.compid,A1.depid,A1.jobid,A1.empstatus,A1.empgrade,A1.joindate,A1.workcity,A1.paystatus,A1.salarystatus,A1.paymode,A1.costid,A1.bankid,A1.bankno,A1.openbankemp,A1.bankid2,A1.bankno2,A1.openbankemp2,A1.salarytype,A1.salarygrade,A1.salarykind,A1.salarycity,A1.effectdate,A1.calcway,A1.remark ";
		String sql2 = "(select EID ";
		String sql5 = "(select a.eid ";
		for (int i = 0; i < systpaystditemList.size(); i++) {

			item = (Systpaystditem) systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			sql1 = sql1 + "," + "" + "A2." + title;

			sql2 = sql2 + ", case when paystdItemID = " + paystdItemID + " then xvalue else '' end " + title;
			sql5 = sql5 + ","+" max(a."+title+")"+" "+title;
		}
		long current = page.getCurrent();
		long size = page.getSize();
		sql1 = sql1 + " from ctSalary_Register A1 ";
		sql2 = sql2 + "  from ctsalary_standard_register  Where  corpcode = '" + corpcode +"') a group by eid ) A2";
		sql5 = sql5 + " from " +sql2;

		sql = sql1 + " left join " + sql5 + " on A1.EID = A2.EID ";

		sql = sql + " where A1.corpcode ='" + corpcode + "'";
		String name = ctsalaryRegister.getName();
		Integer depid = ctsalaryRegister.getDepid();
		Integer jobid = ctsalaryRegister.getJobid();
		if(!StringUtils.isEmpty(name)){
			sql = sql + " and A1.name like '%"+name+"'%";
		}
		if(!StringUtils.isEmpty(depid)){
			sql = sql + " and A1.depid ="+depid;
		}
		if(!StringUtils.isEmpty(jobid)){
			sql = sql + " and  A1.jobid ="+jobid;
		}


		sql = sql + " limit " + (current - 1) * size + "," + size;
		List<LinkedHashMap> list = systpaystditemMapper.listSalaryTemplate(sql);
		QueryWrapper<Systpaystditem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", corpcode);
		List stditem = systpaystditemService.list(queryWrapper);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());
		Map resultMap = new HashMap();
		resultMap.put("resultpage", resultpage);
		resultMap.put("stditem", stditem);
		return R.ok(resultMap);

	}

	/**
	 * 通过id查询薪资开启
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(ctsalaryRegisterService.getById(id));
	}

	/**
	 * 新增薪资开启
	 *
	 * @param ctsalaryRegister 薪资开启
	 * @return R
	 */
	@ApiOperation(value = "新增薪资开启", notes = "新增薪资开启")
	@SysLog("新增薪资开启")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_ctsalaryregister_add')")
	public R save(@RequestBody CtsalaryRegister ctsalaryRegister) {
		return R.ok(ctsalaryRegisterService.save(ctsalaryRegister));
	}

	/**
	 * 修改薪资开启      @PreAuthorize("@pms.hasPermission('admin_ctsalaryregister_edit')" )
	 *
	 * @param ctsalaryRegister 薪资开启
	 * @return R
	 */
	@ApiOperation(value = "修改薪资开启", notes = "修改薪资开启")
	@SysLog("修改薪资开启")
	@PostMapping("/updateById")
	public R updateById(@RequestBody CtsalaryRegister ctsalaryRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<CtsalaryRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode", corpcode);
		updateWrapper.eq("id", ctsalaryRegister.getId());
		return R.ok(ctsalaryRegisterService.update(ctsalaryRegister, updateWrapper));
	}
	/*
	 *//**
	 * 通过id删除薪资开启     @PreAuthorize("@pms.hasPermission('admin_ctsalaryregister_del')" )
	 * @param id id
	 * @return R
	 */
    @ApiOperation(value = "通过id删除薪资开启", notes = "通过id删除薪资开启")
    @SysLog("通过id删除薪资开启" )
    @GetMapping("/update/{id}" )
    public R removeById(@PathVariable Integer id) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		QueryWrapper<CtsalaryRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("id",id);
        return R.ok(ctsalaryRegisterService.remove(queryWrapper));
    }

	/**
	 * 薪资开启登记
	 *
	 * @return
	 */
	@ApiOperation(value = "薪资开启登记处理", notes = "薪资开启登记处理")
	@PostMapping("/salaryStart")
	@Transactional
	public R salaryStart(@RequestBody Map map) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		CtsalaryRegister ctsalaryRegister =null;
		Integer userid = pigxUser.getId();
		Map paramMap = new HashMap();
		paramMap.put("userid", userid);
		paramMap.put("id", map.get("id").toString());
		Integer id = Integer.parseInt(map.get("id").toString());

		ctsalaryRegister = ctsalaryRegisterService.getById(id);
		if (StringUtils.isEmpty(ctsalaryRegister)) {
			return R.failed("在信息不存在，请核实！");
		}
		Integer eid = ctsalaryRegister.getEid();

		Ctemployee ctemployee = ctemployeeService.getById(eid);
		if(!StringUtils.isEmpty(ctemployee)){
			return R.failed("此员工薪资已开启过，请核实！");
		}
		QueryWrapper<CtsalaryAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("eid",eid);
		List list = ctsalaryAllService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("此员工薪资已开启过，请核实！");
		}
		Integer result2 = 0;
		ctsalaryRegisterMapper.cSP_SalaryStart(paramMap);
		result2 = (Integer) paramMap.get("result");
		Systmessage systmessage2 = systmessageService.getById(result2);
		String message12 = systmessage2.getTitle();
		if (result2 == 0) {
			return R.ok(message12);
		} else {
			return R.failed(message12);
		}


	}

	/**
	 * 薪资开启登记
	 *
	 * @return
	 */
	@ApiOperation(value = "薪资开启登记", notes = "薪资开启登记")
	@PostMapping("/salaryCheck")
	@Transactional
	public R salaryCheck(@RequestBody Map map) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		CtsalaryRegister ctsalaryRegister = new CtsalaryRegister();
		Integer userid = pigxUser.getId();
		Map paramMap = new HashMap();
		paramMap.put("userid", userid);
		paramMap.put("id", map.get("id").toString());
		UpdateWrapper<CtsalaryRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", map.get("id").toString());
		updateWrapper.eq("corpcode", corpcode);


		//QueryWrapper<CtsalaryRegister> queryWrapper = new QueryWrapper<>();
		//queryWrapper.eq("id",ctsalaryRegister.getId());
		CtsalaryRegister ctsalaryRegister1 = ctsalaryRegisterService.getById(map.get("id").toString());
		if (StringUtils.isEmpty(ctsalaryRegister1)) {
			return R.failed("在信息不存在，请核实！");
		}
		BeanUtils.copyProperties(ctsalaryRegister1, ctsalaryRegister);

		ctsalaryRegister.setBadge(map.get("badge") != null ? map.get("badge").toString() : null);
		ctsalaryRegister.setName(map.get("name") != null ? map.get("name").toString() : null);
		ctsalaryRegister.setCompid(map.get("compid") != null ? Integer.parseInt(map.get("compid").toString()) : null);
		ctsalaryRegister.setDepid(map.get("depid") != null ? Integer.parseInt(map.get("depid").toString()) : null);
		ctsalaryRegister.setJobid(map.get("jobid") != null ? Integer.parseInt(map.get("jobid").toString()) : null);
		ctsalaryRegister.setEmpstatus(map.get("empstatus") != null ? Integer.parseInt(map.get("empstatus").toString()) : null);
		ctsalaryRegister.setEmpgrade(map.get("empgrade") != null ? Integer.parseInt(map.get("empgrade").toString()) : null);
		ctsalaryRegister.setJoindate(map.get("joindate") != null ? map.get("joindate").toString() : null);
		ctsalaryRegister.setWorkcity(map.get("workcity") != null ? Integer.parseInt(map.get("workcity").toString()) : null);
		ctsalaryRegister.setPaystatus(map.get("paystatus") != null ? Integer.parseInt(map.get("paystatus").toString()) : null);
		ctsalaryRegister.setSalarystatus(map.get("salarystatus") != null ? Integer.parseInt(map.get("salarystatus").toString()) : null);
		ctsalaryRegister.setPaymode(map.get("paymode") != null ? Integer.parseInt(map.get("paymode").toString()) : null);
		ctsalaryRegister.setCostid(map.get("costid") != null ? Integer.parseInt(map.get("costid").toString()) : null);
		ctsalaryRegister.setBankid(map.get("bankid") != null ? Integer.parseInt(map.get("bankid").toString()) : null);
		ctsalaryRegister.setBankno(map.get("bankno") != null ? map.get("bankno").toString() : null);
		ctsalaryRegister.setOpenbankemp(map.get("openbankemp") != null ? map.get("openbankemp").toString() : null);
		ctsalaryRegister.setBankid2(map.get("bankid2") != null ? Integer.parseInt(map.get("bankid2").toString()) : null);
		ctsalaryRegister.setBankno2(map.get("bankno2") != null ? map.get("bankno2").toString() : null);
		ctsalaryRegister.setOpenbankemp2(map.get("openbankemp2") != null ? map.get("openbankemp2").toString() : null);
		ctsalaryRegister.setSalarytype(map.get("salarytype") != null ? Integer.parseInt(map.get("salarytype").toString()) : null);
		ctsalaryRegister.setSalarygrade(map.get("salarygrade") != null ? Integer.parseInt(map.get("salarygrade").toString()) : null);
		ctsalaryRegister.setSalarykind(map.get("salarykind") != null ? Integer.parseInt(map.get("salarykind").toString()) : null);
		ctsalaryRegister.setSalarycity(map.get("salarycity") != null ? Integer.parseInt(map.get("salarycity").toString()) : null);
		ctsalaryRegister.setEffectdate(map.get("effectdate") != null ? map.get("effectdate").toString() : null);
		ctsalaryRegister.setRemark(map.get("remark") != null ? map.get("remark").toString() : null);
		ctsalaryRegister.setCalcway(map.get("calcway") != null ? Integer.parseInt(map.get("calcway").toString()) : null);
		ctsalaryRegister.setEmptype(map.get("emptype") != null ? Integer.parseInt(map.get("emptype").toString()) : null);
		ctsalaryRegister.setEid(map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);

		ctsalaryRegisterService.update(ctsalaryRegister, updateWrapper);

		//systpaystditemService
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if (StringUtils.isEmpty(systpayrollgroup)) {
			return R.failed("请维护薪资账号！");
		}
		Integer gid = systpayrollgroup.getId();
		QueryWrapper<Systpaystditem> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode", corpcode);
		queryWrapper2.eq("gid", gid);
		List<Systpaystditem> list = systpaystditemService.list(queryWrapper2);
		Systpaystditem systpaystditem = null;
		Integer paystditemid = null;
		String svalue = null;
		CtsalaryStandardRegister ctsalaryStandardRegister = null;
		//	String ss = map.get("3").toString();
		for (int i = 0; i < list.size(); i++) {
			ctsalaryStandardRegister = new CtsalaryStandardRegister();
			systpaystditem = (Systpaystditem) list.get(i);
			paystditemid = systpaystditem.getId();
			//svalue = map.get("\""+paystditemid+"\"")!=null ? map.get("\""+paystditemid+"\"").toString():null;
			svalue = map.get(String.valueOf(paystditemid)) != null ? map.get(String.valueOf(paystditemid)).toString() : null;
			ctsalaryStandardRegister.setCorpcode(corpcode);
			ctsalaryStandardRegister.setCorpid(pigxUser.getCorpid());
			ctsalaryStandardRegister.setEid(map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);
			ctsalaryStandardRegister.setPaystditemid(paystditemid);
			ctsalaryStandardRegister.setGid(gid);
			if (!StringUtils.isEmpty(svalue)) {
				ctsalaryStandardRegister.setXvalue(Double.parseDouble(svalue));
			} else {
				ctsalaryStandardRegister.setXvalue(0.0);
			}
			QueryWrapper<CtsalaryStandardRegister> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("corpcode", corpcode);
			queryWrapper1.eq("eid", map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);
			queryWrapper1.eq("gid", gid);
			queryWrapper1.eq("paystditemid", paystditemid);

			List list1 = ctsalaryStandardRegisterService.list(queryWrapper1);
			if(list1.size()>0){

				UpdateWrapper<CtsalaryStandardRegister> updateWrapper1 = new UpdateWrapper<>();
				updateWrapper1.eq("corpcode", corpcode);
				updateWrapper1.eq("eid", map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);
				updateWrapper1.eq("gid", gid);
				updateWrapper1.eq("paystditemid", paystditemid);
				//ctsalaryStandardRegisterService.save(ctsalaryStandardRegister);
				ctsalaryStandardRegisterService.update(ctsalaryStandardRegister, updateWrapper1);
			}else{
				ctsalaryStandardRegisterService.save(ctsalaryStandardRegister);
			}

		}
		//处理确认按钮paystditemid = {Integer@20122} 6
		ctsalaryRegisterMapper.cSP_SalaryCheck(paramMap);
		Integer result = (Integer) paramMap.get("result");
		Integer result2 = null;
		//如果成功，调用处理存储过程
		if (result == 0) {

			Systmessage systmessage2 = systmessageService.getById(result);
			String message12 = systmessage2.getTitle();
			return R.ok(message12);
		} else {
			Systmessage systmessage1 = systmessageService.getById(result);
			String message1 = systmessage1.getTitle();
			return R.failed(message1);
		}
	}

	/**
	 * 薪资调整
	 * @param ctchangesalaryRegister 薪资
	 * @return
	 *//*
	@ApiOperation(value = "薪资调整", notes = "薪资调整")
	@PostMapping("/changSalary" )
	@Transactional
	public R salaryStart(@RequestBody(required = false) CtchangesalaryRegister ctchangesalaryRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();

		//存历史
		Integer eid = ctchangesalaryRegister.getEid();
		Ctemployee ctemployee1 = ctemployeeService.getById(eid);
		if(StringUtils.isEmpty(ctemployee1)){
			return R.failed("此信息不存在，请核实！");
		}
		QueryWrapper<CtchangesalaryRegister> ctchangesalaryRegisterQueryWrapper = new QueryWrapper<>();
		ctchangesalaryRegisterQueryWrapper.eq("eid",ctchangesalaryRegister.getEid());
		ctchangesalaryRegisterQueryWrapper.eq("corpcode",corpcode);
		List list = ctchangesalaryRegisterService.list(ctchangesalaryRegisterQueryWrapper);
		if(list.size()>0){
			ctchangesalaryRegisterService.remove(ctchangesalaryRegisterQueryWrapper);
		}

		//CtchangesalaryAll ctchangesalaryAll = new CtchangesalaryAll();
		//BeanUtils.copyProperties(ctemployee1,ctchangesalaryAll);
		//插入变动登记表 主要需要插入old字段
		ctchangesalaryRegister.setA1Old(ctemployee1.getA1());
		ctchangesalaryRegister.setA2Old(ctemployee1.getA2());
		ctchangesalaryRegister.setA3Old(ctemployee1.getA3());
		ctchangesalaryRegister.setA4Old(ctemployee1.getA4());
		ctchangesalaryRegister.setA5Old(ctemployee1.getA5());
		ctchangesalaryRegister.setBankid2Old(ctemployee1.getBankid2());
		ctchangesalaryRegister.setBankidOld(ctemployee1.getBankid());
		ctchangesalaryRegister.setBanknoOld(ctemployee1.getBankno());
		ctchangesalaryRegister.setBankno2Old(ctemployee1.getBankno2());
		ctchangesalaryRegister.setCalcwayOld(ctemployee1.getCalcway());
		ctchangesalaryRegister.setCostidOld(ctemployee1.getCostid());
		ctchangesalaryRegister.setOpenbankemp2Old(ctemployee1.getOpenbankemp2());
		ctchangesalaryRegister.setOpenbankempOld(ctemployee1.getOpenbankemp());
		ctchangesalaryRegister.setPaymodeOld(ctemployee1.getPaymode());
		ctchangesalaryRegister.setSalarycity(ctemployee1.getSalarycity());
		ctchangesalaryRegister.setSalarygradeOld(ctemployee1.getSalarygrade());
		ctchangesalaryRegister.setSalarykindOld(ctemployee1.getSalarykind());
		ctchangesalaryRegister.setSalarystatusOld(ctemployee1.getSalarystatus());
		ctchangesalaryRegister.setSalarytypeOld(ctemployee1.getSalarytype());
		ctchangesalaryRegister.setCorpcode(corpcode);
		ctchangesalaryRegister.setCorpid(pigxUser.getCorpid());
		ctchangesalaryRegisterService.save(ctchangesalaryRegister);
		Integer id = ctchangesalaryRegister.getId();



		//systpaystditemService
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请维护薪资账号！");
		}
		Integer gid = systpayrollgroup.getId();
		QueryWrapper<Systpaystditem> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("gid",gid);
		List<Systpaystditem> list2 = systpaystditemService.list(queryWrapper2);
		Systpaystditem systpaystditem = null;
		Integer paystditemid = null;
		String svalue =null;
		CtchangesalaryStandardRegister ctchangesalaryStandardRegister = null;
		//	String ss = map.get("3").toString();
		for(int i=0;i<list.size();i++){
			ctchangesalaryStandardRegister = new CtchangesalaryStandardRegister();
			systpaystditem = (Systpaystditem)list.get(i);
			paystditemid = systpaystditem.getId();
			//svalue = map.get("\""+paystditemid+"\"")!=null ? map.get("\""+paystditemid+"\"").toString():null;
			svalue = map.get(String.valueOf(paystditemid))!=null ? map.get(String.valueOf(paystditemid)).toString():null;
			ctchangesalaryStandardRegister.setCorpcode(corpcode);
			ctchangesalaryStandardRegister.setCorpid(pigxUser.getCorpid());
			ctchangesalaryStandardRegister.setEid(map.get("eid")!=null ? Integer.parseInt(map.get("eid").toString()):null);
			ctchangesalaryStandardRegister.setPaystditemid(paystditemid);
			ctchangesalaryStandardRegister.setGid(gid);
			if(!StringUtils.isEmpty(svalue)){
				ctchangesalaryStandardRegister.setXvalue(Double.parseDouble(svalue));
			}else{
				ctchangesalaryStandardRegister.setXvalue(0.0);
			}
			UpdateWrapper<CtchangesalaryStandardRegister> updateWrapper1 = new UpdateWrapper<>();
			updateWrapper1.eq("corpcode",corpcode);
			updateWrapper1.eq("eid",map.get("eid")!=null ? Integer.parseInt(map.get("eid").toString()):null);
			updateWrapper1.eq("gid",gid);
			//ctsalaryStandardRegisterService.save(ctsalaryStandardRegister);
			ctchangesalaryStandardRegisterService.saveOrUpdate(ctchangesalaryStandardRegister,updateWrapper1);
		}





		Map paramMap = new HashMap();
		paramMap.put("userid",userid);
		paramMap.put("id",id);
		//处理确认按钮
		ctsalaryRegisterMapper.cSP_ChangeSalaryCheck(paramMap);
		Map resultMap2 = null;
		Integer result = (Integer) paramMap.get("result");
		Integer result2 = null;
		//如果成功，调用处理存储过程
		if(result==0){
			ctsalaryRegisterMapper.cSP_ChangeSalaryStart(paramMap);
			result2 = (Integer) paramMap.get("result");
			Systmessage systmessage2 = systmessageService.getById(result2);
			String message12 =  systmessage2.getTitle();
			return R.ok(message12);
		}else{
			Systmessage systmessage1 = systmessageService.getById(result);
			String message1 =  systmessage1.getTitle();
			return R.failed(message1);
		}

	}
*/

	/**
	 * 薪资调整
	 *
	 * @param map 薪资
	 * @return
	 */
	@ApiOperation(value = "薪资调整", notes = "薪资调整")
	@PostMapping("/changSalary")
	@Transactional
	public R changSalary(@RequestBody(required = false) Map map) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		//存历史
		String seid = map.get("eid").toString();
		Integer eid = Integer.parseInt(seid);
		Ctemployee ctemployee1 = ctemployeeService.getById(eid);
		if (StringUtils.isEmpty(ctemployee1)) {
			return R.failed("此信息不存在，请核实！");
		}
		QueryWrapper<CtchangesalaryRegister> ctchangesalaryRegisterQueryWrapper = new QueryWrapper<>();
		ctchangesalaryRegisterQueryWrapper.eq("eid", eid);
		ctchangesalaryRegisterQueryWrapper.eq("corpcode", corpcode);
		List list = ctchangesalaryRegisterService.list(ctchangesalaryRegisterQueryWrapper);
		if (list.size() > 0) {
			ctchangesalaryRegisterService.remove(ctchangesalaryRegisterQueryWrapper);
		}

		//CtchangesalaryAll ctchangesalaryAll = new CtchangesalaryAll();
		//BeanUtils.copyProperties(ctemployee1,ctchangesalaryAll);
		//插入变动登记表 主要需要插入old字段
		CtchangesalaryRegister ctchangesalaryRegister = new CtchangesalaryRegister();

		ctchangesalaryRegister.setBadge(map.get("badge") != null ? map.get("badge").toString() : null);
		ctchangesalaryRegister.setName(map.get("name") != null ? map.get("name").toString() : null);
		ctchangesalaryRegister.setCompid(map.get("compid") != null ? Integer.parseInt(map.get("compid").toString()) : null);
		ctchangesalaryRegister.setDepid(map.get("depid") != null ? Integer.parseInt(map.get("depid").toString()) : null);
		ctchangesalaryRegister.setJobid(map.get("jobid") != null ? Integer.parseInt(map.get("jobid").toString()) : null);
		ctchangesalaryRegister.setEmpstatus(map.get("empstatus") != null ? Integer.parseInt(map.get("empstatus").toString()) : null);
		ctchangesalaryRegister.setEmpgrade(map.get("empgrade") != null ? Integer.parseInt(map.get("empgrade").toString()) : null);
		ctchangesalaryRegister.setJoindate(map.get("joindate") != null ? map.get("joindate").toString() : null);
		ctchangesalaryRegister.setWorkcity(map.get("workcity") != null ? Integer.parseInt(map.get("workcity").toString()) : null);
		ctchangesalaryRegister.setPaystatus(map.get("paystatus") != null ? Integer.parseInt(map.get("paystatus").toString()) : null);
		ctchangesalaryRegister.setSalarystatus(map.get("salarystatus") != null ? Integer.parseInt(map.get("salarystatus").toString()) : null);
		ctchangesalaryRegister.setPaymode(map.get("paymode") != null ? Integer.parseInt(map.get("paymode").toString()) : null);
		ctchangesalaryRegister.setCostid(map.get("costid") != null ? Integer.parseInt(map.get("costid").toString()) : null);
		ctchangesalaryRegister.setBankid(map.get("bankid") != null ? Integer.parseInt(map.get("bankid").toString()) : null);
		ctchangesalaryRegister.setBankno(map.get("bankno") != null ? map.get("bankno").toString() : null);
		ctchangesalaryRegister.setOpenbankemp(map.get("openbankemp") != null ? map.get("openbankemp").toString() : null);
		ctchangesalaryRegister.setBankid2(map.get("bankid2") != null ? Integer.parseInt(map.get("bankid2").toString()) : null);
		ctchangesalaryRegister.setBankno2(map.get("bankno2") != null ? map.get("bankno2").toString() : null);
		ctchangesalaryRegister.setOpenbankemp2(map.get("openbankemp2") != null ? map.get("openbankemp2").toString() : null);
		ctchangesalaryRegister.setSalarytype(map.get("salarytype") != null ? Integer.parseInt(map.get("salarytype").toString()) : null);
		ctchangesalaryRegister.setSalarygrade(map.get("salarygrade") != null ? Integer.parseInt(map.get("salarygrade").toString()) : null);
		ctchangesalaryRegister.setSalarykind(map.get("salarykind") != null ? Integer.parseInt(map.get("salarykind").toString()) : null);
		ctchangesalaryRegister.setSalarycity(map.get("salarycity") != null ? Integer.parseInt(map.get("salarycity").toString()) : null);
		ctchangesalaryRegister.setEffectdate(map.get("effectdate") != null ? map.get("effectdate").toString() : null);
		ctchangesalaryRegister.setRemark(map.get("remark") != null ? map.get("remark").toString() : null);
		ctchangesalaryRegister.setCalcway(map.get("calcway") != null ? Integer.parseInt(map.get("calcway").toString()) : null);
		ctchangesalaryRegister.setEmptype(map.get("emptype") != null ? Integer.parseInt(map.get("emptype").toString()) : null);
		ctchangesalaryRegister.setEid(map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);

		ctchangesalaryRegister.setA1Old(ctemployee1.getA1());
		ctchangesalaryRegister.setA2Old(ctemployee1.getA2());
		ctchangesalaryRegister.setA3Old(ctemployee1.getA3());
		ctchangesalaryRegister.setA4Old(ctemployee1.getA4());
		ctchangesalaryRegister.setA5Old(ctemployee1.getA5());
		ctchangesalaryRegister.setBankid2Old(ctemployee1.getBankid2());
		ctchangesalaryRegister.setBankidOld(ctemployee1.getBankid());
		ctchangesalaryRegister.setBanknoOld(ctemployee1.getBankno());
		ctchangesalaryRegister.setBankno2Old(ctemployee1.getBankno2());
		ctchangesalaryRegister.setCalcwayOld(ctemployee1.getCalcway());
		ctchangesalaryRegister.setCostidOld(ctemployee1.getCostid());
		ctchangesalaryRegister.setOpenbankemp2Old(ctemployee1.getOpenbankemp2());
		ctchangesalaryRegister.setOpenbankempOld(ctemployee1.getOpenbankemp());
		ctchangesalaryRegister.setPaymodeOld(ctemployee1.getPaymode());
		ctchangesalaryRegister.setSalarycity(ctemployee1.getSalarycity());
		ctchangesalaryRegister.setSalarygradeOld(ctemployee1.getSalarygrade());
		ctchangesalaryRegister.setSalarykindOld(ctemployee1.getSalarykind());
		ctchangesalaryRegister.setSalarystatusOld(ctemployee1.getSalarystatus());
		ctchangesalaryRegister.setSalarytypeOld(ctemployee1.getSalarytype());
		ctchangesalaryRegister.setCorpcode(corpcode);
		ctchangesalaryRegister.setCorpid(pigxUser.getCorpid());
		ctchangesalaryRegisterService.save(ctchangesalaryRegister);
		Integer id = ctchangesalaryRegister.getId();

		//systpaystditemService
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if (StringUtils.isEmpty(systpayrollgroup)) {
			return R.failed("请维护薪资账号！");
		}
		Integer gid = systpayrollgroup.getId();
		QueryWrapper<Systpaystditem> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode", corpcode);
		queryWrapper2.eq("gid", gid);
		List<Systpaystditem> list2 = systpaystditemService.list(queryWrapper2);
		Systpaystditem systpaystditem = null;
		Integer paystditemid = null;
		String svalue = null;
		CtchangesalaryStandardRegister ctchangesalaryStandardRegister = null;
		//	String ss = map.get("3").toString();
		for (int i = 0; i < list2.size(); i++) {
			ctchangesalaryStandardRegister = new CtchangesalaryStandardRegister();
			systpaystditem = (Systpaystditem) list2.get(i);
			paystditemid = systpaystditem.getId();
			//svalue = map.get("\""+paystditemid+"\"")!=null ? map.get("\""+paystditemid+"\"").toString():null;
			svalue = map.get(String.valueOf(paystditemid)) != null ? map.get(String.valueOf(paystditemid)).toString() : null;
			ctchangesalaryStandardRegister.setCorpcode(corpcode);
			ctchangesalaryStandardRegister.setCorpid(pigxUser.getCorpid());
			ctchangesalaryStandardRegister.setEid(map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);
			ctchangesalaryStandardRegister.setPaystditemid(paystditemid);
			ctchangesalaryStandardRegister.setGid(gid);
			ctchangesalaryStandardRegister.setSeqid(id);
			ctchangesalaryStandardRegister.setRegtime(currentTime);
			if (!StringUtils.isEmpty(svalue)) {
				ctchangesalaryStandardRegister.setXvalue(Double.parseDouble(svalue));
			} else {
				ctchangesalaryStandardRegister.setXvalue(0.0);
			}
			//UpdateWrapper<CtchangesalaryStandardRegister> updateWrapper1 = new UpdateWrapper<>();
			//updateWrapper1.eq("corpcode",corpcode);
			//updateWrapper1.eq("eid",map.get("eid")!=null ? Integer.parseInt(map.get("eid").toString()):null);
			//updateWrapper1.eq("gid",gid);
			//ctsalaryStandardRegisterService.save(ctsalaryStandardRegister);
			ctchangesalaryStandardRegisterService.save(ctchangesalaryStandardRegister);
		}
		Integer id2= ctchangesalaryStandardRegister.getId();
		String sql = "";
		Map paramMap = new HashMap();
		paramMap.put("userid", userid);
		paramMap.put("id", id);
		//处理确认按钮
		ctsalaryRegisterMapper.cSP_ChangeSalaryCheck(paramMap);
		Map resultMap2 = null;
		Integer result = (Integer) paramMap.get("result");
		Integer result2 = null;
		//如果成功，调用处理存储过程
		if (result == 0) {

			sql = " Update ctEmployee a,ctChangeSalary_Register b  " +
					"  Set a.SalaryGrade = b.SalaryGrade, " +
					" a.SalaryType = b.SalaryType, " +
					" a.SalaryKind = b.SalaryKind, " +
					" a.SalaryCity = b.SalaryCity, " +
					" a.CalcWay = b.CalcWay, " +
					" a.PayMode=b.PayMode, " +
					" a.SalaryStatus=b.SalaryStatus, " +
					" a.PayStatus=b.PayStatus, " +
					" a.CostID=b.CostID, " +
					" a.BankID=b.BankID, " +
					"  a.BankNo=b.BankNo, " +
					" a.OpenBankEmp=b.OpenBankEmp, " +
					" a.BankID2=b.BankID2, " +
					" a.BankNo2=b.BankNo2, " +
					"  a.effectdate = b.effectdate, " +
					"  a.remark = b.remark, " +
					" a.OpenBankEmp2=b.OpenBankEmp2 " +
					" Where a.EID=b.EID and b.ID="+id;
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete a From ctStandard a,ctChangeSalary_Register b Where a.EID=b.EID And b.ID="+id;
			systpaystditemMapper.listSalaryTemplate3(sql);
			sql = "Insert Into ctStandard(CorpID,EID,PayStdItemID,BeginDate,EndDate,xValue,RegTime,GID,Remark,corpcode) " +
					" Select a.CorpID,a.EID,a.PayStdItemID,a.BeginDate,a.EndDate,xValue,a.RegTime,a.GID,a.Remark,a.corpcode " +
					" From ctChangeSalary_Standard_Register a " +
					" Where a.SeqID="+id;
			systpaystditemMapper.listSalaryTemplate3(sql);

			CtchangesalaryAll ctchangesalaryAll = new CtchangesalaryAll();
			BeanUtils.copyProperties(ctchangesalaryRegister,ctchangesalaryAll);
			ctchangesalaryAll.setId(id);
			ctchangesalaryAll.setRegby(userid);
			ctchangesalaryAll.setRegdate(currentTime);
			ctchangesalaryAllService.save(ctchangesalaryAll);

			sql = "Delete From ctChangeSalary_Register Where ID="+id;
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = " Insert Into ctChangeSalary_Standard_All(ID,CorpID,SeqID,EID,PayStdItemID,BeginDate,EndDate,xValue,OldxValue,RegTime,GID,Remark,corpcode) " +
					" Select ID,CorpID,SeqID,EID,PayStdItemID,BeginDate,EndDate,xValue,OldxValue,RegTime,GID,Remark,corpcode " +
					" From ctChangeSalary_Standard_Register a " +
					" Where a.SeqID="+id;
			systpaystditemMapper.listSalaryTemplate3(sql);

			sql = "Delete FROM ctChangeSalary_Standard_Register Where SeqID="+id;
			systpaystditemMapper.listSalaryTemplate3(sql);

		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			Systmessage systmessage1 = systmessageService.getById(result);
			String message1 = systmessage1.getTitle();
			return R.failed(message1);
		}
		return R.ok("操作成功");
	}

	/**
	 * 应加应扣
	 *
	 * @param map 薪资
	 * @return
	 */
	@ApiOperation(value = "应加应扣", notes = "应加应扣")
	@PostMapping("/savesystpayiteminputvalue")
	@Transactional
	public R savesystpayiteminputvalue(@RequestBody(required = false) Map map) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		//存历史
		String seid = map.get("eid").toString();
		Integer eid = Integer.parseInt(seid);
		Ctemployee ctemployee1 = ctemployeeService.getById(eid);
		if (StringUtils.isEmpty(ctemployee1)) {
			return R.failed("此信息不存在，请核实！");
		}


		Integer id = null;

		//systpaystditemService
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if (StringUtils.isEmpty(systpayrollgroup)) {
			return R.failed("请维护薪资账号！");
		}

		QueryWrapper<Systpayrollgroupprocess> queryWrapper3 = new QueryWrapper<>();
		queryWrapper3.eq("corpcode", corpcode);
		Systpayrollgroupprocess systpayrollgroupprocess = systpayrollgroupprocessService.getOne(queryWrapper3);

		Integer gid = systpayrollgroup.getId();
		QueryWrapper<Systpayrollitem> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode", corpcode);
		queryWrapper2.eq("gid", gid);
		queryWrapper2.eq("iftype",1);
		List<Systpayrollitem> list2 = systpayrollitemService.list(queryWrapper2);
		Systpayrollitem systpayrollitem = null;
		Integer paystditemid = null;
		String svalue = null;
		Systpayiteminputvalue systpayiteminputvalue1 = null;
		//	String ss = map.get("3").toString();
		for (int i = 0; i < list2.size(); i++) {
			systpayiteminputvalue1 = new Systpayiteminputvalue();
			systpayrollitem = (Systpayrollitem) list2.get(i);
			paystditemid = systpayrollitem.getId();
			QueryWrapper<Systpayiteminputvalue> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("corpid", pigxUser.getCorpid());
			queryWrapper1.eq("eid", eid);
			queryWrapper1.eq("PayItem", paystditemid);
			List<Systpayiteminputvalue> list1 = systpayiteminputvalueService.list(queryWrapper1);
			SystpayiteminputvalueAll systpayiteminputvalueAll = null;
			for (int gg = 0; gg < list1.size(); gg++) {

				systpayiteminputvalueAll = new SystpayiteminputvalueAll();
				Systpayiteminputvalue systpayiteminputvalue = list1.get(gg);
				BeanUtils.copyProperties(systpayiteminputvalue, systpayiteminputvalueAll);
				systpayiteminputvalueAllService.save(systpayiteminputvalueAll);
			}
			systpayiteminputvalueService.remove(queryWrapper1);


			//svalue = map.get("\""+paystditemid+"\"")!=null ? map.get("\""+paystditemid+"\"").toString():null;
			svalue = map.get(String.valueOf(paystditemid)) != null ? map.get(String.valueOf(paystditemid)).toString() : null;
			systpayiteminputvalue1.setCorpcode(corpcode);
			systpayiteminputvalue1.setCorpid(pigxUser.getCorpid());
			systpayiteminputvalue1.setEid(map.get("eid") != null ? Integer.parseInt(map.get("eid").toString()) : null);
			systpayiteminputvalue1.setPayitem(paystditemid);
			systpayiteminputvalue1.setGid(gid);
			//systpayiteminputvalue1.setSeqid(id);
			systpayiteminputvalue1.setTerm(systpayrollgroupprocess.getTerm());
			if (!StringUtils.isEmpty(svalue)) {
				systpayiteminputvalue1.setFactvalue(Double.parseDouble(svalue));
			} else {
				systpayiteminputvalue1.setFactvalue(0.0);
			}

			systpayiteminputvalueService.save(systpayiteminputvalue1);
		}

		return R.ok("保存成功！");


	}

}


