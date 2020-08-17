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
import com.pig4cloud.pigx.admin.entity.DailyPaperTo;
import com.pig4cloud.pigx.admin.service.DailyPaperToService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 日报汇报人、抄送人
 *
 * @author gaoxiao
 * @date 2020-06-18 15:29:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dailypaperto" )
@Api(value = "dailypaperto", tags = "日报汇报人、抄送人管理")
public class DailyPaperToController {

    private final  DailyPaperToService dailyPaperToService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param dailyPaperTo 日报汇报人、抄送人
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getDailyPaperToPage(Page page, DailyPaperTo dailyPaperTo) {
        return R.ok(dailyPaperToService.page(page, Wrappers.query(dailyPaperTo)));
    }


    /**
     * 通过id查询日报汇报人、抄送人
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(dailyPaperToService.getById(id));
    }

    /**
     * 新增日报汇报人、抄送人
     * @param dailyPaperTo 日报汇报人、抄送人
     * @return R
     */
    @ApiOperation(value = "新增日报汇报人、抄送人", notes = "新增日报汇报人、抄送人")
    @SysLog("新增日报汇报人、抄送人" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_dailypaperto_add')" )
    public R save(@RequestBody DailyPaperTo dailyPaperTo) {
        return R.ok(dailyPaperToService.save(dailyPaperTo));
    }

 /*   *//**
     * 修改日报汇报人、抄送人
     * @param dailyPaperTo 日报汇报人、抄送人
     * @return R
     *//*
    @ApiOperation(value = "修改日报汇报人、抄送人", notes = "修改日报汇报人、抄送人")
    @SysLog("修改日报汇报人、抄送人" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_dailypaperto_edit')" )
    public R updateById(@RequestBody DailyPaperTo dailyPaperTo) {
        return R.ok(dailyPaperToService.updateById(dailyPaperTo));
    }*/

/*
    */
/**
     * 通过id删除日报汇报人、抄送人
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除日报汇报人、抄送人", notes = "通过id删除日报汇报人、抄送人")
    @SysLog("通过id删除日报汇报人、抄送人" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_dailypaperto_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(dailyPaperToService.removeById(id));
    }
*/

}
