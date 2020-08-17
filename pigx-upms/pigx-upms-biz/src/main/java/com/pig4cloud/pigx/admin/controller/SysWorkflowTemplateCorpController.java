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
import com.pig4cloud.pigx.admin.entity.SysWorkflowTemplateCorp;
import com.pig4cloud.pigx.admin.service.SysWorkflowTemplateCorpService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 公司流程模板配置表
 *
 * @author XP
 * @date 2020-06-16 16:10:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysworkflowtemplatecorp" )
@Api(value = "sysworkflowtemplatecorp", tags = "公司流程模板配置表管理")
public class SysWorkflowTemplateCorpController {

    private final  SysWorkflowTemplateCorpService sysWorkflowTemplateCorpService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysWorkflowTemplateCorp 公司流程模板配置表
     * @return
     */
/*
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSysWorkflowTemplateCorpPage(Page page, SysWorkflowTemplateCorp sysWorkflowTemplateCorp) {
        return R.ok(sysWorkflowTemplateCorpService.page(page, Wrappers.query(sysWorkflowTemplateCorp)));
    }
*/


    /**
     * 通过id查询公司流程模板配置表
     * @param id id
     * @return R
     */
/*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(sysWorkflowTemplateCorpService.getById(id));
    }
*/

    /**
     * 新增公司流程模板配置表
     * @param sysWorkflowTemplateCorp 公司流程模板配置表
     * @return R
     */
/*    @ApiOperation(value = "新增公司流程模板配置表", notes = "新增公司流程模板配置表")
    @SysLog("新增公司流程模板配置表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_sysworkflowtemplatecorp_add')" )
    public R save(@RequestBody SysWorkflowTemplateCorp sysWorkflowTemplateCorp) {
        return R.ok(sysWorkflowTemplateCorpService.save(sysWorkflowTemplateCorp));
    }*/

    /**
     * 修改公司流程模板配置表
     * @param sysWorkflowTemplateCorp 公司流程模板配置表
     * @return R
     */
/*    @ApiOperation(value = "修改公司流程模板配置表", notes = "修改公司流程模板配置表")
    @SysLog("修改公司流程模板配置表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_sysworkflowtemplatecorp_edit')" )
    public R updateById(@RequestBody SysWorkflowTemplateCorp sysWorkflowTemplateCorp) {
        return R.ok(sysWorkflowTemplateCorpService.updateById(sysWorkflowTemplateCorp));
    }*/

    /**
     * 通过id删除公司流程模板配置表
     * @param id id
     * @return R
     */
/*
    @ApiOperation(value = "通过id删除公司流程模板配置表", notes = "通过id删除公司流程模板配置表")
    @SysLog("通过id删除公司流程模板配置表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_sysworkflowtemplatecorp_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(sysWorkflowTemplateCorpService.removeById(id));
    }
*/

}
