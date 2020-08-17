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
import com.pig4cloud.pigx.admin.entity.CtcdPaymode;
import com.pig4cloud.pigx.admin.service.CtcdPaymodeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 付款方式
 *
 * @author gaoxiao
 * @date 2020-06-11 15:47:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdpaymode" )
@Api(value = "ctcdpaymode", tags = "付款方式管理")
public class CtcdPaymodeController {

    private final  CtcdPaymodeService ctcdPaymodeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdPaymode 付款方式
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdPaymodePage(Page page, CtcdPaymode ctcdPaymode) {
        return R.ok(ctcdPaymodeService.page(page, Wrappers.query(ctcdPaymode)));
    }

	/**
	 * 付款方式查询所有
	 * @param ctcdPaymode 付款方式
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdPaymode" )
	public R getAllCtcdPaymode( CtcdPaymode ctcdPaymode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdPaymode.setCorpcode(corpcode);
		QueryWrapper<CtcdPaymode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdPaymode.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdPaymodeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		return R.ok(ctcdPaymodeService.list(Wrappers.query(ctcdPaymode)));
	}
    /**
     * 通过id查询付款方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdPaymodeService.getById(id));
    }

    /**
     * 新增付款方式      @PreAuthorize("@pms.hasPermission('admin_ctcdpaymode_add')" )
     * @param ctcdPaymode 付款方式
     * @return R
     */
    @ApiOperation(value = "新增付款方式", notes = "新增付款方式")
    @SysLog("新增付款方式" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdPaymode ctcdPaymode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdPaymode.setCorpcode(corpcode);
		ctcdPaymode.setCorpid(pigxUser.getCorpid());
        return R.ok(ctcdPaymodeService.save(ctcdPaymode));
    }

    /**
     * 修改付款方式     @PreAuthorize("@pms.hasPermission('admin_ctcdpaymode_edit')" )
     * @param ctcdPaymode 付款方式
     * @return R
     */
    @ApiOperation(value = "修改付款方式", notes = "修改付款方式")
    @SysLog("修改付款方式" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdPaymode ctcdPaymode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdPaymode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdPaymode.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdPaymode.getId());
		List list = ctcdPaymodeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdPaymode> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdPaymode.getId());
        return R.ok(ctcdPaymodeService.update(ctcdPaymode,updateWrapper));
    }

    /**
     * 通过id删除付款方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除付款方式", notes = "通过id删除付款方式")
    @SysLog("通过id删除付款方式" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdpaymode_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdPaymodeService.removeById(id));
    }

}
