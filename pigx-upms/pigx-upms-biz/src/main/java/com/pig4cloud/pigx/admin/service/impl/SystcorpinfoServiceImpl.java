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
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.mapper.SysUserMapper;
import com.pig4cloud.pigx.admin.mapper.SystcorpinfoMapper;
import com.pig4cloud.pigx.admin.service.SysFileService;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-24 16:01:55
 */
@Slf4j
@Service
@AllArgsConstructor
public class SystcorpinfoServiceImpl extends ServiceImpl<SystcorpinfoMapper, Systcorpinfo> implements SystcorpinfoService {
	private final MinioTemplate minioTemplate;
	private final SysFileService sysFileService;
	private final SystcorpinfoMapper SystcorpinfoMapper;
	private final SysUserMapper sysUserMapper;
	private final EtemployeeMapper etemployeeMapper;

	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 */
	@Override
	public R uploadLogo(MultipartFile file,String param) {
		String contentType = file.getContentType();
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", CommonConstants.BUCKET_NAME);
		resultMap.put("fileName", fileName);
		//resultMap.put("url", String.format("/admin/sys-file/%s/%s", CommonConstants.BUCKET_NAME, fileName));
		//resultMap.put("url", String.format("/%s/%s", CommonConstants.BUCKET_NAME, fileName));
		resultMap.put("url", "/"+CommonConstants.BUCKET_NAME+"/" +fileName);
		PigxUser pu = SecurityUtils.getUser();
		Etemployee etemployee = new Etemployee();
		SysUser sysUser = new SysUser();
		try {

			Systcorpinfo sci = new Systcorpinfo();
			if("logo".equals(param)){
				sci.setLogo( "/"+CommonConstants.BUCKET_NAME+"/" +fileName);
				sci.setId(pu.getCorpid());
				minioTemplate.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream(),contentType);
				//文件管理数据记录,收集管理追踪文件
				sysFileService.fileLog(file, fileName);
				SystcorpinfoMapper.updateById(sci);
			}
			if("portrait".equals(param)){
				sysUser.setPortrait( "/"+CommonConstants.BUCKET_NAME+"/" +fileName);
				etemployee.setPortrait( "/"+CommonConstants.BUCKET_NAME+"/" +fileName);
				etemployee.setEid(pu.getEid());
				sysUser.setUserId(pu.getId());
				minioTemplate.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream(),contentType);
				//文件管理数据记录,收集管理追踪文件
				sysFileService.fileLog(file, fileName);
				etemployeeMapper.updateById(etemployee);
				sysUserMapper.updateById(sysUser);
			}

		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}

	@Override
	public R  findProvinceCityDistrict() {
		List<Systprovince> provinces = SystcorpinfoMapper.listProvince();
		List<Systcity> citys = SystcorpinfoMapper.listCity();
		List<Systdistrict> districts = SystcorpinfoMapper.listDistrict();
		String sprovinces = "";
		String scitys = "";
		String sdistricts = "";
		/*
		Map map = null;
		String name = "";
		String code = "";
		sprovinces ="{";
		for(int i=0;i<provinces.size();i++){
			map = provinces.get(i);
			code = (String)map.get("code");
			name = (String)map.get("name");
			if(i==provinces.size()-1){
				sprovinces = sprovinces +code+":'"+name+"'";
			}else{
				sprovinces = sprovinces +code+":'"+name+"'"+",";
			}
		}
		sprovinces = sprovinces +"}";


		scitys ="{";
		for(int g=0;g<citys.size();g++){
			map = citys.get(g);
			code = (String)map.get("code");
			name = (String)map.get("name");
			if(g==citys.size()-1){
				scitys = scitys +code+":'"+name+"'";
			}else{
				scitys = scitys +code+":'"+name+"'"+",";
			}
		}
		scitys = scitys +"}";

		sdistricts ="{";
		for(int j=0;j<districts.size();j++){
			map = districts.get(j);
			code = (String)map.get("code");
			name = (String)map.get("name");
			if(j==districts.size()-1){
				sdistricts = sdistricts +code+":'"+name+"'";
			}else{
				sdistricts = sdistricts +code+":'"+name+"'"+",";
			}
		}
		sdistricts = sdistricts +"}";
		*/
		Map map = new HashMap();
		map.put("province_list",provinces);
		map.put("city_list",citys);
		map.put("county_list",districts);
		//String result = "{";
		//result = result + "province_list:" + JSON.toJSONString(provinces);
	//	result = result + ",";
	//	result = result + "city_list:" + JSON.toJSONString(citys)+"";
	//	result = result + ",";
	//	result = result + "county_list:" + JSON.toJSONString(districts)+"";
	//	result = result + "}";

		//result = "{ 'code': 0, 'msg': null, 'data': { 'sysUser': { 'userId': 29, 'corpid': 4 } } }";
		//result = "{ 'code': 0, 'msg': null, 'data': {"+result+"}}";
		//result="{'province_list':  { 'sysUser': { 'userId': 29, 'corpid': 4 }} }";
		//result  = JSON.toJSONString(map);
		//System.out.println(result);
		//try {
		//	response.getWriter().print(JSON.toJSONString(map));
		//} catch (Exception e) {

		//}

		return R.ok(map);
	}

}
