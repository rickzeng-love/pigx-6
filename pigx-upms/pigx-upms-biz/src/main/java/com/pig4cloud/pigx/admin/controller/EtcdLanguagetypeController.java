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
import com.pig4cloud.pigx.admin.entity.EtcdLeavereason;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdLanguagetype;
import com.pig4cloud.pigx.admin.service.EtcdLanguagetypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 语言种类
 *
 * @author gaoxiao
 * @date 2020-06-24 17:02:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdlanguagetype" )
@Api(value = "etcdlanguagetype", tags = "语言种类管理")
public class EtcdLanguagetypeController {

    private final  EtcdLanguagetypeService etcdLanguagetypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdLanguagetype 语言种类
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdLanguagetypePage(Page page, EtcdLanguagetype etcdLanguagetype) {
        return R.ok(etcdLanguagetypeService.page(page, Wrappers.query(etcdLanguagetype)));
    }

	/**
	 * 查询所有
	 * @param etcdLanguagetype
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllEtcdLanguagetype" )
	public R getAllEtcdLanguagetype( EtcdLanguagetype etcdLanguagetype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdLanguagetype.setCorpcode(corpcode);
		return R.ok(etcdLanguagetypeService.list(Wrappers.query(etcdLanguagetype).orderByAsc("xorder")));
	}
    /**
     * 通过id查询语言种类
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdLanguagetypeService.getById(id));
    }

    /**
     * 新增语言种类      @PreAuthorize("@pms.hasPermission('admin_etcdlanguagetype_add')" )
     * @param etcdLanguagetype 语言种类
     * @return R
     */
    @ApiOperation(value = "新增语言种类", notes = "新增语言种类")
    @SysLog("新增语言种类" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdLanguagetype etcdLanguagetype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdLanguagetype.setCorpcode(corpcode);
		etcdLanguagetype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdLanguagetype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdLanguagetype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdLanguagetypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdLanguagetypeService.save(etcdLanguagetype));
    }

    /**
     * 修改语言种类
     * @param etcdLanguagetype 语言种类
     * @return R
     */
    @ApiOperation(value = "修改语言种类", notes = "修改语言种类")
    @SysLog("修改语言种类" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdLanguagetype etcdLanguagetype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
    	QueryWrapper<EtcdLanguagetype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdLanguagetype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdLanguagetype.getId());
		List list = etcdLanguagetypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdLanguagetype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdLanguagetype.getId());
        return R.ok(etcdLanguagetypeService.update(etcdLanguagetype,updateWrapper));
    }

    /**
     * 通过id删除语言种类
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除语言种类", notes = "通过id删除语言种类")
    @SysLog("通过id删除语言种类" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdlanguagetype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdLanguagetypeService.removeById(id));
    }

}
