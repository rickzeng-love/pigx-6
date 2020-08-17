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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.service.EtstaffAllService;
import com.pig4cloud.pigx.admin.util.CommonUtil;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.IdcardValidator;
import com.pig4cloud.pigx.admin.util.PinYinUtil;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.EtstaffRegisterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 入职登记
 *
 * @author gaoxiao
 * @date 2020-04-10 19:14:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etstaffregister" )
@Api(value = "etstaffregister", tags = "入职登记管理")
public class EtstaffRegisterController {

    private final  EtstaffRegisterService etstaffRegisterService;
    private final EtstaffAllService etstaffAllService;
    private final EtemployeeMapper etemployeeMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etstaffRegister 入职登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getEtstaffRegisterPage(Page page, @RequestBody EtstaffRegister etstaffRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffRegister.setCorpcode(corpcode);

		QueryWrapper<EtstaffRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("initialized",2);
		String name = etstaffRegister.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etstaffRegister.getName());
		}
		Integer depid = etstaffRegister.getDepid();
		Integer jobid = etstaffRegister.getJobid();
		if(!org.springframework.util.StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!org.springframework.util.StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
        return R.ok(etstaffRegisterService.page(page, queryWrapper));
    }
	/**
	 * 待审批分页查询
	 * @param page 分页对象
	 * @param etstaffRegister 入职登记
	 * @return
	 */
	@ApiOperation(value = "待审批分页查询", notes = "待审批分页查询")
	@PostMapping("/pagedaishenpi" )
	public R pagedaishenpi(Page page, EtstaffRegister etstaffRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffRegister.setCorpcode(corpcode);
		QueryWrapper<EtstaffRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("isneedaudit",0);
		queryWrapper.eq("submit",1);
		String name = etstaffRegister.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etstaffRegister.getName());
		}
		Integer depid = etstaffRegister.getDepid();
		Integer jobid = etstaffRegister.getJobid();
		if(!org.springframework.util.StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!org.springframework.util.StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(etstaffRegisterService.page(page, queryWrapper));
	}
	/**
	 * 查询所有
	 * @param etstaffRegister 入职登记表
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllEtstaffRegister" )
	public R getAllOtcdPosition(EtstaffRegister etstaffRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffRegister.setCorpcode(corpcode);
		return R.ok(etstaffRegisterService.list(Wrappers.query(etstaffRegister)));
	}

    /**
     * 通过id查询入职登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etstaffRegisterService.getById(id));
    }

    /**
     * 新增入职登记
     * @param etstaffRegister 入职登记
     * @return R
     */
    @ApiOperation(value = "新增入职登记", notes = "新增入职登记")
    @SysLog("新增入职登记" )
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('admin_etstaffregister_add')" )
    public R save(@RequestBody EtstaffRegister etstaffRegister) {
        return R.ok(etstaffRegisterService.save(etstaffRegister));
    }

  /**
     * 修改入职登记
     * @param etstaffRegister 入职登记    @PreAuthorize("@pms.hasPermission('admin_etstaffregister_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改入职登记", notes = "修改入职登记")
    @SysLog("修改入职登记" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtstaffRegister etstaffRegister) {
    	UpdateWrapper<EtstaffRegister> updateWrapper = new UpdateWrapper<>();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etstaffRegister.getId());
		EtstaffRegister etstaffRegister2 = new EtstaffRegister();
		etstaffRegister2.setSpno(etstaffRegister.getSpno());
        return R.ok(etstaffRegisterService.update(etstaffRegister2,updateWrapper));
    }

	/**
	 * 修改入职登记-逻辑判断
	 * @param etemployee 入职登记    @PreAuthorize("@pms.hasPermission('admin_etstaffregister_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "修改入职登记-逻辑判断", notes = "修改入职登记-逻辑判断")
	@SysLog("修改入职登记-逻辑判断" )
	@PostMapping("/updateetstaffRegisterById")
	public R updateetstaffRegisterById(@RequestBody @Valid  EtstaffRegister etemployee,BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}
		EtstaffRegister etstaffRegister = null;
		etstaffRegister = etstaffRegisterService.getById(etemployee.getId());
		if(org.springframework.util.StringUtils.isEmpty(etstaffRegister)){
			return R.failed("此信息不存在，请核实！");
		}

		//判断条件
		//isNeedAudit 0：需审核 1：不需要审核
		//Initialized 提交审核 1：审核通过 2：不通过3：不审核
		Integer isNeedAudit = etstaffRegister.getIsneedaudit();
		Integer submit = etstaffRegister.getSubmit();
		if(!org.springframework.util.StringUtils.isEmpty(isNeedAudit)){
			if(isNeedAudit==0){
				if(submit!=2){
					return R.failed(null,"审批状态不合法，请确认！");
				}
			}
		}


		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		Integer userId = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		etemployee.setCorpcode(etstaffRegister.getCorpcode());
		etemployee.setCompid(etstaffRegister.getCompid());
		etemployee.setRegdate(currentTime);

		//etemployee.setEmpstatus(1);
		//姓名或工号中存在回车或空格等非法字符!
		String name = etemployee.getName();
		String badge = etemployee.getBadge();
		if(name!="" && name!=null){
			etemployee.setName(CommonUtil.getNewStringByRemoveHC(name));
			//获取姓名拼音
			String ename = PinYinUtil.getFullSpell(name);
			etemployee.setEname(ename);
		}
		if(badge!="" && badge!=null){
			etemployee.setBadge(CommonUtil.getNewStringByRemoveHC(badge));
		}

		//1:黑名单校验 确认时再校验
		String certNO = etemployee.getCertno();
		if(StringUtils.isNotBlank(certNO)){
			List employeeList1 = etstaffRegisterService.getEtEmployeeBlackList(etemployee);
			if(employeeList1.size()>0){
				return R.failed(null,"在黑名单中，不允许入职!");
			}
		}

		//入：2.公司、部门 要有对应关系且都有效，请重新选择
		List employeeList2 = etstaffRegisterService.getEtEmployeeCDRelationship(etemployee);
		if(!(employeeList2.size()>=0)){
			return R.failed(null,"公司、部门 要有对应关系且都有效，请重新选择！");
		}
		//入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
		List employeeList3 = etstaffRegisterService.getEtEmployeeCJRelationship(etemployee);
		if(!(employeeList3.size()>0)){
			return R.failed(null,"公司、部门、岗位要有对应关系且都有效，请重新选择!");
		}
		//入：4.员工工号已经存在；
		String badge2 = etemployee.getBadge();
		if(!org.springframework.util.StringUtils.isEmpty(badge2)){
			List employeeList4 = etstaffRegisterService.getEtEmployeeExistsEmployee(etemployee);
			if(employeeList4.size()>0){
				return R.failed(null,"员工工号已经存在!");
			}
		}

		//入：5.直接主管不存在；
		Integer reportTo = etemployee.getReportto();
		if(reportTo!=null){
			List employeeList5 = etstaffRegisterService.getEtEmployeeNotExistsReportTo(etemployee);
			if(!(employeeList5.size()>0)){
				return R.failed(null,"直接主管不存在!");
			}else{
				Map etemployeeReportTo = (Map)employeeList5.get(0);
				String ReportToName = (String)etemployeeReportTo.get("name");
				etemployee.setReporttoname(ReportToName);
			}
		}

	/*
		//入：6职能WFreportTo主管不存在.；
		Integer  = etemployee.getWfreportto();
		if(WFreportTo!=null){
			List employeeList6 = etemployeeService.getEtEmployeeNotExistsWFReportTo(etemployee);
			if(!(employeeList6.size()>0)){
				return R.ok(null,"职能主管不存在!");
			}
		}*/

		//入：7.所选公司不存在；
		List employeeList7 = etstaffRegisterService.getEtEmployeeNotExistsComp(etemployee);
		if(!(employeeList7.size()>0)){
			return R.failed(null,"所选公司不存在!");
		}
		//入：8.所选公司已经失效；
		List employeeList8 = etstaffRegisterService.getEtEmployeeCompIsDisable(etemployee);
		if(employeeList8.size()>0){
			return R.failed(null,"所选公司已经失效!");
		}
		//入：9.所选部门不存在；
		List employeeList9 = etstaffRegisterService.getEtEmployeeNotExistsDept(etemployee);
		if(!(employeeList9.size()>0)){
			return R.failed(null,"所选部门不存在!");
		}
		// 入：10.所选部门已经失效；
		List employeeList10 = etstaffRegisterService.getEtEmployeeDeptIsDisable(etemployee);
		if(employeeList10.size()>0){
			return R.failed(null,"所选部门已经失效!");
		}
		//入：11.所选岗位不存在；
		List employeeList11 = etstaffRegisterService.getEtEmployeeNotExistsJob(etemployee);
		if(!(employeeList11.size()>0)){
			return R.failed(null,"所选岗位不存在!");
		}

		//入：12.所选岗位已经失效；
		List employeeList12 = etstaffRegisterService.getEtEmployeeJobIsDisable(etemployee);
		if(employeeList12.size()>0){
			return R.failed(null,"所选岗位已经失效!");
		}
		// 入：13.身份证不符合要求，请看身份证验证提示窗口；
		IdcardValidator iv = new IdcardValidator();
		String certno = etemployee.getCertno();
		boolean b = false;
		if(certno!="" &&certno!=null){
			b = iv.isValidatedAllIdcard(certno);
			if(!b){
				return R.failed(null,"身份证不符合要求，请查看身份证是否正确!");
			}
		}

		//入：14.手机号与现有人员重复；
		List employeeList14 = etstaffRegisterService.getEtEmployeeMobileRepeat(etemployee);
		if(employeeList14.size()>0){
			return R.failed(null,"手机号与现有人员重复!");
		}
		//入：14.手机号与现有人员重复；
		List employeeList141 = etstaffRegisterService.getEtstaffRegisterByMobile(etemployee);
		if(employeeList141.size()>0){
			return R.failed(null,"此手机号与正在办理入职的人员重复!");
		}

		//入：15.员工状态为实习，实习期、实习结束日期不能为空
		Integer empstatu = etemployee.getEmpstatus();
		String pracbegindate = etemployee.getPracbegindate();
		String pracenddate = etemployee.getPracenddate();
		String joindate = etemployee.getJoindate();
		if(empstatu==2){
			if(StringUtils.isBlank(pracbegindate) || StringUtils.isBlank(pracenddate) ){
				return R.failed(null,"员工状态为实习，实习开始日期、实习结束日期不能为空!");
			}
			if(pracbegindate.compareTo(joindate)<0 || pracbegindate.compareTo(pracenddate)>0){
				return R.failed(null,"实习期不能早于入职日期,实习开始时间不能大于结束时间!");
			}
		}

		//16入 员工状态为试用，试用期、试用结束日期不能为空!
		String prabbegindate = etemployee.getProbbegindate();
		String prabbenddate = etemployee.getProbenddate();
		if(empstatu==4){
			if(StringUtils.isBlank(prabbegindate) || StringUtils.isBlank(prabbenddate) ){
				return R.failed(null,"员工状态为试用，试用期、试用结束日期不能为空!");
			}
			if(prabbegindate.compareTo(joindate)<0 || prabbegindate.compareTo(prabbenddate)>0){
				return R.failed(null,"试用期不能早于入职日期,试用开始时间不能大于结束时间!");
			}
		}
		//17 国籍为中华人民共和国的员工必须填写身份证!
		// isnull(country,0)=41 And (isnull(certType,0)<>1 or certNo is null))
		Integer country = etemployee.getCountry();
		Integer certType = etemployee.getCerttype();
		if(country!=null && certType!=null){
			if(country==41){
				if(certType!=1){
					return R.failed(null,"国籍为中华人民共和国的员工必须填写身份证!");
				}
			}
		}
		//处理提交状态
		//确认标识 0:人事录入信息，1：人事确认录入，2：放弃入职
		etemployee.setRegdate(currentTime);
		etemployee.setRegby(userId);
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtstaffRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etemployee.getId());
		return R.ok(etstaffRegisterService.update(etemployee,updateWrapper));
	}

    /**
     * 通过id删除入职登记
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_etstaffregister_del')" )
     * @return R
     */
    @ApiOperation(value = "通过id删除入职登记", notes = "通过id删除入职登记")
    @SysLog("通过id删除入职登记" )
    @PostMapping("/removeById" )
    public R removeById(Integer id) {
    	if(org.springframework.util.StringUtils.isEmpty(id)){
    		R.ok("id不能为空！");
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtstaffRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("id",id);
		queryWrapper.eq("isneedaudit",0).eq("submit",0);
		List list = etstaffRegisterService.list(queryWrapper);
		if(list.size()>0){
			return R.ok(null,"此员工信息还在审核中,请等待审核结束！");
		}
		EtstaffRegister etstaffRegister2  =etstaffRegisterService.getById(id);
		EtstaffAll  etstaffAll = new EtstaffAll();
		BeanUtils.copyProperties(etstaffRegister2,etstaffAll);
		EtstaffRegister etstaffRegister3  = new EtstaffRegister();
		etstaffAllService.save(etstaffAll);
		UpdateWrapper<EtstaffRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",id);
		return R.ok(etstaffRegisterService.remove(updateWrapper));
    }
	/**
	 * 新增待入职员工
	 * @param etemployee 员工信息表
	 * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
	 */
	@ApiOperation(value = "新增待入职员工", notes = "新增待入职员工")
	@SysLog("新增待入职员工" )
	@PostMapping("/saveEtstaffRegisterForRuZhi")
	public R saveEtstaffRegisterForRuZhi(@RequestBody @Valid EtstaffRegister etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		Integer eid = pigxUser.getEid();
		Integer userId = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		etemployee.setCorpcode(corpcode);
		etemployee.setCorpid(pigxUser.getCorpid());
		etemployee.setCompid(compid);
		etemployee.setRegdate(currentTime);
		etemployee.setWorkcity(etemployee.getCity());

		//etemployee.setEmpstatus(1);
		//姓名或工号中存在回车或空格等非法字符!
		String name = etemployee.getName();
		String badge = etemployee.getBadge();
		if(name!="" && name!=null){
			etemployee.setName(CommonUtil.getNewStringByRemoveHC(name));
			//获取姓名拼音
			String ename = PinYinUtil.getFullSpell(name);
			etemployee.setEname(ename);
		}
		if(badge!="" && badge!=null){
			etemployee.setBadge(CommonUtil.getNewStringByRemoveHC(badge));
		}

		//1:黑名单校验 确认时再校验
		String certNO = etemployee.getCertno();
		if(StringUtils.isNotBlank(certNO)){
			List employeeList1 = etstaffRegisterService.getEtEmployeeBlackList(etemployee);
			if(employeeList1.size()>0){
				return R.ok(null,"在黑名单中，不允许入职!");
			}
		}

		//入：2.公司、部门 要有对应关系且都有效，请重新选择
		List employeeList2 = etstaffRegisterService.getEtEmployeeCDRelationship(etemployee);
		if(!(employeeList2.size()>=0)){
			return R.ok(null,"公司、部门 要有对应关系且都有效，请重新选择！");
		}
		//入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
		List employeeList3 = etstaffRegisterService.getEtEmployeeCJRelationship(etemployee);
		if(!(employeeList3.size()>0)){
			return R.ok(null,"公司、部门、岗位要有对应关系且都有效，请重新选择!");
		}
		//入：4.员工工号已经存在；
		if(!StringUtils.isEmpty(etemployee.getBadge())){
			List employeeList4 = etstaffRegisterService.getEtEmployeeExistsEmployee(etemployee);
			if(employeeList4.size()>0){
				return R.ok(null,"员工工号已经存在!");
			}
		}

		//入：5.直接主管不存在；
		Integer reportTo = etemployee.getReportto();
		if(reportTo!=null){
			List employeeList5 = etstaffRegisterService.getEtEmployeeNotExistsReportTo(etemployee);
			if(!(employeeList5.size()>0)){
				return R.ok(null,"直接主管不存在!");
			}else{
				Map etemployeeReportTo = (Map)employeeList5.get(0);
				String ReportToName = (String)etemployeeReportTo.get("name");
				etemployee.setReporttoname(ReportToName);
			}
		}

	/*
		//入：6.职能WFreportTo主管不存在；
		Integer  = etemployee.getWfreportto();
		if(WFreportTo!=null){
			List employeeList6 = etemployeeService.getEtEmployeeNotExistsWFReportTo(etemployee);
			if(!(employeeList6.size()>0)){
				return R.ok(null,"职能主管不存在!");
			}
		}*/

		//入：7.所选公司不存在；
		List employeeList7 = etstaffRegisterService.getEtEmployeeNotExistsComp(etemployee);
		if(!(employeeList7.size()>0)){
			return R.ok(null,"所选公司不存在!");
		}
		//入：8.所选公司已经失效；
		List employeeList8 = etstaffRegisterService.getEtEmployeeCompIsDisable(etemployee);
		if(employeeList8.size()>0){
			return R.ok(null,"所选公司已经失效!");
		}
		//入：9.所选部门不存在；
		List employeeList9 = etstaffRegisterService.getEtEmployeeNotExistsDept(etemployee);
		if(!(employeeList9.size()>0)){
			return R.ok(null,"所选部门不存在!");
		}
		// 入：10.所选部门已经失效；
		List employeeList10 = etstaffRegisterService.getEtEmployeeDeptIsDisable(etemployee);
		if(employeeList10.size()>0){
			return R.ok(null,"所选部门已经失效!");
		}
		//入：11.所选岗位不存在；
		List employeeList11 = etstaffRegisterService.getEtEmployeeNotExistsJob(etemployee);
		if(!(employeeList11.size()>0)){
			return R.ok(null,"所选岗位不存在!");
		}

		//入：12.所选岗位已经失效；
		List employeeList12 = etstaffRegisterService.getEtEmployeeJobIsDisable(etemployee);
		if(employeeList12.size()>0){
			return R.ok(null,"所选岗位已经失效!");
		}
		// 入：13.身份证不符合要求，请看身份证验证提示窗口；
		IdcardValidator iv = new IdcardValidator();
		String certno = etemployee.getCertno();
		boolean b = false;
		if(certno!="" &&certno!=null){
			b = iv.isValidatedAllIdcard(certno);
			if(!b){
				return R.ok(null,"身份证不符合要求，请查看身份证是否正确!");
			}
		}

		//入：14.手机号与现有人员重复；
		List employeeList14 = etstaffRegisterService.getEtEmployeeMobileRepeat(etemployee);
		if(employeeList14.size()>0){
			return R.ok(null,"手机号与现有人员重复!");
		}
		//入：14.手机号与现有人员重复；
		List employeeList141 = etstaffRegisterService.getEtstaffRegisterByMobile(etemployee);
		if(employeeList141.size()>0){
			return R.ok(null,"此手机号与正在办理入职的人员重复!");
		}

		//入：15.员工状态为实习，实习期、实习结束日期不能为空
		Integer empstatu = etemployee.getEmpstatus();
		String pracbegindate = etemployee.getPracbegindate();
		String pracenddate = etemployee.getPracenddate();
		String joindate = etemployee.getJoindate();
		if(empstatu==2){
			if(StringUtils.isBlank(pracbegindate) || StringUtils.isBlank(pracenddate) ){
				return R.ok(null,"员工状态为实习，实习开始日期、实习结束日期不能为空!");
			}
			if(pracbegindate.compareTo(joindate)<0 || pracbegindate.compareTo(pracenddate)>0){
				return R.ok(null,"实习期不能早于入职日期,实习开始时间不能大于结束时间!");
			}
		}

		//16入 员工状态为试用，试用期、试用结束日期不能为空!
		String prabbegindate = etemployee.getProbbegindate();
		String prabbenddate = etemployee.getProbenddate();
		if(empstatu==4){
			if(StringUtils.isEmpty(prabbegindate) || StringUtils.isEmpty(prabbenddate) ){
				return R.ok(null,"员工状态为试用，试用期、试用结束日期不能为空!");
			}
			if(prabbegindate.compareTo(joindate)<0 || prabbegindate.compareTo(prabbenddate)>0){
				return R.ok(null,"试用期不能早于入职日期,试用开始时间不能大于结束时间!");
			}
		}
		//17 国籍为中华人民共和国的员工必须填写身份证!
		// isnull(country,0)=41 And (isnull(certType,0)<>1 or certNo is null))
		Integer country = etemployee.getCountry();
		Integer certType = etemployee.getCerttype();
		if(country!=null && certType!=null){
			if(country==41){
				if(certType!=1){
					return R.ok(null,"国籍为中华人民共和国的员工必须填写身份证!");
				}
			}
		}
		//etemployee.setWorkcity();
		//处理提交状态
		//确认标识 0:人事录入信息，1：人事确认录入，2：放弃入职
		etemployee.setRegdate(currentTime);
		etemployee.setRegby(userId);
		etemployee.setInitialized(0);
		etemployee.setInitializedby(userId);
		etemployee.setInitializedtime(currentTime);
		//是否提交领导审批 0：需审核 1：不需要审核
		Integer isneedaudit = etemployee.getIsneedaudit();
		if(!org.springframework.util.StringUtils.isEmpty(isneedaudit)){
			if(isneedaudit==0){
				//提交审核标志
				etemployee.setSubmit(1);
				//发送流程
			}
		}
		/*if(isneedaudit==0){
			//提交审核标志
			etemployee.setSubmit(1);
			//发送流程
		}else{
			Integer issendinvite = etemployee.getIssendinvite();
			if(issendinvite==0){
				//发送入职邀请
			}
		}*/
		//1、新增 2、变动
		etemployee.setType(1);
		etstaffRegisterService.save(etemployee);
		return R.ok(etemployee);
	}

	/**
	 * 上传文件
	 * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@ApiOperation(value = "上传个人照片", notes = "上传个人照片")
	@PostMapping("/uploadFile")
	public R upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {

		return etstaffRegisterService.uploadFile(file,id);
	}

	/**
	 * 放弃入职
	 * @return R
	 */
	@ApiOperation(value = "放弃入职", notes = "放弃入职")
	@PostMapping("/abandonEtstaffRegister" )
	public R abandonEtstaffRegister(@RequestBody EtstaffRegister etstaffRegister){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtstaffRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("id",etstaffRegister.getId());
		queryWrapper.eq("isneedaudit",0).eq("submit",0);
		List list = etstaffRegisterService.list(queryWrapper);
		if(list.size()>0){
			return R.ok(null,"此员工信息还在审核中,请等待审核结束！");
		}
		EtstaffRegister etstaffRegister2  =etstaffRegisterService.getById(etstaffRegister.getId());
		EtstaffAll  etstaffAll = new EtstaffAll();
		BeanUtils.copyProperties(etstaffRegister2,etstaffAll);
		EtstaffRegister etstaffRegister3  = new EtstaffRegister();
		//放弃入职
		etstaffRegister3.setInitialized(2);
		etstaffRegister3.setId(etstaffRegister.getId());
		etstaffAllService.save(etstaffAll);
		UpdateWrapper<EtstaffRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etstaffRegister.getId());
		etstaffRegisterService.update(etstaffRegister3,updateWrapper);

		return R.ok("数据已修改！");
	}
	/**
	 * 员工入职-统计人数
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "员工入职-统计人数", notes = "员工入职-统计人数")
	@PostMapping("/getEmployeeInformationForCount" )
	public R getEmployeeInformationForCount(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);

		long dairuzhicount = 0;//待入职
		long zuijinruzhicount = 0;//最近入职
		long fangqiruzhicount = 0;//放弃入职
		long daishenpicount = 0;//待审批
		long yitongguocount = 0;//已通过
		long yibohuicount = 0;//已驳回

		//待入职
		Map empStatusdairuzhi = etemployeeMapper.listEtstaffRegisterCount(etemployee);
		if(empStatusdairuzhi!=null){
			dairuzhicount = (Long) empStatusdairuzhi.get("count");
		}

		//最近入职
		Map empStatuszuijinruzhi = etemployeeMapper.listEtstaffAllRecentEntryCount(etemployee);
		if(empStatuszuijinruzhi!=null){
			zuijinruzhicount = (Long) empStatuszuijinruzhi.get("count");
		}
		//放弃入职
		Map empStatusangqiruzhi = etemployeeMapper.listEtstaffAllGiveUpEntryCount(etemployee);
		if(empStatusangqiruzhi!=null){
			fangqiruzhicount = (Long) empStatusangqiruzhi.get("count");
		}
		//待审批
		Map empStatusdaishenpi = etemployeeMapper.listEtstaffRegisterApprovalPendingCount(etemployee);
		if(empStatusdaishenpi!=null){
			daishenpicount = (Long) empStatusdaishenpi.get("count");
		}
		//已通过
		Map empStatusyitongguo = etemployeeMapper.listEtstaffAllPassedCount(etemployee);
		if(empStatusyitongguo!=null){
			yitongguocount = (Long) empStatusyitongguo.get("count");
		}
		//已驳回
		Map empStatusyibohui = etemployeeMapper.listEtstaffAllNotPassedCount(etemployee);
		if(empStatusyibohui!=null){
			yibohuicount = (Long) empStatusyibohui.get("count");
		}



		Map resultMap1 = new HashMap();
		Map resultMap2 = new HashMap();
		Map resultMap3 = new HashMap();
		Map resultMap4 = new HashMap();
		Map resultMap5 = new HashMap();
		Map resultMap6 = new HashMap();
		Map resultMap7 = new HashMap();
		Map resultMap8 = new HashMap();

		resultMap1.put("empstatus","待入职");
		resultMap1.put("people",dairuzhicount);
		resultMap2.put("empstatus","最近入职");
		resultMap2.put("people",zuijinruzhicount);
		resultMap3.put("empstatus","放弃入职");
		resultMap3.put("people",fangqiruzhicount);
		resultMap4.put("empstatus","待审批");
		resultMap4.put("people",daishenpicount);
		resultMap5.put("empstatus","已通过");
		resultMap5.put("people",yitongguocount);
		resultMap6.put("empstatus","已驳回");
		resultMap6.put("people",yibohuicount);

		List resultList = new ArrayList(6);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		resultList.add(2,resultMap3);
		resultList.add(3,resultMap4);
		resultList.add(4,resultMap5);
		resultList.add(5,resultMap6);
		return R.ok(resultList);
	}



}
