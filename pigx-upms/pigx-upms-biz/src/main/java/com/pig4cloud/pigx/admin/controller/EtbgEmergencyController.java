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
import com.pig4cloud.pigx.admin.entity.EtbgWorking;
import com.pig4cloud.pigx.admin.mapper.EtbgEmergencyMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgEmergency;
import com.pig4cloud.pigx.admin.service.EtbgEmergencyService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 紧急联系人
 *
 * @author gaoxiao
 * @date 2020-04-13 09:39:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgemergency" )
@Api(value = "etbgemergency", tags = "紧急联系人管理")
public class EtbgEmergencyController {

    private final  EtbgEmergencyService etbgEmergencyService;
    private final EtbgEmergencyMapper etbgEmergencyMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgEmergency 紧急联系人
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgEmergencyPage(Page page, EtbgEmergency etbgEmergency) {
        return R.ok(etbgEmergencyService.page(page, Wrappers.query(etbgEmergency)));
    }
	/**
	 * 查询所有
	 * @param etbgEmergency
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgEmergency" )
	public R getAllEtbgEmergency(EtbgEmergency etbgEmergency) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgEmergency.setCorpcode(corpcode);
		etbgEmergency.setUserid(userid);
		return R.ok(etbgEmergencyService.list(Wrappers.query(etbgEmergency)));
	}

    /**
     * 通过id查询紧急联系人
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgEmergencyService.getById(id));
    }

    /**
     * 新增紧急联系人
     * @param etbgEmergency 紧急联系人     @PreAuthorize("@pms.hasPermission('admin_etbgemergency_add')" )
     * @return R
     */
    @ApiOperation(value = "新增紧急联系人", notes = "新增紧急联系人")
    @SysLog("新增紧急联系人" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgEmergency etbgEmergency) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgEmergency.setCorpcode(corpcode);
		etbgEmergency.setUserid(userid);
    	return R.ok(etbgEmergencyService.save(etbgEmergency));
    }

    /**
     * 修改紧急联系人
     * @param etbgEmergency 紧急联系人    @PreAuthorize("@pms.hasPermission('admin_etbgemergency_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改紧急联系人", notes = "修改紧急联系人")
    @SysLog("修改紧急联系人" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgEmergency etbgEmergency) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgEmergency> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgEmergency.getId());
		return R.ok(etbgEmergencyService.update(etbgEmergency,updateWrapper));
    }

	/**
	 * 删除紧急联系人
	 * @param etbgEmergency 删除紧急联系人    @PreAuthorize("@pms.hasPermission('admin_etbgemergency_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "删除紧急联系人", notes = "删除紧急联系人")
	@SysLog("删除紧急联系人" )
	@PostMapping("invalidEtbgEmergency")
	public R invalidEtbgEmergency(@RequestBody EtbgEmergency etbgEmergency) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgEmergency> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgEmergency.getId());
		etbgEmergency.setIsdisabled(1);
		return R.ok(etbgEmergencyService.update(etbgEmergency,updateWrapper));
	}

/*
    */
/**
     * 通过id删除紧急联系人
     * @param id id    @PreAuthorize("@pms.hasPermission('admin_etbgemergency_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除紧急联系人", notes = "通过id删除紧急联系人")
    @SysLog("通过id删除紧急联系人" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgEmergencyService.removeById(id));
    }
*/


	/**
	 * 查询所有
	 * @param etbgEmergency
	 * @return
	 */
	@ApiOperation(value = "根据EID查询紧急联系人", notes = "根据EID查询紧急联系人")
	@PostMapping("/getEtbgEmergencyBySql" )
	public R listEtbgEmergencyBySql(@RequestBody EtbgEmergency etbgEmergency) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgEmergency.setCorpcode(corpcode);
		Integer eid = etbgEmergency.getEid();
		if(StringUtils.isEmpty(eid)) {
			return R.ok("EID不能为空！");
		}
		return R.ok(etbgEmergencyMapper.listEtbgEmergencyBySql(etbgEmergency));
	}
}
