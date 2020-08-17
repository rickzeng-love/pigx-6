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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Oatportaletnotice;
import com.pig4cloud.pigx.admin.service.OatportaletnoticeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 通知通告
 *
 * @author gaoxiao
 * @date 2020-04-17 15:16:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oatportaletnotice" )
@Api(value = "oatportaletnotice", tags = "通知通告管理")
public class OatportaletnoticeController {

    private final  OatportaletnoticeService oatportaletnoticeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oatportaletnotice 通知通告
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getOatportaletnoticePage" )
    public R getOatportaletnoticePage(Page page, @RequestBody(required = false)  Oatportaletnotice oatportaletnotice) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(oatportaletnotice)){
			oatportaletnotice = new Oatportaletnotice();
		}
		oatportaletnotice.setCorpcode(corpcode);
        return R.ok(oatportaletnoticeService.page(page, Wrappers.query(oatportaletnotice)));
    }


    /**
     * 通过id查询通知通告
     * @param docid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{docid}" )
    public R getById(@PathVariable("docid" ) Integer docid) {
        return R.ok(oatportaletnoticeService.getById(docid));
    }

    /**
     * 新增通知通告
     * @param oatportaletnotice 通知通告      @PreAuthorize("@pms.hasPermission('admin_oatportaletnotice_add')" )
     * @return R
     */
    @ApiOperation(value = "新增通知通告", notes = "新增通知通告")
    @SysLog("新增通知通告" )
    @PostMapping("/save")
    public R save(@RequestBody Oatportaletnotice oatportaletnotice) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		oatportaletnotice.setCorpcode(corpcode);
		oatportaletnotice.setCorpcode(corpcode);
		oatportaletnotice.setCreateuserid(pigxUser.getId());
		oatportaletnotice.setIsdisabled(0);
		oatportaletnotice.setIsshow(0);
		oatportaletnotice.setCreateusername(pigxUser.getName());
		oatportaletnotice.setIstop(0);
		oatportaletnotice.setCreatedate(currentTime);
        return R.ok(oatportaletnoticeService.save(oatportaletnotice));
    }

/*
    */
/**
     * 修改通知通告
     * @param oatportaletnotice 通知通告
     * @return R      @PreAuthorize("@pms.hasPermission('admin_oatportaletnotice_edit')" )
     */

    @ApiOperation(value = "修改通知通告", notes = "修改通知通告")
    @SysLog("修改通知通告" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody Oatportaletnotice oatportaletnotice) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		oatportaletnotice.setCorpcode(corpcode);
		UpdateWrapper<Oatportaletnotice> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("docid",oatportaletnotice.getDocid());
		return R.ok(oatportaletnoticeService.update(oatportaletnotice,updateWrapper));
    }


/**
     * 通过id删除通知通告
     * @param docid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除通知通告", notes = "通过id删除通知通告")
    @SysLog("通过id删除通知通告" )
    @DeleteMapping("/{docid}" )
    @PreAuthorize("@pms.hasPermission('admin_oatportaletnotice_del')" )
    public R removeById(@PathVariable Integer docid) {
        return R.ok(oatportaletnoticeService.removeById(docid));
    }
*/

}
