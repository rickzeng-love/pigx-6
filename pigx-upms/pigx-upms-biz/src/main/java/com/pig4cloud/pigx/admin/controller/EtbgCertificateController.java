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
import com.pig4cloud.pigx.admin.entity.EtbgAccessorymaterials;
import com.pig4cloud.pigx.admin.mapper.EtbgCertificateMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgCertificate;
import com.pig4cloud.pigx.admin.service.EtbgCertificateService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 证件管理
 *
 * @author gaoxiao
 * @date 2020-04-13 15:27:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgcertificate" )
@Api(value = "etbgcertificate", tags = "证件管理管理")
public class EtbgCertificateController {

    private final  EtbgCertificateService etbgCertificateService;
    private final EtbgCertificateMapper etbgCertificateMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgCertificate 证件管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgCertificatePage(Page page, EtbgCertificate etbgCertificate) {
        return R.ok(etbgCertificateService.page(page, Wrappers.query(etbgCertificate)));
    }
	/**
	 * 查询所有
	 * @param etbgCertificate
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgCertificate" )
	public R getAllEtbgCertificate(EtbgCertificate etbgCertificate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgCertificate.setCorpcode(corpcode);
		etbgCertificate.setUserid(userid);
		return R.ok(etbgCertificateService.list(Wrappers.query(etbgCertificate)));
	}

    /**
     * 通过id查询证件管理
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgCertificateService.getById(id));
    }

    /**
     * 新增证件管理
     * @param etbgCertificate 证件管理     @PreAuthorize("@pms.hasPermission('admin_etbgcertificate_add')" )
     * @return R
     */
    @ApiOperation(value = "新增证件管理", notes = "新增证件管理")
    @SysLog("新增证件管理" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgCertificate etbgCertificate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgCertificate.setCorpcode(corpcode);
		etbgCertificate.setUserid(userid);
        return R.ok(etbgCertificateService.save(etbgCertificate));
    }

    /**
     * 修改证件管理
     * @param etbgCertificate 证件管理      @PreAuthorize("@pms.hasPermission('admin_etbgcertificate_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改证件", notes = "修改证件")
    @SysLog("修改证件" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgCertificate etbgCertificate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgCertificate> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgCertificate.getId());
		return R.ok(etbgCertificateService.update(etbgCertificate,updateWrapper));
    }
	/**
	 * 修改证件管理
	 * @param etbgCertificate 证件管理      @PreAuthorize("@pms.hasPermission('admin_etbgcertificate_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "删除证件", notes = "删除证件")
	@SysLog("删除证件" )
	@PostMapping("/invalidEtbgCertificate")
	public R invalidEtbgCertificate(@RequestBody EtbgCertificate etbgCertificate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgCertificate> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgCertificate.getId());
		etbgCertificate.setIsdisabled(1);
		return R.ok(etbgCertificateService.update(etbgCertificate,updateWrapper));
	}

/*    *//**
     * 通过id删除证件管理
     * @param id id    @PreAuthorize("@pms.hasPermission('admin_etbgcertificate_del')" )
     * @return R
     *//*
    @ApiOperation(value = "通过id删除证件管理", notes = "通过id删除证件管理")
    @SysLog("通过id删除证件管理" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgCertificateService.removeById(id));
    }
	*/
    /**
	 * 分员工档案-证书资料
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工档案-证书资料", notes = "员工档案-证书资料")
	@PostMapping("/getEtbgCertificateAllBySql" )
	public R getEtbgCertificateAllBySql(Page page,EtbgCertificate etbgCertificate) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgCertificate.setCorpcode(corpcode);
		IPage resultpage = etbgCertificateMapper.listEtbgCertificateAllBySql(page,etbgCertificate);
		return R.ok(resultpage);
	}

}
