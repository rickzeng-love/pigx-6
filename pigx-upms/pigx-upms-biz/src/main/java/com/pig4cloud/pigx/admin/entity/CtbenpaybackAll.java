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
 * 社保福利补缴历史
 *
 * @author xp
 * @date 2020-06-17 15:39:22
 */
@Data
@TableName("ctbenpayback_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "社保福利补缴历史")
public class CtbenpaybackAll extends Model<CtbenpaybackAll> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "")
	private Integer id;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer eid;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String badge;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String name;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer compid;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer depid;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer jobid;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer empstatus;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer emptype;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer empgroup;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer empkind;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer empgrade;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer empcategory;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer empproperty;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer workcity;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String joindate;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String term;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String paybackterm;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer months;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double accuselfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double accucomppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double bencomppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double benselfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String reason;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String effectdate;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer regby;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String regdate;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer initialized;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer initializedby;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String initializedtime;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer submit;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer submitby;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String submittime;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String remark;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer corpid;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double penSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double penCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double mdclSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double mdclCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double uempSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double uempCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double injuryCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double smdclSelfpayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double smdclCorppayback;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String corpcode;
}
