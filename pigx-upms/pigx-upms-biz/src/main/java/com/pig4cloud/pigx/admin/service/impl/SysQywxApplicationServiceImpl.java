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
package com.pig4cloud.pigx.admin.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.SysQywxApplication;
import com.pig4cloud.pigx.admin.entity.SysQywxApplicationAuthorizer;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.SysQywxApplicationAuthorizerMapper;
import com.pig4cloud.pigx.admin.mapper.SysQywxApplicationMapper;
import com.pig4cloud.pigx.admin.mapper.SystcorpinfoMapper;
import com.pig4cloud.pigx.admin.service.SysPublicParamService;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationAuthorizerService;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.QYWXURL;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.qq.weixin.mp.aes.EnterpriseConst;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业微信第三方应用信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:38:59
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysQywxApplicationServiceImpl extends ServiceImpl<SysQywxApplicationMapper, SysQywxApplication> implements SysQywxApplicationService {
	private final SysQywxApplicationAuthorizerMapper sysQywxApplicationAuthorizerMapper;
	private final SysQywxApplicationMapper sysQywxApplicationMapper;
	private final SystcorpinfoMapper systcorpinfomapper;
	private final SysPublicParamService sysPublicParamService;
	private final SysQywxApplicationAuthorizerService sysQywxApplicationAuthorizerService;
	private final CacheManager cacheManager;
	@Resource
	private RestTemplate restTemplate;

	public void getSuiteApplication() {
		String suiteid = "";
		String suitesecret = "";
		String sencodingaeskey = "";
		String stoken = "";
		String scorpid = "";

		String suiteid_b = "";
		String suitesecret_b = "";
		String sencodingaeskey_b = "";
		String stoken_b = "";
		Cache cache = cacheManager.getCache(CacheConstants.PARAMS_DETAILS);
		if (cache != null && cache.get("SUITE_ID") != null && cache.get("SUITE_SECRET") != null && cache.get("STOKEN") != null && cache.get("SENCODINGAESKEY") != null && cache.get("SCORPID") != null) {
			suiteid = (String) cache.get("SUITE_ID").get();
			suitesecret = (String) cache.get("SUITE_SECRET").get();
			stoken = (String) cache.get("STOKEN").get();
			sencodingaeskey = (String) cache.get("SENCODINGAESKEY").get();
			scorpid = (String) cache.get("SCORPID").get();
			EnterpriseConst.SUITEID = suiteid;
			EnterpriseConst.SUITESECRET = suitesecret;
			EnterpriseConst.STOKEN = stoken;
			EnterpriseConst.SENCODINGAESKEY = sencodingaeskey;
			EnterpriseConst.SCORPID = scorpid;


		} else {
			suiteid = sysPublicParamService.getSysPublicParamKeyToValue("SUITE_ID");
			suitesecret = sysPublicParamService.getSysPublicParamKeyToValue("SUITE_SECRET");
			stoken = sysPublicParamService.getSysPublicParamKeyToValue("STOKEN");
			sencodingaeskey = sysPublicParamService.getSysPublicParamKeyToValue("SENCODINGAESKEY");
			scorpid = sysPublicParamService.getSysPublicParamKeyToValue("SCORPID");
			EnterpriseConst.SUITEID = suiteid;
			EnterpriseConst.SUITESECRET = suitesecret;
			EnterpriseConst.STOKEN = stoken;
			EnterpriseConst.SENCODINGAESKEY = sencodingaeskey;
			EnterpriseConst.SCORPID = scorpid;

		}

		if (cache != null && cache.get("SUITE_ID_B") != null && cache.get("SUITE_SECRET_B") != null && cache.get("STOKEN_B") != null && cache.get("SENCODINGAESKEY_B") != null ) {
			suiteid_b = (String) cache.get("SUITE_ID_B").get();
			suitesecret_b = (String) cache.get("SUITE_SECRET_B").get();
			stoken_b = (String) cache.get("STOKEN_B").get();
			sencodingaeskey_b = (String) cache.get("SENCODINGAESKEY_B").get();
			EnterpriseConst.SUITEID_B = suiteid_b;
			EnterpriseConst.SUITESECRET_B = suitesecret_b;
			EnterpriseConst.STOKEN_B = stoken_b;
			EnterpriseConst.SENCODINGAESKEY_B = sencodingaeskey_b;


		} else {
			suiteid_b = sysPublicParamService.getSysPublicParamKeyToValue("SUITE_ID_B");
			suitesecret_b = sysPublicParamService.getSysPublicParamKeyToValue("SUITE_SECRET_B");
			stoken_b = sysPublicParamService.getSysPublicParamKeyToValue("STOKEN_B");
			sencodingaeskey_b = sysPublicParamService.getSysPublicParamKeyToValue("SENCODINGAESKEY_B");
			EnterpriseConst.SUITEID_B = suiteid_b;
			EnterpriseConst.SUITESECRET_B = suitesecret_b;
			EnterpriseConst.STOKEN_B = stoken_b;
			EnterpriseConst.SENCODINGAESKEY_B = sencodingaeskey_b;

		}
	}

	public String getSuiteAccessToken(SysQywxApplication sysQywxApplication) {
		String suite_token_url = QYWXURL.SUITE_TOKEN_URL;
		HashMap<String, String> map = new HashMap<>();
		map.put("suite_id", sysQywxApplication.getSuiteId());
		map.put("suite_secret", sysQywxApplication.getSuiteSecret());
		map.put("suite_ticket", sysQywxApplication.getSuiteTicket());
		//获取token
		//log.info("restTemplate:之前");
		ResponseEntity<String> result = restTemplate.postForEntity(suite_token_url, map, String.class);
		log.info("result:" + result);
		log.info("result.getBody:" + result.getBody());
		if (result != null) {
			log.info("result:" + result);
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String suite_access_token = jsonmap.get("suite_access_token").toString();
			String expires_in = jsonmap.get("expires_in").toString();
			long lexpires_in = Long.parseLong(expires_in);
			String expiredate = DateUtils.getDateTimeaddMinus(lexpires_in);
			SysQywxApplication sysqa = new SysQywxApplication();
			sysqa.setId(sysQywxApplication.getId());
			sysqa.setSuiteAccessToken(suite_access_token);
			sysqa.setTokenExpiresTime(expiredate);
			updateById(sysqa);
			return suite_access_token;

		} else {

		}
		return "";
	}

	public String getPreAuthCode(SysQywxApplication sysQywxApplication) {
		String pre_auth_code_url = QYWXURL.PRE_AUTH_CODE_URL;
		HashMap<String, String> map = new HashMap<>();
		map.put("suite_access_token", sysQywxApplication.getSuiteAccessToken());
		//获取token
		ResponseEntity<String> result = restTemplate.getForEntity(pre_auth_code_url + "?suite_access_token={suite_access_token}", String.class, map);
		if (result != null) {
			log.info("result:" + result);
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String pre_auth_code = jsonmap.get("pre_auth_code").toString();
			String expires_in = jsonmap.get("expires_in").toString();
			long lexpires_in = Long.parseLong(expires_in);
			String expiredate = DateUtils.getDateTimeaddMinus(lexpires_in);
			SysQywxApplication sysqa = new SysQywxApplication();
			sysqa.setId(sysQywxApplication.getId());
			sysqa.setPreAuthCode(pre_auth_code);
			sysqa.setCodeExpiresTime(expiredate);
			updateById(sysqa);
			return pre_auth_code;

		} else {
			//throw new DeptException("获取token异常");
		}
		return "";
	}


	public String setSessionInfo() {

		QueryWrapper<SysQywxApplication> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("suite_id","wwf2ede2a6b9326291");
		SysQywxApplication sysQywxApplication = this.getOne(queryWrapper);
		String suite_token_url = QYWXURL.SET_SESSION_INFO;
		HashMap map = new HashMap<>();
		HashMap map2 = new HashMap<>();
		String suite_access_token = sysQywxApplication.getSuiteAccessToken();
		map.put("pre_auth_code", sysQywxApplication.getPreAuthCode());
		//map2.put("appid","[]");
		map2.put("auth_type","1");
		map.put("session_info", map2);
		//获取token
		//log.info("restTemplate:之前");
		ResponseEntity<String> result = restTemplate.postForEntity(suite_token_url + "?suite_access_token="+suite_access_token, map, String.class);

		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}
		return null;
	}
	public String setSessionInfoAddress() {

		QueryWrapper<SysQywxApplication> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("suite_id","ww9a171b67684cdfcf");
		SysQywxApplication sysQywxApplication = this.getOne(queryWrapper);
		String suite_token_url = QYWXURL.SET_SESSION_INFO;
		HashMap map = new HashMap<>();
		HashMap map2 = new HashMap<>();
		String suite_access_token = sysQywxApplication.getSuiteAccessToken();
		map.put("pre_auth_code", sysQywxApplication.getPreAuthCode());
		//map2.put("appid","[]");
		map2.put("auth_type","1");
		map.put("session_info", map2);
		//获取token
		//log.info("restTemplate:之前");
		ResponseEntity<String> result = restTemplate.postForEntity(suite_token_url + "?suite_access_token="+suite_access_token, map, String.class);

		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}
		return null;
	}

	public String getPermanentCodeAndAccessToken(SysQywxApplication sysQywxApplication) {
		String suite_token_url = QYWXURL.PERMANENT_CODE;
		HashMap<String, String> map = new HashMap<>();
		map.put("auth_code", sysQywxApplication.getAuthcode());
		String suite_access_token = sysQywxApplication.getSuiteAccessToken();
//		map.put("suite_access_token",sysQywxApplication.getSuiteAccessToken());
		//获取token
		log.info("获取永久授权方法内临时授权码2:auth_code:" + sysQywxApplication.getAuthcode());
		log.info("获取永久授权方法内:suite_access_token:" + sysQywxApplication.getSuiteAccessToken());
		//获取返回信息
		ResponseEntity<String> result = restTemplate.postForEntity(suite_token_url + "?suite_access_token=" + suite_access_token, map, String.class);
		log.info("result:" + result);
		log.info("result.getBody:" + result.getBody());
		JSONObject jsonmap = JSONObject.parseObject(result.getBody());
		String suite_access_token_new = null;
		if (jsonmap.get("access_token") != null){
			suite_access_token_new = jsonmap.get("access_token").toString();
			log.info("获取永久授权方法内:access_token:" + suite_access_token_new);
		}
		String access_token_expires_time = null;
		if (jsonmap.get("expires_in") != null) {
			String expires_in = jsonmap.get("expires_in").toString();
			long lexpires_in = Long.parseLong(expires_in);
			access_token_expires_time = DateUtils.getDateTimeaddMinus(lexpires_in);
			log.info("access_token过期时间:access_token_expires_time:" + access_token_expires_time);
		}
		String permanent_code = null;
		if (jsonmap.get("permanent_code") != null) {
			permanent_code = jsonmap.get("permanent_code").toString();
			log.info("企业微信永久授权码:permanent_code:" + permanent_code);
		}
		//授权方企业信息
		String auth_corp_info_in;
		String auth_corpid = null;
		String auth_corp_name = null;
		String auth_corp_type = null;
		String auth_corp_square_logo_url = null;
		int auth_corp_user_max = 0;
		String auth_corp_full_name = null;
		int auth_subject_type = 0;
		String auth_verified_end_time = null;
		String auth_corp_wxqrcode = null;
		String auth_corp_scale = null;
		String auth_corp_industry = null;
		String auth_corp_sub_industry = null;
		String auth_location = null;
		if (jsonmap.get("auth_corp_info") != null) {
			auth_corp_info_in = jsonmap.get("auth_corp_info").toString();
			JSONObject auth_corp_info = JSONObject.parseObject(auth_corp_info_in);
			log.info("授权方企业信息:auth_corp_info:" + auth_corp_info);
			if (auth_corp_info.get("corpid") != null) {
				auth_corpid = auth_corp_info.get("corpid").toString();
				log.info("授权方企业微信id:auth_corpid:" + auth_corpid);
			}
			if (auth_corp_info.get("corp_name") != null) {
				auth_corp_name = auth_corp_info.get("corp_name").toString();
				log.info("授权方企业名称:auth_corp_name:" + auth_corp_name);
			}
			if (auth_corp_info.get("corp_type") != null) {
				auth_corp_type = auth_corp_info.get("corp_type").toString();
				log.info("授权方企业类型，认证号：verified:auth_corp_type:" + auth_corp_type);
			}
			if (auth_corp_info.get("corp_square_logo_url") != null) {
				auth_corp_square_logo_url = auth_corp_info.get("corp_square_logo_url").toString();
				log.info("授权方企业方形头像:auth_corp_square_logo_url:" + auth_corp_square_logo_url);
			}
			if (auth_corp_info.get("corp_user_max") != null) {
				auth_corp_user_max = Integer.parseInt(auth_corp_info.get("corp_user_max").toString());
				log.info("授权方企业用户规模:auth_corp_user_max:" + auth_corp_user_max);
			}
			if (auth_corp_info.get("corp_full_name") != null) {
				auth_corp_full_name = auth_corp_info.get("corp_full_name").toString();
				log.info("授权方企业的主体名称:auth_corp_full_name:" + auth_corp_full_name);
			}
			if (auth_corp_info.get("subject_type") != null) {
				auth_subject_type = Integer.parseInt(auth_corp_info.get("subject_type").toString());
				log.info("企业类型，1. 企业; 2. 政府以及事业单位; 3. 其他组织, 4.团队号:auth_subject_type:" + auth_subject_type);
			}
			if (auth_corp_info.get("verified_end_time") != null) {
				String verified_end_time = auth_corp_info.get("verified_end_time").toString();
				int lexpires_verified_end_time = Integer.parseInt(verified_end_time);
				auth_verified_end_time = DateUtils.stampToDate(lexpires_verified_end_time);
				log.info("认证到期时间:auth_verified_end_time:" + auth_verified_end_time);
			}
			if (auth_corp_info.get("corp_wxqrcode") != null) {
				auth_corp_wxqrcode = auth_corp_info.get("corp_wxqrcode").toString();
				log.info("授权企业在微工作台（原企业号）的二维码:auth_corp_wxqrcode:" + auth_corp_wxqrcode);
			}
			if (auth_corp_info.get("corp_scale") != null) {
				auth_corp_scale = auth_corp_info.get("corp_scale").toString();
				log.info("企业规模:auth_corp_scale:" + auth_corp_scale);
			}
			if (auth_corp_info.get("corp_industry") != null) {
				auth_corp_industry = auth_corp_info.get("corp_industry").toString();
				log.info("企业所属行业:auth_corp_industry:" + auth_corp_industry);
			}
			if (auth_corp_info.get("corp_sub_industry") != null) {
				auth_corp_sub_industry = auth_corp_info.get("corp_sub_industry").toString();
				log.info("企业所属子行业:auth_corp_sub_industry:" + auth_corp_sub_industry);
			}
			if (auth_corp_info.get("location") != null) {
				auth_location = auth_corp_info.get("location").toString();
				log.info("企业所在地信息:auth_location:" + auth_location);
			}
		}
		String auth_info = null;
		if(jsonmap.get("auth_info") != null){
			auth_info = jsonmap.get("auth_info").toString();
			log.info("授权信息:auth_info:" + auth_info);
		}
		String auth_user_info = null;
		if(jsonmap.get("auth_info") != null) {
			auth_user_info = jsonmap.get("auth_user_info").toString();
			log.info("授权管理员的信息:auth_user_info:" + auth_user_info);
		}
		String dealer_corp_info = null;
		if (jsonmap.containsKey("dealer_corp_info")) {
			dealer_corp_info = jsonmap.get("dealer_corp_info").toString();
		}
		log.info("代理服务商企业信息:dealer_corp_info:" + dealer_corp_info);
		SysQywxApplicationAuthorizer authorizer = sysQywxApplicationAuthorizerMapper.queryBySAuthCorpid(auth_corpid,sysQywxApplication.getSuiteId());
		if (authorizer == null) {
			authorizer = new SysQywxApplicationAuthorizer();
		}
		//给实体（SysQywxApplicationAuthorizer）赋值
		authorizer.setId(authorizer.getId());
		authorizer.setPermanentCode(permanent_code);
		authorizer.setAccessToken(suite_access_token_new);
		authorizer.setAccessTokenExpiresTime(access_token_expires_time);
		authorizer.setSuiteId(sysQywxApplication.getSuiteId());
		authorizer.setAuthCorpid(auth_corpid);
		authorizer.setAuthCorpName(auth_corp_name);
		authorizer.setAuthCorpType(auth_corp_type);
		authorizer.setAuthCorpSquareLogoUrl(auth_corp_square_logo_url);
		authorizer.setAuthCorpUserMax(auth_corp_user_max);
		authorizer.setAuthCorpFullName(auth_corp_full_name);
		authorizer.setAuthSubjectType(auth_subject_type);
		authorizer.setAuthVerifiedEndTime(auth_verified_end_time);
		authorizer.setAuthCorpWxqrcode(auth_corp_wxqrcode);
		authorizer.setAuthCorpScale(auth_corp_scale);
		authorizer.setAuthCorpIndustry(auth_corp_industry);
		authorizer.setAuthCorpSubIndustry(auth_corp_sub_industry);
		authorizer.setAuthLocation(auth_location);
		authorizer.setAuthInfo(auth_info);
		authorizer.setAuthUserInfo(auth_user_info);
		authorizer.setDealerCorpInfo(dealer_corp_info);
		//执行保存或者更新操作
		QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("auth_corpid",auth_corpid);
		queryWrapper.eq("suite_id",sysQywxApplication.getSuiteId());
		SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer = sysQywxApplicationAuthorizerService.getOne(queryWrapper);
		if(StringUtils.isEmpty(sysQywxApplicationAuthorizer)){
			sysQywxApplicationAuthorizerService.save(authorizer);
		}else{
			UpdateWrapper<SysQywxApplicationAuthorizer> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("auth_corpid",auth_corpid);
			queryWrapper.eq("suite_id",sysQywxApplication.getSuiteId());
			sysQywxApplicationAuthorizerService.update(authorizer,updateWrapper);
		}


		return permanent_code;

	}

	@Override
	public String selectUser(Map map) {
		String select_user_url = QYWXURL.SELECT_USER_URL;
		String qywxcorpid = null;
		if (map.get("qywxcorpid").toString() != null) {
			qywxcorpid = map.get("qywxcorpid").toString();
			log.info("成员企业的corpid---------------------------qywxcorpid:" + qywxcorpid);
		}
		//获取当前用户的企业access_token
		String crop_access_token = getCORPAddressAccessToken(qywxcorpid);
		log.info("----------------------------------------当前用户的企业access_token:" + crop_access_token);
		int userid = 0;
		if (map.containsKey("id") && map.get("id") != null) {
			userid = Integer.parseInt(map.get("userid").toString());
			log.info("成员UserID---------------------------userid:" + userid);
		}

		//调用接口
		ResponseEntity<String> result = restTemplate.getForEntity(select_user_url + "?access_token=" + crop_access_token + "&userid=" + userid, String.class);
		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}

		return null;
	}

	@Override
	public String createUser(Map map) {
		String creat_user_url = QYWXURL.CREAT_USER_URL;
		//获取返回参数
		String code = getUserMapB(map, creat_user_url);

		return code;
	}

	@Override
	public String updateUser(Map map) {
		String update_user_url = QYWXURL.UPDATE_USER_URL;
		//获取返回参数
		String code = getUserMapB(map, update_user_url);

		return code;
	}

	@Override
	public String deleteUser(Map map) {
		String del_user_url = QYWXURL.DEL_USER_URL;
		String qywxcorpid = null;
		if (map.get("qywxcorpid").toString() != null) {
			qywxcorpid = map.get("qywxcorpid").toString();
			log.info("成员企业的corpid---------------------------qywxcorpid:" + qywxcorpid);
		}
		//获取当前用户的企业access_token
		String crop_access_token = getCORPAddressAccessToken(qywxcorpid);
		log.info("----------------------------------------当前用户的企业access_token:" + crop_access_token);
		int userid = 0;
		if (map.containsKey("id") && map.get("id") != null) {
			userid = Integer.parseInt(map.get("userid").toString());
			log.info("成员UserID---------------------------userid:" + userid);
		}

		//调用接口
		ResponseEntity<String> result = restTemplate.getForEntity(del_user_url + "?access_token=" + crop_access_token + "&userid=" + userid, String.class);
		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}

		return null;
	}

	@Override
	public String selectDepartment(Map map) {
		String select_dept_url = QYWXURL.LIST_DEPT_URL;
		String qywxcorpid = null;
		if (map.get("qywxcorpid") != null) {
			qywxcorpid = map.get("qywxcorpid").toString();
			log.info("成员企业的corpid---------------------------qywxcorpid:" + qywxcorpid);
		}
		//获取当前用户的企业access_token
		String crop_access_token = getCORPAddressAccessToken(qywxcorpid);
		log.info("----------------------------------------当前用户的企业access_token:" + crop_access_token);
		int id = 0;
		if (map.containsKey("id") && map.get("id") != null) {
			id = Integer.parseInt(map.get("id").toString());
			log.info("部门id---------------------------id:" + id);
		}

		//调用接口
		//ResponseEntity<String> result11 = restTemplate.getForEntity("https://api.uukit.com/req/mock/on5skn" + "?access_token=" +crop_access_token+ "&id=" + id, String.class);
		ResponseEntity<String> result = restTemplate.getForEntity(select_dept_url + "?access_token=" + crop_access_token + "&id=" + id, String.class);
		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}

		return null;
	}
	@Override
	public String createDepartment(Map map) {
		String creat_dept_url = QYWXURL.CREAT_DEPT_URL;
		//获取返回参数
		String code = getDeptMapB(map, creat_dept_url);

		return code;
	}

	@Override
	public String updateDepartment(Map map) {
		String update_dept_url = QYWXURL.UPDATE_DEPT_URL;
		//获取返回参数
		String code = getDeptMapB(map, update_dept_url);

		return code;
	}

	@Override
	public String deleteDepartment(Map map) {
		String del_dept_url = QYWXURL.DEL_DEPT_URL;
		String qywxcorpid = null;
		if (map.get("qywxcorpid") != null) {
			qywxcorpid = map.get("qywxcorpid").toString();
			log.info("成员企业的corpid---------------------------qywxcorpid:" + qywxcorpid);
		}
		//获取当前用户的企业access_token
		String crop_access_token = getCORPAccessTokenB(qywxcorpid);
		log.info("----------------------------------------当前用户的企业access_token:" + crop_access_token);
		int id = 0;
		if (map.containsKey("id") && map.get("id") != null) {
			id = Integer.parseInt(map.get("id").toString());
			log.info("部门id---------------------------id:" + id);
		}

		//调用接口
		ResponseEntity<String> result = restTemplate.getForEntity(del_dept_url + "?access_token=" + crop_access_token + "&id=" + id, String.class);
		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}

		return null;
	}

	@Override
	public String sendMessage(Map map) {
		String send_msg_url = QYWXURL.SEND_MSG_URL;

		//返回值
		String code = null;
		//获取接收方
		if (!(map.containsKey("touser")) || !(map.containsKey("toparty")) || !(map.containsKey("totag"))) {
			//接收方不存在
			code = "接收方不存在";
			return code;
		}
		String touser = null;
		if ((map.containsKey("touser")) && (map.get("touser") != null)) {
			touser = map.get("touser").toString();
			log.info("接收人ID列表，多个接收者用‘|’分隔---------------------------touser:" + touser);
		}
		String toparty = null;
		if ((map.containsKey("toparty")) && (map.get("toparty") != null)) {
			toparty = map.get("toparty").toString();
			log.info("接收部门ID列表，多个接收者用‘|’分隔---------------------------toparty:" + toparty);
		}
		String totag = null;
		if ((map.containsKey("totag")) && (map.get("totag") != null)) {
			totag = map.get("totag").toString();
			log.info("接收标签ID列表，多个接收者用‘|’分隔---------------------------totag:" + totag);
		}
		String msgtype = null;
		if ((map.containsKey("msgtype")) && (map.get("msgtype") != null)) {
			msgtype = map.get("msgtype").toString();
			log.info("消息类型---------------------------------------------------msgtype:" + msgtype);
		}
		String agentid = null;
		if ((map.containsKey("agentid")) && (map.get("agentid") != null)) {
			agentid = map.get("agentid").toString();
			log.info("企业应用的id-----------------------------------------------agentid:" + agentid);
		}
		//文本消息
		String content = null;
		if ((map.containsKey("content")) && (map.get("content") != null)) {
			content = map.get("content").toString();
			log.info("文本消息内容-----------------------------------------------content:" + content);
		}
		//任务卡片消息
		String title = null;
		if ((map.containsKey("title")) && (map.get("title") != null)) {
			title = map.get("title").toString();
			log.info("标题，不超过128个字节---------------------------------------title:" + title);
		}
		String description = null;
		if ((map.containsKey("description")) && (map.get("description") != null)) {
			description = map.get("description").toString();
			log.info("描述，不超过512个字节---------------------------------------description:" + description);
		}
		String url = null;
		if ((map.containsKey("url")) && (map.get("url") != null)) {
			url = map.get("url").toString();
			log.info("点击后跳转的链接--------------------------------------------url:" + url);
		}
		String task_id = null;
		if ((map.containsKey("task_id")) && (map.get("task_id") != null)) {
			task_id = map.get("task_id").toString();
			log.info("任务id----------------------------------------------------task_id:" + task_id);
		}
		String btn = null;
		if ((map.containsKey("btn")) && (map.get("btn") != null)) {
			btn = map.get("btn").toString();
			log.info("按钮列表。--------------------------------------------------btn:" + btn);
		}
		String safe = null;
		if ((map.containsKey("safe")) && (map.get("safe") != null)) {
			safe = map.get("safe").toString();
			log.info("是否是保密消息，0表示否，1表示是，默认0------------------------safe:" + safe);
		}
		String enable_id_trans = null;
		if ((map.containsKey("enable_id_trans")) && (map.get("enable_id_trans") != null)) {
			enable_id_trans = map.get("enable_id_trans").toString();
			log.info("是否开启id转译，0表示否，1表示是，默认0------------------------enable_id_trans:" + enable_id_trans);
		}
		String enable_duplicate_check = null;
		if ((map.containsKey("enable_duplicate_check")) && (map.get("enable_duplicate_check") != null)) {
			enable_duplicate_check = map.get("enable_duplicate_check").toString();
			log.info("是否开启重复消息检查，0表示否，1表示是，默认0--------------------enable_duplicate_check:" + enable_duplicate_check);
		}
		String duplicate_check_interval = null;
		if ((map.containsKey("duplicate_check_interval")) && (map.get("duplicate_check_interval") != null)) {
			duplicate_check_interval = map.get("duplicate_check_interval").toString();
			log.info("是否重复消息检查的时间间隔，默认1800s，最大不超过4小时------------duplicate_check_interval:" + duplicate_check_interval);
		}
		HashMap<String, String> msgmap = new HashMap<>();
		if (StringUtil.isNotEmpty(touser)) {
			msgmap.put("touser", touser);
		}
		if (StringUtil.isNotEmpty(toparty)) {
			msgmap.put("toparty", toparty);
		}
		if (StringUtil.isNotEmpty(totag)) {
			msgmap.put("totag", totag);
		}
		if (StringUtil.isNotEmpty(msgtype)) {
			msgmap.put("msgtype", msgtype);
		}
		HashMap<String, String> msgTypemap = new HashMap<>();
		String json = null;
		switch (msgtype) {
			case "text":
				if (StringUtil.isNotEmpty(content)) {
					msgTypemap.put("content", content);
				}
				if (msgTypemap != null) {
					json = JSON.toJSONString(msgTypemap);
					msgmap.put("text", json);
				}
				break;
			case "taskcard":
				if (StringUtil.isNotEmpty(title)) {
					msgTypemap.put("title", title);
				}
				if (StringUtil.isNotEmpty(description)) {
					msgTypemap.put("description", description);
				}
				if (StringUtil.isNotEmpty(url)) {
					msgTypemap.put("url", url);
				}
				if (StringUtil.isNotEmpty(task_id)) {
					msgTypemap.put("task_id", task_id);
				}
				if (StringUtil.isNotEmpty(btn)) {
					msgTypemap.put("btn", btn);
				}

//				JSONObject allJson = new JSONObject();
//				allJson.put("textcard",msgTypemap);
				if (msgTypemap != null) {
					json = JSON.toJSONString(msgTypemap);
					;
					msgmap.put("textcard", json);
				}
				break;
		}
		msgmap.put("enable_id_trans", "0");
		msgmap.put("enable_duplicate_check", "0");
		msgmap.put("duplicate_check_interval", "0");
		//调用接口获取返回参数
		code = pushInfo(map, msgmap, send_msg_url);

		return code;
	}
	@Override
	public String sendMessageB(Map map) {
		String send_msg_url = QYWXURL.SEND_MSG_URL;

		//返回值
		String code = null;
		//获取接收方
		if (!(map.containsKey("touser")) || !(map.containsKey("toparty")) || !(map.containsKey("totag"))) {
			//接收方不存在
			code = "接收方不存在";
			return code;
		}
		String touser = null;
		if ((map.containsKey("touser")) && (map.get("touser") != null)) {
			touser = map.get("touser").toString();
			log.info("接收人ID列表，多个接收者用‘|’分隔---------------------------touser:" + touser);
		}
		String toparty = null;
		if ((map.containsKey("toparty")) && (map.get("toparty") != null)) {
			toparty = map.get("toparty").toString();
			log.info("接收部门ID列表，多个接收者用‘|’分隔---------------------------toparty:" + toparty);
		}
		String totag = null;
		if ((map.containsKey("totag")) && (map.get("totag") != null)) {
			totag = map.get("totag").toString();
			log.info("接收标签ID列表，多个接收者用‘|’分隔---------------------------totag:" + totag);
		}
		String msgtype = null;
		if ((map.containsKey("msgtype")) && (map.get("msgtype") != null)) {
			msgtype = map.get("msgtype").toString();
			log.info("消息类型---------------------------------------------------msgtype:" + msgtype);
		}
		String agentid = null;
		if ((map.containsKey("agentid")) && (map.get("agentid") != null)) {
			agentid = map.get("agentid").toString();
			log.info("企业应用的id-----------------------------------------------agentid:" + agentid);
		}
		//文本消息
		String content = null;
		if ((map.containsKey("content")) && (map.get("content") != null)) {
			content = map.get("content").toString();
			log.info("文本消息内容-----------------------------------------------content:" + content);
		}
		//任务卡片消息
		String title = null;
		if ((map.containsKey("title")) && (map.get("title") != null)) {
			title = map.get("title").toString();
			log.info("标题，不超过128个字节---------------------------------------title:" + title);
		}
		String description = null;
		if ((map.containsKey("description")) && (map.get("description") != null)) {
			description = map.get("description").toString();
			log.info("描述，不超过512个字节---------------------------------------description:" + description);
		}
		String url = null;
		if ((map.containsKey("url")) && (map.get("url") != null)) {
			url = map.get("url").toString();
			log.info("点击后跳转的链接--------------------------------------------url:" + url);
		}
		String task_id = null;
		if ((map.containsKey("task_id")) && (map.get("task_id") != null)) {
			task_id = map.get("task_id").toString();
			log.info("任务id----------------------------------------------------task_id:" + task_id);
		}
		String btn = null;
		if ((map.containsKey("btn")) && (map.get("btn") != null)) {
			btn = map.get("btn").toString();
			log.info("按钮列表。--------------------------------------------------btn:" + btn);
		}
		String safe = null;
		if ((map.containsKey("safe")) && (map.get("safe") != null)) {
			safe = map.get("safe").toString();
			log.info("是否是保密消息，0表示否，1表示是，默认0------------------------safe:" + safe);
		}
		String enable_id_trans = null;
		if ((map.containsKey("enable_id_trans")) && (map.get("enable_id_trans") != null)) {
			enable_id_trans = map.get("enable_id_trans").toString();
			log.info("是否开启id转译，0表示否，1表示是，默认0------------------------enable_id_trans:" + enable_id_trans);
		}
		String enable_duplicate_check = null;
		if ((map.containsKey("enable_duplicate_check")) && (map.get("enable_duplicate_check") != null)) {
			enable_duplicate_check = map.get("enable_duplicate_check").toString();
			log.info("是否开启重复消息检查，0表示否，1表示是，默认0--------------------enable_duplicate_check:" + enable_duplicate_check);
		}
		String duplicate_check_interval = null;
		if ((map.containsKey("duplicate_check_interval")) && (map.get("duplicate_check_interval") != null)) {
			duplicate_check_interval = map.get("duplicate_check_interval").toString();
			log.info("是否重复消息检查的时间间隔，默认1800s，最大不超过4小时------------duplicate_check_interval:" + duplicate_check_interval);
		}
		HashMap<String, String> msgmap = new HashMap<>();
		if (StringUtil.isNotEmpty(touser)) {
			msgmap.put("touser", touser);
		}
		if (StringUtil.isNotEmpty(toparty)) {
			msgmap.put("toparty", toparty);
		}
		if (StringUtil.isNotEmpty(totag)) {
			msgmap.put("totag", totag);
		}
		if (StringUtil.isNotEmpty(msgtype)) {
			msgmap.put("msgtype", msgtype);
		}
		HashMap<String, String> msgTypemap = new HashMap<>();
		String json = null;
		switch (msgtype) {
			case "text":
				if (StringUtil.isNotEmpty(content)) {
					msgTypemap.put("content", content);
				}
				if (msgTypemap != null) {
					json = JSON.toJSONString(msgTypemap);
					msgmap.put("text", json);
				}
				break;
			case "taskcard":
				if (StringUtil.isNotEmpty(title)) {
					msgTypemap.put("title", title);
				}
				if (StringUtil.isNotEmpty(description)) {
					msgTypemap.put("description", description);
				}
				if (StringUtil.isNotEmpty(url)) {
					msgTypemap.put("url", url);
				}
				if (StringUtil.isNotEmpty(task_id)) {
					msgTypemap.put("task_id", task_id);
				}
				if (StringUtil.isNotEmpty(btn)) {
					msgTypemap.put("btn", btn);
				}

//				JSONObject allJson = new JSONObject();
//				allJson.put("textcard",msgTypemap);
				if (msgTypemap != null) {
					json = JSON.toJSONString(msgTypemap);
					;
					msgmap.put("textcard", json);
				}
				break;
		}
		msgmap.put("enable_id_trans", "0");
		msgmap.put("enable_duplicate_check", "0");
		msgmap.put("duplicate_check_interval", "0");
		//调用接口获取返回参数
		code = pushInfoB(map, msgmap, send_msg_url);

		return code;
	}

	@Override
	public String updateSendMessage(Map map) {
		String update_send_msg_url = QYWXURL.UPDATE_SEND_MSG_URL;

		String userids = null;
		if ((map.containsKey("userids")) && (map.get("userids").toString() != null)) {
			userids = map.get("userids").toString();
			log.info("企业的成员ID列表------------------------------------------userids:" + userids);
		}
		String agentid = null;
		if ((map.containsKey("agentid")) && (map.get("agentid").toString() != null)) {
			agentid = map.get("agentid").toString();
			log.info("应用的agentid--------------------------------------------agentid:" + agentid);
		}
		String task_id = null;
		if ((map.containsKey("task_id")) && (map.get("task_id").toString() != null)) {
			task_id = map.get("task_id").toString();
			log.info("发送任务卡片消息时指定的task_id-----------------------------task_id:" + task_id);
		}
		String clicked_key = null;
		if ((map.containsKey("clicked_key")) && (map.get("clicked_key").toString() != null)) {
			clicked_key = map.get("clicked_key").toString();
			log.info("设置指定的按钮为选择状态，需要与发送消息时指定的btn:key一致------clicked_key:" + clicked_key);
		}

		HashMap<String, String> msgmap = new HashMap<>();
		if (StringUtil.isNotEmpty(userids)) {
			msgmap.put("userids", userids);
		}
		if (StringUtil.isNotEmpty(agentid)) {
			msgmap.put("agentid", agentid);
		}
		if (StringUtil.isNotEmpty(task_id)) {
			msgmap.put("task_id", task_id);
		}
		if (StringUtil.isNotEmpty(clicked_key)) {
			msgmap.put("clicked_key", clicked_key);
		}
		//调用接口获取返回参数
		String code = pushInfo(map, msgmap, update_send_msg_url);

		return code;
	}

	@Override
	public String updateSendMessageB(Map map) {
		String update_send_msg_url = QYWXURL.UPDATE_SEND_MSG_URL;

		String userids = null;
		if ((map.containsKey("userids")) && (map.get("userids").toString() != null)) {
			userids = map.get("userids").toString();
			log.info("企业的成员ID列表------------------------------------------userids:" + userids);
		}
		String agentid = null;
		if ((map.containsKey("agentid")) && (map.get("agentid").toString() != null)) {
			agentid = map.get("agentid").toString();
			log.info("应用的agentid--------------------------------------------agentid:" + agentid);
		}
		String task_id = null;
		if ((map.containsKey("task_id")) && (map.get("task_id").toString() != null)) {
			task_id = map.get("task_id").toString();
			log.info("发送任务卡片消息时指定的task_id-----------------------------task_id:" + task_id);
		}
		String clicked_key = null;
		if ((map.containsKey("clicked_key")) && (map.get("clicked_key").toString() != null)) {
			clicked_key = map.get("clicked_key").toString();
			log.info("设置指定的按钮为选择状态，需要与发送消息时指定的btn:key一致------clicked_key:" + clicked_key);
		}

		HashMap<String, String> msgmap = new HashMap<>();
		if (StringUtil.isNotEmpty(userids)) {
			msgmap.put("userids", userids);
		}
		if (StringUtil.isNotEmpty(agentid)) {
			msgmap.put("agentid", agentid);
		}
		if (StringUtil.isNotEmpty(task_id)) {
			msgmap.put("task_id", task_id);
		}
		if (StringUtil.isNotEmpty(clicked_key)) {
			msgmap.put("clicked_key", clicked_key);
		}
		//调用接口获取返回参数
		String code = pushInfoB(map, msgmap, update_send_msg_url);

		return code;
	}

	/**
	 * 获取访问用户的身份
	 */
	@Override
	public String getuserinfo3red(String suiteAccessToken, String code) {
		//返回值
		String userid = null;
		String get_userinfo3rd_url = QYWXURL.GET_USERINFO3RD_URL;
		//调用接口获得当前用户企业的access_token
		ResponseEntity<String> result = restTemplate.getForEntity(get_userinfo3rd_url + "?suite_access_token=" + suiteAccessToken + "&code=" + code, String.class);

		if (result != null) {
			log.info("result:" + result);
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			log.info("result.getBody:" + result.getBody());
			if (jsonmap.get("UserId") != null) {
				userid = jsonmap.get("UserId").toString();
			}
			log.info("------------------------------------获取到当前用户的userid:" + userid);
			return userid;
		}
		return null;
	}

	@Override
	public String getCORPAccessToken(String corpid) {
		String get_corp_token_url = QYWXURL.GET_CORP_TOKEN_URL;
		//获取access_token
		//获取suite_access_token
		getSuiteApplication();
		String suiteid = EnterpriseConst.SUITEID;
		SysQywxApplicationAuthorizer entry = sysQywxApplicationAuthorizerMapper.queryBySAuthCorpid(corpid,suiteid);
		String access_token = entry.getAccessToken();
		log.info("--------------------表中查出当前的access_token:" + access_token);
		String access_token_expires_time = entry.getAccessTokenExpiresTime();
		log.info("--------------------表中查出当前的access_token过期时间:" + access_token_expires_time);
		String nowtime = DateUtils.getNow("yyyy-MM-dd HH:mm:ss");
		//查看当前用户企业的access_token是否过期(true:未过期 false：已过期)
		boolean falg = DateUtils.compareDate(access_token_expires_time, nowtime);
		if (falg) {
			//没有过期，直接返回
			return access_token;
		} else {        //已过期，重新获取

			SysQywxApplication sysQywxApplication = sysQywxApplicationMapper.queryBySuiteId(suiteid);
			String suite_access_token = sysQywxApplication.getSuiteAccessToken();
			log.info("--------------------第三方应用的suite_access_token:" + suite_access_token);
			String permanent_code = entry.getPermanentCode();
			log.info("--------------------授权方企业的永久授权码permanent_code:" + permanent_code);
			HashMap<String, String> map = new HashMap<>();
			map.put("auth_corpid", corpid);
			map.put("permanent_code", permanent_code);
			//调用接口获得当前用户企业的access_token
			ResponseEntity<String> result = restTemplate.postForEntity(get_corp_token_url + "?suite_access_token=" + suite_access_token, map, String.class);
			log.info("--------------------result:" + result);
			if (result != null) {
				log.info("--------------------result.getBody:" + result.getBody());
				JSONObject jsonmap = JSONObject.parseObject(result.getBody());
				String access_token_new = jsonmap.get("access_token").toString();
				log.info("--------------------重新获取到当前用户企业的access_token_new:" + access_token_new);
				String expires_in = jsonmap.get("expires_in").toString();
				long lexpires_in = Long.parseLong(expires_in);
				String expires_time_new = DateUtils.getDateTimeaddMinus(lexpires_in);
				log.info("--------------------重新获取到当前用户企业的access_token过期时间expires_time_new:" + expires_time_new);
				entry.setId(entry.getId());
				entry.setAccessToken(access_token_new);
				entry.setAccessTokenExpiresTime(expires_time_new);
				//执行保存或者更新操作
				sysQywxApplicationAuthorizerService.saveOrUpdate(entry);
				return access_token_new;
			}
		}

		return null;
	}
	@Override
	public String getCORPAccessTokenB(String corpid) {
		String get_corp_token_url = QYWXURL.GET_CORP_TOKEN_URL;
		//获取access_token
		//获取suite_access_token
		getSuiteApplication();
		String suiteid = EnterpriseConst.SUITEID_B;
		SysQywxApplicationAuthorizer entry = sysQywxApplicationAuthorizerMapper.queryBySAuthCorpid(corpid,suiteid);
		String access_token = entry.getAccessToken();
		log.info("--------------------表中查出当前的access_token:" + access_token);
		String access_token_expires_time = entry.getAccessTokenExpiresTime();
		log.info("--------------------表中查出当前的access_token过期时间:" + access_token_expires_time);
		String nowtime = DateUtils.getNow("yyyy-MM-dd HH:mm:ss");
		//查看当前用户企业的access_token是否过期(true:未过期 false：已过期)
		boolean falg = DateUtils.compareDate(access_token_expires_time, nowtime);
		if (falg) {
			//没有过期，直接返回
			return access_token;
		} else {        //已过期，重新获取

			SysQywxApplication sysQywxApplication = sysQywxApplicationMapper.queryBySuiteId(suiteid);
			String suite_access_token = sysQywxApplication.getSuiteAccessToken();
			log.info("--------------------第三方应用的suite_access_token:" + suite_access_token);
			String permanent_code = entry.getPermanentCode();
			log.info("--------------------授权方企业的永久授权码permanent_code:" + permanent_code);
			HashMap<String, String> map = new HashMap<>();
			map.put("auth_corpid", corpid);
			map.put("permanent_code", permanent_code);
			//调用接口获得当前用户企业的access_token
			ResponseEntity<String> result = restTemplate.postForEntity(get_corp_token_url + "?suite_access_token=" + suite_access_token, map, String.class);
			log.info("--------------------result:" + result);
			if (result != null) {
				log.info("--------------------result.getBody:" + result.getBody());
				JSONObject jsonmap = JSONObject.parseObject(result.getBody());
				String access_token_new = jsonmap.get("access_token").toString();
				log.info("--------------------重新获取到当前用户企业的access_token_new:" + access_token_new);
				String expires_in = jsonmap.get("expires_in").toString();
				long lexpires_in = Long.parseLong(expires_in);
				String expires_time_new = DateUtils.getDateTimeaddMinus(lexpires_in);
				log.info("--------------------重新获取到当前用户企业的access_token过期时间expires_time_new:" + expires_time_new);
				entry.setId(entry.getId());
				entry.setAccessToken(access_token_new);
				entry.setAccessTokenExpiresTime(expires_time_new);
				//执行保存或者更新操作
				sysQywxApplicationAuthorizerService.saveOrUpdate(entry);
				return access_token_new;
			}
		}

		return null;
	}


	/*
	 * 获取当前企业同步通讯录的access_token
	 * */
	public String getCORPAddressAccessToken(String qywxcorpid) {
		String get_address_token_url = QYWXURL.GET_ADDRESS_TOKEN_URL;
		log.info("------------------------------------当前企业的通讯录的qywxcorpid:" + qywxcorpid);
		getSuiteApplication();
		String suiteid = EnterpriseConst.SUITEID_B;
		//获取企业的address_access_token
		SysQywxApplicationAuthorizer entry = sysQywxApplicationAuthorizerMapper.queryBySAuthCorpid(qywxcorpid,suiteid);
		String address_access_token = entry.getAddressAccessToken();
		log.info("------------------------------------表中查出当前企业通讯录的access_token:" + address_access_token);
		String access_token_expires_time = entry.getAddressAccessTokenTime();
		log.info("------------------------------------表中查出当前企业通讯录的access_token过期时间:" + access_token_expires_time);
		String nowtime = DateUtils.getNow("yyyy-MM-dd HH:mm:ss");
		//查看当前用户企业的access_token是否过期(true:未过期 false：已过期)
		boolean falg = true;
		if(StringUtils.isEmpty(access_token_expires_time)){
			falg = false;
		}else{
			falg = DateUtils.compareDate(access_token_expires_time, nowtime);
		}
		if (falg) {
			//没有过期，直接返回
			return address_access_token;
		} else {        //已过期，重新获取
			//获取通讯录的密钥
			Systcorpinfo systcorpinfo = systcorpinfomapper.seachAddressSecretByQywxCorpid(qywxcorpid);
			String addresssecret = systcorpinfo.getAddresssecret();
			log.info("------------------------------------当前企业的通讯录的密钥addresssecret:" + addresssecret);
			//调用接口获得当前用户企业的access_token
			ResponseEntity<String> result = restTemplate.getForEntity(get_address_token_url + "?corpid=" + qywxcorpid + "&corpsecret=" + addresssecret, String.class);
			if (result != null) {
				log.info("result:" + result);
				log.info("result.getBody:" + result.getBody());
				JSONObject jsonmap = JSONObject.parseObject(result.getBody());
				String access_token_new = jsonmap.get("access_token").toString();
				log.info("------------------------------------重新获取到当前用户企业的access_token_new:" + access_token_new);
				String expires_in = jsonmap.get("expires_in").toString();
				long lexpires_in = Long.parseLong(expires_in);
				String expires_time_new = DateUtils.getDateTimeaddMinus(lexpires_in);
				log.info("------------------------------------重新获取到当前用户企业的expires_time_new:" + expires_time_new);
				entry.setId(entry.getId());
				entry.setAddressAccessToken(access_token_new);
				entry.setAddressAccessTokenTime(expires_time_new);
				//执行保存或者更新操作
				sysQywxApplicationAuthorizerService.saveOrUpdate(entry);

				return access_token_new;
			}
		}
		return null;
	}

	/*
	 * 组装用户信息到hashmap
	 * */
	public String getUserMap(Map map, String url) {
		//返回值
		String code = null;
		if (map == null) {
			code = "map为空";
			return code;
		}
		//获取user信息
		String userid = null;
		if (map.get("userid") != null) {
			userid = map.get("userid").toString();
			log.info("成员UserID---------------------------userid:" + userid);
		} else {
			code = "成员userid为空";
			return code;
		}
		String name = null;
		if (url.contains("create")) {
			if (map.get("name") != null) {
				name = map.get("name").toString();
				log.info("成员名称---------------------------name:" + name);
			} else {
				code = "成员名称name为空";
				return code;
			}
		}
		String alias = null;
		if (map.get("alias") != null) {
			alias = map.get("alias").toString();
			log.info("成员别名------------------------------alias:" + alias);
		}
		String mobile = null;
		if (map.get("mobile") != null) {
			mobile = map.get("mobile").toString();
			log.info("手机号码------------------------------mobile:" + mobile);
		}
		String department = null;
		if (url.contains("create")) {
			if (map.get("department") != null) {
				department = map.get("department").toString();
				log.info("成员所属部门id列表---------------------department:" + department);
			} else {
				code = "成员所属部门department为空";
				return code;
			}
		}
		String order = null;
		if (map.get("order") != null) {
			order = map.get("order").toString();
			log.info("部门内的排序值，默认为0-----------------order:" + order);
		}
		String position = null;
		if (map.get("position") != null) {
			position = map.get("position").toString();
			log.info("职务信息------------------------------position:" + position);
		}
		String gender = null;
		if (map.get("gender") != null) {
			gender = map.get("gender").toString();
			log.info("性别。（1表示男性，2表示女性）-----------gender:" + gender);
		}
		String email = null;
		if (map.get("email") != null) {
			email = map.get("email").toString();
			log.info("邮箱---------------------------------email:" + email);
		}
		String is_leader_in_dept = null;
		if (map.get("is_leader_in_dept") != null) {
			is_leader_in_dept = map.get("is_leader_in_dept").toString();
			log.info("表示在所在的部门内是否为上级。（1表示为上级，0表示非上级）is_leader_in_dept:" + is_leader_in_dept);
		}
		String avatar_mediaid = null;
		if (map.get("avatar_mediaid") != null) {
			avatar_mediaid = map.get("avatar_mediaid").toString();
			log.info("成员头像的mediaid----------------------avatar_mediaid:" + avatar_mediaid);
		}
		String enable = null;
		if (map.get("enable") != null) {
			enable = map.get("enable").toString();
			log.info("启用/禁用成员。（1表示启用成员，0表示禁用成员）enable:" + enable);
		}
		String to_invite = null;
		if (map.get("to_invite") != null) {
			to_invite = map.get("to_invite").toString();
			log.info("是否邀请该成员使用企业微信----------------to_invite:" + to_invite);
		}
		String address = null;
		if (map.get("address") != null) {
			address = map.get("address").toString();
			log.info("地址-----------------------------------address:" + address);
		}
		String main_department = null;
		if (map.get("main_department") != null) {
			main_department = map.get("main_department").toString();
			log.info("主部门----------------------------------main_department:" + main_department);
		}

		//判断是创建还是更新
		if (url.contains("create")) {
			//判断"mobile/email二者不能同时为空"
			if (StringUtil.isNotEmpty(mobile) && StringUtil.isNotEmpty(email)) {
				code = "mobile/email二者不能同时为空";
				return code;
			}
		}
		HashMap<String, String> usermap = new HashMap<>();

		//插入user信息
		if (StringUtil.isNotEmpty(userid)) {
			usermap.put("\"userid\"", "\""+userid+"\"");
		}
		if (StringUtil.isNotEmpty(name)) {
			usermap.put("\"name\"", "\""+name+"\"");
		}
		if (StringUtil.isNotEmpty(alias)) {
			usermap.put("\"alias\"", "\""+alias+"\"");
		}
		if (StringUtil.isNotEmpty(mobile)) {
			usermap.put("\"mobile\"", "\""+mobile+"\"");
		}
		if (StringUtil.isNotEmpty(department)) {
			usermap.put("\"department\"", "["+department+"]");
		}
		if (StringUtil.isNotEmpty(order)) {
			usermap.put("order", order);
		}
		if (StringUtil.isNotEmpty(position)) {
			usermap.put("position", position);
		}
		if (StringUtil.isNotEmpty(gender)) {
			usermap.put("gender", gender);
		}
		if (StringUtil.isNotEmpty(email)) {
			usermap.put("email", email);
		}
		if (StringUtil.isNotEmpty(is_leader_in_dept)) {
			usermap.put("is_leader_in_dept", is_leader_in_dept);
		}
		if (StringUtil.isNotEmpty(avatar_mediaid)) {
			usermap.put("avatar_mediaid", avatar_mediaid);
		}
		if (StringUtil.isNotEmpty(enable)) {
			usermap.put("enable", enable);
		}
		if (StringUtil.isNotEmpty(to_invite)) {
			usermap.put("to_invite", to_invite);
		}
		if (StringUtil.isNotEmpty(address)) {
			usermap.put("address", address);
		}
		if (StringUtil.isNotEmpty(main_department)) {
			usermap.put("main_department", main_department);
		}
		log.info("封装的数据----------------------------------usermap:" + usermap);
		log.info("封装的数据----------------------------------usermaptoString:" + usermap.toString());
		//获取返回参数
		code = pushInfo(map, usermap, url);

		return code;
	}
	/*
	 * 组装用户信息到hashmap
	 * */
	public String getUserMapB(Map map, String url) {
		//返回值
		String code = null;
		if (map == null) {
			code = "map为空";
			return code;
		}
		//获取user信息
		String userid = null;
		if (map.get("userid") != null) {
			userid = map.get("userid").toString();
			log.info("成员UserID---------------------------userid:" + userid);
		} else {
			code = "成员userid为空";
			return code;
		}
		String name = null;
		if (url.contains("create")) {
			if (map.get("name") != null) {
				name = map.get("name").toString();
				log.info("成员名称---------------------------name:" + name);
			} else {
				code = "成员名称name为空";
				return code;
			}
		}
		String alias = null;
		if (map.get("alias") != null) {
			alias = map.get("alias").toString();
			log.info("成员别名------------------------------alias:" + alias);
		}
		String mobile = null;
		if (map.get("mobile") != null) {
			mobile = map.get("mobile").toString();
			log.info("手机号码------------------------------mobile:" + mobile);
		}
		String department = null;
		if (url.contains("create")) {
			if (map.get("department") != null) {
				department = map.get("department").toString();
				log.info("成员所属部门id列表---------------------department:" + department);
			} else {
				code = "成员所属部门department为空";
				return code;
			}
		}
		String order = null;
		if (map.get("order") != null) {
			order = map.get("order").toString();
			log.info("部门内的排序值，默认为0-----------------order:" + order);
		}
		String position = null;
		if (map.get("position") != null) {
			position = map.get("position").toString();
			log.info("职务信息------------------------------position:" + position);
		}
		String gender = null;
		if (map.get("gender") != null) {
			gender = map.get("gender").toString();
			log.info("性别。（1表示男性，2表示女性）-----------gender:" + gender);
		}
		String email = null;
		if (map.get("email") != null) {
			email = map.get("email").toString();
			log.info("邮箱---------------------------------email:" + email);
		}
		String is_leader_in_dept = null;
		if (map.get("is_leader_in_dept") != null) {
			is_leader_in_dept = map.get("is_leader_in_dept").toString();
			log.info("表示在所在的部门内是否为上级。（1表示为上级，0表示非上级）is_leader_in_dept:" + is_leader_in_dept);
		}
		String avatar_mediaid = null;
		if (map.get("avatar_mediaid") != null) {
			avatar_mediaid = map.get("avatar_mediaid").toString();
			log.info("成员头像的mediaid----------------------avatar_mediaid:" + avatar_mediaid);
		}
		String enable = null;
		if (map.get("enable") != null) {
			enable = map.get("enable").toString();
			log.info("启用/禁用成员。（1表示启用成员，0表示禁用成员）enable:" + enable);
		}
		String to_invite = null;
		if (map.get("to_invite") != null) {
			to_invite = map.get("to_invite").toString();
			log.info("是否邀请该成员使用企业微信----------------to_invite:" + to_invite);
		}
		String address = null;
		if (map.get("address") != null) {
			address = map.get("address").toString();
			log.info("地址-----------------------------------address:" + address);
		}
		String main_department = null;
		if (map.get("main_department") != null) {
			main_department = map.get("main_department").toString();
			log.info("主部门----------------------------------main_department:" + main_department);
		}

		//判断是创建还是更新
		if (url.contains("create")) {
			//判断"mobile/email二者不能同时为空"
			if (StringUtil.isNotEmpty(mobile) && StringUtil.isNotEmpty(email)) {
				code = "mobile/email二者不能同时为空";
				return code;
			}
		}
		HashMap<String,Object> usermap = new HashMap<>();
		List list = new ArrayList(1);
		//插入user信息
		if (StringUtil.isNotEmpty(userid)) {
			usermap.put("userid", userid);
		}
		if (StringUtil.isNotEmpty(name)) {
			usermap.put("name", name);
		}
		if (StringUtil.isNotEmpty(alias)) {
			usermap.put("alias", alias);
		}
		if (StringUtil.isNotEmpty(mobile)) {
			usermap.put("mobile",mobile);
		}
		if (StringUtil.isNotEmpty(department)) {
			list.add(department);
			usermap.put("department", list);
		}
		if (StringUtil.isNotEmpty(order)) {
			usermap.put("order", order);
		}
		if (StringUtil.isNotEmpty(position)) {
			usermap.put("position", position);
		}
		if (StringUtil.isNotEmpty(gender)) {
			usermap.put("gender", gender);
		}
		if (StringUtil.isNotEmpty(email)) {
			usermap.put("email", email);
		}
		if (StringUtil.isNotEmpty(is_leader_in_dept)) {
			usermap.put("is_leader_in_dept", is_leader_in_dept);
		}
		if (StringUtil.isNotEmpty(avatar_mediaid)) {
			usermap.put("avatar_mediaid", avatar_mediaid);
		}
		if (StringUtil.isNotEmpty(enable)) {
			usermap.put("enable", enable);
		}
		if (StringUtil.isNotEmpty(to_invite)) {
			usermap.put("to_invite", to_invite);
		}
		if (StringUtil.isNotEmpty(address)) {
			usermap.put("address", address);
		}
		if (StringUtil.isNotEmpty(main_department)) {
			usermap.put("main_department", main_department);
		}
		log.info("封装的数据----------------------------------usermap:" + JSONObject.toJSON(usermap));
		log.info("封装的数据----------------------------------usermaptoString:" + JSONObject.toJSON(usermap).toString());
		//获取返回参数
		code = pushInfoB(map, usermap, url);

		return code;
	}

	/*
	 * 组装部门信息到hashmap
	 * */
	public String getDeptMap(Map map, String url) {
		//返回值
		String code = null;
		if (map == null) {
			code = "map为空";
			return code;
		}
		//获取部门信息
		String name = null;
		if (url.contains("create")) {
			if (map.get("name") != null) {
				name = map.get("name").toString();
				log.info("部门名称----------------------------------name:" + name);
			} else {
				code = "部门名称name为空";
				return code;
			}
		}
		String name_en = null;
		if (map.get("name_en") != null) {
			name_en = map.get("name_en").toString();
			log.info("部门英文名称----------------------------------name_en:" + name_en);
		}
		String parentid = null;
		if (url.contains("create")) {
			if (map.get("parentid") != null) {
				parentid = map.get("parentid").toString();
				log.info("父部门id----------------------------------parentid:" + parentid);
			} else {
				code = "父部门idparentid为空";
				return code;
			}
		}
		String order = null;
		if (map.get("order") != null) {
			order = map.get("order").toString();
			log.info("在父部门中的次序值----------------------------------order:" + order);
		}
		String id = null;
		if (map.get("id") != null) {
			id = map.get("id").toString();
			log.info("部门id----------------------------------id:" + id);
		} else {
			code = "部门id为空";
			return code;
		}

		//插入部门信息
		HashMap<String, String> depmap = new HashMap<>();
		if (StringUtil.isNotEmpty(name)) {
			depmap.put("name", name);
		}
		if (StringUtil.isNotEmpty(name_en)) {
			depmap.put("name_en",name_en);
		}
		if (StringUtil.isNotEmpty(parentid)) {
			depmap.put("parentid", parentid);
		}
		if (StringUtil.isNotEmpty(order)) {
			depmap.put("order", order);
		}
		if (StringUtil.isNotEmpty(id)) {
			depmap.put("id", id);
		}

		//获取返回参数
		code = pushInfo(map, depmap, url);

		return code;
	}

	/*
	 * 组装部门信息到hashmap
	 * */
	public String getDeptMapB(Map map, String url) {
		//返回值
		String code = null;
		if (map == null) {
			code = "map为空";
			return code;
		}
		//获取部门信息
		String name = null;
		if (url.contains("create")) {
			if (map.get("name") != null) {
				name = map.get("name").toString();
				log.info("部门名称----------------------------------name:" + name);
			} else {
				code = "部门名称name为空";
				return code;
			}
		}
		String name_en = null;
		if (map.get("name_en") != null) {
			name_en = map.get("name_en").toString();
			log.info("部门英文名称----------------------------------name_en:" + name_en);
		}
		String parentid = null;
		if (url.contains("create")) {
			if (map.get("parentid") != null) {
				parentid = map.get("parentid").toString();
				log.info("父部门id----------------------------------parentid:" + parentid);
			} else {
				code = "父部门idparentid为空";
				return code;
			}
		}
		String order = null;
		if (map.get("order") != null) {
			order = map.get("order").toString();
			log.info("在父部门中的次序值----------------------------------order:" + order);
		}
		String id = null;
		if (map.get("id") != null) {
			id = map.get("id").toString();
			log.info("部门id----------------------------------id:" + id);
		} else {
			code = "部门id为空";
			return code;
		}

		//插入部门信息
		HashMap<String,Object> depmap = new HashMap<>();
		if (StringUtil.isNotEmpty(name)) {
			depmap.put("name", name);
		}
		if (StringUtil.isNotEmpty(name_en)) {
			depmap.put("name_en", name_en);
		}
		if (StringUtil.isNotEmpty(parentid)) {
			depmap.put("parentid", parentid);
		}
		if (StringUtil.isNotEmpty(order)) {
			depmap.put("order", order);
		}
		if (StringUtil.isNotEmpty(id)) {
			depmap.put("id", id);
		}

		log.info("封装的数据----------------------------------depmap:" + JSONObject.toJSON(depmap));
		log.info("封装的数据----------------------------------depmaptoString:" + JSONObject.toJSON(depmap).toString());
		//获取返回参数
		code = pushInfoB(map, depmap, url);

		return code;
	}

	/*
	 * 通过POST请求推送数据到企业微信
	 * */
	public String pushInfo(Map map, HashMap qywxmap, String url) {
		String qywxcorpid = null;
		if (map.get("qywxcorpid") != null) {
			qywxcorpid = map.get("qywxcorpid").toString();
			log.info("成员企业的corpid---------------------------qywxcorpid:" + qywxcorpid);
		}
		//获取当前用户的企业access_token
		String crop_access_token = getCORPAccessToken(qywxcorpid);
		log.info("----------------------------------------当前用户的企业access_token:" + crop_access_token);

		//调用接口
		ResponseEntity<String> result = restTemplate.postForEntity(url + "?access_token=" + crop_access_token, qywxmap, String.class);
		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}
		return null;
	}
	/*
	 * 通过POST请求推送数据到企业微信
	 * */
	public String pushInfoB(Map map, HashMap qywxmap, String url) {
		String qywxcorpid = null;
		if (map.get("qywxcorpid") != null) {
			qywxcorpid = map.get("qywxcorpid").toString();
			log.info("成员企业的corpid---------------------------qywxcorpid:" + qywxcorpid);
		}
		//获取当前用户的企业access_token
		String crop_access_token = getCORPAddressAccessToken(qywxcorpid);
		log.info("----------------------------------------当前用户的企业access_token:" + crop_access_token);
//https://api.uukit.com/req/mock/on5skn
		//调用接口
		//ResponseEntity<String> result11 = restTemplate.postForEntity("https://api.uukit.com/req/mock/on5skn" + "?access_token=" + crop_access_token, JSONObject.toJSON(qywxmap), String.class);
		ResponseEntity<String> result = restTemplate.postForEntity(url + "?access_token=" + crop_access_token, JSONObject.toJSON(qywxmap), String.class);
		if (result != null) {
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String errcode = jsonmap.get("errcode").toString();
			return errcode;
		}
		return null;
	}

}
