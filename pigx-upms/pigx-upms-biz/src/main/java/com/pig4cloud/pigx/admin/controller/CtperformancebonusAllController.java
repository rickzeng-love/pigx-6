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
import com.pig4cloud.pigx.admin.entity.CtperformancebonusAll;
import com.pig4cloud.pigx.admin.service.CtperformancebonusAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 绩效接口历史
 *
 * @author shishengjie
 * @date 2020-07-21 14:16:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctperformancebonusall" )
@Api(value = "ctperformancebonusall", tags = "绩效接口历史管理")
public class CtperformancebonusAllController {

    private final  CtperformancebonusAllService ctperformancebonusAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctperformancebonusAll 绩效接口历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtperformancebonusAllPage(Page page, CtperformancebonusAll ctperformancebonusAll) {
        return R.ok(ctperformancebonusAllService.page(page, Wrappers.query(ctperformancebonusAll)));
    }


    /**
     * 通过id查询绩效接口历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctperformancebonusAllService.getById(id));
    }

    /**
     * 新增绩效接口历史
     * @param ctperformancebonusAll 绩效接口历史
     * @return R
     */
    @ApiOperation(value = "新增绩效接口历史", notes = "新增绩效接口历史")
    @SysLog("新增绩效接口历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctperformancebonusall_add')" )
    public R save(@RequestBody CtperformancebonusAll ctperformancebonusAll) {
        return R.ok(ctperformancebonusAllService.save(ctperformancebonusAll));
    }

    /**
     * 修改绩效接口历史
     * @param ctperformancebonusAll 绩效接口历史
     * @return R
     */
    @ApiOperation(value = "修改绩效接口历史", notes = "修改绩效接口历史")
    @SysLog("修改绩效接口历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctperformancebonusall_edit')" )
    public R updateById(@RequestBody CtperformancebonusAll ctperformancebonusAll) {
        return R.ok(ctperformancebonusAllService.updateById(ctperformancebonusAll));
    }

    /**
     * 通过id删除绩效接口历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除绩效接口历史", notes = "通过id删除绩效接口历史")
    @SysLog("通过id删除绩效接口历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctperformancebonusall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctperformancebonusAllService.removeById(id));
    }

}
