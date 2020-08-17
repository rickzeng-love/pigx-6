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
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.entity.SysUserRole;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.*;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.swing.StringUIClientPropertyKey;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 员工信息表
 *
 * @author gaoxiao
 * @date 2020-04-08 13:22:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etemployee" )
@Api(value = "etemployee", tags = "员工信息管理")
public class EtemployeeController {

    private final EtemployeeService etemployeeService;
    private  final EtstaffRegisterService etstaffRegisterService;
	private  final EtemployeeMapper etemployeeMapper;
	private final EtstaffAllService etstaffAllService;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final SysUserService sysUserService;
	private  final CtemployeeService ctemployeeService;
	private final  SysUserRoleService sysUserRoleService;
	private final  AtstaffRegisterService atstaffRegisterService;
	private final  AtstaffAllService atstaffAllService;
	private final  CtsalaryRegisterService ctsalaryRegisterService;
	private final  CtsalaryAllService ctsalaryAllService;
	private final  CtbenefitRegisterService ctbenefitRegisterService;
	private final  EteventService eteventService;
	private final CtbenefitAllService ctbenefitAllService;
	//ctBenefit_All
    /**
     * 分页查询
     * @param page 分页对象
     * @param etemployee 员工信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtemployeePage(Page page,@RequestBody(required = false) Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(org.springframework.util.StringUtils.isEmpty(etemployee)){
			etemployee = new Etemployee();
		}
		etemployee.setCorpcode(corpcode);
        return R.ok(etemployeeService.page(page, Wrappers.query(etemployee)));
    }
	/**
	 * 分页查询只返回list
	 * @param page 分页对象
	 * @param name 员工姓名
	 * @return
	 */
	@ApiOperation(value = "分页查询只返回list", notes = "分页查询只返回list")
	@GetMapping(value={"/getEtemployeePageList/{name}","/getEtemployeePageList"} )
	public R getEtemployeePageList(Page page, @PathVariable(required = false) String name) {
		Etemployee etemployee = new Etemployee();
		if(StringUtils.isNotEmpty(name)){
			etemployee.setName(name);
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		IPage resultpage = etemployeeMapper.listEmployeeForPageBySql(page,etemployee);
		List resultList = resultpage.getRecords();
		return R.ok(resultList,"",CommonConstants.SUCCESS200);
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "查询在职信息", notes = "查询在职信息")
	@GetMapping("/getEtemployeePageForZaizhi" )
	public R getEtemployeePageForZaizhi(Page page,@RequestBody(required = false)  Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(org.springframework.util.StringUtils.isEmpty(etemployee)){
			etemployee = new Etemployee();
		}
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeService.page(page, Wrappers.query(etemployee).or().eq("empstatus",1).or().eq("empstatus",2).or().eq("empstatus",4)));
	}

