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
import com.pig4cloud.pigx.admin.api.dto.DeptTreeOrg;
import com.pig4cloud.pigx.admin.api.dto.JobTreeOrg;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.mapper.OtjobMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 岗位信息
 *
 * @author gaoxiao
 * @date 2020-04-07 16:29:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otjob" )
@Api(value = "otjob", tags = "岗位信息管理")
public class OtjobController {

    private final  OtjobService otjobService;
	private final OtjobchangeAllService otjobchangeAllService;
	private final OtjobMapper otjobMapper;
	private final EtstaffAllService etstaffAllService;
	private final EtleaveRegisterService etleaveRegisterService;
	private final EtstaffRegisterService etstaffRegisterService;
	private final EtemployeeMapper etemployeeMapper;
	private final  EtemployeeService etemployeeService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param otjob 岗位信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtjobPage(Page page, Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(otjob)){
			otjob = new Otjob();
		}
		otjob.setCorpcode(corpcode);
    	return R.ok(otjobService.page(page, Wrappers.query(otjob)));
    }
	/**
	 * 查询所有
	 * @param otjob 岗位信息
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllOtjob" )
	public R getAllOtjob( Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Otjob> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String title = otjob.getTitle();
		Integer depid = otjob.getDepid();
		Integer adminid = otjob.getAdminid();
		Integer isdisabled = otjob.getIsdisabled();
		if(title!=null && title!=""){
			queryWrapper.like("title",otjob.getTitle());
		}
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(adminid)){
			queryWrapper.eq("adminid",adminid);
		}
		if(!StringUtils.isEmpty(isdisabled)){
			queryWrapper.eq("isdisabled",isdisabled);
		}
		return R.ok(otjobService.list(queryWrapper));
	}

    /**
     * 通过id查询岗位信息
     * @param jobid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{jobid}" )
    public R getById(@PathVariable("jobid" ) Integer jobid) {

    	return R.ok(otjobService.getById(jobid));
    }

    /**
     * 新增岗位信息
     * @param otjob 岗位信息
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otjob_add')" )
     */
    @ApiOperation(value = "新增岗位信息", notes = "新增岗位信息")
    @SysLog("新增岗位信息" )
    @PostMapping("/save")
    public R save(@RequestBody Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		otjob.setCompid(pigxUser.getCompid());
		otjob.setCorpcode(pigxUser.getCorpcode());
		otjob.setCorpid(pigxUser.getCorpid());
		otjob.setIsdisabled(0);
		//TYPE=1 新增  1:岗位代码已存在
		String jobcode = otjob.getJobcode();
		if(!StringUtils.isEmpty(jobcode)){
			List JobList = otjobService.getOtjobByExistsJobID(otjob);
			if(JobList.size()>0){
				return R.failed(null,"岗位代码已存在，请重新填写");
			}
		}


		//TYPE=1 新增  2.上级岗位必须存在且有效，或者生效日期必须晚于上级岗位的创建日期！
		if(otjob.getAdminid()!=null){
			List AdminJobList = otjobService.getOtjobByExistsEnableJobID(otjob);
			if(!(AdminJobList.size()>0)){
				return R.failed(null,"上级岗位必须存在且有效，或者生效日期必须晚于上级岗位的创建日期!，请重新填写");
			}
		}

		//TYPE=1 新增  3.所属公司必须存在且有效,生效日期晚于所属公司的创建日期！
		if(otjob.getCompid()!=null){
			List EnableCompList = otjobService.getOtjobByExistsEnableCompID(otjob);
			if(!( EnableCompList.size()>0)){
				return R.failed(null,"所属公司必须存在且有效,生效日期晚于所属公司的创建日期！，请重新填写");
			}
		}

		//TYPE=1 新增  4.所属部门必须存在且有效,生效日期晚于所属部门的创建日期！
		List EnableDepList = otjobService.getOtjobByExistsEnableDepID(otjob);
		if(!(EnableDepList.size()>0)){
			return R.failed(null,"所属部门必须存在且有效,生效日期晚于所属部门的创建日期！，请重新填写");
		}
		//TYPE=1 新增  5.上级岗位必须是所属公司下面的岗位！
		if(otjob.getAdminid()!=null){
			List AdminJobList2 = otjobService.getOtJobmentByAdminJobID(otjob);
			if(!(AdminJobList2.size()>0)){
				return R.failed(null,"上级岗位必须是所属公司下面的岗位！，请重新填写");
			}
		}

		//TYPE=1 新增  6.岗位所属部门必须是所属公司下面的部门!
		List DepartJobList = otjobService.getOtJobByDepartJobID(otjob);
		if(!(DepartJobList.size()>0)){
			return R.failed(null,"岗位所属部门必须是所属公司下面的部门!，请重新填写");
		}

		//查询历史并赋值
//		Otjob oldOtjob = otjobService.getById(otjob.getJobid());
		OtjobchangeAll otjobchangeAll = new OtjobchangeAll();
		BeanUtils.copyProperties(otjob,otjobchangeAll);
		///变动类型 1：新增，2：修改，3：删除，4：合并
		otjobchangeAll.setType(1);
		otjobchangeAll.setRegby(pigxUser.getId());
		otjobchangeAll.setRegdate(DateUtils.getTimeString());
//		otjobchangeAll.setTitleOld(oldOtjob.getTitle());
//		otjobchangeAll.setDepidOld(oldOtjob.getDepid());
//		otjobchangeAll.setJobtypeOld(oldOtjob.getJobtype());
//		otjobchangeAll.setAdminidOld(oldOtjob.getAdminid());
//		职等（原） 未找到相应字段
//		职级（原） 未找到相应字段
//		是否虚拟（原） 未找到相应字段
//		岗位职责（原） 未找到相应字段
//		任职要求（原） 未找到相应字段
//		otjobchangeAll.setCompidOld(oldOtjob.getCompid());
//		otjobchangeAll.setFunctionidOld(oldOtjob.getFunctionid());
//		otjobchangeAll.setJobabbrOld(oldOtjob.getJobabbr());
//		otjobchangeAll.setJobcodeOld(oldOtjob.getJobcode());
//		otjobchangeAll.setJobgradeOld(oldOtjob.getJobgrade());
//		otjobchangeAll.setJobnumOld(oldOtjob.getJobnum());
//		otjobchangeAll.setJobpropertyOld(oldOtjob.getJobproperty());
		otjobchangeAllService.save(otjobchangeAll);

		otjobService.save(otjob);
    	return R.ok("保存成功！");
    }

