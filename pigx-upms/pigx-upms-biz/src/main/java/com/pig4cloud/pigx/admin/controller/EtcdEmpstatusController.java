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
import com.pig4cloud.pigx.admin.entity.EtcdCountry;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdEmpstatus;
import com.pig4cloud.pigx.admin.service.EtcdEmpstatusService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 员工状态
 *
 * @author gaoxiao
 * @date 2020-04-15 19:15:48
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdempstatus" )
@Api(value = "etcdempstatus", tags = "员工状态管理")
public class EtcdEmpstatusController {

    private final  EtcdEmpstatusService etcdEmpstatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdEmpstatus 员工状态
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdEmpstatusPage(Page page, EtcdEmpstatus etcdEmpstatus) {
		etcdEmpstatus.setIsdisabled(0);
        return R.ok(etcdEmpstatusService.page(page, Wrappers.query(etcdEmpstatus)));
    }
	/**
	 * 查询所有
	 * @param etcdEmpstatus
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtcdEmpstatus" )
	public R getAllEtcdEmpstatus(EtcdEmpstatus etcdEmpstatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etemployee.setCorpcode(corpcode);
		return R.ok(etcdEmpstatusService.list(Wrappers.query(etcdEmpstatus)));
	}

    /**
     * 通过id查询员工状态
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdEmpstatusService.getById(id));
    }

    /**
     * 新增员工状态
     * @param etcdEmpstatus 员工状态     @PreAuthorize("@pms.hasPermission('admin_etcdempstatus_add')" )
     * @return R
     */
    @ApiOperation(value = "新增员工状态", notes = "新增员工状态")
    @SysLog("新增员工状态" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdEmpstatus etcdEmpstatus) {
		QueryWrapper<EtcdEmpstatus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEmpstatus.getTitle());
		List list = etcdEmpstatusService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdEmpstatusService.save(etcdEmpstatus));
    }

    /**
     * 修改员工状态     @PreAuthorize("@pms.hasPermission('admin_etcdempstatus_edit')" )
     * @param etcdEmpstatus 员工状态
     * @return R
     */
    @ApiOperation(value = "修改员工状态", notes = "修改员工状态")
    @SysLog("修改员工状态" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdEmpstatus etcdEmpstatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdEmpstatus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEmpstatus.getTitle());
		queryWrapper.ne("id",etcdEmpstatus.getId());
		List list = etcdEmpstatusService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdEmpstatus> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdEmpstatus.getId());
        return R.ok(etcdEmpstatusService.update(etcdEmpstatus,updateWrapper));
    }

    /**
     * 通过id删除员工状态
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_etcdempstatus_del')" )
     * @return R
     */
    @ApiOperation(value = "通过id删除员工状态", notes = "通过id删除员工状态")
    @SysLog("通过id删除员工状态" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdEmpstatusService.removeById(id));
    }

}
