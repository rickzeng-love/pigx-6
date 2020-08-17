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
import com.pig4cloud.pigx.admin.entity.AvwAttendProcess;
import com.pig4cloud.pigx.admin.service.AvwAttendProcessService;
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
 * @date 2020-07-08 13:45:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/avwattendprocess" )
@Api(value = "avwattendprocess", tags = "VIEW管理")
public class AvwAttendProcessController {

    private final  AvwAttendProcessService avwAttendProcessService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param avwAttendProcess VIEW
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getAvwAttendProcessPage(Page page, AvwAttendProcess avwAttendProcess) {
        return R.ok(avwAttendProcessService.page(page, Wrappers.query(avwAttendProcess)));
    }

	/**
	 * 月度考勤流程
	 * @param avwAttendProcess VIEW
	 * @return
	 */
	@ApiOperation(value = "月度考勤流程", notes = "月度考勤流程")
	@PostMapping("/getAvwAttendProcessList" )
	public R getAvwAttendProcessList(@RequestBody(required=false) AvwAttendProcess avwAttendProcess) {
		if(StringUtils.isEmpty(avwAttendProcess)){
			avwAttendProcess = new AvwAttendProcess();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		avwAttendProcess.setCorpcode(corpcode);
		return R.ok(avwAttendProcessService.list( Wrappers.query(avwAttendProcess)));
	}


	/**
     * 通过id查询VIEW
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(avwAttendProcessService.getById(term));
    }

    /**
     * 新增VIEW
     * @param avwAttendProcess VIEW
     * @return R
     */
    @ApiOperation(value = "新增VIEW", notes = "新增VIEW")
    @SysLog("新增VIEW" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_avwattendprocess_add')" )
    public R save(@RequestBody AvwAttendProcess avwAttendProcess) {
        return R.ok(avwAttendProcessService.save(avwAttendProcess));
    }

/*
    */
/**
     * 修改VIEW
     * @param avwAttendProcess VIEW
     * @return R
     *//*

    @ApiOperation(value = "修改VIEW", notes = "修改VIEW")
    @SysLog("修改VIEW" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_avwattendprocess_edit')" )
    public R updateById(@RequestBody AvwAttendProcess avwAttendProcess) {
        return R.ok(avwAttendProcessService.updateById(avwAttendProcess));
    }

    */
/**
     * 通过id删除VIEW
     * @param term id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除VIEW", notes = "通过id删除VIEW")
    @SysLog("通过id删除VIEW" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_avwattendprocess_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(avwAttendProcessService.removeById(term));
    }
*/

}
