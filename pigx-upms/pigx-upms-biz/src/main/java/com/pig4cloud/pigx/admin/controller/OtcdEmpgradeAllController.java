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
import com.pig4cloud.pigx.admin.entity.OtcdEmpgradeAll;
import com.pig4cloud.pigx.admin.service.OtcdEmpgradeAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 职级历史
 *
 * @author gaoxiao
 * @date 2020-05-29 10:12:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdempgradeall" )
@Api(value = "otcdempgradeall", tags = "职级历史管理")
public class OtcdEmpgradeAllController {

    private final  OtcdEmpgradeAllService otcdEmpgradeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdEmpgradeAll 职级历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdEmpgradeAllPage(Page page, OtcdEmpgradeAll otcdEmpgradeAll) {
        return R.ok(otcdEmpgradeAllService.page(page, Wrappers.query(otcdEmpgradeAll)));
    }


    /**
     * 通过id查询职级历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdEmpgradeAllService.getById(id));
    }

    /**
     * 新增职级历史
     * @param otcdEmpgradeAll 职级历史
     * @return R
     */
    @ApiOperation(value = "新增职级历史", notes = "新增职级历史")
    @SysLog("新增职级历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdempgradeall_add')" )
    public R save(@RequestBody OtcdEmpgradeAll otcdEmpgradeAll) {
        return R.ok(otcdEmpgradeAllService.save(otcdEmpgradeAll));
    }

/*    *//**
     * 修改职级历史
     * @param otcdEmpgradeAll 职级历史
     * @return R
     *//*
    @ApiOperation(value = "修改职级历史", notes = "修改职级历史")
    @SysLog("修改职级历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdempgradeall_edit')" )
    public R updateById(@RequestBody OtcdEmpgradeAll otcdEmpgradeAll) {
        return R.ok(otcdEmpgradeAllService.updateById(otcdEmpgradeAll));
    }*/

/*
    */
/**
     * 通过id删除职级历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除职级历史", notes = "通过id删除职级历史")
    @SysLog("通过id删除职级历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdempgradeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdEmpgradeAllService.removeById(id));
    }
*/

}
