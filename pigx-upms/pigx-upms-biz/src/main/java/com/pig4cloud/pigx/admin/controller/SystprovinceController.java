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
import com.pig4cloud.pigx.admin.entity.EtbgFamily;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systprovince;
import com.pig4cloud.pigx.admin.service.SystprovinceService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 省份
 *
 * @author gaoxiao
 * @date 2020-07-13 17:27:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systprovince" )
@Api(value = "systprovince", tags = "省份管理")
public class SystprovinceController {

    private final  SystprovinceService systprovinceService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systprovince 省份
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystprovincePage(Page page, Systprovince systprovince) {
        return R.ok(systprovinceService.page(page, Wrappers.query(systprovince)));
    }


    /**
     * 通过id查询省份
     * @param code id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{code}" )
    public R getById(@PathVariable("code" ) Integer code) {
        return R.ok(systprovinceService.getById(code));
    }
	/**
	 * 查询所有
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllSystprovince" )
	public R getAllSystprovince() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		return R.ok(systprovinceService.list());
	}
    /**
     * 新增省份
     * @param systprovince 省份
     * @return R
     */
    @ApiOperation(value = "新增省份", notes = "新增省份")
    @SysLog("新增省份" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systprovince_add')" )
    public R save(@RequestBody Systprovince systprovince) {
        return R.ok(systprovinceService.save(systprovince));
    }

    /**
     * 修改省份
     * @param systprovince 省份
     * @return R
     */
    @ApiOperation(value = "修改省份", notes = "修改省份")
    @SysLog("修改省份" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systprovince_edit')" )
    public R updateById(@RequestBody Systprovince systprovince) {
        return R.ok(systprovinceService.updateById(systprovince));
    }

    /**
     * 通过id删除省份
     * @param code id
     * @return R
     */
    @ApiOperation(value = "通过id删除省份", notes = "通过id删除省份")
    @SysLog("通过id删除省份" )
    @DeleteMapping("/{code}" )
    @PreAuthorize("@pms.hasPermission('admin_systprovince_del')" )
    public R removeById(@PathVariable Integer code) {
        return R.ok(systprovinceService.removeById(code));
    }

}
