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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtcardlostAll;
import com.pig4cloud.pigx.admin.service.AtcardlostAllService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 补卡登记历史
 *
 * @author shishengjie
 * @date 2020-07-22 11:43:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcardlostall" )
@Api(value = "atcardlostall", tags = "补卡登记历史管理")
public class AtcardlostAllController {

    private final  AtcardlostAllService atcardlostAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcardlostAll 补卡登记历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcardlostAllPage(Page page, AtcardlostAll atcardlostAll) {
        return R.ok(atcardlostAllService.page(page, Wrappers.query(atcardlostAll)));
    }


    /**
     * 通过id查询补卡登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcardlostAllService.getById(id));
    }

    /**
     * 新增补卡登记历史
     * @param atcardlostAll 补卡登记历史
     * @return R
     */
    @ApiOperation(value = "新增补卡登记历史", notes = "新增补卡登记历史")
    @SysLog("新增补卡登记历史" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atcardlostall_add')" )
    public R save(@RequestBody AtcardlostAll atcardlostAll) {
        return R.ok(atcardlostAllService.save(atcardlostAll));
    }

    /**
     * 修改补卡登记历史
     * @param atcardlostAll 补卡登记历史
     * @return R
     */
    @ApiOperation(value = "修改补卡登记历史", notes = "修改补卡登记历史")
    @SysLog("修改补卡登记历史" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atcardlostall_edit')" )
    public R updateById(@RequestBody AtcardlostAll atcardlostAll) {
        return R.ok(atcardlostAllService.updateById(atcardlostAll));
    }

    /**
     * 通过id删除补卡登记历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除补卡登记历史", notes = "通过id删除补卡登记历史")
    @SysLog("通过id删除补卡登记历史" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_atcardlostall_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(atcardlostAllService.removeById(id));
    }

}
