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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.mapper.EtbgLanguageMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgLanguage;
import com.pig4cloud.pigx.admin.service.EtbgLanguageService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 语言能力
 *
 * @author gaoxiao
 * @date 2020-06-03 11:01:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbglanguage" )
@Api(value = "etbglanguage", tags = "语言能力管理")
public class EtbgLanguageController {

    private final  EtbgLanguageService etbgLanguageService;
    private final EtbgLanguageMapper etbgLanguageMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgLanguage 语言能力
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgLanguagePage(Page page, EtbgLanguage etbgLanguage) {
        return R.ok(etbgLanguageService.page(page, Wrappers.query(etbgLanguage)));
    }


    /**
     * 通过id查询语言能力
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgLanguageService.getById(id));
    }

    /**
     * 新增语言能力
     * @param etbgLanguage 语言能力 @PreAuthorize("@pms.hasPermission('admin_etbglanguage_add')" )
     * @return R
     */
    @ApiOperation(value = "新增语言能力", notes = "新增语言能力")
    @SysLog("新增语言能力" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgLanguage etbgLanguage) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgLanguage.setCorpcode(corpcode);
        return R.ok(etbgLanguageService.save(etbgLanguage));
    }

    /**
     * 修改语言能力
     * @param etbgLanguage 语言能力    @PreAuthorize("@pms.hasPermission('admin_etbglanguage_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改语言能力", notes = "修改语言能力")
    @SysLog("修改语言能力" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgLanguage etbgLanguage) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgLanguage> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgLanguage.getId());
		return R.ok(etbgLanguageService.update(etbgLanguage,updateWrapper));
    }
	/**
	 * 删除语言能力
	 * @param etbgLanguage 语言能力    @PreAuthorize("@pms.hasPermission('admin_etbglanguage_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "删除语言能力", notes = "删除语言能力")
	@SysLog("删除语言能力" )
	@PostMapping("/invalidEtbgLanguage")
	public R invalidEtbgLanguage(@RequestBody EtbgLanguage etbgLanguage) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgLanguage.setIsdisabled(1);
		UpdateWrapper<EtbgLanguage> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgLanguage.getId());
		return R.ok(etbgLanguageService.update(etbgLanguage,updateWrapper));
	}

    /**
     * 通过id删除语言能力
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除语言能力", notes = "通过id删除语言能力")
    @SysLog("通过id删除语言能力" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etbglanguage_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgLanguageService.removeById(id));
    }
	/**
	 * 分员工档案-语言能力
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工档案-语言能力", notes = "员工档案-语言能力")
	@PostMapping("/getEtbgLanguageAllBySql" )
	public R getEtbgLanguageAllBySql(Page page,EtbgLanguage etbgLanguage) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgLanguage.setCorpcode(corpcode);
		IPage resultpage = etbgLanguageMapper.listEtbgLanguageAllBySql(page,etbgLanguage);
		return R.ok(resultpage);
	}
}
