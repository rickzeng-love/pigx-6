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
import com.pig4cloud.pigx.admin.entity.Systcity;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.entity.Systdistrict;
import com.pig4cloud.pigx.admin.entity.Systprovince;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-24 16:01:55
 */
public interface SystcorpinfoService extends IService<Systcorpinfo> {
	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 */
	R uploadLogo(MultipartFile file,String param);
	/**
	 * 查询省市区

	 * @return 省市区列表
	 */
	public R   findProvinceCityDistrict() ;

}
