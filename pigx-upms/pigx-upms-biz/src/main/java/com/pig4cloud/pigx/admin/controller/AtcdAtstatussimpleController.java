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
import com.pig4cloud.pigx.admin.entity.AtcdAtstatussimple;
import com.pig4cloud.pigx.admin.service.AtcdAtstatussimpleService;
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
 * @date 2020-08-06 15:31:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdatstatussimple" )
@Api(value = "atcdatstatussimple", tags = "管理")
public class AtcdAtstatussimpleController {

    private final  AtcdAtstatussimpleService atcdAtstatussimpleService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdAtstatussimple 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdAtstatussimplePage(Page page, AtcdAtstatussimple atcdAtstatussimple) {
        return R.ok(atcdAtstatussimpleService.page(page, Wrappers.query(atcdAtstatussimple)));
    }
	/**
	 * 查询所有
	 * @param atcdAtstatussimple
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtcdAtstatussimple" )
	public R getAllAtcdAtstatussimple(@RequestBody(required = false) AtcdAtstatussimple atcdAtstatussimple)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atcdAtstatussimple)){
			atcdAtstatussimple = new AtcdAtstatussimple();
		}
		return R.ok(atcdAtstatussimpleService.list(Wrappers.query(atcdAtstatussimple)));
	}

    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdAtstatussimpleService.getById(id));
    }

    /**
     * 新增
     * @param atcdAtstatussimple 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atcdatstatussimple_add')" )
    public R save(@RequestBody AtcdAtstatussimple atcdAtstatussimple) {
        return R.ok(atcdAtstatussimpleService.save(atcdAtstatussimple));
    }

    /**
     * 修改
     * @param atcdAtstatussimple 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atcdatstatussimple_edit')" )
    public R updateById(@RequestBody AtcdAtstatussimple atcdAtstatussimple) {
        return R.ok(atcdAtstatussimpleService.updateById(atcdAtstatussimple));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atcdatstatussimple_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcdAtstatussimpleService.removeById(id));
    }

}
