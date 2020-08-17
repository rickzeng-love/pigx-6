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
import com.pig4cloud.pigx.admin.entity.EtbgEducation;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 教育经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:36:11
 */
@Mapper
public interface EtbgEducationMapper extends BaseMapper<EtbgEducation> {

	/*
	 *  学历分布报表
	 */
	public List<Map> listEtbgEducationBiliALL(EtbgEducation etbgEducation);
	public List<Map> listEtbgEducationBySql(EtbgEducation etbgEducation);
	//员工档案-教育经历
	public IPage listEtbgEducationAllBySql(Page page, @Param("query") Etemployee etemployee);

}
