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

package com.pig4cloud.pigx.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtcdCerttype;
import com.pig4cloud.pigx.admin.service.SysFileService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systcorpinfo;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.http.HttpResponse;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-24 16:01:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systcorpinfo" )
@Api(value = "systcorpinfo", tags = "管理")
public class SystcorpinfoController {

    private final  SystcorpinfoService systcorpinfoService;
	private final SysFileService sysFileService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param systcorpinfo 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystcorpinfoPage(Page page, Systcorpinfo systcorpinfo) {
        return R.ok(systcorpinfoService.page(page, Wrappers.query(systcorpinfo)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {

		Systcorpinfo sci = systcorpinfoService.getById(id);
		if(sci==null){
			return R.ok(new Systcorpinfo());
		}
    	return R.ok(systcorpinfoService.getById(id));
    }

	/**
	 * 通过id查询
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@PostMapping("/getSystcorpinfo" )
	public R getSystcorpinfo() {
		String currentTime = DateUtils.getTimeString();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);

		return R.ok(systcorpinfoService.getOne(queryWrapper));
	}

    /**
     * 新增
     * @param systcorpinfo 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systcorpinfo_add')" )
    public R save(@RequestBody Systcorpinfo systcorpinfo) {
        return R.ok(systcorpinfoService.save(systcorpinfo));
    }

/*    *//**
     * 修改@PreAuthorize("@pms.hasPermission('admin_systcorpinfo_edit')" )
     * @param systcorpinfo 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PostMapping(value="/updateById")
    public R updateById(@RequestBody Systcorpinfo systcorpinfo) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<Systcorpinfo> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",systcorpinfo.getId());
        return R.ok(systcorpinfoService.update(systcorpinfo,updateWrapper));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_systcorpinfo_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(systcorpinfoService.removeById(id));
    }
	*/
    /**
	 * 上传文件
	 * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file,@RequestParam("id") String param) {

		return systcorpinfoService.uploadLogo(file,param);
	}
	/**
	 *获取省市
	 *
	 * @return
	 */
	@PostMapping(value="/findpcd")
	public R findProvinceCityDistrict(){
		  return systcorpinfoService.findProvinceCityDistrict();
	}

}
