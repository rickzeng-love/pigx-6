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
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.service.SysWorkflowTemplateCorpService;
import com.pig4cloud.pigx.admin.service.SystcorpinfoService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.service.WftcdTemplateTypeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学制
 *
 * @author XP
 * @date 2020-06-16 16:10:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wftcdtemplatetype" )
@Api(value = "wftcdtemplatetype", tags = "学制管理")
public class WftcdTemplateTypeController {

    private final  WftcdTemplateTypeService wftcdTemplateTypeService;
	private final SysWorkflowTemplateCorpService sysWorkflowTemplateCorpService;
	private final SystcorpinfoService systcorpinfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param wftcdTemplateType 学制
     * @return
     *//*
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getWftcdTemplateTypePage(Page page, WftcdTemplateType wftcdTemplateType) {
        return R.ok(wftcdTemplateTypeService.page(page, Wrappers.query(wftcdTemplateType)));
    }*/

    /**
     * 查询
     * @return
     */
    @ApiOperation(value = "列表查询", notes = "列表查询")
    @PostMapping("/getWftcdTemplateTypeList" )
    public R getWftcdTemplateTypeList() {
		WftcdTemplateType wftcdTemplateType = null;
		SysWorkflowTemplateCorp sysWorkflowTemplateCorp = null;
		List<SysWorkflowTemplateCorp> sysList = null;
		List<WftcdTemplateType> list = wftcdTemplateTypeService.list();
		List result = new ArrayList(list.size());
		Map map  = null;
		WftcdTemplateType wftcdTemplateType1 = null;
		String typetitle = "";
		if (list.size() > 0){
			for (int i = 0; i < list.size(); i++){
				wftcdTemplateType1 = list.get(i);
				String itemid = wftcdTemplateType1.getId().toString();
				typetitle = wftcdTemplateType1.getTitle();
				sysWorkflowTemplateCorp = new SysWorkflowTemplateCorp();
				sysWorkflowTemplateCorp.setType(itemid);
				PigxUser pigxUser = SecurityUtils.getUser();
//				String qywxcorpid = pigxUser.getQywxCorpid();
				String corpcode = pigxUser.getCorpcode();
				QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("corpcode",corpcode);
				Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
				String qywxcorpid= "";
				if(!StringUtils.isEmpty(systcorpinfo)){
					qywxcorpid = systcorpinfo.getQywxCorpid();

				}else{
					return R.failed("请维护企业微信corpid！");
				}
				sysWorkflowTemplateCorp.setQywxCorpid(qywxcorpid);
				sysList = sysWorkflowTemplateCorpService.list(Wrappers.query(sysWorkflowTemplateCorp));
				map = new HashMap();
				map.put("titletype",typetitle);
				map.put("item",sysList);
				result.add(i,map);

			}
		}
		return R.ok(result);
    }


    /**
     * 通过id查询学制
     * @param id id
     * @return R
     */
/*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(wftcdTemplateTypeService.getById(id));
    }
*/

    /**
     * 新增学制
     * @param wftcdTemplateType 学制
     * @return R
     */
/*    @ApiOperation(value = "新增学制", notes = "新增学制")
    @SysLog("新增学制" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_wftcdtemplatetype_add')" )
    public R save(@RequestBody WftcdTemplateType wftcdTemplateType) {
        return R.ok(wftcdTemplateTypeService.save(wftcdTemplateType));
    }*/

    /**
     * 修改学制
     * @param wftcdTemplateType 学制
     * @return R
     */
/*    @ApiOperation(value = "修改学制", notes = "修改学制")
    @SysLog("修改学制" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_wftcdtemplatetype_edit')" )
    public R updateById(@RequestBody WftcdTemplateType wftcdTemplateType) {
        return R.ok(wftcdTemplateTypeService.updateById(wftcdTemplateType));
    }*/

    /**
     * 通过id删除学制
     * @param id id
     * @return R
     */
/*
    @ApiOperation(value = "通过id删除学制", notes = "通过id删除学制")
    @SysLog("通过id删除学制" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_wftcdtemplatetype_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(wftcdTemplateTypeService.removeById(id));
    }
*/

}
