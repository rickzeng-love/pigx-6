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
import com.pig4cloud.pigx.admin.mapper.AtshiftsupplyWorkAllMapper;
import com.pig4cloud.pigx.admin.util.ExportExcelUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftsupplyWorkAll;
import com.pig4cloud.pigx.admin.service.AtshiftsupplyWorkAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 调班记录
 *
 * @author GXS
 * @date 2020-05-27 16:52:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftsupplyworkall" )
@Api(value = "atshiftsupplyworkall", tags = "调班记录管理")
public class AtshiftsupplyWorkAllController {

    private final  AtshiftsupplyWorkAllService atshiftsupplyWorkAllService;
    private final AtshiftsupplyWorkAllMapper atshiftsupplyWorkAllMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftsupplyWorkAll 调班记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftsupplyWorkAllPage(Page page, AtshiftsupplyWorkAll atshiftsupplyWorkAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftsupplyWorkAll.setCorpcode(corpCode);
        return R.ok(atshiftsupplyWorkAllService.page(page, Wrappers.query(atshiftsupplyWorkAll)));
    }

	/**
	 * 查询所有
	 * @param atshiftsupplyWorkAll
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllAtshiftsupplyWorkAll" )
    public R getAllAtshiftsupplyWorkAll(AtshiftsupplyWorkAll atshiftsupplyWorkAll)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftsupplyWorkAll.setCorpcode(corpCode);
		return R.ok(atshiftsupplyWorkAllService.list(Wrappers.query(atshiftsupplyWorkAll)));
	}

	/**
	 * 调班记录的查询列表，包含转换成中文
	 * @param page
	 * @param atshiftsupplyWorkAll
	 * @return
	 */
	@ApiOperation(value = "调班记录的查询列表", notes = "调班记录的查询列表")
	@GetMapping("/getAtshiftsupplyWorkAllList" )
	public R getAtshiftsupplyWorkAllList(Page page, AtshiftsupplyWorkAll atshiftsupplyWorkAll){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atshiftsupplyWorkAll.setCorpcode(corpcode);
		return R.ok(atshiftsupplyWorkAllService.getAtshiftsupplyWorkAllList(page,atshiftsupplyWorkAll));
	}

	/**
	 * 导出调班记录
	 * @param response
	 * @param request
	 */
	@ApiOperation(value = "导出调班记录", notes = "导出调班记录")
	@RequestMapping(value = "/exportAtshiftsupplyWorkAllList",method = RequestMethod.GET)
	public void exportAtshiftsupplyWorkAllList(HttpServletResponse response, HttpServletRequest request){
		//获取查询条件
		String name=request.getParameter("name");
		String term=request.getParameter("term");
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		//得到所有要导出的数据
		List<AtshiftsupplyWorkAll> dataList=atshiftsupplyWorkAllMapper.ExportAtshiftsupplyWorkAll(term,name,corpcode);

		//定义导出的excel名字
		String excelName = "调班记录列表";

		//获取需要转出的excel表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		//fieldMap.put("id","编号");
		//fieldMap.put("Term","月份");
		fieldMap.put("Name","姓名");
		fieldMap.put("Badge","工号");
		fieldMap.put("DepName","部门");
		fieldMap.put("JobName","岗位");
		fieldMap.put("GroupName","考勤组");
		fieldMap.put("S1Name","第1天");
		fieldMap.put("S2Name","第2天");
		fieldMap.put("S3Name","第3天");
		fieldMap.put("S4Name","第4天");
		fieldMap.put("S5Name","第5天");
		fieldMap.put("S6Name","第6天");
		fieldMap.put("S7Name","第7天");
		fieldMap.put("S8Name","第8天");
		fieldMap.put("S9Name","第9天");
		fieldMap.put("S10Name","第10天");
		fieldMap.put("S11Name","第11天");
		fieldMap.put("S12Name","第12天");
		fieldMap.put("S13Name","第13天");
		fieldMap.put("S14Name","第14天");
		fieldMap.put("S15Name","第15天");
		fieldMap.put("S16Name","第16天");
		fieldMap.put("S17Name","第17天");
		fieldMap.put("S18Name","第18天");
		fieldMap.put("S19Name","第19天");
		fieldMap.put("S20Name","第20天");
		fieldMap.put("S21Name","第21天");
		fieldMap.put("S22Name","第22天");
		fieldMap.put("S23Name","第23天");
		fieldMap.put("S24Name","第24天");
		fieldMap.put("S25Name","第25天");
		fieldMap.put("S26Name","第26天");
		fieldMap.put("S27Name","第27天");
		fieldMap.put("S28Name","第28天");
		fieldMap.put("S29Name","第29天");
		fieldMap.put("S30Name","第30天");
		fieldMap.put("S31Name","第31天");

		//导出相关信息
		new ExportExcelUtils().export(excelName,dataList,fieldMap,response);
	}

    /**
     * 通过id查询调班记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atshiftsupplyWorkAllService.getById(id));
    }

    /**
     * 新增调班记录
     * @param atshiftsupplyWorkAll 调班记录 @PreAuthorize("@pms.hasPermission('admin_atshiftsupplyworkall_add')" )
     * @return R
     */
    @ApiOperation(value = "新增调班记录", notes = "新增调班记录")
    @SysLog("新增调班记录" )
    @PostMapping
    public R save(@RequestBody AtshiftsupplyWorkAll atshiftsupplyWorkAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		atshiftsupplyWorkAll.setCorpid(corpID);
		atshiftsupplyWorkAll.setCorpcode(corpCode);
        return R.ok(atshiftsupplyWorkAllService.save(atshiftsupplyWorkAll));
    }

/*
    */
/**
     * 修改调班记录
     * @param atshiftsupplyWorkAll 调班记录 @PreAuthorize("@pms.hasPermission('admin_atshiftsupplyworkall_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改调班记录", notes = "修改调班记录")
    @SysLog("修改调班记录" )
    @PutMapping
    public R updateById(@RequestBody AtshiftsupplyWorkAll atshiftsupplyWorkAll) {
        return R.ok(atshiftsupplyWorkAllService.updateById(atshiftsupplyWorkAll));
    }

    */
/**
     * 通过id删除调班记录
     * @param id id @PreAuthorize("@pms.hasPermission('admin_atshiftsupplyworkall_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除调班记录", notes = "通过id删除调班记录")
    @SysLog("通过id删除调班记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atshiftsupplyWorkAllService.removeById(id));
    }
*/


}
