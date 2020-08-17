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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.pig4cloud.pigx.admin.api.entity.SysFile;
import com.pig4cloud.pigx.admin.entity.SysWorkflowTemplate;
import com.pig4cloud.pigx.admin.entity.SysWorkflowTemplateCorp;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.SysQywxApplicationAuthorizerMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.*;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.core.util.TaskActionEntity;
import com.pig4cloud.pigx.common.core.util.domain.BscInstTaskOpt;
import com.pig4cloud.pigx.common.core.util.domain.DingyiEntity;
import com.pig4cloud.pigx.common.core.util.domain.InstEntity;
import com.pig4cloud.pigx.common.core.util.domain.TaskEntity;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 审批流总控制器
 *
 * @author power
 * @date 2020-04-16 23:23:23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/proInstWork")
@Api(value = "proInstWork", tags = "审批流总控制器")
public class QYWXProInstWorkController {
	private final SysQywxApplicationAuthorizerMapper sysQywxApplicationAuthorizerMapper;
	private final SysWorkflowTemplateCorpService sysWorkflowTemplateCorpService;
	private final SysWorkflowTemplateService sysWorkflowTemplateService;
	private final SysQywxApplicationService sysQywxApplicationService;
	private final SysFileService sysFileServicel;
	private final SystcorpinfoService systcorpinfoService;

	@Resource
	private ProInstWorkService proInstWorkService;

	@Resource
	private RestTemplate restTemplate;

	/**
	 * 更新/复制模板
	 * access_token 密钥
	 * open_template_id 客户模板id
	 * @return 企业模板id
	 */
	@ApiOperation(value = "更新复制模板", notes = "更新复制模板")
	@PostMapping("/copymodel")
	public R copymodel(@RequestParam("open_template_id") String open_template_id) {
		log.info("--------------------模板open_template_id:" + open_template_id);
		String copy_template_url = QYWXURL.COPY_TEMPLATE_URL;
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String corpcode = pigxUser.getCorpcode();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		HashMap<String, String> map = new HashMap<>();
		map.put("open_template_id", open_template_id);

		//获取返回信息
		ResponseEntity<String> result = restTemplate.postForEntity(copy_template_url + "?access_token=" + access_token, map, String.class);
		log.info("--------------------result:" + result);
		log.info("--------------------result.getBody():" + result.getBody());
		JSONObject jsonmap = JSONObject.parseObject(result.getBody());
		String template_id = jsonmap.get("template_id").toString();
		log.info("--------------------template_id:" + template_id);
		SysWorkflowTemplateCorp sysWorkflowTemplateCorp = new SysWorkflowTemplateCorp();
		sysWorkflowTemplateCorp.setQywxCorpid(qywxcorpid);
		sysWorkflowTemplateCorp.setOpenTemplateId(open_template_id);
		sysWorkflowTemplateCorp.setTemplateId(template_id);
		//通过主键open_template_id获取SysWorkflowTemplate
		SysWorkflowTemplate sysWorkflowTemplate = sysWorkflowTemplateService.getById(open_template_id);
		String name = sysWorkflowTemplate.getName();
		sysWorkflowTemplateCorp.setName(name);
		String type = sysWorkflowTemplate.getType();
		sysWorkflowTemplateCorp.setType(type);
		//执行保存
		return R.ok(sysWorkflowTemplateCorpService.save(sysWorkflowTemplateCorp));
	}


