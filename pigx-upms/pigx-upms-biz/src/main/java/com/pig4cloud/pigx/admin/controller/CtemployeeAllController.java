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
import com.pig4cloud.pigx.admin.entity.CtemployeeAll;
import com.pig4cloud.pigx.admin.service.CtemployeeAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 
 *
 * @author shishengjie
 * @date 2020-07-21 15:58:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctemployeeall" )
@Api(value = "ctemployeeall", tags = "管理")
public class CtemployeeAllController {

    private final  CtemployeeAllService ctemployeeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctemployeeAll 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtemployeeAllPage(Page page, CtemployeeAll ctemployeeAll) {
        return R.ok(ctemployeeAllService.page(page, Wrappers.query(ctemployeeAll)));
    }


    /**
     * 通过id查询
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) LocalDateTime term) {
        return R.ok(ctemployeeAllService.getById(term));
    }

    /**
     * 新增
     * @param ctemployeeAll 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctemployeeall_add')" )
    public R save(@RequestBody CtemployeeAll ctemployeeAll) {
        return R.ok(ctemployeeAllService.save(ctemployeeAll));
    }

    /**
     * 修改
     * @param ctemployeeAll 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctemployeeall_edit')" )
    public R updateById(@RequestBody CtemployeeAll ctemployeeAll) {
        return R.ok(ctemployeeAllService.updateById(ctemployeeAll));
    }

    /**
     * 通过id删除
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_ctemployeeall_del')" )
    public R removeById(@PathVariable LocalDateTime term) {
        return R.ok(ctemployeeAllService.removeById(term));
    }

}
