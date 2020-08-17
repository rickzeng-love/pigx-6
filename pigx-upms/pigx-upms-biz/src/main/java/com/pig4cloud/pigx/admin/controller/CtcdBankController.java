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
import com.pig4cloud.pigx.admin.entity.CtcdCitypay;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdBank;
import com.pig4cloud.pigx.admin.service.CtcdBankService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 银行
 *
 * @author gaoxiao
 * @date 2020-06-10 17:32:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbank" )
@Api(value = "ctcdbank", tags = "银行管理")
public class CtcdBankController {

    private final  CtcdBankService ctcdBankService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBank 银行
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBankPage(Page page, CtcdBank ctcdBank) {
        return R.ok(ctcdBankService.page(page, Wrappers.query(ctcdBank)));
    }

	/**
	 * 银行查询所有
	 * @param ctcdBank 银行
	 * @return
	 */
	@ApiOperation(value = "银行查询所有", notes = "银行查询所有")
	@PostMapping("/getAllCtcdBank" )
	public R getAllCtcdBank( CtcdBank ctcdBank) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdBank.setCorpcode(corpcode);
		return R.ok(ctcdBankService.list(Wrappers.query(ctcdBank)));
	}
    /**
     * 通过id查询银行
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBankService.getById(id));
    }

    /**
     * 新增银行     @PreAuthorize("@pms.hasPermission('admin_ctcdbank_add')" )
     * @param ctcdBank 银行
     * @return R
     */
    @ApiOperation(value = "新增银行", notes = "新增银行")
    @SysLog("新增银行" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdBank ctcdBank) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctcdBank.setCorpcode(corpcode);
		ctcdBank.setCorpid(corpid);

		QueryWrapper<CtcdBank> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBank.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdBankService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}

        return R.ok(ctcdBankService.save(ctcdBank));
    }

    /**
     * 修改银行     @PreAuthorize("@pms.hasPermission('admin_ctcdbank_edit')" )
     * @param ctcdBank 银行
     * @return R
     */
    @ApiOperation(value = "修改银行", notes = "修改银行")
    @SysLog("修改银行" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBank ctcdBank) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdBank> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBank.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdBank.getId());
		List list = ctcdBankService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdBank> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdBank.getId());
		return R.ok(ctcdBankService.update(ctcdBank,updateWrapper));
    }

    /**
     * 通过id删除银行
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除银行", notes = "通过id删除银行")
    @SysLog("通过id删除银行" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbank_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBankService.removeById(id));
    }

}
