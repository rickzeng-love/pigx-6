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
 * 退休离职历史表
 *
 * @author gaoxiao
 * @date 2020-04-10 20:51:27
 */
@Data
@TableName("etleave_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "退休离职历史表")
public class EtleaveAll extends Model<EtleaveAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer type;
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
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 公司编码
     */
    @ApiModelProperty(value="公司编码")
    private Integer compid;
    /**
     * 部门编码
     */
    @ApiModelProperty(value="部门编码")
    private Integer depid;
    /**
     * 岗位编码
     */
    @ApiModelProperty(value="岗位编码")
    private Integer jobid;
    /**
     * 入职日期
     */
    @ApiModelProperty(value="入职日期")
    private String joindate;
    /**
     * 最早离职日期
     */
    @ApiModelProperty(value="最早离职日期")
    private String lastleavedate;
    /**
     * 申请离职日期
     */
    @ApiModelProperty(value="申请离职日期")
    private String applydate;
    /**
     * 退休日期
     */
    @ApiModelProperty(value="退休日期")
    private String leavedate;
    /**
     * 离职类型
     */
    @ApiModelProperty(value="离职类型")
    private Integer leavetype;
    /**
     * 离职原因
     */
    @ApiModelProperty(value="离职原因")
    private Integer leavereason;
    /**
     * 同步解除合同
     */
    @ApiModelProperty(value="同步解除合同")
    private Integer isendcon;
    /**
     * 支付违约金
     */
    @ApiModelProperty(value="支付违约金")
    private Integer ispay;
    /**
     * 违约金
     */
    @ApiModelProperty(value="违约金")
    private Double payfee;
    /**
     * 支付经济补偿金
     */
    @ApiModelProperty(value="支付经济补偿金")
    private Integer isexpenses;
    /**
     * 经济补偿金
     */
    @ApiModelProperty(value="经济补偿金")
    private Double expensefee;
    /**
     * 是否竞业限制
     */
    @ApiModelProperty(value="是否竞业限制")
    private Integer iscompete;
    /**
     * 竞业限制金
     */
    @ApiModelProperty(value="竞业限制金")
    private Double competefee;
    /**
     * 新工作单位
     */
    @ApiModelProperty(value="新工作单位")
    private String newcompany;
    /**
     * 新工作职务
     */
    @ApiModelProperty(value="新工作职务")
    private String newjob;
    /**
     * 新薪酬情况
     */
    @ApiModelProperty(value="新薪酬情况")
    private String newsalary;
    /**
     * 是否黑名单
     */
    @ApiModelProperty(value="是否黑名单")
    private Integer isblacklist;
    /**
     * 组织代码
     */
    @ApiModelProperty(value="组织代码")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfstatus;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记时间
     */
    @ApiModelProperty(value="登记时间")
    private String regdate;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 确认人
     */
    @ApiModelProperty(value="确认人")
    private Integer initializedby;
    /**
     * 确认时间
     */
    @ApiModelProperty(value="确认时间")
    private String initializedtime;
    /**
     * 审核
     */
    @ApiModelProperty(value="审核")
    private Integer submit;
    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private Integer submitby;
    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private String submittime;
    /**
     * 封存
     */
    @ApiModelProperty(value="封存")
    private Integer closed;
    /**
     * 封存人
     */
    @ApiModelProperty(value="封存人")
    private Integer closedby;
    /**
     * 封存时间
     */
    @ApiModelProperty(value="封存时间")
    private String closedtime;
    /**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;
	/**
	 * 身份证号
	 */
	@ApiModelProperty(value="身份证号")
	private String certno;
	/**
	 * 离职申请
	 */
	@ApiModelProperty(value="离职申请")
	private String lzsq;
	/**
	 * 离职交接
	 */
	@ApiModelProperty(value="离职交接")
	private String lzjj;
	private Integer corpid;
	private String spno;
	private Integer isneedaudit;
	private String lzzl;
    }
