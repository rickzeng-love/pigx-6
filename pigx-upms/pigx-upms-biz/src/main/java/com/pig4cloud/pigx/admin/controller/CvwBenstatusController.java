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
import com.pig4cloud.pigx.admin.entity.CtbenefitRegister;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CvwBenstatus;
import com.pig4cloud.pigx.admin.service.CvwBenstatusService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * VIEW
 *
 * @author XP
 * @date 2020-06-16 16:10:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cvwbenstatus" )
@Api(value = "cvwbenstatus", tags = "VIEW管理")
public class CvwBenstatusController {

    private final  CvwBenstatusService cvwBenstatusService;


    /**
     * 社保信息分页查询
     * @param page 分页对象
     * @param cvwBenstatus 社保信息
     * @return
     */
    @ApiOperation(value = "社保信息分页查询", notes = "社保信息分页查询")
    @PostMapping("/getCvwBenstatusPage" )
    public R getCvwBenstatusPage(Page page,@RequestBody CvwBenstatus cvwBenstatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		if(StringUtils.isEmpty(cvwBenstatus)){
			cvwBenstatus = new CvwBenstatus();
		}
		cvwBenstatus.setCorpcode(corpcode);
		cvwBenstatus.setCorpid(corpid);
		String name = cvwBenstatus.getName();
		Integer depid = cvwBenstatus.getDepid();
		Integer jobid = cvwBenstatus.getJobid();
		Integer benstatus =null;
		QueryWrapper<CvwBenstatus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",pigxUser.getCorpcode());
		if (!StringUtils.isEmpty(cvwBenstatus.getBenstatus())){
			queryWrapper.eq("benstatus",cvwBenstatus.getBenstatus());
		}


		if (name != null && name != "") {
			queryWrapper.like("Name", name);
		}
		if (!StringUtils.isEmpty(depid)) {
			queryWrapper.eq("DepID", depid);
		}
		if (!StringUtils.isEmpty(jobid )) {
			queryWrapper.eq("JobID", jobid);
		}
        return R.ok(cvwBenstatusService.page(page,queryWrapper));
    }


    /**
     * 通过id查询社保信息
     * @param eid id
     * @return R
     */
   /* @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{eid}" )
    public R getById(@PathVariable("eid" ) Integer eid) {
        return R.ok(cvwBenstatusService.getById(eid));
    }
*/
    /**
     * 新增社保信息
     * @param cvwBenstatus 社保信息
     * @return R
     */
   /* @ApiOperation(value = "新增社保信息", notes = "新增社保信息")
    @SysLog("新增社保信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_cvwbenstatus_add')" )
    public R save(@RequestBody CvwBenstatus cvwBenstatus) {
        return R.ok(cvwBenstatusService.save(cvwBenstatus));
    }
*/
    /**
     * 修改社保信息
     * @param cvwBenstatus 社保信息
     * @return R
     *//*
    @ApiOperation(value = "修改社保信息", notes = "修改社保信息")
    @SysLog("修改社保信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_cvwbenstatus_edit')" )
    public R updateById(@RequestBody CvwBenstatus cvwBenstatus) {
        return R.ok(cvwBenstatusService.updateById(cvwBenstatus));
    }*/

    /**
     * 通过id删除社保信息
     * @param eid id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除社保信息", notes = "通过id删除社保信息")
    @SysLog("通过id删除社保信息" )
    @DeleteMapping("/{eid}" )
    @PreAuthorize("@pms.hasPermission('admin_cvwbenstatus_del')" )
    public R removeById(@PathVariable Integer eid) {
        return R.ok(cvwBenstatusService.removeById(eid));
    }
*/
}
