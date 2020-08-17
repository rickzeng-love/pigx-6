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
import com.pig4cloud.pigx.admin.entity.CtcdBensupplyreason;
import com.pig4cloud.pigx.admin.service.CtcdBensupplyreasonService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 福利补缴原因
 *
 * @author gaoxiao
 * @date 2020-06-10 14:04:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbensupplyreason" )
@Api(value = "ctcdbensupplyreason", tags = "福利补缴原因管理")
public class CtcdBensupplyreasonController {

    private final  CtcdBensupplyreasonService ctcdBensupplyreasonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBensupplyreason 福利补缴原因
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBensupplyreasonPage(Page page, CtcdBensupplyreason ctcdBensupplyreason) {
        return R.ok(ctcdBensupplyreasonService.page(page, Wrappers.query(ctcdBensupplyreason)));
    }
	/**
	 * 福利补缴原因查询所有
	 * @param ctcdBensupplyreason 福利补缴原因
	 * @return
	 */
	@ApiOperation(value = "福利补缴原因查询所有", notes = "福利补缴原因查询所有")
	@PostMapping("/getAllCtcdBensupplyreason" )
	public R getAllCtcdBentype(CtcdBensupplyreason ctcdBensupplyreason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdBensupplyreason.setCorpcode(corpcode);
		return R.ok(ctcdBensupplyreasonService.list(Wrappers.query(ctcdBensupplyreason)));
	}

    /**
     * 通过id查询福利补缴原因
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBensupplyreasonService.getById(id));
    }

    /**
     * 新增福利补缴原因  @PreAuthorize("@pms.hasPermission('admin_ctcdbensupplyreason_add')" )
     * @param ctcdBensupplyreason 福利补缴原因
     * @return R
     */
    @ApiOperation(value = "新增福利补缴原因", notes = "新增福利补缴原因")
    @SysLog("新增福利补缴原因" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdBensupplyreason ctcdBensupplyreason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctcdBensupplyreason.setCorpcode(corpcode);
		ctcdBensupplyreason.setCorpid(corpid);
		QueryWrapper<CtcdBensupplyreason> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBensupplyreason.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdBensupplyreasonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdBensupplyreasonService.save(ctcdBensupplyreason));
    }

    /**
     * 修改福利补缴原因     @PreAuthorize("@pms.hasPermission('admin_ctcdbensupplyreason_edit')" )
     * @param ctcdBensupplyreason 福利补缴原因
     * @return R
     */
    @ApiOperation(value = "修改福利补缴原因", notes = "修改福利补缴原因")
    @SysLog("修改福利补缴原因" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBensupplyreason ctcdBensupplyreason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdBensupplyreason> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBensupplyreason.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdBensupplyreason.getId());
		List list = ctcdBensupplyreasonService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdBensupplyreason> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdBensupplyreason.getId());
        return R.ok(ctcdBensupplyreasonService.update(ctcdBensupplyreason,updateWrapper));
    }

    /**
     * 通过id删除福利补缴原因
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除福利补缴原因", notes = "通过id删除福利补缴原因")
    @SysLog("通过id删除福利补缴原因" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbensupplyreason_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBensupplyreasonService.removeById(id));
    }

}
