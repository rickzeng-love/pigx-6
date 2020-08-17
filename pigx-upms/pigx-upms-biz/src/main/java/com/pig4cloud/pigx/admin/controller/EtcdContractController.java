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
import com.pig4cloud.pigx.admin.entity.EtcdContract;
import com.pig4cloud.pigx.admin.service.EtcdContractService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 签约单位
 *
 * @author gaoxiao
 * @date 2020-06-24 11:20:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdcontract" )
@Api(value = "etcdcontract", tags = "签约单位管理")
public class EtcdContractController {

    private final  EtcdContractService etcdContractService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdContract 签约单位
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdContractPage(Page page, EtcdContract etcdContract) {
        return R.ok(etcdContractService.page(page, Wrappers.query(etcdContract)));
    }


    /**
     * 通过id查询签约单位
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdContractService.getById(id));
    }

	/**
	 * 合同签约单位查询所有
	 * @param etcdContract 合同属性
	 * @return
	 */
	@ApiOperation(value = "合同签约单位查询所有", notes = "合同签约单位查询所有")
	@PostMapping("/getAllEtcdContract" )
	public R getAllEtcdContract(  EtcdContract etcdContract) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdContract.setCorpcode(corpcode);
		return R.ok(etcdContractService.list(Wrappers.query(etcdContract)));
	}
    /**
     * 新增签约单位     @PreAuthorize("@pms.hasPermission('admin_etcdcontract_add')" )
     * @param etcdContract 签约单位
     * @return R
     */
    @ApiOperation(value = "新增签约单位", notes = "新增签约单位")
    @SysLog("新增签约单位" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdContract etcdContract) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etcdContract.setCorpcode(corpcode);
		etcdContract.setCorpid(pigxUser.getCorpid());
		QueryWrapper<EtcdContract> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdContract.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		List list = etcdContractService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdContractService.save(etcdContract));
    }

    /**
     * 修改签约单位
     * @param etcdContract 签约单位      @PreAuthorize("@pms.hasPermission('admin_etcdcontract_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改签约单位", notes = "修改签约单位")
    @SysLog("修改签约单位" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdContract etcdContract) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<EtcdContract> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdContract.getTitle());
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",etcdContract.getId());
		List list = etcdContractService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
		UpdateWrapper<EtcdContract> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etcdContract.getId());
        return R.ok(etcdContractService.update(etcdContract,updateWrapper));
    }

    /**
     * 通过id删除签约单位
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除签约单位", notes = "通过id删除签约单位")
    @SysLog("通过id删除签约单位" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdcontract_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdContractService.removeById(id));
    }

}
