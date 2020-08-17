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

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 部门信息
 *
 * @author gaoxiao
 * @date 2020-03-27 16:22:26
 */
@Data
@TableName("otdepartment")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "部门信息")
public class Otdepartment extends Model<Otdepartment> {
private static final long serialVersionUID = 1L;

    /**
     * 部门编码
     */
    @TableId
    @ApiModelProperty(value="部门编码")
    private Integer depid;
    /**
     * 所属组织ID
     */
    @ApiModelProperty(value="所属组织ID")
    private Integer corpid;
    /**
     * 所属组织代码
     */
    @ApiModelProperty(value="所属组织代码")
    private String corpcode;
    /**
     * 部门代码
     */
    @ApiModelProperty(value="部门代码")
    private String depcode;
    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String title;
    /**
     * 部门简称
     */
    @ApiModelProperty(value="部门简称")
    private String depabbr;
    /**
     * 所属公司
     */
    @ApiModelProperty(value="所属公司")
    private Integer compid;
    /**
     * 上级部门
     */
    @ApiModelProperty(value="上级部门")
    private Integer adminid;
    /**
     * 职能上级
     */
    @ApiModelProperty(value="职能上级")
    private Integer functionid;
    /**
     * 部门级别
     */
    @ApiModelProperty(value="部门级别")
    private Integer depgrade;
    /**
     * 部门类型
     */
    @ApiModelProperty(value="部门类型")
    private Integer deptype;
    /**
     * 部门类别
     */
    @ApiModelProperty(value="部门类别")
    private Integer depproperty;
    /**
     * 成本中心
     */
    @ApiModelProperty(value="成本中心")
    private Integer depcost;
    /**
     * 部门领导
     */
    @NotBlank
    @ApiModelProperty(value="部门领导")
    private Integer director;
    /**
     * 分管领导
     */
    @ApiModelProperty(value="分管领导")
    private Integer director2;
    /**
     * 部门人事专员
     */
    @ApiModelProperty(value="部门人事专员")
    private Integer depemp;
    /**
     * 部门编制(人数)
     */
    @ApiModelProperty(value="部门编制(人数)")
    private Integer depnum;
    /**
     * 成立日期
     */
    @ApiModelProperty(value="成立日期")
    private String effectdate;
    /**
     * 虚拟标识：1-是；0-否
     */
    @ApiModelProperty(value="虚拟标识：1-是；0-否")
    private Integer isou;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private String xorder;
    /**
     * 是否失效
     */
    @ApiModelProperty(value="是否失效")
    private Integer isdisabled;
    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String depemail;
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer agent;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String assistant;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String depstr;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String ouid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String statisticdep;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String statisticdep2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String traindep;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String wxworkorgid;
    /**
     * 
     */
    @ApiModelProperty(value="")
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
	@ApiModelProperty(value="")
	private String province;
	@ApiModelProperty(value="")
	private String city;
	@ApiModelProperty(value="")
	private String district;
	private Integer regby;
	private String regbyname;
	private String regdate;
	private Integer qywxFlag;
	private String qywxCorpid;
	private String orgfunction;
	private String decription;
    }
