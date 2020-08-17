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
 * 定期支付扣除登记
 *
 * @author shishengjie
 * @date 2020-07-21 14:41:58
 */
@Data
@TableName("ctregularpay_register")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "定期支付扣除登记")
public class CtregularpayRegister extends Model<CtregularpayRegister> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
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
     * 公司
     */
    @ApiModelProperty(value="公司")
    private Integer compid;
    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private Integer depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 支付\扣除
     */
    @ApiModelProperty(value="支付\\扣除")
    private Integer xtype;
    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer paytype;
    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
    private Double xvalue;
    /**
     * 开始月份
     */
    @ApiModelProperty(value="开始月份")
    private String fromterm;
    /**
     * 结束月份
     */
    @ApiModelProperty(value="结束月份")
    private String toterm;
    /**
     * 生效日期
     */
    @ApiModelProperty(value="生效日期")
    private String effectdate;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记时间
     */
    @ApiModelProperty(value="登记时间")
    private String regtime;
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
     * 封账
     */
    @ApiModelProperty(value="封账")
    private Integer closed;
    /**
     * 封账人
     */
    @ApiModelProperty(value="封账人")
    private Integer closedby;
    /**
     * 封账时间
     */
    @ApiModelProperty(value="封账时间")
    private String closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 组织
     */
    @ApiModelProperty(value="组织")
    private String corpcode;
    /**
     * CorpID
     */
    @ApiModelProperty(value="CorpID")
    private Integer corpid;
    }
