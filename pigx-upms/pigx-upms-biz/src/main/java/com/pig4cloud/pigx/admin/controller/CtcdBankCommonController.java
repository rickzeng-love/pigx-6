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
import com.pig4cloud.pigx.admin.entity.CtcdBankCommon;
import com.pig4cloud.pigx.admin.service.CtcdBankCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 银行
 *
 * @author gaoxiao
 * @date 2020-07-10 15:17:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbankcommon" )
@Api(value = "ctcdbankcommon", tags = "银行管理")
public class CtcdBankCommonController {

    private final  CtcdBankCommonService ctcdBankCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBankCommon 银行
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBankCommonPage(Page page, CtcdBankCommon ctcdBankCommon) {
        return R.ok(ctcdBankCommonService.page(page, Wrappers.query(ctcdBankCommon)));
    }


    /**
     * 通过id查询银行
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBankCommonService.getById(id));
    }

    /**
     * 新增银行
     * @param ctcdBankCommon 银行
     * @return R
     */
    @ApiOperation(value = "新增银行", notes = "新增银行")
    @SysLog("新增银行" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdbankcommon_add')" )
    public R save(@RequestBody CtcdBankCommon ctcdBankCommon) {
        return R.ok(ctcdBankCommonService.save(ctcdBankCommon));
    }

    /**
     * 修改银行
     * @param ctcdBankCommon 银行
     * @return R
     */
    @ApiOperation(value = "修改银行", notes = "修改银行")
    @SysLog("修改银行" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdbankcommon_edit')" )
    public R updateById(@RequestBody CtcdBankCommon ctcdBankCommon) {
        return R.ok(ctcdBankCommonService.updateById(ctcdBankCommon));
    }

    /**
     * 通过id删除银行
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除银行", notes = "通过id删除银行")
    @SysLog("通过id删除银行" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbankcommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBankCommonService.removeById(id));
    }

}
