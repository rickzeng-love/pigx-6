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
import com.pig4cloud.pigx.admin.entity.AtchangeAll;
import com.pig4cloud.pigx.admin.service.AtchangeAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 变动登记历史
 *
 * @author gaoxiao
 * @date 2020-07-07 14:02:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atchangeall" )
@Api(value = "atchangeall", tags = "变动登记历史管理")
public class AtchangeAllController {

    private final  AtchangeAllService atchangeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atchangeAll 变动登记历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtchangeAllPage" )
    public R getAtchangeAllPage(Page page,@RequestBody(required = false) AtchangeAll atchangeAll) {
    	if(StringUtils.isEmpty(atchangeAll)){
    		atchangeAll = new AtchangeAll();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atchangeAll.setCorpcode(corpcode);
        return R.ok(atchangeAllService.page(page, Wrappers.query(atchangeAll)));
    }


    /**
     * 通过id查询变动登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atchangeAllService.getById(id));
    }

    /**
     * 新增变动登记历史
     * @param atchangeAll 变动登记历史
     * @return R
     */
    @ApiOperation(value = "新增变动登记历史", notes = "新增变动登记历史")
    @SysLog("新增变动登记历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atchangeall_add')" )
    public R save(@RequestBody AtchangeAll atchangeAll) {
        return R.ok(atchangeAllService.save(atchangeAll));
    }

    /**
     * 修改变动登记历史
     * @param atchangeAll 变动登记历史
     * @return R
     */
    @ApiOperation(value = "修改变动登记历史", notes = "修改变动登记历史")
    @SysLog("修改变动登记历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atchangeall_edit')" )
    public R updateById(@RequestBody AtchangeAll atchangeAll) {
        return R.ok(atchangeAllService.updateById(atchangeAll));
    }

    /**
     * 通过id删除变动登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除变动登记历史", notes = "通过id删除变动登记历史")
    @SysLog("通过id删除变动登记历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atchangeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atchangeAllService.removeById(id));
    }

}
