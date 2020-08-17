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
import com.pig4cloud.pigx.admin.entity.AtregsectRegister;
import com.pig4cloud.pigx.admin.service.AtregsectRegisterService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author shishengjie
 * @date 2020-07-22 14:00:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atregsectregister" )
@Api(value = "atregsectregister", tags = "管理")
public class AtregsectRegisterController {

    private final  AtregsectRegisterService atregsectRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atregsectRegister 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtregsectRegisterPage(Page page, AtregsectRegister atregsectRegister) {
        return R.ok(atregsectRegisterService.page(page, Wrappers.query(atregsectRegister)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atregsectRegisterService.getById(id));
    }

    /**
     * 新增
     * @param atregsectRegister 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atregsectregister_add')" )
    public R save(@RequestBody AtregsectRegister atregsectRegister) {
        return R.ok(atregsectRegisterService.save(atregsectRegister));
    }

    /**
     * 修改
     * @param atregsectRegister 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atregsectregister_edit')" )
    public R updateById(@RequestBody AtregsectRegister atregsectRegister) {
        return R.ok(atregsectRegisterService.updateById(atregsectRegister));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atregsectregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atregsectRegisterService.removeById(id));
    }

}
