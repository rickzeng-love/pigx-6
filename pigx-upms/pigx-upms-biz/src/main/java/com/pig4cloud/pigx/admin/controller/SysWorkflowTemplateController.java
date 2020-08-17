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
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.SysWorkflowTemplateMapper;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.SysWorkflowTemplate;
import com.pig4cloud.pigx.admin.service.SysWorkflowTemplateService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 服务商流程模板配置表
 *
 * @author XP
 * @date 2020-06-16 16:10:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysworkflowtemplate" )
@Api(value = "sysworkflowtemplate", tags = "服务商流程模板配置表管理")
public class SysWorkflowTemplateController {

    private final  SysWorkflowTemplateService sysWorkflowTemplateService;
    private final SysWorkflowTemplateMapper sysWorkflowTemplateMapper;
    private final SystcorpinfoService systcorpinfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysWorkflowTemplate 服务商流程模板配置表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getSysWorkflowTemplatePage(Page page, SysWorkflowTemplate sysWorkflowTemplate) {
		String name = sysWorkflowTemplate.getName();
		QueryWrapper<SysWorkflowTemplate> queryWrapper = new QueryWrapper<>();
		if (name != null && name != "") {
			queryWrapper.like("name", name);
		}
		return R.ok(sysWorkflowTemplateService.page(page, queryWrapper));

    }


    /**
     * 通过id查询服务商流程模板配置表
     * @param templateId id
     * @return R
     */
/*    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{templateId}" )
    public R getById(@PathVariable("templateId" ) String templateId) {
        return R.ok(sysWorkflowTemplateService.getById(templateId));
    }*/

    /**
     * 新增服务商流程模板配置表
     * @param sysWorkflowTemplate 服务商流程模板配置表
	 * @PreAuthorize("@pms.hasPermission('admin_sysworkflowtemplate_add')" )
     * @return R
     */
    @ApiOperation(value = "新增服务商流程模板配置表", notes = "新增服务商流程模板配置表")
    @SysLog("新增服务商流程模板配置表" )
    @PostMapping("/insert")
    public R save(@RequestBody SysWorkflowTemplate sysWorkflowTemplate) {
        return R.ok(sysWorkflowTemplateService.save(sysWorkflowTemplate));
    }

/*    *//**
     * 修改服务商流程模板配置表
     * @param sysWorkflowTemplate 服务商流程模板配置表
	 * @PreAuthorize("@pms.hasPermission('admin_sysworkflowtemplate_edit')" )
     * @return R
     *//*
    @ApiOperation(value = "修改服务商流程模板配置表", notes = "修改服务商流程模板配置表")
    @SysLog("修改服务商流程模板配置表" )
    @PostMapping("/update")
    public R updateById(@RequestBody SysWorkflowTemplate sysWorkflowTemplate) {
        return R.ok(sysWorkflowTemplateService.updateById(sysWorkflowTemplate));
    }

    *//**
     * 通过id删除服务商流程模板配置表
     * @param templateId id
	 * @PreAuthorize("@pms.hasPermission('admin_sysworkflowtemplate_del')" )
     * @return R
     *//*
    @ApiOperation(value = "通过id删除服务商流程模板配置表", notes = "通过id删除服务商流程模板配置表")
    @SysLog("通过id删除服务商流程模板配置表" )
    @DeleteMapping("/{templateId}" )
    public R removeById(@PathVariable String templateId) {
        return R.ok(sysWorkflowTemplateService.removeById(templateId));
    }*/


	/**
	 * @return
	 */
	@ApiOperation(value = "模板查询", notes = "模板查询")
	@PostMapping("/getWorkFlowTemplateList" )
	public R getWorkFlowTemplateList() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Systcorpinfo systcorpinfo = new Systcorpinfo();
		systcorpinfo.setCorpcode(corpcode);
		Systcorpinfo systcorpinfo1 = systcorpinfoService.getOne(Wrappers.query(systcorpinfo));
		if(StringUtils.isEmpty(systcorpinfo1)){
			R.failed("请维护企业corpid！");
		}
		String qywxcorpid = systcorpinfo1.getQywxCorpid();
		Map map = new HashMap();
		map.put("qywxcorpid",qywxcorpid);
		return R.ok(sysWorkflowTemplateMapper.listWorkFlowTemplateList(map));

	}
}
