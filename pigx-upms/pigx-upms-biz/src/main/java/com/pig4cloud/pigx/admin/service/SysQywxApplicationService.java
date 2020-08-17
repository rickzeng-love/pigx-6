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

package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.SysQywxApplication;

import java.util.Map;

/**
 * 企业微信第三方应用信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:38:59
 */
public interface SysQywxApplicationService extends IService<SysQywxApplication> {
	public void getSuiteApplication();

	public String getSuiteAccessToken(SysQywxApplication sysQywxApplication);

	public String getPreAuthCode(SysQywxApplication sysQywxApplication);

	public String setSessionInfo();
	public String setSessionInfoAddress();

	public String getPermanentCodeAndAccessToken(SysQywxApplication sysQywxApplication);

	//查询成员
	public String selectUser(Map map);
	//创建成员
	public String createUser(Map map);

	//更新成员
	public String updateUser(Map map);

	//删除成员
	public String deleteUser(Map map);

	//查询部门
	public String selectDepartment(Map map);
	//创建部门
	public String createDepartment(Map map);

	//更新部门
	public String updateDepartment(Map map);

	//删除部门
	public String deleteDepartment(Map map);

	//发送消息
	public String sendMessage(Map map);
	//发送消息
	public String sendMessageB(Map map);

	//更新已发送消息
	public String updateSendMessage(Map map);
	//更新已发送消息
	public String updateSendMessageB(Map map);
	//获取访问用户的身份
	public String getuserinfo3red(String suiteAccessToken, String code);

	//获取当前企业的access_token
	public String getCORPAccessToken(String qywxcorpid);
	//获取当前企业的access_token
	public String getCORPAccessTokenB(String qywxcorpid);



}
