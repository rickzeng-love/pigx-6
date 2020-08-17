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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.mapper.CtbencalcMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Ctbencalc;
import com.pig4cloud.pigx.admin.service.CtbencalcService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 本月社保福利
 *
 * @author gaoxiao
 * @date 2020-06-13 11:12:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbencalc" )
@Api(value = "ctbencalc", tags = "本月社保福利管理")
public class CtbencalcController {

    private final  CtbencalcService ctbencalcService;
    private final CtbencalcMapper ctbencalcMapper;
    private final SystpaystditemMapper systpaystditemMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctbencalc 本月社保福利
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getCtbencalcPage" )
    public R getCtbencalcPage(Page page,@RequestBody(required = false) Ctbencalc ctbencalc) {
    	PigxUser pigxUser = SecurityUtils.getUser();
    	Integer corpid = pigxUser.getCorpid();
    	if(StringUtils.isEmpty(ctbencalc)){
    		ctbencalc = new Ctbencalc();
		}
		ctbencalc.setCorpid(corpid);
        return R.ok(ctbencalcService.page(page, Wrappers.query(ctbencalc)));
    }

	/**
	 * 分页查询
	 * @param ctbencalc 本月社保福利
	 * @return
	 */
	@ApiOperation(value = "公积金缴存信息", notes = "分页查询")
	@PostMapping("/getCtbencalcForGJJ" )
	public R getCtbencalcForGJJ(Ctbencalc ctbencalc) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//公积金信息
		ctbencalc.setCorpcode(corpcode);
		ctbencalc.setEid(pigxUser.getEid());
		Map map = ctbencalcMapper.listctbencalcByEidForGJJ(ctbencalc);
		Map yearMap = new HashMap();
		yearMap.put("corpcode",corpcode);
		yearMap.put("eid",pigxUser.getEid());
		//获取缴存年份
		List<Map> yearList = ctbencalcMapper.listctbencalcByEidGroupbyYear(yearMap);
		//获取明细
		List<Map> listMx = null;
		Map lmap = null;
		String year = "";
		//缴存月数month
		Integer month = 0;
		//缴存总额
		double accuselfcorp = 0.0;
		List resultlist = new ArrayList(yearList.size());
		Map resultMap = null;
		for(int i=0;i<yearList.size();i++){
			listMx = null;
			lmap = yearList.get(i);
			year = lmap.get("yearterm").toString();
			resultMap = new HashMap();
			resultMap.put("year",year);
			Map mxMap = new HashMap();
			mxMap.put("corpcode",corpcode);
			mxMap.put("eid",pigxUser.getEid());
			mxMap.put("yearterm",year);
			listMx = ctbencalcMapper.listctbencalcByEidAndYear(mxMap);
			for(int g=0;g<listMx.size();g++){
				Map m = listMx.get(i);
				accuselfcorp = accuselfcorp + Double.parseDouble(m.get("accuselfcorp").toString());
			}
			month = month + listMx.size();
			resultMap.put("mx",listMx);
			resultlist.add(i,resultMap);
		}
		Ctbencalc ctbencalc1 = new Ctbencalc();
		ctbencalc1.setCorpcode(corpcode);
		ctbencalc1.setEid(pigxUser.getEid());
		Map jcxx = ctbencalcMapper.listctbencalcByEidForGJJ(ctbencalc1);
		Map map1 = new HashMap();
		map1.put("month",month);
		map1.put("ljjc",accuselfcorp);
		map1.put("mx",resultlist);
		map1.put("jcxx",jcxx);


