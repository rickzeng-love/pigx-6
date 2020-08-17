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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.api.entity.SysDeptRelation;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.AtcdAgentmodeMapper;
import com.pig4cloud.pigx.admin.service.AtattendPeriodsService;
import com.pig4cloud.pigx.admin.service.AtcdAttendperiodService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.AtcdAgentmodeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 考勤账套管理
 *
 * @author GXS
 * @date 2020-05-25 11:06:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdagentmode" )
@Api(value = "atcdagentmode", tags = "考勤账套管理")
public class AtcdAgentmodeController {

    private final  AtcdAgentmodeService atcdAgentmodeService;
    private final AtcdAttendperiodService atcdAttendperiodService;
    private final AtcdAgentmodeMapper atcdAgentmodeMapper;
    private final AtattendPeriodsService atattendPeriodsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdAgentmode 考勤账套管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdAgentmodePage(Page page, AtcdAgentmode atcdAgentmode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdAgentmode.setCorpcode(corpCode);
        return R.ok(atcdAgentmodeService.page(page, Wrappers.query(atcdAgentmode)));
    }

	/**
	 * 查询所有
	 * @param atcdAgentmode
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtcdAgentmode" )
    public R getAllAtcdAgentmode(AtcdAgentmode atcdAgentmode)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atcdAgentmode)){
			atcdAgentmode = new AtcdAgentmode();
		}
		atcdAgentmode.setCorpcode(corpCode);
		return R.ok(atcdAgentmodeService.list(Wrappers.query(atcdAgentmode)));
	}

	/**
	 * 查询有效的考勤账套记录
	 * @param atcdAgentmode
	 * @return
	 */
	@ApiOperation(value = "查询有效记录", notes = "查询有效记录")
	@GetMapping("/getAtcdAgentmodeByIsDisabled" )
	public R getAtcdAgentmodeByIsDisabled(AtcdAgentmode atcdAgentmode)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdAgentmode.setCorpcode(corpCode);
		atcdAgentmode.setIsdisabled(0);
		return R.ok(atcdAgentmodeService.list(Wrappers.query(atcdAgentmode)));
	}

	/**
	 * 根据考勤管理员为用户ID获取相应的考勤账套选项
	 * @return
	 */
	@ApiOperation(value = "获取用户ID所拥有的考勤账套列表", notes = "获取用户ID所拥有的考勤账套列表")
	@GetMapping("/getAtcdAgentmodeByUserId" )
	public R getAtcdAgentmodeByUserId(){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		//获取UserID值
		Integer userid=pigxUser.getId();
		return R.ok(atcdAgentmodeMapper.listAtcdAgentmodeByUserId(userid,corpCode));
	}


	/**
	 * 考勤账套的查询列表，包含考勤开户时间
	 * @param page
	 * @param atcdAgentmode
	 * @return
	 */
	@ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	@GetMapping("/getAtcdAgentmodeList" )
	public R getAtcdAgentmodeList(Page page, AtcdAgentmode atcdAgentmode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atcdAgentmode.setCorpcode(corpcode);
		return R.ok(atcdAgentmodeService.getAtcdAgentmodeList(page,atcdAgentmode));
	}

	/**
	 * 获取编辑界面数据
	 * @param atcdAgentmode
	 * @return
	 */
	@ApiOperation(value = "获取编辑界面数据", notes = "获取编辑界面数据")
	@PostMapping("/getAtcdAgentmodeInfo" )
	public R getAtcdAgentmodeInfo(AtcdAgentmode atcdAgentmode){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atcdAgentmode.setCorpcode(corpcode);
		return R.ok(atcdAgentmodeMapper.listAtcdAgentmodeInfo(atcdAgentmode));
	}



    /**
     * 通过id查询考勤账套管理
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdAgentmodeService.getById(id));
    }

//	/**
//	 * 根据考勤账套ID值获取对应的考勤期间数据，编辑时显示数据调用
//	 * @param id
//	 * @return
//	 */
//	@ApiOperation(value = "通过id查询", notes = "通过id查询")
//	@GetMapping("/getAtcdAttendperiodById" )
//	public R getAtcdAttendperiodById(@PathVariable("id" ) Integer id){
//		PigxUser pigxUser = SecurityUtils.getUser();
//		String corpCode = pigxUser.getCorpcode();
//		Integer id2=0;
//		AtcdAttendperiod atcdAttendperiod=new AtcdAttendperiod();
//		atcdAttendperiod.setCorpcode(corpCode);
//		List<AtcdAttendperiod> atcdAttendperiodList =atcdAttendperiodService.list(Wrappers.query(atcdAttendperiod).eq("AgentMode",id));
//		AtcdAttendperiod atcdAttendperiod1 = null;
//		if(atcdAttendperiodList.size()>0)
//		{
//			atcdAttendperiod1=atcdAttendperiodList.get(0);
//			id2=atcdAttendperiod1.getId();
//		}
//		return R.ok(atcdAttendperiodService.getById(id2));
//	}

    /**
     * 新增考勤账套管理
     * @param atcdAgentmode 考勤账套管理 @PreAuthorize("@pms.hasPermission('admin_atcdagentmode_add')" )
     * @return R
     */
    @ApiOperation(value = "新增考勤账套管理", notes = "新增考勤账套管理")
    @SysLog("新增考勤账套管理" )
    @PostMapping("/save")
    public R save(@RequestBody AtcdAgentmode atcdAgentmode) {
    	//新增考勤账套记录信息，要更新两张表：薪资账套、账套对应的考勤期间，需要单独写新增保存、修改保存、删除事件未编写代码.....
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		Integer compID=pigxUser.getCompid();
		atcdAgentmode.setCorpid(corpID);
		atcdAgentmode.setCorpcode(corpCode);
		atcdAgentmode.setIsdisabled(0);
		atcdAgentmode.setCompid(compID);

		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpCode);
		List list = atcdAgentmodeService.list(queryWrapper);
		if(list.size()>0){
			return R.failed("免费版本只能添加一个考勤账套！如需多个账套，请联系客服！");
		}

		String currentTime = DateUtils.getTimeString();
		/*
		//生成考勤期间
		QueryWrapper<AtattendProcess> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpCode);
		AtattendProcess atattendProcess  = atattendPeriodsService.getOne(queryWrapper2);
		if(StringUtils.isEmpty(atattendProcess)) {
			AtattendProcess atattendProcess1 = new AtattendProcess();
			atattendProcess1.setCorpcode(corpCode);
			atattendProcess1.setCorpid(pigxUser.getCorpid());
			atattendProcess1.setTerm(currentTime);
			atattendPeriodsService.save(atattendProcess1);
		}
*/

    	return R.ok(atcdAgentmodeService.save(atcdAgentmode));
    }

	/**
	 * 保存考勤账套及其对应的考勤期间数据
	 * @param map @PreAuthorize("@pms.hasPermission('admin_atcdagentmode_add')" )
	 * @return
	 */
	@ApiOperation(value = "新增考勤账套及期间数据", notes = "新增考勤账套及期间数据")
	@SysLog("新增考勤账套及期间数据" )
	@PostMapping("/save2")
	@Transactional
	public R save2(@RequestBody Map map){
		//界面上传入字段数据
		String title=(String)map.get("title");
		String remark=(String)map.get("remark");
		Integer startday=(Integer)map.get("startday");

		//操作者相关信息
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		Integer compID=pigxUser.getCompid();
		boolean saveflag=false;

		//保存新增的考勤账套
		AtcdAgentmode atcdAgentmode=new AtcdAgentmode();
		atcdAgentmode.setCorpid(corpID);
		atcdAgentmode.setCorpcode(corpCode);
		atcdAgentmode.setIsdisabled(0);
		atcdAgentmode.setCompid(compID);
		atcdAgentmode.setTitle(title);
		atcdAgentmode.setRemark(remark);
		saveflag=atcdAgentmodeService.save(atcdAgentmode);
		if(saveflag)
		{
			//获取考勤账套的ID
			Integer agentMode=atcdAgentmode.getId();

			//保存新增的考勤期间
			AtcdAttendperiod atcdAttendperiod=new AtcdAttendperiod();
			atcdAttendperiod.setCorpid(corpID);
			atcdAttendperiod.setCorpcode(corpCode);
			atcdAttendperiod.setTitle("考勤周期(本月第几天)");
			atcdAttendperiod.setMonflag(0);
			atcdAttendperiod.setAgentmode(agentMode);
			atcdAttendperiod.setStartday(startday);
			return R.ok(atcdAttendperiodService.save(atcdAttendperiod));
		}
		else
		{
			return R.ok(null,"新增保存失败!");
		}
	}

    /**
     * 修改考勤账套管理
     * @param atcdAgentmode 考勤账套管理 @PreAuthorize("@pms.hasPermission('admin_atcdagentmode_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改考勤账套", notes = "修改考勤账套")
    @SysLog("修改考勤账套" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody AtcdAgentmode atcdAgentmode) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		UpdateWrapper<AtcdAgentmode> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpCode);
		updateWrapper.eq("id",atcdAgentmode.getId());
        return R.ok(atcdAgentmodeService.update(atcdAgentmode,updateWrapper));
    }

	/**
	 * 修改考勤账套及对应的考勤期单数据
	 * @param map 考勤账套管理 @PreAuthorize("@pms.hasPermission('admin_atcdagentmode_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "修改考勤账套管理", notes = "修改考勤账套管理")
	@SysLog("修改考勤账套管理" )
	@PutMapping("/updateById2")
	@Transactional
	public R updateById2(@RequestBody Map map, BindingResult results) {
		//界面上传入字段数据
		String title=(String)map.get("title");
		String remark=(String)map.get("remark");
		Integer startday=(Integer)map.get("startday");
		//获取考勤账套ID、考勤期间的ID
		Integer id=(Integer)map.get("id");
		Integer id2=(Integer)map.get("id2");

		boolean saveflag=false;

		//获取考勤账套的数据并更新
		AtcdAgentmode atcdAgentmode=atcdAgentmodeService.getById(id);
		atcdAgentmode.setTitle(title);
		atcdAgentmode.setRemark(remark);
		saveflag=atcdAgentmodeService.updateById(atcdAgentmode);
		if(saveflag)
		{
			AtcdAttendperiod atcdAttendperiod=atcdAttendperiodService.getById(id2);
			atcdAttendperiod.setStartday(startday);
			return R.ok(atcdAttendperiodService.updateById(atcdAttendperiod));
		}
		else
		{
			return R.ok(null,"修改保存失败!");
		}
	}

/*
    */
