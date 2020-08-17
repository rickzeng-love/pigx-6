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
import com.pig4cloud.pigx.admin.mapper.AtstaffRegisterMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.AtstaffRegisterService;
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
 * 
 *
 * @author gaoxiao
 * @date 2020-05-30 15:46:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atstaffregister" )
@Api(value = "atstaffregister", tags = "管理")
public class AtstaffRegisterController {

    private final  AtstaffRegisterService atstaffRegisterService;
    private  final AtstaffRegisterMapper atstaffRegisterMapper;
    private final SystmessageMapper systmessageMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atstaffRegister 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtstaffRegisterPage(Page page, AtstaffRegister atstaffRegister) {
        return R.ok(atstaffRegisterService.page(page, Wrappers.query(atstaffRegister)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atstaffRegisterService.getById(id));
    }

    /**
     * 新增
     * @param atstaffRegister 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atstaffregister_add')" )
    public R save(@RequestBody AtstaffRegister atstaffRegister) {
        return R.ok(atstaffRegisterService.save(atstaffRegister));
    }

/*    *//**
     * 修改
     * @param atstaffRegister      @PreAuthorize("@pms.hasPermission('admin_atstaffregister_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody AtstaffRegister atstaffRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<AtstaffRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id",atstaffRegister.getId());
		updateWrapper.eq("corpcode",corpcode);
        return R.ok(atstaffRegisterService.update(atstaffRegister,updateWrapper));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atstaffregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atstaffRegisterService.removeById(id));
    }
	*/
    /**
	 * 考勤开启查询
	 * @param page 分页对象
	 * @param atstaffRegister 考勤开启查询
	 * @return
	 * 测试通过
	 */
	@ApiOperation(value = "考勤开启查询", notes = "考勤开启查询")
	@PostMapping("/getAtstaffRegisterPageBySql" )
	public R getAtstaffRegisterPageBySql(Page page,@RequestBody(required=false) AtstaffRegister atstaffRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atstaffRegister)){
			atstaffRegister = new AtstaffRegister();
		}
		QueryWrapper<AtstaffRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = atstaffRegister.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",name);
		}
		Integer depid = atstaffRegister.getDepid();
		Integer jobid = atstaffRegister.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("jobid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(atstaffRegisterService.page(page,queryWrapper));
	}
	/**
	 * 新增
	 * @param atstaffRegister
	 * @return R
	 */
	@ApiOperation(value = "开启考勤", notes = "开启考勤")
	@SysLog("新增" )
	@PostMapping("/confirmAtstaffRegister")
	public R confirmAtstaffRegister(@RequestBody AtstaffRegister atstaffRegister) {

		return R.ok(atstaffRegisterService.save(atstaffRegister));
	}


	/**
	 * 实习期转正
	 * @param atstaffRegister
	 * @return R
	 */
	@ApiOperation(value = "开启考勤", notes = "开启考勤")
	@SysLog("开启考勤" )
	@PostMapping("/aSPStaffStart")
	@Transactional
	public R aSPStaffStart(@RequestBody AtstaffRegister atstaffRegister) {
		//审批标志
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int corpid = pigxUser.getCorpid();
		int compid = pigxUser.getCompid();
		Integer userid = pigxUser.getId();

		//校验变动
		Map map = new HashMap();
		map.put("id",atstaffRegister.getId());
		map.put("userid",userid);

		Map map2 = new HashMap();
		map2.put("id",atstaffRegister.getId());
		map2.put("userid",userid);
		String message = null;
		//调用确认的存储过程
		atstaffRegisterMapper.aSPStaffCheck(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			map2.put("userid",pigxUser.getId());
			atstaffRegisterMapper.aSPStaffStart(map2);
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

}
