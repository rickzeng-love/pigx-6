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
import com.pig4cloud.pigx.admin.entity.EtcdTitletype;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtchangeorgAll;
import com.pig4cloud.pigx.admin.service.EtchangeorgAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 调动登记历史
 *
 * @author gaoxiao
 * @date 2020-04-14 18:57:38
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etchangeorgall" )
@Api(value = "etchangeorgall", tags = "调动登记历史管理")
public class EtchangeorgAllController {

    private final  EtchangeorgAllService etchangeorgAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etchangeorgAll 调动登记历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtchangeorgAllPage(Page page, EtchangeorgAll etchangeorgAll) {
        return R.ok(etchangeorgAllService.page(page, Wrappers.query(etchangeorgAll)));
    }
	/**
	 * 查询所有
	 * @param etchangeorgAll
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtchangeorgAll" )
	public R getAllEtchangeorgAll(EtchangeorgAll etchangeorgAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//etemployee.setCorpcode(corpcode);
		return R.ok(etchangeorgAllService.list(Wrappers.query(etchangeorgAll)));
	}

    /**
     * 通过id查询调动登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etchangeorgAllService.getById(id));
    }

    /**
     * 新增调动登记历史      @PreAuthorize("@pms.hasPermission('admin_etchangeorgall_add')" )
     * @param etchangeorgAll 调动登记历史
     * @return R
     */
    @ApiOperation(value = "新增调动登记历史", notes = "新增调动登记历史")
    @SysLog("新增调动登记历史" )
    @PostMapping
    public R save(@RequestBody EtchangeorgAll etchangeorgAll) {
        return R.ok(etchangeorgAllService.save(etchangeorgAll));
    }

/*
    */
/**
     * 修改调动登记历史      @PreAuthorize("@pms.hasPermission('admin_etchangeorgall_edit')" )
     * @param etchangeorgAll 调动登记历史
     * @return R
     *//*

    @ApiOperation(value = "修改调动登记历史", notes = "修改调动登记历史")
    @SysLog("修改调动登记历史" )
    @PutMapping
    public R updateById(@RequestBody EtchangeorgAll etchangeorgAll) {
        return R.ok(etchangeorgAllService.updateById(etchangeorgAll));
    }

    */
/**
     * 通过id删除调动登记历史      @PreAuthorize("@pms.hasPermission('admin_etchangeorgall_del')" )
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除调动登记历史", notes = "通过id删除调动登记历史")
    @SysLog("通过id删除调动登记历史" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etchangeorgAllService.removeById(id));
    }
*/

}
