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
import com.pig4cloud.pigx.admin.entity.WeekPaperTo;
import com.pig4cloud.pigx.admin.service.WeekPaperToService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 周报汇报人、抄送人
 *
 * @author gaoxiao
 * @date 2020-06-18 15:29:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/weekpaperto" )
@Api(value = "weekpaperto", tags = "周报汇报人、抄送人管理")
public class WeekPaperToController {

    private final  WeekPaperToService weekPaperToService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param weekPaperTo 周报汇报人、抄送人
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getWeekPaperToPage(Page page, WeekPaperTo weekPaperTo) {
        return R.ok(weekPaperToService.page(page, Wrappers.query(weekPaperTo)));
    }


    /**
     * 通过id查询周报汇报人、抄送人
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(weekPaperToService.getById(id));
    }

    /**
     * 新增周报汇报人、抄送人
     * @param weekPaperTo 周报汇报人、抄送人
     * @return R
     */
    @ApiOperation(value = "新增周报汇报人、抄送人", notes = "新增周报汇报人、抄送人")
    @SysLog("新增周报汇报人、抄送人" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_weekpaperto_add')" )
    public R save(@RequestBody WeekPaperTo weekPaperTo) {
        return R.ok(weekPaperToService.save(weekPaperTo));
    }

/*
    */
/**
     * 修改周报汇报人、抄送人
     * @param weekPaperTo 周报汇报人、抄送人
     * @return R
     *//*

    @ApiOperation(value = "修改周报汇报人、抄送人", notes = "修改周报汇报人、抄送人")
    @SysLog("修改周报汇报人、抄送人" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_weekpaperto_edit')" )
    public R updateById(@RequestBody WeekPaperTo weekPaperTo) {
        return R.ok(weekPaperToService.updateById(weekPaperTo));
    }

    */
/**
     * 通过id删除周报汇报人、抄送人
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除周报汇报人、抄送人", notes = "通过id删除周报汇报人、抄送人")
    @SysLog("通过id删除周报汇报人、抄送人" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_weekpaperto_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(weekPaperToService.removeById(id));
    }
*/

}
