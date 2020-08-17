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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.EtstaffRegister;
import com.pig4cloud.pigx.admin.mapper.EtstaffAllMapper;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtstaffAll;
import com.pig4cloud.pigx.admin.service.EtstaffAllService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 入职登记历史
 *
 * @author gaoxiao
 * @date 2020-04-27 13:39:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etstaffall" )
@Api(value = "etstaffall", tags = "入职登记历史管理")
public class EtstaffAllController {

    private final  EtstaffAllService etstaffAllService;
    private final EtstaffAllMapper etstaffAllMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etstaffAll 入职登记历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getEtstaffAllPage(Page page, EtstaffAll etstaffAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffAll.setCorpcode(corpcode);
		QueryWrapper<EtstaffAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		String name = etstaffAll.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etstaffAll.getName());
		}
		Integer depid = etstaffAll.getDepid();
		Integer jobid = etstaffAll.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
        return R.ok(etstaffAllService.page(page,queryWrapper));
    }

	/**
	 * 最近入职分页查询
	 * @param page 分页对象
	 * @param etstaffAll 入职登记历史
	 * @return
	 */
	@ApiOperation(value = "最近入职分页查询", notes = "最近入职分页查询")
	@PostMapping("/pagezuijinruzhi" )
	public R pagezuijinruzhi(Page page, @RequestBody  EtstaffAll etstaffAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffAll.setCorpcode(corpcode);
		etstaffAll.setInitialized(1);
		return R.ok(etstaffAllMapper.listEtstaffAllRecentEntryPageSql(page,etstaffAll));
	}
	/**
	 * 放弃入职分页查询
	 * @param page 分页对象
	 * @param etstaffAll 入职登记历史
	 * @return
	 */
	@ApiOperation(value = "放弃入职分页查询", notes = "放弃入职分页查询")
	@PostMapping("/pagefangqiruzhi" )
	public R pagefangqiruzhi(Page page, @RequestBody  EtstaffAll etstaffAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffAll.setCorpcode(corpcode);
		QueryWrapper<EtstaffAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("Initialized",2);
		String name = etstaffAll.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etstaffAll.getName());
		}
		Integer depid = etstaffAll.getDepid();
		Integer jobid = etstaffAll.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(etstaffAllService.page(page,queryWrapper));
	}
	/**
	 * 已通过分页查询
	 * @param page 分页对象
	 * @param etstaffAll 入职登记历史
	 * @return
	 */
	@ApiOperation(value = "已通过分页查询", notes = "已通过分页查询")
	@PostMapping("/pageyitongguo" )
	public R pageyitongguo(Page page, @RequestBody  EtstaffAll etstaffAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffAll.setCorpcode(corpcode);
		QueryWrapper<EtstaffAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("submit",2);
		String name = etstaffAll.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etstaffAll.getName());
		}
		Integer depid = etstaffAll.getDepid();
		Integer jobid = etstaffAll.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(etstaffAllService.page(page, queryWrapper));
	}
	/**
	 * 已驳回分页查询
	 * @param page 分页对象
	 * @param etstaffAll 入职登记历史
	 * @return
	 */
	@ApiOperation(value = "已驳回分页查询", notes = "已驳回分页查询")
	@PostMapping("/pageyibohui" )
	public R pageyibohui(Page page,@RequestBody  EtstaffAll etstaffAll) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etstaffAll.setCorpcode(corpcode);
		QueryWrapper<EtstaffAll> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("submit",3);
		String name = etstaffAll.getName();
		if(name!=null && name!=""){
			queryWrapper.like("name",etstaffAll.getName());
		}
		Integer depid = etstaffAll.getDepid();
		Integer jobid = etstaffAll.getJobid();
		if(!StringUtils.isEmpty(depid)){
			queryWrapper.eq("depid",depid);
		}
		if(!StringUtils.isEmpty(jobid)){
			queryWrapper.eq("jobid",jobid);
		}
		return R.ok(etstaffAllService.page(page, queryWrapper));
	}




	/**
     * 通过id查询入职登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etstaffAllService.getById(id));
    }

    /**
     * 新增入职登记历史
     * @param etstaffAll 入职登记历史
     * @return R
     */
    @ApiOperation(value = "新增入职登记历史", notes = "新增入职登记历史")
    @SysLog("新增入职登记历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_etstaffall_add')" )
    public R save(@RequestBody EtstaffAll etstaffAll) {
        return R.ok(etstaffAllService.save(etstaffAll));
    }

/*
    */
/**
     * 修改入职登记历史
     * @param etstaffAll 入职登记历史
     * @return R
     *//*

    @ApiOperation(value = "修改入职登记历史", notes = "修改入职登记历史")
    @SysLog("修改入职登记历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_etstaffall_edit')" )
    public R updateById(@RequestBody EtstaffAll etstaffAll) {
        return R.ok(etstaffAllService.updateById(etstaffAll));
    }

    */
/**
     * 通过id删除入职登记历史
     * @param id id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除入职登记历史", notes = "通过id删除入职登记历史")
    @SysLog("通过id删除入职登记历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_etstaffall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etstaffAllService.removeById(id));
    }
*/

}
