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
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtstaffRegister;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 入职登记
 *
 * @author gaoxiao
 * @date 2020-04-10 19:14:19
 */
public interface EtstaffRegisterService extends IService<EtstaffRegister> {
	/**
	 * 入：1.黑名单校验；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeBlackList(EtstaffRegister etemployee);
	/**
	 * 入：2.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeCDRelationship(EtstaffRegister etemployee);
	/**
	 * 入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeCJRelationship(EtstaffRegister etemployee);
	/**
	 * 入：4.员工工号已经存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeExistsEmployee(EtstaffRegister etemployee);
	/**
	 * 入：4-1.员员工号号已存在，正在办理入职手续
	 *
	 * @return List<Map>
	 */
	public List<Map> getExistsEtstaffRegisterByBadge(EtstaffRegister etemployee);

	/**
	 * 入：5.直接主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsReportTo(EtstaffRegister etemployee);
	/**
	 * 入：6.职能主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsWFReportTo(EtstaffRegister etemployee);
	/**
	 * 入：7.所选公司不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsComp(EtstaffRegister etemployee);
	/**
	 * 入：8.所选公司已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeCompIsDisable(EtstaffRegister etemployee);
	/**
	 * 入：9.所选部门不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsDept(EtstaffRegister etemployee);
	/**
	 * 入：10.所选部门已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeDeptIsDisable(EtstaffRegister etemployee);
	/**
	 * 入：11.所选岗位不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNotExistsJob(EtstaffRegister etemployee);
	/**
	 * 入：12.所选岗位已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeJobIsDisable(EtstaffRegister etemployee);
	/**
	 * 入：14.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeMobileRepeat(EtstaffRegister etemployee);
	/**
	 * 入：14-1.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtstaffRegisterByMobile(EtstaffRegister etemployee);
	/*
	 **入职邀请
	 * 1、手机号短信验证
	 * 2、验证手机号是否在登记表中
	 * 3、插入sys_user 插入档案信息
	 */
	public R inviteRegiter(String mobile, String corpcode,String code);
	/**
	 * 上传文件
	 *
	 * @param file
	 * @param id
	 * @return
	 */
	public R uploadFile(MultipartFile file, Integer id);
	//录用 审批后调用
	public void submitLuYongBySpno(String spno,int openSpStatus,int spr);
	//调动 审批后调用
	public void submitDiaoDongBySpno(String spno,int openSpStatus,int spr);
	//离职 审批后调用
	public void submitLiZhiBySpno(String spno,int openSpStatus,int spr);
	//转正 审批后调用
	public void submitZhuanZhengBySpno(String spno,int openSpStatus,int spr);
}
