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
import com.pig4cloud.pigx.admin.entity.AtcardlostRegister;
import com.pig4cloud.pigx.admin.service.AtcardlostRegisterService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 补卡登记
 *
 * @author gaoxiao
 * @date 2020-07-07 17:38:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcardlostregister" )
@Api(value = "atcardlostregister", tags = "补卡登记管理")
public class AtcardlostRegisterController {

    private final  AtcardlostRegisterService atcardlostRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcardlostRegister 补卡登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcardlostRegisterPage(Page page, AtcardlostRegister atcardlostRegister) {
        return R.ok(atcardlostRegisterService.page(page, Wrappers.query(atcardlostRegister)));
    }


    /**
     * 通过id查询补卡登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcardlostRegisterService.getById(id));
    }

    /**
     * 新增补卡登记
     * @param atcardlostRegister 补卡登记
     * @return R
     */
    @ApiOperation(value = "新增补卡登记", notes = "新增补卡登记")
    @SysLog("新增补卡登记" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atcardlostregister_add')" )
    public R save(@RequestBody AtcardlostRegister atcardlostRegister) {
        return R.ok(atcardlostRegisterService.save(atcardlostRegister));
    }

    /**
     * 修改补卡登记
     * @param atcardlostRegister 补卡登记
     * @return R
     */
    @ApiOperation(value = "修改补卡登记", notes = "修改补卡登记")
    @SysLog("修改补卡登记" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atcardlostregister_edit')" )
    public R updateById(@RequestBody AtcardlostRegister atcardlostRegister) {
        return R.ok(atcardlostRegisterService.updateById(atcardlostRegister));
    }

    /**
     * 通过id删除补卡登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除补卡登记", notes = "通过id删除补卡登记")
    @SysLog("通过id删除补卡登记" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atcardlostregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcardlostRegisterService.removeById(id));
    }

}
