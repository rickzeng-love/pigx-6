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
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:40:18
 */
@Data
@TableName("etbg_working")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "工作经历")
public class EtbgWorking extends Model<EtbgWorking> {
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
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private String begindate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private String enddate;
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
     * 工作地点
     */
    @ApiModelProperty(value="工作地点")
    private String workplace;
    /**
     * 证明人
     */
    @ApiModelProperty(value="证明人")
    private String reference;
    /**
     * 联系方式
     */
    @ApiModelProperty(value="联系方式")
    private String tel;
    /**
     * 是否海外工作
     */
    @ApiModelProperty(value="是否海外工作")
    private Integer isout;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer inoutflag;
    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String department;
    /**
     * 薪资（税前）
     */
    @ApiModelProperty(value="薪资（税前）")
    private BigDecimal salarytax;
    /**
     * 主要职责
     */
    @ApiModelProperty(value="主要职责")
    private String duty;
    /**
     * 离职原因
     */
    @ApiModelProperty(value="离职原因")
    private String leavereason;
	/**
	 * 证明人职务
	 */
	@ApiModelProperty(value="证明人职务")
	private String zmrzw;
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
	private Integer isdisabled;

    }
