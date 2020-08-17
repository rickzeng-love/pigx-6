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
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.entity.SystpayrollitemCommon;
import com.pig4cloud.pigx.admin.entity.Systpayrollobj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-06-27 10:15:44
 */
@Mapper
public interface SystpayrollitemCommonMapper extends BaseMapper<SystpayrollitemCommon> {
	public List<TreeOrg> listsystpayrollitemCommonTree(SystpayrollitemCommon systpayrollitemCommon);
	//薪资套选薪资项树状列表
	public List<TreeOrg> listsystpayrollitemCommonTreeAndIschecked(SystpayrollitemCommon systpayrollitemCommon);
}
