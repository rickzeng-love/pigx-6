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
import com.pig4cloud.pigx.admin.entity.AtshiftsupplyWork;
import com.pig4cloud.pigx.admin.service.AtshiftsupplyWorkService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 手工排班
 *
 * @author GXS
 * @date 2020-05-29 10:38:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftsupplywork" )
@Api(value = "atshiftsupplywork", tags = "手工排班管理")
public class AtshiftsupplyWorkController {

    private final  AtshiftsupplyWorkService atshiftsupplyWorkService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftsupplyWork 手工排班
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftsupplyWorkPage(Page page, AtshiftsupplyWork atshiftsupplyWork) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftsupplyWork.setCorpcode(corpCode);
        return R.ok(atshiftsupplyWorkService.page(page, Wrappers.query(atshiftsupplyWork)));
    }

	/**
	 * 查询所有
	 * @param atshiftsupplyWork
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllAtshiftsupplyWork" )
	public R getAllAtshiftsupplyWork(AtshiftsupplyWork atshiftsupplyWork)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftsupplyWork.setCorpcode(corpCode);
		return R.ok(atshiftsupplyWorkService.list(Wrappers.query(atshiftsupplyWork)));
	}


    /**
     * 通过id查询手工排班
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atshiftsupplyWorkService.getById(id));
    }

    /**
     * 新增手工排班
     * @param atshiftsupplyWork 手工排班 @PreAuthorize("@pms.hasPermission('admin_atshiftsupplywork_add')" )
     * @return R
     */
    @ApiOperation(value = "新增手工排班", notes = "新增手工排班")
    @SysLog("新增手工排班" )
    @PostMapping
    public R save(@RequestBody AtshiftsupplyWork atshiftsupplyWork) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		Integer corpId = pigxUser.getCorpid();
		atshiftsupplyWork.setCorpid(corpId);
		atshiftsupplyWork.setCorpcode(corpCode);
        return R.ok(atshiftsupplyWorkService.save(atshiftsupplyWork));
    }
/*

    */
/**
     * 修改手工排班
     * @param atshiftsupplyWork 手工排班 @PreAuthorize("@pms.hasPermission('admin_atshiftsupplywork_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改手工排班", notes = "修改手工排班")
    @SysLog("修改手工排班" )
    @PutMapping
    public R updateById(@RequestBody AtshiftsupplyWork atshiftsupplyWork) {
        return R.ok(atshiftsupplyWorkService.updateById(atshiftsupplyWork));
    }

    */
/**
     * 通过id删除手工排班 @PreAuthorize("@pms.hasPermission('admin_atshiftsupplywork_del')" )
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除手工排班", notes = "通过id删除手工排班")
    @SysLog("通过id删除手工排班" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atshiftsupplyWorkService.removeById(id));
    }
*/

}
