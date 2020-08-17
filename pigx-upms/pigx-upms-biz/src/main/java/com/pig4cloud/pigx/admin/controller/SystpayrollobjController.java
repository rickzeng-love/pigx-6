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
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.api.vo.TreeUtil;
import com.pig4cloud.pigx.admin.entity.CtcdConst;
import com.pig4cloud.pigx.admin.mapper.SystpayrollitemMapper;
import com.pig4cloud.pigx.admin.mapper.SystpayrollobjMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systpayrollobj;
import com.pig4cloud.pigx.admin.service.SystpayrollobjService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 薪资高级数据源
 *
 * @author gaoxiao
 * @date 2020-06-26 14:45:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollobj" )
@Api(value = "systpayrollobj", tags = "薪资高级数据源管理")
public class SystpayrollobjController {

    private final  SystpayrollobjService systpayrollobjService;
    private final SystpayrollobjMapper systpayrollobjMapperj;
    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollobj 薪资高级数据源
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollobjPage(Page page, Systpayrollobj systpayrollobj) {
        return R.ok(systpayrollobjService.page(page, Wrappers.query(systpayrollobj)));
    }


    /**
     * 通过id查询薪资高级数据源
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systpayrollobjService.getById(id));
    }

    /**
     * 新增薪资高级数据源
     * @param systpayrollobj 薪资高级数据源
     * @return R
     */
    @ApiOperation(value = "新增薪资高级数据源", notes = "新增薪资高级数据源")
    @SysLog("新增薪资高级数据源" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollobj_add')" )
    public R save(@RequestBody Systpayrollobj systpayrollobj) {
        return R.ok(systpayrollobjService.save(systpayrollobj));
    }

    /**
     * 修改薪资高级数据源
     * @param systpayrollobj 薪资高级数据源
     * @return R
     */
    @ApiOperation(value = "修改薪资高级数据源", notes = "修改薪资高级数据源")
    @SysLog("修改薪资高级数据源" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollobj_edit')" )
    public R updateById(@RequestBody Systpayrollobj systpayrollobj) {
        return R.ok(systpayrollobjService.updateById(systpayrollobj));
    }

 /*   *//**
     * 通过id删除薪资高级数据源
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除薪资高级数据源", notes = "通过id删除薪资高级数据源")
    @SysLog("通过id删除薪资高级数据源" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollobj_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systpayrollobjService.removeById(id));
    }
*/

	/**
	 * 获取公式中薪资高级数据源
	 * @return
	 */
	@ApiOperation(value = "获取公式中薪资高级数据源", notes = "获取公式中薪资高级数据源")
	@PostMapping("/getSystpayrollobjForFormulaTree" )
	public R getSystpayrollobjForFormulaTree() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Systpayrollobj systpayrollobj = new Systpayrollobj();
		List formulaList = systpayrollobjMapperj.listSystpayrollobjForFormula(systpayrollobj);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("系统高级数据源");
		return R.ok(TreeUtil.findChildren2(treeOrg,formulaList));

	}
}