	/**
	 * 查询所有
	 * @param etemployee 员工
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtemployee" )
	public R getAllOtcdEmpgrade(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeService.list(Wrappers.query(etemployee)));
	}
	/**
	 * 查询总人数1
	 * @param etemployee 员工
	 * @return
	 */
	@ApiOperation(value = "查询总人数1", notes = "查询总人数1")
	@PostMapping("/getEmployeeByCommonCount" )
	public R getEmployeeByCommonCount(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEmployeeByCommonCount(etemployee));
	}
	/**
	 * 查询总人数2
	 * @param etemployee 员工
	 * @return
	 */
	@ApiOperation(value = "查询总人数2", notes = "查询总人数2")
	@PostMapping("/getEmployeeByCommonCount2" )
	public R getEmployeeByCommonCount2(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEmployeeByCommonCount2(etemployee));
	}

	/**
	 * 上级领导
	 * @param page 分页对象
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "上级领导", notes = "上级领导")
	@GetMapping("/getEtemployeeForReportTo" )
	public R getEtemployeeForReportTo(Page page, @RequestBody(required = false) Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(org.springframework.util.StringUtils.isEmpty(etemployee)){
			etemployee = new Etemployee();
		}
		QueryWrapper<Etemployee> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("empstatus",1).or().eq("empstatus",2);
		String name = etemployee.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etemployee.getName());
		}

		return R.ok(etemployeeService.page(page, queryWrapper));
	}
	/**
	 * 查询所有
	 * @return
	 */
	@ApiOperation(value = "按姓名拼音首字母查询所有", notes = "按姓名拼音首字母查询所有")
	@GetMapping("/getAllOtcdEmpgradeWithPinyin" )
	public R getAllOtcdEmpgradeWithPinyin() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		List<Map> listetemployee = etemployeeService.getAllOtcdEmpgradeWithPinyin(etemployee);
		Map<String,Object> mapResult= PinYinUtil.px(listetemployee);
		return R.ok(mapResult);
	}
	/**
	 * 按员工状态查询所有
	 * @return
	 */
	@ApiOperation(value = "按员工状态查询所有", notes = "按员工状态查询所有")
	@GetMapping("/getAllOtcdEmpgradeWithEmpStatus" )
	public R getAllOtcdEmpgradeWithEmpStatus() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		//正常
		etemployee.setEmpstatus(1);
		List<Map<String,Object>> listetemployee1 = etemployeeService.listMaps(Wrappers.query(etemployee));
		//实习
		etemployee.setEmpstatus(2);
		List<Map<String,Object>> listetemployee2 = etemployeeService.listMaps(Wrappers.query(etemployee));
		//见习
		//etemployee.setEmpstatus(3);
		//List<Map<String,Object>> listetemployee3 = etemployeeService.listMaps(Wrappers.query(etemployee));
		//试用
		etemployee.setEmpstatus(4);
		List<Map<String,Object>> listetemployee4 = etemployeeService.listMaps(Wrappers.query(etemployee));
		//请假
		//etemployee.setEmpstatus(5);
		//List<Map<String,Object>> listetemployee5 = etemployeeService.listMaps(Wrappers.query(etemployee));
		//离职
		etemployee.setEmpstatus(6);
		List<Map<String,Object>> listetemployee6 = etemployeeService.listMaps(Wrappers.query(etemployee));
		//退休
		etemployee.setEmpstatus(7);
		List<Map<String,Object>> listetemployee7 = etemployeeService.listMaps(Wrappers.query(etemployee));

		EtstaffRegister etstaffRegister = new EtstaffRegister();
		etstaffRegister.setCorpcode(corpcode);
		//etstaffRegister.setSubmit(-1);
		List<Map<String,Object>> listetemployee10 = etstaffRegisterService.listMaps(Wrappers.query(etstaffRegister));


		Map mapResult = new HashMap();
		if(listetemployee1.size()>0){
			mapResult.put("1",listetemployee1);
		}
		if(listetemployee2.size()>0){
			mapResult.put("2",listetemployee2);
		}
		if(listetemployee4.size()>0){
			mapResult.put("4",listetemployee4);
		}
		if(listetemployee6.size()>0){
			mapResult.put("6",listetemployee6);
		}
		if(listetemployee7.size()>0){
			mapResult.put("7",listetemployee7);
		}
		if(listetemployee10.size()>0){
			mapResult.put("10",listetemployee10);
		}
		Map map2 = etemployeeMapper.listEmployeeByCommonCount2(etemployee);
		Map map1 = etemployeeMapper.listEmployeeByCommonCount(etemployee);
		mapResult.put("count1",map1);
		mapResult.put("count2",map2);
		return R.ok(mapResult);
	}
    /**
     * 通过id查询员工信息表
     * @param eid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{eid}" )
    public R getById(@PathVariable("eid" ) Integer eid) {
        return R.ok(etemployeeService.getById(eid));
    }

   /* *//**
     * 新增员工信息表
     * @param etemployee 员工信息表
     * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
     *//*
    @ApiOperation(value = "新增员工信息表", notes = "新增员工信息表")
    @SysLog("新增员工信息表" )
    @PostMapping("/save")
    public R save(@RequestBody @Valid Etemployee etemployee, BindingResult results) {
		if (results.hasErrors()){
			return R.ok(results.getFieldError().getDefaultMessage());
		}

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		String currentTime = DateUtils.getTimeString();
		etemployee.setCorpcode(corpcode);
		etemployee.setCompid(compid);
		etemployee.setCreatedate(currentTime);
		//etemployee.setEmpstatus(1);
		//姓名或工号中存在回车或空格等非法字符!
		String name = etemployee.getName();
		String badge = etemployee.getBadge();
		if(name!="" && name!=null){
			etemployee.setName(CommonUtil.getNewStringByRemoveHC(name));
		}
		if(badge!="" && badge!=null){
			etemployee.setBadge(CommonUtil.getNewStringByRemoveHC(badge));
		}
		//获取姓名拼音
		String ename = PinYinUtil.getFullSpell(name);
		etemployee.setEname(ename);
		//1:黑名单校验 确认时再校验
		String certNO = etemployee.getCertno();
		if(StringUtils.isNotBlank(certNO)){
			List employeeList1 = etemployeeService.getEtEmployeeBlackList(etemployee);
			if(employeeList1.size()>0){
				return R.ok(null,"在黑名单中，不允许入职!");
			}
		}

		//入：2.公司、部门 要有对应关系且都有效，请重新选择
		List employeeList2 = etemployeeService.getEtEmployeeCDRelationship(etemployee);
		if(!(employeeList2.size()>=0)){
			return R.ok(null,"公司、部门 要有对应关系且都有效，请重新选择！");
		}
		//入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
		List employeeList3 = etemployeeService.getEtEmployeeCJRelationship(etemployee);
		if(!(employeeList3.size()>0)){
			return R.ok(null,"公司、部门、岗位要有对应关系且都有效，请重新选择!");
		}
		//入：4.员工工号已经存在；
		List employeeList4 = etemployeeService.getEtEmployeeExistsEmployee(etemployee);
		if(employeeList4.size()>0){
			return R.ok(null,"员工工号已经存在!");
		}
		//入：5.直接主管不存在；
		Integer reportTo = etemployee.getReportto();
		if(reportTo!=null){
			List employeeList5 = etemployeeService.getEtEmployeeNotExistsReportTo(etemployee);
			if(!(employeeList5.size()>0)){
				return R.ok(null,"直接主管不存在!");
			}else{
				Etemployee etemployeeReportTo = (Etemployee)employeeList5.get(0);
				String ReportToName = etemployeeReportTo.getName();
				etemployee.setReporttoname(ReportToName);
			}
		}

	*//*
		//入：6.职能主管不存在；
		Integer WFreportTo = etemployee.getWfreportto();
		if(WFreportTo!=null){
			List employeeList6 = etemployeeService.getEtEmployeeNotExistsWFReportTo(etemployee);
			if(!(employeeList6.size()>0)){
				return R.ok(null,"职能主管不存在!");
			}
		}*//*

		//入：7.所选公司不存在；
		List employeeList7 = etemployeeService.getEtEmployeeNotExistsComp(etemployee);
		if(!(employeeList7.size()>0)){
			return R.ok(null,"所选公司不存在!");
		}
		//入：8.所选公司已经失效；
		List employeeList8 = etemployeeService.getEtEmployeeCompIsDisable(etemployee);
		if(employeeList8.size()>0){
			return R.ok(null,"所选公司已经失效!");
		}
		//入：9.所选部门不存在；
		List employeeList9 = etemployeeService.getEtEmployeeNotExistsDept(etemployee);
		if(!(employeeList9.size()>0)){
			return R.ok(null,"所选部门不存在!");
		}
		// 入：10.所选部门已经失效；
		List employeeList10 = etemployeeService.getEtEmployeeDeptIsDisable(etemployee);
		if(employeeList10.size()>0){
			return R.ok(null,"所选部门已经失效!");
		}
		//入：11.所选岗位不存在；
		List employeeList11 = etemployeeService.getEtEmployeeNotExistsJob(etemployee);
		if(!(employeeList11.size()>0)){
			return R.ok(null,"所选岗位不存在!");
		}

		//入：12.所选岗位已经失效；
		List employeeList12 = etemployeeService.getEtEmployeeJobIsDisable(etemployee);
		if(employeeList12.size()>0){
			return R.ok(null,"所选岗位已经失效!");
		}
		// 入：13.身份证不符合要求，请看身份证验证提示窗口；
		IdcardValidator iv = new IdcardValidator();
		String certno = etemployee.getCertno();
		boolean b = false;
		if(certno!="" &&certno!=null){
			b = iv.isValidatedAllIdcard(certno);
			//计算出生日期
			int year = Integer.parseInt(certno.substring(6, 8));
			int month = Integer.parseInt(certno.substring(8, 10));
			int day = Integer.parseInt(certno.substring(10, 12));
			String birthday = year+"-"+month+"-"+day;
			etemployee.setBirthday(birthday);
		}
		if(b){
			return R.ok(null,"身份证不符合要求，请查看身份证是否正确!");
		}
		//入：14.手机号与现有人员重复；
		List employeeList14 = etemployeeService.getEtEmployeeMobileRepeat(etemployee);
		if(employeeList14.size()>0){
			return R.ok(null,"手机号与现有人员重复!");
		}
		//入：14-1.手机号与正在办理入职的人员重复；
		List employeeList141 = etemployeeService.getEtstaffRegisterByMobile(etemployee);
		if(employeeList141.size()>0){
			return R.ok(null,"手机号与正在办理入职的人员重复!");
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
			if(StringUtils.isBlank(prabbegindate) || StringUtils.isBlank(prabbenddate) ){
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
		if(country==41){
			if(certType!=1){
				return R.ok(null,"国籍为中华人民共和国的员工必须填写身份证!");
			}
		}
		//处理入职装填


    	return R.ok(etemployeeService.save(etemployee));
    }*/

	/**
	 * 确认入职
	 * @param e 员工信息表
	 * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_add')" )
	 */
	@ApiOperation(value = "确认入职", notes = "确认入职")
	@SysLog("确认入职" )
	@PostMapping("/save")
	@Transactional
	public R save(@RequestBody  EtstaffRegister e) {
		EtstaffRegister etstaffRegister = null;
		etstaffRegister = etstaffRegisterService.getById(e.getId());
		if(org.springframework.util.StringUtils.isEmpty(etstaffRegister)){
			return R.failed(null,"此员工信息不存在，请核实");
		}
		//判断条件
		//isNeedAudit 0：需审核 1：不需要审核
		//Initialized 提交审核 1：审核通过 2：不通过3：不审核
		Integer isNeedAudit = etstaffRegister.getIsneedaudit();
		Integer submit = etstaffRegister.getSubmit();
		if(!org.springframework.util.StringUtils.isEmpty(isNeedAudit)){
			if(isNeedAudit==0){
				if(!org.springframework.util.StringUtils.isEmpty(submit)){
					if(submit!=2){
						return R.failed(null,"审批状态不合法，请确认！");
					}
				}

			}
		}


		Etemployee etemployee = new Etemployee();
		BeanUtils.copyProperties(etstaffRegister,etemployee);

		PigxUser pigxUser = SecurityUtils.getUser();
		String currentTime = DateUtils.getTimeString();
		etemployee.setCreatedate(currentTime);
		//etemployee.setEmpstatus(1);
		//姓名或工号中存在回车或空格等非法字符!
		String name = etemployee.getName();
		String badge = etemployee.getBadge();
		if(name!="" && name!=null){
			etemployee.setName(CommonUtil.getNewStringByRemoveHC(name));
		}
		if(badge!="" && badge!=null){
			etemployee.setBadge(CommonUtil.getNewStringByRemoveHC(badge));
		}

		//1:黑名单校验 确认时再校验
		String certNO = etemployee.getCertno();
		if(StringUtils.isNotBlank(certNO)){
			List employeeList1 = etemployeeService.getEtEmployeeBlackList(etemployee);
			if(employeeList1.size()>0){
				return R.failed(null,"在黑名单中，不允许入职!");
			}
		}

		//入：2.公司、部门 要有对应关系且都有效，请重新选择
		List employeeList2 = etemployeeService.getEtEmployeeCDRelationship(etemployee);
		if(!(employeeList2.size()>=0)){
			return R.failed(null,"公司、部门 要有对应关系且都有效，请重新选择！");
		}
		//入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
		List employeeList3 = etemployeeService.getEtEmployeeCJRelationship(etemployee);
		if(!(employeeList3.size()>0)){
			return R.failed(null,"公司、部门、岗位要有对应关系且都有效，请重新选择!");
		}
		//入：4.员工工号已经存在；
		if(!(org.springframework.util.StringUtils.isEmpty(etemployee.getBadge()))){
			List employeeList4 = etemployeeService.getEtEmployeeExistsEmployee(etemployee);
			if(employeeList4.size()>0){
				return R.failed(null,"员工工号已经存在!");
			}
		}

		//入：5.直接主管不存在；
		Integer reportTo = etemployee.getReportto();
		if(!(org.springframework.util.StringUtils.isEmpty(reportTo))){
			List employeeList5 = etemployeeService.getEtEmployeeNotExistsReportTo(etemployee);
			if(!(employeeList5.size()>0)){
				return R.failed(null,"上级领导不存在!");
			}else{
				Map etemployeeReportTo = (Map)employeeList5.get(0);
				Object o = etemployeeReportTo.get("name");
				if(!org.springframework.util.StringUtils.isEmpty(o)){
					String ReportToName = o.toString();
					etemployee.setReporttoname(ReportToName);
				}

			}
		}

	/*
		//入：6.职能主管不存在；
		Integer WFreportTo = etemployee.getWfreportto();
		if(WFreportTo!=null){
			List employeeList6 = etemployeeService.getEtEmployeeNotExistsWFReportTo(etemployee);
			if(!(employeeList6.size()>0)){
				return R.ok(null,"职能主管不存在!");
			}
		}*/

		//入：7.所选公司不存在；
		List employeeList7 = etemployeeService.getEtEmployeeNotExistsComp(etemployee);
		if(!(employeeList7.size()>0)){
			return R.failed(null,"所选公司不存在!");
		}
		//入：8.所选公司已经失效；
		List employeeList8 = etemployeeService.getEtEmployeeCompIsDisable(etemployee);
		if(employeeList8.size()>0){
			return R.failed(null,"所选公司已经失效!");
		}
		//入：9.所选部门不存在；
		List employeeList9 = etemployeeService.getEtEmployeeNotExistsDept(etemployee);
		if(!(employeeList9.size()>0)){
			return R.failed(null,"所选部门不存在!");
		}
		// 入：10.所选部门已经失效；
		List employeeList10 = etemployeeService.getEtEmployeeDeptIsDisable(etemployee);
		if(employeeList10.size()>0){
			return R.failed(null,"所选部门已经失效!");
		}
		//入：11.所选岗位不存在；
		List employeeList11 = etemployeeService.getEtEmployeeNotExistsJob(etemployee);
		if(!(employeeList11.size()>0)){
			return R.failed(null,"所选岗位不存在!");
		}

		//入：12.所选岗位已经失效；
		List employeeList12 = etemployeeService.getEtEmployeeJobIsDisable(etemployee);
		if(employeeList12.size()>0){
			return R.failed(null,"所选岗位已经失效!");
		}
		// 入：13.身份证不符合要求，请看身份证验证提示窗口；
		IdcardValidator iv = new IdcardValidator();
		String certno = etemployee.getCertno();
		boolean b = false;
		if(certno!="" &&certno!=null){
			b = iv.isValidatedAllIdcard(certno);
			//计算出生日期
			int year = Integer.parseInt(certno.substring(6, 10));
			int month = Integer.parseInt(certno.substring(10, 12));
			int day = Integer.parseInt(certno.substring(12, 14));
			String birthday = year+"-"+month+"-"+day;
			etemployee.setBirthday(birthday);
			if(!b){
				return R.failed(null,"身份证不符合要求，请查看身份证是否正确!");
			}
		}

		//入：14.手机号与现有人员重复；
		List employeeList14 = etemployeeService.getEtEmployeeMobileRepeat(etemployee);
		if(employeeList14.size()>0){
			return R.failed(null,"手机号与现有人员重复!");
		}
		//入：14-1.手机号与正在办理入职的人员重复；
		List employeeList141 = etemployeeService.getEtstaffRegisterByMobile(etemployee);
		if(employeeList141.size()>0){
			return R.failed(null,"手机号与正在办理入职的人员重复!");
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
		if(country==41){
			if(certType!=1){
				return R.failed(null,"国籍为中华人民共和国的员工必须填写身份证!");
			}
		}
		EtstaffAll  etstaffAll = new EtstaffAll();
		BeanUtils.copyProperties(etstaffRegister,etstaffAll);
		etstaffAll.setClosed(pigxUser.getId());
		etstaffAll.setClosedtime(currentTime);
		etstaffAll.setInitialized(1);// 2020-08-12 ssj添加 ∵查询最近入职的代码里有对这个字段=1的判断
		etstaffAllService.save(etstaffAll);
		etstaffRegisterService.removeById(e.getId());

		etemployeeService.save(etemployee);


		//插入用户表sys_user
		SysUser sysUser = new SysUser();
		sysUser.setUsername(etemployee.getName());
		sysUser.setCorpid(pigxUser.getCorpid());
		sysUser.setCorpcode(pigxUser.getCorpcode());
		sysUser.setCorpname(pigxUser.getCorpname());
		sysUser.setCompid(pigxUser.getCompid());
		sysUser.setCompname(pigxUser.getCompname());
		sysUser.setAccount(etemployee.getMobile());
		sysUser.setDeptId(etemployee.getDepid());
		sysUser.setPassword(ENCODER.encode(etemployee.getMobile()));
		sysUser.setPhone(etemployee.getMobile());
		sysUser.setLockFlag("0");
		sysUser.setCreateTime(LocalDateUtil.dateToLocalDateTime(new Date()));
		sysUser.setDelFlag("0");
		sysUser.setEid(etemployee.getEid());
		sysUser.setQywxFlag(1);
		sysUser.setStatus(0);
		sysUserService.save(sysUser);

		//插入薪资表
		/*
		Ctemployee ctemployee2 =ctemployeeService.getById(etemployee.getEid());
		if(org.springframework.util.StringUtils.isEmpty(ctemployee2)){
			Integer eid = etemployee.getEid();
			Ctemployee ctemployee = new Ctemployee();
			ctemployee.setEid(eid);
			ctemployee.setName(etemployee.getName());
			ctemployee.setCorpcode(pigxUser.getCorpcode());
			ctemployee.setCompid(pigxUser.getCompid());
			ctemployee.setCreatedate(currentTime);
			ctemployee.setBadge(etemployee.getBadge());
			ctemployee.setCorpcode(pigxUser.getCorpcode());
			ctemployee.setCorpid(pigxUser.getCorpid());
			ctemployee.setCompid(pigxUser.getCompid());
			ctemployeeService.save(ctemployee);

		}
		*/

		CtsalaryRegister ctsalaryRegister = new CtsalaryRegister();
		ctsalaryRegister.setEid(etemployee.getEid());
		List list3 = ctsalaryRegisterService.list(Wrappers.query(ctsalaryRegister));
		if(list3.size()<1){
			CtsalaryRegister ctsalaryRegister2 = new CtsalaryRegister();
			ctsalaryRegister2.setEid(etemployee.getEid());
			ctsalaryRegister2.setName(etemployee.getName());
			ctsalaryRegister2.setBadge(etemployee.getBadge());
			ctsalaryRegister2.setCompid(etemployee.getCompid());
			ctsalaryRegister2.setDepid(etemployee.getDepid());
			ctsalaryRegister2.setJobid(etemployee.getJobid());
			ctsalaryRegister2.setEmpstatus(etemployee.getEmpstatus());
			ctsalaryRegister2.setJoindate(etemployee.getJoindate());
			ctsalaryRegister2.setWorkcity(etemployee.getWorkcity());
			ctsalaryRegister2.setEmptype(etemployee.getEmptype());
			ctsalaryRegister2.setEmpcategory(etemployee.getEmpcategory());
			ctsalaryRegister2.setEmpgrade(etemployee.getEmpgrade());
			ctsalaryRegister2.setEmpkind(etemployee.getEmpkind());
			ctsalaryRegister2.setEmpproperty(etemployee.getEmpproperty());
			ctsalaryRegister2.setEmpgroup(etemployee.getEmpgroup());
			ctsalaryRegister2.setCorpcode(pigxUser.getCorpcode());
			ctsalaryRegister2.setCorpid(pigxUser.getCorpid());
			ctsalaryRegister2.setCompid(pigxUser.getCompid());
			ctsalaryRegister2.setRegby(pigxUser.getId());
			ctsalaryRegister2.setRegdate(currentTime);
			ctsalaryRegister2.setJoindate(etemployee.getJoindate());
			ctsalaryRegisterService.save(ctsalaryRegister2);
		}
		//插入社保
		CtbenefitRegister ctbenefitRegister = new CtbenefitRegister();
		ctbenefitRegister.setEid(etemployee.getEid());
		List list4 = ctbenefitRegisterService.list(Wrappers.query(ctbenefitRegister));
		if(list4.size()<1){
			CtbenefitRegister ctbenefitRegister2 = new CtbenefitRegister();
			ctbenefitRegister2.setEid(etemployee.getEid());
			ctbenefitRegister2.setName(etemployee.getName());
			ctbenefitRegister2.setCorpcode(pigxUser.getCorpcode());
			ctbenefitRegister2.setCorpid(pigxUser.getCorpid());
			ctbenefitRegister2.setCompid(pigxUser.getCompid());
			ctbenefitRegister2.setRegby(pigxUser.getId());
			ctbenefitRegister2.setRegdate(currentTime);
			ctbenefitRegister2.setJoindate(etemployee.getJoindate());
			ctbenefitRegister2.setBadge(etemployee.getBadge());
			ctbenefitRegister2.setCompid(etemployee.getCompid());
			ctbenefitRegister2.setDepid(etemployee.getDepid());
			ctbenefitRegister2.setJobid(etemployee.getJobid());
			ctbenefitRegister2.setEmpstatus(etemployee.getEmpstatus());
			ctbenefitRegister2.setJoindate(etemployee.getJoindate());
			ctbenefitRegister2.setWorkcity(etemployee.getWorkcity());
			ctbenefitRegister2.setEmptype(etemployee.getEmptype());
			ctbenefitRegister2.setEmpcategory(etemployee.getEmpcategory());
			ctbenefitRegister2.setEmpgrade(etemployee.getEmpgrade());
			ctbenefitRegister2.setEmpkind(etemployee.getEmpkind());
			ctbenefitRegister2.setEmpproperty(etemployee.getEmpproperty());
			ctbenefitRegister2.setEmpgroup(etemployee.getEmpgroup());
			ctbenefitRegister2.setCorpcode(pigxUser.getCorpcode());
			ctbenefitRegister2.setCompid(pigxUser.getCompid());
			ctbenefitRegisterService.save(ctbenefitRegister2);
		}
		//插入考勤
		AtstaffRegister atstaffRegister = new AtstaffRegister();
		atstaffRegister.setCorpcode(pigxUser.getCorpcode());
		atstaffRegister.setCorpid(pigxUser.getCorpid());
		atstaffRegister.setTerm(currentTime);
		atstaffRegister.setEid(etemployee.getEid());
		atstaffRegister.setBadge(etemployee.getBadge());
		atstaffRegister.setName(etemployee.getName());
		atstaffRegister.setCompid(etemployee.getCompid());
		atstaffRegister.setDepid(etemployee.getDepid());
		atstaffRegister.setJobid(etemployee.getJobid());
		atstaffRegister.setJoindate(etemployee.getJoindate());
		atstaffRegister.setRegby(pigxUser.getId());
		atstaffRegister.setRegdate(currentTime);
		atstaffRegister.setCardid(UUID.randomUUID().toString());
		atstaffRegister.setJoindate(etemployee.getJoindate());
		AtstaffRegister atstaffRegister2 = new AtstaffRegister();
		atstaffRegister2.setEid(etemployee.getEid());
		AtstaffAll atstaffAll = new AtstaffAll();
		atstaffAll.setEid(etemployee.getEid());
		List list = atstaffRegisterService.list(Wrappers.query(atstaffRegister2));
		List list2 = atstaffAllService.list(Wrappers.query(atstaffAll));
		if(list.size()<1 && list2.size()<1){
			atstaffRegisterService.save(atstaffRegister);
		}

		Etevent etevent = new Etevent();
		BeanUtils.copyProperties(etemployee,etevent);
		etevent.setType(1);
		etevent.setEffectdate(etemployee.getJoindate());
		etevent.setRemark("新员工入职");
		eteventService.save(etevent);


		//移动端普通员工权限
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(sysUser.getUserId());
		sysUserRole.setRoleId(7);
		sysUserRoleService.save(sysUserRole);
		//pc端普通员工权限
		SysUserRole sysUserRole2 = new SysUserRole();
		sysUserRole2.setUserId(sysUser.getUserId());
		sysUserRole2.setRoleId(13);
		sysUserRoleService.save(sysUserRole2);

		//处理入职装填
		return R.ok(sysUser,"操作成功！");
	}

/*

    */
/**
     * 修改员工信息表
     * @param etemployee 员工信息表
     * @return R    @PreAuthorize("@pms.hasPermission('admin_etemployee_edit')" )
     *//*

    @ApiOperation(value = "修改员工信息表", notes = "修改员工信息表")
    @SysLog("修改员工信息表" )
    @PutMapping("/updateById")
    public R updateById(@RequestBody Etemployee etemployee) {
        return R.ok(etemployeeService.updateById(etemployee));
    }
*/

    /**
     * 通过id删除员工信息表
     * @param eid id
     * @return R
     */
    @ApiOperation(value = "通过id删除员工信息表", notes = "通过id删除员工信息表")
    @SysLog("通过id删除员工信息表" )
    @DeleteMapping("/{eid}" )
    @PreAuthorize("@pms.hasPermission('admin_etemployee_del')" )
    public R removeById(@PathVariable Integer eid) {
        return R.ok(etemployeeService.removeById(eid));
    }
	/**
	 * 根据条件判断部门是否存在
	 * @return R
	 */
	@ApiOperation(value = "根据条件判断部门是否存在", notes = "根据条件判断部门是否存在")
	@PostMapping("/updateOtdepartment" )
	public R getOtdepartmentByCompCode(@RequestBody Etemployee etemployee){
		return R.ok("数据已修改！");
	}
	/**
	 * 员工花名册-分页
	 * @param page 分页对象
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "员工花名册", notes = "员工花名册")
	@GetMapping("/getEtEmployeeHMC" )
	public R getEtEmployeeHMC(Page page, Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeService.getEtEmployeeHMC(page,etemployee));
	}
	//报表
	//1、人事概况
	/**
	 * 人事概况
	 * @return
	 */
	@ApiOperation(value = "人事概况", notes = "人事概况")
	@GetMapping("/getPersonnelProfile" )
	public R getPersonnelProfile() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		long count1 = 0;
		long count2 = 0;
		long count4 = 0;
		long countsum = 0;
		Map empStatus1 = etemployeeService.getPersonnelProfileEmpStatus1(etemployee);
		if(empStatus1!=null){
			count1 = (Long) empStatus1.get("count");
			empStatus1.put("empstatus","正式");
		}else{
			empStatus1 = new HashMap();
			empStatus1.put("empstatus","正式");

		}

		Map empStatus2 = etemployeeService.getPersonnelProfileEmpStatus2(etemployee);
		if(empStatus2!=null){
			count2 = (Long) empStatus2.get("count");
			empStatus2.put("empstatus","实习");
		}else{
			empStatus2 = new HashMap();
			empStatus2.put("empstatus","实习");
		}

		Map empStatus4 = etemployeeService.getPersonnelProfileEmpStatus4(etemployee);
		if(empStatus4!=null){
			count4 = (Long) empStatus4.get("count");
			empStatus4.put("empstatus","试用");
		}else{
			empStatus4 = new HashMap();
			empStatus4.put("empstatus","试用");

		}

		Map empStatussum = etemployeeService.getPersonnelProfileSum(etemployee);
		if(empStatussum!=null){
			countsum = (Long) empStatussum.get("count");
		}

		double bili1 = 0.0;
		double bili2 =  0.0;
		double bili4 =  0.0;
		if(countsum==0){

		}else{
			bili1 = new BigDecimal((float)count1/countsum).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili2 = new BigDecimal((float)count2/countsum).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili4 = new BigDecimal((float)count4/countsum).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		}
		empStatus1.put("bili",bili1);
		empStatus2.put("bili",bili2);
		empStatus4.put("bili",bili4);
		List resultList = new ArrayList(4);
		resultList.add(0,empStatus1);
		resultList.add(1,empStatus2);
		resultList.add(2,empStatus4);
		resultList.add(3,empStatussum);
		return R.ok(resultList);
	}
	/**
	 * 人事提醒
	 * @return
	 */
	@ApiOperation(value = "人事提醒", notes = "人事提醒")
	@GetMapping("/getPersonnelReminder" )
	public R getPersonnelReminder() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		Page page1 = new Page();
		Page page2 = new Page();
		Page page3 = new Page();
		Page page4 = new Page();
		Page page5 = new Page();
		//未签订合同
		IPage<Map> resultMap1 =etemployeeService.getHasNoContract(page1,etemployee);
		Map map1 = new HashMap();
		map1.put("title","1");
		map1.put("count",page1.getTotal());
		map1.put("people",resultMap1);
		//合同到期
		IPage<Map> resultMap2 =etemployeeService.getContractExpire(page2,etemployee);
		Map map2 = new HashMap();
		map2.put("title","2");
		map2.put("count",page2.getTotal());
		map2.put("people",resultMap2);
		//待入职
		IPage<Map> resultMap3 =etemployeeService.getEtstaffRegisterForRuZhi(page3,etemployee);
		Map map3 = new HashMap();
		map3.put("title","3");
		map3.put("count",page3.getTotal());
		map3.put("people",resultMap3);
		//待转正
		IPage<Map> resultMap4 =etemployeeService.getEtemployeeWaitingForCorrection(page4,etemployee);
		Map map4 = new HashMap();
		map4.put("title","4");
		map4.put("count",page4.getTotal());
		map4.put("people",resultMap4);
		//待离职
		IPage<Map> resultMap5 =etemployeeService.getEtleaveRegisterWaitingForLeave(page5,etemployee);
		Map map5 = new HashMap();
		map5.put("title","5");
		map5.put("count",page5.getTotal());
		map5.put("people",resultMap5);

		List resultList = new ArrayList(5);
		resultList.add(0,map1);
		resultList.add(1,map2);
		resultList.add(2,map3);
		resultList.add(3,map4);
		resultList.add(4,map5);


		return R.ok(resultList);
	}


	/**
	 * 人事提醒
	 * @return
	 */
	@ApiOperation(value = "未签订合同列表", notes = "未签订合同列表")
	@GetMapping("/getHasNoContract" )
	public R getHasNoContract(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		//未签订合同
		IPage<Map> resultMap1 =etemployeeService.getHasNoContract(page,etemployee);
		return R.ok(resultMap1);
	}

	/**
	 * 人事提醒
	 * @return
	 */
	@ApiOperation(value = "合同到期列表", notes = "合同到期列表")
	@GetMapping("/getContractExpire" )
	public R getContractExpire(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		//合同到期列表
		IPage<Map> resultMap =etemployeeService.getContractExpire2(page,etemployee);
		return R.ok(resultMap);
	}

	/**
	 * 人事提醒
	 * @return
	 */
	@ApiOperation(value = "待入职列表", notes = "待入职列表")
	@GetMapping("/getEtstaffRegisterForRuZhi" )
	public R getEtstaffRegisterForRuZhi(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		//合同到期列表
		IPage<Map> resultMap =etemployeeService.getEtstaffRegisterForRuZhi(page,etemployee);
		return R.ok(resultMap);
	}

	/**
	 * 人事提醒
	 * @return
	 */
	@ApiOperation(value = "待转正列表", notes = "待转正列表")
	@GetMapping("/getEtemployeeWaitingForCorrection" )
	public R getEtemployeeWaitingForCorrection(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		//合同到期列表
		IPage<Map> resultMap =etemployeeService.getEtemployeeWaitingForCorrection(page,etemployee);
		return R.ok(resultMap);
	}
	/**
	 * 人事提醒
	 * @return
	 */
	@ApiOperation(value = "待离职职表", notes = "待离职职表")
	@GetMapping("/getEtleaveRegisterWaitingForLeave" )
	public R getEtleaveRegisterWaitingForLeave(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		//合同到期列表
		IPage<Map> resultMap =etemployeeService.getEtleaveRegisterWaitingForLeave(page,etemployee);
		return R.ok(resultMap);
	}

	/**
	 * 性别报表
	 * @return
	 */
	@ApiOperation(value = "性别报表", notes = "性别报表")
	@GetMapping("/getPersonnelGender" )
	public R getPersonnelGender() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		// 性别报表
		List<Map> resultMap1 =etemployeeService.getEtemployeeForGender(etemployee);
		Map map = null;
		long gender = 1;
		long count1 = 0;
		long count2 = 0;
		long count3 = 0;
		if(resultMap1!=null){
			for(int i=0;i<resultMap1.size();i++){
				map = resultMap1.get(i);
				gender = (Long)map.get("gender");
				if(gender==1){
					count1 =  (Long)map.get("gendercount");
				}
				if(gender==2){
					count2 =  (Long)map.get("gendercount");
				}
				if(gender==3){
					count3 =  (Long)map.get("gendercount");
				}
			}
		}

		long allcount =0;
		//性别报表all
		Map resultMap2 =etemployeeService.getEtemployeeForGenderALL(etemployee);
		if(resultMap2!=null){
			allcount = (Long) resultMap2.get("allcount");
		}

		double bili1 = 0.0;  //男
		double bili2 =  0.0;// 女
		double bili3 =  0.0; // 未知
		if(allcount==0){

		}else{
			bili1 = new BigDecimal((float)count1/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili2 = new BigDecimal((float)count2/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili3 = new BigDecimal((float)count3/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		}

		Map map1 = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		map1.put("gender","男");
		map1.put("count",count1);
		map1.put("bili",bili1);
		map2.put("gender","女");
		map2.put("count",count2);
		map2.put("bili",bili2);
		map3.put("gender","未知");
		map3.put("count",count3);
		map3.put("bili",bili3);
		List resultList = new ArrayList(3);
		resultList.add(0,map1);
		resultList.add(1,map2);
		resultList.add(2,map3);
		/*Map resultMap = new HashMap();
		resultMap.put("1",map1);
		resultMap.put("2",map2);
		resultMap.put("3",map3);*/
		return R.ok(resultList);
	}

	/**
	 * 年龄报表
	 * @return
	 */
	@ApiOperation(value = "年龄报表", notes = "年龄报表")
	@GetMapping("/getEtemployeeForAge" )
	public R getEtemployeeForAge() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		BigDecimal count1 = new BigDecimal(0);
		BigDecimal count2 = new BigDecimal(0);
		BigDecimal count3 = new BigDecimal(0);
		BigDecimal count4 = new BigDecimal(0);
		BigDecimal count5 = new BigDecimal(0);
		BigDecimal count = new BigDecimal(0);
		//年龄段分布
		//18-24,25-29,30-35,36-40,40岁以上
		//18-24
		etemployee.setAge1(18);
		etemployee.setAge2(24);
		Map resultMap1 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap1!=null){
			count1 =  (BigDecimal) resultMap1.get("people");
		}else{
			resultMap1 = new HashMap();
		}

		//25-29
		etemployee.setAge1(25);
		etemployee.setAge2(29);
		Map resultMap2 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap2!=null){
			count2 =  (BigDecimal)resultMap2.get("people");
		}else{
			resultMap2 = new HashMap();
		}

		//30-35
		etemployee.setAge1(30);
		etemployee.setAge2(35);
		Map resultMap3 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap3!=null){
			count3 =  (BigDecimal)resultMap3.get("people");
		}else{
			resultMap3 = new HashMap();
		}
		//36-40
		etemployee.setAge1(36);
		etemployee.setAge2(40);
		Map resultMap4 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap4!=null){
			count4 =  (BigDecimal)resultMap4.get("people");
		}else{
			resultMap4 = new HashMap();
		}
		//40岁以上
		etemployee.setAge1(40);
		etemployee.setAge2(200);
		Map resultMap5 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap5!=null){
			count5 =  (BigDecimal)resultMap5.get("people");
		}else{
			resultMap5 = new HashMap();
		}

		//总人数
		//count = count1 + count2 + count3 + count4 + count5;
		count = count1.add(count2).add(count3).add(count4).add(count5);
		double bili1 = 0.0;  //
		double bili2 =  0.0;//
		double bili3 =  0.0; //
		double bili4 =  0.0; //
		double bili5 =  0.0; //
		if(count.compareTo(BigDecimal.ZERO)==0){

		}else{
			bili1 = count1.divide(count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili2 = count2.divide(count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili3 = count3.divide(count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili4 = count4.divide(count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili5 = count5.divide(count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		}
		resultMap1.put("order","18-24");
		resultMap1.put("bili",bili1+"");
		resultMap1.put("people",count1);
		resultMap2.put("order","25-29");
		resultMap2.put("bili",bili2+"");
		resultMap2.put("people",count2);
		resultMap3.put("order","30-35");
		resultMap3.put("bili",bili3+"");
		resultMap3.put("people",count3);
		resultMap4.put("order","36-40");
		resultMap4.put("bili",bili4+"");
		resultMap4.put("people",count4);
		resultMap5.put("order","40");
		resultMap5.put("bili",bili5+"");
		resultMap5.put("people",count5);


		Map resultMap = new HashMap();
		resultMap.put("1",resultMap1);
		resultMap.put("2",resultMap2);
		resultMap.put("3",resultMap3);
		resultMap.put("4",resultMap4);
		resultMap.put("5",resultMap5);
		List resultList = new ArrayList(5);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		resultList.add(2,resultMap3);
		resultList.add(3,resultMap4);
		resultList.add(4,resultMap5);

		return R.ok(resultList);
	}

	/**
	 * 员工转正
	 * @param etemployee 员工转正
	 * @return
	 */
	@ApiOperation(value = "员工转正", notes = "员工转正")
	@GetMapping("/getEmployeeZhuangZheng" )
	public R getEmployeeZhuangZheng(Page page ,Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeService.getEmployeeZhuangZheng(page,etemployee));
	}

	/**
	 * 员工关怀
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "员工关怀", notes = "员工关怀")
	@GetMapping("/getEmployeeCare" )
	public R getEmployeeCare(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		etemployee.setDay(10);
		return R.ok(etemployeeService.getEmployeeCare(etemployee));
	}
	/**
	 * 入职周年
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "入职周年", notes = "入职周年")
	@GetMapping("/getEmployeeEntrAnniversary" )
	public R getEmployeeEntrAnniversary(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		etemployee.setDay(10);
		return R.ok(etemployeeService.getEmployeeEntrAnniversary(etemployee));
	}

	//统计报表
	/**
	 * 员工在职、待入职、待离职状态统计
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "员工在职/待入职/待离职状态统计", notes = "员工在职、待入职、待离职状态统计")
	@PostMapping("/getEmployeeStatusInformation" )
	public R getEmployeeStatusInformation(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);

		long zaizhicount = 0;//在职员工
		long dairuzhicount = 0;//待入职
		long dailizhicount = 0;//待离职
		Map empStatusZaiZhi = etemployeeService.getPersonnelProfileSum(etemployee);
		if(empStatusZaiZhi!=null){
			zaizhicount = (Long) empStatusZaiZhi.get("count");
		}
		Map empStatusDaiRuZhi = etemployeeService.getEtstaffRegisterCount(etemployee);
		if(empStatusDaiRuZhi!=null){
			dairuzhicount = (Long) empStatusDaiRuZhi.get("count");
		}
		Map empStatusDaiLiZhi = etemployeeService.getEtleaveRegisterCount(etemployee);
		if(empStatusDaiLiZhi!=null){
			dailizhicount = (Long) empStatusDaiLiZhi.get("count");
		}
		Map resultMap1 = new HashMap();
		Map resultMap2 = new HashMap();
		Map resultMap3 = new HashMap();
		resultMap1.put("empstatus","在职");
		resultMap1.put("people",zaizhicount);
		resultMap2.put("empstatus","待入职");
		resultMap2.put("people",dairuzhicount);
		resultMap3.put("empstatus","待离职");
		resultMap3.put("people",dailizhicount);
		List resultList = new ArrayList(3);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		resultList.add(2,resultMap3);
		return R.ok(resultList);
	}
	/**
	 * 政治面貌统计
	 * @return
	 */
	@ApiOperation(value = "政治面貌统计", notes = "政治面貌统计")
	@PostMapping("/getEmployeeForParty" )
	public R getEmployeeForParty() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		// 政治面貌统计
		List<Map> resultMap1 =etemployeeService.getEmployeeForParty(etemployee);
		Map map = null;
		int party = 1;
		long count1 = 0;  //中共党员
		long count2 = 0; //中共预备党员
		long count3 = 0;//共青团员
		long count4 = 0;//其他
		long count5 = 0;//其他
		long count6 = 0;//其他
		long count7 = 0;//其他
		long count8 = 0;//其他
		long count9 = 0;//其他
		long count10 = 0;//其他
		long count11 = 0;//其他
		long count12 = 0;//无党派民主人士
		long count13 = 0;//群众
		if(resultMap1!=null){
			for(int i=0;i<resultMap1.size();i++){
				map = resultMap1.get(i);
				Object o = map.get("party");
				if(o!=null){
					party = (Integer)map.get("party");
				}else{
					party = 99999;
				}

				if(party==190){
					count1 =  (Long)map.get("partycount");
				}
				if(party==191){
					count2 =  (Long)map.get("partycount");
				}
				if(party==192){
					count3 =  (Long)map.get("partycount");
				}
				if(party==193){
					count4 =  (Long)map.get("partycount");
				}
				if(party==194){
					count5 =  (Long)map.get("partycount");
				}
				if(party==195){
					count6 =  (Long)map.get("partycount");
				}
				if(party==196){
					count7 =  (Long)map.get("partycount");
				}
				if(party==197){
					count8 =  (Long)map.get("partycount");
				}
				if(party==198){
					count9 =  (Long)map.get("partycount");
				}
				if(party==199){
					count10 =  (Long)map.get("partycount");
				}
				if(party==200){
					count11 =  (Long)map.get("partycount");
				}
				if(party==201){
					count12 =  (Long)map.get("partycount");
				}
				if(party==202){
					count13 =  (Long)map.get("partycount");
				}
			}
		}

		long allcount =0;
		allcount = count1 + count2 +count3 + count4 +count5 +
				count6 +count7 + count8 +count9 + count10
				+count11 + count12 + count13;
		long qitacount = count4 +count5 +
				count6 +count7 + count8 +count9 + count10
				+count11;
		double bili1 = 0.0;  //中共党员
		double bili2 =  0.0;// 共青团员
		double bili3 =  0.0; // 无党派民主人士
		double bili4 =  0.0; //群众
		double bili5 =  0.0; //其他

		if(allcount==0){

		}else{
			bili1 = new BigDecimal((float)(count1+count2)/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili2 = new BigDecimal((float)count3/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili3 = new BigDecimal((float)count12/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili4 = new BigDecimal((float)count13/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili5 = new BigDecimal((float)qitacount/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		}

		Map map1 = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		Map map4 = new HashMap();
		Map map5 = new HashMap();
		map1.put("party","中共党员");
		map1.put("people",count1+count2);
		map1.put("bili",bili1);
		map2.put("party","共青团员");
		map2.put("people",count3);
		map2.put("bili",bili2);
		map3.put("party","无党派民主人士");
		map3.put("people",count12);
		map3.put("bili",bili3);
		map4.put("party","群众");
		map4.put("people",count13);
		map4.put("bili",bili4);
		map5.put("party","其他");
		map5.put("people",qitacount);
		map5.put("bili",bili5);

		List resultList = new ArrayList(5);
		resultList.add(0,map1);
		resultList.add(1,map2);
		resultList.add(2,map3);
		resultList.add(3,map4);
		resultList.add(4,map5);
		return R.ok(resultList);
	}
	/**
	 *
	 * 各月份人员流动统计
	 * @return
	 */
	@ApiOperation(value = "各月份人员流动统计", notes = "各月份人员流动统计")
	@PostMapping("/getEmployeeFlowByMonth" )
	public R getEmployeeFlowByMonth() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtleaveAll etleaveAll  = new EtleaveAll();
		etleaveAll.setCorpcode(corpcode);
		etleaveAll.setType(1);
		EtstaffAll etstaffAll = new EtstaffAll();
		etstaffAll.setCorpcode(corpcode);
		etstaffAll.setType(1);
		List<Map> ruzhi = etemployeeService.getEmployeeFlowRuZhiByMonth(etstaffAll);
		List<Map> lizhi = etemployeeService.getEmployeeLiZhiFlowByMonth(etleaveAll);

		Map map1 = new HashMap();
		Map map2 = new HashMap();
		map1.put("title","入职");
		map1.put("data",ruzhi);
		map2.put("title","离职");
		map2.put("data",lizhi);
		List resultList = new ArrayList(2);
		resultList.add(0,map1);
		resultList.add(1,map2);
		return R.ok(resultList);
	}

	/**
	 *
	 * 离职原因占比
	 * @return
	 */
	@ApiOperation(value = "离职原因占比", notes = "离职原因占比")
	@PostMapping("/getEmployeeLiZhiByReason" )
	public R getEmployeeLiZhiByReason() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtleaveAll etleaveAll = new EtleaveAll();
		etleaveAll.setCorpcode(corpcode);
		etleaveAll.setType(1);
		long reasoncount= 0;
		double bili = 0.0;
		long count = 0;
		List<Map> lizhi = etemployeeService.getEmployeeLiZhiByReason(etleaveAll);
		Map reasoncountlizhi = etemployeeService.getEmployeeLiZhiByReasonCount(etleaveAll);
		if(reasoncountlizhi!=null){
			reasoncount = (Long) reasoncountlizhi.get("reasoncount");

		}else{
			reasoncount =99999999;
		}
		List resultList = new ArrayList(lizhi.size());
		Map map = null;
		for(int i=0;i<lizhi.size();i++){
			map = lizhi.get(i);
			count= (Long)map.get("reasoncount");
			bili = new BigDecimal((float)count/reasoncount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			map.put("bili",bili);
			resultList.add(i,map);
		}
		return R.ok(resultList);
	}
	/**
	 * 工龄报表
	 * @return
	 */
	@ApiOperation(value = "工龄报表", notes = "工龄报表")
	@PostMapping("/getEtemployeeForGAge" )
	public R getEtemployeeForGAge() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		double count1 = 0;
		double count2 = 0;
		double count3 = 0;
		double count4 = 0;
		double count5 = 0;
		double count6 = 0;
		double count = 0;
		//年龄段分布
		//6个月以下,6个月-1年(6-12),1-3年(12-36),3-5年(36-72),5年以上(72以上)
		//18-24
		etemployee.setAge1(0);
		etemployee.setAge2(6);
		Map resultMap1 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap1!=null){
			count1 = Double.parseDouble(resultMap1.get("people").toString()) ;
		}else{
			resultMap1 = new HashMap();
		}

		//6个月-1年(6-12)
		etemployee.setAge1(6);
		etemployee.setAge2(12);
		Map resultMap2 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap2!=null){
			count2 =  Double.parseDouble(resultMap2.get("people").toString()) ;
		}else{
			resultMap2 = new HashMap();
		}

		//1-3年(12-36)
		etemployee.setAge1(12);
		etemployee.setAge2(36);
		Map resultMap3 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap3!=null){
			count3 =  Double.parseDouble(resultMap3.get("people").toString()) ;
		}else{
			resultMap3 = new HashMap();
		}
		//3-5年(36-72)
		etemployee.setAge1(36);
		etemployee.setAge2(72);
		Map resultMap4 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap4!=null){
			count4 =  Double.parseDouble(resultMap4.get("people").toString()) ;
		}else{
			resultMap4 = new HashMap();
		}
		//5年以上(72以上)
		etemployee.setAge1(72);
		etemployee.setAge2(600);
		Map resultMap5 =etemployeeService.getEtemployeeForAge(etemployee);
		if(resultMap5!=null){
			count5 =  Double.parseDouble(resultMap5.get("people").toString()) ;
		}else{
			resultMap5 = new HashMap();
		}

		count = count1 + count2 + count3 + count4 + count5;

		double bili1 = 0.0;  //
		double bili2 =  0.0;//
		double bili3 =  0.0; //
		double bili4 =  0.0; //
		double bili5 =  0.0; //
		if(count==0){

		}else{
			bili1 = new BigDecimal((float)count1/count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili2 = new BigDecimal((float)count2/count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili3 = new BigDecimal((float)count3/count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili4 = new BigDecimal((float)count4/count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili5 = new BigDecimal((float)count5/count).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		}
		//6个月以下,6个月-1年(6-12),1-3年(12-36),3-5年(36-72),5年以上(72以上)
		resultMap1.put("title","6个月以下");
		resultMap1.put("bili",bili1+"");
		resultMap1.put("people",count1);
		resultMap2.put("title","6个月-1年");
		resultMap2.put("bili",bili2+"");
		resultMap2.put("people",count2);
		resultMap3.put("title","1-3年");
		resultMap3.put("bili",bili3+"");
		resultMap3.put("people",count3);
		resultMap4.put("title","3-5年");
		resultMap4.put("bili",bili4+"");
		resultMap4.put("people",count4);
		resultMap5.put("title","5年以上");
		resultMap5.put("bili",bili5+"");
		resultMap5.put("people",count5);


		List resultList = new ArrayList(5);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		resultList.add(2,resultMap3);
		resultList.add(3,resultMap4);
		resultList.add(4,resultMap5);

		return R.ok(resultList);
	}
	/**
	 *
	 * 离职原因占比
	 * @return
	 */
	@ApiOperation(value = "婚姻状况占比", notes = "婚姻状况占比")
	@PostMapping("/getEmployeeByMarriage" )
	public R getEmployeeByMarriage() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		long marriagecount= 0;
		double bili = 0.0;
		long count = 0;
		List<Map> marriageList = etemployeeService.getEmployeeByMarriage(etemployee);
		Map marriageMap = etemployeeService.getEmployeeByMarriageCount(etemployee);
		if(marriageMap!=null){
			marriagecount = (Long) marriageMap.get("marriagecount");

		}else{
			marriagecount =99999999;
		}
		List resultList = new ArrayList(marriageList.size());
		Map map = null;
		for(int i=0;i<marriageList.size();i++){
			map = marriageList.get(i);
			count= (Long)map.get("marriagecount");
			bili = new BigDecimal((float)count/marriagecount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			map.put("bili",bili);
			resultList.add(i,map);
		}
		return R.ok(resultList);
	}

	/**
	 *
	 * 省市区占比
	 * @return
	 */
	@ApiOperation(value = "省市区占比", notes = "省市区占比")
	@PostMapping("/getEmployeeByResident" )
	public R getEmployeeByResident() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		long residentcount= 0;
		double bili = 0.0;
		long count = 0;
		List<Map> residentList = etemployeeService.getEmployeeByResident(etemployee);
		Map residentMap = etemployeeService.getEmployeeByResidentCount(etemployee);
		if(residentMap!=null){
			residentcount = (Long) residentMap.get("residentcount");

		}else{
			residentcount =99999999;
		}
		List resultList = new ArrayList(residentList.size());
		Map map = null;
		for(int i=0;i<residentList.size();i++){
			map = residentList.get(i);
			count= (Long)map.get("residentcount");
			bili = new BigDecimal((float)count/residentcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			map.put("bili",bili);
			resultList.add(i,map);
		}
		return R.ok(resultList);
	}

	/**
	 * 员工花名册查询-PC端
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工花名册查询-PC端", notes = "员工花名册查询-PC端")
	@PostMapping("/getEtEmployeeHMCBySqlForPc")
	public R getEtEmployeeHMCBySqlForPc(Page page,@RequestBody(required = false) Etemployee etemployee) {
		if(org.springframework.util.StringUtils.isEmpty(etemployee)){
			etemployee = new Etemployee();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEtEmployeeHMCBySqlForPc(page,etemployee));
	}

	/**
	 * 员工花名册查询-PC端
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工花名册查询-PC端-待离职", notes = "员工花名册查询-PC端-待离职")
	@PostMapping("/getEtEmployeeHMCBySqlForPc2")
	public R getEtEmployeeHMCBySqlForPc2(Page page) {
		Etemployee etemployee = new Etemployee();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEtEmployeeHMCBySqlForPc2(page,etemployee));
	}
	/**
	 * 员工花名册查询-PC端
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工花名册查询-PC端-已离职", notes = "员工花名册查询-PC端-已离职")
	@PostMapping("/getEtEmployeeHMCBySqlForPc3")
	public R getEtEmployeeHMCBySqlForPc3(Page page) {
		Etemployee etemployee = new Etemployee();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEtEmployeeHMCBySqlForPc3(page,etemployee));
	}
	/**
	 * 员工花名册人员统计
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "员工花名册人员统计", notes = "员工花名册人员统计")
	@PostMapping("/getEmployeeStatusInformationForHMCPC" )
	public R getEmployeeStatusInformationForHMCPC(Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);

		long zaizhicount = 0;//在职员工
		long quanzhicount = 0;//全职人员
		long feiquanzhicount = 0;//非全职人员
		long zhengshicount = 0;//正式
		long shiyongcount = 0;//试用
		long shixicount = 0;//实习
		long dailizhicount = 0;//待离职
		long yilizhicount = 0;//已离职
		//在职
		Map empStatusZaiZhi = etemployeeService.getPersonnelProfileSum(etemployee);
		if(empStatusZaiZhi!=null){
			zaizhicount = (Long) empStatusZaiZhi.get("count");
		}

		//全职
		/*Map empStatusquanzhi = etemployeeMapper.listPersonnelProfileEmpStatus9(etemployee);
		if(empStatusquanzhi!=null){
			quanzhicount = (Long) empStatusquanzhi.get("count");
		} 2020-08-14 因发现与查询数据sql不同修随改为如下*/
		Map empStatusquanzhi = etemployeeMapper.listPersonnelProfileEmpStatus5(etemployee);
		if(empStatusquanzhi!=null){
			quanzhicount = (Long) empStatusquanzhi.get("count");
		}

		//非全职
		Map empStatusfeiquanzhi = etemployeeMapper.listPersonnelProfileEmpStatus6(etemployee);
		if(empStatusfeiquanzhi!=null){
			feiquanzhicount = (Long) empStatusfeiquanzhi.get("count");
		}
		//正式
		Map empStatuszhengshi = etemployeeMapper.listPersonnelProfileEmpStatus1(etemployee);
		if(empStatuszhengshi!=null){
			zhengshicount = (Long) empStatuszhengshi.get("count");
		}
		//试用
		Map empStatusshiyong = etemployeeMapper.listPersonnelProfileEmpStatus2(etemployee);
		if(empStatusshiyong!=null){
			shiyongcount = (Long) empStatusshiyong.get("count");
		}
		//实习
		Map empStatusshixi = etemployeeMapper.listPersonnelProfileEmpStatus4(etemployee);
		if(empStatusshixi!=null){
			shixicount = (Long) empStatusshixi.get("count");
		}
		//待离职
		Map empStatusDaiLiZhi = etemployeeMapper.listEtleaveRegisterCount(etemployee);
		if(empStatusDaiLiZhi!=null){
			dailizhicount = (Long) empStatusDaiLiZhi.get("count");
		}
		//已离职
		Map empStatusyiLiZhi = etemployeeMapper.listEtleaveAllCount(etemployee);
		if(empStatusyiLiZhi!=null){
			yilizhicount = (Long) empStatusyiLiZhi.get("count");
		}


		Map resultMap1 = new HashMap();
		Map resultMap2 = new HashMap();
		Map resultMap3 = new HashMap();
		Map resultMap4 = new HashMap();
		Map resultMap5 = new HashMap();
		Map resultMap6 = new HashMap();
		Map resultMap7 = new HashMap();
		Map resultMap8 = new HashMap();
		resultMap1.put("empstatus","在职");
		resultMap1.put("people",zaizhicount);
		resultMap2.put("empstatus","全职");
		resultMap2.put("people",quanzhicount);
		resultMap3.put("empstatus","非全职");
		resultMap3.put("people",feiquanzhicount);
		resultMap4.put("empstatus","正式");
		resultMap4.put("people",zhengshicount);
		resultMap5.put("empstatus","试用");
		resultMap5.put("people",shiyongcount);
		resultMap6.put("empstatus","实习");
		resultMap6.put("people",shixicount);
		resultMap7.put("empstatus","待离职");
		resultMap7.put("people",dailizhicount);
		resultMap8.put("empstatus","已离职");
		resultMap8.put("people",yilizhicount);
		List resultList = new ArrayList(8);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		resultList.add(2,resultMap3);
		resultList.add(3,resultMap4);
		resultList.add(4,resultMap5);
		resultList.add(5,resultMap6);
		resultList.add(6,resultMap7);
		resultList.add(7,resultMap8);
		return R.ok(resultList);
	}
	/**
	 * 员工花名册查询员工详情-PC
	 * @return
	 */
	@ApiOperation(value = "员工花名册查询员工详情(个人信息、在职信息)-PC", notes = "员工花名册查询员工详情(个人信息、在职信息)-PC")
	@PostMapping("/getEtEmployeeHMCDetailByEidSql")
	public R getEtEmployeeHMCDetailByEidSql(@RequestBody Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEtEmployeeHMCDetailByEidSql(etemployee));
	}

	/**
	 * 通过adminid获取岗位列表
	 * @return
	 */
	@ApiOperation(value = "通过jobid获取员工列表", notes = "通过jobid获取员工列表")
	@PostMapping("/getEtEmployeeListByjobId" )
	public R getEtEmployeeListByjobId(Page page,Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		IPage<Map>  resultList  = etemployeeMapper.listEtEmployeeHMCBySqlForPc(page,etemployee);
		return R.ok(resultList);
	}
	/**
	 * 员工转正分页查询
	 * @param page 分页对象
	 * @param etemployee 入职登记
	 * @return
	 */
	@ApiOperation(value = "员工转正分页查询", notes = "员工转正分页查询")
	@PostMapping("/zhuanzhengetstaffRegisterPage" )
	public R zhuanzhengetstaffRegister(Page page, @RequestBody Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		return R.ok(etemployeeMapper.listEtemployeeWaitingForCorrection(page, etemployee));
	}

	/**
	 * 确认转正
	 * @return R
	 */
	@ApiOperation(value = "确认转正", notes = "确认转正")
	@PostMapping("/makesureEtemployee" )
	public R makesureEtemployee(@RequestBody Etemployee etemployee ){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		String currentTime = DateUtils.getTimeString();
		Etemployee etemployee1 = etemployeeService.getById(etemployee.getEid());

		EtstaffAll  etstaffAll = new EtstaffAll();
		BeanUtils.copyProperties(etemployee1,etstaffAll);
		etstaffAll.setRegby(pigxUser.getId());
		etstaffAll.setRegdate(currentTime);
		etstaffAllService.save(etstaffAll);
		UpdateWrapper<Etemployee> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("eid",etemployee.getEid());

		etemployeeService.update(etemployee,updateWrapper);

		return R.ok("数据已修改！");
	}

	/**
	 **薪资开启选人确定
	 * @return
	 */
	@ApiOperation(value = "考勤开启选人", notes = "考勤开启选人")
	@PostMapping("/getCtemployeeForKaiQi" )
	@Transactional(rollbackFor = Exception.class)
	public R getCtemployeeForKaiQi(Page page, @RequestBody(required = false) Ctemployee ctemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		Integer eid = null;
		String eids = ctemployee.getEids();
		if(org.springframework.util.StringUtils.isEmpty(eids)){
			R.failed("员工EID不能为空！");
		}
		String[] ss = eids.split(",");
		for(int i=0;i<ss.length;i++){
			eid = Integer.parseInt(ss[i]);
			Etemployee etemployee = etemployeeService.getById(eid);
			AtstaffAll atstaffAll = new AtstaffAll();
			atstaffAll.setEid(eid);
			AtstaffRegister atstaffRegister = new AtstaffRegister();
			atstaffRegister.setEid(eid);
			List atstaffList = atstaffAllService.list(Wrappers.query(atstaffAll));
			if(atstaffList.size()>0){
				return  R.failed("此员工已开启过考勤！");
			}
			List atstaffregisterList = atstaffRegisterService.list(Wrappers.query(atstaffRegister));
			if(atstaffregisterList.size()>0){
				return  R.failed("此员工的登记信息已存在！");
			}
			AtstaffRegister atstaffRegister2 = new AtstaffRegister();
			BeanUtils.copyProperties(etemployee,atstaffRegister2);
			//atstaffRegister2.setCorpid(corpid);
			//atstaffRegister2.setCorpcode(corpcode);
			atstaffRegisterService.save(atstaffRegister2);
		}


		return R.ok("操作成功！");
	}
	/**
	 **薪资开启选人确定
	 * @return
	 */
	@ApiOperation(value = "薪资开启选人", notes = "薪资开启选人")
	@PostMapping("/getCtemployeeForXinzi" )
	@Transactional(rollbackFor = Exception.class)
	public R getCtemployeeForXinzi(Page page, @RequestBody(required = false) Ctemployee ctemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		Integer eid = null;
		String eids = ctemployee.getEids();
		if(org.springframework.util.StringUtils.isEmpty(eids)){
			R.failed("员工EID不能为空！");
		}
		String[] ss = eids.split(",");
		for(int i=0;i<ss.length;i++){
			eid = Integer.parseInt(ss[i]);
			Etemployee etemployee = etemployeeService.getById(eid);
			CtsalaryAll ctsalaryAll = new CtsalaryAll();
			ctsalaryAll.setEid(eid);
			CtsalaryRegister ctsalaryRegister = new CtsalaryRegister();
			ctsalaryRegister.setEid(eid);
			List ctstaffList = ctsalaryAllService.list(Wrappers.query(ctsalaryAll));
			if(ctstaffList.size()>0){
				return  R.failed("此员工已开启过薪资！");
			}
			List ctstaffregisterList = ctsalaryRegisterService.list(Wrappers.query(ctsalaryRegister));
			if(ctstaffregisterList.size()>0){
				return  R.failed("此员工的登记信息已存在！");
			}
			CtsalaryRegister ctsalaryRegister2 = new CtsalaryRegister();
			BeanUtils.copyProperties(etemployee,ctsalaryRegister2);
			//atstaffRegister2.setCorpid(corpid);
			//atstaffRegister2.setCorpcode(corpcode);
			ctsalaryRegisterService.save(ctsalaryRegister2);
		}


		return R.ok("操作成功！");
	}

	/**
	 **薪资开启选人确定
	 * @return
	 */
	@ApiOperation(value = "社保开启选人", notes = "社保开启选人")
	@PostMapping("/getCtBenstatusForXinzi" )
	@Transactional(rollbackFor = Exception.class)
	public R getCtBenstatusForXinzi(Page page, @RequestBody(required = false) CvwBenstatus cvwBenstatus) {
		String eids = cvwBenstatus.getEids();
		if(org.springframework.util.StringUtils.isEmpty(eids)){
			R.failed("员工EID不能为空！");
		}
		Integer eid = null;
		String[] ss = eids.split(",");
		for(int i=0;i<ss.length;i++){
			eid = Integer.parseInt(ss[i]);
			Etemployee etemployee = etemployeeService.getById(eid);
			//ctbenefitstatus
			//ctbenefitstatus_all  ctBenefit_All
			CtbenefitAll ctbenefitAll = new CtbenefitAll();
			ctbenefitAll.setEid(eid);
			CtbenefitRegister ctbenefitRegister = new CtbenefitRegister();
			ctbenefitRegister.setEid(eid);
			List ctbenefitallList = ctbenefitAllService.list(Wrappers.query(ctbenefitAll));
			if(ctbenefitallList.size()>0){
				return  R.failed("此员工已开启过社保！");
			}
			List ctbenefitRegisterList = ctbenefitRegisterService.list(Wrappers.query(ctbenefitRegister));
			if(ctbenefitRegisterList.size()>0){
				return  R.failed("此员工的登记信息已存在！");
			}
			CtbenefitRegister ctbenefitRegister1 = new CtbenefitRegister();
			BeanUtils.copyProperties(etemployee,ctbenefitRegister1);
			ctbenefitRegisterService.save(ctbenefitRegister1);
		}


		return R.ok("操作成功！");
	}

}
