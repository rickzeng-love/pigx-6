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
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Oatportaletlaw;
import com.pig4cloud.pigx.admin.service.OatportaletlawService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 法律法规
 *
 * @author gaoxiao
 * @date 2020-04-28 15:13:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oatportaletlaw" )
@Api(value = "oatportaletlaw", tags = "法律法规管理")
public class OatportaletlawController {

    private final  OatportaletlawService oatportaletlawService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oatportaletlaw 法律法规
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getOatportaletlawPage(Page page, @RequestBody(required = false) Oatportaletlaw oatportaletlaw) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(oatportaletlaw)){
			oatportaletlaw = new Oatportaletlaw();
		}
		oatportaletlaw.setCorpcode(corpcode);
    	return R.ok(oatportaletlawService.page(page, Wrappers.query(oatportaletlaw)));
    }


    /**
     * 通过id查询法律法规
     * @param docid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{docid}" )
    public R getById(@PathVariable("docid" ) Integer docid) {
        return R.ok(oatportaletlawService.getById(docid));
    }

    /**
     * 新增法律法规
     * @param oatportaletlaw 法律法规
     * @return R
     */
    @ApiOperation(value = "新增法律法规", notes = "新增法律法规")
    @SysLog("新增法律法规" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_oatportaletlaw_add')" )
    public R save(@RequestBody Oatportaletlaw oatportaletlaw) {
    	String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		oatportaletlaw.setCorpcode(corpcode);
		oatportaletlaw.setCorpcode(corpcode);
		oatportaletlaw.setCreateuserid(pigxUser.getId());
		oatportaletlaw.setIsdisabled(0);
		oatportaletlaw.setIsshow(0);
		oatportaletlaw.setCreateusername(pigxUser.getName());
		oatportaletlaw.setIstop(0);
		oatportaletlaw.setCreatedate(currentTime);

        return R.ok(oatportaletlawService.save(oatportaletlaw));
    }

/*
    */
/**
     * 修改法律法规
     * @param oatportaletlaw 法律法规
     * @return R
     *//*

    @ApiOperation(value = "修改法律法规", notes = "修改法律法规")
    @SysLog("修改法律法规" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_oatportaletlaw_edit')" )
    public R updateById(@RequestBody Oatportaletlaw oatportaletlaw) {
        return R.ok(oatportaletlawService.updateById(oatportaletlaw));
    }

    */
/**
     * 通过id删除法律法规
     * @param docid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除法律法规", notes = "通过id删除法律法规")
    @SysLog("通过id删除法律法规" )
    @DeleteMapping("/{docid}" )
    @PreAuthorize("@pms.hasPermission('admin_oatportaletlaw_del')" )
    public R removeById(@PathVariable Integer docid) {
        return R.ok(oatportaletlawService.removeById(docid));
    }
*/

}
