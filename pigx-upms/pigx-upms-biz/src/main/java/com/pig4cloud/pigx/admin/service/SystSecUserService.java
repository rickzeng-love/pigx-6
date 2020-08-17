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

package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.api.dto.SystSecUserDTO;
import com.pig4cloud.pigx.admin.api.dto.UserInfo;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.entity.SystSecUser;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * @author lengleng
 * @date 2017/10/31
 */
public interface SystSecUserService extends IService<SystSecUser> {
	/**
	 * 查询用户信息
	 *
	 * @param systSecUser 用户
	 * @return userInfo
	 */
	UserInfo findUserInfo(SystSecUser systSecUser);

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param systSecUserDTO 参数列表
	 * @return
	 */
	IPage getUsersWithRolePage(Page page,SystSecUserDTO systSecUserDTO);

	/**
	 * 删除用户
	 *
	 * @param systSecUser 用户
	 * @return boolean
	 */
	Boolean deleteUserById(SystSecUser systSecUser);

	/**
	 * 更新当前用户基本信息
	 *
	 * @param systSecUserDTO 用户信息
	 * @return Boolean
	 */
	R<Boolean> updateUserInfo(SystSecUserDTO systSecUserDTO);

	/**
	 * 更新指定用户信息
	 *
	 * @param systSecUserDTO 用户信息
	 * @return
	 */
	Boolean updateUser(SystSecUserDTO systSecUserDTO);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO selectUserVoById(Integer id);

	/**
	 * 查询上级部门的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	List<SysUser> listAncestorUsers(String username);

	/**
	 * 保存用户信息
	 *
	 * @param systSecUserDTO DTO 对象
	 * @return success/fail
	 */
	Boolean saveUser(SystSecUserDTO systSecUserDTO);
}