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
import com.pig4cloud.pigx.admin.mapper.OtcdPositiongradeMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.service.OtcdEmpgradeService;
import com.pig4cloud.pigx.admin.service.OtcdPositiongradeAllService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.OtcdPositiongradeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 职等
 *
 * @author gaoxiao
 * @date 2020-04-07 13:22:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdpositiongrade" )
@Api(value = "otcdpositiongrade", tags = "职等管理")
public class OtcdPositiongradeController {

    private final  OtcdPositiongradeService otcdPositiongradeService;
    private final OtcdPositiongradeMapper otcdPositiongradeMapper;
	private final EtemployeeService etemployeeService;
	private final OtcdPositiongradeAllService otcdPositiongradeAllService;
	private final OtcdEmpgradeService otcdEmpgradeService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdPositiongrade 职等
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdPositiongradePage(Page page, OtcdPositiongrade otcdPositiongrade) {
        return R.ok(otcdPositiongradeService.page(page, Wrappers.query(otcdPositiongrade)));
    }
	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param otcdPositiongrade 职等
	 * @return
	 */
	@ApiOperation(value = "职等分页查询", notes = "职等分页查询")
	@PostMapping("/getOtcdPositiongradePageBySql" )
	public R getOtcdPositiongradePageBySql(Page page, OtcdPositiongrade otcdPositiongrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPositiongrade.setCorpcode(corpcode);
		return R.ok(otcdPositiongradeMapper.listOtcdPositiongrade(page,otcdPositiongrade));
	}

