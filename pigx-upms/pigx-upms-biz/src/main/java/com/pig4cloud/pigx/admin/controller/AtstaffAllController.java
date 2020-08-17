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
import com.pig4cloud.pigx.admin.entity.AtstaffRegister;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtstaffAll;
import com.pig4cloud.pigx.admin.service.AtstaffAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 考勤开启历史
 *
 * @author gaoxiao
 * @date 2020-06-23 14:20:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atstaffall" )
@Api(value = "atstaffall", tags = "考勤开启历史管理")
public class AtstaffAllController {

    private final  AtstaffAllService atstaffAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atstaffAll 考勤开启历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtstaffAllPage(Page page, AtstaffAll atstaffAll) {
        return R.ok(atstaffAllService.page(page, Wrappers.query(atstaffAll)));
    }

	/**
	 * 考勤历史查询
	 * @param page 分页对象
	 * @param atstaffRegister 考勤历史查询
	 * @return
	 * 测试通过
	 */
	@ApiOperation(value = "考勤历史查询", notes = "考勤历史查询")
	@PostMapping("/getAtstaffAllPageBySql" )
	public R getAtstaffAllPageBySql(Page page,@RequestBody(required=false) AtstaffAll atstaffAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atstaffAll)){
			atstaffAll = new AtstaffAll();
		}
		QueryWrapper<AtstaffAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = atstaffAll.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",name);
		}
		Integer depid = atstaffAll.getDepid();
		Integer jobid = atstaffAll.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("jobid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(atstaffAllService.page(page,queryWrapper));
	}

    /**
     * 通过id查询考勤开启历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atstaffAllService.getById(id));
    }

    /**
     * 新增考勤开启历史
     * @param atstaffAll 考勤开启历史
     * @return R
     */
    @ApiOperation(value = "新增考勤开启历史", notes = "新增考勤开启历史")
    @SysLog("新增考勤开启历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atstaffall_add')" )
    public R save(@RequestBody AtstaffAll atstaffAll) {
        return R.ok(atstaffAllService.save(atstaffAll));
    }
/*

    */
/**
     * 修改考勤开启历史
     * @param atstaffAll 考勤开启历史
     * @return R
     *//*

    @ApiOperation(value = "修改考勤开启历史", notes = "修改考勤开启历史")
    @SysLog("修改考勤开启历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atstaffall_edit')" )
    public R updateById(@RequestBody AtstaffAll atstaffAll) {
        return R.ok(atstaffAllService.updateById(atstaffAll));
    }

    */
/**
     * 通过id删除考勤开启历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤开启历史", notes = "通过id删除考勤开启历史")
    @SysLog("通过id删除考勤开启历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atstaffall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atstaffAllService.removeById(id));
    }
*/

}
