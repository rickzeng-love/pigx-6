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
import com.pig4cloud.pigx.admin.entity.Systpayrollobj;
import com.pig4cloud.pigx.admin.mapper.SystdataobjsMapper;
import com.pig4cloud.pigx.admin.mapper.SystpayrollobjMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systdataobjs;
import com.pig4cloud.pigx.admin.service.SystdataobjsService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 对象
 *
 * @author gaoxiao
 * @date 2020-06-26 13:38:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systdataobjs" )
@Api(value = "systdataobjs", tags = "对象管理")
public class SystdataobjsController {

    private final  SystdataobjsService systdataobjsService;
    private final SystdataobjsMapper systdataobjsMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systdataobjs 对象
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystdataobjsPage(Page page, Systdataobjs systdataobjs) {
        return R.ok(systdataobjsService.page(page, Wrappers.query(systdataobjs)));
    }


    /**
     * 通过id查询对象
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systdataobjsService.getById(id));
    }

    /**
     * 新增对象
     * @param systdataobjs 对象
     * @return R
     */
    @ApiOperation(value = "新增对象", notes = "新增对象")
    @SysLog("新增对象" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systdataobjs_add')" )
    public R save(@RequestBody Systdataobjs systdataobjs) {
        return R.ok(systdataobjsService.save(systdataobjs));
    }


	/**
	 * 获取公式中薪资条件
	 * @return
	 */
	@ApiOperation(value = "获取公式中薪资条件", notes = "获取公式中薪资条件")
	@PostMapping("/getSystdataobjsForFormulaTree" )
	public R getSystdataobjsForFormulaTree() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Systdataobjs systdataobjs = new Systdataobjs();
		List formulaList = systdataobjsMapper.listSystdataobjsForFormula(systdataobjs);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("请选择条件");
		return R.ok(TreeUtil.findChildren2(treeOrg,formulaList));

	}
/*
    */
/**
     * 修改对象
     * @param systdataobjs 对象
     * @return R
     *//*

    @ApiOperation(value = "修改对象", notes = "修改对象")
    @SysLog("修改对象" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systdataobjs_edit')" )
    public R updateById(@RequestBody Systdataobjs systdataobjs) {
        return R.ok(systdataobjsService.updateById(systdataobjs));
    }

    */
/**
     * 通过id删除对象
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除对象", notes = "通过id删除对象")
    @SysLog("通过id删除对象" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systdataobjs_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systdataobjsService.removeById(id));
    }
*/

}
