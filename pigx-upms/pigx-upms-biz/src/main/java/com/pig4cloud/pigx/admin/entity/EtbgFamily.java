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
 * 
 *
 * @author gaoxiao
 * @date 2020-04-13 10:23:24
 */
@Data
@TableName("etbg_family")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class EtbgFamily extends Model<EtbgFamily> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 员工编码
     */
    @ApiModelProperty(value="员工编码")
    private Integer eid;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * 亲属姓名
     */
    @ApiModelProperty(value="亲属姓名")
    private String fname;
    /**
     * 亲属关系
     */
    @ApiModelProperty(value="亲属关系")
    private Integer relation;
    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
    private Integer gender;
    /**
     * 出生日期
     */
    @ApiModelProperty(value="出生日期")
    private String birthday;
    /**
     * 工作单位
     */
    @ApiModelProperty(value="工作单位")
    private String company;
    /**
     * 担任职务
     */
    @ApiModelProperty(value="担任职务")
    private String job;
    /**
     * 工作状态
     */
    @ApiModelProperty(value="工作状态")
    private Integer status;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 亲属工号
     */
    @ApiModelProperty(value="亲属工号")
    private String rbadge;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 所属公司
     */
    @ApiModelProperty(value="所属公司")
    private Integer compid;
    /**
     * 所属部门
     */
    @ApiModelProperty(value="所属部门")
    private Integer depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 联系方式
     */
    @ApiModelProperty(value="联系方式")
    private String tel;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	private Integer userid;
	/**
	 * 组织code
	 */
	@ApiModelProperty(value="组织code")
	private String corpcode;
	/**
	 * 是否失效
	 */
	@ApiModelProperty(value="是否失效")
	private Integer isdisabled;

    }
