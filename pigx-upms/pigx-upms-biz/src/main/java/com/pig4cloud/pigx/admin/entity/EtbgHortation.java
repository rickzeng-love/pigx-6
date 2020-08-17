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
 * 奖惩记录
 *
 * @author gaoxiao
 * @date 2020-04-13 10:20:30
 */
@Data
@TableName("etbg_hortation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "奖惩记录")
public class EtbgHortation extends Model<EtbgHortation> {
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
     * 公司内部?
     */
    @ApiModelProperty(value="公司内部?")
    private Integer inout;
    /**
     * 奖励/惩罚
     */
    @ApiModelProperty(value="奖励/惩罚")
    private Integer hortationtype;
    /**
     * 奖惩种类
     */
    @ApiModelProperty(value="奖惩种类")
    private Integer hortationkind;
    /**
     * 发生日期
     */
    @ApiModelProperty(value="发生日期")
    private String happendate;
    /**
     * 机构
     */
    @ApiModelProperty(value="机构")
    private String organizer;
    /**
     * 原因
     */
    @ApiModelProperty(value="原因")
    private String reason;
    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String description;
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
     * 奖惩金额
     */
    @ApiModelProperty(value="奖惩金额")
    private Double amount;
    /**
     * 发放开始月份
     */
    @ApiModelProperty(value="发放开始月份")
    private String paymonth;
    /**
     * 发放结束月份
     */
    @ApiModelProperty(value="发放结束月份")
    private String payendmonth;
    /**
     * 确认?
     */
    @ApiModelProperty(value="确认?")
    private Integer initialized;
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
    }
