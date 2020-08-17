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
import com.pig4cloud.pigx.admin.entity.AtimportLog;
import com.pig4cloud.pigx.admin.service.AtimportLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 导入日志
 *
 * @author gaoxiao
 * @date 2020-07-07 14:56:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atimportlog" )
@Api(value = "atimportlog", tags = "导入日志管理")
public class AtimportLogController {

    private final  AtimportLogService atimportLogService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atimportLog 导入日志
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtimportLogPage(Page page, AtimportLog atimportLog) {
        return R.ok(atimportLogService.page(page, Wrappers.query(atimportLog)));
    }


    /**
     * 通过id查询导入日志
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atimportLogService.getById(id));
    }

    /**
     * 新增导入日志
     * @param atimportLog 导入日志
     * @return R
     */
    @ApiOperation(value = "新增导入日志", notes = "新增导入日志")
    @SysLog("新增导入日志" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atimportlog_add')" )
    public R save(@RequestBody AtimportLog atimportLog) {
        return R.ok(atimportLogService.save(atimportLog));
    }

    /**
     * 修改导入日志
     * @param atimportLog 导入日志
     * @return R
     */
    @ApiOperation(value = "修改导入日志", notes = "修改导入日志")
    @SysLog("修改导入日志" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atimportlog_edit')" )
    public R updateById(@RequestBody AtimportLog atimportLog) {
        return R.ok(atimportLogService.updateById(atimportLog));
    }

    /**
     * 通过id删除导入日志
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除导入日志", notes = "通过id删除导入日志")
    @SysLog("通过id删除导入日志" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atimportlog_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atimportLogService.removeById(id));
    }

}
