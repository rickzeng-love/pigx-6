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
import com.pig4cloud.pigx.admin.entity.EtbgAccessorymaterials;
import com.pig4cloud.pigx.admin.entity.EtbgWorking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 员工附件
 *
 * @author gaoxiao
 * @date 2020-04-16 11:36:31
 */
@Mapper
public interface EtbgAccessorymaterialsMapper extends BaseMapper<EtbgAccessorymaterials> {

	public List<Map> listEtbgAccessorymaterialsBySql(EtbgAccessorymaterials etbgAccessorymaterials);
	public IPage listEtbgAccessorymaterialsAllBySql(Page page, @Param("query") EtbgAccessorymaterials etbgAccessorymaterials);
}
