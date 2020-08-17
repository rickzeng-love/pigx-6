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
import com.pig4cloud.pigx.admin.entity.CtsalaryAll;
import com.pig4cloud.pigx.admin.service.CtsalaryAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资历史
 *
 * @author gaoxiao
 * @date 2020-06-13 10:20:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctsalaryall" )
@Api(value = "ctsalaryall", tags = "薪资历史管理")
public class CtsalaryAllController {

    private final  CtsalaryAllService ctsalaryAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctsalaryAll 薪资历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtsalaryAllPage(Page page, CtsalaryAll ctsalaryAll) {
        return R.ok(ctsalaryAllService.page(page, Wrappers.query(ctsalaryAll)));
    }


    /**
     * 通过id查询薪资历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctsalaryAllService.getById(id));
    }

    /**
     * 新增薪资历史
     * @param ctsalaryAll 薪资历史
     * @return R
     */
    @ApiOperation(value = "新增薪资历史", notes = "新增薪资历史")
    @SysLog("新增薪资历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctsalaryall_add')" )
    public R save(@RequestBody CtsalaryAll ctsalaryAll) {
        return R.ok(ctsalaryAllService.save(ctsalaryAll));
    }

/*
    */
/**
     * 修改薪资历史
     * @param ctsalaryAll 薪资历史
     * @return R
     *//*

    @ApiOperation(value = "修改薪资历史", notes = "修改薪资历史")
    @SysLog("修改薪资历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctsalaryall_edit')" )
    public R updateById(@RequestBody CtsalaryAll ctsalaryAll) {
        return R.ok(ctsalaryAllService.updateById(ctsalaryAll));
    }

    */
/**
     * 通过id删除薪资历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资历史", notes = "通过id删除薪资历史")
    @SysLog("通过id删除薪资历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctsalaryall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctsalaryAllService.removeById(id));
    }
*/

}
