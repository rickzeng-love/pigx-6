/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.pig4cloud.pigx.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.api.dto.UserDTO;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysUserMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import com.qq.weixin.mp.aes.EnterpriseConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lengleng
 * @date 2018/12/16
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class SysUserController {
	private final SysUserService userService;
	private final MobileService mobileService;
	private final SystcorpinfoService systcorpinfoService;
	private final SysQywxApplicationService sysQywxApplicationService;
	private final SysQywxApplicationAuthorizerService sysQywxApplicationAuthorizerService;
	private final OtdepartmentService otdepartmentService;



	/**
	 * 获取当前用户全部信息
	 *
	 * @return 用户信息
	 */

	@GetMapping(value = {"/info"})
	public R info() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String username = pigxUser.getUsername();
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getAccount, username));
		if (user == null) {
			return R.failed(null, "获取当前用户信息失败");
		}
		return R.ok(userService.findUserInfo(user));
	}
	/**
	 * 获取指定用户全部信息
	 *
	 * @return 用户信息
	 */

	@Inner
	@GetMapping("/info/{username}")
	public R info(@PathVariable String username) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getAccount, username));
		if (user == null) {
			return R.failed(null, String.format("用户信息为空 %s", username));
		}
		return R.ok(userService.findUserInfo(user));
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id ID
	 * @return 用户信息
	 */
	@GetMapping("/{id}")
	public R user(@PathVariable Integer id) {
		return R.ok(userService.selectUserVoById(id));
	}

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param username 用户名
	 * @return
	 */
	@GetMapping("/details/{username}")
	public R user(@PathVariable String username) {
		SysUser condition = new SysUser();
		condition.setUsername(username);
		return R.ok(userService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 删除用户信息
	 *
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除用户信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	@ApiOperation(value = "删除用户", notes = "根据ID删除用户")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path")
	public R userDel(@PathVariable Integer id) {
		SysUser sysUser = userService.getById(id);
		return R.ok(userService.deleteUserById(sysUser));
	}

	/**
	 * 添加用户
	 *
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@SysLog("添加用户")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	public R user(@RequestBody UserDTO userDto) {
		return R.ok(userService.saveUser(userDto));
	}

	/**
	 * 更新用户信息
	 *
	 * @param userDto 用户信息
	 * @return R
	 */
	@SysLog("更新用户信息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public R updateUser(@Valid @RequestBody UserDTO userDto) {
		return R.ok(userService.updateUser(userDto));
	}

	/**
	 * 分页查询用户
	 *
	 * @param page    参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@GetMapping("/page")
	public R getUserPage(Page page, UserDTO userDTO) {

		return R.ok(userService.getUsersWithRolePage(page, userDTO));
	}


	/**
	 * 分页查询用户
	 *

	 * @return 用户集合
	 */
	@PostMapping("/getUserAll")
	public R getUserAll() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		//queryWrapper.eq("status",0);
		return R.ok(userService.list(queryWrapper));
	}
	/**
	 * 分页查询用户
	 *

	 * @return 用户集合
	 */
	@PostMapping("/getUsePage")
	public R getUsePage(Page page,@RequestBody(required = false) SysUser sysUser) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(sysUser)){
			sysUser = new SysUser();
		}
		sysUser.setCorpcode(corpcode);
		//queryWrapper.eq("status",0);
		return R.ok(userService.page(page,Wrappers.query(sysUser)));
	}



	/**
	 * 获取管理员列表
	 *

	 * @return
	 */
	@PostMapping("/getalladminList")
	public R getalladminList() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Map map = new HashMap();
		map.put("corpcode",corpcode	);
		return R.ok(userService.getalladminList(map));
	}

	/**
	 * 修改个人信息
	 *
	 * @param userDto userDto
	 * @return success/false
	 */
	@SysLog("修改个人信息")
	@PostMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		return userService.updateUserInfo(userDto);
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@GetMapping("/ancestor/{username}")
	public R listAncestorUsers(@PathVariable String username) {
		return R.ok(userService.listAncestorUsers(username));
	}
	/**
	 * 修改个人信息
	 *
	 *
	 * @return success/false
	 */
	//@SysLog("修改个人信息")
	//@PutMapping("/updateUserPassword")
	//public R updateUserPassword(@Valid @RequestBody UserDTO userDto) {
	//	return userService.updateUserPassword(userDto);
	//}

	/*
	 * 验证码校验/verfiySmsCode
	 */
	@PutMapping("/verfiySmsCode")
	public R verfiySmsCode(@PathVariable String mobile,@PathVariable String code) {
		return mobileService.verfiySmsCode(mobile,code);
	}

	/**
	 * 修改	@PreAuthorize("@pms.hasPermission('admin_systcorpinfo_edit')" )
	 * @param systcorpinfo
	 * @return R
	 */
	@SysLog("修改" )
	@PostMapping(value="/updateById")
	public R updateById(@RequestBody Systcorpinfo systcorpinfo) {
		return R.ok(systcorpinfoService.updateById(systcorpinfo));
	}

	/**
	 * 获取用户信息，用做下拉框的数据源
	 * Add By GongXiShan 2020525
	 * @param sysUser
	 * @return
	 */
	@ApiOperation(value = "查询启用的所有记录", notes = "查询启用的所有记录")
	@GetMapping("/getSysUserData" )
	public R getSysUserData(SysUser sysUser)
	{
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		sysUser.setCorpcode(corpCode);
		return R.ok(userService.list(Wrappers.query(sysUser).ne("UserID",1).ne("Status",18)));
	}
	@PostMapping("/getSessionInfo")
	public R getSessionInfo(){

		String result = sysQywxApplicationService.setSessionInfo();
		if(StringUtils.isEmpty(result)){
			return R.failed("操作失败");
		}else{
			if("0".equals(result)){
				return R.ok("操作成功！");
			}else{
				return R.failed(result,"调用失败");
			}
		}
		//return R.ok("操作成功！");
	}
	@PostMapping("/getSessionInfoAddress")
	public R getSessionInfoAddress(){

		String result = sysQywxApplicationService.setSessionInfoAddress();
		if(StringUtils.isEmpty(result)){
			return R.failed("操作失败");
		}else{
			if("0".equals(result)){
				return R.ok("操作成功！");
			}else{
				return R.failed(result,"调用失败");
			}
		}
		//return R.ok("操作成功！");
	}
	@PostMapping("/createUser" )
	public R createUser(@RequestBody List<SysUser> sysUserList){
		//用户企业微信corpid放在systcorpinfo表里
		//String scorpid = "wwc649dbd1e325fb9f";
		SysUser sysUser =  new SysUser();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		sysUser.setCorpcode(corpcode);

		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}

		if(StringUtils.isEmpty(sysUserList)){
			return R.failed("参数不能为空！");
		}
		sysQywxApplicationService.getSuiteApplication();
		String suiteId = EnterpriseConst.SUITEID_B;
		QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("auth_corpid",qywxcorpid);
		queryWrapper1.eq("suite_id",suiteId);
		SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer = sysQywxApplicationAuthorizerService.getOne(queryWrapper1);
		if(StringUtils.isEmpty(sysQywxApplicationAuthorizer)){
			return R.failed("请安装企业微信-E人E事-SaaS产品！");
		}
		SysUser sysUser1 = null;
		SysUser sysUser2 = null;
		Integer userId = null;
		QueryWrapper<SysUser> queryWrapper2 = null;
		for(int gg=0;gg<sysUserList.size();gg++){
			sysUser1 = sysUserList.get(gg);
			userId = sysUser1.getUserId();
			queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("user_id",userId);
			sysUser2 = userService.getOne(queryWrapper2);
			if(StringUtils.isEmpty(sysUser2)){
				continue;
			}

			QueryWrapper<Otdepartment> queryWrapper3 = new QueryWrapper<>();
			queryWrapper3.eq("depid",sysUser2.getDeptId());
			Otdepartment otdepartment = otdepartmentService.getOne(queryWrapper3);
			if(StringUtils.isEmpty(otdepartment)){
				SysUser sysUser3 =  new SysUser();
				sysUser3.setUserId(sysUser2.getUserId());
				sysUser3.setQywxFlag(4);
				sysUser3.setRemark("员工所属部门不存在！");
				userService.updateById(sysUser3);
			}

			Map map = null;
			map = new HashMap();
			map.put("qywxcorpid",qywxcorpid);
			map.put("userid",sysUser2.getUserId());
			map.put("name",sysUser2.getUsername());
			map.put("alias",sysUser2.getBadge()+"-"+sysUser2.getUsername());
			map.put("mobile",sysUser2.getPhone());
			map.put("department",sysUser2.getDeptId());

			String code1 = sysQywxApplicationService.selectUser(map);
			log.info("企业微信查询部人："+sysUser2.getUserId()+"返回值："+code1);
			Integer status = sysUser2.getStatus();
			if(status!=0){
				String code2 = sysQywxApplicationService.deleteUser(map);
				log.info("企业微信删除人："+sysUser2.getUserId()+"返回值："+code2);
				if("0".equals(code2)){
					SysUser sysUser3 =  new SysUser();
					sysUser3.setUserId(sysUser2.getUserId());
					sysUser3.setQywxFlag(0);
					sysUser3.setRemark("同步成功！");
					userService.updateById(sysUser3);
					//return R.ok("同步成功！");
				}else{
					SysUser sysUser3 =  new SysUser();
					sysUser3.setUserId(sysUser2.getUserId());
					sysUser3.setQywxFlag(3);
					sysUser3.setRemark("同步失败！代码:"+code2);
					userService.updateById(sysUser3);
					//return R.failed(code2,"同步失败！");
				}
			}
			//如果存在，就更新
			if("0".equals(code1)){
				String code2 = sysQywxApplicationService.updateUser(map);
				log.info("企业微信更人："+sysUser2.getUserId()+"返回值："+code2);
				if("0".equals(code2)){
					SysUser sysUser3 =  new SysUser();
					sysUser3.setUserId(sysUser2.getUserId());
					sysUser3.setQywxFlag(0);
					sysUser3.setRemark("同步成功！");
					userService.updateById(sysUser3);
					//return R.ok("同步成功！");
				}else{
					SysUser sysUser3 =  new SysUser();
					sysUser3.setUserId(sysUser2.getUserId());
					sysUser3.setQywxFlag(2);
					sysUser3.setRemark("同步失败！！代码:"+code2);
					userService.updateById(sysUser3);
					//return R.failed(code2,"同步失败！");
				}
			}else{
				String code = sysQywxApplicationService.createUser(map);
				log.info("企业微信创建人："+sysUser2.getUserId()+"返回值："+code);
				if("0".equals(code)){
					SysUser sysUser3 =  new SysUser();
					sysUser3.setUserId(sysUser2.getUserId());
					sysUser3.setQywxFlag(0);
					sysUser3.setRemark("同步成功！");
					userService.updateById(sysUser3);
					//return R.ok("同步成功！");
				}else{
					SysUser sysUser3 =  new SysUser();
					sysUser3.setUserId(sysUser2.getUserId());
					sysUser3.setQywxFlag(1);
					sysUser3.setRemark("同步失败！代码:"+code);
					userService.updateById(sysUser3);
					//return R.failed(code,"同步失败！");
				}
			}

		}
		return R.ok("同步完成，请在列表中查看同步结果！");

	}


	@PostMapping("/createDept" )
	public R createDept(@RequestBody List<Otdepartment> otdeparmentListList){
		//用户企业微信corpid放在systcorpinfo表里
		//String scorpid = "wwc649dbd1e325fb9f";
		SysUser sysUser =  new SysUser();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		sysUser.setCorpcode(corpcode);

		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}

		if(StringUtils.isEmpty(otdeparmentListList)){
			return R.failed("参数不能为空！");
		}
		sysQywxApplicationService.getSuiteApplication();
		String suiteId = EnterpriseConst.SUITEID_B;
		QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("auth_corpid",qywxcorpid);
		queryWrapper1.eq("suite_id",suiteId);
		SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer = sysQywxApplicationAuthorizerService.getOne(queryWrapper1);
		if(StringUtils.isEmpty(sysQywxApplicationAuthorizer)){
			return R.failed("请安装企业微信-E人E事-SaaS产品！");
		}
		Otdepartment otdepartment = null;
		Otdepartment otdepartment2  = null;
		Integer depId = null;
		QueryWrapper<Otdepartment> queryWrapper2 = null;
		for(int gg=0;gg<otdeparmentListList.size();gg++){
			otdepartment = otdeparmentListList.get(gg);
			depId = otdepartment.getDepid();
			queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("depid",depId);
			otdepartment2 = otdepartmentService.getOne(queryWrapper2);
			if(StringUtils.isEmpty(otdepartment2)){
				continue;
			}

			Map map = null;
			map = new HashMap();
			map.put("qywxcorpid",qywxcorpid);
			map.put("id",otdepartment2.getDepid());
			map.put("name",otdepartment2.getTitle());
			map.put("parentid",otdepartment2.getAdminid());
			if(StringUtils.isEmpty(otdepartment2.getXorder())){

			}else{
				map.put("order",otdepartment2.getXorder());
			}

			/*
			"name": "广州研发中心",
					"name_en": "RDGZ",
					"parentid": 1,
					"order": 1,
					"id": 2
					*/
			//判断上级部门是否存在,不存在就直接跳过
			Integer adminid = otdepartment2.getAdminid();
			if(!StringUtils.isEmpty(adminid)){
				Map map1 = new HashMap();
				map1.put("qywxcorpid",qywxcorpid);
				map1.put("id",otdepartment2.getAdminid());
				String code11 = sysQywxApplicationService.selectDepartment(map1);
				log.info("企业微信查询部门："+otdepartment2.getAdminid()+"返回值："+code11);
				if(!"0".equals(code11)){
					Otdepartment otdepartment1 =  new Otdepartment();
					otdepartment1.setDepid(otdepartment2.getDepid());
					otdepartment1.setQywxFlag(2);
					otdepartment1.setRemark("上级部门不存在");
					otdepartmentService.updateById(otdepartment1);
					continue;
				}
			}
			//判断当前部门是否存在
			String code1 = sysQywxApplicationService.selectDepartment(map);
			log.info("企业微信查询部门："+otdepartment2.getDepid()+"返回值："+code1);
			//如果存在，就更新
			if("0".equals(code1)){
				String code2 = sysQywxApplicationService.updateDepartment(map);
				log.info("企业微信修改部门："+otdepartment2.getDepid()+"返回值："+code2);
				if("0".equals(code2)){
					Otdepartment otdepartment1 =  new Otdepartment();
					otdepartment1.setDepid(otdepartment2.getDepid());
					otdepartment1.setQywxFlag(0);
					otdepartment1.setRemark("同步成功！");
					otdepartmentService.updateById(otdepartment1);
					//return R.ok("同步成功！");
				}else{
					Otdepartment otdepartment1 =  new Otdepartment();
					otdepartment1.setDepid(otdepartment2.getDepid());
					otdepartment1.setQywxFlag(2);
					otdepartment1.setRemark("同步失败！代码:"+code2);
					otdepartmentService.updateById(otdepartment1);
					//return R.failed(code2,"同步失败！");
				}
			}else{
				//如果不存在，判断上级部门是否为null，如果为空，改成1(企业微信默认的跟部门为1)
				if(StringUtils.isEmpty(otdepartment2.getAdminid())){
					map.put("parentid",1);
				}
				String code = sysQywxApplicationService.createDepartment(map);
				log.info("企业微信创建部门："+otdepartment2.getDepid()+"返回值："+code);
				if("0".equals(code)){
					Otdepartment otdepartment1 =  new Otdepartment();
					otdepartment1.setDepid(otdepartment2.getDepid());
					otdepartment1.setQywxFlag(0);
					otdepartment1.setRemark("同步成功！");
					otdepartmentService.updateById(otdepartment1);
					//return R.ok("同步成功！");
				}else{
					Otdepartment otdepartment1 =  new Otdepartment();
					otdepartment1.setDepid(otdepartment2.getDepid());
					otdepartment1.setQywxFlag(1);
					otdepartment1.setRemark("同步失败！代码:"+code);
					otdepartmentService.updateById(otdepartment1);
					//return R.failed(code,"同步失败！");
				}
			}

		}
		return R.ok("同步完成，请在列表中查看同步结果！");

	}

	@PostMapping("/getUrl" )
	public R getUrl(){
		//用户企业微信corpid放在systcorpinfo表里
		//String scorpid = "wwc649dbd1e325fb9f";
		SysUser sysUser =  new SysUser();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		sysUser.setCorpcode(corpcode);

		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}

		QueryWrapper<SysQywxApplication> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("suite_id","wwf2ede2a6b9326291");
		SysQywxApplication sysQywxApplication = sysQywxApplicationService.getOne(queryWrapper1);
		String suiteid = sysQywxApplication.getSuiteId();
		String preauthcode = sysQywxApplication.getPreAuthCode();
		String url = "https://open.work.weixin.qq.com/3rdapp/install?suite_id=" +suiteid+
				"&pre_auth_code=" +preauthcode+
				"&redirect_uri=http://hrcloud.antechunion.com/static/SetupCallBack2.html&state=wwf2ede2a6b9326291";


		QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("auth_corpid",qywxcorpid);
		queryWrapper2.eq("suite_id",suiteid);
		SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer =sysQywxApplicationAuthorizerService.getOne(queryWrapper2);
		if(StringUtils.isEmpty(sysQywxApplicationAuthorizer)){
			return R.ok(url,"请安装企业微信-E人E事-SaaS产品！",2);
		}



		return R.ok(url);

	}

	@PostMapping("/getUrlAddress" )
	public R getUrlAddress(){
		//用户企业微信corpid放在systcorpinfo表里
		//String scorpid = "wwc649dbd1e325fb9f";
		SysUser sysUser =  new SysUser();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		sysUser.setCorpcode(corpcode);

		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}

		QueryWrapper<SysQywxApplication> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("suite_id","ww9a171b67684cdfcf");
		SysQywxApplication sysQywxApplication = sysQywxApplicationService.getOne(queryWrapper1);
		String suiteid = sysQywxApplication.getSuiteId();
		String preauthcode = sysQywxApplication.getPreAuthCode();
		String url = "https://open.work.weixin.qq.com/3rdapp/install?suite_id=" +suiteid+
				"&pre_auth_code=" +preauthcode+
				"&redirect_uri=http://hrcloud.antechunion.com/static/SetupCallBack2.html&state=ww9a171b67684cdfcf";


		QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("auth_corpid",qywxcorpid);
		queryWrapper2.eq("suite_id",suiteid);
		SysQywxApplicationAuthorizer sysQywxApplicationAuthorizer =sysQywxApplicationAuthorizerService.getOne(queryWrapper2);
		if(StringUtils.isEmpty(sysQywxApplicationAuthorizer)){
			return R.ok(url,"请安装企业微信-E人E事-SaaS产品！",2);
		}



		return R.ok(url);

	}


}