	/**
	 * 职等分页查询
	 * @param otcdPositiongrade 职等
	 * @return
	 */
	@ApiOperation(value = "职等在职员工", notes = "职等在职员工")
	@PostMapping("/getEtempoyeeByPositionGrade" )
	public R getEtempoyeeByPositionGrade(OtcdPositiongrade otcdPositiongrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPositiongrade.setCorpcode(corpcode);
		return R.ok(otcdPositiongradeMapper.listEtempoyeeByPositionGrade(otcdPositiongrade));
	}
	/**
	 * 查询所有
	 * @param otcdPositiongrade 职级
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllOtcdPosition" )
	public R getAllOtcdPositiongrade(OtcdPositiongrade otcdPositiongrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPositiongrade.setCorpcode(corpcode);
//		otcdPositiongrade.setIsdisabled(0);
		return R.ok(otcdPositiongradeService.list(Wrappers.query(otcdPositiongrade)));
	}
    /**
     * 通过id查询职等
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdPositiongradeService.getById(id));
    }

    /**
     * 新增职等
     * @param otcdPositiongrade 职等
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdpositiongrade_add')" )
     */
    @ApiOperation(value = "新增职等", notes = "新增职等")
    @SysLog("新增职等" )
    @PostMapping("/save")
    public R save(@RequestBody OtcdPositiongrade otcdPositiongrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPositiongrade.setCorpcode(corpcode);

		QueryWrapper<OtcdPositiongrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdPositiongrade.getTitle());
		List jobtypelist = otcdPositiongradeService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("职等名称已存在！");
		}
		QueryWrapper<OtcdPositiongrade> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdPositiongrade.getCode());
		List jobtypelist2 = otcdPositiongradeService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("职等编码已存在！");
		}
		//变动类型 1：新增，2：修改，3：删除
		OtcdPositiongradeAll otcdPositiongradeAll = new OtcdPositiongradeAll();
		BeanUtils.copyProperties(otcdPositiongrade,otcdPositiongradeAll);
		otcdPositiongradeAll.setType(1);
		otcdPositiongradeAll.setIsdisabled(0);
		otcdPositiongradeAllService.save(otcdPositiongradeAll);
        return R.ok(otcdPositiongradeService.save(otcdPositiongrade));
    }

    /**
     * 修改职等
     * @param otcdPositiongrade 职等
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdpositiongrade_edit')" )
     */
    @ApiOperation(value = "修改职等", notes = "修改职等")
    @SysLog("修改职等" )
    @PutMapping("/updateById")
    public R updateById(@RequestBody OtcdPositiongrade otcdPositiongrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPositiongrade.setCorpcode(corpcode);

		QueryWrapper<OtcdPositiongrade> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdPositiongrade.getTitle());
		queryWrapper.ne("id",otcdPositiongrade.getId());
		List jobtypelist = otcdPositiongradeService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("职等名称已存在！");
		}
		QueryWrapper<OtcdPositiongrade> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdPositiongrade.getCode());
		queryWrapper2.ne("id",otcdPositiongrade.getId());
		List jobtypelist2 = otcdPositiongradeService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("职等编码已存在！");
		}

		//变动类型 1：新增，2：修改，3：删除
		OtcdPositiongradeAll otcdPositiongradeAll = new OtcdPositiongradeAll();
		BeanUtils.copyProperties(otcdPositiongrade,otcdPositiongradeAll);
		otcdPositiongradeAll.setType(2);

		OtcdPositiongrade otcdPositiongrade1 = otcdPositiongradeService.getById(otcdPositiongrade.getId());
		if(otcdPositiongrade1!=null){
			otcdPositiongradeAll.setCodeOld(otcdPositiongrade1.getCode());
			otcdPositiongradeAll.setCompidOld(otcdPositiongrade1.getCompid());
			otcdPositiongradeAll.setDescriptionOld(otcdPositiongrade1.getDescription());
			otcdPositiongradeAll.setTitleOld(otcdPositiongrade1.getTitle());
		}
		otcdPositiongradeAll.setType(2);
		otcdPositiongradeAllService.save(otcdPositiongradeAll);
		UpdateWrapper<OtcdPositiongrade> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdPositiongrade.getId());
		return R.ok(otcdPositiongradeService.update(otcdPositiongrade,updateWrapper));
    }

    /**
     * 通过id删除职等
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除职等", notes = "通过id删除职等")
    @SysLog("通过id删除职等" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdpositiongrade_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdPositiongradeService.removeById(id));
    }
	/*
	 * PC端职务管理在职、离职统计
	 * @param otcdPosition 职务
	 * @return
	 */
	@ApiOperation(value = "职等管理在职、离职统计", notes = "职等管理在职、离职统计")
	@PostMapping("/getZaiZhiAndLizhiPersonByPosition" )
	public R getZaiZhiAndLizhiPersonByPosition(OtcdPositiongrade otcdPositiongrade) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPositiongrade.setCorpcode(corpcode);

		long zaizhicount = 0;//在职员工
		long lizhizhicount = 0;//离职
		Map empZaiZhi = otcdPositiongradeMapper.listZaiZhiPersonByPositiongrade(otcdPositiongrade);
		if(empZaiZhi!=null){
			zaizhicount = (Long) empZaiZhi.get("count");
		}
		Map empLiZhi = otcdPositiongradeMapper.listLizhiPersonByPositiongrade(otcdPositiongrade);
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
	 * 失效职等
	 * @return R
	 */
	@ApiOperation(value = "失效职等", notes = "失效职等")
	@PostMapping("/invalidById" )
	public R invalidById(@RequestBody OtcdPositiongrade otcdPositiongrade){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer id = otcdPositiongrade.getId();

		QueryWrapper<Etemployee> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("posgrade",id);
		List emplist = etemployeeService.list(queryWrapper);
		if(emplist.size()>0){
			return R.ok(null,"此职等被使用，请先修改员工职等！");
		}
		QueryWrapper<OtcdEmpgrade> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("posgradeid",id);
		List empgradelist = otcdEmpgradeService.list(queryWrapper2);
		if(empgradelist.size()>0){
			return R.ok(null,"此职等存在关联职级，请先调整关联职级！");
		}

//		otcdPositiongrade.setIsdisabled(1);
//		UpdateWrapper<OtcdPositiongrade> updateWrapper = new UpdateWrapper<>();
//		updateWrapper.eq("corpcode",corpcode);
//		updateWrapper.eq("id",otcdPositiongrade.getId());
//		otcdPositiongradeService.update(otcdPositiongrade,updateWrapper);

		otcdPositiongradeService.removeById(otcdPositiongrade.getId());

		return R.ok("数据已修改！");
	}



}
