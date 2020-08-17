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
import com.pig4cloud.pigx.admin.entity.CtcdDecsumperiods;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdPayrollperiod;
import com.pig4cloud.pigx.admin.service.CtcdPayrollperiodService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资累计期间设置
 *
 * @author gaoxiao
 * @date 2020-07-15 14:05:07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdpayrollperiod" )
@Api(value = "ctcdpayrollperiod", tags = "薪资累计期间设置管理")
public class CtcdPayrollperiodController {

    private final  CtcdPayrollperiodService ctcdPayrollperiodService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdPayrollperiod 薪资累计期间设置
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdPayrollperiodPage(Page page, CtcdPayrollperiod ctcdPayrollperiod) {
        return R.ok(ctcdPayrollperiodService.page(page, Wrappers.query(ctcdPayrollperiod)));
    }
	/**
	 * 累计扣税期间查询所有
	 * @param ctcdPayrollperiod 薪资累计期间设置
	 * @return
	 */
	@ApiOperation(value = "薪资累计期间设置", notes = "薪资累计期间设置")
	@PostMapping("/getAllCtcdPayrollperiod" )
	public R getAllCtcdPayrollperiod(@RequestBody(required = false)  CtcdPayrollperiod ctcdPayrollperiod) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdPayrollperiod.setCorpcode(corpcode);
		if(StringUtils.isEmpty(ctcdPayrollperiod)){
			ctcdPayrollperiod = new CtcdPayrollperiod();
		}
		return R.ok(ctcdPayrollperiodService.list(Wrappers.query(ctcdPayrollperiod)));
	}

    /**
     * 通过id查询薪资累计期间设置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdPayrollperiodService.getById(id));
    }

    /**
     * 新增薪资累计期间设置
     * @param ctcdPayrollperiod 薪资累计期间设置
     * @return R
     */
    @ApiOperation(value = "新增薪资累计期间设置", notes = "新增薪资累计期间设置")
    @SysLog("新增薪资累计期间设置" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdpayrollperiod_add')" )
    public R save(@RequestBody CtcdPayrollperiod ctcdPayrollperiod) {
        return R.ok(ctcdPayrollperiodService.save(ctcdPayrollperiod));
    }

    /**
     * 修改薪资累计期间设置
     * @param ctcdPayrollperiod 薪资累计期间设置
     * @return R
     */
    @ApiOperation(value = "修改薪资累计期间设置", notes = "修改薪资累计期间设置")
    @SysLog("修改薪资累计期间设置" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdpayrollperiod_edit')" )
    public R updateById(@RequestBody CtcdPayrollperiod ctcdPayrollperiod) {
        return R.ok(ctcdPayrollperiodService.updateById(ctcdPayrollperiod));
    }

    /**
     * 通过id删除薪资累计期间设置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资累计期间设置", notes = "通过id删除薪资累计期间设置")
    @SysLog("通过id删除薪资累计期间设置" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdpayrollperiod_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdPayrollperiodService.removeById(id));
    }

}
