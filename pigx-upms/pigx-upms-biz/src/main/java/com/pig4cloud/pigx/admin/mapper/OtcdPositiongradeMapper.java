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
import com.pig4cloud.pigx.admin.entity.OtcdPosition;
import com.pig4cloud.pigx.admin.entity.OtcdPositiongrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 职等
 *
 * @author gaoxiao
 * @date 2020-04-07 13:22:19
 */
@Mapper
public interface OtcdPositiongradeMapper extends BaseMapper<OtcdPositiongrade> {
	//pc端职等列表
	public IPage listOtcdPositiongrade(Page page, @Param("query") OtcdPositiongrade otcdPositiongrade);
	//PC端职等在职人员
	public IPage listEtempoyeeByPositionGrade(OtcdPositiongrade otcdPositiongrade);

	//PC端职务获取在职人员数量
	public Map listZaiZhiPersonByPositiongrade(OtcdPositiongrade otcdPositiongrade);
	//PC端职务获取离职人员数量
	public Map listLizhiPersonByPositiongrade(OtcdPositiongrade otcdPositiongrade);
}
