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
import com.pig4cloud.pigx.admin.entity.AtcdAgentmode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;


/**
 * 考勤账套管理
 *
 * @author GXS
 * @date 2020-05-25 11:06:46
 */
@Mapper
public interface AtcdAgentmodeMapper extends BaseMapper<AtcdAgentmode> {
	/**
	 * 考勤账套的查询列表，包含考勤开户时间
	 * @param page
	 * @param atcdAgentmode
	 * @return
	 */
	public IPage ListAtcdAgentmode(Page page, @Param("query") AtcdAgentmode atcdAgentmode);

	/**
	 * 获取编辑界面的数据
	 * @param atcdAgentmode
	 * @return
	 */
	public Map listAtcdAgentmodeInfo(AtcdAgentmode atcdAgentmode);

	/**
	 * 根据考勤管理员为用户ID获取相应的考勤账套选项
	 * @param userId
	 * @param corpCode
	 * @return
	 */
	public List<Map> listAtcdAgentmodeByUserId(Integer userId, String corpCode);

}
