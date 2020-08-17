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
import com.pig4cloud.pigx.admin.entity.AtplanRange;
import com.pig4cloud.pigx.admin.entity.AtshiftDetails;
import com.pig4cloud.pigx.admin.entity.AvwShiftDay;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 日结果汇总
 *
 * @author gaoxiao
 * @date 2020-06-22 11:40:32
 */
@Mapper
public interface AtplanRangeMapper extends BaseMapper<AtplanRange> {

	public List<AvwShiftDay> listAtShiftDayByEid(AtplanRange atplanRange);
	//手机端管理员考勤状态分页查询
	public IPage<Map> listAtShiftDayByEidForStatistics(Page page, @Param("query") Map map);
	//
	public List<AvwShiftDay> listAtShiftDayForStatistics2(Map map);
}
