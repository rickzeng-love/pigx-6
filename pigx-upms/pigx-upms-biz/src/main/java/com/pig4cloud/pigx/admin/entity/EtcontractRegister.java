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
 * 合同变动
 *
 * @author gaoxiao
 * @date 2020-04-17 10:29:01
 */
@Data
@TableName("etcontract_register")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "合同变动")
public class EtcontractRegister extends Model<EtcontractRegister> {
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
     * 岗位编码
     */
    @ApiModelProperty(value="岗位编码")
    private Integer jobid;
    /**
     * 职级
     */
    @ApiModelProperty(value="职级")
    private Integer empgrade;
    /**
     * 职等
     */
    @ApiModelProperty(value="职等")
    private Integer posgrade;
    /**
     * 职务
     */
    @ApiModelProperty(value="职务")
    private Integer position;
    /**
     * 合同次数
     */
    @ApiModelProperty(value="合同次数")
    private Integer concount;
    /**
     * 签约单位
     */
    @ApiModelProperty(value="签约单位")
    private Integer contract;
    /**
     * 合同类型
     */
    @ApiModelProperty(value="合同类型")
    private Integer contype;
    /**
     * 合同属性
     */
    @ApiModelProperty(value="合同属性")
    private Integer conproperty;
    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String conno;
    /**
     * 合同开始日期
     */
    @ApiModelProperty(value="合同开始日期")
    private String conbegindate;
    /**
     * 合同期(月)
     */
    @ApiModelProperty(value="合同期(月)")
    private Integer conterm;
    /**
     * 合同结束日期
     */
    @ApiModelProperty(value="合同结束日期")
    private String conenddate;
    /**
     * 新合同次数
     */
    @ApiModelProperty(value="新合同次数")
    private Integer newConcount;
    /**
     * 新合同签约单位
     */
    @ApiModelProperty(value="新合同签约单位")
    private Integer newContract;
    /**
     * 新合同类型
     */
    @ApiModelProperty(value="新合同类型")
    private Integer newContype;
    /**
     * 新合同属性
     */
    @ApiModelProperty(value="新合同属性")
    private Integer newConproperty;
    /**
     * 新合同编号
     */
    @ApiModelProperty(value="新合同编号")
    private String newConno;
    /**
     * 新合同开始日期
     */
    @ApiModelProperty(value="新合同开始日期")
    private String newConbegindate;
    /**
     * 新合同期(月)
     */
    @ApiModelProperty(value="新合同期(月)")
    private Integer newConterm;
    /**
     * 新合同结束日期
     */
    @ApiModelProperty(value="新合同结束日期")
    private String newConenddate;
    /**
     * 生效日期
     */
    @ApiModelProperty(value="生效日期")
    private String effectdate;
    /**
     * 原因
     */
    @ApiModelProperty(value="原因")
    private String reason;
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
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
	/**
	 * 到期提醒
	 */
	@ApiModelProperty(value="到期提醒")
	private Integer expirationreminder;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	private Integer userid;
	private Integer corpid;
    }
