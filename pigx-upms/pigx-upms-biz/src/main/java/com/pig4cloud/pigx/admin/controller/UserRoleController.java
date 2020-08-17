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
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.CommonLog;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.UserRole;
import com.pig4cloud.pigx.admin.service.UserRoleService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户角色表
 *
 * @author gaoxiao
 * @date 2020-06-16 15:55:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userrole" )
@Api(value = "userrole", tags = "用户角色表管理")
public class UserRoleController {

    private final  UserRoleService userRoleService;
	private final SysUserService userService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param userRole 用户角色表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getUserRolePage(Page page, UserRole userRole) {
        return R.ok(userRoleService.page(page, Wrappers.query(userRole)));
    }


    /**
     * 通过id查询用户角色表
     * @param userId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{userId}" )
    public R getById(@PathVariable("userId" ) Integer userId) {
        return R.ok(userRoleService.getById(userId));
    }

    /**
     * 新增用户角色表
     * @param userRole 用户角色表
     * @return R
     */
    @ApiOperation(value = "新增用户角色表", notes = "新增用户角色表")
    @SysLog("新增用户角色表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_userrole_add')" )
    public R save(@RequestBody UserRole userRole) {
        return R.ok(userRoleService.save(userRole));
    }

	/**
	 * 新增用户角色表  	@PreAuthorize("@pms.hasPermission('admin_userrole_add')" )
	 * @param userRole 用户角色表
	 * @return R
	 */
	@ApiOperation(value = "新增用户角色表", notes = "新增用户角色表")
	@SysLog("新增用户角色表" )
	@PostMapping("/save2")
	public R save2(@RequestBody UserRole userRole) {
		return R.ok(userRoleService.save(userRole));
	}



	/**
     * 修改用户角色表
     * @param userRole 用户角色表
     * @return R
     */
    @ApiOperation(value = "修改用户角色表", notes = "修改用户角色表")
    @SysLog("修改用户角色表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_userrole_edit')" )
    public R updateById(@RequestBody UserRole userRole) {
        return R.ok(userRoleService.updateById(userRole));
    }


	/**
	 * 通过id删除用户角色表      @PreAuthorize("@pms.hasPermission('admin_userrole_del')" )
	 * @param userRole
	 * @return R
	 */
	@ApiOperation(value = "通过id删除用户角色表", notes = "通过id删除用户角色表")
	@SysLog("通过id删除用户角色表" )
	@PostMapping("/removeById" )
	public R removeById(@RequestBody UserRole userRole) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
		SysUser sysUser = userService.getById(userRole.getUserId());
		if(corpcode.equals(sysUser.getCorpcode())){
			queryWrapper.eq("user_id",userRole.getUserId());
			queryWrapper.eq("role_id",userRole.getRoleId());
			userRoleService.remove(queryWrapper);
		}
		return R.ok("删除成功！");
	}
	/**
	 * 获取用户角色列表
	 *
	 * @return 获取用户角色列表
	 */
	@PostMapping("/listRoleIdByUserId")
	public R listRoleIdByUserId() {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer userid = pigxUser.getId();
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_Id",userid);
		return R.ok(userRoleService.list(queryWrapper));
	}
	/**
	 * 保存角色列表
	 *
	 * @return 保存角色列表
	 */
	@PostMapping("/saveRoles")
	public R saveRoles(List<UserRole> roleLists) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UserRole userrole = null;
		Integer userid = null;
		Integer roleid = null;
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
		for(int i =0;i<roleLists.size();i++){
			userrole = roleLists.get(i);
			SysUser sysUser = userService.getById(userrole.getUserId());
			if(!corpcode.equals(sysUser.getCorpcode())){
				continue;
			}
			userid = userrole.getUserId();
			roleid = userrole.getRoleId();
			queryWrapper.eq("user_id",userid);
			queryWrapper.eq("role_id",roleid);
			List list = userRoleService.list(queryWrapper);
			if(list.size()>0){
				continue;
			}
			userRoleService.save(userrole);

		}
		return R.ok("操作成功！");
	}
}
