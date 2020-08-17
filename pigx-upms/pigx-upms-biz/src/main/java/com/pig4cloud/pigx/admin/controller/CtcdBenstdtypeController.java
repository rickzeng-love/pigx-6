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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.CtcdBeartype;
import com.pig4cloud.pigx.admin.entity.CtcdBenrate;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdBenstdtype;
import com.pig4cloud.pigx.admin.service.CtcdBenstdtypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 基数类型
 *
 * @author gaoxiao
 * @date 2020-06-10 16:57:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbenstdtype" )
@Api(value = "ctcdbenstdtype", tags = "基数类型管理")
public class CtcdBenstdtypeController {

    private final  CtcdBenstdtypeService ctcdBenstdtypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBenstdtype 基数类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBenstdtypePage(Page page, CtcdBenstdtype ctcdBenstdtype) {
        return R.ok(ctcdBenstdtypeService.page(page, Wrappers.query(ctcdBenstdtype)));
    }
	/**
	 * 基数类型查询所有
	 * @param ctcdBenstdtype 基数类型
	 * @return
	 */
	@ApiOperation(value = "基数类型查询所有", notes = "基数类型查询所有")
	@PostMapping("/getAllCtcdBeartype" )
	public R getAllCtcdBeartype(CtcdBenstdtype ctcdBenstdtype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//ctcdBenstdtype.setCorpcode(corpcode);
		return R.ok(ctcdBenstdtypeService.list(Wrappers.query(ctcdBenstdtype)));
	}


    /**
     * 通过id查询基数类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBenstdtypeService.getById(id));
    }

    /**
     * 新增基数类型     @PreAuthorize("@pms.hasPermission('admin_ctcdbenstdtype_add')" )
     * @param ctcdBenstdtype 基数类型
     * @return R
     */
    @ApiOperation(value = "新增基数类型", notes = "新增基数类型")
    @SysLog("新增基数类型" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdBenstdtype ctcdBenstdtype) {
		QueryWrapper<CtcdBenstdtype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBenstdtype.getTitle());
		List list = ctcdBenstdtypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdBenstdtypeService.save(ctcdBenstdtype));
    }

    /**
     * 修改基数类型     @PreAuthorize("@pms.hasPermission('admin_ctcdbenstdtype_edit')" )
     * @param ctcdBenstdtype 基数类型
     * @return R
     */
    @ApiOperation(value = "修改基数类型", notes = "修改基数类型")
    @SysLog("修改基数类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBenstdtype ctcdBenstdtype) {
		QueryWrapper<CtcdBenstdtype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBenstdtype.getTitle());
		queryWrapper.ne("id",ctcdBenstdtype.getId());
		List list = ctcdBenstdtypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}

        return R.ok(ctcdBenstdtypeService.updateById(ctcdBenstdtype));
    }

    /**
     * 通过id删除基数类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除基数类型", notes = "通过id删除基数类型")
    @SysLog("通过id删除基数类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbenstdtype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBenstdtypeService.removeById(id));
    }

}
