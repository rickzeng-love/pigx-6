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
import com.pig4cloud.pigx.admin.entity.EtcdCertgrade;
import com.pig4cloud.pigx.admin.service.EtcdCertgradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 证件级别
 *
 * @author gaoxiao
 * @date 2020-06-22 11:22:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcertgrade" )
@Api(value = "etcdcertgrade", tags = "证件级别管理")
public class EtcdCertgradeController {

    private final  EtcdCertgradeService etcdCertgradeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdCertgrade 证件级别
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdCertgradePage(Page page, EtcdCertgrade etcdCertgrade) {
        return R.ok(etcdCertgradeService.page(page, Wrappers.query(etcdCertgrade)));
    }
	/**
	 * 证件类型查询所有
	 * @param etcdCertgrade 员工
	 * @return
	 */
	@ApiOperation(value = "证件及別查询所有", notes = "证件及別查询所有")
	@GetMapping("/getAllEtcdCertgrade" )
	public R getAllEtcdCertgrade(EtcdCertgrade etcdCertgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdCertgrade.setCorpcode(corpcode);
		QueryWrapper<EtcdCertgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCertgrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdCertgradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		return R.ok(etcdCertgradeService.list(Wrappers.query(etcdCertgrade)));
	}

    /**
     * 通过id查询证件级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdCertgradeService.getById(id));
    }

    /**
     * 新增证件级别     @PreAuthorize("@pms.hasPermission('admin_etcdcertgrade_add')" )
     * @param etcdCertgrade 证件级别
     * @return R
     */
    @ApiOperation(value = "新增证件级别", notes = "新增证件级别")
    @SysLog("新增证件级别" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdCertgrade etcdCertgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdCertgrade.setCorpcode(corpcode);
		etcdCertgrade.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdCertgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCertgrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdCertgradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdCertgradeService.save(etcdCertgrade));
    }

    /**
     * 修改证件级别     @PreAuthorize("@pms.hasPermission('admin_etcdcertgrade_edit')" )
     * @param etcdCertgrade 证件级别
     * @return R
     */
    @ApiOperation(value = "修改证件级别", notes = "修改证件级别")
    @SysLog("修改证件级别" )
    @PostMapping("updateById")
    public R updateById(@RequestBody EtcdCertgrade etcdCertgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdCertgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCertgrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdCertgrade.getId());
		List list = etcdCertgradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdCertgrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdCertgrade.getId());
        return R.ok(etcdCertgradeService.update(etcdCertgrade,updateWrapper));
    }

    /**
     * 通过id删除证件级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除证件级别", notes = "通过id删除证件级别")
    @SysLog("通过id删除证件级别" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdcertgrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdCertgradeService.removeById(id));
    }

}
