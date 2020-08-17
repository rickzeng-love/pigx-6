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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtchangeorgRegister;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.mapper.EtleaveRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.service.SystmessageService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtleaveRegister;
import com.pig4cloud.pigx.admin.service.EtleaveRegisterService;
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
 * 退休离职变动表
 *
 * @author gaoxiao
 * @date 2020-04-10 20:51:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etleaveregister" )
@Api(value = "etleaveregister", tags = "退休离职变动表管理")
public class EtleaveRegisterController {

    private final  EtleaveRegisterService etleaveRegisterService;
    private final EtleaveRegisterMapper etleaveRegisterMapper;
    private final SystmessageMapper systmessageMapper;
	private final EtemployeeService etemployeeService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param etleaveRegister 退休离职变动表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R page(Page page, EtleaveRegister etleaveRegister) {
        return R.ok(etleaveRegisterService.page(page, Wrappers.query(etleaveRegister)));
    }


    /**
     * 通过id查询退休离职变动表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etleaveRegisterService.getById(id));
    }

    /**
     * 新增退休离职变动表
     * @param etleaveRegister 退休离职变动表
     * @return R
     */
    @ApiOperation(value = "新增退休离职变动表", notes = "新增退休离职变动表")
    @SysLog("新增退休离职变动表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etleaveregister_add')" )
    public R save(@RequestBody EtleaveRegister etleaveRegister) {
        return R.ok(etleaveRegisterService.save(etleaveRegister));
    }

  /*  *//**
     * 修改退休离职变动表      @PreAuthorize("@pms.hasPermission('admin_etleaveregister_edit')" )
     * @param etleaveRegister 退休离职变动表
     * @return R
     */
    @ApiOperation(value = "修改退休离职变动表", notes = "修改退休离职变动表")
    @SysLog("修改退休离职变动表" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtleaveRegister etleaveRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtleaveRegister etleaveRegister2 = new EtleaveRegister();
		etleaveRegister2.setSpno(etleaveRegister.getSpno());
		UpdateWrapper<EtleaveRegister> queryWrapper = new UpdateWrapper();
		queryWrapper.eq("id",etleaveRegister.getId());
		queryWrapper.eq("corpcode",corpcode);
        return R.ok(etleaveRegisterService.update(etleaveRegister,queryWrapper));
    }

    /**
     * 通过id删除退休离职变动表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除退休离职变动表", notes = "通过id删除退休离职变动表")
    @SysLog("通过id删除退休离职变动表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etleaveregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etleaveRegisterService.removeById(id));
    }

    @ApiOperation(value = "保存离职变动", notes = "保存离职变动")
    @PutMapping("/saveEtstaffRegisterForLiZhi")
	public R saveEtstaffRegisterForLiZhi(@RequestBody @Valid EtleaveRegister etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		Integer userId = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		etemployee.setCorpcode(corpcode);
		etemployee.setCompid(compid);
		etemployee.setSubmittime(currentTime);   //登记时间


		//离：1.员工不存在，请确认工号是否正确；
		List employeeList15 = etleaveRegisterService.getEtEmployeeNoExistsEmp(etemployee);
		if (!(employeeList15.size() > 0)) {
			return R.ok(null,"员工不存在，请确认工号是否正确!");
		}
		//离：2.员工已经离职，不能再做离职；
		List employeeList16 = etleaveRegisterService.getEtEmployeeIsResignation(etemployee);
		if(employeeList16.size() > 0){
			return R.ok(null,"员工已经离职，不能再做离职!");
		}
		//离：3.部门负责人离职，请先更换部门负责人
		List employeeList17 = etleaveRegisterService.getEtEmployeeDirectorLeave(etemployee);
		if(employeeList17.size() > 0){
			return R.ok(null,"部门负责人离职，请先更换部门负责人!");
		}
		//离：4.请先调整该员工下属员工的行政上级！
		List employeeList18 = etleaveRegisterService.getEtEmployeeReportTo(etemployee);
		if(employeeList18.size() > 0){
			return R.ok(null,"请先调整该员工下属员工的行政上级!");
		}
		//离：5.该员工有兼职信息，先终止兼职再离职！
		List employeeList19 = etleaveRegisterService.getEtEmployeePartTime(etemployee);
		if(employeeList19.size() > 0){
			return R.ok(null,"该员工有兼职信息，先终止兼职再离职!");
		}
		//离：6.该员工担任兼职信息中的上级，请先取消
		List employeeList20 = etleaveRegisterService.getEtEmployeePartTimeLeader(etemployee);
		if(employeeList20.size() > 0){
			return R.ok(null,"该员工担任兼职信息中的上级，请先取消!");
		}
		//离：7.员工离职请先解除劳动合同；
		List employeeList21 = etleaveRegisterService.getEtEmployeeContractUnit(etemployee);
		if(employeeList21.size() > 0){
			return R.ok(null,"员工离职请先解除劳动合同!");
		}
		etemployee.setRegby(userId);
		etemployee.setRegdate(currentTime);
		etemployee.setInitialized(0);
		return R.ok(etleaveRegisterService.save(etemployee));
	}

	/**
	 * 离职管理分页查询
	 * @param page 分页对象
	 * @param etleaveRegister 离职管理分页查询
	 * @return
	 */
	@ApiOperation(value = "离职管理分页查询", notes = "离职管理分页查询")
	@PostMapping("/getEtleaveRegisterPage" )
	public R getEtleaveRegisterPage(Page page, EtleaveRegister etleaveRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etleaveRegister.setCorpcode(corpcode);
		return R.ok(etleaveRegisterMapper.listEtleaveRegisterPage(page,etleaveRegister));
	}


