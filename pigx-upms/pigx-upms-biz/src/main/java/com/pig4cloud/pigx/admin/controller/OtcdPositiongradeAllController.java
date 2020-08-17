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
import com.pig4cloud.pigx.admin.entity.OtcdPositiongradeAll;
import com.pig4cloud.pigx.admin.service.OtcdPositiongradeAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 职等历史
 *
 * @author gaoxiao
 * @date 2020-05-29 10:13:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdpositiongradeall" )
@Api(value = "otcdpositiongradeall", tags = "职等历史管理")
public class OtcdPositiongradeAllController {

    private final  OtcdPositiongradeAllService otcdPositiongradeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdPositiongradeAll 职等历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdPositiongradeAllPage(Page page, OtcdPositiongradeAll otcdPositiongradeAll) {
        return R.ok(otcdPositiongradeAllService.page(page, Wrappers.query(otcdPositiongradeAll)));
    }


    /**
     * 通过id查询职等历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdPositiongradeAllService.getById(id));
    }

    /**
     * 新增职等历史
     * @param otcdPositiongradeAll 职等历史
     * @return R
     */
    @ApiOperation(value = "新增职等历史", notes = "新增职等历史")
    @SysLog("新增职等历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongradeall_add')" )
    public R save(@RequestBody OtcdPositiongradeAll otcdPositiongradeAll) {
        return R.ok(otcdPositiongradeAllService.save(otcdPositiongradeAll));
    }

/*
    */
/**
     * 修改职等历史
     * @param otcdPositiongradeAll 职等历史
     * @return R
     *//*

    @ApiOperation(value = "修改职等历史", notes = "修改职等历史")
    @SysLog("修改职等历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongradeall_edit')" )
    public R updateById(@RequestBody OtcdPositiongradeAll otcdPositiongradeAll) {
        return R.ok(otcdPositiongradeAllService.updateById(otcdPositiongradeAll));
    }

    */
/**
     * 通过id删除职等历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除职等历史", notes = "通过id删除职等历史")
    @SysLog("通过id删除职等历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongradeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdPositiongradeAllService.removeById(id));
    }
*/

}
