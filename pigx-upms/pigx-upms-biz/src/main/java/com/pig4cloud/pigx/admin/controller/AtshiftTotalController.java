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
import com.pig4cloud.pigx.admin.entity.Ctattenditem;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.CtattenditemService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftTotal;
import com.pig4cloud.pigx.admin.service.AtshiftTotalService;
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
 * @author shishengjie
 * @date 2020-07-22 13:44:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshifttotal" )
@Api(value = "atshifttotal", tags = "管理")
public class AtshiftTotalController {

    private final  AtshiftTotalService atshiftTotalService;
	private final CtattenditemService ctattenditemService;
	private final SystpaystditemMapper 	systpaystditemMapper;
    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftTotal 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtshiftTotalPage" )
    public R getAtshiftTotalPage(Page page,@RequestBody(required = false) AtshiftTotal atshiftTotal) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		atshiftTotal.setCorpid(corpid);
        return R.ok(atshiftTotalService.page(page, Wrappers.query(atshiftTotal)));
    }


    /**
     * 通过id查询
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) String term) {
        return R.ok(atshiftTotalService.getById(term));
    }

    /**
     * 新增
     * @param atshiftTotal 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atshifttotal_add')" )
    public R save(@RequestBody AtshiftTotal atshiftTotal) {
        return R.ok(atshiftTotalService.save(atshiftTotal));
    }

    /**
     * 修改
     * @param atshiftTotal 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atshifttotal_edit')" )
    public R updateById(@RequestBody AtshiftTotal atshiftTotal) {
        return R.ok(atshiftTotalService.updateById(atshiftTotal));
    }

    /**
     * 通过id删除
     * @param term id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atshifttotal_del')" )
    public R removeById(@PathVariable String term) {
        return R.ok(atshiftTotalService.removeById(term));
    }
	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "月汇总查询(动态)", notes = "月汇总查询(动态)")
	@PostMapping("/getAtshiftTotalPageSql")
	public R getAtshiftTotalPageSql(Page page,@RequestBody(required = false) AtshiftTotal atshiftTotal) {
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
		sql1 = sql1 + " from atshift_total A1 ";
		sql2 = sql2 +"  from ctattenditemfactvalue  Where  corpcode = '" + corpcode +"'group by eid) A2";

//		sql5 = sql5 + " from " +sql2;

		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql = sql + " where A1.corpcode ='" + corpcode + "'";

		if(StringUtils.isEmpty(atshiftTotal)){
			atshiftTotal = new AtshiftTotal();
		}
		Integer depid = atshiftTotal.getDepid();
		Integer jobid = atshiftTotal.getJobid();
		String name = atshiftTotal.getName();
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
