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
import com.pig4cloud.pigx.admin.entity.CtchangesalaryAll;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.CvwEmployee;
import com.pig4cloud.pigx.admin.service.CvwEmployeeService;
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
 * @author gaoxiao
 * @date 2020-06-12 16:38:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cvwemployee" )
@Api(value = "cvwemployee", tags = "薪资视图管理")
public class CvwEmployeeController {

    private final  CvwEmployeeService cvwEmployeeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param cvwEmployee VIEW
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getCvwEmployeePage(Page page, CvwEmployee cvwEmployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CvwEmployee> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = cvwEmployee.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",name);
		}
		Integer depid = cvwEmployee.getDepid();
		Integer jobid = cvwEmployee.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.like("jobid",jobid);
		}
		return R.ok(cvwEmployeeService.page(page,queryWrapper));
    }


    /**
     * 通过id查询VIEW
     * @param eid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{eid}" )
    public R getById(@PathVariable("eid" ) Integer eid) {
        return R.ok(cvwEmployeeService.getById(eid));
    }

    /**
     * 新增VIEW
     * @param cvwEmployee VIEW
     * @return R
     */
    @ApiOperation(value = "新增VIEW", notes = "新增VIEW")
    @SysLog("新增VIEW" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_cvwemployee_add')" )
    public R save(@RequestBody CvwEmployee cvwEmployee) {
        return R.ok(cvwEmployeeService.save(cvwEmployee));
    }

/*
    */
/**
     * 修改VIEW
     * @param cvwEmployee VIEW
     * @return R
     *//*

    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_cvwemployee_edit')" )
    public R updateById(@RequestBody CvwEmployee cvwEmployee) {
        return R.ok(cvwEmployeeService.updateById(cvwEmployee));
    }

    */
/**
     * 通过id删除VIEW
     * @param eid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{eid}" )
    @PreAuthorize("@pms.hasPermission('admin_cvwemployee_del')" )
    public R removeById(@PathVariable Integer eid) {
        return R.ok(cvwEmployeeService.removeById(eid));
    }
*/

}
