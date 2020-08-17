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
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Warn;
import com.pig4cloud.pigx.admin.service.WarnService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 关怀提醒
 *
 * @author gaoxiao
 * @date 2020-04-26 19:51:15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/warn" )
@Api(value = "warn", tags = "关怀提醒管理")
public class WarnController {

    private final  WarnService warnService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param warn 关怀提醒
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getWarnPage(Page page, Warn warn) {
        return R.ok(warnService.page(page, Wrappers.query(warn)));
    }

	/**
	 * 查询所有
	 * @param warn 员工
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllWarn" )
	public R getAllWarn(Warn warn) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		warn.setCorpcode(corpcode);
		List resultList = warnService.list(Wrappers.query(warn).orderByDesc("createdate"));
		Map map = new HashMap();
		if(resultList!=null && resultList.size()>0){
			map = (Map)map.get(0);
		}
		return R.ok(map);
	}
    /**
     * 通过id查询关怀提醒
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(warnService.getById(id));
    }

    /**
     * 新增关怀提醒
     * @param warn 关怀提醒     @PreAuthorize("@pms.hasPermission('admin_warn_add')" )
     * @return R
     */
    @ApiOperation(value = "新增关怀提醒", notes = "新增关怀提醒")
    @SysLog("新增关怀提醒" )
    @PostMapping("/save")
    public R save(@RequestBody Warn warn) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		warn.setCorpcode(corpcode);
		warnService.save(warn);
        return R.ok(warn);
    }

/*
    */
/**
     * 修改关怀提醒
     * @param warn 关怀提醒      @PreAuthorize("@pms.hasPermission('admin_warn_edit')" )
     * @return R
     */

    @ApiOperation(value = "修改关怀提醒", notes = "修改关怀提醒")
    @SysLog("修改关怀提醒" )
    @PostMapping("/updateId")
    public R updateById(@RequestBody Warn warn) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Warn> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("id",warn.getId());
        return R.ok(warnService.update(warn,queryWrapper));
    }


/**
     * 通过id删除关怀提醒
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除关怀提醒", notes = "通过id删除关怀提醒")
    @SysLog("通过id删除关怀提醒" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_warn_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(warnService.removeById(id));
    }
*/

}
