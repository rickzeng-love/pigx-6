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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtbgFamily;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtcdCardlostreason;
import com.pig4cloud.pigx.admin.service.AtcdCardlostreasonService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 补卡原因
 *
 * @author GXS
 * @date 2020-05-22 14:31:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdcardlostreason" )
@Api(value = "atcdcardlostreason", tags = "补卡原因管理")
public class AtcdCardlostreasonController {

    private final  AtcdCardlostreasonService atcdCardlostreasonService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdCardlostreason 补卡原因
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdCardlostreasonPage(Page page, AtcdCardlostreason atcdCardlostreason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdCardlostreason.setCorpcode(corpCode);
        return R.ok(atcdCardlostreasonService.page(page, Wrappers.query(atcdCardlostreason)));
    }

	/**
	 * 查询所有
	 * @param atcdCardlostreason
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllAtcdCardlostreason" )
	public R getAllAtcdCardlostreason(AtcdCardlostreason atcdCardlostreason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
//		Integer userid = pigxUser.getId();
		atcdCardlostreason.setCorpcode(corpCode);
		return R.ok(atcdCardlostreasonService.list(Wrappers.query(atcdCardlostreason)));
	}


	/**
     * 通过id查询补卡原因
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdCardlostreasonService.getById(id));
    }

    /**
     * 新增补卡原因
     * @param atcdCardlostreason 补卡原因 @PreAuthorize("@pms.hasPermission('admin_atcdcardlostreason_add')" )
     * @return R
     */
    @ApiOperation(value = "新增补卡原因", notes = "新增补卡原因")
    @SysLog("新增补卡原因" )
    @PostMapping
    public R save(@RequestBody AtcdCardlostreason atcdCardlostreason) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		//Integer userid = pigxUser.getId();
		atcdCardlostreason.setCorpid(corpID);
		atcdCardlostreason.setCorpcode(corpCode);
        return R.ok(atcdCardlostreasonService.save(atcdCardlostreason));
    }
/*

    */
/**
     * 修改补卡原因
     * @param atcdCardlostreason 补卡原因 @PreAuthorize("@pms.hasPermission('admin_atcdcardlostreason_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改补卡原因", notes = "修改补卡原因")
    @SysLog("修改补卡原因" )
    @PutMapping
    public R updateById(@RequestBody AtcdCardlostreason atcdCardlostreason) {
        return R.ok(atcdCardlostreasonService.updateById(atcdCardlostreason));
    }

    */
/**
     * 通过id删除补卡原因
     * @param id id @PreAuthorize("@pms.hasPermission('admin_atcdcardlostreason_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除补卡原因", notes = "通过id删除补卡原因")
    @SysLog("通过id删除补卡原因" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcdCardlostreasonService.removeById(id));
    }
*/

}
