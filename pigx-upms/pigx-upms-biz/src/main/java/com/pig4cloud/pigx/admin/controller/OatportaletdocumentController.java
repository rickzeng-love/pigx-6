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
import com.pig4cloud.pigx.admin.entity.Oatportaletnotice;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Oatportaletdocument;
import com.pig4cloud.pigx.admin.service.OatportaletdocumentService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 规章制度
 *
 * @author gaoxiao
 * @date 2020-04-17 15:24:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oatportaletdocument" )
@Api(value = "oatportaletdocument", tags = "规章制度管理")
public class OatportaletdocumentController {

    private final  OatportaletdocumentService oatportaletdocumentService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oatportaletdocument 规章制度
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getOatportaletdocumentPage" )
    public R getOatportaletdocumentPage(Page page,@RequestBody(required = false) Oatportaletdocument oatportaletdocument) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(oatportaletdocument)){
			oatportaletdocument = new Oatportaletdocument();
		}
		oatportaletdocument.setCorpcode(corpcode);
        return R.ok(oatportaletdocumentService.page(page, Wrappers.query(oatportaletdocument)));
    }


    /**
     * 通过id查询规章制度
     * @param docid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{docid}" )
    public R getById(@PathVariable("docid" ) Integer docid) {
        return R.ok(oatportaletdocumentService.getById(docid));
    }

    /**
     * 新增规章制度
     * @param oatportaletdocument 规章制度     @PreAuthorize("@pms.hasPermission('admin_oatportaletdocument_add')" )
     * @return R
     */
    @ApiOperation(value = "新增规章制度", notes = "新增规章制度")
    @SysLog("新增规章制度" )
    @PostMapping("/save")
    public R save(@RequestBody Oatportaletdocument oatportaletdocument) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		oatportaletdocument.setCorpcode(corpcode);
		oatportaletdocument.setCorpcode(corpcode);
		oatportaletdocument.setCreateuserid(pigxUser.getId());
		oatportaletdocument.setIsdisabled(0);
		oatportaletdocument.setIsshow(0);
		oatportaletdocument.setCreateusername(pigxUser.getName());
		oatportaletdocument.setIstop(0);
		oatportaletdocument.setCreatedate(currentTime);
        return R.ok(oatportaletdocumentService.save(oatportaletdocument));
    }

/*
    */
/**
     * 修改规章制度
     * @param oatportaletdocument 规章制度     @PreAuthorize("@pms.hasPermission('admin_oatportaletdocument_edit')" )
     * @return R
     */

    @ApiOperation(value = "修改规章制度", notes = "修改规章制度")
    @SysLog("修改规章制度" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody Oatportaletdocument oatportaletdocument) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		oatportaletdocument.setCorpcode(corpcode);
		UpdateWrapper<Oatportaletdocument> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("docid",oatportaletdocument.getDocid());
        return R.ok(oatportaletdocumentService.update(oatportaletdocument,updateWrapper));
    }


/**
     * 通过id删除规章制度
     * @param docid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除规章制度", notes = "通过id删除规章制度")
    @SysLog("通过id删除规章制度" )
    @DeleteMapping("/{docid}" )
    @PreAuthorize("@pms.hasPermission('admin_oatportaletdocument_del')" )
    public R removeById(@PathVariable Integer docid) {
        return R.ok(oatportaletdocumentService.removeById(docid));
    }
*/

}
