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
import com.pig4cloud.pigx.admin.entity.CtcdBenrate;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdCostcenter;
import com.pig4cloud.pigx.admin.service.CtcdCostcenterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 成本中心
 *
 * @author gaoxiao
 * @date 2020-06-10 14:07:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdcostcenter" )
@Api(value = "ctcdcostcenter", tags = "成本中心管理")
public class CtcdCostcenterController {

    private final  CtcdCostcenterService ctcdCostcenterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdCostcenter 成本中心
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdCostcenterPage(Page page, CtcdCostcenter ctcdCostcenter) {
        return R.ok(ctcdCostcenterService.page(page, Wrappers.query(ctcdCostcenter)));
    }

	/**
	 * 成本中心查询所有
	 * @param ctcdCostcenter 成本中心
	 * @return
	 */
	@ApiOperation(value = "成本中心查询所有", notes = "成本中心查询所有")
	@PostMapping("/getAllCtcdCostcenter" )
	public R getAllCtcdCostcenter(CtcdCostcenter ctcdCostcenter) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdCostcenter.setCorpcode(corpcode);
		return R.ok(ctcdCostcenterService.list(Wrappers.query(ctcdCostcenter)));
	}

    /**
     * 通过id查询成本中心
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdCostcenterService.getById(id));
    }

    /**
     * 新增成本中心     @PreAuthorize("@pms.hasPermission('admin_ctcdcostcenter_add')" )
     * @param ctcdCostcenter 成本中心
     * @return R
     */
    @ApiOperation(value = "新增成本中心", notes = "新增成本中心")
    @SysLog("新增成本中心" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdCostcenter ctcdCostcenter) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctcdCostcenter.setCorpcode(corpcode);
		ctcdCostcenter.setCorpid(corpid);
		QueryWrapper<CtcdCostcenter> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdCostcenter.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdCostcenterService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdCostcenterService.save(ctcdCostcenter));
    }

    /**
     * 修改成本中心     @PreAuthorize("@pms.hasPermission('admin_ctcdcostcenter_edit')" )
     * @param ctcdCostcenter 成本中心
     * @return R
     */
    @ApiOperation(value = "修改成本中心", notes = "修改成本中心")
    @SysLog("修改成本中心" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdCostcenter ctcdCostcenter) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdCostcenter> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdCostcenter.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdCostcenter.getId());
		List list = ctcdCostcenterService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdCostcenter> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdCostcenter.getId());
        return R.ok(ctcdCostcenterService.update(ctcdCostcenter,updateWrapper));
    }

    /**
     * 通过id删除成本中心
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除成本中心", notes = "通过id删除成本中心")
    @SysLog("通过id删除成本中心" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdcostcenter_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdCostcenterService.removeById(id));
    }

}
