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

package com.pig4cloud.pigx.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author gaoxiao
 * @since 2020/03/10
 */
@Data
@ApiModel(value = "角色")
@EqualsAndHashCode(callSuper = true)
public class SystSecRole extends Model<SystSecRole> {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色编号
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "角色编号")
	private Integer roleId;
	/**
	 * 角色名称
	 */
	@NotBlank(message = "角色名称不能为空")
	@ApiModelProperty(value = "角色名称")
	private String title;
	/**
	 * 角色英文名称
	 */
	@ApiModelProperty(value = "角色英文名称")
	private String title2;
	/**
	 * 所属站点
	 */
	@ApiModelProperty(value = "所属站点")
	private Integer DKID;
	/**
	 * 角色类型 0-业务角色，1-员工自助，2-APP角色
	 */
	@ApiModelProperty(value = "角色类型 0-业务角色，1-员工自助，2-APP角色")
	private Integer roleType;
	/**
	 * 是否失效（0-正常,1-删除）
	 */
	@TableLogic
	@ApiModelProperty(value = "是否失效 0-正常,1-删除")
	private Integer IsDisabled;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime SubmitTime;


	@ApiModelProperty(value = "数据权限类型")
	private Integer dsType;
	/**
	 * 数据权限作用范围
	 */
	@ApiModelProperty(value = "数据权限作用范围")
	private String dsScope;

}
