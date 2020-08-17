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
import com.pig4cloud.pigx.admin.entity.AtshiftsupplyWorkAll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 调班记录
 *
 * @author GXS
 * @date 2020-05-27 16:52:54
 */
@Mapper
public interface AtshiftsupplyWorkAllMapper extends BaseMapper<AtshiftsupplyWorkAll> {
	/**
	 * 调班记录的查询列表
	 * @param page
	 * @param atshiftsupplyWorkAll
	 * @return
	 */
	public IPage ListAtshiftsupplyWorkAll(Page page, @Param("query") AtshiftsupplyWorkAll atshiftsupplyWorkAll);

	/**
	 * 获取导出excel的数据集
	 * @param name
	 * @param corpcode
	 * @return
	 */
	public List<AtshiftsupplyWorkAll> ExportAtshiftsupplyWorkAll(String term, String name, String corpcode);

}
