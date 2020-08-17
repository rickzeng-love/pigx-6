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
import com.pig4cloud.pigx.admin.entity.AtshiftWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 员工排班
 *
 * @author GXS
 * @date 2020-05-27 16:53:58
 */
@Mapper
public interface AtshiftWorkMapper extends BaseMapper<AtshiftWork> {

	/**
	 * 查询员工排班的员工班次信息列表
	 * @param page
	 * @param atshiftWork
	 * @return
	 */
	public IPage ListAtshiftWork(Page page, @Param("query") AtshiftWork atshiftWork);

	/**
	 * 导出员工排班记录列表
	 * @param name
	 * @param corpcode
	 * @param term
	 * @return
	 */
	public List<AtshiftWork> ExportAtshiftWork(String name, String corpcode, LocalDateTime term);

	/**
	 * 按月份统计班次的每日排班统计
	 * @param corpcode
	 * @param term
	 * @return
	 */
	public List<Map> ListAtshiftWorkTotal(String corpcode, String term);

	/**
	 * 判断手工排班登记表是否已含有当前员工
	 * @param EID
	 * @param term
	 * @param corpcode
	 * @return
	 */
	public List<Map> ListShiftHandleAddEmpCheckSub(Integer EID, String term, String corpcode);

	/**
	 * 判断当前考勤账套是否在考勤分析中
	 * @param AgentMode
	 * @return
	 */
	public List<Map> ListAttendLockStatusCheck(Integer AgentMode);

	public IPage listAvwStatusForAtshiftWork(Page page, @Param("query") AtshiftWork atshiftWork);
}
