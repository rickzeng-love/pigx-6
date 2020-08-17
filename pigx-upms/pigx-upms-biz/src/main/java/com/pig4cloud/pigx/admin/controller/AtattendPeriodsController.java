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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtattendPeriods;
import com.pig4cloud.pigx.admin.service.AtattendPeriodsService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 年度考勤区间
 *
 * @author gaoxiao
 * @date 2020-07-08 13:32:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atattendperiods" )
@Api(value = "atattendperiods", tags = "年度考勤区间管理")
public class AtattendPeriodsController {

    private final  AtattendPeriodsService atattendPeriodsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atattendPeriods 年度考勤区间
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtattendPeriodsPage" )
    public R getAtattendPeriodsPage(Page page,@RequestBody(required=false)  AtattendPeriods atattendPeriods) {
        if(StringUtils.isEmpty(atattendPeriods)){
			atattendPeriods = new AtattendPeriods();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atattendPeriods.setCorpcode(corpcode);
    	return R.ok(atattendPeriodsService.page(page, Wrappers.query(atattendPeriods)));
    }


    /**
     * 通过id查询年度考勤区间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atattendPeriodsService.getById(id));
    }

    /**
     * 新增年度考勤区间
     * @param atattendPeriods 年度考勤区间
     * @return R
     */
    @ApiOperation(value = "新增年度考勤区间", notes = "新增年度考勤区间")
    @SysLog("新增年度考勤区间" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atattendperiods_add')" )
    public R save(@RequestBody AtattendPeriods atattendPeriods) {
        return R.ok(atattendPeriodsService.save(atattendPeriods));
    }

    /**
     * 修改年度考勤区间
     * @param atattendPeriods 年度考勤区间
     * @return R
     */
    @ApiOperation(value = "修改年度考勤区间", notes = "修改年度考勤区间")
    @SysLog("修改年度考勤区间" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atattendperiods_edit')" )
    public R updateById(@RequestBody AtattendPeriods atattendPeriods) {
        return R.ok(atattendPeriodsService.updateById(atattendPeriods));
    }

    /**
     * 通过id删除年度考勤区间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除年度考勤区间", notes = "通过id删除年度考勤区间")
    @SysLog("通过id删除年度考勤区间" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atattendperiods_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atattendPeriodsService.removeById(id));
    }

}
