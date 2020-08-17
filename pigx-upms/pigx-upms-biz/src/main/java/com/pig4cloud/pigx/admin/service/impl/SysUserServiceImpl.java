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

package com.pig4cloud.pigx.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.dto.UserDTO;
import com.pig4cloud.pigx.admin.api.dto.UserInfo;
import com.pig4cloud.pigx.admin.api.entity.*;
import com.pig4cloud.pigx.admin.api.vo.MenuVO;
import com.pig4cloud.pigx.admin.api.vo.RoleVo;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysUserMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.CommonUtil;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.LocalDateUtil;
import com.pig4cloud.pigx.admin.util.SymmetricEncoder;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.constant.SecurityConstants;
import com.pig4cloud.pigx.common.core.constant.enums.LoginTypeEnum;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import com.pig4cloud.pigx.qrcode.QrCodeGenWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2017/10/31
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final SysMenuService sysMenuService;
	private final SysRoleService sysRoleService;
	private final SysUserMapper sysUserMapper;
	private final SysDeptService sysDeptService;
	private final SysUserRoleService sysUserRoleService;
	private final SysDeptRelationService sysDeptRelationService;
	private final SysUserMapper userMapper;
	private final RedisTemplate redisTemplate;
	private final SystcorpinfoService systcorpinfoService;
	private final OtcompchangeAllService otcompchangeAllService;
	private  final OtcompanyService otcompanyService;
	private final  EtemployeeService etemployeeService;
	private final CacheManager cacheManager;
	private final SysPublicParamService sysPublicParamService;
	private final MinioTemplate minioTemplate;
	private final  CtemployeeService ctemployeeService;
	private final  AtstatusService atstatusService;
	private final  AtcdAgentmodeUserService atcdAgentmodeUserService;
	private final AtstaffRegisterService atstaffRegisterService;
	private final  ReminderEventService reminderEventService;
	private final ReminderEventCommonService reminderEventCommonService;
	private final EtcdEmpgradeService etcdEmpgradeService;
	private final EtcdEmpgradeCommonService etcdEmpgradeCommonService;
	private final EtcdContypeService etcdContypeService;
	private final  EtcdContypeCommonService etcdContypeCommonService;
	private  final EtcdConpropertyService etcdConpropertyService;
	private final EtcdConpropertyCommonService etcdConpropertyCommonService;
	private final OtcdDeptypeCommonService otcdDeptypeCommonService;
	private final OtcdDeptypeService otcdDeptypeService;
	private final OtcdJobtypeCommonService otcdJobtypeCommonService;
	private final OtcdJobtypeService otcdJobtypeService;
	private final OtcdPositionService otcdPositionService;
	private final OtcdPositionCommonService otcdPositionCommonService;
	private final OtcdPositiongradeService otcdPositiongradeService;
	private final OtcdPositiongradeCommonService otcdPositiongradeCommonService;
	private final  CtcdSalarygradeService ctcdSalarygradeService;
	private final  CtcdSalarygradeCommonService ctcdSalarygradeCommonService;
	private final CtcdSalarykindService ctcdSalarykindService;
	private final CtcdSalarykindCommonService ctcdSalarykindCommonService;
	private final  CtcdPaymodeCommonService ctcdPaymodeCommonService;
	private final  CtcdPaymodeService ctcdPaymodeService;
	private final  CtcdBankCommonService ctcdBankCommonService;
	private final  CtcdBankService ctcdBankService;
	private final  CtcdSalarytypeCommonService ctcdSalarytypeCommonService;
	private final  CtcdSalarytypeService ctcdSalarytypeService;
	private final  CtcdBenrateCommonService ctcdBenrateCommonService;
	private final  CtcdBenrateService ctcdBenrateService;
	private final  CtbenefitRegisterService ctbenefitRegisterService;
	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		baseMapper.insert(sysUser);
		List<SysUserRole> userRoleList = userDto.getRole()
				.stream().map(roleId -> {
					SysUserRole userRole = new SysUserRole();
					userRole.setUserId(sysUser.getUserId());
					userRole.setRoleId(roleId);
					return userRole;
				}).collect(Collectors.toList());
		//清空userinfo
		cacheManager.getCache(CacheConstants.USER_DETAILS).clear();
		return sysUserRoleService.saveBatch(userRoleList);
	}

	/**
	 * 通过查用户的全部信息
	 *
	 * @param sysUser 用户
	 * @return
	 */
	@Override
	public UserInfo findUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);
		//设置角色列表  （ID）
		List<Integer> roleIds = sysRoleService.findRolesByUserId(sysUser.getUserId())
				.stream()
				.map(SysRole::getRoleId)
				.collect(Collectors.toList());
		userInfo.setRoles(ArrayUtil.toArray(roleIds, Integer.class));
		String menuMp  = null;
		//设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		roleIds.forEach(roleId -> {
			List<String> permissionList = sysMenuService.findMenuByRoleId(roleId,menuMp)
					.stream()
					.filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
					.map(MenuVO::getPermission)
					.collect(Collectors.toList());
			permissions.addAll(permissionList);
		});
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	@Override
	public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO, new DataScope());
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO selectUserVoById(Integer id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
	public Boolean deleteUserById(SysUser sysUser) {
		sysUserRoleService.deleteByUserId(sysUser.getUserId());
		this.removeById(sysUser.getUserId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public R<Boolean> updateUserInfo(UserDTO userDto) {
		userDto.setAccount("6666");
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getAccount());
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getPassword())
				&& StrUtil.isNotBlank(userDto.getNewpassword1())) {
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
			} else {
				log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
				return R.ok(Boolean.FALSE, "原密码错误，修改失败");
			}
		}
		sysUser.setPhone(userDto.getPhone());
		sysUser.setUserId(userVO.getUserId());
		sysUser.setAvatar(userDto.getAvatar());
		return R.ok(this.updateById(sysUser));
	}

	@Override
	@Caching(evict = {@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true),
			@CacheEvict(value = CacheConstants.MENU_DETAILS_MOBILE, allEntries = true),
			@CacheEvict(value = CacheConstants.MENU_DETAILS_PC, allEntries = true),
			@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	})
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());

		if (StrUtil.isNotBlank(userDto.getPassword())) {
			sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		}
		this.updateById(sysUser);

		sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
				.eq(SysUserRole::getUserId, userDto.getUserId()));
		userDto.getRole().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * 查询上级部门的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsers(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getUsername, username));

		SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
		if (sysDept == null) {
			return null;
		}

		Integer parentId = sysDept.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getDeptId, parentId));
	}

	/**
	 * 获取当前用户的子部门信息
	 *
	 * @return 子部门列表
	 */
	private List<Integer> getChildDepts() {
		Integer deptId = SecurityUtils.getUser().getDeptId();
		//获取当前部门的子部门
		return sysDeptRelationService
				.list(Wrappers.<SysDeptRelation>query().lambda()
						.eq(SysDeptRelation::getAncestor, deptId))
				.stream()
				.map(SysDeptRelation::getDescendant)
				.collect(Collectors.toList());
	}

	//add by gao
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#username")
	public R updateUserPassword(String username, String newpassword1,String content) {

		//加密随机码，规则：手机号
		SymmetricEncoder se=new SymmetricEncoder();
		String code = se.AESDncode(username, content);
		log.info("解密随机码为:{}，{}", username, code);
		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.UPDATEPASSWORD.getType() + StringPool.AT + username);
		boolean b = false;
		if (codeObj != null) {
			b = StrUtil.equals(code,codeObj.toString());
			if(!b){
				return R.ok("", "操作异常,请返回重新获取验证码",2);
			}
		}else{
			log.info("手机号验证码已过期:{}，{}", username, "");
			return R.ok("", "操作超时,请返回重新获取验证码",1);
		}


		UserVO userVO = baseMapper.getUserVoByUsername(username);
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(newpassword1)) {
			//ENCODER.matches(userDto.getPassword(), userVO.getPassword())；
			//if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(newpassword1));
			//} else {
			//	log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
			//return R.failed(null, "原密码错误，修改失败");
			//}
		}
		sysUser.setPhone(username);
		sysUser.setUserId(userVO.getUserId());
		return R.ok(this.updateById(sysUser),"修改成功！");
	}

	/**
	 * 修改密码校验 验证码
	 * @param  userDto
	 * @return code
	 */
	@Override
	public R<Boolean> verfiySmsCode(UserDTO userDto) {
		String mobile = userDto.getPhone();
		List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getPhone, mobile));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}",mobile);
			//return R.ok(Boolean.FALSE, "手机号未注册");
			return R.ok(null, "手机号未注册",2);
		}

		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile);
		String code = userDto.getCode();
		boolean b = false;
		if (codeObj != null) {
			b = StrUtil.equals(code,codeObj.toString());
		}else{
			log.info("手机号验证码已过期:{}，{}", mobile, codeObj.toString());
			return R.ok(Boolean.FALSE, "手机号验证码已过期");
		}
		if(b){
			return R.ok(Boolean.TRUE, code);
		}else{
			return R.ok(Boolean.TRUE, "验证码不匹配，请重新输入！");
		}
		//String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));

		//log.debug("手机号生成验证码成功:{},{}", mobile, code);
		//redisTemplate.opsForValue().set(
		//CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile
		//	, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

	}
	@Transactional
	@Override
	public R userRegister(String username,String compname,String account){
		String currentTime = DateUtils.getTimeString();
		/*
		Map map = new HashMap();
		map.put("username",username);
		map.put("compname",compname);
		map.put("account",account);
		//map.put("result","");
		baseMapper.userRegister(map);
		int result = (Integer)map.get("result");
		if(result==1){
			return R.failed(null, "手机号已注册，不能重复注册！");
		}*/
		/*
		SysUser sysUser = new SysUser();
		sysUser.setAccount(account);
		sysUser.setUsername(username);
		sysUser.setPhone(account);
		sysUser.setBadge(account);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(account));
		baseMapper.insert(sysUser);
		Integer userId = sysUser.getUserId();
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(userId);
		sysUserRole.setRoleId(1);
		sysUserRoleService.save(sysUserRole);
		 */
		String corpcode = UUID.randomUUID().toString();
		//生成公司二维码
		// 生成带logo的二维码
		String webdomain = "";
		String url = "/personnel/qrcode?corpcode="+corpcode;
		String filepath = "";
		String fileName = corpcode+"-logo"+".png";
		String ewmFileName = corpcode+".png";
		Cache cache = cacheManager.getCache(CacheConstants.PARAMS_DETAILS);
		if (cache != null && cache.get("MINIO") != null) {
			filepath = (String)cache.get("MINIO").get();
		}else{
			filepath = sysPublicParamService.getSysPublicParamKeyToValue("MINIO");
		}
		if (cache != null && cache.get("WEBDOMAIN") != null) {
			webdomain = (String)cache.get("WEBDOMAIN").get();
		}else{
			webdomain = sysPublicParamService.getSysPublicParamKeyToValue("WEBDOMAIN");
		}
		InputStream inputStream= null;
		String msg = "";
		String logo ="";
		try {
			msg = webdomain+url;
			logo = filepath+ "/"+CommonConstants.BUCKET_NAME+"/"+fileName;
			BufferedImage bi = QrCodeGenWrapper.createQrCodeConfig()
					.setMsg(msg)
					.setW(300)
					.setOnColor(0xffff0000)
					.setOffColor(0xffffffff)
					.setPadding(0)
					//.setLogo(logo)
					.asBufferedImage();
			inputStream =CommonUtil.bufferedImageToInputStream(bi);
			minioTemplate.putObject(CommonConstants.BUCKET_NAME, ewmFileName, inputStream,"image/png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//1、校验手机号是否已存在
		List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getAccount, account));
		if (!(CollUtil.isEmpty(userList))) {
			return R.failed("手机号已存在");
		}

		//保存组织信systcorpinfo
		Systcorpinfo systcorpinfo = new Systcorpinfo();

		systcorpinfo.setCorpcode(corpcode);
		systcorpinfo.setCorpname(compname);
		systcorpinfo.setAllowempnum(100);
		systcorpinfo.setContact(username);
		systcorpinfo.setPayrollgroupnum(1);
		systcorpinfo.setRegdate(DateUtils.getTimeString());
		systcorpinfo.setIsinitialized(0);
		systcorpinfo.setQrcode(logo);
		systcorpinfoService.save(systcorpinfo);
		Integer corpid = systcorpinfo.getId();
		log.info("id:"+systcorpinfo.getId());
		//插入用户表sys_user
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser.setCorpid(corpid);
		sysUser.setCorpcode(corpcode);
		sysUser.setCorpname(compname);
		sysUser.setCompname(compname);
		sysUser.setAccount(account);
		sysUser.setPhone(account);
		sysUser.setLockFlag("0");
		sysUser.setCreateTime(LocalDateUtil.dateToLocalDateTime(new Date()));
		sysUser.setDelFlag("0");
		sysUser.setStatus(0);
		sysUser.setQywxFlag(1);
		save(sysUser);
		//插入公司信息
		Otcompany otcompany = new Otcompany();
		otcompany.setCorpcode(corpcode);
		otcompany.setTitle(compname);
		otcompany.setCorpid(systcorpinfo.getId());
		otcompany.setRegTel(account);
		otcompany.setCreatedate(DateUtils.getTimeString());
		otcompany.setEffectdate(DateUtils.getTimeString());
		otcompany.setIsregister(1);
		otcompanyService.save(otcompany);
		//存入历史表
		OtcompchangeAll otcompchangeAll  = new OtcompchangeAll();
		otcompchangeAll.setCorpcode(corpcode);
		otcompchangeAll.setTitle(compname);
		otcompchangeAll.setRegby(sysUser.getUserId());
		otcompchangeAll.setRegdate(LocalDateUtil.dateToLocalDateTime(new Date()));
		otcompchangeAll.setIsregister(1);
		otcompchangeAll.setType(1);
		otcompchangeAllService.save(otcompchangeAll);

		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		etemployee.setName(username);
		etemployee.setEmpstatus(1);
		etemployee.setMobile(account);
		etemployee.setCreatedate(DateUtils.getTimeString());
		etemployee.setCompid(otcompany.getCompid());
		etemployee.setCorpid(corpid);
		etemployeeService.save(etemployee);
		//更新employee EID
		SysUser sysUser2 = new SysUser();
		sysUser2.setUserId(sysUser.getUserId());
		sysUser2.setEid(etemployee.getEid());
		sysUser2.setCompid(otcompany.getCompid());
		updateById(sysUser2);
		//插入薪资表
		/*Integer eid = etemployee.getEid();
		log.info("eid:"+etemployee.getEid());
		Ctemployee ctemployee = new Ctemployee();
		ctemployee.setEid(eid);
		ctemployee.setName(username);
		ctemployee.setCorpcode(corpcode);
		ctemployee.setCorpid(corpid);
		ctemployee.setCompid(otcompany.getCompid());
		ctemployee.setCreatedate(currentTime);
		ctemployeeService.save(ctemployee);

		CtbenefitRegister ctbenefitRegister = new CtbenefitRegister();*/
		//ctbenefitRegister.setCorpcode(corpcode);
		//插入考勤
		//Atstatus atstatus = new Atstatus();
		//atstatus.setEid(eid);
		//atstatusService.save(atstatus);
		//赋予管理员权限
	/*	SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(sysUser.getUserId());
		sysUserRole.setRoleId(1);
		sysUserRoleService.save(sysUserRole);*/
		SysUserRole sysUserRole2 = new SysUserRole();
		sysUserRole2.setUserId(sysUser.getUserId());
		sysUserRole2.setRoleId(6);
		sysUserRoleService.save(sysUserRole2);
		SysUserRole sysUserRole3 = new SysUserRole();
		sysUserRole3.setUserId(sysUser.getUserId());
		sysUserRole3.setRoleId(7);
		sysUserRoleService.save(sysUserRole3);
		SysUserRole sysUserRole4 = new SysUserRole();
		sysUserRole4.setUserId(sysUser.getUserId());
		sysUserRole4.setRoleId(8);
		sysUserRoleService.save(sysUserRole4);
		SysUserRole sysUserRole5 = new SysUserRole();
		sysUserRole5.setUserId(sysUser.getUserId());
		sysUserRole5.setRoleId(9);
		sysUserRoleService.save(sysUserRole5);
		SysUserRole sysUserRole6 = new SysUserRole();
		sysUserRole6.setUserId(sysUser.getUserId());
		sysUserRole6.setRoleId(10);
		sysUserRoleService.save(sysUserRole6);
		SysUserRole sysUserRole7 = new SysUserRole();
		sysUserRole7.setUserId(sysUser.getUserId());
		sysUserRole7.setRoleId(11);
		sysUserRoleService.save(sysUserRole7);

		//插入考勤管理员权限
		//考虑到注册的时候需要处理的表太多，考勤相关可不开发，
		//在考勤模块做个单独开启考勤的口，在点击开启考勤是处理
		//这些数据
		/*
		AtcdAgentmodeUser atcdAgentmodeUser = new AtcdAgentmodeUser();
		atcdAgentmodeUser.setCorpcode(corpcode);
		atcdAgentmodeUser.setCorpid(corpid);
		atcdAgentmodeUser.setAid(null);
		atcdAgentmodeUser.setUserid(sysUser.getUserId());
		atcdAgentmodeUser.setUserName(sysUser.getUsername());
		atcdAgentmodeUserService.save(atcdAgentmodeUser);
		 */
		//考勤登记表

		/*Insert Into atStaff_Register(Term,EID,Badge,Name,CompID,DepID,JobID,JoinDate,AID)
		Select GetDate() ,a.EID,a.Badge,a.Name,a.CompID,a.DepID,a.JobID,a.JoinDate,
				(Select Top 1 k.AID From atCD_AgentMode_User k,etStaff_Register m Where K.UserID=m.InitializedBy And m.CertNo=a.CertNo)
		From Inserted a
		Where Not Exists(Select 1 From atStaff_Register b Where a.EID=b.EID)
		And Not Exists(Select 1 From atStaff_All c Where a.EID=c.EID)
		and a.JoinDate is Not NULL
				*/
		//插入考勤登记表
	/*	AtstaffRegister atstaffRegister = new AtstaffRegister();
		atstaffRegister.setTerm(DateUtils.getNow());
		atstaffRegister.setEid(eid);
		atstaffRegister.setBadge(sysUser.getBadge());
		atstaffRegister.setName(sysUser.getUsername());
		atstaffRegister.setCompid(otcompany.getCompid());
		atstaffRegisterService.save(atstaffRegister);*/

		//代办提醒
		List<ReminderEventCommon> reminderEventCommonList = reminderEventCommonService.list();
		List reminderEventList = new ArrayList(reminderEventCommonList.size());
		ReminderEvent reminderEvent = null;
		ReminderEventCommon reminderEventCommon = null;
		for(int i=0;i<reminderEventCommonList.size();i++){
			reminderEventCommon = reminderEventCommonList.get(i);
			reminderEventCommon.setId(null);
			reminderEvent = new ReminderEvent();
			BeanUtils.copyProperties(reminderEventCommon,reminderEvent);
			reminderEvent.setCorpcode(corpcode);
			reminderEvent.setCorpid(systcorpinfo.getId());
			reminderEventList.add(i,reminderEvent);
		}
		reminderEventService.saveBatch(reminderEventList);


		String code = RandomUtil.randomNumbers(Integer.parseInt("10"));
		log.debug("生成随机码:{},{}", account, code);
		redisTemplate.opsForValue().set(
				CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.USERREGISTER.getType() + StringPool.AT + account
				, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
		return R.ok(code, "注册成功！");

	}

	@Transactional
	@Override
	public R userRegisterForPC(String username,String password,String compname,String account){
		String currentTime = DateUtils.getTimeString();
		String corpcode = UUID.randomUUID().toString();
		//生成公司二维码
		// 生成带logo的二维码
		String webdomain = "";
		String url = "/personnel/qrcode?corpcode="+corpcode;
		String filepath = "";
		String fileName = corpcode+"-logo"+".png";
		String ewmFileName = corpcode+".png";
		Cache cache = cacheManager.getCache(CacheConstants.PARAMS_DETAILS);
		if (cache != null && cache.get("MINIO") != null) {
			filepath = (String)cache.get("MINIO").get();
		}else{
			filepath = sysPublicParamService.getSysPublicParamKeyToValue("MINIO");
		}
		if (cache != null && cache.get("WEBDOMAIN") != null) {
			webdomain = (String)cache.get("WEBDOMAIN").get();
		}else{
			webdomain = sysPublicParamService.getSysPublicParamKeyToValue("WEBDOMAIN");
		}
		InputStream inputStream= null;
		String msg = "";
		String logo ="";
		try {
			//http://hrcloud.antechunion.com:9000/image
			msg = webdomain+url;
			logo = filepath+ "/"+CommonConstants.BUCKET_NAME+"/"+fileName;
			BufferedImage bi = QrCodeGenWrapper.createQrCodeConfig()
					.setMsg(msg)
					.setW(300)
					.setOnColor(0xffff0000)
					.setOffColor(0xffffffff)
					.setPadding(0)
					//.setLogo(logo)
					.asBufferedImage();
			inputStream =CommonUtil.bufferedImageToInputStream(bi);
			minioTemplate.putObject(CommonConstants.BUCKET_NAME, ewmFileName, inputStream,"image/png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//1、校验手机号是否已存在
		List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getAccount, account));
		if (!(CollUtil.isEmpty(userList))) {
			return R.failed("手机号已存在");
		}

		//保存组织信systcorpinfo
		Systcorpinfo systcorpinfo = new Systcorpinfo();

		systcorpinfo.setCorpcode(corpcode);
		systcorpinfo.setCorpname(compname);
		systcorpinfo.setAllowempnum(100);
		systcorpinfo.setContact(username);
		systcorpinfo.setPayrollgroupnum(1);
		systcorpinfo.setRegdate(DateUtils.getTimeString());
		systcorpinfo.setIsinitialized(0);
		systcorpinfo.setMobile(account);
		systcorpinfo.setQrcode(logo);
		systcorpinfoService.save(systcorpinfo);
		Integer corpid = systcorpinfo.getId();
		log.info("id:"+systcorpinfo.getId());
		//插入用户表sys_user
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser.setCorpid(corpid);
		sysUser.setCorpcode(corpcode);
		sysUser.setCorpname(compname);
		sysUser.setCompname(compname);
		sysUser.setAccount(account);
		sysUser.setPassword(ENCODER.encode(password));
		sysUser.setPhone(account);
		sysUser.setLockFlag("0");
		sysUser.setIsadmin(0);
		sysUser.setCreateTime(LocalDateUtil.dateToLocalDateTime(new Date()));
		sysUser.setDelFlag("0");
		sysUser.setQywxFlag(1);
		save(sysUser);
		//插入公司信息
		Otcompany otcompany = new Otcompany();
		otcompany.setCorpcode(corpcode);
		otcompany.setTitle(compname);
		otcompany.setCorpid(systcorpinfo.getId());
		otcompany.setRegTel(account);
		otcompany.setCreatedate(DateUtils.getTimeString());
		otcompany.setEffectdate(DateUtils.getTimeString());
		otcompany.setIsregister(1);
		otcompanyService.save(otcompany);
		//存入历史表
		OtcompchangeAll otcompchangeAll  = new OtcompchangeAll();
		otcompchangeAll.setCorpcode(corpcode);
		otcompchangeAll.setTitle(compname);
		otcompchangeAll.setRegby(sysUser.getUserId());
		otcompchangeAll.setRegdate(LocalDateUtil.dateToLocalDateTime(new Date()));
		otcompchangeAll.setIsregister(1);
		otcompchangeAllService.save(otcompchangeAll);

		Etemployee etemployee = new Etemployee();
		etemployee.setCorpcode(corpcode);
		etemployee.setName(username);
		etemployee.setEmpstatus(1);
		etemployee.setMobile(account);
		etemployee.setCreatedate(DateUtils.getTimeString());
		etemployee.setCompid(otcompany.getCompid());
		etemployee.setCorpid(corpid);
		etemployeeService.save(etemployee);
		//更新employee EID
		SysUser sysUser2 = new SysUser();
		sysUser2.setUserId(sysUser.getUserId());
		sysUser2.setEid(etemployee.getEid());
		sysUser2.setCompid(otcompany.getCompid());
		updateById(sysUser2);
		/*//插入薪资表
		Integer eid = etemployee.getEid();
		log.info("eid:"+etemployee.getEid());
		Ctemployee ctemployee = new Ctemployee();
		ctemployee.setEid(eid);
		ctemployee.setName(username);
		ctemployee.setCorpcode(corpcode);
		ctemployee.setCompid(otcompany.getCompid());
		ctemployee.setCreatedate(currentTime);
		ctemployeeService.save(ctemployee);*/
		//赋予管理员权限
	/*	SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(sysUser.getUserId());
		sysUserRole.setRoleId(11);
		sysUserRoleService.save(sysUserRole);*/

/*
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(sysUser.getUserId());
		sysUserRole.setRoleId(1);
		sysUserRoleService.save(sysUserRole);*/
		SysUserRole sysUserRole2 = new SysUserRole();
		sysUserRole2.setUserId(sysUser.getUserId());
		sysUserRole2.setRoleId(6);
		sysUserRoleService.save(sysUserRole2);
		SysUserRole sysUserRole3 = new SysUserRole();
		sysUserRole3.setUserId(sysUser.getUserId());
		sysUserRole3.setRoleId(7);
		sysUserRoleService.save(sysUserRole3);
		SysUserRole sysUserRole4 = new SysUserRole();
		sysUserRole4.setUserId(sysUser.getUserId());
		sysUserRole4.setRoleId(8);
		sysUserRoleService.save(sysUserRole4);
		SysUserRole sysUserRole5 = new SysUserRole();
		sysUserRole5.setUserId(sysUser.getUserId());
		sysUserRole5.setRoleId(9);
		sysUserRoleService.save(sysUserRole5);
		SysUserRole sysUserRole6 = new SysUserRole();
		sysUserRole6.setUserId(sysUser.getUserId());
		sysUserRole6.setRoleId(10);
		sysUserRoleService.save(sysUserRole6);
		SysUserRole sysUserRole7 = new SysUserRole();
		sysUserRole7.setUserId(sysUser.getUserId());
		sysUserRole7.setRoleId(11);
		sysUserRoleService.save(sysUserRole7);
		SysUserRole sysUserRole8 = new SysUserRole();
		sysUserRole8.setUserId(sysUser.getUserId());
		sysUserRole8.setRoleId(12);
		sysUserRoleService.save(sysUserRole8);
		SysUserRole sysUserRole9 = new SysUserRole();
		sysUserRole9.setUserId(sysUser.getUserId());
		sysUserRole9.setRoleId(13);
		sysUserRoleService.save(sysUserRole9);


		//代办提醒
		List<ReminderEventCommon> reminderEventCommonList = reminderEventCommonService.list();
		List reminderEventList = new ArrayList(reminderEventCommonList.size());
		ReminderEvent reminderEvent = null;
		ReminderEventCommon reminderEventCommon = null;
		for(int i=0;i<reminderEventCommonList.size();i++){
			reminderEventCommon = reminderEventCommonList.get(i);
			reminderEventCommon.setId(null);
			reminderEvent = new ReminderEvent();
			BeanUtils.copyProperties(reminderEventCommon,reminderEvent);
			reminderEvent.setCorpcode(corpcode);
			reminderEvent.setCorpid(systcorpinfo.getId());
			reminderEventList.add(i,reminderEvent);
		}
		reminderEventService.saveBatch(reminderEventList);

		//员工级别
		List<EtcdEmpgradeCommon> empgradeCommonList = etcdEmpgradeCommonService.list();
		List empgradeList = new ArrayList(empgradeCommonList.size());
		EtcdEmpgrade etcdEmpgrade  = null;
		EtcdEmpgradeCommon etcdEmpgradeCommon  = null;
		for(int i=0;i<empgradeCommonList.size();i++){
			etcdEmpgradeCommon = empgradeCommonList.get(i);
			etcdEmpgradeCommon.setId(null);
			etcdEmpgrade = new EtcdEmpgrade();
			BeanUtils.copyProperties(etcdEmpgradeCommon,etcdEmpgrade);
			etcdEmpgrade.setCorpcode(corpcode);
			etcdEmpgrade.setCorpid(systcorpinfo.getId());
			empgradeList.add(i,etcdEmpgrade);
		}
		etcdEmpgradeService.saveBatch(empgradeList);
		//合同类型
		List<EtcdContypeCommon> etcdContypeCommonList = etcdContypeCommonService.list();
		List cdContypeList = new ArrayList(etcdContypeCommonList.size());
		EtcdContype etcdContype  = null;
		EtcdContypeCommon etcdContypeCommon  = null;
		for(int i=0;i<etcdContypeCommonList.size();i++){
			etcdContypeCommon = etcdContypeCommonList.get(i);
			etcdContypeCommon.setId(null);
			etcdContype = new EtcdContype();
			BeanUtils.copyProperties(etcdContypeCommon,etcdContype);
			etcdContype.setCorpcode(corpcode);
			etcdContype.setCorpid(systcorpinfo.getId());
			cdContypeList.add(i,etcdContype);
		}
		etcdContypeService.saveBatch(cdContypeList);
		//合同属性
		//etcdConproperty
		List<EtcdConpropertyCommon> etcdConpropertyCommonList = etcdConpropertyCommonService.list();
		List cdConpropertyList = new ArrayList(etcdConpropertyCommonList.size());
		EtcdConproperty etcdConproperty  = null;
		EtcdConpropertyCommon etcdConpropertyCommon  = null;
		for(int i=0;i<etcdConpropertyCommonList.size();i++){
			etcdConpropertyCommon = etcdConpropertyCommonList.get(i);
			etcdConpropertyCommon.setId(null);
			etcdConproperty = new EtcdConproperty();
			BeanUtils.copyProperties(etcdConpropertyCommon,etcdConproperty);
			etcdConproperty.setCorpcode(corpcode);
			etcdConproperty.setCorpid(systcorpinfo.getId());
			cdConpropertyList.add(i,etcdConproperty);
		}
		etcdConpropertyService.saveBatch(cdConpropertyList);
		//部门类型
		List<OtcdDeptypeCommon> otcdDeptypeCommonList = otcdDeptypeCommonService.list();
		List cdDeptypeList = new ArrayList(otcdDeptypeCommonList.size());
		OtcdDeptype otcdDeptype  = null;
		OtcdDeptypeCommon otcdDeptypeCommon  = null;
		for(int i=0;i<otcdDeptypeCommonList.size();i++){
			otcdDeptypeCommon = otcdDeptypeCommonList.get(i);
			otcdDeptypeCommon.setId(null);
			otcdDeptype = new OtcdDeptype();
			BeanUtils.copyProperties(otcdDeptypeCommon,otcdDeptype);
			otcdDeptype.setCorpcode(corpcode);
			otcdDeptype.setCorpid(systcorpinfo.getId());
			cdDeptypeList.add(i,otcdDeptype);
		}
		otcdDeptypeService.saveBatch(cdDeptypeList);

		//岗位类型
		List<OtcdJobtypeCommon> otcdJobtypeCommonList = otcdJobtypeCommonService.list();
		List cdJobtypeList = new ArrayList(otcdJobtypeCommonList.size());
		OtcdJobtype otcdJobtype  = null;
		OtcdJobtypeCommon otcdJobtypeCommon  = null;
		for(int i=0;i<otcdJobtypeCommonList.size();i++){
			otcdJobtypeCommon = otcdJobtypeCommonList.get(i);
			otcdJobtypeCommon.setId(null);
			otcdJobtype = new OtcdJobtype();
			BeanUtils.copyProperties(otcdJobtypeCommon,otcdJobtype);
			otcdJobtype.setCorpcode(corpcode);
			otcdJobtype.setCorpid(systcorpinfo.getId());
			cdJobtypeList.add(i,otcdJobtype);
		}
		otcdJobtypeService.saveBatch(cdJobtypeList);

		//职务OtcdPosition
		List<OtcdPositionCommon> otcdPositionCommonList = otcdPositionCommonService.list();
		List cdPositionList = new ArrayList(otcdPositionCommonList.size());
		OtcdPosition otcdPosition  = null;
		OtcdPositionCommon otcdPositionCommon  = null;
		for(int i=0;i<otcdPositionCommonList.size();i++){
			otcdPositionCommon = otcdPositionCommonList.get(i);
			otcdPositionCommon.setId(null);
			otcdPosition = new OtcdPosition();
			BeanUtils.copyProperties(otcdPositionCommon,otcdPosition);
			otcdPosition.setCorpcode(corpcode);
			otcdPosition.setCorpid(systcorpinfo.getId());
			cdPositionList.add(i,otcdPosition);
		}
		otcdPositionService.saveBatch(cdPositionList);
		//OtcdPositiongrade
		//职等
		List<OtcdPositiongradeCommon> otcdPositiongradeCommonList = otcdPositiongradeCommonService.list();
		List cdPositiongradeList = new ArrayList(otcdPositiongradeCommonList.size());
		OtcdPositiongrade otcdPositiongrade  = null;
		OtcdPositiongradeCommon otcdPositiongradeCommon  = null;
		for(int i=0;i<otcdPositiongradeCommonList.size();i++){
			otcdPositiongradeCommon = otcdPositiongradeCommonList.get(i);
			otcdPositiongradeCommon.setId(null);
			otcdPositiongrade = new OtcdPositiongrade();
			BeanUtils.copyProperties(otcdPositiongradeCommon,otcdPositiongrade);
			otcdPositiongrade.setCorpcode(corpcode);
			otcdPositiongrade.setCorpid(systcorpinfo.getId());
			cdPositiongradeList.add(i,otcdPositiongrade);
		}
		otcdPositiongradeService.saveBatch(cdPositiongradeList);
		/*//薪资等级 CtcdSalarygrade
		List<CtcdSalarygradeCommon> ctcdSalarygradeCommonList = ctcdSalarygradeCommonService.list();
		List cdSalarygradeList = new ArrayList(ctcdSalarygradeCommonList.size());
		CtcdSalarygrade ctcdSalarygrade  = null;
		CtcdSalarygradeCommon ctcdSalarygradeCommon  = null;
		for(int i=0;i<ctcdSalarygradeCommonList.size();i++){
			ctcdSalarygradeCommon = ctcdSalarygradeCommonList.get(i);
			ctcdSalarygradeCommon.setId(null);
			ctcdSalarygrade = new CtcdSalarygrade();
			BeanUtils.copyProperties(ctcdSalarygradeCommon,ctcdSalarygrade);
			ctcdSalarygrade.setCorpcode(corpcode);
			ctcdSalarygrade.setCorpid(systcorpinfo.getId());
			cdSalarygradeList.add(i,ctcdSalarygrade);
		}
		ctcdSalarygradeService.saveBatch(cdSalarygradeList);
		//薪资架构
		//CtcdSalarykind
		List<CtcdSalarykindCommon> ctcdSalarykindCommonList = ctcdSalarykindCommonService.list();
		List ctcdSalarykindList = new ArrayList(ctcdSalarykindCommonList.size());
		CtcdSalarykind ctcdSalarykind  = null;
		CtcdSalarykindCommon ctcdSalarykindCommon  = null;
		for(int i=0;i<ctcdSalarygradeCommonList.size();i++){
			ctcdSalarykindCommon = ctcdSalarykindCommonList.get(i);
			ctcdSalarykindCommon.setId(null);
			ctcdSalarykind = new CtcdSalarykind();
			BeanUtils.copyProperties(ctcdSalarykindCommon,ctcdSalarykind);
			ctcdSalarykind.setCorpcode(corpcode);
			ctcdSalarykind.setCorpid(systcorpinfo.getId());
			ctcdSalarykindList.add(i,ctcdSalarykind);
		}
		ctcdSalarykindService.saveBatch(ctcdSalarykindList);
		//支付方式 ctcdPaymode
		List<CtcdPaymodeCommon> ctcdPaymodeCommonList = ctcdPaymodeCommonService.list();
		List cdPaymodeList = new ArrayList(ctcdPaymodeCommonList.size());
		CtcdPaymode ctcdPaymode  = null;
		CtcdPaymodeCommon ctcdPaymodeCommon  = null;
		for(int i=0;i<ctcdPaymodeCommonList.size();i++){
			ctcdPaymodeCommon = ctcdPaymodeCommonList.get(i);
			ctcdPaymodeCommon.setId(null);
			ctcdPaymode = new CtcdPaymode();
			BeanUtils.copyProperties(ctcdPaymodeCommon,ctcdPaymode);
			ctcdPaymode.setCorpcode(corpcode);
			ctcdPaymode.setCorpid(systcorpinfo.getId());
			cdPaymodeList.add(i,ctcdPaymode);
		}
		ctcdPaymodeService.saveBatch(cdPaymodeList);
		//银行CtcdBank
		List<CtcdBankCommon> ctcdBankCommonList = ctcdBankCommonService.list();
		List cdBankList = new ArrayList(ctcdBankCommonList.size());
		CtcdBank ctcdBank  = null;
		CtcdBankCommon ctcdBankCommon  = null;
		for(int i=0;i<ctcdBankCommonList.size();i++){
			ctcdBankCommon = ctcdBankCommonList.get(i);
			ctcdBankCommon.setId(null);
			ctcdBank = new CtcdBank();
			BeanUtils.copyProperties(ctcdBankCommon,ctcdBank);
			ctcdBank.setCorpcode(corpcode);
			ctcdBank.setCorpid(systcorpinfo.getId());
			cdBankList.add(i,ctcdBank);
		}
		ctcdBankService.saveBatch(cdBankList);
		//薪资类型
		List<CtcdSalarytypeCommon> ctcdSalarytypeCommonList = ctcdSalarytypeCommonService.list();
		List cdSalarytypeList = new ArrayList(ctcdSalarytypeCommonList.size());
		CtcdSalarytype ctcdSalarytype  = null;
		CtcdSalarytypeCommon ctcdSalarytypeCommon  = null;
		for(int i=0;i<ctcdSalarytypeCommonList.size();i++){
			ctcdSalarytypeCommon = ctcdSalarytypeCommonList.get(i);
			ctcdSalarytypeCommon.setId(null);
			ctcdSalarytype = new CtcdSalarytype();
			BeanUtils.copyProperties(ctcdSalarytypeCommon,ctcdSalarytype);
			ctcdSalarytype.setCorpcode(corpcode);
			ctcdSalarytype.setCorpid(systcorpinfo.getId());
			cdSalarytypeList.add(i,ctcdSalarytype);
		}
		ctcdSalarytypeService.saveBatch(cdSalarytypeList);
		//福利比例 CtcdBenrate
		List<CtcdBenrateCommon> ctcdBenrateCommonList = ctcdBenrateCommonService.list();
		List cdBenrateList = new ArrayList(ctcdBenrateCommonList.size());
		CtcdBenrate ctcdBenrate  = null;
		CtcdBenrateCommon ctcdBenrateCommon  = null;
		for(int i=0;i<ctcdBenrateCommonList.size();i++){
			ctcdBenrateCommon = ctcdBenrateCommonList.get(i);
			ctcdBenrateCommon.setId(null);
			ctcdBenrate = new CtcdBenrate();
			BeanUtils.copyProperties(ctcdBenrateCommon,ctcdBenrate);
			ctcdBenrate.setCorpcode(corpcode);
			ctcdBenrate.setCorpid(systcorpinfo.getId());
			cdBenrateList.add(i,ctcdBenrate);
		}
		ctcdBenrateService.saveBatch(cdBenrateList);*/
		String code = RandomUtil.randomNumbers(Integer.parseInt("10"));
		log.debug("生成随机码:{},{}", account, code);
		redisTemplate.opsForValue().set(
				CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.USERREGISTER.getType() + StringPool.AT + account
				, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
		return R.ok(code, "注册成功！");

	}
	@Override
	public  List<Map> getalladminList(Map map){
		return sysUserMapper.listalladminList(map );
	}


}
