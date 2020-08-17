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
import com.pig4cloud.pigx.admin.entity.EtleaveRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 退休离职变动表
 *
 * @author gaoxiao
 * @date 2020-04-10 20:51:22
 */
@Mapper
public interface EtleaveRegisterMapper extends BaseMapper<EtleaveRegister> {
	/**
	 * 离：1.员工不存在，请确认工号是否正确；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeNoExistsEmp(EtleaveRegister Etemployee);
	/**
	 * 离：2.员工已经离职，不能再做离职；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeIsResignation(EtleaveRegister Etemployee);
	/**
	 * 离：3.部门负责人离职，请先更换部门负责人
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeDirectorLeave(EtleaveRegister Etemployee);
	/**
	 * 离：4.请先调整该员工下属员工的行政上级！
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeReportTo(EtleaveRegister Etemployee);
	/**
	 * 离：5.该员工有兼职信息，先终止兼职再离职！
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeePartTime(EtleaveRegister Etemployee);
	/**
	 * 离：6.该员工担任兼职信息中的上级，请先取消
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeePartTimeLeader(EtleaveRegister Etemployee);
	/**
	 * 离：7.员工离职请先解除劳动合同；
	 *
	 * @return List<Map>
	 */
	public List<Map> listEtEmployeeContractUnit(EtleaveRegister Etemployee);
	//员工调动
	public List<Map> listEtleaveRegisterPage(Page page, @Param("query") EtleaveRegister etleaveRegister);
	//离职校验
	Map eSPLeaveCheckSub(Map map);
	//离职确认
	Map eSPLeaveStart(Map map);
}
