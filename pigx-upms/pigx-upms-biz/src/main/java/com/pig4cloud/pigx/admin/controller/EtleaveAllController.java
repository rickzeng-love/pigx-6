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
import com.pig4cloud.pigx.admin.entity.EtleaveAll;
import com.pig4cloud.pigx.admin.service.EtleaveAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 退休离职历史表
 *
 * @author gaoxiao
 * @date 2020-04-10 20:51:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etleaveall" )
@Api(value = "etleaveall", tags = "退休离职历史表管理")
public class EtleaveAllController {

    private final  EtleaveAllService etleaveAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etleaveAll 退休离职历史表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtleaveAllPage(Page page, EtleaveAll etleaveAll) {
        return R.ok(etleaveAllService.page(page, Wrappers.query(etleaveAll)));
    }


    /**
     * 通过id查询退休离职历史表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etleaveAllService.getById(id));
    }

    /**
     * 新增退休离职历史表
     * @param etleaveAll 退休离职历史表
     * @return R
     */
    @ApiOperation(value = "新增退休离职历史表", notes = "新增退休离职历史表")
    @SysLog("新增退休离职历史表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etleaveall_add')" )
    public R save(@RequestBody EtleaveAll etleaveAll) {
        return R.ok(etleaveAllService.save(etleaveAll));
    }

/*
    */
/**
     * 修改退休离职历史表
     * @param etleaveAll 退休离职历史表
     * @return R
     *//*

    @ApiOperation(value = "修改退休离职历史表", notes = "修改退休离职历史表")
    @SysLog("修改退休离职历史表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etleaveall_edit')" )
    public R updateById(@RequestBody EtleaveAll etleaveAll) {
        return R.ok(etleaveAllService.updateById(etleaveAll));
    }

    */
/**
     * 通过id删除退休离职历史表
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除退休离职历史表", notes = "通过id删除退休离职历史表")
    @SysLog("通过id删除退休离职历史表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etleaveall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etleaveAllService.removeById(id));
    }
*/

}
