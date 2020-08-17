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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.OtcdJobtype;
import com.pig4cloud.pigx.admin.mapper.OtcdJobtypeMapper;
import com.pig4cloud.pigx.admin.service.OtcdJobtypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 岗位类型
 *
 * @author gaoxiao
 * @date 2020-04-08 11:20:39
 */
@Service
@AllArgsConstructor
public class OtcdJobtypeServiceImpl extends ServiceImpl<OtcdJobtypeMapper, OtcdJobtype> implements OtcdJobtypeService {
	private final OtcdJobtypeMapper otcdJobtypeMapper;
	/**
	 * 根据corpcode获取岗位类型列表带人数
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtcdJobtypeByCorpcodeWithPeople(OtcdJobtype otcdJobtype){
		return otcdJobtypeMapper.listOtcdJobtypeByCorpcodeWithPeople(otcdJobtype);
	}
}
