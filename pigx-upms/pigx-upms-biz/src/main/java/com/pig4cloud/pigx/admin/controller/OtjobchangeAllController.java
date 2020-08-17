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
import com.pig4cloud.pigx.admin.entity.OtdepchangeAll;
import com.pig4cloud.pigx.admin.mapper.OtjobchangeAllMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.OtjobchangeAll;
import com.pig4cloud.pigx.admin.service.OtjobchangeAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 岗位历史
 *
 * @author gaoxiao
 * @date 2020-04-07 16:29:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otjobchangeall" )
@Api(value = "otjobchangeall", tags = "岗位历史管理")
public class OtjobchangeAllController {

    private final  OtjobchangeAllService otjobchangeAllService;
    private final OtjobchangeAllMapper otjobchangeAllMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otjobchangeAll 岗位历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtjobchangeAllPage(Page page, OtjobchangeAll otjobchangeAll) {
        return R.ok(otjobchangeAllService.page(page, Wrappers.query(otjobchangeAll)));
    }


    /**
     * 通过id查询岗位历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otjobchangeAllService.getById(id));
    }

    /**
     * 新增岗位历史
     * @param otjobchangeAll 岗位历史
     * @return R
     */
    @ApiOperation(value = "新增岗位历史", notes = "新增岗位历史")
    @SysLog("新增岗位历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otjobchangeall_add')" )
    public R save(@RequestBody OtjobchangeAll otjobchangeAll) {
        return R.ok(otjobchangeAllService.save(otjobchangeAll));
    }

/*    *//**
     * 修改岗位历史
     * @param otjobchangeAll 岗位历史
     * @return R
     *//*
    @ApiOperation(value = "修改岗位历史", notes = "修改岗位历史")
    @SysLog("修改岗位历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otjobchangeall_edit')" )
    public R updateById(@RequestBody OtjobchangeAll otjobchangeAll) {
        return R.ok(otjobchangeAllService.updateById(otjobchangeAll));
    }

    *//**
     * 通过id删除岗位历史
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除岗位历史", notes = "通过id删除岗位历史")
    @SysLog("通过id删除岗位历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otjobchangeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otjobchangeAllService.removeById(id));
    }
	*/
    /**
	 * 岗位历史分页查询
	 * @param page 分页对象
	 * @param otjobchangeAll 历史表
	 * @return
	 */
	@ApiOperation(value = "" +
			"", notes = "岗位历史分页查询")
	@PostMapping("/getOtjobchangeAll" )
	public R getOtjobchangeAll(Page page, OtjobchangeAll otjobchangeAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otjobchangeAll.setCorpcode(corpcode);
		return R.ok(otjobchangeAllMapper.listOtjobchangeAll(page,otjobchangeAll));
	}
}
