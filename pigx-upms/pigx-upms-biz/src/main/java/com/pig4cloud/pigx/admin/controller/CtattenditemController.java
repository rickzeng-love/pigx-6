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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Ctattenditem;
import com.pig4cloud.pigx.admin.service.CtattenditemService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-07-17 09:06:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctattenditem" )
@Api(value = "ctattenditem", tags = "管理")
public class CtattenditemController {

    private final  CtattenditemService ctattenditemService;
	private final SystpaystditemMapper systpaystditemMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctattenditem 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getCtattenditemPage" )
    public R getCtattenditemPage(Page page, @RequestBody(required = false) Ctattenditem ctattenditem) {
    	PigxUser pigxUser = SecurityUtils.getUser();
    	Integer corpid = pigxUser.getCorpid();
    	if(StringUtils.isEmpty(ctattenditem)){
    		ctattenditem = new Ctattenditem();
		}
		ctattenditem.setCorpid(corpid);
        return R.ok(ctattenditemService.page(page, Wrappers.query(ctattenditem)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctattenditemService.getById(id));
    }

    /**
     * 新增考勤项 @PreAuthorize("@pms.hasPermission('admin_ctattenditem_add')" )
     * @param ctattenditem 
     * @return R
     */
    @ApiOperation(value = "新增考勤项", notes = "新增考勤项")
    @SysLog("新增考勤项" )
    @PostMapping("/save")
    public R save(@RequestBody Ctattenditem ctattenditem) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Ctattenditem> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",ctattenditem.getTitle());
		List<Ctattenditem> list = ctattenditemService.list(queryWrapper);
		if (list.size()>0) {
			return R.failed("考勤项名称重复，请核实！");
		}
		ctattenditem.setCorpcode(corpcode);
		ctattenditem.setCorpid(pigxUser.getCorpid());
		ctattenditemService.save(ctattenditem);
		ctattenditem.setColname("item" + ctattenditem.getId());
		return R.ok(ctattenditemService.updateById(ctattenditem));
    }

	/**
	 * 修改考勤项
	 *
	 * @param ctattenditem 考勤项
	 * @PreAuthorize("@pms.hasPermission('admin_systpaystditemcommon_edit')")
	 * @return R
	 */
	@ApiOperation(value = "修改考勤项", notes = "修改考勤项")
	@SysLog("修改考勤项")
	@PostMapping("/updateById")
	public R updateById(@RequestBody Ctattenditem ctattenditem) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Ctattenditem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.ne("id",ctattenditem.getId());
		queryWrapper.eq("title",ctattenditem.getTitle());
		List list = ctattenditemService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("薪资项名称重复，请核实！");
		}
		ctattenditem.setCorpcode(corpcode);
		UpdateWrapper<Ctattenditem> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",ctattenditem.getId());
		return R.ok(ctattenditemService.update(ctattenditem,updateWrapper));
	}

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctattenditem_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctattenditemService.removeById(id));
    }
	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "考勤项(动态)", notes = "考勤项(动态)")
	@PostMapping("/getCtattenditemPageSql")
	public R getCtattenditemPageSql(Page page) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		Integer type = null;
		//获取当前考勤想
		Ctattenditem ctattenditem = new Ctattenditem();
		ctattenditem.setCorpcode(corpcode);
		List ctattenditemList = ctattenditemService.list(Wrappers.query(ctattenditem));
		String colName = "";
		Integer ssrid = null;
		String title = "";
		Integer attendItemID = null;
		String sql = "";
		String sql1 = "select A1.id,A1.eid,A1.badge,A1.name,A1.compid,A1.depid,A1.jobid ";
		String sql2 = "(select EID ";
		String sql5 = "(select a.eid ";
		Ctattenditem item = null;
		for (int i = 0; i < ctattenditemList.size(); i++) {

			item = (Ctattenditem) ctattenditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			attendItemID = item.getId();
			sql1 = sql1 + "," + "" + "A2.'"+colName +"'";

			sql2+=",max(case when AttendItem="+attendItemID+" then FactValue else '' end) '"+colName+"'";
//			sql2 = sql2 + " ,case when paystdItemID = "+attendItemID +" then xvalue else '' end "+"A"+attendItemID ;
//			sql5 = sql5 + ","+" max(a."+"A"+attendItemID+")"+" A"+attendItemID;

		}
		long current = page.getCurrent();
		long size = page.getSize();
		sql1 = sql1 + " from ctattend A1 ";
		sql2 = sql2 +"  from ctattenditemfactvalue  Where  corpcode = '" + corpcode +"'group by eid) A2";

//		sql5 = sql5 + " from " +sql2;

		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql = sql + " where A1.corpcode ='" + corpcode + "'";
		sql = sql + " limit " + (current - 1) * size + "," + size;
		List<LinkedHashMap> list = systpaystditemMapper.listSalaryTemplate(sql);
		QueryWrapper<Ctattenditem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode", corpcode);
		List stditem = ctattenditemService.list(queryWrapper);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());
		Map resultMap = new HashMap();
		resultMap.put("resultpage", resultpage);
		resultMap.put("stditem", stditem);

		return R.ok(resultMap);

	}
}
