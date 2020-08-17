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
package com.pig4cloud.pigx.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.dto.DeptTree;
import com.pig4cloud.pigx.admin.api.dto.DeptTreeOrg;
import com.pig4cloud.pigx.admin.api.dto.TreeNode;
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.api.entity.SysDept;
import com.pig4cloud.pigx.admin.api.vo.TreeUtil;
import com.pig4cloud.pigx.admin.entity.Otcompany;
import com.pig4cloud.pigx.admin.entity.Otdepartment;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.mapper.OtcompanyMapper;
import com.pig4cloud.pigx.admin.service.OtcompanyService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-26 16:50:17
 */
@Service
@AllArgsConstructor
public class OtcompanyServiceImpl extends ServiceImpl<OtcompanyMapper, Otcompany> implements OtcompanyService {
	private final OtcompanyMapper otcompanyMapper;
	/**
	 * 根据条件查询公司
	 * @return R
	 */
	@Override
	public List<Map> getOtcompanyList(){
		Otcompany otcompany = new Otcompany();
		PigxUser pigxUser = SecurityUtils.getUser();
		//otcompany.setCompcode(pigxUser.getCorpcode());
		Map map = new HashMap();
		map.put("corpcode",pigxUser.getCorpcode());
		map.put("deptid",pigxUser.getDeptId());
		return otcompanyMapper.listCompany(map);
	}
	/**
	 * 根据条件查询组织
	 * @return R
	 */
	@Override
	public List<Map> getcorpList(){
		Otcompany otcompany = new Otcompany();
		PigxUser pigxUser = SecurityUtils.getUser();
		//otcompany.setCompcode(pigxUser.getCorpcode());
		Map map = new HashMap();
		map.put("corpcode",pigxUser.getCorpcode());
		map.put("deptid",pigxUser.getDeptId());
		return otcompanyMapper.listCorp(map);
	}
	/**
	 * 手机组织架构树根据条件查询二级及以下公司、部门
	 *
	 * @return R
	 */
	@Override
	public List<Map> getCompanySecondLevel(Map map){
		return otcompanyMapper.listCompanySecondLevel(map);
	}
	/**
	 * 手机组织架构树根据条件查询二级及以下公司、部门
	 *
	 * @return R
	 */
	@Override
	public List<Map> getDept(Map map){
		return otcompanyMapper.listDept(map);
	}
	/**
	 * 构建树查询
	 *
	 * @param otcompany
	 * @return
	 */
	@Override
	public TreeOrg getOrganizationTree(Otcompany otcompany) {

		List organizaitonList = otcompanyMapper.listOrganizationTree(otcompany);
		String  root ="C"+otcompany.getCompid();
		TreeOrg treeOrg = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("C8");
		treeOrg.setTitle("root");
		return TreeUtil.findChildren2(treeOrg,organizaitonList);
		//return TreeUtil.buildTree2(organizaitonList,root);
	}

	/**
	 * 获取组织架构图谱-到部门 电脑端
	 *
	 * @param otcompany
	 * @return
	 */
	@Override
	public TreeOrg getOrganizationToDeptTree(Otcompany otcompany) {

		List organizaitonList = otcompanyMapper.listOrganizationToDeptTree(otcompany);
		String  root ="C"+otcompany.getCompid();
		String title = otcompany.getTitle();
		TreeOrg treeOrg = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId(root);
		//treeOrg.setTitle("root");
		return TreeUtil.findChildren2(treeOrg,organizaitonList);
		//return TreeUtil.buildTree2(organizaitonList,root);
	}

	/**
	 * 构建树查询手机端
	 *
	 * @param otcompany
	 * @return
	 */
	@Override
	public TreeOrg getOrganizationTreeMobile(Otcompany otcompany) {

		List organizaitonList = otcompanyMapper.listOrganizationTreeMobile(otcompany);
		String  root ="C"+otcompany.getCompid();
		TreeOrg treeOrg = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId(root);
		treeOrg.setTitle(otcompany.getTitle());
		return TreeUtil.findChildren2(treeOrg,organizaitonList);
		//return TreeUtil.buildTree2(organizaitonList,root);
	}
	/**
	 * 构建树查询手机端
	 *
	 * @param otcompany
	 * @return
	 */
	@Override
	public TreeOrg getToDeptTreeMobile(Otcompany otcompany) {

		List organizaitonList = otcompanyMapper.listToDeptTreeMobile(otcompany);
		String  root ="C"+otcompany.getCompid();
		String title = otcompany.getTitle();
		TreeOrg treeOrg = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId(root);
		treeOrg.setTitle(title);
		return TreeUtil.findChildren2(treeOrg,organizaitonList);
		//return TreeUtil.buildTree2(organizaitonList,root);
	}

	/**
	 *  PC端部门管理
	 *
	 * @param otdepartment
	 * @return
	 */
	@Override
	public IPage<DeptTreeOrg> getOrganizationTreePC(Page page, Otdepartment otdepartment) {

		IPage<DeptTreeOrg> organizaitonList = otcompanyMapper.listOrganizationTreePC(page,otdepartment);
		return organizaitonList;
	}
	/**
	 *  PC端部门管理
	 *
	 * @param otdepartment
	 * @return
	 */
	@Override
	public List getOrganizationTreePCSecond(Otdepartment otdepartment) {

		List organizaitonList = otcompanyMapper.listOrganizationTreePCSecond(otdepartment);
		return organizaitonList;
	}


}
