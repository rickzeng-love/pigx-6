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
import com.pig4cloud.pigx.admin.entity.Systpayrollgroupprocess;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtattendProcess;
import com.pig4cloud.pigx.admin.service.AtattendProcessService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 考勤流程表
 *
 * @author gaoxiao
 * @date 2020-06-22 13:35:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atattendprocess" )
@Api(value = "atattendprocess", tags = "考勤流程表管理")
public class AtattendProcessController {

    private final  AtattendProcessService atattendProcessService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atattendProcess 考勤流程表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtattendProcessPage(Page page, AtattendProcess atattendProcess) {
        return R.ok(atattendProcessService.page(page, Wrappers.query(atattendProcess)));
    }


    /**
     * 通过id查询考勤流程表
     * @param agentmode id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{agentmode}" )
    public R getById(@PathVariable("agentmode" ) Integer agentmode) {
        return R.ok(atattendProcessService.getById(agentmode));
    }


	/**
	 * 分页查询

	 * @return
	 */
	@ApiOperation(value = "查询考勤状态", notes = "查询考勤状态")
	@PostMapping("/getAtattendProcessStatus" )
	public R getAtattendProcessStatus() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<AtattendProcess> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpid",pigxUser.getCorpid());
		AtattendProcess atattendProcess = atattendProcessService.getOne(queryWrapper);
		Integer initialized = null;  //1
		Integer calc = null;//2
		Integer submit = null;//3
		Integer submitby = null;//4
		Integer closed = null;//5
		Integer status = 0;
		//0：无记录1：初始化2：计算3：签发4：封账
		if(StringUtils.isEmpty(atattendProcess)){
			return R.failed("请先创建账套！");
		}else{
			initialized = atattendProcess.getInitialized();
			calc = atattendProcess.getCalc();
			submit = atattendProcess.getSubmit();
			submitby = atattendProcess.getSubmitby();
			closed = atattendProcess.getClosed();
			if(!StringUtils.isEmpty(initialized) && initialized==1){
				status = 1;
			}
			if(!StringUtils.isEmpty(calc) && calc==1){
				status = 2;
			}
			if(!StringUtils.isEmpty(submit) && submit==1){
				status = 3;
			}else{
				if(!StringUtils.isEmpty(submitby)){
					status = 4;
				}
			}

			if(!StringUtils.isEmpty(closed) && closed==1){
				status = 5;
			}

		}
		return R.ok(status);
	}

    /**
     * 新增考勤流程表
     * @param atattendProcess 考勤流程表
     * @return R
     */
    @ApiOperation(value = "新增考勤流程表", notes = "新增考勤流程表")
    @SysLog("新增考勤流程表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atattendprocess_add')" )
    public R save(@RequestBody AtattendProcess atattendProcess) {
        return R.ok(atattendProcessService.save(atattendProcess));
    }

/*
    */
/**
     * 修改考勤流程表
     * @param atattendProcess 考勤流程表
     * @return R
     *//*

    @ApiOperation(value = "修改考勤流程表", notes = "修改考勤流程表")
    @SysLog("修改考勤流程表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atattendprocess_edit')" )
    public R updateById(@RequestBody AtattendProcess atattendProcess) {
        return R.ok(atattendProcessService.updateById(atattendProcess));
    }

    */
/**
     * 通过id删除考勤流程表
     * @param agentmode id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤流程表", notes = "通过id删除考勤流程表")
    @SysLog("通过id删除考勤流程表" )
    @DeleteMapping("/{agentmode}" )
    @PreAuthorize("@pms.hasPermission('admin_atattendprocess_del')" )
    public R removeById(@PathVariable Integer agentmode) {
        return R.ok(atattendProcessService.removeById(agentmode));
    }
*/

}
