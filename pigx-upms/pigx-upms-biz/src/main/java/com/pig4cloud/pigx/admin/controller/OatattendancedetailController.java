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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtShiftWorkKey;
import com.pig4cloud.pigx.admin.entity.AtshiftType;
import com.pig4cloud.pigx.admin.entity.AtshiftWork;
import com.pig4cloud.pigx.admin.mapper.OatattendancedetailMapper;
import com.pig4cloud.pigx.admin.service.AtshiftWorkService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Oatattendancedetail;
import com.pig4cloud.pigx.admin.service.OatattendancedetailService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 考勤打卡原始表
 *
 * @author gaoxiao
 * @date 2020-06-19 12:11:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oatattendancedetail" )
@Api(value = "oatattendancedetail", tags = "考勤打卡原始表管理")
public class OatattendancedetailController {

    private final  OatattendancedetailService oatattendancedetailService;
    private final OatattendancedetailMapper oatattendancedetailMapper;
    private final AtshiftWorkService atshiftWorkService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param oatattendancedetail 考勤打卡原始表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOatattendancedetailPage(Page page, Oatattendancedetail oatattendancedetail) {
        return R.ok(oatattendancedetailService.page(page, Wrappers.query(oatattendancedetail)));
    }


    /**
     * 通过id查询考勤打卡原始表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(oatattendancedetailService.getById(id));
    }

    /**
     * 新增考勤打卡原始表     @PreAuthorize("@pms.hasPermission('admin_oatattendancedetail_add')" )
     * @param oatattendancedetail 考勤打卡原始表
     * @return R
     */
    @ApiOperation(value = "新增考勤打卡原始表", notes = "新增考勤打卡原始表")
    @SysLog("新增考勤打卡原始表" )
    @PostMapping("/save")
    public R save(@RequestBody Oatattendancedetail oatattendancedetail) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		oatattendancedetail.setUserid(pigxUser.getId());
		oatattendancedetail.setCorpcode(corpcode);
		oatattendancedetail.setCorpid(corpid);
		oatattendancedetail.setAttendancedate(currentTime);
		oatattendancedetail.setAttendancetime(currentTime);
		oatattendancedetail.setAttendancetype(3);
        return R.ok(oatattendancedetailService.save(oatattendancedetail));
    }

