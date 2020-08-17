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
import com.pig4cloud.pigx.admin.entity.CtchangesalaryRegister;
import com.pig4cloud.pigx.admin.service.CtchangesalaryRegisterService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资变动登记表
 *
 * @author gaoxiao
 * @date 2020-06-12 15:03:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctchangesalaryregister" )
@Api(value = "ctchangesalaryregister", tags = "薪资变动登记表管理")
public class CtchangesalaryRegisterController {

    private final  CtchangesalaryRegisterService ctchangesalaryRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctchangesalaryRegister 薪资变动登记表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtchangesalaryRegisterPage(Page page, CtchangesalaryRegister ctchangesalaryRegister) {
        return R.ok(ctchangesalaryRegisterService.page(page, Wrappers.query(ctchangesalaryRegister)));
    }


    /**
     * 通过id查询薪资变动登记表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctchangesalaryRegisterService.getById(id));
    }

    /**
     * 新增薪资变动登记表
     * @param ctchangesalaryRegister 薪资变动登记表
     * @return R
     */
    @ApiOperation(value = "新增薪资变动登记表", notes = "新增薪资变动登记表")
    @SysLog("新增薪资变动登记表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalaryregister_add')" )
    public R save(@RequestBody CtchangesalaryRegister ctchangesalaryRegister) {
        return R.ok(ctchangesalaryRegisterService.save(ctchangesalaryRegister));
    }

/*
    */
/**
     * 修改薪资变动登记表
     * @param ctchangesalaryRegister 薪资变动登记表
     * @return R
     *//*

    @ApiOperation(value = "修改薪资变动登记表", notes = "修改薪资变动登记表")
    @SysLog("修改薪资变动登记表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalaryregister_edit')" )
    public R updateById(@RequestBody CtchangesalaryRegister ctchangesalaryRegister) {
        return R.ok(ctchangesalaryRegisterService.updateById(ctchangesalaryRegister));
    }

    */
/**
     * 通过id删除薪资变动登记表
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资变动登记表", notes = "通过id删除薪资变动登记表")
    @SysLog("通过id删除薪资变动登记表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalaryregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctchangesalaryRegisterService.removeById(id));
    }
*/

}
