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
import com.pig4cloud.pigx.admin.entity.AtshiftWorkAll;
import com.pig4cloud.pigx.admin.service.AtshiftWorkAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 员工排班历史
 *
 * @author gaoxiao
 * @date 2020-07-08 17:11:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftworkall" )
@Api(value = "atshiftworkall", tags = "员工排班历史管理")
public class AtshiftWorkAllController {

    private final  AtshiftWorkAllService atshiftWorkAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftWorkAll 员工排班历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftWorkAllPage(Page page, AtshiftWorkAll atshiftWorkAll) {
        return R.ok(atshiftWorkAllService.page(page, Wrappers.query(atshiftWorkAll)));
    }


    /**
     * 通过id查询员工排班历史
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(atshiftWorkAllService.getById(term));
    }

    /**
     * 新增员工排班历史
     * @param atshiftWorkAll 员工排班历史
     * @return R
     */
    @ApiOperation(value = "新增员工排班历史", notes = "新增员工排班历史")
    @SysLog("新增员工排班历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftworkall_add')" )
    public R save(@RequestBody AtshiftWorkAll atshiftWorkAll) {
        return R.ok(atshiftWorkAllService.save(atshiftWorkAll));
    }

/*
    */
/**
     * 修改员工排班历史
     * @param atshiftWorkAll 员工排班历史
     * @return R
     *//*

    @ApiOperation(value = "修改员工排班历史", notes = "修改员工排班历史")
    @SysLog("修改员工排班历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftworkall_edit')" )
    public R updateById(@RequestBody AtshiftWorkAll atshiftWorkAll) {
        return R.ok(atshiftWorkAllService.updateById(atshiftWorkAll));
    }

    */
/**
     * 通过id删除员工排班历史
     * @param term id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除员工排班历史", notes = "通过id删除员工排班历史")
    @SysLog("通过id删除员工排班历史" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftworkall_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(atshiftWorkAllService.removeById(term));
    }
*/

}
