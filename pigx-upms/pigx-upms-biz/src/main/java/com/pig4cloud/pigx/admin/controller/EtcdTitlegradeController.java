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
import com.pig4cloud.pigx.admin.entity.EtcdEmptype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdTitlegrade;
import com.pig4cloud.pigx.admin.service.EtcdTitlegradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 职称级别
 *
 * @author gaoxiao
 * @date 2020-04-15 19:06:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdtitlegrade" )
@Api(value = "etcdtitlegrade", tags = "职称级别管理")
public class EtcdTitlegradeController {

    private final  EtcdTitlegradeService etcdTitlegradeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdTitlegrade 职称级别
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdTitlegradePage(Page page, EtcdTitlegrade etcdTitlegrade) {
        return R.ok(etcdTitlegradeService.page(page, Wrappers.query(etcdTitlegrade)));
    }

	/**
	 * 查询所有
	 * @param etcdTitlegrade
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtcdTitlegrade" )
	public R getAllEtcdTitlegrade(EtcdTitlegrade etcdTitlegrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etemployee.setCorpcode(corpcode);
		return R.ok(etcdTitlegradeService.list(Wrappers.query(etcdTitlegrade)));
	}
    /**
     * 通过id查询职称级别
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdTitlegradeService.getById(id));
    }

    /**
     * 新增职称级别      @PreAuthorize("@pms.hasPermission('admin_etcdtitlegrade_add')" )
     * @param etcdTitlegrade 职称级别
     * @return R
     */
    @ApiOperation(value = "新增职称级别", notes = "新增职称级别")
    @SysLog("新增职称级别" )
    @PostMapping
    public R save(@RequestBody EtcdTitlegrade etcdTitlegrade) {
        return R.ok(etcdTitlegradeService.save(etcdTitlegrade));
    }
/*

    */
/**
     * 修改职称级别      @PreAuthorize("@pms.hasPermission('admin_etcdtitlegrade_edit')" )
     * @param etcdTitlegrade 职称级别
     * @return R
     *//*

    @ApiOperation(value = "修改职称级别", notes = "修改职称级别")
    @SysLog("修改职称级别" )
    @PutMapping
    public R updateById(@RequestBody EtcdTitlegrade etcdTitlegrade) {
        return R.ok(etcdTitlegradeService.updateById(etcdTitlegrade));
    }

    */
/**
     * 通过id删除职称级别
     * @param id id      @PreAuthorize("@pms.hasPermission('admin_etcdtitlegrade_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除职称级别", notes = "通过id删除职称级别")
    @SysLog("通过id删除职称级别" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdTitlegradeService.removeById(id));
    }
*/

}
