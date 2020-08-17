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
import com.pig4cloud.pigx.admin.entity.EtprobAll;
import com.pig4cloud.pigx.admin.service.EtprobAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 试用期变动历史
 *
 * @author gaoxiao
 * @date 2020-04-15 11:18:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etproball" )
@Api(value = "etproball", tags = "试用期变动历史管理")
public class EtprobAllController {

    private final  EtprobAllService etprobAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etprobAll 试用期变动历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtprobAllPage(Page page, EtprobAll etprobAll) {
        return R.ok(etprobAllService.page(page, Wrappers.query(etprobAll)));
    }


    /**
     * 通过id查询试用期变动历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etprobAllService.getById(id));
    }

    /**
     * 新增试用期变动历史
     * @param etprobAll 试用期变动历史
     * @return R
     */
    @ApiOperation(value = "新增试用期变动历史", notes = "新增试用期变动历史")
    @SysLog("新增试用期变动历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etproball_add')" )
    public R save(@RequestBody EtprobAll etprobAll) {
        return R.ok(etprobAllService.save(etprobAll));
    }

  /*  *//**
     * 修改试用期变动历史
     * @param etprobAll 试用期变动历史
     * @return R
     *//*
    @ApiOperation(value = "修改试用期变动历史", notes = "修改试用期变动历史")
    @SysLog("修改试用期变动历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etproball_edit')" )
    public R updateById(@RequestBody EtprobAll etprobAll) {
        return R.ok(etprobAllService.updateById(etprobAll));
    }
*/
   /* *//**
     * 通过id删除试用期变动历史
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除试用期变动历史", notes = "通过id删除试用期变动历史")
    @SysLog("通过id删除试用期变动历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etproball_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etprobAllService.removeById(id));
    }
*/
}
