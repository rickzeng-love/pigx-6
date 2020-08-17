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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtbenpaybackAll;
import com.pig4cloud.pigx.admin.service.CtbenpaybackAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 社保福利补缴历史
 *
 * @author xp
 * @date 2020-06-17 15:39:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbenpaybackall")
@Api(value = "ctbenpaybackall", tags = "社保福利补缴历史管理")
public class CtbenpaybackAllController {

	private final CtbenpaybackAllService ctbenpaybackAllService;

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param ctbenpaybackAll 社保福利补缴历史
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getCtbenpaybackAllPage")
	public R getCtbenpaybackAllPage(Page page, @RequestBody(required = false)  CtbenpaybackAll ctbenpaybackAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		if(StringUtils.isEmpty(ctbenpaybackAll)){
			ctbenpaybackAll = new CtbenpaybackAll();
		}
		ctbenpaybackAll.setCorpcode(corpcode);
		String name = ctbenpaybackAll.getName();
		Integer depid = ctbenpaybackAll.getDepid();
		Integer jobid = ctbenpaybackAll.getJobid();
		QueryWrapper<CtbenpaybackAll> queryWrapper = new QueryWrapper<>();
		if (name != null && name != "") {
			queryWrapper.like("Name", name);
		}
		if (!StringUtils.isEmpty(depid)) {
			queryWrapper.eq("DepID", depid);
		}
		if (!StringUtils.isEmpty(jobid)) {
			queryWrapper.eq("JobID", jobid);
		}
		return R.ok(ctbenpaybackAllService.page(page, queryWrapper));
	}


	/**
	 * 通过id查询社保福利补缴历史
	 *
	 * @param id id
	 * @return R
	 */
/*
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(ctbenpaybackAllService.getById(id));
	}
*/

	/**
	 * 新增社保福利补缴历史
	 *
	 * @param ctbenpaybackAll 社保福利补缴历史
	 * @return R
	 *//*
	@ApiOperation(value = "新增社保福利补缴历史", notes = "新增社保福利补缴历史")
	@SysLog("新增社保福利补缴历史")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_ctbenpaybackall_add')")
	public R save(@RequestBody CtbenpaybackAll ctbenpaybackAll) {
		return R.ok(ctbenpaybackAllService.save(ctbenpaybackAll));
	}*/

	/**
	 * 修改社保福利补缴历史
	 *
	 * @param ctbenpaybackAll 社保福利补缴历史
	 * @return R
	 *//*
	@ApiOperation(value = "修改社保福利补缴历史", notes = "修改社保福利补缴历史")
	@SysLog("修改社保福利补缴历史")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_ctbenpaybackall_edit')")
	public R updateById(@RequestBody CtbenpaybackAll ctbenpaybackAll) {
		return R.ok(ctbenpaybackAllService.updateById(ctbenpaybackAll));
	}*/

	/**
	 * 通过id删除社保福利补缴历史
	 *
	 * @param id id
	 * @return R
	 *//*
	@ApiOperation(value = "通过id删除社保福利补缴历史", notes = "通过id删除社保福利补缴历史")
	@SysLog("通过id删除社保福利补缴历史")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_ctbenpaybackall_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(ctbenpaybackAllService.removeById(id));
	}
*/
}