/**
     * 通过id删除考勤账套管理
     * @param id id @PreAuthorize("@pms.hasPermission('admin_atcdagentmode_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤账套管理", notes = "通过id删除考勤账套管理")
    @SysLog("通过id删除考勤账套管理" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
    	return R.ok(atcdAgentmodeService.removeById(id));
    }
*/

	/**
	 * 通过id删除考勤账套、考勤期期间数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id删除考勤账套/考勤期间", notes = "通过id删除考勤账套/考勤期间")
	@SysLog("通过id删除考勤账套管理" )
	@DeleteMapping("/removeById2/{id}" )
	@Transactional
    public R removeById2(@PathVariable Integer id, BindingResult results) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		boolean saveflag=false;

		//删除考勤期间
		AtcdAttendperiod atcdAttendperiod=new AtcdAttendperiod();
		atcdAttendperiod.setCorpcode(corpCode);
		List<Integer> idList = atcdAttendperiodService.list(Wrappers.query(atcdAttendperiod).eq("AgentMode",atcdAttendperiod.getAgentmode()))
				.stream().map(AtcdAttendperiod::getId).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(idList)) {
			saveflag=atcdAttendperiodService.removeByIds(idList);
		}
		else{
			saveflag=true;
		}

		if(saveflag)
		{
			//删除考勤账套
			return R.ok(atcdAgentmodeService.removeById(id));
		}
		else
		{
			return R.ok(null,"删除保存失败!");
		}
	}


}
