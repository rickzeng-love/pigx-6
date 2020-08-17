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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtchangeorgAllMapper;
import com.pig4cloud.pigx.admin.mapper.EtchangeorgRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.util.CommonUtil;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.EtchangeorgRegisterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 调动登记
 *
 * @author gaoxiao
 * @date 2020-04-14 19:05:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etchangeorgregister" )
@Api(value = "etchangeorgregister", tags = "调动登记管理")
public class EtchangeorgRegisterController {

    private final  EtchangeorgRegisterService etchangeorgRegisterService;
    private final EtchangeorgRegisterMapper etchangeorgRegisterMapper;
    private final EtemployeeService etemployeeService;
    private final SystmessageMapper systmessageMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etchangeorgRegister 调动登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R page(Page page, EtchangeorgRegister etchangeorgRegister) {
        return R.ok(etchangeorgRegisterService.page(page, Wrappers.query(etchangeorgRegister)));
    }


    /**
     * 通过id查询调动登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etchangeorgRegisterService.getById(id));
    }

    /**
     * 新增调动登记
     * @param etchangeorgRegister 调动登记       @PreAuthorize("@pms.hasPermission('admin_etchangeorgregister_add')" )
     * @return R
     */
    @ApiOperation(value = "新增调动登记", notes = "新增调动登记")
    @SysLog("新增调动登记" )
    @PostMapping
    public R save(@RequestBody EtchangeorgRegister etchangeorgRegister) {
        return R.ok(etchangeorgRegisterService.save(etchangeorgRegister));
    }

    /**
     * 修改调动登记     @PreAuthorize("@pms.hasPermission('admin_etchangeorgregister_edit')" )
     * @param etchangeorgRegister 调动登记
     * @return R
     */
    @ApiOperation(value = "修改调动登记", notes = "修改调动登记")
    @SysLog("修改调动登记" )
    @PutMapping
    public R updateById(@RequestBody EtchangeorgRegister etchangeorgRegister) {
        return R.ok(etchangeorgRegisterService.updateById(etchangeorgRegister));
    }

    /**
     * 通过id删除调动登记     @PreAuthorize("@pms.hasPermission('admin_etchangeorgregister_del')" )
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除调动登记", notes = "通过id删除调动登记")
    @SysLog("通过id删除调动登记" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etchangeorgRegisterService.removeById(id));
    }
	/**
	 * 新增待入职员工
	 * @param etemployee 员工信息表
	 * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
	 */
	@ApiOperation(value = "新增调动员工", notes = "新增调动员工")
	@SysLog("新增调动员工" )
	@PostMapping("/saveEtchangeorgRegister")
	public R saveEtchangeorgRegister(@RequestBody @Valid EtchangeorgRegister etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}
		//操作人信息
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		Integer userId = pigxUser.getId();
		Integer currtneteid = pigxUser.getEid();
		String currentTime = DateUtils.getTimeString();
		etemployee.setRegdate(currentTime);
		//
		//直接主管或职能上级不能是自己!!
		Integer reportto = etemployee.getReportto();
		Integer eid = etemployee.getEid();
		if(reportto==eid){
			return R.ok("直接主管或职能上级不能是自己!");
		}


