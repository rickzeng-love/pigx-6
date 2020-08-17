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
import com.pig4cloud.pigx.admin.mapper.CtchangebenefitRegisterMapper;
import com.pig4cloud.pigx.admin.service.CtbenefitstatusService;
import com.pig4cloud.pigx.admin.service.SystmessageService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.CtchangebenefitRegisterService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
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
 * 福利变动登记
 *
 * @author XP
 * @date 2020-06-12 16:19:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ctchangebenefitregister" )
@Api(value = "ctchangebenefitregister", tags = "福利变动登记管理")
public class CtchangebenefitRegisterController {

    private final  CtchangebenefitRegisterService ctchangebenefitRegisterService;
    private final CtbenefitstatusService ctbenefitstatusService;
    private final CtchangebenefitRegisterMapper ctchangebenefitRegisterMapper;
    private final SystmessageService systmessageService;



	/**
	 * 社保调整
	 * @param ctchangebenefitRegister 社保变动
	 * @return
	 */
	@ApiOperation(value = "社保调整", notes = "社保调整")
	@PostMapping("/cSPChangeBenefitStart" )
	@Transactional
	public R cSPChangeBenefitStart(@RequestBody CtchangebenefitRegister ctchangebenefitRegister) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		String currentTime = DateUtils.getTimeString();
		//存历史
		Integer eid = ctchangebenefitRegister.getEid();
		Ctbenefitstatus ctbenefitstatus = ctbenefitstatusService.getById(eid);
		if(StringUtils.isEmpty(ctbenefitstatus)){
			return R.failed("此信息不存在，请核实！");
		}
		//CtchangesalaryAll ctchangesalaryAll = new CtchangesalaryAll();
		//BeanUtils.copyProperties(ctemployee1,ctchangesalaryAll);
		//插入变动登记表 主要需要插入old字段
		ctchangebenefitRegister.setBeartypeOld(ctbenefitstatus.getBeartype());
		ctchangebenefitRegister.setBenareaOld(ctbenefitstatus.getBenarea());
		ctchangebenefitRegister.setBenbaseOld(ctbenefitstatus.getBenbase());
		ctchangebenefitRegister.setBencityOld(ctbenefitstatus.getBencity());
		ctchangebenefitRegister.setBenstatusOld(ctbenefitstatus.getBenstatus());
		ctchangebenefitRegister.setInitialized(1);
		ctchangebenefitRegister.setInitializedby(userid);
		ctchangebenefitRegister.setInitializedtime(currentTime);
		ctchangebenefitRegister.setCorpcode(corpcode);
		ctchangebenefitRegister.setCorpid(pigxUser.getCorpid());
		ctchangebenefitRegisterService.save(ctchangebenefitRegister);
		Integer id = ctchangebenefitRegister.getId();

		Map paramMap = new HashMap();
		paramMap.put("userid",userid);
		paramMap.put("id",id);
		//处理确认按钮
		ctchangebenefitRegisterMapper.cSP_ChangeBenefitCheck(paramMap);
		Map resultMap2 = null;
		Integer result = (Integer) paramMap.get("result");
		Integer result2 = null;
		//如果成功，调用处理存储过程
		if(result==0){
			ctchangebenefitRegisterMapper.cSP_ChangeBenefitStart(paramMap);
			result2 = (Integer) paramMap.get("result");
			Systmessage systmessage2 = systmessageService.getById(result2);
			String message12 =  systmessage2.getTitle();
			if(result2==0){

				return R.ok(message12);
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.failed(message12);
			}

		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			Systmessage systmessage1 = systmessageService.getById(result);
			String message1 =  systmessage1.getTitle();
			return R.failed(message1);
		}

	}





	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param ctchangebenefitRegister 福利变动登记
	 * @return
	 */
/*    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCtchangebenefitRegisterPage(Page page, CtchangebenefitRegister ctchangebenefitRegister) {
        return R.ok(ctchangebenefitRegisterService.page(page, Wrappers.query(ctchangebenefitRegister)));
    }*/


    /**
     * 通过id查询福利变动登记
     * @param id id
     * @return R
     */
/*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(ctchangebenefitRegisterService.getById(id));
    }
*/

    /**
     * 新增福利变动登记
     * @param ctchangebenefitRegister 福利变动登记
     * @return R
     */
/*    @ApiOperation(value = "新增福利变动登记", notes = "新增福利变动登记")
    @SysLog("新增福利变动登记" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangebenefitregister_add')" )
    public R save(@RequestBody CtchangebenefitRegister ctchangebenefitRegister) {
        return R.ok(ctchangebenefitRegisterService.save(ctchangebenefitRegister));
    }*/

    /**
     * 修改福利变动登记
     * @param ctchangebenefitRegister 福利变动登记
     * @return R
     */
/*    @ApiOperation(value = "修改福利变动登记", notes = "修改福利变动登记")
    @SysLog("修改福利变动登记" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_ctchangebenefitregister_edit')" )
    public R updateById(@RequestBody CtchangebenefitRegister ctchangebenefitRegister) {
        return R.ok(ctchangebenefitRegisterService.updateById(ctchangebenefitRegister));
    }*/

    /**
     * 通过id删除福利变动登记
     * @param id id
     * @return R
     */
/*
    @ApiOperation(value = "通过id删除福利变动登记", notes = "通过id删除福利变动登记")
    @SysLog("通过id删除福利变动登记" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_ctchangebenefitregister_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(ctchangebenefitRegisterService.removeById(id));
    }
*/

}
