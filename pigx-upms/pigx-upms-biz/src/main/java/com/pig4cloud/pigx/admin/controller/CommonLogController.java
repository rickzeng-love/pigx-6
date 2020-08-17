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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统通用标记，薪资、考勤等开启记录
 *
 * @author gaoxiao
 * @date 2020-06-16 15:17:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/commonlog" )
@Api(value = "commonlog", tags = "系统通用标记，薪资、考勤等开启记录管理")
public class CommonLogController {

    private final  CommonLogService commonLogService;
	private final  CtcdSalarygradeService ctcdSalarygradeService;
	private final  CtcdSalarykindService ctcdSalarykindService;
	private final CtcdPaymodeService ctcdPaymodeService;
	private final  CtcdCalcwayService ctcdCalcwayService;
	private final  CtcdCostcenterService ctcdCostcenterService;
	private final  CtcdEmpgradeSalarygradeService ctcdEmpgradeSalarygradeService;
	private final  CtcdSicktypeService ctcdSicktypeService;
	private final  CtcdAttendsickrateService ctcdAttendsickrateService;
	private final  CtcdBeartypeService ctcdBeartypeService;
	private final  CtcdBenareaService ctcdBenareaService;
	private final  CtcdBenrateService ctcdBenrateService;
	private final  CtcdBensupplyreasonService ctcdBensupplyreasonService;
	private final  CtcdBentypeService ctcdBentypeService;
	private final  CtcdCitypayService ctcdCitypayService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param commonLog 系统通用标记，薪资、考勤等开启记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCommonLogPage(Page page, CommonLog commonLog) {
        return R.ok(commonLogService.page(page, Wrappers.query(commonLog)));
    }
	/**
	 * 查询是否开启过薪资
	 * @return
	 */
	@ApiOperation(value = "查询是否开启过薪资", notes = "查询是否开启过薪资")
	@PostMapping("/getCommonLogList" )
	public R getCommonLogList(@RequestBody  CommonLog commonLog) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CommonLog> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("type",commonLog.getType());
		List list = commonLogService.list(queryWrapper);
		if(list.size()>0){
			return R.ok("已开启");
		}else{
			return R.ok("1","未开启");
		}
	}

    /**
     * 通过id查询系统通用标记，薪资、考勤等开启记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(commonLogService.getById(id));
    }

    /**
     * 新增系统通用标记，薪资、考勤等开启记录     @PreAuthorize("@pms.hasPermission('admin_commonlog_add')" )
     * @param commonLog 系统通用标记，薪资、考勤等开启记录
     * @return R
     */
    @ApiOperation(value = "开启薪资模块", notes = "开启薪资模块")
    @SysLog("开启薪资模块" )
    @PostMapping("/save2")
    public R save2(@RequestBody CommonLog commonLog) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
    	//开启薪资模块
		//处理数据字典
		/*薪资等级管理	 CtcdSalarygrade
		薪资架构管理	CtcdSalarykind
		薪资状态管理	CtcdStatus
		薪资类型 CtcdSalarytype
		付款方式管理	CtcdPaymode
		银行管理	CtcdPaymode
		薪资核算方式管理	CtcdCalcway
		等级对应配置管理	CtcdEmpgradeSalarygrade
		薪资等级管理	CtcdSalarygrade
		病假类型管理	CtcdSicktype
		病假工资比率管理	CtcdAttendsickrate
		承担方式管理	CtcdBeartype
		福利地区管理	CtcdBenarea
		福利比例管理	CtcdBenrate
		基数类型管理	CtcdBeartype
		福利补缴原因管理	CtcdBensupplyreason
		福利类型管理	CtcdBentype
		城市最低工资管理	CtcdCitypay
		成本中心管理	CtcdCostcenter*/
		// CtcdStatus ctcdStatus = new CtcdStatus();
		/*
		 1、 CtcdStatus  薪资状态 0：暂停 1：正常 这个通用，不用处理
		 2、 CtcdSalarytype  薪资类型

		 */

		CtcdSalarytype ctcdSalarytype   = new CtcdSalarytype();
		ctcdSalarytype.setCorpcode(corpcode);
		ctcdSalarytype.setCorpid(corpid);


		CtcdSalarygrade ctcdSalarygrade   = new CtcdSalarygrade();
		CtcdSalarykind ctcdSalarykind = new CtcdSalarykind();

		CtcdPaymode ctcdPaymode = new CtcdPaymode();
		CtcdCalcway ctcdCalcway = new CtcdCalcway();
		CtcdEmpgradeSalarygrade ctcdEmpgradeSalarygrade = new CtcdEmpgradeSalarygrade();
		CtcdSicktype ctcdSicktype = new CtcdSicktype();
		CtcdAttendsickrate ctcdAttendsickrate = new CtcdAttendsickrate();
		CtcdBeartype ctcdBeartype = new CtcdBeartype();
		CtcdBenarea ctcdBenarea = new CtcdBenarea();
		CtcdBenrate ctcdBenrate = new CtcdBenrate();
		CtcdBensupplyreason ctcdBensupplyreason = new CtcdBensupplyreason();
		CtcdBentype ctcdBentype = new CtcdBentype();
		CtcdCitypay ctcdCitypay = new CtcdCitypay();
		CtcdCostcenter ctcdCostcenter = new CtcdCostcenter();

		/*commonLogService.save();
		ctcdSalarygradeService.save();
		ctcdSalarykindService.save();
		ctcdPaymodeService.save();
		ctcdCalcwayService.save();
		ctcdCostcenterService.save();
		ctcdEmpgradeSalarygradeService.save();
		ctcdSicktypeService.save();
		ctcdAttendsickrateService.save();
		ctcdBeartypeService.save();
		ctcdBenareaService.save();
		ctcdBenrateService.save();
		ctcdBensupplyreasonService.save();
		ctcdBentypeService.save();
		pctcdCitypayService.save();*/



		return R.ok(commonLogService.save(commonLog));
    }


	/**
	 * 新增系统通用标记，薪资、考勤等开启记录     @PreAuthorize("@pms.hasPermission('admin_commonlog_add')" )
	 * @param commonLog 系统通用标记，薪资、考勤等开启记录
	 * @return R
	 */
	@ApiOperation(value = "通用标识日志", notes = "通用标识日志")
	@SysLog("通用标识日志" )
	@PostMapping("/save")
	public R save(@RequestBody CommonLog commonLog) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		commonLog.setCorpcode(corpcode);
		commonLog.setCorpid(pigxUser.getCorpid());
		commonLog.setCorpname(pigxUser.getCorpname());
		commonLog.setUsername(pigxUser.getName());
		commonLog.setCreatedate(currentTime);

		return R.ok(commonLogService.save(commonLog));
	}

   /* *//**
     * 修改系统通用标记，薪资、考勤等开启记录
     * @param commonLog 系统通用标记，薪资、考勤等开启记录
     * @return R
     *//*
    @ApiOperation(value = "修改系统通用标记，薪资、考勤等开启记录", notes = "修改系统通用标记，薪资、考勤等开启记录")
    @SysLog("修改系统通用标记，薪资、考勤等开启记录" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_commonlog_edit')" )
    public R updateById(@RequestBody CommonLog commonLog) {
        return R.ok(commonLogService.updateById(commonLog));
    }

    *//**
     * 通过id删除系统通用标记，薪资、考勤等开启记录
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除系统通用标记，薪资、考勤等开启记录", notes = "通过id删除系统通用标记，薪资、考勤等开启记录")
    @SysLog("通过id删除系统通用标记，薪资、考勤等开启记录" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_commonlog_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(commonLogService.removeById(id));
    }
*/
}
