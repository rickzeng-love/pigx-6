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
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.mapper.OtdepchangeAllMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.OtdepchangeAll;
import com.pig4cloud.pigx.admin.service.OtdepchangeAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 部门历史
 *
 * @author gaoxiao
 * @date 2020-04-07 10:09:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otdepchangeall" )
@Api(value = "otdepchangeall", tags = "部门历史管理")
public class OtdepchangeAllController {

    private final  OtdepchangeAllService otdepchangeAllService;
    private final OtdepchangeAllMapper otdepchangeAllMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otdepchangeAll 部门历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtdepchangeAllPage(Page page, OtdepchangeAll otdepchangeAll) {
        return R.ok(otdepchangeAllService.page(page, Wrappers.query(otdepchangeAll)));
    }


    /**
     * 通过id查询部门历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otdepchangeAllService.getById(id));
    }

    /**
     * 新增部门历史
     * @param otdepchangeAll 部门历史
     * @return R
     */
    @ApiOperation(value = "新增部门历史", notes = "新增部门历史")
    @SysLog("新增部门历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_otdepchangeall_add')" )
    public R save(@RequestBody OtdepchangeAll otdepchangeAll) {
        return R.ok(otdepchangeAllService.save(otdepchangeAll));
    }

/*    *//**
     * 修改部门历史
     * @param otdepchangeAll 部门历史
     * @return R
     *//*
    @ApiOperation(value = "修改部门历史", notes = "修改部门历史")
    @SysLog("修改部门历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_otdepchangeall_edit')" )
    public R updateById(@RequestBody OtdepchangeAll otdepchangeAll) {
        return R.ok(otdepchangeAllService.updateById(otdepchangeAll));
    }

    *//**
     * 通过id删除部门历史
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除部门历史", notes = "通过id删除部门历史")
    @SysLog("通过id删除部门历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otdepchangeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otdepchangeAllService.removeById(id));
    }
	*/
    /**
	 * 部门历史分页查询
	 * @param page 分页对象
	 * @param otdepchangeAll 历史表
	 * @return
	 */
	@ApiOperation(value = "部门历史分页查询", notes = "部门历史分页查询")
	@PostMapping("/getOtdepchangeAll" )
	public R getOtdepchangeAll(Page page, OtdepchangeAll otdepchangeAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otdepchangeAll.setCorpcode(corpcode);
		return R.ok(otdepchangeAllMapper.listOtdepchangeAll(page,otdepchangeAll));
	}


}
