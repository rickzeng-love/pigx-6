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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.entity.SysUserRole;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.*;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.AliDayunSms;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.LocalDateUtil;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.constant.SecurityConstants;
import com.pig4cloud.pigx.common.core.constant.enums.LoginTypeEnum;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 入职登记
 *
 * @author gaoxiao
 * @date 2020-04-10 19:14:19
 */
@Slf4j
@Service
@AllArgsConstructor
public class EtstaffRegisterServiceImpl extends ServiceImpl<EtstaffRegisterMapper, EtstaffRegister> implements EtstaffRegisterService {
	private  final EtstaffRegisterMapper etstaffRegisterMapper;
	private final RedisTemplate redisTemplate;
	private final SysUserMapper userMapper;
	private final CacheManager cacheManager;
	private final SysPublicParamService sysPublicParamService;
	private  final SysUserService sysUserService;
	private final MinioTemplate minioTemplate;
	private final SysFileService sysFileService;
	private  final SysUserRoleService sysUserRoleService;
	private final EtstaffAllService etstaffAllService;
	private final  EtchangeorgRegisterService etchangeorgRegisterService;
	private final EtchangeorgRegisterMapper etchangeorgRegisterMapper;
	private final SystmessageMapper systmessageMapper;
	private final  EtchangeorgAllService etchangeorgAllService;
	private final  EtleaveRegisterService etleaveRegisterService;
	private  final  EtleaveAllService etleaveAllService;
	private final EtleaveRegisterMapper etleaveRegisterMapper;
	private final  EtprobRegisterMapper etprobRegisterMapper;
	private final  EtprobAllService etprobAllService;
	private  final EtprobRegisterService etprobRegisterService;
	private final  EtpracRegisterService etpracRegisterService;
	private final  EtpracRegisterMapper etpracRegisterMapper;
	private final  EtpracAllService etpracAllService;
	/**
	 * 入：1.黑名单校验；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeBlackList(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeBlackList(etemployee);
	}
	/**
	 * 入：2.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeCDRelationship(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeCDRelationship(etemployee);
	}
	/**
	 * 入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeCJRelationship(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeCJRelationship(etemployee);
	}
	/**
	 * 入：4.员工工号已经存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeExistsEmployee(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeExistsEmployee(etemployee);
	}
	/**
	 * 入：4-1.员员工号号已存在，正在办理入职手续
	 *
	 * @return List<Map>
	 */
	public List<Map> getExistsEtstaffRegisterByBadge(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listExistsEtstaffRegisterByBadge(etemployee);
	}
	/**
	 * 入：5.直接主管不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsReportTo(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeNotExistsReportTo(etemployee);
	}
	/**
	 * 入：6.职能主管不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsWFReportTo(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeNotExistsWFReportTo(etemployee);
	}
	/**
	 * 入：7.所选公司不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsComp(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeNotExistsComp(etemployee);
	}
	/**
	 * 入：8.所选公司已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeCompIsDisable(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeCompIsDisable(etemployee);
	}

	/**
	 * 入：9.所选部门不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsDept(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeNotExistsDept(etemployee);
	}
	/**
	 * 入：10.所选部门已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeDeptIsDisable(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeDeptIsDisable(etemployee);
	}
	/**
	 * 入：11.所选岗位不存在；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeNotExistsJob(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeNotExistsJob(etemployee);
	}
	/**
	 * 入：12.所选岗位已经失效；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeJobIsDisable(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeJobIsDisable(etemployee);
	}
	/**
	 * 入：14.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtEmployeeMobileRepeat(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtEmployeeMobileRepeat(etemployee);
	}
	/**
	 * 入：14-1.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	@Override
	public List<Map> getEtstaffRegisterByMobile(EtstaffRegister etemployee){
		return etstaffRegisterMapper.listEtstaffRegisterByMobile(etemployee);
	}
	/*
	 **入职邀请
	 * 1、手机号短信验证
	 * 2、验证手机号是否在登记表中
	 * 3、插入sys_user 插入档案信息
	 */
	public R inviteRegiter(String mobile, String corpcode,String code){
		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile);
		//重复验证：

		//1、手机验证码验证
		if (codeObj == null) {
			log.info("手机号验证码未过期:{}，{}", mobile, codeObj);
			return R.ok("验证码已过期");
		}
		boolean b = false;
		if (codeObj != null) {
			b = StrUtil.equals(code,codeObj.toString());
		}else{
			return R.ok("验证码不匹配！");
		}

