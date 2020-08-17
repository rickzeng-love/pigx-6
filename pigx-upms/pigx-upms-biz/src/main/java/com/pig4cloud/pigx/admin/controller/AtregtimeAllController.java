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
import com.pig4cloud.pigx.admin.entity.AtregtimeAll;
import com.pig4cloud.pigx.admin.service.AtregtimeAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 加班登记历史
 *
 * @author shishengjie
 * @date 2020-07-22 14:32:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atregtimeall" )
@Api(value = "atregtimeall", tags = "加班登记历史管理")
public class AtregtimeAllController {

    private final  AtregtimeAllService atregtimeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atregtimeAll 加班登记历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtregtimeAllPage(Page page, AtregtimeAll atregtimeAll) {
        return R.ok(atregtimeAllService.page(page, Wrappers.query(atregtimeAll)));
    }


    /**
     * 通过id查询加班登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atregtimeAllService.getById(id));
    }

    /**
     * 新增加班登记历史
     * @param atregtimeAll 加班登记历史
     * @return R
     */
    @ApiOperation(value = "新增加班登记历史", notes = "新增加班登记历史")
    @SysLog("新增加班登记历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atregtimeall_add')" )
    public R save(@RequestBody AtregtimeAll atregtimeAll) {
        return R.ok(atregtimeAllService.save(atregtimeAll));
    }

    /**
     * 修改加班登记历史
     * @param atregtimeAll 加班登记历史
     * @return R
     */
    @ApiOperation(value = "修改加班登记历史", notes = "修改加班登记历史")
    @SysLog("修改加班登记历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atregtimeall_edit')" )
    public R updateById(@RequestBody AtregtimeAll atregtimeAll) {
        return R.ok(atregtimeAllService.updateById(atregtimeAll));
    }

    /**
     * 通过id删除加班登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除加班登记历史", notes = "通过id删除加班登记历史")
    @SysLog("通过id删除加班登记历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atregtimeall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atregtimeAllService.removeById(id));
    }

}
