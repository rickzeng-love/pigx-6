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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtleaveAll;
import com.pig4cloud.pigx.admin.entity.EtstaffAll;
import com.pig4cloud.pigx.admin.entity.OtcdPosition;
import com.pig4cloud.pigx.admin.entity.Otdepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author gaoxiao
 * @date 2020-03-27 16:22:26
 */
@Mapper
public interface OtdepartmentMapper extends BaseMapper<Otdepartment> {
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
	public List<Map> listOtdepartmentByAdminID(Otdepartment otdepartment);
	/**
	 * 所属公司必须存在且有效,或生效日期晚于所属公司的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByCompID(Otdepartment otdepartment);
	/**
	 * 部门上级必须在所属公司里的部门！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByAdminIDinCompID(Otdepartment otdepartment);
	/**
	 * 失效的部门不能做变更！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDisableDepID(Otdepartment otdepartment);
	/**
	 * 信息没有发生变化！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDepAbbr(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理其下属部门！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDepLoseEfficacy(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理其下属岗位！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDepLoseEfficacyJob(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理部门内部在职员工！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDepLoseEfficacyEmployee(Otdepartment otdepartment);
	/**
	 * 部门失效时请先处理兼职对信息！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtdepartmentByDepLoseEfficacyTeam(Otdepartment otdepartment);
	//部门数量
	public Map listOtdepartmentByCount(Otdepartment otdepartment);
	//岗位数量
	public Map listOtjobByCount(Otdepartment otdepartment);
	//岗位类别数量
	public Map listOtcdjobtypeByCount(Otdepartment otdepartment);
	//职务数量
	public Map listOtcdPositionByCount(Otdepartment otdepartment);
	//部门概况
	public List<Map>  listOtdepartmentByPeople(Otdepartment otdepartment);
	//岗位概况
	public List<Map>  listOtjobByPeople(Otdepartment otdepartment);
	//部门人员流动
	public List<Map>  listEmployeeFlowRuZhiByDept(EtstaffAll etstaffAll);
	public List<Map>  listEmployeeLiZhiFlowByDept(EtleaveAll etleaveAll);
	//岗位人员流动
	public List<Map>  listEmployeeFlowRuZhiByJob(EtstaffAll etstaffAll);
	public List<Map>  listEmployeeLiZhiFlowByJob(EtleaveAll etleaveAll);

	//部门管理详情接口1
	public List<Map>  listOtdepartmentByDepId(Otdepartment otdepartment);

	//部门管理详情接口2
	public List<Map>  listEmployeeLiByDepId(Otdepartment otdepartment);
	//部门管理详情接口3
	public List<Map>  listOtdepartmentByDepIdForDeatail(Otdepartment otdepartment);
	//根据compid获取部门列表
	public IPage<Map> listOtdepartmentByCompid(Page page, @Param("query")  Otdepartment otdepartment);
	//根据depid获取部门列表
	public IPage<Map> listOtdepartmentByDepid(Page page, @Param("query")  Otdepartment otdepartment);
	//根据depid获取部门列表
	public IPage<Map> listOtdepartmentByAdminid(Page page, @Param("query")  Otdepartment otdepartment);



}
