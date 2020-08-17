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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtshiftTotal;
import com.pig4cloud.pigx.admin.entity.Ctattenditem;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.CtattenditemService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AvwShiftdayeasy;
import com.pig4cloud.pigx.admin.service.AvwShiftdayeasyService;
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
 * gaoxiao
 *
 * @author gaoxiao
 * @date 2020-08-07 17:12:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/avwshiftdayeasy" )
@Api(value = "avwshiftdayeasy", tags = "gaoxiao管理")
public class AvwShiftdayeasyController {

    private final  AvwShiftdayeasyService avwShiftdayeasyService;
    private final CtattenditemService ctattenditemService;
    private final SystpaystditemMapper systpaystditemMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param avwShiftdayeasy gaoxiao
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAvwShiftdayeasyPage(Page page, AvwShiftdayeasy avwShiftdayeasy) {
        return R.ok(avwShiftdayeasyService.page(page, Wrappers.query(avwShiftdayeasy)));
    }


    /**
     * 通过id查询gaoxiao
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(avwShiftdayeasyService.getById(term));
    }

    /**
     * 新增gaoxiao
     * @param avwShiftdayeasy gaoxiao
     * @return R
     */
    @ApiOperation(value = "新增gaoxiao", notes = "新增gaoxiao")
    @SysLog("新增gaoxiao" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_avwshiftdayeasy_add')" )
    public R save(@RequestBody AvwShiftdayeasy avwShiftdayeasy) {
        return R.ok(avwShiftdayeasyService.save(avwShiftdayeasy));
    }

    /**
     * 修改gaoxiao
     * @param avwShiftdayeasy gaoxiao
     * @return R
     */
    @ApiOperation(value = "修改gaoxiao", notes = "修改gaoxiao")
    @SysLog("修改gaoxiao" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_avwshiftdayeasy_edit')" )
    public R updateById(@RequestBody AvwShiftdayeasy avwShiftdayeasy) {
        return R.ok(avwShiftdayeasyService.updateById(avwShiftdayeasy));
    }

    /**
     * 通过id删除gaoxiao
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除gaoxiao", notes = "通过id删除gaoxiao")
    @SysLog("通过id删除gaoxiao" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_avwshiftdayeasy_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(avwShiftdayeasyService.removeById(term));
    }
	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "日汇总查询(动态)", notes = "日汇总查询(动态)")
	@PostMapping("/getAvwShiftdayeasyPageSql")
	public R getAvwShiftdayeasyPageSql(Page page,@RequestBody(required = false) AvwShiftdayeasy avwShiftdayeasy) {
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
		String sql1 = "select A1.term,A1.eid,A1.badge,A1.name,A1.compid,A1.depid,A1.jobid ";
		String sql2 = "(select EID ";
		String sql5 = "(select a.eid ";
		Ctattenditem item = null;
		for (int i = 0; i < ctattenditemList.size(); i++) {

			item = (Ctattenditem) ctattenditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			attendItemID = item.getId();
			sql1 = sql1 + "," + "" + "A2."+colName +"";

			sql2+=",max(case when AttendItem="+attendItemID+" then FactValue else '' end) "+colName+"";
//			sql2 = sql2 + " ,case when paystdItemID = "+attendItemID +" then xvalue else '' end "+"A"+attendItemID ;
//			sql5 = sql5 + ","+" max(a."+"A"+attendItemID+")"+" A"+attendItemID;

		}
		long current = page.getCurrent();
		long size = page.getSize();
		sql1 = sql1 + " from avw_shiftdayeasy A1 ";
		sql2 = sql2 +"  from ctattenditemfactvalue  Where  corpcode = '" + corpcode +"'group by eid) A2";

//		sql5 = sql5 + " from " +sql2;

		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql = sql + " where A1.corpcode ='" + corpcode + "'";

		if(StringUtils.isEmpty(avwShiftdayeasy)){
			avwShiftdayeasy = new AvwShiftdayeasy();
		}
		Integer depid = avwShiftdayeasy.getDepid();
		Integer jobid = avwShiftdayeasy.getJobid();
		String name = avwShiftdayeasy.getName();
		if(!StringUtils.isEmpty(depid)){
			sql = sql + " and  A1.depid ="+depid;
		}
		if(!StringUtils.isEmpty(jobid)){
			sql = sql + " and A1.jobid ="+jobid;
		}
		if(!StringUtils.isEmpty(name)){
			sql = sql + " and A1.name like '%"+name+"%'";
		}


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
