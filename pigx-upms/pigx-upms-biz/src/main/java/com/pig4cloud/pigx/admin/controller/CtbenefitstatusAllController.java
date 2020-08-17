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
import com.pig4cloud.pigx.admin.entity.CtbenefitstatusAll;
import com.pig4cloud.pigx.admin.service.CtbenefitstatusAllService;
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
 * @date 2020-07-21 15:54:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbenefitstatusall" )
@Api(value = "ctbenefitstatusall", tags = "管理")
public class CtbenefitstatusAllController {

    private final  CtbenefitstatusAllService ctbenefitstatusAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctbenefitstatusAll 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtbenefitstatusAllPage(Page page, CtbenefitstatusAll ctbenefitstatusAll) {
        return R.ok(ctbenefitstatusAllService.page(page, Wrappers.query(ctbenefitstatusAll)));
    }


    /**
     * 通过id查询
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) LocalDateTime term) {
        return R.ok(ctbenefitstatusAllService.getById(term));
    }

    /**
     * 新增
     * @param ctbenefitstatusAll 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctbenefitstatusall_add')" )
    public R save(@RequestBody CtbenefitstatusAll ctbenefitstatusAll) {
        return R.ok(ctbenefitstatusAllService.save(ctbenefitstatusAll));
    }

    /**
     * 修改
     * @param ctbenefitstatusAll 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctbenefitstatusall_edit')" )
    public R updateById(@RequestBody CtbenefitstatusAll ctbenefitstatusAll) {
        return R.ok(ctbenefitstatusAllService.updateById(ctbenefitstatusAll));
    }

    /**
     * 通过id删除
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_ctbenefitstatusall_del')" )
    public R removeById(@PathVariable LocalDateTime term) {
        return R.ok(ctbenefitstatusAllService.removeById(term));
    }

}
