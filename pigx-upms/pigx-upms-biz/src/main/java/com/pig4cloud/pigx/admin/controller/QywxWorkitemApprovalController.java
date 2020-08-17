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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QywxWorkitemApproval;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.QywxWorkitemApprovalMapper;
import com.pig4cloud.pigx.admin.service.QywxWorkitemApprovalService;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 审批流程子表-审批信息
 *
 * @author XP
 * @date 2020-06-10 14:18:31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/qywxworkitemapproval" )
@Api(value = "qywxworkitemapproval", tags = "审批流程子表-审批信息管理")
public class QywxWorkitemApprovalController {

    private final QywxWorkitemApprovalService qywxWorkitemApprovalService;
	private final SystcorpinfoService systcorpinfoService;
	private final QywxWorkitemApprovalMapper qywxWorkitemApprovalMapper;
    /**
     * 分页查询
     * @param page 分页对象
     * @param qywxWorkitemApproval 审批流程子表-审批信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getQywxWorkitemApprovalPage(Page page, QywxWorkitemApproval qywxWorkitemApproval) {
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywx_corpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper1);

		QueryWrapper<QywxWorkitemApproval> queryWrapper = new QueryWrapper<>();

		String qywx_corpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywx_corpid = systcorpinfo.getQywxCorpid();
			queryWrapper.eq("QYWXCorpid", qywx_corpid);
			qywxWorkitemApproval.setQywxcorpid(qywx_corpid);
		}else{
			return R.failed("请维护企业微信corpid！");
		}
		int userid = pigxUser.getId();
		qywxWorkitemApproval.setItemuserid(userid);
		String startTime = qywxWorkitemApproval.getStartTime();
		String endTime = qywxWorkitemApproval.getEndTime();
		String type = qywxWorkitemApproval.getType();
		String openspname = qywxWorkitemApproval.getOpenspname();

		queryWrapper.eq("itemUserId", userid).eq("itemStatus", 1);
		if (StringUtil.isNotEmpty(openspname)) {
			queryWrapper.eq("openSpName", openspname);
		}
		if (StringUtil.isNotEmpty(startTime)){
			queryWrapper.gt("applyTime", startTime);
		}
		if (StringUtil.isNotEmpty(endTime)){
			queryWrapper.lt("applyTime", endTime);
		}
		if (StringUtil.isNotEmpty(type)){
			queryWrapper.eq("type", type);
		}

        return R.ok(qywxWorkitemApprovalService.page(page, queryWrapper));
    }

	/**
	 * 管理端历史分页查询
	 * @param page 分页对象
	 * @param qywxWorkitemApproval 管理端审批信息历史
	 * @return
	 */
	@ApiOperation(value = "管理端审批信息历史分页查询", notes = "管理端审批信息历史分页查询")
	@PostMapping("/getQywxWorkitemAllPage" )
	public R getQywxWorkitemAllPage(Page page, @RequestBody QywxWorkitemApproval qywxWorkitemApproval) {
		Map map = new HashMap();
		//审批状态
		int openspstatus = qywxWorkitemApproval.getOpenspstatus();
		log.info("--------------------审批状态 openspstatus:" + openspstatus);

		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywx_corpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);

		QueryWrapper<QywxWorkitemApproval> queryWrapper = new QueryWrapper<>();