		return R.ok(map1);
	}


	/**
	 * 分页查询
	 * @param ctbencalc 本月社保福利
	 * @return
	 */
	@ApiOperation(value = "社保信息", notes = "社保信息")
	@PostMapping("/getCtbencgetCtbencalcForSBalcForSB" )
	public R getCtbencgetCtbencalcForSBalcForSB(Ctbencalc ctbencalc) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//公积金信息
		ctbencalc.setCorpcode(corpcode);
		ctbencalc.setEid(pigxUser.getEid());

	/*	--医疗保险 IsMdcl
				--养老保险 IsPens
		--失业保险 IsUnem
				--生育保险 IsBabs
		--工伤保险 IsInjs
				--大病医疗 IsSMdcl
		--缴纳补充医疗保险 IsSuMdcl
				--缴纳补充养老保险 IsSuPen*/
		Integer IsMdcl = null;
		Integer IsPens = null;
		Integer IsUnem = null;
		Integer IsBabs = null;
		Integer IsInjs = null;
		Integer IsSMdcl = null;
		Integer IsSuMdcl = null;
		Integer IsSuPen = null;

		Map mapInfo = ctbencalcMapper.listctbencalcByEid(ctbencalc);
		IsMdcl = mapInfo.get("IsMdcl")!=null ? Integer.parseInt(mapInfo.get("IsMdcl").toString()):null;
		IsPens = mapInfo.get("IsPens")!=null ? Integer.parseInt(mapInfo.get("IsPens").toString()):null;
		IsUnem = mapInfo.get("IsUnem")!=null ? Integer.parseInt(mapInfo.get("IsUnem").toString()):null;
		IsBabs = mapInfo.get("IsBabs")!=null ? Integer.parseInt(mapInfo.get("IsBabs").toString()):null;
		IsInjs = mapInfo.get("IsInjs")!=null ? Integer.parseInt(mapInfo.get("IsInjs").toString()):null;
		IsSMdcl = mapInfo.get("IsSMdcl")!=null ? Integer.parseInt(mapInfo.get("IsSMdcl").toString()):null;
		IsSuMdcl = mapInfo.get("IsSuMdcl")!=null ? Integer.parseInt(mapInfo.get("IsSuMdcl").toString()):null;
		IsSuPen = mapInfo.get("IsSuPen")!=null ? Integer.parseInt(mapInfo.get("IsSuPen").toString()):null;
		String sql = "select  A1.eid";//from cvw_benstatus A1
		if(!StringUtils.isEmpty(IsMdcl) && IsMdcl==1){
			sql = sql + ",A2.医疗保险";
		}
		if(!StringUtils.isEmpty(IsPens)&&IsPens==1){
			sql = sql + ",A2.养老保险";
		}
		if(!StringUtils.isEmpty(IsUnem)&&IsUnem==1){
			sql = sql + ",A2.失业保险";
		}
		if(!StringUtils.isEmpty(IsBabs)&&IsBabs==1){
			sql = sql + ",A2.生育保险";
		}
		if(!StringUtils.isEmpty(IsInjs)&&IsInjs==1){
			sql = sql + ",A2.工伤保险";
		}
		if(!StringUtils.isEmpty(IsSMdcl)&&IsSMdcl==1){
			sql = sql + ",A2.大病医疗";
		}
		if(!StringUtils.isEmpty(IsSuMdcl)&&IsSuMdcl==1){
			sql = sql + ",A2.补充医疗保险";
		}
		if(!StringUtils.isEmpty(IsSuPen)&&IsSuPen==1){
			sql = sql + ",A2.补充养老保险";
		}
		sql = sql + " from cvw_benstatus A1";
		sql = sql + " left join  "+
		"(select EID, " +
				"case when IsMdcl = 1 then MdclBase else '' end 医疗保险, " +
				"case when IsPens = 1 then PensBase else '' end  养老保险, " +
				"case when IsUnem = 1 then UnemBase else '' end  失业保险, " +
				"case when IsBabs = 1 then BabsBase else '' end  生育保险, " +
				"case when IsInjs = 1 then InjsBase else '' end  工伤保险, " +
				"case when IsSMdcl = 1 then SMdclBase else '' end  大病医疗, " +
				"case when IsSuMdcl = 1 then SuMdclBase else '' end  补充医疗保险, " +
				"case when IsSuPen = 1 then SuPenBase else '' end  补充养老保险 " +
				" from cvw_benstatus where eid = "+pigxUser.getEid() + " and corpcode='"+corpcode+"') A2";
		sql = sql + "  on A1.eid = A2.eid ";
		sql = sql + " where A1.eid="+pigxUser.getEid() +" and A1.corpcode='"+corpcode+"'";

		List list = systpaystditemMapper.listSalaryTemplate(sql);
		Map map = new HashMap();
		if(list.size()>0){
			map = (Map)list.get(0);
		}

		Map yearMap = new HashMap();
		yearMap.put("corpcode",corpcode);
		yearMap.put("eid",pigxUser.getEid());
		//获取缴存年份
		List<Map> yearList = ctbencalcMapper.listctbencalcByEidGroupbyYear(yearMap);
		//获取明细
		List<Map> listMx = null;
		Map lmap = null;
		String year = "";
		//缴存月数month
		Integer month = 0;
		//缴存总额
		double accuselfcorp = 0.0;
		double bencorptotal = 0.0;
		double benselftotal = 0.0;
		double benselfcorptotal = 0.0;
		List resultlist = new ArrayList(yearList.size());
		Map resultMap = null;
		for(int i=0;i<yearList.size();i++){
			listMx = null;
			lmap = yearList.get(i);
			year = lmap.get("yearterm").toString();
			resultMap = new HashMap();
			resultMap.put("year",year);
			Map mxMap = new HashMap();
			mxMap.put("corpcode",corpcode);
			mxMap.put("eid",pigxUser.getEid());
			mxMap.put("yearterm",year);
			listMx = ctbencalcMapper.listctbencalcForSBByEidAndYear(mxMap);
			for(int g=0;g<listMx.size();g++){
				Map m = listMx.get(i);
				benselfcorptotal = benselfcorptotal + Double.parseDouble(m.get("benselfcorptotal").toString());
			}
			month = month + listMx.size();
			resultMap.put("mx",listMx);
			resultlist.add(i,resultMap);
		}

		Map map1 = new HashMap();
		map1.put("month",month);
		map1.put("ljjc",benselfcorptotal);
		map1.put("mx",resultlist);
		map1.put("jcxx",map);


		return R.ok(map1);
	}




	/**
     * 通过id查询本月社保福利
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctbencalcService.getById(id));
    }

    /**
     * 新增本月社保福利
     * @param ctbencalc 本月社保福利
     * @return R
     */
    @ApiOperation(value = "新增本月社保福利", notes = "新增本月社保福利")
    @SysLog("新增本月社保福利" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctbencalc_add')" )
    public R save(@RequestBody Ctbencalc ctbencalc) {
        return R.ok(ctbencalcService.save(ctbencalc));
    }
/*

    */
/**
     * 修改本月社保福利
     * @param ctbencalc 本月社保福利
     * @return R
     *//*

    @ApiOperation(value = "修改本月社保福利", notes = "修改本月社保福利")
    @SysLog("修改本月社保福利" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctbencalc_edit')" )
    public R updateById(@RequestBody Ctbencalc ctbencalc) {
        return R.ok(ctbencalcService.updateById(ctbencalc));
    }

    */
/**
     * 通过id删除本月社保福利
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除本月社保福利", notes = "通过id删除本月社保福利")
    @SysLog("通过id删除本月社保福利" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctbencalc_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctbencalcService.removeById(id));
    }
*/

}
