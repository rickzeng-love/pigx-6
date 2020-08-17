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
import com.pig4cloud.pigx.admin.entity.CtcdTaxrate;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdTaxratesum;
import com.pig4cloud.pigx.admin.service.CtcdTaxratesumService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 普通税率(累计)
 *
 * @author gaoxioa
 * @date 2020-07-14 19:11:15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdtaxratesum" )
@Api(value = "ctcdtaxratesum", tags = "普通税率(累计)管理")
public class CtcdTaxratesumController {

    private final  CtcdTaxratesumService ctcdTaxratesumService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdTaxratesum 普通税率(累计)
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdTaxratesumPage(Page page, CtcdTaxratesum ctcdTaxratesum) {
        return R.ok(ctcdTaxratesumService.page(page, Wrappers.query(ctcdTaxratesum)));
    }
	/**
	 * @param ctcdTaxratesum 普通税率查询所有
	 * @return
	 */
	@ApiOperation(value = "普通税率查询所有", notes = "普通税率查询所有")
	@PostMapping("/getAllCtcdTaxratesum" )
	public R getAllCtcdTaxratesum(@RequestBody(required = false) CtcdTaxratesum ctcdTaxratesum) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(ctcdTaxratesum)){
			ctcdTaxratesum = new CtcdTaxratesum();
		}
		return R.ok(ctcdTaxratesumService.list(Wrappers.query(ctcdTaxratesum)));
	}


    /**
     * 通过id查询普通税率(累计)
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdTaxratesumService.getById(id));
    }

    /**
     * 新增普通税率(累计)
     * @param ctcdTaxratesum 普通税率(累计)
     * @return R
     */
    @ApiOperation(value = "新增普通税率(累计)", notes = "新增普通税率(累计)")
    @SysLog("新增普通税率(累计)" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdtaxratesum_add')" )
    public R save(@RequestBody CtcdTaxratesum ctcdTaxratesum) {
        return R.ok(ctcdTaxratesumService.save(ctcdTaxratesum));
    }

    /**
     * 修改普通税率(累计)
     * @param ctcdTaxratesum 普通税率(累计)
     * @return R
     */
    @ApiOperation(value = "修改普通税率(累计)", notes = "修改普通税率(累计)")
    @SysLog("修改普通税率(累计)" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdtaxratesum_edit')" )
    public R updateById(@RequestBody CtcdTaxratesum ctcdTaxratesum) {
        return R.ok(ctcdTaxratesumService.updateById(ctcdTaxratesum));
    }

    /**
     * 通过id删除普通税率(累计)
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除普通税率(累计)", notes = "通过id删除普通税率(累计)")
    @SysLog("通过id删除普通税率(累计)" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdtaxratesum_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdTaxratesumService.removeById(id));
    }

}
