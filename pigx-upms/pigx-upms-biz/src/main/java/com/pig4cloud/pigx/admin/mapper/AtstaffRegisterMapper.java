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
import com.pig4cloud.pigx.admin.entity.AtstaffRegister;
import com.pig4cloud.pigx.admin.entity.OtcdPositiongrade;
import com.pig4cloud.pigx.common.core.util.R;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-05-30 15:46:08
 */
@Mapper
public interface AtstaffRegisterMapper extends BaseMapper<AtstaffRegister> {
	//考勤开启
	public IPage listAtstaffRegister(Page page, @Param("query") AtstaffRegister atstaffRegister);
	//考勤开启校验
	public R aSPStaffCheck(Map map);
	//考勤开启
	public R aSPStaffStart(Map map);
}
