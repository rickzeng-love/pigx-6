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
import com.pig4cloud.pigx.admin.entity.AvwCardRecord;
import com.pig4cloud.pigx.admin.service.AvwCardRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-07-07 17:30:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/avwcardrecord" )
@Api(value = "avwcardrecord", tags = "VIEW管理")
public class AvwCardRecordController {

    private final  AvwCardRecordService avwCardRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param avwCardRecord VIEW
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAvwCardRecordPage(Page page, AvwCardRecord avwCardRecord) {
        return R.ok(avwCardRecordService.page(page, Wrappers.query(avwCardRecord)));
    }


    /**
     * 通过id查询VIEW
     * @param badge id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{badge}" )
    public R getById(@PathVariable("badge" ) String badge) {
        return R.ok(avwCardRecordService.getById(badge));
    }

    /**
     * 新增VIEW
     * @param avwCardRecord VIEW
     * @return R
     */
    @ApiOperation(value = "新增VIEW", notes = "新增VIEW")
    @SysLog("新增VIEW" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_avwcardrecord_add')" )
    public R save(@RequestBody AvwCardRecord avwCardRecord) {
        return R.ok(avwCardRecordService.save(avwCardRecord));
    }

    /**
     * 修改VIEW
     * @param avwCardRecord VIEW
     * @return R
     */
    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_avwcardrecord_edit')" )
    public R updateById(@RequestBody AvwCardRecord avwCardRecord) {
        return R.ok(avwCardRecordService.updateById(avwCardRecord));
    }

    /**
     * 通过id删除VIEW
     * @param badge id
     * @return R
     */
    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{badge}" )
    @PreAuthorize("@pms.hasPermission('admin_avwcardrecord_del')" )
    public R removeById(@PathVariable String badge) {
        return R.ok(avwCardRecordService.removeById(badge));
    }

}
