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
import com.pig4cloud.pigx.admin.entity.CtcdSalarygrade;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdSalarykind;
import com.pig4cloud.pigx.admin.service.CtcdSalarykindService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资架构
 *
 * @author gaoxiao
 * @date 2020-06-11 13:57:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdsalarykind" )
@Api(value = "ctcdsalarykind", tags = "薪资架构管理")
public class CtcdSalarykindController {

    private final  CtcdSalarykindService ctcdSalarykindService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdSalarykind 薪资架构
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdSalarykindPage(Page page, CtcdSalarykind ctcdSalarykind) {
        return R.ok(ctcdSalarykindService.page(page, Wrappers.query(ctcdSalarykind)));
    }

	/**
	 * 薪资架构
	 * @param ctcdSalarykind
	 * @ctcdSalarykind
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdSalarykind" )
	public R getAllCtcdSalarykind( CtcdSalarykind ctcdSalarykind) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdSalarykind.setCorpcode(corpcode);
		return R.ok(ctcdSalarykindService.list(Wrappers.query(ctcdSalarykind)));
	}
    /**
     * 通过id查询薪资架构
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdSalarykindService.getById(id));
    }

    /**
     * 新增薪资架构      @PreAuthorize("@pms.hasPermission('admin_ctcdsalarykind_add')" )
     * @param ctcdSalarykind 薪资架构
     * @return R
     */
    @ApiOperation(value = "新增薪资架构", notes = "新增薪资架构")
    @SysLog("新增薪资架构" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdSalarykind ctcdSalarykind) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdSalarykind.setCorpcode(corpcode);
		ctcdSalarykind.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdSalarykind> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSalarykind.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdSalarykindService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdSalarykindService.save(ctcdSalarykind));
    }

    /**
     * 修改薪资架构      @PreAuthorize("@pms.hasPermission('admin_ctcdsalarykind_edit')" )
     * @param ctcdSalarykind 薪资架构
     * @return R
     */
    @ApiOperation(value = "修改薪资架构", notes = "修改薪资架构")
    @SysLog("修改薪资架构" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdSalarykind ctcdSalarykind) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdSalarykind> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSalarykind.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdSalarykind.getId());
		List list = ctcdSalarykindService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdSalarykind> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdSalarykind.getId());
        return R.ok(ctcdSalarykindService.update(ctcdSalarykind,updateWrapper));
    }

    /**
     * 通过id删除薪资架构
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资架构", notes = "通过id删除薪资架构")
    @SysLog("通过id删除薪资架构" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarykind_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdSalarykindService.removeById(id));
    }

}
