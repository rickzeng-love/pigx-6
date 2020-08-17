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
import com.pig4cloud.pigx.admin.entity.SystpayrollgroupprocessAll;
import com.pig4cloud.pigx.admin.service.SystpayrollgroupprocessAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资流程查看历史
 *
 * @author gaoxiao
 * @date 2020-06-13 10:12:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollgroupprocessall" )
@Api(value = "systpayrollgroupprocessall", tags = "薪资流程查看历史管理")
public class SystpayrollgroupprocessAllController {

    private final  SystpayrollgroupprocessAllService systpayrollgroupprocessAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollgroupprocessAll 薪资流程查看历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollgroupprocessAllPage(Page page, SystpayrollgroupprocessAll systpayrollgroupprocessAll) {
        return R.ok(systpayrollgroupprocessAllService.page(page, Wrappers.query(systpayrollgroupprocessAll)));
    }


    /**
     * 通过id查询薪资流程查看历史
     * @param gid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{gid}" )
    public R getById(@PathVariable("gid" ) Integer gid) {
        return R.ok(systpayrollgroupprocessAllService.getById(gid));
    }

    /**
     * 新增薪资流程查看历史
     * @param systpayrollgroupprocessAll 薪资流程查看历史
     * @return R
     */
    @ApiOperation(value = "新增薪资流程查看历史", notes = "新增薪资流程查看历史")
    @SysLog("新增薪资流程查看历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroupprocessall_add')" )
    public R save(@RequestBody SystpayrollgroupprocessAll systpayrollgroupprocessAll) {
        return R.ok(systpayrollgroupprocessAllService.save(systpayrollgroupprocessAll));
    }

/*
    */
/**
     * 修改薪资流程查看历史
     * @param systpayrollgroupprocessAll 薪资流程查看历史
     * @return R
     *//*

    @ApiOperation(value = "修改薪资流程查看历史", notes = "修改薪资流程查看历史")
    @SysLog("修改薪资流程查看历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroupprocessall_edit')" )
    public R updateById(@RequestBody SystpayrollgroupprocessAll systpayrollgroupprocessAll) {
        return R.ok(systpayrollgroupprocessAllService.updateById(systpayrollgroupprocessAll));
    }

    */
/**
     * 通过id删除薪资流程查看历史
     * @param gid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资流程查看历史", notes = "通过id删除薪资流程查看历史")
    @SysLog("通过id删除薪资流程查看历史" )
    @DeleteMapping("/{gid}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroupprocessall_del')" )
    public R removeById(@PathVariable Integer gid) {
        return R.ok(systpayrollgroupprocessAllService.removeById(gid));
    }
*/

}