		String qywx_corpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywx_corpid = systcorpinfo.getQywxCorpid();
			queryWrapper.eq("QYWXCorpid", qywx_corpid);
			qywxWorkitemApproval.setQywxcorpid(qywx_corpid);
		}else{
			return R.failed("请维护企业微信corpid！");
		}

		String startTime = qywxWorkitemApproval.getStartTime();
		log.info("--------------------startTime:" + startTime);
		String endTime = qywxWorkitemApproval.getEndTime();
		log.info("--------------------endTime:" + endTime);
		String type = qywxWorkitemApproval.getType();
		log.info("--------------------type:" + type);
		String openspname = qywxWorkitemApproval.getOpenspname();
		log.info("--------------------openspname:" + openspname);

		if (StringUtil.isNotEmpty(openspname)) {
			queryWrapper.eq("openSpName", openspname);
		}
		if (StringUtil.isNotEmpty(startTime)){
			queryWrapper.gt("applyTime", startTime);
			map.put("starttime",startTime);
		}
		if (StringUtil.isNotEmpty(endTime)){
			queryWrapper.lt("applyTime", endTime);
			map.put("endtime",endTime);
		}
		if (StringUtil.isNotEmpty(type)){
			queryWrapper.eq("type", type);
			map.put("type",type);
		}

		//1-审批中
		Page approval = qywxWorkitemApprovalService.page(page, queryWrapper.eq("openspstatus", 1));
		long approvalNum = approval.getTotal();
		//2-已通过
		Page beApproval = qywxWorkitemApprovalService.page(page, queryWrapper.eq("openspstatus", 2));
		long beApprovalNum = beApproval.getTotal();
		//3-已驳回
		Page afApproval = qywxWorkitemApprovalService.page(page, queryWrapper.eq("openspstatus", 3));
		long afApprovalNum = afApproval.getTotal();

		map.put("approvalNum",approvalNum);
		map.put("beApprovalNum",beApprovalNum);
		map.put("afApprovalNum",afApprovalNum);

		IPage<Map> resultList  = qywxWorkitemApprovalMapper.queryPageBySql(page,qywxWorkitemApproval);
		map.put("pageAll",resultList);

		return R.ok(map);

	}

	/**
	 * 员工端历史分页查询
	 * @param page 分页对象
	 * @param qywxWorkitemApproval 员工端审批信息历史
	 * @return
	 */
	@ApiOperation(value = "员工端审批信息历史分页查询", notes = "员工端审批信息历史分页查询")
	@PostMapping("/getQywxWorkitemUserAllPage" )
	public R getQywxWorkitemUserAllPage(Page page, @RequestBody QywxWorkitemApproval qywxWorkitemApproval) {
		Map map = new HashMap();
		//审批状态
		int openspstatus = qywxWorkitemApproval.getOpenspstatus();
		log.info("--------------------审批状态 openspstatus:" + openspstatus);

		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywx_corpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);

		QueryWrapper<QywxWorkitemApproval> queryWrapper = new QueryWrapper<>();

		String qywx_corpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywx_corpid = systcorpinfo.getQywxCorpid();
			queryWrapper.eq("QYWXCorpid", qywx_corpid);
			qywxWorkitemApproval.setQywxcorpid(qywx_corpid);
		}else{
			return R.failed("请维护企业微信corpid！");
		}
		int userid = pigxUser.getId();
		log.info("--------------------userid:" + userid);
		queryWrapper.eq("applyUserId", userid);
		qywxWorkitemApproval.setApplyuserid(userid);
		String startTime = qywxWorkitemApproval.getStartTime();
		log.info("--------------------startTime:" + startTime);
		String endTime = qywxWorkitemApproval.getEndTime();
		log.info("--------------------endTime:" + endTime);
		String type = qywxWorkitemApproval.getType();
		log.info("--------------------type:" + type);
		String openspname = qywxWorkitemApproval.getOpenspname();
		log.info("--------------------openspname:" + openspname);

		queryWrapper.eq("itemUserId", userid);
		if (StringUtil.isNotEmpty(openspname)) {
			queryWrapper.eq("openSpName", openspname);
		}
		if (StringUtil.isNotEmpty(startTime)){
			queryWrapper.gt("applyTime", startTime);
			map.put("starttime",startTime);
		}
		if (StringUtil.isNotEmpty(endTime)){
			queryWrapper.lt("applyTime", endTime);
			map.put("endtime",endTime);
		}
		if (StringUtil.isNotEmpty(type)){
			queryWrapper.eq("type", type);
			map.put("type",type);
		}

		//1-审批中
		Page approval = qywxWorkitemApprovalService.page(page, queryWrapper.eq("openspstatus", 1));
		long approvalNum = approval.getTotal();
		//2-已通过
		Page beApproval = qywxWorkitemApprovalService.page(page, queryWrapper.eq("openspstatus", 2));
		long beApprovalNum = beApproval.getTotal();
		//3-已驳回
		Page afApproval = qywxWorkitemApprovalService.page(page, queryWrapper.eq("openspstatus", 3));
		long afApprovalNum = afApproval.getTotal();

		map.put("approvalNum",approvalNum);
		map.put("beApprovalNum",beApprovalNum);
		map.put("afApprovalNum",afApprovalNum);


		IPage<Map> resultList  = qywxWorkitemApprovalMapper.queryPageBySql(page,qywxWorkitemApproval);
		map.put("pageAll",resultList);

		return R.ok(map);

	}


    /**
     * 通过id查询审批流程子表-审批信息
     * @param id id
     * @return R
     */
  /*  @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qywxWorkitemApprovalService.getById(id));
    }
*/
    /**
     * 新增审批流程子表-审批信息
     * @param qywxWorkitemApproval 审批流程子表-审批信息
     * @return R
    /* *//*
    @ApiOperation(value = "新增审批流程子表-审批信息", notes = "新增审批流程子表-审批信息")
    @SysLog("新增审批流程子表-审批信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitemapproval_add')" )
    public R save(@RequestBody QywxWorkitemApproval qywxWorkitemApproval) {
        return R.ok(qywxWorkitemApprovalService.save(qywxWorkitemApproval));
    }*/

    /**
     * 修改审批流程子表-审批信息
     * @param qywxWorkitemApproval 审批流程子表-审批信息
     * @return R
     */
   /* @ApiOperation(value = "修改审批流程子表-审批信息", notes = "修改审批流程子表-审批信息")
    @SysLog("修改审批流程子表-审批信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitemapproval_edit')" )
    public R updateById(@RequestBody QywxWorkitemApproval qywxWorkitemApproval) {
        return R.ok(qywxWorkitemApprovalService.updateById(qywxWorkitemApproval));
    }
*/
    /**
     * 通过id删除审批流程子表-审批信息
     * @param id id
     * @return R
     */
   /* @ApiOperation(value = "通过id删除审批流程子表-审批信息", notes = "通过id删除审批流程子表-审批信息")
    @SysLog("通过id删除审批流程子表-审批信息" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitemapproval_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qywxWorkitemApprovalService.removeById(id));
    }
*/
}
