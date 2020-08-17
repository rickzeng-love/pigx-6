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
import com.pig4cloud.pigx.admin.entity.AtregtimeRegister;
import com.pig4cloud.pigx.admin.service.AtregtimeRegisterService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 加班登记
 *
 * @author gaoxiao
 * @date 2020-06-25 10:26:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atregtimeregister" )
@Api(value = "atregtimeregister", tags = "加班登记管理")
public class AtregtimeRegisterController {

    private final  AtregtimeRegisterService atregtimeRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atregtimeRegister 加班登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtregtimeRegisterPage(Page page, AtregtimeRegister atregtimeRegister) {
        return R.ok(atregtimeRegisterService.page(page, Wrappers.query(atregtimeRegister)));
    }


    /**
     * 通过id查询加班登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atregtimeRegisterService.getById(id));
    }

    /**
     * 新增加班登记
     * @param atregtimeRegister 加班登记
     * @return R
     */
    @ApiOperation(value = "新增加班登记", notes = "新增加班登记")
    @SysLog("新增加班登记" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atregtimeregister_add')" )
    public R save(@RequestBody AtregtimeRegister atregtimeRegister) {
        return R.ok(atregtimeRegisterService.save(atregtimeRegister));
    }

/*
    */
/**
     * 修改加班登记
     * @param atregtimeRegister 加班登记
     * @return R
     *//*

    @ApiOperation(value = "修改加班登记", notes = "修改加班登记")
    @SysLog("修改加班登记" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atregtimeregister_edit')" )
    public R updateById(@RequestBody AtregtimeRegister atregtimeRegister) {
        return R.ok(atregtimeRegisterService.updateById(atregtimeRegister));
    }

    */
/**
     * 通过id删除加班登记
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除加班登记", notes = "通过id删除加班登记")
    @SysLog("通过id删除加班登记" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atregtimeregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atregtimeRegisterService.removeById(id));
    }
*/

}
