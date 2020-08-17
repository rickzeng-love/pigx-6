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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftProcess;
import com.pig4cloud.pigx.admin.service.AtshiftProcessService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 排班期间
 *
 * @author gaoxiao
 * @date 2020-07-08 14:42:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftprocess" )
@Api(value = "atshiftprocess", tags = "排班期间管理")
public class AtshiftProcessController {

    private final  AtshiftProcessService atshiftProcessService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftProcess 排班期间
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftProcessPage(Page page, AtshiftProcess atshiftProcess) {
        return R.ok(atshiftProcessService.page(page, Wrappers.query(atshiftProcess)));
    }


	/**
	 * 分页查询
	 * @param atshiftProcess 排班期间
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getAtshiftProcessList" )
	public R getAtshiftProcessList(@RequestBody(required = false) AtshiftProcess atshiftProcess) {
		if(StringUtils.isEmpty(atshiftProcess)){
			atshiftProcess = new AtshiftProcess();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atshiftProcess.setCorpcode(corpcode);
		QueryWrapper<AtshiftProcess> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("term",atshiftProcess.getTerm());
		return R.ok(atshiftProcessService.list(queryWrapper));
	}


    /**
     * 通过id查询排班期间
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(atshiftProcessService.getById(term));
    }

    /**
     * 新增排班期间
     * @param atshiftProcess 排班期间
     * @return R
     */
    @ApiOperation(value = "新增排班期间", notes = "新增排班期间")
    @SysLog("新增排班期间" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftprocess_add')" )
    public R save(@RequestBody AtshiftProcess atshiftProcess) {
        return R.ok(atshiftProcessService.save(atshiftProcess));
    }

   /* *//**
     * 修改排班期间
     * @param atshiftProcess 排班期间
     * @return R
     *//*
    @ApiOperation(value = "修改排班期间", notes = "修改排班期间")
    @SysLog("修改排班期间" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftprocess_edit')" )
    public R updateById(@RequestBody AtshiftProcess atshiftProcess) {
        return R.ok(atshiftProcessService.updateById(atshiftProcess));
    }

    *//**
     * 通过id删除排班期间
     * @param term id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除排班期间", notes = "通过id删除排班期间")
    @SysLog("通过id删除排班期间" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftprocess_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(atshiftProcessService.removeById(term));
    }
*/
}
