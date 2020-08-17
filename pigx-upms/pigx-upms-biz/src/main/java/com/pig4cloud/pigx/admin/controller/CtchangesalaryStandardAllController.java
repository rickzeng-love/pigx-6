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
import com.pig4cloud.pigx.admin.entity.CtchangesalaryStandardAll;
import com.pig4cloud.pigx.admin.service.CtchangesalaryStandardAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-07-28 11:53:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctchangesalarystandardall" )
@Api(value = "ctchangesalarystandardall", tags = "管理")
public class CtchangesalaryStandardAllController {

    private final  CtchangesalaryStandardAllService ctchangesalaryStandardAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctchangesalaryStandardAll 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtchangesalaryStandardAllPage(Page page, CtchangesalaryStandardAll ctchangesalaryStandardAll) {
        return R.ok(ctchangesalaryStandardAllService.page(page, Wrappers.query(ctchangesalaryStandardAll)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctchangesalaryStandardAllService.getById(id));
    }

    /**
     * 新增
     * @param ctchangesalaryStandardAll 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalarystandardall_add')" )
    public R save(@RequestBody CtchangesalaryStandardAll ctchangesalaryStandardAll) {
        return R.ok(ctchangesalaryStandardAllService.save(ctchangesalaryStandardAll));
    }

    /**
     * 修改
     * @param ctchangesalaryStandardAll 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalarystandardall_edit')" )
    public R updateById(@RequestBody CtchangesalaryStandardAll ctchangesalaryStandardAll) {
        return R.ok(ctchangesalaryStandardAllService.updateById(ctchangesalaryStandardAll));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalarystandardall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctchangesalaryStandardAllService.removeById(id));
    }

}
