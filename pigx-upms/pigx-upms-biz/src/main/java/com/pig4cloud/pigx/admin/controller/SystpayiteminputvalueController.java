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
import com.pig4cloud.pigx.admin.entity.Systpayiteminputvalue;
import com.pig4cloud.pigx.admin.service.SystpayiteminputvalueService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资项值(输入表)
 *
 * @author gaoxiao
 * @date 2020-06-15 11:25:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayiteminputvalue" )
@Api(value = "systpayiteminputvalue", tags = "薪资项值(输入表)管理")
public class SystpayiteminputvalueController {

    private final  SystpayiteminputvalueService systpayiteminputvalueService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayiteminputvalue 薪资项值(输入表)
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayiteminputvaluePage(Page page, Systpayiteminputvalue systpayiteminputvalue) {
        return R.ok(systpayiteminputvalueService.page(page, Wrappers.query(systpayiteminputvalue)));
    }


    /**
     * 通过id查询薪资项值(输入表)
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(systpayiteminputvalueService.getById(id));
    }

    /**
     * 新增薪资项值(输入表)
     * @param systpayiteminputvalue 薪资项值(输入表)
     * @return R
     */
    @ApiOperation(value = "新增薪资项值(输入表)", notes = "新增薪资项值(输入表)")
    @SysLog("新增薪资项值(输入表)" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayiteminputvalue_add')" )
    public R save(@RequestBody Systpayiteminputvalue systpayiteminputvalue) {
        return R.ok(systpayiteminputvalueService.save(systpayiteminputvalue));
    }
/*

    */
/**
     * 修改薪资项值(输入表)
     * @param systpayiteminputvalue 薪资项值(输入表)
     * @return R
     *//*

    @ApiOperation(value = "修改薪资项值(输入表)", notes = "修改薪资项值(输入表)")
    @SysLog("修改薪资项值(输入表)" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayiteminputvalue_edit')" )
    public R updateById(@RequestBody Systpayiteminputvalue systpayiteminputvalue) {
        return R.ok(systpayiteminputvalueService.updateById(systpayiteminputvalue));
    }

    */
/**
     * 通过id删除薪资项值(输入表)
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资项值(输入表)", notes = "通过id删除薪资项值(输入表)")
    @SysLog("通过id删除薪资项值(输入表)" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayiteminputvalue_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(systpayiteminputvalueService.removeById(id));
    }
*/

}
