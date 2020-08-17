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
import com.pig4cloud.pigx.admin.entity.AtcdAgentmodeUser;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Atstatus;
import com.pig4cloud.pigx.admin.service.AtstatusService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 考勤信息查看

 *
 * @author gaoxiao
 * @date 2020-05-30 11:42:38
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atstatus" )
@Api(value = "atstatus", tags = "考勤信息查看 管理")
public class AtstatusController {

    private final  AtstatusService atstatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atstatus 考勤信息查看

     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtstatusPage(Page page, Atstatus atstatus) {
        return R.ok(atstatusService.page(page, Wrappers.query(atstatus)));
    }


	/**
	 * 查询所有
	 * @param atstatus
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtstatus" )
	public R getAllAtstatus(@RequestBody(required = false) Atstatus atstatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atstatus)){
			atstatus = new Atstatus();
		}
		atstatus.setCorpcode(corpCode);
		return R.ok(atstatusService.list(Wrappers.query(atstatus)));
	}



	/**
     * 通过id查询考勤信息查看

     * @param eid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{eid}" )
    public R getById(@PathVariable("eid" ) Integer eid) {
        return R.ok(atstatusService.getById(eid));
    }

    /**
     * 新增考勤信息查看

     * @param atstatus 考勤信息查看

     * @return R
     */
    @ApiOperation(value = "新增考勤信息查看 ", notes = "新增考勤信息查看 ")
    @SysLog("新增考勤信息查看 " )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atstatus_add')" )
    public R save(@RequestBody Atstatus atstatus) {
        return R.ok(atstatusService.save(atstatus));
    }

/*
    */
/**
     * 修改考勤信息查看

     * @param atstatus 考勤信息查看

     * @return R
     *//*

    @ApiOperation(value = "修改考勤信息查看 ", notes = "修改考勤信息查看 ")
    @SysLog("修改考勤信息查看 " )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atstatus_edit')" )
    public R updateById(@RequestBody Atstatus atstatus) {
        return R.ok(atstatusService.updateById(atstatus));
    }

    */
/**
     * 通过id删除考勤信息查看

     * @param eid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤信息查看 ", notes = "通过id删除考勤信息查看 ")
    @SysLog("通过id删除考勤信息查看 " )
    @DeleteMapping("/{eid}" )
    @PreAuthorize("@pms.hasPermission('admin_atstatus_del')" )
    public R removeById(@PathVariable Integer eid) {
        return R.ok(atstatusService.removeById(eid));
    }
*/

}
