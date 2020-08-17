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
import com.pig4cloud.pigx.admin.entity.OtcdPositiongradeCommon;
import com.pig4cloud.pigx.admin.service.OtcdPositiongradeCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 职等
 *
 * @author gaoxiao
 * @date 2020-07-10 14:27:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdpositiongradecommon" )
@Api(value = "otcdpositiongradecommon", tags = "职等管理")
public class OtcdPositiongradeCommonController {

    private final  OtcdPositiongradeCommonService otcdPositiongradeCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdPositiongradeCommon 职等
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdPositiongradeCommonPage(Page page, OtcdPositiongradeCommon otcdPositiongradeCommon) {
        return R.ok(otcdPositiongradeCommonService.page(page, Wrappers.query(otcdPositiongradeCommon)));
    }


    /**
     * 通过id查询职等
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdPositiongradeCommonService.getById(id));
    }

    /**
     * 新增职等
     * @param otcdPositiongradeCommon 职等
     * @return R
     */
    @ApiOperation(value = "新增职等", notes = "新增职等")
    @SysLog("新增职等" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongradecommon_add')" )
    public R save(@RequestBody OtcdPositiongradeCommon otcdPositiongradeCommon) {
        return R.ok(otcdPositiongradeCommonService.save(otcdPositiongradeCommon));
    }

    /**
     * 修改职等
     * @param otcdPositiongradeCommon 职等
     * @return R
     */
    @ApiOperation(value = "修改职等", notes = "修改职等")
    @SysLog("修改职等" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongradecommon_edit')" )
    public R updateById(@RequestBody OtcdPositiongradeCommon otcdPositiongradeCommon) {
        return R.ok(otcdPositiongradeCommonService.updateById(otcdPositiongradeCommon));
    }

    /**
     * 通过id删除职等
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除职等", notes = "通过id删除职等")
    @SysLog("通过id删除职等" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongradecommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdPositiongradeCommonService.removeById(id));
    }

}
