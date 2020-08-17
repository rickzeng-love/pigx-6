/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.pig4cloud.pigx.admin.api.dto;

import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.entity.SystSecUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author gaoxiao
 * @since 2020/03/10
 */
@Data
@ApiModel(value = "ANTECH系统用户传输对象")
@EqualsAndHashCode(callSuper = true)
public class SystSecUserDTO extends SystSecUser {
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色id集合")
	private List<Integer> role;
	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	private Integer deptId;
	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newpassword1;
}
