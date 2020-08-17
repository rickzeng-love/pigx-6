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
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.api.vo.TreeUtil;
import com.pig4cloud.pigx.admin.entity.Systpayrollgroup;
import com.pig4cloud.pigx.admin.entity.Systpayrollobj;
import com.pig4cloud.pigx.admin.entity.SystpaystditemCommon;
import com.pig4cloud.pigx.admin.mapper.SystpayrollitemCommonMapper;
import com.pig4cloud.pigx.admin.mapper.SystpayrollitemMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.SystpayrollitemCommon;
import com.pig4cloud.pigx.admin.service.SystpayrollitemCommonService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-06-27 10:15:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollitemcommon" )
@Api(value = "systpayrollitemcommon", tags = "薪资项管理")
public class SystpayrollitemCommonController {

    private final  SystpayrollitemCommonService systpayrollitemCommonService;
    private final SystpayrollitemCommonMapper systpayrollitemCommonMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollitemCommon 薪资项
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollitemCommonPage(Page page, SystpayrollitemCommon systpayrollitemCommon) {
        return R.ok(systpayrollitemCommonService.page(page, Wrappers.query(systpayrollitemCommon)));
    }


    /**
     * 通过id查询薪资项
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systpayrollitemCommonService.getById(id));
    }

    /**
     * 新增薪资项  @PreAuthorize("@pms.hasPermission('admin_systpayrollitemcommon_add')" )
     * @param systpayrollitemCommon 薪资项
     * @return R
     */
    @ApiOperation(value = "新增薪资项", notes = "新增薪资项")
    @SysLog("新增薪资项" )
    @PostMapping("/save")
    public R save(@RequestBody SystpayrollitemCommon systpayrollitemCommon) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SystpayrollitemCommon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",systpayrollitemCommon.getTitle());
		List list = systpayrollitemCommonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("薪资项名称重复，请核实！");
		}
		systpayrollitemCommon.setCorpcode(corpcode);
		systpayrollitemCommon.setCorpid(pigxUser.getCorpid());
        return R.ok(systpayrollitemCommonService.save(systpayrollitemCommon));
    }

	/**
	 * 查询薪资项树状列表（工资项）
	 * @return
	 */
	@ApiOperation(value = "查询薪资项树状列表-工资项", notes = "查询薪资项树状列表-工资项")
	@PostMapping("/getSystpayrollitemcommonTree" )
	public R getSystpayrollitemcommonTree() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		SystpayrollitemCommon systpayrollitemCommon = new SystpayrollitemCommon();
		systpayrollitemCommon.setCorpcode(corpcode);
		List list = systpayrollitemCommonMapper.listsystpayrollitemCommonTree(systpayrollitemCommon);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("可配置项");
		return R.ok(TreeUtil.findChildren2(treeOrg,list));

	}

	/**
	 * 查询薪资项树状列表
	 * @return
	 */
	@ApiOperation(value = "薪资套选薪资项树状列表", notes = "薪资套选薪资项树状列表")
	@PostMapping("/getsystpayrollitemCommonTreeAndIschecked" )
	public R getsystpayrollitemCommonTreeAndIschecked() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		SystpayrollitemCommon systpayrollitemCommon = new SystpayrollitemCommon();
		systpayrollitemCommon.setCorpcode(corpcode);
		List list = systpayrollitemCommonMapper.listsystpayrollitemCommonTreeAndIschecked(systpayrollitemCommon);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("可配置项");
		return R.ok(TreeUtil.findChildren2(treeOrg,list));

	}

   /**
     * 修改薪资项   @PreAuthorize("@pms.hasPermission('admin_systpayrollitemcommon_edit')" )
     * @param systpayrollitemCommon 薪资项
     * @return R
     */
    @ApiOperation(value = "修改薪资项", notes = "修改薪资项")
    @SysLog("修改薪资项" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody SystpayrollitemCommon systpayrollitemCommon) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SystpayrollitemCommon> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",systpayrollitemCommon.getTitle());
		queryWrapper.ne("id",systpayrollitemCommon.getId());
		List list = systpayrollitemCommonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("薪资项名称重复，请核实！");
		}

		systpayrollitemCommon.setCorpcode(corpcode);
		UpdateWrapper<SystpayrollitemCommon> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",systpayrollitemCommon.getId());
        return R.ok(systpayrollitemCommonService.update(systpayrollitemCommon,updateWrapper));
    }

    /**
     * 通过id删除薪资项
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除薪资项", notes = "通过id删除薪资项")
    @SysLog("通过id删除薪资项" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollitemcommon_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systpayrollitemCommonService.removeById(id));
    }
*/
}
