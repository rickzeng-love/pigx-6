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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.CtcdCostcenter;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtcdDecsumperiods;
import com.pig4cloud.pigx.admin.service.CtcdDecsumperiodsService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 累计扣税期间
 *
 * @author gaoxiao
 * @date 2020-06-10 18:22:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctcddecsumperiods" )
@Api(value = "ctcddecsumperiods", tags = "累计扣税期间管理")
public class CtcdDecsumperiodsController {

    private final  CtcdDecsumperiodsService ctcdDecsumperiodsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctcdDecsumperiods 累计扣税期间
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtcdDecsumperiodsPage(Page page, CtcdDecsumperiods ctcdDecsumperiods) {
        return R.ok(ctcdDecsumperiodsService.page(page, Wrappers.query(ctcdDecsumperiods)));
    }
	/**
	 * 累计扣税期间查询所有
	 * @param ctcdDecsumperiods 累计扣税期间
	 * @return
	 */
	@ApiOperation(value = "累计扣税区间", notes = "成本中心查询所有")
	@PostMapping("/getAllCtcdDecsumperiods" )
	public R getAllCtcdDecsumperiods(@RequestBody(required = false) CtcdDecsumperiods ctcdDecsumperiods) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		ctcdDecsumperiods.setCorpcode(corpcode);
		if(StringUtils.isEmpty(ctcdDecsumperiods)){
			ctcdDecsumperiods = new CtcdDecsumperiods();
		}
		return R.ok(ctcdDecsumperiodsService.list(Wrappers.query(ctcdDecsumperiods)));
	}


    /**
     * 通过id查询累计扣税期间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctcdDecsumperiodsService.getById(id));
    }

    /**
     * 新增累计扣税期间     @PreAuthorize("@pms.hasPermission('admin_ctcddecsumperiods_add')" )
     * @param ctcdDecsumperiods 累计扣税期间
     * @return R
     */
    @ApiOperation(value = "新增累计扣税期间", notes = "新增累计扣税期间")
    @SysLog("新增累计扣税期间" )
    @PostMapping("/save")
    public R save(@RequestBody CtcdDecsumperiods ctcdDecsumperiods) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		ctcdDecsumperiods.setCorpcode(corpcode);
		ctcdDecsumperiods.setCorpid(corpid);
        return R.ok(ctcdDecsumperiodsService.save(ctcdDecsumperiods));
    }

    /**    @PreAuthorize("@pms.hasPermission('admin_ctcddecsumperiods_edit')" )
     * 修改累计扣税期间
     * @param ctcdDecsumperiods 累计扣税期间
     * @return R
     */
    @ApiOperation(value = "修改累计扣税期间", notes = "修改累计扣税期间")
    @SysLog("修改累计扣税期间" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtcdDecsumperiods ctcdDecsumperiods) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<CtcdDecsumperiods> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("gid",ctcdDecsumperiods.getGid());
		updateWrapper.eq("term",ctcdDecsumperiods.getTerm());

        return R.ok(ctcdDecsumperiodsService.update(ctcdDecsumperiods,updateWrapper));
    }

    /**
     * 通过id删除累计扣税期间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除累计扣税期间", notes = "通过id删除累计扣税期间")
    @SysLog("通过id删除累计扣税期间" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctcddecsumperiods_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctcdDecsumperiodsService.removeById(id));
    }

}
