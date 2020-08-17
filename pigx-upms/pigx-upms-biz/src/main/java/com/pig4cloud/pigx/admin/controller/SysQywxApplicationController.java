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
import com.pig4cloud.pigx.admin.entity.SysQywxApplication;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 企业微信第三方应用信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:38:59
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/sysqywxapplication" )
@Api(value = "sysqywxapplication", tags = "企业微信第三方应用信息管理")
public class SysQywxApplicationController {

    private final  SysQywxApplicationService sysQywxApplicationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysQywxApplication 企业微信第三方应用信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSysQywxApplicationPage(Page page, SysQywxApplication sysQywxApplication) {
        return R.ok(sysQywxApplicationService.page(page, Wrappers.query(sysQywxApplication)));
    }

    /**
     * 通过id查询企业微信第三方应用信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(sysQywxApplicationService.getById(id));
    }

    /**
     * 新增企业微信第三方应用信息
     * @param sysQywxApplication 企业微信第三方应用信息
     * @return R
     */
    @ApiOperation(value = "新增企业微信第三方应用信息", notes = "新增企业微信第三方应用信息")
    @SysLog("新增企业微信第三方应用信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_sysqywxapplication_add')" )
    public R save(@RequestBody SysQywxApplication sysQywxApplication) {
        return R.ok(sysQywxApplicationService.save(sysQywxApplication));
    }

    /**
     * 修改企业微信第三方应用信息
     * @param sysQywxApplication 企业微信第三方应用信息
     * @return R
     */
    @ApiOperation(value = "修改企业微信第三方应用信息", notes = "修改企业微信第三方应用信息")
    @SysLog("修改企业微信第三方应用信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_sysqywxapplication_edit')" )
    public R updateById(@RequestBody SysQywxApplication sysQywxApplication) {
        return R.ok(sysQywxApplicationService.updateById(sysQywxApplication));
    }

    /**
     * 通过id删除企业微信第三方应用信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除企业微信第三方应用信息", notes = "通过id删除企业微信第三方应用信息")
    @SysLog("通过id删除企业微信第三方应用信息" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_sysqywxapplication_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysQywxApplicationService.removeById(id));
    }

}
