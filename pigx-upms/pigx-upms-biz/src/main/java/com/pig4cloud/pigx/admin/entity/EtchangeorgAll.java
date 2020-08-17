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
 * 调动登记历史
 *
 * @author gaoxiao
 * @date 2020-04-14 18:57:38
 */
@Data
@TableName("etchangeorg_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "调动登记历史")
public class EtchangeorgAll extends Model<EtchangeorgAll> {
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
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private Integer empstatus;
    /**
     * 岗位状态
     */
    @ApiModelProperty(value="岗位状态")
    private Integer jobstatus;
    /**
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private String joindate;
    /**
     * 上级领导
     */
    @ApiModelProperty(value="上级领导")
    private Integer reportto;
    /**
     * 职能上级
     */
    @ApiModelProperty(value="职能上级")
    private Integer wfreportto;
    /**
     * 新公司
     */
    @ApiModelProperty(value="新公司")
    private Integer newCompid;
    /**
     * 新部门
     */
    @ApiModelProperty(value="新部门")
    private Integer newDepid;
    /**
     * 新岗位
     */
    @ApiModelProperty(value="新岗位")
    private Integer newJobid;
    /**
     * 新上级领导
     */
    @ApiModelProperty(value="新上级领导")
    private Integer newReportto;
    /**
     * 新职能上级
     */
    @ApiModelProperty(value="新职能上级")
    private Integer newWfreportto;
    /**
     * 生效时间
     */
    @ApiModelProperty(value="生效时间")
    private String effectdate;
    /**
     * 变动类型
     */
    @ApiModelProperty(value="变动类型")
    private Integer orgchangetype;
    /**
     * 变动原因
     */
    @ApiModelProperty(value="变动原因")
    private Integer orgchangereason;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String errormsg;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记日期
     */
    @ApiModelProperty(value="登记日期")
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
     * 工种
     */
    @ApiModelProperty(value="工种")
    private Integer jobgz;
    /**
     * 新工种
     */
    @ApiModelProperty(value="新工种")
    private Integer newJobgz;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfinstid;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer position;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer posgrade;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer empgrade;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer newPosition;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer newPosgrade;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer newEmpgrade;
	/**
	 * 省
	 */
	@ApiModelProperty(value="省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value="市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(value="区")
	private String district;
	/**
	 * 省
	 */
	@ApiModelProperty(value="省")
	private String newProvince;
	/**
	 * 市
	 */
	@ApiModelProperty(value="市")
	private String newCity;
	/**
	 * 区
	 */
	@ApiModelProperty(value="区")
	private String newDistrict;
	private Integer coprid;
	private Integer isneedaudit;
	private String spno;
    }
