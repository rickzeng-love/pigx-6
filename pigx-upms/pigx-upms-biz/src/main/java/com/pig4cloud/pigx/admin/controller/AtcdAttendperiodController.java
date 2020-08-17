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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtcdAttendperiod;
import com.pig4cloud.pigx.admin.service.AtcdAttendperiodService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 考勤期间设置
 *
 * @author GXS
 * @date 2020-05-25 11:06:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdattendperiod" )
@Api(value = "atcdattendperiod", tags = "考勤期间设置管理")
public class AtcdAttendperiodController {

    private final  AtcdAttendperiodService atcdAttendperiodService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdAttendperiod 考勤期间设置
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdAttendperiodPage(Page page, AtcdAttendperiod atcdAttendperiod) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdAttendperiod.setCorpcode(corpCode);
        return R.ok(atcdAttendperiodService.page(page, Wrappers.query(atcdAttendperiod)));
    }

	/**
	 * 查询所有
	 * @param atcdAttendperiod
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllAtcdAttendperiod" )
    public R getAllAtcdAttendperiod(AtcdAttendperiod atcdAttendperiod)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atcdAttendperiod.setCorpcode(corpcode);
		return R.ok(atcdAttendperiodService.list(Wrappers.query(atcdAttendperiod)));
	}

	/**
	 * 根据考勤账套ID获取考勤期间信息
	 * @param atcdAttendperiod
	 * @return
	 */
	@ApiOperation(value = "根据考勤账套获取考勤期间信息", notes = "根据考勤账套获取考勤期间信息")
	@GetMapping("/getAtcdAttendperiodByAgentMode" )
    public R getAtcdAttendperiodByAgentMode(AtcdAttendperiod atcdAttendperiod)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdAttendperiod.setCorpcode(corpCode);
		return R.ok(atcdAttendperiodService.list(Wrappers.query(atcdAttendperiod).eq("AgentMode",atcdAttendperiod.getAgentmode())));
	}


    /**
     * 通过id查询考勤期间设置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdAttendperiodService.getById(id));
    }

    /**
     * 新增考勤期间设置
     * @param atcdAttendperiod 考勤期间设置 @PreAuthorize("@pms.hasPermission('admin_atcdattendperiod_add')" )
     * @return R
     */
    @ApiOperation(value = "新增考勤期间设置", notes = "新增考勤期间设置")
    @SysLog("新增考勤期间设置" )
    @PostMapping
    public R save(@RequestBody AtcdAttendperiod atcdAttendperiod) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		atcdAttendperiod.setCorpid(corpID);
		atcdAttendperiod.setCorpcode(corpCode);
		atcdAttendperiod.setTitle("考勤周期(本月第几天)");
		atcdAttendperiod.setMonflag(0);
        return R.ok(atcdAttendperiodService.save(atcdAttendperiod));
    }

/*
    */
/**
     * 修改考勤期间设置
     * @param atcdAttendperiod 考勤期间设置 @PreAuthorize("@pms.hasPermission('admin_atcdattendperiod_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改考勤期间设置", notes = "修改考勤期间设置")
    @SysLog("修改考勤期间设置" )
    @PutMapping
    public R updateById(@RequestBody AtcdAttendperiod atcdAttendperiod) {
        return R.ok(atcdAttendperiodService.updateById(atcdAttendperiod));
    }


    */
/**
     * 通过id删除考勤期间设置
     * @param id id @PreAuthorize("@pms.hasPermission('admin_atcdattendperiod_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤期间设置", notes = "通过id删除考勤期间设置")
    @SysLog("通过id删除考勤期间设置" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcdAttendperiodService.removeById(id));
    }
*/

}
