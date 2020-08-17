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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtleaveRegister;
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.mapper.EtpracRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtpracRegister;
import com.pig4cloud.pigx.admin.service.EtpracRegisterService;
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
 * 实习期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:12:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etpracregister" )
@Api(value = "etpracregister", tags = "实习期变动管理")
public class EtpracRegisterController {

    private final  EtpracRegisterService etpracRegisterService;
    private final EtemployeeService etemployeeService;
    private final EtpracRegisterMapper etpracRegisterMapper;
    private final SystmessageMapper systmessageMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etpracRegister 实习期变动
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtpracRegisterPage(Page page, EtpracRegister etpracRegister) {
        return R.ok(etpracRegisterService.page(page, Wrappers.query(etpracRegister)));
    }


    /**
     * 通过id查询实习期变动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etpracRegisterService.getById(id));
    }

    /**
     * 新增实习期变动
     * @param etpracRegister 实习期变动
     * @return R
     */
    @ApiOperation(value = "新增实习期变动", notes = "新增实习期变动")
    @SysLog("新增实习期变动" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etpracregister_add')" )
    public R save(@RequestBody EtpracRegister etpracRegister) {
        return R.ok(etpracRegisterService.save(etpracRegister));
    }

/*    *//**
     * 修改实习期变动   @PreAuthorize("@pms.hasPermission('admin_etpracregister_edit')" )
     * @param etpracRegister 实习期变动
     * @return R
     */
    @ApiOperation(value = "修改实习期变动", notes = "修改实习期变动")
	@SysLog("修改实习期变动" )
	@PostMapping("/updateById")
	public R updateById(@RequestBody EtpracRegister etpracRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtpracRegister etpracRegister2 = new EtpracRegister();
		etpracRegister2.setSpno(etpracRegister.getSpno());
		UpdateWrapper<EtpracRegister> queryWrapper = new UpdateWrapper();
		queryWrapper.eq("id",etpracRegister.getId());
		queryWrapper.eq("corpcode",corpcode);
		return R.ok(etpracRegisterService.update(etpracRegister2,queryWrapper));
	}

	@ApiOperation(value = "修改实习期变动", notes = "修改实习期变动")
	@SysLog("修改实习期变动" )
	@PostMapping("/update")
	public R update(@RequestBody EtpracRegister etpracRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtpracRegister etpracRegister2 = new EtpracRegister();
		etpracRegister2.setSpno(etpracRegister.getSpno());
		UpdateWrapper<EtpracRegister> queryWrapper = new UpdateWrapper();
		queryWrapper.eq("eid",etpracRegister.getEid());
		queryWrapper.eq("corpcode",corpcode);
		return R.ok(etpracRegisterService.update(etpracRegister2,queryWrapper));
	}
    /**
     * 通过id删除实习期变动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除实习期变动", notes = "通过id删除实习期变动")
    @SysLog("通过id删除实习期变动" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etpracregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etpracRegisterService.removeById(id));
    }

	/**
	 * 实习期员工转正
	 * @param etemployee 转正登记表
	 * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
	 */
	@ApiOperation(value = "实习期员工转正", notes = "实习期员工转正")
	@SysLog("实习期员工转正" )
	@PostMapping("/saveEtpracRegisterForZhuangZheng")
    public R saveEtpracRegisterForZhuangZheng(@RequestBody @Valid EtpracRegister etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}
		//操作用户信息
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		Integer userId = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		etemployee.setCorpcode(corpcode);
		etemployee.setCompid(compid);
		etemployee.setSubmittime(currentTime);   //登记时间
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

		//实习转正：1.员工不为实习状态,不能进行实习相关操作；
		//实习转正：2.员工已经离职,不能进行实习相关操作；
		//实习转正：3.员工不能同时存在两种及以上的状态；
		List employeeList1 = etpracRegisterService.getEtEmployeeNoPracticeForPractice(etemployee);
		if (!(employeeList1.size() > 0)) {
			return R.ok(null,"员工不为实习状态,不能进行实习相关操作!");
		}
		List employeeList2 = etpracRegisterService.getEtEmployeeEmpLeaveForPractice(etemployee);
		if (!(employeeList2.size() > 0)) {
			return R.ok(null,"员工已经离职,不能进行实习相关操作!");
		}
		List employeeList3 = etpracRegisterService.getEtEmployeeTwoStatusForPractice(etemployee);
		if (!(employeeList3.size() > 0)) {
			return R.ok(null,"员工不能同时存在两种及以上的状态!");
		}
		etemployee.setRegby(userId);
		etemployee.setRegdate(currentTime);
		etemployee.setInitialized(0);
		return R.ok(etpracRegisterService.save(etemployee));
	}

	/**
	 * 实习期转正
	 * @param etpracRegister
	 * @return R
	 */
	@ApiOperation(value = "实习期转正", notes = "实习期转正")
	@SysLog("实习期转正" )
	@PostMapping("/eSPPracStart")
	@Transactional
	public R eSPLeaveStart(@RequestBody  EtpracRegister etpracRegister) {
		//审批标志
		Integer isneedaudit = etpracRegister.getIsneedaudit();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		Etemployee etemployee = etemployeeService.getById(etpracRegister.getEid());
		BeanUtils.copyProperties(etemployee,etpracRegister);
		//申请日期 离职日期 离职类型 离职原因  离职资料 离职备注
		//applydate  LeaveDate LeaveType LeaveReason Remark
		etpracRegister.setType(2);//转正
		etpracRegister.setRegdate(currentTime);
		etpracRegister.setRegby(pigxUser.getId());
		etpracRegister.setInitializedby(pigxUser.getId());
		//人事录入信息 确认标识 0:人事录入信息，1：人事确认录入，2：放弃入职
		etpracRegister.setInitialized(0);
		etpracRegister.setInitializedtime(currentTime);
		if(!StringUtils.isEmpty(isneedaudit)){
			if(isneedaudit==0){
				//提交审核标志 提交审核 1审核通过 2不通过3不审核0
				etpracRegister.setSubmit(1);
				etpracRegister.setSubmitby(pigxUser.getId());
				etpracRegister.setSubmittime(currentTime);
			}else{
				//不需要审核
				etpracRegister.setSubmit(0);
			}
		}

		etpracRegisterService.save(etpracRegister);

		//校验变动
		Map map = new HashMap();
		map.put("id",etpracRegister.getId());
		Map map2 = new HashMap();
		map2.put("id",etpracRegister.getId());
		String message = null;
		//调用确认的存储过程
		etpracRegisterMapper.eSPPracCheckSub(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			map2.put("userid",pigxUser.getId());
			etpracRegisterMapper.eSPPracStart(map2);
			int mesid = (Integer) map2.get("result");
			systmessage = systmessageMapper.selectById(mesid);
			message = systmessage.getTitle();
			if(mesid==0){
				return R.ok(message);
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed(message);
			}

		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			systmessage = systmessageMapper.selectById(msgid);
			if (systmessage != null) {
				message = systmessage.getTitle();
				return R.failed(message);
			}
		}


		return R.ok("操作成功！");
	}


}
