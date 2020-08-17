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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.CtcdCostcenter;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdLataxrate;
import com.pig4cloud.pigx.admin.service.CtcdLataxrateService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 劳务税率
 *
 * @author gaoxioa
 * @date 2020-07-14 19:13:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdlataxrate" )
@Api(value = "ctcdlataxrate", tags = "劳务税率管理")
public class CtcdLataxrateController {

    private final  CtcdLataxrateService ctcdLataxrateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdLataxrate 劳务税率
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdLataxratePage(Page page, CtcdLataxrate ctcdLataxrate) {
        return R.ok(ctcdLataxrateService.page(page, Wrappers.query(ctcdLataxrate)));
    }

	/**
	 * @param ctcdLataxrate 劳务税率查询所有
	 * @return
	 */
	@ApiOperation(value = "劳务税率查询所有", notes = "劳务税率查询所有")
	@PostMapping("/getAllCtcdLataxrate" )
	public R getAllCtcdCostcenter(@RequestBody(required = false) CtcdLataxrate ctcdLataxrate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(ctcdLataxrate)){
			ctcdLataxrate = new CtcdLataxrate();
		}
		return R.ok(ctcdLataxrateService.list(Wrappers.query(ctcdLataxrate)));
	}

    /**
     * 通过id查询劳务税率
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdLataxrateService.getById(id));
    }

    /**
     * 新增劳务税率
     * @param ctcdLataxrate 劳务税率
     * @return R
     */
    @ApiOperation(value = "新增劳务税率", notes = "新增劳务税率")
    @SysLog("新增劳务税率" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdlataxrate_add')" )
    public R save(@RequestBody CtcdLataxrate ctcdLataxrate) {
        return R.ok(ctcdLataxrateService.save(ctcdLataxrate));
    }

    /**
     * 修改劳务税率
     * @param ctcdLataxrate 劳务税率
     * @return R
     */
    @ApiOperation(value = "修改劳务税率", notes = "修改劳务税率")
    @SysLog("修改劳务税率" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdlataxrate_edit')" )
    public R updateById(@RequestBody CtcdLataxrate ctcdLataxrate) {
        return R.ok(ctcdLataxrateService.updateById(ctcdLataxrate));
    }

    /**
     * 通过id删除劳务税率
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除劳务税率", notes = "通过id删除劳务税率")
    @SysLog("通过id删除劳务税率" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdlataxrate_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdLataxrateService.removeById(id));
    }

}
