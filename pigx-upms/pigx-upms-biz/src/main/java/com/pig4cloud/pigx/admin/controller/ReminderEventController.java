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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.OtcdPositiongrade;
import com.pig4cloud.pigx.admin.entity.QywxWorkitemApproval;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.mapper.ReminderEventMapper;
import com.pig4cloud.pigx.admin.service.QywxWorkitemApprovalService;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.ReminderEvent;
import com.pig4cloud.pigx.admin.service.ReminderEventService;
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
 * 代办提醒
 *
 * @author gaoxiao
 * @date 2020-07-09 08:47:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/reminderevent" )
@Api(value = "reminderevent", tags = "代办提醒管理")
public class ReminderEventController {

    private final  ReminderEventService reminderEventService;
    private final ReminderEventMapper reminderEventMapper;
    private final EtemployeeMapper etemployeeMapper;
    private final QywxWorkitemApprovalService qywxWorkitemApprovalService;
    private final SystcorpinfoService systcorpinfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param reminderEvent 代办提醒
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getReminderEventPage(Page page, ReminderEvent reminderEvent) {
        return R.ok(reminderEventService.page(page, Wrappers.query(reminderEvent)));
    }


	/**
	 * 提醒事项
	 * @return
	 */
	@ApiOperation(value = "提醒事项", notes = "提醒事项")
	@PostMapping("/getReminderEventForDaiBan" )
	public R getReminderEventForDaiBan() {
		ReminderEvent reminderEvent = null;
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		QueryWrapper<ReminderEvent> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode	);
		List<ReminderEvent> reminderEventList = null;
		reminderEventList = reminderEventService.list(queryWrapper);
		String code = "";
		Integer type  = null;
		Integer reminderday = null;
		Integer isdisabled = null;
/*		01	入职
		02	生日
		03	入职周年
		04	转正
		05	离职
		06	试用到期
		07	实习到期
		08	合同到期
		09	即将退休*/
		List list01 = null;
		List list02 = null;
		List list03 = null;
		List list04 = null;
		List list05 = null;
		List list06 = null;
		List list07 = null;
		List list08 = null;
		List list09 = null;
		List list10 = null;
		for(int i=0;i<reminderEventList.size();i++){
			reminderEvent = reminderEventList.get(i);
			code = reminderEvent.getCode();
			type = reminderEvent.getType();
			reminderday = reminderEvent.getReminderday();
			isdisabled = reminderEvent.getIsdisabled();
			if("01".equals(code) && isdisabled==0){
				list01 = etemployeeMapper.listReminderEvent01(reminderEvent);
			}else if("02".equals(code) && isdisabled==0){
				list02 = etemployeeMapper.listReminderEvent02(reminderEvent);
			}else if("03".equals(code) && isdisabled==0){
				list03 = etemployeeMapper.listReminderEvent03(reminderEvent);
			}else if("04".equals(code) && isdisabled==0){
				list04 = etemployeeMapper.listReminderEvent04(reminderEvent);
			}else if("05".equals(code) && isdisabled==0){
				list05 = etemployeeMapper.listReminderEvent05(reminderEvent);
			}else if("06".equals(code) && isdisabled==0 ){
				list06 = etemployeeMapper.listReminderEvent06(reminderEvent);
			}else if("07".equals(code) && isdisabled==0){
				list07 = etemployeeMapper.listReminderEvent07(reminderEvent);
			}else if("08".equals(code) && isdisabled==0){
				list08 = etemployeeMapper.listReminderEvent08(reminderEvent);
			}else if("09".equals(code) && isdisabled==0){
				list09 = etemployeeMapper.listReminderEvent09(reminderEvent);
			}

		}
		List<Integer> listAll = new ArrayList<Integer>();
		if(!StringUtils.isEmpty(list01)){
			listAll.addAll(list01);
		}
		if(!StringUtils.isEmpty(list03)){
			listAll.addAll(list03);
		}
		if(!StringUtils.isEmpty(list04)){
			listAll.addAll(list04);
		}
		if(!StringUtils.isEmpty(list05)){
			listAll.addAll(list05);
		}
		if(!StringUtils.isEmpty(list06)){
			listAll.addAll(list06);
		}
		if(!StringUtils.isEmpty(list07)){
			listAll.addAll(list07);
		}
		if(!StringUtils.isEmpty(list08)){
			listAll.addAll(list08);
		}
		if(!StringUtils.isEmpty(list09)){
			listAll.addAll(list09);
		}
		QueryWrapper<Systcorpinfo> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper1);

		QueryWrapper<QywxWorkitemApproval> queryWrapper2 = new QueryWrapper<>();

		String qywx_corpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywx_corpid = systcorpinfo.getQywxCorpid();
			queryWrapper2.eq("QYWXCorpid", qywx_corpid);
		}else{
			return R.failed("请维护企业微信corpid！");
		}
		queryWrapper2.eq("itemUserId", pigxUser.getId()).eq("itemStatus", 1);
		List qywxworkitemapprovalList = qywxWorkitemApprovalService.list(queryWrapper2);
		Map map = new HashMap();
		map.put("renshi",listAll);
		map.put("daiban",qywxworkitemapprovalList);
		map.put("shengri",list02);
		Integer shuliang = 0;
		if(!StringUtils.isEmpty(listAll)){
			shuliang = shuliang + listAll.size();
		}
		if(!StringUtils.isEmpty(list02)){
			shuliang = shuliang + list02.size();
		}
		if(!StringUtils.isEmpty(qywxworkitemapprovalList)){
			shuliang = shuliang + qywxworkitemapprovalList.size();
		}
		map.put("shuliang",shuliang);
		return R.ok(map);
	}


	/**
	 * 查询所有
	 * @param reminderEvent
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllReminderEvent" )
	public R getAllReminderEvent( ReminderEvent reminderEvent) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		reminderEvent.setCorpcode(corpcode);
		//reminderEvent.setIsdisabled(0);
		return R.ok(reminderEventService.list(Wrappers.query(reminderEvent)));
	}
		/**
		 * 通过id查询代办提醒
		 * @param id id
		 * @return R
		 */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(reminderEventService.getById(id));
    }

    /**
     * 新增代办提醒      @PreAuthorize("@pms.hasPermission('admin_reminderevent_add')" )
     * @param reminderEvent 代办提醒
     * @return R
     */
    @ApiOperation(value = "新增代办提醒", notes = "新增代办提醒")
    @SysLog("新增代办提醒" )
    @PostMapping("/save")
    public R save(@RequestBody ReminderEvent reminderEvent) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		reminderEvent.setCorpcode(corpcode);
		reminderEvent.setCorpid(pigxUser.getCorpid());
        return R.ok(reminderEventService.save(reminderEvent));
    }

    /**
     * 修改代办提醒     @PreAuthorize("@pms.hasPermission('admin_reminderevent_edit')" )
     * @param reminderEvent 代办提醒
     * @return R
     */
    @ApiOperation(value = "修改代办提醒", notes = "修改代办提醒")
    @SysLog("修改代办提醒" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody ReminderEvent reminderEvent) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		UpdateWrapper<ReminderEvent> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("code",reminderEvent.getCode());
        return R.ok(reminderEventService.update(reminderEvent,updateWrapper));
    }
/*

    */
/**
     * 通过id删除代办提醒
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除代办提醒", notes = "通过id删除代办提醒")
    @SysLog("通过id删除代办提醒" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_reminderevent_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(reminderEventService.removeById(id));
    }
*/

}
