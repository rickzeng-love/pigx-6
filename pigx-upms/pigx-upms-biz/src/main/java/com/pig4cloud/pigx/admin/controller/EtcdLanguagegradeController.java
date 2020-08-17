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
import com.pig4cloud.pigx.admin.entity.EtcdLanguagetype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdLanguagegrade;
import com.pig4cloud.pigx.admin.service.EtcdLanguagegradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 语言级别
 *
 * @author gaoxiao
 * @date 2020-06-25 14:51:17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdlanguagegrade" )
@Api(value = "etcdlanguagegrade", tags = "语言级别管理")
public class EtcdLanguagegradeController {

    private final  EtcdLanguagegradeService etcdLanguagegradeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdLanguagegrade 语言级别
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdLanguagegradePage(Page page, EtcdLanguagegrade etcdLanguagegrade) {
        return R.ok(etcdLanguagegradeService.page(page, Wrappers.query(etcdLanguagegrade)));
    }
	/**
	 * 查询所有
	 * @param etcdLanguagegrade
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllEtcdLanguagegrade" )
	public R getAllEtcdLanguagetype(EtcdLanguagegrade etcdLanguagegrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdLanguagegrade.setCorpcode(corpcode);
		return R.ok(etcdLanguagegradeService.list(Wrappers.query(etcdLanguagegrade).orderByAsc("xorder")));
	}

    /**
     * 通过id查询语言级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdLanguagegradeService.getById(id));
    }

    /**
     * 新增语言级别     @PreAuthorize("@pms.hasPermission('admin_etcdlanguagegrade_add')" )
     * @param etcdLanguagegrade 语言级别
     * @return R
     */
    @ApiOperation(value = "新增语言级别", notes = "新增语言级别")
    @SysLog("新增语言级别" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdLanguagegrade etcdLanguagegrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdLanguagegrade.setCorpcode(corpcode);
		etcdLanguagegrade.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdLanguagegrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdLanguagegrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdLanguagegrade.getId());
		List list = etcdLanguagegradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}

        return R.ok(etcdLanguagegradeService.save(etcdLanguagegrade));
    }

    /**
     * 修改语言级别      @PreAuthorize("@pms.hasPermission('admin_etcdlanguagegrade_edit')" )
     * @param etcdLanguagegrade 语言级别
     * @return R
     */
    @ApiOperation(value = "修改语言级别", notes = "修改语言级别")
    @SysLog("修改语言级别" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdLanguagegrade etcdLanguagegrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdLanguagegrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdLanguagegrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdLanguagegradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdLanguagegrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdLanguagegrade.getId());
        return R.ok(etcdLanguagegradeService.update(etcdLanguagegrade,updateWrapper));
    }

    /**
     * 通过id删除语言级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除语言级别", notes = "通过id删除语言级别")
    @SysLog("通过id删除语言级别" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdlanguagegrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdLanguagegradeService.removeById(id));
    }

}
