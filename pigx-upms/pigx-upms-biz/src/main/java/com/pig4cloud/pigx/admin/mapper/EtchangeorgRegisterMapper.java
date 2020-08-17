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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtchangeorgRegister;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 调动登记
 *
 * @author gaoxiao
 * @date 2020-04-14 19:05:41
 */
@Mapper
public interface EtchangeorgRegisterMapper extends BaseMapper<EtchangeorgRegister> {
	/**
	 * 调动：1.员工已经离职，不能做变动；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeUnSignDel(EtchangeorgRegister Etemployee);
	/**
	 * 调动：2.新公司已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewCompIsDisableForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：3.新公司还未成立；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewCompUnSetupForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：4.新部门已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewDepIsDisableForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：5.新部门还未成立；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewDepUnSetupForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：6.所选新部门不在新公司下；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewCompNewDepForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：7.所选新岗位已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewJobIsDisableForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：8.所选新岗位还未成立；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewJobUnSetupForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：9.所选新岗位不在新部门下；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNewJobNewDepForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：10.新行政上级已经离职；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeReportToLeaveForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：11.生效日期不能在最近一次变动之前；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeEffectDateForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：12.该员工正兼职该岗位，先终止兼职；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeePartJobForChange(EtchangeorgRegister Etemployee);
	//员工调动
	public List<Map> listEtchangeorgRegisterPage(Page page, @Param("query") EtchangeorgRegister etchangeorgRegister);
	//员工调动校验
	Map eSPChangeOrgChecksub(Map map);
	//员工调动确认
	Map eSPChangeOrgStart(Map map);
}
