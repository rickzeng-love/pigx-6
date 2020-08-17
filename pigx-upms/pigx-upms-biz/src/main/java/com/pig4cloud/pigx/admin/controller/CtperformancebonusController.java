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
import com.pig4cloud.pigx.admin.entity.Ctperformancebonus;
import com.pig4cloud.pigx.admin.service.CtperformancebonusService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 绩效接口
 *
 * @author shishengjie
 * @date 2020-07-21 14:09:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctperformancebonus" )
@Api(value = "ctperformancebonus", tags = "绩效接口管理")
public class CtperformancebonusController {

    private final  CtperformancebonusService ctperformancebonusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctperformancebonus 绩效接口
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtperformancebonusPage(Page page, Ctperformancebonus ctperformancebonus) {
        return R.ok(ctperformancebonusService.page(page, Wrappers.query(ctperformancebonus)));
    }


    /**
     * 通过id查询绩效接口
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctperformancebonusService.getById(id));
    }

    /**
     * 新增绩效接口
     * @param ctperformancebonus 绩效接口
     * @return R
     */
    @ApiOperation(value = "新增绩效接口", notes = "新增绩效接口")
    @SysLog("新增绩效接口" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctperformancebonus_add')" )
    public R save(@RequestBody Ctperformancebonus ctperformancebonus) {
        return R.ok(ctperformancebonusService.save(ctperformancebonus));
    }

    /**
     * 修改绩效接口
     * @param ctperformancebonus 绩效接口
     * @return R
     */
    @ApiOperation(value = "修改绩效接口", notes = "修改绩效接口")
    @SysLog("修改绩效接口" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctperformancebonus_edit')" )
    public R updateById(@RequestBody Ctperformancebonus ctperformancebonus) {
        return R.ok(ctperformancebonusService.updateById(ctperformancebonus));
    }

    /**
     * 通过id删除绩效接口
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除绩效接口", notes = "通过id删除绩效接口")
    @SysLog("通过id删除绩效接口" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctperformancebonus_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctperformancebonusService.removeById(id));
    }

}
