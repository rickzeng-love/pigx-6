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
import com.pig4cloud.pigx.admin.entity.OtcdJobtypeAll;
import com.pig4cloud.pigx.admin.service.OtcdJobtypeAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 岗位类型历史
 *
 * @author gaoxiao
 * @date 2020-05-29 10:14:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdjobtypeall" )
@Api(value = "otcdjobtypeall", tags = "岗位类型历史管理")
public class OtcdJobtypeAllController {

    private final  OtcdJobtypeAllService otcdJobtypeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdJobtypeAll 岗位类型历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdJobtypeAllPage(Page page, OtcdJobtypeAll otcdJobtypeAll) {
        return R.ok(otcdJobtypeAllService.page(page, Wrappers.query(otcdJobtypeAll)));
    }


    /**
     * 通过id查询岗位类型历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdJobtypeAllService.getById(id));
    }

    /**
     * 新增岗位类型历史      @PreAuthorize("@pms.hasPermission('admin_otcdjobtypeall_add')" )
     * @param otcdJobtypeAll 岗位类型历史
     * @return R
     */
    @ApiOperation(value = "新增岗位类型历史", notes = "新增岗位类型历史")
    @SysLog("新增岗位类型历史" )
    @PostMapping("/save")
    public R save(@RequestBody OtcdJobtypeAll otcdJobtypeAll) {

        return R.ok(otcdJobtypeAllService.save(otcdJobtypeAll));
    }

/*    *//**
     * 修改岗位类型历史
     * @param otcdJobtypeAll 岗位类型历史
     * @return R
     *//*
    @ApiOperation(value = "修改岗位类型历史", notes = "修改岗位类型历史")
    @SysLog("修改岗位类型历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otcdjobtypeall_edit')" )
    public R updateById(@RequestBody OtcdJobtypeAll otcdJobtypeAll) {
        return R.ok(otcdJobtypeAllService.updateById(otcdJobtypeAll));
    }*/

  /*  *//**
     * 通过id删除岗位类型历史
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除岗位类型历史", notes = "通过id删除岗位类型历史")
    @SysLog("通过id删除岗位类型历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdjobtypeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdJobtypeAllService.removeById(id));
    }
*/
}