/*    *//**
     * 修改考勤打卡原始表
     * @param oatattendancedetail 考勤打卡原始表
     * @return R
     *//*
    @ApiOperation(value = "修改考勤打卡原始表", notes = "修改考勤打卡原始表")
    @SysLog("修改考勤打卡原始表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_oatattendancedetail_edit')" )
    public R updateById(@RequestBody Oatattendancedetail oatattendancedetail) {
        return R.ok(oatattendancedetailService.updateById(oatattendancedetail));
    }

    *//**
     * 通过id删除考勤打卡原始表
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除考勤打卡原始表", notes = "通过id删除考勤打卡原始表")
    @SysLog("通过id删除考勤打卡原始表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_oatattendancedetail_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(oatattendancedetailService.removeById(id));
    }
	*/
    /**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "查询考勤打卡记录", notes = "查询考勤打卡记录")
	@PostMapping("/getOatattendancedetailForUser" )
	public R getOatattendancedetailForUser(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		String date = DateUtils.getNow(DateUtils.FORMAT_SHORT);
		String ss = date.substring(8,10);

		String day = "";
		if(!StringUtils.isEmpty(ss)){
			if(Integer.parseInt(ss)<10){
				day = "S"+date.substring(9,10);
			}else{
				day = "S"+date.substring(8,10);
			}
		}

		String term = DateUtils.getFirstDayOfThisMonth();
		Integer eid = pigxUser.getEid();
		AtShiftWorkKey atShiftWorkKey=new AtShiftWorkKey(term,eid);
		//获取当前班次

		QueryWrapper<AtshiftWork> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("eid",eid);
		queryWrapper.eq("term",term);
		queryWrapper.eq("date_format(term, '%Y-%m-%d')",term);
		List<AtshiftWork> atshiftWorkList  = atshiftWorkService.list(queryWrapper);
		if(atshiftWorkList.size()<1){
			R.ok("系统中无您排班记录，请联系管理员排班！");
		}

		Oatattendancedetail oatattendancedetail = new Oatattendancedetail();
		oatattendancedetail.setCorpcode(corpcode);
		oatattendancedetail.setDay(day);
		oatattendancedetail.setEid(pigxUser.getEid());
		oatattendancedetail.setTerm(term);
		AtshiftType atshiftType = oatattendancedetailMapper.listAtshiftTypeForUser(oatattendancedetail);
		Map map = new HashMap();
		String begintime = atshiftType.getBegintime();
		String endtime = atshiftType.getEndtime();
		String btscanbegintime = atshiftType.getBtscanbegintime();
		String btscanendtime = atshiftType.getBtscanendtime();
		String etscanbegintime = atshiftType.getEtscanbegintime();
		String etscanendtime = atshiftType.getEtscanendtime();
		if(!StringUtils.isEmpty(begintime)){
			begintime = begintime.substring(1);
		}
		if(!StringUtils.isEmpty(endtime)){
			endtime = endtime.substring(1);
		}
		if(!StringUtils.isEmpty(btscanbegintime)){
			btscanbegintime = btscanbegintime.substring(1);
		}
		if(!StringUtils.isEmpty(btscanendtime)){
			btscanendtime = btscanendtime.substring(1);
		}
		if(!StringUtils.isEmpty(etscanbegintime)){
			etscanbegintime = etscanbegintime.substring(1);
		}
		if(!StringUtils.isEmpty(etscanendtime)){
			etscanendtime = etscanendtime.substring(1);
		}
		map.put("begintime",begintime);
		map.put("endtime",endtime);
		map.put("btscanbegintime",btscanbegintime);
		map.put("btscanendtime",btscanendtime);
		map.put("etscanbegintime",etscanbegintime);
		map.put("etscanendtime",etscanendtime);
		map.put("userid",userid);
		map.put("corpcode",pigxUser.getCorpcode());
		return R.ok(oatattendancedetailMapper.listOatattendancedetailForUser(map));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "查询打卡扫描区间", notes = "查询打卡扫描区间")
	@PostMapping("/getMorningOrAfternoonForUser" )
	public R getMorningOrAfternoonForUser(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		String date = DateUtils.getNow(DateUtils.FORMAT_SHORT);
		String ss = date.substring(8,10);
		String day = "";
		if(!StringUtils.isEmpty(ss)){
			if(Integer.parseInt(ss)<10){
				day = "S"+date.substring(9,10);
			}else{
				day = "S"+date.substring(8,10);
			}
		}
		String term = DateUtils.getFirstDayOfThisMonth();
		Integer eid = pigxUser.getEid();
		AtShiftWorkKey atShiftWorkKey=new AtShiftWorkKey(term,eid);
		//获取当前班次

		QueryWrapper<AtshiftWork> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("eid",eid);
		queryWrapper.eq("term",term);
		queryWrapper.eq("date_format(term, '%Y-%m-%d')",term);
		List<AtshiftWork> atshiftWorkList  = atshiftWorkService.list(queryWrapper);
		if(atshiftWorkList.size()<1){
			R.ok("系统中无您排班记录，请联系管理员排班！");
		}

		Oatattendancedetail oatattendancedetail = new Oatattendancedetail();
		oatattendancedetail.setCorpcode(corpcode);
		oatattendancedetail.setDay(day);
		oatattendancedetail.setEid(pigxUser.getEid());
		oatattendancedetail.setTerm(term);
		AtshiftType atshiftType = oatattendancedetailMapper.listAtshiftTypeForUser(oatattendancedetail);
		Map map = new HashMap();
		String begintime = atshiftType.getBegintime();
		String endtime = atshiftType.getEndtime();
		String btscanbegintime = atshiftType.getBtscanbegintime();
		String btscanendtime = atshiftType.getBtscanendtime();
		String etscanbegintime = atshiftType.getEtscanbegintime();
		String etscanendtime = atshiftType.getEtscanendtime();
		if(!StringUtils.isEmpty(begintime)){
			begintime = begintime.substring(1);
		}
		if(!StringUtils.isEmpty(endtime)){
			endtime = endtime.substring(1);
		}
		if(!StringUtils.isEmpty(btscanbegintime)){
			btscanbegintime = btscanbegintime.substring(1);
		}
		if(!StringUtils.isEmpty(btscanendtime)){
			btscanendtime = btscanendtime.substring(1);
		}
		if(!StringUtils.isEmpty(etscanbegintime)){
			etscanbegintime = etscanbegintime.substring(1);
		}
		if(!StringUtils.isEmpty(etscanendtime)){
			etscanendtime = etscanendtime.substring(1);
		}
		map.put("begintime",begintime);
		map.put("endtime",endtime);
		map.put("btscanbegintime",btscanbegintime);
		map.put("btscanendtime",btscanendtime);
		map.put("etscanbegintime",etscanbegintime);
		map.put("etscanendtime",etscanendtime);
		map.put("userid",userid);
		map.put("corpcode",pigxUser.getCorpcode());
		return R.ok(map);
	}

/*	*//**
	 * 今日考勤统计
	 * @param page 分页对象
	 * @return
	 *//*
	@ApiOperation(value = "今日考勤统计", notes = "今日考勤统计")
	@PostMapping("/getTodayAttendance" )
	public R getTodayAttendance(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		String date = DateUtils.getNow(DateUtils.FORMAT_SHORT);
		String day = "S"+date.substring(8,10);
		String term = DateUtils.getFirstDayOfThisMonth()+" 00:00:00";
		Integer eid = pigxUser.getEid();
		AtShiftWorkKey atShiftWorkKey=new AtShiftWorkKey(term,eid);

		Map map = new HashMap();
		map.put("corpcode",corpcode);
		map.put("day",day);
		map.put("term",term);
		List<Map> list = oatattendancedetailMapper.listTodayAttendance(map);
		//获取当前小时分钟
		String currenttime = DateUtils.getNow(DateUtils.FORMAT_MINUTE);
		Map lmap = null;

	*//*	attendancedate
	attendancetime
		begintime
	endtime
		btscanbegintime
	btscanendtime
		etscanbegintime
				etscanendtime*//*

		String begintime = "";
		String endtime = "";
		String btscanbegintime = "";
		String btscanendtime = "";
		String etscanbegintime = "";
		String etscanendtime = "";
		String attendancetime = "";
		for(int i=0;i<list.size();i++){
			lmap = list.get(i);
			begintime = lmap.get("begintime")!=null ? lmap.get("begintime").toString(): "";
			if(!StringUtils.isEmpty(begintime)){
				begintime = begintime.substring(1);
			}
			if(!StringUtils.isEmpty(endtime)){
				endtime = endtime.substring(1);
			}
			if(!StringUtils.isEmpty(btscanbegintime)){
				btscanbegintime = btscanbegintime.substring(1);
			}
			if(!StringUtils.isEmpty(btscanendtime)){
				btscanendtime = btscanendtime.substring(1);
			}
			if(!StringUtils.isEmpty(etscanbegintime)){
				etscanbegintime = etscanbegintime.substring(1);
			}
			if(!StringUtils.isEmpty(etscanendtime)){
				etscanendtime = etscanendtime.substring(1);
			}
			if(!StringUtils.isEmpty(attendancetime)){
				attendancetime = attendancetime.substring(11,13);
			}

			//1 首先判断是否在上班时间点
			//判断是否为空，不为空则 判断是上午卡还是下午卡
			if(currenttime.compareTo(begintime)>=0 && currenttime.compareTo(endtime)<0){

			}
			if(!StringUtils.isEmpty(attendancetime)){
				if(attendancetime.compareTo(btscanbegintime)>=0 && attendancetime.compareTo(btscanendtime)<=0 ){

				}
			}


		}



		//获取当前班次

		AtshiftWork atshiftWork  = atshiftWorkService.getById(atShiftWorkKey);
		if(StringUtils.isEmpty(atshiftWork)){
			R.ok("系统中无您排班记录，请联系管理员排班！");
		}

		Oatattendancedetail oatattendancedetail = new Oatattendancedetail();
		oatattendancedetail.setCorpcode(corpcode);
		oatattendancedetail.setDay(day);
		oatattendancedetail.setEid(pigxUser.getEid());
		oatattendancedetail.setTerm(term);
		AtshiftType atshiftType = oatattendancedetailMapper.listAtshiftTypeForUser(oatattendancedetail);
		Map map = new HashMap();
		String begintime = atshiftType.getBegintime();
		String endtime = atshiftType.getEndtime();
		String btscanbegintime = atshiftType.getBtscanbegintime();
		String btscanendtime = atshiftType.getBtscanendtime();
		String etscanbegintime = atshiftType.getEtscanbegintime();
		String etscanendtime = atshiftType.getEtscanendtime();
		if(!StringUtils.isEmpty(begintime)){
			begintime = begintime.substring(1);
		}
		if(!StringUtils.isEmpty(endtime)){
			endtime = endtime.substring(1);
		}
		if(!StringUtils.isEmpty(btscanbegintime)){
			btscanbegintime = btscanbegintime.substring(1);
		}
		if(!StringUtils.isEmpty(btscanendtime)){
			btscanendtime = btscanendtime.substring(1);
		}
		if(!StringUtils.isEmpty(etscanbegintime)){
			etscanbegintime = etscanbegintime.substring(1);
		}
		if(!StringUtils.isEmpty(etscanendtime)){
			etscanendtime = etscanendtime.substring(1);
		}
		map.put("begintime",begintime);
		map.put("endtime",endtime);
		map.put("btscanbegintime",btscanbegintime);
		map.put("btscanendtime",btscanendtime);
		map.put("etscanbegintime",etscanbegintime);
		map.put("etscanendtime",etscanendtime);
		map.put("userid",userid);
		map.put("corpcode",pigxUser.getCorpcode());
		return R.ok(map);
	}*/
}
