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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.OtcdEmpgradeMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.service.OtcdEmpgradeAllService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.OtcdEmpgradeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 职级
 *
 * @author gaoxiao
 * @date 2020-04-07 13:22:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdempgrade" )
@Api(value = "otcdempgrade", tags = "职级管理")
public class OtcdEmpgradeController {

    private final  OtcdEmpgradeService otcdEmpgradeService;
    private final OtcdEmpgradeMapper otcdEmpgradeMapper;
	private final EtemployeeService etemployeeService;
	private  final OtcdEmpgradeAllService otcdEmpgradeAllService;
	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param otcdEmpgrade 职级
	 * @return
	 */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdEmpgradePage(Page page, OtcdEmpgrade otcdEmpgrade) {
        return R.ok(otcdEmpgradeService.page(page, Wrappers.query(otcdEmpgrade)));
    }
	/**
	 * 分页查询
	 * @param page 分页对象
	 *
	 * @param otcdEmpgrade 职级
	 * @return
	 */
	@ApiOperation(value = "职级分页查询", notes = "职级分页查询")
	@PostMapping("/getOtcdEmpgradePageBySql" )
	public R getOtcdEmpgradePageBySql(Page page, OtcdEmpgrade otcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);
		return R.ok(otcdEmpgradeMapper.listOtcdEmpgrade(page,otcdEmpgrade));
	}

	/**
	 * 职级查询（不分页）
	 *
	 * @param otcdEmpgrade 职级
	 * @return
	 */
	@ApiOperation(value = "职级查询（不分页）", notes = "职级查询（不分页）")
	@PostMapping("/getOtcdEmpgradeAllBySql" )
	public R getOtcdEmpgradeAllBySql(@RequestBody(required = false) OtcdEmpgrade otcdEmpgrade) {
		if(StringUtils.isEmpty(otcdEmpgrade)){
			otcdEmpgrade=new OtcdEmpgrade();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);
		return R.ok(otcdEmpgradeMapper.listOtcdEmpgradeAll(otcdEmpgrade));
	}

	/**
	 * 职级在职员工
	 *
	 * @param otcdEmpgrade 职级
	 * @return
	 */
	@ApiOperation(value = "职级在职员工", notes = "职级在职员工")
	@PostMapping("/getEtempoyeeByEmpGrade" )
	public R getEtempoyeeByEmpGrade(OtcdEmpgrade otcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);
		return R.ok(otcdEmpgradeMapper.listEtempoyeeByEmpGrade(otcdEmpgrade));
	}
	/**
	 * 查询所有
	 * @param otcdEmpgrade 职级
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllOtcdEmpgrade" )
	public R getAllOtcdEmpgrade( OtcdEmpgrade otcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);
		otcdEmpgrade.setIsdisabled(1);
		return R.ok(otcdEmpgradeService.list(Wrappers.query(otcdEmpgrade)));
	}


    /**
     * 通过id查询职级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdEmpgradeService.getById(id));
    }

    /**
     * 新增职级
     * @param otcdEmpgrade 职级
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdempgrade_add')" )
     */
    @ApiOperation(value = "新增职级", notes = "新增职级")
    @SysLog("新增职级" )
    @PostMapping("/save")
    public R save(@RequestBody OtcdEmpgrade otcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);


		QueryWrapper<OtcdEmpgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdEmpgrade.getTitle());
		List jobtypelist = otcdEmpgradeService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("职级名称已存在！");
		}
		QueryWrapper<OtcdEmpgrade> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdEmpgrade.getCode());
		List jobtypelist2 = otcdEmpgradeService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("职级编码已存在！");
		}
		//变动类型 1：新增，2：修改，3：删除
	    OtcdEmpgradeAll otcdEmpgradeAll = new OtcdEmpgradeAll();
		BeanUtils.copyProperties(otcdEmpgrade,otcdEmpgradeAll);
		otcdEmpgradeAll.setType(1);
		otcdEmpgradeAllService.save(otcdEmpgradeAll);
    	return R.ok(otcdEmpgradeService.save(otcdEmpgrade));
    }

    /**
     * 修改职级
     * @param otcdEmpgrade 职级
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdempgrade_edit')" )
     */
    @ApiOperation(value = "修改职级", notes = "修改职级")
    @SysLog("修改职级" )
    @PutMapping("/updateById")
    public R updateById(@RequestBody OtcdEmpgrade otcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);


		QueryWrapper<OtcdEmpgrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdEmpgrade.getTitle());
		queryWrapper.ne("id",otcdEmpgrade.getId());
		List jobtypelist = otcdEmpgradeService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("职级名称已存在！");
		}
		QueryWrapper<OtcdEmpgrade> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdEmpgrade.getCode());
		queryWrapper2.ne("id",otcdEmpgrade.getId());
		List jobtypelist2 = otcdEmpgradeService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("职级编码已存在！");
		}
		OtcdEmpgradeAll otcdEmpgradeAll = new OtcdEmpgradeAll();
		BeanUtils.copyProperties(otcdEmpgrade,otcdEmpgradeAll);
		otcdEmpgradeAll.setType(1);
		//原来的职务信息
		OtcdEmpgrade otcdEmpgrade1 = otcdEmpgradeService.getById(otcdEmpgrade.getId());
		if(otcdEmpgrade1!=null){
			otcdEmpgradeAll.setCompidOld(otcdEmpgrade1.getCompid());
			otcdEmpgradeAll.setCodeOld(otcdEmpgrade1.getCode());
			otcdEmpgradeAll.setDescriptionOld(otcdEmpgrade1.getDescription());
			otcdEmpgradeAll.setPosgradeidOld(otcdEmpgrade1.getPosgradeid());
			otcdEmpgradeAll.setTitleOld(otcdEmpgrade1.getTitle());
		}
		otcdEmpgradeAllService.save(otcdEmpgradeAll);
		UpdateWrapper<OtcdEmpgrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdEmpgrade.getId());
		return R.ok(otcdEmpgradeService.update(otcdEmpgrade,updateWrapper));
    }

