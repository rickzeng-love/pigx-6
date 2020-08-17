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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgHortation;
import com.pig4cloud.pigx.admin.service.EtbgHortationService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 奖惩记录
 *
 * @author gaoxiao
 * @date 2020-04-13 10:20:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbghortation" )
@Api(value = "etbghortation", tags = "奖惩记录管理")
public class EtbgHortationController {

    private final  EtbgHortationService etbgHortationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgHortation 奖惩记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgHortationPage(Page page, EtbgHortation etbgHortation) {
        return R.ok(etbgHortationService.page(page, Wrappers.query(etbgHortation)));
    }
	/**
	 * 查询所有
	 * @param etbgHortation
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgHortation" )
	public R getAllEtbgHortation(EtbgHortation etbgHortation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgHortation.setCorpcode(corpcode);
		etbgHortation.setUserid(userid);
		return R.ok(etbgHortationService.list(Wrappers.query(etbgHortation)));
	}

    /**
     * 通过id查询奖惩记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgHortationService.getById(id));
    }

    /**
     * 新增奖惩记录
     * @param etbgHortation 奖惩记录     @PreAuthorize("@pms.hasPermission('admin_etbghortation_add')" )
     * @return R
     */
    @ApiOperation(value = "新增奖惩记录", notes = "新增奖惩记录")
    @SysLog("新增奖惩记录" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgHortation etbgHortation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgHortation.setCorpcode(corpcode);
		etbgHortation.setUserid(userid);
        return R.ok(etbgHortationService.save(etbgHortation));
    }

    /**
     * 修改奖惩记录
     * @param etbgHortation 奖惩记录      @PreAuthorize("@pms.hasPermission('admin_etbghortation_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改奖惩记录", notes = "修改奖惩记录")
    @SysLog("修改奖惩记录" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgHortation etbgHortation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgHortation> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgHortation.getId());
        return R.ok(etbgHortationService.update(etbgHortation,updateWrapper));
    }
/*

    */
/**
     * 通过id删除奖惩记录
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_etbghortation_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除奖惩记录", notes = "通过id删除奖惩记录")
    @SysLog("通过id删除奖惩记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgHortationService.removeById(id));
    }
*/

}
