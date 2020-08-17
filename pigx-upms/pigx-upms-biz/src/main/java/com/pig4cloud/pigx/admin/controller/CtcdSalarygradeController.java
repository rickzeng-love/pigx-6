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
import com.pig4cloud.pigx.admin.entity.EtbgEducation;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdSalarygrade;
import com.pig4cloud.pigx.admin.service.CtcdSalarygradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资等级
 *
 * @author gaoxiao
 * @date 2020-06-11 13:58:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdsalarygrade" )
@Api(value = "ctcdsalarygrade", tags = "薪资等级管理")
public class CtcdSalarygradeController {

    private final  CtcdSalarygradeService ctcdSalarygradeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdSalarygrade 薪资等级
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdSalarygradePage(Page page, CtcdSalarygrade ctcdSalarygrade) {
        return R.ok(ctcdSalarygradeService.page(page, Wrappers.query(ctcdSalarygrade)));
    }

	/**
	 * 薪资等级
	 * @param ctcdSalarygrade
	 * @ctcdSalarygrade
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdSalarygrade" )
	public R getAllCtcdSalarygrade(CtcdSalarygrade ctcdSalarygrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		ctcdSalarygrade.setCorpcode(corpcode);
		return R.ok(ctcdSalarygradeService.list(Wrappers.query(ctcdSalarygrade)));
	}
    /**
     * 通过id查询薪资等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdSalarygradeService.getById(id));
    }

    /**
     * 新增薪资等级     @PreAuthorize("@pms.hasPermission('admin_ctcdsalarygrade_add')" )
     * @param ctcdSalarygrade 薪资等级
     * @return R
     */
    @ApiOperation(value = "新增薪资等级", notes = "新增薪资等级")
    @SysLog("新增薪资等级" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdSalarygrade ctcdSalarygrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdSalarygrade.setCorpcode(corpcode);
		ctcdSalarygrade.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdSalarygrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSalarygrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdSalarygradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdSalarygradeService.save(ctcdSalarygrade));
    }

    /**
     * 修改薪资等级
     * @param ctcdSalarygrade 薪资等级      @PreAuthorize("@pms.hasPermission('admin_ctcdsalarygrade_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改薪资等级", notes = "修改薪资等级")
    @SysLog("修改薪资等级" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdSalarygrade ctcdSalarygrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdSalarygrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSalarygrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdSalarygrade.getId());
		List list = ctcdSalarygradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdSalarygrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdSalarygrade.getId());
        return R.ok(ctcdSalarygradeService.update(ctcdSalarygrade,updateWrapper));
    }

    /**
     * 通过id删除薪资等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资等级", notes = "通过id删除薪资等级")
    @SysLog("通过id删除薪资等级" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarygrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdSalarygradeService.removeById(id));
    }

}
