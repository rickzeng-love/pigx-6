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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.mapper.OtdepartmentMapper;
import com.pig4cloud.pigx.admin.mapper.OtjobMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 部门信息
 *
 * @author gaoxiao
 * @date 2020-03-27 16:22:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otdepartment" )
@Api(value = "otdepartment", tags = "部门信息管理")
public class OtdepartmentController {
	private final SysQywxApplicationService sysQywxApplicationService;

    private final  OtdepartmentService otdepartmentService;
	private final OtdepchangeAllService otdepchangeAllService;
	private final EtemployeeService etemployeeService;
	private final OtdepartmentMapper otdepartmentMapper;
	private final EtemployeeMapper etemployeeMapper;
	private final EtleaveRegisterService etleaveRegisterService;
	private final EtstaffRegisterService etstaffRegisterService;
	private final OtjobMapper otjobMapper;
	private final OtjobService otjobService;
	private  final  OtjobchangeAllService otjobchangeAllService;
	private  final  EtstaffAllService etstaffAllService;
	private final  SystcorpinfoService systcorpinfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otdepartment 部门信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtdepartmentPage(Page page, Otdepartment otdepartment) {
        return R.ok(otdepartmentService.page(page, Wrappers.query(otdepartment)));
    }

	/**
	 * 查询所有
	 * @param otdepartment 入职登记表
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllOtdepartment" )
	public R getAllOtdepartment( Otdepartment otdepartment) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Otdepartment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String title = otdepartment.getTitle();
		Integer adminid = otdepartment.getAdminid();
		Integer isdisabled = otdepartment.getIsdisabled();
		if(title!=null && title!=""){
			queryWrapper.like("title",otdepartment.getTitle());
		}
		if(!StringUtils.isEmpty(adminid)){
			queryWrapper.eq("adminid",adminid);
		}
		if(!StringUtils.isEmpty(isdisabled)){
			queryWrapper.eq("isdisabled",isdisabled);
		}
		return R.ok(otdepartmentService.list(queryWrapper));
	}
    /**
     * 通过id查询部门信息
     * @param depid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{depid}" )
    public R getById(@PathVariable("depid" ) Integer depid) {
        return R.ok(otdepartmentService.getById(depid));
    }

    /**
     * 新增部门信息
	 *  @PreAuthorize("@pms.hasPermission('admin_otdepartment_add')" )
     * @param otdepartment 部门信息
     * @return R
     */
    @ApiOperation(value = "新增部门信息", notes = "新增部门信息")
    @SysLog("新增部门信息")
    @PostMapping("/save")
    public R save(@RequestBody Otdepartment otdepartment) {

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		otdepartment.setCorpcode(corpcode);
		otdepartment.setCorpid(pigxUser.getCorpid());
		otdepartment.setCompid(compid);
		otdepartment.setRegby(pigxUser.getId());
		otdepartment.setRegbyname(pigxUser.getUsername());
		otdepartment.setRegdate(DateUtils.getTimeString());

		//校验名称是否重复
		QueryWrapper<Otdepartment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otdepartment.getTitle());
		List list = otdepartmentService.list(queryWrapper);
		if(list.size()>0){
			return R.failed(null,"名称已存在，请重新填写！");
		}

		//1:校验部门编码
		List compCompList = otdepartmentService.listOtdepartmentByDepCode(otdepartment);
		if(compCompList.size()>0){
			return R.failed(null,"部门编号已存在，请重新填写");
		}
		//2:校验上级部门

		if(!StringUtils.isEmpty(otdepartment.getAdminid())){
			List adminIDList = otdepartmentService.getOtdepartmentByAdminID(otdepartment);
			if(!(adminIDList.size()>0)){
				return R.failed(null,"上级部门必须存在且有效，或者生效日期必须晚于上级部门的创建日期！");
			}
		}

