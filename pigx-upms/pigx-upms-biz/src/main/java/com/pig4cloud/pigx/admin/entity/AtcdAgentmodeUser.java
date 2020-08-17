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

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 考勤管理员
 *
 * @author GXS
 * @date 2020-05-22 16:57:40
 */
@Data
@TableName("atcd_agentmode_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "考勤管理员")
public class AtcdAgentmodeUser extends Model<AtcdAgentmodeUser> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 帐套号
     */
    @ApiModelProperty(value="帐套号")
    private Integer aid;
    /**
     * 用户
     */
    @ApiModelProperty(value="用户")
    private Integer userid;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 是否默认
     */
    @ApiModelProperty(value="是否默认")
    private Integer isdefault;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String compcode;
    /**
     * 组织ID
     */
    @ApiModelProperty(value="组织ID")
    private Integer corpid;
    /**
     * 组织编码
     */
    @ApiModelProperty(value="组织编码")
    private String corpcode;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value="用户名称")
	private String username;
    }

