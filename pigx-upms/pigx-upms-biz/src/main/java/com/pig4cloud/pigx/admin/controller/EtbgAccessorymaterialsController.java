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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtbgWorking;
import com.pig4cloud.pigx.admin.mapper.EtbgAccessorymaterialsMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgAccessorymaterials;
import com.pig4cloud.pigx.admin.service.EtbgAccessorymaterialsService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 员工附件
 *
 * @author gaoxiao
 * @date 2020-04-16 11:36:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgaccessorymaterials" )
@Api(value = "etbgaccessorymaterials", tags = "员工附件管理")
public class EtbgAccessorymaterialsController {

    private final  EtbgAccessorymaterialsService etbgAccessorymaterialsService;
    private final EtbgAccessorymaterialsMapper etbgAccessorymaterialsMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgAccessorymaterials 员工附件
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgAccessorymaterialsPage(Page page, EtbgAccessorymaterials etbgAccessorymaterials) {
        return R.ok(etbgAccessorymaterialsService.page(page, Wrappers.query(etbgAccessorymaterials)));
    }
	/**
	 * 查询所有
	 * @param etbgAccessorymaterials 入职登记表
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgAccessorymaterials" )
	public R getAllEtbgAccessorymaterials(EtbgAccessorymaterials etbgAccessorymaterials) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgAccessorymaterials.setCorpcode(corpcode);
		etbgAccessorymaterials.setUserid(userid);
		return R.ok(etbgAccessorymaterialsService.list(Wrappers.query(etbgAccessorymaterials)));
	}


    /**
     * 通过id查询员工附件
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgAccessorymaterialsService.getById(id));
    }

    /**
     * 新增员工附件    @PreAuthorize("@pms.hasPermission('admin_etbgaccessorymaterials_add')" )
     * @param etbgAccessorymaterials 员工附件
     * @return R
     */
    @ApiOperation(value = "新增员工附件", notes = "新增员工附件")
    @SysLog("新增员工附件" )
    @PostMapping
    public R save(@RequestBody EtbgAccessorymaterials etbgAccessorymaterials) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgAccessorymaterials.setCorpcode(corpcode);
		etbgAccessorymaterials.setUserid(userid);
        return R.ok(etbgAccessorymaterialsService.save(etbgAccessorymaterials));
    }

	/**
	 * 新增员工附件    @PreAuthorize("@pms.hasPermission('admin_etbgaccessorymaterials_add')" )
	 * @param etbgAccessorymaterials 员工附件
	 * @return R
	 */
	@ApiOperation(value = "新增员工附件2", notes = "新增员工附件2")
	@SysLog("新增员工附件2" )
	@PostMapping("/save2")
	public R save2(@RequestBody EtbgAccessorymaterials etbgAccessorymaterials) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgAccessorymaterials.setCorpcode(corpcode);
		etbgAccessorymaterials.setUserid(userid);
		return R.ok(etbgAccessorymaterialsService.save(etbgAccessorymaterials));
	}

    /**
     * 修改员工附件    @PreAuthorize("@pms.hasPermission('admin_etbgaccessorymaterials_edit')" )
     * @param etbgAccessorymaterials 员工附件
     * @return R
     */
    @ApiOperation(value = "修改员工附件", notes = "修改员工附件")
    @SysLog("修改员工附件" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgAccessorymaterials etbgAccessorymaterials) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgAccessorymaterials> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgAccessorymaterials.getId());
        return R.ok(etbgAccessorymaterialsService.update(etbgAccessorymaterials,updateWrapper));
    }
/*
    *//**
     * 通过id删除员工附件
     * @param id id    @PreAuthorize("@pms.hasPermission('admin_etbgaccessorymaterials_del')" )
     * @return R
     *//*
    @ApiOperation(value = "通过id删除员工附件", notes = "通过id删除员工附件")
    @SysLog("通过id删除员工附件" )
    @DeleteMapping("/{id}" )
    */public R removeById(@PathVariable Integer id) {
        return R.ok(etbgAccessorymaterialsService.removeById(id));
    }

    /**
	 * 上传文件
	 * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@ApiOperation(value = "上传附件信息", notes = "上传附件信息")
	@PostMapping("/uploadFile")
	public R upload(@RequestParam("file") MultipartFile file,String columnName) {

		return etbgAccessorymaterialsService.uploadFile(file,columnName);
	}

	/**
	 * 查询所有
	 * @param etbgAccessorymaterials
	 * @return
	 */
	@ApiOperation(value = "根据EID查询档案", notes = "根据EID查询档案")
	@PostMapping("/getEtbgAccessorymaterialsBySql" )
	public R getEtbgAccessorymaterialsBySql(@RequestBody EtbgAccessorymaterials etbgAccessorymaterials) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgAccessorymaterials.setCorpcode(corpcode);
		Integer eid = etbgAccessorymaterials.getEid();
		if(StringUtils.isEmpty(eid)){
			return R.ok("EID不能为空！");
		}
		return R.ok(etbgAccessorymaterialsMapper.listEtbgAccessorymaterialsBySql(etbgAccessorymaterials));
	}
	/**
	 * 分员工档案-附件资料
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工档案-附件资料", notes = "员工档案-附件资料")
	@PostMapping("/getEtbgAccessorymaterialsAllBySql" )
	public R getEtbgAccessorymaterialsAllBySql(Page page,EtbgAccessorymaterials etbgAccessorymaterials) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgAccessorymaterials.setCorpcode(corpcode);
		IPage resultpage = etbgAccessorymaterialsMapper.listEtbgAccessorymaterialsAllBySql(page,etbgAccessorymaterials);
		return R.ok(resultpage);
	}
}
