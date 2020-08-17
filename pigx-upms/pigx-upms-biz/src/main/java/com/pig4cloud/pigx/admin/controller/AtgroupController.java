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

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.util.StringUtil;
import com.pig4cloud.pigx.admin.entity.AtgroupShift;
import com.pig4cloud.pigx.admin.entity.AtgroupTurn;
import com.pig4cloud.pigx.admin.mapper.AtgroupMapper;
import com.pig4cloud.pigx.admin.mapper.AtgroupShiftMapper;
import com.pig4cloud.pigx.admin.service.AtgroupShiftService;
import com.pig4cloud.pigx.admin.service.AtgroupTurnService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Atgroup;
import com.pig4cloud.pigx.admin.service.AtgroupService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 轮班组管理
 *
 * @author GXS
 * @date 2020-05-25 10:52:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atgroup" )
@Api(value = "atgroup", tags = "轮班组管理")
public class AtgroupController {

    private final  AtgroupService atgroupService;
    private final AtgroupMapper atgroupMapper;
    private final AtgroupShiftMapper atgroupShiftMapper;
    private final AtgroupShiftService atgroupShiftService;
    private final AtgroupTurnService atgroupTurnService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atgroup 轮班组管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtgroupPage(Page page, Atgroup atgroup) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atgroup.setCorpcode(corpCode);
        return R.ok(atgroupService.page(page, Wrappers.query(atgroup)));
    }

	/**
	 * 查询所有
	 * @param atgroup
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtgroup" )
    public R getAllAtgroup(@RequestBody(required=false)  Atgroup atgroup){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atgroup)){
			atgroup = new Atgroup();
		}
		atgroup.setCorpcode(corpCode);

		return R.ok(atgroupService.list(Wrappers.query(atgroup)));
	}


	/**
	 * 查询列表记录，包含考勤时间合并显示
	 * @param page
	 * @param atgroup
	 * @return
	 */
	@ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	@PostMapping("/getAtgroupList" )
	public R getAtgroupList(Page page,@RequestBody(required=false) Atgroup atgroup){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atgroup)){
			atgroup = new Atgroup();
		}
		atgroup.setCorpcode(corpCode);
		return R.ok(atgroupMapper.selectGroup(atgroup));
	}

	/**
	 * 获取编辑界面的考勤组、工作日对应的数据
	 * @param atgroup
	 * @return
	 */
	@ApiOperation(value = "获取编辑界面数据", notes = "获取编辑界面数据")
	@PostMapping("/getAtgroupInfo" )
	public R getAtgroupInfo(Atgroup atgroup){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atgroup.setCorpcode(corpcode);
		Integer groupid = atgroup.getGroupid();
		Map map=null;

		//获取考勤组信息(单条记录)
		Atgroup atgroup1=atgroupService.getById(groupid);
		//map.put("group",atgroup1);
		//实体对象转换成Json 实体对象转Json字符串
		map.put("group",JSONObject.toJSONString(atgroup1));

		//获取考勤组对应的工作日信息(多条记录)
		List<Map> mxlist=atgroupShiftMapper.listAtgroupWordDayInfo(groupid,corpcode);
		//map.put("groupshift",mxlist);
		//实体对象转换成Json 实体列表对象转Json字符串
		map.put("groupshift",JSONObject.toJSONString(mxlist));

		return R.ok(map);
	}


    /**
     * 通过id查询轮班组管理
     * @param groupid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{groupid}" )
    public R getById(@PathVariable("groupid" ) Integer groupid) {
        return R.ok(atgroupService.getById(groupid));
    }


    //以下相关的编辑界面数据获取、保存、修改、删除的事件需要重新编写 20200526 未编写

    /**
     * 新增轮班组管理
     * @param atgroup 轮班组管理 @PreAuthorize("@pms.hasPermission('admin_atgroup_add')" )
     * @return R
     */
    @ApiOperation(value = "新增轮班组", notes = "新增轮班组")
    @SysLog("新增轮班组" )
    @PostMapping("/save")
	@Transactional
    public R save(@RequestBody Atgroup atgroup) {
    	//保存轮班组和轮班组-轮班定义
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		atgroup.setCorpid(corpid);
		atgroup.setCorpcode(corpcode);
		//默认值:参照点(第1次)、轮班周期、周期、间隔、日历类型、排班策略
		atgroup.setReferpoint(0);
		atgroup.setTurndaytype("3");
		atgroup.setPeriods(0);
		atgroup.setInterval(1);
		atgroup.setXcalendar(0);
		//atgroup.setShiftmode(2);
		atgroupService.save(atgroup);
		List<AtgroupTurn> list = atgroup.getAtgroupTurnList();
		AtgroupTurn atgroupTurn = null;
		for(int i=0;i<list.size();i++){
			atgroupTurn = list.get(i);
			atgroupTurn.setGroupid(atgroup.getGroupid());
			atgroupTurn.setCorpcode(corpcode);
			atgroupTurn.setCorpid(pigxUser.getCorpid());
			atgroupTurn.setInitialized(1);
			atgroupTurn.setIscheck(1);
			atgroupTurn.setReferpoint(1);
			atgroupTurnService.save(atgroupTurn);
		}

    	return R.ok("保存成功！");
    }

	/**
	 * 新增保存考勤组/工作日数据
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "新增考勤组", notes = "新增考勤组")
	@SysLog("新增考勤组" )
	@PostMapping("/saveAtgroup")
	@Transactional
	public R saveAtgroup(@RequestBody Map map){
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		Atgroup atgroup=new Atgroup();
		Integer groupid=0;
		Boolean saveFlag=false;


		//保存考勤组数据
		//List<Atgroup> atgroupList=(List<Atgroup>) map.get("group");
		//json字符串转List
		List<Atgroup> atgroupList= JSONArray.parseArray((String)map.get("group"),Atgroup.class);
		if(atgroupList.size()>0)
		{
			atgroup=(Atgroup)atgroupList.get(0);
		}
		else
		{
			return R.ok(null,"未接收考勤组的相关数据!");
		}
		atgroup.setCorpid(corpid);
		atgroup.setCorpcode(corpcode);
		//默认值:参照点(第1次)、轮班周期、周期、间隔、日历类型、排班策略
		atgroup.setReferpoint(0);
		atgroup.setTurndaytype("3");
		atgroup.setPeriods(0);
		atgroup.setInterval(1);
		atgroup.setXcalendar(0);
		atgroup.setShiftmode(2);
		saveFlag=atgroupService.save(atgroup);
		//获取考勤组ID
		groupid=atgroup.getGroupid();

		//保存工作日数据
		if(saveFlag)
		{
			//List<AtgroupShift> atgroupShiftList =(List<AtgroupShift>) map.get("groupshift");
			List<AtgroupShift> atgroupShiftList =JSONArray.parseArray((String)map.get("groupshift"),AtgroupShift.class);
			List<AtgroupShift> resultList = new ArrayList<AtgroupShift>(atgroupShiftList.size());
			for(AtgroupShift gshift:atgroupShiftList)
			{
				gshift.setCorpid(corpid);
				gshift.setCorpcode(corpcode);
				gshift.setGroupid(groupid);
				resultList.add(gshift);
			}
			return R.ok(atgroupShiftService.saveBatch(resultList));
		}
		else
		{
			return R.ok(null,"新增考勤组失败!");
		}
	}
/*

    */
