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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QywxWorkitemApproval;
import com.pig4cloud.pigx.admin.entity.QywxWorkitemCcinfo;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.QywxWorkitemCcinfoMapper;
import com.pig4cloud.pigx.admin.service.QywxWorkitemCcinfoService;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 审批流程子表-抄送人信息
 *
 * @author XP
 * @date 2020-06-10 14:18:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qywxworkitemccinfo" )
@Api(value = "qywxworkitemccinfo", tags = "审批流程子表-抄送人信息管理")
public class QywxWorkitemCcinfoController {

    private final QywxWorkitemCcinfoService qywxWorkitemCcinfoService;
    private final QywxWorkitemCcinfoMapper qywxWorkitemCcinfoMapper;
	private final SystcorpinfoService systcorpinfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qywxWorkitemCcinfo 审批流程子表-抄送人信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getQywxWorkitemCcinfoPage(Page page,  @RequestBody QywxWorkitemCcinfo qywxWorkitemCcinfo) {
		Map map = new HashMap();
		//审批状态
		int openspstatus = qywxWorkitemCcinfo.getOpenspstatus();
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywx_corpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);

		QueryWrapper<QywxWorkitemCcinfo> queryWrapper = new QueryWrapper<>();

		String qywx_corpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywx_corpid = systcorpinfo.getQywxCorpid();
			queryWrapper.eq("QYWXCorpid", qywx_corpid);
			qywxWorkitemCcinfo.setQywxcorpid(qywx_corpid);
		}else{
			return R.failed("请维护企业微信corpid！");
		}
		int userid = pigxUser.getId();
		qywxWorkitemCcinfo.setItemuserid(userid);
		String startTime = qywxWorkitemCcinfo.getStartTime();
		String endTime = qywxWorkitemCcinfo.getEndTime();
		String type = qywxWorkitemCcinfo.getType();
		String openspname = qywxWorkitemCcinfo.getOpenspname();

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
		if (openspstatus > 0){
			queryWrapper.eq("openSpStatus", openspstatus);
		}

		//1-审批中
		Page approval = qywxWorkitemCcinfoService.page(page, queryWrapper.eq("openspstatus", 1));
		long approvalNum = approval.getTotal();
		//2-已通过
		Page beApproval = qywxWorkitemCcinfoService.page(page, queryWrapper.eq("openspstatus", 2));
		long beApprovalNum = beApproval.getTotal();
		//3-已驳回
		Page afApproval = qywxWorkitemCcinfoService.page(page, queryWrapper.eq("openspstatus", 3));
		long afApprovalNum = afApproval.getTotal();

		map.put("approvalNum",approvalNum);
		map.put("beApprovalNum",beApprovalNum);
		map.put("afApprovalNum",afApprovalNum);

		IPage<Map> resultList  = qywxWorkitemCcinfoMapper.queryPageBySql(page,qywxWorkitemCcinfo);
		map.put("pageAll",resultList);

		return R.ok(map);
    }


    /**
     * 通过id查询审批流程子表-抄送人信息
     * @param id id
     * @return R
     */
   /* @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qywxWorkitemCcinfoService.getById(id));
    }
*/
    /**
     * 新增审批流程子表-抄送人信息
     * @param qywxWorkitemCcinfo 审批流程子表-抄送人信息
     * @return R
     */
    /*@ApiOperation(value = "新增审批流程子表-抄送人信息", notes = "新增审批流程子表-抄送人信息")
    @SysLog("新增审批流程子表-抄送人信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitemccinfo_add')" )
    public R save(@RequestBody QywxWorkitemCcinfo qywxWorkitemCcinfo) {
        return R.ok(qywxWorkitemCcinfoService.save(qywxWorkitemCcinfo));
    }*/

    /**
     * 修改审批流程子表-抄送人信息
     * @param qywxWorkitemCcinfo 审批流程子表-抄送人信息
     * @return R
     */
   /* @ApiOperation(value = "修改审批流程子表-抄送人信息", notes = "修改审批流程子表-抄送人信息")
    @SysLog("修改审批流程子表-抄送人信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitemccinfo_edit')" )
    public R updateById(@RequestBody QywxWorkitemCcinfo qywxWorkitemCcinfo) {
        return R.ok(qywxWorkitemCcinfoService.updateById(qywxWorkitemCcinfo));
    }*/

    /**
     * 通过id删除审批流程子表-抄送人信息
     * @param id id
     * @return R
     */
    /*@ApiOperation(value = "通过id删除审批流程子表-抄送人信息", notes = "通过id删除审批流程子表-抄送人信息")
    @SysLog("通过id删除审批流程子表-抄送人信息" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qywxworkitemccinfo_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qywxWorkitemCcinfoService.removeById(id));
    }
*/
}
