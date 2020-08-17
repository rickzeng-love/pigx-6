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
import com.pig4cloud.pigx.admin.entity.CtchangebenefitRegister;
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.mapper.CtbenefitstatusMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.CtchangebenefitRegisterService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Ctbenefitstatus;
import com.pig4cloud.pigx.admin.service.CtbenefitstatusService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 福利
 *
 * @author XP
 * @date 2020-06-11 15:05:48
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbenefitstatus" )
@Api(value = "ctbenefitstatus", tags = "福利管理")
public class CtbenefitstatusController {

    private final CtbenefitstatusService ctbenefitstatusService;
    private final CtchangebenefitRegisterService ctchangebenefitRegisterService;
    private final CtbenefitstatusMapper ctbenefitstatusMapper;
	private final SystmessageMapper systmessageMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctbenefitstatus 福利
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getCtbenefitstatusPage(Page page, Ctbenefitstatus ctbenefitstatus) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		ctbenefitstatus.setCorpcode(corpcode);
		ctbenefitstatus.setCorpid(corpid);
		String name = ctbenefitstatus.getName();
		int depid = ctbenefitstatus.getDepid();
		int jobid = ctbenefitstatus.getJobid();
		QueryWrapper<Ctbenefitstatus> queryWrapper = new QueryWrapper<>();
		if (name != null && name != "") {
			queryWrapper.like("Name", name);
		}
		if (depid > 0) {
			queryWrapper.or().eq("DepID", depid);
		}
		if (jobid > 0) {
			queryWrapper.or().eq("JobID", jobid);
		}
        return R.ok(ctbenefitstatusService.page(page, queryWrapper));
    }


    /**
     * 通过id查询福利
     * @param eid id
     * @return R
     */
   /* @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{eid}" )
    public R getById(@PathVariable("eid" ) Integer eid) {
        return R.ok(ctbenefitstatusService.getById(eid));
    }
*/
    /**
     * 新增福利
     * @param ctbenefitstatus 福利
     * @return R
     */
/*    @ApiOperation(value = "新增福利", notes = "新增福利")
    @SysLog("新增福利" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctbenefitstatus_add')" )
    public R save(@RequestBody Ctbenefitstatus ctbenefitstatus) {
        return R.ok(ctbenefitstatusService.save(ctbenefitstatus));
    }*/

    /**
     * 修改福利
     * @param ctchangebenefitRegister 福利
	 * @PreAuthorize("@pms.hasPermission('admin_ctbenefitstatus_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改福利", notes = "修改福利")
    @SysLog("修改福利" )
    @PostMapping("/update")
	@Transactional
    public R updateById(@RequestBody CtchangebenefitRegister ctchangebenefitRegister) {
		String message = null;
		Ctbenefitstatus ctbenefitstatus = ctbenefitstatusService.getById(ctchangebenefitRegister.getEid());
		ctchangebenefitRegister.setBenstatusOld(ctbenefitstatus.getBenstatus());
		ctchangebenefitRegister.setBencityOld(ctbenefitstatus.getBencity());
		ctchangebenefitRegister.setBenareaOld(ctbenefitstatus.getBenarea());
		ctchangebenefitRegister.setBeartypeOld(ctbenefitstatus.getBeartype());
		ctchangebenefitRegister.setBenbaseOld(ctbenefitstatus.getBenbase());
		//保存数据到登记表
		ctchangebenefitRegisterService.save(ctchangebenefitRegister);
		int id = ctchangebenefitRegister.getId();

		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		Map paramMap = new HashMap();
		paramMap.put("userid", userid);
		paramMap.put("id", id);
		//调用确认的存储过程
		Map msgMap = ctbenefitstatusMapper.cSP_ChangeBenefitCheck(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			Map mesMap = ctbenefitstatusMapper.cSP_ChangeBenefitStart(paramMap);
			int mesid = (Integer) mesMap.get("result");
			systmessage = systmessageMapper.selectById(mesid);
			message = systmessage.getTitle();
			return R.ok(message);
		} else {
			systmessage = systmessageMapper.selectById(msgid);
			if (systmessage != null) {
				message = systmessage.getTitle();
				return R.ok(message);
			}
		}
        return R.ok(message);
    }

    /**
     * 通过id删除福利
     * @param eid id
     * @return R
     */
 /*   @ApiOperation(value = "通过id删除福利", notes = "通过id删除福利")
    @SysLog("通过id删除福利" )
    @DeleteMapping("/{eid}" )
    @PreAuthorize("@pms.hasPermission('admin_ctbenefitstatus_del')" )
    public R removeById(@PathVariable Integer eid) {
        return R.ok(ctbenefitstatusService.removeById(eid));
    }*/

}
