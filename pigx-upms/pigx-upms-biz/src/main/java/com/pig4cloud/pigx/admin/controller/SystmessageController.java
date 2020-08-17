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
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.service.SystmessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 系统错误提示表
 *
 * @author gaoxiao
 * @date 2020-05-30 17:57:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systmessage" )
@Api(value = "systmessage", tags = "系统错误提示表管理")
public class SystmessageController {

    private final  SystmessageService systmessageService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systmessage 系统错误提示表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystmessagePage(Page page, Systmessage systmessage) {
        return R.ok(systmessageService.page(page, Wrappers.query(systmessage)));
    }


    /**
     * 通过id查询系统错误提示表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systmessageService.getById(id));
    }

    /**
     * 新增系统错误提示表
     * @param systmessage 系统错误提示表
     * @return R
     */
    @ApiOperation(value = "新增系统错误提示表", notes = "新增系统错误提示表")
    @SysLog("新增系统错误提示表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systmessage_add')" )
    public R save(@RequestBody Systmessage systmessage) {
        return R.ok(systmessageService.save(systmessage));
    }

    /**
     * 修改系统错误提示表
     * @param systmessage 系统错误提示表
     * @return R
     */
    @ApiOperation(value = "修改系统错误提示表", notes = "修改系统错误提示表")
    @SysLog("修改系统错误提示表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systmessage_edit')" )
    public R updateById(@RequestBody Systmessage systmessage) {
        return R.ok(systmessageService.updateById(systmessage));
    }

    /**
     * 通过id删除系统错误提示表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除系统错误提示表", notes = "通过id删除系统错误提示表")
    @SysLog("通过id删除系统错误提示表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systmessage_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systmessageService.removeById(id));
    }

}
