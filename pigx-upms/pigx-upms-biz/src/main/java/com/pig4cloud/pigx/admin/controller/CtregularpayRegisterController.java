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
import com.pig4cloud.pigx.admin.entity.CtregularpayRegister;
import com.pig4cloud.pigx.admin.service.CtregularpayRegisterService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 定期支付扣除登记
 *
 * @author shishengjie
 * @date 2020-07-21 14:41:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctregularpayregister" )
@Api(value = "ctregularpayregister", tags = "定期支付扣除登记管理")
public class CtregularpayRegisterController {

    private final  CtregularpayRegisterService ctregularpayRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctregularpayRegister 定期支付扣除登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtregularpayRegisterPage(Page page, CtregularpayRegister ctregularpayRegister) {
        return R.ok(ctregularpayRegisterService.page(page, Wrappers.query(ctregularpayRegister)));
    }


    /**
     * 通过id查询定期支付扣除登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctregularpayRegisterService.getById(id));
    }

    /**
     * 新增定期支付扣除登记
     * @param ctregularpayRegister 定期支付扣除登记
     * @return R
     */
    @ApiOperation(value = "新增定期支付扣除登记", notes = "新增定期支付扣除登记")
    @SysLog("新增定期支付扣除登记" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctregularpayregister_add')" )
    public R save(@RequestBody CtregularpayRegister ctregularpayRegister) {
        return R.ok(ctregularpayRegisterService.save(ctregularpayRegister));
    }

    /**
     * 修改定期支付扣除登记
     * @param ctregularpayRegister 定期支付扣除登记
     * @return R
     */
    @ApiOperation(value = "修改定期支付扣除登记", notes = "修改定期支付扣除登记")
    @SysLog("修改定期支付扣除登记" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctregularpayregister_edit')" )
    public R updateById(@RequestBody CtregularpayRegister ctregularpayRegister) {
        return R.ok(ctregularpayRegisterService.updateById(ctregularpayRegister));
    }

    /**
     * 通过id删除定期支付扣除登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除定期支付扣除登记", notes = "通过id删除定期支付扣除登记")
    @SysLog("通过id删除定期支付扣除登记" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctregularpayregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctregularpayRegisterService.removeById(id));
    }

}
