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
import com.pig4cloud.pigx.admin.entity.EtcdEmpgradeCommon;
import com.pig4cloud.pigx.admin.service.EtcdEmpgradeCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 员工等级
 *
 * @author gaoxiao
 * @date 2020-07-10 11:37:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdempgradecommon" )
@Api(value = "etcdempgradecommon", tags = "员工等级管理")
public class EtcdEmpgradeCommonController {

    private final  EtcdEmpgradeCommonService etcdEmpgradeCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdEmpgradeCommon 员工等级
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdEmpgradeCommonPage(Page page, EtcdEmpgradeCommon etcdEmpgradeCommon) {
        return R.ok(etcdEmpgradeCommonService.page(page, Wrappers.query(etcdEmpgradeCommon)));
    }


    /**
     * 通过id查询员工等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdEmpgradeCommonService.getById(id));
    }

    /**
     * 新增员工等级
     * @param etcdEmpgradeCommon 员工等级
     * @return R
     */
    @ApiOperation(value = "新增员工等级", notes = "新增员工等级")
    @SysLog("新增员工等级" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etcdempgradecommon_add')" )
    public R save(@RequestBody EtcdEmpgradeCommon etcdEmpgradeCommon) {
        return R.ok(etcdEmpgradeCommonService.save(etcdEmpgradeCommon));
    }

    /**
     * 修改员工等级
     * @param etcdEmpgradeCommon 员工等级
     * @return R
     */
    @ApiOperation(value = "修改员工等级", notes = "修改员工等级")
    @SysLog("修改员工等级" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etcdempgradecommon_edit')" )
    public R updateById(@RequestBody EtcdEmpgradeCommon etcdEmpgradeCommon) {
        return R.ok(etcdEmpgradeCommonService.updateById(etcdEmpgradeCommon));
    }

    /**
     * 通过id删除员工等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除员工等级", notes = "通过id删除员工等级")
    @SysLog("通过id删除员工等级" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdempgradecommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdEmpgradeCommonService.removeById(id));
    }

}
