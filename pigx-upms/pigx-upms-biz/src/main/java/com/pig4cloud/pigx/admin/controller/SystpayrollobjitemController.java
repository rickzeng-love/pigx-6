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
import com.pig4cloud.pigx.admin.entity.Systpayrollobjitem;
import com.pig4cloud.pigx.admin.service.SystpayrollobjitemService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资数据源字段
 *
 * @author gaoxiao
 * @date 2020-06-26 14:45:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollobjitem" )
@Api(value = "systpayrollobjitem", tags = "薪资数据源字段管理")
public class SystpayrollobjitemController {

    private final  SystpayrollobjitemService systpayrollobjitemService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollobjitem 薪资数据源字段
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollobjitemPage(Page page, Systpayrollobjitem systpayrollobjitem) {
        return R.ok(systpayrollobjitemService.page(page, Wrappers.query(systpayrollobjitem)));
    }


    /**
     * 通过id查询薪资数据源字段
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systpayrollobjitemService.getById(id));
    }

    /**
     * 新增薪资数据源字段
     * @param systpayrollobjitem 薪资数据源字段
     * @return R
     */
    @ApiOperation(value = "新增薪资数据源字段", notes = "新增薪资数据源字段")
    @SysLog("新增薪资数据源字段" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollobjitem_add')" )
    public R save(@RequestBody Systpayrollobjitem systpayrollobjitem) {
        return R.ok(systpayrollobjitemService.save(systpayrollobjitem));
    }
/*

    */
/**
     * 修改薪资数据源字段
     * @param systpayrollobjitem 薪资数据源字段
     * @return R
     *//*

    @ApiOperation(value = "修改薪资数据源字段", notes = "修改薪资数据源字段")
    @SysLog("修改薪资数据源字段" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollobjitem_edit')" )
    public R updateById(@RequestBody Systpayrollobjitem systpayrollobjitem) {
        return R.ok(systpayrollobjitemService.updateById(systpayrollobjitem));
    }

    */
/**
     * 通过id删除薪资数据源字段
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资数据源字段", notes = "通过id删除薪资数据源字段")
    @SysLog("通过id删除薪资数据源字段" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollobjitem_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systpayrollobjitemService.removeById(id));
    }
*/

}
