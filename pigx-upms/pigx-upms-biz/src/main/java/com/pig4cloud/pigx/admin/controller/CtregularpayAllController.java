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
import com.pig4cloud.pigx.admin.entity.CtregularpayAll;
import com.pig4cloud.pigx.admin.service.CtregularpayAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 登记支付扣除历史
 *
 * @author shishengjie
 * @date 2020-07-21 15:45:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctregularpayall" )
@Api(value = "ctregularpayall", tags = "登记支付扣除历史管理")
public class CtregularpayAllController {

    private final  CtregularpayAllService ctregularpayAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctregularpayAll 登记支付扣除历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtregularpayAllPage(Page page, CtregularpayAll ctregularpayAll) {
        return R.ok(ctregularpayAllService.page(page, Wrappers.query(ctregularpayAll)));
    }


    /**
     * 通过id查询登记支付扣除历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctregularpayAllService.getById(id));
    }

    /**
     * 新增登记支付扣除历史
     * @param ctregularpayAll 登记支付扣除历史
     * @return R
     */
    @ApiOperation(value = "新增登记支付扣除历史", notes = "新增登记支付扣除历史")
    @SysLog("新增登记支付扣除历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctregularpayall_add')" )
    public R save(@RequestBody CtregularpayAll ctregularpayAll) {
        return R.ok(ctregularpayAllService.save(ctregularpayAll));
    }

    /**
     * 修改登记支付扣除历史
     * @param ctregularpayAll 登记支付扣除历史
     * @return R
     */
    @ApiOperation(value = "修改登记支付扣除历史", notes = "修改登记支付扣除历史")
    @SysLog("修改登记支付扣除历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctregularpayall_edit')" )
    public R updateById(@RequestBody CtregularpayAll ctregularpayAll) {
        return R.ok(ctregularpayAllService.updateById(ctregularpayAll));
    }

    /**
     * 通过id删除登记支付扣除历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除登记支付扣除历史", notes = "通过id删除登记支付扣除历史")
    @SysLog("通过id删除登记支付扣除历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctregularpayall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctregularpayAllService.removeById(id));
    }

}
