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
import com.pig4cloud.pigx.admin.entity.OtcdDeptypeCommon;
import com.pig4cloud.pigx.admin.service.OtcdDeptypeCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 部门类型
 *
 * @author gaoxiao
 * @date 2020-07-10 14:00:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcddeptypecommon" )
@Api(value = "otcddeptypecommon", tags = "部门类型管理")
public class OtcdDeptypeCommonController {

    private final  OtcdDeptypeCommonService otcdDeptypeCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdDeptypeCommon 部门类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdDeptypeCommonPage(Page page, OtcdDeptypeCommon otcdDeptypeCommon) {
        return R.ok(otcdDeptypeCommonService.page(page, Wrappers.query(otcdDeptypeCommon)));
    }


    /**
     * 通过id查询部门类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdDeptypeCommonService.getById(id));
    }

    /**
     * 新增部门类型
     * @param otcdDeptypeCommon 部门类型
     * @return R
     */
    @ApiOperation(value = "新增部门类型", notes = "新增部门类型")
    @SysLog("新增部门类型" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcddeptypecommon_add')" )
    public R save(@RequestBody OtcdDeptypeCommon otcdDeptypeCommon) {
        return R.ok(otcdDeptypeCommonService.save(otcdDeptypeCommon));
    }

    /**
     * 修改部门类型
     * @param otcdDeptypeCommon 部门类型
     * @return R
     */
    @ApiOperation(value = "修改部门类型", notes = "修改部门类型")
    @SysLog("修改部门类型" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcddeptypecommon_edit')" )
    public R updateById(@RequestBody OtcdDeptypeCommon otcdDeptypeCommon) {
        return R.ok(otcdDeptypeCommonService.updateById(otcdDeptypeCommon));
    }

    /**
     * 通过id删除部门类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除部门类型", notes = "通过id删除部门类型")
    @SysLog("通过id删除部门类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcddeptypecommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdDeptypeCommonService.removeById(id));
    }

}
