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
import com.pig4cloud.pigx.admin.entity.EtcdConpropertyCommon;
import com.pig4cloud.pigx.admin.service.EtcdConpropertyCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 合同属性
 *
 * @author gaoxiao
 * @date 2020-07-10 13:50:38
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdconpropertycommon" )
@Api(value = "etcdconpropertycommon", tags = "合同属性管理")
public class EtcdConpropertyCommonController {

    private final  EtcdConpropertyCommonService etcdConpropertyCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdConpropertyCommon 合同属性
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdConpropertyCommonPage(Page page, EtcdConpropertyCommon etcdConpropertyCommon) {
        return R.ok(etcdConpropertyCommonService.page(page, Wrappers.query(etcdConpropertyCommon)));
    }


    /**
     * 通过id查询合同属性
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdConpropertyCommonService.getById(id));
    }

    /**
     * 新增合同属性
     * @param etcdConpropertyCommon 合同属性
     * @return R
     */
    @ApiOperation(value = "新增合同属性", notes = "新增合同属性")
    @SysLog("新增合同属性" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etcdconpropertycommon_add')" )
    public R save(@RequestBody EtcdConpropertyCommon etcdConpropertyCommon) {
        return R.ok(etcdConpropertyCommonService.save(etcdConpropertyCommon));
    }

    /**
     * 修改合同属性
     * @param etcdConpropertyCommon 合同属性
     * @return R
     */
    @ApiOperation(value = "修改合同属性", notes = "修改合同属性")
    @SysLog("修改合同属性" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etcdconpropertycommon_edit')" )
    public R updateById(@RequestBody EtcdConpropertyCommon etcdConpropertyCommon) {
        return R.ok(etcdConpropertyCommonService.updateById(etcdConpropertyCommon));
    }

    /**
     * 通过id删除合同属性
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同属性", notes = "通过id删除合同属性")
    @SysLog("通过id删除合同属性" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdconpropertycommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdConpropertyCommonService.removeById(id));
    }

}
