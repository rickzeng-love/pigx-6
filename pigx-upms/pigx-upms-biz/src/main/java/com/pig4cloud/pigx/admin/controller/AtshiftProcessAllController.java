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
import com.pig4cloud.pigx.admin.entity.AtshiftProcessAll;
import com.pig4cloud.pigx.admin.service.AtshiftProcessAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 排班期间历史
 *
 * @author gaoxiao
 * @date 2020-07-08 14:42:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftprocessall" )
@Api(value = "atshiftprocessall", tags = "排班期间历史管理")
public class AtshiftProcessAllController {

    private final  AtshiftProcessAllService atshiftProcessAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftProcessAll 排班期间历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftProcessAllPage(Page page, AtshiftProcessAll atshiftProcessAll) {
        return R.ok(atshiftProcessAllService.page(page, Wrappers.query(atshiftProcessAll)));
    }




    /**
     * 通过id查询排班期间历史
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(atshiftProcessAllService.getById(term));
    }

    /**
     * 新增排班期间历史
     * @param atshiftProcessAll 排班期间历史
     * @return Rs
     */
    @ApiOperation(value = "新增排班期间历史", notes = "新增排班期间历史")
    @SysLog("新增排班期间历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftprocessall_add')" )
    public R save(@RequestBody AtshiftProcessAll atshiftProcessAll) {
        return R.ok(atshiftProcessAllService.save(atshiftProcessAll));
    }

    /**
     * 修改排班期间历史
     * @param atshiftProcessAll 排班期间历史
     * @return R
     */
    @ApiOperation(value = "修改排班期间历史", notes = "修改排班期间历史")
    @SysLog("修改排班期间历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftprocessall_edit')" )
    public R updateById(@RequestBody AtshiftProcessAll atshiftProcessAll) {
        return R.ok(atshiftProcessAllService.updateById(atshiftProcessAll));
    }

    /**
     * 通过id删除排班期间历史
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除排班期间历史", notes = "通过id删除排班期间历史")
    @SysLog("通过id删除排班期间历史" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftprocessall_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(atshiftProcessAllService.removeById(term));
    }

}
