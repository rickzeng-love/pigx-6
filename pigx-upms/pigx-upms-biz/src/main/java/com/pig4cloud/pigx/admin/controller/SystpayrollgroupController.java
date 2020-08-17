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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.api.vo.TreeUtil;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.mapper.SystpayrollgroupMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.service.impl.SystpayrollgroupServiceImpl;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 薪资账套
 *
 * @author gaoxiao
 * @date 2020-06-09 16:21:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollgroup" )
@Api(value = "systpayrollgroup", tags = "薪资账套管理")
public class SystpayrollgroupController {

    private final  SystpayrollgroupService systpayrollgroupService;
    private final SystpayrollgroupMapper systpayrollgroupMapper;
    private final CtcdPayrollperiodService ctcdPayrollperiodService;
	private final SystpayrollgroupprocessService systpayrollgroupprocessService;
	private final SystpayrollitemService systpayrollitemService;
	private final CtcdAgentgidUserService ctcdAgentgidUserService;
	private final SystmessageMapper systmessageMapper;
	private final SystpayrollitemCommonService systpayrollitemCommonService;
    //private final CtemployeeService ctemployeeService;
    //private final CtsalaryRegisterService ctsalaryRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollgroup 薪资账套
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollgroupPage(Page page, Systpayrollgroup systpayrollgroup) {
        return R.ok(systpayrollgroupService.page(page, Wrappers.query(systpayrollgroup)));
    }

	/**
	 * 查询账套
	 * @return
	 */
	@ApiOperation(value = "查询账套列表", notes = "查询账套列表")
	@PostMapping("/getSystpayrollgroupList" )
	public R getSystpayrollgroupList( Systpayrollgroup systpayrollgroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		systpayrollgroup.setCorpcode(corpcode);
		return R.ok(systpayrollgroupService.list(Wrappers.query(systpayrollgroup)));
	}


    /**
     * 通过id查询薪资账套
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systpayrollgroupService.getById(id));
    }

    /**
     * 新增薪资账套      @PreAuthorize("@pms.hasPermission('admin_systpayrollgroup_add')" )
     * @param systpayrollgroup 薪资账套
     * @return R
     */
    @ApiOperation(value = "新增薪资账套", notes = "新增薪资账套")
    @SysLog("新增薪资账套" )
    @PostMapping("/save")
	@Transactional
    public R save(@RequestBody Systpayrollgroup systpayrollgroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//免费版本 只能添加一个账套
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		List list = systpayrollgroupService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("免费版本只能添加一个薪资套！如需多个账套，请联系客服！");
		}
		systpayrollgroup.setCorpcode(corpcode);
		systpayrollgroup.setCorpid(pigxUser.getCorpid());
		systpayrollgroupService.save(systpayrollgroup);
		///生成累计计税期间
		QueryWrapper<CtcdPayrollperiod> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("corpcode",corpcode);
		CtcdPayrollperiod ctcdPayrollperiod2  = ctcdPayrollperiodService.getOne(queryWrapper1);
		if(StringUtils.isEmpty(ctcdPayrollperiod2)){
			CtcdPayrollperiod ctcdPayrollperiod = new CtcdPayrollperiod();
			ctcdPayrollperiod.setCorpcode(corpcode);
			ctcdPayrollperiod.setCorpid(pigxUser.getCorpid());
			ctcdPayrollperiod.setGid(systpayrollgroup.getId());
			ctcdPayrollperiod.setTitle("开始累积月份");
			ctcdPayrollperiod.setMonflag(1);
			ctcdPayrollperiod.setStartmonth(12);
			ctcdPayrollperiodService.save(ctcdPayrollperiod);
		}
		String currentTime = DateUtils.getTimeString();
/*		//生成薪资期间
		QueryWrapper<Systpayrollgroupprocess> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systpayrollgroupprocess systpayrollgroupprocess2  = systpayrollgroupprocessService.getOne(queryWrapper2);
		if(StringUtils.isEmpty(systpayrollgroupprocess2)) {
			Systpayrollgroupprocess systpayrollgroupprocess = new Systpayrollgroupprocess();
			systpayrollgroupprocess.setCorpcode(corpcode);
			systpayrollgroupprocess.setCorpid(pigxUser.getCorpid());
			systpayrollgroupprocess.setTitle(systpayrollgroup.getTitle());
			systpayrollgroupprocess.setGid(systpayrollgroup.getId());
			systpayrollgroupprocess.setTerm(currentTime);
			systpayrollgroupprocessService.save(systpayrollgroupprocess);
		}*/
		//生成薪资项 固定项
		SystpayrollitemCommon systpayrollitemCommon = new SystpayrollitemCommon();
		systpayrollitemCommon.setGid(systpayrollgroup.getId());
		systpayrollitemCommon.setCorpcode(corpcode);
		systpayrollitemCommon.setCorpid(pigxUser.getCorpid());
		systpayrollitemCommon.setTitle("实发工资");
		systpayrollitemCommon.setDigital(2);
		systpayrollitemCommon.setDigitaltype(1);
		systpayrollitemCommon.setIftype(0);
		systpayrollitemCommon.setType(11);
		systpayrollitemCommon.setColname("FactPay");
		systpayrollitemCommon.setIsdisabled(0);
		systpayrollitemCommonService.save(systpayrollitemCommon);

		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setGid(systpayrollgroup.getId());
		systpayrollitem.setCorpcode(corpcode);
		systpayrollitem.setCorpid(pigxUser.getCorpid());
		systpayrollitem.setTitle("实发工资");
		systpayrollitem.setDigital(2);
		systpayrollitem.setDigitaltype(1);
		systpayrollitem.setIftype(0);
		systpayrollitem.setType(11);
		systpayrollitem.setColname("FactPay");
		systpayrollitem.setIsdisabled(0);
		systpayrollitem.setParentid(systpayrollitemCommon.getId());
		systpayrollitemService.save(systpayrollitem);


