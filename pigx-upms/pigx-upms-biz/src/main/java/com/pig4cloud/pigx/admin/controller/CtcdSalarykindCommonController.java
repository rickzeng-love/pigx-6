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
import com.pig4cloud.pigx.admin.entity.CtcdSalarykindCommon;
import com.pig4cloud.pigx.admin.service.CtcdSalarykindCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资架构
 *
 * @author gaoxiao
 * @date 2020-07-10 14:49:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdsalarykindcommon" )
@Api(value = "ctcdsalarykindcommon", tags = "薪资架构管理")
public class CtcdSalarykindCommonController {

    private final  CtcdSalarykindCommonService ctcdSalarykindCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdSalarykindCommon 薪资架构
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdSalarykindCommonPage(Page page, CtcdSalarykindCommon ctcdSalarykindCommon) {
        return R.ok(ctcdSalarykindCommonService.page(page, Wrappers.query(ctcdSalarykindCommon)));
    }


    /**
     * 通过id查询薪资架构
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdSalarykindCommonService.getById(id));
    }

    /**
     * 新增薪资架构
     * @param ctcdSalarykindCommon 薪资架构
     * @return R
     */
    @ApiOperation(value = "新增薪资架构", notes = "新增薪资架构")
    @SysLog("新增薪资架构" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarykindcommon_add')" )
    public R save(@RequestBody CtcdSalarykindCommon ctcdSalarykindCommon) {
        return R.ok(ctcdSalarykindCommonService.save(ctcdSalarykindCommon));
    }

    /**
     * 修改薪资架构
     * @param ctcdSalarykindCommon 薪资架构
     * @return R
     */
    @ApiOperation(value = "修改薪资架构", notes = "修改薪资架构")
    @SysLog("修改薪资架构" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarykindcommon_edit')" )
    public R updateById(@RequestBody CtcdSalarykindCommon ctcdSalarykindCommon) {
        return R.ok(ctcdSalarykindCommonService.updateById(ctcdSalarykindCommon));
    }

    /**
     * 通过id删除薪资架构
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资架构", notes = "通过id删除薪资架构")
    @SysLog("通过id删除薪资架构" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarykindcommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdSalarykindCommonService.removeById(id));
    }

}