		//调动生效日期不能晚于今天!
		String effectdate = etemployee.getEffectdate();
		if(effectdate.compareTo(currentTime)<0){
			return R.ok("调动生效日期不能晚于今天!");
		}
		//调动生效日期不能早于入职日期!
		String joindate = etemployee.getJoindate();
		if(joindate.compareTo(currentTime)<0){
			return R.ok("调动生效日期不能早于入职日期!");
		}
		//1.员工已经离职，不能做变动
		List employeeList1 = etchangeorgRegisterService.getEtEmployeeUnSignDel(etemployee);
		if(employeeList1.size()>0){
			return R.ok(null,"员工已经离职，不能做变动!");
		}
		//2.新公司已经失效
		List employeeList2 = etchangeorgRegisterService.getEtEmployeeNewCompIsDisableForChange(etemployee);
		if(employeeList2.size()>0){
			return R.ok(null,"新公司已经失效!");
		}
		//3.新公司还未成立
		List employeeList3 = etchangeorgRegisterService.getEtEmployeeNewCompUnSetupForChange(etemployee);
		if(employeeList3.size()>0){
			return R.ok(null,"新公司还未成立!");
		}
		//4.新部门已经失效
		List employeeList4 = etchangeorgRegisterService.getEtEmployeeNewDepIsDisableForChange(etemployee);
		if(employeeList4.size()>0){
			return R.ok(null,"新部门已经失效!");
		}
		//5.新部门还未成立
		List employeeList5 = etchangeorgRegisterService.getEtEmployeeNewDepUnSetupForChange(etemployee);
		if(employeeList5.size()>0){
			return R.ok(null,"新部门还未成立!");
		}
		//6.所选新部门不在新公司下
		List employeeList6 = etchangeorgRegisterService.getEtEmployeeNewCompNewDepForChange(etemployee);
		if(!(employeeList6.size()>0)){
			return R.ok(null,"所选新部门不在新公司下!");
		}
		//7.所选新岗位已经失效
		List employeeList7 = etchangeorgRegisterService.getEtEmployeeNewJobIsDisableForChange(etemployee);
		if(employeeList7.size()>0){
			return R.ok(null,"所选新岗位已经失效!");
		}
		//8.所选新岗位还未成立
		List employeeList8 = etchangeorgRegisterService.getEtEmployeeNewJobUnSetupForChange(etemployee);
		if(employeeList8.size()>0){
			return R.ok(null,"所选新岗位还未成立!");
		}
		//9.所选新岗位不在新部门下
		List employeeList9 = etchangeorgRegisterService.getEtEmployeeNewJobNewDepForChange(etemployee);
		if(!(employeeList9.size()>0)){
			return R.ok(null,"所选新岗位不在新部门下!");
		}
		//10.新行政上级已经离职
		List employeeList10 = etchangeorgRegisterService.getEtEmployeeReportToLeaveForChange(etemployee);
		if(employeeList10.size()>0){
			return R.ok(null,"新上级已经离职!");
		}
		//11.生效日期不能在最近一次变动之前
		List employeeList11 = etchangeorgRegisterService.getEtEmployeeEffectDateForChange(etemployee);
		if(!(employeeList11.size()>0)){
			return R.ok(null,"生效日期不能在最近一次变动之前!");
		}
		//12.该员工有兼职岗位，先终止兼职
		List employeeList12 = etchangeorgRegisterService.getEtEmployeePartJobForChange(etemployee);
		if(employeeList12.size()>0){
			return R.ok(null,"该员工有兼职岗位，先终止兼职!");
		}
		etemployee.setRegby(userId);
		etemployee.setRegdate(currentTime);
		etemployee.setInitialized(0);
		return R.ok(etchangeorgRegisterService.save(etemployee));
	}

	/**
	 * 新增待入职员工
	 * @param etemployee 员工信息表
	 * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
	 */
	@ApiOperation(value = "确认调动", notes = "确认调动")
	@SysLog("确认调动" )
	@PostMapping("/updateEmployeeForDD")
	public R updateEmployeeForDD(@RequestBody @Valid EtchangeorgRegister etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}

		//1.员工已经离职，不能做变动
		List employeeList1 = etchangeorgRegisterService.getEtEmployeeUnSignDel(etemployee);
		if(employeeList1.size()>0){
			return R.ok(null,"员工已经离职，不能做变动!");
		}


		return R.ok(etchangeorgRegisterService.save(etemployee));
	}

	/**
	 * 调动管理分页查询
	 * @param page 分页对象
	 * @param etchangeorgRegister 调动管理分页查询
	 * @return
	 */
	@ApiOperation(value = "调动管理分页查询", notes = "调动管理分页查询")
	@PostMapping("/getEtchangeorgRegisterPage" )
	public R getEtchangeorgRegisterPage(Page page, EtchangeorgRegister etchangeorgRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etchangeorgRegister.setCorpcode(corpcode);
		return R.ok(etchangeorgRegisterMapper.listEtchangeorgRegisterPage(page,etchangeorgRegister));
	}

	/**
	 * 员工调动
	 * @param etchangeorgRegister
	 * @return R
	 */
	@ApiOperation(value = "员工调动", notes = "员工调动")
	@SysLog("员工调动" )
	@PostMapping("/eSPChangeOrgStart")
	@Transactional
	public R eSPChangeOrgStart(@RequestBody  EtchangeorgRegister etchangeorgRegister) {
		//审批标志
		Etemployee etemployee = etemployeeService.getById(etchangeorgRegister.getEid());
		if(etemployee.getEmpstatus()==6){//6	Loff	离职		0
			return R.failed("员工已经离职，不能再做调动！");
		}
		Integer isneedaudit = etchangeorgRegister.getIsneedaudit();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		BeanUtil.copyProperties(etemployee,etchangeorgRegister, CopyOptions.create().setIgnoreNullValue(true));
		etchangeorgRegister.setRegdate(currentTime);
		etchangeorgRegister.setRegby(pigxUser.getId());
		etchangeorgRegister.setInitializedby(pigxUser.getId());
		//人事录入信息 确认标识 0:人事录入信息，1：人事确认录入，2：放弃入职
		etchangeorgRegister.setInitialized(0);
		etchangeorgRegister.setInitializedtime(currentTime);
		etchangeorgRegister.setType(1);
		if(!StringUtils.isEmpty(isneedaudit)){
			if(isneedaudit==0){
				//提交审核标志 提交审核 1审核通过 2不通过3不审核0
				etchangeorgRegister.setSubmit(1);
				etchangeorgRegister.setSubmitby(pigxUser.getId());
				etchangeorgRegister.setSubmittime(currentTime);
			}else{
				//不需要审核
				etchangeorgRegister.setSubmit(0);
			}
		}else{
			//不需要审核
			etchangeorgRegister.setSubmit(0);
		}

		etchangeorgRegisterService.save(etchangeorgRegister);

		//校验变动
		Map map = new HashMap();
		map.put("id",etchangeorgRegister.getId());
		Map map2 = new HashMap();
		map2.put("id",etchangeorgRegister.getId());
		String message = null;
		//调用确认的存储过程
		etchangeorgRegisterMapper.eSPChangeOrgChecksub(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			if(isneedaudit!=0){
				map2.put("userid",pigxUser.getId());
				etchangeorgRegisterMapper.eSPChangeOrgStart(map2);
				int mesid = (Integer) map2.get("result");
				systmessage = systmessageMapper.selectById(mesid);
				message = systmessage.getTitle();
				if(mesid!=0){
					return R.failed(message);
				}else{
					return R.ok(message);
				}

			}

		} else {
			systmessage = systmessageMapper.selectById(msgid);

			if (systmessage != null) {
				message = systmessage.getTitle();
				return R.failed(message);
			}
		}

		return R.ok("操作成功！");
	}

}
