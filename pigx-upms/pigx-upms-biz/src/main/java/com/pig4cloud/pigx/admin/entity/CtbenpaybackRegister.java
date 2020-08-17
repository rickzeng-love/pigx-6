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
 * 福利补缴登记
 *
 * @author gaoxiao
 * @date 2020-06-13 10:35:25
 */
@Data
@TableName("ctbenpayback_register")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "福利补缴登记")
public class CtbenpaybackRegister extends Model<CtbenpaybackRegister> {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	@ApiModelProperty(value = "序号")
	private Integer id;
	/**
	 * 员工编码
	 */
	@ApiModelProperty(value = "员工编码")
	private Integer eid;
	/**
	 * 工号
	 */
	@ApiModelProperty(value = "工号")
	private String badge;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;
	/**
	 * 公司
	 */
	@ApiModelProperty(value = "公司")
	private Integer compid;
	/**
	 * 部门
	 */
	@ApiModelProperty(value = "部门")
	private Integer depid;
	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位")
	private Integer jobid;
	/**
	 * 员工状态
	 */
	@ApiModelProperty(value = "员工状态")
	private Integer empstatus;
	/**
	 * 员工类型
	 */
	@ApiModelProperty(value = "员工类型")
	private Integer emptype;
	/**
	 * 员工组
	 */
	@ApiModelProperty(value = "员工组")
	private Integer empgroup;
	/**
	 * 员工性质
	 */
	@ApiModelProperty(value = "员工性质")
	private Integer empkind;
	/**
	 * 员工级别
	 */
	@ApiModelProperty(value = "员工级别")
	private Integer empgrade;
	/**
	 * 员工类别
	 */
	@ApiModelProperty(value = "员工类别")
	private Integer empcategory;
	/**
	 * 员工属性
	 */
	@ApiModelProperty(value = "员工属性")
	private Integer empproperty;
	/**
	 * 工作城市
	 */
	@ApiModelProperty(value = "工作城市")
	private Integer workcity;
	/**
	 * 入职时间
	 */
	@ApiModelProperty(value = "入职时间")
	private String joindate;
	/**
	 * 薪资月份
	 */
	@ApiModelProperty(value = "薪资月份")
	private String term;
	/**
	 * 补缴月份
	 */
	@ApiModelProperty(value = "补缴月份")
	private String paybackterm;
	/**
	 * 补缴月数
	 */
	@ApiModelProperty(value = "补缴月数")
	private Integer months;
	/**
	 * 个人公积金补缴金额
	 */
	@ApiModelProperty(value = "个人公积金补缴金额")
	private Double accuselfpayback;
	/**
	 * 公司公积金补缴金额
	 */
	@ApiModelProperty(value = "公司公积金补缴金额")
	private Double accucomppayback;
	/**
	 * 社保公司缴纳额
	 */
	@ApiModelProperty(value = "社保公司缴纳额")
	private Double bencomppayback;
	/**
	 * 社保个人缴纳额
	 */
	@ApiModelProperty(value = "社保个人缴纳额")
	private Double benselfpayback;
	/**
	 * 原因
	 */
	@ApiModelProperty(value = "原因")
	private String reason;
	/**
	 * 生效时间
	 */
	@ApiModelProperty(value = "生效时间")
	private String effectdate;
	/**
	 * 登记人
	 */
	@ApiModelProperty(value = "登记人")
	private Integer regby;
	/**
	 * 登记时间
	 */
	@ApiModelProperty(value = "登记时间")
	private String regdate;
	/**
	 * 确认
	 */
	@ApiModelProperty(value = "确认")
	private Integer initialized;
	/**
	 * 确认人
	 */
	@ApiModelProperty(value = "确认人")
	private Integer initializedby;
	/**
	 * 确认时间
	 */
	@ApiModelProperty(value = "确认时间")
	private String initializedtime;
	/**
	 * 审核
	 */
	@ApiModelProperty(value = "审核")
	private Integer submit;
	/**
	 * 审核人
	 */
	@ApiModelProperty(value = "审核人")
	private Integer submitby;
	/**
	 * 审核时间
	 */
	@ApiModelProperty(value = "审核时间")
	private String submittime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 组织
	 */
	@ApiModelProperty(value = "组织")
	private String corpcode;
	/**
	 * CorpID
	 */
	@ApiModelProperty(value = "CorpID")
	private Integer corpid;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal penSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal penCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal mdclSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal mdclCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal uempSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal uempCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal injuryCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal smdclSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal smdclCorppayback;
}
