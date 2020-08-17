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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtcdAgentmode;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.OavwAttendancedetail;
import com.pig4cloud.pigx.admin.service.OavwAttendancedetailService;
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
 * @date 2020-06-19 12:16:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oavwattendancedetail" )
@Api(value = "oavwattendancedetail", tags = "考勤打卡原始表管理")
public class OavwAttendancedetailController {

    private final  OavwAttendancedetailService oavwAttendancedetailService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oavwAttendancedetail VIEW
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOavwAttendancedetailPage(Page page, OavwAttendancedetail oavwAttendancedetail) {
        return R.ok(oavwAttendancedetailService.page(page, Wrappers.query(oavwAttendancedetail)));
    }

	/**
	 * 查询所有
	 * @param oavwAttendancedetail
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllOavwAttendancedetail" )
	public R getAllOavwAttendancedetail(@RequestBody(required = false) OavwAttendancedetail oavwAttendancedetail)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(oavwAttendancedetail)){
			oavwAttendancedetail = new OavwAttendancedetail();
		}
		oavwAttendancedetail.setCorpcode(corpCode);
		return R.ok(oavwAttendancedetailService.list(Wrappers.query(oavwAttendancedetail)));
	}

	/**
     * 通过id查询VIEW
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(oavwAttendancedetailService.getById(id));
    }

    /**
     * 新增VIEW
     * @param oavwAttendancedetail VIEW
     * @return R
     */
    @ApiOperation(value = "新增VIEW", notes = "新增VIEW")
    @SysLog("新增VIEW" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_oavwattendancedetail_add')" )
    public R save(@RequestBody OavwAttendancedetail oavwAttendancedetail) {
        return R.ok(oavwAttendancedetailService.save(oavwAttendancedetail));
    }

/*
    */
/**
     * 修改VIEW
     * @param oavwAttendancedetail VIEW
     * @return R
     *//*

    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_oavwattendancedetail_edit')" )
    public R updateById(@RequestBody OavwAttendancedetail oavwAttendancedetail) {
        return R.ok(oavwAttendancedetailService.updateById(oavwAttendancedetail));
    }

    */
/**
     * 通过id删除VIEW
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_oavwattendancedetail_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(oavwAttendancedetailService.removeById(id));
    }
*/

}
