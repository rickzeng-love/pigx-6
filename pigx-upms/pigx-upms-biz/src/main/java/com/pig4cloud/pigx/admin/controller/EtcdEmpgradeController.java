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
import com.pig4cloud.pigx.admin.entity.EtcdEmptype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdEmpgrade;
import com.pig4cloud.pigx.admin.service.EtcdEmpgradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 员工级别
 *
 * @author gaoxiao
 * @date 2020-06-11 15:17:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdempgrade" )
@Api(value = "etcdempgrade", tags = "员工级别管理")
public class EtcdEmpgradeController {

    private final  EtcdEmpgradeService etcdEmpgradeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdEmpgrade 员工级别
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdEmpgradePage(Page page, EtcdEmpgrade etcdEmpgrade) {
        return R.ok(etcdEmpgradeService.page(page, Wrappers.query(etcdEmpgrade)));
    }
	/**
	 * 员工级别查询所有
	 * @param etcdEmpgrade
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllEtcdEmptype" )
	public R getAllEtcdEmptype(EtcdEmpgrade etcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdEmpgrade.setCorpcode(corpcode);
		return R.ok(etcdEmpgradeService.list(Wrappers.query(etcdEmpgrade)));
	}

    /**
     * 通过id查询员工级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdEmpgradeService.getById(id));
    }

    /**
     * 新增员工级别     @PreAuthorize("@pms.hasPermission('admin_etcdempgrade_add')" )
     * @param etcdEmpgrade 员工级别
     * @return R
     */
    @ApiOperation(value = "新增员工级别", notes = "新增员工级别")
    @SysLog("新增员工级别" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdEmpgrade etcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdEmpgrade.setCorpcode(corpcode);
		etcdEmpgrade.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdEmpgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEmpgrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdEmpgradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdEmpgradeService.save(etcdEmpgrade));
    }

    /**
     * 修改员工级别      @PreAuthorize("@pms.hasPermission('admin_etcdempgrade_edit')" )
     * @param etcdEmpgrade 员工级别
     * @return R
     */
    @ApiOperation(value = "修改员工级别", notes = "修改员工级别")
    @SysLog("修改员工级别" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdEmpgrade etcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdEmpgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEmpgrade.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdEmpgrade.getId());
		List list = etcdEmpgradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdEmpgrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdEmpgrade.getId());
        return R.ok(etcdEmpgradeService.update(etcdEmpgrade,updateWrapper));
    }

    /**
     * 通过id删除员工级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除员工级别", notes = "通过id删除员工级别")
    @SysLog("通过id删除员工级别" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdempgrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdEmpgradeService.removeById(id));
    }

}
