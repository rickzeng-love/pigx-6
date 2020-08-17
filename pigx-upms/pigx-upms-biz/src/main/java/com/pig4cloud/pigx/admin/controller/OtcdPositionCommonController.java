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
import com.pig4cloud.pigx.admin.entity.OtcdPositionCommon;
import com.pig4cloud.pigx.admin.service.OtcdPositionCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 职务
 *
 * @author gaoxiao
 * @date 2020-07-10 14:15:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdpositioncommon" )
@Api(value = "otcdpositioncommon", tags = "职务管理")
public class OtcdPositionCommonController {

    private final  OtcdPositionCommonService otcdPositionCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdPositionCommon 职务
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdPositionCommonPage(Page page, OtcdPositionCommon otcdPositionCommon) {
        return R.ok(otcdPositionCommonService.page(page, Wrappers.query(otcdPositionCommon)));
    }


    /**
     * 通过id查询职务
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdPositionCommonService.getById(id));
    }

    /**
     * 新增职务
     * @param otcdPositionCommon 职务
     * @return R
     */
    @ApiOperation(value = "新增职务", notes = "新增职务")
    @SysLog("新增职务" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdpositioncommon_add')" )
    public R save(@RequestBody OtcdPositionCommon otcdPositionCommon) {
        return R.ok(otcdPositionCommonService.save(otcdPositionCommon));
    }

    /**
     * 修改职务
     * @param otcdPositionCommon 职务
     * @return R
     */
    @ApiOperation(value = "修改职务", notes = "修改职务")
    @SysLog("修改职务" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdpositioncommon_edit')" )
    public R updateById(@RequestBody OtcdPositionCommon otcdPositionCommon) {
        return R.ok(otcdPositionCommonService.updateById(otcdPositionCommon));
    }

    /**
     * 通过id删除职务
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除职务", notes = "通过id删除职务")
    @SysLog("通过id删除职务" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdpositioncommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdPositionCommonService.removeById(id));
    }

}
