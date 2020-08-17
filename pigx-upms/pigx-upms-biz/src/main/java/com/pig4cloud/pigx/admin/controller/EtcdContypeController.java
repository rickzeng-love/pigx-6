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
import com.pig4cloud.pigx.admin.entity.EtcdConproperty;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdContype;
import com.pig4cloud.pigx.admin.service.EtcdContypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 合同类型
 *
 * @author gaoxiao
 * @date 2020-06-23 16:32:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcontype" )
@Api(value = "etcdcontype", tags = "合同类型管理")
public class EtcdContypeController {

    private final  EtcdContypeService etcdContypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdContype 合同类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdContypePage(Page page, EtcdContype etcdContype) {
        return R.ok(etcdContypeService.page(page, Wrappers.query(etcdContype)));
    }

	/**
	 * 合同类型查询所有
	 * @param etcdContype 合同类型
	 * @return
	 */
	@ApiOperation(value = "合同类型查询所有", notes = "合同类型查询所有")
	@PostMapping("/getAllEtcdContype" )
	public R getAllEtcdContype( EtcdContype etcdContype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdContype.setCorpcode(corpcode);
		return R.ok(etcdContypeService.list(Wrappers.query(etcdContype)));
	}
    /**
     * 通过id查询合同类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdContypeService.getById(id));
    }

    /**
     * 新增合同类型     @PreAuthorize("@pms.hasPermission('admin_etcdcontype_add')" )
     * @param etcdContype 合同类型
     * @return R
     */
    @ApiOperation(value = "新增合同类型", notes = "新增合同类型")
    @SysLog("新增合同类型" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdContype etcdContype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdContype.setCorpcode(corpcode);
		etcdContype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdContype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdContype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdContypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdContypeService.save(etcdContype));
    }

    /**
     * 修改合同类型
     * @param etcdContype 合同类型     @PreAuthorize("@pms.hasPermission('admin_etcdcontype_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改合同类型", notes = "修改合同类型")
    @SysLog("修改合同类型" )
    @PostMapping("updateById")
    public R updateById(@RequestBody EtcdContype etcdContype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdContype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdContype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdContype.getId());
		List list = etcdContypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdContype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdContype.getId());
        return R.ok(etcdContypeService.update(etcdContype,updateWrapper));
    }

    /**
     * 通过id删除合同类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同类型", notes = "通过id删除合同类型")
    @SysLog("通过id删除合同类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdcontype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdContypeService.removeById(id));
    }

}
