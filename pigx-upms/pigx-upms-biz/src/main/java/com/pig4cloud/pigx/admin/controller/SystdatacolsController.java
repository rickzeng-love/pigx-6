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
import com.pig4cloud.pigx.admin.entity.Systdatacols;
import com.pig4cloud.pigx.admin.service.SystdatacolsService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 列
 *
 * @author gaoxiao
 * @date 2020-06-26 13:39:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systdatacols" )
@Api(value = "systdatacols", tags = "列管理")
public class SystdatacolsController {

    private final  SystdatacolsService systdatacolsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systdatacols 列
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystdatacolsPage(Page page, Systdatacols systdatacols) {
        return R.ok(systdatacolsService.page(page, Wrappers.query(systdatacols)));
    }


    /**
     * 通过id查询列
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systdatacolsService.getById(id));
    }

    /**
     * 新增列
     * @param systdatacols 列
     * @return R
     */
    @ApiOperation(value = "新增列", notes = "新增列")
    @SysLog("新增列" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systdatacols_add')" )
    public R save(@RequestBody Systdatacols systdatacols) {
        return R.ok(systdatacolsService.save(systdatacols));
    }

    /**
     * 修改列
     * @param systdatacols 列
     * @return R
     */
    @ApiOperation(value = "修改列", notes = "修改列")
    @SysLog("修改列" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systdatacols_edit')" )
    public R updateById(@RequestBody Systdatacols systdatacols) {
        return R.ok(systdatacolsService.updateById(systdatacols));
    }

    /**
     * 通过id删除列
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除列", notes = "通过id删除列")
    @SysLog("通过id删除列" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systdatacols_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systdatacolsService.removeById(id));
    }

}
