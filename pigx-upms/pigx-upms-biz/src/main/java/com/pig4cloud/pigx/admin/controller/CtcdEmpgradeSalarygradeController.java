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
import com.pig4cloud.pigx.admin.entity.CtcdCostcenter;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdEmpgradeSalarygrade;
import com.pig4cloud.pigx.admin.service.CtcdEmpgradeSalarygradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 等级对应配置
 *
 * @author gaoxiao
 * @date 2020-06-12 17:04:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdempgradesalarygrade" )
@Api(value = "ctcdempgradesalarygrade", tags = "等级对应配置管理")
public class CtcdEmpgradeSalarygradeController {

    private final  CtcdEmpgradeSalarygradeService ctcdEmpgradeSalarygradeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdEmpgradeSalarygrade 等级对应配置
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdEmpgradeSalarygradePage(Page page, CtcdEmpgradeSalarygrade ctcdEmpgradeSalarygrade) {
        return R.ok(ctcdEmpgradeSalarygradeService.page(page, Wrappers.query(ctcdEmpgradeSalarygrade)));
    }
	/**
	 * 等级对应配置查询所有
	 * @param ctcdEmpgradeSalarygrade 等级对应配置
	 * @return
	 */
	@ApiOperation(value = "等级对应配置查询所有", notes = "等级对应配置查询所有")
	@PostMapping("/getAllCtcdEmpgradeSalarygrade" )
	public R getAllCtcdEmpgradeSalarygrade(CtcdEmpgradeSalarygrade ctcdEmpgradeSalarygrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdEmpgradeSalarygrade.setCorpcode(corpcode);
		return R.ok(ctcdEmpgradeSalarygradeService.list(Wrappers.query(ctcdEmpgradeSalarygrade)));
	}

    /**
     * 通过id查询等级对应配置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdEmpgradeSalarygradeService.getById(id));
    }

    /**
     * 新增等级对应配置     @PreAuthorize("@pms.hasPermission('admin_ctcdempgradesalarygrade_add')" )
     * @param ctcdEmpgradeSalarygrade 等级对应配置
     * @return R
     */
    @ApiOperation(value = "新增等级对应配置", notes = "新增等级对应配置")
    @SysLog("新增等级对应配置" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdEmpgradeSalarygrade ctcdEmpgradeSalarygrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdEmpgradeSalarygrade.setCorpcode(corpcode);
		ctcdEmpgradeSalarygrade.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdEmpgradeSalarygrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("empgrade",ctcdEmpgradeSalarygrade.getEmpgrade());
		queryWrapper.eq("salarygrade",ctcdEmpgradeSalarygrade.getSalarygrade());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdEmpgradeSalarygradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("已存在对应的员工等级-薪资等级，请核实！");
		}

        return R.ok(ctcdEmpgradeSalarygradeService.save(ctcdEmpgradeSalarygrade));
    }

    /**
     * 修改等级对应配置     @PreAuthorize("@pms.hasPermission('admin_ctcdempgradesalarygrade_edit')" )
     * @param ctcdEmpgradeSalarygrade 等级对应配置
     * @return R
     */
    @ApiOperation(value = "修改等级对应配置", notes = "修改等级对应配置")
    @SysLog("修改等级对应配置" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdEmpgradeSalarygrade ctcdEmpgradeSalarygrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdEmpgradeSalarygrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("empgrade",ctcdEmpgradeSalarygrade.getEmpgrade());
		queryWrapper.eq("salarygrade",ctcdEmpgradeSalarygrade.getSalarygrade());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdEmpgradeSalarygrade.getId());
		List list = ctcdEmpgradeSalarygradeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("已存在对应的员工等级-薪资等级，请核实！");
		}
		UpdateWrapper<CtcdEmpgradeSalarygrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdEmpgradeSalarygrade.getId());
    	return R.ok(ctcdEmpgradeSalarygradeService.update(ctcdEmpgradeSalarygrade,updateWrapper));
    }

    /**
     * 通过id删除等级对应配置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除等级对应配置", notes = "通过id删除等级对应配置")
    @SysLog("通过id删除等级对应配置" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdempgradesalarygrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdEmpgradeSalarygradeService.removeById(id));
    }

}
