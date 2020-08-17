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
import com.pig4cloud.pigx.admin.entity.EtcdCerttype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdConproperty;
import com.pig4cloud.pigx.admin.service.EtcdConpropertyService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 合同属性
 *
 * @author gaoxiao
 * @date 2020-06-23 16:32:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdconproperty" )
@Api(value = "etcdconproperty", tags = "合同属性管理")
public class EtcdConpropertyController {

    private final  EtcdConpropertyService etcdConpropertyService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdConproperty 合同属性
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdConpropertyPage(Page page, EtcdConproperty etcdConproperty) {
        return R.ok(etcdConpropertyService.page(page, Wrappers.query(etcdConproperty)));
    }

	/**
	 * 合同属性查询所有
	 * @param etcdConproperty 合同属性
	 * @return
	 */
	@ApiOperation(value = "合同属性查询所有", notes = "合同属性查询所有")
	@PostMapping("/getAllEtcdConproperty" )
	public R getAllOtcdEmpgrade( EtcdConproperty etcdConproperty) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdConproperty.setCorpcode(corpcode);
		return R.ok(etcdConpropertyService.list(Wrappers.query(etcdConproperty)));
	}
    /**
     * 通过id查询合同属性
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdConpropertyService.getById(id));
    }

    /**
     * 新增合同属性      @PreAuthorize("@pms.hasPermission('admin_etcdconproperty_add')" )
     * @param etcdConproperty 合同属性
     * @return R
     */
    @ApiOperation(value = "新增合同属性", notes = "新增合同属性")
    @SysLog("新增合同属性" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdConproperty etcdConproperty) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdConproperty.setCorpcode(corpcode);
		etcdConproperty.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdConproperty> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdConproperty.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdConpropertyService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdConpropertyService.save(etcdConproperty));
    }

    /**
     * 修改合同属性     @PreAuthorize("@pms.hasPermission('admin_etcdconproperty_edit')" )
     * @param etcdConproperty 合同属性
     * @return R
     */
    @ApiOperation(value = "修改合同属性", notes = "修改合同属性")
    @SysLog("修改合同属性" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdConproperty etcdConproperty) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdConproperty> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdConproperty.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdConproperty.getId());
		List list = etcdConpropertyService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdConproperty> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdConproperty.getId());
        return R.ok(etcdConpropertyService.update(etcdConproperty,updateWrapper));
    }

    /**
     * 通过id删除合同属性
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同属性", notes = "通过id删除合同属性")
    @SysLog("通过id删除合同属性" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdconproperty_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdConpropertyService.removeById(id));
    }

}
