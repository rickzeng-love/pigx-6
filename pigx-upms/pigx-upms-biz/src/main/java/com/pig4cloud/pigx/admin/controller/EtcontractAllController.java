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
import com.pig4cloud.pigx.admin.entity.EtcontractRegister;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.Systmessage;
import com.pig4cloud.pigx.admin.mapper.EtcontractAllMapper;
import com.pig4cloud.pigx.admin.mapper.SystmessageMapper;
import com.pig4cloud.pigx.admin.service.EtcontractRegisterService;
import com.pig4cloud.pigx.admin.service.EtemployeeService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtcontractAll;
import com.pig4cloud.pigx.admin.service.EtcontractAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 合同变动历史
 *
 * @author gaoxiao
 * @date 2020-04-17 10:30:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcontractall" )
@Api(value = "etcontractall", tags = "合同变动历史管理")
public class EtcontractAllController {

    private final  EtcontractAllService etcontractAllService;
    private final EtemployeeService etemployeeService;
    private final EtcontractAllMapper etcontractAllMapper;
    private final EtcontractRegisterService etcontractRegisterService;
    private final SystmessageMapper systmessageMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etcontractAll 合同变动历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtcontractAllPage(Page page, EtcontractAll etcontractAll) {
        return R.ok(etcontractAllService.page(page, Wrappers.query(etcontractAll)));
    }

