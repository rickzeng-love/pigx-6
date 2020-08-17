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
import com.pig4cloud.pigx.admin.entity.CtbenpaybackRegister;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 福利补缴登记
 *
 * @author gaoxiao
 * @date 2020-06-13 10:35:25
 */
@Mapper
public interface CtbenpaybackRegisterMapper extends BaseMapper<CtbenpaybackRegister> {

	//根据id调用存储过程cSP_BenPayBackCheck获取信息提示
    Map cSP_BenPayBackCheck(Map paramMap);

}

