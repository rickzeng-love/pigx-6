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
import com.pig4cloud.pigx.admin.entity.EtchangeorgRegister;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;
import java.util.Map;

/**
 * 调动登记
 *
 * @author gaoxiao
 * @date 2020-04-14 19:05:41
 */
public interface EtchangeorgRegisterService extends IService<EtchangeorgRegister> {

	/**
	 * 调动：1.员工已经离职，不能做变动；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeUnSignDel(EtchangeorgRegister Etemployee);
	/**
	 * 调动：2.新公司已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewCompIsDisableForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：3.新公司还未成立；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewCompUnSetupForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：4.新部门已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewDepIsDisableForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：5.新部门还未成立；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewDepUnSetupForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：6.所选新部门不在新公司下；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewCompNewDepForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：7.所选新岗位已经失效；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewJobIsDisableForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：8.所选新岗位还未成立；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewJobUnSetupForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：9.所选新岗位不在新部门下；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeNewJobNewDepForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：10.新行政上级已经离职；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeReportToLeaveForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：11.生效日期不能在最近一次变动之前；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeeEffectDateForChange(EtchangeorgRegister Etemployee);
	/**
	 * 调动：12.该员工正兼职该岗位，先终止兼职；
	 *
	 * @return List<Map>
	 */
	public List<Map> getEtEmployeePartJobForChange(EtchangeorgRegister Etemployee);

	/**
	 * 确认调动
	 *
	 * @return List<Map>
	 */
	public R updateEmployeeForDD(EtchangeorgRegister etemployee);

}
