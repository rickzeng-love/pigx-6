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
import com.pig4cloud.pigx.admin.entity.AtcdAgentmode;
import com.pig4cloud.pigx.admin.mapper.AtcdAgentmodeMapper;
import com.pig4cloud.pigx.admin.service.AtcdAgentmodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 考勤账套管理
 *
 * @author GXS
 * @date 2020-05-25 11:06:46
 */
@Service
@AllArgsConstructor
public class AtcdAgentmodeServiceImpl extends ServiceImpl<AtcdAgentmodeMapper, AtcdAgentmode> implements AtcdAgentmodeService {
	private  final AtcdAgentmodeMapper atcdAgentmodeMapper;

	/**
	 * 考勤账套的查询列表，包含考勤开户时间
	 * @param page
	 * @param atcdAgentmode
	 * @return
	 */
	@Override
	public IPage getAtcdAgentmodeList(Page page, AtcdAgentmode atcdAgentmode) {
		return atcdAgentmodeMapper.ListAtcdAgentmode(page,atcdAgentmode);
	}
}
