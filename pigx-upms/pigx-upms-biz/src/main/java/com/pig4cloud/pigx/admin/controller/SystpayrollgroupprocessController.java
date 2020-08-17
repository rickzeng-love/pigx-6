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
import com.pig4cloud.pigx.admin.entity.Systpayrollgroup;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.Systpayrollgroupprocess;
import com.pig4cloud.pigx.admin.service.SystpayrollgroupprocessService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 薪资流程查看
 *
 * @author gaoxiao
 * @date 2020-06-13 10:14:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollgroupprocess" )
@Api(value = "systpayrollgroupprocess", tags = "薪资流程查看管理")
public class SystpayrollgroupprocessController {

    private final  SystpayrollgroupprocessService systpayrollgroupprocessService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollgroupprocess 薪资流程查看
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollgroupprocessPage(Page page, Systpayrollgroupprocess systpayrollgroupprocess) {
        return R.ok(systpayrollgroupprocessService.page(page, Wrappers.query(systpayrollgroupprocess)));
    }


	/**
	 * 分页查询

	 * @return
	 */
	@ApiOperation(value = "查询薪资流状态", notes = "查询薪资流状态")
	@PostMapping("/getSystpayrollgroupprocessStatus" )
	public R getSystpayrollgroupprocessStatus() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();


		QueryWrapper<Systpayrollgroupprocess> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpid",pigxUser.getCorpid());
		Systpayrollgroupprocess systpayrollgroupprocess = systpayrollgroupprocessService.getOne(queryWrapper);
		Integer initialized = null;  //1
		Integer submit = null;//2
		Integer confirm = null;//3
		Integer confirmby = null;//4
		Integer closed = null;//5
		Integer status = 0;
		//0：无记录1：初始化2：计算3：签发4：封账
		if(StringUtils.isEmpty(systpayrollgroupprocess)){
			status = 0;
		}else{
			initialized = systpayrollgroupprocess.getInitialized();

			submit = systpayrollgroupprocess.getSubmit();
			confirm = systpayrollgroupprocess.getConfirm();
			closed = systpayrollgroupprocess.getClosed();
			confirmby = systpayrollgroupprocess.getConfirmby();
			if(!StringUtils.isEmpty(initialized) && initialized==1){
				status = 1;
			}
			if(!StringUtils.isEmpty(submit) && submit==1){
				status = 2;
			}
			if(!StringUtils.isEmpty(confirm) && confirm==1){
				status = 3;
			}else{
				if(!StringUtils.isEmpty(confirmby)){
					status = 4;
				}
			}

			if(!StringUtils.isEmpty(closed) && closed==1){
				status = 5;
			}

		}
		return R.ok(status);
	}


    /**
     * 通过id查询薪资流程查看
     * @param gid id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{gid}" )
    public R getById(@PathVariable("gid" ) Integer gid) {
        return R.ok(systpayrollgroupprocessService.getById(gid));
    }

	/**
	 * 通过id查询薪资流程查看
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@PostMapping("/getSystpayrollgroupprocess" )
	public R getSystpayrollgroupprocess() {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpid = pigxUser.getCorpid();
		QueryWrapper<Systpayrollgroupprocess> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpid",corpid);
		return R.ok(systpayrollgroupprocessService.getOne(queryWrapper));
	}

	/**
     * 新增薪资流程查看
     * @param systpayrollgroupprocess 薪资流程查看
     * @return R
     */
    @ApiOperation(value = "新增薪资流程查看", notes = "新增薪资流程查看")
    @SysLog("新增薪资流程查看" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroupprocess_add')" )
    public R save(@RequestBody Systpayrollgroupprocess systpayrollgroupprocess) {
        return R.ok(systpayrollgroupprocessService.save(systpayrollgroupprocess));
    }

/*
    */
/**
     * 修改薪资流程查看
     * @param systpayrollgroupprocess 薪资流程查看
     * @return R
     *//*

    @ApiOperation(value = "修改薪资流程查看", notes = "修改薪资流程查看")
    @SysLog("修改薪资流程查看" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroupprocess_edit')" )
    public R updateById(@RequestBody Systpayrollgroupprocess systpayrollgroupprocess) {
        return R.ok(systpayrollgroupprocessService.updateById(systpayrollgroupprocess));
    }

    */
/**
     * 通过id删除薪资流程查看
     * @param gid id
     * @return R
     *//*

    @ApiOperation(value = "通过id删除薪资流程查看", notes = "通过id删除薪资流程查看")
    @SysLog("通过id删除薪资流程查看" )
    @DeleteMapping("/{gid}" )
    @PreAuthorize("@pms.hasPermission('admin_systpayrollgroupprocess_del')" )
    public R removeById(@PathVariable Integer gid) {
        return R.ok(systpayrollgroupprocessService.removeById(gid));
    }
*/

}
