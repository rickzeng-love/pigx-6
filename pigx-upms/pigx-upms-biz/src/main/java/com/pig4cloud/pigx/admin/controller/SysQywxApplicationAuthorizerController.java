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
import com.pig4cloud.pigx.admin.entity.SysQywxApplicationAuthorizer;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationAuthorizerService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 企业微信第三方应用开发接入授权企业信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:39:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysqywxapplicationauthorizer" )
@Api(value = "sysqywxapplicationauthorizer", tags = "企业微信第三方应用开发接入授权企业信息管理")
public class SysQywxApplicationAuthorizerController {

    private final  SysQywxApplicationAuthorizerService sysQywxApplicationAuthorizerService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysQywxApplicationAuthorizer 企业微信第三方应用开发接入授权企业信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSysQywxApplicationAuthorizerPage(Page page, SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer) {
        return R.ok(sysQywxApplicationAuthorizerService.page(page, Wrappers.query(sysQywxApplicationAuthorizer)));
    }


    /**
     * 通过id查询企业微信第三方应用开发接入授权企业信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(sysQywxApplicationAuthorizerService.getById(id));
    }

    /**
     * 新增企业微信第三方应用开发接入授权企业信息
     * @param sysQywxApplicationAuthorizer 企业微信第三方应用开发接入授权企业信息
     * @return R
     */
    @ApiOperation(value = "新增企业微信第三方应用开发接入授权企业信息", notes = "新增企业微信第三方应用开发接入授权企业信息")
    @SysLog("新增企业微信第三方应用开发接入授权企业信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_sysqywxapplicationauthorizer_add')" )
    public R save(@RequestBody SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer) {
        return R.ok(sysQywxApplicationAuthorizerService.save(sysQywxApplicationAuthorizer));
    }

    /**
     * 修改企业微信第三方应用开发接入授权企业信息
     * @param sysQywxApplicationAuthorizer 企业微信第三方应用开发接入授权企业信息
     * @return R
     */
    @ApiOperation(value = "修改企业微信第三方应用开发接入授权企业信息", notes = "修改企业微信第三方应用开发接入授权企业信息")
    @SysLog("修改企业微信第三方应用开发接入授权企业信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_sysqywxapplicationauthorizer_edit')" )
    public R updateById(@RequestBody SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer) {
        return R.ok(sysQywxApplicationAuthorizerService.updateById(sysQywxApplicationAuthorizer));
    }

    /**
     * 通过id删除企业微信第三方应用开发接入授权企业信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除企业微信第三方应用开发接入授权企业信息", notes = "通过id删除企业微信第三方应用开发接入授权企业信息")
    @SysLog("通过id删除企业微信第三方应用开发接入授权企业信息" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_sysqywxapplicationauthorizer_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysQywxApplicationAuthorizerService.removeById(id));
    }

}