	/**
	 * 查询所有
	 * @param etcontractAll
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtcontractAll" )
	public R getAllEtbgWorking(EtcontractAll etcontractAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etcontractAll.setCorpcode(corpcode);
		//etcontractAll.setUserid(userid);
		return R.ok(etcontractAllService.list(Wrappers.query(etcontractAll)));
	}

    /**
     * 通过id查询合同变动历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etcontractAllService.getById(id));
    }

    /**
     * 合同新签
     * @param etcontractRegister 合同变动历史    @PreAuthorize("@pms.hasPermission('admin_etcontractall_add')" )
     * @return R
     */
    @ApiOperation(value = "合同新签", notes = "合同新签")
    @SysLog("新增合同变" )
    @PostMapping("/save")
	@Transactional
    public R save(@RequestBody EtcontractRegister etcontractRegister) {
		///类型 type :新签1变更2续签3终止4解除5
		EtcontractAll etcontractAll = null;
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int coprid = pigxUser.getCorpid();

		etcontractRegister.setRegby(pigxUser.getId());
		etcontractRegister.setRegdate(currentTime);
		etcontractRegister.setType(1);//合同新签
		int eid = etcontractRegister.getEid();
		Etemployee etemployee = etemployeeService.getById(eid);
		etcontractRegister.setCorpid(etemployee.getCorpid());
		etcontractRegister.setCorpcode(etemployee.getCorpcode());
		etcontractRegister.setBadge(etemployee.getBadge());
		etcontractRegister.setCompid(etemployee.getCompid());
		etcontractRegister.setDepid(etemployee.getDepid());
		etcontractRegister.setEid(etemployee.getEid());
		etcontractRegister.setJobid(etemployee.getJobid());
		etcontractRegister.setEmpgrade(etemployee.getEmpgrade());
		etcontractRegister.setPosgrade(etemployee.getPosgrade());
		etcontractRegister.setPosition(etemployee.getPosition());
		etcontractRegisterService.save(etcontractRegister);
		//校验合同
		//eSPContractCheckSub
		Map map = new HashMap();
		map.put("id",etcontractRegister.getId());
		Map map2 = new HashMap();
		map2.put("id",etcontractRegister.getId());
		String message = null;
		//调用确认的存储过程
		etcontractAllMapper.eSPContractCheckSub(map);
		int msgid = (Integer) map.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			map2.put("userid",pigxUser.getId());
			etcontractAllMapper.eSPContractStart(map2);
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
	/**
	 * 合同变更
	 * @param etcontractRegister 合同变动历史    @PreAuthorize("@pms.hasPermission('admin_etcontractall_add')" )
	 * @return R
	 */
	@ApiOperation(value = "合同变更/续签", notes = "合同变更/续签")
	@SysLog("新增合同变" )
	@PostMapping("/updateByBGOrXQ")
	@Transactional
	public R updateByBG(@RequestBody EtcontractRegister etcontractRegister) {
		///类型 type :新签1变更2续签3终止4解除5
		int type = etcontractRegister.getType();
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		int coprid = pigxUser.getCorpid();
		//首先查询出原始记录
		EtcontractAll etcontractAll = etcontractAllService.getById(etcontractRegister.getId());
		EtcontractRegister etcontractRegister2 = new EtcontractRegister();
		BeanUtils.copyProperties(etcontractAll,etcontractRegister2);
		etcontractRegister2.setRegby(pigxUser.getId());
		etcontractRegister2.setRegdate(currentTime);
		etcontractRegister2.setType(etcontractRegister.getType());
		//etcontractRegister2.setType(2);//合同变更
	/*	合同编号conno合同类型contype合同属性conproperty合同期(月)conterm
		合同开始日期conbegindate合同结束日期conenddate签约单位contract
		签订时间 regdate*/
		if(type!=4 && type!=5){
			etcontractRegister2.setNewConno(etcontractRegister.getConno());
			etcontractRegister2.setNewContype(etcontractRegister.getContype());
			etcontractRegister2.setNewConproperty(etcontractRegister.getConproperty());
			etcontractRegister2.setNewConterm(etcontractRegister.getConterm());
			etcontractRegister2.setNewConbegindate(etcontractRegister.getConbegindate());
			etcontractRegister2.setNewConenddate(etcontractRegister.getConenddate());
			etcontractRegister2.setNewContract(etcontractRegister.getContract());
		}

		etcontractRegisterService.save(etcontractRegister2);

		//校验合同
		//eSPContractCheckSub
		Map map = new HashMap();
		map.put("id",etcontractRegister2.getId());

		String message = null;
		//调用确认的存储过程
		Map lmap = etcontractAllMapper.eSPContractCheckSub(map);
		int msgid = (Integer) lmap.get("result");
		Systmessage systmessage = new Systmessage();
		//如果成功
		if (msgid == 0) {
			//调用生效的存储过程
			map.put("userid",pigxUser.getId());
			Map mesMap = etcontractAllMapper.eSPContractStart(map);
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

		return R.ok("操作成功！");
	}

    /**
     * 修改合同变动历史
     * @param etcontractAll 合同变动历史    @PreAuthorize("@pms.hasPermission('admin_etcontractall_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改合同变动历史", notes = "修改合同变动历史")
    @SysLog("修改合同变动历史" )
    @PutMapping
    public R updateById(@RequestBody EtcontractAll etcontractAll) {
        return R.ok(etcontractAllService.updateById(etcontractAll));
    }

    /**
     * 通过id删除合同变动历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除合同变动历史", notes = "通过id删除合同变动历史")
    @SysLog("通过id删除合同变动历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etcontractall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etcontractAllService.removeById(id));
    }
	/**
	 * 合同未签订
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "合同未签订", notes = "合同未签订")
	@PostMapping("/getEtcontractAllWQD" )
	public R getEtcontractAllWQD(Page page) {
		EtcontractAll etcontractAll = new EtcontractAll();
		PigxUser pigxUser = SecurityUtils.getUser();
		String currentTime = DateUtils.getTimeString();
		etcontractAll.setCorpcode(pigxUser.getCorpcode());
		return R.ok(etcontractAllMapper.listEtcontractAllWQD(page,etcontractAll));
	}

	/**
	 * 合同已签订
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "合同已签订", notes = "合同已签订")
	@PostMapping("/getEtcontractAllYQD" )
	public R getEtcontractAllYQD(Page page) {
		EtcontractAll etcontractAll = new EtcontractAll();
		PigxUser pigxUser = SecurityUtils.getUser();
		String currentTime = DateUtils.getTimeString();
		etcontractAll.setCorpcode(pigxUser.getCorpcode());
		return R.ok(etcontractAllMapper.listEtcontractAllYQD(page,etcontractAll));
	}

	/**
	 * 合同签订历史
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "合同签订历史", notes = "合同签订历史")
	@PostMapping("/getEtcontractAllQDLS" )
	public R getEtcontractAllQDLS(Page page) {
		EtcontractAll etcontractAll = new EtcontractAll();
		PigxUser pigxUser = SecurityUtils.getUser();
		String currentTime = DateUtils.getTimeString();
		etcontractAll.setCorpcode(pigxUser.getCorpcode());
		return R.ok(etcontractAllMapper.listEtcontractAllQDLS(page,etcontractAll));
	}

	/**
	 * 合同签订历史
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "合同签订历史", notes = "合同签订历史")
	@PostMapping("/getEtcontractAllByEID" )
	public R getEtcontractAllByEID(Page page) {
		EtcontractAll etcontractAll = new EtcontractAll();
		PigxUser pigxUser = SecurityUtils.getUser();
		String currentTime = DateUtils.getTimeString();
		etcontractAll.setCorpcode(pigxUser.getCorpcode());
		etcontractAll.setEid(pigxUser.getEid());
		return R.ok(etcontractAllMapper.listEtcontractAllByEID(etcontractAll));
	}




}
