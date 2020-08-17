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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.AtshiftGroupMapper;
import com.pig4cloud.pigx.admin.service.AtcdAgentmodeService;
import com.pig4cloud.pigx.admin.service.AtshiftGroupSubService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.AtshiftGroupService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 轮班定义
 *
 * @author gaoxiao
 * @date 2020-07-06 16:26:48
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftgroup" )
@Api(value = "atshiftgroup", tags = "轮班定义管理")
public class AtshiftGroupController {

    private final  AtshiftGroupService atshiftGroupService;
    private final AtshiftGroupSubService atshiftGroupSubService;
    private final AtshiftGroupMapper atshiftGroupMapper;
	private final AtcdAgentmodeService atcdAgentmodeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftGroup 轮班定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtshiftGroupPage" )
    public R getAtshiftGroupPage(Page page, @RequestBody(required = false) AtshiftGroup atshiftGroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftGroup)){
			atshiftGroup = new AtshiftGroup();
		}
		atshiftGroup.setCorpcode(corpcode);
    	return R.ok(atshiftGroupService.page(page, Wrappers.query(atshiftGroup)));
    }


    /**
     * 通过id查询轮班定义
     * @param turnid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{turnid}" )
    public R getById(@PathVariable("turnid" ) Integer turnid) {
        return R.ok(atshiftGroupService.getById(turnid));
    }

	/**
	 * 轮班定义所有
	 * @param atshiftGroup 轮班定义
	 * @return
	 */
	@ApiOperation(value = "轮班定义所有", notes = "轮班定义所有")
	@PostMapping("/getAtshiftgroup" )
	public R getAtshiftgroup(@RequestBody(required = false) AtshiftGroup atshiftGroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftGroup)){
			atshiftGroup = new AtshiftGroup();
		}
		atshiftGroup.setCorpcode(corpcode);
		return R.ok(atshiftGroupMapper.selectAtShiftGroup(atshiftGroup));
	}


	/**
	 * 轮班定义所有
	 * @param atshiftGroup 轮班定义
	 * @return
	 */
	@ApiOperation(value = "轮班定义下面的班次", notes = "轮班定义下面的班次")
	@PostMapping("/getAtshiftTypeByTurn" )
	public R getAtshiftGroupSubByTurn(@RequestBody AtshiftGroup atshiftGroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atshiftGroup.setCorpcode(corpcode);

		return R.ok(atshiftGroupMapper.listAtshiftGroupSubByTurn(atshiftGroup));
	}
    /**
     * 新增轮班定义      @PreAuthorize("@pms.hasPermission('admin_atshiftgroup_add')" )
     * @param atshiftGroup 轮班定义
     * @return R
     */
    @ApiOperation(value = "新增轮班定义", notes = "新增轮班定义")
    @SysLog("新增轮班定义" )
    @PostMapping("/save")
    public R save(@RequestBody AtshiftGroup atshiftGroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		if(StringUtils.isEmpty(atcdAgentmode)){
			return R.failed("考勤账套不存在，请先维护考勤账套！");
		}
		Integer agentmod = atcdAgentmode.getId();
		atshiftGroup.setAgentmode(agentmod);
		atshiftGroup.setCorpcode(corpcode);
		atshiftGroup.setCorpid(pigxUser.getCorpid());
		atshiftGroup.setInitialized(1);
		atshiftGroup.setIscheck(1);
		atshiftGroupService.save(atshiftGroup);
		List<AtshiftGroupSub> list = atshiftGroup.getAtshiftGroupSubList();
		AtshiftGroupSub atshiftGroupSub = null;
		for(int i=0;i<list.size();i++){
			atshiftGroupSub = list.get(i);
			atshiftGroupSub.setCorpcode(corpcode);
			atshiftGroupSub.setCorpid(pigxUser.getCorpid());
			atshiftGroupSub.setInitialized(1);
			atshiftGroupSub.setIscheck(1);
			atshiftGroupSub.setTurnid(atshiftGroup.getTurnid());
			atshiftGroupSubService.save(atshiftGroupSub);
		}
        return R.ok(atshiftGroup,"保存成功！");
    }

    /**
     * 修改轮班定义
     * @param atshiftGroup 轮班定义
     * @return R
     */
    @ApiOperation(value = "修改轮班定义", notes = "修改轮班定义")
    @SysLog("修改轮班定义" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftgroup_edit')" )
    public R updateById(@RequestBody AtshiftGroup atshiftGroup) {
        return R.ok(atshiftGroupService.updateById(atshiftGroup));
    }

    /**
     * 通过id删除轮班定义
     * @param turnid id
     * @return R
     */
    @ApiOperation(value = "通过id删除轮班定义", notes = "通过id删除轮班定义")
    @SysLog("通过id删除轮班定义" )
    @DeleteMapping("/{turnid}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftgroup_del')" )
    public R removeById(@PathVariable Integer turnid) {
        return R.ok(atshiftGroupService.removeById(turnid));
    }

}