		//权限
		//ctcd_agentgid_user
		CtcdAgentgidUser ctcdAgentgidUser =new CtcdAgentgidUser();
		ctcdAgentgidUser.setGid(systpayrollgroup.getId());
		ctcdAgentgidUser.setUserid(pigxUser.getId());
		ctcdAgentgidUserService.save(ctcdAgentgidUser);
		/*Map map = new HashMap();
		map.put("gid",systpayrollgroup.getId());
		map.put("corpid",pigxUser.getCorpid());
		systpayrollgroupMapper.sysSP_CreatePayrollGroupCustom(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		String message = null;
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok(systpayrollgroup,"保存成功！");
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}*/
		return R.ok("成功！");

    }


	/**
	 * @return R
	 */
	@ApiOperation(value = "生成公式", notes = "生成公式")
	@SysLog("生成公式" )
	@PostMapping("/sysSPCreatePayrollItemFormulaCustom")
	@Transactional
	public R sysSPCreatePayrollItemFormulaCustom() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if(StringUtils.isEmpty(systpayrollgroup)){
			return R.failed("请维护薪资账号！");
		}
		Integer gid = systpayrollgroup.getId();
		Map map = new HashMap();
		map.put("gid",systpayrollgroup.getId());
		map.put("corpid",pigxUser.getCorpid());
		systpayrollgroupMapper.sysSP_CreatePayrollItemFormulaCustom(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		String message = null;
		systmessage = systmessageMapper.selectById(msgid);
		message = systmessage.getTitle();
		//如果成功
		if (msgid == 0) {
			return R.ok("生成成功！");
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.failed(message);
		}

	}


	/**
     * 修改薪资账套     @PreAuthorize("@pms.hasPermission('admin_systpayrollgroup_edit')" )
     * @param systpayrollgroup 薪资账套
     * @return R
     */
    @ApiOperation(value = "修改薪资账套", notes = "修改薪资账套")
    @SysLog("修改薪资账套" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody Systpayrollgroup systpayrollgroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",systpayrollgroup.getId());
		List list = systpayrollgroupService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("薪资套名称重复，请核实！");
		}
		systpayrollgroup.setCorpcode(corpcode);
		UpdateWrapper<Systpayrollgroup> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",systpayrollgroup.getId());
        return R.ok(systpayrollgroupService.updateById(systpayrollgroup));
    }

/*    *//**
     * 通过id删除薪资账套
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除薪资账套", notes = "通过id删除薪资账套")
    @SysLog("通过id删除薪资账套" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroup_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systpayrollgroupService.removeById(id));
    }*/
/*	public R removeById222(Page page,Systpayrollgroup systpayrollgroup) {
		List resultList = new ArrayList(3);
    	List list = new ArrayList(3);
    	List list2 = null;

    	for(int i=0;list.size()>0;i++){
    		String type = "";
    		Map map  = new HashMap();
			list2 = systpayrollgroupService.list(Wrappers.query(systpayrollgroup));
			map.put("xinagzixiang",list2);
			map.put("titletype","基本工资");
			resultList.add(i,map);
		}
		return R.ok(systpayrollgroupService.removeById(id));
	}*/

	/**
	 * 获取薪资项树状列表
	 * @return
	 */
	@ApiOperation(value = "获取薪资项树状列表", notes = "获取薪资项树状列表")
	@PostMapping("/getSystpayrollgroupTree" )
	public TreeOrg getSystpayrollgroupTree() {
		Systpayrollgroup systpayrollgroup = new Systpayrollgroup();
		PigxUser pigxUser = SecurityUtils.getUser();
		systpayrollgroup.setCorpcode(pigxUser.getCorpcode());
		List systpayrollgroupList  = systpayrollgroupMapper.listSystpayrollgroupTree(systpayrollgroup);
		TreeOrg treeOrg = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("root");
		treeOrg.setTitle("薪资项");
		return TreeUtil.findChildren2(treeOrg,systpayrollgroupList);
	}
}
