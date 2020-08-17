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
import com.pig4cloud.pigx.admin.entity.Etevent;
import com.pig4cloud.pigx.admin.service.EteventService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 时间记录
 *
 * @author gaoxiao
 * @date 2020-08-06 09:53:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etevent" )
@Api(value = "etevent", tags = "时间记录管理")
public class EteventController {

    private final  EteventService eteventService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etevent 时间记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEteventPage(Page page, Etevent etevent) {
        return R.ok(eteventService.page(page, Wrappers.query(etevent)));
    }


    /**
     * 通过id查询时间记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(eteventService.getById(id));
    }

    /**
     * 新增时间记录
     * @param etevent 时间记录
     * @return R
     */
    @ApiOperation(value = "新增时间记录", notes = "新增时间记录")
    @SysLog("新增时间记录" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etevent_add')" )
    public R save(@RequestBody Etevent etevent) {
        return R.ok(eteventService.save(etevent));
    }

    /**
     * 修改时间记录
     * @param etevent 时间记录
     * @return R
     */
    @ApiOperation(value = "修改时间记录", notes = "修改时间记录")
    @SysLog("修改时间记录" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etevent_edit')" )
    public R updateById(@RequestBody Etevent etevent) {
        return R.ok(eteventService.updateById(etevent));
    }

    /**
     * 通过id删除时间记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除时间记录", notes = "通过id删除时间记录")
    @SysLog("通过id删除时间记录" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etevent_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(eteventService.removeById(id));
    }

}
