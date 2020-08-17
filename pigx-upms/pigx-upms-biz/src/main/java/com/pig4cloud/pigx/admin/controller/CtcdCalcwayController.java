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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.CtcdBentype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdCalcway;
import com.pig4cloud.pigx.admin.service.CtcdCalcwayService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资核算方式
 *
 * @author gaoxiao
 * @date 2020-06-12 16:53:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdcalcway" )
@Api(value = "ctcdcalcway", tags = "薪资核算方式管理")
public class CtcdCalcwayController {

    private final  CtcdCalcwayService ctcdCalcwayService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdCalcway 薪资核算方式
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdCalcwayPage(Page page, CtcdCalcway ctcdCalcway) {
        return R.ok(ctcdCalcwayService.page(page, Wrappers.query(ctcdCalcway)));
    }
	/**
	 * 薪资核算方式查询所有
	 * @param ctcdCalcway 薪资核算方式
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdCalcway" )
	public R getAllCtcdBentype(CtcdCalcway ctcdCalcway) {

		return R.ok(ctcdCalcwayService.list( Wrappers.query(ctcdCalcway)));
	}

    /**
     * 通过id查询薪资核算方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdCalcwayService.getById(id));
    }

    /**
     * 新增薪资核算方式     @PreAuthorize("@pms.hasPermission('admin_ctcdcalcway_add')" )
     * @param ctcdCalcway 薪资核算方式
     * @return R
     */
    @ApiOperation(value = "新增薪资核算方式", notes = "新增薪资核算方式")
    @SysLog("新增薪资核算方式" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdCalcway ctcdCalcway) {
		QueryWrapper<CtcdCalcway> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdCalcway.getTitle());
		List list = ctcdCalcwayService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdCalcwayService.save(ctcdCalcway));
    }

    /**
     * 修改薪资核算方式     @PreAuthorize("@pms.hasPermission('admin_ctcdcalcway_edit')" )
     * @param ctcdCalcway 薪资核算方式
     * @return R
     */
    @ApiOperation(value = "修改薪资核算方式", notes = "修改薪资核算方式")
    @SysLog("修改薪资核算方式" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdCalcway ctcdCalcway) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdCalcway> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdCalcway.getTitle());
		queryWrapper.ne("id",ctcdCalcway.getId());
		List list = ctcdCalcwayService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
    	return R.ok(ctcdCalcwayService.updateById(ctcdCalcway));
    }

    /**
     * 通过id删除薪资核算方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资核算方式", notes = "通过id删除薪资核算方式")
    @SysLog("通过id删除薪资核算方式" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdcalcway_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdCalcwayService.removeById(id));
    }

}
