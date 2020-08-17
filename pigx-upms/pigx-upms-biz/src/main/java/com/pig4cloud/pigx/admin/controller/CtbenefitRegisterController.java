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
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.mapper.CtbenefitRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtbenefitRegister;
import com.pig4cloud.pigx.admin.service.CtbenefitRegisterService;
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

import java.util.HashMap;
import java.util.Map;


/**
 * 福利开启登记
 *
 * @author XP
 * @date 2020-06-11 15:05:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbenefitregister")
@Api(value = "ctbenefitregister", tags = "福利开启登记管理")
public class CtbenefitRegisterController {

	private final CtbenefitRegisterService ctbenefitRegisterService;
	private final CtbenefitRegisterMapper ctbenefitRegisterMapper;
	private final SystmessageMapper systmessageMapper;

	/**
	 * 分页查询
	 *
	 * @param page              分页对象
	 * @param ctbenefitRegister 福利开启登记
	 * @return
	 */
/*	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getCtbenefitRegisterPage(Page page, CtbenefitRegister ctbenefitRegister) {
		return R.ok(ctbenefitRegisterService.page(page, Wrappers.query(ctbenefitRegister)));
	}*/

	/**
	 * 分页查询
	 *
	 * @param page              分页对象
	 * @param ctbenefitRegister 福利开启登记
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getCtbenefitRegisterPage")
	public R getCtbenefitRegisterPage(Page page,@RequestBody(required = false) CtbenefitRegister ctbenefitRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		if(StringUtils.isEmpty(ctbenefitRegister)){
			ctbenefitRegister = new CtbenefitRegister();
		}
		ctbenefitRegister.setCorpcode(corpcode);
		//ctbenefitRegister.setCorpid(corpid);
		String name = ctbenefitRegister.getName();
		Integer depid = ctbenefitRegister.getDepid();
		Integer jobid = ctbenefitRegister.getJobid();
		QueryWrapper<CtbenefitRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		if (!StringUtils.isEmpty(name)) {
			queryWrapper.like("name", name);
		}
		if (!StringUtils.isEmpty(depid)) {
			queryWrapper.eq("depid", depid);
		}
		if (!StringUtils.isEmpty(jobid)) {
			queryWrapper.eq("jobid", jobid);
		}
		return R.ok(ctbenefitRegisterService.page(page, queryWrapper));
	}


	/**
	 * 通过id查询福利开启登记
	 *
	 * @param id id
	 * @return R
	 */
/*	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(ctbenefitRegisterService.getById(id));
	}*/

	/**
	 * 新增福利开启登记
	 *
	 * @param ctbenefitRegister 福利开启登记
	 * @return R
	 */
/*	@ApiOperation(value = "新增福利开启登记", notes = "新增福利开启登记")
	@SysLog("新增福利开启登记")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_ctbenefitregister_add')")
	public R save(@RequestBody CtbenefitRegister ctbenefitRegister) {
		return R.ok(ctbenefitRegisterService.save(ctbenefitRegister));
	}*/

	/**
	 * 修改福利开启登记
	 *
	 * @param ctbenefitRegister 福利开启登记
	 * @PreAuthorize("@pms.hasPermission('admin_ctbenefitregister_edit')")
	 * @return R
	 */
	@ApiOperation(value = "修改福利开启登记", notes = "修改福利开启登记")
	@SysLog("修改福利开启登记")
	@PostMapping("/update")
	public R updateById(@RequestBody CtbenefitRegister ctbenefitRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		int regby = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctbenefitRegister.setRegby(regby);
		ctbenefitRegister.setRegdate(currentTime);
		UpdateWrapper<CtbenefitRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctbenefitRegister.getId());
		return R.ok(ctbenefitRegisterService.update(ctbenefitRegister,updateWrapper));
	}
/*

	*/
/**
	 * 通过id删除福利开启登记
	 *
	 * @param id id
	 * @PreAuthorize("@pms.hasPermission('admin_ctbenefitregister_del')")
	 * @return R
	 */

	@ApiOperation(value = "通过id删除福利开启登记", notes = "通过id删除福利开启登记")
	@SysLog("通过id删除福利开启登记")
	@GetMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		QueryWrapper<CtbenefitRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("id",id);

		return R.ok(ctbenefitRegisterService.remove(queryWrapper));
	}


	/**
	 * 通过存储过程开启社保
	 *
	 * */
	@ApiOperation(value = "通过存储过程开启社保", notes = "通过存储过程开启社保")
	@SysLog("通过存储过程开启社保")
	@PostMapping("/benefitStart")
	@Transactional
	public R benefitStart(@RequestBody CtbenefitRegister ctbenefitRegister) {
		String message = null;
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		Map paramMap = new HashMap();
		paramMap.put("userid", userid);
		paramMap.put("id",ctbenefitRegister.getId());
		Map paramMap2 = new HashMap();
		paramMap2.put("userid", userid);
		paramMap2.put("id",ctbenefitRegister.getId());
		//调用确认的存储过程
		ctbenefitRegisterMapper.cSP_BenefitCheck(paramMap);
		int msgid = (Integer) paramMap.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			ctbenefitRegisterMapper.cSP_BenefitStart(paramMap2);
			int mesid = (Integer) paramMap2.get("result");
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

		return R.ok(message);
	}

}
