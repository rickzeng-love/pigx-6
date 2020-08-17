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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.EtbgAccessorymaterials;
import com.pig4cloud.pigx.admin.entity.EtstaffRegister;
import com.pig4cloud.pigx.admin.mapper.EtbgAccessorymaterialsMapper;
import com.pig4cloud.pigx.admin.service.EtbgAccessorymaterialsService;
import com.pig4cloud.pigx.admin.service.SysFileService;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 员工附件
 *
 * @author gaoxiao
 * @date 2020-04-16 11:36:31
 */
@Slf4j
@Service
@AllArgsConstructor
public class EtbgAccessorymaterialsServiceImpl extends ServiceImpl<EtbgAccessorymaterialsMapper, EtbgAccessorymaterials> implements EtbgAccessorymaterialsService {
	private final MinioTemplate minioTemplate;
	private final SysFileService sysFileService;
	/**
	 * 上传文件
	 *
	 * @param file
	 * @param columnName
	 * @return
	 */
	public R uploadFile(MultipartFile file,String columnName){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		String mobile = pigxUser.getUsername();
		String contentType = file.getContentType();
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", CommonConstants.BUCKET_NAME);
		resultMap.put("fileName", fileName);
		resultMap.put("url", String.format("/%s/%s", CommonConstants.BUCKET_NAME, fileName));
		resultMap.put("url", "/"+CommonConstants.BUCKET_NAME+"/" +fileName);

		try {
			String url = "/"+CommonConstants.BUCKET_NAME+"/" +fileName;
			//根据手机号查询是否存在档案
			EtbgAccessorymaterials one = getOne(Wrappers.<EtbgAccessorymaterials>query().lambda().eq(EtbgAccessorymaterials::getAccount, mobile));
			EtbgAccessorymaterials etbgAccessorymaterials = new EtbgAccessorymaterials();
			if("lzzm".equals(columnName)){
				etbgAccessorymaterials.setLzzm(url);
			}else if("tjzm".equals(columnName)){
				etbgAccessorymaterials.setTjzm(url);
			}else if("sfzzm".equals(columnName)){
				etbgAccessorymaterials.setSfzzm(url);
			}else if("sfzfm".equals(columnName)){
				etbgAccessorymaterials.setSfzfm(url);
			}else if("xlz".equals(columnName)){
				etbgAccessorymaterials.setXlz(url);
			}else if("xwz".equals(columnName)){
				etbgAccessorymaterials.setXwz(url);
			}else if("xxwyz".equals(columnName)){
				etbgAccessorymaterials.setXxwyz(url);
			}else if("yhk".equals(columnName)){
				etbgAccessorymaterials.setYhk(url);
			}
			//如果存在就更新
			if(one!=null){
				QueryWrapper<EtbgAccessorymaterials> queryWrapper = new QueryWrapper<EtbgAccessorymaterials>();
				queryWrapper.eq("account", mobile);
				update(etbgAccessorymaterials,queryWrapper);
			}else{
				//如果不存在就插入
				etbgAccessorymaterials.setAccount(mobile);
				etbgAccessorymaterials.setCode(UUID.randomUUID().toString());
				etbgAccessorymaterials.setCorpcode(corpcode);
				save(etbgAccessorymaterials);
			}
			minioTemplate.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream(),contentType);
			//文件管理数据记录,收集管理追踪文件
			sysFileService.fileLog(file, fileName);

		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}
}
