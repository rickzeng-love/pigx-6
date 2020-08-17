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
import com.pig4cloud.pigx.admin.entity.AtshiftsupplyWorkAll;
import com.pig4cloud.pigx.admin.mapper.AtshiftsupplyWorkAllMapper;
import com.pig4cloud.pigx.admin.service.AtshiftsupplyWorkAllService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 调班记录
 *
 * @author GXS
 * @date 2020-05-27 16:52:54
 */
@Service
@AllArgsConstructor
public class AtshiftsupplyWorkAllServiceImpl extends ServiceImpl<AtshiftsupplyWorkAllMapper, AtshiftsupplyWorkAll> implements AtshiftsupplyWorkAllService {
	private final AtshiftsupplyWorkAllMapper atshiftsupplyWorkAllMapper;

	/**
	 * 调班记录的查询列表,包含转换成中文
	 * @param page
	 * @param atshiftsupplyWorkAll
	 * @return
	 */
	@Override
	public IPage getAtshiftsupplyWorkAllList(Page page, AtshiftsupplyWorkAll atshiftsupplyWorkAll) {
		return atshiftsupplyWorkAllMapper.ListAtshiftsupplyWorkAll(page,atshiftsupplyWorkAll);
	}
}
