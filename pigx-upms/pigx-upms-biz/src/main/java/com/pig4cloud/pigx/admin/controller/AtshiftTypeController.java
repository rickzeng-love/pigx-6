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
import com.pig4cloud.pigx.admin.entity.AtcdAgentmode;
import com.pig4cloud.pigx.admin.entity.CtcdSalarytype;
import com.pig4cloud.pigx.admin.mapper.AtcdAgentmodeUserMapper;
import com.pig4cloud.pigx.admin.mapper.AtshiftTypeMapper;
import com.pig4cloud.pigx.admin.service.AtcdAgentmodeService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftType;
import com.pig4cloud.pigx.admin.service.AtshiftTypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.pig4cloud.pigx.admin.mapper.AtcdAgentmodeUserMapper;

import javax.management.Query;
import java.util.Map;
import java.util.UUID;


/**
 * 班次管理
 *
 * @author GXS
 * @date 2020-05-25 10:52:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshifttype" )
@Api(value = "atshifttype", tags = "班次管理")
public class AtshiftTypeController {

    private final AtshiftTypeService atshiftTypeService;
    private final AtshiftTypeMapper atshiftTypeMapper;
    private final AtcdAgentmodeUserMapper atcdAgentmodeUserMapper;
    private final AtcdAgentmodeService atcdAgentmodeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftType 班次管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtshiftTypePage" )
    public R getAtshiftTypePage(Page page,@RequestBody AtshiftType atshiftType) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftType.setCorpcode(corpCode);
        return R.ok(atshiftTypeService.page(page, Wrappers.query(atshiftType)));
    }

/*	*//**
	 * 查询所有
	 * @param atshiftType
	 * @return
	 *//*
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtshiftType" )
	public R getAllAtshiftType(@RequestBody(required = false) AtshiftType atshiftType){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftType)){
			atshiftType = new AtshiftType();
		}
		atshiftType.setCorpcode(corpCode);
		QueryWrapper<AtshiftType> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpCode);
		if(!StringUtils.isEmpty(atshiftType.getTitle())){
			queryWrapper.like("title",atshiftType.getTitle());
		}

		return R.ok(atshiftTypeService.list(queryWrapper));
	}*/

	/**
	 * 查询有效的班次记录
	 * @param atshiftType
	 * @return
	 */
	@ApiOperation(value = "查询有效记录", notes = "查询有效记录")
	@PostMapping("/getAtshiftTypeByIsDisabled" )
	public R getAtshiftTypeByIsDisabled(AtshiftType atshiftType){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftType.setCorpcode(corpCode);
		atshiftType.setIsdisabled(0);
		//自动生成班次编码
		atshiftType.setShift(UUID.randomUUID().toString());
		return R.ok(atshiftTypeService.list(Wrappers.query(atshiftType)));
	}

	/**
	 * 根据考勤管理员为UserID值的考勤账套关联的班次信息
	 * @return
	 */
	@ApiOperation(value = "根据考勤管理员为UserID值的考勤账套关联的班次信息", notes = "根据考勤管理员为UserID值的考勤账套关联的班次信息")
	@PostMapping("/getAtshiftTypeByUserID" )
	public R getAtshiftTypeByUserID()
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		//获取UserID值
		Integer userid=pigxUser.getId();
		return R.ok(atshiftTypeMapper.listAtshiftTypeByUserId(userid,corpCode));
	}

	/**
	 * 考勤组管理的工作日更改班次的弹出界面班次列表
	 * @param shift
	 * @return
	 */
	@ApiOperation(value = "考勤组管理的工作日更改班次的弹出界面班次列表", notes = "考勤组管理的工作日更改班次的弹出界面班次列表")
	@GetMapping("/getAtshiftTypeSelList/{shift}" )
	public R getAtshiftTypeSelList(@PathVariable("shift" ) String shift){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		return R.ok(atshiftTypeMapper.listAtshiftTypeSelList(shift,corpCode));
	}

	/**
	 * 查询列表记录，包含考勤时间合并显示
	 * @param page
	 * @param atshiftType
	 * @return
	 */
	@ApiOperation(value = "查询列表记录", notes = "查询列表记录")
	@PostMapping("/getAtshiftTypeList" )
	public R getAtshiftTypeList(Page page,@RequestBody(required = false) AtshiftType atshiftType){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftType)){
			atshiftType = new AtshiftType();
		}
		atshiftType.setCorpcode(corpCode);
		return R.ok(atshiftTypeService.getAtshiftTypeList(page,atshiftType));
	}
	/**
	 * 班次查询所有
	 * @param atshiftType
	 * @ctcdSalarytype
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtshiftType" )
	public R getAllAtshiftType(@RequestBody(required = false) AtshiftType atshiftType) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftType)){
			atshiftType = new AtshiftType();
		}
		atshiftType.setCorpcode(corpcode);
		return R.ok(atshiftTypeService.list(Wrappers.query(atshiftType)));
	}

	/**
	 * 根据shift获取班次信息，
	 * @param atshiftType
	 * @return
	 */
	@ApiOperation(value = "根据shift获取班次信息", notes = "根据shift获取班次信息")
	@PostMapping("/getAtshiftTypeByShift" )
	public R getAtshiftTypeByShift(@RequestBody AtshiftType atshiftType){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		QueryWrapper<AtshiftType> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpCode);
		queryWrapper.eq("shift",atshiftType.getShift());
		AtshiftType atshiftType1 = atshiftTypeService.getOne(queryWrapper);
		return R.ok(atshiftType1);
	}

	/**
	 * 获取打卡模式的下拉框数据源
	 * @return
	 */
	@ApiOperation(value = "获取打卡模式的下拉框数据源", notes = "获取打卡模式的下拉框数据源")
	@PostMapping("/getAtshiftTypeTypeList" )
	public R getAtshiftTypeTypeList(){
		return R.ok(atshiftTypeMapper.listAtshiftTypeType());
	}

