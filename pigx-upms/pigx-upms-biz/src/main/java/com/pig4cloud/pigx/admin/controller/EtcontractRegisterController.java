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
import com.pig4cloud.pigx.admin.entity.EtcontractRegister;
import com.pig4cloud.pigx.admin.service.EtcontractRegisterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 合同变动
 *
 * @author gaoxiao
 * @date 2020-04-17 10:29:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcontractregister" )
@Api(value = "etcontractregister", tags = "合同变动管理")
public class EtcontractRegisterController {

    private final  EtcontractRegisterService etcontractRegisterService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcontractRegister 合同变动
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcontractRegisterPage(Page page, EtcontractRegister etcontractRegister) {
        return R.ok(etcontractRegisterService.page(page, Wrappers.query(etcontractRegister)));
    }


    /**
     * 通过id查询合同变动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcontractRegisterService.getById(id));
    }

    /**
     * 新增合同变动
     * @param etcontractRegister 合同变动     @PreAuthorize("@pms.hasPermission('admin_etcontractregister_add')" )
     * @return R
     */
    @ApiOperation(value = "新增合同变动", notes = "新增合同变动")
    @SysLog("新增合同变动" )
    @PostMapping
    public R save(@RequestBody EtcontractRegister etcontractRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etcontractRegister.setCorpcode(corpcode);
		etcontractRegister.setUserid(userid);
        return R.ok(etcontractRegisterService.save(etcontractRegister));
    }

/*
    */
/**
     * 修改合同变动
     * @param etcontractRegister 合同变动
     * @return R
     *//*

    @ApiOperation(value = "修改合同变动", notes = "修改合同变动")
    @SysLog("修改合同变动" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etcontractregister_edit')" )
    public R updateById(@RequestBody EtcontractRegister etcontractRegister) {
        return R.ok(etcontractRegisterService.updateById(etcontractRegister));
    }

    */
/**
     * 通过id删除合同变动
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除合同变动", notes = "通过id删除合同变动")
    @SysLog("通过id删除合同变动" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcontractregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcontractRegisterService.removeById(id));
    }
*/

}