		//2、验证是否有此手机号存在
		EtstaffRegister etstaffRegister = getOne(Wrappers.<EtstaffRegister>query().lambda().eq(EtstaffRegister::getCorpcode,corpcode)
				.eq(EtstaffRegister::getMobile, mobile));
		if (etstaffRegister == null) {
			return R.ok(Boolean.TRUE, "没有改手机号的相关信息,请联系HR！");
		}
		SysUser sysUser2 = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getCorpcode,corpcode)
				.eq(SysUser::getAccount, mobile));
		if(sysUser2==null){
			//3、插入sys_user和其他档案表
			SysUser sysUser = new SysUser();
			sysUser.setUsername(etstaffRegister.getName());
			sysUser.setCompid(etstaffRegister.getCompid());
			sysUser.setDelFlag("0");
			sysUser.setCreateTime(LocalDateUtil.dateToLocalDateTime(new Date()));
			sysUser.setPhone(mobile);
			sysUser.setAccount(mobile);
			sysUser.setCorpcode(etstaffRegister.getCorpcode());
			sysUser.setBadge(etstaffRegister.getBadge());
			sysUser.setIsdisabled("0");
			sysUser.setLockFlag("0");
			sysUserService.save(sysUser);
			//保存权限信息
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(sysUser.getUserId());
			sysUserRole.setRoleId(5);
			sysUserRoleService.save(sysUserRole);
		}


		String randomcode = RandomUtil.randomNumbers(Integer.parseInt("10"));
		log.debug("生成随机码:{},{}", mobile, randomcode);
		redisTemplate.opsForValue().set(
				CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.USERREGISTER.getType() + StringPool.AT + mobile
				, randomcode, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

		Map resultMap = new HashMap();
		resultMap.put("code",randomcode);
		resultMap.put("etstaffRegister",etstaffRegister);

		return R.ok(resultMap,"验证成功！");
	}
	/**
	 * 上传文件
	 *
	 * @param file
	 * @param id
	 * @return
	 */
	public R uploadFile(MultipartFile file,Integer id){
		String contentType = file.getContentType();
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", CommonConstants.BUCKET_NAME);
		resultMap.put("fileName", fileName);
		//resultMap.put("url", String.format("/admin/sys-file/%s/%s", CommonConstants.BUCKET_NAME, fileName));
		//resultMap.put("url", String.format("/%s/%s", CommonConstants.BUCKET_NAME, fileName));
		resultMap.put("url", "/"+CommonConstants.BUCKET_NAME+"/" +fileName);

		try {

			PigxUser pu = SecurityUtils.getUser();
			EtstaffRegister etstaffRegister = new EtstaffRegister();
			etstaffRegister.setId(id);
			etstaffRegister.setPortrait("/"+CommonConstants.BUCKET_NAME+"/" +fileName);
			minioTemplate.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream(),contentType);
			//文件管理数据记录,收集管理追踪文件
			sysFileService.fileLog(file, fileName);
			updateById(etstaffRegister);
		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}
	//录用 审批后调用
	@Override
	public void submitLuYongBySpno(String spno,int openSpStatus,int spr){
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		UpdateWrapper<EtstaffRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("spno",spno);
		EtstaffRegister etstaffRegister = new EtstaffRegister();
		if(openSpStatus==2){
			etstaffRegister.setSubmit(2);
			//审批人需要确认下
			etstaffRegister.setSubmitby(spr);
			etstaffRegister.setSubmittime(currentTime);
			etstaffRegisterMapper.update(etstaffRegister,updateWrapper);
		}
		if(openSpStatus==3){
			etstaffRegister.setSubmit(3);
			//审批人需要确认下
			etstaffRegister.setSubmitby(spr);
			etstaffRegister.setSubmittime(currentTime);
			etstaffRegisterMapper.update(etstaffRegister,updateWrapper);
			QueryWrapper<EtstaffRegister> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("spno",spno);
			EtstaffRegister etstaffRegister1 = etstaffRegisterMapper.selectOne(queryWrapper);
			EtstaffAll etstaffAll = new EtstaffAll();
			BeanUtils.copyProperties(etstaffRegister1,etstaffAll);
			etstaffAllService.save(etstaffAll);
			etstaffRegisterMapper.delete(queryWrapper);

		}


	}

	//调动 审批后调用
	@Override
	public void submitDiaoDongBySpno(String spno,int openSpStatus,int spr){

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		Systmessage systmessage = null;
		String message = "";
		UpdateWrapper<EtchangeorgRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("spno",spno);
		QueryWrapper<EtchangeorgAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("spno",spno);
		EtchangeorgRegister etchangeorgRegister = etchangeorgRegisterService.getOne(updateWrapper);
		if(openSpStatus==2){
			Map map = new HashMap();
			map.put("id",etchangeorgRegister.getId());
			map.put("userid",spr);
			etchangeorgRegisterMapper.eSPChangeOrgStart(map);
			int mesid = (Integer) map.get("result");
			systmessage = systmessageMapper.selectById(mesid);
			message = systmessage.getTitle();
		}

		if(openSpStatus==3){
			EtchangeorgAll etchangeorgAll = new EtchangeorgAll();
			BeanUtils.copyProperties(etchangeorgRegister,etchangeorgAll);
			etchangeorgAll.setSubmit(3);
			etchangeorgAll.setSubmitby(spr);
			etchangeorgAll.setSubmittime(currentTime);
			etchangeorgAllService.save(etchangeorgAll);
			etchangeorgAllService.remove(queryWrapper);
		}
	}

	//离职 审批后调用
	@Override
	public void submitLiZhiBySpno(String spno,int openSpStatus,int spr){

		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		Systmessage systmessage = null;
		String message = "";
		UpdateWrapper<EtleaveRegister> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("spno",spno);
		QueryWrapper<EtleaveRegister> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("spno",spno);
		EtleaveRegister etleaveRegister = etleaveRegisterService.getOne(updateWrapper);
		if(openSpStatus==2){
			Map map = new HashMap();
			map.put("id",etleaveRegister.getId());
			map.put("userid",spr);
			etleaveRegisterMapper.eSPLeaveStart(map);
			int mesid = (Integer) map.get("result");
			systmessage = systmessageMapper.selectById(mesid);
			message = systmessage.getTitle();
		}

		if(openSpStatus==3){
			EtleaveAll etleaveAll  = new EtleaveAll();
			BeanUtils.copyProperties(etleaveRegister,etleaveAll);
			etleaveAll.setSubmit(3);
			etleaveAll.setSubmitby(spr);
			etleaveAll.setSubmittime(currentTime);
			etleaveAllService.save(etleaveAll);
			etleaveRegisterService.remove(queryWrapper);
		}
	}

	//转正 审批后调用
	@Override
	public void submitZhuanZhengBySpno(String spno,int openSpStatus,int spr){
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		Systmessage systmessage = null;
		String message = "";

		QueryWrapper<EtprobRegister> probqueryWrapper = new QueryWrapper<>();
		probqueryWrapper.eq("spno",spno);
		EtprobRegister etprobRegister  = etprobRegisterService.getOne(probqueryWrapper);

		QueryWrapper<EtpracRegister> pracqueryWrapper = new QueryWrapper<>();
		pracqueryWrapper.eq("spno",spno);
		EtpracRegister etpracRegister  = etpracRegisterService.getOne(pracqueryWrapper);
		if(!StringUtils.isEmpty(etprobRegister)){

			if(openSpStatus==2){
				Map map = new HashMap();
				map.put("id",etprobRegister.getId());
				map.put("userid",spr);
				etprobRegisterMapper.eSPProbStart(map);
				int mesid = (Integer) map.get("result");
				systmessage = systmessageMapper.selectById(mesid);
				message = systmessage.getTitle();
			}

			if(openSpStatus==3){
				EtprobAll etprobAll  = new EtprobAll();
				BeanUtils.copyProperties(etprobRegister,etprobAll);
				etprobAll.setSubmit(3);
				etprobAll.setSubmitby(spr);
				etprobAll.setSubmittime(currentTime);
				etprobAllService.save(etprobAll);
				etprobRegisterService.remove(probqueryWrapper);
			}
		}
		if(!StringUtils.isEmpty(etpracRegister)){

			if(openSpStatus==2){
				Map map = new HashMap();
				map.put("id",etpracRegister.getId());
				map.put("userid",spr);
				etpracRegisterMapper.eSPPracStart(map);
				int mesid = (Integer) map.get("result");
				systmessage = systmessageMapper.selectById(mesid);
				message = systmessage.getTitle();
			}

			if(openSpStatus==3){
				EtpracAll etpracAll  = new EtpracAll();
				BeanUtils.copyProperties(etpracRegister,etpracAll);
				etpracAll.setSubmit(3);
				etpracAll.setSubmitby(spr);
				etpracAll.setSubmittime(currentTime);
				etpracAllService.save(etpracAll);
				etpracRegisterService.remove(pracqueryWrapper);
			}
		}
	}
}
