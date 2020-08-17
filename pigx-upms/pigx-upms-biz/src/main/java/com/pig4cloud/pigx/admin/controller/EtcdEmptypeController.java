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
import com.pig4cloud.pigx.admin.entity.EtcdEmpstatus;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdEmptype;
import com.pig4cloud.pigx.admin.service.EtcdEmptypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 员工类型
 *
 * @author gaoxiao
 * @date 2020-04-15 19:13:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdemptype" )
@Api(value = "etcdemptype", tags = "工作性质管理")
public class EtcdEmptypeController {

    private final  EtcdEmptypeService etcdEmptypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdEmptype 员工类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdEmptypePage(Page page, EtcdEmptype etcdEmptype) {
        return R.ok(etcdEmptypeService.page(page, Wrappers.query(etcdEmptype)));
    }
	/**
	 * 查询所有
	 * @param etcdEmptype
	 * @return
	 */
	@ApiOperation(value = "工作性质查询所有", notes = "工作性质查询所有")
	@GetMapping("/getAllEtcdEmptype" )
	public R getAllEtcdEmptype(EtcdEmptype etcdEmptype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etcdEmptype.setCorpcode(corpcode);
		return R.ok(etcdEmptypeService.list(Wrappers.query(etcdEmptype)));
	}

    /**
     * 通过id查询员工类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdEmptypeService.getById(id));
    }

    /**
     * 新增员工类型
     * @param etcdEmptype 员工类型     @PreAuthorize("@pms.hasPermission('admin_etcdemptype_add')" )
     * @return R
     */
    @ApiOperation(value = "新增员工类型", notes = "新增员工类型")
    @SysLog("新增员工类型" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdEmptype etcdEmptype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdEmptype.setCorpcode(corpcode);
		etcdEmptype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdEmptype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEmptype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdEmptypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdEmptypeService.save(etcdEmptype));
    }

    /**
     * 修改员工类型
     * @param etcdEmptype 员工类型     @PreAuthorize("@pms.hasPermission('admin_etcdemptype_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改员工类型", notes = "修改员工类型")
    @SysLog("修改员工类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdEmptype etcdEmptype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdEmptype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEmptype.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdEmptype.getId());
		List list = etcdEmptypeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdEmptype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdEmptype.getId());
        return R.ok(etcdEmptypeService.update(etcdEmptype,updateWrapper));
    }

    /**
     * 通过id删除员工类型      @PreAuthorize("@pms.hasPermission('admin_etcdemptype_del')" )
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除员工类型", notes = "通过id删除员工类型")
    @SysLog("通过id删除员工类型" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdEmptypeService.removeById(id));
    }

}
