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
import com.pig4cloud.pigx.admin.mapper.OtcdJobtypeMapper;
import com.pig4cloud.pigx.admin.service.OtcdJobtypeAllService;
import com.pig4cloud.pigx.admin.service.OtcdJobtypeService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 岗位类型
 *
 * @author gaoxiao
 * @date 2020-04-08 11:20:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdjobtype" )
@Api(value = "otcdjobtype", tags = "岗位类型管理")
public class OtcdJobtypeController {

    private final OtcdJobtypeService otcdJobtypeService;
    private final OtcdJobtypeMapper otcdJobtypeMapper;
    private final OtcdJobtypeAllService otcdJobtypeAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdJobtype 岗位类型
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdJobtypePage(Page page, OtcdJobtype otcdJobtype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(otcdJobtype)){
			otcdJobtype = new OtcdJobtype();
		}
		otcdJobtype.setCorpcode(corpcode);
		QueryWrapper<OtcdJobtype> queryWrapper=new QueryWrapper<>();
		if (!StringUtils.isEmpty(otcdJobtype.getTitle())) {
			queryWrapper.likeRight("title",otcdJobtype.getTitle());
		}

        return R.ok(otcdJobtypeService.page(page, queryWrapper));
    }

	/**
	 * 分页查询
	 * @param page 分页对象
	 *
	 * @param otcdJobtype 岗位类别
	 * @return
	 */
	@ApiOperation(value = "岗位类别分页查询", notes = "岗位类别分页查询")
	@PostMapping("/getOtcdJobtypePageBySql" )
	public R getOtcdEmpgradePageBySql(Page page, @RequestBody(required = false) OtcdJobtype otcdJobtype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(otcdJobtype)){
			otcdJobtype = new  OtcdJobtype();
		}
		otcdJobtype.setCorpcode(corpcode);
		return R.ok(otcdJobtypeMapper.listotcdJobtype(page,otcdJobtype));
	}

	/**
	 * 查询所有
	 * @param otcdJobtype 岗位类型
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllOtcdJobtype" )
	public R getAllOtcdJobtype(OtcdJobtype otcdJobtype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdJobtype.setCorpcode(corpcode);
		return R.ok(otcdJobtypeService.list(Wrappers.query(otcdJobtype)));
	}

    /**
     * 通过id查询岗位类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {

    	return R.ok(otcdJobtypeService.getById(id));
    }

    /**
     * 新增岗位类型
     * @param otcdJobtype 岗位类型
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdjobtype_add')" )
     */
    @ApiOperation(value = "新增岗位类型", notes = "新增岗位类型")
    @SysLog("新增岗位类型" )
    @PostMapping("/save")
    public R save(@RequestBody OtcdJobtype otcdJobtype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdJobtype.setCorpcode(corpcode);
		otcdJobtype.setCorpid(pigxUser.getCorpid());
		QueryWrapper<OtcdJobtype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdJobtype.getTitle());
		List jobtypelist = otcdJobtypeService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("此岗位类型名称已存在！");
		}
		QueryWrapper<OtcdJobtype> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdJobtype.getCode());
		List jobtypelist2 = otcdJobtypeService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("此岗位类型编码已存在！");
		}
		OtcdJobtypeAll otcdJobtypeAll = new OtcdJobtypeAll();
		BeanUtils.copyProperties(otcdJobtype,otcdJobtypeAll);
		otcdJobtypeAll.setType(1);
		otcdJobtypeAllService.save(otcdJobtypeAll);
    	return R.ok(otcdJobtypeService.save(otcdJobtype));
    }

    /**
     * 修改岗位类型
     * @param otcdJobtype 岗位类型
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdjobtype_edit')" )
     */
    @ApiOperation(value = "修改岗位类型", notes = "修改岗位类型")
    @SysLog("修改岗位类型" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody OtcdJobtype otcdJobtype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdJobtype.setCorpcode(corpcode);
		QueryWrapper<OtcdJobtype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdJobtype.getTitle());
		queryWrapper.ne("id",otcdJobtype.getId());
		List jobtypelist = otcdJobtypeService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("岗位类型名称已存在！");
		}
		QueryWrapper<OtcdJobtype> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdJobtype.getCode());
		queryWrapper2.ne("id",otcdJobtype.getId());
		List jobtypelist2 = otcdJobtypeService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("岗位类型编码已存在！");
		}
		OtcdJobtypeAll otcdJobtypeAll = new OtcdJobtypeAll();
		BeanUtils.copyProperties(otcdJobtype,otcdJobtypeAll);
		//原来的岗位类型信息
		OtcdJobtype otcdJobtype1 = otcdJobtypeService.getById(otcdJobtype.getId());
		if(otcdJobtype1!=null){
			otcdJobtypeAll.setCodeOld(otcdJobtype1.getCode());
			otcdJobtypeAll.setTitleOld(otcdJobtype1.getTitle());
		}
		otcdJobtypeAll.setType(2);
		otcdJobtypeAllService.save(otcdJobtypeAll);
		UpdateWrapper<OtcdJobtype> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdJobtype.getId());
        return R.ok(otcdJobtypeService.update(otcdJobtype,updateWrapper));
    }

    /**
     * 通过id删除岗位类型
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除岗位类型", notes = "通过id删除岗位类型")
    @SysLog("通过id删除岗位类型" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdjobtype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdJobtypeService.removeById(id));
    }
	/**
	 * 通过corpcode获取岗位信息
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "获取岗位信息", notes = "获取岗位信息")
	@SysLog("获取岗位信息" )
	@PutMapping("/getOtcdJobtypes")
	public R getOtcdJobtypes() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		OtcdJobtype otcdJobtype = new OtcdJobtype();
		otcdJobtype.setCorpcode(corpcode);
		List otcdJobtypeList = otcdJobtypeService.listOtcdJobtypeByCorpcodeWithPeople(otcdJobtype);
		return R.ok(otcdJobtypeList);
	}
	/**
	 * 根据条件判断部门是否存在
	 * @return R
	 */
	@ApiOperation(value = "失效岗位类型", notes = "失效岗位类型")
	@PostMapping("/invalidById" )
	@Transactional(rollbackFor = Exception.class)
	public R invalidById(@RequestBody OtcdJobtype otcdJobtype){
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer jobid = otcdJobtype.getId();
		String corpcode = pigxUser.getCorpcode();



		List list = otcdJobtypeMapper.listOtJobByotcdJobtype(otcdJobtype);
		if(list.size()>0){
			return R.ok(null,"此岗位类型被应用，请先处理相关岗位！");
		}

		OtcdJobtypeAll otcdJobtypeAll = new OtcdJobtypeAll();
		//原来的岗位类型信息
		OtcdJobtype otcdJobtype1 = otcdJobtypeService.getById(otcdJobtype.getId());
		BeanUtils.copyProperties(otcdJobtype1,otcdJobtypeAll);
		if(otcdJobtype1!=null){
			otcdJobtypeAll.setCodeOld(otcdJobtype1.getCode());
			otcdJobtypeAll.setTitleOld(otcdJobtype1.getTitle());
		}
		otcdJobtypeAll.setType(3);
		otcdJobtypeAllService.save(otcdJobtypeAll);
		OtcdJobtype otcdJobtype3 = new OtcdJobtype();
		otcdJobtype3.setId(otcdJobtype.getId());
		otcdJobtype3.setIsdisabled(1);
		QueryWrapper<OtcdJobtype> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdJobtype.getId());
		otcdJobtypeService.remove(updateWrapper);
		return R.ok("数据已修改！");
	}


}
