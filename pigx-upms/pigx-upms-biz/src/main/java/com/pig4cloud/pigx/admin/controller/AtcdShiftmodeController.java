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
import com.pig4cloud.pigx.admin.entity.AtcdCalendartype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtcdShiftmode;
import com.pig4cloud.pigx.admin.service.AtcdShiftmodeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 排班策略
 *
 * @author gaoxiao
 * @date 2020-07-04 11:53:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdshiftmode" )
@Api(value = "atcdshiftmode", tags = "排班策略管理")
public class AtcdShiftmodeController {

    private final  AtcdShiftmodeService atcdShiftmodeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdShiftmode 排班策略
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdShiftmodePage(Page page, AtcdShiftmode atcdShiftmode) {
        return R.ok(atcdShiftmodeService.page(page, Wrappers.query(atcdShiftmode)));
    }


    /**
     * 通过id查询排班策略
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdShiftmodeService.getById(id));
    }

    /**
     * 新增排班策略
     * @param atcdShiftmode 排班策略
     * @return R
     */
    @ApiOperation(value = "新增排班策略", notes = "新增排班策略")
    @SysLog("新增排班策略" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atcdshiftmode_add')" )
    public R save(@RequestBody AtcdShiftmode atcdShiftmode) {
        return R.ok(atcdShiftmodeService.save(atcdShiftmode));
    }
	/**
	 * 排班策略
	 * @param atcdShiftmode 排班策略
	 * @return
	 */
	@ApiOperation(value = "排班策略", notes = "排班策略")
	@PostMapping("/getAllAtcdShiftmode" )
	public R getAllAtcdShiftmode(@RequestBody(required = false) AtcdShiftmode atcdShiftmode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atcdShiftmode)){
			atcdShiftmode = new AtcdShiftmode();
		}
		return R.ok(atcdShiftmodeService.list(Wrappers.query(atcdShiftmode)));
	}

    /**
     * 修改排班策略
     * @param atcdShiftmode 排班策略
     * @return R
     */
    @ApiOperation(value = "修改排班策略", notes = "修改排班策略")
    @SysLog("修改排班策略" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atcdshiftmode_edit')" )
    public R updateById(@RequestBody AtcdShiftmode atcdShiftmode) {
        return R.ok(atcdShiftmodeService.updateById(atcdShiftmode));
    }

    /**
     * 通过id删除排班策略
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除排班策略", notes = "通过id删除排班策略")
    @SysLog("通过id删除排班策略" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atcdshiftmode_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcdShiftmodeService.removeById(id));
    }

}
