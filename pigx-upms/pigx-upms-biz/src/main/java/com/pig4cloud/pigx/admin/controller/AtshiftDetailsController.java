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
import com.pig4cloud.pigx.admin.mapper.AtshiftDetailsMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftDetails;
import com.pig4cloud.pigx.admin.service.AtshiftDetailsService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 日明细结果
 *
 * @author gaoxiao
 * @date 2020-06-22 09:59:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftdetails" )
@Api(value = "atshiftdetails", tags = "日明细结果管理")
public class AtshiftDetailsController {

    private final  AtshiftDetailsService atshiftDetailsService;
    private final AtshiftDetailsMapper atshiftDetailsMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftDetails 日明细结果
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftDetailsPage(Page page, AtshiftDetails atshiftDetails) {
        return R.ok(atshiftDetailsService.page(page, Wrappers.query(atshiftDetails)));
    }


    /**
     * 通过id查询日明细结果
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atshiftDetailsService.getById(id));
    }

    /**
     * 新增日明细结果
     * @param atshiftDetails 日明细结果
     * @return R
     */
    @ApiOperation(value = "新增日明细结果", notes = "新增日明细结果")
    @SysLog("新增日明细结果" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftdetails_add')" )
    public R save(@RequestBody AtshiftDetails atshiftDetails) {
        return R.ok(atshiftDetailsService.save(atshiftDetails));
    }
/*
    *//**
     * 修改日明细结果
     * @param atshiftDetails 日明细结果
     * @return R
     *//*
    @ApiOperation(value = "修改日明细结果", notes = "修改日明细结果")
    @SysLog("修改日明细结果" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshiftdetails_edit')" )
    public R updateById(@RequestBody AtshiftDetails atshiftDetails) {
        return R.ok(atshiftDetailsService.updateById(atshiftDetails));
    }

    *//**
     * 通过id删除日明细结果
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除日明细结果", notes = "通过id删除日明细结果")
    @SysLog("通过id删除日明细结果" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atshiftdetails_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atshiftDetailsService.removeById(id));
    }
	*/
    /**
	 * 查詢日明細
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "查詢日明細", notes = "查詢日明細")
	@PostMapping("/getAtshiftDetailsPageByEid" )
	public R getAtshiftDetailsPageByEid(Page page) {
		AtshiftDetails atshiftDetails = new AtshiftDetails();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		String corpcode = pigxUser.getCorpcode();
		atshiftDetails.setCorpcode(corpcode);
		atshiftDetails.setEid(eid);

		return R.ok(atshiftDetailsService.page(atshiftDetailsMapper.listAtshiftDetailsPageByEid(page,atshiftDetails)));
	}

}
