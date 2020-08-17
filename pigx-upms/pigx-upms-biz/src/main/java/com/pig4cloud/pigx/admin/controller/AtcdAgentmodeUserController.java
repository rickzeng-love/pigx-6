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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtcdCardlostreason;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.mapper.AtcdAgentmodeUserMapper;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtcdAgentmodeUser;
import com.pig4cloud.pigx.admin.service.AtcdAgentmodeUserService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 考勤管理员
 *
 * @author GXS
 * @date 2020-05-22 16:57:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atcdagentmodeuser" )
@Api(value = "atcdagentmodeuser", tags = "考勤管理员管理")
public class AtcdAgentmodeUserController {

    private final  AtcdAgentmodeUserService atcdAgentmodeUserService;
    private final AtcdAgentmodeUserMapper atcdAgentmodeUserMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atcdAgentmodeUser 考勤管理员
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtcdAgentmodeUserPage(Page page, AtcdAgentmodeUser atcdAgentmodeUser) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdAgentmodeUser.setCorpcode(corpCode);
        return R.ok(atcdAgentmodeUserService.page(page, Wrappers.query(atcdAgentmodeUser)));
    }



	/**
	 * 查询所有
	 * @param atcdAgentmodeUser
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@PostMapping("/getAllAtcdAgentmodeUser" )
	public R getAllAtcdAgentmodeUser(AtcdAgentmodeUser atcdAgentmodeUser) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atcdAgentmodeUser.setCorpcode(corpCode);
		return R.ok(atcdAgentmodeUserService.list(Wrappers.query(atcdAgentmodeUser)));
	}

	/**
	 * 考勤管理员-查询列表 转换成中文
	 * @param page 分页对象
	 * @param atcdAgentmodeUser 员工信息表
	 * @return
	 */
	@ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	@PostMapping("/getAtcdAgentmodeUserList" )
	public R getAtcdAgentmodeUserList(Page page, AtcdAgentmodeUser atcdAgentmodeUser) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		atcdAgentmodeUser.setCorpcode(corpcode);
		return R.ok(atcdAgentmodeUserService.getAtcdAgentmodeUserList(page,atcdAgentmodeUser));
	}

	/**
	 * 根据当前用户默认的考勤账套(只有一条记录)
	 * @return
	 */
	@ApiOperation(value = "根据用户ID获取默认考勤账套", notes = "根据用户ID获取默认考勤账套")
	@GetMapping("/getAtcdAgentmodeUserByUserID" )
	public R getAtcdAgentmodeUserByUserID(){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//获取UserID值
		Integer userid=pigxUser.getId();
		return R.ok(atcdAgentmodeUserMapper.listAtcdAgentmodeUserByUserID(userid,corpcode));
	}

	/**
     * 通过id查询考勤管理员
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(atcdAgentmodeUserService.getById(id));
    }

    /**
     * 新增考勤管理员
     * @param atcdAgentmodeUser 考勤管理员 @PreAuthorize("@pms.hasPermission('admin_atcdagentmodeuser_add')" )
     * @return R
     */
    @ApiOperation(value = "新增考勤管理员", notes = "新增考勤管理员")
    @SysLog("新增考勤管理员" )
    @PostMapping("/save")
    public R save(@RequestBody AtcdAgentmodeUser atcdAgentmodeUser) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer corpID = pigxUser.getCorpid();
		String corpCode = pigxUser.getCorpcode();
		//Integer userid = pigxUser.getId();
		atcdAgentmodeUser.setCorpid(corpID);
		atcdAgentmodeUser.setCorpcode(corpCode);
        return R.ok(atcdAgentmodeUserService.save(atcdAgentmodeUser));
    }

/*
    */
/**
     * 修改考勤管理员
     * @param atcdAgentmodeUser 考勤管理员 @PreAuthorize("@pms.hasPermission('admin_atcdagentmodeuser_edit')" )
     * @return R
     */

    @ApiOperation(value = "修改考勤管理员", notes = "修改考勤管理员")
    @SysLog("修改考勤管理员" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody AtcdAgentmodeUser atcdAgentmodeUser) {
		PigxUser pigxUser = SecurityUtils.getUser();
		UpdateWrapper<AtcdAgentmodeUser> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",pigxUser.getCorpcode());
		updateWrapper.eq("id",atcdAgentmodeUser.getId());
        return R.ok(atcdAgentmodeUserService.update(atcdAgentmodeUser,updateWrapper));
    }


/**
     * 通过id删除考勤管理员
     * @param id id @PreAuthorize("@pms.hasPermission('admin_atcdagentmodeuser_del')" )
     * @return R
     *//*

    @ApiOperation(value = "通过id删除考勤管理员", notes = "通过id删除考勤管理员")
    @SysLog("通过id删除考勤管理员" )
    @DeleteMapping("/{id}" )

    public R removeById(@PathVariable Integer id) {
        return R.ok(atcdAgentmodeUserService.removeById(id));
    }
*/

}
