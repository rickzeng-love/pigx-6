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
import com.pig4cloud.pigx.admin.entity.ReminderEventCommon;
import com.pig4cloud.pigx.admin.service.ReminderEventCommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 代办提醒
 *
 * @author gaoxiao
 * @date 2020-07-09 09:00:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/remindereventcommon" )
@Api(value = "remindereventcommon", tags = "代办提醒管理")
public class ReminderEventCommonController {

    private final  ReminderEventCommonService reminderEventCommonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param reminderEventCommon 代办提醒
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getReminderEventCommonPage(Page page, ReminderEventCommon reminderEventCommon) {
        return R.ok(reminderEventCommonService.page(page, Wrappers.query(reminderEventCommon)));
    }


    /**
     * 通过id查询代办提醒
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(reminderEventCommonService.getById(id));
    }

    /**
     * 新增代办提醒
     * @param reminderEventCommon 代办提醒
     * @return R
     */
    @ApiOperation(value = "新增代办提醒", notes = "新增代办提醒")
    @SysLog("新增代办提醒" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_remindereventcommon_add')" )
    public R save(@RequestBody ReminderEventCommon reminderEventCommon) {
        return R.ok(reminderEventCommonService.save(reminderEventCommon));
    }

    /**
     * 修改代办提醒
     * @param reminderEventCommon 代办提醒
     * @return R
     */
    @ApiOperation(value = "修改代办提醒", notes = "修改代办提醒")
    @SysLog("修改代办提醒" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_remindereventcommon_edit')" )
    public R updateById(@RequestBody ReminderEventCommon reminderEventCommon) {
        return R.ok(reminderEventCommonService.updateById(reminderEventCommon));
    }

    /**
     * 通过id删除代办提醒
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除代办提醒", notes = "通过id删除代办提醒")
    @SysLog("通过id删除代办提醒" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_remindereventcommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(reminderEventCommonService.removeById(id));
    }

}
