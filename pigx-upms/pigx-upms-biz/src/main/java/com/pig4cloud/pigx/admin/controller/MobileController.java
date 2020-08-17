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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pig4cloud.pigx.admin.api.dto.UserDTO;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.entity.SysUserRole;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysUserMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.LocalDateUtil;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.qrcode.QrCodeGenWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author lengleng
 * @date 2018/11/14
 * <p>
 * 手机验证码
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mobile")
@Api(value = "mobile", tags = "手机管理模块")
public class MobileController {
	private final MobileService mobileService;
	private final SysUserService userService;
	private  final  EtstaffRegisterService etstaffRegisterService;


	@Inner(value = false)
	@GetMapping("/{mobile}")
	public R sendSmsCode(@PathVariable String mobile) {
		return mobileService.sendSmsCode(mobile);
	}
	/*
	* 验证码校验/verfiySmsCode
	 */
	//@PutMapping("/verfiySmsCode")
	@Inner(value = false)
	//@GetMapping("/verfiySmsCode")
	@RequestMapping(value="/verfiySmsCode",method= RequestMethod.GET)
	public R verfiySmsCode(@RequestParam("phone") String phone,@RequestParam("code") String code) {
		return mobileService.verfiySmsCode(phone,code);
	}

	/**
	 * 修改个人信息
	 *
	 * @param username
	 * @param newpassword1
	 * @return success/false
	 */
	@Inner(value = false)
	@SysLog("修改个人信息")
	//@PutMapping("/updateUserPassword")
	@RequestMapping(value="/updateUserPassword",method= RequestMethod.GET)
	public R updateUserPassword(@RequestParam("username") String username,@RequestParam("newpassword1") String newpassword1,@RequestParam("code") String content) {
		return userService.updateUserPassword(username,newpassword1,content);
	}

	/**
	 * 注册
	 *
	 * @param username
	 * @param compname
	 * @param account
	 * @return success/false
	 */
	@Inner(value = false)
	@SysLog("注册")
	//@PutMapping("/updateUserPassword")
	@RequestMapping(value="/userRegisterForPC",method= RequestMethod.GET)
	public R userRegisterForPC(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("compname") String compname,@RequestParam("account") String account) {


		return userService.userRegisterForPC(username,password,compname,account);
	}
	/**
	 * 注册
	 *
	 * @param username
	 * @param compname
	 * @param account
	 * @return success/false
	 */
	@Inner(value = false)
	@SysLog("注册")
	//@PutMapping("/updateUserPassword")
	@RequestMapping(value="/userRegister",method= RequestMethod.GET)
	public R userRegister(@RequestParam("username") String username,@RequestParam("compname") String compname,@RequestParam("account") String account) {


		return userService.userRegister(username,compname,account);
	}
	/*
	 * 判断手机号是否已注册
	 */
	@Inner(value = false)
	@RequestMapping(value="/getUserByPhone",method= RequestMethod.GET)
	public R getUserByPhone(@RequestParam("phone") String phone) {
		return mobileService.getUserByPhone(phone);
	}
	/*
	**入职邀请
	* 1、手机号短信验证
	* 2、验证手机号是否在登记表中
	* 3、插入sys_user 插入档案信息
	 */
	@ApiOperation(value = "入职邀请", notes = "入职邀请")
	@Inner(value = false)
	@SysLog("入职邀请")
	@RequestMapping(value="/inviteRegiter",method= RequestMethod.GET)
	public R  inviteRegiter(@RequestParam("mobile") String mobile,@RequestParam("corpcode") String corpcode,@RequestParam("code") String code){
		return etstaffRegisterService.inviteRegiter(mobile,corpcode,code);
	}

}
