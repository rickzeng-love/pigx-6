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
import com.pig4cloud.pigx.admin.entity.EtcontractAll;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同变动历史
 *
 * @author gaoxiao
 * @date 2020-04-17 10:30:25
 */
@Mapper
public interface EtcontractAllMapper extends BaseMapper<EtcontractAll> {
	//合同未签订
	public IPage<Map> listEtcontractAllWQD(Page page, @Param("query") EtcontractAll etcontractAll);
	//合同已签订
	public IPage<Map> listEtcontractAllYQD(Page page, @Param("query") EtcontractAll etcontractAll);
	//合同签订历史
	public IPage<Map> listEtcontractAllQDLS(Page page, @Param("query") EtcontractAll etcontractAll);
	//合同校验
	Map eSPContractCheckSub(Map map);
	//合同确认
	Map eSPContractStart(Map map);
	//
	public List<Map> listEtcontractAllByEID(EtcontractAll etcontractAll);
}
