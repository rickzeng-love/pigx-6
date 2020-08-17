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
import com.pig4cloud.pigx.admin.entity.EtcdCity;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtcdCalendartype;
import com.pig4cloud.pigx.admin.service.AtcdCalendartypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-07-22 13:35:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdcalendartype" )
@Api(value = "atcdcalendartype", tags = "日历类型管理")
public class AtcdCalendartypeController {

    private final  AtcdCalendartypeService atcdCalendartypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdCalendartype 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdCalendartypePage(Page page, AtcdCalendartype atcdCalendartype) {
        return R.ok(atcdCalendartypeService.page(page, Wrappers.query(atcdCalendartype)));
    }

	/**
	 * 日历类型
	 * @param atcdCalendartype 日历类型
	 * @return
	 */
	@ApiOperation(value = "日历类型", notes = "日历类型")
	@PostMapping("/getAllAtcdCalendartype" )
	public R getAllAtcdCalendartype(@RequestBody(required = false)  AtcdCalendartype atcdCalendartype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atcdCalendartype)){
			atcdCalendartype = new AtcdCalendartype();
		}
		return R.ok(atcdCalendartypeService.list(Wrappers.query(atcdCalendartype)));
	}

    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdCalendartypeService.getById(id));
    }

    /**
     * 新增
     * @param atcdCalendartype 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atcdcalendartype_add')" )
    public R save(@RequestBody AtcdCalendartype atcdCalendartype) {
        return R.ok(atcdCalendartypeService.save(atcdCalendartype));
    }

    /**
     * 修改
     * @param atcdCalendartype 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atcdcalendartype_edit')" )
    public R updateById(@RequestBody AtcdCalendartype atcdCalendartype) {
        return R.ok(atcdCalendartypeService.updateById(atcdCalendartype));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atcdcalendartype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcdCalendartypeService.removeById(id));
    }

}
