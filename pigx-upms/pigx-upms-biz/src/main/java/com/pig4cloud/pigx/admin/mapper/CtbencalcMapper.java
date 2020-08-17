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
import com.pig4cloud.pigx.admin.entity.Ctbencalc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 本月社保福利
 *
 * @author gaoxiao
 * @date 2020-06-13 11:12:45
 */
@Mapper
public interface CtbencalcMapper extends BaseMapper<Ctbencalc> {
	//获取公积金信息
	public Map listctbencalcByEidForGJJ(Ctbencalc ctbencalc);
	//获取缴存的年份
	public List<Map> listctbencalcByEidGroupbyYear(Map map);
	//铜鼓
	public List<Map> listctbencalcByEidAndYear(Map map);
	//获取社保信息
	public Map listctbencalcByEid(Ctbencalc ctbencalc);
	public List<Map>  listctbencalcForSBByEidAndYear(Map map);

}
