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

package com.pig4cloud.pigx.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公司流程模板配置表
 *
 * @author XP
 * @date 2020-06-16 16:10:16
 */
@Data
@TableName("sys_workflow_template_corp")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "公司流程模板配置表")
public class SysWorkflowTemplateCorp extends Model<SysWorkflowTemplateCorp> {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	@ApiModelProperty(value = "序号")
	private Integer id;
	/**
	 * 流程名称
	 */
	@ApiModelProperty(value = "流程名称")
	private String name;
	/**
	 * 服务商流程模板id
	 */
	@ApiModelProperty(value = "服务商流程模板id")
	private String templateId;
	/**
	 * 公司流程模板id
	 */
	@ApiModelProperty(value = "公司流程模板id")
	private String openTemplateId;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String type;
	/**
	 * 用户所属企业的corpid
	 */
	@ApiModelProperty(value = "用户所属企业的corpid")
	private String qywxCorpid;
	/**
	 * 1：可用，0：不可用
	 */
	@ApiModelProperty(value = "1：可用，0：不可用")
	private String isdisabled;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private String updateTime;
}
