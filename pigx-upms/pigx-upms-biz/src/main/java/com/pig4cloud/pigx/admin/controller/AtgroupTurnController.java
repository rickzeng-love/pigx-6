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
import com.pig4cloud.pigx.admin.entity.CtcdSalarykind;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtgroupTurn;
import com.pig4cloud.pigx.admin.service.AtgroupTurnService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 轮班组-轮班定义
 *
 * @author gaoxiao
 * @date 2020-07-06 16:27:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atgroupturn" )
@Api(value = "atgroupturn", tags = "轮班组-轮班定义管理")
public class AtgroupTurnController {

    private final  AtgroupTurnService atgroupTurnService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atgroupTurn 轮班组-轮班定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtgroupTurnPage(Page page, AtgroupTurn atgroupTurn) {
        return R.ok(atgroupTurnService.page(page, Wrappers.query(atgroupTurn)));
    }


	/**
	 * 轮班组-轮班定义
	 * @param atgroupTurn
	 * @ctcdSalarykind
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtgroupTurn" )
	public R getAllAtgroupTurn(@RequestBody  AtgroupTurn atgroupTurn) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atgroupTurn.setCorpcode(corpcode);
		return R.ok(atgroupTurnService.list(Wrappers.query(atgroupTurn)));
	}


    /**
     * 通过id查询轮班组-轮班定义
     * @param groupid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{groupid}" )
    public R getById(@PathVariable("groupid" ) Integer groupid) {
        return R.ok(atgroupTurnService.getById(groupid));
    }

    /**
     * 新增轮班组-轮班定义     @PreAuthorize("@pms.hasPermission('admin_atgroupturn_add')" )
     * @param atgroupTurn 轮班组-轮班定义
     * @return R
     */
    @ApiOperation(value = "新增轮班组-轮班定义", notes = "新增轮班组-轮班定义")
    @SysLog("新增轮班组-轮班定义" )
    @PostMapping("/save")
    public R save(@RequestBody AtgroupTurn atgroupTurn) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atgroupTurn.setCorpcode(corpcode);
		atgroupTurn.setCorpid(pigxUser.getCorpid());
		atgroupTurn.setInitialized(1);
		atgroupTurn.setIscheck(1);
        return R.ok(atgroupTurnService.save(atgroupTurn));
    }

    /**
     * 修改轮班组-轮班定义
     * @param atgroupTurn 轮班组-轮班定义
     * @return R
     */
    @ApiOperation(value = "修改轮班组-轮班定义", notes = "修改轮班组-轮班定义")
    @SysLog("修改轮班组-轮班定义" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atgroupturn_edit')" )
    public R updateById(@RequestBody AtgroupTurn atgroupTurn) {
        return R.ok(atgroupTurnService.updateById(atgroupTurn));
    }

    /**
     * 通过id删除轮班组-轮班定义
     * @param groupid id
     * @return R
     */
    @ApiOperation(value = "通过id删除轮班组-轮班定义", notes = "通过id删除轮班组-轮班定义")
    @SysLog("通过id删除轮班组-轮班定义" )
    @DeleteMapping("/{groupid}" )
    @PreAuthorize("@pms.hasPermission('admin_atgroupturn_del')" )
    public R removeById(@PathVariable Integer groupid) {
        return R.ok(atgroupTurnService.removeById(groupid));
    }

}