	/**
	 * 更新/复制模板
	 * access_token 密钥
	 * open_template_id 客户模板id
	 * @return 企业模板id
	 */
	@ApiOperation(value = "更新复制模板", notes = "更新复制模板")
	@PostMapping("/copyWorkFlowTemplate")
	public R copyWorkFlowTemplate(List<SysWorkflowTemplateCorp> workflowTemplateCorpList) {
		String copy_template_url = QYWXURL.COPY_TEMPLATE_URL;
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);

		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		SysWorkflowTemplateCorp sysWorkflowTemplateCorp = null;
		for(int i=0;i<workflowTemplateCorpList.size();i++){
			sysWorkflowTemplateCorp = workflowTemplateCorpList.get(i);
			String openTemplateId =  sysWorkflowTemplateCorp.getOpenTemplateId();
			log.info("--------------------模板template_id:" +openTemplateId);
			//校验是否已存在
			QueryWrapper<SysWorkflowTemplateCorp> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("open_template_id",openTemplateId);
			SysWorkflowTemplateCorp sysWorkflowTemplateCorp1 = sysWorkflowTemplateCorpService.getOne(queryWrapper);
			if(!org.springframework.util.StringUtils.isEmpty(sysWorkflowTemplateCorp1)){
				continue;
			}

			HashMap<String, String> map = new HashMap<>();
			map.put("open_template_id", openTemplateId);

			//获取返回信息
			ResponseEntity<String> result = restTemplate.postForEntity(copy_template_url + "?access_token=" + access_token, map, String.class);
			log.info("--------------------result:" + result);
			log.info("--------------------result.getBody():" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String template_id = jsonmap.get("template_id").toString();
			log.info("--------------------template_id:" + template_id);
			SysWorkflowTemplateCorp sysWorkflowTemplateCorp2 = new SysWorkflowTemplateCorp();
			sysWorkflowTemplateCorp2.setQywxCorpid(qywxcorpid);
			sysWorkflowTemplateCorp2.setOpenTemplateId(openTemplateId);
			sysWorkflowTemplateCorp2.setTemplateId(template_id);
			//通过主键open_template_id获取SysWorkflowTemplate
			SysWorkflowTemplate sysWorkflowTemplate = sysWorkflowTemplateService.getById(sysWorkflowTemplateCorp.getOpenTemplateId());
			String name = sysWorkflowTemplate.getName();
			sysWorkflowTemplateCorp2.setName(name);
			String type = sysWorkflowTemplate.getType();
			sysWorkflowTemplateCorp2.setType(type);
			sysWorkflowTemplateCorpService.save(sysWorkflowTemplateCorp2);
		}

		return R.ok("同步成功！");



		//执行保存

	}

