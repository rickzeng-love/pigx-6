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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtcdCountry;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdLeavereason;
import com.pig4cloud.pigx.admin.service.EtcdLeavereasonService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 离职原因
 *
 * @author gaoxiao
 * @date 2020-04-27 14:01:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdleavereason" )
@Api(value = "etcdleavereason", tags = "离职原因管理")
public class EtcdLeavereasonController {

    private final  EtcdLeavereasonService etcdLeavereasonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdLeavereason 离职原因
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdLeavereasonPage(Page page, EtcdLeavereason etcdLeavereason) {
        return R.ok(etcdLeavereasonService.page(page, Wrappers.query(etcdLeavereason)));
    }

	/**
	 * 查询所有
	 * @param etcdLeavereason
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtcdLeavereason" )
	public R getAllEtcdLeavereason( EtcdLeavereason etcdLeavereason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etemployee.setCorpcode(corpcode);
		return R.ok(etcdLeavereasonService.list(Wrappers.query(etcdLeavereason).orderByAsc("xorder")));
	}

    /**
     * 通过id查询离职原因
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdLeavereasonService.getById(id));
    }

    /**
     * 新增离职原因     @PreAuthorize("@pms.hasPermission('admin_etcdleavereason_add')" )
     * @param etcdLeavereason 离职原因
     * @return R
     */
    @ApiOperation(value = "新增离职原因", notes = "新增离职原因")
    @SysLog("新增离职原因" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdLeavereason etcdLeavereason) {
		QueryWrapper<EtcdLeavereason> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdLeavereason.getTitle());
		List list = etcdLeavereasonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdLeavereasonService.save(etcdLeavereason));
    }

    /**
     * 修改离职原因
     * @param etcdLeavereason 离职原因     @PreAuthorize("@pms.hasPermission('admin_etcdleavereason_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改离职原因", notes = "修改离职原因")
    @SysLog("修改离职原因" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdLeavereason etcdLeavereason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdLeavereason> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdLeavereason.getTitle());
		queryWrapper.ne("id",etcdLeavereason.getId());
		List list = etcdLeavereasonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdLeavereason> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdLeavereason.getId());
        return R.ok(etcdLeavereasonService.update(etcdLeavereason,updateWrapper));
    }

    /**
     * 通过id删除离职原因
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除离职原因", notes = "通过id删除离职原因")
    @SysLog("通过id删除离职原因" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdleavereason_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdLeavereasonService.removeById(id));
    }

}
