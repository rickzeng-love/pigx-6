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
import com.pig4cloud.pigx.admin.entity.Ctstandard;
import com.pig4cloud.pigx.admin.service.CtstandardService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资模板
 *
 * @author gaoxiao
 * @date 2020-04-22 14:42:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctstandard" )
@Api(value = "ctstandard", tags = "薪资模板管理")
public class CtstandardController {

    private final  CtstandardService ctstandardService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctstandard 薪资模板
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtstandardPage(Page page, Ctstandard ctstandard) {
        return R.ok(ctstandardService.page(page, Wrappers.query(ctstandard)));
    }



    /**
     * 通过id查询薪资模板
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(ctstandardService.getById(id));
    }

    /**
     * 新增薪资模板
     * @param ctstandard 薪资模板
     * @return R
     */
    @ApiOperation(value = "新增薪资模板", notes = "新增薪资模板")
    @SysLog("新增薪资模板" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctstandard_add')" )
    public R save(@RequestBody Ctstandard ctstandard) {
        return R.ok(ctstandardService.save(ctstandard));
    }




 /*   *//**
     * 修改薪资模板
     * @param ctstandard 薪资模板
     * @return R
     *//*
    @ApiOperation(value = "修改薪资模板", notes = "修改薪资模板")
    @SysLog("修改薪资模板" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctstandard_edit')" )
    public R updateById(@RequestBody Ctstandard ctstandard) {
        return R.ok(ctstandardService.updateById(ctstandard));
    }

    *//**
     * 通过id删除薪资模板
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除薪资模板", notes = "通过id删除薪资模板")
    @SysLog("通过id删除薪资模板" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctstandard_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(ctstandardService.removeById(id));
    }
*/
}
