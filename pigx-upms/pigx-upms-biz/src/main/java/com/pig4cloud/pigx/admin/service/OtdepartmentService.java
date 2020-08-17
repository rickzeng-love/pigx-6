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
import com.pig4cloud.pigx.admin.entity.EtleaveAll;
import com.pig4cloud.pigx.admin.entity.EtstaffAll;
import com.pig4cloud.pigx.admin.entity.OtcdPosition;
import com.pig4cloud.pigx.admin.entity.Otdepartment;

import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author gaoxiao
 * @date 2020-03-27 16:22:26
 */
public interface OtdepartmentService extends IService<Otdepartment> {
	/**
	 * 根据部门编号判断部门是否存在
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDepCode(Otdepartment otdepartment);
	/**
	 * 上级部门必须存在且有效，或者生效日期必须晚于上级部门的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByAdminID(Otdepartment otdepartment);
	/**
	 * 所属公司必须存在且有效,或生效日期晚于所属公司的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByCompID(Otdepartment otdepartment);
	/**
	 * 部门上级必须在所属公司里的部门！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByAdminIDinCompID(Otdepartment otdepartment);

	/**
	 * 失效的部门不能做变更！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByDisableDepID(Otdepartment otdepartment);
	/**
	 * 信息没有发生变化！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByDepAbbr(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理其下属部门！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByDepLoseEfficacy(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理其下属岗位！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByDepLoseEfficacyJob(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理部门内部在职员工！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByDepLoseEfficacyEmployee(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理兼职对信息！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtdepartmentByDepLoseEfficacyTeam(Otdepartment otdepartment);
	//部门数量
	public Map getOtdepartmentByCount(Otdepartment otdepartment);
	//岗位数量
	public Map getOtjobByCount(Otdepartment otdepartment);
	//岗位类别数量
	public Map getOtcdjobtypeByCount(Otdepartment otdepartment);
	//职务数量
	public Map getOtcdPositionByCount(Otdepartment otdepartment);
	//部门概况
	public List<Map>  getOtdepartmentByPeople(Otdepartment otdepartment);
	//岗位概况
	public List<Map>  getOtjobByPeople(Otdepartment otdepartment);
	//部门人员流动
	public List<Map>  getEmployeeFlowRuZhiByDept(EtstaffAll etstaffAll);
	public List<Map>  getEmployeeLiZhiFlowByDept(EtleaveAll etleaveAll);
	//岗位人员流动
	public List<Map>  getEmployeeFlowRuZhiByJob(EtstaffAll etstaffAll);
	public List<Map>  getEmployeeLiZhiFlowByJob(EtleaveAll etleaveAll);
}
