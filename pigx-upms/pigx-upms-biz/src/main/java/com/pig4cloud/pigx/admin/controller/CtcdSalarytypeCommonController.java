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
import com.pig4cloud.pigx.admin.entity.CtcdSalarytypeCommon;
import com.pig4cloud.pigx.admin.service.CtcdSalarytypeCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资类型
 *
 * @author gaoxiao
 * @date 2020-07-10 15:37:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdsalarytypecommon" )
@Api(value = "ctcdsalarytypecommon", tags = "薪资类型管理")
public class CtcdSalarytypeCommonController {

    private final  CtcdSalarytypeCommonService ctcdSalarytypeCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdSalarytypeCommon 薪资类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdSalarytypeCommonPage(Page page, CtcdSalarytypeCommon ctcdSalarytypeCommon) {
        return R.ok(ctcdSalarytypeCommonService.page(page, Wrappers.query(ctcdSalarytypeCommon)));
    }


    /**
     * 通过id查询薪资类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdSalarytypeCommonService.getById(id));
    }

    /**
     * 新增薪资类型
     * @param ctcdSalarytypeCommon 薪资类型
     * @return R
     */
    @ApiOperation(value = "新增薪资类型", notes = "新增薪资类型")
    @SysLog("新增薪资类型" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarytypecommon_add')" )
    public R save(@RequestBody CtcdSalarytypeCommon ctcdSalarytypeCommon) {
        return R.ok(ctcdSalarytypeCommonService.save(ctcdSalarytypeCommon));
    }

    /**
     * 修改薪资类型
     * @param ctcdSalarytypeCommon 薪资类型
     * @return R
     */
    @ApiOperation(value = "修改薪资类型", notes = "修改薪资类型")
    @SysLog("修改薪资类型" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarytypecommon_edit')" )
    public R updateById(@RequestBody CtcdSalarytypeCommon ctcdSalarytypeCommon) {
        return R.ok(ctcdSalarytypeCommonService.updateById(ctcdSalarytypeCommon));
    }

    /**
     * 通过id删除薪资类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资类型", notes = "通过id删除薪资类型")
    @SysLog("通过id删除薪资类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdsalarytypecommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdSalarytypeCommonService.removeById(id));
    }

}