	/**
	 * 更新/复制模板
	 * access_token 密钥
	 * open_template_id 客户模板id
	 * @return 企业模板id
	 */
	@ApiOperation(value = "更新复制模板", notes = "更新复制模板")
	@PostMapping("/copyWorkFlowTemplate2")
	public R copyWorkFlowTemplate2(@RequestBody List<SysWorkflowTemplate> workflowTemplateList) {
		String copy_template_url = QYWXURL.COPY_TEMPLATE_URL;
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper2);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);

		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		SysWorkflowTemplateCorp sysWorkflowTemplateCorp = null;
		SysWorkflowTemplate sysWorkflowTemplate = null;
		SysWorkflowTemplate sysWorkflowTemplate2 = null;
		for(int i=0;i<workflowTemplateList.size();i++){
			sysWorkflowTemplate = workflowTemplateList.get(i);
			//在这里说明下，当时的开发者建表的时候，把服务商的模板id和公司的模板id弄反了，
			//因为接口已经开发好了，就不改了，沿用下来了。
			//服务商模板id
			String openTemplateId =  sysWorkflowTemplate.getTemplateId();
			sysWorkflowTemplate2 = sysWorkflowTemplateService.getById(openTemplateId);
			log.info("--------------------模板template_id:" +openTemplateId);
			//校验是否已存在
			QueryWrapper<SysWorkflowTemplateCorp> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("template_id",openTemplateId);
			queryWrapper.eq("qywx_corpid",qywxcorpid);
			SysWorkflowTemplateCorp sysWorkflowTemplateCorp1 = sysWorkflowTemplateCorpService.getOne(queryWrapper);
			if(!org.springframework.util.StringUtils.isEmpty(sysWorkflowTemplateCorp1)){
				continue;
			}

			HashMap<String, String> map = new HashMap<>();
			map.put("open_template_id", openTemplateId);


			//获取返回信息
			ResponseEntity<String> result = restTemplate.postForEntity(copy_template_url + "?access_token=" + access_token, JSONObject.toJSON(map), String.class);
			log.info("--------------------result:" + result);
			log.info("--------------------result.getBody():" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String template_id = jsonmap.get("template_id").toString();
			log.info("--------------------template_id:" + template_id);
			SysWorkflowTemplateCorp sysWorkflowTemplateCorp2 = new SysWorkflowTemplateCorp();
			sysWorkflowTemplateCorp2.setQywxCorpid(qywxcorpid);
			sysWorkflowTemplateCorp2.setOpenTemplateId(template_id);
			sysWorkflowTemplateCorp2.setTemplateId(openTemplateId);
			//通过主键open_template_id获取SysWorkflowTemplate
			String name = sysWorkflowTemplate2.getName();
			sysWorkflowTemplateCorp2.setName(name);
			String type = sysWorkflowTemplate2.getType();
			sysWorkflowTemplateCorp2.setType(type);
			sysWorkflowTemplateCorpService.save(sysWorkflowTemplateCorp2);
		}

		return R.ok("同步成功！");



		//执行保存

	}


	/**
	 * 上传微信临时素材
	 * file
	 * @return
	 */
	@ApiOperation(value = "上传微信临时素材", notes = "上传微信临时素材")
	@PostMapping("/filetransfer")
	public void filetransfer(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
		String upload_url = QYWXURL.UPLOAD_URL;
		log.info("--------------------file:" + file);
		String ftype = file.getContentType();
		log.info("--------------------ftype:" + ftype);
		String filetype = ftype.indexOf("image")!=-1?"image":ftype.indexOf("video")!=-1?"video":ftype.indexOf("voice")!=-1?"voice":"file";
		log.info("--------------------filetype:" + filetype);
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}/*else{
			return R.failed("请维护企业微信corpid！");
		}*/
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		String uri = upload_url + "?access_token=" + access_token + "&type=" + filetype;
		//获取返回信息
		JSONObject jsonObject = JSONObject.parseObject(WeiXinUtil.httpRequest(uri,MultipartFileToFile.multipartFileToFile(file)));
		log.info("--------------------jsonObject:" + jsonObject);

		/*ResponseEntity<String> result = restTemplate.postForEntity(upload_url + "?access_token=" + access_token + "&type=" + filetype, map, String.class);
		log.info("--------------------result:" + result);
		log.info("--------------------result.getBody():" + result.getBody());
		JSONObject jsonmap = JSONObject.parseObject(result.getBody());
		if (jsonmap.get("type") != null) {
			String type = jsonmap.get("type").toString();
		}
		if (jsonmap.get("media_id") != null) {
			String media_id = jsonmap.get("media_id").toString();
		}*/

		/*SysFile sysFile = new SysFile();
		sysFile.setFileName(media_id);
		sysFile.setType(type);
		sysFileServicel.save(sysFile);*/
		//1. 第一种方法，指定输出到客户端的时候，这些文字使用UTF-8编码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		response.getWriter().print(jsonObject);
	}


	/**
	 * 下载微信临时素材
	 * file
	 * @return
	 */
	@ApiOperation(value = "下载微信临时素材", notes = "下载微信临时素材")
	@PostMapping("/filedown")
	public void filedown(@RequestParam("media_id")String media_id, HttpServletResponse response) throws Exception {
		String get_down_url = QYWXURL.GET_DOWN_URL;
		log.info("--------------------media_id:" + media_id);
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			/*return R.failed("请维护企业微信corpid！");*/
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		//调用接口
		ResponseEntity<String> result = restTemplate.getForEntity(get_down_url + "?access_token=" + access_token + "&media_id=" + media_id, String.class);
		log.info("--------------------result:" + result);
		log.info("--------------------result.getBody():" + result.getBody());


	}

	/**
	 * 获取模板
	 * @param templateid 模板的templateid
	 * @return
	 */
	@ApiOperation(value = "获取模板", notes = "获取模板")
	@PostMapping("/getMoban")
	public void getMoban(@RequestParam("templateid") String templateid, HttpServletResponse response) throws IOException {
		String get_template_url = QYWXURL.GET_TEMPLATE_URL;
		log.info("--------------------templateid:" + templateid);
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			/*return R.failed("请维护企业微信corpid！");*/
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		HashMap<String, String> map = new HashMap<>();
		map.put("template_id", templateid);

		//获取返回信息
		ResponseEntity<String> result = restTemplate.postForEntity(get_template_url + "?access_token=" + access_token, map, String.class);
		log.info("--------------------result:" + result);
		log.info("--------------------result.getBody():" + result.getBody());

		//1. 第一种方法，指定输出到客户端的时候，这些文字使用UTF-8编码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		response.getWriter().print(result.getBody());

	}

	/**
	 * 提交审批
	 * json
	 */
	@ApiOperation(value = "提交审批", notes = "提交审批")
	@PostMapping("/gotoshenpi")
	public void gotoshenpi(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws IOException {
		String get_applyevent_url = QYWXURL.GET_APPLYEVENT_URL;
		log.info("--------------------jsonObject:" + jsonObject);
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			/*return R.failed("请维护企业微信corpid！");*/
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		String json = jsonObject.toJSONString();
		log.info("--------------------json:" + json);

		//获取返回信息
		ResponseEntity<JSONObject> result = restTemplate.postForEntity(get_applyevent_url + "?access_token=" + access_token, jsonObject, JSONObject.class);
		log.info("--------------------result:" + result);
		if (result.getBody() != null) {
			log.info("--------------------result.getBody():" + result.getBody());
		}
		//1. 第一种方法，指定输出到客户端的时候，这些文字使用UTF-8编码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		response.getWriter().print(result.getBody());
	}


	/**
	 * 查询审批申请当前状态
	 * @param thirdNo
	 * @return
	 */
	@ApiOperation(value = "查询审批申请当前状态", notes = "查询审批申请当前状态")
	@PostMapping("/getApprovalInfo")
	public void getApprovalInfo(@RequestParam("thirdNo") String thirdNo, HttpServletResponse response) throws IOException {
		String get_approvalinfo_url = QYWXURL.GET_APPROVALINFO_URL;
		log.info("--------------------审批单号thirdNo:" + thirdNo);
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			/*return R.failed("请维护企业微信corpid！");*/
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		HashMap<String, String> map = new HashMap<>();
		map.put("thirdNo", thirdNo);

		//获取返回信息
		ResponseEntity<String> result = restTemplate.postForEntity(get_approvalinfo_url + "?access_token=" + access_token, map, String.class);
		log.info("--------------------result:" + result);
		log.info("--------------------result.getBody():" + result.getBody());

		//1. 第一种方法，指定输出到客户端的时候，这些文字使用UTF-8编码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		response.getWriter().print(result.getBody());
	}

	/**
	 * 查询审批详情
	 * @param thirdNo
	 * @return
	 */
	@ApiOperation(value = "查询审批详情", notes = "查询审批详情")
	@PostMapping("/getApprovalDetail")
	public void getApprovalDetail(@RequestParam("thirdNo") String thirdNo, HttpServletResponse response) throws IOException {
		String get_approvaldetail_url = QYWXURL.GET_APPROVALDETAIL_URL;
		log.info("--------------------审批单号thirdNo:" + thirdNo);
		//获取qywxcorpid
		PigxUser pigxUser = SecurityUtils.getUser();
//		String qywxcorpid = pigxUser.getQywxCorpid();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!org.springframework.util.StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			/*return R.failed("请维护企业微信corpid！");*/
		}
		log.info("--------------------授权方的qywxcorpid:" + qywxcorpid);
		//获取access_token
		String access_token = sysQywxApplicationService.getCORPAccessToken(qywxcorpid);
		HashMap<String, String> map = new HashMap<>();
		map.put("sp_no", thirdNo);

		//获取返回信息
		ResponseEntity<String> result = restTemplate.postForEntity(get_approvaldetail_url + "?access_token=" + access_token, map, String.class);
		log.info("--------------------result:" + result);
		log.info("--------------------result.getBody():" + result.getBody());

		//1. 第一种方法，指定输出到客户端的时候，这些文字使用UTF-8编码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		response.getWriter().print(result.getBody());
	}

	/**
	 * 更新全局变量
	 *
	 * @param taskEntity
	 * @return
	 */
	@ApiOperation(value = "更新全局变量", notes = "更新全局变量")
	@PostMapping("/updateVariables")
	public R updateVariables(@RequestBody TaskActionEntity taskEntity) {
		return proInstWorkService.updateVariables(taskEntity);
	}

	/**
	 * 完成待办
	 *
	 * @param taskEntity 启动实例参数
	 * @return 实例
	 */
	@ApiOperation(value = "完成待办", notes = "完成待办")
	@PostMapping("/overtask")
	public R overtask(@RequestBody TaskActionEntity taskEntity) {

		BscInstTaskOpt task = new BscInstTaskOpt();
		task.setTaskid(taskEntity.getTaskid());
		task.setComment(taskEntity.getComment());
		task.setOptflag(taskEntity.getOptflag());

		JSONObject params = taskEntity.getQuanjubianliang();

		if (params != null) {

			Map<String, Object> mm = JSON.parseObject(params.toString(), Map.class);

			if(mm.size()>0){

				task.setParams(mm);

				if(mm.containsKey("usercode") && mm.get("usercode")!=null){

					task.setUsercode(mm.get("usercode").toString());

					mm.remove("usercode");
				}

			}

		}

		return proInstWorkService.completeMyTask(task);
	}


	/**
	 * 流程实例化
	 *
	 * @param taskEntity 启动实例参数
	 * @return 实例
	 */
	@ApiOperation(value = "流程实例化", notes = "流程实例化")
	@PostMapping("/shilihua")
	public R shilihua(@RequestBody TaskActionEntity taskEntity) {
		return proInstWorkService.shilihua(taskEntity);
	}


	/**
	 * 流程定义列表翻页
	 *
	 * @param taskEntity 启动实例参数
	 * @return 实例
	 */
	@ApiOperation(value = "流程定义列表翻页", notes = "流程定义列表翻页")
	@GetMapping("/dingyipage")
	public R dingyipage(Page page, TaskActionEntity taskEntity) {

		if (taskEntity == null || (StringUtils.isBlank(taskEntity.getDingyiid()) && StringUtils.isBlank(taskEntity.getDingyikey()))) {
			taskEntity = new TaskActionEntity();
			taskEntity.setDingyiid("");
			taskEntity.setDingyikey("");
		}

		PageHelper.startPage(Integer.parseInt(page.getCurrent() + ""), Integer.parseInt(page.getSize() + ""));
		List<DingyiEntity> dingyilist = proInstWorkService.dingyilist(taskEntity.getDingyiid(), taskEntity.getDingyikey());
		return R.ok(PageUtil.getIpage(dingyilist));
	}


	/**
	 * 流程实例列表翻页
	 *
	 * @param taskEntity
	 * @return
	 */
	@ApiOperation(value = "流程实例列表翻页", notes = "流程实例列表翻页")
	@GetMapping("/shilipage")
	public R shilipage(Page page, TaskActionEntity taskEntity) {

		if (taskEntity == null || (StringUtils.isBlank(taskEntity.getDingyiid()) && StringUtils.isBlank(taskEntity.getInstid()) && StringUtils.isBlank(taskEntity.getYwid()))) {
			taskEntity = new TaskActionEntity();
			taskEntity.setDingyiid("");
			taskEntity.setInstid("");
			taskEntity.setYwid("");
		}

		PageHelper.startPage(Integer.parseInt(page.getCurrent() + ""), Integer.parseInt(page.getSize() + ""));
		List<InstEntity> instlist = proInstWorkService.shililist(taskEntity.getDingyiid(), taskEntity.getInstid(), taskEntity.getYwid());
		return R.ok(PageUtil.getIpage(instlist));
	}


	/**
	 * 任务列表翻页
	 *
	 * @param taskEntity
	 * @return
	 */
	@ApiOperation(value = "流程实例列表翻页", notes = "流程实例列表翻页")
	@GetMapping("/taskpage")
	public R taskpage(Page page, TaskActionEntity taskEntity) {

		if (taskEntity == null || (StringUtils.isBlank(taskEntity.getInstid()))) {
			taskEntity = new TaskActionEntity();
			taskEntity.setInstid("");
		}

		PageHelper.startPage(Integer.parseInt(page.getCurrent() + ""), Integer.parseInt(page.getSize() + ""));
		List<TaskEntity> instlist = proInstWorkService.tasklist(taskEntity.getInstid());
		return R.ok(PageUtil.getIpage(instlist));
	}



}
