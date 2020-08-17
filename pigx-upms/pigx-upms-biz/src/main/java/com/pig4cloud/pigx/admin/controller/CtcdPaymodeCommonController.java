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
import com.pig4cloud.pigx.admin.entity.CtcdPaymodeCommon;
import com.pig4cloud.pigx.admin.service.CtcdPaymodeCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 付款方式
 *
 * @author gaoxiao
 * @date 2020-07-10 15:06:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdpaymodecommon" )
@Api(value = "ctcdpaymodecommon", tags = "付款方式管理")
public class CtcdPaymodeCommonController {

    private final  CtcdPaymodeCommonService ctcdPaymodeCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdPaymodeCommon 付款方式
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdPaymodeCommonPage(Page page, CtcdPaymodeCommon ctcdPaymodeCommon) {
        return R.ok(ctcdPaymodeCommonService.page(page, Wrappers.query(ctcdPaymodeCommon)));
    }


    /**
     * 通过id查询付款方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdPaymodeCommonService.getById(id));
    }

    /**
     * 新增付款方式
     * @param ctcdPaymodeCommon 付款方式
     * @return R
     */
    @ApiOperation(value = "新增付款方式", notes = "新增付款方式")
    @SysLog("新增付款方式" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdpaymodecommon_add')" )
    public R save(@RequestBody CtcdPaymodeCommon ctcdPaymodeCommon) {
        return R.ok(ctcdPaymodeCommonService.save(ctcdPaymodeCommon));
    }

    /**
     * 修改付款方式
     * @param ctcdPaymodeCommon 付款方式
     * @return R
     */
    @ApiOperation(value = "修改付款方式", notes = "修改付款方式")
    @SysLog("修改付款方式" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdpaymodecommon_edit')" )
    public R updateById(@RequestBody CtcdPaymodeCommon ctcdPaymodeCommon) {
        return R.ok(ctcdPaymodeCommonService.updateById(ctcdPaymodeCommon));
    }

    /**
     * 通过id删除付款方式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除付款方式", notes = "通过id删除付款方式")
    @SysLog("通过id删除付款方式" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdpaymodecommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdPaymodeCommonService.removeById(id));
    }

}
