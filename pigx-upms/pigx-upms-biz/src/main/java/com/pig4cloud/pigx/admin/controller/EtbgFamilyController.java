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
import com.pig4cloud.pigx.admin.mapper.EtbgFamilyMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgFamily;
import com.pig4cloud.pigx.admin.service.EtbgFamilyService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-04-13 10:23:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgfamily" )
@Api(value = "etbgfamily", tags = "家庭信息管理")
public class EtbgFamilyController {

    private final  EtbgFamilyService etbgFamilyService;
    private final EtbgFamilyMapper etbgFamilyMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgFamily 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgFamilyPage(Page page, EtbgFamily etbgFamily) {
        return R.ok(etbgFamilyService.page(page, Wrappers.query(etbgFamily)));
    }
	/**
	 * 查询所有
	 * @param etbgFamily
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllEtbgFamily" )
	public R getAllEtbgFamily(EtbgFamily etbgFamily) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgFamily.setCorpcode(corpcode);
		etbgFamily.setUserid(userid);
		return R.ok(etbgFamilyService.list(Wrappers.query(etbgFamily)));
	}

    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgFamilyService.getById(id));
    }

    /**
     * 新增
     * @param etbgFamily      @PreAuthorize("@pms.hasPermission('admin_etbgfamily_add')" )
     * @return R
     */
    @ApiOperation(value = "新增家庭成员", notes = "新增家庭成员")
    @SysLog("新增" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgFamily etbgFamily) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgFamily.setCorpcode(corpcode);
		etbgFamily.setUserid(userid);
    	return R.ok(etbgFamilyService.save(etbgFamily));
    }

    /**
     * 修改
     * @param etbgFamily       @PreAuthorize("@pms.hasPermission('admin_etbgfamily_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改家庭成员", notes = "修改家庭成员")
    @SysLog("修改家庭成员" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgFamily etbgFamily) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgFamily> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgFamily.getId());
		return R.ok(etbgFamilyService.update(etbgFamily,updateWrapper));
    }
	/**
	 * 删除家庭成员
	 * @param etbgFamily       @PreAuthorize("@pms.hasPermission('admin_etbgfamily_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "删除家庭成员", notes = "删除家庭成员")
	@SysLog("删除家庭成员" )
	@PostMapping("/invalidEtbgFamily")
	public R invalidEtbgFamily(@RequestBody EtbgFamily etbgFamily) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgFamily> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgFamily.getId());
		etbgFamily.setIsdisabled(1);
		return R.ok(etbgFamilyService.update(etbgFamily,updateWrapper));
	}

 /*   *//**
     * 通过id删除
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_etbgfamily_del')" )
     * @return R
     *//*
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgFamilyService.removeById(id));
    }
	*/
    /**
	 * 分员工档案-家庭成员
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工档案-家庭成员", notes = "员工档案-家庭成员")
	@PostMapping("/getEtbgEducationAllBySql" )
	public R getEtbgEducationAllBySql(Page page,EtbgFamily etbgFamily) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgFamily.setCorpcode(corpcode);
		IPage resultpage = etbgFamilyMapper.listEtbgFamilyAllBySql(page,etbgFamily);
		return R.ok(resultpage);
	}
}
