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
import com.pig4cloud.pigx.admin.entity.CtcdSalarytype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdSicktype;
import com.pig4cloud.pigx.admin.service.CtcdSicktypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 病假类型
 *
 * @author gaoxiao
 * @date 2020-06-12 17:17:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdsicktype" )
@Api(value = "ctcdsicktype", tags = "病假类型管理")
public class CtcdSicktypeController {

    private final  CtcdSicktypeService ctcdSicktypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdSicktype 病假类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdSicktypePage(Page page, CtcdSicktype ctcdSicktype) {
        return R.ok(ctcdSicktypeService.page(page, Wrappers.query(ctcdSicktype)));
    }

	/**
	 * 病假类型
	 * @param ctcdSicktype
	 * @ctcdSicktype
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdSicktype" )
	public R getAllCtcdSicktype( CtcdSicktype ctcdSicktype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdSicktype.setCorpcode(corpcode);
		return R.ok(ctcdSicktypeService.list(Wrappers.query(ctcdSicktype)));
	}
    /**
     * 通过id查询病假类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdSicktypeService.getById(id));
    }

    /**
     * 新增病假类型     @PreAuthorize("@pms.hasPermission('admin_ctcdsicktype_add')" )
     * @param ctcdSicktype 病假类型
     * @return R
     */
    @ApiOperation(value = "新增病假类型", notes = "新增病假类型")
    @SysLog("新增病假类型" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdSicktype ctcdSicktype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdSicktype.setCorpcode(corpcode);
		ctcdSicktype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdSicktype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSicktype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdSicktypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdSicktypeService.save(ctcdSicktype));
    }

    /**
     * 修改病假类型 	@PreAuthorize("@pms.hasPermission('admin_ctcdsicktype_edit')" )
     * @param ctcdSicktype 病假类型
     * @return R
     */
    @ApiOperation(value = "修改病假类型", notes = "修改病假类型")
    @SysLog("修改病假类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdSicktype ctcdSicktype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdSicktype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdSicktype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdSicktype.getId());
		List list = ctcdSicktypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdSicktype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdSicktype.getId());
        return R.ok(ctcdSicktypeService.update(ctcdSicktype,updateWrapper));
    }

    /**
     * 通过id删除病假类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除病假类型", notes = "通过id删除病假类型")
    @SysLog("通过id删除病假类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdsicktype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdSicktypeService.removeById(id));
    }

}