		//3:所属公司必须存在且有效,或生效日期晚于所属公司的创建日期
		List list2 = otdepartmentService.getOtdepartmentByCompID(otdepartment);
		if(!(list2.size()>0)){
			return R.failed(null,"所属公司必须存在且有效,或生效日期晚于所属公司的创建日期！");
		}
		//4:部门上级必须在所属公司里的部门！
		if(!StringUtils.isEmpty(otdepartment.getAdminid())){
			List list3 = otdepartmentService.getOtdepartmentByAdminIDinCompID(otdepartment);
			if(!(list3.size()>0)){
				return R.failed(null,"部门上级必须在所属公司里的部门！");
			}
		}
		OtdepchangeAll otdepchangeAll = new OtdepchangeAll();
		BeanUtils.copyProperties(otdepartment,otdepchangeAll);
		otdepartmentService.save(otdepartment);
		//动类型 1：新增，2：修改，3：删除
		otdepchangeAll.setType(1);
		otdepchangeAllService.save(otdepchangeAll);
    	return R.ok("新增成功！");
    }

/*    *//**
     * 修改部门信息
     * @param otdepartment 部门信息
     * @return R
     *//*
    @ApiOperation(value = "修改部门信息", notes = "修改部门信息")
    @SysLog("修改部门信息" )
    @PutMapping("/updateById")
    @PreAuthorize("@pms.hasPermission('admin_otdepartment_edit')" )
    public R updateById(@RequestBody Otdepartment otdepartment) {
        return R.ok(otdepartmentService.updateById(otdepartment));
    }



    *//**
     * 通过id删除部门信息
     * @param depid id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除部门信息", notes = "通过id删除部门信息")
    @SysLog("通过id删除部门信息" )
    @DeleteMapping("/{depid}" )
    @PreAuthorize("@pms.hasPermission('admin_otdepartment_del')" )
    public R removeById(@PathVariable Integer depid) {
        return R.ok(otdepartmentService.removeById(depid));
    }
	*/

    /**
	 * 修改部门信息
	 * @return R
	 */
	@ApiOperation(value = "修改部门信息", notes = "修改部门信息")
	@PostMapping("/updateOtdepartment" )
	@Transactional
    public R updateOtdepartment(@RequestBody Otdepartment otdepartment){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//Integer compid = pigxUser.getCompid();
		otdepartment.setCorpcode(corpcode);
		//otdepartment.setCompid(compid);
		Integer depid = otdepartment.getDepid();
		Integer adminid = otdepartment.getAdminid();
		if(adminid!=null){
			if(depid==adminid){
				return R.failed(null,"上级部门不能是自己");
			}
		}

		//带星号的必须填写完整
		/*
		需要补充
		 */
		//1:校验部门编码w
		String depcode = otdepartment.getDepcode();
		if(!StringUtils.isEmpty(depcode)){
			List compCompList = otdepartmentService.listOtdepartmentByDepCode(otdepartment);
			if(compCompList.size()>0){
				return R.failed(null,"部门编号已存在，请重新填写");
			}
		}

		//2:校验上级部门w
		if(!StringUtils.isEmpty(otdepartment.getAdminid())){
			List adminIDList = otdepartmentService.getOtdepartmentByAdminID(otdepartment);
			if(!(adminIDList.size()>0)){
				return R.failed(null,"上级部门必须存在且有效，或者生效日期必须晚于上级部门的创建日期！");
			}
		}

		//3:所属公司必须存在且有效,或生效日期晚于所属公司的创建日期w
		Integer compid = otdepartment.getCompid();
		if(StringUtils.isEmpty(compid)){
			List list2 = otdepartmentService.getOtdepartmentByCompID(otdepartment);
			if(!(list2.size()>0)){
				return R.failed(null,"所属公司必须存在且有效,或生效日期晚于所属公司的创建日期！");
			}
		}

		//4:部门上级必须在所属公司里的部门！
		if(!StringUtils.isEmpty(otdepartment.getAdminid())){
			List list3 = otdepartmentService.getOtdepartmentByAdminIDinCompID(otdepartment);
			if(!(list3.size()>0)){
				return R.failed(null,"部门上级必须在所属公司里的部门！");
			}
		}

		//5:失效的部门不能做变更！！w
		List list4 = otdepartmentService.getOtdepartmentByDisableDepID(otdepartment);
		if(list4.size()>0){
			return R.failed(null,"失效的部门不能做变更！");
		}
		//6:信息没有发生变化！w
		List list5 = otdepartmentService.getOtdepartmentByDepAbbr(otdepartment);
		if(list5.size()>0){
			return R.failed(null,"信息没有发生变化！");
		}
		//查询历史并赋值
		Otdepartment odt = otdepartmentService.getById(depid);
		OtdepchangeAll otdepchangeAll = new OtdepchangeAll();
		BeanUtils.copyProperties(otdepartment,otdepchangeAll);
		///变动类型 1：新增，2：修改，3：删除
		otdepchangeAll.setType(2);
		otdepchangeAll.setRegby(pigxUser.getId());
		otdepchangeAll.setRegdate(DateUtils.getTimeString());
		otdepchangeAll.setRegbyname(pigxUser.getUsername());
		otdepchangeAll.setAddressOld(otdepchangeAll.getAddress());
		otdepchangeAll.setAdminidOld(otdepchangeAll.getAdminid());
		otdepchangeAll.setCityOld(otdepchangeAll.getCity());
		otdepchangeAll.setCompidOld(otdepchangeAll.getCompid());
		otdepchangeAll.setDecriptionOld(otdepchangeAll.getDecription());
		otdepchangeAll.setDepabbrOld(otdepchangeAll.getDepabbr());
		otdepchangeAll.setDepcodeOld(otdepchangeAll.getDepcode());
		otdepchangeAll.setDepcostOld(otdepchangeAll.getDepcost());
		otdepchangeAll.setDeppropertyOld(otdepchangeAll.getDepproperty());
		otdepchangeAll.setDepgradeOld(otdepchangeAll.getDepgrade());
		otdepchangeAll.setDepempOld(otdepchangeAll.getDepemp());
		otdepchangeAll.setDeptypeOld(otdepchangeAll.getDeptype());
		otdepchangeAll.setDirector2Old(otdepchangeAll.getDirector2());
		otdepchangeAll.setDirectorOld(otdepchangeAll.getDirector());
		otdepchangeAll.setDistrictOld(otdepchangeAll.getDistrict());
		otdepchangeAll.setOrgfunctionOld(otdepchangeAll.getOrgfunction());
		otdepchangeAll.setProvinceOld(otdepchangeAll.getProvince());
		otdepchangeAll.setTitleOld(otdepchangeAll.getTitle());
		otdepchangeAll.setIsouOld(otdepchangeAll.getIsou());
		otdepartment.setCorpcode(null);
		otdepartment.setDepid(null);
		UpdateWrapper<Otdepartment> otdepartmentUpdateWrapper = new UpdateWrapper<>();
		otdepartmentUpdateWrapper.eq("corpcode",pigxUser.getCorpcode());
		otdepartmentUpdateWrapper.eq("depid",depid);
		otdepchangeAllService.save(otdepchangeAll);
		otdepartmentService.update(otdepartment,otdepartmentUpdateWrapper);

		return R.ok("数据已修改！");
	}

	/**
	 * 根据条件判断部门是否存在
	 * @return R
	 */
	@ApiOperation(value = "失效部门", notes = "失效部门")
	@PostMapping("/invalidById" )
	public R invalidById(@RequestBody Otdepartment otdepartment){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer depid = otdepartment.getDepid();
		//7:部门失效时请先处理其下属部门
		List list6 = otdepartmentService.getOtdepartmentByDepLoseEfficacy(otdepartment);
		if(list6.size()>0){
			return R.failed(null,"部门失效时请先处理其下属部门！");
		}
		//8:部门失效时请先处理其下属岗位！
		List list7 = otdepartmentService.getOtdepartmentByDepLoseEfficacyJob(otdepartment);
		if(list7.size()>0){
			return R.failed(null,"部门失效时请先处理其下属岗位！");
		}
		//9:部门失效时请先处理部门内部在职员工！
		List list8 = otdepartmentService.getOtdepartmentByDepLoseEfficacyEmployee(otdepartment);
		if(list8.size()>0){
			return R.failed(null,"部门失效时请先处理部门内部在职员工！");
		}
		//10:部门失效时请先处理兼职对信息！
		List list9 = otdepartmentService.getOtdepartmentByDepLoseEfficacyTeam(otdepartment);
		if( list9.size()>0){
			return R.failed(null,"部门失效时请先处理兼职对信息！");
		}
		//查询历史并赋值
		Otdepartment odt = otdepartmentService.getById(depid);
		OtdepchangeAll otdepchangeAll = new OtdepchangeAll();
		BeanUtils.copyProperties(otdepartment,otdepchangeAll);
		///变动类型 1：新增，2：修改，3：删除
		otdepchangeAll.setType(3);
		otdepchangeAll.setRegby(pigxUser.getId());
		otdepchangeAll.setRegdate(DateUtils.getTimeString());
		otdepchangeAll.setRegbyname(pigxUser.getUsername());
		otdepchangeAll.setRegbyname(pigxUser.getUsername());
		otdepchangeAll.setAddressOld(otdepchangeAll.getAddress());
		otdepchangeAll.setAdminidOld(otdepchangeAll.getAdminid());
		otdepchangeAll.setCityOld(otdepchangeAll.getCity());
		otdepchangeAll.setCompidOld(otdepchangeAll.getCompid());
		otdepchangeAll.setDecriptionOld(otdepchangeAll.getDecription());
		otdepchangeAll.setDepabbrOld(otdepchangeAll.getDepabbr());
		otdepchangeAll.setDepcodeOld(otdepchangeAll.getDepcode());
		otdepchangeAll.setDepcostOld(otdepchangeAll.getDepcost());
		otdepchangeAll.setDeppropertyOld(otdepchangeAll.getDepproperty());
		otdepchangeAll.setDepgradeOld(otdepchangeAll.getDepgrade());
		otdepchangeAll.setDepempOld(otdepchangeAll.getDepemp());
		otdepchangeAll.setDeptypeOld(otdepchangeAll.getDeptype());
		otdepchangeAll.setDirector2Old(otdepchangeAll.getDirector2());
		otdepchangeAll.setDirectorOld(otdepchangeAll.getDirector());
		otdepchangeAll.setDistrictOld(otdepchangeAll.getDistrict());
		otdepchangeAll.setOrgfunctionOld(otdepchangeAll.getOrgfunction());
		otdepchangeAll.setProvinceOld(otdepchangeAll.getProvince());
		otdepchangeAll.setTitleOld(otdepchangeAll.getTitle());
		otdepchangeAll.setIsouOld(otdepchangeAll.getIsou());

		Otdepartment o = new Otdepartment();
		o.setIsdisabled(1);
		UpdateWrapper<Otdepartment> otdepartmentUpdateWrapper = new UpdateWrapper<>();
		otdepartmentUpdateWrapper.eq("depid",depid);
		otdepchangeAllService.save(otdepchangeAll);
		otdepartmentService.remove(otdepartmentUpdateWrapper);
		return R.ok("数据已修改！");
	}

	//统计报表
	/**
	 * 员工在职、待入职、待离职状态统计
	 * @param etemployee 员工信息表
	 * @return
	 */
	@ApiOperation(value = "部门/统计", notes = "员工在职、待入职、待离职状态统计")
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
	//组织统计报表
	/**
	 * 部门/岗位/岗位类型数量统计
	 * @return
	 */
	@ApiOperation(value = "部门/岗位/岗位类型数量统计", notes = "部门/岗位/岗位类型数量统计")
	@PostMapping("/getOrganizationCount" )
	public R getOrganizationCount() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Otdepartment otdepartment = new Otdepartment();
		otdepartment.setCorpcode(corpcode);

		long depcount = 0;//部门数量
		long jobcount = 0;//岗位数量
		long jobtypecount = 0;//岗位类型数量
		long positioncount = 0;//职务数量
		Map depmap = otdepartmentService.getOtdepartmentByCount(otdepartment);
		if(depmap!=null){
			depcount = (Long) depmap.get("depcount");
		}
		Map jobmap = otdepartmentService.getOtjobByCount(otdepartment);
		if(jobmap!=null){
			jobcount = (Long) jobmap.get("jobcount");
		}
		Map jobtypemap = otdepartmentService.getOtcdjobtypeByCount(otdepartment);
		if(jobtypemap!=null){
			jobtypecount = (Long) jobtypemap.get("jobtypecount");
		}
		Map positionmap = otdepartmentService.getOtcdPositionByCount(otdepartment);
		if(positionmap!=null){
			positioncount = (Long) positionmap.get("positioncount");
		}
		Map resultMap1 = new HashMap();
		Map resultMap2 = new HashMap();
		Map resultMap3 = new HashMap();
		Map resultMap4 = new HashMap();
		resultMap1.put("title","部门数");
		resultMap1.put("count",depcount);
		resultMap2.put("title","岗位数");
		resultMap2.put("count",jobcount);
		resultMap3.put("title","岗位类型");
		resultMap3.put("count",jobtypecount);
		resultMap4.put("title","职务数");
		resultMap4.put("count",positioncount);
		List resultList = new ArrayList(4);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		resultList.add(2,resultMap3);
		resultList.add(3,resultMap4);
		return R.ok(resultList);
	}

	/**
	 *
	 * 部门概况
	 * @return
	 */
	@ApiOperation(value = "部门概况", notes = "部门概况")
	@PostMapping("/getOtdepartmentByPeople" )
	public R getOtdepartmentByPeople() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Otdepartment otdepartment = new Otdepartment();
		Etemployee etemployee = new Etemployee();
		otdepartment.setCorpcode(corpcode);
		etemployee.setCorpcode(corpcode);
		long employeecount= 0;
		double bili = 0.0;
		long count = 0;
		List<Map> deptList = otdepartmentService.getOtdepartmentByPeople(otdepartment);
		Map employeeByCommonCount = etemployeeService.getEmployeeByCommonCount(etemployee);
		if(employeeByCommonCount!=null){
			employeecount = (Long) employeeByCommonCount.get("count");

		}else{
			employeecount =99999999;
		}
		List resultList = new ArrayList(deptList.size());
		Map map = null;
		for(int i=0;i<deptList.size();i++){
			map = deptList.get(i);
			count= (Long)map.get("count");
			if(employeecount==0) {
				bili=0;
			}else{
				bili = new BigDecimal((float)count/employeecount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			}

			map.put("bili",bili);
			resultList.add(i,map);
		}
		return R.ok(resultList);
	}

	/**
	 *
	 * 岗位概况
	 * @return
	 */
	@ApiOperation(value = "岗位概况", notes = "岗位概况")
	@PostMapping("/getOtjobByPeople" )
	public R getOtjobByPeople() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Otdepartment otdepartment = new Otdepartment();
		Etemployee etemployee = new Etemployee();
		otdepartment.setCorpcode(corpcode);
		etemployee.setCorpcode(corpcode);
		long employeecount= 0;
		double bili = 0.0;
		long count = 0;
		List<Map> jobList = otdepartmentService.getOtjobByPeople(otdepartment);
		Map employeeByCommonCount = etemployeeService.getEmployeeByCommonCount(etemployee);
		if(employeeByCommonCount!=null){
			employeecount = (Long) employeeByCommonCount.get("count");

		}else{
			employeecount =99999999;
		}
		List resultList = new ArrayList(jobList.size());
		Map map = null;
		for(int i=0;i<jobList.size();i++){
			map = jobList.get(i);
			count= (Long)map.get("count");
			bili = new BigDecimal((float)count/employeecount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			map.put("bili",bili);
			resultList.add(i,map);
		}
		return R.ok(resultList);
	}

	/**
	 *
	 *
	 * 部门人员流动
	 * @return
	 */
	@ApiOperation(value = "部门人员流动", notes = "部门人员流动")
	@PostMapping("/getEmployeeFlowByDept" )
	public R getEmployeeFlowByMonth() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtleaveAll etleaveAll  = new EtleaveAll();
		etleaveAll.setCorpcode(corpcode);
		etleaveAll.setType(1);
		EtstaffAll etstaffAll = new EtstaffAll();
		etstaffAll.setCorpcode(corpcode);
		etstaffAll.setType(1);
		List<Map> ruzhi = otdepartmentService.getEmployeeFlowRuZhiByDept(etstaffAll);
		List<Map> lizhi = otdepartmentService.getEmployeeLiZhiFlowByDept(etleaveAll);

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
	 *
	 * 岗位人员流动
	 * @return
	 */
	@ApiOperation(value = "岗位人员流动", notes = "岗位人员流动")
	@PostMapping("/getEmployeeFlowByJob" )
	public R getEmployeeFlowByJob() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		EtleaveAll etleaveAll  = new EtleaveAll();
		etleaveAll.setCorpcode(corpcode);
		etleaveAll.setType(1);
		EtstaffAll etstaffAll = new EtstaffAll();
		etstaffAll.setCorpcode(corpcode);
		etstaffAll.setType(1);
		List<Map> ruzhi = otdepartmentService.getEmployeeFlowRuZhiByJob(etstaffAll);
		List<Map> lizhi = otdepartmentService.getEmployeeLiZhiFlowByJob(etleaveAll);

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
	 * 部门管理详情接口
	 * @return
	 */
	@ApiOperation(value = "部门管理详情接口", notes = "部门管理详情接口")
	@PostMapping("/getOtdepartmentForDetail" )
	public R getOtdepartmentForDetail(@RequestBody Otdepartment otdepartment) {
		List<Map>  maplist = otdepartmentMapper.listOtdepartmentByDepId(otdepartment);
		return R.ok(maplist);
	}
	/**
	 *
	 * 部门管理详情接口
	 * @return
	 */
	@ApiOperation(value = "部门管理详情接口2", notes = "部门管理详情接口2")
	@PostMapping("/getOtdepartmentForDetail2" )
	public R getOtdepartmentForDetail2(@RequestBody Otdepartment otdepartment) {
		List<Map>  maplist = otdepartmentMapper.listEmployeeLiByDepId(otdepartment);
		return R.ok(maplist);
	}
	/**
	 *
	 * 部门管理详情接口
	 * @return
	 */
	@ApiOperation(value = "部门管理详情接口3", notes = "部门管理详情接口3")
	@PostMapping("/getOtdepartmentForDetail3" )
	public R getOtdepartmentForDetail3(@RequestBody Otdepartment otdepartment) {
		List<Map>  maplist = otdepartmentMapper.listOtdepartmentByDepIdForDeatail(otdepartment);
		return R.ok(maplist);
	}
	/**
	 * 合并部门
	 * @return R
	 */
	@ApiOperation(value = "合并部门", notes = "合并部门")
	@PostMapping("/combineOtdepartment" )
	@Transactional
	public R combineOtdepartment(@RequestBody Map map){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		map.put("corpcode",corpcode);
		Integer depid =Integer.parseInt(map.get("depid").toString());
		Integer newdepid = Integer.parseInt(map.get("newdepid").toString());
		if(depid==newdepid){
			return R.failed(null,"新部门不能是自己");
		}

		//判断离职的
		//判断是否有入职
		//判断变动的
		//判断上级领导
		//etleaveRegisterService.get
		//etstaffRegisterService;
		EtleaveRegister etleaveRegister = new EtleaveRegister();
		etleaveRegister.setDepid(depid);
		EtstaffRegister etstaffRegister = new EtstaffRegister();
		etstaffRegister.setDepid(depid);
		List result = etleaveRegisterService.list(Wrappers.query(etleaveRegister));
		if(result!=null && result.size()>0){
			return R.failed("原部门存在没有处理的离职信息，请确认后再操作！");
		}
		List result2 = etstaffRegisterService.list(Wrappers.query(etstaffRegister));
		if(result!=null && result.size()>0){
			return R.failed("原部门存在没有处理的入职或者调动记录，请确认后再操作！");
		}

		//新部门信息
		Otdepartment newotdepartment = otdepartmentService.getById(newdepid);
		Otdepartment odt = otdepartmentService.getById(depid);
		//失效原部门信息 改为删除
//		Otdepartment otdepartment = new Otdepartment();
//		otdepartment.setDepid(depid);
//		otdepartment.setIsdisabled(1);
		QueryWrapper<Otdepartment> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("depid",depid);
		otdepartmentService.remove(queryWrapper);

		//处理岗位信息
		UpdateWrapper<Otjob> otjobUpdateWrapper = new UpdateWrapper<>();
		otjobUpdateWrapper.eq("corpcode",pigxUser.getCorpcode());
		otjobUpdateWrapper.eq("depid",depid);
		Otjob otjob = new Otjob();
		otjob.setDepid(newdepid);
		otjobService.update(otjob,otjobUpdateWrapper);

		//修改原部门员工信息
		Etemployee etemployee = new Etemployee();
		etemployee.setDepid(depid);
		Map depmap = new HashMap();
		depmap.put("depid",depid);
		depmap.put("newdepid",newdepid);
		depmap.put("reportto",newotdepartment.getDirector());
		depmap.put("wfreportto",newotdepartment.getDirector2());
		depmap.put("corpcode",corpcode);
		etemployeeMapper.updateEmployeeForDepCombine(depmap);


		//插入历史
		//1、插入部门历史

		OtdepchangeAll otdepchangeAll = new OtdepchangeAll();
		BeanUtils.copyProperties(odt,otdepchangeAll);
		otdepchangeAll.setRegbyname(pigxUser.getUsername());
		otdepchangeAll.setAddressOld(otdepchangeAll.getAddress());
		otdepchangeAll.setAdminidOld(otdepchangeAll.getAdminid());
		otdepchangeAll.setCityOld(otdepchangeAll.getCity());
		otdepchangeAll.setCompidOld(otdepchangeAll.getCompid());
		otdepchangeAll.setDecriptionOld(otdepchangeAll.getDecription());
		otdepchangeAll.setDepabbrOld(otdepchangeAll.getDepabbr());
		otdepchangeAll.setDepcodeOld(otdepchangeAll.getDepcode());
		otdepchangeAll.setDepcostOld(otdepchangeAll.getDepcost());
		otdepchangeAll.setDeppropertyOld(otdepchangeAll.getDepproperty());
		otdepchangeAll.setDepgradeOld(otdepchangeAll.getDepgrade());
		otdepchangeAll.setDepempOld(otdepchangeAll.getDepemp());
		otdepchangeAll.setDeptypeOld(otdepchangeAll.getDeptype());
		otdepchangeAll.setDirector2Old(otdepchangeAll.getDirector2());
		otdepchangeAll.setDirectorOld(otdepchangeAll.getDirector());
		otdepchangeAll.setDistrictOld(otdepchangeAll.getDistrict());
		otdepchangeAll.setOrgfunctionOld(otdepchangeAll.getOrgfunction());
		otdepchangeAll.setProvinceOld(otdepchangeAll.getProvince());
		otdepchangeAll.setTitleOld(otdepchangeAll.getTitle());
		otdepchangeAll.setInitializedby(pigxUser.getId());
		otdepchangeAll.setInitializedtime(DateUtils.getTimeString());
		otdepchangeAll.setType(4);// 部门合并
		otdepchangeAllService.save(otdepchangeAll);
		//2、插入岗位历史
		Otjob otjob2 = new Otjob();
		otjob2.setDepid(newdepid);
		List otjbolist  = otjobService.list(Wrappers.query(otjob2));
		otjobService.saveBatch(otjbolist);
		//3、插入人员历史
		Etemployee etemployee2 = new Etemployee();
		etemployee2.setDepid(newdepid);
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
		etstaffAllService.saveBatch(etstallList);
		return R.ok("数据已修改！");
	}

	/**
	 * 分页查询只返回list
	 * @param page 分页对象
	 * @param name 员工姓名
	 * @return
	 */
	@ApiOperation(value = "分页查询只返回list", notes = "分页查询只返回list")
	@GetMapping(value={"/getOtdepartmentPageList/{name}","/getOtdepartmentPageList"} )
	public R getOtdepartmentPageList(Page page, @PathVariable(required = false) String name) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Otdepartment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		if(!StringUtils.isEmpty(name)){
			queryWrapper.like("title",name);
		}
		queryWrapper.eq("isdisabled",0);
		IPage resultpage = otdepartmentService.page(page,queryWrapper);
		List resultList = resultpage.getRecords();
		return R.ok(resultList,"", CommonConstants.SUCCESS200);
	}

	/**
	 * 根据compid获取部门列表
	 * @return
	 */
	@ApiOperation(value = "根据compid获取部门列表", notes = "根据compid获取部门列表")
	@PostMapping("/getOtdeparmentListByCompid" )
	public R getCompDeitalByCompid(Page page, Otdepartment otdepartment) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer compid = pigxUser.getCompid();
		otdepartment.setCorpcode(corpcode);
		otdepartment.setCompid(compid);
		IPage<Map> resultMap = otdepartmentMapper.listOtdepartmentByCompid(page,otdepartment);
		return R.ok(resultMap);
	}

	/**
	 * 通过depid获取部门详情
	 * @return
	 */
	@ApiOperation(value = "通过depid获取部门详情", notes = "通过depid获取部门详情")
	@PostMapping("/getOtdeparmentDeitalBydepid" )
	public R getOtdeparmentDeitalBydepid(@RequestBody Otdepartment otdepartment) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otdepartment.setCorpcode(corpcode);
		List<Map> resultList  = otdepartmentMapper.listOtdepartmentByDepIdForDeatail(otdepartment);
		return R.ok(resultList);
	}

	/**
	 * 根据depid获取部门列表
	 * @return
	 */
	@ApiOperation(value = "根据adminid获取部门列表", notes = "根据adminidd获取部门列表")
	@PostMapping("/getOtdeparmentListByDepid" )
	public R getOtdeparmentListByDepid(Page page, Otdepartment otdepartment) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otdepartment.setCorpcode(corpcode);
		IPage<Map> resultMap = otdepartmentMapper.listOtdepartmentByDepid(page,otdepartment);
		return R.ok(resultMap);
	}


	@PostMapping("/createDepartment" )
	public R createDepartment(){
		String scorpid = "wwc649dbd1e325fb9f";
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();


		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}

		QueryWrapper<Otdepartment> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("qywx_flag",1);
		queryWrapper2.eq("corpcode",corpcode);
		List<Otdepartment> departList = otdepartmentService.list(queryWrapper2);
		Otdepartment ldepartment = null;
		Map map = null;
		for(int i=0;i<departList.size();i++){
			ldepartment = departList.get(i);
			map = new HashMap();
			map.put("qywxcorpid",qywxcorpid);
			map.put("id",ldepartment.getDepid());
			map.put("name",ldepartment.getTitle());
			map.put("parentid",ldepartment.getAdminid());
			map.put("order",ldepartment.getXorder());

			String code = sysQywxApplicationService.createDepartment(map);
			if("0".equals(code)){
				Otdepartment otdepartment1 =  new Otdepartment();
				otdepartment1.setDepid(ldepartment.getDepid());
				otdepartment1.setQywxFlag(0);
				otdepartmentService.updateById(otdepartment1);
			}else{
				return R.ok(code);
			}
		}
		return R.ok("创建成功！");
	}

}
