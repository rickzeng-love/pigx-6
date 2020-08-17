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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtpracRegister;
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.mapper.EtpracRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.EtprobRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtprobRegister;
import com.pig4cloud.pigx.admin.service.EtprobRegisterService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 试用期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:18:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etprobregister" )
@Api(value = "etprobregister", tags = "试用期变动管理")
public class EtprobRegisterController {

    private final  EtprobRegisterService etprobRegisterService;
    private final EtpracRegisterMapper etpracRegisterMapper;
    private final SystmessageMapper systmessageMapper;
    private final EtemployeeService etemployeeService;
    private final EtprobRegisterMapper etprobRegisterMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etprobRegister 试用期变动
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtprobRegisterPage(Page page, EtprobRegister etprobRegister) {
        return R.ok(etprobRegisterService.page(page, Wrappers.query(etprobRegister)));
    }


    /**
     * 通过id查询试用期变动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etprobRegisterService.getById(id));
    }

    /**
     * 新增试用期变动
     * @param etprobRegister 试用期变动
     * @return R
     */
    @ApiOperation(value = "新增试用期变动", notes = "新增试用期变动")
    @SysLog("新增试用期变动" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etprobregister_add')" )
    public R save(@RequestBody EtprobRegister etprobRegister) {
        return R.ok(etprobRegisterService.save(etprobRegister));
    }

 /*   *//**
     * 修改试用期变动
     * @param etprobRegister 试用期变动
     * @return R
     */
    @ApiOperation(value = "修改试用期变动", notes = "修改试用期变动")
    @SysLog("修改试用期变动" )
	@PostMapping("/updateById")
    public R updateById(@RequestBody EtprobRegister etprobRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtprobRegister etprobRegister2 = new EtprobRegister();
		etprobRegister2.setSpno(etprobRegister.getSpno());
		UpdateWrapper<EtprobRegister> queryWrapper = new UpdateWrapper();
		queryWrapper.eq("id",etprobRegister.getId());
		queryWrapper.eq("corpcode",corpcode);
        return R.ok(etprobRegisterService.update(etprobRegister2,queryWrapper));
    }

	@ApiOperation(value = "修改试用期变动", notes = "修改试用期变动")
	@SysLog("修改试用期变动" )
	@PostMapping("/update")
	public R update(@RequestBody EtprobRegister etprobRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtprobRegister etprobRegister2 = new EtprobRegister();
		etprobRegister2.setSpno(etprobRegister.getSpno());
		UpdateWrapper<EtprobRegister> queryWrapper = new UpdateWrapper();
		queryWrapper.eq("id",etprobRegister.getId());
		queryWrapper.eq("corpcode",corpcode);
		return R.ok(etprobRegisterService.update(etprobRegister2,queryWrapper));
	}

    /**
     * 通过id删除试用期变动
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除试用期变动", notes = "通过id删除试用期变动")
    @SysLog("通过id删除试用期变动" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etprobregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etprobRegisterService.removeById(id));
    }*/
	/**
	 * 实习期员工转正
	 * @param etemployee 转正登记表
	 * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
	 */
	@ApiOperation(value = "试用期员工转正", notes = "试用期员工转正")
	@SysLog("试用期员工转正" )
	@PostMapping("/saveEtprobRegisterForZhuangZheng")
	public R saveEtprobRegisterForZhuangZheng(@RequestBody @Valid EtprobRegister etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}
		//操作用户信息
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		Integer userId = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();

		//实习转正日期不能晚于今天!
		String effectdate = etemployee.getEffectdate();
		if(effectdate.compareTo(currentTime)<0){
			return R.ok("实习转正日期不能晚于今天!");
		}
		//实习转正日期不能早于入职日期!
		String joindate = etemployee.getJoindate();
		if(joindate.compareTo(currentTime)<0){
			return R.ok("实习转正日期不能早于入职日期!");
		}

		//试用转正1：员工不为试用状态,不能做试用相关的操作
		//试用转正：2.员工已经离职,不能进行试用相关操作；；
		List employeeList1 = etprobRegisterService.getEtEmployeeNotProbForProbation(etemployee);
		if (!(employeeList1.size() > 0)) {
			return R.ok(null,"员工不为试用状态,不能做试用相关的操作！");
		}
		List employeeList2 = etprobRegisterService.getEtEmployeeEmpLeaveForProbation(etemployee);
		if (employeeList2.size() > 0) {
			return R.ok(null,"员工已经离职,不能进行试用相关操作!");
		}

		etemployee.setRegby(userId);
		etemployee.setRegdate(currentTime);
		etemployee.setInitialized(0);
		return R.ok(etprobRegisterService.save(etemployee));
	}

	/**
	 * 试用期转正
	 * @param etprobRegister
	 * @return R
	 */
	@ApiOperation(value = "试用期转正", notes = "试用期转正")
	@SysLog("试用期转正" )
	@PostMapping("/eSPProbStart")
	@Transactional
	public R eSPProbStart(@RequestBody  EtprobRegister etprobRegister) {
		//审批标志
		Integer isneedaudit = etprobRegister.getIsneedaudit();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		Etemployee etemployee = etemployeeService.getById(etprobRegister.getEid());
		//BeanUtils.copyProperties(etemployee,etprobRegister);

		BeanUtil.copyProperties(etemployee,etprobRegister, CopyOptions.create().setIgnoreNullValue(true));
		//申请日期 离职日期 离职类型 离职原因  离职资料 离职备注
		//applydate  LeaveDate LeaveType LeaveReason Remark
		etprobRegister.setType(2);//转正
		etprobRegister.setRegdate(currentTime);
		etprobRegister.setRegby(pigxUser.getId());
		etprobRegister.setInitializedby(pigxUser.getId());
		etprobRegister.setCorpcode(corpcode);
		etprobRegister.setCorpid(corpid);
		//人事录入信息 确认标识 0:人事录入信息，1：人事确认录入，2：放弃入职
		etprobRegister.setInitialized(0);
		etprobRegister.setInitializedtime(currentTime);
		if(!StringUtils.isEmpty(isneedaudit)){
			if(isneedaudit==0){
				//提交审核标志 提交审核 1审核通过 2不通过3不审核0
				etprobRegister.setSubmit(1);
				etprobRegister.setSubmitby(pigxUser.getId());
				etprobRegister.setSubmittime(currentTime);
			}else{
				//不需要审核
				etprobRegister.setSubmit(0);
			}
		}else{
			//不需要审核
			etprobRegister.setSubmit(0);
		}

		etprobRegisterService.save(etprobRegister);

		//校验变动
		Map map = new HashMap();
		map.put("id",etprobRegister.getId());
		Map map2 = new HashMap();
		map2.put("id",etprobRegister.getId());
		String message = null;
		//调用确认的存储过程
		etprobRegisterMapper.eSPProbCheckSub(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功

		if (msgid == 0) {
			if(!StringUtils.isEmpty(isneedaudit)){
				if(isneedaudit!=0){
					//调用生效的存储过程
					map2.put("userid",pigxUser.getId());
					etprobRegisterMapper.eSPProbStart(map2);
					int mesid = (Integer) map2.get("result");
					systmessage = systmessageMapper.selectById(mesid);
					message = systmessage.getTitle();
					if(mesid==0){
						return R.ok(etprobRegister,message);
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return R.failed(message);
					}
				}
			}


		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			systmessage = systmessageMapper.selectById(msgid);
			if (systmessage != null) {
				message = systmessage.getTitle();
				return R.failed(message);
			}
		}


		return R.ok(etprobRegister,"执行成功！");
	}
}
