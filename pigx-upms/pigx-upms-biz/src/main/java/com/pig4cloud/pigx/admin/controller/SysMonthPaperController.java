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
import com.google.gson.internal.$Gson$Preconditions;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysMonthPaperMapper;
import com.pig4cloud.pigx.admin.service.MonthPaperToService;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.SysMonthPaperService;
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
 * 月报
 *
 * @author gaoxiao
 * @date 2020-04-28 14:43:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysmonthpaper" )
@Api(value = "sysmonthpaper", tags = "月报管理")
public class SysMonthPaperController {

    private final  SysMonthPaperService sysMonthPaperService;
    private final MonthPaperToService monthPaperToService;
    private final SysMonthPaperMapper sysMonthPaperMapper;
    private final SysUserService sysUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysMonthPaper 月报
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R page(Page page, SysMonthPaper sysMonthPaper) {
        return R.ok(sysMonthPaperService.page(page, Wrappers.query(sysMonthPaper)));
    }
	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getSysMonthPaperPageSql" )
	public R getSysMonthPaperPageSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		String portrait = pigxUser.getPortrait();
		int userid = pigxUser.getId();
		SysMonthPaper sysMonthPaper = new SysMonthPaper();
		sysMonthPaper.setCorpcode(corpcode);
		sysMonthPaper.setOper(userid);
		sysMonthPaper.setIsdisable(0);
		return R.ok(sysMonthPaperMapper.listSysMonthPaperPageSql(page,sysMonthPaper));
	}


    /**
     * 通过id查询月报
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PostMapping("/getById" )
    public R getById(@RequestBody SysMonthPaper sysMonthPaper2) {

    	int id = sysMonthPaper2.getId();
		SysMonthPaper sysMonthPaper = sysMonthPaperService.getById(id);
		QueryWrapper<MonthPaperTo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		queryWrapper.eq("type",0);
		List listReportto = monthPaperToService.list(queryWrapper);
		QueryWrapper<MonthPaperTo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("pid",id);
		queryWrapper2.eq("type",1);
		List listDuplicateto = monthPaperToService.list(queryWrapper2);
		sysMonthPaper.setReporttoList(listReportto);
		sysMonthPaper.setDuplicatetoList(listDuplicateto);
		return R.ok(sysMonthPaper);
    }
	/**
	 * 我收到的工作汇报
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "我收到的月报", notes = "我收到的月报")
	@PostMapping("/getSysMonthPaperPageForMyReciveSql" )
	public R getSysMonthPaperPageForMyReciveSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();

		QueryWrapper<SysMonthPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.inSql("id","select pid from sys_month_paper_to where userid ="+userid+" and type=0");
		queryWrapper.eq("isdisable",0);
		return R.ok(sysMonthPaperService.page(page, queryWrapper));
	}

	/**
	 * 我收到的工作汇报
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "抄送我的月报", notes = "抄送我的月报")
	@PostMapping("/getSysMonthPaperPageForDuplicatetomeSql" )
	public R getSysMonthPaperPageForDuplicatetomeSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String portrait = pigxUser.getPortrait();
		String corpcode =pigxUser.getCorpcode();
		int userid = pigxUser.getId();

		QueryWrapper<SysMonthPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.inSql("id","select pid from sys_month_paper_to where userid ="+userid+" and type=1");
		queryWrapper.eq("isdisable",0);
		return R.ok(sysMonthPaperService.page(page, queryWrapper));
	}
    /**
     * 新增月报      @PreAuthorize("@pms.hasPermission('admin_sysmonthpaper_add')" )
     * @param sysMonthPaper 月报
     * @return R
     */
    @ApiOperation(value = "新增月报", notes = "新增月报")
    @SysLog("新增月报" )
    @PostMapping("/save")
    public R save(@RequestBody SysMonthPaper sysMonthPaper) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		sysMonthPaper.setCorpcode(pigxUser.getCorpcode());
		sysMonthPaper.setCorpid(pigxUser.getCorpid());
		sysMonthPaper.setOper(pigxUser.getId());
		sysMonthPaper.setOpername(pigxUser.getName());
		sysMonthPaper.setOperdate(currentTime);
		sysMonthPaper.setIsdisable(0);
		sysMonthPaper.setPortrait(pigxUser.getPortrait());
		sysMonthPaperService.save(sysMonthPaper);
		int id = sysMonthPaper.getId();
		String duplicatetos = sysMonthPaper.getDuplicateto();
		String 	reporttos = sysMonthPaper.getReportto();
		String[] arrayreprotto =null;
		String[] arrayduplicatetos =null;
		Integer userid = null;


		//汇报人
		if(!StringUtils.isEmpty(reporttos)){
			arrayreprotto  =reporttos.split(",");
			for(int i=0;i<arrayreprotto.length;i++){
				userid = Integer.parseInt(arrayreprotto[i]);
				SysUser user = sysUserService.getById(userid);
				MonthPaperTo monthPaperTo = new MonthPaperTo();
				monthPaperTo.setPid(id);
				monthPaperTo.setType(0);
				monthPaperTo.setUserid(userid);
				monthPaperTo.setName(user.getUsername());
				monthPaperToService.save(monthPaperTo);
			}
		}
		//抄送人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayduplicatetos  =duplicatetos.split(",");
			for(int i=0;i<arrayduplicatetos.length;i++){
				userid = Integer.parseInt(arrayduplicatetos[i]);


				SysUser user = sysUserService.getById(userid);
				MonthPaperTo monthPaperTo = new MonthPaperTo();
				monthPaperTo.setPid(id);
				monthPaperTo.setType(1);
				monthPaperTo.setUserid(userid);
				monthPaperTo.setName(user.getUsername());
				monthPaperToService.save(monthPaperTo);
			}
		}

		return R.ok("保存成功！");
    }

    /**
     * 修改月报      @PreAuthorize("@pms.hasPermission('admin_sysmonthpaper_edit')" )
     * @param sysMonthPaper 月报
     * @return R
     */
    @ApiOperation(value = "修改月报", notes = "修改月报")
    @SysLog("修改月报" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody SysMonthPaper sysMonthPaper) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		sysMonthPaper.setUpdatedate(currentTime);
		//删除抄送人，汇报人
		QueryWrapper<MonthPaperTo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid",sysMonthPaper.getId());
		monthPaperToService.remove(queryWrapper);

		int id = sysMonthPaper.getId();
		String duplicatetos = sysMonthPaper.getDuplicateto();
		String 	reporttos = sysMonthPaper.getReportto();
		String[] arrayreprotto =null;
		String[] arrayduplicatetos =null;
		Integer userid = null;


		//汇报人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayreprotto  =reporttos.split(",");
			for(int i=0;i<arrayreprotto.length;i++){
				userid = Integer.parseInt(arrayreprotto[i]);
				SysUser user = sysUserService.getById(userid);
				MonthPaperTo monthPaperTo = new MonthPaperTo();
				monthPaperTo.setPid(id);
				monthPaperTo.setType(0);
				monthPaperTo.setUserid(userid);
				monthPaperTo.setName(user.getUsername());
				monthPaperToService.save(monthPaperTo);
			}
		}
		//抄送人
		if(!StringUtils.isEmpty(duplicatetos)){
			arrayduplicatetos  =duplicatetos.split(",");
			for(int i=0;i<arrayduplicatetos.length;i++){
				userid = Integer.parseInt(arrayduplicatetos[i]);


				SysUser user = sysUserService.getById(userid);
				MonthPaperTo monthPaperTo = new MonthPaperTo();
				monthPaperTo.setPid(id);
				monthPaperTo.setType(1);
				monthPaperTo.setUserid(userid);
				monthPaperTo.setName(user.getUsername());
				monthPaperToService.save(monthPaperTo);
			}
		}

		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<SysMonthPaper> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",sysMonthPaper.getId());
        return R.ok(sysMonthPaperService.update(sysMonthPaper,updateWrapper));
    }

    /**
     * 通过id删除月报     @PreAuthorize("@pms.hasPermission('admin_sysmonthpaper_del')" )
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除月报", notes = "通过id删除月报")
    @SysLog("通过id删除月报" )
    @PostMapping("/removeById" )
    public R removeById(@RequestBody SysMonthPaper sysMonthPaper2) {
    	int id = sysMonthPaper2.getId();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SysMonthPaper> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		queryWrapper.eq("corpcode",corpcode);
		SysMonthPaper sysMonthPaper = new SysMonthPaper();
		sysMonthPaper.setUpdatedate(currentTime);
		sysMonthPaper.setIsdisable(1);
        return R.ok(sysMonthPaperService.update(sysMonthPaper,queryWrapper));
    }

}
