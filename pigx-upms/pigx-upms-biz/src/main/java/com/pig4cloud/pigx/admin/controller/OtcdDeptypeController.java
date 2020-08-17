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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.OtcdJobtype;
import com.pig4cloud.pigx.admin.mapper.OtcdDeptypeMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.OtcdDeptype;
import com.pig4cloud.pigx.admin.service.OtcdDeptypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 部门类型
 *
 * @author gaoxiao
 * @date 2020-06-24 16:06:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcddeptype" )
@Api(value = "otcddeptype", tags = "部门类型管理")
public class OtcdDeptypeController {

    private final  OtcdDeptypeService otcdDeptypeService;
    private final OtcdDeptypeMapper otcdDeptypeMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdDeptype 部门类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdDeptypePage(Page page, OtcdDeptype otcdDeptype) {
        return R.ok(otcdDeptypeService.page(page, Wrappers.query(otcdDeptype)));
    }

	/**
	 * 分页查询
	 * @param otcdDeptype 部门类型
	 * @return
	 */
	@ApiOperation(value = "部门类型查询", notes = "部门类型查询")
	@PostMapping("/getOtcdDeptypeAll" )
	public R getOtcdDeptypeAll(OtcdDeptype otcdDeptype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdDeptype.setCorpcode(corpcode);
		return R.ok(otcdDeptypeService.list(Wrappers.query(otcdDeptype)));
	}

	/**
	 * 查询启用中部门类型
	 * @param otcdDeptype 部门类型
	 * @return
	 */
	@ApiOperation(value = "查询启用中部门类型", notes = "查询启用中部门类型")
	@PostMapping("/getOtcdDeptypeEnable" )
	public R getOtcdDeptypeEnable(@RequestBody(required = false) OtcdDeptype otcdDeptype) {
		if(otcdDeptype==null){
			otcdDeptype=new OtcdDeptype();
		}
		otcdDeptype.setIsdisabled(0);
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdDeptype.setCorpcode(corpcode);
		return R.ok(otcdDeptypeService.list(Wrappers.query(otcdDeptype)));
	}

    /**
     * 通过id查询部门类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdDeptypeService.getById(id));
    }

    /**
     * 新增部门类型      @PreAuthorize("@pms.hasPermission('admin_otcddeptype_add')" )
     * @param otcdDeptype 部门类型
     * @return R
     */
    @ApiOperation(value = "新增部门类型", notes = "新增部门类型")
    @SysLog("新增部门类型" )
    @PostMapping("/save")
    public R save(@RequestBody OtcdDeptype otcdDeptype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdDeptype.setCorpcode(corpcode);
		otcdDeptype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<OtcdDeptype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",otcdDeptype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = otcdDeptypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(otcdDeptypeService.save(otcdDeptype));
    }

    /**
     * 修改部门类型     @PreAuthorize("@pms.hasPermission('admin_otcddeptype_edit')" )
     * @param otcdDeptype 部门类型
     * @return R
     */
    @ApiOperation(value = "修改部门类型", notes = "修改部门类型")
    @SysLog("修改部门类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody OtcdDeptype otcdDeptype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<OtcdDeptype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",otcdDeptype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",otcdDeptype.getId());
		List list = otcdDeptypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<OtcdDeptype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdDeptype.getId());
        return R.ok(otcdDeptypeService.update(otcdDeptype,updateWrapper));
    }
/*

    */
/**
     * 通过id删除部门类型
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除部门类型", notes = "通过id删除部门类型")
    @SysLog("通过id删除部门类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcddeptype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdDeptypeService.removeById(id));
    }
*/

}
