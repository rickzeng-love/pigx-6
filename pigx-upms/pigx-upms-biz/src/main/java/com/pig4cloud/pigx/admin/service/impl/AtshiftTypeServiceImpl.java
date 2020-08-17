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
import com.pig4cloud.pigx.admin.entity.AtshiftType;
import com.pig4cloud.pigx.admin.mapper.AtshiftTypeMapper;
import com.pig4cloud.pigx.admin.service.AtshiftTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 班次管理
 *
 * @author GXS
 * @date 2020-05-25 10:52:00
 */
@Service
@AllArgsConstructor
public class AtshiftTypeServiceImpl extends ServiceImpl<AtshiftTypeMapper, AtshiftType> implements AtshiftTypeService {
	private  final AtshiftTypeMapper atshiftTypeMapper;

	/**
	 * 查询列表记录，包含考勤时间合并显示
	 * @param page
	 * @param atshiftType
	 * @return
	 */
	@Override
	public IPage getAtshiftTypeList(Page page, AtshiftType atshiftType) {
		return atshiftTypeMapper.listAtshiftType(page,atshiftType);
	}
}
