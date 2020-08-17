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
package com.pig4cloud.pigx.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.Systpayrollitem;
import com.pig4cloud.pigx.admin.entity.Systpaystditem;
import com.pig4cloud.pigx.admin.mapper.CtstandardMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.CtstandardService;
import com.pig4cloud.pigx.admin.service.SystpayrollitemService;
import com.pig4cloud.pigx.admin.service.SystpaystditemService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-04-22 14:42:49
 */
@Slf4j
@Service
@AllArgsConstructor
public class SystpaystditemServiceImpl extends ServiceImpl<SystpaystditemMapper, Systpaystditem> implements SystpaystditemService {
	private  final  SystpaystditemMapper systpaystditemMapper;
	private final SystpayrollitemService systpayrollitemService;


	/*
	 **组装薪资模板s
	 *
	 */
	@Override
	public List<LinkedHashMap>  getSalaryTemplate(){
		String sql = getSalaryTemplateSql();
		return systpaystditemMapper.listSalaryTemplate(sql);
	}

	/*
	 **组装薪资模板sql
	 *
	 *//*
	@Override
	public String getSalaryTemplateSql(){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		//获取当前组织薪资项
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpid(corpid);
		systpaystditem.setIsdisabled(0);
		List systpaystditemList = list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		Systpaystditem item = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.EID,A1.Badge,A1.Name,(select title from otcompany c where CompID=A1.CompID) compname,(select title from otdepartment d where DepID=A1.DepID) depname";
		String sql2= "(select EID,";
		//1、返回列名，以键值对的形式
		//2、拼接薪资模板sql
		//(Select A3.EID As A3A1ID ,Max(Case When A3.PayStdItemID='10000' Then A3.xValue Else Null End) As xValue_10000 ,Max(Case When A3.PayStdItemID='10001' Then A3.xValue Else Null End) As xValue_10001 ,Max(Case When A3.PayStdItemID='10002' Then A3.xValue Else Null End) As xValue_10002  From ctStandard As A3 Where  A3.CorpID='1' Group By A3.EID) A201 On A1.EID=A201.A3A1ID
		//select case when A2.PayStdItemID from ctStandard A2
		//select case when A2.PayStdItemID from ctStandard A2
		Map mapResult = new HashMap();
		for(int i=0;i<systpaystditemList.size();i++){
			item = (Systpaystditem)systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			mapResult.put(colName,title);
			sql1 = sql1 + "," + "A2." +colName;
			if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when PayStdItemID = "+paystdItemID +" then xValue else '' end  "+colName+",";
			}else{
				sql2 = sql2 + " case when PayStdItemID = "+paystdItemID +" then xValue else '' end "+colName ;
			}

		}
		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from ctStandard  Where  CorpID = " + corpid +") A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";
		sql = sql + " where A1.CorpID =" + corpid;
		log.info(sql);
		//systpaystditemMapper.
		//ctstandardService
		return  sql;
	}*/

