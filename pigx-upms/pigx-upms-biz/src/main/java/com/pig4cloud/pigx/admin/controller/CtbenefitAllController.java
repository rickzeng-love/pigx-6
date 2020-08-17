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
import com.pig4cloud.pigx.admin.entity.CtbenefitAll;
import com.pig4cloud.pigx.admin.service.CtbenefitAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 福利开启历史
 *
 * @author XP
 * @date 2020-06-11 15:05:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbenefitall")
@Api(value = "ctbenefitall", tags = "福利开启历史管理")
public class CtbenefitAllController {

	private final CtbenefitAllService ctbenefitAllService;

	/**
	 * 分页查询
	 *
	 * @param page         分页对象
	 * @param ctbenefitAll 福利开启历史
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/page")
	public R getCtbenefitAllPage(Page page, CtbenefitAll ctbenefitAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctbenefitAll.setCorpcode(corpcode);
		ctbenefitAll.setCorpid(corpid);
		String name = ctbenefitAll.getName();
		int depid = ctbenefitAll.getDepid();
		int jobid = ctbenefitAll.getJobid();
		QueryWrapper<CtbenefitAll> queryWrapper = new QueryWrapper<>();
		if (name != null && name != "") {
			queryWrapper.like("Name", name);
		}
		if (depid > 0) {
			queryWrapper.or().eq("DepID", depid);
		}
		if (jobid > 0) {
			queryWrapper.or().eq("JobID", jobid);
		}
		return R.ok(ctbenefitAllService.page(page, queryWrapper));
	}


	/**
	 * 通过id查询福利开启历史
	 *
	 * @param id id
	 * @return R
	 */
	/*@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(ctbenefitAllService.getById(id));
	}
*/
	/**
	 * 新增福利开启历史
	 *
	 * @param ctbenefitAll 福利开启历史
	 * @return R
	 */
	/*@ApiOperation(value = "新增福利开启历史", notes = "新增福利开启历史")
	@SysLog("新增福利开启历史")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_ctbenefitall_add')")
	public R save(@RequestBody CtbenefitAll ctbenefitAll) {
		return R.ok(ctbenefitAllService.save(ctbenefitAll));
	}
*/
	/**
	 * 修改福利开启历史
	 *
	 * @param ctbenefitAll 福利开启历史
	 * @return R
	 */
	/*@ApiOperation(value = "修改福利开启历史", notes = "修改福利开启历史")
	@SysLog("修改福利开启历史")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_ctbenefitall_edit')")
	public R updateById(@RequestBody CtbenefitAll ctbenefitAll) {
		return R.ok(ctbenefitAllService.updateById(ctbenefitAll));
	}*/

	/**
	 * 通过id删除福利开启历史
	 *
	 * @param id id
	 * @return R
	 */
	/*@ApiOperation(value = "通过id删除福利开启历史", notes = "通过id删除福利开启历史")
	@SysLog("通过id删除福利开启历史")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_ctbenefitall_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(ctbenefitAllService.removeById(id));
	}
*/
}
