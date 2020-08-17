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
import com.pig4cloud.pigx.admin.entity.AtimportDshandsetting;
import com.pig4cloud.pigx.admin.service.AtimportDshandsettingService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 手动读卡区间
 *
 * @author gaoxioa
 * @date 2020-07-14 15:54:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atimportdshandsetting" )
@Api(value = "atimportdshandsetting", tags = "手动读卡区间管理")
public class AtimportDshandsettingController {

    private final  AtimportDshandsettingService atimportDshandsettingService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atimportDshandsetting 手动读卡区间
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtimportDshandsettingPage(Page page, AtimportDshandsetting atimportDshandsetting) {
        return R.ok(atimportDshandsettingService.page(page, Wrappers.query(atimportDshandsetting)));
    }


    /**
     * 通过id查询手动读卡区间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atimportDshandsettingService.getById(id));
    }

    /**
     * 新增手动读卡区间     @PreAuthorize("@pms.hasPermission('admin_atimportdshandsetting_add')" )
     * @param atimportDshandsetting 手动读卡区间
     * @return R
     */
    @ApiOperation(value = "新增手动读卡区间", notes = "新增手动读卡区间")
    @SysLog("新增手动读卡区间" )
    @PostMapping("/save")
    public R save(@RequestBody AtimportDshandsetting atimportDshandsetting) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atimportDshandsetting.setCorpcode(corpcode);
		atimportDshandsetting.setCorpid(pigxUser.getCorpid());
        return R.ok(atimportDshandsettingService.save(atimportDshandsetting));
    }

    /**
     * 修改手动读卡区间
     * @param atimportDshandsetting 手动读卡区间
     * @return R
     */
    @ApiOperation(value = "修改手动读卡区间", notes = "修改手动读卡区间")
    @SysLog("修改手动读卡区间" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atimportdshandsetting_edit')" )
    public R updateById(@RequestBody AtimportDshandsetting atimportDshandsetting) {
        return R.ok(atimportDshandsettingService.updateById(atimportDshandsetting));
    }

    /**
     * 通过id删除手动读卡区间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除手动读卡区间", notes = "通过id删除手动读卡区间")
    @SysLog("通过id删除手动读卡区间" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atimportdshandsetting_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atimportDshandsettingService.removeById(id));
    }

}
