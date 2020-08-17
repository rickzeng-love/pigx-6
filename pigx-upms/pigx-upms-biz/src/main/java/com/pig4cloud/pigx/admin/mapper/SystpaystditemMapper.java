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

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pigx.admin.entity.Systpaystditem;
import com.pig4cloud.pigx.admin.service.impl.SystpaystditemServiceImpl;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-04-22 14:42:49
 */
@Mapper
public interface SystpaystditemMapper extends BaseMapper<Systpaystditem> {
	/*
	 **组装薪资模板
	 *
	 */
	public List<LinkedHashMap> listSalaryTemplate(String sql);
	public Map listSalaryTemplate2(String sql);

	@SqlParser(filter=true)
	public void listSalaryTemplate3(String sql);
	public List<Map> listSalaryTemplate4(String sql);
	@SqlParser(filter=true)
	public List<Map> listSalaryTemplate5(String sql);


}
