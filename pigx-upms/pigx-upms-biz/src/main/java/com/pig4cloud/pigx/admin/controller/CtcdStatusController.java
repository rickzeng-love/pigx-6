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
import com.pig4cloud.pigx.admin.entity.CtcdBank;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdStatus;
import com.pig4cloud.pigx.admin.service.CtcdStatusService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资状态
 *
 * @author gaoxiao
 * @date 2020-06-10 18:03:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdstatus" )
@Api(value = "ctcdstatus", tags = "薪资状态管理")
public class CtcdStatusController {

    private final  CtcdStatusService ctcdStatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdStatus 薪资状态
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdStatusPage(Page page, CtcdStatus ctcdStatus) {
        return R.ok(ctcdStatusService.page(page, Wrappers.query(ctcdStatus)));
    }


	/**
	 * 薪资状态查询所有
	 * @param ctcdStatus 薪资状态
	 * @return
	 */
	@ApiOperation(value = "薪资状态查询所有", notes = "薪资状态查询所有")
	@PostMapping("/getAllCtcdStatus" )
	public R getAllCtcdStatus(CtcdStatus ctcdStatus) {


		return R.ok(ctcdStatusService.list(Wrappers.query(ctcdStatus)));
	}
    /**
     * 通过id查询薪资状态
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdStatusService.getById(id));
    }

    /**
     * 新增薪资状态     @PreAuthorize("@pms.hasPermission('admin_ctcdstatus_add')" )
     * @param ctcdStatus 薪资状态
     * @return R
     */
    @ApiOperation(value = "新增薪资状态", notes = "新增薪资状态")
    @SysLog("新增薪资状态" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdStatus ctcdStatus) {
		QueryWrapper<CtcdStatus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdStatus.getTitle());
		queryWrapper.ne("id",ctcdStatus.getId());
		List list = ctcdStatusService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}

        return R.ok(ctcdStatusService.save(ctcdStatus));
    }

    /**
     * 修改薪资状态      @PreAuthorize("@pms.hasPermission('admin_ctcdstatus_edit')" )
     * @param ctcdStatus 薪资状态
     * @return R
     */
    @ApiOperation(value = "修改薪资状态", notes = "修改薪资状态")
    @SysLog("修改薪资状态" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdStatus ctcdStatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdStatus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdStatus.getTitle());
		List list = ctcdStatusService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdStatus> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdStatus.getId());
        return R.ok(ctcdStatusService.update(ctcdStatus,updateWrapper));
    }

    /**
     * 通过id删除薪资状态
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资状态", notes = "通过id删除薪资状态")
    @SysLog("通过id删除薪资状态" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdstatus_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdStatusService.removeById(id));
    }

}