//	/**
//	 * 新增时获取当前用户默认的考勤账套 转到 AtcdAgentmodeUserController.getAtcdAgentmodeUserByUserID
//	 * @return
//	 */
//	@ApiOperation(value = "新增时获取当前用户默认的考勤账套", notes = "新增时获取当前用户默认的考勤账套")
//	@GetMapping("/getAtshiftTypeAgentMode" )
//	public R getAtshiftTypeAgentMode(){
//		PigxUser pigxUser = SecurityUtils.getUser();
//		String corpcode = pigxUser.getCorpcode();
//		Integer userid=0;
//		//怎么获取UserID的值？未解决...
//		//userid=pigxUser.getUserID();
//		return R.ok(atshiftTypeMapper.listAtshiftTypeAgentMode(userid,corpcode));
//	}

	/**
	 * 修改打卡时间计算工作时数
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "修改打卡时间计算工作时数", notes = "修改打卡时间计算工作时数")
	@PostMapping("/getAtshiftTypeXHours" )
	public R getAtshiftTypeXHours(@RequestBody Map map){
		return R.ok(atshiftTypeMapper.listAtshiftTypeXHours(map));
	}



    /**
     * 通过id查询班次管理
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PostMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atshiftTypeService.getById(id));
    }

    /**
     * 新增班次管理
     * @param atshiftType 班次管理 @PreAuthorize("@pms.hasPermission('admin_atshifttype_add')" )
     * @return R
     */
    @ApiOperation(value = "新增班次", notes = "新增班次")
    @SysLog("新增班次" )
    @PostMapping("/save")
    public R save(@RequestBody AtshiftType atshiftType) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		String corpcode = pigxUser.getCorpcode();
		//获取UserID值
		Integer userid=pigxUser.getId();
		//设置公司ID、公司编码、是否失效、是否确认、考勤账套
		atshiftType.setCorpid(corpid);
		atshiftType.setCorpcode(corpcode);
		atshiftType.setIsdisabled(0);
		atshiftType.setInitialized(1);
		atshiftType.setShift(UUID.randomUUID().toString());
		//判断班次编码是否重复
		QueryWrapper<AtshiftType> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",pigxUser.getCorpcode());
		queryWrapper2.eq("shift",atshiftType.getShift());
		AtshiftType atshiftType2 = atshiftTypeService.getOne(queryWrapper2);
		if(!StringUtils.isEmpty(atshiftType2)){
			return R.ok("班次编码重复！");
		}
		//查询考勤账套号
		QueryWrapper<AtcdAgentmode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",pigxUser.getCorpcode());
		AtcdAgentmode atcdAgentmode = atcdAgentmodeService.getOne(queryWrapper);
		Integer aid = null;
		if(!StringUtils.isEmpty(atcdAgentmode)){
			aid = atcdAgentmode.getId();
		}else{
			return R.failed("请维护考勤账套！");
		}
		atshiftType.setAgentmode(aid);
		//设置考勤账套  由用户选择
		//Map agentmodeMap=null;
		//查询权限
		//agentmodeMap=atcdAgentmodeUserMapper.listAtcdAgentmodeUserByUserID(userid,corpcode);
		//if(agentmodeMap!=null){
		//	atshiftType.setAgentmode((Integer)agentmodeMap.get("AID"));
		//}
    	return R.ok(atshiftTypeService.save(atshiftType));
    }

/*
    */
/**
     * 修改班次管理
     * @param atshiftType 班次管理 @PreAuthorize("@pms.hasPermission('admin_atshifttype_edit')" )
     * @return R
     *//*

    @ApiOperation(value = "修改班次管理", notes = "修改班次管理")
    @SysLog("修改班次管理" )
    @PutMapping
    public R updateById(@RequestBody AtshiftType atshiftType) {
        return R.ok(atshiftTypeService.updateById(atshiftType));
    }

    */
/**
     * 通过id删除班次管理 @PreAuthorize("@pms.hasPermission('admin_atshifttype_del')" )
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除班次管理", notes = "通过id删除班次管理")
    @SysLog("通过id删除班次管理" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atshiftTypeService.removeById(id));
    }
*/

}
