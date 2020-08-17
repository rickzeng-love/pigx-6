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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgServingrecords;
import com.pig4cloud.pigx.admin.service.EtbgServingrecordsService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 任职记录
 *
 * @author gaoxiao
 * @date 2020-04-13 10:21:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgservingrecords" )
@Api(value = "etbgservingrecords", tags = "任职记录管理")
public class EtbgServingrecordsController {

    private final  EtbgServingrecordsService etbgServingrecordsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgServingrecords 任职记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgServingrecordsPage(Page page, EtbgServingrecords etbgServingrecords) {
        return R.ok(etbgServingrecordsService.page(page, Wrappers.query(etbgServingrecords)));
    }
	/**
	 * 查询所有
	 * @param etbgServingrecords
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgServingrecords" )
	public R getAllEtbgServingrecords(EtbgServingrecords etbgServingrecords) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgServingrecords.setCorpcode(corpcode);
		etbgServingrecords.setUserid(userid);
		return R.ok(etbgServingrecordsService.list(Wrappers.query(etbgServingrecords)));
	}

    /**
     * 通过id查询任职记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgServingrecordsService.getById(id));
    }

    /**
     * 新增任职记录
     * @param etbgServingrecords 任职记录      @PreAuthorize("@pms.hasPermission('admin_etbgservingrecords_add')" )
     * @return R
     */
    @ApiOperation(value = "新增任职记录", notes = "新增任职记录")
    @SysLog("新增任职记录" )
    @PostMapping
    public R save(@RequestBody EtbgServingrecords etbgServingrecords) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgServingrecords.setCorpcode(corpcode);
		etbgServingrecords.setUserid(userid);
        return R.ok(etbgServingrecordsService.save(etbgServingrecords));
    }

    /**
     * 修改任职记录      @PreAuthorize("@pms.hasPermission('admin_etbgservingrecords_edit')" )
     * @param etbgServingrecords 任职记录
     * @return R
     */
    @ApiOperation(value = "修改任职记录", notes = "修改任职记录")
    @SysLog("修改任职记录" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgServingrecords etbgServingrecords) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgServingrecords> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgServingrecords.getId());
        return R.ok(etbgServingrecordsService.update(etbgServingrecords,updateWrapper));
    }
/*

    */
/**
     * 通过id删除任职记录      @PreAuthorize("@pms.hasPermission('admin_etbgservingrecords_del')" )
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除任职记录", notes = "通过id删除任职记录")
    @SysLog("通过id删除任职记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgServingrecordsService.removeById(id));
    }
*/

}
