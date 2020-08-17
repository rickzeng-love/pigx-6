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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtcdCardlostreason;
import com.pig4cloud.pigx.admin.entity.Atstatus;
import com.pig4cloud.pigx.admin.mapper.SystattendanceaddressMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systattendanceaddress;
import com.pig4cloud.pigx.admin.service.SystattendanceaddressService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 打卡地点
 *
 * @author GXS
 * @date 2020-05-22 16:30:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systattendanceaddress" )
@Api(value = "systattendanceaddress", tags = "打卡地点管理")
public class SystattendanceaddressController {

    private final  SystattendanceaddressService systattendanceaddressService;
    private final SystattendanceaddressMapper systattendanceaddressMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systattendanceaddress 打卡地点
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystattendanceaddressPage(Page page, Systattendanceaddress systattendanceaddress) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		systattendanceaddress.setCorpcode(corpCode);
        return R.ok(systattendanceaddressService.page(page, Wrappers.query(systattendanceaddress)));
    }

	/**
	 * 查询所有
	 * @param systattendanceaddress
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllSystattendanceaddress" )
	public R getAllSystattendanceaddress(@RequestBody(required = false) Systattendanceaddress systattendanceaddress) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(systattendanceaddress)){
			systattendanceaddress = new Systattendanceaddress();
		}
		systattendanceaddress.setCorpcode(corpCode);
		return R.ok(systattendanceaddressService.list(Wrappers.query(systattendanceaddress)));
	}


	/**
	 * 查询启用的所有记录
	 * @param systattendanceaddress
	 * @return
	 */
	@ApiOperation(value = "查询启用的所有记录", notes = "查询启用的所有记录")
	@GetMapping("/getUsedSystattendanceaddress" )
	public R getUsedSystattendanceaddress(Systattendanceaddress systattendanceaddress) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		systattendanceaddress.setCorpcode(corpCode);
		return R.ok(systattendanceaddressService.list(Wrappers.query(systattendanceaddress).or().eq("IsUsed",1)));
	}



	/**
     * 通过id查询打卡地点
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systattendanceaddressService.getById(id));
    }

    /**
     * 新增打卡地点
     * @param systattendanceaddress 打卡地点 @PreAuthorize("@pms.hasPermission('admin_systattendanceaddress_add')" )
     * @return R
     */
    @ApiOperation(value = "新增打卡地点", notes = "新增打卡地点")
    @SysLog("新增打卡地点" )
    @PostMapping("/save")
    public R save(@RequestBody Systattendanceaddress systattendanceaddress) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		systattendanceaddress.setCorpid(corpID);
		systattendanceaddress.setCorpcode(corpCode);
        return R.ok(systattendanceaddressService.save(systattendanceaddress));
    }

    /**
     * 修改打卡地点
     * @param systattendanceaddress 打卡地点 @PreAuthorize("@pms.hasPermission('admin_systattendanceaddress_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改打卡地点", notes = "修改打卡地点")
    @SysLog("修改打卡地点" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody Systattendanceaddress systattendanceaddress) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<Systattendanceaddress> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",systattendanceaddress.getId());
        return R.ok(systattendanceaddressService.update(systattendanceaddress,updateWrapper));
    }

/*
    */
/**
     * 通过id删除打卡地点
     * @param id id @PreAuthorize("@pms.hasPermission('admin_systattendanceaddress_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除打卡地点", notes = "通过id删除打卡地点")
    @SysLog("通过id删除打卡地点" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systattendanceaddressService.removeById(id));
    }
*/

	/**
	 * 获取当前用户打卡经纬度信息
	 * @return R
	 */
	@ApiOperation(value = "获取当前用户打卡经纬度信息", notes = "获取当前用户打卡经纬度信息")
	@PostMapping("/getAttendaceaddreddForUser" )
	public R getAttendaceaddreddForUser() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int eid = pigxUser.getEid();
		Atstatus atstatus = new Atstatus();
		atstatus.setCorpcode(corpcode);
		atstatus.setEid(eid);
		return R.ok(systattendanceaddressMapper.listAttendaceaddreddForUser(atstatus));
	}

}