/*    *//**
     * 通过id删除职级
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除职级", notes = "通过id删除职级")
    @SysLog("通过id删除职级" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdempgrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdEmpgradeService.removeById(id));
    }
	*/
    /*
	 * PC端职务管理在职、离职统计
	 * @param otcdPosition 职务
	 * @return
	 */
	@ApiOperation(value = "职级管理在职、离职统计", notes = "职级管理在职、离职统计")
	@PostMapping("/getZaiZhiAndLizhiPersonByPosition" )
	public R getZaiZhiAndLizhiPersonByPosition(OtcdEmpgrade otcdEmpgrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdEmpgrade.setCorpcode(corpcode);

		long zaizhicount = 0;//在职员工
		long lizhizhicount = 0;//离职
		Map empZaiZhi = otcdEmpgradeMapper.listZaiZhiPersonByEmpGrade(otcdEmpgrade);
		if(empZaiZhi!=null){
			zaizhicount = (Long) empZaiZhi.get("count");
		}
		Map empLiZhi = otcdEmpgradeMapper.listLizhiPersonByEmpGrade(otcdEmpgrade);
		if(empLiZhi!=null){
			lizhizhicount = (Long) empLiZhi.get("count");
		}

		Map resultMap1 = new HashMap();
		Map resultMap2 = new HashMap();
		resultMap1.put("title","在职员工");
		resultMap1.put("people",zaizhicount);
		resultMap2.put("title","离职员工");
		resultMap2.put("people",lizhizhicount);
		List resultList = new ArrayList(2);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		return R.ok(resultList);
	}

	/**
	 * 失效职级
	 * @return R
	 */
	@ApiOperation(value = "失效职级", notes = "失效职级")
	@PostMapping("/invalidById" )
	public R invalidById(@RequestBody OtcdEmpgrade otcdEmpgrade){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer id = otcdEmpgrade.getId();

		QueryWrapper<Etemployee> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("empgrade",id);
		List emplist = etemployeeService.list(queryWrapper);
		if(emplist.size()>0){
			return R.ok(null,"此职级被使用，请先修改员工职务！");
		}

		otcdEmpgrade.setIsdisabled(1);
		UpdateWrapper<OtcdEmpgrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdEmpgrade.getId());
		otcdEmpgradeService.update(otcdEmpgrade,updateWrapper);

		return R.ok("数据已修改！");
	}


}