	/**
	 * 员工离职
	 * @param etleaveRegister
	 * @return R
	 */
	@ApiOperation(value = "员工离职", notes = "员工离职")
	@SysLog("员工调动" )
	@PostMapping("/eSPLeaveStart")
	@Transactional
	public R eSPLeaveStart(@RequestBody  EtleaveRegister etleaveRegister) {
		if(etleaveRegister.getLzzl()==null||etleaveRegister.getLzzl().isEmpty()){
			return R.failed("请提交离职资料");
		}
		if(etleaveRegister.getApplydate()==null||etleaveRegister.getApplydate().isEmpty()){
			return R.failed("请选择申请日期");
		}
		//审批标志
		Integer isneedaudit = etleaveRegister.getIsneedaudit();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		Etemployee etemployee = etemployeeService.getById(etleaveRegister.getEid());
		//BeanUtils.copyProperties(etemployee,etleaveRegister);

		//BeanUtil.copyProperties(etemployee,etleaveRegister,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
		BeanUtil.copyProperties(etemployee,etleaveRegister, CopyOptions.create().setIgnoreNullValue(true));
		//申请日期 离职日期 离职类型 离职原因  离职资料 离职备注
		//applydate  LeaveDate LeaveType LeaveReason Remark
		etleaveRegister.setApplydate(currentTime);
		etleaveRegister.setType(1);//离职
		etleaveRegister.setRegdate(currentTime);
		etleaveRegister.setRegby(pigxUser.getId());
		etleaveRegister.setInitializedby(pigxUser.getId());
		//人事录入信息 确认标识 0:人事录入信息，1：人事确认录入，2：放弃入职
		etleaveRegister.setInitialized(0);
		etleaveRegister.setInitializedtime(currentTime);
		if(!StringUtils.isEmpty(isneedaudit)){
			if(isneedaudit==0){
				//提交审核标志 提交审核 1审核通过 2不通过3不审核0
				etleaveRegister.setSubmit(1);
				etleaveRegister.setSubmitby(pigxUser.getId());
				etleaveRegister.setSubmittime(currentTime);
			}else{
				//不需要审核
				etleaveRegister.setSubmit(0);
			}
		}else{
			etleaveRegister.setSubmit(0);
		}

		etleaveRegisterService.save(etleaveRegister);

		//校验变动
		Map map = new HashMap();
		map.put("id",etleaveRegister.getId());
		Map map2 = new HashMap();
		map2.put("id",etleaveRegister.getId());
		String message = null;
		//调用确认的存储过程
		etleaveRegisterMapper.eSPLeaveCheckSub(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			if(!StringUtils.isEmpty(isneedaudit)){
				if(isneedaudit!=0){
					map2.put("userid",pigxUser.getId());
					etleaveRegisterMapper.eSPLeaveStart(map2);
					int mesid = (Integer) map2.get("result");
					systmessage = systmessageMapper.selectById(mesid);
					message = systmessage.getTitle();
					if(mesid==0){
						return R.ok(etleaveRegister,message);
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



		return R.ok(etleaveRegister,"操作成功！");
	}


}
