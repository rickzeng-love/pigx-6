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
import com.pig4cloud.pigx.admin.entity.QywxWorkitem;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.QywxWorkitemMapper;
import com.pig4cloud.pigx.admin.service.QywxWorkitemService;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 审批流程主表
 *
 * @author XP
 * @date 2020-06-10 14:18:31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/qywxworkitem" )
@Api(value = "qywxworkitem", tags = "审批流程主表管理")
public class QywxWorkitemController {

    private final QywxWorkitemService qywxWorkitemService;
    private final QywxWorkitemMapper qywxWorkitemMapper;
	private final SystcorpinfoService systcorpinfoService;

    /**
     * 分页查询我发起的
     * @param page 分页对象
     * @param qywxWorkitem 审批流程主表
     * @return
     */
    @ApiOperation(value = "分页查询我发起的", notes = "分页查询我发起的")
    @PostMapping("/page" )
    public R getQywxWorkitemPage(Page page, @RequestBody QywxWorkitem qywxWorkitem) {
		Map map = new HashMap();
    	//审批状态
		int openspstatus = qywxWorkitem.getOpenspstatus();
		log.info("--------------------审批状态 openspstatus:" + openspstatus);
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywx_corpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);
		String qywx_corpid= "";
		QueryWrapper<QywxWorkitem> queryWrapper = new QueryWrapper<>();
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywx_corpid = systcorpinfo.getQywxCorpid();
			log.info("--------------------qywx_corpid:" + qywx_corpid);
			queryWrapper.eq("QYWXCorpid", qywx_corpid);
			qywxWorkitem.setQywxcorpid(qywx_corpid);
		}else{
			return R.failed("请维护企业微信corpid！");
		}
		int userid = pigxUser.getId();
		log.info("--------------------userid:" + userid);
		queryWrapper.eq("applyUserId", userid);
		qywxWorkitem.setApplyuserid(userid);
		String startTime = qywxWorkitem.getStartTime();
		log.info("--------------------startTime:" + startTime);
		String endTime = qywxWorkitem.getEndTime();
		log.info("--------------------endTime:" + endTime);
		String type = qywxWorkitem.getType();
		log.info("--------------------type:" + type);
		String openspname = qywxWorkitem.getOpenspname();
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
		Page approval = qywxWorkitemService.page(page, queryWrapper.eq("openSpStatus", 1));
		long approvalNum = approval.getTotal();
		//2-已通过
		Page beApproval = qywxWorkitemService.page(page, queryWrapper.eq("openSpStatus", 2));
		long beApprovalNum = beApproval.getTotal();
		//3-已驳回
		Page afApproval = qywxWorkitemService.page(page, queryWrapper.eq("openSpStatus", 3));
		long afApprovalNum = afApproval.getTotal();

		map.put("approvalNum",approvalNum);
		map.put("beApprovalNum",beApprovalNum);
		map.put("afApprovalNum",afApprovalNum);

		IPage<Map> resultList  = qywxWorkitemMapper.queryPageBySql(page,qywxWorkitem);
		map.put("pageAll",resultList);

		return R.ok(map);
    }


    /**
     * 通过id查询审批流程主表
     * @param thirdno id
     * @return R
     */
/*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{thirdno}" )
    public R getById(@PathVariable("thirdno" ) String thirdno) {
        return R.ok(qywxWorkitemService.getById(thirdno));
    }
*/

    /**
     * 新增审批流程主表
     * @param qywxWorkitem 审批流程主表
     * @return R
     */
  /*  @ApiOperation(value = "新增审批流程主表", notes = "新增审批流程主表")
    @SysLog("新增审批流程主表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitem_add')" )
    public R save(@RequestBody QywxWorkitem qywxWorkitem) {
        return R.ok(qywxWorkitemService.save(qywxWorkitem));
    }
*/
    /**
     * 修改审批流程主表
     * @param qywxWorkitem 审批流程主表
     * @return R
     */
   /* @ApiOperation(value = "修改审批流程主表", notes = "修改审批流程主表")
    @SysLog("修改审批流程主表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitem_edit')" )
    public R updateById(@RequestBody QywxWorkitem qywxWorkitem) {
        return R.ok(qywxWorkitemService.updateById(qywxWorkitem));
    }*/

    /**
     * 通过id删除审批流程主表
     * @param thirdno id
     * @return R
     */
   /* @ApiOperation(value = "通过id删除审批流程主表", notes = "通过id删除审批流程主表")
    @SysLog("通过id删除审批流程主表" )
    @DeleteMapping("/{thirdno}" )
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitem_del')" )
    public R removeById(@PathVariable String thirdno) {
        return R.ok(qywxWorkitemService.removeById(thirdno));
    }
*/
}
