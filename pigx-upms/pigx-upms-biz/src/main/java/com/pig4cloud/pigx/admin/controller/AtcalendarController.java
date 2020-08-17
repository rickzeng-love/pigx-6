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
import com.pig4cloud.pigx.admin.mapper.AtcalendarMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Atcalendar;
import com.pig4cloud.pigx.admin.service.AtcalendarService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 日历
 *
 * @author gaoxiao
 * @date 2020-07-02 13:49:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcalendar" )
@Api(value = "atcalendar", tags = "日历管理")
public class AtcalendarController {

    private final  AtcalendarService atcalendarService;
    private final AtcalendarMapper atcalendarMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcalendar 日历
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcalendarPage(Page page, Atcalendar atcalendar) {
        return R.ok(atcalendarService.page(page, Wrappers.query(atcalendar)));
    }

	/**
	 * 分页查询
	 * @param atcalendar 日历
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getAtcalendarBySql" )
	public R getAtcalendarBySql(@RequestBody(required=false) Atcalendar atcalendar) {
		if(StringUtils.isEmpty(atcalendar)){
			atcalendar = new Atcalendar();
		}
		return R.ok(atcalendarMapper.listAtcalendarBySql(atcalendar));
	}


	/**
     * 通过id查询日历
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcalendarService.getById(id));
    }

    /**
     * 新增日历
     * @param atcalendar 日历
     * @return R
     */
    @ApiOperation(value = "新增日历", notes = "新增日历")
    @SysLog("新增日历" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atcalendar_add')" )
    public R save(@RequestBody Atcalendar atcalendar) {
        return R.ok(atcalendarService.save(atcalendar));
    }

    /**
     * 修改日历
     * @param atcalendar 日历
     * @return R
     */
    @ApiOperation(value = "修改日历", notes = "修改日历")
    @SysLog("修改日历" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atcalendar_edit')" )
    public R updateById(@RequestBody Atcalendar atcalendar) {
        return R.ok(atcalendarService.updateById(atcalendar));
    }

    /**
     * 通过id删除日历
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除日历", notes = "通过id删除日历")
    @SysLog("通过id删除日历" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atcalendar_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcalendarService.removeById(id));
    }

}
