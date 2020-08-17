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
import com.pig4cloud.pigx.admin.entity.Ctattend;
import com.pig4cloud.pigx.admin.service.CtattendService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 考勤接口
 *
 * @author gaoxiao
 * @date 2020-06-13 11:19:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctattend" )
@Api(value = "ctattend", tags = "考勤接口管理")
public class CtattendController {

    private final  CtattendService ctattendService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctattend 考勤接口
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getCtattendPage" )
    public R getCtattendPage(Page page, @RequestBody(required = false) Ctattend ctattend) {
		PigxUser pigxUser = SecurityUtils.getUser();
    	if(StringUtils.isEmpty(ctattend)){
    		ctattend  = new Ctattend();
		}
		ctattend.setCorpid(pigxUser.getCorpid());
        return R.ok(ctattendService.page(page, Wrappers.query(ctattend)));
    }


    /**
     * 通过id查询考勤接口
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctattendService.getById(id));
    }

    /**
     * 新增考勤接口
     * @param ctattend 考勤接口
     * @return R
     */
    @ApiOperation(value = "新增考勤接口", notes = "新增考勤接口")
    @SysLog("新增考勤接口" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctattend_add')" )
    public R save(@RequestBody Ctattend ctattend) {
        return R.ok(ctattendService.save(ctattend));
    }

/*
    */
/**
     * 修改考勤接口
     * @param ctattend 考勤接口
     * @return R
     *//*

    @ApiOperation(value = "修改考勤接口", notes = "修改考勤接口")
    @SysLog("修改考勤接口" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctattend_edit')" )
    public R updateById(@RequestBody Ctattend ctattend) {
        return R.ok(ctattendService.updateById(ctattend));
    }

    */
/**
     * 通过id删除考勤接口
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤接口", notes = "通过id删除考勤接口")
    @SysLog("通过id删除考勤接口" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctattend_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctattendService.removeById(id));
    }
*/

}
