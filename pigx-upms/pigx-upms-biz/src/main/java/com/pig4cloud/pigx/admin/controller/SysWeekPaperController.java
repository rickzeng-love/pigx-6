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
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysWeekPaperMapper;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.admin.service.WeekPaperToService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.SysWeekPaperService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 周报
 *
 * @author gaoxiao
 * @date 2020-04-28 14:43:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysweekpaper" )
@Api(value = "sysweekpaper", tags = "周报管理")
public class SysWeekPaperController {

    private final  SysWeekPaperService sysWeekPaperService;
    private final WeekPaperToService weekPaperToService;
    private final SysWeekPaperMapper sysWeekPaperMapper;
	private final SysUserService sysUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysWeekPaper 周报
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R page(Page page, SysWeekPaper sysWeekPaper) {
        return R.ok(sysWeekPaperService.page(page, Wrappers.query(sysWeekPaper)));
    }

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getSysWeekPaperPageSql" )
	public R getSysWeekPaperPageSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		String portrait = pigxUser.getPortrait();
		int userid = pigxUser.getId();
		SysWeekPaper sysWeekPaper = new SysWeekPaper();
		sysWeekPaper.setCorpcode(corpcode);
		sysWeekPaper.setOper(userid);
		sysWeekPaper.setIsdisable(0);
		return R.ok(sysWeekPaperMapper.listSysWeekPaperPageSql(page,sysWeekPaper));
	}

	/**
	 * 我收到的周报
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "我收到的周报", notes = "我收到的周报")
	@PostMapping("/getSysWeekPaperPageForMyReciveSql" )
	public R getSysWeekPaperPageForMyReciveSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();

		QueryWrapper<SysWeekPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.inSql("id","select pid from sys_week_paper_to where userid ="+userid+" and type=0");
		queryWrapper.eq("isdisable",0);
		return R.ok(sysWeekPaperService.page(page, queryWrapper));
	}

	/**
	 * 抄送我的周报
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "抄送我的周报", notes = "抄送我的周报")
	@PostMapping("/getSysWeekPaperPageForDuplicatetomeSql" )
	public R getSysWeekPaperPageForDuplicatetomeSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();

		QueryWrapper<SysWeekPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.inSql("id","select pid from sys_week_paper_to where userid ="+userid+" and type=1");
		queryWrapper.eq("isdisable",0);
		return R.ok(sysWeekPaperService.page(page, queryWrapper));
	}

    /**
     * 通过id查询周报
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PostMapping("/getById" )
    public R getById(@RequestBody SysWeekPaper sysWeekPaper2) {
    	int id = sysWeekPaper2.getId();
		SysWeekPaper sysWeekPaper = sysWeekPaperService.getById(id);
		QueryWrapper<WeekPaperTo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		queryWrapper.eq("type",0);
		List listReportto = weekPaperToService.list(queryWrapper);
		QueryWrapper<WeekPaperTo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("pid",id);
		queryWrapper2.eq("type",1);
		List listDuplicateto = weekPaperToService.list(queryWrapper2);
		sysWeekPaper.setReporttoList(listReportto);
		sysWeekPaper.setDuplicatetoList(listDuplicateto);
		return R.ok(sysWeekPaper);
    }

    /**
     * 新增周报      @PreAuthorize("@pms.hasPermission('admin_sysweekpaper_add')" )
     * @param sysWeekPaper 周报
     * @return R
     */
    @ApiOperation(value = "新增周报", notes = "新增周报")
    @SysLog("新增周报" )
    @PostMapping("/save")
    public R save(@RequestBody SysWeekPaper sysWeekPaper) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		sysWeekPaper.setCorpcode(pigxUser.getCorpcode());
		sysWeekPaper.setCorpid(pigxUser.getCorpid());
		sysWeekPaper.setOper(pigxUser.getId());
		sysWeekPaper.setOpername(pigxUser.getName());
		sysWeekPaper.setOperdate(currentTime);
		sysWeekPaper.setIsdisable(0);
		sysWeekPaper.setPortrait(pigxUser.getPortrait());
		sysWeekPaperService.save(sysWeekPaper);
		int id = sysWeekPaper.getId();
		String duplicatetos = sysWeekPaper.getDuplicateto();
		String 	reporttos = sysWeekPaper.getReportto();
		String[] arrayreprotto =null;
		String[] arrayduplicatetos =null;
		Integer userid = null;
		//汇报人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayreprotto  =reporttos.split(",");
			for(int i=0;i<arrayreprotto.length;i++){
				userid = Integer.parseInt(arrayreprotto[i]);
				SysUser user = sysUserService.getById(userid);
				WeekPaperTo weekPaperTo = new WeekPaperTo();
				weekPaperTo.setPid(id);
				weekPaperTo.setType(0);
				weekPaperTo.setUserid(userid);
				weekPaperTo.setName(user.getUsername());
				weekPaperToService.save(weekPaperTo);
			}
		}
		//抄送人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayduplicatetos  =duplicatetos.split(",");
			for(int i=0;i<arrayduplicatetos.length;i++){
				userid = Integer.parseInt(arrayduplicatetos[i]);
				SysUser user = sysUserService.getById(userid);
				WeekPaperTo weekPaperTo = new WeekPaperTo();
				weekPaperTo.setPid(id);
				weekPaperTo.setType(1);
				weekPaperTo.setUserid(userid);
				weekPaperTo.setName(user.getUsername());
				weekPaperToService.save(weekPaperTo);
			}
		}

		return R.ok("保存成功！");
    }

    /**
     * 修改周报      @PreAuthorize("@pms.hasPermission('admin_sysweekpaper_edit')" )
     * @param sysWeekPaper 周报
     * @return R
     */
    @ApiOperation(value = "修改周报", notes = "修改周报")
    @SysLog("修改周报" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody SysWeekPaper sysWeekPaper) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		sysWeekPaper.setUpdatedate(currentTime);
		//删除抄送人，汇报人
		QueryWrapper<WeekPaperTo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid",sysWeekPaper.getId());
		weekPaperToService.remove(queryWrapper);

		int id = sysWeekPaper.getId();
		String duplicatetos = sysWeekPaper.getDuplicateto();
		String 	reporttos = sysWeekPaper.getReportto();
		String[] arrayreprotto =null;
		String[] arrayduplicatetos =null;
		Integer userid = null;


		//汇报人
		if(!StringUtils.isEmpty(reporttos)){
			arrayreprotto  =reporttos.split(",");
			for(int i=0;i<arrayreprotto.length;i++){
				userid = Integer.parseInt(arrayreprotto[i]);
				SysUser user = sysUserService.getById(userid);
				WeekPaperTo weekPaperTo = new WeekPaperTo();
				weekPaperTo.setPid(id);
				weekPaperTo.setType(0);
				weekPaperTo.setUserid(userid);
				weekPaperTo.setName(user.getUsername());
				weekPaperToService.save(weekPaperTo);
			}
		}
		//抄送人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayduplicatetos  =duplicatetos.split(",");
			for(int i=0;i<arrayduplicatetos.length;i++){
				userid = Integer.parseInt(arrayduplicatetos[i]);


				SysUser user = sysUserService.getById(userid);
				WeekPaperTo weekPaperTo = new WeekPaperTo();
				weekPaperTo.setPid(id);
				weekPaperTo.setType(1);
				weekPaperTo.setUserid(userid);
				weekPaperTo.setName(user.getUsername());
				weekPaperToService.save(weekPaperTo);
			}
		}
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<SysWeekPaper> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",sysWeekPaper.getId());
        return R.ok(sysWeekPaperService.update(sysWeekPaper,updateWrapper));
    }

    /**
     * 通过id删除周报      @PreAuthorize("@pms.hasPermission('admin_sysweekpaper_del')" )
     * @return R
     */
    @ApiOperation(value = "通过id删除周报", notes = "通过id删除周报")
    @SysLog("通过id删除周报" )
    @PostMapping("/removeById" )
    public R removeById(@RequestBody SysWeekPaper sysWeekPaper2) {
    	int id = sysWeekPaper2.getId();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SysWeekPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		queryWrapper.eq("corpcode",corpcode);
		SysWeekPaper sysWeekPaper  = new SysWeekPaper();
		sysWeekPaper.setUpdatedate(currentTime);
		sysWeekPaper.setIsdisable(1);
		return R.ok(sysWeekPaperService.update(sysWeekPaper,queryWrapper));
    }

}
