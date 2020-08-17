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
import com.pig4cloud.pigx.admin.entity.AtgroupShift;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 轮班组班次管理
 *
 * @author GXS
 * @date 2020-05-25 10:53:19
 */
@Mapper
public interface AtgroupShiftMapper extends BaseMapper<AtgroupShift> {

	/**
	 * 获取编辑界面上工作日的数据
	 * @param groupid
	 * @param corpcode
	 * @return
	 */
	public List<Map> listAtgroupWordDayInfo(Integer groupid, String corpcode);
}
