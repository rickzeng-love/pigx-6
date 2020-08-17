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
import com.pig4cloud.pigx.admin.entity.AtchangeRegister;
import com.pig4cloud.pigx.common.core.util.R;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 考勤变动
 *
 * @author gaoxiao
 * @date 2020-07-07 14:02:17
 */
@Mapper
public interface AtchangeRegisterMapper extends BaseMapper<AtchangeRegister> {
	//考勤开启校验
	public R aSPChangeCheck(Map map);
	//考勤开启
	public R aSPChangeStart(Map map);
}
