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
import com.pig4cloud.pigx.admin.entity.CtcdBank;
import com.pig4cloud.pigx.admin.entity.CtcdSicktype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdAttendsickrate;
import com.pig4cloud.pigx.admin.service.CtcdAttendsickrateService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 病假工资比率
 *
 * @author gaoxiao
 * @date 2020-06-12 17:17:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdattendsickrate" )
@Api(value = "ctcdattendsickrate", tags = "病假工资比率管理")
public class CtcdAttendsickrateController {

    private final  CtcdAttendsickrateService ctcdAttendsickrateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdAttendsickrate 病假工资比率
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdAttendsickratePage(Page page, CtcdAttendsickrate ctcdAttendsickrate) {
        return R.ok(ctcdAttendsickrateService.page(page, Wrappers.query(ctcdAttendsickrate)));
    }

	/**
	 * 病假工资比率
	 * @param ctcdAttendsickrate
	 * @ctcdAttendsickrate
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllCtcdAttendsickrate" )
	public R getAllCtcdSicktype(  CtcdAttendsickrate ctcdAttendsickrate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdAttendsickrate.setCorpcode(corpcode);
		return R.ok(ctcdAttendsickrateService.list(Wrappers.query(ctcdAttendsickrate)));
	}
    /**
     * 通过id查询病假工资比率
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdAttendsickrateService.getById(id));
    }

    /**
     * 新增病假工资比率     @PreAuthorize("@pms.hasPermission('admin_ctcdattendsickrate_add')" )
     * @param ctcdAttendsickrate 病假工资比率
     * @return R
     */
    @ApiOperation(value = "新增病假工资比率", notes = "新增病假工资比率")
    @SysLog("新增病假工资比率" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdAttendsickrate ctcdAttendsickrate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdAttendsickrate.setCorpcode(corpcode);
		ctcdAttendsickrate.setCorpid(pigxUser.getCorpid());

        return R.ok(ctcdAttendsickrateService.save(ctcdAttendsickrate));
    }

    /**
     * 修改病假工资比率     @PreAuthorize("@pms.hasPermission('admin_ctcdattendsickrate_edit')" )
     * @param ctcdAttendsickrate 病假工资比率
     * @return R
     */
    @ApiOperation(value = "修改病假工资比率", notes = "修改病假工资比率")
    @SysLog("修改病假工资比率" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdAttendsickrate ctcdAttendsickrate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdAttendsickrate.setCorpcode(corpcode);
		UpdateWrapper<CtcdAttendsickrate> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdAttendsickrate.getId());
        return R.ok(ctcdAttendsickrateService.update(ctcdAttendsickrate,updateWrapper));
    }

    /**
     * 通过id删除病假工资比率
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除病假工资比率", notes = "通过id删除病假工资比率")
    @SysLog("通过id删除病假工资比率" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdattendsickrate_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdAttendsickrateService.removeById(id));
    }

}
