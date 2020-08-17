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
import com.pig4cloud.pigx.admin.entity.AtshiftGroupSub;
import com.pig4cloud.pigx.admin.service.AtshiftGroupSubService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 轮班班次
 *
 * @author gaoxiao
 * @date 2020-07-06 16:26:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftgroupsub" )
@Api(value = "atshiftgroupsub", tags = "轮班班次管理")
public class AtshiftGroupSubController {

    private final  AtshiftGroupSubService atshiftGroupSubService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftGroupSub 轮班班次
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftGroupSubPage(Page page, AtshiftGroupSub atshiftGroupSub) {
        return R.ok(atshiftGroupSubService.page(page, Wrappers.query(atshiftGroupSub)));
    }


    /**
     * 通过id查询轮班班次
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atshiftGroupSubService.getById(id));
    }

    /**
     * 新增轮班班次
     * @param atshiftGroupSub 轮班班次
     * @return R
     */
    @ApiOperation(value = "新增轮班班次", notes = "新增轮班班次")
    @SysLog("新增轮班班次" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftgroupsub_add')" )
    public R save(@RequestBody AtshiftGroupSub atshiftGroupSub) {
        return R.ok(atshiftGroupSubService.save(atshiftGroupSub));
    }

    /**
     * 修改轮班班次
     * @param atshiftGroupSub 轮班班次
     * @return R
     */
    @ApiOperation(value = "修改轮班班次", notes = "修改轮班班次")
    @SysLog("修改轮班班次" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftgroupsub_edit')" )
    public R updateById(@RequestBody AtshiftGroupSub atshiftGroupSub) {
        return R.ok(atshiftGroupSubService.updateById(atshiftGroupSub));
    }

    /**
     * 通过id删除轮班班次
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除轮班班次", notes = "通过id删除轮班班次")
    @SysLog("通过id删除轮班班次" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftgroupsub_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atshiftGroupSubService.removeById(id));
    }

}
