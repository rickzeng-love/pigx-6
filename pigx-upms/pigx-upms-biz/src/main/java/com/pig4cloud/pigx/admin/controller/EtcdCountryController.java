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
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdCountry;
import com.pig4cloud.pigx.admin.service.EtcdCountryService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 国家地区
 *
 * @author gaoxiao
 * @date 2020-04-15 19:53:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcountry" )
@Api(value = "etcdcountry", tags = "国家地区管理")
public class EtcdCountryController {

    private final  EtcdCountryService etcdCountryService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdCountry 国家地区
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdCountryPage(Page page, EtcdCountry etcdCountry) {
        return R.ok(etcdCountryService.page(page, Wrappers.query(etcdCountry)));
    }
	/**
	 * 查询所有
	 * @param etcdCountry
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtcdCountry" )
	public R getAllEtcdCountry(EtcdCountry etcdCountry) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etemployee.setCorpcode(corpcode);
		return R.ok(etcdCountryService.list(Wrappers.query(etcdCountry).orderByAsc("xorder")));
	}

    /**
     * 通过id查询国家地区
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdCountryService.getById(id));
    }

    /**
     * 新增国家地区
     * @param etcdCountry 国家地区     @PreAuthorize("@pms.hasPermission('admin_etcdcountry_add')" )
     * @return R
     */
    @ApiOperation(value = "新增国家地区", notes = "新增国家地区")
    @SysLog("新增国家地区" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdCountry etcdCountry) {
		QueryWrapper<EtcdCountry> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCountry.getTitle());
		List list = etcdCountryService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdCountryService.save(etcdCountry));
    }

    /**
     * 修改国家地区
     * @param etcdCountry 国家地区      @PreAuthorize("@pms.hasPermission('admin_etcdcountry_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改国家地区", notes = "修改国家地区")
    @SysLog("修改国家地区" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdCountry etcdCountry) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdCountry> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCountry.getTitle());
		queryWrapper.ne("id",etcdCountry.getId());
		List list = etcdCountryService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdCountry> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdCountry.getId());
        return R.ok(etcdCountryService.update(etcdCountry,updateWrapper));
    }

    /**
     * 通过id删除国家地区      @PreAuthorize("@pms.hasPermission('admin_etcdcountry_del')" )
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除国家地区", notes = "通过id删除国家地区")
    @SysLog("通过id删除国家地区" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdCountryService.removeById(id));
    }

}
