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
import com.pig4cloud.pigx.admin.api.dto.DeptTreeOrg;
import com.pig4cloud.pigx.admin.api.dto.MenuTree;
import com.pig4cloud.pigx.admin.api.dto.TreeNode;
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.entity.Otcompany;
import com.pig4cloud.pigx.admin.entity.Otdepartment;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-26 16:50:17
 */
@Mapper
public interface OtcompanyMapper extends BaseMapper<Otcompany> {
	/**
	 * 根据条件查询公司
	 *
	 * @param map
	 * @return
	 */
	List<Map> listCompany(Map map);
	/**
	 * 根据条件查询组织
	 *
	 * @param map
	 * @return
	 */
	List<Map> listCorp(Map map);
	/**
	 * 手机组织架构树根据条件查询二级及以下公司、部门
	 *
	 * @return R
	 */
	public List<Map> listCompanySecondLevel(Map map);
	/**
	 * 手机组织架构树根据条件查询二级及以下部门
	 *
	 * @return R
	 */
	public List<Map> listDept(Map map);
	/**
	 * 电脑端获取组织架构图
	 *
	 * @return R
	 */
	public List<TreeNode> listOrganizationTree(Otcompany otcompany);

	/**
	 * 电脑端获取组织架构图-到部门
	 *
	 * @return R
	 */
	public List<TreeNode> listOrganizationToDeptTree(Otcompany otcompany);

	/**
	 * 手机端获取组织架构树
	 *
	 * @return R
	 */
	public List<TreeNode> listOrganizationTreeMobile(Otcompany otcompany);
	/**
	 * 手机端获取组织架构树-到部门
	 *
	 * @return R
	 */
	public List<TreeNode> listToDeptTreeMobile(Otcompany otcompany);
	/**
	 * PC端部门管理
	 *
	 * @return  IPage<TreeNode>
	 */

	public IPage<DeptTreeOrg> listOrganizationTreePC(Page page, @Param("query") Otdepartment otdepartment);
	/**
	 * PC端部门管理
	 *
	 * @return  List
	 */

	public List listOrganizationTreePCSecond(Otdepartment otdepartment);
	//根据compid获取公司详情
	public Map listCompDeitalByCompid(Otcompany otcompany);

}
