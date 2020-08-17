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

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 部门历史
 *
 * @author gaoxiao
 * @date 2020-04-07 10:09:02
 */
@Data
@TableName("otdepchange_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "部门历史")
public class OtdepchangeAll extends Model<OtdepchangeAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 变动类型
     */
    @ApiModelProperty(value="变动类型")
    private Integer type;
    /**
     * 部门编码
     */
    @ApiModelProperty(value="部门编码")
    private Integer depid;
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
     * 部门上级
     */
    @ApiModelProperty(value="部门上级")
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
     * 部门属性
     */
    @ApiModelProperty(value="部门属性")
    private Integer depproperty;
    /**
     * 部门成本
     */
    @ApiModelProperty(value="部门成本")
    private Integer depcost;
    /**
     * 部门主管
     */
    @ApiModelProperty(value="部门主管")
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
     * 编制人数
     */
    @ApiModelProperty(value="编制人数")
    private Integer depnum;
    /**
     * 组织代码
     */
    @ApiModelProperty(value="组织代码")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isou;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer isouOld;
    /**
     * 成立时间
     */
    @ApiModelProperty(value="成立时间")
    private String effectdate;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
	/**
	 * 登记人姓名
	 */
	@ApiModelProperty(value="登记人姓名")
	private String regbyname;

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
     * 
     */
    @ApiModelProperty(value="")
    private Integer depstr;
    /**
     * 所属公司(原)
     */
    @ApiModelProperty(value="所属公司(原)")
    private Integer compidOld;
    /**
     * 部门代码(原)
     */
    @ApiModelProperty(value="部门代码(原)")
    private String depcodeOld;
    /**
     * 部门名称(原)
     */
    @ApiModelProperty(value="部门名称(原)")
    private String titleOld;
    /**
     * 部门简称(原)
     */
    @ApiModelProperty(value="部门简称(原)")
    private String depabbrOld;
    /**
     * 部门上级(原)
     */
    @ApiModelProperty(value="部门上级(原)")
    private Integer adminidOld;
    /**
     * 部门级别(原)
     */
    @ApiModelProperty(value="部门级别(原)")
    private Integer depgradeOld;
    /**
     * 部门类型(原)
     */
    @ApiModelProperty(value="部门类型(原)")
    private Integer deptypeOld;
    /**
     * 部门属性(原)
     */
    @ApiModelProperty(value="部门属性(原)")
    private Integer deppropertyOld;
    /**
     * 部门成本(原)
     */
    @ApiModelProperty(value="部门成本(原)")
    private Integer depcostOld;
    /**
     * 部门主管(原)
     */
    @ApiModelProperty(value="部门主管(原)")
    private Integer directorOld;
    /**
     * 分管领导(原)
     */
    @ApiModelProperty(value="分管领导(原)")
    private Integer director2Old;
    /**
     * 部门人事专员(原)
     */
    @ApiModelProperty(value="部门人事专员(原)")
    private Integer depempOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer corpid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String address;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String orgfunction;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String decription;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String province;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String city;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String district;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String addressOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String orgfunctionOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String decriptionOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String provinceOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cityOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String districtOld;

	@ApiModelProperty(value="")
	@TableField(exist=false)
	private String regdate1;
	@TableField(exist=false)
	private String regdate2;
	@TableField(exist=false)
	private String stype;
    }
