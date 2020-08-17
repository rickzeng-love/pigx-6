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
import com.pig4cloud.pigx.admin.entity.SysQywxApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 企业微信第三方应用信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:38:59
 */
@Mapper
public interface SysQywxApplicationMapper extends BaseMapper<SysQywxApplication> {

	 SysQywxApplication listQYWeixinApplication(@Param("suiteId") String suiteId, @Param("suiteSecret") String suiteSecret);
	SysQywxApplication queryBySuiteId(@Param("suiteId") String suiteId);

}
