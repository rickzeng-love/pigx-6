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
 * 企业微信第三方应用开发接入授权企业信息
 *
 * @author gaoxiao
 * @date 2020-04-23 11:39:08
 */
@Data
@TableName("sys_qywx_application_authorizer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "企业微信第三方应用开发接入授权企业信息")
public class SysQywxApplicationAuthorizer extends Model<SysQywxApplicationAuthorizer> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 企业微信永久授权码,最长为512字节
     */
    @ApiModelProperty(value="企业微信永久授权码,最长为512字节")
    private String permanentCode;
    /**
     * 授权方（企业）access_token,最长为512字节
     */
    @ApiModelProperty(value="授权方（企业）access_token,最长为512字节")
    private String accessToken;
    /**
     * access_token过期时间
     */
    @ApiModelProperty(value="access_token过期时间")
    private String accessTokenExpiresTime;
    /**
     * jssdk生成签名所需的jsapi_ticket(企业)
     */
    @ApiModelProperty(value="jssdk生成签名所需的jsapi_ticket(企业)")
    private String corpJsapiTick;
    /**
     * jssdk生成签名所需的jsapi_ticket\r\n(应用)         刷新规则同access_token
     */
    @ApiModelProperty(value="jssdk生成签名所需的jsapi_ticket\r\n(应用)         刷新规则同access_token")
    private String jsapiTicket;
    /**
     * access_token过期时间
     */
    @ApiModelProperty(value="access_token过期时间")
    private String jsExpiresTime;
    /**
     * 第三方应用的suiteId
     */
    @ApiModelProperty(value="第三方应用的suiteId")
    private String suiteId;
    /**
     * 授权方企业微信id
     */
    @ApiModelProperty(value="授权方企业微信id")
    private String authCorpid;
    /**
     * 授权方企业名称
     */
    @ApiModelProperty(value="授权方企业名称")
    private String authCorpName;
    /**
     * 授权方企业类型，认证号：verified, 注册号：unverified
     */
    @ApiModelProperty(value="授权方企业类型，认证号：verified, 注册号：unverified")
    private String authCorpType;
    /**
     * 授权方企业方形头像
     */
    @ApiModelProperty(value="授权方企业方形头像")
    private String authCorpSquareLogoUrl;
    /**
     * 授权方企业用户规模
     */
    @ApiModelProperty(value="授权方企业用户规模")
    private Integer authCorpUserMax;
    /**
     * 授权方企业的主体名称(仅认证过的企业有)'
     */
    @ApiModelProperty(value="授权方企业的主体名称(仅认证过的企业有)'")
    private String authCorpFullName;
    /**
     * 企业类型，1. 企业; 2. 政府以及事业单位; 3. 其他组织, 4.团队号
     */
    @ApiModelProperty(value="企业类型，1. 企业; 2. 政府以及事业单位; 3. 其他组织, 4.团队号")
    private Integer authSubjectType;
    /**
     * 认证到期时间
     */
    @ApiModelProperty(value="认证到期时间")
    private String authVerifiedEndTime;
    /**
     * 授权企业在微工作台（原企业号）的二维码，可用于关注微工作台
     */
    @ApiModelProperty(value="授权企业在微工作台（原企业号）的二维码，可用于关注微工作台")
    private String authCorpWxqrcode;
    /**
     * 企业规模。当企业未设置该属性时，值为空
     */
    @ApiModelProperty(value="企业规模。当企业未设置该属性时，值为空")
    private String authCorpScale;
    /**
     * 企业所属行业。当企业未设置该属性时，值为空
     */
    @ApiModelProperty(value="企业所属行业。当企业未设置该属性时，值为空")
    private String authCorpIndustry;
    /**
     * 企业所属子行业。当企业未设置该属性时，值为空
     */
    @ApiModelProperty(value="企业所属子行业。当企业未设置该属性时，值为空")
    private String authCorpSubIndustry;
    /**
     * 企业所在地信息, 为空时表示未知
     */
    @ApiModelProperty(value="企业所在地信息, 为空时表示未知")
    private String authLocation;
    /**
     * 授权信息
     */
    @ApiModelProperty(value="授权信息")
    private String authInfo;
    /**
     * 授权管理员的信息
     */
    @ApiModelProperty(value="授权管理员的信息")
    private String authUserInfo;
    /**
     * 代理服务商企业信息
     */
    @ApiModelProperty(value="代理服务商企业信息")
    private String dealerCorpInfo;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private String createDate;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private String updateDate;
	/**
	 * 企业通讯录的access_token
	 */
	@ApiModelProperty(value="企业通讯录的access_token")
	private String addressAccessToken;
	/**
	 * 企业通讯录的access_token过期时间
	 */
	@ApiModelProperty(value="企业通讯录的access_token过期时间")
	private String addressAccessTokenTime;
    }
