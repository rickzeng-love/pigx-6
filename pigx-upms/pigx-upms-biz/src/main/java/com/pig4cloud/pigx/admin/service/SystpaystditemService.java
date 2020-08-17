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

package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.Systpaystditem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-04-22 14:42:49
 */
public interface SystpaystditemService extends IService<Systpaystditem> {

	/*
	 **组装薪资模板
	 *
	 */

	public List<LinkedHashMap> getSalaryTemplate();
	/*
	 **组装薪资模板sql
	 *
	 */
	public String getSalaryTemplateSql();
	//获取员工个人工资条
	public List<LinkedHashMap> getSalaryTemplateByEID(Integer type);
	//获取员工个人工资条历史
	public List<LinkedHashMap> getSalaryTemplateByEIDAndTerm(Integer type,String term);
}
