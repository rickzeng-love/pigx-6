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
import com.pig4cloud.pigx.admin.entity.DailyPaperTo;
import com.pig4cloud.pigx.admin.entity.MonthPaperTo;
import com.pig4cloud.pigx.admin.entity.SysMonthPaper;
import com.pig4cloud.pigx.admin.mapper.SysDailyPaperMapper;
import com.pig4cloud.pigx.admin.service.DailyPaperToService;
import com.pig4cloud.pigx.admin.service.MonthPaperToService;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.SysDailyPaper;
import com.pig4cloud.pigx.admin.service.SysDailyPaperService;
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
 * 日报
 *
 * @author gaoxiao
 * @date 2020-04-28 14:43:17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysdailypaper" )
@Api(value = "sysdailypaper", tags = "日报管理")
public class SysDailyPaperController {

    private final  SysDailyPaperService sysDailyPaperService;
    private  final DailyPaperToService dailyPaperToService;
    private final SysDailyPaperMapper sysDailyPaperMapper;
    private final SysUserService sysUserService;
    private final MonthPaperToService monthPaperToService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param sysDailyPaper 日报
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getSysDailyPaperPage" )
    public R getSysDailyPaperPage(Page page, SysDailyPaper sysDailyPaper) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();
		sysDailyPaper.setCorpcode(corpcode);
		sysDailyPaper.setOper(userid);
		sysDailyPaper.setIsdisable(0);
        return R.ok(sysDailyPaperMapper.listSysDailyPaperPageSql(page,sysDailyPaper));
    }

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getSysDailyPaperPageSql" )
	public R getSysDailyPaperPageSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		SysDailyPaper sysDailyPaper = new SysDailyPaper();
		int userid = pigxUser.getId();
		sysDailyPaper.setCorpcode(corpcode);
		sysDailyPaper.setOper(userid);
		sysDailyPaper.setIsdisable(0);


		return R.ok(sysDailyPaperService.page(page, Wrappers.query(sysDailyPaper).orderByDesc("operdate")));
	}

	/**
	 * 我收到的日报
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "我收到的日报", notes = "我收到的日报")
	@PostMapping("/getSysDailyPaperPageForMyReciveSql" )
	public R getSysDailyPaperPageForMyReciveSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();

		QueryWrapper<SysDailyPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.inSql("id","select pid from sys_daily_paper_to where userid ="+userid+" and type=0");
		queryWrapper.eq("isdisable",0);
		return R.ok(sysDailyPaperService.page(page, queryWrapper));
	}

	/**
	 * 抄送我的日报
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "抄送我的日报", notes = "抄送我的日报")
	@PostMapping("/getSysDailyPaperPageForDuplicatetomeSql" )
	public R getSysDailyPaperPageForDuplicatetomeSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();

		QueryWrapper<SysDailyPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.inSql("id","select pid from sys_daily_paper_to where userid ="+userid+" and type=1");
		queryWrapper.eq("isdisable",0);
		return R.ok(sysDailyPaperService.page(page, queryWrapper));
	}



    /**
     * 通过id查询日报
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PostMapping("/getById" )
    public R getById(@RequestBody SysDailyPaper sysDailyPaper) {
    	int id = sysDailyPaper.getId();
		SysDailyPaper sysDailyPaper1  = sysDailyPaperService.getById(id);
		QueryWrapper<DailyPaperTo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid",id);
		queryWrapper.eq("type",0);
		List listReportto = dailyPaperToService.list(queryWrapper);
		QueryWrapper<DailyPaperTo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("pid",id);
		queryWrapper2.eq("type",1);
		List listDuplicateto = dailyPaperToService.list(queryWrapper2);
		sysDailyPaper1.setReporttoList(listReportto);
		sysDailyPaper1.setDuplicatetoList(listDuplicateto);
		return R.ok(sysDailyPaper1);
    }

    /**
     * 新增日报      @PreAuthorize("@pms.hasPermission('admin_sysdailypaper_add')" )
     * @param sysDailyPaper 日报
     * @return R
     */
    @ApiOperation(value = "新增日报", notes = "新增日报")
    @SysLog("新增日报" )
    @PostMapping("/save")
    public R save(@RequestBody SysDailyPaper sysDailyPaper) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		sysDailyPaper.setCorpcode(pigxUser.getCorpcode());
		sysDailyPaper.setCorpid(pigxUser.getCorpid());
		sysDailyPaper.setOper(pigxUser.getId());
		sysDailyPaper.setOpername(pigxUser.getName());
		sysDailyPaper.setOperdate(currentTime);
		sysDailyPaper.setIsdisable(0);
		sysDailyPaper.setPortrait(pigxUser.getPortrait());
		sysDailyPaperService.save(sysDailyPaper);
		int id = sysDailyPaper.getId();
    	String duplicatetos = sysDailyPaper.getDuplicateto();
    	String 	reporttos = sysDailyPaper.getReportto();
    	String[] arrayreprotto =null;
		String[] arrayduplicatetos =null;
		Integer userid = null;



		//汇报人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayreprotto  =reporttos.split(",");
			for(int i=0;i<arrayreprotto.length;i++){
				userid = Integer.parseInt(arrayreprotto[i]);
				SysUser user = sysUserService.getById(userid);
				DailyPaperTo dailyPaperTo = new DailyPaperTo();
				dailyPaperTo.setPid(id);
				dailyPaperTo.setType(0);
				dailyPaperTo.setUserid(userid);
				dailyPaperTo.setName(user.getUsername());
				dailyPaperToService.save(dailyPaperTo);
			}
		}
		//抄送人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayduplicatetos  =duplicatetos.split(",");
			for(int i=0;i<arrayduplicatetos.length;i++){
				userid = Integer.parseInt(arrayduplicatetos[i]);
				SysUser user = sysUserService.getById(userid);
				DailyPaperTo dailyPaperTo = new DailyPaperTo();
				dailyPaperTo.setPid(id);
				dailyPaperTo.setType(1);
				dailyPaperTo.setUserid(userid);
				dailyPaperTo.setName(user.getUsername());
				dailyPaperToService.save(dailyPaperTo);
			}
		}

		return R.ok("保存成功！");
    }

    /**
     * 修改日报     @PreAuthorize("@pms.hasPermission('admin_sysdailypaper_edit')" )
     * @param sysDailyPaper 日报
     * @return R
     */
    @ApiOperation(value = "修改日报", notes = "修改日报")
    @SysLog("修改日报" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody SysDailyPaper sysDailyPaper) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		sysDailyPaper.setUpdatedate(currentTime);
		//删除抄送人，汇报人
		QueryWrapper<DailyPaperTo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid",sysDailyPaper.getId());
		dailyPaperToService.remove(queryWrapper);

		int id = sysDailyPaper.getId();
		String duplicatetos = sysDailyPaper.getDuplicateto();
		String 	reporttos = sysDailyPaper.getReportto();
		String[] arrayreprotto =null;
		String[] arrayduplicatetos =null;
		Integer userid = null;


		//汇报人
		if(!StringUtils.isEmpty(reporttos)){
			arrayreprotto  =reporttos.split(",");
			for(int i=0;i<arrayreprotto.length;i++){
				userid = Integer.parseInt(arrayreprotto[i]);
				SysUser user = sysUserService.getById(userid);
				DailyPaperTo dailyPaperTo = new DailyPaperTo();
				dailyPaperTo.setPid(id);
				dailyPaperTo.setType(0);
				dailyPaperTo.setUserid(userid);
				dailyPaperTo.setName(user.getUsername());
				dailyPaperToService.save(dailyPaperTo);
			}
		}
		//抄送人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayduplicatetos  =duplicatetos.split(",");
			for(int i=0;i<arrayduplicatetos.length;i++){
				userid = Integer.parseInt(arrayduplicatetos[i]);


				SysUser user = sysUserService.getById(userid);
				DailyPaperTo dailyPaperTo = new DailyPaperTo();
				dailyPaperTo.setPid(id);
				dailyPaperTo.setType(1);
				dailyPaperTo.setUserid(userid);
				dailyPaperTo.setName(user.getUsername());
				dailyPaperToService.save(dailyPaperTo);
			}
		}
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<SysDailyPaper> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",sysDailyPaper.getId());
		return R.ok(sysDailyPaperService.updateById(sysDailyPaper));
    }

    /**
     * 通过id删除日报      @PreAuthorize("@pms.hasPermission('admin_sysdailypaper_del')" )
     * @return R
     */
    @ApiOperation(value = "通过id删除日报", notes = "通过id删除日报")
    @SysLog("通过id删除日报" )
    @PostMapping("/removeById" )
    public R removeById(@RequestBody SysDailyPaper sysDailyPaper2) {
    	int id = sysDailyPaper2.getId();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SysDailyPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		queryWrapper.eq("corpcode",corpcode);
		SysDailyPaper sysDailyPaper  = new SysDailyPaper();
		sysDailyPaper.setUpdatedate(currentTime);
		sysDailyPaper.setIsdisable(1);
		return R.ok(sysDailyPaperService.update(sysDailyPaper,queryWrapper));
    }

}
