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

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 *
 * @author gaoxiao
 * @date 2020-03-24 16:01:55
 */
@Data
@ApiModel(value = "")
public class Systcorpinfo  implements Serializable  {
private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
	@TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="组织ID")
    private Integer id;
    /**
     * 组织代码
     */

    @ApiModelProperty(value="组织代码")
    private String corpcode;
    /**
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    private String corpname;
    /**
     * LOGOurl
     */
    @ApiModelProperty(value="LOGOurl")
    private String logo;
    /**
     * 行业
     */
    @ApiModelProperty(value="行业")
    private String trade;
    /**
     * 规模
     */
    @ApiModelProperty(value="规模")
    private String scale;
    /**
     * 省
     */
    @ApiModelProperty(value="省")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(value="市")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(value="区")
    private String district;
    /**
     * 详细地址
     */
    @ApiModelProperty(value="详细地址")
    private String adress;
    /**
     * 邮政编码
     */
    @ApiModelProperty(value="邮政编码")
    private String postcode;
    /**
     * 联系人
     */
    @ApiModelProperty(value="联系人")
    private String contact;
    /**
     * 手机
     */
    @ApiModelProperty(value="手机")
    private String mobile;
    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String portrait;
    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * 许可人数
     */
    @ApiModelProperty(value="许可人数")
    private Integer allowempnum;
    /**
     * 薪资账套数
     */
    @ApiModelProperty(value="薪资账套数")
    private Integer payrollgroupnum;
    /**
     * 注册日期
     */
    @ApiModelProperty(value="注册日期")
    private String regdate;
    /**
     * 确认标识
     */
    @ApiModelProperty(value="确认标识")
    private Integer isinitialized;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
	/**
	 * 组织人数
	 */
	@ApiModelProperty(value="组织人数")
	@TableField(exist = false)
	private String people;
	/**
	 * 二维码
	 */
	@ApiModelProperty(value="二维码")
	private String qrcode;
	/**
	 * 通讯录密钥
	 */
	@ApiModelProperty(value="通讯录密钥")
	private String addresssecret;
	//用户企业微信corpid
	private String  qywxCorpid;
	private Integer isadmin;
    }
