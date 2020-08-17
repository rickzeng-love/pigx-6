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
import com.pig4cloud.pigx.admin.entity.Ctemployee;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-04-14 14:20:29
 */
@Mapper
public interface CtemployeeMapper extends BaseMapper<Ctemployee> {
	/**
	 * 薪资初始化
	 *
	 * @param  registerMap
	 */
	Map sysSPcInit1(Map registerMap);
	/**
	 * 薪资初始化
	 *
	 * @param  registerMap
	 */
	Map sysSPcCalc1(Map registerMap);
	/**
	 * 签发
	 *
	 * @param  registerMap
	 */
	Map cSPPayrollConfirm(Map registerMap);
	/**
	 * 反签发
	 *
	 * @param  registerMap
	 */
	Map cSPPayrollUnConfirm(Map registerMap);
	/**
	 * 封账
	 *
	 * @param  registerMap
	 */
	Map sysSPcClose1(Map registerMap);
	//人员结构占比
	public Map listCtstandardAllGroupByEmpstatus(Map map);
	//薪资支出占比
	public Map listCtstandardAllByType(Map map);
	public Map listGGJAndShebao(Map map);
	//部门人均工资排名
	public List<LinkedHashMap> listBMRJGZ(Map map);
	//部门支出工资排名
	public List<LinkedHashMap> listBMZCGZPM(Map map);
	//岗位支出工资排名
	public List<LinkedHashMap> listGWZCGZPM(Map map);
	//近六个月工资趋势分析
	public List<LinkedHashMap> listGZQSFX(Map map);
	//近六个月人力成本趋势分析
	public List<LinkedHashMap> listRLCBQSFX(Map map);
	//近六个月人计薪人数趋势分析-
	public List<LinkedHashMap> listJXRSQSFX(Map map);



}
