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
import com.pig4cloud.pigx.admin.entity.Systcity;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.entity.Systdistrict;
import com.pig4cloud.pigx.admin.entity.Systprovince;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-24 16:01:55
 */
@Mapper
public interface SystcorpinfoMapper extends BaseMapper<Systcorpinfo> {
	/**
	 * 查询省份列表

	 * @return 省份列表
	 */
	List<Systprovince> listProvince();
	/**
	 * 查询城市列表

	 * @return 城市列表
	 */
	List<Systcity> listCity();
	/**
	 * 查询区列表

	 * @return 区列表
	 */
	List<Systdistrict> listDistrict();

	/**
	 * 根据企业的corpid查询通讯录密钥
	 * @return 通讯录密钥
	 * */
	Systcorpinfo seachAddressSecretByQywxCorpid(String qywx_corpid);
}
