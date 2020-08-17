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
import com.pig4cloud.pigx.admin.entity.CvwEmployee;
import com.pig4cloud.pigx.admin.entity.Systpayrollitem;
import com.pig4cloud.pigx.admin.entity.Systpaystditem;
import com.pig4cloud.pigx.admin.mapper.SystpayrollempinfoMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.SystpayrollitemService;
import com.pig4cloud.pigx.admin.service.SystpaystditemService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systpayrollempinfo;
import com.pig4cloud.pigx.admin.service.SystpayrollempinfoService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-06-30 15:44:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollempinfo" )
@Api(value = "systpayrollempinfo", tags = "管理")
public class SystpayrollempinfoController {

    private final  SystpayrollempinfoService systpayrollempinfoService;
    private final SystpaystditemService systpaystditemService;
    private final SystpaystditemMapper systpaystditemMapper;
	private final SystpayrollitemService systpayrollitemService;
	private final SystpayrollempinfoMapper systpayrollempinfoMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollempinfo 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollempinfoPage(Page page, Systpayrollempinfo systpayrollempinfo) {
        return R.ok(systpayrollempinfoService.page(page, Wrappers.query(systpayrollempinfo)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(systpayrollempinfoService.getById(id));
    }

    /**
     * 新增
     * @param systpayrollempinfo 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollempinfo_add')" )
    public R save(@RequestBody Systpayrollempinfo systpayrollempinfo) {
        return R.ok(systpayrollempinfoService.save(systpayrollempinfo));
    }

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "获取当月薪资", notes = "获取当月薪资(动态)")
	@PostMapping("/getSystpayrollempinfoPageSql" )
	public R getSystpayrollempinfoPageSql(Page page,@RequestBody(required = false) Systpayrollempinfo systpayrollempinfo ){

		if(StringUtils.isEmpty(systpayrollempinfo)){
			systpayrollempinfo = new Systpayrollempinfo();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		Integer type =null;
		//获取当前组织薪资项
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpcode(corpcode);
		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpcode(corpcode);
		systpayrollitem.setIftype(1);
		Systpayrollitem systpayrollitem2 = new Systpayrollitem();
		systpayrollitem2.setCorpcode(corpcode);
		systpayrollitem2.setIftype(0);
		//systpaystditem.setIsdisabled(0);
		List systpaystditemList = systpaystditemService.list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));
		List systpayrollitemList2 = systpayrollitemService.list(Wrappers.query(systpayrollitem2).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.corpid,A1.id,A1.term,A1.eid,A1.badge,A1.gid,A1.name,A1.compid,A1.depid,A1.jobid,A1.empgrade,A1.joindate,A1.costid,A1.salarycity,A1.remark ";
		String sql2= "(select EID ";
		String sql3 = "(select EID ";
		String sql4 = "(select EID ";
		String sql5 = "(select a.eid ";
		String sql6 = "(select a.eid ";
		String sql7 = "(select a.eid ";
		//1、返回列名，以键值对的形式
		//2、拼接薪资模板sql
		//(Select A3.EID As A3A1ID ,Max(Case When A3.PayStdItemID='10000' Then A3.xValue Else Null End) As xValue_10000 ,Max(Case When A3.PayStdItemID='10001' Then A3.xValue Else Null End) As xValue_10001 ,Max(Case When A3.PayStdItemID='10002' Then A3.xValue Else Null End) As xValue_10002  From ctStandard As A3 Where  A3.CorpID='1' Group By A3.EID) A201 On A1.EID=A201.A3A1ID
		//select case when A2.PayStdItemID from ctStandard A2
		//select case when A2.PayStdItemID from ctStandard A2
		//Map mapResult = new HashMap();
		for(int i=0;i<systpaystditemList.size();i++){

			item = (Systpaystditem)systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," +"" + "A2."+"A"+paystdItemID ;
			sql2 = sql2 + " ,case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+"A"+paystdItemID ;

			sql5 = sql5 + ","+" max(a."+"A"+paystdItemID+")"+" A"+paystdItemID;


		}
		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +"B"+ paystdItemID;
			/*if(i!=systpayrollitemList.size()-1){
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}*/
			sql3 = sql3 + " ,case when payitem = "+paystdItemID +" then factvalue else '' end "+"B"+ paystdItemID;
			sql6 = sql6 + ","+" max(a."+"B"+paystdItemID+")"+" B"+paystdItemID;

		}
		for(int i=0;i<systpayrollitemList2.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList2.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A4." +"B"+ paystdItemID;
			sql4 = sql4 + " ,case when payitem = "+paystdItemID +" then factvalue else '' end "+"B"+ paystdItemID;
			sql7 = sql7 + ","+" max(a."+"B"+paystdItemID+")"+" B"+paystdItemID;
		}

		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from systpayrollempinfo A1 ";
		sql2 = sql2 +"  from ctstandard  Where  corpid = " + corpid +") a group by eid ) A2";

		sql5 = sql5 + " from " +sql2;

		sql = sql1+" left join "+sql5 +" on A1.EID = A2.EID ";

		sql3 = sql3  +"  from systpayiteminputvalue  Where  corpid = " + corpid +") a group by eid ) A3 on A1.EID = A3.EID ";

		sql6 = sql6 + " from " +sql3;
		sql4 = sql4  +"  from systpayitemfactvalue  Where  corpid = " + corpid +") a group by eid ) A4 on A1.EID = A4.EID ";

		sql7 = sql7 + " from " +sql4;

		sql = sql + " left join "+sql6;

		sql = sql + " left join "+sql7;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpcode ='" + corpcode +"'";
		Integer depid = systpayrollempinfo.getDepid();
		Integer jobid = systpayrollempinfo.getJobid();
		String name = systpayrollempinfo.getName();
		if(!StringUtils.isEmpty(depid)){
			sql = sql + " and  A1.depid ="+depid;
		}
		if(!StringUtils.isEmpty(jobid)){
			sql = sql + " and A1.jobid ="+jobid;
		}
		if(!StringUtils.isEmpty(name)){
			sql = sql + " and A1.name like '%"+name+"%'";
		}


		long current = page.getCurrent();
		long size = page.getSize();
		sql = sql + " limit "+(current-1)*size+","+size;
		List<LinkedHashMap> list = systpaystditemMapper.listSalaryTemplate(sql);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());

