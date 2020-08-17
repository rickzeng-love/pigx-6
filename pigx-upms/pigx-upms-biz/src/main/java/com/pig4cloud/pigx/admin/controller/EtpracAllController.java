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
import com.pig4cloud.pigx.admin.entity.EtpracAll;
import com.pig4cloud.pigx.admin.service.EtpracAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 实习期变动历史
 *
 * @author gaoxiao
 * @date 2020-04-15 11:14:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etpracall" )
@Api(value = "etpracall", tags = "实习期变动历史管理")
public class EtpracAllController {

    private final  EtpracAllService etpracAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etpracAll 实习期变动历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtpracAllPage(Page page, EtpracAll etpracAll) {
        return R.ok(etpracAllService.page(page, Wrappers.query(etpracAll)));
    }


    /**
     * 通过id查询实习期变动历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etpracAllService.getById(id));
    }

    /**
     * 新增实习期变动历史
     * @param etpracAll 实习期变动历史
     * @return R
     */
    @ApiOperation(value = "新增实习期变动历史", notes = "新增实习期变动历史")
    @SysLog("新增实习期变动历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etpracall_add')" )
    public R save(@RequestBody EtpracAll etpracAll) {
        return R.ok(etpracAllService.save(etpracAll));
    }

/*
    */
/**
     * 修改实习期变动历史
     * @param etpracAll 实习期变动历史
     * @return R
     *//*

    @ApiOperation(value = "修改实习期变动历史", notes = "修改实习期变动历史")
    @SysLog("修改实习期变动历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etpracall_edit')" )
    public R updateById(@RequestBody EtpracAll etpracAll) {
        return R.ok(etpracAllService.updateById(etpracAll));
    }

    */
/**
     * 通过id删除实习期变动历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除实习期变动历史", notes = "通过id删除实习期变动历史")
    @SysLog("通过id删除实习期变动历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etpracall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etpracAllService.removeById(id));
    }
*/

}
