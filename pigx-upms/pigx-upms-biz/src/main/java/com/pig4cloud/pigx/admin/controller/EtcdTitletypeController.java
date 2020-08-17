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
import com.pig4cloud.pigx.admin.entity.EtcdTitlegrade;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcdTitletype;
import com.pig4cloud.pigx.admin.service.EtcdTitletypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 职称类型
 *
 * @author gaoxiao
 * @date 2020-04-15 19:07:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdtitletype" )
@Api(value = "etcdtitletype", tags = "职称类型管理")
public class EtcdTitletypeController {

    private final  EtcdTitletypeService etcdTitletypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcdTitletype 职称类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcdTitletypePage(Page page, EtcdTitletype etcdTitletype) {
        return R.ok(etcdTitletypeService.page(page, Wrappers.query(etcdTitletype)));
    }

	/**
	 * 查询所有
	 * @param etcdTitletype
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtcdTitletype" )
	public R getAllEtcdTitletype(EtcdTitletype etcdTitletype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etemployee.setCorpcode(corpcode);
		return R.ok(etcdTitletypeService.list(Wrappers.query(etcdTitletype)));
	}
    /**
     * 通过id查询职称类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcdTitletypeService.getById(id));
    }

    /**
     * 新增职称类型
     * @param etcdTitletype 职称类型      @PreAuthorize("@pms.hasPermission('admin_etcdtitletype_add')" )
     * @return R
     */
    @ApiOperation(value = "新增职称类型", notes = "新增职称类型")
    @SysLog("新增职称类型" )
    @PostMapping
    public R save(@RequestBody EtcdTitletype etcdTitletype) {
        return R.ok(etcdTitletypeService.save(etcdTitletype));
    }

/*
    */
/**
     * 修改职称类型
     * @param etcdTitletype 职称类型      @PreAuthorize("@pms.hasPermission('admin_etcdtitletype_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改职称类型", notes = "修改职称类型")
    @SysLog("修改职称类型" )
    @PutMapping
    public R updateById(@RequestBody EtcdTitletype etcdTitletype) {
        return R.ok(etcdTitletypeService.updateById(etcdTitletype));
    }

    */
/**
     * 通过id删除职称类型
     * @param id id      @PreAuthorize("@pms.hasPermission('admin_etcdtitletype_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除职称类型", notes = "通过id删除职称类型")
    @SysLog("通过id删除职称类型" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcdTitletypeService.removeById(id));
    }
*/

}
