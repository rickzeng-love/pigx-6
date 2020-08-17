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
import com.pig4cloud.pigx.admin.entity.CvwBenstatus;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CtchangebenefitAll;
import com.pig4cloud.pigx.admin.service.CtchangebenefitAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 福利变动历史
 *
 * @author gaoxiao
 * @date 2020-06-13 10:25:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctchangebenefitall" )
@Api(value = "ctchangebenefitall", tags = "福利变动历史管理")
public class CtchangebenefitAllController {

    private final  CtchangebenefitAllService ctchangebenefitAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctchangebenefitAll 福利变动历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getCtchangebenefitAllPage" )
    public R getCtchangebenefitAllPage(Page page, @RequestBody(required = false) CtchangebenefitAll ctchangebenefitAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		if(StringUtils.isEmpty(ctchangebenefitAll)){
			ctchangebenefitAll = new CtchangebenefitAll();
		}
		ctchangebenefitAll.setCorpcode(corpcode);
		String name = ctchangebenefitAll.getName();
		Integer depid = ctchangebenefitAll.getDepid();
		Integer jobid = ctchangebenefitAll.getJobid();

		QueryWrapper<CtchangebenefitAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",pigxUser.getCorpcode());
		if (!StringUtils.isEmpty(name)) {
			queryWrapper.like("Name", name);
		}
		if (!StringUtils.isEmpty(depid)) {
			queryWrapper.eq("DepID", depid);
		}
		if (!StringUtils.isEmpty(jobid)) {
			queryWrapper.eq("JobID", jobid);
		}
        return R.ok(ctchangebenefitAllService.page(page, queryWrapper));
    }


    /**
     * 通过id查询福利变动历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctchangebenefitAllService.getById(id));
    }

    /**
     * 新增福利变动历史
     * @param ctchangebenefitAll 福利变动历史
     * @return R
     */
    @ApiOperation(value = "新增福利变动历史", notes = "新增福利变动历史")
    @SysLog("新增福利变动历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangebenefitall_add')" )
    public R save(@RequestBody CtchangebenefitAll ctchangebenefitAll) {
        return R.ok(ctchangebenefitAllService.save(ctchangebenefitAll));
    }

/*
    */
/**
     * 修改福利变动历史
     * @param ctchangebenefitAll 福利变动历史
     * @return R
     *//*

    @ApiOperation(value = "修改福利变动历史", notes = "修改福利变动历史")
    @SysLog("修改福利变动历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangebenefitall_edit')" )
    public R updateById(@RequestBody CtchangebenefitAll ctchangebenefitAll) {
        return R.ok(ctchangebenefitAllService.updateById(ctchangebenefitAll));
    }

    */
/**
     * 通过id删除福利变动历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除福利变动历史", notes = "通过id删除福利变动历史")
    @SysLog("通过id删除福利变动历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctchangebenefitall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctchangebenefitAllService.removeById(id));
    }
*/

}
