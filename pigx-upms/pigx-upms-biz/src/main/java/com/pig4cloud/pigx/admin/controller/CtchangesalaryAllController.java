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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemAllMapper;
import com.pig4cloud.pigx.admin.mapper.SystpaystditemMapper;
import com.pig4cloud.pigx.admin.service.SystpaystditemAllService;
import com.pig4cloud.pigx.admin.service.SystpaystditemService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.CtchangesalaryAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 薪资变动历史
 *
 * @author gaoxiao
 * @date 2020-06-12 15:16:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctchangesalaryall" )
@Api(value = "ctchangesalaryall", tags = "薪资变动历史管理")
public class CtchangesalaryAllController {

    private final  CtchangesalaryAllService ctchangesalaryAllService;
	private final SystpaystditemService systpaystditemService;
	private final SystpaystditemMapper systpaystditemMapper;
	private  final SystpaystditemAllMapper systpaystditemAllMapper;
	private final SystpaystditemAllService systpaystditemAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctchangesalaryAll 薪资变动历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getCtchangesalaryAllPage(Page page,@RequestBody(required = false) CtchangesalaryAll ctchangesalaryAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<CtchangesalaryAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		if(StringUtils.isEmpty(ctchangesalaryAll)){
			ctchangesalaryAll = new CtchangesalaryAll();
		}
		String name = ctchangesalaryAll.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",name);
		}
		Integer depid = ctchangesalaryAll.getDepid();
		Integer jobid = ctchangesalaryAll.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.like("jobid",jobid);
		}
        return R.ok(ctchangesalaryAllService.page(page,queryWrapper));
    }


    /**
     * 通过id查询薪资变动历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctchangesalaryAllService.getById(id));
    }

    /**
     * 新增薪资变动历史
     * @param ctchangesalaryAll 薪资变动历史
     * @return R
     */
    @ApiOperation(value = "新增薪资变动历史", notes = "新增薪资变动历史")
    @SysLog("新增薪资变动历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalaryall_add')" )
    public R save(@RequestBody CtchangesalaryAll ctchangesalaryAll) {
        return R.ok(ctchangesalaryAllService.save(ctchangesalaryAll));
    }


	/**
	 * 获取薪资变动历史
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "获取薪资变动历史(动态)", notes = "获取薪资变动历史(动态)")
	@PostMapping("/getCtchangesalaryall" )
	public R getCtchangesalaryall(Page page,@RequestBody(required = false)  CtchangesalaryAll ctchangesalaryAll){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		List<Map> resultList = null;
		Integer type =null;
		//获取当前薪资薪资
		SystpaystditemAll systpaystditem = new SystpaystditemAll();
		systpaystditem.setCorpcode(corpcode);
		if(StringUtils.isEmpty(ctchangesalaryAll)){
			ctchangesalaryAll = new CtchangesalaryAll();
		}
		List systpaystditemList = systpaystditemAllService.list(Wrappers.query(systpaystditem).orderByAsc("xorder"));

		Systpaystditem item = null;
		Systpayrollitem item2 = null;
		String colName = "";
		String title = "";
		Integer paystdItemID = null;
		String sql = "";
		String sql1 = "select A1.id,A1.changetype,A1.eid,A1.badge,A1.name,A1.compid,A1.depid,A1.jobid,A1.empstatus,A1.emptype,A1.empgroup,A1.empkind,A1.empgrade,A1.empproperty,A1.empcategory,A1.joindate,A1.workcity,A1.paystatus_old,A1.salarystatus_old,A1.paymode_old,A1.costid_old,A1.bankid_old,A1.bankno_old,A1.openbankemp_old,A1.bankid2_old,A1.bankno2_old,A1.openbankemp2_old,A1.salarytype_old,A1.salarygrade_old,A1.salarykind_old,A1.salarycity_old,A1.paystatus,A1.salarystatus,A1.paymode,A1.costid,A1.bankid,A1.bankno,A1.openbankemp,A1.bankid2,A1.bankno2,A1.openbankemp2,A1.salarytype,A1.salarygrade,A1.salarykind,A1.salarycity,A1.a1_old,A1.a2_old,A1.a3_old,A1.a4_old,A1.a5_old,A1.a1,A1.a2,A1.a3,A1.a4,A1.a5,A1.reason,A1.effectdate,A1.regby,A1.regdate,A1.initializedby,A1.submitby,A1.calcway,A1.calcway_old,A1.isspecial,A1.corpid,A1.initialized,A1.initializedtime,A1.submit,A1.submittime,A1.remark ";
		String sql2= "(select EID ";
		for(int i=0;i<systpaystditemList.size();i++){

			item = (Systpaystditem)systpaystditemList.get(i);
			colName = item.getColname();
			title = item.getTitle();
			paystdItemID = item.getId();
			sql1 = sql1 + "," +"" + "A2." +title;
			/*if(i!=systpaystditemList.size()-1){
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end  "+title+",";
			}else{
				sql2 = sql2 + " case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;
			}*/
			sql2 = sql2 +","+ " case when paystdItemID = "+paystdItemID +" then xvalue else '' end "+title ;

		}
		long current = page.getCurrent();
		long size = page.getSize();
		sql1 = sql1+" from ctchangesalary_all A1 ";
		sql2 = sql2 +"  from ctstandard_all  Where  corpcode = '" + corpcode +"') A2";
		sql = sql1+" left join "+sql2 +" on A1.EID = A2.EID ";

		sql = sql + " where A1.corpcode ='" + corpcode +"'";
		//ctchangesalaryAll
		Integer depid = ctchangesalaryAll.getDepid();
		Integer jobid = ctchangesalaryAll.getJobid();
		String name = ctchangesalaryAll.getName();
		if(!StringUtils.isEmpty(depid)){
			sql = sql + " where A1.depid ="+depid;
		}
		if(!StringUtils.isEmpty(jobid)){
			sql = sql + " where A1.jobid ="+jobid;
		}
		if(!StringUtils.isEmpty(name)){
			sql = sql + " where A1.name like '%"+name+"%'";
		}
		sql = sql + "  limit "+(current-1)*size+","+size;
		List<LinkedHashMap> list = systpaystditemMapper.listSalaryTemplate(sql);
		IPage resultpage = new Page();
		resultpage.setRecords(list);
		resultpage.setTotal(list.size());
		return  R.ok(resultpage);

	}

/*
    */
/**
     * 修改薪资变动历史
     * @param ctchangesalaryAll 薪资变动历史
     * @return R
     *//*

    @ApiOperation(value = "修改薪资变动历史", notes = "修改薪资变动历史")
    @SysLog("修改薪资变动历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalaryall_edit')" )
    public R updateById(@RequestBody CtchangesalaryAll ctchangesalaryAll) {
        return R.ok(ctchangesalaryAllService.updateById(ctchangesalaryAll));
    }

    */
/**
     * 通过id删除薪资变动历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资变动历史", notes = "通过id删除薪资变动历史")
    @SysLog("通过id删除薪资变动历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctchangesalaryall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctchangesalaryAllService.removeById(id));
    }
*/

}
