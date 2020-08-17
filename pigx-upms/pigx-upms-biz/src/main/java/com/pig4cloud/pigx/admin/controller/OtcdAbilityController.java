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
import com.pig4cloud.pigx.admin.entity.OtcdAbility;
import com.pig4cloud.pigx.admin.service.OtcdAbilityService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 能力
 *
 * @author gaoxiao
 * @date 2020-06-24 16:26:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdability" )
@Api(value = "otcdability", tags = "能力管理")
public class OtcdAbilityController {

    private final  OtcdAbilityService otcdAbilityService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdAbility 能力
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdAbilityPage(Page page, OtcdAbility otcdAbility) {
        return R.ok(otcdAbilityService.page(page, Wrappers.query(otcdAbility)));
    }


    /**
     * 通过id查询能力
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdAbilityService.getById(id));
    }

    /**
     * 新增能力
     * @param otcdAbility 能力
     * @return R
     */
    @ApiOperation(value = "新增能力", notes = "新增能力")
    @SysLog("新增能力" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdability_add')" )
    public R save(@RequestBody OtcdAbility otcdAbility) {
        return R.ok(otcdAbilityService.save(otcdAbility));
    }

    /**
     * 修改能力
     * @param otcdAbility 能力
     * @return R
     */
    @ApiOperation(value = "修改能力", notes = "修改能力")
    @SysLog("修改能力" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdability_edit')" )
    public R updateById(@RequestBody OtcdAbility otcdAbility) {
        return R.ok(otcdAbilityService.updateById(otcdAbility));
    }

    /**
     * 通过id删除能力
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除能力", notes = "通过id删除能力")
    @SysLog("通过id删除能力" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdability_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdAbilityService.removeById(id));
    }

}
