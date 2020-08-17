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
 * 企业微信第三方应用信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:38:59
 */
@Data
@TableName("sys_qywx_application")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "企业微信第三方应用信息")
public class SysQywxApplication extends Model<SysQywxApplication> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 第三方应用的suiteId
     */
    @ApiModelProperty(value="第三方应用的suiteId")
    private String suiteId;
    /**
     * 第三方应用对应的suiteSecret
     */
    @ApiModelProperty(value="第三方应用对应的suiteSecret")
    private String suiteSecret;
    /**
     * ticket内容，最长为512字节
     */
    @ApiModelProperty(value="ticket内容，最长为512字节")
    private String suiteTicket;
    /**
     * ticket推送时间
     */
    @ApiModelProperty(value="ticket推送时间")
    private String ticketTime;
    /**
     * 第三方应用access_token,最长为512字节
     */
    @ApiModelProperty(value="第三方应用access_token,最长为512字节")
    private String suiteAccessToken;
    /**
     * access_token过期时间，有效期2小时
     */
    @ApiModelProperty(value="access_token过期时间，有效期2小时")
    private String tokenExpiresTime;
    /**
     * 预授权码,最长为512字节
     */
    @ApiModelProperty(value="预授权码,最长为512字节")
    private String preAuthCode;
    /**
     * 预授权码过期时间
     */
    @ApiModelProperty(value="预授权码过期时间")
    private String codeExpiresTime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String createDate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String updateDate;

	@TableField(exist=false)
	@ApiModelProperty(value="临时授权码")
	private String authcode;
    }
