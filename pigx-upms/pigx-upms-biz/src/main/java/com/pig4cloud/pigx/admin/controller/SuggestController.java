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
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Suggest;
import com.pig4cloud.pigx.admin.service.SuggestService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-26 10:26:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/suggest" )
@Api(value = "suggest", tags = "管理")
public class SuggestController {

    private final  SuggestService suggestService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param suggest 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSuggestPage(Page page, Suggest suggest) {
        return R.ok(suggestService.page(page, Wrappers.query(suggest)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(suggestService.getById(id));
    }

    /**
     * 新增   @PreAuthorize("@pms.hasPermission('admin_suggest_add')" )
     * @param suggest 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping(value="/save")
    public R save(@RequestBody Suggest suggest) {
		PigxUser pu = SecurityUtils.getUser();
		suggest.setStatus(0);
		suggest.setTelphone(pu.getUsername());
		suggest.setUserid(pu.getId());
		suggest.setCreatedate(DateUtils.getNow());
		return R.ok(suggestService.save(suggest));
    }

    /**
     * 修改      @PreAuthorize("@pms.hasPermission('admin_suggest_edit')" )
     * @param suggest 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping

    public R updateById(@RequestBody Suggest suggest) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<Suggest> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",suggest.getId());
        return R.ok(suggestService.update(suggest,updateWrapper));
    }

/*
    */
/**
     * 通过id删除
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_suggest_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(suggestService.removeById(id));
    }
*/

}
