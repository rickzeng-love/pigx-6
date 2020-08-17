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
import com.pig4cloud.pigx.admin.entity.CtcdCitypay;
import com.pig4cloud.pigx.admin.service.CtcdCitypayService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 城市最低工资
 *
 * @author gaoxiao
 * @date 2020-06-10 17:21:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcdcitypay" )
@Api(value = "ctcdcitypay", tags = "城市最低工资管理")
public class CtcdCitypayController {

    private final  CtcdCitypayService ctcdCitypayService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdCitypay 城市最低工资
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdCitypayPage(Page page, CtcdCitypay ctcdCitypay) {
        return R.ok(ctcdCitypayService.page(page, Wrappers.query(ctcdCitypay)));
    }
	/**
	 * 城市最低工资查询所有
	 * @param ctcdCitypay 城市最低工资
	 * @return
	 */
	@ApiOperation(value = "城市最低工资查询所有", notes = "城市最低工资查询所有")
	@PostMapping("/getAllCtcdCitypay" )
	public R getAllCtcdCitypay(CtcdCitypay ctcdCitypay) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdCitypay.setCorpcode(corpcode);
		return R.ok(ctcdCitypayService.list(Wrappers.query(ctcdCitypay)));
	}


    /**
     * 通过id查询城市最低工资
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdCitypayService.getById(id));
    }

    /**
     * 新增城市最低工资     @PreAuthorize("@pms.hasPermission('admin_ctcdcitypay_add')" )
     * @param ctcdCitypay 城市最低工资
     * @return R
     */
    @ApiOperation(value = "新增城市最低工资", notes = "新增城市最低工资")
    @SysLog("新增城市最低工资" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdCitypay ctcdCitypay) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctcdCitypay.setCorpcode(corpcode);
		ctcdCitypay.setCorpid(corpid);

        return R.ok(ctcdCitypayService.save(ctcdCitypay));
    }

    /**
     * 修改城市最低工资     @PreAuthorize("@pms.hasPermission('admin_ctcdcitypay_edit')" )
     * @param ctcdCitypay 城市最低工资
     * @return R
     */
    @ApiOperation(value = "修改城市最低工资", notes = "修改城市最低工资")
    @SysLog("修改城市最低工资" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdCitypay ctcdCitypay) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<CtcdCitypay> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctcdCitypay.getId());
        return R.ok(ctcdCitypayService.update(ctcdCitypay,updateWrapper));
    }

    /**
     * 通过id删除城市最低工资
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除城市最低工资", notes = "通过id删除城市最低工资")
    @SysLog("通过id删除城市最低工资" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcdcitypay_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdCitypayService.removeById(id));
    }

}
