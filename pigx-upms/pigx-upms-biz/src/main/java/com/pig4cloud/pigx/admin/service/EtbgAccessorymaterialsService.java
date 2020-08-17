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
import com.pig4cloud.pigx.admin.entity.EtbgAccessorymaterials;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 员工附件
 *
 * @author gaoxiao
 * @date 2020-04-16 11:36:31
 */
public interface EtbgAccessorymaterialsService extends IService<EtbgAccessorymaterials> {
	/**
	 * 上传文件
	 *
	 * @param file
	 * @param columnName
	 * @return
	 */
	public R uploadFile(MultipartFile file,String columnName);
}
