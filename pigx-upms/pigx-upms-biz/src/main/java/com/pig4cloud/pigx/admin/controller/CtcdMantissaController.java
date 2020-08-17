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
import com.pig4cloud.pigx.admin.entity.CtcdLataxrate;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdMantissa;
import com.pig4cloud.pigx.admin.service.CtcdMantissaService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 进位类型
 *
 * @author gaoxiao
 * @date 2020-08-12 11:37:07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdmantissa" )
@Api(value = "ctcdmantissa", tags = "进位类型管理")
public class CtcdMantissaController {

    private final  CtcdMantissaService ctcdMantissaService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdMantissa 进位类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdMantissaPage(Page page, CtcdMantissa ctcdMantissa) {
        return R.ok(ctcdMantissaService.page(page, Wrappers.query(ctcdMantissa)));
    }

	/**
	 * @param ctcdMantissa 进位类型查询所有
	 * @return
	 */
	@ApiOperation(value = "进位类型查询所有", notes = "进位类型查询所有")
	@PostMapping("/getAllCtcdMantissa" )
	public R getAllCtcdMantissa(@RequestBody(required = false) CtcdMantissa ctcdMantissa) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(ctcdMantissa)){
			ctcdMantissa = new CtcdMantissa();
		}
		return R.ok(ctcdMantissaService.list(Wrappers.query(ctcdMantissa)));
	}

    /**
     * 通过id查询进位类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdMantissaService.getById(id));
    }

    /**
     * 新增进位类型
     * @param ctcdMantissa 进位类型
     * @return R
     */
    @ApiOperation(value = "新增进位类型", notes = "新增进位类型")
    @SysLog("新增进位类型" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdmantissa_add')" )
    public R save(@RequestBody CtcdMantissa ctcdMantissa) {
        return R.ok(ctcdMantissaService.save(ctcdMantissa));
    }

    /**
     * 修改进位类型
     * @param ctcdMantissa 进位类型
     * @return R
     */
    @ApiOperation(value = "修改进位类型", notes = "修改进位类型")
    @SysLog("修改进位类型" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdmantissa_edit')" )
    public R updateById(@RequestBody CtcdMantissa ctcdMantissa) {
        return R.ok(ctcdMantissaService.updateById(ctcdMantissa));
    }

    /**
     * 通过id删除进位类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除进位类型", notes = "通过id删除进位类型")
    @SysLog("通过id删除进位类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdmantissa_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdMantissaService.removeById(id));
    }

}
