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
import com.pig4cloud.pigx.admin.entity.EtbgTraining;
import com.pig4cloud.pigx.admin.service.EtbgTrainingService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 培训经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:41:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgtraining" )
@Api(value = "etbgtraining", tags = "培训经历管理")
public class EtbgTrainingController {

    private final  EtbgTrainingService etbgTrainingService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgTraining 培训经历
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgTrainingPage(Page page, EtbgTraining etbgTraining) {
        return R.ok(etbgTrainingService.page(page, Wrappers.query(etbgTraining)));
    }

	/**
	 * 查询所有
	 * @param etbgTraining
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgTraining" )
	public R getAllEtbgTraining(EtbgTraining etbgTraining) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgTraining.setCorpcode(corpcode);
		etbgTraining.setUserid(userid);
		return R.ok(etbgTrainingService.list(Wrappers.query(etbgTraining)));
	}
    /**
     * 通过id查询培训经历
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgTrainingService.getById(id));
    }

    /**
     * 新增培训经历     @PreAuthorize("@pms.hasPermission('admin_etbgtraining_add')" )
     * @param etbgTraining 培训经历
     * @return R
     */
    @ApiOperation(value = "新增培训经历", notes = "新增培训经历")
    @SysLog("新增培训经历" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgTraining etbgTraining) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgTraining.setCorpcode(corpcode);
		etbgTraining.setUserid(userid);
        return R.ok(etbgTrainingService.save(etbgTraining));
    }

 /*   *//**
     * 修改培训经历
     * @param etbgTraining 培训经历     @PreAuthorize("@pms.hasPermission('admin_etbgtraining_edit')" )
     * @return R
     *//*
    @ApiOperation(value = "修改培训经历", notes = "修改培训经历")
    @SysLog("修改培训经历" )
    @PutMapping
    public R updateById(@RequestBody EtbgTraining etbgTraining) {
        return R.ok(etbgTrainingService.updateById(etbgTraining));
    }*/

/*
    */
/**
     * 通过id删除培训经历
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_etbgtraining_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除培训经历", notes = "通过id删除培训经历")
    @SysLog("通过id删除培训经历" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgTrainingService.removeById(id));
    }
*/

}
