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
import com.pig4cloud.pigx.admin.entity.AvwAttendProcess;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftTotalAll;
import com.pig4cloud.pigx.admin.service.AtshiftTotalAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 月汇总历史
 *
 * @author gaoxiao
 * @date 2020-07-08 13:54:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshifttotalall" )
@Api(value = "atshifttotalall", tags = "月汇总历史管理")
public class AtshiftTotalAllController {

    private final  AtshiftTotalAllService atshiftTotalAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftTotalAll 月汇总历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtshiftTotalAllPage" )
    public R getAtshiftTotalAllPage(Page page,@RequestBody(required=false) AtshiftTotalAll atshiftTotalAll) {
		if(StringUtils.isEmpty(atshiftTotalAll)){
			atshiftTotalAll = new AtshiftTotalAll();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		QueryWrapper<AtshiftTotalAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = atshiftTotalAll.getName();
		String term1 = atshiftTotalAll.getTerm1();
		String term2 = atshiftTotalAll.getTerm2();
		Integer depid = atshiftTotalAll.getDepid();
		Integer jobid = atshiftTotalAll.getJobid();
		if(!StringUtils.isEmpty(name)){
			queryWrapper.like("name",name);
		}
		if(!StringUtils.isEmpty(term1)){
			queryWrapper.lt("term",term1);
		}
		if(!StringUtils.isEmpty(term2)){
			queryWrapper.gt("term",term2);
		}
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
    	return R.ok(atshiftTotalAllService.page(page,queryWrapper));
    }


    /**
     * 通过id查询月汇总历史
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(atshiftTotalAllService.getById(term));
    }

    /**
     * 新增月汇总历史
     * @param atshiftTotalAll 月汇总历史
     * @return R
     */
    @ApiOperation(value = "新增月汇总历史", notes = "新增月汇总历史")
    @SysLog("新增月汇总历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshifttotalall_add')" )
    public R save(@RequestBody AtshiftTotalAll atshiftTotalAll) {
        return R.ok(atshiftTotalAllService.save(atshiftTotalAll));
    }

/*
    */
/**
     * 修改月汇总历史
     * @param atshiftTotalAll 月汇总历史
     * @return R
     *//*

    @ApiOperation(value = "修改月汇总历史", notes = "修改月汇总历史")
    @SysLog("修改月汇总历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshifttotalall_edit')" )
    public R updateById(@RequestBody AtshiftTotalAll atshiftTotalAll) {
        return R.ok(atshiftTotalAllService.updateById(atshiftTotalAll));
    }

    */
/**
     * 通过id删除月汇总历史
     * @param term id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除月汇总历史", notes = "通过id删除月汇总历史")
    @SysLog("通过id删除月汇总历史" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atshifttotalall_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(atshiftTotalAllService.removeById(term));
    }
*/

}
