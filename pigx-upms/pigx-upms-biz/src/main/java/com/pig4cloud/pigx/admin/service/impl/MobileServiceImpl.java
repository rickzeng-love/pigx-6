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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.mapper.SysUserMapper;
import com.pig4cloud.pigx.admin.service.MobileService;
import com.pig4cloud.pigx.admin.service.SysPublicParamService;
import com.pig4cloud.pigx.admin.util.AliDayunSms;
import com.pig4cloud.pigx.admin.util.SymmetricEncoder;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.constant.SecurityConstants;
import com.pig4cloud.pigx.common.core.constant.enums.LoginTypeEnum;
import com.pig4cloud.pigx.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lengleng
 * @date 2018/11/14
 * <p>
 * 手机登录相关业务实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class MobileServiceImpl implements MobileService {
	private final RedisTemplate redisTemplate;
	private final SysUserMapper userMapper;
	private final CacheManager cacheManager;
	private final SysPublicParamService sysPublicParamService;



	/**
	 * 发送手机验证码
	 * TODO: 调用短信网关发送验证码,测试返回前端
	 *
	 * @param mobile mobile
	 * @return code
	 */
	@Override
	public R<Boolean> sendSmsCode(String mobile) {
		/*
		List<SysUser> userList = userMapper.selectList(Wrappers
			.<SysUser>query().lambda()
			.eq(SysUser::getPhone, mobile));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}", mobile);
			return R.ok(Boolean.FALSE, "手机号未注册");
		}
		*/
		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile);

		if (codeObj != null) {
			log.info("手机号验证码未过期:{}，{}", mobile, codeObj);
			return R.ok(Boolean.FALSE, "手机号验证码未过期");
		}
		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));

		AliDayunSms aliDayunSms = new AliDayunSms();
		String accessKeyId = "";
		String accessSecret = "";
		Cache cache = cacheManager.getCache(CacheConstants.PARAMS_DETAILS);
		if (cache != null && cache.get("ALIDAYYUNSMS_ACCESSSECRET") != null) {
			accessKeyId = (String)cache.get("ALIDAYYUNSMS_ACCESSKEYID").get();
			accessSecret =(String)cache.get("ALIDAYYUNSMS_ACCESSSECRET").get();
		}else{
			accessKeyId = sysPublicParamService.getSysPublicParamKeyToValue("ALIDAYYUNSMS_ACCESSKEYID");
			accessSecret = sysPublicParamService.getSysPublicParamKeyToValue("ALIDAYYUNSMS_ACCESSSECRET");
		}

		try {
			aliDayunSms.sendMessage(accessKeyId,accessSecret,mobile,code);
		} catch (ClientException e) {
			e.printStackTrace();
			return R.ok(Boolean.TRUE,"网络繁忙，请稍后再试！");
		}
		log.debug("手机号生成验证码成功:{},{}", mobile, code);
		redisTemplate.opsForValue().set(
			CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile
			, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
		return R.ok(Boolean.TRUE, "请查收短信！  "+code);
	}
	/**
	 * 修改密码校验 验证码
	 * @param  phone
	 * @param  code
	 * @return code
	 */
	@Override
	public R verfiySmsCode(String phone,String code) {
		//String mobile = userDto.getPhone();
		String mobile = phone;
		List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getPhone, mobile));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}",mobile);
			//return R.ok(Boolean.FALSE, "手机号未注册");
			return R.ok(null, "手机号未注册",2);
		}

		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile);
		//String code = userDto.getCode();
		boolean b = false;
		if (codeObj != null) {
			b = StrUtil.equals(code,codeObj.toString());
		}else{
			log.info("手机号验证码已过期:{}，{}", mobile, "");
			return R.failed(null, "手机号验证码已过期");
		}
		if(b){

			String verfiycode = RandomUtil.randomNumbers(Integer.parseInt("10"));
			log.debug("生成随机码:{},{}", phone, verfiycode);
			log.debug("生成随机码:{},{}", phone, verfiycode);
			//加密随机码，规则：手机号
			SymmetricEncoder se=new SymmetricEncoder();
			String content = se.AESEncode(phone, verfiycode);
			log.debug("加密随机码为:{},{}", phone, content);
			redisTemplate.opsForValue().set(
					CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.UPDATEPASSWORD.getType() + StringPool.AT + phone
					, verfiycode, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);


			return R.ok(content, "",0);
		}else{
			return R.failed(null, "验证码不匹配，请重新输入！");
		}
		//String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));

		//log.debug("手机号生成验证码成功:{},{}", mobile, code);
		//redisTemplate.opsForValue().set(
				//CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile
			//	, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

	}
	/**
	 * 判断手机号是否已注册
	 * @param  phone
	 * @return code
	 */
	@Override
	public R<Boolean> getUserByPhone(String phone) {

		List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getAccount, phone));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}",phone);
			//return R.ok(Boolean.FALSE, "手机号未注册");
			return R.ok(null, "手机号未注册",2);
		}else{
			return R.ok(null, "手机号已注册",0);
		}



	}
}
