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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systdistrict;
import com.pig4cloud.pigx.admin.service.SystdistrictService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 区县
 *
 * @author gaoxiao
 * @date 2020-07-13 17:24:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systdistrict" )
@Api(value = "systdistrict", tags = "区县管理")
public class SystdistrictController {

    private final  SystdistrictService systdistrictService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systdistrict 区县
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystdistrictPage(Page page, Systdistrict systdistrict) {
        return R.ok(systdistrictService.page(page, Wrappers.query(systdistrict)));
    }

	/**
	 * 查询所有
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllSystdistrict" )
	public R getAllSystdistrict() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		return R.ok(systdistrictService.list());
	}
    /**
     * 通过id查询区县
     * @param code id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{code}" )
    public R getById(@PathVariable("code" ) Integer code) {
        return R.ok(systdistrictService.getById(code));
    }

    /**
     * 新增区县
     * @param systdistrict 区县
     * @return R
     */
    @ApiOperation(value = "新增区县", notes = "新增区县")
    @SysLog("新增区县" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systdistrict_add')" )
    public R save(@RequestBody Systdistrict systdistrict) {
        return R.ok(systdistrictService.save(systdistrict));
    }

    /**
     * 修改区县
     * @param systdistrict 区县
     * @return R
     */
    @ApiOperation(value = "修改区县", notes = "修改区县")
    @SysLog("修改区县" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systdistrict_edit')" )
    public R updateById(@RequestBody Systdistrict systdistrict) {
        return R.ok(systdistrictService.updateById(systdistrict));
    }

    /**
     * 通过id删除区县
     * @param code id
     * @return R
     */
    @ApiOperation(value = "通过id删除区县", notes = "通过id删除区县")
    @SysLog("通过id删除区县" )
    @DeleteMapping("/{code}" )
    @PreAuthorize("@pms.hasPermission('admin_systdistrict_del')" )
    public R removeById(@PathVariable Integer code) {
        return R.ok(systdistrictService.removeById(code));
    }

}
