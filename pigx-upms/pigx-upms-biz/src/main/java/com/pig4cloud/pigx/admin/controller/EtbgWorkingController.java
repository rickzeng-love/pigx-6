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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtbgEducation;
import com.pig4cloud.pigx.admin.mapper.EtbgWorkingMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgWorking;
import com.pig4cloud.pigx.admin.service.EtbgWorkingService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 工作经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:40:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgworking" )
@Api(value = "etbgworking", tags = "工作经历管理")
public class EtbgWorkingController {

    private final  EtbgWorkingService etbgWorkingService;
    private final EtbgWorkingMapper etbgWorkingMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgWorking 工作经历
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgWorkingPage(Page page, EtbgWorking etbgWorking) {
        return R.ok(etbgWorkingService.page(page, Wrappers.query(etbgWorking)));
    }

	/**
	 * 查询所有
	 * @param etbgWorking
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgWorking" )
	public R getAllEtbgWorking(EtbgWorking etbgWorking) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgWorking.setCorpcode(corpcode);
		etbgWorking.setUserid(userid);
		return R.ok(etbgWorkingService.list(Wrappers.query(etbgWorking)));
	}

    /**
     * 通过id查询工作经历
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgWorkingService.getById(id));
    }

    /**
     * 新增工作经历
     * @param etbgWorking 工作经历     @PreAuthorize("@pms.hasPermission('admin_etbgworking_add')" )
     * @return R
     */
    @ApiOperation(value = "新增工作经历", notes = "新增工作经历")
    @SysLog("新增工作经历" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgWorking etbgWorking) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgWorking.setCorpcode(corpcode);
		etbgWorking.setUserid(userid);
        return R.ok(etbgWorkingService.save(etbgWorking));
    }

    /**
     * 修改工作经历
     * @param etbgWorking 工作经历     @PreAuthorize("@pms.hasPermission('admin_etbgworking_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改工作经历", notes = "修改工作经历")
    @SysLog("修改工作经历" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgWorking etbgWorking) {

		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgWorking> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgWorking.getId());
		return R.ok(etbgWorkingService.update(etbgWorking,updateWrapper));
    }
	/**
	 * 修改工作经历
	 * @param etbgWorking 工作经历     @PreAuthorize("@pms.hasPermission('admin_etbgworking_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "删除工作经历", notes = "删除工作经历")
	@SysLog("删除工作经历" )
	@PostMapping("/invalidEtbgWorking")
	public R invalidEtbgWorking(@RequestBody EtbgWorking etbgWorking){
		etbgWorking.setIsdisabled(1);
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgWorking> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgWorking.getId());
		return R.ok(etbgWorkingService.update(etbgWorking,updateWrapper));
	}
/*
    */
/**
     * 通过id删除工作经历
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_etbgworking_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除工作经历", notes = "通过id删除工作经历")
    @SysLog("通过id删除工作经历" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgWorkingService.removeById(id));
    }
*/

    /**
	 * 查询所有
	 * @param etbgWorking
	 * @return
	 */
	@ApiOperation(value = "根据EID查询工作经历", notes = "根据EID查询学历")
	@PostMapping("/getEtbgWorkingBySql" )
	public R getEtbgWorkingBySql(@RequestBody EtbgWorking etbgWorking) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgWorking.setCorpcode(corpcode);
		Integer eid = etbgWorking.getEid();
		if(StringUtils.isEmpty(eid)){
			return R.ok("EID不能为空！");
		}
		return R.ok(etbgWorkingMapper.listEtbgWorkingBySql(etbgWorking));
	}

	/**
	 * 分员工档案-教育工作经历
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工档案-工作经历", notes = "员工档案-工作经历")
	@PostMapping("/getEtbgWorkingAllBySql" )
	public R getEtbgWorkingAllBySql(Page page,EtbgWorking etbgWorking) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgWorking.setCorpcode(corpcode);
		IPage resultpage = etbgWorkingMapper.listEtbgWorkingAllBySql(page,etbgWorking);
		return R.ok(resultpage);
	}

}
