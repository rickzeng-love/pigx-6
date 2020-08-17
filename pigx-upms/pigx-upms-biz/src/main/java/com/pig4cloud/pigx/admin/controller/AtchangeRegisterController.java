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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.AtchangeRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.AvwStatusService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.AtchangeRegisterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 考勤变动
 *
 * @author gaoxiao
 * @date 2020-07-07 14:02:17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atchangeregister" )
@Api(value = "atchangeregister", tags = "考勤变动管理")
public class AtchangeRegisterController {

    private final  AtchangeRegisterService atchangeRegisterService;
    private final AtchangeRegisterMapper atchangeRegisterMapper;
    private final SystmessageMapper systmessageMapper;
    private final AvwStatusService avwStatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atchangeRegister 考勤变动
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getAtchangeAllPage" )
    public R getAtchangeAllPage(Page page, @RequestBody(required = false) AtchangeRegister atchangeRegister) {
       if(StringUtils.isEmpty(atchangeRegister)){
		   atchangeRegister = new AtchangeRegister();
	   }
		PigxUser pigxUser = SecurityUtils.getUser();
		atchangeRegister.setCorpcode(pigxUser.getCorpcode());
        return R.ok(atchangeRegisterService.page(page, Wrappers.query(atchangeRegister)));
    }


    /**
     * 通过id查询考勤变动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atchangeRegisterService.getById(id));
    }

    /**
     * 新增考勤变动
     * @param atchangeRegister 考勤变动
     * @return R
     */
    @ApiOperation(value = "新增考勤变动", notes = "新增考勤变动")
    @SysLog("新增考勤变动" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atchangeregister_add')" )
    public R save(@RequestBody AtchangeRegister atchangeRegister) {
        return R.ok(atchangeRegisterService.save(atchangeRegister));
    }


	/**
	 * 考勤变动
	 * @param atchangeRegister
	 * @return R
	 */
	@ApiOperation(value = "考勤变动", notes = "考勤变动")
	@SysLog("考勤变动" )
	@PostMapping("/aSPChangeStart")
	@Transactional
	public R aSPChangeStart(@RequestBody AtchangeRegister atchangeRegister) {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		AvwStatus avwStatus  = avwStatusService.getById(atchangeRegister.getEid());
		BeanUtils.copyProperties(avwStatus,atchangeRegister);
		atchangeRegister.setInitializedtime(currentTime);
		atchangeRegister.setInitializedby(pigxUser.getId());
		atchangeRegister.setInitialized(1);
		atchangeRegister.setSubmit(1);
		atchangeRegister.setSubmitby(pigxUser.getId());
		atchangeRegister.setSubmittime(currentTime);
		atchangeRegister.setAidOld(atchangeRegister.getAid());
		atchangeRegister.setAtstatusOld(atchangeRegister.getAtstatus());
		atchangeRegister.setCardaddridOld(atchangeRegister.getCardid());
		atchangeRegister.setGroupidOld(atchangeRegister.getGroupid());
		atchangeRegister.setLineidOld(atchangeRegister.getLineid());
		atchangeRegister.setTeamidOld(atchangeRegister.getTeamid());
		atchangeRegisterService.save(atchangeRegister);

		//校验变动
		Map map = new HashMap();
		map.put("id",atchangeRegister.getId());
		Map map2 = new HashMap();
		map2.put("id",atchangeRegister.getId());
		String message = null;
		//调用确认的存储过程
		atchangeRegisterMapper.aSPChangeCheck(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			map2.put("userid",pigxUser.getId());
			atchangeRegisterMapper.aSPChangeStart(map2);
			int mesid = (Integer) map2.get("result");
			systmessage = systmessageMapper.selectById(mesid);
			message = systmessage.getTitle();
			if(mesid==0){
				return R.ok(message);
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed(message);
			}

		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			systmessage = systmessageMapper.selectById(msgid);
			if (systmessage != null) {
				message = systmessage.getTitle();
				return R.failed(message);
			}
		}


		return R.ok("操作成功！");
	}

/*
    */
/**
     * 修改考勤变动
     * @param atchangeRegister 考勤变动
     * @return R
     *//*

    @ApiOperation(value = "修改考勤变动", notes = "修改考勤变动")
    @SysLog("修改考勤变动" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atchangeregister_edit')" )
    public R updateById(@RequestBody AtchangeRegister atchangeRegister) {
        return R.ok(atchangeRegisterService.updateById(atchangeRegister));
    }

    */
/**
     * 通过id删除考勤变动
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤变动", notes = "通过id删除考勤变动")
    @SysLog("通过id删除考勤变动" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atchangeregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atchangeRegisterService.removeById(id));
    }
*/

}
