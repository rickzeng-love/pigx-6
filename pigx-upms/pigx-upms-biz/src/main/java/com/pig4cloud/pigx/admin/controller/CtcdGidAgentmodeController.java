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
import com.pig4cloud.pigx.admin.entity.AtcdAgentmode;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdGidAgentmode;
import com.pig4cloud.pigx.admin.service.CtcdGidAgentmodeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资考勤账套对应
 *
 * @author gaoxiao
 * @date 2020-07-22 14:07:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdgidagentmode" )
@Api(value = "ctcdgidagentmode", tags = "薪资考勤账套对应管理")
public class CtcdGidAgentmodeController {

    private final  CtcdGidAgentmodeService ctcdGidAgentmodeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdGidAgentmode 薪资考勤账套对应
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdGidAgentmodePage(Page page, CtcdGidAgentmode ctcdGidAgentmode) {
        return R.ok(ctcdGidAgentmodeService.page(page, Wrappers.query(ctcdGidAgentmode)));
    }

	/**
	 * 查询所有
	 * @param ctcdGidAgentmode
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdGidAgentmode" )
	public R getAllCtcdGidAgentmode(@RequestBody(required = false) CtcdGidAgentmode ctcdGidAgentmode)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(ctcdGidAgentmode)){
			ctcdGidAgentmode = new CtcdGidAgentmode();
		}
		ctcdGidAgentmode.setCorpcode(corpCode);
		return R.ok(ctcdGidAgentmodeService.list(Wrappers.query(ctcdGidAgentmode)));
	}


    /**
     * 通过id查询薪资考勤账套对应
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdGidAgentmodeService.getById(id));
    }

    /**
     * 新增薪资考勤账套对应
     * @param ctcdGidAgentmode 薪资考勤账套对应
     * @return R
     */
    @ApiOperation(value = "新增薪资考勤账套对应", notes = "新增薪资考勤账套对应")
    @SysLog("新增薪资考勤账套对应" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdgidagentmode_add')" )
    public R save(@RequestBody CtcdGidAgentmode ctcdGidAgentmode) {
        return R.ok(ctcdGidAgentmodeService.save(ctcdGidAgentmode));
    }

    /**
     * 修改薪资考勤账套对应
     * @param ctcdGidAgentmode 薪资考勤账套对应
     * @return R
     */
    @ApiOperation(value = "修改薪资考勤账套对应", notes = "修改薪资考勤账套对应")
    @SysLog("修改薪资考勤账套对应" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctcdgidagentmode_edit')" )
    public R updateById(@RequestBody CtcdGidAgentmode ctcdGidAgentmode) {
        return R.ok(ctcdGidAgentmodeService.updateById(ctcdGidAgentmode));
    }

    /**
     * 通过id删除薪资考勤账套对应
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资考勤账套对应", notes = "通过id删除薪资考勤账套对应")
    @SysLog("通过id删除薪资考勤账套对应" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdgidagentmode_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdGidAgentmodeService.removeById(id));
    }

}
