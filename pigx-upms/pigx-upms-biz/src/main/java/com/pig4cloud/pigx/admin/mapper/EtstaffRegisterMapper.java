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

package com.pig4cloud.pigx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.EtstaffRegister;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 入职登记
 *
 * @author gaoxiao
 * @date 2020-04-10 19:14:19
 */
@Mapper
public interface EtstaffRegisterMapper extends BaseMapper<EtstaffRegister> {
	/**
	 * 入：1.黑名单校验；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeBlackList(EtstaffRegister etemployee);
	/**
	 * 入：2.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeCDRelationship(EtstaffRegister etemployee);
	/**
	 * 入：3.公司、部门、岗位要有对应关系且都有效，请重新选择
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeCJRelationship(EtstaffRegister etemployee);
	/**
	 * 入：4.员工工号已经存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeExistsEmployee(EtstaffRegister etemployee);
	/**
	 * 入：4-1.员员工号号已存在，正在办理入职手续
	 *
	 * @return List<Map>
	 */
	public List<Map> listExistsEtstaffRegisterByBadge(EtstaffRegister etemployee);

	/**
	 * 入：5.直接主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsReportTo(EtstaffRegister etemployee);
	/**
	 * 入：6.职能主管不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsWFReportTo(EtstaffRegister etemployee);
	/**
	 * 入：7.所选公司不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsComp(EtstaffRegister etemployee);
	/**
	 * 入：8.所选公司已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeCompIsDisable(EtstaffRegister etemployee);
	/**
	 * 入：9.所选部门不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsDept(EtstaffRegister etemployee);
	/**
	 * 入：10.所选部门已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeDeptIsDisable(EtstaffRegister etemployee);
	/**
	 * 入：11.所选岗位不存在；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNotExistsJob(EtstaffRegister etemployee);
	/**
	 * 入：12.所选岗位已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeJobIsDisable(EtstaffRegister etemployee);
	/**
	 * 入：14.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeMobileRepeat(EtstaffRegister etemployee);
	/**
	 * 入：14-1.手机号与现有人员重复；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtstaffRegisterByMobile(EtstaffRegister etemployee);

}
