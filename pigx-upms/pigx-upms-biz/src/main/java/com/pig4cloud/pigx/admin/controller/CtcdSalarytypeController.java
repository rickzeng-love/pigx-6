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
import com.pig4cloud.pigx.admin.entity.CtcdSalarykind;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdSalarytype;
import com.pig4cloud.pigx.admin.service.CtcdSalarytypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资类型
 *
 * @author gaoxiao
 * @date 2020-06-11 14:00:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdsalarytype" )
@Api(value = "ctcdsalarytype", tags = "薪资类型管理")
public class CtcdSalarytypeController {

    private final  CtcdSalarytypeService ctcdSalarytypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdSalarytype 薪资类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdSalarytypePage(Page page, CtcdSalarytype ctcdSalarytype) {
        return R.ok(ctcdSalarytypeService.page(page, Wrappers.query(ctcdSalarytype)));
    }

	/**
	 * 薪资类型
	 * @param ctcdSalarytype
	 * @ctcdSalarytype
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdSalarytype" )
	public R getAllCtcdSalarytype( CtcdSalarytype ctcdSalarytype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(ctcdSalarytype)){
			ctcdSalarytype = new CtcdSalarytype();
		}
		ctcdSalarytype.setCorpcode(corpcode);
		return R.ok(ctcdSalarytypeService.list(Wrappers.query(ctcdSalarytype)));
	}
    /**
     * 通过id查询薪资类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdSalarytypeService.getById(id));
    }

    /**
     * 新增薪资类型      @PreAuthorize("@pms.hasPermission('admin_ctcdsalarytype_add')" )
     * @param ctcdSalarytype 薪资类型
     * @return R
     */
    @ApiOperation(value = "新增薪资类型", notes = "新增薪资类型")
    @SysLog("新增薪资类型" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdSalarytype ctcdSalarytype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdSalarytype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSalarytype.getTitle());
		queryWrapper.eq("corpcode",pigxUser.getCorpcode());
		List list = ctcdSalarytypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		ctcdSalarytype.setCorpcode(corpcode);
		ctcdSalarytype.setCorpid(pigxUser.getCorpid());
        return R.ok(ctcdSalarytypeService.save(ctcdSalarytype));
    }

    /**
     * 修改薪资类型      @PreAuthorize("@pms.hasPermission('admin_ctcdsalarytype_edit')" )
     * @param ctcdSalarytype 薪资类型
     * @return R
     */
    @ApiOperation(value = "修改薪资类型", notes = "修改薪资类型")
    @SysLog("修改薪资类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdSalarytype ctcdSalarytype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdSalarytype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSalarytype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdSalarytype.getId());
		List list = ctcdSalarytypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdSalarytype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdSalarytype.getId());
        return R.ok(ctcdSalarytypeService.update(ctcdSalarytype,updateWrapper));
    }

    /**
     * 通过id删除薪资类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资类型", notes = "通过id删除薪资类型")
    @SysLog("通过id删除薪资类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarytype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdSalarytypeService.removeById(id));
    }

}
