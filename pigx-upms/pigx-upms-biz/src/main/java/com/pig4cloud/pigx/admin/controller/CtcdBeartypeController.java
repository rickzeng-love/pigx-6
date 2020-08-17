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
import com.pig4cloud.pigx.admin.entity.CtcdBenarea;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdBeartype;
import com.pig4cloud.pigx.admin.service.CtcdBeartypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 承担方式
 *
 * @author gaoxiao
 * @date 2020-06-10 13:59:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbeartype" )
@Api(value = "ctcdbeartype", tags = "承担方式管理")
public class CtcdBeartypeController {

    private final  CtcdBeartypeService ctcdBeartypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBeartype 承担方式
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBeartypePage(Page page, CtcdBeartype ctcdBeartype) {
        return R.ok(ctcdBeartypeService.page(page, Wrappers.query(ctcdBeartype)));
    }
	/**
	 * 承担方式查询所有
	 * @param ctcdBeartype 福利类型
	 * @return
	 */
	@ApiOperation(value = "承担方式查询所有", notes = "承担方式查询所有")
	@PostMapping("/getAllCtcdBeartype" )
	public R getAllCtcdBeartype(CtcdBeartype ctcdBeartype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		return R.ok(ctcdBeartypeService.list(Wrappers.query(ctcdBeartype)));
	}

    /**
     * 通过id查询承担方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBeartypeService.getById(id));
    }

    /**
     * 新增承担方式     @PreAuthorize("@pms.hasPermission('admin_ctcdbeartype_add')" )
     * @param ctcdBeartype 承担方式
     * @return R
     */
    @ApiOperation(value = "新增承担方式", notes = "新增承担方式")
    @SysLog("新增承担方式" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdBeartype ctcdBeartype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdBeartype.setCorpcode(corpcode);
		ctcdBeartype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdBeartype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBeartype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdBeartypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}

        return R.ok(ctcdBeartypeService.save(ctcdBeartype));
    }

    /**
     * 修改承担方式     @PreAuthorize("@pms.hasPermission('admin_ctcdbeartype_edit')" )
     * @param ctcdBeartype 承担方式
     * @return R
     */
    @ApiOperation(value = "修改承担方式", notes = "修改承担方式")
    @SysLog("修改承担方式" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBeartype ctcdBeartype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdBeartype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBeartype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdBeartype.getId());
		List list = ctcdBeartypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdBeartype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdBeartype.getId());
        return R.ok(ctcdBeartypeService.update(ctcdBeartype,updateWrapper));
    }

    /**
     * 通过id删除承担方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除承担方式", notes = "通过id删除承担方式")
    @SysLog("通过id删除承担方式" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbeartype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBeartypeService.removeById(id));
    }

}
