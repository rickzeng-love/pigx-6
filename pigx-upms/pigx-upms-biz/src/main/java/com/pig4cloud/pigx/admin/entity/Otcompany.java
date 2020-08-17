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
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-26 16:50:17
 */
@Data
@TableName("otcompany")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class Otcompany extends Model<Otcompany> {
private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @TableId
    @ApiModelProperty(value="公司ID")
    private Integer compid;
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
     * 公司代码
     */
    @ApiModelProperty(value="公司代码")
    private String compcode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value="公司名称")
    private String title;
    /**
     * 公司地址
     */
    @ApiModelProperty(value="公司简称")
    private String compabbr;
    /**
     * 上级公司
     */
    @ApiModelProperty(value="上级公司")
    private Integer adminid;
    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String regTel;
    /**
     * 公司级别
     */
    @ApiModelProperty(value="公司级别")
    private Integer compgrade;
    /**
     * 公司类型
     */
    @ApiModelProperty(value="公司类型")
    private Integer comptype;
    /**
     * 公司区域
     */
    @ApiModelProperty(value="公司区域")
    private Integer comparea;
    /**
     * 成立日期
     */
    @ApiModelProperty(value="成立日期")
    private String effectdate;
    /**
     * 法人
     */
    @ApiModelProperty(value="法人")
    private String lawyer;
    /**
     * 公司地址
     */
    @ApiModelProperty(value="公司地址")
    private String address;
    /**
     * 注册地址
     */
    @ApiModelProperty(value="注册地址")
    private String regaddress;
    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String tel;
    /**
     * 传真
     */
    @ApiModelProperty(value="传真")
    private String fax;
    /**
     * 右邮编
     */
    @ApiModelProperty(value="右邮编")
    private String postcode;
    /**
     * 网址
     */
    @ApiModelProperty(value="网址")
    private String website;
    /**
     * 公司邮箱
     */
    @ApiModelProperty(value="公司邮箱")
    private String compmail;
    /**
     * 失效
     */
    @ApiModelProperty(value="失效")
    private Integer isdisabled;
    /**
     * 失效日期
     */
    @ApiModelProperty(value="失效日期")
    private String disableddate;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private String xorder;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String groupname;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String groupid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String groupemail;
    /**
     * 总经理
     */
    @ApiModelProperty(value="总经理")
    private String director;
    /**
     * 经营范围
     */
    @ApiModelProperty(value="经营范围")
    private String operaterange;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String wxworkorgid;
    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private String createdate;
    /**
     * 更新日期
     */
    @ApiModelProperty(value="更新日期")
    private String updatedate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String dingorgid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer delFlag;
    /**
     * 
     */
    @ApiModelProperty(value="",hidden=true)
    private Integer tenantId;
    /**
     * 虚拟标识：1-是；0-否
     */
    @ApiModelProperty(value="虚拟标识：1-是；0-否")
    private Integer isou;
	/**
	 * 公司或部门：1-是；0-否
	 */
	@ApiModelProperty(value="公司或部门：1-是；0-否")
	private String stype;
	/**
	 * 公司或部门人数
	 */
	@ApiModelProperty(value="公司或部门人数")
	private String people;
	/*
	  * 公司id  字符串
	 */
	@ApiModelProperty(value="公司ID")
	private String  sid;

	/**
	 * 是否注册用户1-是；0-否
	 */
	@ApiModelProperty(value="是否注册用户1-是；0-否")
	private Integer isregister;

    }
