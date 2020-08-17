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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdEdulength;
import com.pig4cloud.pigx.admin.service.EtcdEdulengthService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 学制
 *
 * @author gaoxiao
 * @date 2020-06-01 17:17:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdedulength" )
@Api(value = "etcdedulength", tags = "学制管理")
public class EtcdEdulengthController {

    private final  EtcdEdulengthService etcdEdulengthService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdEdulength 学制
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdEdulengthPage(Page page, EtcdEdulength etcdEdulength) {
        return R.ok(etcdEdulengthService.page(page, Wrappers.query(etcdEdulength)));
    }


    /**
     * 通过id查询学制
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdEdulengthService.getById(id));
    }

    /**
     * 新增学制     @PreAuthorize("@pms.hasPermission('admin_etcdedulength_add')" )
     * @param etcdEdulength 学制
     * @return R
     */
    @ApiOperation(value = "新增学制", notes = "新增学制")
    @SysLog("新增学制" )
    @PostMapping("/save")
    public R save(@RequestBody EtcdEdulength etcdEdulength) {
		QueryWrapper<EtcdEdulength> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEdulength.getTitle());
		List list = etcdEdulengthService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdEdulengthService.save(etcdEdulength));
    }

    /**
     * 修改学制
     * @param etcdEdulength 学制      @PreAuthorize("@pms.hasPermission('admin_etcdedulength_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改学制", notes = "修改学制")
    @SysLog("修改学制" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtcdEdulength etcdEdulength) {
		QueryWrapper<EtcdEdulength> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title",etcdEdulength.getTitle());
		queryWrapper.ne("id",etcdEdulength.getId());
		List list = etcdEdulengthService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("名称重复，请核实！");
		}
        return R.ok(etcdEdulengthService.updateById(etcdEdulength));
    }

    /**
     * 通过id删除学制
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除学制", notes = "通过id删除学制")
    @SysLog("通过id删除学制" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcdedulength_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdEdulengthService.removeById(id));
    }

}
