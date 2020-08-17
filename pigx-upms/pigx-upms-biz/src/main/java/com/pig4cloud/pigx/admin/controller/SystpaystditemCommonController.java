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
import com.pig4cloud.pigx.admin.entity.SystpayrollitemCommon;
import com.pig4cloud.pigx.admin.entity.SystpaystditemCommon;
import com.pig4cloud.pigx.admin.service.SystpaystditemCommonService;
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
 * 薪资项
 *
 * @author XP
 * @date 2020-06-09 16:04:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpaystditemcommon")
@Api(value = "systpaystditemcommon", tags = "薪资项设置/工资项设置")
public class SystpaystditemCommonController {

	private final SystpaystditemCommonService systpaystditemCommonService;

	/**
	 * 分页查询
	 *
	 * @param page                 分页对象
	 * @param systpaystditemCommon 薪资项
	 * @return
	 */
	/*@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getSystpaystditemCommonPage(Page page, SystpaystditemCommon systpaystditemCommon) {
		return R.ok(systpaystditemCommonService.page(page, Wrappers.query(systpaystditemCommon)));
	}*/


	/**
	 * 通过id查询薪资项
	 *
	 * @param id id
	 * @return R
	 */
	/*@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(systpaystditemCommonService.getById(id));
	}*/

	/**
	 * 新增工资项
	 *
	 * @param systpaystditemCommon 工资项
	 * @PreAuthorize("@pms.hasPermission('admin_systpaystditemcommon_add')")
	 * @return R
	 */
	@ApiOperation(value = "新增工资项", notes = "新增工资项")
	@SysLog("新增工资项")
	@PostMapping("/save")
	public R save(@RequestBody SystpaystditemCommon systpaystditemCommon) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SystpaystditemCommon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",systpaystditemCommon.getTitle());
		List list = systpaystditemCommonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("薪资项名称重复，请核实！");
		}
		systpaystditemCommon.setCorpcode(corpcode);
		systpaystditemCommon.setCorpid(pigxUser.getCorpid());
		systpaystditemCommonService.save(systpaystditemCommon);
		int id = systpaystditemCommon.getId();
		systpaystditemCommon.setColname("item" + id);
		return R.ok(systpaystditemCommonService.updateById(systpaystditemCommon));
	}

	/**
	 * 修改工资项
	 *
	 * @param systpaystditemCommon 工资项
	 * @PreAuthorize("@pms.hasPermission('admin_systpaystditemcommon_edit')")
	 * @return R
	 */
	@ApiOperation(value = "修改工资项", notes = "修改工资项")
	@SysLog("修改工资项")
	@PostMapping("/updateById")
	public R updateById(@RequestBody SystpaystditemCommon systpaystditemCommon) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SystpaystditemCommon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",systpaystditemCommon.getId());
		queryWrapper.eq("title",systpaystditemCommon.getTitle());
		List list = systpaystditemCommonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("薪资项名称重复，请核实！");
		}
		systpaystditemCommon.setCorpcode(corpcode);
		UpdateWrapper<SystpaystditemCommon> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",systpaystditemCommon.getId());
		return R.ok(systpaystditemCommonService.update(systpaystditemCommon,updateWrapper));
	}
	/*
	 * 通过id删除工资项
	 *
			 * @param id id
	 * @PreAuthorize("@pms.hasPermission('admin_systpaystditemcommon_del')")
	 * @return R
	 */

	@ApiOperation(value = "通过id删除工资项", notes = "通过id删除工资项")
	@SysLog("通过id删除工资项")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Long id) {
		return R.ok(systpaystditemCommonService.removeById(id));
	}

/*
	*/
/**
	 * 通过id删除工资项
	 *
	 * @param id id
	 * @PreAuthorize("@pms.hasPermission('admin_systpaystditemcommon_del')")
	 * @return R
	 *//*

	@ApiOperation(value = "通过id删除工资项", notes = "通过id删除工资项")
	@SysLog("通过id删除工资项")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Long id) {
		return R.ok(systpaystditemCommonService.removeById(id));
	}
*/

}
