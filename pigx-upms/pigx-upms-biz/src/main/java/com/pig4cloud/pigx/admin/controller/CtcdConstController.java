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
import com.pig4cloud.pigx.admin.entity.CtcdCitypay;
import com.pig4cloud.pigx.admin.entity.Systpayrollitem;
import com.pig4cloud.pigx.admin.mapper.CtcdConstMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdConst;
import com.pig4cloud.pigx.admin.service.CtcdConstService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资常数
 *
 * @author gaoxiao
 * @date 2020-06-12 16:57:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdconst" )
@Api(value = "ctcdconst", tags = "薪资常数管理")
public class CtcdConstController {

    private final  CtcdConstService ctcdConstService;
    private final CtcdConstMapper ctcdConstMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdConst 薪资常数
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdConstPage(Page page, CtcdConst ctcdConst) {
        return R.ok(ctcdConstService.page(page, Wrappers.query(ctcdConst)));
    }
	/**
	 * 薪资常数查询所有
	 * @param ctcdConst 薪资常数
	 * @return
	 */
	@ApiOperation(value = "薪资常数查询所有", notes = "薪资常数查询所有")
	@PostMapping("/getAllCtcdConst" )
	public R getAllCtcdCitypay( CtcdConst ctcdConst) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdConst.setCorpcode(corpcode);
		return R.ok(ctcdConstService.list(Wrappers.query(ctcdConst)));
	}

    /**
     * 通过id查询薪资常数
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdConstService.getById(id));
    }

    /**
     * 新增薪资常数   @PreAuthorize("@pms.hasPermission('admin_ctcdconst_add')" )
     * @param ctcdConst 薪资常数
     * @return R
     */
    @ApiOperation(value = "新增薪资常数", notes = "新增薪资常数")
    @SysLog("新增薪资常数" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdConst ctcdConst) {

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdConst.setCorpcode(corpcode);
		ctcdConst.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdConst> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdConst.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdConstService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdConstService.save(ctcdConst));
    }

    /**
     * 修改薪资常数     @PreAuthorize("@pms.hasPermission('admin_ctcdconst_edit')" )
     * @param ctcdConst 薪资常数
     * @return R
     */
    @ApiOperation(value = "修改薪资常数", notes = "修改薪资常数")
    @SysLog("修改薪资常数" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdConst ctcdConst) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdConst> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdConst.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdConst.getId());
		List list = ctcdConstService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdConst> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdConst.getId());
        return R.ok(ctcdConstService.update(ctcdConst,updateWrapper));
    }

	/**
	 * 获取公式中薪资常数和函数树
	 * @return
	 */
	@ApiOperation(value = "获取公式中薪资常数和函数树", notes = "获取公式中薪资常数和函数树")
	@PostMapping("/getCtcdConstForFormulaTree" )
	public R getCtcdConstForFormulaTree() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		CtcdConst ctcdConst = new CtcdConst();
		ctcdConst.setCorpcode(corpcode);
		List formulaList = ctcdConstMapper.listCtcdConstForFormula(ctcdConst);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("系统数据源");
		return R.ok(TreeUtil.findChildren2(treeOrg,formulaList));

	}

 /*   *//**
     * 通过id删除薪资常数
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除薪资常数", notes = "通过id删除薪资常数")
    @SysLog("通过id删除薪资常数" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdconst_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdConstService.removeById(id));
    }
*/
}