	/*
	 **组装薪资模板sql
	 *
	 */
	/*@Override
	public String getSalaryTemplateSql(){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		//获取当前组织薪资项
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpcode(corpcode);
		//systpaystditem.setIsdisabled(0);
		List systpaystditemList = list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		Systpaystditem item = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.EID,A1.Badge,A1.Name,(select title from otcompany c where CompID=A1.CompID) compname,(select title from otdepartment d where DepID=A1.DepID) depname";
		String sql2= "(select EID,";
		//1、返回列名，以键值对的形式
		//2、拼接薪资模板sql
		//(Select A3.EID As A3A1ID ,Max(Case When A3.PayStdItemID='10000' Then A3.xValue Else Null End) As xValue_10000 ,Max(Case When A3.PayStdItemID='10001' Then A3.xValue Else Null End) As xValue_10001 ,Max(Case When A3.PayStdItemID='10002' Then A3.xValue Else Null End) As xValue_10002  From ctStandard As A3 Where  A3.CorpID='1' Group By A3.EID) A201 On A1.EID=A201.A3A1ID
		//select case when A2.PayStdItemID from ctStandard A2
		//select case when A2.PayStdItemID from ctStandard A2
		Map mapResult = new HashMap();
		for(int i=0;i<systpaystditemList.size();i++){
			item = (Systpaystditem)systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			mapResult.put(colName,title);
			sql1 = sql1 + "," + "A2." +title;
			if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql2 = sql2 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}
		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from systpayitemfactvalue  Where  CorpID = " + corpid +") A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";
		sql = sql + " where A1.CorpID =" + corpid;
		log.info(sql);
		//systpaystditemMapper.
		//ctstandardService
		return  sql;
	}*/

/*	@Override
	public String getSalaryTemplateSql(){
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
		List systpaystditemList = list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));
		List systpayrollitemList2 = systpayrollitemService.list(Wrappers.query(systpayrollitem2).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.EID,A1.Badge,A1.Name,(select title from otcompany c where CompID=A1.CompID) compname,(select title from otdepartment d where DepID=A1.DepID) depname";
		String sql2= "(select EID,";
		String sql3 = "(select EID,";
		String sql4 = "(select EID,";
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
			sql1 = sql1 + "," +"" + "A2." +title;
			if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end  "+title+",";
			}else{
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +title;
			if(i!=systpayrollitemList.size()-1){
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A4." +title;
			if(i!=systpayrollitemList.size()-1){
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}

		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from ctstandard  Where  corpcode = '" + corpcode +"') A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql3 = sql3  +"  from systpayitemfactvalue  Where  corpcode = '" + corpcode +"') A3 on A1.EID = A3.EID ";
		sql4 = sql4  +"  from systpayitemfactvalue  Where  corpcode = '" + corpcode +"') A4 on A1.EID = A4.EID ";
		sql = sql + " left join "+sql3;
		sql = sql + " left join "+sql4;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpcode ='" + corpcode +"'";
		log.info(sql);
		//systpaystditemMapper.
		//ctstandardService
		return  sql;
	}*/
	@Override
	public String getSalaryTemplateSql(){
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
		List systpaystditemList = list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));
		List systpayrollitemList2 = systpayrollitemService.list(Wrappers.query(systpayrollitem2).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.EID,A1.Badge,A1.Name,(select title from otcompany c where CompID=A1.CompID) compname,(select title from otdepartment d where DepID=A1.DepID) depname";
		String sql2= "(select EID,";
		String sql3 = "(select EID,";
		String sql4 = "(select EID,";
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
			sql1 = sql1 + "," +"" + "A2." +title;
			if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end  "+title+",";
			}else{
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +title;
			if(i!=systpayrollitemList.size()-1){
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList2.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList2.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A4." +title;
			if(i!=systpayrollitemList2.size()-1){
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}

		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from ctstandard  Where  corpcode = '" + corpcode +"') A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql3 = sql3  +"  from systpayiteminputvalue  Where  corpcode = '" + corpcode +"') A3 on A1.EID = A3.EID ";
		sql4 = sql4  +"  from systpayitemfactvalue  Where  corpcode = '" + corpcode +"') A4 on A1.EID = A4.EID ";
		sql = sql + " left join "+sql3;
		sql = sql + " left join "+sql4;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.corpcode ='" + corpcode +"'";
		log.info(sql);
		//systpaystditemMapper.
		//ctstandardService
		return  sql;
	}

	/*
	 **工资条
	 *
	 */
	@Override
	public List<LinkedHashMap>  getSalaryTemplateByEID(Integer type){
		String sql = getSalaryTemplateSqlByEID(type);
		return systpaystditemMapper.listSalaryTemplate(sql);
	}
	/*
	 **工资条历史
	 *
	 */
	@Override
	public List<LinkedHashMap>  getSalaryTemplateByEIDAndTerm(Integer type,String term){
		String sql = getSalaryTemplateSqlByEIDAndTerm(type,term);
		return systpaystditemMapper.listSalaryTemplate(sql);
	}

	public String getSalaryTemplateSqlByEID(Integer type){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		Integer eid = pigxUser.getEid();
		List<Map> resultList = null;
		//获取当前组织薪资项
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpcode(corpcode);
		systpaystditem.setType(type);
		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpcode(corpcode);
		systpayrollitem.setIftype(1);
		systpayrollitem.setType(type);
		Systpayrollitem systpayrollitem2 = new Systpayrollitem();
		systpayrollitem2.setCorpcode(corpcode);
		systpayrollitem2.setIftype(0);
		systpayrollitem2.setType(type);
		//systpaystditem.setIsdisabled(0);
		List systpaystditemList = list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));
		List systpayrollitemList2 = systpayrollitemService.list(Wrappers.query(systpayrollitem2).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.EID ";
		String sql2= "(select EID,";
		String sql3 = "(select EID,";
		String sql4 = "(select EID,";
		if(systpaystditemList.size()<1){
			sql2 =  "(select EID ";
		}
		if(systpayrollitemList.size()<1){
			sql3 =  "(select EID ";
		}
		if(systpayrollitemList2.size()<1){
			sql4 =  "(select EID ";
		}

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
			sql1 = sql1 + "," + "A2." +title;
			if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end  "+title+",";
			}else{
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +title;
			if(i!=systpayrollitemList.size()-1){
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList2.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList2.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A4." +title;
			if(i!=systpayrollitemList2.size()-1){
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}

		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from ctstandard  Where  eid="+eid+" and corpcode = '" + corpcode +"') A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql3 = sql3  +"  from systpayiteminputvalue  Where  corpcode = '" + corpcode +"'"+" and eid="+eid+") A3 on A1.EID = A3.EID ";
		sql4 = sql4  +"  from systpayitemfactvalue  Where  corpcode = '" + corpcode +"'"+" and eid="+eid+") A4 on A1.EID = A4.EID ";
		sql = sql + " left join "+sql3;
		sql = sql + " left join "+sql4;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.eid ="+eid;
		sql = sql + " and  A1.corpcode ='" + corpcode +"'";
		log.info(sql);
		//systpaystditemMapper.
		//ctstandardService
		return  sql;
	}

	public String getSalaryTemplateSqlByEIDAndTerm(Integer type,String term){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		Integer eid = pigxUser.getEid();
		List<Map> resultList = null;
		//获取当前组织薪资项
		Systpaystditem systpaystditem = new Systpaystditem();
		systpaystditem.setCorpcode(corpcode);
		systpaystditem.setType(type);
		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpcode(corpcode);
		systpayrollitem.setIftype(1);
		systpayrollitem.setType(type);
		Systpayrollitem systpayrollitem2 = new Systpayrollitem();
		systpayrollitem2.setCorpcode(corpcode);
		systpayrollitem2.setIftype(0);
		systpayrollitem2.setType(type);
		//systpaystditem.setIsdisabled(0);
		List systpaystditemList = list(Wrappers.query(systpaystditem).orderByAsc("xorder"));
		List systpayrollitemList = systpayrollitemService.list(Wrappers.query(systpayrollitem).orderByAsc("xorder"));
		List systpayrollitemList2 = systpayrollitemService.list(Wrappers.query(systpayrollitem2).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select  A1.EID ";
		String sql2= "(select EID,";
		String sql3 = "(select EID,";
		String sql4 = "(select EID,";
		if(systpaystditemList.size()<1){
			sql2 =  "(select EID ";
		}
		if(systpayrollitemList.size()<1){
			sql3 =  "(select EID ";
		}
		if(systpayrollitemList2.size()<1){
			sql4 =  "(select EID ";
		}

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
			sql1 = sql1 + "," + "A2." +title;
			if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end  "+title+",";
			}else{
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A3." +title;
			if(i!=systpayrollitemList.size()-1){
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql3 = sql3 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}
		for(int i=0;i<systpayrollitemList2.size();i++){
			item2 = (Systpayrollitem)systpayrollitemList2.get(i);
			colName = item2.getColname();
			title = item2.getTitle();
			paystdItemID = item2.getId();
			//mapResult.put(colName,title);
			sql1 = sql1 + "," + "A4." +title;
			if(i!=systpayrollitemList2.size()-1){
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end  "+title+",";
			}else{
				sql4 = sql4 + " case when payitem = "+paystdItemID +" then factvalue else '' end "+title ;
			}

		}

		//sql1 = sql1+" cVW_Employee A1 where A1.corpid = " + corpid;
		sql1 = sql1+" from cvw_employee A1 ";
		sql2 = sql2 +"  from ctstandard  Where  eid="+eid+" and corpcode = '" + corpcode +"') A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";
		//TIMESTAMPDIFF(MONTH,'2020-03-23 00:00:00',DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%S'))
		sql3 = sql3  +"  from systpayiteminputvalue_all  Where  corpcode = '" + corpcode +"'"+" and eid="+eid
				+" and TIMESTAMPDIFF(MONTH,"+"'"+term+" 00:00:00',"+"DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%S'))=0"
				+") A3 on A1.EID = A3.EID ";
		sql4 = sql4  +"  from systpayitemfactvalue_all  Where  corpcode = '" + corpcode +"'"+" and eid="+eid
				+" and TIMESTAMPDIFF(MONTH,"+"'"+term+" 00:00:00',"+"DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%S'))=0"
				+") A4 on A1.EID = A4.EID ";
		sql = sql + " left join "+sql3;
		sql = sql + " left join "+sql4;
		//sql = sql + " left join " + sql3 +" on A1.EID=A3.EID";
		sql = sql + " where A1.eid ="+eid;
		sql = sql + " and  A1.corpcode ='" + corpcode +"'";
		log.info(sql);
		//systpaystditemMapper.
		//ctstandardService
		return  sql;
	}
}
