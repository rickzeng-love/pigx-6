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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.SystpayrollformulaAll;
import com.pig4cloud.pigx.admin.entity.Systpayrollgroup;
import com.pig4cloud.pigx.admin.service.SystpayrollformulaAllService;
import com.pig4cloud.pigx.admin.service.SystpayrollgroupService;
import com.pig4cloud.pigx.admin.service.SystpaystditemAllService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systpayrollformula;
import com.pig4cloud.pigx.admin.service.SystpayrollformulaService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 薪资公式
 *
 * @author gaoxiao
 * @date 2020-06-26 17:25:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollformula" )
@Api(value = "systpayrollformula", tags = "薪资公式管理")
public class SystpayrollformulaController {

    private final  SystpayrollformulaService systpayrollformulaService;
	private final SystpayrollgroupService systpayrollgroupService;
	private final SystpayrollformulaAllService systpayrollformulaAllService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollformula 薪资公式
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollformulaPage(Page page, Systpayrollformula systpayrollformula) {
        return R.ok(systpayrollformulaService.page(page, Wrappers.query(systpayrollformula)));
    }

	/**
	 * 分页查询
	 * @param systpayrollformula 薪资公式
	 * @return
	 */
	@ApiOperation(value = "查询公式", notes = "查询公式")
	@PostMapping("/getSystpayrollformulaList" )
	public R getSystpayrollformulaList(@RequestBody Systpayrollformula systpayrollformula) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		Integer gid = null;
		if(!StringUtils.isEmpty(systpayrollgroup)){
			gid = systpayrollgroup.getId();
		}
		QueryWrapper<Systpayrollformula> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("gid",gid);
		queryWrapper2.eq("colname",systpayrollformula.getColname());

		return R.ok(systpayrollformulaService.list(queryWrapper2));
	}



	/**
     * 通过id查询薪资公式
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systpayrollformulaService.getById(id));
    }
/*

    */
/**
     * 新增薪资公式
     * @param systpayrollformula 薪资公式     @PreAuthorize("@pms.hasPermission('admin_systpayrollformula_add')" )
     * @return R
     *//*

    @ApiOperation(value = "新增薪资公式", notes = "新增薪资公式")
    @SysLog("新增薪资公式" )
    @PostMapping("/save")
    public R save(@RequestBody Systpayrollformula systpayrollformula) {
    	Integer gid = null;
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if(!StringUtils.isEmpty(systpayrollgroup)){
			gid = systpayrollgroup.getId();
		}
		if(StringUtils.isEmpty(gid)){
			R.failed("请先维护薪资账套！");
		}
		systpayrollformula.setGid(gid);
		systpayrollformula.setCorpcode(corpcode);
		systpayrollformula.setCorpid(pigxUser.getCorpid());
        return R.ok(systpayrollformulaService.save(systpayrollformula));
    }
*/



	/**
	 * 新增薪资公式
	 * @param systpayrollformula 薪资公式     @PreAuthorize("@pms.hasPermission('admin_systpayrollformula_add')" )
	 * @return R
	 */
	@ApiOperation(value = "新增薪资公式", notes = "新增薪资公式")
	@SysLog("新增薪资公式" )
	@PostMapping("/save")
	public R save(@RequestBody Systpayrollformula systpayrollformula) {
		Integer gid = null;
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if(!StringUtils.isEmpty(systpayrollgroup)){
			gid = systpayrollgroup.getId();
		}
		if(StringUtils.isEmpty(gid)){
			return R.failed("请先维护薪资账套！");
		}

		QueryWrapper<Systpayrollformula> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("colname",systpayrollformula.getColname());
		queryWrapper2.eq("gid",gid);
		List<Systpayrollformula> oldlist = systpayrollformulaService.list(queryWrapper2);
		List<SystpayrollformulaAll> newlist = new ArrayList(oldlist.size());
		Systpayrollformula oldsystpayrollformula = null;
		SystpayrollformulaAll newsystpayrollformula = null;
		for(int i=0;i<oldlist.size();i++){
			newsystpayrollformula = new SystpayrollformulaAll();
			oldsystpayrollformula = oldlist.get(i);
			BeanUtils.copyProperties(oldsystpayrollformula,newsystpayrollformula);
			newlist.add(newsystpayrollformula);
		}
		systpayrollformulaAllService.saveBatch(newlist);
		systpayrollformulaService.remove(queryWrapper2);
		List<Systpayrollformula> newformulaList = systpayrollformula.getFormulaList();
		List<Systpayrollformula> resultformulaList = new ArrayList<>(newformulaList.size());
		Systpayrollformula resultsystpayrollformula = null;
		for(int i=0;i<newformulaList.size();i++){
			resultsystpayrollformula = newformulaList.get(i);
			resultsystpayrollformula.setCorpcode(corpcode);
			resultsystpayrollformula.setCorpid(pigxUser.getId());
			resultsystpayrollformula.setGid(gid);
			resultsystpayrollformula.setColname(systpayrollformula.getColname());
			resultformulaList.add(resultsystpayrollformula);

		}
		systpayrollformulaService.saveBatch(resultformulaList);

		return R.ok("保存成功！");
	}

/*
    */
/**
     * 修改薪资公式      @PreAuthorize("@pms.hasPermission('admin_systpayrollformula_edit')" )
     * @param systpayrollformula 薪资公式
     * @return R
     */

    @ApiOperation(value = "修改薪资公式", notes = "修改薪资公式")
    @SysLog("修改薪资公式" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody Systpayrollformula systpayrollformula) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<Systpayrollformula> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("colname",systpayrollformula.getColname());
		updateWrapper.eq("gid",systpayrollformula.getGid());
		updateWrapper.eq("id",systpayrollformula.getId());
        return R.ok(systpayrollformulaService.update(systpayrollformula,updateWrapper));
    }


/**
     * 通过id删除薪资公式     @PreAuthorize("@pms.hasPermission('admin_systpayrollformula_del')" )
     * @param systpayrollformula
     * @return R
     */

    @ApiOperation(value = "通过id删除薪资公式", notes = "通过id删除薪资公式")
    @SysLog("通过id删除薪资公式" )
    @PostMapping("removeById" )
    public R removeById(@RequestBody Systpayrollformula systpayrollformula) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systpayrollformula> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("colname",systpayrollformula.getColname());
		queryWrapper.eq("gid",systpayrollformula.getGid());
		queryWrapper.eq("id",systpayrollformula.getId());
		Systpayrollformula systpayrollformula1 = systpayrollformulaService.getOne(queryWrapper);
		SystpayrollformulaAll systpayrollformulaAll = new SystpayrollformulaAll();
		if(!StringUtils.isEmpty(systpayrollformula1)){
			BeanUtils.copyProperties(systpayrollformula1,systpayrollformulaAll);
		}
		systpayrollformulaAllService.save(systpayrollformulaAll);
		systpayrollformulaService.remove(queryWrapper);
        return R.ok("删除成功!");
    }


}
