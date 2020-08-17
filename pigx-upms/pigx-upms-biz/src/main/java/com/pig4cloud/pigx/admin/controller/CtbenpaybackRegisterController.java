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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.CtbenpaybackRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.CtbenpaybackRegisterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 福利补缴登记
 *
 * @author gaoxiao
 * @date 2020-06-13 10:35:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctbenpaybackregister" )
@Api(value = "ctbenpaybackregister", tags = "福利补缴登记管理")
public class CtbenpaybackRegisterController {

    private final  CtbenpaybackRegisterService ctbenpaybackRegisterService;
    private final CtbenpaybackRegisterMapper ctbenpaybackRegisterMapper;
    private final SystmessageMapper systmessageMapper;
    private final EtemployeeService etemployeeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ctbenpaybackRegister 福利补缴登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getCtbenpaybackRegisterPage" )
    public R getCtbenpaybackRegisterPage(Page page, @RequestBody(required = false) CtbenpaybackRegister ctbenpaybackRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer corpid = pigxUser.getCorpid();
		if(StringUtils.isEmpty(ctbenpaybackRegister)){
			ctbenpaybackRegister = new CtbenpaybackRegister();
		}
		ctbenpaybackRegister.setCorpcode(corpcode);
		ctbenpaybackRegister.setCorpid(corpid);
		String name = ctbenpaybackRegister.getName();
		Integer depid = ctbenpaybackRegister.getDepid();
		Integer jobid = ctbenpaybackRegister.getJobid();
		QueryWrapper<CtbenpaybackRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		if (!StringUtils.isEmpty(name)) {
			queryWrapper.like("Name", name);
		}
		if (!StringUtils.isEmpty(depid)) {
			queryWrapper.eq("DepID", depid);
		}
		if (!StringUtils.isEmpty(jobid)) {
			queryWrapper.eq("JobID", jobid);
		}
        return R.ok(ctbenpaybackRegisterService.page(page, queryWrapper));
    }


    /**
     * 通过id查询福利补缴登记
     * @param id id
     * @return R
     */
   /* @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctbenpaybackRegisterService.getById(id));
    }*/

    /**
     * 新增福利补缴登记
	 * @PreAuthorize("@pms.hasPermission('admin_ctbenpaybackregister_add')" )
     * @return R
     */
    @ApiOperation(value = "福利补缴登记选择员工", notes = "福利补缴登记选择员工")
    @SysLog("新增福利补缴登记" )
    @PostMapping("/chosseEtemployee")
	@Transactional
    public R chosseEtemployee(@RequestBody Map map) {
    	String eids = map.get("eids").toString();
    	String[] eidArray = null;
    	Integer eid = null;
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
    	if(StringUtils.isEmpty(eids)){
    		return R.failed("请选择员工！");
		}else{
			eidArray = eids.split(",");
		}
		CtbenpaybackRegister ctbenpaybackRegister = null;
    	for(int i=0;i<eidArray.length;i++){
			eid = Integer.parseInt(eidArray[i]);
			ctbenpaybackRegister = new CtbenpaybackRegister();

			Etemployee etemployee =etemployeeService.getById(eid);
			BeanUtils.copyProperties(etemployee,ctbenpaybackRegister);
			ctbenpaybackRegister.setRegby(pigxUser.getId());
			ctbenpaybackRegister.setRegdate(currentTime);
			ctbenpaybackRegisterService.save(ctbenpaybackRegister);
		}
        return R.ok("保存成功！");
    }

/*
    */
/**
     * 修改福利补缴登记
     * @param ctbenpaybackRegister 福利补缴登记
	 * @PreAuthorize("@pms.hasPermission('admin_ctbenpaybackregister_edit')" )
     * @return R
     */

    @ApiOperation(value = "修改福利补缴登记", notes = "修改福利补缴登记")
    @SysLog("修改福利补缴登记" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody CtbenpaybackRegister ctbenpaybackRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		String currentTime = DateUtils.getTimeString();
		UpdateWrapper<CtbenpaybackRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id",ctbenpaybackRegister.getId());
		updateWrapper.eq("corpcode",corpcode);
		if(!StringUtils.isEmpty(ctbenpaybackRegister.getInitialized())){
			ctbenpaybackRegister.setInitializedby(pigxUser.getId());
			ctbenpaybackRegister.setInitializedtime(currentTime);
		}
        return R.ok(ctbenpaybackRegisterService.update(ctbenpaybackRegister,updateWrapper));
    }


/**
     * 通过id删除福利补缴登记
     * @param id id
	 * @PreAuthorize("@pms.hasPermission('admin_ctbenpaybackregister_del')" )
     * @return R
     */

    @ApiOperation(value = "通过id删除福利补缴登记", notes = "通过id删除福利补缴登记")
    @SysLog("通过id删除福利补缴登记" )
	@GetMapping("/{id}")
    public R removeById(@PathVariable Integer id) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode =pigxUser.getCorpcode();
		QueryWrapper<CtbenpaybackRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("id",id);
        return R.ok(ctbenpaybackRegisterService.remove(queryWrapper));
    }


    /**
	 * 确认
	 * @param paramMap
	 * @return R
	 * */
    @ApiOperation(value = "确认", notes = "确认")
	@SysLog("确认")
	@PostMapping("/confirm")
	@Transactional
	public R confirm(Map paramMap){
		PigxUser pigxUser = SecurityUtils.getUser();
		int userid = pigxUser.getId();
		paramMap.put("userid", userid);
		//调用确认的存储过程
		Map msgMap = ctbenpaybackRegisterMapper.cSP_BenPayBackCheck(paramMap);
		int msgid = (Integer) msgMap.get("result");
		Systmessage systmessage = systmessageMapper.selectById(msgid);
		String message = systmessage.getTitle();
		return R.ok(message);
	}

}
