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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.api.dto.DeptTreeOrg;
import com.pig4cloud.pigx.admin.api.dto.TreeNode;
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.entity.Otcompany;
import com.pig4cloud.pigx.admin.entity.Otdepartment;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-26 16:50:17
 */
public interface OtcompanyService extends IService<Otcompany> {
	/**
	 * 根据条件查询公司
	 *
	 * @return R
	 */
	public List<Map> getOtcompanyList();
	/**
	 * 根据条件查询组织
	 *
	 * @return R
	 */
	public List<Map> getcorpList();
	/**
	 * 手机组织架构树根据条件查询二级及以下公司、部门
	 *
	 * @return R
	 */
	public List<Map> getCompanySecondLevel(Map map);
	/**
	 * 手机组织架构树根据条件查询二级及以下部门
	 *
	 * @return R
	 */
	public List<Map> getDept(Map map);
	/**
	 * 组织架构图谱
	 *
	 * @return R
	 */
	public TreeOrg getOrganizationTree(Otcompany otcompany );
	/**
	 * 组织架构树
	 *
	 * @return R
	 */
	public TreeOrg getOrganizationTreeMobile(Otcompany otcompany);
	/**
	 * 组织架构树 --手机端
	 *
	 * @return R
	 */
	public TreeOrg getToDeptTreeMobile(Otcompany otcompany);
	/**
	 * PC端部门管理
	 *
	 * @return TreeOrg
	 */
	public IPage<DeptTreeOrg> getOrganizationTreePC(Page page, Otdepartment otdepartment);
	public List getOrganizationTreePCSecond(Otdepartment otdepartment);
	public TreeOrg getOrganizationToDeptTree(Otcompany otcompany);
}
