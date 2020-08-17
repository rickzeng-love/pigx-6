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
import com.pig4cloud.pigx.admin.entity.OtcdEmpgrade;
import com.pig4cloud.pigx.admin.entity.OtcdPositiongrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 职级
 *
 * @author gaoxiao
 * @date 2020-04-07 13:22:03
 */
@Mapper
public interface OtcdEmpgradeMapper extends BaseMapper<OtcdEmpgrade> {
	//pc端职级列表
	public IPage listOtcdEmpgrade(Page page, @Param("query") OtcdEmpgrade otcdEmpgrade);
	//pc端职级列表(查询所有
	List<OtcdEmpgrade> listOtcdEmpgradeAll( OtcdEmpgrade otcdEmpgrade);
	//PC端职级在职人员
	public IPage listEtempoyeeByEmpGrade(OtcdEmpgrade otcdEmpgrade);
	//PC端职务获取在职人员数量
	public Map listZaiZhiPersonByEmpGrade(OtcdEmpgrade otcdEmpgrade);
	//PC端职务获取离职人员数量
	public Map listLizhiPersonByEmpGrade(OtcdEmpgrade otcdEmpgrade);

}
