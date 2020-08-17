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
import com.pig4cloud.pigx.admin.entity.MonthPaperTo;
import com.pig4cloud.pigx.admin.service.MonthPaperToService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 月报汇报人、抄送人
 *
 * @author gaoxiao
 * @date 2020-06-18 15:29:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monthpaperto" )
@Api(value = "monthpaperto", tags = "月报汇报人、抄送人管理")
public class MonthPaperToController {

    private final  MonthPaperToService monthPaperToService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param monthPaperTo 月报汇报人、抄送人
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getMonthPaperToPage(Page page, MonthPaperTo monthPaperTo) {
        return R.ok(monthPaperToService.page(page, Wrappers.query(monthPaperTo)));
    }


    /**
     * 通过id查询月报汇报人、抄送人
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(monthPaperToService.getById(id));
    }

    /**
     * 新增月报汇报人、抄送人
     * @param monthPaperTo 月报汇报人、抄送人
     * @return R
     */
    @ApiOperation(value = "新增月报汇报人、抄送人", notes = "新增月报汇报人、抄送人")
    @SysLog("新增月报汇报人、抄送人" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_monthpaperto_add')" )
    public R save(@RequestBody MonthPaperTo monthPaperTo) {
        return R.ok(monthPaperToService.save(monthPaperTo));
    }

/*
    */
/**
     * 修改月报汇报人、抄送人
     * @param monthPaperTo 月报汇报人、抄送人
     * @return R
     *//*

    @ApiOperation(value = "修改月报汇报人、抄送人", notes = "修改月报汇报人、抄送人")
    @SysLog("修改月报汇报人、抄送人" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_monthpaperto_edit')" )
    public R updateById(@RequestBody MonthPaperTo monthPaperTo) {
        return R.ok(monthPaperToService.updateById(monthPaperTo));
    }

    */
/**
     * 通过id删除月报汇报人、抄送人
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除月报汇报人、抄送人", notes = "通过id删除月报汇报人、抄送人")
    @SysLog("通过id删除月报汇报人、抄送人" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_monthpaperto_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(monthPaperToService.removeById(id));
    }
*/

}
