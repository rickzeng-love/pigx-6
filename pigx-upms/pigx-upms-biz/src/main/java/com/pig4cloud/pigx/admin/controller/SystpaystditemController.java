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
import com.pig4cloud.pigx.admin.entity.CtcdPayrollitemtype;
import com.pig4cloud.pigx.admin.entity.Systpaystditem;
import com.pig4cloud.pigx.admin.entity.SystpaystditemCommon;
import com.pig4cloud.pigx.admin.service.CtcdPayrollitemtypeService;
import com.pig4cloud.pigx.admin.service.SystpaystditemCommonService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.SystpaystditemService;
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
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-04-22 14:42:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpaystditem" )
@Api(value = "systpaystditem", tags = "薪资项管理")
public class SystpaystditemController {

    private final  SystpaystditemService systpaystditemService;
    private final SystpaystditemCommonService systpaystditemCommonService;
	private  final CtcdPayrollitemtypeService ctcdPayrollitemtypeService;
    /**
     * 分页查询
     * @param page 分页对象getSalaryTemplate
     * @param systpaystditem 薪资项
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpaystditemPage(Page page, Systpaystditem systpaystditem) {
        return R.ok(systpaystditemService.page(page, Wrappers.query(systpaystditem)));
    }


    /**
     * 通过id查询薪资项
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(systpaystditemService.getById(id));
    }

    /**
     * 新增薪资项
     * @param systpaystditem 薪资项
     * @return R
     */
    @ApiOperation(value = "新增薪资项", notes = "新增薪资项")
    @SysLog("新增薪资项" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpaystditem_add')" )
    public R save(@RequestBody Systpaystditem systpaystditem) {
        return R.ok(systpaystditemService.save(systpaystditem));
    }

/*
    */
/**
     * 修改薪资项
     * @param systpaystditem 薪资项
     * @return R
     *//*

    @ApiOperation(value = "修改薪资项", notes = "修改薪资项")
    @SysLog("修改薪资项" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpaystditem_edit')" )
    public R updateById(@RequestBody Systpaystditem systpaystditem) {
        return R.ok(systpaystditemService.updateById(systpaystditem));
    }

    */
/**
     * 通过id删除薪资项
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资项", notes = "通过id删除薪资项")
    @SysLog("通过id删除薪资项" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systpaystditem_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(systpaystditemService.removeById(id));
    }
*/

    /*
    **组装薪资模板
    *
     */
	@PostMapping("/getSalaryTemplate")
    public R getSalaryTemplate(){
    	return R.ok(systpaystditemService.getSalaryTemplate());
	}


	/*
	 **查询员工个人工资条
	 *
	 */
	@PostMapping("/getSalaryTemplateByEID")
	public R getSalaryTemplateByEID(){

		//1 基本工资
		//2	津补贴
		//3	工龄/司龄工资
		//4	绩效奖金
		//5	计时/计件工资
		//6	社保/公积金
		//7	调整项
		//8	加班费
		//9	考勤扣款
		//10 个人所得税
		//11 汇总项
		List list1 = null;
		List list2 = null;
		List list3 = null;
		List list4 = null;
		List list5 = null;
		List list6 = null;
		List list7 = null;
		List list8 = null;
		List list9 = null;
		List list10 = null;


		Integer type = null;
		CtcdPayrollitemtype ctcdPayrollitemtype = null;
		QueryWrapper<CtcdPayrollitemtype> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("isdisabled",0);
		List typeList = ctcdPayrollitemtypeService.list(queryWrapper.orderByAsc("xorder"));
		for(int i=0;i<typeList.size();i++){
			ctcdPayrollitemtype = (CtcdPayrollitemtype)typeList.get(i);
			type = ctcdPayrollitemtype.getId();
			if(type==1){
				list1 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==2){
				list2 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==3){
				list3 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==4){
				list4 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==5){
				list5 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==6){
				list6 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==7){
				list7 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==8){
				list8 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==9){
				list9 = systpaystditemService.getSalaryTemplateByEID(type);
			}else if(type==10 || type==11){
				list10 = systpaystditemService.getSalaryTemplateByEID(type);
			}

		}
		Map resultMap = new HashMap();
		resultMap.put("list1",list1);
		resultMap.put("list2",list2);
		resultMap.put("list3",list3);
		resultMap.put("list4",list4);
		resultMap.put("list5",list5);
		resultMap.put("list6",list6);
		resultMap.put("list7",list7);
		resultMap.put("list8",list8);
		resultMap.put("list9",list9);
		resultMap.put("list10",list10);
		return R.ok(resultMap);
	}

	/*
	 **查询员工个人工资条历史
	 *
	 *//*
	@PostMapping("/getSalaryTemplateByEIDAndTerm")
	public R getSalaryTemplateByEIDAndTerm(String term, Integer num){

		//1 基本工资
		//2	津补贴
		//3	工龄/司龄工资
		//4	绩效奖金
		//5	计时/计件工资
		//6	社保/公积金
		//7	调整项
		//8	加班费
		//9	考勤扣款
		//10 个人所得税
		//11 汇总项
		//term = term+" 00:00:00";
		String pdate = term;
		//term = term+" 00:00:00";
		Date dateterm =DateUtils.parse2(term);
		Double sfgz = 0.0;
		Map resultMap = new HashMap();
		for(int z=1;z<num+1;z++){
			if(z!=1){
				pdate = DateUtils.getDateForPlus(dateterm);
			}



			List list1 = null;
			List list2 = null;
			List list3 = null;
			List list4 = null;
			List list5 = null;
			List list6 = null;
			List list7 = null;
			List list8 = null;
			List list9 = null;
			List list10 = null;


			Integer type = null;
			CtcdPayrollitemtype ctcdPayrollitemtype = null;
			QueryWrapper<CtcdPayrollitemtype> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("isdisabled",0);
			List typeList = ctcdPayrollitemtypeService.list(queryWrapper.orderByAsc("xorder"));
			for(int i=0;i<typeList.size();i++){
				ctcdPayrollitemtype = (CtcdPayrollitemtype)typeList.get(i);
				type = ctcdPayrollitemtype.getId();
				if(type==1){
					list1 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==2){
					list2 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==3){
					list3 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==4){
					list4 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==5){
					list5 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==6){
					list6 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==7){
					list7 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==8){
					list8 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==9){
					list9 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==10 || type==11){
					list10 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
					Map lmap = list10.get(0)!=null ? (Map)list10.get(0):null;
					if(lmap!=null){
						Object llsfgz = lmap.get("实发工资");
						if(llsfgz!=null){
							sfgz = sfgz +Double.parseDouble(llsfgz.toString());
						}
					}
				}

			}
			Map resultMap1 = new HashMap();
			resultMap1.put("list1",list1);
			resultMap1.put("list2",list2);
			resultMap1.put("list3",list3);
			resultMap1.put("list4",list4);
			resultMap1.put("list5",list5);
			resultMap1.put("list6",list6);
			resultMap1.put("list7",list7);
			resultMap1.put("list8",list8);
			resultMap1.put("list9",list9);
			resultMap1.put("list10",list10);
			Map resultMap2 = new HashMap();
			resultMap2.put("term",term.substring(0,7));
			List resultList = new ArrayList(2);
			resultList.add(resultMap1);
			resultList.add(resultMap2);
			resultMap.put("map",resultList);
			resultMap.put("sfgz",sfgz);

		}


		return R.ok(resultMap);
	}*/

	/*
	 **查询员工个人工资条历史
	 *
	 */
	@PostMapping("/getSalaryTemplateByEIDAndTerm")
	public R getSalaryTemplateByEIDAndTerm(String term, Integer num){

		//1 基本工资
		//2	津补贴
		//3	工龄/司龄工资
		//4	绩效奖金
		//5	计时/计件工资
		//6	社保/公积金
		//7	调整项
		//8	加班费
		//9	考勤扣款
		//10 个人所得税
		//11 汇总项
		//term = term+" 00:00:00";
		String pdate = term;
		//term = term+" 00:00:00";
		Date dateterm =DateUtils.parse2(term);
		Double sfgz = 0.0;
		Map resultMap = new HashMap();
		List list = new ArrayList(num);
		for(int z=1;z<num+1;z++){
			if(z!=1){
				pdate = DateUtils.getDateForPlus(dateterm);
			}



			List list1 = null;
			List list2 = null;
			List list3 = null;
			List list4 = null;
			List list5 = null;
			List list6 = null;
			List list7 = null;
			List list8 = null;
			List list9 = null;
			List list10 = null;


			Integer type = null;
			CtcdPayrollitemtype ctcdPayrollitemtype = null;
			QueryWrapper<CtcdPayrollitemtype> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("isdisabled",0);
			List typeList = ctcdPayrollitemtypeService.list(queryWrapper.orderByAsc("xorder"));

			for(int i=0;i<typeList.size();i++){
				ctcdPayrollitemtype = (CtcdPayrollitemtype)typeList.get(i);
				type = ctcdPayrollitemtype.getId();
				if(type==1){
					list1 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==2){
					list2 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==3){
					list3 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==4){
					list4 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==5){
					list5 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==6){
					list6 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==7){
					list7 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==8){
					list8 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==9){
					list9 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
				}else if(type==10 || type==11){
					list10 = systpaystditemService.getSalaryTemplateByEIDAndTerm(type,pdate);
					Map lmap = list10.get(0)!=null ? (Map)list10.get(0):null;
					if(lmap!=null){
						Object llsfgz = lmap.get("实发工资");
						if(llsfgz!=null){
							sfgz = sfgz +Double.parseDouble(llsfgz.toString());
						}
					}
				}

			}

			Map resultMap1 = new HashMap();
			resultMap1.put("list1",list1);
			resultMap1.put("list2",list2);
			resultMap1.put("list3",list3);
			resultMap1.put("list4",list4);
			resultMap1.put("list5",list5);
			resultMap1.put("list6",list6);
			resultMap1.put("list7",list7);
			resultMap1.put("list8",list8);
			resultMap1.put("list9",list9);
			resultMap1.put("list10",list10);
			Map resultMap2 = new HashMap();
			resultMap2.put("term",term.substring(0,7));
			List resultList = new ArrayList(2);
			resultList.add(resultMap1);
			resultList.add(resultMap2);
			list.add(z-1,resultList);

		}
		resultMap.put("sfgz",sfgz);
		resultMap.put("mx",list);

		return R.ok(resultMap);
	}

	/**
	 * 保存薪资项-账套
	 * @param mapList 薪资项数组 	@PreAuthorize("@pms.hasPermission('admin_systpaystditem_add')" )
	 * @return R
	 */
	@ApiOperation(value = "保存薪资项-账套", notes = "保存薪资项-账套")
	@SysLog("保存薪资项-账套" )
	@PostMapping("/saveSystpaystditem")
	public R saveSystpaystditem(@RequestBody List<Map> mapList) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Map map = null;
		Integer id = null;
		String title = "";
		String colname = "";
		SystpaystditemCommon systpaystditemCommon = null;
		Systpaystditem systpaystditem2 = null;
		for(int i=0;i<mapList.size();i++){
			map = mapList.get(i);
			if(StringUtils.isEmpty(map.get("id"))){
				continue;
			}
			if(StringUtils.isEmpty(map.get("title"))){
				continue;
			}
			systpaystditemCommon = systpaystditemCommonService.getById(id);
			systpaystditem2 = systpaystditemService.getById(id);
			if(StringUtils.isEmpty(systpaystditem2)){
				continue;
			}
			id = Integer.parseInt((String)map.get("id"));
			title = map.get("title").toString();
			Systpaystditem systpaystditem = new Systpaystditem();
			systpaystditem.setId(id);
			systpaystditem.setTitle(title);
			systpaystditem.setColname(systpaystditemCommon.getColname());
			systpaystditem.setCorpid(pigxUser.getCorpid());
			systpaystditem.setCorpcode(pigxUser.getCorpcode());
			systpaystditem.setIsdisabled(0);
			systpaystditem.setRemark(systpaystditemCommon.getRemark());
			systpaystditemService.save(systpaystditem);

		}
		return R.ok("");
		//return R.ok(systpaystditemService.save(systpaystditem));
	}
}
