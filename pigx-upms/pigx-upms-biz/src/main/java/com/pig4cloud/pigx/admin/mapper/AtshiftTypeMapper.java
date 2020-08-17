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
import com.pig4cloud.pigx.admin.entity.AtshiftType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 班次管理
 *
 * @author GXS
 * @date 2020-05-25 10:52:00
 */
@Mapper
public interface AtshiftTypeMapper extends BaseMapper<AtshiftType> {
	/**
	 * 获取打卡模式的数据源
	 * @return
	 */
	public List<Map> listAtshiftTypeType();

//	/**
//	 * 新增时获取当前用户默认的考勤账套
//	 * @param userID
//	 * @return
//	 */
//	public Map listAtshiftTypeAgentMode(Integer userID,String corpcode);

	/**
	 * 修改打卡时间计算工作时数
	 * @param map
	 * @return
	 */
	public Map listAtshiftTypeXHours(Map map);

	/**
	 * 查询列表记录，包含考勤时间合并显示
	 * @param page
	 * @param atshiftType
	 * @return
	 */
	public IPage listAtshiftType(Page page, @Param("query") AtshiftType atshiftType);

	/**
	 * 根据考勤管理员为UserID值的考勤账套关联的班次信息
	 * @param userId
	 * @param corpCode
	 * @return
	 */
	public List<Map> listAtshiftTypeByUserId(Integer userId, String corpCode);

	/**
	 * 考勤组管理的工作日更改班次的弹出界面班次列表
	 * @param shift
	 * @param corpCode
	 * @return
	 */
	public List<Map> listAtshiftTypeSelList(String shift, String corpCode);

}