		//组装动态项
		Systpayrollempinfo systpayrollempinfo2 = new Systpayrollempinfo();
		systpayrollempinfo2.setCorpcode(corpcode);
		List list1 = systpayrollempinfoMapper.listSystpaystditemBysql(systpayrollempinfo2);
		List list2 = systpayrollempinfoMapper.listSystpayrollitemBysql(systpayrollempinfo2);
		List all = new ArrayList();
		all.addAll(list1);
		all.addAll(list2);

		Map resultMap = new HashMap();
		resultMap.put("resultpage",resultpage);
		resultMap.put("stditem",all);
		/*
		QueryWrapper<Systpaystditem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		List stditem = systpaystditemService.list(queryWrapper);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());
		Map resultMap = new HashMap();
		resultMap.put("resultpage",resultpage);
		resultMap.put("stditem",stditem);
		*/

		return  R.ok(resultMap);

	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "获取调整项", notes = "获取调整项(动态)")
	@PostMapping("/getSystpayrollempinfoInputPageSql" )
	public R getSystpayrollempinfoInputPageSql(Page page,@RequestBody(required = false) Systpayrollempinfo systpayrollempinfo ){

		if(StringUtils.isEmpty(systpayrollempinfo)){
			systpayrollempinfo = new Systpayrollempinfo();
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		Integer type =null;
		//获取当前组织薪资项
		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpcode(corpcode);
		systpayrollitem.setIftype(1);
		//systpaystditem.setIsdisabled(0);
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql2 = "(select a.eid ";
		String sql1 = "select  A1.corpid,A1.id,A1.term,A1.eid,A1.badge,A1.gid,A1.name,A1.compid,A1.depid,A1.jobid,A1.empgrade,A1.joindate,A1.costid,A1.salarycity,A1.remark ";
		String sql3 = "(select EID ";


		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +"B"+ paystdItemID;
			sql2 = sql2 + ","+"max(a."+"B"+paystdItemID+")"+" B"+paystdItemID;

			sql3 = sql3 + " ,case when payitem = "+paystdItemID +" then factvalue else '' end "+"B"+ paystdItemID;

		}
		sql3 = sql3  +"  from systpayiteminputvalue  Where  corpcode = '" + corpcode +"' ) a group by eid " +" ) A3 on A1.EID = A3.EID ";
		sql2 = sql2 + " from " +sql3;


		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from systpayrollempinfo A1 ";



		sql = sql1 + " left join "+sql2;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpcode ='" + corpcode +"'";
		Integer depid = systpayrollempinfo.getDepid();
		Integer jobid = systpayrollempinfo.getJobid();
		String name = systpayrollempinfo.getName();
		if(!StringUtils.isEmpty(depid)){
			sql = sql + " and  A1.depid ="+depid;
		}
		if(!StringUtils.isEmpty(jobid)){
			sql = sql + " and A1.jobid ="+jobid;
		}
		if(!StringUtils.isEmpty(name)){
			sql = sql + " and A1.name like '%"+name+"%'";
		}


		long current = page.getCurrent();
		long size = page.getSize();
		sql = sql + " limit "+(current-1)*size+","+size;
		List<LinkedHashMap> list = systpaystditemMapper.listSalaryTemplate(sql);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());

		//组装动态项
		Systpayrollempinfo systpayrollempinfo2 = new Systpayrollempinfo();
		systpayrollempinfo2.setCorpcode(corpcode);
		systpayrollempinfo2.setIftype(1);
		List list2 = systpayrollempinfoMapper.listSystpayrollitemBysql(systpayrollempinfo2);
		List all = new ArrayList();
		all.addAll(list2);

		Map resultMap = new HashMap();
		resultMap.put("resultpage",resultpage);
		resultMap.put("stditem",all);
		/*
		QueryWrapper<Systpaystditem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		List stditem = systpaystditemService.list(queryWrapper);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());
		Map resultMap = new HashMap();
		resultMap.put("resultpage",resultpage);
		resultMap.put("stditem",stditem);
		*/

		return  R.ok(resultMap);

	}

/*    *//**
     * 修改
     * @param systpayrollempinfo 
     * @return R
     *//*
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollempinfo_edit')" )
    public R updateById(@RequestBody Systpayrollempinfo systpayrollempinfo) {
        return R.ok(systpayrollempinfoService.updateById(systpayrollempinfo));
    }

    *//**
     * 通过id删除
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollempinfo_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(systpayrollempinfoService.removeById(id));
    }*/

}
