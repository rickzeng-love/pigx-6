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
import com.pig4cloud.pigx.admin.entity.AtshiftWork;
import com.pig4cloud.pigx.admin.mapper.AtshiftWorkMapper;
import com.pig4cloud.pigx.admin.service.AtshiftWorkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 员工排班
 *
 * @author GXS
 * @date 2020-05-27 16:53:58
 */
@Service
@AllArgsConstructor
public class AtshiftWorkServiceImpl extends ServiceImpl<AtshiftWorkMapper, AtshiftWork> implements AtshiftWorkService {
	private final AtshiftWorkMapper atshiftWorkMapper;

	/**
	 *查询员工排班的员工班次信息列表
	 * @param page
	 * @param atshiftWork
	 * @return
	 */
	@Override
	public IPage getAtshiftWorkList(Page page, AtshiftWork atshiftWork) {
		return atshiftWorkMapper.ListAtshiftWork(page,atshiftWork);
	}
}
