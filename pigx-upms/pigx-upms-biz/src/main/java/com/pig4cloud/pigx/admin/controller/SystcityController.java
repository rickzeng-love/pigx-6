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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systcity;
import com.pig4cloud.pigx.admin.service.SystcityService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 城市
 *
 * @author gaoxiao
 * @date 2020-07-13 17:23:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systcity" )
@Api(value = "systcity", tags = "城市管理")
public class SystcityController {

    private final  SystcityService systcityService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systcity 城市
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystcityPage(Page page, Systcity systcity) {
        return R.ok(systcityService.page(page, Wrappers.query(systcity)));
    }
	/**
	 * 查询所有
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllSystcity" )
	public R getAllSystcity() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		return R.ok(systcityService.list());
	}

    /**
     * 通过id查询城市
     * @param code id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{code}" )
    public R getById(@PathVariable("code" ) Integer code) {
        return R.ok(systcityService.getById(code));
    }


	/**
	 * 通过省查询市
	 * @param code id
	 * @return R
	 */
	@ApiOperation(value = "通过省查询市", notes = "通过省查询市")
	@GetMapping("/city/{code}" )
	public R getCityList(@PathVariable("code" ) Integer code) {
		QueryWrapper<Systcity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("left(code,2)",code.toString().substring(0,2));
		return R.ok(systcityService.list(queryWrapper));
	}


	/**
     * 新增城市
     * @param systcity 城市
     * @return R
     */
    @ApiOperation(value = "新增城市", notes = "新增城市")
    @SysLog("新增城市" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systcity_add')" )
    public R save(@RequestBody Systcity systcity) {
        return R.ok(systcityService.save(systcity));
    }

    /**
     * 修改城市
     * @param systcity 城市
     * @return R
     */
    @ApiOperation(value = "修改城市", notes = "修改城市")
    @SysLog("修改城市" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systcity_edit')" )
    public R updateById(@RequestBody Systcity systcity) {
        return R.ok(systcityService.updateById(systcity));
    }

    /**
     * 通过id删除城市
     * @param code id
     * @return R
     */
    @ApiOperation(value = "通过id删除城市", notes = "通过id删除城市")
    @SysLog("通过id删除城市" )
    @DeleteMapping("/{code}" )
    @PreAuthorize("@pms.hasPermission('admin_systcity_del')" )
    public R removeById(@PathVariable Integer code) {
        return R.ok(systcityService.removeById(code));
    }

}
