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
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdBentype;
import com.pig4cloud.pigx.admin.service.CtcdBentypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 福利类型
 *
 * @author gaoxiao
 * @date 2020-06-10 13:48:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbentype" )
@Api(value = "ctcdbentype", tags = "福利类型管理")
public class CtcdBentypeController {

    private final  CtcdBentypeService ctcdBentypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBentype 福利类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBentypePage(Page page, CtcdBentype ctcdBentype) {
        return R.ok(ctcdBentypeService.page(page, Wrappers.query(ctcdBentype)));
    }
	/**
	 * 福利类型查询所有
	 * @param ctcdBentype 福利类型
	 * @return
	 */
	@ApiOperation(value = "福利类型查询所有", notes = "福利类型查询所有")
	@PostMapping("/getAllCtcdBentype" )
	public R getAllCtcdBentype(@RequestBody(required = false) CtcdBentype ctcdBentype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		return R.ok(ctcdBentypeService.list(Wrappers.query(ctcdBentype)));
	}

    /**
     * 通过id查询福利类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBentypeService.getById(id));
    }

    /**
     * 新增福利类型
     * @param ctcdBentype 福利类型     @PreAuthorize("@pms.hasPermission('admin_ctcdbentype_add')" )
     * @return R
     */
    @ApiOperation(value = "新增福利类型", notes = "新增福利类型")
    @SysLog("新增福利类型" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdBentype ctcdBentype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdBentype.setCorpcode(corpcode);
		ctcdBentype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<CtcdBentype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBentype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = ctcdBentypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(ctcdBentypeService.save(ctcdBentype));
    }

    /**
     * 修改福利类型
     * @param ctcdBentype 福利类型     @PreAuthorize("@pms.hasPermission('admin_ctcdbentype_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改福利类型", notes = "修改福利类型")
    @SysLog("修改福利类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBentype ctcdBentype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtcdBentype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",ctcdBentype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctcdBentype.getId());
		List list = ctcdBentypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<CtcdBentype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdBentype.getId());
        return R.ok(ctcdBentypeService.update(ctcdBentype,updateWrapper));
    }

    /**
     * 通过id删除福利类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除福利类型", notes = "通过id删除福利类型")
    @SysLog("通过id删除福利类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbentype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBentypeService.removeById(id));
    }

}
