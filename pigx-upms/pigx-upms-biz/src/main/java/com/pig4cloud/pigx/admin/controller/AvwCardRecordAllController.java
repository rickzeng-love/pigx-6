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
import com.pig4cloud.pigx.admin.entity.AvwCardRecordAll;
import com.pig4cloud.pigx.admin.service.AvwCardRecordAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-07-07 17:30:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/avwcardrecordall" )
@Api(value = "avwcardrecordall", tags = "VIEW管理")
public class AvwCardRecordAllController {

    private final  AvwCardRecordAllService avwCardRecordAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param avwCardRecordAll VIEW
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAvwCardRecordAllPage(Page page, AvwCardRecordAll avwCardRecordAll) {
        return R.ok(avwCardRecordAllService.page(page, Wrappers.query(avwCardRecordAll)));
    }


    /**
     * 通过id查询VIEW
     * @param badge id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{badge}" )
    public R getById(@PathVariable("badge" ) String badge) {
        return R.ok(avwCardRecordAllService.getById(badge));
    }

    /**
     * 新增VIEW
     * @param avwCardRecordAll VIEW
     * @return R
     */
    @ApiOperation(value = "新增VIEW", notes = "新增VIEW")
    @SysLog("新增VIEW" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_avwcardrecordall_add')" )
    public R save(@RequestBody AvwCardRecordAll avwCardRecordAll) {
        return R.ok(avwCardRecordAllService.save(avwCardRecordAll));
    }

    /**
     * 修改VIEW
     * @param avwCardRecordAll VIEW
     * @return R
     */
    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_avwcardrecordall_edit')" )
    public R updateById(@RequestBody AvwCardRecordAll avwCardRecordAll) {
        return R.ok(avwCardRecordAllService.updateById(avwCardRecordAll));
    }

    /**
     * 通过id删除VIEW
     * @param badge id
     * @return R
     */
    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{badge}" )
    @PreAuthorize("@pms.hasPermission('admin_avwcardrecordall_del')" )
    public R removeById(@PathVariable String badge) {
        return R.ok(avwCardRecordAllService.removeById(badge));
    }

}
