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
import com.pig4cloud.pigx.admin.entity.CtcdBentype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdBenarea;
import com.pig4cloud.pigx.admin.service.CtcdBenareaService;
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
 * 福利地区
 *
 * @author gaoxiao
 * @date 2020-06-10 14:52:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbenarea" )
@Api(value = "ctcdbenarea", tags = "福利地区管理")
public class CtcdBenareaController {

    private final  CtcdBenareaService ctcdBenareaService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBenarea 福利地区
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBenareaPage(Page page, CtcdBenarea ctcdBenarea) {
        return R.ok(ctcdBenareaService.page(page, Wrappers.query(ctcdBenarea)));
    }

	/**
	 * 福利地区查询所有
	 * @param ctcdBenarea 福利类型
	 * @return
	 */
	@ApiOperation(value = "福利地区查询所有", notes = "福利地区查询所有")
	@PostMapping("/getAllCtcdBenarea" )
	public R getAllCtcdBentype(@RequestBody(required = false) CtcdBenarea ctcdBenarea) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(ctcdBenarea)){
			ctcdBenarea = new CtcdBenarea();
		}
		QueryWrapper<CtcdBenarea> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = ctcdBenarea.getTitle();
		if(name!=null && name!=""){
			queryWrapper.like("title",ctcdBenarea.getTitle());
		}
		return R.ok(ctcdBenareaService.list(queryWrapper));
	}

    /**
     * 通过id查询福利地区
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBenareaService.getById(id));
    }

    /**
     * 新增福利地区
     * @param ctcdBenarea 福利地区     @PostMapping
	 *     @PreAuthorize("@pms.hasPermission('admin_ctcdbenarea_add')" )
     * @return R
     */
    @ApiOperation(value = "新增福利地区", notes = "新增福利地区")
    @SysLog("新增福利地区" )
	@PostMapping("/save")
    public R save(@RequestBody CtcdBenarea ctcdBenarea) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdBenarea.setCoprcode(corpcode);
		ctcdBenarea.setCorpcode(corpcode);
		ctcdBenarea.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdBenarea> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBenarea.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdBenareaService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdBenareaService.save(ctcdBenarea));
    }

    /**
     * 修改福利地区
     * @param ctcdBenarea 福利地区      @PreAuthorize("@pms.hasPermission('admin_ctcdbenarea_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改福利地区", notes = "修改福利地区")
    @SysLog("修改福利地区" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBenarea ctcdBenarea) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdBenarea> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBenarea.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdBenarea.getId());
		List list = ctcdBenareaService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdBenarea> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdBenarea.getId());
        return R.ok(ctcdBenareaService.update(ctcdBenarea,updateWrapper));
    }

    /**
     * 通过id删除福利地区
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除福利地区", notes = "通过id删除福利地区")
    @SysLog("通过id删除福利地区" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbenarea_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBenareaService.removeById(id));
    }

}
