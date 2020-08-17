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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdCerttype;
import com.pig4cloud.pigx.admin.service.EtcdCerttypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 证件类型
 *
 * @author gaoxiao
 * @date 2020-04-09 18:50:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcerttype" )
@Api(value = "etcdcerttype", tags = "证件类型管理")
public class EtcdCerttypeController {

    private final  EtcdCerttypeService etcdCerttypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdCerttype 证件类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdCerttypePage(Page page, EtcdCerttype etcdCerttype) {
        return R.ok(etcdCerttypeService.page(page, Wrappers.query(etcdCerttype)));
    }

	/**
	 * 证件类型查询所有
	 * @param etcdCerttype 员工
	 * @return
	 */
	@ApiOperation(value = "证件类型查询所有", notes = "证件类型查询所有")
	@GetMapping("/getAllEtcdCerttype" )
	public R getAllOtcdEmpgrade(EtcdCerttype etcdCerttype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etcdCerttype.setCorpcode(corpcode);
		return R.ok(etcdCerttypeService.list(Wrappers.query(etcdCerttype)));
	}
    /**
     * 通过id查询证件类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdCerttypeService.getById(id));
    }

    /**
     * 新增证件类型
     * @param etcdCerttype 证件类型      @PreAuthorize("@pms.hasPermission('admin_etcdcerttype_add')" )
     * @return R
     */
    @ApiOperation(value = "新增证件类型", notes = "新增证件类型")
    @SysLog("新增证件类型" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdCerttype etcdCerttype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdCerttype.setCorpcode(corpcode);
		etcdCerttype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdCerttype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCerttype.getTitle());
		//queryWrapper.eq("corpcode",corpcode);

		List list = etcdCerttypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdCerttypeService.save(etcdCerttype));
    }

    /**
     * 修改证件类型
     * @param etcdCerttype 证件类型      @PreAuthorize("@pms.hasPermission('admin_etcdcerttype_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改证件类型", notes = "修改证件类型")
    @SysLog("修改证件类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdCerttype etcdCerttype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdCerttype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdCerttype.getTitle());
		//queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdCerttype.getId());
		List list = etcdCerttypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdCerttype> updateWrapper = new UpdateWrapper<>();
		//updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdCerttype.getId());
		etcdCerttypeService.update(etcdCerttype,updateWrapper);
        return R.ok("修改成功！");
    }

    /**
     * 通过id删除证件类型     @PreAuthorize("@pms.hasPermission('admin_etcdcerttype_del')" )
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除证件类型", notes = "通过id删除证件类型")
    @SysLog("通过id删除证件类型" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdCerttypeService.removeById(id));
    }

}
