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
import com.pig4cloud.pigx.admin.entity.OtcdAbilitylevel;
import com.pig4cloud.pigx.admin.service.OtcdAbilitylevelService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 能力级别
 *
 * @author gaoxiao
 * @date 2020-06-24 16:27:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdabilitylevel" )
@Api(value = "otcdabilitylevel", tags = "能力级别管理")
public class OtcdAbilitylevelController {

    private final  OtcdAbilitylevelService otcdAbilitylevelService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdAbilitylevel 能力级别
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdAbilitylevelPage(Page page, OtcdAbilitylevel otcdAbilitylevel) {
        return R.ok(otcdAbilitylevelService.page(page, Wrappers.query(otcdAbilitylevel)));
    }


    /**
     * 通过id查询能力级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdAbilitylevelService.getById(id));
    }

    /**
     * 新增能力级别
     * @param otcdAbilitylevel 能力级别
     * @return R
     */
    @ApiOperation(value = "新增能力级别", notes = "新增能力级别")
    @SysLog("新增能力级别" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdabilitylevel_add')" )
    public R save(@RequestBody OtcdAbilitylevel otcdAbilitylevel) {
        return R.ok(otcdAbilitylevelService.save(otcdAbilitylevel));
    }

    /**
     * 修改能力级别
     * @param otcdAbilitylevel 能力级别
     * @return R
     */
    @ApiOperation(value = "修改能力级别", notes = "修改能力级别")
    @SysLog("修改能力级别" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdabilitylevel_edit')" )
    public R updateById(@RequestBody OtcdAbilitylevel otcdAbilitylevel) {
        return R.ok(otcdAbilitylevelService.updateById(otcdAbilitylevel));
    }

    /**
     * 通过id删除能力级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除能力级别", notes = "通过id删除能力级别")
    @SysLog("通过id删除能力级别" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdabilitylevel_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdAbilitylevelService.removeById(id));
    }

}