/**
     * 修改轮班组管理
     * @param atgroup 轮班组管理 @PreAuthorize("@pms.hasPermission('admin_atgroup_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改轮班组", notes = "修改轮班组")
    @SysLog("修改轮班组" )
    @PutMapping
    public R updateById(@RequestBody Atgroup atgroup) {
        return R.ok(atgroupService.updateById(atgroup));
    }
*/

	/**
	 * 修改保存考勤组/工作日的数据
	 * @param map
	 * @param results
	 * @return
	 */
	@ApiOperation(value = "修改保存考勤组/工作日的数据", notes = "修改保存考勤组/工作日的数据")
	@SysLog("修改保存考勤组/工作日的数据" )
	@PutMapping("/updateById2")
	@Transactional
	public R updateById2(@RequestBody Map map, BindingResult results) {
		List<Atgroup> atgroupList= JSONArray.parseArray((String)map.get("group"),Atgroup.class);
		List<AtgroupShift> atgroupShiftList =JSONArray.parseArray((String)map.get("groupshift"),AtgroupShift.class);
		Boolean saveFlag=false;

		//修改保存考勤组数据
		saveFlag=atgroupService.updateBatchById(atgroupList);
		if (saveFlag)
		{
			//修改保存工作日数据
			return R.ok(atgroupShiftService.updateBatchById(atgroupShiftList));
		}
		else
		{
			return R.ok(null,"修改保存考勤组数据失败!");
		}
	}

   /* *//**
     * 通过id删除轮班组管理
     * @param groupid id @PreAuthorize("@pms.hasPermission('admin_atgroup_del')" )
     * @return R
     *//*
    @ApiOperation(value = "通过id删除轮班组管理", notes = "通过id删除轮班组管理")
    @SysLog("通过id删除轮班组管理" )
    @DeleteMapping("/{groupid}" )
    public R removeById(@PathVariable Integer groupid) {
        return R.ok(atgroupService.removeById(groupid));
    }

*/
	/**
	 * 通过id删除轮班组/工作日数据
	 * @param groupid id @PreAuthorize("@pms.hasPermission('admin_atgroup_del')" )
	 * @return R
	 */
	@ApiOperation(value = "通过id删除轮班组/工作日数据", notes = "通过id删除轮班组/工作日数据")
	@SysLog("通过id删除轮班组管理" )
	@DeleteMapping("/removeById2/{groupid}" )
	@Transactional
	public R removeById2(@PathVariable Integer groupid, BindingResult results) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		boolean saveflag=false;

		//判断是否存在员工绑定考勤组的情况
		Integer count=(Integer) atgroupMapper.listAtgroupCount(groupid,corpCode).get("count");
		if (count>0)
		{
			return R.ok(null,"存在员工绑定此考勤组,不可删除!");
		}

		//删除工作日的数据
		AtgroupShift atgroupShift=new AtgroupShift();
		atgroupShift.setCorpcode(corpCode);
		List<Integer> idList = atgroupShiftService.list(Wrappers.query(atgroupShift).eq("groupid",groupid))
							.stream().map(AtgroupShift::getId).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(idList))
		{
			saveflag=atgroupShiftService.removeByIds(idList);
		}
		else{
			saveflag=true;
		}

		if(saveflag)
		{
			//删除考勤组数据
			return R.ok(atgroupService.removeById(groupid));
		}
		else
		{
			return R.ok(null,"删除保存失败!");
		}

	}

}