/*    *//**
     * 修改岗位信息
     * @param otjob 岗位信息
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otjob_edit')" )
     */
    @ApiOperation(value = "修改岗位信息", notes = "修改岗位信息")
    @SysLog("修改岗位信息" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Otjob otjob1 = otjobMapper.selectById(otjob.getJobid());
		otjob.setCompid(otjob1.getCompid());
		//TYPE=2 修改  1:岗位代码已存在
		List JobList2 = otjobService.getOtjobByExistsJobID(otjob);
		if(JobList2.size()>0){
			return R.failed(null,"岗位代码已存在!，请重新填写");
		}
		//TYPE=2 修改  2:失效的岗位不能做变更
		List DisabledList = otjobService.getOtJobByIsDisabled(otjob);
		if(DisabledList.size()>0){
			return R.failed(null,"失效的岗位不能做变更!，请重新填写");
		}
		//TYPE=2 修改  3:信息没有发生变化
		List NoChangeList = otjobService.getOtJobByNoChange(otjob);
		if(NoChangeList.size()>0){
			return R.failed(null,"信息没有发生变化!，请重新填写");
		}
		//TYPE=2 修改  4:所属公司必须存在且有效,生效日期晚于所属公司的创建日期！
		List ExistsEnableCompList = otjobService.getOtjobByExistsEnableCompID(otjob);
		if(!(ExistsEnableCompList.size()>0)){
			return R.failed(null,"所属公司必须存在且有效,生效日期晚于所属公司的创建日期!，请重新填写");
		}
		//TYPE=2 修改  5:所属部门必须存在且有效,生效日期晚于所属部门的创建日期！
		List ExistsEnableDepList = otjobService.getOtjobByExistsEnableDepID(otjob);
		if(!(ExistsEnableDepList.size()>0)){
			return R.failed(null,"所属部门必须存在且有效,生效日期晚于所属部门的创建日期!，请重新填写");
		}
		//TYPE=2 修改  6:上级岗位必须是所属公司下面的岗位！
		if(!StringUtils.isEmpty(otjob.getAdminid())){
			List AdminJobList1 = otjobService.getOtJobmentByAdminJobID(otjob);
			if(!(AdminJobList1.size()>0)){
				return R.failed(null,"上级岗位必须是所属公司下面的岗位!，请重新填写");
			}
		}
		//TYPE=2 修改  7:岗位所属部门必须是所属公司下面的部门！
		List DepartJobList1 = otjobService.getOtJobByDepartJobID(otjob);
		if(!(DepartJobList1.size()>0)){
			return R.failed(null,"岗位所属部门必须是所属公司下面的部门!，请重新填写");
		}

		//查询历史并赋值
		Otjob oldOtjob = otjobService.getById(otjob.getJobid());
		OtjobchangeAll otjobchangeAll = new OtjobchangeAll();
		BeanUtils.copyProperties(otjob,otjobchangeAll);
		///变动类型 1：新增，2：修改，3：删除，4：合并
		otjobchangeAll.setType(2);
		otjobchangeAll.setRegby(pigxUser.getId());
		otjobchangeAll.setRegdate(DateUtils.getTimeString());
		otjobchangeAll.setTitleOld(oldOtjob.getTitle());
		otjobchangeAll.setDepidOld(oldOtjob.getDepid());
		otjobchangeAll.setJobtypeOld(oldOtjob.getJobtype());
		otjobchangeAll.setAdminidOld(oldOtjob.getAdminid());
		//职等（原） 未找到相应字段
		//职级（原） 未找到相应字段
		//是否虚拟（原） 未找到相应字段
		//岗位职责（原） 未找到相应字段
		//任职要求（原） 未找到相应字段
		otjobchangeAll.setCompidOld(oldOtjob.getCompid());
		otjobchangeAll.setFunctionidOld(oldOtjob.getFunctionid());
		otjobchangeAll.setJobabbrOld(oldOtjob.getJobabbr());
		otjobchangeAll.setJobcodeOld(oldOtjob.getJobcode());
		otjobchangeAll.setJobgradeOld(oldOtjob.getJobgrade());
		otjobchangeAll.setJobnumOld(oldOtjob.getJobnum());
		otjobchangeAll.setJobpropertyOld(oldOtjob.getJobproperty());
		otjobchangeAllService.save(otjobchangeAll);

    	UpdateWrapper<Otjob> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",pigxUser.getCorpcode());
		updateWrapper.eq("jobid",otjob.getJobid());
        return R.ok(otjobService.update(otjob,updateWrapper));
    }

    /**
     * 通过id删除岗位信息
     * @param jobid id
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otjob_del')" )
     *//*
    @ApiOperation(value = "通过id删除岗位信息", notes = "通过id删除岗位信息")
    @SysLog("通过id删除岗位信息" )
    @DeleteMapping("/{jobid}" )
    public R removeById(@PathVariable Integer jobid) {
        return R.ok(otjobService.removeById(jobid));
    }
	*/
    /**
	 * 修改岗位
	 * @return R
	 */
	@ApiOperation(value = "修改岗位", notes = "修改岗位")
	@PostMapping("/updateOtjob" )
	public R updateOtjob(@RequestBody  Otjob otjob){

		//TYPE=2 修改  1:岗位代码已存在
		List JobList2 = otjobService.getOtjobByExistsJobID(otjob);
		if(JobList2.size()>=0){
			return R.failed(null,"岗位代码已存在!，请重新填写");
		}
		//TYPE=2 修改  2:失效的岗位不能做变更
		List DisabledList = otjobService.getOtJobByIsDisabled(otjob);
		if(DisabledList.size()>=0){
			return R.failed(null,"失效的岗位不能做变更!，请重新填写");
		}
		//TYPE=2 修改  3:信息没有发生变化
		List NoChangeList = otjobService.getOtJobByNoChange(otjob);
		if(NoChangeList.size()>=0){
			return R.failed(null,"信息没有发生变化!，请重新填写");
		}
		//TYPE=2 修改  4:所属公司必须存在且有效,生效日期晚于所属公司的创建日期！
		List ExistsEnableCompList = otjobService.getOtjobByExistsEnableCompID(otjob);
		if(!(ExistsEnableCompList.size()>=0)){
			return R.failed(null,"所属公司必须存在且有效,生效日期晚于所属公司的创建日期!，请重新填写");
		}
		//TYPE=2 修改  5:所属部门必须存在且有效,生效日期晚于所属部门的创建日期！
		List ExistsEnableDepList = otjobService.getOtjobByExistsEnableDepID(otjob);
		if(!(ExistsEnableDepList.size()>=0)){
			return R.failed(null,"所属部门必须存在且有效,生效日期晚于所属部门的创建日期!，请重新填写");
		}
		//TYPE=2 修改  6:上级岗位必须是所属公司下面的岗位！
		List AdminJobList1 = otjobService.getOtJobmentByAdminJobID(otjob);
		if(!(AdminJobList1.size()>=0)){
			return R.failed(null,"上级岗位必须是所属公司下面的岗位!，请重新填写");
		}
		//TYPE=2 修改  7:岗位所属部门必须是所属公司下面的部门！
		List DepartJobList1 = otjobService.getOtJobByDepartJobID(otjob);
		if(!(DepartJobList1.size()>=0)){
			return R.failed(null,"岗位所属部门必须是所属公司下面的部门!，请重新填写");
		}

		return R.ok("数据已修改！");
	}
	/**
	 * 根据条件判断部门是否存在
	 * @return R
	 */
	@ApiOperation(value = "失效岗位", notes = "失效岗位")
	@PostMapping("/invalidById" )
	@Transactional
	public R invalidById(@RequestBody Otjob otjob){
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer jobid = otjob.getJobid();
		//TYPE=3 失效  1:岗位失效时请先处理其下属岗位！
		List DueJobList = otjobService.getOtJobByDueJobID(otjob);
		if(DueJobList.size()>0){
			return R.ok(null,"岗位失效时请先处理其下属岗位!，请重新填写");
		}
		//TYPE=3 失效  2:岗位失效时请先处理岗位上在职员工！
		List DueEmployeeList = otjobService.getOtjobByDueEmployee(otjob);
		if(DueEmployeeList.size()>0){
			return R.failed(null,"岗位失效时请先处理其下属岗位!，请重新填写");
		}
		//TYPE=3 失效  3:岗位失效时请先处理兼职对信息！
		/*
		List DisableJobList = otjobService.getOtjobByDisableJobID(otjob);
		if(DisableJobList.size()>0){
			return R.ok(null,"岗位失效时请先处理兼职对信息!，请重新填写");
		}*/
		//查询历史并赋值
		Otjob odt  = otjobService.getById(jobid);
		OtjobchangeAll otjobchangeAll = new OtjobchangeAll();
		BeanUtils.copyProperties(odt,otjobchangeAll);
		otjobchangeAll.setAdminidOld(odt.getAdminid());
		otjobchangeAll.setCompidOld(odt.getCompid());
		otjobchangeAll.setDepidOld(odt.getDepid());
		otjobchangeAll.setFunctionidOld(odt.getFunctionid());
		otjobchangeAll.setJobabbrOld(odt.getJobabbr());
		otjobchangeAll.setJobcodeOld(odt.getJobcode());
		otjobchangeAll.setJobgradeOld(odt.getJobgrade());
		otjobchangeAll.setJobnumOld(odt.getJobnum());
		otjobchangeAll.setJobtypeOld(odt.getJobtype());
		otjobchangeAll.setJobpropertyOld(odt.getJobproperty());
		otjobchangeAll.setTitleOld(odt.getTitle());
		Otjob o = new Otjob();
		o.setIsdisabled(1);
		QueryWrapper<Otjob> objobUpdateWrapper = new QueryWrapper<>();
		objobUpdateWrapper.eq("corpcode",pigxUser.getCorpcode());
		objobUpdateWrapper.eq("jobid",jobid);
		otjobchangeAllService.save(otjobchangeAll);
		otjobService.remove(objobUpdateWrapper);
		return R.ok("数据已修改！");
	}
	/**
	 * 通过corpcode获取岗位信息
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "获取岗位信息", notes = "获取岗位信息")
	@SysLog("获取岗位信息" )
	@PutMapping("/getOtjobs")
	public R getOtjobs() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Otjob otjob = new Otjob();
		otjob.setCorpcode(corpcode);
		List jobList = otjobService.getOtjobsByCorpcodeWithPeople(otjob);
		return R.ok(jobList);
	}
	/**
	 * PC端部门管理
	 * @return
	 */
	@ApiOperation(value = "PC端岗位管理", notes = "PC端岗位管理")
	@PostMapping("/getJobTreePC" )
	public R getJobTreePC(Page page,@RequestBody Otjob otjob) {

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otjob.setCorpcode(corpcode);
		IPage<JobTreeOrg> jobIPage= otjobMapper.listJobTreePC(page,otjob);
		return R.ok(jobIPage);
	}
	/**
	 * PC端部门管理
	 * @return
	 */
	@ApiOperation(value = "PC端岗位管理-二级及以下菜单", notes = "PC端岗位管理-二级及以下菜单")
	@PostMapping("/getJobTreePCSecond" )
	public R getJobTreePCSecond(Page page,@RequestBody(required = false) Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(otjob)){
			otjob = new Otjob();
		}
		otjob.setCorpcode(corpcode);
		IPage<JobTreeOrg> jobIPage= otjobMapper.listJobTreePCSecond(page,otjob);
		return R.ok(jobIPage);
	}

	/**
	 *
	 * pc端部门管理-详情接口1
	 * @return
	 */
	@ApiOperation(value = "pc端岗位管理-详情接口1", notes = "pc端岗位管理-详情接口1")
	@PostMapping("/getOtjobDetailByjobId" )
	public R getOtjobDetailByjobId(@RequestBody Otjob otjob) {
		Map  map = otjobMapper.listOtjobDetailByjobId(otjob);
		return R.ok(map);
	}
	/**
	 *
	 * 根据jobID获取岗位详情
	 * @return
	 */
	@ApiOperation(value = "根据jobID获取岗位详情", notes = "根据jobID获取岗位详情")
	@PostMapping("/getOtjobDetailByjobId2" )
	public R getOtjobDetailByjobId2(@RequestBody Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otjob.setCorpcode(corpcode);
		Map  map = otjobMapper.listOtjobDetailByjobId2(otjob);
		return R.ok(map);
	}
	/**
	 *
	 * pc端部门管理-详情接口3
	 * @return
	 */
	@ApiOperation(value = "pc端岗位管理-详情接口3", notes = "pc端岗位管理-详情接口3")
	@PostMapping("/getOtjobDetailByjobId3" )
	public R getOtjobDetailByjobId3(@RequestBody Otjob otjob) {
		List<Map>  maplist = otjobMapper.listOtjobDetailByjobId3(otjob);
		return R.ok(maplist);
	}

	/**
	 * 合并岗位
	 * @return R
	 */
	@ApiOperation(value = "合并岗位", notes = "合并岗位")
	@PostMapping("/combineOtJob" )
	@Transactional
	public R combineOtJob(@RequestBody Map map){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		map.put("corpcode",corpcode);
		Integer jobid =Integer.parseInt(map.get("jobid").toString());
		Integer newjobid = Integer.parseInt(map.get("newjobid").toString());
		if(jobid==newjobid){
			return R.failed(null,"新岗位不能是自己");
		}

		//判断离职的
		//判断是否有入职
		//判断变动的
		//判断上级领导
		//etleaveRegisterService.get
		//etstaffRegisterService;
		EtleaveRegister etleaveRegister = new EtleaveRegister();
		etleaveRegister.setJobid(jobid);
		EtstaffRegister etstaffRegister = new EtstaffRegister();
		etstaffRegister.setDepid(jobid);
		List result = etleaveRegisterService.list(Wrappers.query(etleaveRegister));
		if(result!=null && result.size()>0){
			return R.failed("原部门存在没有处理的离职信息，请确认后再操作！");
		}
		List result2 = etstaffRegisterService.list(Wrappers.query(etstaffRegister));
		if(result!=null && result.size()>0){
			return R.failed("原部门存在没有处理的入职或者调动记录，请确认后再操作！");
		}
		//查看原部门下是否存在子部门
		QueryWrapper<Otjob> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("adminid",jobid);
		List jobtypelist2 = otjobService.list(queryWrapper);
			if(jobtypelist2.size()>0){
			return R.failed("此岗位下存在下级岗位，请先处理下级岗位！");
		}
		//新岗位信息
		Otjob newotjob = otjobService.getById(newjobid);
			//原部门
		Otjob oldotjob = otjobService.getById(jobid);
		//失效原部门信息
		Otdepartment otdepartment = new Otdepartment();
		Otjob otjob = new Otjob();
		otjob.setJobid(jobid);
		otjob.setIsdisabled(1);
		otjobService.updateById(otjob);

		//处理岗位信息
		UpdateWrapper<Otjob> otjobUpdateWrapper = new UpdateWrapper<>();
		otjobUpdateWrapper.eq("corpcode",pigxUser.getCorpcode());
		otjobUpdateWrapper.eq("jobid",jobid);
		Otjob otjob2 = new Otjob();
		otjob2.setCompid(newotjob.getCompid());
		otjob2.setDepid(newotjob.getDepid());
		otjobService.update(otjob2,otjobUpdateWrapper);

		//修改原部门员工信息
		Etemployee etemployee = new Etemployee();
		etemployee.setJobid(jobid);
		Map depmap = new HashMap();
		depmap.put("newcompid",newotjob.getCompid());
		depmap.put("newdepid",newotjob.getDepid());
		depmap.put("newjobid",newotjob.getJobid());
		depmap.put("corpcode",corpcode);
		etemployeeMapper.updateEmployeeForJobCombine(depmap);

		//2、插入岗位历史
//		otjobService.save(oldotjob);


		//3、插入人员历史
		Etemployee etemployee2 = new Etemployee();
		etemployee2.setJobid(jobid);
		List employeelist  = etemployeeService.list(Wrappers.query(etemployee2));
		Etemployee e = null;
		EtstaffAll ets = null;
		List etstallList = new ArrayList(employeelist.size());
		for(int i=0;i<employeelist.size();i++){
			e = (Etemployee)employeelist.get(i);
			ets = new EtstaffAll();
			BeanUtils.copyProperties(e,ets);
			ets.setInitializedby(pigxUser.getId());
			ets.setInitializedtime(DateUtils.getTimeString());
			etstallList.add(ets);
		}

		//查询历史并赋值
		OtjobchangeAll otjobchangeAll = new OtjobchangeAll();
		BeanUtils.copyProperties(newotjob,otjobchangeAll);
		///变动类型 1：新增，2：修改，3：删除，4：合并
		otjobchangeAll.setType(4);
		otjobchangeAll.setRegby(pigxUser.getId());
		otjobchangeAll.setRegdate(DateUtils.getTimeString());
		otjobchangeAll.setTitleOld(oldotjob.getTitle());
		otjobchangeAll.setDepidOld(oldotjob.getDepid());
		otjobchangeAll.setJobtypeOld(oldotjob.getJobtype());
		otjobchangeAll.setAdminidOld(oldotjob.getAdminid());
		//职等（原） 未找到相应字段
		//职级（原） 未找到相应字段
		//是否虚拟（原） 未找到相应字段
		//岗位职责（原） 未找到相应字段
		//任职要求（原） 未找到相应字段
		otjobchangeAll.setCompidOld(oldotjob.getCompid());
		otjobchangeAll.setFunctionidOld(oldotjob.getFunctionid());
		otjobchangeAll.setJobabbrOld(oldotjob.getJobabbr());
		otjobchangeAll.setJobcodeOld(oldotjob.getJobcode());
		otjobchangeAll.setJobgradeOld(oldotjob.getJobgrade());
		otjobchangeAll.setJobnumOld(oldotjob.getJobnum());
		otjobchangeAll.setJobpropertyOld(oldotjob.getJobproperty());


		otjobchangeAllService.save(otjobchangeAll);

		etstaffAllService.saveBatch(etstallList);
		otjobService.removeById(oldotjob.getJobid());
		return R.ok("数据已修改！");
	}

	/**
	 * 分页查询只返回list
	 * @param page 分页对象
	 * @param name 员工姓名
	 * @return
	 */
	@ApiOperation(value = "分页查询只返回list", notes = "分页查询只返回list")
	@GetMapping(value={"/getJobPageList/{name}","/getJobPageList"} )
	public R getJobPageList(Page page, @PathVariable(required = false) String name) {

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Otjob> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		if(!StringUtils.isEmpty(name)){
			queryWrapper.like("title",name);
		}
		queryWrapper.eq("isdisabled",0);
		IPage resultpage = otjobService.page(page,queryWrapper);
		List resultList = resultpage.getRecords();
		return R.ok(resultList,"", CommonConstants.SUCCESS200);
	}

	/**
	 * 通过jobid获取岗位详情
	 * @return
	 */
	@ApiOperation(value = "通过jobid获取岗位详情", notes = "通过jobid获取岗位详情")
	@PostMapping("/getJobDeitalByJobid" )
	public R getJobDeitalByJobid(@RequestBody Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otjob.setCorpcode(corpcode);
		Map resultMap  = otjobMapper.listOtjobDetailByjobId2(otjob);
		return R.ok(resultMap);
	}

	/**
	 * 通过depid获取岗位列表
	 * @return
	 */
	@ApiOperation(value = "通过depid获取岗位列表", notes = "通过depid获取岗位列表")
	@PostMapping("/getJobListBydepid" )
	public R getJobListBydepid(Page page, Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otjob.setCorpcode(corpcode);
		IPage<Map> resultList  = otjobMapper.listOtjobListBydepId2(page,otjob);
		return R.ok(resultList);
	}

	/**
	 * 通过adminid获取岗位列表
	 * @return
	 */
	@ApiOperation(value = "通过adminid获取岗位列表", notes = "通过adminid获取岗位列表")
	@PostMapping("/getOtjobListByadminId" )
	public R getOtjobListByadminId(Page page, Otjob otjob) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otjob.setCorpcode(corpcode);
		IPage<Map> resultList  = otjobMapper.listOtjobListByadminId(page,otjob);
		return R.ok(resultList);
	}

}
