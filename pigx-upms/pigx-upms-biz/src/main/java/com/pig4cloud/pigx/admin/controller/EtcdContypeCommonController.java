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
import com.pig4cloud.pigx.admin.entity.EtcdContypeCommon;
import com.pig4cloud.pigx.admin.service.EtcdContypeCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 合同类型
 *
 * @author gaoxiao
 * @date 2020-07-10 13:42:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcontypecommon" )
@Api(value = "etcdcontypecommon", tags = "合同类型管理")
public class EtcdContypeCommonController {

    private final  EtcdContypeCommonService etcdContypeCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdContypeCommon 合同类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdContypeCommonPage(Page page, EtcdContypeCommon etcdContypeCommon) {
        return R.ok(etcdContypeCommonService.page(page, Wrappers.query(etcdContypeCommon)));
    }


    /**
     * 通过id查询合同类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdContypeCommonService.getById(id));
    }

    /**
     * 新增合同类型
     * @param etcdContypeCommon 合同类型
     * @return R
     */
    @ApiOperation(value = "新增合同类型", notes = "新增合同类型")
    @SysLog("新增合同类型" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etcdcontypecommon_add')" )
    public R save(@RequestBody EtcdContypeCommon etcdContypeCommon) {
        return R.ok(etcdContypeCommonService.save(etcdContypeCommon));
    }

    /**
     * 修改合同类型
     * @param etcdContypeCommon 合同类型
     * @return R
     */
    @ApiOperation(value = "修改合同类型", notes = "修改合同类型")
    @SysLog("修改合同类型" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etcdcontypecommon_edit')" )
    public R updateById(@RequestBody EtcdContypeCommon etcdContypeCommon) {
        return R.ok(etcdContypeCommonService.updateById(etcdContypeCommon));
    }

    /**
     * 通过id删除合同类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同类型", notes = "通过id删除合同类型")
    @SysLog("通过id删除合同类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdcontypecommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdContypeCommonService.removeById(id));
    }

}
