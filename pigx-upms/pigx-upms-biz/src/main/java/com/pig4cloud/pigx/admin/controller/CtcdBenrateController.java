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
import com.pig4cloud.pigx.admin.entity.CtcdBentype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdBenrate;
import com.pig4cloud.pigx.admin.service.CtcdBenrateService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 福利比例
 *
 * @author gaoxiao
 * @date 2020-06-10 13:54:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdbenrate" )
@Api(value = "ctcdbenrate", tags = "福利比例管理")
public class CtcdBenrateController {

    private final  CtcdBenrateService ctcdBenrateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdBenrate 福利比例
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdBenratePage(Page page, CtcdBenrate ctcdBenrate) {
        return R.ok(ctcdBenrateService.page(page, Wrappers.query(ctcdBenrate)));
    }
	/**
	 * 福利比例查询所有
	 * @param ctcdBenrate 福利比例
	 * @return
	 */
	@ApiOperation(value = "福利比例查询所有", notes = "福利比例查询所有")
	@PostMapping("/getAllCtcdBenrate" )
	public R getAllCtcdBentype(CtcdBenrate ctcdBenrate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdBenrate.setCorpcode(corpcode);
		return R.ok(ctcdBenrateService.list(Wrappers.query(ctcdBenrate)));
	}

    /**
     * 通过id查询福利比例
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdBenrateService.getById(id));
    }

    /**
     * 新增福利比例     @PreAuthorize("@pms.hasPermission('admin_ctcdbenrate_add')" )
     * @param ctcdBenrate 福利比例
     * @return R
     */
    @ApiOperation(value = "新增福利比例", notes = "新增福利比例")
    @SysLog("新增福利比例" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdBenrate ctcdBenrate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctcdBenrate.setCorpcode(corpcode);
		ctcdBenrate.setCorpid(corpid);

        return R.ok(ctcdBenrateService.save(ctcdBenrate));
    }

    /**
     * 修改福利比例      @PreAuthorize("@pms.hasPermission('admin_ctcdbenrate_edit')" )
     * @param ctcdBenrate 福利比例
     * @return R
     */
    @ApiOperation(value = "修改福利比例", notes = "修改福利比例")
    @SysLog("修改福利比例" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdBenrate ctcdBenrate) {

        return R.ok(ctcdBenrateService.updateById(ctcdBenrate));
    }

    /**
     * 通过id删除福利比例
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除福利比例", notes = "通过id删除福利比例")
    @SysLog("通过id删除福利比例" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdbenrate_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdBenrateService.removeById(id));
    }

}
