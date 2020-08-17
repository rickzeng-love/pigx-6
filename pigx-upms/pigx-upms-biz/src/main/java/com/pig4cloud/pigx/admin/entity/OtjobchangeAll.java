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
 * 岗位历史
 *
 * @author gaoxiao
 * @date 2020-04-07 16:29:46
 */
@Data
@TableName("otjobchange_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位历史")
public class OtjobchangeAll extends Model<OtjobchangeAll> {
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
     * 岗位编码
     */
    @ApiModelProperty(value="岗位编码")
    private Integer jobid;
    /**
     * 岗位代码
     */
    @ApiModelProperty(value="岗位代码")
    private String jobcode;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    private String title;
    /**
     * 岗位简称
     */
    @ApiModelProperty(value="岗位简称")
    private String jobabbr;
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
     * 上级岗位
     */
    @ApiModelProperty(value="上级岗位")
    private Integer adminid;
    /**
     * 职能上级
     */
    @ApiModelProperty(value="职能上级")
    private Integer functionid;
    /**
     * 岗位级别
     */
    @ApiModelProperty(value="岗位级别")
    private Integer jobgrade;
    /**
     * 岗位类型
     */
    @ApiModelProperty(value="岗位类型")
    private Integer jobtype;
    /**
     * 岗位序列
     */
    @ApiModelProperty(value="岗位序列")
    private Integer jobproperty;
    /**
     * 岗位编制
     */
    @ApiModelProperty(value="岗位编制")
    private Integer jobnum;
    /**
     * 是否核心岗位
     */
    @ApiModelProperty(value="是否核心岗位")
    private Integer iscore;
    /**
     * 是否计件
     */
    @ApiModelProperty(value="是否计件")
    private Integer ispiece;
    /**
     * 生效时间
     */
    @ApiModelProperty(value="生效时间")
    private LocalDateTime effectdate;
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
    private LocalDateTime initializedtime;
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
    private LocalDateTime submittime;
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
    private LocalDateTime closedtime;
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer xtype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double restjobnum;
    /**
     * 所属公司(原)
     */
    @ApiModelProperty(value="所属公司(原)")
    private Integer compidOld;
    /**
     * 岗位代码(原)
     */
    @ApiModelProperty(value="岗位代码(原)")
    private String jobcodeOld;
    /**
     * 岗位名称(原)
     */
    @ApiModelProperty(value="岗位名称(原)")
    private String titleOld;
    /**
     * 岗位简称(原)
     */
    @ApiModelProperty(value="岗位简称(原)")
    private String jobabbrOld;
    /**
     * 岗位级别(原)
     */
    @ApiModelProperty(value="岗位级别(原)")
    private Integer jobgradeOld;
    /**
     * 岗位类型(原)
     */
    @ApiModelProperty(value="岗位类型(原)")
    private Integer jobtypeOld;
    /**
     * 岗位属性(原)
     */
    @ApiModelProperty(value="岗位属性(原)")
    private Integer jobpropertyOld;
    /**
     * 岗位编制(原)
     */
    @ApiModelProperty(value="岗位编制(原)")
    private Integer jobnumOld;
    /**
     * 所属部门(原)
     */
    @ApiModelProperty(value="所属部门(原)")
    private Integer depidOld;
    /**
     * 上级岗位(原)
     */
    @ApiModelProperty(value="上级岗位(原)")
    private Integer adminidOld;
    /**
     * 职能上级(原)
     */
    @ApiModelProperty(value="职能上级(原)")
    private Integer functionidOld;
    /**
     * 对应职级
     */
    @ApiModelProperty(value="对应职级")
    private Integer empgradeid;
    /**
     * 对应职等
     */
    @ApiModelProperty(value="对应职等")
    private Integer posgradeid;
    /**
     * 岗位职责
     */
    @ApiModelProperty(value="岗位职责")
    private String jobdescription;
    /**
     * 任职要求
     */
    @ApiModelProperty(value="任职要求")
    private String jobrequirements;
	/**
	 * 登记人姓名
	 */
	@ApiModelProperty(value="登记人姓名")
	private String regname;
	@TableField(exist=false)
	private String regdate1;
	@TableField(exist=false)
	private String regdate2;
	private Integer corpid;
    }
