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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtgroupShift;
import com.pig4cloud.pigx.admin.service.AtgroupShiftService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 轮班组班次管理
 *
 * @author GXS
 * @date 2020-05-25 10:53:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atgroupshift" )
@Api(value = "atgroupshift", tags = "班次管理")
public class AtgroupShiftController {

    private final  AtgroupShiftService atgroupShiftService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atgroupShift 轮班组班次管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtgroupShiftPage" )
    public R getAtgroupShiftPage(Page page, @RequestBody(required = false)  AtgroupShift atgroupShift) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atgroupShift)){
			atgroupShift = new AtgroupShift();
		}
		atgroupShift.setCorpcode(corpCode);
        return R.ok(atgroupShiftService.page(page, Wrappers.query(atgroupShift)));
    }


    /**
     * 通过id查询轮班组班次管理
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atgroupShiftService.getById(id));
    }

    /**
     * 新增轮班组班次管理
     * @param atgroupShift 轮班组班次管理 @PreAuthorize("@pms.hasPermission('admin_atgroupshift_add')" )
     * @return R
     */
    @ApiOperation(value = "新增班次", notes = "新增班次")
    @SysLog("新增班次" )
    @PostMapping("/saveAtgroupShift")
    public R saveAtgroupShift(@RequestBody AtgroupShift atgroupShift) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		atgroupShift.setCorpid(corpid);
		atgroupShift.setCorpcode(corpcode);
    	return R.ok(atgroupShiftService.save(atgroupShift));
    }

/*
    */
/**
     * 修改轮班组班次管理
     * @param atgroupShift 轮班组班次管理 @PreAuthorize("@pms.hasPermission('admin_atgroupshift_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改班次", notes = "修改班次")
    @SysLog("修改班次" )
    @PutMapping
    public R updateById(@RequestBody AtgroupShift atgroupShift) {
        return R.ok(atgroupShiftService.updateById(atgroupShift));
    }

    */
/**
     * 通过id删除轮班组班次管理
     * @param id id @PreAuthorize("@pms.hasPermission('admin_atgroupshift_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除班次", notes = "通过id删除班次")
    @SysLog("通过id删除班次" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atgroupShiftService.removeById(id));
    }
*/

}
