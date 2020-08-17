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
import com.pig4cloud.pigx.admin.entity.CtcdCitypay;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdCity;
import com.pig4cloud.pigx.admin.service.EtcdCityService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资城市
 *
 * @author gaoxiao
 * @date 2020-07-10 15:44:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcity" )
@Api(value = "etcdcity", tags = "薪资城市管理")
public class EtcdCityController {

    private final  EtcdCityService etcdCityService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdCity 薪资城市
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdCityPage(Page page, EtcdCity etcdCity) {
        return R.ok(etcdCityService.page(page, Wrappers.query(etcdCity)));
    }

	/**
	 * 薪资城市
	 * @param etcdCity 薪资城市
	 * @return
	 */
	@ApiOperation(value = "薪资城市", notes = "薪资城市")
	@PostMapping("/getAllEtcdCity" )
	public R getAllCtcdCitypay(@RequestBody(required = false) EtcdCity etcdCity) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(etcdCity)){
			etcdCity = new EtcdCity();
		}
		etcdCity.setCorpcode(corpcode);
		return R.ok(etcdCityService.list(Wrappers.query(etcdCity)));
	}
    /**
     * 通过id查询薪资城市
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdCityService.getById(id));
    }

    /**
     * 新增薪资城市
     * @param etcdCity 薪资城市    @PreAuthorize("@pms.hasPermission('admin_etcdcity_add')" )
     * @return R
     */
    @ApiOperation(value = "新增薪资城市", notes = "新增薪资城市")
    @SysLog("新增薪资城市" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdCity etcdCity) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		etcdCity.setCorpcode(corpcode);
		etcdCity.setCorpid(corpid);
        return R.ok(etcdCityService.save(etcdCity));
    }

    /**
     * 修改薪资城市
     * @param etcdCity 薪资城市
     * @return R
     */
//    @ApiOperation(value = "修改薪资城市", notes = "修改薪资城市")
//    @SysLog("修改薪资城市" )
//    @PutMapping
//    @PreAuthorize("@pms.hasPermission('admin_etcdcity_edit')" )
//    public R updateById(@RequestBody EtcdCity etcdCity) {
//        return R.ok(etcdCityService.updateById(etcdCity));
//    }

	/**
	 * 修改薪资城市
	 * @param etcdCity 薪资城市
	 * @return R
	 */
	@ApiOperation(value = "修改薪资城市", notes = "修改薪资城市")
	@SysLog("修改薪资城市" )
	@PostMapping("/updateById")
//	@PreAuthorize("@pms.hasPermission('admin_etcdcity_edit')" )
	public R updateById(@RequestBody EtcdCity etcdCity) {
		return R.ok(etcdCityService.updateById(etcdCity));
	}



    /**
     * 通过id删除薪资城市
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资城市", notes = "通过id删除薪资城市")
    @SysLog("通过id删除薪资城市" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdcity_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdCityService.removeById(id));
    }

}
