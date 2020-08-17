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
import com.pig4cloud.pigx.admin.entity.AtstaffRegister;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AvwStatus;
import com.pig4cloud.pigx.admin.service.AvwStatusService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-07-07 14:07:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/avwstatus" )
@Api(value = "avwstatus", tags = "VIEW管理")
public class AvwStatusController {

    private final  AvwStatusService avwStatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param avwStatus VIEW
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAvwStatusPage(Page page, AvwStatus avwStatus) {
        return R.ok(avwStatusService.page(page, Wrappers.query(avwStatus)));
    }
	/**
	 * 考勤信息查询
	 * @param page 分页对象
	 * @param avwStatus 考勤开启查询
	 * @return
	 * 测试通过
	 */
	@ApiOperation(value = "考勤信息查询", notes = "考勤信息查询")
	@PostMapping("/getAvwStatusPageBySql" )
	public R getAvwStatusPageBySql(Page page,@RequestBody(required=false) AvwStatus avwStatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(avwStatus)){
			avwStatus = new AvwStatus();
		}
		QueryWrapper<AvwStatus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = avwStatus.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",name);
		}
		Integer depid = avwStatus.getDepid();
		Integer jobid = avwStatus.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("jobid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(avwStatusService.page(page,queryWrapper));
	}

    /**
     * 通过id查询VIEW
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(avwStatusService.getById(term));
    }

    /**
     * 新增VIEW
     * @param avwStatus VIEW
     * @return R
     */
    @ApiOperation(value = "新增VIEW", notes = "新增VIEW")
    @SysLog("新增VIEW" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_avwstatus_add')" )
    public R save(@RequestBody AvwStatus avwStatus) {
        return R.ok(avwStatusService.save(avwStatus));
    }

    /**
     * 修改VIEW
     * @param avwStatus VIEW
     * @return R
     */
    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_avwstatus_edit')" )
    public R updateById(@RequestBody AvwStatus avwStatus) {
        return R.ok(avwStatusService.updateById(avwStatus));
    }

    /**
     * 通过id删除VIEW
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_avwstatus_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(avwStatusService.removeById(term));
    }

}
