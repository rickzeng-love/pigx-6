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
import com.pig4cloud.pigx.admin.entity.AtshiftDay;
import com.pig4cloud.pigx.admin.service.AtshiftDayService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author shishengjie
 * @date 2020-07-22 11:18:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftday" )
@Api(value = "atshiftday", tags = "管理")
public class AtshiftDayController {

    private final  AtshiftDayService atshiftDayService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftDay 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftDayPage(Page page, AtshiftDay atshiftDay) {
        return R.ok(atshiftDayService.page(page, Wrappers.query(atshiftDay)));
    }


    /**
     * 通过id查询
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(atshiftDayService.getById(term));
    }

    /**
     * 新增
     * @param atshiftDay 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftday_add')" )
    public R save(@RequestBody AtshiftDay atshiftDay) {
        return R.ok(atshiftDayService.save(atshiftDay));
    }

    /**
     * 修改
     * @param atshiftDay 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftday_edit')" )
    public R updateById(@RequestBody AtshiftDay atshiftDay) {
        return R.ok(atshiftDayService.updateById(atshiftDay));
    }

    /**
     * 通过id删除
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftday_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(atshiftDayService.removeById(term));
    }

}
